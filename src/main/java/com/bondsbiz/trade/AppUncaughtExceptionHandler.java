package com.bondsbiz.trade;

import java.lang.Thread.UncaughtExceptionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppUncaughtExceptionHandler implements UncaughtExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(AppUncaughtExceptionHandler.class);

	@Override
	public void uncaughtException(Thread thread, Throwable e) {
		String message = "Uncaught Exception: " + e.getClass().getName();
		if (e.getMessage() != null) {
			message += "; " + e.getMessage();
		}

		message += " in thread " + thread.getName();

		LOGGER.error(message);
	}

}
