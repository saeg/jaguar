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
	private String POPUP_MESSAGE = "Please try now to find the other bug in the project ... using only the Eclipse resources.";
	
	
	public StopJaguarAction(IProject project,ViewPart view) {
		this.setEnabled(false);
		this.project = project;
		this.view = view;
	}

	public void run(){
		System.out.println("jaguar debugging session stopped");
		JaguarPlugin.ui(project, this, "jaguar debugging session stopped");
		this.setEnabled(false);
		
		IViewPart explorerView = null;
		IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IViewPart []viewList = activePage.getViews();
		for(int i = 0; i < viewList.length; i++){
			if((viewList[i].getTitle().equals("Project Explorer") && activePage.getPerspective().getId().equals("org.eclipse.ui.resourcePerspective")) || 
					(viewList[i].getTitle().equals("Package Explorer") && activePage.getPerspective().getId().equals("org.eclipse.jdt.ui.JavaPerspective"))){
				
				explorerView = viewList[i];
				
				StopEclipseAction stopAction = new StopEclipseAction(project);
				stopAction.setText("Stop eclipse debugging session");
				ImageDescriptor stopImage = JaguarPlugin.imageDescriptorFromPlugin(JaguarPlugin.PLUGIN_ID, "icon/stop.png");
				stopAction.setImageDescriptor(stopImage);//ImageDescriptor.createFromFile(getClass(), "icon/jaguar.png"));
				
				StartEclipseAction startAction = new StartEclipseAction(project,stopAction);
				startAction.setText("Start eclipse debugging session");
				ImageDescriptor startImage = JaguarPlugin.imageDescriptorFromPlugin(JaguarPlugin.PLUGIN_ID, "icon/bug.png");
				startAction.setImageDescriptor(startImage);//ImageDescriptor.createFromFile(getClass(), "icon/jaguar.png"));
				
				//remove other toolbar buttons to put start in the project explorer view to put add the start and stop buttons first
				IToolBarManager tool = explorerView.getViewSite().getActionBars().getToolBarManager();
				List <IContributionItem> contributionList = new ArrayList<IContributionItem>();
				for(IContributionItem item : tool.getItems()){
					contributionList.add(item);
				}
				explorerView.getViewSite().getActionBars().getToolBarManager().removeAll();
				explorerView.getViewSite().getActionBars().getToolBarManager().add(startAction);
				explorerView.getViewSite().getActionBars().getToolBarManager().add(stopAction);
				
				for(IContributionItem item : contributionList){
					explorerView.getViewSite().getActionBars().getToolBarManager().add(item);
				}
				explorerView.getViewSite().getActionBars().getToolBarManager().update(true);
				
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
				
			}
		}
		
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
