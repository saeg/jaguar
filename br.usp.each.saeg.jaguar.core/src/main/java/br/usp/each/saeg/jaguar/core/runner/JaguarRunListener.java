package br.usp.each.saeg.jaguar.core.runner;

import java.io.IOException;

import org.jacoco.agent.rt.RT;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import br.usp.each.saeg.jaguar.core.JaCoCoClient;
import br.usp.each.saeg.jaguar.core.Jaguar;

public class JaguarRunListener extends RunListener {

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
		try {
		    RT.getAgent().dump(true);
			jaguar.collect(client.getExecutionDataStore(), currentTestFailed);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
