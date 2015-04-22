package br.usp.each.saeg.jaguar.plugin;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.jdt.junit.launcher.JUnitLaunchShortcut;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;

public class JaguarJunitShortcut extends JUnitLaunchShortcut {

	JacocoAgentJar jacocoJar = new JacocoAgentJar();
	
	@Override
	public void launch(ISelection selection, String mode) {
		try {
			ILaunchConfigurationWorkingCopy workingCopy = getLaunchConfigurations(selection)[0].getWorkingCopy();
			workingCopy.setAttribute(IJavaLaunchConfigurationConstants.ATTR_VM_ARGUMENTS, jacocoJar.getQuotedVmArguments("*"));
			System.out.println("setting by shortcut: " + jacocoJar.getQuotedVmArguments("*"));
			ILaunchConfiguration configuration = workingCopy.doSave();
			configuration.launch(ILaunchManager.RUN_MODE, null);
		} catch (CoreException e) {
			e.printStackTrace();
		}
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
