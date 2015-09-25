package br.usp.each.saeg.jaguar.plugin.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

import br.usp.each.saeg.jaguar.plugin.JaguarPlugin;
import br.usp.each.saeg.jaguar.plugin.ProjectUtils;
import br.usp.each.saeg.jaguar.plugin.views.JaguarView;

/**
 * @author Danilo Mutti (dmutti@gmail.com)
 * @author Higor Amario (higoramario@gmail.com)
 */
public class JaguarViewHandler extends OnlyAfterColoringHandler {

	IProject project;
	
	public JaguarViewHandler(){
		super();
	}
	
	public JaguarViewHandler(IProject project){
		super();
		this.project = project;
	}
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		if (project == null){
			project = ProjectUtils.getCurrentSelectedProject();
		}
		
        try {
        	PlatformUI.getWorkbench().getWorkbenchWindows()[0].getActivePage().showView(JaguarView.ID, project.getName(), IWorkbenchPage.VIEW_VISIBLE);
        } catch (Exception e) {
        	e.printStackTrace();
           JaguarPlugin.log(e);
        }
        JaguarPlugin.ui(project, this, "jaguar view");
        return null;
	}

}
