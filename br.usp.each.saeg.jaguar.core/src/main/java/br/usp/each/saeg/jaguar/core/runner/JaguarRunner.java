package br.usp.each.saeg.jaguar.core.runner;

import java.io.File;

import org.junit.runner.JUnitCore;
import org.junit.runners.model.InitializationError;

import br.usp.each.saeg.jaguar.core.JaCoCoClient;
import br.usp.each.saeg.jaguar.core.Jaguar;
import br.usp.each.saeg.jaguar.core.heuristic.Heuristic;
import br.usp.each.saeg.jaguar.core.infra.FileUtils;

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

	public JaguarRunner(Heuristic heuristic, File projectDir, File sourceDir, File testDir) throws InitializationError, ClassNotFoundException {
		this.heuristic = heuristic;
		this.projectDir = projectDir;
		this.sourceDir = sourceDir;
		this.testDir = testDir;
	}

	private void run() throws Exception {
		final Class<?>[] classes = FileUtils.findTestClasses(testDir);

		final Jaguar jaguar = new Jaguar(heuristic, sourceDir);
		final JaCoCoClient client = new JaCoCoClient();
		client.connect();

		junit.addListener(new JaguarRunListener(jaguar, client));
		junit.run(classes);

		client.close();
		jaguar.generateXML(jaguar.generateRank(), projectDir);
	}

	public static void main(String[] args){
		Heuristic heuristic = null;
		try {
			heuristic = (Heuristic) Class.forName(
					"br.usp.each.saeg.jaguar.core.heuristic.impl." + args[0] + "Heuristic").newInstance();
		} catch (Exception e) {
			System.out.println(e);
		}
		File projectPath = new File(args[1]);
		File sourcePath = new File(args[2]);
		File testPath = new File(args[3]);
		try {
			new JaguarRunner(heuristic, projectPath, sourcePath, testPath).run();
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("End!");
		System.exit(0);
	}

}
