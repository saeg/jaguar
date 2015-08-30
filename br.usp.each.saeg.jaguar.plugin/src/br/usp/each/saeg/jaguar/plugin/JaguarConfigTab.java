package br.usp.each.saeg.jaguar.plugin;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.internal.ui.SWTFactory;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.swt.SWT;
import org.eclipse.swt.accessibility.AccessibleAdapter;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

@SuppressWarnings("restriction")
public class JaguarConfigTab extends AbstractLaunchConfigurationTab {

	private Button fControlFlowRadioButton;
	private Button fDataFlowRadioButton;
	private Button fFlatRadioButton;
	private Button fHierarchicalRadioButton;
	private Text fIncludesText;
	private Label fIncludesLabel;
	private Text fLogLevelText;
	private Label fLogLevelLabel;

	private ModifyListener fBasicModifyListener = new ModifyListener() {
		@Override
		public void modifyText(ModifyEvent evt) {
			scheduleUpdateJob();
		}
	};
	
	@Override
	public void createControl(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		setControl(comp);
		comp.setLayout(new GridLayout(2, true));
		comp.setFont(parent.getFont());
		
		createCoverageTypeGroup(comp);
		createOutputTypeGroup(comp);
		createIncludesGroup(comp);
		createLogLevelGroup(comp);
	}

	private void createCoverageTypeGroup(Composite parent) {
		Group group = SWTFactory.createGroup(parent, LaunchConfigurationsMessages.JaguarConfigTab_coverage_type_group_name, 2, 2, GridData.FILL_HORIZONTAL);
		Composite comp = SWTFactory.createComposite(group, parent.getFont(), 2, 3, GridData.FILL_BOTH, 5, 0);
		fControlFlowRadioButton = createRadioButton(comp, LaunchConfigurationsMessages.JaguarConfigTab_control_flow);
		fControlFlowRadioButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateLaunchConfigurationDialog();
			}
		});
		fDataFlowRadioButton = createRadioButton(comp, LaunchConfigurationsMessages.JaguarConfigTab_data_flow);
		fControlFlowRadioButton.setSelection(true);
	}
	
	private void createOutputTypeGroup(Composite parent) {
		Group group = SWTFactory.createGroup(parent, LaunchConfigurationsMessages.JaguarConfigTab_output_type_group_name, 2, 2, GridData.FILL_HORIZONTAL);
		Composite comp = SWTFactory.createComposite(group, parent.getFont(), 2, 3, GridData.FILL_BOTH, 5, 0);
		fFlatRadioButton = createRadioButton(comp, LaunchConfigurationsMessages.JaguarConfigTab_output_flat);
		fFlatRadioButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateLaunchConfigurationDialog();
			}
		});
		fHierarchicalRadioButton = createRadioButton(comp, LaunchConfigurationsMessages.JaguarConfigTab_output_hierarchical);
		fFlatRadioButton.setSelection(true);
	}

	private void createIncludesGroup(Composite parent) {
		Group group = SWTFactory.createGroup(parent, LaunchConfigurationsMessages.JaguarConfigTab_includes_group_name, 2, 2, GridData.FILL_HORIZONTAL);
		Composite comp = SWTFactory.createComposite(group, parent.getFont(), 2, 3, GridData.FILL_BOTH, 0, 0);
		
		fIncludesLabel = SWTFactory.createLabel(comp, LaunchConfigurationsMessages.JaguarConfigTab_includes_text, parent.getFont(), 1);
		GridData gd = new GridData();
		gd.horizontalSpan = 2;
		fIncludesLabel.setLayoutData(gd);
		
		fIncludesText = SWTFactory.createSingleText(comp, 1);
		fIncludesText.getAccessible().addAccessibleListener(new AccessibleAdapter() {
			@Override
			public void getName(AccessibleEvent e) {
				e.result =  LaunchConfigurationsMessages.JaguarConfigTab_includes_group_name;
			}
		});
		fIncludesText.addModifyListener(fBasicModifyListener);
	}
	
	private void createLogLevelGroup(Composite parent) {
		Group group = SWTFactory.createGroup(parent, LaunchConfigurationsMessages.JaguarConfigTab_log_level_group_name, 2, 2, GridData.FILL_HORIZONTAL);
		Composite comp = SWTFactory.createComposite(group, parent.getFont(), 2, 3, GridData.FILL_BOTH, 0, 0);
		
		fLogLevelLabel = SWTFactory.createLabel(comp, LaunchConfigurationsMessages.JaguarConfigTab_log_level_text, parent.getFont(), 1);
		GridData gd = new GridData();
		gd.horizontalSpan = 2;
		fLogLevelLabel.setLayoutData(gd);
		
		fLogLevelText = SWTFactory.createSingleText(comp, 1);
		fLogLevelText.getAccessible().addAccessibleListener(new AccessibleAdapter() {
			@Override
			public void getName(AccessibleEvent e) {
				e.result =  LaunchConfigurationsMessages.JaguarConfigTab_log_level_group_name;
			}
		});
		fLogLevelText.addModifyListener(fBasicModifyListener);
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
		configuration.setAttribute(JaguarConstants.ATTR_COVERAGE_TYPE, true);
		configuration.setAttribute(JaguarConstants.ATTR_INCLUDES,  JaguarConstants.ATTR_INCLUDES_DEFAULT_VALUE);
		configuration.setAttribute(JaguarConstants.ATTR_OUTPUT_TYPE,  JaguarConstants.ATTR_OUTPUT_TYPE_DEFAULT_VALUE);
		configuration.setAttribute(JaguarConstants.ATTR_LOG_LEVEL, JaguarConstants.ATTR_LOG_LEVEL_DEFAULT_VALUE);
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		try {
			
			// Coverage Type
			if (configuration.getAttribute(JaguarConstants.ATTR_COVERAGE_TYPE, true)){
				fControlFlowRadioButton.setSelection(true);
				fDataFlowRadioButton.setSelection(false);
			}else{
				fControlFlowRadioButton.setSelection(false);
				fDataFlowRadioButton.setSelection(true);
			}
			
			// Output Type
			if (configuration.getAttribute(JaguarConstants.ATTR_OUTPUT_TYPE,  JaguarConstants.ATTR_OUTPUT_TYPE_DEFAULT_VALUE).equals( JaguarConstants.ATTR_OUTPUT_TYPE_DEFAULT_VALUE)){
				fFlatRadioButton.setSelection(true);
				fHierarchicalRadioButton.setSelection(false);
			}else{
				fFlatRadioButton.setSelection(false);
				fHierarchicalRadioButton.setSelection(true);
			}
			
			// Includes
			fIncludesText.setText(configuration.getAttribute(JaguarConstants.ATTR_INCLUDES,  JaguarConstants.ATTR_INCLUDES_DEFAULT_VALUE));
			
			// Log Level
			fLogLevelText.setText(configuration.getAttribute(JaguarConstants.ATTR_LOG_LEVEL,  JaguarConstants.ATTR_LOG_LEVEL_DEFAULT_VALUE));
			
		} catch (CoreException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		setAttribute(JaguarConstants.ATTR_COVERAGE_TYPE, configuration, fControlFlowRadioButton.getSelection(), true);
		configuration.setAttribute(JaguarConstants.ATTR_OUTPUT_TYPE, fFlatRadioButton.getSelection() ? JaguarConstants.ATTR_OUTPUT_TYPE_DEFAULT_VALUE : "H");
		configuration.setAttribute(JaguarConstants.ATTR_INCLUDES, fIncludesText.getText());
		configuration.setAttribute(JaguarConstants.ATTR_LOG_LEVEL, fLogLevelText.getText());
	}
	

}
