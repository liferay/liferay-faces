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

    @Override
	public void debug(String message) {
		if (isDebugEnabled())
            this.doLog(java.util.logging.Level.FINE, message, null);
	}

    @Override
	public void debug(String message, Object... arguments) {

		if (isDebugEnabled()) {
			Throwable throwable = getThrowable(arguments);
            this.doLog(java.util.logging.Level.FINE, formatMessage(message, arguments), throwable);
		}
        
	}

    @Override
	public void error(Throwable throwable) {
		if (isErrorEnabled()) 
            this.doLog(java.util.logging.Level.SEVERE, throwable.getMessage(), throwable);
	}

    @Override
	public void error(String message) {
		if (isErrorEnabled()) 
            this.doLog(java.util.logging.Level.SEVERE, message, null);
	}

    @Override
	public void error(String message, Object... arguments) {

		if (isErrorEnabled()) {
			Throwable throwable = getThrowable(arguments);
            this.doLog(java.util.logging.Level.SEVERE, formatMessage(message, arguments), throwable);
		}
	}

    @Override
	public void info(String message) {
		if (isInfoEnabled()) 
            this.doLog(java.util.logging.Level.INFO, message, null);
	}

    @Override
	public void info(String message, Object... arguments) {

		if (isInfoEnabled()) {
			Throwable throwable = getThrowable(arguments);
            this.doLog(java.util.logging.Level.INFO, formatMessage(message, arguments), throwable);
		}
	}

    @Override
	public void trace(String message) {
		if (isTraceEnabled()) 
            this.doLog(java.util.logging.Level.FINEST, message , null);
	}

    @Override
	public void trace(String message, Object... arguments) {

		if (isTraceEnabled()) {
			Throwable throwable = getThrowable(arguments);
            this.doLog(java.util.logging.Level.FINEST, formatMessage(message, arguments), throwable);
		}
	}

    @Override
	public void warn(String message) {
		if (isWarnEnabled())
            this.doLog(java.util.logging.Level.WARNING, message, null);
	}

    @Override
	public void warn(String message, Object... arguments) {

		if (isWarnEnabled()) {
			Throwable throwable = getThrowable(arguments);
            this.doLog(java.util.logging.Level.WARNING, formatMessage(message, arguments), throwable);
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
                // Toha: MessageFormat needs to special processing of quoting string, but many
                // don't mention this. For example in many log messages in liferay-brigge-impl you can
                // meet char (') that cause wrong message processing
                message=message.replaceAll("'", "''");
				formattedMessage = MessageFormat.format(message, argumentList.toArray(new Object[] {}));
			}
			catch (IllegalArgumentException e) {
				System.err.println("ERROR " + e.getClass() + ": " + e.getMessage() + ": " + message);
			}

			return formattedMessage;
		}
	}

    @Override
	public boolean isDebugEnabled() {
		return logger.isLoggable(java.util.logging.Level.FINE);
	}

    @Override
	public boolean isErrorEnabled() {
		return logger.isLoggable(java.util.logging.Level.SEVERE);
	}

    @Override
	public boolean isInfoEnabled() {
		return logger.isLoggable(java.util.logging.Level.INFO);
	}

    @Override
	public boolean isTraceEnabled() {
		return logger.isLoggable(java.util.logging.Level.FINEST);
	}

    @Override
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

    private void doLog(java.util.logging.Level level, String message, Throwable thr)  {
        Throwable stackStource = new Throwable();
        StackTraceElement[] stackTrace = stackStource.getStackTrace();
        StackTraceElement callerStack = stackTrace[2];
        java.util.logging.LogRecord logRecord = new java.util.logging.LogRecord(level, message);
    
        if (thr!=null)
            logRecord.setThrown(thr);
        
        logRecord.setSourceClassName(callerStack.getClassName());
        logRecord.setSourceMethodName(callerStack.getMethodName());
        logger.log(logRecord);
    }
    
}
