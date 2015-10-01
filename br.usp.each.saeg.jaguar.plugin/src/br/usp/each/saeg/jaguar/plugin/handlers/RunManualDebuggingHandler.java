package br.usp.each.saeg.jaguar.plugin.handlers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import br.usp.each.saeg.jaguar.plugin.JaguarPlugin;
import br.usp.each.saeg.jaguar.plugin.ProjectUtils;
import br.usp.each.saeg.jaguar.plugin.actions.IdJaguarAction;
import br.usp.each.saeg.jaguar.plugin.actions.StartEclipseAction;
import br.usp.each.saeg.jaguar.plugin.actions.StopEclipseAction;
import br.usp.each.saeg.jaguar.plugin.project.ProjectPersistence;
import br.usp.each.saeg.jaguar.plugin.project.ProjectState;
import br.usp.each.saeg.jaguar.plugin.views.JaguarView;
import br.usp.each.saeg.jaguar.plugin.views.RoadmapView;

public class RunManualDebuggingHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final IProject project = ProjectUtils.getCurrentSelectedProject();
		if (!project.isOpen()) {
			return null;
		}

		ProjectState state = ProjectPersistence.getStateOf(project);
		if (state == null) {
			return null;
		}
		
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
				stopAction.setImageDescriptor(stopImage);
				
				StartEclipseAction startAction = new StartEclipseAction(project,stopAction);
				startAction.setText("Start eclipse debugging session");
				ImageDescriptor startImage = JaguarPlugin.imageDescriptorFromPlugin(JaguarPlugin.PLUGIN_ID, "icon/bug.png");
				startAction.setImageDescriptor(startImage);
				
				IdJaguarAction idAction = new IdJaguarAction(project,startAction);
				idAction.setText("Create ID number");
				ImageDescriptor idImage = JaguarPlugin.imageDescriptorFromPlugin(JaguarPlugin.PLUGIN_ID, "icon/key.png");
				idAction.setImageDescriptor(idImage);
				
				//remove other toolbar buttons to put start in the project explorer view to put add the start and stop buttons first
				IToolBarManager tool = explorerView.getViewSite().getActionBars().getToolBarManager();
				List <IContributionItem> contributionList = new ArrayList<IContributionItem>();
				for(IContributionItem item : tool.getItems()){
					contributionList.add(item);
				}
				explorerView.getViewSite().getActionBars().getToolBarManager().removeAll();
				explorerView.getViewSite().getActionBars().getToolBarManager().add(idAction);
				explorerView.getViewSite().getActionBars().getToolBarManager().add(startAction);
				explorerView.getViewSite().getActionBars().getToolBarManager().add(stopAction);
				for(IContributionItem item : contributionList){
					explorerView.getViewSite().getActionBars().getToolBarManager().add(item);
				}
				explorerView.getViewSite().getActionBars().getToolBarManager().update(true);
				
				//close editor windows
				closeAllEditors();
				
			}
		}
		
		JaguarPlugin.ui(project, this, "eclipse debugging activated");
		this.setEnabled(false);
		return null;
	}
	
	@Override
	public boolean isEnabled() {
		IProject project = ProjectUtils.getCurrentSelectedProject();
		if (project == null) {
			return false;
		}

		ProjectState state = ProjectPersistence.getStateOf(project);
		if (state == null) {
			return false;
		}

		return true;
	}
	
	private void closeAllEditors(){
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IWorkbenchPage page = window.getActivePage();
		page.closeAllEditors(true);
	}
	
}
