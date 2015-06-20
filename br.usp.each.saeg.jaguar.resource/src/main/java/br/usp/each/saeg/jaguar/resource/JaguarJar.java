package br.usp.each.saeg.jaguar.resource;

import java.io.InputStream;
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

	private static final String ERRORMSG = String.format("The resource %s has not been found", RESOURCE);

}
