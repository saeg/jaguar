package br.usp.each.saeg.jaguar.core;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.jacoco.core.data.ExecutionDataReader;
import org.jacoco.core.data.ExecutionDataStore;
import org.jacoco.core.data.SessionInfoStore;

public class JaCoCoClient {

    public static final int DEFAULT_PORT = 6300;

    private final InetAddress address;

    private final int port;

    private Socket socket;

    private ExecutionDataReader reader;

    public JaCoCoClient(final InetAddress address, final int port) {
        this.address = address;
        this.port = port;
    }

    public JaCoCoClient() throws UnknownHostException {
        this(InetAddress.getLocalHost(), DEFAULT_PORT);
    }

    public void connect() throws IOException {
        socket = new Socket(address, port);
        reader = new ExecutionDataReader(socket.getInputStream());
    }

    public void close() throws IOException {
        socket.close();
    }

    public ExecutionDataStore getExecutionDataStore() throws IOException {
        final ExecutionDataStore e;

        reader.setSessionInfoVisitor(new SessionInfoStore());
        reader.setExecutionDataVisitor(e = new ExecutionDataStore());

        // Read until we receive a CMDOK or a exception occur
        while (reader.read());

        return e;
    }

}
