package br.usp.each.saeg.jaguar.plugin.actions;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.part.ViewPart;

import br.usp.each.saeg.jaguar.plugin.JaguarPlugin;
import br.usp.each.saeg.jaguar.plugin.ProjectUtils;
import br.usp.each.saeg.jaguar.plugin.views.JaguarView;
import br.usp.each.saeg.jaguar.plugin.views.RoadmapView;

public class StartJaguarAction extends Action implements IWorkbenchAction {
	
	private static final String ID = "br.usp.each.saeg.jaguar.plugin.actions.StartJaguarAction";
	private IProject project;
	Action stopAction;
	ViewPart view;
	
	public StartJaguarAction(IProject project, Action stop, ViewPart view) {
		this.setEnabled(false);
		this.project = project;
		this.stopAction = stop;
		this.view = view;
	}

	public void run(){
		System.out.println("jaguar debugging session started");
		JaguarPlugin.ui(project, this, "jaguar debugging session started");
		if(view instanceof JaguarView){
			((JaguarView)view).loadView();
		}
		if(view instanceof RoadmapView){
			((RoadmapView)view).loadView();
		}
		this.setEnabled(false);
		this.stopAction.setEnabled(true);
	}
	
	@Override
	public void dispose() {
	}

}
