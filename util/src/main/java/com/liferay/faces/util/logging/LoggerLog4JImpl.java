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
package com.liferay.faces.util.logging;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;


/**
 * @author  Neil Griffin
 */
public class LoggerLog4JImpl extends LoggerDefaultImpl {

	// Private Constants
	private static final String CALLING_CLASS_FQCN = LoggerLog4JImpl.class.getName();

	// Private Lazy-Initialized Data Memebers
	private org.apache.log4j.Logger logger;
	private Boolean traceSupported;

	public LoggerLog4JImpl(String className) {

		super();

		try {

			// Traverse the callstack to determine whether or not this class is being instantiated during
			// the shutdown of a Tomcat webapp context.
			boolean webappContextStopping = false;
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

			for (StackTraceElement stackTraceElement : stackTraceElements) {

				// Note: Currently only Tomcat is being detected.
				if (stackTraceElement.getClassName().equals("org.apache.catalina.core.StandardContext") &&
						stackTraceElement.getMethodName().equals("stop")) {
					webappContextStopping = true;
				}
			}

			// If this class is not being instantiated during the shutdown of a webapp context, then get
			// the Log4J logger and proceed. Otherwise, don't bother getting the logger, because it will
			// cause undesirable error output.
			if (!webappContextStopping) {
				logger = LogManager.getLogger(className);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void debug(String message, Object... arguments) {

		if (isDebugEnabled()) {

			try {
				logger.log(CALLING_CLASS_FQCN, Level.DEBUG, formatMessage(message, arguments), getThrowable(arguments));
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void error(String message, Object... arguments) {

		if (isErrorEnabled()) {

			try {
				logger.log(CALLING_CLASS_FQCN, Level.ERROR, formatMessage(message, arguments), getThrowable(arguments));
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void info(String message, Object... arguments) {

		if (isInfoEnabled()) {

			try {
				logger.log(CALLING_CLASS_FQCN, Level.INFO, formatMessage(message, arguments), getThrowable(arguments));
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void trace(String message, Object... arguments) {

		if (isTraceSupported()) {

			if (isTraceEnabled()) {

				try {
					logger.log(CALLING_CLASS_FQCN, Level.TRACE, formatMessage(message, arguments),
						getThrowable(arguments));
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		else {

			// Attempt debug if trace is not supported
			debug(message, arguments);
		}
	}

	@Override
	public void warn(String message, Object... arguments) {

		if (isWarnEnabled()) {

			try {
				logger.log(CALLING_CLASS_FQCN, Level.WARN, formatMessage(message, arguments), getThrowable(arguments));
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	@Override
	public boolean isErrorEnabled() {
		return logger.isEnabledFor(Level.ERROR);
	}

	@Override
	public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}

	@Override
	public boolean isTraceEnabled() {
		return logger.isTraceEnabled();
	}

	@Override
	public boolean isWarnEnabled() {
		return logger.isEnabledFor(Level.WARN);
	}

	protected boolean isTraceSupported() {

		if (traceSupported == null) {

			try {
				isTraceEnabled();
				traceSupported = Boolean.TRUE;
			}
			catch (NoSuchMethodError e) {

				// log4j didn't support isTraceEnabled until 1.2.12
				traceSupported = Boolean.FALSE;
			}
		}

		return traceSupported.booleanValue();
	}

}
