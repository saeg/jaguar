package br.usp.each.saeg.jaguar.core.runner;

import java.io.IOException;

import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.usp.each.saeg.jaguar.core.JaCoCoClient;
import br.usp.each.saeg.jaguar.core.Jaguar;

public class JaguarRunListener extends RunListener {

	private static Logger logger = LoggerFactory.getLogger("JaguarLogger");
	private final Jaguar jaguar;

	private final JaCoCoClient client;

	private boolean currentTestFailed;

	public JaguarRunListener(Jaguar jaguar, JaCoCoClient client) {
		this.jaguar = jaguar;
		this.client = client;
	}

	@Override
	public void testStarted(Description description) throws Exception {
		currentTestFailed = false;
		jaguar.increaseNTests();
	}

	@Override
	public void testFailure(Failure failure) throws Exception {
		currentTestFailed = true;
		jaguar.increaseNTestsFailed();
	}

	@Override
	public void testFinished(Description description) throws Exception {
		printTestResult(description);
 		try {
			jaguar.collect(client.read(), currentTestFailed);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void printTestResult(Description description) {
		String result = currentTestFailed ? "Failed" : "Passed";
		logger.info("Test {}.{} : {}", description.getClassName(), description.getMethodName(), result);
	}

}
