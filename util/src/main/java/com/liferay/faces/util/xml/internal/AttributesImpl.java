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
package com.liferay.faces.util.xml.internal;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;


/**
 * @author  Neil Griffin
 */
public class AttributesImpl implements Attributes {

	// Public Constants
	public static final String TYPE_CDATA = "CDATA";
	public static final String TYPE_ID = "ID";
	public static final String TYPE_IDREF = "IDREF";
	public static final String TYPE_IDREFS = "IDREFS";
	public static final String TYPE_NMTOKEN = "NMTOKEN";
	public static final String TYPE_NMTOKENS = "NMTOKENS";
	public static final String TYPE_ENTITY = "ENTITY";
	public static final String TYPE_ENTITIES = "ENTITIES";
	public static final String TYPE_NOTATION = "NOTATION";

	// Private Data Members
	private List<Attribute> attributes;

	public AttributesImpl() {
		this.attributes = new ArrayList<Attribute>();
	}

	public void add(String uri, String localName, String qName, String type, String value) {
		Attribute attribute = new Attribute(uri, localName, qName, type, value);
		attributes.add(attribute);
	}

	public int getIndex(String qName) {

		int index = -1;

		if (qName != null) {
			int length = attributes.size();

			for (int i = 0; i < length; i++) {
				Attribute attribute = attributes.get(i);

				if (qName.equals(attribute.qName)) {
					index = i;

					break;
				}
			}
		}

		return index;
	}

	public int getIndex(String uri, String localName) {

		int index = -1;

		if ((uri != null) && (localName != null)) {

			int length = attributes.size();

			for (int i = 0; i < length; i++) {
				Attribute attribute = attributes.get(i);

				if (uri.equals(attribute.uri) && localName.equals(attribute.localName)) {
					index = i;

					break;
				}
			}
		}

		return index;
	}

	public int getLength() {
		return attributes.size();
	}

	public String getLocalName(int index) {
		return attributes.get(index).localName;
	}

	public String getQName(int index) {
		return attributes.get(index).qName;
	}

	public String getType(int index) {
		return attributes.get(index).type;
	}

	public String getType(String qName) {
		String type = null;

		if (qName != null) {
			int length = attributes.size();

			for (int i = 0; i < length; i++) {
				Attribute attribute = attributes.get(i);

				if (qName.equals(attribute.qName)) {
					type = attribute.type;

					break;
				}
			}
		}

		return type;
	}

	public String getType(String uri, String localName) {
		String type = null;

		if ((uri != null) && (localName != null)) {
			int length = attributes.size();

			for (int i = 0; i < length; i++) {
				Attribute attribute = attributes.get(i);

				if (uri.equals(attribute.uri) && localName.equals(attribute.localName)) {
					type = attribute.type;

					break;
				}
			}
		}

		return type;
	}

	public String getURI(int index) {
		return attributes.get(index).uri;
	}

	public String getValue(int index) {
		return attributes.get(index).value;
	}

	public String getValue(String qName) {
		String value = null;

		if (qName != null) {
			int length = attributes.size();

			for (int i = 0; i < length; i++) {
				Attribute attribute = attributes.get(i);

				if (qName.equals(attribute.qName)) {
					value = attribute.value;

					break;
				}
			}
		}

		return value;
	}

	public String getValue(String uri, String localName) {
		String value = null;

		if ((uri != null) && (localName != null)) {
			int length = attributes.size();

			for (int i = 0; i < length; i++) {
				Attribute attribute = attributes.get(i);

				if (uri.equals(attribute.uri) && localName.equals(attribute.localName)) {
					value = attribute.value;

					break;
				}
			}
		}

		return value;
	}

	protected class Attribute {

		// Public Data Members
		public String uri;
		public String localName;
		public String qName;
		public String type;
		public String value;

		public Attribute(String uri, String localName, String qName, String type, String value) {
			this.uri = uri;
			this.localName = localName;
			this.qName = qName;
			this.type = type;
			this.value = value;
		}

	}
}
