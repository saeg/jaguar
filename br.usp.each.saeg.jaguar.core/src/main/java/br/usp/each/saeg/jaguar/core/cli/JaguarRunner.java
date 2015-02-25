package br.usp.each.saeg.jaguar.core.cli;

import java.io.File;

import org.junit.runner.JUnitCore;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import br.usp.each.saeg.jaguar.core.JaCoCoClient;
import br.usp.each.saeg.jaguar.core.Jaguar;
import br.usp.each.saeg.jaguar.core.heuristic.Heuristic;
import br.usp.each.saeg.jaguar.core.infra.FileUtils;
import br.usp.each.saeg.jaguar.core.runner.JaguarRunListener;

/**
 * @author Henrique Ribeiro
 * 
 */
public class JaguarRunner {

	private final JUnitCore junit = new JUnitCore();

	private final Heuristic heuristic;
	private final File projectDir;
	private final File sourceDir;
	private final File testDir;
	private final Boolean isDataFlow;
	private final String outputFile;


	public JaguarRunner(Heuristic heuristic, File projectDir, File sourceDir,
			File testDir, Boolean isDataFlow, String outputFile) {
		super();
		this.heuristic = heuristic;
		this.projectDir = projectDir;
		this.sourceDir = sourceDir;
		this.testDir = testDir;
		this.isDataFlow = isDataFlow;
		this.outputFile = outputFile;
	}

	private void run() throws Exception {
		final Class<?>[] classes = FileUtils.findTestClasses(testDir);

		final Jaguar jaguar = new Jaguar(heuristic, sourceDir, isDataFlow);
		final JaCoCoClient client = new JaCoCoClient(isDataFlow);
		client.connect();

		junit.addListener(new JaguarRunListener(jaguar, client));
		junit.run(classes);

		client.close();
		jaguar.generateXML(jaguar.generateRank(), projectDir, outputFile);
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
			new JaguarRunner(options.getHeuristic(), options.getProjectPath(), options.getSourcePath(), options.getTestPath(), options.getDataFlow(), options.getOutputFileName()).run();
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("End!");
		System.exit(0);
	}

}
