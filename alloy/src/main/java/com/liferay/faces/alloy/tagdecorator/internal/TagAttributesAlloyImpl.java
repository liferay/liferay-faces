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
package com.liferay.faces.alloy.tagdecorator.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.faces.render.Renderer;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagAttributes;


/**
 * @author  Neil Griffin
 */
public class TagAttributesAlloyImpl extends TagAttributes {

	// Private Data Members
	private Map<String, TagAttribute> tagAttributeMap = new HashMap<String, TagAttribute>();

	public TagAttributesAlloyImpl(TagAttributes tagAttributes) {

		TagAttribute[] tagAttributesArray = tagAttributes.getAll();

		for (TagAttribute tagAttribute : tagAttributesArray) {

			String localName = tagAttribute.getLocalName();

			if (!Renderer.PASSTHROUGH_RENDERER_LOCALNAME_KEY.equals(localName)) {
				String namespace = tagAttribute.getNamespace();
				String mapKey = getMapKey(namespace, localName);
				this.tagAttributeMap.put(mapKey, tagAttribute);
			}
		}
	}

	@Override
	public TagAttribute get(String localName) {
		return tagAttributeMap.get(localName);
	}

	@Override
	public TagAttribute get(String namespace, String localName) {
		String mapKey = getMapKey(namespace, localName);

		return tagAttributeMap.get(mapKey);
	}

	@Override
	public TagAttribute[] getAll() {

		TagAttribute[] tagAttributeArray = new TagAttribute[tagAttributeMap.size()];
		Set<Entry<String, TagAttribute>> entrySet = tagAttributeMap.entrySet();
		int i = 0;

		for (Entry<String, TagAttribute> mapEntry : entrySet) {
			tagAttributeArray[i++] = mapEntry.getValue();
		}

		return tagAttributeArray;
	}

	@Override
	public TagAttribute[] getAll(String namespace) {

		List<TagAttribute> tagAttributeList = new ArrayList<TagAttribute>();
		Set<Entry<String, TagAttribute>> entrySet = tagAttributeMap.entrySet();

		for (Entry<String, TagAttribute> mapEntry : entrySet) {
			TagAttribute tagAttribute = mapEntry.getValue();
			String tagAttributeNamespace = tagAttribute.getNamespace();

			if (((namespace == null) && (tagAttributeNamespace == null)) ||
					((namespace != null) && namespace.equals(tagAttributeNamespace))) {
				tagAttributeList.add(tagAttribute);
			}
		}

		return tagAttributeList.toArray(new TagAttribute[tagAttributeList.size()]);
	}

	private String getMapKey(String namespace, String localName) {
		String mapKey = namespace;

		if (mapKey == null) {
			mapKey = localName;
		}
		else {

			if (localName != null) {
				mapKey += localName;
			}
		}

		return mapKey;
	}

	@Override
	public String[] getNamespaces() {

		Set<String> namespaceSet = new HashSet<String>();
		Set<Entry<String, TagAttribute>> entrySet = tagAttributeMap.entrySet();

		for (Entry<String, TagAttribute> mapEntry : entrySet) {
			TagAttribute tagAttribute = mapEntry.getValue();
			namespaceSet.add(tagAttribute.getNamespace());
		}

		return namespaceSet.toArray(new String[namespaceSet.size()]);
	}
}
