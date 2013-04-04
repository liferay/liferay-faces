/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.bridge.context;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletResponse;
import javax.portlet.faces.Bridge;
import javax.portlet.faces.BridgeDefaultViewNotSpecifiedException;
import javax.portlet.faces.BridgeInvalidViewPathException;

import com.liferay.faces.bridge.config.BridgeConfig;
import com.liferay.faces.bridge.container.PortletContainer;
import com.liferay.faces.bridge.context.url.BridgeActionURL;
import com.liferay.faces.bridge.context.url.BridgePartialActionURL;
import com.liferay.faces.bridge.context.url.BridgeRedirectURL;
import com.liferay.faces.bridge.context.url.BridgeResourceURL;
import com.liferay.faces.bridge.scope.BridgeRequestScope;


/**
 * This is an abstract class modeled after the JSF {@link FacesContext} in that it encapsulates a {@link ThreadLocal}
 * singleton instance. The instance contains contextual information related to the bridge.
 *
 * @author  Neil Griffin
 */
public abstract class BridgeContext {

	// Private Static Data Members
	private static ThreadLocal<BridgeContext> instance = new ThreadLocal<BridgeContext>();

	public static BridgeContext getCurrentInstance() {
		return instance.get();
	}

	public static void setCurrentInstance(BridgeContext bridgeContext) {

		if (bridgeContext == null) {
			instance.remove();
		}
		else {
			instance.set(bridgeContext);
		}
	}

	/**
	 * Creates a {@link PortletRequestDispatcher} for the specified <code>path</code> and issues a forward/include as
	 * appropriate.
	 *
	 * @throws  IOException
	 */
	public abstract void dispatch(String path) throws IOException;

	/**
	 * Encodes a bridge "action" URL, meaning a URL that conforms to the deviation requirements of {@link
	 * javax.faces.context.ExternalContext#encodeActionURL(String)} listed in Section 6.1.3.1 of the Bridge Spec.
	 *
	 * @param   url  The URL to be encoded.
	 *
	 * @return  A new instance of {@link BrideActionURL} that conforms to the proper encoding.
	 */
	public abstract BridgeActionURL encodeActionURL(String url);

	/**
	 * Encodes a bridge "partial action" URL, meaning a URL that is intended to be used for Ajax (partial request)
	 * processing. Note that {@link ExternalContext#encodePartialActionURL(String)} was added in JSF 2.0 which means
	 * there are no Bridge Spec deviation requirements yet.
	 *
	 * @param   url  The URL to be encoded.
	 *
	 * @return  A new instance of {@link BridgePartialActionURL} that conforms to the proper encoding.
	 */
	public abstract BridgePartialActionURL encodePartialActionURL(String url);

	/**
	 * Encodes a bridge "redirect" URL, meaning a URL that conforms to the deviation requirements of {@link
	 * javax.faces.context.ExternalContext#redirect(String)} listed in Section 6.1.3.1 of the Bridge Spec.
	 *
	 * @param   baseUrl     The URL to be encoded.
	 * @param   parameters  The URL parameters that are to be included as part of the encoding.
	 *
	 * @return  A new instance of {@link BridgeRedirectURL} that conforms to the proper encoding.
	 */
	public abstract BridgeRedirectURL encodeRedirectURL(String baseUrl, Map<String, List<String>> parameters);

	/**
	 * Encodes a bridge "resource" URL, meaning a URL that conforms to the deviation requirements of {@link
	 * javax.faces.context.ExternalContext#encodeResourceURL(String)} listed in Section 6.1.3.1 of the Bridge Spec.
	 *
	 * @param   url  The URL to be encoded.
	 *
	 * @return  A new instance of {@link BridgeResourceURL} that conforms to the proper encoding.
	 */
	public abstract BridgeResourceURL encodeResourceURL(String url);

	/**
	 * If the specified <code>url</code> is external to this portlet context, then calling this method causes a
	 * redirection to the specified <code>url</code>. Otherwise, this method will effectively "navigate" to the Faces
	 * viewId found in the URL path.
	 */
	public abstract void redirect(String url) throws IOException;

	/**
	 * Performs any necessary cleanup.
	 */
	public abstract void release();

	/**
	 * Returns the attribute map associated with this context.
	 */
	public abstract Map<String, Object> getAttributes();

	/**
	 * Convenience method that returns the {@link BridgeConfig} for this context.
	 */
	public abstract BridgeConfig getBridgeConfig();

