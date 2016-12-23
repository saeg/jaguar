package br.usp.each.saeg.jaguar.plugin.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import br.usp.each.saeg.jaguar.plugin.Configuration;
import br.usp.each.saeg.jaguar.plugin.JaguarPlugin;
import br.usp.each.saeg.jaguar.plugin.ProjectUtils;
import br.usp.each.saeg.jaguar.plugin.actions.IdAction;
import br.usp.each.saeg.jaguar.plugin.actions.StartEclipseAction;
import br.usp.each.saeg.jaguar.plugin.actions.StopEclipseAction;
import br.usp.each.saeg.jaguar.plugin.project.ProjectPersistence;
import br.usp.each.saeg.jaguar.plugin.project.ProjectState;

public class RunManualDebuggingHandler extends AbstractHandler {
	
	private final String REPORT_FILE_NAME = "jaguar.xml";
	private boolean used = false;
	private String POPUP_TITLE = "";
	private String POPUP_MESSAGE = "";
	
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
		
		if(!Configuration.EXPERIMENT_JAGUAR_FIRST){
			IViewPart explorerView = null;
			IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			IViewPart []viewList = activePage.getViews();
			for(int i = 0; i < viewList.length; i++){
				if((viewList[i].getTitle().equals("Project Explorer") && activePage.getPerspective().getId().equals("org.eclipse.ui.resourcePerspective")) || 
					(viewList[i].getTitle().equals("Package Explorer") && activePage.getPerspective().getId().equals("org.eclipse.jdt.ui.JavaPerspective"))){
					explorerView = viewList[i];
									
					StopEclipseAction stopAction = new StopEclipseAction(project);
					stopAction.setText((Configuration.LANGUAGE_EN)?"Stop eclipse debugging session":"Finalizar depuracao");
					ImageDescriptor stopImage = JaguarPlugin.imageDescriptorFromPlugin(JaguarPlugin.PLUGIN_ID, "icon/stop.png");
					stopAction.setImageDescriptor(stopImage);
					
					StartEclipseAction startAction = new StartEclipseAction(project,stopAction);
					startAction.setText((Configuration.LANGUAGE_EN)?"Start eclipse debugging session":"Iniciar depuracao");
					ImageDescriptor startImage = JaguarPlugin.imageDescriptorFromPlugin(JaguarPlugin.PLUGIN_ID, "icon/bug.png");
					startAction.setImageDescriptor(startImage);
					
					IdAction idAction = null;
					if(!Configuration.EXTERNAL_ID_GENERATION){
						idAction = new IdAction(project,startAction);
						idAction.setText((Configuration.LANGUAGE_EN)?"Create ID number":"Criar numero ID");
						ImageDescriptor idImage = JaguarPlugin.imageDescriptorFromPlugin(JaguarPlugin.PLUGIN_ID, "icon/key.png");
						idAction.setImageDescriptor(idImage);
					}
					//remove other toolbar buttons to put start in the project explorer view to put add the start and stop buttons first
					IToolBarManager tool = explorerView.getViewSite().getActionBars().getToolBarManager();
					List <IContributionItem> contributionList = new ArrayList<IContributionItem>();
					for(IContributionItem item : tool.getItems()){
						contributionList.add(item);
					}
					explorerView.getViewSite().getActionBars().getToolBarManager().removeAll();
					if(!Configuration.EXTERNAL_ID_GENERATION){
						explorerView.getViewSite().getActionBars().getToolBarManager().add(idAction);
					}
					explorerView.getViewSite().getActionBars().getToolBarManager().add(startAction);
					explorerView.getViewSite().getActionBars().getToolBarManager().add(stopAction);
					for(IContributionItem item : contributionList){
						explorerView.getViewSite().getActionBars().getToolBarManager().add(item);
					}
					explorerView.getViewSite().getActionBars().getToolBarManager().update(true);
					
					//close editor windows
					closeAllEditors();
					
					if(Configuration.EXPERIMENT_VERSION){
						setPopupMessage();
						openDialogPopup(POPUP_MESSAGE);
					}
				}
			}
			
			JaguarPlugin.ui(project, this, "eclipse debugging activated");
		}
		//this.setEnabled(false);
		used = true;
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
		
		if(!Configuration.EXPERIMENT_VERSION){
			return false;
		}
		
		if(Configuration.EXPERIMENT_JAGUAR_FIRST){
			return false;
		}
		
		if(used){
			return false;
		}
		
		Map<String, List<IResource>> xmlFiles = ProjectUtils.xmlFilesOf(project);
		if (xmlFiles.containsKey(REPORT_FILE_NAME)){
			return false;
		}

		return true;
	}
	
	private void closeAllEditors(){
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IWorkbenchPage page = window.getActivePage();
		page.closeAllEditors(true);
	}
	
	private void openDialogPopup(String idMessage) {
		MessageDialog.openInformation(new Shell(),POPUP_TITLE,idMessage);
	}
	
	private void setPopupMessage(){
		if(!Configuration.LANGUAGE_EN){
			POPUP_TITLE = "Depuracao usando o Eclipse";
			if(!Configuration.EXTERNAL_ID_GENERATION){
				POPUP_MESSAGE = "Ok! Para gerar o seu numero ID, clique no botao \"chave\" na area superior da janela Project Explorer.";
			}else{
				POPUP_MESSAGE = "Ok! Clique no botao \"bug\" na area superior da janela Project Explorer.";
			}
		}else{
			POPUP_TITLE = "Eclipse Debugging";
			if(!Configuration.EXTERNAL_ID_GENERATION){
				POPUP_MESSAGE = "Ok! To generate your ID number, click on \"key\" button at the top of Project Explorer area.";
			}else{
				POPUP_MESSAGE = "Ok! Click on the \"bug\" button at the top of the Project Explorer area.";
			}
		}
	}
	
}