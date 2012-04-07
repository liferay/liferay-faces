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
package com.liferay.faces.bridge.container.liferay;

import javax.portlet.PortletMode;
import javax.portlet.ResourceURL;
import javax.portlet.WindowState;


/**
 * @author  Neil Griffin
 */
public class LiferayResourceURL extends LiferayBaseURL implements ResourceURL {

	// Private Data Members
	private String cacheLevel;
	private String toStringValue;

	public LiferayResourceURL(ParsedBaseURL parsedLiferayURL, String responseNamespace) {
		super(parsedLiferayURL, responseNamespace);
	}

	@Override
	public String toString() {

		if (toStringValue == null) {

			// FACES-863: Liferay 5.2 requires the p_p_mode and p_p_state parameters even though this shouldn't be the
			// case for a ResourceURL.
			LiferayReleaseInfo liferayReleaseInfo = new LiferayReleaseInfo();
			int liferayBuildNumber = liferayReleaseInfo.getBuildNumber();

			if ((liferayBuildNumber >= 5200) && (liferayBuildNumber < 6000)) {
				StringBuilder url = new StringBuilder(super.toString());

				// Add the p_p_mode parameter with value "view".
				boolean firstParameter = false;
				appendParameterToURL(firstParameter, LiferayConstants.P_P_MODE, PortletMode.VIEW.toString(), url);

				// Add the p_p_state parameter with value "normal".
				appendParameterToURL(firstParameter, LiferayConstants.P_P_STATE, WindowState.NORMAL.toString(), url);

				toStringValue = url.toString();
			}
			else {
				toStringValue = super.toString();
			}
		}

		return toStringValue;
	}

	public String getCacheability() {
		return cacheLevel;
	}

	public void setCacheability(String cacheLevel) {
		this.cacheLevel = cacheLevel;
	}

	@Override
	public String getPortletLifecycleId() {
		return LiferayConstants.LIFECYCLE_RESOURCE_PHASE_ID;
	}

	public void setResourceID(String resourceId) {
		// Ignore
	}

}