	/**
	 * Convenience method that returns the {@link BridgeRequestScope} for this context.
	 */
	public abstract BridgeRequestScope getBridgeRequestScope();

	/**
	 * Returns the flag indicating whether or not the current {@link BridgeRequestScope} is preserved at the end of the
	 * {@link javax.portlet.RenderRequest}.
	 *
	 * @return  <code>true</code> if the scope is to be preserved, otherwise <code>false</code>. Default value is <code>
	 *          false</code> which is an optimization for JSF 2 that deviates from the Bridge Spec. See FACES-219.
	 */
	public abstract boolean isBridgeRequestScopePreserved();

	/**
	 * Returns the default Render Kit ID, which is optionally specified by developers with the in the
	 * WEB-INF/portlet.xml descriptor. If not specified by the developer, then returns null.
	 */
	public abstract String getDefaultRenderKitId();

	/**
	 * <p>Returns an immutable {@link Map} whose keys are determined by {@link PortletMode#toString()} and whose values
	 * are retrieved from the following sections of the WEB-INF/portlet.xml descriptor.</p>
	 * <code>
	 * <pre>
	  &lt;init-param&gt;
	  &lt;name&gt;javax.portlet.faces.defaultViewId.view&lt;/name&gt;
	  &lt;value&gt;/xhtml/portletViewMode.xhtml&lt;/value&gt;
	  &lt;/init-param&gt;
	  &lt;init-param&gt;
	  &lt;name&gt;javax.portlet.faces.defaultViewId.edit&lt;/name&gt;
	  &lt;value&gt;/xhtml/portletEditMode.xhtml&lt;/value&gt;
	  &lt;/init-param&gt;
	  &lt;init-param&gt;
	  &lt;name&gt;javax.portlet.faces.defaultViewId.help&lt;/name&gt;
	  &lt;value&gt;/xhtml/portletHelpMode.xhtml&lt;/value&gt;
	  &lt;/init-param&gt;
	 * </pre>
	 * </code>
	 *
	 * @return
	 */
	public abstract Map<String, String> getDefaultViewIdMap();

	/**
	 * Returns the target view (and optional query string) as described in section 5.2.3 of the Bridge Spec titled
	 * "Determining the Target View".
	 *
	 * @throws  {@link BridgeDefaultViewNotSpecifiedException} when the default view is not specified in the
	 *            WEB-INF/portlet.xml descriptor.
	 * @throws  {@link BridgeInvalidViewPathException} when the {@link Bridge#VIEW_PATH} request attribute contains an
	 *            invalid path such that the target view cannot be determined.
	 */
	public abstract String getFacesViewId() throws BridgeDefaultViewNotSpecifiedException,
		BridgeInvalidViewPathException;

	/**
	 * Returns the viewId associated with the specified <code>viewPath</code> by examining the servlet-mapping entries
	 * from the WEB-INF/web.xml descriptor.
	 *
	 * @param   viewPath  The path to the view.
	 *
	 * @return  The viewId associated with the specified <code>viewPath</code> (providing that the view physically
	 *          exists). Otherwise returns null.
	 */
	public abstract String getFacesViewIdFromPath(String viewPath);

	/**
	 * Returns the viewId associated with the specified <code>viewPath</code> by examining the servlet-mapping entries
	 * from the WEB-INF/web.xml descriptor.
	 *
	 * @param   viewPath   The path to the view.
	 * @param   mustExist  Flag indicating whether or not the view must physically exist in order for the viewId to be
	 *                     returned.
	 *
	 * @return  The viewId associated with the specified <code>viewPath</code>. Otherwise returns null.
	 */
	public abstract String getFacesViewIdFromPath(String viewPath, boolean mustExist);

	/**
	 * Returns the query-string part of the to-view-id of the last navigation-rule that fired, or the query-string part
	 * of the {@link Bridge#VIEW_ID} request attribute.
	 */
	public abstract String getFacesViewQueryString();

	/**
	 * Returns a flag indicating whether or not a render-redirect has occurred after dispatching to a JSP.
	 *
	 * @return  <code>true</code> if a render-redirect has occurred after dispatching to a JSP, otherwise <code>
	 *          false</code>.
	 */
	public abstract boolean isRenderRedirectAfterDispatch();

	public abstract IncongruityContext getIncongruityContext();

