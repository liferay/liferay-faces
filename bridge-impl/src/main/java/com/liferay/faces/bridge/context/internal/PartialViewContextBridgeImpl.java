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
package com.liferay.faces.bridge.context.internal;

import java.io.IOException;
import java.util.List;

import javax.faces.context.PartialResponseWriter;
import javax.faces.context.PartialViewContext;
import javax.faces.context.PartialViewContextWrapper;

import com.liferay.faces.bridge.client.internal.BridgeScriptUtil;
import com.liferay.faces.util.client.Script;
import com.liferay.faces.util.context.FacesRequestContext;
import com.liferay.faces.util.context.PartialResponseWriterWrapper;


/**
 * This class is a wrapper around the {@link PartialViewContext}. Its purpose is to wrap the {@link
 * PartialResponseWriter} with a {@link PartialResponseWriterAlloyImpl} which writes {@link Script}s from {@link
 * FacesRequestContext} to the &lt;eval&gt; section of the partial response.
 *
 * @author  Neil Griffin
 */
public class PartialViewContextBridgeImpl extends PartialViewContextWrapper {

	// Private Data Members
	private PartialResponseWriter partialResponseWriter;
	private PartialViewContext wrappedPartialViewContext;

	public PartialViewContextBridgeImpl(PartialViewContext partialViewContext) {
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
			partialResponseWriter = new PartialResponseWriterBridgeImpl(super.getPartialResponseWriter());
		}

		return partialResponseWriter;
	}

	@Override
	public PartialViewContext getWrapped() {
		return wrappedPartialViewContext;
	}

	/**
	 * This class serves as a wrapper around the {@link PartialResponseWriter} that will encode JavaScript within an
	 * <eval>...</eval> section just before the end of the partial-response document.
	 *
	 * @author  Kyle Stiemann
	 */
	protected class PartialResponseWriterBridgeImpl extends PartialResponseWriterWrapper {

		// Private Data Members
		private boolean wroteEval;

		public PartialResponseWriterBridgeImpl(PartialResponseWriter partialResponseWriter) {
			super(partialResponseWriter);
		}

		@Override
		public void endDocument() throws IOException {

			if (!wroteEval) {

				FacesRequestContext facesRequestContext = FacesRequestContext.getCurrentInstance();
				List<Script> scripts = facesRequestContext.getScripts();

				if (!scripts.isEmpty()) {

					super.startEval();
					BridgeScriptUtil.writeScripts(partialResponseWriter, scripts);
					super.endEval();
				}
			}

			super.endDocument();
		}

		@Override
		public void endEval() throws IOException {

			FacesRequestContext facesRequestContext = FacesRequestContext.getCurrentInstance();
			List<Script> scripts = facesRequestContext.getScripts();

			if (!scripts.isEmpty()) {
				BridgeScriptUtil.writeScripts(partialResponseWriter, scripts);
			}

			super.endEval();
			wroteEval = true;
		}
	}
}
