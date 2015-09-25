package br.usp.each.saeg.jaguar.plugin.actions;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;

import br.usp.each.saeg.jaguar.plugin.JaguarPlugin;
import br.usp.each.saeg.jaguar.plugin.utils.EmailSend;

public class StopJaguarAction extends Action implements IWorkbenchAction {
	
	private static final String ID = "br.usp.each.saeg.jaguar.plugin.actions.StopJaguarAction";
	private IProject project;
	private String POPUP_TITLE = "JaguarView Debugging";
	private String POPUP_MESSAGE = "Please try now to find the other bug in the project ... using only the Eclipse resources.";
	
	
	public StopJaguarAction(IProject project) {
		this.setEnabled(false);
		this.project = project;
	}

	public void run(){
		System.out.println("jaguar debugging session stopped");
		JaguarPlugin.ui(project, this, "jaguar debugging session stopped");
		this.setEnabled(false);
		
		IViewPart explorerView = null;
		IViewPart []viewList = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getViews();//.findView("org.eclipse.jdt.ui.PackageExplorer");
		for(int i = 0; i < viewList.length; i++){
			if(viewList[i].getTitle().equals("Project Explorer")){//.getViewSite().getId().equals("org.eclipse.ui.navigator.resources.ProjectExplorer")){
				explorerView = viewList[i];
				
				
				StopEclipseAction stopAction = new StopEclipseAction(project);
				stopAction.setText("Stop eclipse debugging session");
				ImageDescriptor stopImage = JaguarPlugin.imageDescriptorFromPlugin(JaguarPlugin.PLUGIN_ID, "icon/control_stop_blue.png");
				stopAction.setImageDescriptor(stopImage);//ImageDescriptor.createFromFile(getClass(), "icon/jaguar.png"));
				
				StartEclipseAction startAction = new StartEclipseAction(project,stopAction);
				startAction.setText("Start eclipse debugging session");
				ImageDescriptor startImage = JaguarPlugin.imageDescriptorFromPlugin(JaguarPlugin.PLUGIN_ID, "icon/control_play_blue.png");
				startAction.setImageDescriptor(startImage);//ImageDescriptor.createFromFile(getClass(), "icon/jaguar.png"));
				
				
				explorerView.getViewSite().getActionBars().getToolBarManager().add(startAction);
				explorerView.getViewSite().getActionBars().getToolBarManager().add(stopAction);
				explorerView.getViewSite().getActionBars().getToolBarManager().update(true);
			}
		}
		
		//sending email - only when the roadmap is used at last
		/*try {
			EmailSend.generateAndSendEmail();
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		openDialogPopup(POPUP_MESSAGE);
		
		//send email, remove color, remove data, close jaguarview
	}
	
	private void openDialogPopup(String idMessage) {
		MessageDialog.openInformation(new Shell(),POPUP_TITLE,idMessage);
	}
	
	@Override
	public void dispose() {
	}

}
