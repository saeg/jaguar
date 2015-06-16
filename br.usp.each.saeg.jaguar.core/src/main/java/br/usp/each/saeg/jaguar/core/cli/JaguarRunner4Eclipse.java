package br.usp.each.saeg.jaguar.core.cli;

import java.io.File;
import java.util.Arrays;

import org.junit.runner.JUnitCore;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import br.usp.each.saeg.jaguar.core.JaCoCoClient;
import br.usp.each.saeg.jaguar.core.Jaguar;
import br.usp.each.saeg.jaguar.core.heuristic.TarantulaHeuristic;
import br.usp.each.saeg.jaguar.core.infra.FileUtils;
import br.usp.each.saeg.jaguar.core.runner.JaguarRunListener;

/**
 * @author Henrique Ribeiro
 * 
 */
public class JaguarRunner4Eclipse {

	private final JUnitCore junit = new JUnitCore();

	private final File projectDir;
	private final File sourceDir;
	private final File testsListFile;
	private final Boolean isDataFlow;

	public JaguarRunner4Eclipse(File projectDir, File sourceDir,
			File testsListFile, Boolean isDataFlow) {
		super();
		this.projectDir = projectDir;
		this.sourceDir = sourceDir;
		this.testsListFile = testsListFile;
		this.isDataFlow = isDataFlow;
	}

	private void run() throws Exception {
		final Class<?>[] classes = FileUtils.getClassesInFile(testsListFile);
		System.out.println(Arrays.toString(classes));
		
		final Jaguar jaguar = new Jaguar(new TarantulaHeuristic(), sourceDir, isDataFlow); //TODO create results using all Heuristics
		final JaCoCoClient client = new JaCoCoClient(isDataFlow);
		client.connect();

		junit.addListener(new JaguarRunListener(jaguar, client));
		junit.run(classes);

		client.close();
		jaguar.generateXML(jaguar.generateRank(), projectDir, ".jaguar\\coverage"); //TODO standard output
	}

	public static void main(String[] args){
		final JaguarRunnerOptions options = new JaguarRunnerOptions();
		final CmdLineParser parser = new CmdLineParser(options);
		
        try {
            parser.parseArgument(args);
            System.out.println(options.toString());
        } catch (final CmdLineException e) {
            System.err.println(e.getLocalizedMessage());
            parser.printUsage(System.err);
            System.exit(1);
        }
		
		try {
			new JaguarRunner4Eclipse(options.getProjectPath(), options.getSourcePath(), options.getTestListFile(), options.getDataFlow()).run();
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("End!");
		System.exit(0);
	}

}
