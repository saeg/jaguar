package br.usp.each.saeg.jaguar.plugin;

import org.eclipse.core.resources.IResource;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.jdt.junit.launcher.JUnitLaunchShortcut;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;

public class JaguarJunitShortcut extends JUnitLaunchShortcut {

	@Override
	public void launch(ISelection selection, String mode) {
		super.launch(selection, mode);
		 ILaunchConfiguration[] laungConfigurations = super.getLaunchConfigurations(selection);
		 for (ILaunchConfiguration iLaunchConfiguration : laungConfigurations) {

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
