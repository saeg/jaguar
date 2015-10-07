package br.usp.each.saeg.jaguar.plugin.editor;

import org.eclipse.core.resources.IMarker;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.*;

import br.usp.each.saeg.jaguar.plugin.JaguarPlugin;

public class OpenEditor {
	
	public static void at(final IMarker marker) {
        if (marker == null) {
            return;
        }
        try {

            PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
                public void run() {
                    IWorkbenchWindow dwindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
                    IWorkbenchPage page = dwindow.getActivePage();
                    if (page != null) {
                        try {
                        	IDE.openEditor(page, marker);
                        } catch (Exception e) {
                            JaguarPlugin.log(e);
                        }
                    }
                }
            });
        } catch (Exception t) {
            JaguarPlugin.log(t);
        }
    }
	
}
