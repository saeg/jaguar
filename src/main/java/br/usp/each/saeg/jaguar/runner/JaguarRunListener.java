package br.usp.each.saeg.jaguar.runner;

import java.io.IOException;

import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import br.usp.each.saeg.jaguar.Jaguar;
import br.usp.each.saeg.jaguar.jacoco.JacocoTCPClient;

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

        // increase the number of tests
        jaguar.increaseNTests();
    }

    @Override
    public void testFailure(Failure failure) throws Exception {
        currentTestFailed = true;

        // increase the number of failed tests
        jaguar.increaseNTestsFailed();
    }

    @Override
    public void testFinished(Description description) throws Exception {
        try {
            jaguar.collect(tcpClient.read(), currentTestFailed);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
