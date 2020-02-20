package br.usp.each.saeg.jaguar.core.cli;

import java.io.File;
import java.util.Arrays;
import java.util.stream.Stream;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

import org.junit.runner.JUnitCore;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.slf4j.LoggerFactory;

import br.usp.each.saeg.jaguar.core.JaguarClient;
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
	private static Logger logger = (Logger) LoggerFactory.getLogger("JaguarLogger");
	
	private final Heuristic heuristic;
	private final File projectDir;
	private final File sourceDir;
	private final File testDir;
	private final Boolean isDataFlow;
	private final String outputFile;
	private final String outputType;
	private final String testSuite;
	
	public JaguarRunner(Heuristic heuristic, File projectDir, File sourceDir,
			File testDir, String testSuite, Boolean isDataFlow, String outputFile, String outputType) {
		super();
		this.heuristic = heuristic;
		this.projectDir = projectDir;
		this.sourceDir = sourceDir;
		this.testDir = testDir;
		this.isDataFlow = isDataFlow;
		this.outputFile = outputFile;
		this.outputType = outputType;
		this.testSuite = testSuite;
	}

	private void run() throws Exception {
		Class<?> suiteClass = null;
		if (testSuite != null && !testSuite.isEmpty()) {
			try {
				suiteClass = Class.forName(testSuite);
			}
			catch (ClassNotFoundException e) {
				logger.warn("Test suite class {} not found. Running all tests in test folder", testSuite);
			}
		}

		Class<?>[] classes;
		if (suiteClass == null) {
			final Class<?>[] testClasses = FileUtils.findTestCaseClasses(testDir);
			logger.debug("Total TestCase classes = {}", testClasses.length);

			final Class<?>[] annotatedClasses = FileUtils.findAnnotatedTestClasses(testDir);
			logger.debug("Total annotated test classes = {}", annotatedClasses.length);

			classes = Stream.of(testClasses, annotatedClasses)
					.flatMap(Stream::of)
					.toArray(Class<?>[]::new);
		}
		else {
			logger.debug("Running test suite = {}", suiteClass);
			classes = new Class<?>[] { suiteClass };
		}

		final Jaguar jaguar = new Jaguar(sourceDir);
		final JaguarClient client = new JaguarClient(isDataFlow);

		junit.addListener(new JaguarRunListener(jaguar, client));
		junit.run(classes);

		if (outputType.equals("H")) {
			jaguar.generateHierarchicalXML(heuristic, projectDir, outputFile);
		} else {
			jaguar.generateFlatXML(heuristic, projectDir, outputFile);
		}
	}

	public static void main(String[] args) {
		System.out.println("Welcome to Jaguar CLI!");

		final JaguarRunnerOptions options = new JaguarRunnerOptions();
		final CmdLineParser parser = new CmdLineParser(options);
		
        try {
        	logger.info("Command: " + Arrays.toString(args));
            parser.parseArgument(args);
        } catch (final CmdLineException e) {
            System.err.println(e.getLocalizedMessage());
            parser.printUsage(System.err);
            System.exit(1);
        }

        if (options.isHelp()){
			parser.printUsage(System.err);
			System.exit(0);	
        }

		setLogLevel(options.getLogLevel());
        
		try {
			logger.info(options.toString());
			new JaguarRunner(
					options.getHeuristic(),
					options.getProjectPath(),
					options.getSourcePath(),
					options.getTestPath(),
					options.getTestSuite(),
					options.isDataFlow(),
					options.getOutputFileName(),
					options.getOutputType()
			).run();
		} catch (Exception e) {
			logger.error("Exception :" + e.toString());
			logger.error("Exception Message : " + e.getMessage());
			logger.error("Stacktrace :");
			e.printStackTrace(System.err);
			System.exit(1);
		}
		
		logger.info("Jaguar has finished!");
		System.exit(0);
	}

	private static void setLogLevel(String logLevel) {
		Level level;

		switch (logLevel.toUpperCase()) {
			case "ALL":
				level = Level.ALL;
				break;

			case "TRACE":
				level = Level.TRACE;
				break;

			case "DEBUG":
				level = Level.DEBUG;
				break;

			case "INFO":
				level = Level.INFO;
				break;

			case "WARN":
				level = Level.WARN;
				break;

			case "ERROR":
				level = Level.ERROR;
				break;

			default:
				level = Level.OFF;
		}

		logger.setLevel(level);
	}

}
