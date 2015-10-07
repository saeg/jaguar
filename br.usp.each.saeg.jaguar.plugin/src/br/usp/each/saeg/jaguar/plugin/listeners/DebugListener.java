package br.usp.each.saeg.jaguar.plugin.listeners;

import org.apache.commons.lang3.ArrayUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IDebugEventSetListener;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.launching.JavaRuntime;

import br.usp.each.saeg.jaguar.plugin.JaguarPlugin;

public class DebugListener implements IDebugEventSetListener {

    public void register() {
        DebugPlugin.getDefault().addDebugEventListener(this);
    }

    @Override
    public void handleDebugEvents(DebugEvent[] events) {
        if (ArrayUtils.isEmpty(events)) {
            return;
        }

        for (DebugEvent event : events) {
            if (event == null) {
                continue;
            }
            if (event.getKind() != DebugEvent.CREATE && event.getKind() != DebugEvent.TERMINATE) {
                continue;
            }
            
            String message = event.getKind() == DebugEvent.CREATE ? "started" : "finished";

            if (!(event.getSource() instanceof IProcess)) {
                continue;
            }
            IProject project = from((IProcess) event.getSource());
            if (project == null || !project.isOpen()) {
                continue;
            }
            JaguarPlugin.ui(project, this, "debugging "+message);
        }
    }

    private IProject from(IProcess process) {
        ILaunch launch = process.getLaunch();
        ILaunchConfiguration config = launch.getLaunchConfiguration();

        try {
            IJavaProject javaProject = JavaRuntime.getJavaProject(config);
            if (javaProject != null) {
                return javaProject.getProject();
            }

        } catch (Throwable e) {
            JaguarPlugin.errorStatus("error obtaining the project from an IProcess", e);
        }
        return null;
    }

}
