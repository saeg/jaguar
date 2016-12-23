package br.usp.each.saeg.jaguar.plugin.handlers;

import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.jface.dialogs.MessageDialog;

import br.usp.each.saeg.jaguar.plugin.Configuration;
import br.usp.each.saeg.jaguar.plugin.ProjectUtils;
import br.usp.each.saeg.jaguar.plugin.project.ProjectPersistence;
import br.usp.each.saeg.jaguar.plugin.project.ProjectState;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class RunAllHandler extends AbstractHandler {
	
	private final String REPORT_FILE_NAME = "jaguar.xml";
	private String POPUP_TITLE = "";
	private String POPUP_MESSAGE = "";
	
	public RunAllHandler() {
		setPopupMessage();
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final IProject project = ProjectUtils.getCurrentSelectedProject();
		if (!project.isOpen()) {
			return null;
		}

		ProjectState state = ProjectPersistence.getStateOf(project);
		if (state == null) {
			return null;
		}
		
		final AddColorHandler addColorHandler = new AddColorHandler(project);
		final OnlyAfterColoringHandler viewHandler;
		
		if(Configuration.ROADMAP){
			viewHandler = new RoadmapViewHandler(project);
		}else if(Configuration.CODEHIERARCHY){
			viewHandler = new JaguarViewHandler(project);
		}else if(Configuration.ONLY_METHOD_LEVEL){
			viewHandler = new MethodViewHandler(project);
		}else if(Configuration.ONLY_LINE_DUA_LEVEL){
			viewHandler = new LineDuaViewHandler(project);
		}
		
		closeAllEditors();
		try {
			addColorHandler.execute(event);
			viewHandler.execute(event);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		if(Configuration.EXPERIMENT_VERSION){
			/*if(Configuration.EXPERIMENT_JAGUAR_FIRST){
				if(!Configuration.EXTERNAL_ID_GENERATION){
					openDialogPopup(POPUP_MESSAGE);
				}
			}else{
				openDialogPopup(POPUP_MESSAGE);
			}*/
			openDialogPopup(POPUP_MESSAGE);
		}
		
		return null;
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
		
		if (!xmlFiles.containsKey(REPORT_FILE_NAME)){// || xmlFiles.get(REPORT_FILE_NAME).size() > 1) { //error in the Ant project, counts two files instead of one
			return false;
		}
		
		if (state.isAnalyzed()) {
            return false;
        }
		
		if(Configuration.EXPERIMENT_VERSION){
			if(!Configuration.EXPERIMENT_JAGUAR_FIRST){
				IWorkbench workbench = PlatformUI.getWorkbench();
				ICommandService service = (ICommandService) workbench.getService(ICommandService.class);
				Command command = service.getCommand("br.usp.each.saeg.jaguar.plugin.commands.manualDebugging");
				if(command.isEnabled()){
					return false;
				}
			}
		}
		
		return true;
	}
	
	private void closeAllEditors(){
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IWorkbenchPage page = window.getActivePage();
		page.closeAllEditors(true);
	}
	
	private void openDialogPopup(String idMessage) {
		MessageDialog.openInformation(new Shell(),POPUP_TITLE,idMessage);
	}
	
	private void setPopupMessage(){
		if(!Configuration.LANGUAGE_EN){
			POPUP_TITLE = "Depuracao usando a Jaguar";
			if(!Configuration.EXTERNAL_ID_GENERATION && Configuration.EXPERIMENT_JAGUAR_FIRST){
				POPUP_MESSAGE = "Ok! Para gerar o seu numero ID, clique no botao \"chave\" na parte superior da Jaguar View.";
			}else{
				POPUP_MESSAGE = "Ok! Para iniciar esta tarefa, clique no botao \"bug\" na parte superior da Jaguar View.";
			}
		}else{
			POPUP_TITLE = "Jaguar Debugging";
			if(!Configuration.EXTERNAL_ID_GENERATION && Configuration.EXPERIMENT_JAGUAR_FIRST){
				POPUP_MESSAGE = "Ok! To generate your ID number, click on \"key\" button at the top of Jaguar View area.";
			}else{
				POPUP_MESSAGE = "Ok! To start this task, click on the \"bug\" button at the top of Jaguar area.";
			}
		}
	}
}
