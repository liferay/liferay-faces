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
package com.liferay.faces.portal.component.captcha.internal;

import java.util.Map;

import javax.faces.application.Resource;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.render.FacesRenderer;
import javax.portlet.PortletResponse;

import com.liferay.faces.portal.component.captcha.Captcha;
import com.liferay.faces.portal.render.internal.DelayedPortalTagRenderer;
import com.liferay.faces.portal.resource.CaptchaResource;
import com.liferay.faces.portal.resource.LiferayFacesResourceHandler;

import com.liferay.portal.kernel.captcha.CaptchaUtil;

import com.liferay.taglib.ui.CaptchaTag;


/**
 * @author  Juan Gonzalez
 */
//J-
@FacesRenderer(componentFamily = Captcha.COMPONENT_FAMILY, rendererType = Captcha.RENDERER_TYPE)
//J+
public class CaptchaRenderer extends DelayedPortalTagRenderer<Captcha, CaptchaTag> {

	@Override
	public void decode(FacesContext facesContext, UIComponent uiComponent) {

		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, String> requestParameterMap = externalContext.getRequestParameterMap();
		String submittedValue;
		Captcha captcha = cast(uiComponent);
		String captchaImpl = CaptchaUtil.getCaptcha().getClass().getName();

		if (captchaImpl.contains("ReCaptcha")) {
			submittedValue = requestParameterMap.get("recaptcha_response_field");
		}
		else {
			submittedValue = requestParameterMap.get("captchaText");
		}

		captcha.setSubmittedValue(submittedValue);
	}

	@Override
	public CaptchaTag newTag() {
		return new CaptchaTag();
	}

	@Override
	protected Captcha cast(UIComponent uiComponent) {
		return (Captcha) uiComponent;
	}

	@Override
	protected void copyFrameworkAttributes(FacesContext facesContext, Captcha captcha, CaptchaTag captchaTag) {

		String url;

		if (captcha.getUrl() != null) {
			url = captcha.getUrl();
		}
		else {
			Resource captchaResource = facesContext.getApplication().getResourceHandler().createResource(
					CaptchaResource.RESOURCE_NAME, LiferayFacesResourceHandler.LIBRARY_NAME);
			ExternalContext externalContext = facesContext.getExternalContext();
			url = externalContext.encodeResourceURL(captchaResource.getRequestPath());
		}

		captchaTag.setUrl(url);
	}

	@Override
	protected void copyNonFrameworkAttributes(FacesContext facesContext, Captcha captcha, CaptchaTag captchaTag) {
		// no-op
	}

	@Override
	protected StringBuilder getMarkup(UIComponent uiComponent, StringBuilder markup) throws Exception {

		// Fix the refresh captcha link with the namespaced id when using SimpleCaptcha (default in
		// portal-ext.properties). It works using out-of-the-box Liferay portlets because it internally uses the
		// namespace and this is not populated if used through Liferay Faces.
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		PortletResponse portletResponse = (PortletResponse) externalContext.getResponse();
		String namespace = portletResponse.getNamespace();
		String replacement = "id=\"".concat(namespace).concat("refreshCaptcha\"");
		String textToReplace = "id=\"refreshCaptcha\"";
		String modifiedMarkup = markup.toString();

		modifiedMarkup = modifiedMarkup.replace(textToReplace, replacement);

		String captchaImpl = CaptchaUtil.getCaptcha().getClass().getName();

		if (captchaImpl.contains("ReCaptcha")) {
			replacement = "name=\"".concat(namespace).concat("recaptcha_response_field\"");
			textToReplace = "name=\"recaptcha_response_field\"";
		}
		else {
			replacement = "name=\"".concat(namespace).concat("captchaText\"");
			textToReplace = "name=\"captchaText\"";
		}

		modifiedMarkup = modifiedMarkup.replace(textToReplace, replacement);

		// Remove <label>Text Verification<span class="required">...</span></label> since it is not possible to
		// customize the "Text Verification" label in the JSP tag. Better to let JSF developers decorate portal:captcha
		// with alloy:field if they want to.
		int labelStartPos = modifiedMarkup.indexOf("<label");

		if (labelStartPos > 0) {
			int labelFinishPos = modifiedMarkup.indexOf("</label>");
			modifiedMarkup = modifiedMarkup.substring(0, labelStartPos) + modifiedMarkup.substring(labelFinishPos + 8);
		}

		return new StringBuilder(modifiedMarkup);
	}
}
