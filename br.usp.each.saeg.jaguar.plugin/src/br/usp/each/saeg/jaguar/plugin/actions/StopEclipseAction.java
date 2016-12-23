package br.usp.each.saeg.jaguar.plugin.actions;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;

import br.usp.each.saeg.jaguar.plugin.Configuration;
import br.usp.each.saeg.jaguar.plugin.JaguarPlugin;
import br.usp.each.saeg.jaguar.plugin.utils.EmailSend;
import br.usp.each.saeg.jaguar.plugin.utils.ScpSend;

public class StopEclipseAction  extends Action implements IWorkbenchAction {

	private static final String ID = "br.usp.each.saeg.jaguar.plugin.actions.StopEclipseAction";
	private IProject project;
	private String POPUP_TITLE = "";
	private String POPUP_MESSAGE = "";
	
	public StopEclipseAction(IProject project) {
		this.setEnabled(false);
		this.project = project;
		setPopupMessage();
	}

	public void run(){
		System.out.println("eclipse debugging session stopped");
		JaguarPlugin.ui(project, this, "eclipse debugging session stopped");
		this.setEnabled(false);
				
		if(Configuration.EXPERIMENT_JAGUAR_FIRST && Configuration.SEND_DATA){
			sendData();
		}
		
		//close editor windows
		closeAllEditors();
		
		openDialogPopup(POPUP_MESSAGE);
		//send email, remove color, remove data
	}
	
	private void openDialogPopup(String idMessage) {
		MessageDialog.openInformation(new Shell(),POPUP_TITLE,idMessage);
	}
	
	private void closeAllEditors(){
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IWorkbenchPage page = window.getActivePage();
		page.closeAllEditors(true);
	}
	
	@Override
	public void dispose() {
	}

	public void sendEmail(){
		try {
			EmailSend.generateAndSendEmail();
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	public void sendData(){
		ScpSend scp = new ScpSend();
		scp.sendFile();
	}
	
	private void setPopupMessage(){
		if(!Configuration.LANGUAGE_EN){
			POPUP_TITLE = "Depuracao usando o Eclipse";
			if(Configuration.EXPERIMENT_JAGUAR_FIRST){
				POPUP_MESSAGE = "Os dados do experimento foram enviados para o nosso servidor. \nPor favor responda o questionario para finalizar o experimento. \nObrigado.";
			}else{
				POPUP_MESSAGE = "Na tarefa seguinte, procure pelo defeito usando a Jaguar.\n Clique com o botao direito do mouse em \"nome_do_projeto > Jaguar > Run Jaguar\" para comecar";
			}
		}else{
			POPUP_TITLE = "Eclipse Debugging";
			if(Configuration.EXPERIMENT_JAGUAR_FIRST){
				POPUP_MESSAGE = "The experiment data was sent to our server. \nPlease fill out the questionnaire to finish the experiment. \nThank you.";
			}else{
				POPUP_MESSAGE = "For the next task, try to find the bug using Jaguar.\n Right-click on \"project_name > Jaguar > Run Jaguar\" to start";
			}
		}
	}

}
