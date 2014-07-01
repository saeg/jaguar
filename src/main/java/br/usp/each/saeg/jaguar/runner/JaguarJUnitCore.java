package br.usp.each.saeg.jaguar.runner;

import static org.junit.runner.Description.createSuiteDescription;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.runner.Version;

import org.junit.internal.RealSystem;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import br.usp.each.saeg.jaguar.Jaguar;
import br.usp.each.saeg.jaguar.heuristic.Heuristic;
import br.usp.each.saeg.jaguar.infra.FileUtils;
import br.usp.each.saeg.jaguar.jacoco.JacocoTCPClient;

public class JaguarJUnitCore {

	// --- JUnit

	private final JUnitCore junit = new JUnitCore();

	private final RealSystem system = new RealSystem();

	// --- Jaguar

	private File rootdir;

	private Heuristic heuristic;

	public static void main(final String... args) throws Exception {
		System.exit(new JaguarJUnitCore().run(args).wasSuccessful() ? 0 : 1);
	}

	private Result run(String... args) throws Exception {

		args = parse(args);

		println("JUnit version %s", Version.id());

		final List<Class<?>> classes = new ArrayList<Class<?>>();
		final List<Failure> failures = new ArrayList<Failure>();

		for (final String each : args) {

			try {
				classes.add(Class.forName(each));
			} catch (final ClassNotFoundException e) {
				println("Could not find class: %s", each);
				failures.add(new Failure(createSuiteDescription(each), e));
			}

		}

		final Jaguar jaguar = new Jaguar(heuristic, rootdir);
		final JacocoTCPClient tcpClient = new JacocoTCPClient();

		junit.addListener(new TextListener(system));
		junit.addListener(new JaguarRunListener(jaguar, tcpClient));

		final Result result = junit.run(classes.toArray(new Class[0]));

		for (final Failure each : failures) {
			result.getFailures().add(each);
		}

		tcpClient.closeSocket();
		jaguar.generateXML(jaguar.generateRank(), FileUtils.findClassDir(this.getClass()));

		return result;
	}

	private String[] parse(final String[] args) throws Exception {
		heuristic = (Heuristic) Class.forName(
				"br.usp.each.saeg.jaguar.heuristic.impl." + args[0])
				.newInstance();
		rootdir = new File(args[1]);
		return Arrays.copyOfRange(args, 2, args.length);
	}

	private void println(final String format, final Object... args) {
		system.out().println(String.format(format, args));
	}

}
