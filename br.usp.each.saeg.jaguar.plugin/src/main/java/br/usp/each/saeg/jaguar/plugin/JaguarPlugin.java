package br.usp.each.saeg.jaguar.plugin;

import org.apache.commons.lang.ArrayUtils;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class JaguarPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "br.usp.each.saeg.jaguar.plugin"; //$NON-NLS-1$
	private static final IWorkbenchWindow[] NO_WINDOWS = new IWorkbenchWindow[0];
	// The shared instance
	private static JaguarPlugin instance;
	
	/**
	 * The constructor
	 */
	public JaguarPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		instance = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		instance = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
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
    
}
