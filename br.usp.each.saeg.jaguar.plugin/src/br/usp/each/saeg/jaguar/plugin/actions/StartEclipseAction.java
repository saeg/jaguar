package br.usp.each.saeg.jaguar.plugin.actions;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;

import br.usp.each.saeg.jaguar.plugin.JaguarPlugin;

public class StartEclipseAction  extends Action implements IWorkbenchAction {
	private static final String ID = "br.usp.each.saeg.jaguar.plugin.actions.StartEclipseAction";
	private IProject project;
	Action stopAction;
	
	public StartEclipseAction(IProject project, Action stop) {
		this.project = project;
		this.stopAction = stop;
	}

	public void run(){
		System.out.println("eclipse debugging session started");
		JaguarPlugin.ui(project, this, "eclipse debugging session started");
		this.setEnabled(false);
		this.stopAction.setEnabled(true);
	}
	
	@Override
	public void dispose() {
	}


}
