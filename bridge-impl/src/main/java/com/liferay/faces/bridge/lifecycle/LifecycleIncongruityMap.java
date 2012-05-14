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
package com.liferay.faces.bridge.lifecycle;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;


/**
 * @author  Neil Griffin
 */
public class LifecycleIncongruityMap {

	// Private Constants
	private static final String REQUEST_CHARACTER_ENCODING = "_pfbRequestCharacterEncoding";
	private static final String REQUEST_CONTENT_LENGTH = "_pfbRequestContentLength";
	private static final String REQUEST_CONTENT_TYPE = "_pfbRequestContentType";
	private static final String RESPONSE_TO_BE_RESET = "_pfbResponseToBeReset";
	private static final String RESPONSE_BUFFER_SIZE = "_pfbResponseBufferSize";
	private static final String RESPONSE_CHARACTER_ENCODING = "_pfbResponseCharacterEncoding";
	private static final String RESPONSE_CONTENT_LENGTH = "_pfbResponseContentLength";
	private static final String RESPONSE_COMMITTED = "_pfbResponseCommitted";
	private static final String RESPONSE_CONTENT_TYPE = "_pfbResponseContentType";
	private static final String RESPONSE_OUTPUT_STREAM = "_pfbResponseOutputStream";
	private static final String RESPONSE_OUTPUT_WRITER = "_pfbResponseOutputWriter";
	private static final String RESPONSE_STATUS = "_pfbResponseStatus";

	// Private Data Members
	private boolean managementEnabled;
	private Map<String, Object> requestMap;

	public LifecycleIncongruityMap(Map<String, Object> requestMap, boolean managementEnabled) {
		this.requestMap = requestMap;
		this.managementEnabled = managementEnabled;
	}

	public Object get(String key) {
		return requestMap.get(key);
	}

	public void put(String key, Object value) {

		if (managementEnabled) {
			requestMap.put(key, value);
		}
	}

	public void putRequestCharacterEncoding(String requestCharacterEncoding) {

		if (managementEnabled) {
			requestMap.put(REQUEST_CHARACTER_ENCODING, requestCharacterEncoding);
		}
	}

	public void putRequestContentLength(int requestContentLength) {

		if (managementEnabled) {

			requestMap.put(REQUEST_CONTENT_LENGTH, Integer.valueOf(requestContentLength));
		}
	}

	public void putRequestContentType(String requestContentType) {

		if (managementEnabled) {
			requestMap.put(REQUEST_CONTENT_TYPE, requestContentType);
		}
	}

	public void putResponseBufferSize(int responseBufferSize) {

		if (managementEnabled) {
			requestMap.put(RESPONSE_BUFFER_SIZE, Integer.valueOf(responseBufferSize));
		}
	}

	public void putResponseCharacterEncoding(String responseCharacterEncoding) {

		if (managementEnabled) {
			requestMap.put(RESPONSE_CHARACTER_ENCODING, responseCharacterEncoding);
		}
	}

	public void putResponseCommitted(boolean responseCommitted) {

		if (managementEnabled) {
			requestMap.put(RESPONSE_COMMITTED, Boolean.valueOf(responseCommitted));
		}
	}

	public void putResponseContentLength(int responseContentLength) {

		if (managementEnabled) {
			requestMap.put(RESPONSE_CONTENT_LENGTH, Integer.valueOf(responseContentLength));
		}
	}

	public void putResponseContentType(String responseContentType) {

		if (managementEnabled) {
			requestMap.put(RESPONSE_CONTENT_TYPE, responseContentType);
		}
	}

	public void putResponseOutputStream(DelayedResponseOutputStream delayedResponseOutputStream) {

		if (managementEnabled) {
			requestMap.put(RESPONSE_OUTPUT_STREAM, delayedResponseOutputStream);
		}
	}

	public void putResponseOutputWriter(DelayedResponseOutputWriter delayedResponseOutputWriter) {

		if (managementEnabled) {
			requestMap.put(RESPONSE_OUTPUT_WRITER, delayedResponseOutputWriter);
		}
	}

	public void putResponseStatus(int responseStatus) {

		if (managementEnabled) {
			requestMap.put(RESPONSE_STATUS, Integer.valueOf(responseStatus));
		}
	}

	public void putResponseToBeReset(boolean responseToBeReset) {

		if (managementEnabled) {
			requestMap.put(RESPONSE_TO_BE_RESET, Boolean.valueOf(responseToBeReset));
		}
	}

	public boolean isResponseCommitted() {
		return getValueAsBoolean(RESPONSE_COMMITTED, true);
	}

	public String getRequestCharacterEncoding() {
		return (String) requestMap.get(REQUEST_CHARACTER_ENCODING);
	}

	public int getRequestContentLength() {
		return getValueAsInt(REQUEST_CONTENT_LENGTH, -1);
	}

	public String getRequestContentType() {
		return (String) requestMap.get(REQUEST_CONTENT_TYPE);
	}

	public int getResponseBufferSize() {
		return getValueAsInt(RESPONSE_BUFFER_SIZE, 0);
	}

	public String getResponseCharacterEncoding() {
		return (String) requestMap.get(RESPONSE_CHARACTER_ENCODING);
	}

	public int getResponseContentLength() {
		return getValueAsInt(RESPONSE_CONTENT_LENGTH, -1);
	}

	public String getResponseContentType() {
		return (String) requestMap.get(RESPONSE_CONTENT_TYPE);
	}

	public DelayedResponseOutputStream getResponseOutputStream() {

		DelayedResponseOutputStream responseOutputstream = (DelayedResponseOutputStream) requestMap.get(
				RESPONSE_OUTPUT_STREAM);

		if (responseOutputstream == null) {
			responseOutputstream = new DelayedResponseOutputStream();
		}

		return responseOutputstream;
	}

	public DelayedResponseOutputWriter getResponseOutputWriter() {

		DelayedResponseOutputWriter responseOutputWriter = (DelayedResponseOutputWriter) requestMap.get(
				RESPONSE_OUTPUT_WRITER);

		if (responseOutputWriter == null) {
			responseOutputWriter = new DelayedResponseOutputWriter();
		}

		return responseOutputWriter;
	}

	public int getResponseStatus() {
		return getValueAsInt(RESPONSE_STATUS, HttpServletResponse.SC_OK);
	}

	public boolean isResponseToBeReset() {
		return getValueAsBoolean(RESPONSE_TO_BE_RESET, false);
	}

	protected boolean getValueAsBoolean(String key, boolean defaultValue) {

		Boolean value = (Boolean) requestMap.get(key);

		if (value == null) {
			value = Boolean.valueOf(defaultValue);
		}

		return value.booleanValue();
	}

	protected int getValueAsInt(String key, int defaultValue) {

		Integer value = (Integer) requestMap.get(key);

		if (value == null) {
			value = Integer.valueOf(defaultValue);
		}

		return value.intValue();
	}

}
