package br.usp.each.saeg.jaguar.plugin;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate2;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;


public class JunitLaunchDelegate implements ILaunchConfigurationDelegate2 {

	JacocoAgentJar jacocoJar = new JacocoAgentJar();
	
	@Override
	public void launch(ILaunchConfiguration configuration, String mode,
			ILaunch launch, IProgressMonitor monitor) throws CoreException {
		launch.setAttribute(IJavaLaunchConfigurationConstants.ATTR_VM_ARGUMENTS, jacocoJar.getQuotedVmArguments("*")); 
		//TODO includes
		
	}


	@Override
	public ILaunch getLaunch(ILaunchConfiguration configuration, String mode)
			throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean buildForLaunch(ILaunchConfiguration configuration,
			String mode, IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean finalLaunchCheck(ILaunchConfiguration configuration,
			String mode, IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean preLaunchCheck(ILaunchConfiguration configuration,
			String mode, IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		return false;
	}

}
