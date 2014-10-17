package br.usp.each.saeg.jaguar.resource;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 * API to access the jaguar JAR file as a resource.
 */
public class JaguarJar {

	/**
	 * Name of the Jaguar JAR file resource within this bundle.
	 */
	private static final String RESOURCE = "/jaguarresource.jar";

	private JaguarJar() {
	}

	/**
	 * Returns a URL pointing to the JAR file.
	 * 
	 * @return URL of the JAR file
	 */
	public static URL getResource() {
		final URL url = JaguarJar.class.getResource(RESOURCE);
		if (url == null) {
			throw new AssertionError(ERRORMSG);
		}
		return url;
	}

	/**
	 * Returns the content of the JAR file as a stream.
	 * 
	 * @return content of the JAR file
	 */
	public static InputStream getResourceAsStream() {
		final InputStream stream = JaguarJar.class.getResourceAsStream(RESOURCE);
		if (stream == null) {
			throw new AssertionError(ERRORMSG);
		}
		return stream;
	}

	/**
	 * Extract the JaCoCo agent JAR and put it into a temporary location. This
	 * file should be deleted on exit, but may not if the VM is terminated
	 * 
	 * @return Location of the Agent Jar file in the local file system. The file
	 *         should exist and be readable.
	 * @throws IOException
	 *             Unable to unpack agent jar
	 */
	public static File extractToTempLocation() throws IOException {
		final File agentJar = File.createTempFile("jacocoagent", ".jar");
		agentJar.deleteOnExit();

		extractTo(agentJar);

		return agentJar;
	}

	/**
	 * Extract the Jaguar JAR and put it into the specified location.
	 * 
	 * @param destination
	 *            Location to write Jaguar Jar to. Must be writeable
	 * @throws IOException
	 *             Unable to unpack jar
	 */
	public static void extractTo(File destination) throws IOException {
		InputStream inputJarStream = getResourceAsStream();
		OutputStream outputJarStream = null;

		try {

			outputJarStream = new FileOutputStream(destination);

			final byte[] buffer = new byte[8192];

			int bytesRead;
			while ((bytesRead = inputJarStream.read(buffer)) != -1) {
				outputJarStream.write(buffer, 0, bytesRead);
			}
		} finally {
			safeClose(inputJarStream);
			safeClose(outputJarStream);
		}
	}

	/**
	 * Close a stream ignoring any error
	 * 
	 * @param closeable
	 *            stream to be closed
	 */
	private static void safeClose(Closeable closeable) {
		try {
			if (closeable != null) {
				closeable.close();
			}
		} catch (IOException e) {
		}
	}

	private static final String ERRORMSG = String.format("The resource %s has not been found", RESOURCE);

}
