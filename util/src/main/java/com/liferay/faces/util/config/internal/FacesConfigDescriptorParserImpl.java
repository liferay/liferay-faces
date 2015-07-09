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
package com.liferay.faces.util.config.internal;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

import javax.xml.parsers.SAXParser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.xml.internal.SAXHandlerBase;
import com.liferay.faces.util.xml.internal.SAXParseCompleteException;


/**
 * @author  Neil Griffin
 */
public class FacesConfigDescriptorParserImpl extends SAXHandlerBase implements FacesConfigDescriptorParser {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(FacesConfigDescriptorParserImpl.class);

	// Private Constants
	private static final String ABSOLUTE_ORDERING = "absolute-ordering";
	private static final String AFTER = "after";
	private static final String BEFORE = "before";
	private static final String FACES_CONFIG = "faces-config";
	private static final String ORDERING = "ordering";
	private static final String OTHERS = "others";

	// Private Data Members
	private String facesConfigName;
	private List<String> afterNames;
	private List<String> beforeNames;
	private boolean isWebInfFacesConfig;
	private List<String> absoluteOrdering;
	private Ordering ordering;
	private boolean parsingAbsoluteOrdering;
	private boolean parsingAfter;
	private boolean parsingBefore;
	private boolean parsingFacesConfig;
	private boolean parsingName = false;
	private boolean parsingOrdering;
	private boolean parsingOthers;
	private SAXParser saxParser;

	public FacesConfigDescriptorParserImpl(SAXParser saxParser, boolean resolveEntities) {
		super(resolveEntities);
		this.saxParser = saxParser;
		this.afterNames = new ArrayList<String>();
		this.ordering = new OrderingImpl();
		this.beforeNames = new ArrayList<String>();
	}

	@Override
	public void endElement(String uri, String localName, String elementName) throws SAXException {

		if (parsingFacesConfig) {

			if (parsingName && !parsingAbsoluteOrdering && !parsingOrdering) {
				facesConfigName = content.toString().trim();
				parsingName = false;
			}
			else if (parsingAbsoluteOrdering) {

				if (parsingName) {

					if (content != null) {
						String name = content.toString().trim();
						absoluteOrdering.add(name);
					}

					parsingName = false;
				}
				else if (parsingOthers) {

					absoluteOrdering.add(Ordering.OTHERS);

					parsingOthers = false;
				}
				else {

					if (localName.equals(ABSOLUTE_ORDERING)) {
						parsingAbsoluteOrdering = false;
					}
				}
			}
			else if (parsingOrdering) {

				if (parsingBefore) {

					String beforeName = null;

					if (parsingName || parsingOthers) {

						if (parsingOthers) {
							beforeName = Ordering.OTHERS;
							beforeNames.add(beforeName);
							parsingOthers = false;
						}
						else {

							if (content != null) {
								beforeName = content.toString().trim();
								beforeNames.add(beforeName);
							}

							parsingName = false;
						}
//                      System.err.println("endElement: " + localName + ": beforeName = " + beforeName);
					}
					else {

						if (localName.equals(BEFORE)) {
							parsingBefore = false;

							if (content != null) {

								// TODO add {0} parameterization to warn
								logger.warn("Stray content found when parsing FacesConfig named " + facesConfigName +
									". -> Ordering -> before -> content found: " + content +
									" ... probably belongs inside of a 'name' tag.");
								logger.warn("Assuming '" + content + "' is a name ...");
								beforeName = content.toString().trim();
								beforeNames.add(beforeName);
							}
						}
					}
				}

				if (parsingAfter) {

					String afterName = null;

					if (parsingName || parsingOthers) {

						if (parsingOthers) {
							afterName = Ordering.OTHERS;
							afterNames.add(afterName);
							parsingOthers = false;
						}
						else {

							if (content != null) {
								afterName = content.toString().trim();
								afterNames.add(afterName);
							}

							parsingName = false;
						}
//                      System.err.println("endElement: " + localName + ": afterName = " + afterName);
					}
					else {

						if (localName.equals(AFTER)) {
							parsingAfter = false;

							if (content != null) {

								// TODO add {0} parameterization to warn
								logger.warn("Stray content found when parsing FacesConfig named " + facesConfigName +
									". -> Ordering -> after -> content found: " + content +
									" ... probably belongs inside of a 'name' tag.");
								logger.warn("Assuming '" + content + "' is a name ...");
								afterName = content.toString().trim();
								afterNames.add(afterName);
							}
						}
					}
				}

				if (localName.equals(ORDERING)) {
					parsingOrdering = false;
				}
			}
			else {

				if (localName.equals(FACES_CONFIG)) {
					parsingFacesConfig = false;

					// TODO the parse method expects this exception ... does this help so late at this point?
					throw new SAXParseCompleteException();
				}

				super.endElement(uri, localName, elementName);
			}
		}

		content = null;
	}

