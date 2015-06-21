package br.usp.each.saeg.jaguar.plugin;

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.internal.ui.SWTFactory;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

public class JaguarConfigTab extends AbstractLaunchConfigurationTab {

	private Button fControlFlowRadioButton;
	private Button fDataFlowRadioButton;

	@Override
	public void createControl(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		setControl(comp);
		comp.setLayout(new GridLayout(2, true));
		comp.setFont(parent.getFont());
		
		createCoverageTypeGroup(comp);	
	}

	private void createCoverageTypeGroup(Composite parent) {
		Group group = SWTFactory.createGroup(parent, "Coverage Type", 3, 2, GridData.FILL_HORIZONTAL);
		Composite comp = SWTFactory.createComposite(group, parent.getFont(), 3, 3, GridData.FILL_BOTH, 0, 0);
		fControlFlowRadioButton = createRadioButton(comp, "&Control-flow (line)");
		fDataFlowRadioButton = createRadioButton(comp, "&Data-flow (dua)");

		fControlFlowRadioButton.setSelection(true);
	}
	
	@Override
	public boolean isValid(ILaunchConfiguration launchConfig) {
		return true;
	}

	@Override
	public String getName() {
		return "Jaguar Configuration";
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		// TODO Auto-generated method stub
		
	}

}
