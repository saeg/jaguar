package br.usp.each.saeg.jaguar.plugin.handlers;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import br.usp.each.saeg.jaguar.codeforest.model.FaultClassification;
import br.usp.each.saeg.jaguar.codeforest.model.TestCriteria;
import br.usp.each.saeg.jaguar.plugin.JaguarPlugin;
import br.usp.each.saeg.jaguar.plugin.markers.CodeMarkerFactory;
import br.usp.each.saeg.jaguar.plugin.data.CodeDataBuilder;
import br.usp.each.saeg.jaguar.plugin.data.CodeDataBuilderResult;
import br.usp.each.saeg.jaguar.plugin.editor.EditorTracker;
import br.usp.each.saeg.jaguar.plugin.project.ProjectPersistence;
import br.usp.each.saeg.jaguar.plugin.project.ProjectState;
import br.usp.each.saeg.jaguar.plugin.source.parser.ParsingResult;
import br.usp.each.saeg.jaguar.plugin.source.parser.SourceCodeParser;
import br.usp.each.saeg.jaguar.plugin.source.parser.SourceCodeUtils;
import br.usp.each.saeg.jaguar.plugin.ProjectUtils;
import br.usp.each.saeg.jaguar.plugin.views.content.XmlDataReader;
import br.usp.each.saeg.jaguar.codeforest.model.Package;
import br.usp.each.saeg.jaguar.codeforest.model.Class;
import br.usp.each.saeg.jaguar.codeforest.model.Method;

/**
 * @author Danilo Mutti (dmutti@gmail.com)
 * @author Higor Amario (higoramario@gmail.com)
 */
public class AddColorHandler extends AbstractHandler {
	
	private IProject project;
	private final String REPORT_FILE_NAME = "codeforest.xml";
	
	public AddColorHandler() {
		super();
	}

	public AddColorHandler(IProject project) {
		super();
		this.project = project;
	}
	
	@Override
	public Object execute(ExecutionEvent arg) throws ExecutionException {
		if (project == null){
				project = ProjectUtils.getCurrentSelectedProject();
		}
		
		if (!project.isOpen()) {
			return null;
		}
		
		try {
			project.refreshLocal(IResource.DEPTH_ONE, null);
		} catch (CoreException e) {
			e.printStackTrace();
		}
		
		TestCriteria testCriteria = XmlDataReader.readXml(project.getFile(REPORT_FILE_NAME).getLocation().toFile());
		
		//TODO remove - this workaround is for the method's name including the return type
		for(Package pack : testCriteria.getPackages()){
			for(Class clazz: pack.getClasses()){
				for(Method method : clazz.getMethods()){
					String methodName = method.getName().substring(method.getName().indexOf(" ")+1, method.getName().length());
					methodName = methodName.replace(" ", "");
					method.setName(methodName);
				}
			}
		}
		
		ProjectState state = ProjectPersistence.getStateOf(project);
		if (state == null) {
			return null;
		}
		Map<IResource, List<Map<String, Object>>> resourceMarkerProps = new IdentityHashMap<IResource, List<Map<String, Object>>>();
		state.setRequirementType(testCriteria.getRequirementType());//setting the correct requirementType
		
		for (List<IResource> files : ProjectUtils.javaFilesOf(project).values()) {
			for (IResource file : files) {
				ParsingResult result = parse(file, testCriteria);
				CodeDataBuilderResult buildResult = CodeDataBuilder.from(result, SourceCodeUtils.read(file));
				resourceMarkerProps.put(buildResult.getResource(), buildResult.getMarkerProperties());
				state.getAnalysisResult().put(result.getURI(), buildResult.getClassData());
			}
		}

		CodeMarkerFactory.scheduleMarkerCreation(resourceMarkerProps);
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(arg);
		IWorkbenchPage page = window.getActivePage();
		for (IEditorReference editorRef : page.getEditorReferences()) {
			JaguarPlugin.getEditorTracker().annotateEditor(editorRef);
		}
		state.setAnalyzed(true);
		JaguarPlugin.ui(project, this, "jaguar coloring analysis started");
		
		state.createPackageResult(testCriteria);
		
		return null;
	}
	
	// add annotations when the editor is opened using the listener
	private ParsingResult parse(final IResource file, final TestCriteria input) {
		
		ASTParser parser = ASTParser.newParser(4);//AST.JLS4
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		char[] trimmedSource = SourceCodeUtils.readAndTrim(file);
		parser.setSource(trimmedSource);
		parser.setResolveBindings(true);

		@SuppressWarnings("unchecked")
		Hashtable<String, String> options = JavaCore.getOptions();
		options.put(JavaCore.COMPILER_DOC_COMMENT_SUPPORT, JavaCore.DISABLED);
		parser.setCompilerOptions(options);

		CompilationUnit cu = (CompilationUnit) parser.createAST(null);

		ParsingResult result = new ParsingResult(file);
		cu.accept(new SourceCodeParser(cu, trimmedSource, input, result, project));
		return result;
	}
	
	@Override
	public boolean isEnabled() {
		IProject project = ProjectUtils.getCurrentSelectedProject();
		if (project == null) {
			return false;
		}

		ProjectState state = ProjectPersistence.getStateOf(project);
		if (state == null) {
			return false;
		}
		
		Map<String, List<IResource>> xmlFiles = ProjectUtils.xmlFilesOf(project);

		if (!xmlFiles.containsKey(REPORT_FILE_NAME) || xmlFiles.get(REPORT_FILE_NAME).size() > 1) {
			return false;
		}
		
		if (!state.isAnalyzed()) {
			return true;
		}

		return false;
	}
	
}