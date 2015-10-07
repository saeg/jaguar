package br.usp.each.saeg.jaguar.plugin.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import br.usp.each.saeg.jaguar.plugin.JaguarPlugin;
import br.usp.each.saeg.jaguar.plugin.ProjectUtils;
import br.usp.each.saeg.jaguar.plugin.views.JaguarView;
import br.usp.each.saeg.jaguar.plugin.views.RoadmapView;

public class RoadmapViewHandler extends OnlyAfterColoringHandler {
	
	IProject project;
	
	public RoadmapViewHandler(){
		super();
	}
	
	public RoadmapViewHandler(IProject project){
		super();
		this.project = project;
	}
	
	@Override
	public Object execute(ExecutionEvent arg0) throws ExecutionException {
		if (project == null){
			project = ProjectUtils.getCurrentSelectedProject();
		}
		
        try {
        	PlatformUI.getWorkbench().getWorkbenchWindows()[0].getActivePage().showView(RoadmapView.ID, project.getName(), IWorkbenchPage.VIEW_VISIBLE);
        } catch (Exception e) {
        	e.printStackTrace();
           JaguarPlugin.log(e);
        }
        JaguarPlugin.ui(project, this, "roadmap view");
        return null;
	}

}
