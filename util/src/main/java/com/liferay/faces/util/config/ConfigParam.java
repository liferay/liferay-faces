/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.util.config;

/**
 * This is a generic interface that defines the contract for getting configuration param values.
 *
 * @author  Neil Griffin
 */
public interface ConfigParam<T> {

	/**
	 * Returns the alternate name of the config param (or null if there is not an alternate name).
	 */
	public String getAlternateName();

	/**
	 * Returns the boolean value of the config param. If not specified in the config file, then the value of {@link
	 * #getDefaultBooleanValue()} is returned.
	 */
	public boolean getBooleanValue(T config);

	/**
	 * Returns the value of the config param that was specified in the configuration file.
	 */
	public String getConfiguredValue(T config);

	/**
	 * Flag indicating whether or not the config param was specified in the configuration file.
	 */
	public boolean isConfigured(T config);

	/**
	 * Returns the default boolean value of the config param.
	 */
	public boolean getDefaultBooleanValue();

	/**
	 * Returns the default int value of the config param.
	 */
	public int getDefaultIntegerValue();

	/**
	 * Returns the default long value of the config param.
	 */
	public long getDefaultLongValue();

	/**
	 * Returns the default String value of the config param.
	 */
	public String getDefaultStringValue();

	/**
	 * Returns the int value of the config param. If not specified in the config file, then the value of {@link
	 * #getDefaultIntegerValue()} is returned.
	 */
	public int getIntegerValue(T config);

	/**
	 * Returns the long value of the config param. If not specified in the config file, then the value of {@link
	 * #getDefaultLongValue()} is returned.
	 */
	public long getLongValue(T config);

	/**
	 * Returns the name of the config param.
	 */
	public String getName();

	/**
	 * Returns the String value of the config param. If not specified in the config file, then the value of {@link
	 * #getDefaultStringValue()} is returned.
	 */
	public String getStringValue(T config);
}
