package com.bondsbiz.trade;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import javax.json.stream.JsonGenerator;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bondsbiz.trade.business.api.ForexExchangeResource;

public class AppGrizzly {
	private static final Logger LOGGER = LoggerFactory.getLogger(AppGrizzly.class);

	private static final URI BASE_URI = URI.create("http://0.0.0.0:9998/");

	static ResourceConfig config = null;

	public static void main(String[] args) {
		try {
			Thread.setDefaultUncaughtExceptionHandler(new AppUncaughtExceptionHandler());

			config = new ResourceConfig(

					// not needed to add these as already tested: ArrayListReader.class,
					// ListReader.class,

					ForexExchangeResource.class);

			boolean jersey_media_jaxb = true;

			if (jersey_media_jaxb) {

				Set<Class<?>> classes = new HashSet<>();

				LOGGER.debug("dddddddddddddddddd  AppGrizzly packages");

				config.packages("com.bondsbiz.trade.business.api");

				config.packages("com.bondsbiz.trade.business.model");

				config.property(JsonGenerator.PRETTY_PRINTING, true);

				LOGGER.info("iiiiiiiiiiiiiiiiii  AppGrizzly packages");

				// resourceConfig.property(CommonProperties.METAINF_SERVICES_LOOKUP_DISABLE,
				// true);
			}

			HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, config, false);

			Runtime.getRuntime().addShutdownHook(new Thread(() -> {
				try {
					server.shutdown();
				} catch (Exception e) {
					LOGGER.error(e.toString());
				}
			}));

			server.start();

			LOGGER.info("Application started " + BASE_URI + " .Stop the application using CTRL+C");

			Thread.currentThread().join();
		} catch (Exception e) {
			LOGGER.error(e.toString());
		}
	}
}