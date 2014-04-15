package br.usp.each.saeg.jaguar.runner;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;

import br.usp.each.saeg.jaguar.Jaguar;
import br.usp.each.saeg.jaguar.heuristic.Heuristic;
import br.usp.each.saeg.jaguar.heuristic.impl.DRTHeuristic;
import br.usp.each.saeg.jaguar.heuristic.impl.JaccardHeuristic;
import br.usp.each.saeg.jaguar.heuristic.impl.Kulczynski2Heuristic;
import br.usp.each.saeg.jaguar.heuristic.impl.McConHeuristic;
import br.usp.each.saeg.jaguar.heuristic.impl.MinusHeuristic;
import br.usp.each.saeg.jaguar.heuristic.impl.OchiaiHeuristic;
import br.usp.each.saeg.jaguar.heuristic.impl.OpHeuristic;
import br.usp.each.saeg.jaguar.heuristic.impl.TarantulaHeuristic;
import br.usp.each.saeg.jaguar.heuristic.impl.Wong3Heuristic;
import br.usp.each.saeg.jaguar.heuristic.impl.ZoltarHeuristic;
import br.usp.each.saeg.jaguar.infra.FileUtils;
import br.usp.each.saeg.jaguar.jacoco.JacocoTCPClient;

/**
 * JUnit Runner for fault localization.
 * <p>
 * 
 * This runner will search recursively for JUnits tests in all classes within
 * the current directory. Then will run the tests, collecting coverage
 * information using a jacocoagent running as tcpserver. Finally, will print a
 * list of tests requiremtns order by suspisciousness.
 * <p>
 * 
 * It is mandatory to run using the following JM arguments:
 * -javaagent:{jacoco_agent_path}/jacocoagent.jar=output=tcpserver
 * 
 * @author Henrique Ribeiro
 * 
 */
public class JaguarRunner extends Suite {

	private static Collection<Heuristic> heuristics = new ArrayList<Heuristic>() {
		{
			add(new DRTHeuristic());
			add(new JaccardHeuristic());
			add(new Kulczynski2Heuristic());
			add(new McConHeuristic());
			add(new MinusHeuristic());
			add(new OchiaiHeuristic());
			add(new OpHeuristic());
			add(new TarantulaHeuristic());
			add(new Wong3Heuristic());
			add(new ZoltarHeuristic());
		}
	};

	private static JacocoTCPClient tcpClient;
	private static Jaguar jaguar;
	private static Heuristic heuristic;

	/**
	 * Constructor.
	 * 
	 * @param clazz
	 *            * the class calling the runner
	 * @throws InitializationError
	 * @throws ClassNotFoundException
	 */
	public JaguarRunner(final Class<?> clazz) throws InitializationError, ClassNotFoundException {
		super(clazz, FileUtils.findTestClasses(clazz));
		heuristic = getHeuristic(clazz);
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
	private static Heuristic getHeuristic(Class<?> klass) throws InitializationError {
		JaguarRunnerHeuristic heuristicAnnotation = klass.getAnnotation(JaguarRunnerHeuristic.class);
		if (heuristicAnnotation == null) {
			return new TarantulaHeuristic();
		}
		for (Heuristic heuristic : heuristics) {
			if (heuristicAnnotation.value().equals(heuristic.getEnum())) {
				return heuristic;
			}
		}
		return new TarantulaHeuristic();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.junit.runners.Suite#run(org.junit.runner.notification.RunNotifier)
	 */
	@Override
	public void run(final RunNotifier notifier) {
		initializeBeforeTests();

		notifier.addListener(new RunListener() {
			private boolean currentTestFailed;

			@Override
			public void testStarted(final Description description) {
				currentTestFailed = false;
				jaguar.increaseNTests();
			}

			@Override
			public void testFinished(final Description description) {
				try {
					jaguar.collect(tcpClient.read(), currentTestFailed);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void testFailure(Failure failure) throws Exception {
				currentTestFailed = true;
				jaguar.increaseNTestsFailed();
			}

			@Override
			public void testRunFinished(Result result) {
			}
		});

		super.run(notifier);

		tearDown();
		jaguar.generateRank();
	}

	private static void initializeBeforeTests() {
		jaguar = new Jaguar(heuristic);
		try {
			tcpClient = new JacocoTCPClient();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void tearDown() {
		try {
			tcpClient.closeSocket();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
