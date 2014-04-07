package br.usp.each.saeg.jaguar.main;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;

import br.usp.each.saeg.jaguar.infra.FileUtils;

public class JaguarRunner extends Suite {

//	private static JacocoTCPClient tcpClient;
//	private static Jaguar jaguar;

	/**
	 * Constructor.
	 * 
	 * @param clazz
	 *            * the class calling the runner
	 * @throws InitializationError
	 */
	public JaguarRunner(final Class<?> clazz) throws InitializationError {
		super(clazz, FileUtils.findClasses(clazz));
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
			@Override
			public void testStarted(final Description description) {
				System.out.println("testStarted");
			}

			@Override
			public void testFinished(final Description description) {
				System.out.println("testFinished");
//				try {
//					jaguar.analyse(tcpClient.read());
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
			}
			
			@Override
			public void testFailure(Failure failure) throws Exception {
				System.out.println("testFailure: " + failure.getDescription().getMethodName());
			}
			@Override
			public void testRunFinished(Result result){
				System.out.println("testRunFinished");
			}
		});

		super.run(notifier);
		
		System.out.println("teardown");
		tearDown();
	}

	private static void initializeBeforeTests() {
		System.out.println("initializeBeforeTests");
//		try {
//			tcpClient = new JacocoTCPClient();
//		} catch (UnknownHostException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	private void tearDown() {
//		try {
//			tcpClient.closeSocket();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

}
