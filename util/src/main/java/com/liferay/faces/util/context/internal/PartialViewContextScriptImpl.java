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
package com.liferay.faces.util.context.internal;

import java.io.IOException;

import javax.faces.context.PartialResponseWriter;
import javax.faces.context.PartialViewContext;
import javax.faces.context.PartialViewContextWrapper;

import com.liferay.faces.util.client.ClientScript;
import com.liferay.faces.util.client.ClientScriptFactory;
import com.liferay.faces.util.factory.FactoryExtensionFinder;


/**
 * <p>This class is a wrapper around the {@link PartialViewContext}. Its purpose is to provide a way to provide a way to
 * write the value of {@link ExtFacesContext#getJavaScriptMap()} to an <eval>...</eval> section in the partial-response
 * document in order to execute JavaScript on the client.
 *
 * @author  Neil Griffin
 */
public class PartialViewContextScriptImpl extends PartialViewContextWrapper {

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

		if (partialResponseWriter == null) {
			partialResponseWriter = new PartialResponseWriterScriptImpl(super.getPartialResponseWriter());
		}

		return partialResponseWriter;
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

				ClientScriptFactory clientScriptFactory = (ClientScriptFactory) FactoryExtensionFinder.getFactory(
						ClientScriptFactory.class);
				ClientScript clientScript = clientScriptFactory.getClientScript();
				String clientScriptText = clientScript.toString();

				if (clientScriptText.length() > 0) {

					super.startEval();
					super.write(clientScriptText);
					clientScript.clear();
					super.endEval();
					wroteEval = true;
				}
			}

			super.endDocument();
		}

		@Override
		public void endEval() throws IOException {

			if (!wroteEval) {

				ClientScriptFactory clientScriptFactory = (ClientScriptFactory) FactoryExtensionFinder.getFactory(
						ClientScriptFactory.class);
				ClientScript clientScript = clientScriptFactory.getClientScript();
				String clientScriptText = clientScript.toString();

				if (clientScriptText.length() > 0) {
					super.write(clientScriptText);
					clientScript.clear();
				}

			}

			super.endEval();
			wroteEval = true;
		}
	}
}
