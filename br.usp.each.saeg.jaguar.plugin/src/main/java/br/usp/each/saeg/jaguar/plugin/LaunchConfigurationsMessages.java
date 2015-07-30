package br.usp.each.saeg.jaguar.plugin;

import org.eclipse.osgi.util.NLS;

public class LaunchConfigurationsMessages extends NLS {
	private static final String BUNDLE_NAME = "br.usp.each.saeg.jaguar.plugin.LaunchConfigurationsMessages";//$NON-NLS-1$


	public static String JaguarConfigTab_coverage_type_group_name;
	public static String JaguarConfigTab_control_flow;
	public static String JaguarConfigTab_data_flow;
	public static String JaguarConfigTab_includes_group_name;
	public static String JaguarConfigTab_includes_text;

	static {
		// load message values from bundle file
		NLS.initializeMessages(BUNDLE_NAME, LaunchConfigurationsMessages.class);
	}

}

