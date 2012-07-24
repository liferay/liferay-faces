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
package com.liferay.faces.bridge.context;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.Flash;

import com.liferay.faces.bridge.lifecycle.DelayedResponseOutputStream;
import com.liferay.faces.bridge.lifecycle.DelayedResponseOutputWriter;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public abstract class IncongruityContextCompatImpl extends IncongruityContextBaseImpl {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(IncongruityContextCompatImpl.class);

	// Private Constants
	private static final String REQUEST_CONTENT_LENGTH = "requestContentLength";
	private static final String RESPONSE_BUFFER_SIZE = "responseBufferSize";
	private static final String RESPONSE_COMMITTED = "responseCommitted";
	private static final String RESPONSE_CONTENT_LENGTH = "responseContentLength";
	private static final String RESPONSE_CONTENT_TYPE = "responseContentType";
	private static final String RESPONSE_OUTPUT_STREAM = "responseOutputStream";
	private static final String RESPONSE_OUTPUT_WRITER = "responseOutputWriter";
	private static final String RESPONSE_STATUS = "responseStatus";

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public void addResponseCookie(String name, String value, Map<String, Object> properties) {
		throw new IllegalStateException();
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public void addResponseHeader(String name, String value) {
		throw new IllegalStateException();
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public String encodeBookmarkableURL(String baseUrl, Map<String, List<String>> parameters) {
		throw new IllegalStateException();
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public String encodePartialActionURL(String url) {
		throw new IllegalStateException();
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public String encodeRedirectURL(String baseUrl, Map<String, List<String>> parameters) {
		throw new IllegalStateException();
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public void invalidateSession() {
		throw new IllegalStateException();
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public void responseFlushBuffer() throws IOException {
		getIncongruousActions().add(IncongruousAction.RESPONSE_FLUSH_BUFFER);
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public void responseReset() {
		getIncongruousActions().add(IncongruousAction.RESPONSE_RESET);
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public void responseSendError(int statusCode, String message) throws IOException {
		throw new IllegalStateException();
	}

	protected void makeCongruousJSF2(ExternalContext externalContext, IncongruousAction incongruousAction)
		throws IOException {

		if (incongruousAction == IncongruousAction.RESPONSE_FLUSH_BUFFER) {
			logger.debug("responseFlushBuffer");
			externalContext.responseFlushBuffer();
		}
		else if (incongruousAction == IncongruousAction.RESPONSE_RESET) {
			logger.debug("responseReset");
			externalContext.responseReset();
		}
		else if (incongruousAction == IncongruousAction.SET_RESPONSE_BUFFER_SIZE) {
			int responseBufferSize = getResponseBufferSize();
			logger.debug("setResponseBufferSize(\"{0}\")", responseBufferSize);
			externalContext.setResponseBufferSize(responseBufferSize);
		}
		else if (incongruousAction == IncongruousAction.SET_RESPONSE_CONTENT_LENGTH) {
			int responseContentLength = getResponseContentLength();
			logger.debug("setResponseContentLength(\"{0}\")", responseContentLength);
			externalContext.setResponseContentLength(responseContentLength);
		}
		else if (incongruousAction == IncongruousAction.SET_RESPONSE_CONTENT_TYPE) {
			String responseContentType = getResponseContentType();
			logger.debug("setResponseContentType(\"{0}\")", responseContentType);
			externalContext.setResponseContentType(responseContentType);
		}
		else if (incongruousAction == IncongruousAction.WRITE_RESPONSE_OUTPUT_WRITER) {
			DelayedResponseOutputWriter delayedResponseOutputWriter = (DelayedResponseOutputWriter)
				getResponseOutputWriter();
			String delayedOutput = delayedResponseOutputWriter.toString();
			logger.debug("writing responseOutputWriter, delayedOutput=[{0}]", delayedOutput);

			if ((delayedOutput != null) && (delayedOutput.length() > 0)) {
				Writer outputWriter = externalContext.getResponseOutputWriter();
				outputWriter.write(delayedOutput);
			}
		}
		else if (incongruousAction == IncongruousAction.WRITE_RESPONSE_OUTPUT_STREAM) {
			DelayedResponseOutputStream delayedResponseOutputStream = (DelayedResponseOutputStream)
				getResponseOutputStream();
			byte[] delayedOutputByteArray = delayedResponseOutputStream.toByteArray();
			int length = 0;

			if (delayedOutputByteArray != null) {
				length = delayedOutputByteArray.length;
			}

			logger.debug("writing responseOutputStream, delayedOutputByteArray.length=[{0}]", length);

			if ((delayedOutputByteArray != null) && (length > 0)) {
				OutputStream outputStream = externalContext.getResponseOutputStream();
				outputStream.write(delayedOutputByteArray);
			}
		}

	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public String getContextName() {
		throw new IllegalStateException();
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public boolean isResponseCommitted() {
		Boolean responseCommitted = (Boolean) attributeMap.get(RESPONSE_COMMITTED);

		if (responseCommitted == null) {
			return Boolean.FALSE;
		}
		else {
			return responseCommitted;
		}
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public Flash getFlash() {
		throw new IllegalStateException();
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public String getMimeType(String file) {
		throw new IllegalStateException();
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public String getRealPath(String path) {
		throw new IllegalStateException();
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public int getRequestContentLength() {
		return (Integer) attributeMap.get(REQUEST_CONTENT_LENGTH);
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public void setRequestContentLength(int length) {
		attributeMap.put(REQUEST_CONTENT_LENGTH, length);
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public String getRequestScheme() {
		throw new IllegalStateException();
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public String getRequestServerName() {
		throw new IllegalStateException();
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public int getRequestServerPort() {
		throw new IllegalStateException();
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public int getResponseBufferSize() {
		return (Integer) attributeMap.get(RESPONSE_BUFFER_SIZE);
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public void setResponseBufferSize(int size) {
		attributeMap.put(RESPONSE_BUFFER_SIZE, size);
		getIncongruousActions().add(IncongruousAction.SET_RESPONSE_BUFFER_SIZE);
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public void setResponseCommitted(boolean committed) {
		attributeMap.put(RESPONSE_COMMITTED, committed);
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public int getResponseContentLength() {
		return (Integer) attributeMap.get(RESPONSE_CONTENT_LENGTH);
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public void setResponseContentLength(int length) {
		attributeMap.put(RESPONSE_CONTENT_LENGTH, length);
		getIncongruousActions().add(IncongruousAction.SET_RESPONSE_CONTENT_LENGTH);
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public String getResponseContentType() {
		return (String) attributeMap.get(RESPONSE_CONTENT_TYPE);
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public void setResponseContentType(String contentType) {
		attributeMap.put(RESPONSE_CONTENT_TYPE, contentType);
		getIncongruousActions().add(IncongruousAction.SET_RESPONSE_CONTENT_TYPE);
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public void setResponseHeader(String name, String value) {
		throw new IllegalStateException();
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public OutputStream getResponseOutputStream() throws IOException {
		OutputStream responseOutputstream = (DelayedResponseOutputStream) attributeMap.get(RESPONSE_OUTPUT_STREAM);

		if (responseOutputstream == null) {
			responseOutputstream = new DelayedResponseOutputStream();
			attributeMap.put(RESPONSE_OUTPUT_STREAM, responseOutputstream);
		}

		return responseOutputstream;
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public Writer getResponseOutputWriter() throws IOException {
		Writer responseOutputWriter = (DelayedResponseOutputWriter) attributeMap.get(RESPONSE_OUTPUT_WRITER);

		if (responseOutputWriter == null) {
			responseOutputWriter = new DelayedResponseOutputWriter();
			attributeMap.put(RESPONSE_OUTPUT_WRITER, responseOutputWriter);
		}

		return responseOutputWriter;
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public void setResponseStatus(int statusCode) {
		attributeMap.put(RESPONSE_STATUS, statusCode);
		getIncongruousActions().add(IncongruousAction.SET_RESPONSE_STATUS);
	}
}
