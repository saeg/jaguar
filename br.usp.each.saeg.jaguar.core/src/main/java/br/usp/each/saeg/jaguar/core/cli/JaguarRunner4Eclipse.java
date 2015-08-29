package br.usp.each.saeg.jaguar.core.cli;

import java.io.File;

import org.junit.runner.JUnitCore;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
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

	public Heuristic[] heuristics = new Heuristic[]{
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
		
		final Jaguar jaguar = new Jaguar(new TarantulaHeuristic(), sourceDir);
		final JaCoCoClient client = new JaCoCoClient(isDataFlow);
		client.connect();

		junit.addListener(new JaguarRunListener(jaguar, client));
		junit.run(classes);

		client.close();
		jaguar.finish();
		for (Heuristic currentHeuristic : heuristics) {
			jaguar.setCurrentHeuristic(currentHeuristic);
			String coverageType = isDataFlow ? "dataflow" : "controflow";
			String fileName = "coverage_" + coverageType + "_"  + currentHeuristic.getClass().getSimpleName();
			jaguar.generateXML(jaguar.generateRank(), projectDir, fileName);
		}
	}

	public static void main(String[] args){
		final JaguarRunnerOptions options = new JaguarRunnerOptions();
		final CmdLineParser parser = new CmdLineParser(options);
		
		((ch.qos.logback.classic.Logger) LoggerFactory.getLogger("JaguarLogger")).setLevel(Level.INFO);

		try {
            parser.parseArgument(args);
            logger.info(options.toString());
        } catch (final CmdLineException e) {
            System.err.println(e.getLocalizedMessage());
            parser.printUsage(System.err);
            System.exit(1);
        }
		
		try {
			new JaguarRunner4Eclipse(options.getProjectPath(), options.getSourcePath(), options.getTestListFile(), options.getDataFlow()).run();
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		logger.info("Jaguar finished!");
		System.exit(0);
	}

}