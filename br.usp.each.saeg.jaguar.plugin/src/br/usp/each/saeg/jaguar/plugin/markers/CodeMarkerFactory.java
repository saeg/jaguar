package br.usp.each.saeg.jaguar.plugin.markers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.ArrayUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.FindReplaceDocumentAdapter;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Position;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.editors.text.TextFileDocumentProvider;

import br.usp.each.saeg.jaguar.plugin.JaguarPlugin;
import br.usp.each.saeg.jaguar.plugin.data.DuaRequirementData;

/**
 * @author Danilo Mutti (dmutti@gmail.com)
 * @author Higor Amario (higoramario@gmail.com)
 * @author Mario Concilio (marioconcilio@gmail.com)
 *
 * http://www.eclipse.org/forums/index.php/t/89345/
 */
public class CodeMarkerFactory {
	
	public static final String NO_MARKER 		= "br.usp.each.saeg.jaguar.plugin.marker.no";
    public static final String LOW_MARKER 		= "br.usp.each.saeg.jaguar.plugin.marker.low";
    public static final String MID_LOW_MARKER 	= "br.usp.each.saeg.jaguar.plugin.marker.midlow";
    public static final String MID_HIGH_MARKER 	= "br.usp.each.saeg.jaguar.plugin.marker.midhigh";
    public static final String HIGH_MARKER 		= "br.usp.each.saeg.jaguar.plugin.marker.high";
    
    /**
     * Mark only the definition and use lines
     * @param data The DUA to mark
     * @throws CoreException If marker could not be created
     */
    public static void mark(final DuaRequirementData data) throws CoreException {
    	final IResource resource = data.getResource();
    	final Position defPosition = data.getPosition();
    	final Position usePosition = data.getUsePosition();
    	final String markerType = getMarkerType(data.getScore());
    	
    	// definition marker
    	final IMarker defMarker = resource.createMarker(markerType);
    	defMarker.setAttribute("loc", new int[]{ data.getLine() });
    	defMarker.setAttribute(IMarker.CHAR_START, defPosition.getOffset());
    	defMarker.setAttribute(IMarker.CHAR_END, defPosition.getOffset() + defPosition.getLength());
    	
    	// use marker
    	final IMarker useMarker = resource.createMarker(markerType);
    	useMarker.setAttribute("loc", new int[]{ data.getUse() });
    	useMarker.setAttribute(IMarker.CHAR_START, usePosition.getOffset());
    	useMarker.setAttribute(IMarker.CHAR_END, usePosition.getOffset() + usePosition.getLength());
    	
    	/*
    	// IResource to IDocument
    	final IDocumentProvider provider = new TextFileDocumentProvider(); 
    	provider.connect(resource);
    	final IDocument doc = provider.getDocument(resource);
    	
    	// adapter to find text in IDocument
    	final FindReplaceDocumentAdapter finder = new FindReplaceDocumentAdapter(doc);
    	IRegion region;
    	int offset = 0;
    	
    	// search for all occurrences of var and mark each one of them
    	try {
	    	while (true) {
				region = finder.find(offset, data.getVar(), true, true, true, false);
				// if not found, region is null and should break out of loop
	    		if (region == null) break;
	    		
	    		final IMarker marker = resource.createMarker(markerType);
	    		marker.setAttribute(IMarker.CHAR_START, region.getOffset());
	    		marker.setAttribute(IMarker.CHAR_END, region.getOffset() + region.getLength());
	    		
	    		// set offset for search next occurrence
	    		offset = region.getOffset() + region.getLength();
	    	}
    	}
    	catch (BadLocationException e) {
			e.printStackTrace();
		}
		*/
    	
    	// open text editor
    	IDE.openEditor(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage(), defMarker);
    }
	
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
    
    private static String getMarkerType(float score) {
    	String marker = null;
    	if (score <= 0.0) {
    		marker = NO_MARKER;
    	}
    	else if (score <= 0.25) {
    		marker = LOW_MARKER;
    	}
    	else if (score <= 0.50) {
    		marker = MID_LOW_MARKER;
    	}
    	else if (score <= 0.75) {
    		marker = MID_HIGH_MARKER;
    	}
    	else {
    		marker = HIGH_MARKER;
    	}
    	
    	return marker;
    }
	
    private static String getMarkerNameOf(float score) {
    	String marker = null;
    	if (score <= 0.0) {
    		marker = NO_MARKER;
    	}
    	else if (score <= 0.25) {
    		marker = LOW_MARKER;
    	}
    	else if (score <= 0.50) {
    		marker = MID_LOW_MARKER;
    	}
    	else if (score <= 0.75) {
    		marker = MID_HIGH_MARKER;
    	}
    	else {
    		marker = HIGH_MARKER;
    	}
    	
    	return marker;
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
        } 
        catch (CoreException e) {
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
        } 
        catch (CoreException e) {
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

        for (IMarker marker : toDelete) {
            try {
                marker.delete();
            } 
            catch (Exception e) {
                JaguarPlugin.log(e);
            }
        }
    }
    
}