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
package com.liferay.faces.portal.lifecycle;

import java.util.Locale;

import javax.faces.component.UIViewRoot;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import com.liferay.faces.portal.context.LiferayFacesContext;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.theme.ThemeDisplay;


/**
 * This class is a JSF PhaseListener that applies the current Liferay Portal theme display's locale to the UIViewRoot.
 * This is done after the restore view phase (to have the correct locale during the following phases) and before the
 * render response phase (in case the view root has changed in the invoke application phase).
 *
 * @author  Neil Griffin
 */
public class LiferayLocalePhaseListener implements PhaseListener {

	// serialVersionUID
	private static final long serialVersionUID = 3186309855861540422L;

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(LiferayLocalePhaseListener.class);

	public void afterPhase(PhaseEvent phaseEvent) {

		if (phaseEvent.getPhaseId() == PhaseId.RESTORE_VIEW) {
			setLocale();
		}
	}

	public void beforePhase(PhaseEvent phaseEvent) {

		if (phaseEvent.getPhaseId() == PhaseId.RENDER_RESPONSE) {
			setLocale();
		}
	}

	private void setLocale() {

		try {
			LiferayFacesContext liferayFacesContext = LiferayFacesContext.getInstance();

			// It's possible that the FacesServlet was invoked directly, and so this PhaseListener
			// needs to check to see if the request/response is part of a portlet environment before
			// proceeding further.
			if (liferayFacesContext.isPortletEnvironment()) {
				ThemeDisplay themeDisplay = liferayFacesContext.getThemeDisplay();

				if (themeDisplay != null) {
					Locale locale = themeDisplay.getLocale();

					if (locale != null) {
						UIViewRoot viewRoot = liferayFacesContext.getViewRoot();

						if (viewRoot != null) {
							viewRoot.setLocale(locale);
						}
						else {
							logger.error("viewRoot is null!");
						}
					}
				}
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

}
