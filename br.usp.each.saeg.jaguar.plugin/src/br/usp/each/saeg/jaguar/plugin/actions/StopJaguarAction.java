package br.usp.each.saeg.jaguar.plugin.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.part.ViewPart;

import br.usp.each.saeg.jaguar.plugin.JaguarPlugin;
import br.usp.each.saeg.jaguar.plugin.handlers.AddColorHandler;
import br.usp.each.saeg.jaguar.plugin.handlers.JaguarViewHandler;
import br.usp.each.saeg.jaguar.plugin.handlers.RemoveColorHandler;
import br.usp.each.saeg.jaguar.plugin.utils.EmailSend;
import br.usp.each.saeg.jaguar.plugin.views.JaguarView;
import br.usp.each.saeg.jaguar.plugin.views.RoadmapView;

public class StopJaguarAction extends Action implements IWorkbenchAction {
	
	private static final String ID = "br.usp.each.saeg.jaguar.plugin.actions.StopJaguarAction";
	private IProject project;
	private ViewPart view;
	private String POPUP_TITLE = "JaguarView Debugging";
	private String POPUP_MESSAGE = "The experiment's data was sent for our server. Thank you.";
	
	
	public StopJaguarAction(IProject project,ViewPart view) {
		this.setEnabled(false);
		this.project = project;
		this.view = view;
	}

	public void run(){
		System.out.println("jaguar debugging session stopped");
		JaguarPlugin.ui(project, this, "jaguar debugging session stopped");
		this.setEnabled(false);
		
		//sending email - only when the roadmap is used at first
		/*try {
			EmailSend.generateAndSendEmail();
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		//remove colors
		final RemoveColorHandler removeColorHandler = new RemoveColorHandler(project);
		try {
			removeColorHandler.execute(new ExecutionEvent());
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		//close jaguar view
		if(view instanceof JaguarView || view instanceof RoadmapView){
			closeView();
		}
		//close editor windows
		closeAllEditors();
		
		openDialogPopup(POPUP_MESSAGE);
		
	}
	
	private void openDialogPopup(String idMessage) {
		MessageDialog.openInformation(new Shell(),POPUP_TITLE,idMessage);
	}
	
	private void closeAllEditors(){
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IWorkbenchPage page = window.getActivePage();
		page.closeAllEditors(true);
	}
	
	private void closeView(){
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		for(IViewReference viewReference : page.getViewReferences()){
			IViewPart viewPart = viewReference.getView(false);
			if(viewPart instanceof JaguarView || viewPart instanceof RoadmapView){
				page.hideView(viewPart);
			}
		}
	}
	
	@Override
	public void dispose() {
	}

}
