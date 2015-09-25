package br.usp.each.saeg.jaguar.plugin;

import java.io.*;
import java.net.URL;
import java.text.*;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.texteditor.ITextEditor;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

import br.usp.each.saeg.jaguar.plugin.editor.EditorTracker;
import br.usp.each.saeg.jaguar.plugin.listeners.BreakpointListener;
import br.usp.each.saeg.jaguar.plugin.listeners.CloseAllViewsListener;
import br.usp.each.saeg.jaguar.plugin.listeners.CloseProjectListener;
import br.usp.each.saeg.jaguar.plugin.listeners.DebugListener;
import br.usp.each.saeg.jaguar.plugin.listeners.JUnitListener;
import br.usp.each.saeg.jaguar.plugin.listeners.LogListener;
import br.usp.each.saeg.jaguar.plugin.project.ProjectPersistence;
import br.usp.each.saeg.jaguar.plugin.project.ProjectState;


/**
 * The activator class controls the plug-in life cycle
 * @author Henrique Lemos
 * @author Higor Amario (higoramario@gmail.com)
 */
public class JaguarPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "br.usp.each.saeg.jaguar.plugin"; //$NON-NLS-1$
	private static final IWorkbenchWindow[] NO_WINDOWS = new IWorkbenchWindow[0];
	// The shared instance
	private static JaguarPlugin instance;
	
	private EditorTracker tracker;
	private LogListener logListener = new LogListener();
	
	/** Identifier for the 'jaguar' launch group. */
    public static final String ID_COVERAGE_LAUNCH_GROUP = PLUGIN_ID + ".launchGroup.jag";

	public JaguarPlugin(){}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		instance = this;
		instance.tracker = new EditorTracker(getWorkbench());
		instance.getLog().addLogListener(logListener);
        new CloseAllViewsListener().register();
        new CloseProjectListener().register();
        new BreakpointListener().register();
        new DebugListener().register();
        new JUnitListener().register();
	}

	/*
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		instance = null;
		super.stop(context);
	}

	/**
	 * @return the shared instance
	 */
	public static JaguarPlugin getDefault() {
		return instance;
	}
	
	public static IWorkbenchWindow getActiveWorkbenchWindow() {
        return PlatformUI.getWorkbench().getActiveWorkbenchWindow();
    }

    public static IWorkbenchWindow[] getWorkbenchWindow() {
        IWorkbenchWindow[] windows = PlatformUI.getWorkbench().getWorkbenchWindows();
        if (ArrayUtils.isEmpty(windows)) {
            return NO_WINDOWS;
        }
        return windows;
    }

	public static JaguarPlugin getInstance() {
		return instance;
	}
    
	public static EditorTracker getEditorTracker() {
    	return instance.tracker;
    }
	
	public static void ui(IProject project, Object caller, Object arg) {
        try {
            ProjectState state = ProjectPersistence.getStateOf(project);
            if (state == null) {
                return;
            }
            /*File folder = new File(state.formatFolderFileName(project.getName()));
            folder.mkdirs();*/
            FileUtils.write(new File(state.formatLogFileName(project.getName())), "[" + new SimpleDateFormat("MM/dd/yyyy HH:mm:ss:SSS").format(System.currentTimeMillis()) + "] ["+ project.getName() + "] [" + caller.getClass().getSimpleName() + "] " + arg.toString() + System.getProperty("line.separator"), true);
        } catch (Exception e) {
            errorStatus("error while writing log file", e);
        }
    }
	
	public static ITextEditor getEditor() {
        return (ITextEditor) getActiveWorkbenchWindow().getActivePage().getActiveEditor();
    }
	
	 public static Shell getShell() {
		 return getActiveWorkbenchWindow().getShell();
	 }
	
	public static IStatus errorStatus(String message, Throwable t) {
	        return new Status(IStatus.ERROR, PLUGIN_ID, IStatus.ERROR, message, t);
	}
	
	public static void log(Throwable t) {
        String message = t.getMessage();
        if (message == null) {
            message = "Internal Error";
        }
        instance.getLog().log(errorStatus(message, t));
    }

    public static void log(String message, Throwable t) {
        instance.getLog().log(errorStatus(message, t));
    }
	
	public static void log(CoreException t) {
        instance.getLog().log(t.getStatus());
    }
	
	public static void info(String message) {
        //instance.getLog().log(new Status(IStatus.INFO, ID, message));
    }

	public static void warn(String message) {
        // instance.getLog().log(new Status(IStatus.WARNING, ID, message));
    }
}