package br.usp.each.saeg.jaguar.plugin;

import org.eclipse.osgi.util.NLS;

public class LaunchConfigurationsMessages extends NLS {
	private static final String BUNDLE_NAME = "br.usp.each.saeg.jaguar.plugin.LaunchConfigurationsMessages";//$NON-NLS-1$


	// Coverage Type
	public static String JaguarConfigTab_coverage_type_group_name;
	public static String JaguarConfigTab_control_flow;
	public static String JaguarConfigTab_data_flow;

	// Output Type
	public static String JaguarConfigTab_output_type_group_name;
	public static String JaguarConfigTab_output_flat;
	public static String JaguarConfigTab_output_hierarchical;
	
	// Includes
	public static String JaguarConfigTab_includes_group_name;
	public static String JaguarConfigTab_includes_text;
	
	// LogLevel
	public static String JaguarConfigTab_log_level_group_name;
	public static String JaguarConfigTab_log_level_text;
	
	// CompiledClassesPath
	public static String JaguarConfigTab_compiled_classes_path_group_name;
	public static String JaguarConfigTab_compiled_classes_path_text;

	static {
		// load message values from bundle file
		NLS.initializeMessages(BUNDLE_NAME, LaunchConfigurationsMessages.class);
	}

}

