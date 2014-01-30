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
package com.liferay.faces.demos.bean;

import java.util.Enumeration;
import java.util.Map;

import javax.el.ELResolver;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.portlet.ActionResponse;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.WindowState;
import javax.portlet.faces.preference.Preference;

import com.liferay.faces.demos.util.FacesMessageUtil;


/**
 * @author  Neil Griffin
 */
@ManagedBean(name = "portletPreferencesBackingBean")
@RequestScoped
public class PortletPreferencesBackingBean {

	/**
	 * Resets/restores the values in the portletPreferences.xhtml Facelet composition with portlet preference default
	 * values.
	 */
	public void reset() {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		PortletRequest portletRequest = (PortletRequest) externalContext.getRequest();
		PortletPreferences portletPreferences = portletRequest.getPreferences();

		try {
			Enumeration<String> preferenceNames = portletPreferences.getNames();

			while (preferenceNames.hasMoreElements()) {
				String preferenceName = preferenceNames.nextElement();
				portletPreferences.reset(preferenceName);
			}

			portletPreferences.store();

			// Switch the portlet mode back to VIEW.
			ActionResponse actionResponse = (ActionResponse) externalContext.getResponse();
			actionResponse.setPortletMode(PortletMode.VIEW);
			actionResponse.setWindowState(WindowState.NORMAL);

			FacesMessageUtil.addGlobalSuccessInfoMessage(facesContext);
		}
		catch (Exception e) {
			FacesMessageUtil.addGlobalUnexpectedErrorMessage(facesContext);
		}

	}

	/**
	 * Saves the values in the portletPreferences.xhtml Facelet composition as portlet preferences.
	 */
	public void submit() {

		// The JSR 329 specification defines an EL variable named mutablePortletPreferencesValues that is being used in
		// the portletPreferences.xhtml Facelet composition. This object is of type Map<String, Preference> and is
		// designed to be a model managed-bean (in a sense) that contain preference values. However the only way to
		// access this from a Java class is to evaluate an EL expression (effectively self-injecting) the map into
		// this backing bean.
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		String elExpression = "mutablePortletPreferencesValues";
		ELResolver elResolver = facesContext.getApplication().getELResolver();
		@SuppressWarnings("unchecked")
		Map<String, Preference> mutablePreferenceMap = (Map<String, Preference>) elResolver.getValue(
				facesContext.getELContext(), null, elExpression);

		// Get a list of portlet preference names.
		PortletRequest portletRequest = (PortletRequest) externalContext.getRequest();
		PortletPreferences portletPreferences = portletRequest.getPreferences();
		Enumeration<String> preferenceNames = portletPreferences.getNames();

		try {

			// For each portlet preference name:
			while (preferenceNames.hasMoreElements()) {

				// Get the value specified by the user.
				String preferenceName = preferenceNames.nextElement();
				String preferenceValue = mutablePreferenceMap.get(preferenceName).getValue();

				// Prepare to save the value.
				if (!portletPreferences.isReadOnly(preferenceName)) {
					portletPreferences.setValue(preferenceName, preferenceValue);
				}
			}

			// Save the preference values.
			portletPreferences.store();

			// Switch the portlet mode back to VIEW.
			ActionResponse actionResponse = (ActionResponse) externalContext.getResponse();
			actionResponse.setPortletMode(PortletMode.VIEW);
			actionResponse.setWindowState(WindowState.NORMAL);

			// Report a successful message back to the user as feedback.
			FacesMessageUtil.addGlobalSuccessInfoMessage(facesContext);
		}
		catch (Exception e) {
			FacesMessageUtil.addGlobalUnexpectedErrorMessage(facesContext);
		}
	}
}
