package br.usp.each.saeg.jaguar.plugin.markers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.ArrayUtils;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import br.usp.each.saeg.jaguar.plugin.JaguarPlugin;

/**
 * @author Danilo Mutti (dmutti@gmail.com)
 * @author Higor Amario (higoramario@gmail.com)
 *
 * http://www.eclipse.org/forums/index.php/t/89345/
 */
public class CodeMarkerFactory {
	
	public static final String NO_MARKER = "br.usp.each.saeg.jaguar.plugin.marker.no";
    public static final String LOW_MARKER = "br.usp.each.saeg.jaguar.plugin.marker.low";
    public static final String MID_LOW_MARKER = "br.usp.each.saeg.jaguar.plugin.marker.midlow";
    public static final String MID_HIGH_MARKER = "br.usp.each.saeg.jaguar.plugin.marker.midhigh";
    public static final String HIGH_MARKER = "br.usp.each.saeg.jaguar.plugin.marker.high";
	
    public static void scheduleMarkerCreation(final Map<IResource, List<Map<String, Object>>> resourceMarkerProps) {

        WorkspaceJob job = new WorkspaceJob("addMarkers " + System.nanoTime()) {
            @Override
            public IStatus runInWorkspace(IProgressMonitor arg0) throws CoreException {
                for (Entry<IResource, List<Map<String, Object>>> entry : resourceMarkerProps.entrySet()) {
                    for (Map<String, Object> prop : entry.getValue()) {
                        try {
                            String markerName = getMarkerNameOf((Float) prop.get("score"));
                            IMarker marker = entry.getKey().createMarker(markerName);
                            marker.setAttributes(prop);

                        } catch (Exception e) {
                            JaguarPlugin.log(e);
                        }
                    }
                }
                return Status.OK_STATUS;
            }
        };
        job.setPriority(WorkspaceJob.DECORATE);
        job.schedule();
    }
	
    private static String getMarkerNameOf(float score) {
        int newScore = (int) (score * 100);
        if (newScore < 0) {
            return NO_MARKER;
        } else if (newScore <= 25) {
            return LOW_MARKER;
        } else if (newScore <= 50) {
            return MID_LOW_MARKER;
        } else if (newScore <= 75) {
            return MID_HIGH_MARKER;
        } else {
            return HIGH_MARKER;
        }
    }

    public static List<IMarker> findMarkers(IResource resource) {
        try {
            List<IMarker> result = new ArrayList<IMarker>();
            result.addAll(asList(resource.findMarkers(NO_MARKER, false, IResource.DEPTH_ZERO)));
            result.addAll(asList(resource.findMarkers(LOW_MARKER, false, IResource.DEPTH_ZERO)));
            result.addAll(asList(resource.findMarkers(MID_LOW_MARKER, false, IResource.DEPTH_ZERO)));
            result.addAll(asList(resource.findMarkers(MID_HIGH_MARKER, false, IResource.DEPTH_ZERO)));
            result.addAll(asList(resource.findMarkers(HIGH_MARKER, false, IResource.DEPTH_ZERO)));

            return result;
        } catch (CoreException e) {
            JaguarPlugin.log(e);
            return Collections.emptyList();
        }
    }	
	
    public static IMarker findMarker(IResource resource, float score, int... lines) {
        try {
            List<IMarker> markers = asList(resource.findMarkers(getMarkerNameOf(score), false, IResource.DEPTH_ZERO));
            for (IMarker marker : markers) {
                Map<String, Object> attrs = marker.getAttributes();
                if (!attrs.containsKey("loc")) {
                    continue;
                }
                int[] locs = (int[]) attrs.get("loc");

                for (Integer targetLine : lines) {
                    if (ArrayUtils.contains(locs, targetLine)) {
                        return marker;
                    }
                }
            }

            return null;

        } catch (CoreException e) {
            JaguarPlugin.log(e);
            return null;
        }
    }

    private static List<IMarker> asList(IMarker[] arg) {
        if (arg == null || arg.length == 0) {
            return Collections.emptyList();
        }
        return Arrays.asList(arg);
    }
    
    public static void removeMarkers(final Collection<IMarker> toDelete) {
        if (toDelete == null || toDelete.isEmpty()) {
            return;
        }

        WorkspaceJob job = new WorkspaceJob("deleteMarkers " + System.nanoTime()) {
            @Override
            public IStatus runInWorkspace(IProgressMonitor arg0) throws CoreException {
                for (IMarker marker : toDelete) {
                    try {
                        marker.delete();

                    } catch (Exception e) {
                        JaguarPlugin.log(e);
                    }
                }
                return Status.OK_STATUS;
            }
        };
        job.setPriority(WorkspaceJob.DECORATE);
        job.schedule();
    }
    
}