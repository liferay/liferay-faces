/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
package com.liferay.faces.bridge.logging;

/**
 * In order to minimize dependencies, this class provides as a layer of abstraction over different logging mechanisms
 * including Log4J and standard Java SE logging.
 *
 * @author  Neil Griffin
 */
public class LoggerFactory {

	// Private Constants
	private static final String CLASS_NAME_LOG4J_LOGGER = "org.apache.log4j.Logger";

	// Statically-Initialized Private Constants
	private static boolean LOG4J_AVAILABLE = false;

	static {

		try {
			Class.forName(CLASS_NAME_LOG4J_LOGGER);
			LOG4J_AVAILABLE = true;
		}
		catch (Exception e) {
			LOG4J_AVAILABLE = false;
		}
	}

	/**
	 * This method gets a logger from the underlying logging implementation. First it tries Log4J, then standard Java SE
	 * logging mechanism. NOTE: In the future, support should be added for detection of Apache Commons-Logging and
	 * SLF4J.
	 *
	 * @param   clazz  The class associated with the logger.
	 *
	 * @return  The logger associated with the specified class.
	 */
	public static final Logger getLogger(Class<?> clazz) {

		String className = clazz.getName();

		Logger logger = null;

		try {

			if (LOG4J_AVAILABLE) {
				logger = new LoggerLog4JImpl(className);
			}
		}
		catch (NoClassDefFoundError e) {
			System.err.println(
				"com.liferay.faces.bridge.logging.LoggerFactory (WARN): Possibly an incompatible version of log4j.jar in the classpath: " +
				e.getMessage());
		}

		if (logger == null) {
			logger = new LoggerDefaultImpl(className);
		}

		return logger;
	}
}