	@Override
	public FacesConfigDescriptor parse(InputStream inputStream, String path) throws IOException {

		try {

			// see ParseTask in ConfigManager
			if (path.contains("/WEB-INF/faces-config.xml")) {
				isWebInfFacesConfig = true;
			}
			else {
				isWebInfFacesConfig = false;
			}

			try {
				saxParser.parse(inputStream, this);
			}
			catch (SAXParseCompleteException e) {
				// ignore -- this indicates cessation of processing when the facesConfigName is discovered. TODO see if
				// TODO is there a performance benefit here when bailing out early after name and ordering have been
				// discovered?
			}

			// Populate the ordering with routes gathered, if any.
			if (ordering != null) {

				EnumMap<Ordering.Path, String[]> routes = ordering.getRoutes();
				EnumMap<Ordering.Path, String[]> routesToSet = new EnumMap<Ordering.Path, String[]>(
						Ordering.Path.class);

				if (beforeNames.size() > 0) {
					String[] befores = beforeNames.toArray(new String[beforeNames.size()]);

					if (beforeNames.size() > 1) {
						Arrays.sort(befores);
					}

					routesToSet.put(Ordering.Path.BEFORE, befores);
				}
				else {
					routesToSet.put(Ordering.Path.BEFORE, routes.get(Ordering.Path.BEFORE));
				}

				if (afterNames.size() > 0) {
					String[] afters = afterNames.toArray(new String[afterNames.size()]);

					if (afterNames.size() > 1) {
						Arrays.sort(afters);
					}

					routesToSet.put(Ordering.Path.AFTER, afters);
				}
				else {
					routesToSet.put(Ordering.Path.AFTER, routes.get(Ordering.Path.AFTER));
				}

				ordering.setRoutes(routesToSet);
			}

			if ((absoluteOrdering != null) && (absoluteOrdering.size() == 0)) {
				absoluteOrdering = null;
			}

			FacesConfigDescriptor facesConfigDescriptor = new FacesConfigDescriptorImpl(facesConfigName, path,
					isWebInfFacesConfig, absoluteOrdering, ordering);

			this.facesConfigName = null;
			this.absoluteOrdering = null;
			this.ordering = null;
			this.afterNames = null;
			this.beforeNames = null;
			saxParser.reset();

			return facesConfigDescriptor;
		}
		catch (SAXException e) {
			logger.error(e);
			throw new IOException(e.getMessage());
		}

	}

	public FacesConfigDescriptor parse(InputStream inputStream, URL url) throws IOException {
		String path = url.toExternalForm();

		return parse(inputStream, path);
	}

	@Override
	public void startElement(String uri, String localName, String elementName, Attributes attributes)
		throws SAXException {

		content = new StringBuilder();

		if (localName.equals(FACES_CONFIG)) {
			parsingFacesConfig = true;

			if (isWebInfFacesConfig()) {
				this.absoluteOrdering = new ArrayList<String>();
				this.ordering = null;
				this.afterNames = null;
				this.beforeNames = null;
			}
			else {
				this.absoluteOrdering = null;
				this.ordering = new OrderingImpl();
				this.afterNames = new ArrayList<String>();
				this.beforeNames = new ArrayList<String>();
			}
		}
		else if (localName.equals("name")) {
			parsingName = true;
		}
		else if (localName.equals(ABSOLUTE_ORDERING)) {

			if (isWebInfFacesConfig()) {
				parsingAbsoluteOrdering = true;
			}
			else {
				logger.warn("endElement: found " + localName + " section in " + uri.toString() +
					"\nTrying to ignore this section ...");
			}
		}
		else if (localName.equals(ORDERING)) {

			if (isWebInfFacesConfig()) {
				logger.warn("endElement: found " + localName + " section in " + uri.toString() +
					"\nTrying to ignore this section ...");
			}
			else {
				parsingOrdering = true;
			}
		}
		else if (localName.equals(BEFORE)) {
			parsingBefore = true;
		}
		else if (localName.equals(AFTER)) {
			parsingAfter = true;
		}

		// TODO startsWith? really?
		else if (localName.startsWith(OTHERS)) {
			parsingOthers = true;
		}
		else {
			super.startElement(uri, localName, elementName, attributes);
		}
	}

	public boolean isWebInfFacesConfig() {
		return isWebInfFacesConfig;
	}
}
