package br.usp.each.saeg.jaguar.plugin.actions;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.part.ViewPart;

import br.usp.each.saeg.jaguar.plugin.Configuration;
import br.usp.each.saeg.jaguar.plugin.JaguarPlugin;
import br.usp.each.saeg.jaguar.plugin.ProjectUtils;
import br.usp.each.saeg.jaguar.plugin.utils.IDValidator;
import br.usp.each.saeg.jaguar.plugin.views.JaguarView;
import br.usp.each.saeg.jaguar.plugin.views.LineDuaView;
import br.usp.each.saeg.jaguar.plugin.views.MethodView;
import br.usp.each.saeg.jaguar.plugin.views.RoadmapView;

public class StartJaguarAction extends Action implements IWorkbenchAction {
	
	private static final String ID = "br.usp.each.saeg.jaguar.plugin.actions.StartJaguarAction";
	private IProject project;
	Action stopAction;
	ViewPart view;
	private String POPUP_ID_MESSAGE = "";
	
	public StartJaguarAction(IProject project, Action stop, ViewPart view) {
		if(Configuration.EXTERNAL_ID_GENERATION){
			this.setEnabled(true);
		}else{
			if(Configuration.EXPERIMENT_JAGUAR_FIRST){
				this.setEnabled(false);
			}else{
				this.setEnabled(true);
			}
		}
		this.project = project;
		this.stopAction = stop;
		this.view = view;
		setPopupMessage();
	}

	public void run(){
		if(Configuration.EXTERNAL_ID_GENERATION){
			if(Configuration.EXPERIMENT_JAGUAR_FIRST){
				InputDialog inputDialog = new InputDialog(view.getSite().getShell(),"ID",POPUP_ID_MESSAGE,"", new IDValidator());
				inputDialog.open();
				System.out.println("ID number is "+inputDialog.getValue());
				JaguarPlugin.ui(project, this, "ID number is "+inputDialog.getValue());
			}
		}
		
		System.out.println("jaguar debugging session started");
		JaguarPlugin.ui(project, this, "jaguar debugging session started");
		if(view instanceof JaguarView){
			((JaguarView)view).loadView();
		}
		if(view instanceof RoadmapView){
			((RoadmapView)view).loadView();
		}
		if(view instanceof MethodView){
			((MethodView)view).loadView();
		}
		if(view instanceof LineDuaView){
			((LineDuaView)view).loadView();
		}
		this.setEnabled(false);
		this.stopAction.setEnabled(true);
	}
	
	@Override
	public void dispose() {
	}
	
	private void setPopupMessage(){
		if(!Configuration.LANGUAGE_EN){
			POPUP_ID_MESSAGE = "Digite o seu ID de 8 digitos";
		}else{
			POPUP_ID_MESSAGE = "Enter your 8-digit ID number";
		}
	}
	
}
