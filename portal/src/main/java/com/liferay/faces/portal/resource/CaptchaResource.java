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
package com.liferay.faces.portal.resource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSession;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import com.liferay.portal.kernel.captcha.Captcha;
import com.liferay.portal.kernel.captcha.CaptchaUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.util.PortalUtil;


/**
 * @author  Neil Griffin
 * @author  Joe Ssemwogerere
 */
public class CaptchaResource extends Resource {

	// Public Constants
	public static final String CONTENT_TYPE = "image/png";
	public static final String RESOURCE_NAME = "captcha";

	// Private Constants
	private static final String CAPTCHA_TEXT = "CAPTCHA_TEXT";

	// Private Data Members
	private String requestPath;

	public CaptchaResource() {
		setLibraryName(LiferayFacesResourceHandler.LIBRARY_NAME);
		setResourceName(RESOURCE_NAME);
		setContentType(CONTENT_TYPE);
	}

	@Override
	public boolean userAgentNeedsUpdate(FacesContext context) {

		// Since this is a list that can potentially change dynamically, always return true.
		return false;
	}

	@Override
	public InputStream getInputStream() {
		ByteArrayInputStream byteArrayInputStream = null;

		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();
			PortletRequest portletRequest = (PortletRequest) externalContext.getRequest();
			PortletResponse portletResponse = (PortletResponse) externalContext.getResponse();
			PortletSession portletSession = (PortletSession) externalContext.getSession(true);
			HttpServletRequest httpServletRequest = PortalUtil.getHttpServletRequest(portletRequest);
			HttpServletResponse httpServletResponse = PortalUtil.getHttpServletResponse(portletResponse);
			CaptchaHttpServletResponse captchaHttpServletResponse = new CaptchaHttpServletResponse(httpServletResponse);
			String captchaClassName = "com.liferay.portal.captcha.CaptchaImpl";
			ClassLoader portalClassLoader = PortalClassLoaderUtil.getClassLoader();

			@SuppressWarnings("unchecked")
			Class<Captcha> captchaClass = (Class<Captcha>) portalClassLoader.loadClass(captchaClassName);

			CaptchaUtil captchaUtil = new CaptchaUtil();
			Captcha captcha = captchaClass.newInstance();
			captchaUtil.setCaptcha(captcha);
			CaptchaUtil.serveImage(httpServletRequest, captchaHttpServletResponse);

			String captchaText = (String) httpServletRequest.getSession().getAttribute(CAPTCHA_TEXT);
			portletSession.setAttribute(CAPTCHA_TEXT, captchaText);

			CaptchaServletOutputStream captchaServletOutputStream = (CaptchaServletOutputStream)
				captchaHttpServletResponse.getOutputStream();
			byteArrayInputStream = new ByteArrayInputStream(captchaServletOutputStream.toByteArray());
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return byteArrayInputStream;
	}

	@Override
	public String getRequestPath() {

		if (requestPath == null) {
			StringBuilder buf = new StringBuilder();
			buf.append(ResourceHandler.RESOURCE_IDENTIFIER);
			buf.append("/");
			buf.append(getResourceName());
			buf.append("?ln=");
			buf.append(getLibraryName());
			requestPath = buf.toString();
			requestPath = FacesContext.getCurrentInstance().getExternalContext().encodeResourceURL(requestPath);
		}

		return requestPath;
	}

	@Override
	public Map<String, String> getResponseHeaders() {
		return null;
	}

	@Override
	public URL getURL() {
		return null;
	}

	public class CaptchaServletOutputStream extends ServletOutputStream {

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

		public byte[] toByteArray() {
			return byteArrayOutputStream.toByteArray();
		}

		@Override
		public void write(int b) throws IOException {
			byteArrayOutputStream.write(b);
		}
	}

	protected class CaptchaHttpServletResponse extends HttpServletResponseWrapper {

		ServletOutputStream outputStream;

		public CaptchaHttpServletResponse(HttpServletResponse response) {
			super(response);
		}

		@Override
		public ServletOutputStream getOutputStream() throws IOException {

			if (outputStream == null) {
				outputStream = new CaptchaServletOutputStream();
			}

			return outputStream;
		}

	}

}
