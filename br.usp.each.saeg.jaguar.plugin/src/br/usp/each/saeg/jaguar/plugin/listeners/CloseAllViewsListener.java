package br.usp.each.saeg.jaguar.plugin.listeners;

import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import br.usp.each.saeg.jaguar.plugin.JaguarPlugin;

public class CloseAllViewsListener  implements IWorkbenchListener {

    public void register() {
        PlatformUI.getWorkbench().addWorkbenchListener(this);
    }

    @Override
    public boolean preShutdown(IWorkbench workbench, boolean forced) {
        closeAllViews();
        return true;
    }

    @Override
    public void postShutdown(IWorkbench workbench) {
    }

    private static void closeAllViews() {
        IWorkbenchPage page = JaguarPlugin.getActiveWorkbenchWindow().getActivePage();
        if (page != null) {
            IViewReference[] viewReferences = page.getViewReferences();
            for (IViewReference ivr : viewReferences) {
                if (ivr.getId().startsWith("br.usp.each.saeg.jaguar.plugin")) {
                    page.hideView(ivr);
                }
            }
        }
    }
}
