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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * @author  Neil Griffin
 */
public class LoggerDefaultImpl implements Logger {

	// Wrapped logger instance
	private java.util.logging.Logger logger;

	// Private Lazy-Initialized Data Memebers

	public LoggerDefaultImpl() {
	}

	public LoggerDefaultImpl(String className) {
		this.logger = java.util.logging.Logger.getLogger(className);
	}

	public void debug(String message) {
		debug(message, (Object[]) null);
	}

	public void debug(String message, Object... arguments) {

		if (isDebugEnabled()) {
			Throwable throwable = getThrowable(arguments);

			if (throwable == null) {
				logger.log(java.util.logging.Level.FINE, formatMessage(message, arguments));
			}
			else {
				logger.log(java.util.logging.Level.FINE, formatMessage(message, arguments), throwable);
			}
		}
	}

	public void error(Throwable throwable) {
		error(throwable.getMessage(), throwable);
	}

	public void error(String message) {
		error(message, (Object[]) null);
	}

	public void error(String message, Object... arguments) {

		if (isErrorEnabled()) {
			Throwable throwable = getThrowable(arguments);

			if (throwable == null) {
				logger.log(java.util.logging.Level.SEVERE, formatMessage(message, arguments));
			}
			else {
				logger.log(java.util.logging.Level.SEVERE, formatMessage(message, arguments), throwable);
			}
		}
	}

	public void info(String message) {
		info(message, (Object[]) null);
	}

	public void info(String message, Object... arguments) {

		if (isInfoEnabled()) {
			Throwable throwable = getThrowable(arguments);

			if (throwable == null) {
				logger.log(java.util.logging.Level.INFO, formatMessage(message, arguments));
			}
			else {
				logger.log(java.util.logging.Level.INFO, formatMessage(message, arguments), throwable);
			}
		}
	}

	public void trace(String message) {
		trace(message, (Object[]) null);
	}

	public void trace(String message, Object... arguments) {

		if (isTraceEnabled()) {
			Throwable throwable = getThrowable(arguments);

			if (throwable == null) {
				logger.log(java.util.logging.Level.FINEST, formatMessage(message, arguments));
			}
			else {
				logger.log(java.util.logging.Level.FINEST, formatMessage(message, arguments), throwable);
			}
		}
	}

	public void warn(String message) {
		warn(message, (Object[]) null);
	}

	public void warn(String message, Object... arguments) {

		if (isWarnEnabled()) {
			Throwable throwable = getThrowable(arguments);

			if (throwable == null) {
				logger.log(java.util.logging.Level.WARNING, formatMessage(message, arguments));
			}
			else {
				logger.log(java.util.logging.Level.WARNING, formatMessage(message, arguments), throwable);
			}
		}
	}

	protected String formatMessage(String message, Object[] arguments) {

		if ((message == null) || (arguments == null) || (arguments.length == 0)) {
			return message;
		}
		else {
			List<Object> argumentList = new ArrayList<Object>();

			for (Object argument : arguments) {

				if ((argument == null) || (argument instanceof Exception)) {
					argumentList.add(null);
				}
				else {

					if (argument.getClass().isArray()) {
						Object[] argArray = (Object[]) argument;
						StringBuilder arrayAsString = new StringBuilder("L[");
						boolean firstArg = true;

						for (Object arg : argArray) {

							if (firstArg) {
								firstArg = false;
							}
							else {
								arrayAsString.append(", ");
							}

							arrayAsString.append(arg);
						}

						arrayAsString.append("]");
						argumentList.add(arrayAsString.toString());
					}
					else {
						argumentList.add(argument);
					}
				}
			}

			String formattedMessage = message;

			try {
				formattedMessage = MessageFormat.format(message, argumentList.toArray(new Object[] {}));
			}
			catch (IllegalArgumentException e) {
				System.err.println("ERROR " + e.getClass() + ": " + e.getMessage() + ": " + message);
			}

			return formattedMessage;
		}
	}

	public boolean isDebugEnabled() {
		return logger.isLoggable(java.util.logging.Level.FINE);
	}

	public boolean isErrorEnabled() {
		return logger.isLoggable(java.util.logging.Level.SEVERE);
	}

	public boolean isInfoEnabled() {
		return logger.isLoggable(java.util.logging.Level.INFO);
	}

	public boolean isTraceEnabled() {
		return logger.isLoggable(java.util.logging.Level.FINEST);
	}

	public boolean isWarnEnabled() {
		return logger.isLoggable(java.util.logging.Level.WARNING);
	}

	protected Throwable getThrowable(Object[] arguments) {
		Throwable throwable = null;

		if (arguments != null) {

			for (Object arg : arguments) {

				if (arg != null) {

					if (arg instanceof Throwable) {
						throwable = (Throwable) arg;

						break;
					}
				}
			}
		}

		return throwable;
	}

}
