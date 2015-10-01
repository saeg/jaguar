package br.usp.each.saeg.jaguar.plugin.views.content;

import java.util.List;
import java.util.Map;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.text.Assert;

import br.usp.each.saeg.jaguar.plugin.ProjectUtils;
import br.usp.each.saeg.jaguar.plugin.project.ProjectPersistence;
import br.usp.each.saeg.jaguar.plugin.project.ProjectState;

public class JaguarMenuPropertyTester extends PropertyTester {
	
	private final String REPORT_FILE_NAME = "codeforest.xml";
	private static final String PROPERTY_XML_FILE = "hasXmlReportFile";
	
	@Override
	public boolean test(Object receiver, String propertyName, Object[] arg2, Object arg3) {
				
		/*final IProject project = ProjectUtils.getCurrentSelectedProject();
		if (!project.isOpen()) {
			return false;
		}

		ProjectState state = ProjectPersistence.getStateOf(project);
		if (state == null) {
			return false;
		}*/
		
		/*if (!(receiver instanceof IProject)) {
            return false;
        }

        IProject project = (IProject) receiver;

        if (!project.isOpen()) {
            return false;
        }*/
		/*if("hasXmlReportFile".equals(propertyName)){Assert.isTrue(false);}
		
		//IResource file = (IResource)receiver;//project.getFile(REPORT_FILE_NAME);
		return false;*///project.getName().equals("commons-math-sir");//file.getName().equals(REPORT_FILE_NAME);
		//return true;//file.exists();
		
		/*Map<String, List<IResource>> xmlFiles = ProjectUtils.xmlFilesOf(project);

		if (!xmlFiles.containsKey(REPORT_FILE_NAME) || xmlFiles.get(REPORT_FILE_NAME).size() > 1) {
			return false;
		}*/
		
		if (!(receiver instanceof IProject)) {
            return false;
        }

        IProject project = (IProject) receiver;

        if (!project.isOpen()) {
            return false;
        }

        return false;//ProjectUtils.javaFilesOf(project).size() > 0;
		
	}

}
