package br.usp.each.saeg.jaguar.plugin.actions;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;

import br.usp.each.saeg.jaguar.plugin.JaguarPlugin;
import br.usp.each.saeg.jaguar.plugin.utils.EmailSend;

public class StopEclipseAction  extends Action implements IWorkbenchAction {

	private static final String ID = "br.usp.each.saeg.jaguar.plugin.actions.StopEclipseAction";
	private IProject project;
	private String POPUP_TITLE = "Jaguar - ID generation";
	private String POPUP_MESSAGE = "The experiment's data was sent for our server. Thank you.";
	
	public StopEclipseAction(IProject project) {
		this.setEnabled(false);
		this.project = project;
	}

	public void run(){
		System.out.println("jaguar debugging session stopped");
		JaguarPlugin.ui(project, this, "jaguar debugging session stopped");
		this.setEnabled(false);
				
		//sending email - only when the eclipse debugging is used at last
		/*try {
			EmailSend.generateAndSendEmail();
			openDialogPopup(POPUP_MESSAGE);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		openDialogPopup(POPUP_MESSAGE);
		//send email, remove color, remove data
	}
	
	private void openDialogPopup(String idMessage) {
		MessageDialog.openInformation(new Shell(),POPUP_TITLE,idMessage);
	}
	
	@Override
	public void dispose() {
	}



}
