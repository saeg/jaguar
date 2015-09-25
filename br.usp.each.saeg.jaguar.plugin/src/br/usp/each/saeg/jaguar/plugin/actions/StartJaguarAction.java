package br.usp.each.saeg.jaguar.plugin.actions;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;

import br.usp.each.saeg.jaguar.plugin.JaguarPlugin;
import br.usp.each.saeg.jaguar.plugin.ProjectUtils;

public class StartJaguarAction extends Action implements IWorkbenchAction {
	
	private static final String ID = "br.usp.each.saeg.jaguar.plugin.actions.StartJaguarAction";
	private IProject project;
	Action stopAction;
	
	public StartJaguarAction(IProject project, Action stop) {
		this.setEnabled(false);
		this.project = project;
		this.stopAction = stop;
	}

	public void run(){
		System.out.println("jaguar debugging session started");
		JaguarPlugin.ui(project, this, "jaguar debugging session started");
		this.setEnabled(false);
		this.stopAction.setEnabled(true);
	}
	
	@Override
	public void dispose() {
	}

}
