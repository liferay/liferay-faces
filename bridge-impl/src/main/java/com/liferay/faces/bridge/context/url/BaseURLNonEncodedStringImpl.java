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
package com.liferay.faces.bridge.context.url;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.portlet.BaseURL;
import javax.portlet.PortletSecurityException;
import javax.portlet.faces.Bridge;

import com.liferay.faces.bridge.BridgeConstants;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * This class represents a simple "non-encoded" {@link BaseURL}, meaning an implementation that simply wraps a String
 * based URL without providing any encoding. The only methods that are meant to be called is {@link
 * BaseURLNonEncodedStringImpl#toString()} and {@link BaseURLNonEncodedStringImpl#write(Writer, boolean)}. All other
 * methods throw an {@link UnsupportedOperationException}.
 *
 * @author  Neil Griffin
 */
public class BaseURLNonEncodedStringImpl implements BaseURL {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(BaseURLNonEncodedStringImpl.class);

	// Private Data Members
	private String url;
	private Map<String, String[]> parameterMap;
	private String query;
	private String main;
	private String toStringValue;

	public BaseURLNonEncodedStringImpl(String urlWithParameters) {
		this.url = urlWithParameters;
		this.parameterMap = new HashMap<String, String[]>();
	}

	public BaseURLNonEncodedStringImpl(String url, Map<String, String[]> parameterMap) {
		this.url = url;
		this.parameterMap = parameterMap;
	}

	public void addProperty(String key, String value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {

		if (toStringValue == null) {
			StringBuilder buf = new StringBuilder();
			buf.append(getMain());

			String queryString = getQuery();

			if (queryString.length() > 0) {
				buf.append(BridgeConstants.CHAR_QUESTION_MARK);
				buf.append(queryString);
			}

			toStringValue = buf.toString();
		}

		return toStringValue;
	}

	public void write(Writer out) throws IOException {
		throw new UnsupportedOperationException();
	}

	public void write(Writer out, boolean escapeXML) throws IOException {

		// Note: Ignore the escapeXML parameter because this class is simply supposed to return the original URL string.
		out.write(url);
	}

	/**
	 * Returns the main part of the URL, which is everything up until the question mark.
	 */
	protected String getMain() {

		if (main == null) {
			int queryPos = url.indexOf(BridgeConstants.CHAR_QUESTION_MARK);

			if (queryPos >= 0) {
				main = url.substring(0, queryPos);
			}
			else {
				main = url;
			}
		}

		return main;
	}

	public void setParameter(String name, String value) {
		throw new UnsupportedOperationException();
	}

	public void setParameter(String name, String[] values) {
		throw new UnsupportedOperationException();
	}

	public Map<String, String[]> getParameterMap() {
		throw new UnsupportedOperationException();
	}

	public void setParameters(Map<String, String[]> parameters) {
		throw new UnsupportedOperationException();
	}

	public void setProperty(String key, String value) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns the query-string part of the URL (without a leading question mark).
	 */
	protected String getQuery() {

		if (query == null) {

			StringBuilder buf = new StringBuilder();

			// Get the original query-string from the URL.
			String originalQuery = BridgeConstants.EMPTY;
			boolean firstParam = true;
			int queryPos = url.indexOf(BridgeConstants.CHAR_QUESTION_MARK);

			if (queryPos >= 0) {
				originalQuery = url.substring(queryPos + 1);
			}

			// Keep track of all the parameters that are appended to the return value.
			Set<String> processedParameterNames = new HashSet<String>(parameterMap.size());

			// The TCK expects query parameters to appear in exactly the same order as they do in the query-string of
			// the original URL. For this reason, need to iterate over the parameters found in the original
			// query-string.
			String[] queryParameters = originalQuery.split("[&]");

			// For each parameter found in the original query-string:
			for (String queryParameter : queryParameters) {

				if ((queryParameter != null) && (queryParameter.length() > 0)) {

					// Parse the name and value from the name=value pair.
					String[] nameValueArray = queryParameter.split("[=]");

					String name = null;
					String[] values = null;

					if (nameValueArray.length == 1) {
						name = nameValueArray[0];
						values = new String[] { BridgeConstants.EMPTY };
					}
					else if (nameValueArray.length == 2) {
						name = nameValueArray[0];

						// If the parameter name is present in the parameter map, then that means it should be appended
						// to the return value. Otherwise, it should not be appended, because absence from the parameter
						// map means that it was deliberately removed.
						values = parameterMap.get(name);
					}

					if ((name != null) && (values != null) && (values.length > 0)) {

						if (firstParam) {
							firstParam = false;
						}
						else {
							buf.append(BridgeConstants.CHAR_AMPERSAND);
						}

						buf.append(name);
						buf.append(BridgeConstants.CHAR_EQUALS);
						buf.append(values[0]);
						processedParameterNames.add(name);
					}

					// Otherwise, log an error.
					else {

						// Note that "javax.portlet.faces.BackLink" is sometimes deliberately removed and therefore is
						// not an error.
						if (!Bridge.BACK_LINK.equals(name)) {
							logger.error("Invalid name=value pair=[{0}] in URL=[{1}]", queryParameter, url);
						}
					}
				}
			}

			// Now iterate through the entire parameter map to see and append any additional parameters to the return
			// value.
			Set<Entry<String, String[]>> mapEntries = parameterMap.entrySet();

			for (Map.Entry<String, String[]> mapEntry : mapEntries) {

				String name = mapEntry.getKey();

				if (!processedParameterNames.contains(name)) {
					String[] values = mapEntry.getValue();

					if ((values != null) && (values.length > 0)) {

						if (firstParam) {
							firstParam = false;
						}
						else {
							buf.append(BridgeConstants.CHAR_AMPERSAND);
						}

						buf.append(name);
						buf.append(BridgeConstants.CHAR_EQUALS);
						buf.append(values[0]);
					}
				}
			}

			query = buf.toString();
		}

		return query;
	}

	public void setSecure(boolean secure) throws PortletSecurityException {
		throw new UnsupportedOperationException();
	}

}
