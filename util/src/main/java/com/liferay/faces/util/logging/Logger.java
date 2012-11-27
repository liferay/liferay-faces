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

/**
 * @author  Neil Griffin
 */
public interface Logger {

	public static final String SEPARATOR = "----------------------------------------------------------------------";

    // Toha: Methods (with message only) may be useful for some backward compatibility only  
    // or to user new versions of bridge-utils without recompiling. 
    // For newer versions it should be better to remove them.
	@Deprecated
    public void debug(String message);

	public void debug(String message, Object... arguments);

	@Deprecated
	public void error(String message);

	public void error(Throwable throwable);

	public void error(String message, Object... arguments);

	@Deprecated
	public void info(String message);

	public void info(String message, Object... arguments);

	@Deprecated
	public void trace(String message);

	public void trace(String message, Object... arguments);

	@Deprecated
	public void warn(String message);

	public void warn(String message, Object... arguments);

	public boolean isDebugEnabled();

	public boolean isErrorEnabled();

	public boolean isInfoEnabled();

	public boolean isTraceEnabled();

	public boolean isWarnEnabled();
}
