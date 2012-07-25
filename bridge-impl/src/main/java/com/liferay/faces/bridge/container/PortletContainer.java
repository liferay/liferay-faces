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
package com.liferay.faces.bridge.container;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import javax.faces.context.ResponseWriter;
import javax.faces.event.PhaseListener;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.MimeResponse;
import javax.portlet.PortletURL;
import javax.portlet.ResourceResponse;
import javax.portlet.ResourceURL;


/**
 * @author  Neil Griffin
 */
public interface PortletContainer extends PhaseListener {

	/**
	 * Creates a portlet action URL and copies query parameters that might be present in the specified URL.
	 *
	 * @param   fromURL  The URL to copy from.
	 *
	 * @return  The resulting portlet action URL.
	 *
	 * @throws  MalformedURLException
	 */
	public PortletURL createActionURL(String fromURL) throws MalformedURLException;

	/**
	 * Creates a portlet resource URL that is suitable for partial requests (Ajax).
	 *
	 * @param   fromURL  The URL to copy from.
	 *
	 * @return  The resulting partial action resource URL.
	 *
	 * @throws  MalformedURLException
	 */
	public ResourceURL createPartialActionURL(String fromURL) throws MalformedURLException;

	/**
	 * Creates a portlet redirect URL and copies query parameters that might be present in the specified URL.
	 *
	 * @param   fromURL  The URL to copy from.
	 *
	 * @return  The resulting portlet action URL.
	 *
	 * @throws  MalformedURLException
	 */
	public PortletURL createRedirectURL(String fromURL, Map<String, List<String>> parameters)
		throws MalformedURLException;

	/**
	 * Creates a portlet render URL and copies query parameters that might be present in the specified URL.
	 *
	 * @param   fromURL  The URL to copy from.
	 *
	 * @return  The resulting portlet action URL.
	 *
	 * @throws  MalformedURLException
	 */
	public PortletURL createRenderURL(String fromURL) throws MalformedURLException;

	/**
	 * Creates a portlet resource URL and copies the javax.faces.resource value and query parameters that might be
	 * present in the specified URL.
	 *
	 * @param   fromURL  The URL to copy from. Example expected value:
	 *                   /portlet-context-path/*\/javax.faces.resource/jsf.js?ln=javax.faces
	 *
	 * @return  The resulting portlet resource URL.
	 *
	 * @throws  MalformedURLException
	 */
	public ResourceURL createResourceURL(String fromURL) throws MalformedURLException;

	/**
	 * This method provides the portlet conainer with an opportunity to fix/correct/massage the specified request
	 * parameter value.
	 *
	 * @param   value  The request parameter value that needs to be fixed.
	 *
	 * @return  The fixed value.
	 */
	public String fixRequestParameterValue(String value);

	/**
	 * Maintains (copies) the render parameters found in the specified EventRequest to the specified EventResponse.
	 */
	public void maintainRenderParameters(EventRequest eventRequest, EventResponse eventResponse);

	/**
	 * Delegates to the underlying ActionResponse to perform a redirect to the specified URL.
	 */
	public void redirect(String url) throws IOException;

	/**
	 * Determines whether or not the portlet container has the ability (standard or vendor-specific) to add &lt;script
	 * src=".." /&gt; resource to the &lt;head&gt;...&lt;/head&gt; section of the rendered portal page.
	 *
	 * @return  True if the portlet container supports it.
	 */
	public boolean isAbleToAddScriptResourceToHead();

	/**
	 * Determines whether or not the portlet container has the ability (standard or vendor-specific) to add
	 * &lt;script&gt; text to the &lt;head&gt;...&lt;/head&gt; section of the rendered portal page.
	 *
	 * @return  True if the portlet container supports it.
	 */
	public boolean isAbleToAddScriptTextToHead();

	/**
	 * Determines whether or not the portlet container has the ability (standard or vendor-specific) to add stylesheet
	 * &lt;link&gt; tags to the &lt;head&gt;...&lt;/head&gt; section of the rendered portal page.
	 *
	 * @return  True if the portlet container supports it.
	 */
	public boolean isAbleToAddStyleSheetResourceToHead();

	/**
	 * Flag indicating whether or not the portlet container supports/implements the POST-REDIRECT-GET design pattern,
	 * meaning that the ACTION_PHASE originates from an HTTP POST request, and the RENDER_PHASE is a subsequent HTTP GET
	 * request.
	 *
	 * @return  <code>true</code> if the POST-REDIRECT-GET design pattern is supported, otherwise <code>false</code>.
	 */
	public boolean isPostRedirectGetSupported();

	/**
	 * Determines whether or not the portlet container has the ability to support the Portlet 2.0 standard mechanism of
	 * setting the {@link ResourceResponse.HTTP_STATUS_CODE} property on the {@link ResourceResponse}.
	 */
	public boolean isAbleToSetHttpStatusCode();

	/**
	 * Determines whether or not the portlet container has the ability to set the buffer size on its
	 * javax.portlet.ResourceResponse implementation.
	 *
	 * @return  True if the portlet container supports it.
	 */
	public boolean isAbleToSetResourceResponseBufferSize();

	/**
	 * Determines whether or not the portlet container has the ability to issue a forward when a dispatch occurs. If
	 * unable to forward, then an include must happen during the dispatch instead.
	 *
	 * @return  <code>true</code> if able, otherwise <code>false</code>.
	 */
	public boolean isAbleToForwardOnDispatch();

	/**
	 * Returns the value of the header with the specified name from the underlying HttpServletRequest.
	 */
	public String[] getHeader(String name);

	public ResponseWriter getHeadResponseWriter(ResponseWriter wrappableResponseWriter);

	/**
	 * Returns the value of the specified header name from the HttpServletRequest that is wrapped by the current
	 * PortletRequest.
	 */
	public long getHttpServletRequestDateHeader(String name);

	/**
	 * Layer of abstraction over the {@link MimeResponse#setContentType(String)} method.
	 *
	 * @param  contentType  The contentType that is to be set on the specified MimeResponse.
	 */
	public void setMimeResponseContentType(MimeResponse mimeResponse, String contentType);

	/**
	 * This is a convenience method that gets the specified request parameter value. While this could normally be done
	 * by simply calling PortletRequest.getParameter(String), this method provides the portlet container abstraction
	 * layer with an opportunity to fix/correct/massage the parameter value as required. Therefore, any implementing
	 * class of this method must call the {@link getRequestParameter(String)} method before returning a value.
	 *
	 * @param   name  The request parameter name.
	 *
	 * @return  The request parameter value.
	 */
	public String getRequestParameter(String name);

	/**
	 * This is a convenience method that gets the specified request parameter values. While this could normally be done
	 * by simply calling PortletRequest.getParameterValues(String), this method provides the portlet container
	 * abstraction layer with an opportunity to fix/correct/massage the parameter values as required. Therefore, any
	 * implementing class of this method must call the {@link getRequestParameter(String)} method before returning a
	 * value.
	 *
	 * @param   name  The request parameter name.
	 *
	 * @return  The request parameter value.
	 */
	public String[] getRequestParameterValues(String name);

	/**
	 * Gets the query string part of the URL requested by the user-agent (browser) by getting the attribute named
	 * "javax.servlet.forward.query_string" from the request.
	 *
	 * @return  The query_string part of the URL requested by the user-agent (browser).
	 */
	public String getRequestQueryString();

	/**
	 * Returns the URL requested by the user-agent (browser).
	 */
	public String getRequestURL();

	/**
	 * Returns the response namespace.
	 */
	public String getResponseNamespace();
}
