package br.usp.each.saeg.jaguar.core;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.jacoco.core.data.AbstractExecutionDataStore;
import org.jacoco.core.data.ControlFlowExecutionDataStore;
import org.jacoco.core.data.SessionInfoStore;
import org.jacoco.core.data.DataFlowExecutionDataStore;
import org.jacoco.core.runtime.RemoteControlReader;
import org.jacoco.core.runtime.RemoteControlWriter;

public final class JaCoCoClient {

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

	public AbstractExecutionDataStore read() throws IOException {
		SessionInfoStore sessionInfo = new SessionInfoStore();
		AbstractExecutionDataStore executionData = isDataflow ? new DataFlowExecutionDataStore() : new ControlFlowExecutionDataStore();

		reader.setSessionInfoVisitor(sessionInfo);
		reader.setExecutionDataVisitor(executionData);

		// Send a dump and reset command and read the response:
		writer.visitDumpCommand(true, true);
		reader.read();

		return executionData;
	}

	public void closeSocket() throws IOException {
		socket.close();
	}

}