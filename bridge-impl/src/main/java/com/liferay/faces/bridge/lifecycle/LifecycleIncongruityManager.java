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

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.event.ActionListener;

import com.liferay.faces.bridge.logging.Logger;
import com.liferay.faces.bridge.logging.LoggerFactory;
import com.liferay.faces.bridge.scope.BridgeRequestScope;


/**
 * <p>Occasionally there is an incongruity (a mismatch) between the Portlet lifecycle and the JSF lifecycle. This class
 * is designed to compensate for these occasions as much as possible.</p>
 *
 * <p>Example: Sometimes the JSF implementation or a JSF {@link ActionListener} will call methods like {@link
 * ExternalContext#setResponseStatus(int)} during the Portlet {@link javax.portlet.PortletRequest#ACTION_PHASE}. In this
 * case, the Portlet lifecycle is working with a {@link javax.portlet.ActionResponse} but there is no corresponding
 * method. Technically the only time such a method could be called would be during the {@link
 * javax.portlet.PortletRequest#RESOURCE_PHASE} because the {@link javax.portlet.ResourceResponse} class has a
 * setReponseStatus(int) method.</p>
 *
 * <p>The approach that this class takes to solve this problem is to store attributes in the request map (request scope)
 * which, due to the {@link BridgeRequestScope} will be preserved into subsequent phases of the Portlet lifecycle.</p>
 *
 * @author  Neil Griffin
 */
public class LifecycleIncongruityManager {

	// Private Constants
	private static final String INCONGRUITY_LIST = "_pfbIncongruityList";

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(LifecycleIncongruityManager.class);

	// Private Data Members
	private List<CongruousTask> congruousTaskList;
	private LifecycleIncongruityMap lifecycleIncongruityMap;

	@SuppressWarnings("unchecked")
	public LifecycleIncongruityManager(LifecycleIncongruityMap lifecycleIncongruityMap) {
		this.congruousTaskList = (List<CongruousTask>) lifecycleIncongruityMap.get(INCONGRUITY_LIST);

		if (this.congruousTaskList == null) {
			this.congruousTaskList = new ArrayList<CongruousTask>();
		}
	}

	public void addCongruousTask(CongruousTask congruousTask) {
		congruousTaskList.add(congruousTask);
	}

	/**
	 * This method is responsible for iterating through the list of tasks that are necessary to fix any incongruities
	 * that took place between the Portlet lifecycle and the JSF lifecycle.
	 *
	 * @param   externalContext  The current ExternalContext.
	 *
	 * @throws  IOException  If an error occurs calling any methods on the specified ExternalContext.
	 */
	public void makeCongruous(ExternalContext externalContext) throws IOException {

		logger.debug("congruousTaskList.size()=[{0}]", congruousTaskList.size());

		for (CongruousTask congruousTask : congruousTaskList) {

			if (congruousTask == CongruousTask.RESPONSE_FLUSH_BUFFER) {
				logger.debug("responseFlushBuffer");
				externalContext.responseFlushBuffer();
			}
			else if (congruousTask == CongruousTask.RESPONSE_RESET) {
				logger.debug("responseReset");
				externalContext.responseReset();
			}
			else if (congruousTask == CongruousTask.SET_REQUEST_CHARACTER_ENCODING) {
				String requestCharacterEncoding = lifecycleIncongruityMap.getRequestCharacterEncoding();
				logger.debug("setRequestCharacterEncoding(\"{0}\")", requestCharacterEncoding);
				externalContext.setRequestCharacterEncoding(requestCharacterEncoding);
			}
			else if (congruousTask == CongruousTask.SET_RESPONSE_BUFFER_SIZE) {
				int responseBufferSize = lifecycleIncongruityMap.getResponseBufferSize();
				logger.debug("setResponseBufferSize(\"{0}\")", responseBufferSize);
				externalContext.setResponseBufferSize(responseBufferSize);
			}
			else if (congruousTask == CongruousTask.SET_RESPONSE_CHARACTER_ENCODING) {
				String responseCharacterEncoding = lifecycleIncongruityMap.getResponseCharacterEncoding();
				logger.debug("setResponseCharacterEncoding(\"{0}\")", responseCharacterEncoding);
				externalContext.setResponseCharacterEncoding(responseCharacterEncoding);
			}
			else if (congruousTask == CongruousTask.SET_RESPONSE_CONTENT_LENGTH) {
				int responseContentLength = lifecycleIncongruityMap.getResponseContentLength();
				logger.debug("setResponseContentLength(\"{0}\")", responseContentLength);
				externalContext.setResponseContentLength(responseContentLength);
			}
			else if (congruousTask == CongruousTask.SET_RESPONSE_CONTENT_TYPE) {
				String responseContentType = lifecycleIncongruityMap.getResponseContentType();
				logger.debug("setResponseContentType(\"{0}\")", responseContentType);
				externalContext.setResponseContentType(responseContentType);
			}
			else if (congruousTask == CongruousTask.WRITE_RESPONSE_OUTPUT_WRITER) {
				DelayedResponseOutputWriter delayedResponseOutputWriter =
					lifecycleIncongruityMap.getResponseOutputWriter();
				String delayedOutput = delayedResponseOutputWriter.toString();
				logger.debug("writing responseOutputWriter, delayedOutput=[{0}]", delayedOutput);

				if ((delayedOutput != null) && (delayedOutput.length() > 0)) {
					Writer outputWriter = externalContext.getResponseOutputWriter();
					outputWriter.write(delayedOutput);
				}

				lifecycleIncongruityMap.putResponseOutputWriter(null);
			}
			else if (congruousTask == CongruousTask.WRITE_RESPONSE_OUTPUT_STREAM) {
				DelayedResponseOutputStream delayedResponseOutputStream =
					lifecycleIncongruityMap.getResponseOutputStream();
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

				lifecycleIncongruityMap.putResponseOutputStream(null);
			}
		}
	}

}
