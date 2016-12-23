package br.usp.each.saeg.jaguar.plugin.actions;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;

import br.usp.each.saeg.jaguar.plugin.Configuration;
import br.usp.each.saeg.jaguar.plugin.JaguarPlugin;
import br.usp.each.saeg.jaguar.plugin.utils.IDValidator;

public class StartEclipseAction  extends Action implements IWorkbenchAction {
	private static final String ID = "br.usp.each.saeg.jaguar.plugin.actions.StartEclipseAction";
	private IProject project;
	Action stopAction;
	private String POPUP_TITLE = "";
	private String POPUP_MESSAGE = "";
	private String POPUP_ID_MESSAGE = "";
	
	public StartEclipseAction(IProject project, Action stop) {
		this.project = project;
		this.stopAction = stop;
		if(!Configuration.EXTERNAL_ID_GENERATION){
			if(!Configuration.EXPERIMENT_JAGUAR_FIRST){
				this.setEnabled(false);
			}
		}
		setPopupMessage();
	}

	public void run(){
		if(Configuration.EXTERNAL_ID_GENERATION){
			if(!Configuration.EXPERIMENT_JAGUAR_FIRST){
				InputDialog inputDialog = new InputDialog(Display.getCurrent().getActiveShell(),"ID",POPUP_ID_MESSAGE,"", new IDValidator());
				inputDialog.open();
				System.out.println("ID number is "+inputDialog.getValue());
				JaguarPlugin.ui(project, this, "ID number is "+inputDialog.getValue());
			}
		}
				
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

	private void setPopupMessage(){
		if(!Configuration.LANGUAGE_EN){
			POPUP_TITLE = "Depuracao usando o Eclipse";
			POPUP_MESSAGE = "Voce pode comecar esta tarefa olhando os casos de teste. \nClique com o botao direito do mouse sobre \"nome_do_projeto > Run As > JUnit Test\".\nQuando terminar a tarefa, clique no botao \"stop\" na parte superior da janela Project Explorer.";
			POPUP_ID_MESSAGE = "Digite o seu ID de 8 digitos";
		}else{
			POPUP_TITLE = "Eclipse Debugging";
			POPUP_MESSAGE = "To perform the task, you can start by looking the test cases. \nRight-click on \"project_name > Run As > JUnit Test\".\nWhen you finish the task, click on the \"stop\" button at the top of the Project Explorer area.";
			POPUP_ID_MESSAGE = "Enter your 8-digit ID number";
			}
	}
}
