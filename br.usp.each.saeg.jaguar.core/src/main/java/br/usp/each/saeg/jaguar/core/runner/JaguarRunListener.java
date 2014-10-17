package br.usp.each.saeg.jaguar.core.runner;

import java.io.IOException;

import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import br.usp.each.saeg.jaguar.core.Jaguar;
import br.usp.each.saeg.jaguar.core.jacoco.JacocoTCPClient;

public class JaguarRunListener extends RunListener {

	private final Jaguar jaguar;

	private final JacocoTCPClient tcpClient;

	private boolean currentTestFailed;

	public JaguarRunListener(Jaguar jaguar, JacocoTCPClient tcpClient) {
		this.jaguar = jaguar;
		this.tcpClient = tcpClient;
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
		print(description);
		try {
			jaguar.collect(tcpClient.read(), currentTestFailed);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void print(Description description) {
		String result = currentTestFailed ? "Failed" : "Passed";
		System.out.println("Test " + description.getClassName() + "." + description.getMethodName() + ": " + result);
	}

}
