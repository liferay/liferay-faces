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
package javax.portlet.faces.preference;

import java.util.List;

import javax.portlet.ReadOnlyException;


/**
 * @author  Neil Griffin
 */
public interface Preference {
	public void reset() throws ReadOnlyException;

	public String getName();

	public void setName(String name);

	public String getValue();

	public void setValue(String value) throws ReadOnlyException;

	public List<String> getValues();

	public void setValues(String[] values) throws ReadOnlyException;

	public boolean isReadOnly();
}
