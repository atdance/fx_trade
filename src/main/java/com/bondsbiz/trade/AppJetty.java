package com.bondsbiz.trade;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bondsbiz.trade.business.api.ForexExchangeResource;

@ApplicationPath("/")
public class AppJetty extends Application {

	private static final Logger LOGGER = LoggerFactory.getLogger(AppJetty.class);

	public AppJetty() {
		Thread.setDefaultUncaughtExceptionHandler(new AppUncaughtExceptionHandler());
	}

	@Override
	public Set<Class<?>> getClasses() {

		if (true) {
			LOGGER.warn("www WARN getClasses");
		}
		LOGGER.info("iii INFO getClasses");
		LOGGER.debug("ddd DEBUG getClasses");

		return Stream.of(ForexExchangeResource.class).collect(Collectors.toSet());
	}
}