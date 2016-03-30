package br.usp.each.saeg.jaguar.core.cli;

import java.io.File;

import org.junit.runner.JUnitCore;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.usp.each.saeg.jaguar.core.JaCoCoClient;
import br.usp.each.saeg.jaguar.core.Jaguar;
import br.usp.each.saeg.jaguar.core.heuristic.Heuristic;
import br.usp.each.saeg.jaguar.core.runner.JaguarRunListener;
import br.usp.each.saeg.jaguar.core.utils.FileUtils;

/**
 * @author Henrique Ribeiro
 * 
 */
public class JaguarRunner {

	private final JUnitCore junit = new JUnitCore();
	private static Logger logger = LoggerFactory.getLogger("JaguarLogger");
	
	private final Heuristic heuristic;
	private final File projectDir;
	private final File sourceDir;
	private final File testDir;
	private final Boolean isDataFlow;
	private final String outputFile;
	private final String outputType;
	
	public JaguarRunner(Heuristic heuristic, File projectDir, File sourceDir,
			File testDir, Boolean isDataFlow, String outputFile, String outputType) {
		super();
		this.heuristic = heuristic;
		this.projectDir = projectDir;
		this.sourceDir = sourceDir;
		this.testDir = testDir;
		this.isDataFlow = isDataFlow;
		this.outputFile = outputFile;
		this.outputType = outputType;
	}

	private void run() throws Exception {
		final Class<?>[] classes = FileUtils.findTestClasses(testDir);

		final Jaguar jaguar = new Jaguar(heuristic, sourceDir);
		final JaCoCoClient client = new JaCoCoClient(isDataFlow);
		client.connect();

		junit.addListener(new JaguarRunListener(jaguar, client));
		junit.run(classes);

		client.close();
		if (outputType.equals("H")) {
			jaguar.generateHierarchicalXML(jaguar.generateRank(), projectDir, outputFile);
		} else {
			jaguar.generateFlatXML(jaguar.generateRank(), projectDir, outputFile);
		}
	}

	public static void main(String[] args){
		final JaguarRunnerOptions options = new JaguarRunnerOptions();
		final CmdLineParser parser = new CmdLineParser(options);
		
        try {
            parser.parseArgument(args);
            logger.info(options.toString());
        } catch (final CmdLineException e) {
            System.err.println(e.getLocalizedMessage());
            parser.printUsage(System.err);
            System.exit(1);
        }
		
		try {
			new JaguarRunner(options.getHeuristic(), options.getProjectPath(), options.getSourcePath(), options.getTestPath(),
					         options.getDataFlow(), options.getOutputFileName(), options.getOutputType()).run();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		logger.info("End!");
		System.exit(0);
	}

}
