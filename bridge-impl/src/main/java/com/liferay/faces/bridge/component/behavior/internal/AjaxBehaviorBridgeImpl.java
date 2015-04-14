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
package com.liferay.faces.bridge.component.behavior.internal;

import java.util.ArrayList;
import java.util.Collection;

import javax.faces.component.behavior.AjaxBehavior;
import javax.faces.component.behavior.ClientBehaviorContext;
import javax.faces.context.FacesContext;
import javax.faces.render.ClientBehaviorRenderer;


/**
 * @author  Neil Griffin
 */
public class AjaxBehaviorBridgeImpl extends AjaxBehaviorWrapper {

	// Private Data Members
	private Collection<String> executeIds;
	private String namingContainerId;
	private int namingContainerIdLength;
	private Collection<String> renderIds;
	private AjaxBehavior wrappedAjaxBehavior;

	public AjaxBehaviorBridgeImpl(AjaxBehavior ajaxBehavior, String namingContainerId) {

		this.wrappedAjaxBehavior = ajaxBehavior;
		this.namingContainerId = namingContainerId;

		if (namingContainerId != null) {
			namingContainerIdLength = namingContainerId.length();
		}
	}

	@Override
	public Collection<String> getExecute() {

		if (executeIds == null) {

			int namingContainerIdLength = namingContainerId.length();
			Collection<String> wrappedExecuteIds = getWrapped().getExecute();

			if (wrappedExecuteIds != null) {

				this.executeIds = new ArrayList<String>(wrappedExecuteIds.size());

				for (String executeId : wrappedExecuteIds) {

					if (executeId.startsWith(namingContainerId)) {
						executeId = executeId.substring(namingContainerIdLength);
					}

					this.executeIds.add(executeId);
				}
			}
		}

		return executeIds;
	}

	@Override
	public Collection<String> getRender() {

		if (renderIds == null) {

			Collection<String> wrappedRenderIds = getWrapped().getRender();

			if (wrappedRenderIds != null) {

				this.renderIds = new ArrayList<String>(wrappedRenderIds.size());

				for (String renderId : wrappedRenderIds) {

					if (renderId.startsWith(namingContainerId)) {
						renderId = renderId.substring(namingContainerIdLength);
					}

					this.renderIds.add(renderId);
				}
			}
		}

		return renderIds;
	}

	@Override
	public String getScript(ClientBehaviorContext behaviorContext) {

		if (behaviorContext == null) {
			throw new NullPointerException();
		}

		FacesContext facesContext = behaviorContext.getFacesContext();
		ClientBehaviorRenderer clientBehaviorRenderer = getRenderer(facesContext);

		String ajaxBehaviorScript = null;

		if (clientBehaviorRenderer != null) {
			ajaxBehaviorScript = clientBehaviorRenderer.getScript(behaviorContext, this);
		}

		return ajaxBehaviorScript;
	}

	@Override
	public AjaxBehavior getWrapped() {
		return wrappedAjaxBehavior;
	}
}
