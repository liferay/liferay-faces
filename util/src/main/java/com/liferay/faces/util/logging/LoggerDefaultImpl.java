/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogRecord;

import com.liferay.faces.util.lang.StringPool;


/**
 * @author  Neil Griffin
 */
public class LoggerDefaultImpl implements Logger {

	// Self-Injections
	private static LogRecordFactory logRecordFactory = LogRecordFactoryImpl.getInstance();

	// Private Data Members
	private java.util.logging.Logger wrappedLogger;

	public LoggerDefaultImpl() {
	}

	public LoggerDefaultImpl(String className) {
		this.wrappedLogger = java.util.logging.Logger.getLogger(className);
	}

	public void debug(String message) {

		if (isDebugEnabled()) {
			LogRecord logRecord = logRecordFactory.getLogRecord(java.util.logging.Level.FINE, message, null);
			wrappedLogger.log(logRecord);
		}
	}

	public void debug(String message, Object... arguments) {

		if (isDebugEnabled()) {
			Throwable throwable = getThrowable(arguments);
			String formattedMessage = formatMessage(message, arguments);
			LogRecord logRecord = logRecordFactory.getLogRecord(java.util.logging.Level.FINE, formattedMessage,
					throwable);
			wrappedLogger.log(logRecord);
		}

	}

	public void error(Throwable throwable) {

		if (isErrorEnabled()) {
			String message = throwable.getMessage();
			LogRecord logRecord = logRecordFactory.getLogRecord(java.util.logging.Level.SEVERE, message, throwable);
			wrappedLogger.log(logRecord);
		}
	}

	public void error(String message) {

		if (isErrorEnabled()) {
			LogRecord logRecord = logRecordFactory.getLogRecord(java.util.logging.Level.SEVERE, message, null);
			wrappedLogger.log(logRecord);
		}
	}

	public void error(String message, Object... arguments) {

		if (isErrorEnabled()) {
			Throwable throwable = getThrowable(arguments);
			String formattedMessage = formatMessage(message, arguments);
			LogRecord logRecord = logRecordFactory.getLogRecord(java.util.logging.Level.SEVERE, formattedMessage,
					throwable);
			wrappedLogger.log(logRecord);
		}
	}

	public void info(String message) {

		if (isInfoEnabled()) {
			LogRecord logRecord = logRecordFactory.getLogRecord(java.util.logging.Level.INFO, message, null);
			wrappedLogger.log(logRecord);
		}
	}

	public void info(String message, Object... arguments) {

		if (isInfoEnabled()) {
			String formattedMessage = formatMessage(message, arguments);
			Throwable throwable = getThrowable(arguments);
			LogRecord logRecord = logRecordFactory.getLogRecord(java.util.logging.Level.INFO, formattedMessage,
					throwable);
			wrappedLogger.log(logRecord);
		}
	}

	public void trace(String message) {

		if (isTraceEnabled()) {
			LogRecord logRecord = logRecordFactory.getLogRecord(java.util.logging.Level.FINEST, message, null);
			wrappedLogger.log(logRecord);
		}
	}

	public void trace(String message, Object... arguments) {

		if (isTraceEnabled()) {
			String formattedMessage = formatMessage(message, arguments);
			Throwable throwable = getThrowable(arguments);
			LogRecord logRecord = logRecordFactory.getLogRecord(java.util.logging.Level.FINEST, formattedMessage,
					throwable);
			wrappedLogger.log(logRecord);
		}
	}

	public void warn(String message) {

		if (isWarnEnabled()) {
			LogRecord logRecord = logRecordFactory.getLogRecord(java.util.logging.Level.WARNING, message, null);
			wrappedLogger.log(logRecord);
		}
	}

	public void warn(String message, Object... arguments) {

		if (isWarnEnabled()) {
			Throwable throwable = getThrowable(arguments);
			String formattedMessage = formatMessage(message, arguments);
			LogRecord logRecord = logRecordFactory.getLogRecord(java.util.logging.Level.WARNING, formattedMessage,
					throwable);
			wrappedLogger.log(logRecord);
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

				// MessageFormat requires single quote (apostrophe characters) to be escaped.
				if (message.indexOf(StringPool.APOSTROPHE) >= 0) {
					message = message.replaceAll(StringPool.APOSTROPHE, StringPool.DOUBLE_APOSTROPHE);
				}

				formattedMessage = MessageFormat.format(message, argumentList.toArray(new Object[] {}));
			}
			catch (IllegalArgumentException e) {
				System.err.println("ERROR " + e.getClass() + ": " + e.getMessage() + ": " + message);
			}

			return formattedMessage;
		}
	}

	public boolean isDebugEnabled() {
		return wrappedLogger.isLoggable(java.util.logging.Level.FINE);
	}

	public boolean isErrorEnabled() {
		return wrappedLogger.isLoggable(java.util.logging.Level.SEVERE);
	}

	public boolean isInfoEnabled() {
		return wrappedLogger.isLoggable(java.util.logging.Level.INFO);
	}

	public boolean isTraceEnabled() {
		return wrappedLogger.isLoggable(java.util.logging.Level.FINEST);
	}

	public boolean isWarnEnabled() {
		return wrappedLogger.isLoggable(java.util.logging.Level.WARNING);
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
