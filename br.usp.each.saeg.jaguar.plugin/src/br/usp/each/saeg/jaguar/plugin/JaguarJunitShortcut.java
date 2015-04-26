package br.usp.each.saeg.jaguar.plugin;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.junit.launcher.JUnitLaunchShortcut;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;

public class JaguarJunitShortcut extends JUnitLaunchShortcut {

	JacocoAgentJar jacocoJar = new JacocoAgentJar();
	
	@Override
	public void launch(ISelection selection, String mode) {
		super.launch(selection, mode);
	}

	@Override
	protected ILaunchConfigurationWorkingCopy createLaunchConfiguration(IJavaElement element) throws CoreException {
		String vmArguments = jacocoJar.getVmArguments("*");
		
		ILaunchConfigurationWorkingCopy wc = super.createLaunchConfiguration(element);
		wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_VM_ARGUMENTS, vmArguments);
		
		System.out.println("setting by shortcut: " + vmArguments);
		return wc;
	}
	
	@Override
	public void launch(IEditorPart editor, String mode) {
		super.launch(editor, mode);
	}

	@Override
	public ILaunchConfiguration[] getLaunchConfigurations(ISelection selection) {
		return super.getLaunchConfigurations(selection);
	}

	@Override
	public ILaunchConfiguration[] getLaunchConfigurations(IEditorPart editorpart) {
		return super.getLaunchConfigurations(editorpart);
	}

	@Override
	public IResource getLaunchableResource(ISelection selection) {
		return super.getLaunchableResource(selection);
	}

	@Override
	public IResource getLaunchableResource(IEditorPart editorpart) {
		return super.getLaunchableResource(editorpart);
	}

}
