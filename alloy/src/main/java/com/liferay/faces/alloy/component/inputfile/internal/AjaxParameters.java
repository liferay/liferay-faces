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
package com.liferay.faces.alloy.component.inputfile.internal;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.faces.component.behavior.AjaxBehavior;
import javax.faces.component.behavior.ClientBehavior;

import com.liferay.faces.alloy.component.inputfile.InputFile;


/**
 * @author  Neil Griffin
 */
public class AjaxParameters {

	// Private Data Members
	private String execute;
	private String render;

	public AjaxParameters(InputFile inputFile, String clientId, String formClientId) {

		// Default value of execute is "@this" which maps to name and id of the rendered input element.
		this.execute = clientId.concat(" ").concat(clientId);

		// Default value of render is "@none" which maps to an empty string.
		this.render = "";

		// For each Ajax behavior associated with the specified component:
		Map<String, List<ClientBehavior>> clientBehaviorMap = inputFile.getClientBehaviors();
		List<ClientBehavior> clientBehaviors = clientBehaviorMap.get(inputFile.getDefaultEventName());

		for (ClientBehavior clientBehavior : clientBehaviors) {

			if (clientBehavior instanceof AjaxBehavior) {

				// Interpret the value of the f:ajax "execute" attribute.
				AjaxBehavior ajaxBehavior = (AjaxBehavior) clientBehavior;
				Collection<String> executeIds = ajaxBehavior.getExecute();

				if ((executeIds != null) && (executeIds.size() > 0)) {

					boolean foundAllKeyword = false;
					boolean foundNoneKeyword = false;
					StringBuilder buf = new StringBuilder(clientId);

					for (String executeId : executeIds) {

						if ("@all".equals(executeId)) {
							foundAllKeyword = true;

							break;
						}
						else if ("@none".equals(executeId)) {
							foundNoneKeyword = true;
							this.execute = "";

							break;
						}
						else if (executeId.length() > 0) {

							buf.append(" ");
							buf.append(executeId);
						}
					}

					if (!foundNoneKeyword) {

						if (foundAllKeyword) {
							this.execute = "@all";
						}
						else {
							this.execute = buf.toString();
							this.execute = this.execute.replace("@form", formClientId);
							this.execute = this.execute.replace("@this", clientId);

							boolean foundClientId = false;
							String[] executeIdArray = this.execute.split(" ");

							for (String executeId : executeIdArray) {

								if (clientId.equals(executeId)) {
									foundClientId = true;

									break;
								}
							}

							if (!foundClientId) {
								this.execute = clientId.concat(" ").concat(this.execute);
							}
						}
					}
				}

				// Interpret the value of the f:ajax "render" attribute.
				Collection<String> renderIds = ajaxBehavior.getRender();

				if ((renderIds != null) && (renderIds.size() > 0)) {

					boolean first = true;
					boolean foundAllKeyword = false;
					boolean foundNoneKeyword = false;
					StringBuilder buf = new StringBuilder();

					for (String renderId : renderIds) {

						if ("@all".equals(renderId)) {
							foundAllKeyword = true;

							break;
						}
						else if ("@none".equals(renderId)) {
							foundNoneKeyword = true;
							this.render = "";

							break;
						}
						else {

							if (renderId.length() > 0) {

								if (first) {
									first = false;
								}
								else {
									buf.append(" ");
								}

								buf.append(renderId);
							}
						}
					}

					if (!foundNoneKeyword) {

						if (foundAllKeyword) {
							this.render = "@all";
						}
						else {
							this.render = buf.toString();
							this.render = this.render.replace("@form", formClientId);
							this.render = this.render.replace("@this", clientId);
						}
					}
				}
			}
		}
	}

	public String getExecute() {
		return execute;
	}

	public String getRender() {
		return render;
	}

}
