package br.usp.each.saeg.jaguar.plugin.listeners;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointListener;
import org.eclipse.debug.core.model.IBreakpoint;

import br.usp.each.saeg.jaguar.plugin.JaguarPlugin;

public class BreakpointListener implements IBreakpointListener {

    public void register() {
        DebugPlugin.getDefault().getBreakpointManager().addBreakpointListener(this);
    }

    @Override
    public void breakpointRemoved(IBreakpoint breakpoint, IMarkerDelta delta) {
        breakpointEvent(breakpoint, "removed");
    }

    @Override
    public void breakpointChanged(IBreakpoint breakpoint, IMarkerDelta delta) {
    }

    @Override
    public void breakpointAdded(IBreakpoint breakpoint) {
        breakpointEvent(breakpoint, "added");
    }

    private void breakpointEvent(IBreakpoint breakpoint, final String type) {
        try {
            IMarker marker = breakpoint.getMarker();
            JaguarPlugin.ui(marker.getResource().getProject(), this, "breakpoint [" + type + "] @ " + marker.getAttribute("org.eclipse.jdt.debug.core.typeName") + ":" + marker.getAttribute("lineNumber"));
        } catch (Exception e) {
        	JaguarPlugin.errorStatus("error inspecting marker", e);
        }

    }
}
