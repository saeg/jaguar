package br.usp.each.saeg.jaguar.plugin.listeners;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;

import br.usp.each.saeg.jaguar.plugin.JaguarPlugin;
import br.usp.each.saeg.jaguar.plugin.PluginCleanup;

public class CloseProjectListener implements IResourceChangeListener {

    public void register() {
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        workspace.addResourceChangeListener(this);
    }

    @Override
    public void resourceChanged(IResourceChangeEvent event) {
        IResource res = event.getResource();
        if (IResourceChangeEvent.PRE_CLOSE == event.getType() || IResourceChangeEvent.PRE_DELETE == event.getType()) {
            IProject project = res.getProject();
            PluginCleanup.clean(project);
            JaguarPlugin.ui(project, this, "closing the project");
        }
    }
}
