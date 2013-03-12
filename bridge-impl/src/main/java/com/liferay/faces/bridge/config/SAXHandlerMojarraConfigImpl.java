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
package com.liferay.faces.bridge.config;

import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class SAXHandlerMojarraConfigImpl extends SAXHandlerBaseImpl {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(SAXHandlerMojarraConfigImpl.class);

	// Private Constants
	private static final String SOURCE_CLASS = "source-class";
	private static final String SYSTEM_EVENT_CLASS = "system-event-class";
	private static final String SYSTEM_EVENT_LISTENER = "system-event-listener";
	private static final String SYSTEM_EVENT_LISTENER_CLASS = "system-event-listener-class";

	// Private Data Members
	private List<ConfiguredSystemEventListener> configuredSystemEventListeners;
	private boolean parsingSourceClass;
	private boolean parsingSystemEventClass;
	private boolean parsingSystemEventListener;
	private boolean parsingSystemEventListenerClass;
	private String sourceClass;
	private String systemEventClass;
	private String systemEventListenerClass;

	public SAXHandlerMojarraConfigImpl(boolean resolveEntities,
		List<ConfiguredSystemEventListener> configuredSystemEventListeners) {
		super(resolveEntities);
		this.configuredSystemEventListeners = configuredSystemEventListeners;
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {

		if (parsingSourceClass) {
			sourceClass = content.toString().trim();
			parsingSourceClass = false;
		}
		else if (parsingSystemEventClass) {
			systemEventClass = content.toString().trim();
			parsingSystemEventClass = false;
		}
		else if (parsingSystemEventListenerClass) {
			systemEventListenerClass = content.toString().trim();
			parsingSystemEventListenerClass = false;
		}
		else if (parsingSystemEventListener) {

			// NOTE: This has to appear last since system-event-listener is the surrounding element.
			ConfiguredSystemEventListener configuredSystemEventListener = new ConfiguredSystemEventListenerImpl(
					sourceClass, systemEventClass, systemEventListenerClass);
			configuredSystemEventListeners.add(configuredSystemEventListener);
			parsingSystemEventListener = false;
		}

		content = null;
	}

	@Override
	public void startElement(String uri, String localName, String elementName, Attributes attributes)
		throws SAXException {
		logger.trace("localName=[{0}]", localName);

		content = new StringBuilder();

		if (localName.equals(SOURCE_CLASS)) {
			parsingSourceClass = true;
		}
		else if (localName.equals(SYSTEM_EVENT_CLASS)) {
			parsingSystemEventClass = true;
		}
		else if (localName.equals(SYSTEM_EVENT_LISTENER)) {
			parsingSystemEventListener = true;
		}
		else if (localName.equals(SYSTEM_EVENT_LISTENER_CLASS)) {
			parsingSystemEventListenerClass = true;
		}
	}
}
