package br.usp.each.saeg.jaguar.plugin;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.debug.ui.ILaunchConfigurationTabGroup;

public class JaguarConfigTabGroup implements ILaunchConfigurationTabGroup {

	private static final String DELEGATE_LAUNCHMODE = ILaunchManager.RUN_MODE;
	private static final String EXPOINT_TABGROUP = "org.eclipse.debug.ui.launchConfigurationTabGroups"; //$NON-NLS-1$
	// private static final String CONFIGATTR_TYPE = "type"; //$NON-NLS-1$
	
	ILaunchConfigurationTabGroup tabGroupDelegate = null; 
	
	protected ILaunchConfigurationTabGroup createDelegate()
			throws Exception {
		IExtensionPoint extensionpoint = Platform.getExtensionRegistry()
				.getExtensionPoint(EXPOINT_TABGROUP);
		IConfigurationElement[] tabGroupConfigs = extensionpoint
				.getConfigurationElements();
		IConfigurationElement element = null;
		findloop: for (IConfigurationElement tabGroupConfig : tabGroupConfigs) {
				IConfigurationElement[] modeConfigs = tabGroupConfig
						.getChildren("launchMode"); //$NON-NLS-1$
						if (modeConfigs.length == 0) {
							element = tabGroupConfig;
						}
						for (final IConfigurationElement config : modeConfigs) {
							if (DELEGATE_LAUNCHMODE.equals(config.getAttribute("mode"))) { //$NON-NLS-1$
								element = tabGroupConfig;
								break findloop;
							}
						}
		}
		if (element == null) {
			String msg = "No tab group registered to run "; //$NON-NLS-1$
			throw new Exception(msg);
		} else {
			return (ILaunchConfigurationTabGroup) element
					.createExecutableExtension("class"); //$NON-NLS-1$
		}
	}

	@Override
	public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
		try {
			tabGroupDelegate = createDelegate();
			tabGroupDelegate.createTabs(dialog, mode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public ILaunchConfigurationTab[] getTabs() {
		return tabGroupDelegate.getTabs();
	}

	@Override
	public void dispose() {
		tabGroupDelegate.dispose();
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		tabGroupDelegate.setDefaults(configuration);
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		tabGroupDelegate.initializeFrom(configuration);
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		tabGroupDelegate.performApply(configuration);
	}

	@Override
	public void launched(ILaunch launch) {
		// deprecated method will not be called
	}

}
