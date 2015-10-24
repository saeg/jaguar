package br.usp.each.saeg.jaguar.plugin.actions;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;

import br.usp.each.saeg.jaguar.plugin.Configuration;
import br.usp.each.saeg.jaguar.plugin.JaguarPlugin;
import br.usp.each.saeg.jaguar.plugin.project.ProjectPersistence;
import br.usp.each.saeg.jaguar.plugin.project.ProjectState;

public class IdAction extends Action implements IWorkbenchAction {

	private static final String ID = "br.usp.each.saeg.jaguar.plugin.actions.IdJaguarAction";
	private IProject project;
	private Action startAction;
	private UUID uuid;
	private String POPUP_TITLE = "Jaguar - ID generation";
	private String POPUP_MESSAGE = "\nThis ID number was saved in the file ID in the desktop area. \nIt will be used to fill out the response form.";
	private String FILENAME = System.getProperty("user.home") + System.getProperty("file.separator")+"Desktop"+System.getProperty("file.separator")+"id";
		
	public IdAction(IProject project, Action start) {
		uuid = UUID.randomUUID();
		this.project = project;
		this.startAction = start;
		if(!Configuration.EXPERIMENT_JAGUAR_FIRST){
			POPUP_TITLE = "Eclipse - ID generation";
		}
	}

	public void run(){
		System.out.println("id generation: " + uuid);
		JaguarPlugin.ui(project, this, "id generation: " + uuid);
		openDialogPopup("Your ID is: " + uuid + POPUP_MESSAGE);
		saveIdFile(uuid);
		this.setEnabled(false);
		this.startAction.setEnabled(true);
	}
	
	private void saveIdFile(UUID uuid) {
		try {
            FileUtils.write(new File(FILENAME), uuid.toString(), true);
        } catch (Exception e) {
            errorStatus("error while writing log file", e);
        }
	}

	private void openDialogPopup(String idMessage) {
		MessageDialog.openInformation(new Shell(),POPUP_TITLE,idMessage);
	}

	@Override
	public void dispose() {
	}
	
	public IStatus errorStatus(String message, Throwable t) {
        return new Status(IStatus.ERROR, ID, IStatus.ERROR, message, t);
	}
	
}
