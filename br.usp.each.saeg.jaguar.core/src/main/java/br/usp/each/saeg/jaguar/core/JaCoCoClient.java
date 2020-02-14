package br.usp.each.saeg.jaguar.core;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

//import org.jacoco.core.data.AbstractExecutionDataStore;
//import org.jacoco.core.data.ControlFlowExecutionDataStore;
//import org.jacoco.core.data.SessionInfoStore;
//import org.jacoco.core.data.DataFlowExecutionDataStore;
//import org.jacoco.core.runtime.RemoteControlReader;
//import org.jacoco.core.runtime.RemoteControlWriter;
import br.usp.each.saeg.badua.core.data.ExecutionDataStore;
import br.usp.each.saeg.dfjaguar.agent.rt.internal.Agent;
import br.usp.each.saeg.dfjaguar.core.runtime.RemoteControlReader;
import br.usp.each.saeg.dfjaguar.core.runtime.RemoteControlWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class JaCoCoClient {

	private final static Logger logger = LoggerFactory.getLogger("JaguarLogger");

	private static final String DEFAULT_ADDRESS = "localhost";
	private static final int DEFAULT_PORT = 6300;
	private final InetAddress address;
	private final int port;

	private Socket socket;
	private RemoteControlWriter writer;
	private RemoteControlReader reader;
	private final Boolean isDataflow;

	public JaCoCoClient(final InetAddress address, final int port, Boolean isDataflow) {
		this.address = address;
		this.port = port;
		this.isDataflow = isDataflow;
	}

	public JaCoCoClient(Boolean isDataflow) throws UnknownHostException {
		this(InetAddress.getByName(DEFAULT_ADDRESS), DEFAULT_PORT, isDataflow);
	}
	
	public JaCoCoClient() throws UnknownHostException {
		this(InetAddress.getByName(DEFAULT_ADDRESS), DEFAULT_PORT, false);
	}

	public void connect() throws IOException {
		socket = new Socket(address, port);
		writer = new RemoteControlWriter(socket.getOutputStream());
		reader = new RemoteControlReader(socket.getInputStream());
	}

	public void close() throws IOException {
		socket.close();
	}

	public ExecutionDataStore read() throws Exception {
		//SessionInfoStore sessionInfo = new SessionInfoStore();
		logger.trace(">>> session info");
		//AbstractExecutionDataStore executionData = isDataflow ? new DataFlowExecutionDataStore() : new ControlFlowExecutionDataStore();
		ExecutionDataStore executionData = new ExecutionDataStore();
		logger.trace(">>> execution data");

		//reader.setSessionInfoVisitor(sessionInfo);
		logger.trace(">>> set session info visitor");
		reader.setExecutionDataVisitor(executionData);
		logger.trace(">>> set execution data visitor");

		// Send a dump and reset command and read the response:
		writer.visitDumpCommand(true, true);
		logger.trace(">>> visit dump command");

		reader.read();
		logger.trace(">>> read");

		Agent.getInstance().getData().collect(executionData);

		return executionData;
	}

	public void closeSocket() throws IOException {
		socket.close();
	}

}