	/**
	 * NOTE: PROPOSE-FOR-BRIDGE3-API Returns the value of the specified initialization parameter. If found, return the
	 * value of the {@link PortletConfig#getInitParameter(String)} method. Otherwise, return the value of the {@link
	 * PortletContext#getInitParameter(String)} method. This provides a way for init-param values found in the
	 * WEB-INF/portlet.xml descriptor to override context-param values found in the WEB-INF/web.xml descriptor.
	 */
	public abstract String getInitParameter(String name);

	/**
	 * Return the current {@link PortletConfig}.
	 */
	public abstract PortletConfig getPortletConfig();

	/**
	 * Returns the {@link PortletContainer} associated with the current request.
	 */
	public abstract PortletContainer getPortletContainer();

	/**
	 * Returns the {@link PortletContext} associated with the current portlet.
	 */
	public abstract PortletContext getPortletContext();

	/**
	 * Returns the {@link PortletRequest} associated with the current request.
	 */
	public abstract PortletRequest getPortletRequest();

	/**
	 * Preserves the {@link PortletRequest} associated with the current request.
	 */
	public abstract void setPortletRequest(PortletRequest portletRequest);

	/**
	 * Returns the {@link Bridge.PortletPhase} associated with the current portlet lifecycle phase.
	 */
	public abstract Bridge.PortletPhase getPortletRequestPhase();

	/**
	 * Returns the {@link PortletResponse} associated with the current response.
	 */
	public abstract PortletResponse getPortletResponse();

	/**
	 * Preserves the {@link PortletResponse} associated with the current response.
	 */
	public abstract void setPortletResponse(PortletResponse portletResponse);

	/**
	 * Returns a list of attribute names that existed prior to the FacesContext being created.
	 */
	public abstract List<String> getPreFacesRequestAttrNames();

	/**
	 * Returns the {@link Map} of preserved action parameters.
	 */
	public abstract Map<String, String[]> getPreservedActionParams();

	/**
	 * Sets the flag indicating whether or not the bridge is processing {@link Bridge#AFTER_VIEW_CONTENT}.
	 *
	 * @param  processingAfterViewContent  <code>true</code> if processing, otherwise <code>false</code>.
	 */
	public abstract void setProcessingAfterViewContent(boolean processingAfterViewContent);

	/**
	 * Sets a flag indicating whether or not a render-redirect has occurred after dispatching to a JSP.
	 */
	public abstract void setRenderRedirectAfterDispatch(boolean renderRedirectAfterDispatch);

	/**
	 * Gets the render-redirect URL that was set during a render-redirect.
	 */
	public abstract BridgeRedirectURL getRenderRedirectURL();

	/**
	 * Sets the render-redirect URL that is associated with a render-redirect.
	 */
	public abstract void setRenderRedirectURL(BridgeRedirectURL renderRedirectURL);

	public abstract Map<String, String> getRequestHeaderMap();

	public abstract Map<String, String[]> getRequestHeaderValuesMap();

	public abstract Map<String, String> getRequestParameterMap();

	public abstract Map<String, String[]> getRequestParameterValuesMap();

	/**
	 * Returns the pathInfo associated with the current viewId.
	 */
	public abstract String getRequestPathInfo();

	/**
	 * Returns the servletPath associated with the current viewId.
	 */
	public abstract String getRequestServletPath();

	public abstract String getResponseNamespace();

	/**
	 * Returns a {@link Writer} that is meant to be used as a return value for {@link
	 * ExternalContext#getResponseOutputWriter()}.
	 *
	 * @throws  IOException
	 */
	public abstract Writer getResponseOutputWriter() throws IOException;

	/**
	 * Determines whether or not the "javax.portlet.faces.preserveActionParams" init-param has been configured in the
	 * WEB-INF/portlet.xml descriptor.
	 *
	 * @return  <code>true</code> if the "javax.portlet.faces.preserveActionParams" init-param has a value of true,
	 *          otherwise <code>false</code>.
	 */
	public abstract boolean isPreserveActionParams();

	/**
	 * Returns the saved view state.
	 */
	public abstract String getSavedViewState();

	/**
	 * Preserves the saved view state.
	 *
	 * @param  savedViewState  The saved view state.
	 */
	public abstract void setSavedViewState(String savedViewState);

	/**
	 * Flag indicating whether or not the bridge is processing {@link Bridge#AFTER_VIEW_CONTENT}.
	 *
	 * @return  <code>true</code> if processing, otherwise <code>false</code>
	 */
	public abstract boolean isProcessingAfterViewContent();

	/**
	 * Returns the flag indicating whether or not a render redirect occurred.
	 */
	public abstract boolean isRenderRedirect();
}
