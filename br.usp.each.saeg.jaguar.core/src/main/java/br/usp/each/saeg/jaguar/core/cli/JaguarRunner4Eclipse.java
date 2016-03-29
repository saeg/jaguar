package br.usp.each.saeg.jaguar.core.cli;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.junit.runner.JUnitCore;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.usp.each.saeg.jaguar.core.JaCoCoClient;
import br.usp.each.saeg.jaguar.core.Jaguar;
import br.usp.each.saeg.jaguar.core.heuristic.DRTHeuristic;
import br.usp.each.saeg.jaguar.core.heuristic.Heuristic;
import br.usp.each.saeg.jaguar.core.heuristic.JaccardHeuristic;
import br.usp.each.saeg.jaguar.core.heuristic.Kulczynski2Heuristic;
import br.usp.each.saeg.jaguar.core.heuristic.McConHeuristic;
import br.usp.each.saeg.jaguar.core.heuristic.MinusHeuristic;
import br.usp.each.saeg.jaguar.core.heuristic.OchiaiHeuristic;
import br.usp.each.saeg.jaguar.core.heuristic.OpHeuristic;
import br.usp.each.saeg.jaguar.core.heuristic.TarantulaHeuristic;
import br.usp.each.saeg.jaguar.core.heuristic.Wong3Heuristic;
import br.usp.each.saeg.jaguar.core.heuristic.ZoltarHeuristic;
import br.usp.each.saeg.jaguar.core.infra.FileUtils;
import br.usp.each.saeg.jaguar.core.runner.JaguarRunListener;
import ch.qos.logback.classic.Level;

/**
 * @author Henrique Ribeiro
 * 
 */
public class JaguarRunner4Eclipse {

	private final JUnitCore junit = new JUnitCore();
	private static Logger logger = LoggerFactory.getLogger("JaguarLogger");

	private final File projectDir;
	private final File sourceDir;
	private final File testsListFile;
	private final Boolean isDataFlow;
	private final String outputType;
	
	public Heuristic[] heuristics = new Heuristic[] { 
			new DRTHeuristic(), 
			new JaccardHeuristic(), 
			new Kulczynski2Heuristic(),
			new McConHeuristic(), 
			new MinusHeuristic(), 
			new OchiaiHeuristic(),
			new OpHeuristic(), 
			new TarantulaHeuristic(),
			new Wong3Heuristic(), 
			new ZoltarHeuristic() 
	};

	public JaguarRunner4Eclipse(File projectDir, File sourceDir, File testsListFile, Boolean isDataFlow, String outputType) {
		super();
		this.projectDir = projectDir;
		this.sourceDir = sourceDir;
		this.testsListFile = testsListFile;
		this.isDataFlow = isDataFlow;
		this.outputType = outputType;
	}
	
	public static void main(String[] args) {
		final JaguarRunnerOptions options = new JaguarRunnerOptions();
		final CmdLineParser parser = new CmdLineParser(options);

		try {
			parser.parseArgument(args);
		} catch (final CmdLineException e) {
			System.err.println(e.getLocalizedMessage());
			parser.printUsage(System.err);
			System.exit(1);
		}

		setLogLevel(options);
		logger.info(options.toString());

		try {
			new JaguarRunner4Eclipse(options.getProjectPath(), options.getSourcePath(), options.getTestListFile(), options.getDataFlow(),
					options.getOutputType()).run();
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		logger.info("Jaguar finished!");
		System.exit(0);
	}

	private void run() throws Exception {
		final Class<?>[] classes = FileUtils.getClassesInFile(testsListFile);

		final Jaguar jaguar = new Jaguar(new TarantulaHeuristic(), sourceDir);
		final JaCoCoClient client = new JaCoCoClient(isDataFlow);
		client.connect();

		junit.addListener(new JaguarRunListener(jaguar, client));
		junit.run(classes);

		client.close();
		jaguar.finish();
		for (Heuristic currentHeuristic : heuristics) {
			jaguar.setCurrentHeuristic(currentHeuristic);
			String coverageType = isDataFlow ? "dataflow" : "controlflow";
			String fileName = "coverage_" + coverageType + "_" + currentHeuristic.getClass().getSimpleName();
			logger.debug("OutputType = {}", outputType);
			if (outputType.equals("H")) {
				jaguar.generateHierarchicalXML(jaguar.generateRank(), projectDir, fileName + "_hierarchical_" + System.currentTimeMillis());
			} else {
				jaguar.generateFlatXML(jaguar.generateRank(), projectDir, fileName + "_flat_" + System.currentTimeMillis());
			}
		}
	}

	private static void setLogLevel(final JaguarRunnerOptions options) {
		switch (StringUtils.capitalize(options.getLogLevel())) {
			case "ERROR": {
				logger.info("Setting log level to {}", options.getLogLevel());
				((ch.qos.logback.classic.Logger) LoggerFactory.getLogger("JaguarLogger")).setLevel(Level.ERROR);
				break;
			}
			case "INFO": {
				logger.info("Setting log level to {}", options.getLogLevel());
				((ch.qos.logback.classic.Logger) LoggerFactory.getLogger("JaguarLogger")).setLevel(Level.INFO);
				break;
			}
			case "DEBUG": {
				logger.info("Setting log level to {}", options.getLogLevel());
				((ch.qos.logback.classic.Logger) LoggerFactory.getLogger("JaguarLogger")).setLevel(Level.DEBUG);
				break;
			}
			case "TRACE": {
				logger.info("Setting log level to {}", options.getLogLevel());
				((ch.qos.logback.classic.Logger) LoggerFactory.getLogger("JaguarLogger")).setLevel(Level.TRACE);
				break;
			}
			default: {
				logger.info("UnknowN log level '{}', setting it to INFO", options.getLogLevel());
				((ch.qos.logback.classic.Logger) LoggerFactory.getLogger("JaguarLogger")).setLevel(Level.INFO);
				break;
			}
		}
	}

}