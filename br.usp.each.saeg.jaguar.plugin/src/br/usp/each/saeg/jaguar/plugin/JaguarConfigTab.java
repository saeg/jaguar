package br.usp.each.saeg.jaguar.plugin;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.internal.ui.SWTFactory;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.debug.ui.IDebugUIConstants;
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
		Group group = SWTFactory.createGroup(parent, LaunchConfigurationsMessages.JaguarConfigTab_tab1_name, 3, 2, GridData.FILL_HORIZONTAL);
		Composite comp = SWTFactory.createComposite(group, parent.getFont(), 3, 3, GridData.FILL_BOTH, 0, 0);
		fControlFlowRadioButton = createRadioButton(comp, LaunchConfigurationsMessages.JaguarConfigTab_control_flow);
		fDataFlowRadioButton = createRadioButton(comp, LaunchConfigurationsMessages.JaguarConfigTab_data_flow);

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
		setAttribute(JaguarConstants.ATTR_COVERAGE_TYPE, configuration, fControlFlowRadioButton.getSelection(), true);		
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		try {
			if (configuration.getAttribute(JaguarConstants.ATTR_COVERAGE_TYPE, true)){
				fControlFlowRadioButton.setSelection(true);
				fDataFlowRadioButton.setSelection(false);
			}else{
				fDataFlowRadioButton.setSelection(true);
				fControlFlowRadioButton.setSelection(false);
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		setAttribute(JaguarConstants.ATTR_COVERAGE_TYPE, configuration, fControlFlowRadioButton.getSelection(), true);		
	}

}
