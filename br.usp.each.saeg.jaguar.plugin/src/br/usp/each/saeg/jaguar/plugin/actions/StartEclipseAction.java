package br.usp.each.saeg.jaguar.plugin.actions;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;

import br.usp.each.saeg.jaguar.plugin.Configuration;
import br.usp.each.saeg.jaguar.plugin.JaguarPlugin;

public class StartEclipseAction  extends Action implements IWorkbenchAction {
	private static final String ID = "br.usp.each.saeg.jaguar.plugin.actions.StartEclipseAction";
	private IProject project;
	Action stopAction;
	private String POPUP_TITLE = "Eclipse Debugging";
	private String POPUP_MESSAGE = "To perform the task, you can start by looking the test cases. \nRight-click on project_name > Run As > JUnit Test.\nWhen you finish the task, click on the \"stop\" button at the top of the Project Explorer area.";
	
	public StartEclipseAction(IProject project, Action stop) {
		this.project = project;
		this.stopAction = stop;
		if(!Configuration.EXPERIMENT_JAGUAR_FIRST){
			this.setEnabled(false);
		}
	}

	public void run(){
		System.out.println("eclipse debugging session started");
		JaguarPlugin.ui(project, this, "eclipse debugging session started");
		this.setEnabled(false);
		this.stopAction.setEnabled(true);
		openDialogPopup(POPUP_MESSAGE);
	}
	
	private void openDialogPopup(String idMessage) {
		MessageDialog.openInformation(new Shell(),POPUP_TITLE,idMessage);
	}
	
	@Override
	public void dispose() {
	}


}
