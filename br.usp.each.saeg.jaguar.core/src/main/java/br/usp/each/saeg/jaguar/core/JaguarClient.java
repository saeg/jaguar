package br.usp.each.saeg.jaguar.core;

import java.util.Arrays;

import br.usp.each.saeg.badua.agent.rt.internal_04b6ae3.Agent;
import br.usp.each.saeg.badua.core.data.ExecutionDataStore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class JaguarClient {

	private final static Logger logger = LoggerFactory.getLogger("JaguarLogger");

	private final Boolean isDataflow;

	public JaguarClient() {
		this.isDataflow = true;
	}

	public JaguarClient(Boolean isDataflow) {
		this.isDataflow = isDataflow;
	}

	public ExecutionDataStore read() {
		Agent agent = Agent.getInstance();
		ExecutionDataStore current = new ExecutionDataStore();

		agent.getData().collect(data ->
				current.visitClassExecution(new br.usp.each.saeg.badua.core.data.ExecutionData(
						data.getId(),
						data.getName(),
						Arrays.copyOf(data.getData(), data.getData().length))
				)
		);

		agent.reset();

		return current;
	}

}