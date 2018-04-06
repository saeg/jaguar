package br.usp.each.saeg.jaguar.plugin.cli;

import java.io.IOException;

public class Cli {

	public static void main(String[] args) throws IOException, InterruptedException {
		System.out.println("Welcome to Jaguar CLI");
		
//		new JaguarRunner();
		
		String classpath = String.join(System.getProperty("path.separator"), 
				System.getProperty("java.class.path"),
				"/Users/666mario/Documents/develop/each/IMCCalc/bin/test-classes",
				"/Users/666mario/Documents/develop/each/IMCCalc/bin/classes",
				"/Users/666mario/Documents/workspace/.metadata/.plugins/org.eclipse.pde.core/.bundle_pool/plugins/org.junit_4.12.0.v201504281640/junit.jar",
				"/Users/666mario/Documents/workspace/.metadata/.plugins/org.eclipse.pde.core/.bundle_pool/plugins/org.hamcrest.core_1.3.0.v201303031735.jar");
		
		System.out.println("Classpath: " + classpath);
		
		ProcessBuilder builder = new ProcessBuilder("java", 
				"-javaagent:jacocoagent.jar=output=tcpserver,includes=imc.usp.*,dataflow=true", 
				"-Xmx1024m",
				"-classpath",
				 classpath,
				 "br.usp.each.saeg.jaguar.core.cli.JaguarRunner",
				 "--dataflow", 
				 "--outputType", "H", 
				 "--logLevel", "DEBUG", 
				 "--projectDir", "/Users/666mario/Documents/develop/each/IMCCalc", 
				 "--classesDir", "/Users/666mario/Documents/develop/each/IMCCalc/bin/classes"
				);
		
		builder.redirectErrorStream(true);
		builder.inheritIO();
		
		System.out.println("Starting process");
		Process process = builder.start();
		process.waitFor();
	}

}

