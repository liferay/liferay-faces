/**
 * Copyright (c) 2000-2014 Liferay, Inc. All rights reserved.
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

import java.util.logging.LogRecord;


/**
 * @author  Neil Griffin
 */
public class LogRecordFactoryImpl implements LogRecordFactory {

	private static LogRecordFactory instance = new LogRecordFactoryImpl();

	private LogRecordFactoryImpl() {
		// Private constructor in order to enforce singleton pattern
	}

	public static LogRecordFactory getInstance() {
		return instance;
	}

	/* (non-Javadoc)
	 * @see com.liferay.faces.util.logging.LogRecordFactory#getLogRecord(java.util.logging.Level, java.lang.String, java.lang.Throwable)
	 */
	public LogRecord getLogRecord(java.util.logging.Level level, String message, Throwable thrown) {

		// Create a new LogRecord instance.
		LogRecord logRecord = new LogRecord(level, message);

		// Determine the source class name and source method name.
		Throwable source = new Throwable();
		StackTraceElement[] stackTraceElements = source.getStackTrace();
		StackTraceElement callerStackTraceElement = stackTraceElements[2];

		// Set the source class name and source method name.
		logRecord.setSourceClassName(callerStackTraceElement.getClassName());
		logRecord.setSourceMethodName(callerStackTraceElement.getMethodName());

		// If specified, set the throwable associated with the log event.
		if (thrown != null) {
			logRecord.setThrown(thrown);
		}

		// Return the new LogRecord instance.
		return logRecord;
	}

}
