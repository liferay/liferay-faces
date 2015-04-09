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
import com.liferay.faces.util.lang.StringPool;

import com.liferay.portal.kernel.captcha.CaptchaUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;

import com.liferay.taglib.ui.CaptchaTag;


/**
 * @author  Juan Gonzalez
 */
//J-
@FacesRenderer(componentFamily = Captcha.COMPONENT_FAMILY, rendererType = Captcha.RENDERER_TYPE)
//J+
public class CaptchaRenderer extends DelayedPortalTagRenderer<Captcha, CaptchaTag> {

	static {

		try {
			String captchaClassName = PrefsPropsUtil.getString(PropsKeys.CAPTCHA_ENGINE_IMPL);

			ClassLoader portalClassLoader = PortalClassLoaderUtil.getClassLoader();

			@SuppressWarnings("unchecked")
			Class<com.liferay.portal.kernel.captcha.Captcha> captchaClass =
				(Class<com.liferay.portal.kernel.captcha.Captcha>) portalClassLoader.loadClass(captchaClassName);

			CaptchaUtil captchaUtil = new CaptchaUtil();
			com.liferay.portal.kernel.captcha.Captcha captcha;

			captcha = captchaClass.newInstance();
			captchaUtil.setCaptcha(captcha);
		}
		catch (Exception e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static final String SIMPLECAPTCHA_TEXT_PARAM = "captchaText";
	public static final String RECAPTCHA_TEXT_PARAM = "recaptcha_response_field";

	@Override
	public void decode(FacesContext facesContext, UIComponent uiComponent) {

		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, String> requestParameterMap = externalContext.getRequestParameterMap();
		String submittedValue = null;
		Captcha captcha = cast(uiComponent);
		String captchaImpl = CaptchaUtil.getCaptcha().getClass().getName();

		if (captchaImpl.contains("ReCaptcha")) {
			submittedValue = requestParameterMap.get(RECAPTCHA_TEXT_PARAM);
		}
		else {
			submittedValue = requestParameterMap.get(SIMPLECAPTCHA_TEXT_PARAM);
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

		String url = StringPool.BLANK;

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

		//J-
		// NOTE: This method is needed for having the refresh captcha link with the right id
		// when using SimpleCaptcha (default in Liferay portal-ext.properties)
		// It works using other Liferay portlets, because it internally uses the namespace and this
		// is not populated if used through Liferay Faces
		//J+

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();

		PortletResponse portletResponse = (PortletResponse) externalContext.getResponse();

		String replaced = markup.toString().replace("id=\"refreshCaptcha\"",
				"id=\"" + portletResponse.getNamespace() + "refreshCaptcha\"");

		return new StringBuilder(replaced);
	}

}
