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
package com.liferay.faces.util.context;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.faces.context.PartialResponseWriter;
import javax.faces.context.PartialViewContext;
import javax.faces.context.PartialViewContextWrapper;

import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;


/**
 * <p>This class is a wrapper around the {@link PartialViewContext}. Its purpose is to provide a way to provide a way to
 * write the value of {@link ExtFacesContext#getJavaScriptMap()} to an <eval>...</eval> section in the partial-response
 * document in order to execute JavaScript on the client.
 *
 * @author  Neil Griffin
 */
public class PartialViewContextScriptImpl extends PartialViewContextWrapper {

	// Private Constants
	private static final boolean ICEFACES_DETECTED = ProductMap.getInstance().get(ProductConstants.ICEFACES)
		.isDetected();
	private static final String SCRIPT_TAG_BEGIN_REGEX = "<script[^>]*>";

	// Private Data Members
	private PartialResponseWriter partialResponseWriter;
	private PartialViewContext wrappedPartialViewContext;

	public PartialViewContextScriptImpl(PartialViewContext partialViewContext) {
		this.wrappedPartialViewContext = partialViewContext;
	}

	/**
	 * This method is missing from the {@link PartialViewContextWrapper} class so it must be implemented here.
	 */
	@Override
	public void setPartialRequest(boolean isPartialRequest) {
		wrappedPartialViewContext.setPartialRequest(isPartialRequest);
	}

	@Override
	public PartialResponseWriter getPartialResponseWriter() {

		if (ICEFACES_DETECTED) {
			return super.getPartialResponseWriter();
		}
		else {

			if (partialResponseWriter == null) {
				partialResponseWriter = new PartialResponseWriterScriptImpl(super.getPartialResponseWriter());
			}

			return partialResponseWriter;
		}
	}

	@Override
	public PartialViewContext getWrapped() {
		return wrappedPartialViewContext;
	}

	/**
	 * This class serves as a wrapper around the {@link PartialResponseWriter} that will encode the JavaScript cleanup
	 * fragments within an <eval>...</eval> section just before the end of the partial-response document.
	 *
	 * @author  Neil Griffin
	 */
	protected class PartialResponseWriterScriptImpl extends PartialResponseWriterWrapper {

		// Private Data Members
		private boolean wroteEval;

		public PartialResponseWriterScriptImpl(PartialResponseWriter partialResponseWriter) {
			super(partialResponseWriter);
		}

		@Override
		public void endDocument() throws IOException {

			if (!wroteEval) {

				ExtFacesContext extFacesContext = ExtFacesContext.getInstance();
				Map<String, String> javaScriptMap = extFacesContext.getJavaScriptMap();

				if ((javaScriptMap != null) && (javaScriptMap.size() > 0)) {
					super.startEval();
					writeJavaScriptMap(javaScriptMap);
					super.endEval();
				}
			}

			super.endDocument();
		}

		@Override
		public void endEval() throws IOException {

			ExtFacesContext extFacesContext = ExtFacesContext.getInstance();
			Map<String, String> javaScriptMap = extFacesContext.getJavaScriptMap();

			if ((javaScriptMap != null) && (javaScriptMap.size() > 0)) {
				writeJavaScriptMap(javaScriptMap);
			}

			super.endEval();
			wroteEval = true;
		}

		protected void writeJavaScriptMap(Map<String, String> javaScriptMap) throws IOException {

			Set<Entry<String, String>> entrySet = javaScriptMap.entrySet();

			for (Map.Entry<String, String> mapEntry : entrySet) {
				String mapEntryValue = mapEntry.getValue();

				if (mapEntryValue != null) {
					mapEntryValue = mapEntryValue.replaceAll(SCRIPT_TAG_BEGIN_REGEX, StringPool.BLANK);
					mapEntryValue = mapEntryValue.replaceAll(StringPool.SCRIPT_TAG_END, StringPool.BLANK);
					super.write(mapEntryValue);
				}
			}
		}
	}
}
