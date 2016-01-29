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
	private String POPUP_TITLE = "Eclipse debugging";
	private String POPUP_MESSAGE = "The experiment data were sent to our server. Thank you.";
	
	public StopEclipseAction(IProject project) {
		this.setEnabled(false);
		this.project = project;
		if(!Configuration.EXPERIMENT_JAGUAR_FIRST){
			POPUP_MESSAGE = "For the next task, try to find the bug without using Jaguar.\n Right-click on project > Jaguar > Run Jaguar";
		}
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

}
