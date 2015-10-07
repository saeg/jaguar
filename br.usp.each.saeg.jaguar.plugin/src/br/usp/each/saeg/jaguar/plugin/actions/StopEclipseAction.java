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

import br.usp.each.saeg.jaguar.plugin.JaguarPlugin;
import br.usp.each.saeg.jaguar.plugin.utils.EmailSend;

public class StopEclipseAction  extends Action implements IWorkbenchAction {

	private static final String ID = "br.usp.each.saeg.jaguar.plugin.actions.StopEclipseAction";
	private IProject project;
	private String POPUP_TITLE = "Eclipse debugging - ID generation";
	private String POPUP_MESSAGE = "The experiment's data was sent for our server. Thank you.";
	
	public StopEclipseAction(IProject project) {
		this.setEnabled(false);
		this.project = project;
	}

	public void run(){
		System.out.println("eclipse debugging session stopped");
		JaguarPlugin.ui(project, this, "eclipse debugging session stopped");
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



}
