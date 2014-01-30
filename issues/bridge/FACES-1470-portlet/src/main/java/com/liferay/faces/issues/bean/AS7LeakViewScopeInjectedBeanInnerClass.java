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
package com.liferay.faces.issues.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;


/**
 * @author  Neil Griffin
 */
@ManagedBean(name = "as7LeakViewScopeInjectedBeanInnerClass")
@ViewScoped
public class AS7LeakViewScopeInjectedBeanInnerClass implements Serializable {

	private static final String AS7LEAK_ATTRIBUTE_MAP = "com.liferay.faces.support.as7LeakAttributeMap";

	private static final long serialVersionUID = 3854382137975349082L;

	// Injections
	@ManagedProperty(name = "applicationScopeBean", value = "#{applicationScopeBean}")
	private ApplicationScopeBean applicationScopeBean;

	// Private Data Members
	private String foo = this.toString();
	private Map<String, Object> attributeMap = new HashMap<String, Object>();

	public AS7LeakViewScopeInjectedBeanInnerClass() {

		List<AS7LeakAttribute> savedAttributes = new ArrayList<AS7LeakAttribute>();
		AS7LeakAttribute as7LeakAttribute = new AS7LeakAttribute("as7LeakAttributeName", "as7LeakAttributeValue");
		savedAttributes.add(as7LeakAttribute);
		attributeMap.put(AS7LEAK_ATTRIBUTE_MAP, savedAttributes);
	}

	public void setApplicationScopeBean(ApplicationScopeBean applicationScopeBean) {
		this.applicationScopeBean = applicationScopeBean;
	}

	public String getFoo() {
		return foo;
	}

	public void setFoo(String foo) {
		this.foo = foo;
	}

	protected class AS7LeakAttribute extends NameValuePair<Object, Object> implements Serializable {

		// serialVersionUID
		private static final long serialVersionUID = 6818500149051763226L;

		public AS7LeakAttribute(Object name, Object value) {
			super(name, value);
		}
	}

}
