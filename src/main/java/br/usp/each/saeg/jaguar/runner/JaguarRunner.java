package br.usp.each.saeg.jaguar.runner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.internal.RealSystem;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runners.model.InitializationError;

import br.usp.each.saeg.jaguar.Jaguar;
import br.usp.each.saeg.jaguar.heuristic.Heuristic;
import br.usp.each.saeg.jaguar.infra.FileUtils;
import br.usp.each.saeg.jaguar.jacoco.JacocoTCPClient;

/**
 * @author Henrique Ribeiro
 * 
 */
public class JaguarRunner {

	private final JUnitCore junit = new JUnitCore();
	private final RealSystem system = new RealSystem();

	private final Heuristic heuristic;
	private final File projectDir;
	private final File sourceDir;
	private final File testDir;

	public JaguarRunner(File projectDir, Heuristic heuristic) throws InitializationError, ClassNotFoundException {
		this.heuristic = heuristic;
		this.projectDir = projectDir;
		this.testDir = FileUtils.getFile(projectDir, "test-classes");
		this.sourceDir = FileUtils.getFile(projectDir, "classes");
	}

	private Result run() throws Exception {

		final Class<?>[] classes = FileUtils.findTestClasses(testDir);
		final List<Failure> failures = new ArrayList<Failure>();

		final Jaguar jaguar = new Jaguar(heuristic, sourceDir);
		final JacocoTCPClient tcpClient = new JacocoTCPClient();

		junit.addListener(new TextListener(system));
		junit.addListener(new JaguarRunListener(jaguar, tcpClient));

		final Result result = junit.run(classes);

		for (final Failure each : failures) {
			result.getFailures().add(each);
		}

		tcpClient.closeSocket();
		jaguar.generateXML(jaguar.generateRank(), projectDir);

		return result;
	}

	public static void main(String[] args) throws InitializationError, Exception {
		Heuristic heuristic = (Heuristic) Class.forName(
				"br.usp.each.saeg.jaguar.heuristic.impl." + args[0] + "Heuristic").newInstance();
		File projectPath = new File(args[1]);
		new JaguarRunner(projectPath, heuristic).run();
	}

}
