package br.usp.each.saeg.jaguar.plugin;

import java.io.File;
import java.io.IOException;

import org.eclipse.jdt.junit.TestRunListener;
import org.eclipse.jdt.junit.model.ITestCaseElement;
import org.eclipse.jdt.junit.model.ITestElement.Result;
import org.eclipse.jdt.junit.model.ITestRunSession;

import br.usp.each.saeg.jaguar.core.JaCoCoClient;
import br.usp.each.saeg.jaguar.core.Jaguar;
import br.usp.each.saeg.jaguar.core.heuristic.TarantulaHeuristic;

public class JaguarTestRunListener extends TestRunListener {

	private Jaguar jaguar;

	private JaCoCoClient client;

	
	public JaguarTestRunListener() {

	}

	@Override
	public void sessionStarted(ITestRunSession session){
		jaguar = new Jaguar(new TarantulaHeuristic(), new File(session.getLaunchedProject().getPath().toString()));
		try {
			client = new JaCoCoClient();
			client.connect();
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.sessionStarted(session);
	}
	
	@Override
	public void sessionFinished(ITestRunSession session){
		super.sessionFinished(session);
	}
	
	@Override
	public void testCaseStarted(ITestCaseElement testCaseElement){
		jaguar.increaseNTests();
		super.testCaseStarted(testCaseElement);
	}
	
	@Override
	public void testCaseFinished(ITestCaseElement testCaseElement){
		super.testCaseFinished(testCaseElement);
		boolean currentTestFailed = false;
		if (Result.OK != testCaseElement.getTestResult(false)){ 
			jaguar.increaseNTestsFailed();
			currentTestFailed = true;
		}
		
		print(testCaseElement, currentTestFailed);
 		try {
			jaguar.collect(client.read(), currentTestFailed);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void print(ITestCaseElement testCaseElement, boolean currentTestFailed) {
		String result = currentTestFailed ? "Failed" : "Passed";
		System.out.println("Test " + testCaseElement.getTestClassName() + "." + testCaseElement.getTestMethodName() + ": " + result);
	}

}
