package com.bondsbis.trade;

import java.io.InvalidClassException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientDriver {
	protected static Client client = null;

	protected static final String URL_BASE = "http://0.0.0.0:9998/";

	private static final Logger LOGGER = LoggerFactory.getLogger(ClientDriver.class);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ClientConfig clientConfig = new ClientConfig();

		/**
		 * These classes are not currently registered
		 *
		 * <pre>
		 * MessageBodyReader s
		 * MessageBodyWriter s
		 * XmlAdapter s
		 * </pre>
		 */

		client = ClientBuilder.newClient(clientConfig);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		if (null != client) {
			client.close();
		}
	}

	public <T> Response getResponse(String urlString, String mediaType) throws InvalidClassException {

		final WebTarget target = client.target(urlString);

		final Response response = target.request().accept(mediaType).get();

		return response;
	}

	public <T> T get(String urlString, Class<T> clazz, String mediaType) throws InvalidClassException {

		final Response response = client.target(urlString).request().accept(mediaType).get();

		return response.readEntity(clazz);
	}

	public <T> Response post(String urlString, Object entity, String mediaType, boolean checkMediaType)
			throws InvalidClassException {

		final Invocation.Builder invocationBuilder = client.target(urlString).request(mediaType);

		final Response response = invocationBuilder.post(Entity.entity(entity, mediaType));

		if (checkMediaType) {
			isCorrectMediaType(mediaType, response);
		}

		return response;
	}

	private void isCorrectMediaType(String mediaType, final Response response) throws InvalidClassException {

		if (!response.getMediaType().toString().equals(mediaType)) {

			LOGGER.error("requested mediatype was " + mediaType + " but got " + response.getMediaType() + " instead");

			throw new InvalidClassException(
					"requested mediatype was " + mediaType + " but got " + response.getMediaType() + " instead");
		}
	}

}