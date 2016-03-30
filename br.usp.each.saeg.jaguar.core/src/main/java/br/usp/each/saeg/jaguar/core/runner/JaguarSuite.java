package br.usp.each.saeg.jaguar.core.runner;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;

import br.usp.each.saeg.jaguar.core.JaCoCoClient;
import br.usp.each.saeg.jaguar.core.Jaguar;
import br.usp.each.saeg.jaguar.core.heuristic.Heuristic;
import br.usp.each.saeg.jaguar.core.heuristic.TarantulaHeuristic;
import br.usp.each.saeg.jaguar.core.utils.FileUtils;

/**
 * JUnit Runner for fault localization.
 * <p>
 * 
 * This runner will search recursively for JUnits tests in all classes within
 * the current directory. Then will run the tests, collecting coverage
 * information using a jacocoagent running as tcpserver. Finally, will print a
 * list of tests requirements sorted by suspiciousness.
 * <p>
 * 
 * It is mandatory to run using the following VM arguments:
 * -javaagent:{jacoco_agent_path}/jacocoagent.jar=output=tcpserver
 * 
 * @author Henrique Ribeiro
 * 
 */
public class JaguarSuite extends Suite {

	private JaCoCoClient client;
	private Jaguar jaguar;
	private Heuristic heuristic;
	private File classesDir;
	private Boolean isDataflow;

	/**
	 * Constructor.
	 * 
	 * @param clazz
	 *            * the class calling the runner
	 * @throws InitializationError
	 * @throws ClassNotFoundException
	 */
	public JaguarSuite(final Class<?> clazz) throws InitializationError,
			ClassNotFoundException {
		super(clazz, FileUtils.findTestClasses(clazz));
		heuristic = getHeuristic(clazz);
		isDataflow = getDataflow(clazz);
		classesDir = FileUtils.getFile(FileUtils.findClassDir(clazz)
				.getParentFile(), "classes");
	}

	/**
	 * Return the heuristic based on the enum passed on the annotation
	 * {@link JaguarRunnerHeuristic}. If no heuristic is found,
	 * {@link TarantulaHeuristic} is used.
	 * 
	 * @param klass
	 *            class annotated
	 * @return the heuristic
	 * @throws InitializationError
	 */
	private static Heuristic getHeuristic(Class<?> klass) {
		JaguarRunnerHeuristic annotation = klass
				.getAnnotation(JaguarRunnerHeuristic.class);
		try {
			return annotation.value().newInstance();
		} catch (Exception e) {
			return new TarantulaHeuristic();
		}
	}

	private Boolean getDataflow(Class<?> klass) {
		JaguarRunnerHeuristic annotation = klass.getAnnotation(JaguarRunnerHeuristic.class);
		return annotation.isDataflow();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.junit.runners.Suite#run(org.junit.runner.notification.RunNotifier)
	 */
	@Override
	public void run(final RunNotifier notifier) {
		initializeBeforeTests();

		notifier.addListener(new JaguarRunListener(jaguar, client));

		super.run(notifier);

		tearDown();

		jaguar.generateFlatXML(jaguar.generateRank(),
				new File(System.getProperty("user.dir")));
	}

	private void initializeBeforeTests() {
		jaguar = new Jaguar(heuristic, classesDir);
		try {
			client = new JaCoCoClient(isDataflow);
			client.connect();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void tearDown() {
		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
