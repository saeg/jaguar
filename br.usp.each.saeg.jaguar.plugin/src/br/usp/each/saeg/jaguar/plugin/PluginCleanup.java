package br.usp.each.saeg.jaguar.plugin;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;

import br.usp.each.saeg.jaguar.plugin.markers.CodeMarkerFactory;
import br.usp.each.saeg.jaguar.plugin.project.ProjectPersistence;
import br.usp.each.saeg.jaguar.plugin.project.ProjectState;

/**
 * @author Danilo Mutti (dmutti@gmail.com)
 * @author Higor Amario (higoramario@gmail.com)
 */
public class PluginCleanup {
	public static void clean(IProject project) {
        PluginCleanup.removeMarkers(project);
        PluginCleanup.removeState(project);
        PluginCleanup.closeAllViews(project);
    }

    private static void closeAllViews(IProject project) {
        IWorkbenchWindow[] windows = JaguarPlugin.getWorkbenchWindow();
        if (ArrayUtils.isEmpty(windows)) {
            return;
        }
        for (IWorkbenchWindow window : windows) {
            if (ArrayUtils.isEmpty(window.getPages())) {
                continue;
            }
            for (IWorkbenchPage page : window.getPages()) {
                IViewReference[] viewReferences = page.getViewReferences();
                for (IViewReference ivr : viewReferences) {
                    if (ivr.getId().startsWith("br.usp.each.saeg.jaguar.plugin.views") && project.getName().equals(ivr.getSecondaryId())) {
                        page.hideView(ivr);
                    }
                }
            }
        }
    }

    private static void removeState(IProject project) {
        if (!project.isOpen()) {
            return;
        }
        ProjectState state = ProjectPersistence.getStateOf(project);
        if (state == null) {
            return;
        }
        state.clear();
    }

    private static void removeMarkers(IProject project) {
        List<IMarker> toDelete = new ArrayList<IMarker>();
        for (List<IResource> files : ProjectUtils.javaFilesOf(project).values()) {
            for (IResource file : files) {
                for (IMarker marker : CodeMarkerFactory.findMarkers(file)) {
                    try {
                        toDelete.add(marker);
                    } catch (Exception e) {
                        JaguarPlugin.log(e);
                    }
                }
            }
        }
        CodeMarkerFactory.removeMarkers(toDelete);
    }
}
