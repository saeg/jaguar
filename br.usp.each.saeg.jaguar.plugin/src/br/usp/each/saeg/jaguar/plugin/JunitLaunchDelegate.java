package br.usp.each.saeg.jaguar.plugin;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.jdt.junit.launcher.JUnitLaunchConfigurationDelegate;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;

public class JunitLaunchDelegate extends JUnitLaunchConfigurationDelegate {

	JacocoAgentJar jacocoJar = new JacocoAgentJar();
	
	/** Launch mode for the launch delegates used internally. */
	public static final String DELEGATELAUNCHMODE = ILaunchManager.RUN_MODE;

	@Override
	public void launch(ILaunchConfiguration configuration, String mode,
			ILaunch launch, IProgressMonitor monitor) throws CoreException {
		super.launch(configuration, mode, launch, new SubProgressMonitor(monitor, 1));
	}


	@Override
	public ILaunch getLaunch(ILaunchConfiguration configuration, String mode)
			throws CoreException {
		return super.getLaunch(configuration, mode);
	}

	@Override
	public boolean buildForLaunch(ILaunchConfiguration configuration,
			String mode, IProgressMonitor monitor) throws CoreException {
		return super.buildForLaunch(configuration, mode, monitor);
	}

	@Override
	public boolean finalLaunchCheck(ILaunchConfiguration configuration,
			String mode, IProgressMonitor monitor) throws CoreException {
		return super.finalLaunchCheck(configuration, mode, monitor);
	}

	@Override
	public boolean preLaunchCheck(ILaunchConfiguration configuration,
			String mode, IProgressMonitor monitor) throws CoreException {
		String vmArguments = jacocoJar.getQuotedVmArguments("br.usp.each.saeg.opal.*");
		
		ILaunchConfigurationWorkingCopy wc = configuration.getWorkingCopy();
		wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_VM_ARGUMENTS, vmArguments);
		System.out.println("setting by delegate: " + vmArguments);
		configuration = wc.doSave();
		return super.preLaunchCheck(configuration, mode, monitor);
	}

}
