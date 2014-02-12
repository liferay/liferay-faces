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
package com.liferay.util.bridges.jsf.common;

import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.servlet.http.HttpServletRequest;

import com.liferay.faces.portal.context.LiferayFacesContext;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.kernel.util.GetterUtil;


/**
 * @deprecated  This class has been deprecated as of Liferay Faces 2.2 and will not appear in future versions.
 * @author      Neil Griffin
 */
@Deprecated
public class JSFPortletUtil {

	// Logger
	private static Logger logger = LoggerFactory.getLogger(JSFPortletUtil.class);

	/**
	 * @deprecated  Call {@link LiferayFacesContext#getCompanyId()} instead.
	 */
	public static long getCompanyId(FacesContext facesContext) {
		return getCompanyId(getPortletRequest(facesContext));
	}

	/**
	 * @deprecated  Call {@link LiferayFacesContext#getCompanyId()} instead.
	 */
	public static long getCompanyId(PortletRequest portletRequest) {
		long companyId = 0;

		@SuppressWarnings("unchecked")
		Map<String, String> userInfo = (Map<String, String>) portletRequest.getAttribute(RenderRequest.USER_INFO);

		if (userInfo != null) {
			companyId = GetterUtil.getLong(userInfo.get("liferay.company.id"));
		}

		return companyId;
	}

	/**
	 * @deprecated  Call {@link LiferayFacesContext#getLocale()} instead.
	 */
	public static Locale getLocale(FacesContext facesContext) {
		Locale locale = facesContext.getViewRoot().getLocale();

		if (locale == null) {
			locale = facesContext.getApplication().getDefaultLocale();
		}

		return (locale);
	}

	/**
	 * @deprecated  Call {@link LiferayFacesContext#getPortletPreferences()} instead.
	 */
	public static PortletPreferences getPortletPreferences(FacesContext facesContext) {

		return JSFPortletUtil.getPortletRequest(facesContext).getPreferences();
	}

	/**
	 * @deprecated  Call {@link LiferayFacesContext#getPortletRequest()} instead.
	 */
	public static PortletRequest getPortletRequest(FacesContext facesContext) {
		Object request = facesContext.getExternalContext().getRequest();

		if (request == null) {
			return null;
		}

		if (request instanceof PortletRequest) {
			return (PortletRequest) request;
		}
		else if (request instanceof HttpServletRequest) {
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;

			Object portletArtifactHack = httpServletRequest.getAttribute("com.icesoft.faces.portletHack");

			if (portletArtifactHack == null) {
				return null;
			}

			try {
				Class<?> portletArtifactHackClass = portletArtifactHack.getClass();

				Method method = portletArtifactHackClass.getMethod("getPortletRequest");

				if (method != null) {
					Object value = method.invoke(portletArtifactHack);

					if ((value != null) && (value instanceof PortletRequest)) {
						return (PortletRequest) value;
					}
				}
			}
			catch (Exception e) {
				logger.error(e);
			}
		}

		return null;
	}

	/**
	 * @deprecated  Call {@link LiferayFacesContext#getPortletPreferenceAsString(String, String)} instead.
	 */
	public static String getPreferenceValue(FacesContext facesContext, String preferenceName) {

		return getPreferenceValue(facesContext, preferenceName, null);
	}

	/**
	 * @deprecated  Call {@link LiferayFacesContext#getPortletPreferences()} instead.
	 */
	public static String getPreferenceValue(PortletPreferences portletPreferences, String preferenceName) {

		return getPreferenceValue(portletPreferences, preferenceName, null);
	}

	/**
	 * @deprecated  Call {@link LiferayFacesContext#getPortletPreferenceAsString(String, String)} instead.
	 */
	public static String getPreferenceValue(FacesContext facesContext, String preferenceName, String defaultValue) {

		return getPreferenceValue(getPortletPreferences(facesContext), preferenceName, defaultValue);
	}

	/**
	 * @deprecated  Call {@link LiferayFacesContext#getPortletPreferences()} instead.
	 */
	public static String getPreferenceValue(PortletPreferences portletPreferences, String preferenceName,
		String defaultValue) {

		String value = defaultValue;

		if (portletPreferences != null) {
			value = portletPreferences.getValue(preferenceName, defaultValue);
		}

		return value;
	}

}
