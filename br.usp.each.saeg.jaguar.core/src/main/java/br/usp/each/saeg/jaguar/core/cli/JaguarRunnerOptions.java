package br.usp.each.saeg.jaguar.core.cli;

import java.io.File;

import org.kohsuke.args4j.Option;

import br.usp.each.saeg.jaguar.core.heuristic.Heuristic;
import br.usp.each.saeg.jaguar.core.heuristic.TarantulaHeuristic;

/**
 * Class to represent the Command Line Interface (CLI) options
 * and respective descriptions.
 * 
 * @author Henrique Ribeiro
 *
 */
public class JaguarRunnerOptions {

    private Heuristic heuristic = new TarantulaHeuristic();
   
    @Option(name = "-dataflow ", aliases = {"-df"}, 
    		usage = "colect data-flow information"
    				+ "default is false")
	private Boolean dataFlow = false;
    
    @Option(name = "-output ", aliases = {"-o"}, 
    		usage = "the output file name"
    				+ "default is codeforest")
	private String outputFileName = "codeforest";
    
    @Option(name = "-projectDir ", aliases = {"-p"}, required = true,
    		usage = "the path where the project path")
    private File projectPath;
    
    @Option(name = "-classesDir ", aliases = {"-c"},
            usage = "the path where the compiled classes are located\n"
            		+ "default is .\\target\\classes\\")
	private File sourcePath = new File(".\\target\\classes\\");
	
    
    @Option(name = "-testsDir ", aliases = {"-t"}, required = true,
            usage = "the path where the compiled tests are located\n"
            		+ "default is .\\target\\test-classes\\")
	private File testPath = new File(".\\target\\test-classes\\");;
	
	
    @Option(name = "-heuristic", aliases = {"-h"},
    		usage = "heuristic name\n"
    				+ "must be one of the heuristic in the package br.usp.each.saeg.jaguar.core.heuristic\n"
    				+ "must be the exact name of the class without the word Heuristic\n"
		    		+ "default is Tarantura")
    public void setHeuristic(String heuristic){
		try {
			this.heuristic = (Heuristic) Class.forName(
					"br.usp.each.saeg.jaguar.core.heuristic." + heuristic + "Heuristic").newInstance();
		} catch (Exception e) {
			System.out.println(e);
		}
    }
    
    public Heuristic getHeuristic() {
		return heuristic;
	}

	public File getProjectPath() {
		return projectPath;
	}

	public File getSourcePath() {
		return sourcePath;
	}

	public File getTestPath() {
		return testPath;
	}
	
	public String getOutputFileName() {
		return outputFileName;
	}
	
	public Boolean getDataFlow() {
		return dataFlow;
	}

	@Override
	public String toString() {
		return "JaguarRunnerOptions \n"
				+ "heuristic = " + heuristic.getClass().getName() + "\n"
				+ "projectPath = " + projectPath.getPath() + "\n"
				+ "sourcePath = " + sourcePath.getPath() + "\n"
				+ "testPath = " + testPath.getPath() + "\n"
				+ "output = " + outputFileName + "\n"
				+ "dataflow = " + dataFlow ;
	}
	
}
