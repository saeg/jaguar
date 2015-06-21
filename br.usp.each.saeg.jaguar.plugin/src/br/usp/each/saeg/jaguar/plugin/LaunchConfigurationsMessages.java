package br.usp.each.saeg.jaguar.plugin;

import org.eclipse.osgi.util.NLS;

public class LaunchConfigurationsMessages extends NLS {
	private static final String BUNDLE_NAME = "br.usp.each.saeg.jaguar.plugin.LaunchConfigurationsMessages";//$NON-NLS-1$

	public static String JaguarConfigTab_tab1_name;
	public static String JaguarConfigTab_control_flow;
	public static String JaguarConfigTab_data_flow;

	static {
		// load message values from bundle file
		NLS.initializeMessages(BUNDLE_NAME, LaunchConfigurationsMessages.class);
	}

}

