package br.usp.each.saeg.jaguar.core.runner;

import java.io.IOException;

import br.usp.each.saeg.badua.core.data.ExecutionDataStore;
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
	public void testFinished(Description description) {
		printTestResult(description);
 		try {
 			long startTime = System.currentTimeMillis();
 			ExecutionDataStore dataStore = client.read();
 			logger.debug("Time to receive data: {}", System.currentTimeMillis() - startTime);
 			
 			startTime = System.currentTimeMillis();
			jaguar.collect(dataStore, currentTestFailed);
			logger.debug("Time to collect data: {}", System.currentTimeMillis() - startTime);
		} catch (Exception e) {
			logger.error("Exception :" + e.toString());
			logger.error("Exception Message : " + e.getMessage());
			logger.error("Stacktrace :");
			e.printStackTrace(System.err);
			System.exit(1);
		}
	}

	private void printTestResult(Description description) {
		if (currentTestFailed){
			logger.info("Test {} : Failed", description.getDisplayName());
		}else{
			logger.debug("Test {} : Passed", description.getDisplayName());
		}
	}

}
