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
package com.liferay.faces.alloy.component;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.faces.component.UIForm;


/**
 * This class simply prepends "aui-form" to the "styleClass" component attribute. This is certainly a whole lot less
 * code when done as a Facelet composite component, but it's implemented here in Java for speed, since it's a top-level
 * component in most JSF views.
 *
 * @author  Neil Griffin
 */
public class AUIForm extends UIForm {

	// Private Constants
	private static final String AUI_FORM = "aui-form";
	private static final String SPACE = " ";
	private static final String STYLE_CLASS = "styleClass";

	// Private Data Members
	private Map<String, Object> attributesWrapper;

	@Override
	public Map<String, Object> getAttributes() {

		if (attributesWrapper == null) {
			Map<String, Object> attributes = super.getAttributes();

			if (attributes != null) {
				attributesWrapper = new AttributesWrapper(attributes);
			}
		}

		return attributesWrapper;
	}

	protected class AttributesWrapper implements Map<String, Object> {

		private Map<String, Object> wrappedAttributes;

		public AttributesWrapper(Map<String, Object> wrappedAttributes) {
			this.wrappedAttributes = wrappedAttributes;
		}

		public void clear() {
			wrappedAttributes.clear();
		}

		public boolean containsKey(Object key) {
			return wrappedAttributes.containsKey(key);
		}

		public boolean containsValue(Object value) {
			return wrappedAttributes.containsValue(value);
		}

		public Set<java.util.Map.Entry<String, Object>> entrySet() {
			return wrappedAttributes.entrySet();
		}

		public Object get(Object key) {

			Object value = wrappedAttributes.get(key);

			if ((key != null) && key.equals(STYLE_CLASS)) {

				if (value == null) {
					value = AUI_FORM;
				}
				else {
					value = AUI_FORM + SPACE + value;
				}
			}

			return value;
		}

		public Set<String> keySet() {
			return wrappedAttributes.keySet();
		}

		public Object put(String key, Object value) {
			return wrappedAttributes.put(key, value);
		}

		public void putAll(Map<? extends String, ? extends Object> t) {
			wrappedAttributes.putAll(t);
		}

		public Object remove(Object key) {
			return wrappedAttributes.remove(key);
		}

		public int size() {
			return wrappedAttributes.size();
		}

		public Collection<Object> values() {
			return wrappedAttributes.values();
		}

		public boolean isEmpty() {
			return wrappedAttributes.isEmpty();
		}
	}
}
