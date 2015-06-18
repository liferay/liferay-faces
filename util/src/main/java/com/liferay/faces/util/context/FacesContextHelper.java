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
package com.liferay.faces.util.context;

import java.util.Locale;
import java.util.Map;

import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseListener;


/**
 * @author  Neil Griffin
 */
public interface FacesContextHelper {

	/**
	 * Adds the specified key as a message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_ERROR</code>. The message will not be global, rather it will be
	 * associated with the <code>javax.faces.component.UIComponent</code> that is associated with the specified
	 * clientId.
	 */
	public abstract void addComponentErrorMessage(String clientId, String messageId);

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_ERROR</code> and substitutes the specified argument for the tokens
	 * in the specified key. The message will not be global, rather it will be associated with the <code>
	 * javax.faces.component.UIComponent</code> that is associated with the specified clientId.
	 */
	public abstract void addComponentErrorMessage(String clientId, String messageId, Object argument);

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_ERROR</code> and substitutes the specified argument(s) for the
	 * tokens in the specified key. The message will not be global, rather it will be associated with the <code>
	 * javax.faces.component.UIComponent</code> that is associated with the specified clientId.
	 */
	public abstract void addComponentErrorMessage(String clientId, String messageId, Object... arguments);

	/**
	 * Adds the specified key as a message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_INFO</code>. The message will not be global, rather it will be
	 * associated with the <code>javax.faces.component.UIComponent</code> that is associated with the specified
	 * clientId.
	 */
	public abstract void addComponentInfoMessage(String clientId, String messageId);

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_INFO</code> and substitutes the specified argument for the tokens
	 * in the specified key. The message will not be global, rather it will be associated with the <code>
	 * javax.faces.component.UIComponent</code> that is associated with the specified clientId.
	 */
	public abstract void addComponentInfoMessage(String clientId, String messageId, Object argument);

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_INFO</code> and substitutes the specified argument(s) for the
	 * tokens in the specified key. The message will not be global, rather it will be associated with the <code>
	 * javax.faces.component.UIComponent</code> that is associated with the specified clientId.
	 */
	public abstract void addComponentInfoMessage(String clientId, String messageId, Object... arguments);

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_ERROR</code>.
	 */
	public abstract void addGlobalErrorMessage(String messageId);

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_ERROR</code> and substitutes the specified argument for the tokens
	 * in the specified key.
	 */
	public abstract void addGlobalErrorMessage(String messageId, Object argument);

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_ERROR</code> and substitutes the specified argument(s) for the
	 * tokens in the specified key.
	 */
	public abstract void addGlobalErrorMessage(String messageId, Object... arguments);

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_INFO</code>.
	 */
	public abstract void addGlobalInfoMessage(String messageId);

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_INFO</code> and substitutes the specified argument for the tokens
	 * in the specified key.
	 */
	public abstract void addGlobalInfoMessage(String messageId, Object argument);

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_INFO</code> and substitutes the specified argument(s) for the
	 * tokens in the specified key.
	 */
	public abstract void addGlobalInfoMessage(String messageId, Object... arguments);

	/**
	 * Adds a global information message to the current FacesContext with "your-request-processed-successfully" key as
	 * the messageId.
	 */
	public abstract void addGlobalSuccessInfoMessage();

	/**
	 * Adds a global error message to the current FacesContext with "an-unexpected-error-occurred" key as the messageId.
	 */
	public abstract void addGlobalUnexpectedErrorMessage();

	/**
	 * Adds the specified key as a message associated with the UIComponent that has the specified clientId. Specify
	 * clientId=null for global messages.
	 */
	public abstract void addMessage(String clientId, Severity severity, String messageId);

	/**
	 * Adds the specified key as a message associated with the UIComponent that has the specified clientId. Specify
	 * clientId=null for global messages.
	 */
	public abstract void addMessage(String clientId, Severity severity, String messageId, Object argument);

	/**
	 * Adds the specified key as a message associated with the UIComponent that has the specified clientId. Specify
	 * clientId=null for global messages.
	 */
	public abstract void addMessage(String clientId, Severity severity, String messageId, Object... arguments);

	/**
	 * Traverses the component tree starting at the specified UIComponent parent and returns the first UIComponent child
	 * that contains the specified partialClientId.
	 */
	public abstract UIComponent matchComponentInHierarchy(UIComponent parent, String partialClientId);

	/**
	 * Traverses the component tree associated with the UIViewRoot of this FacesContext and returns the first
	 * UIComponent child that contains the specified partialClientId.
	 */
	public abstract UIComponent matchComponentInViewRoot(String partialClientId);

	/**
	 * Sets the current JSF navigation to the specified outcome.
	 *
	 * @param  fromAction  The "from action" as specified in a JSF navigation rule. Can be null to if no action is
	 *                     specified in the rule.
	 * @param  outcome     The "from outcome" as specified in a JSF navigation rule
	 */
	public abstract void navigate(String fromAction, String outcome);

	/**
	 * Sets the current JSF navigation to the specified outcome.
	 *
	 * @param  outcome  The "from outcome" as specified in a JSF navigation rule
	 */
	public abstract void navigateTo(String outcome);

	/**
	 * Delete the whole component tree. This causes the tree to be rebuilt the next time it is accessed. This addresses
	 * the problem that immediate actions cannot change the values of input components. To clear these values, use this
	 * method.
	 */
	public abstract void recreateComponentTree();

	/**
	 * Register a {@link PhaseListener} programatically (instead of in the faces-config.xml). Such a PhaseListener can
	 * therefore be controlled by spring and use dependency injection, which is not possible otherwise.
	 *
	 * @param  phaseListener
	 */
	public abstract void registerPhaseListener(PhaseListener phaseListener);

	/**
	 * clear component tree since input fields will not be refreshed. See <a
	 * href="http://wiki.apache.org/myfaces/ClearInputComponents">ClearInputComponents</a> for more information.
	 *
	 * @param  clientId  all children of the component with this id are cleared.
	 */
	public abstract void removeChildrenFromComponentTree(String clientId);

	/**
	 * Removes all FacesMessage instances from this FacesContext that are associated with the specified clientId.
	 */
	public abstract void removeMessages(String clientId);

	/**
	 * Removes all FacesMessage instances from this FacesContext that are associated with components whose immediate
	 * attribute is true.The typical use case for this method is when seemingly bogus messages are added to the
	 * FacesContext on components whose immediate attribute is true. This happens because {@link UIInput#processDecodes}
	 * calls validate() if the the immediate attribute is true.
	 */
	public abstract void removeMessagesForImmediateComponents();

	/**
	 * Removes all FacesMessage instances from this FacesContext that are associated with components whose immediate
	 * attribute is true.The typical use case for this method is when seemingly bogus messages are added to the
	 * FacesContext on components whose immediate attribute is true. This happens because {@link UIInput#processDecodes}
	 * calls validate() if the the immediate attribute is true.
	 */
	public abstract void removeMessagesForImmediateComponents(UIComponent uiComponent);

	/**
	 * Delete the component subtree of a given component. This causes the subtree to be rebuilt the next time it is
	 * accessed. This addresses the problem that immediate actions cannot change the values of input components. To
	 * clear these values, use this method.
	 *
	 * @param  uiComponent
	 */
	public abstract void removeParentFormFromComponentTree(final UIComponent uiComponent);

	/**
	 * Causes the current view's component tree to be discarded and re-rendered.
	 *
	 * @see  #resetView(boolean)
	 */
	public abstract void resetView();

	/**
	 * Causes the current view's component tree to be discarded and (optionally) re-rendered. This is useful whenever an
	 * action causes navigation back to the current view, but the data in the backing bean(s) has changed substantially.
	 * The view is rendered as if the user is visiting for the first time.
	 *
	 * @param  renderResponse  causes the response to be rendered immediately if true.
	 *
	 * @see    <a href="http://wiki.apache.org/myfaces/ClearInputComponents">ClearInputComponents</a>
	 */
	public abstract void resetView(boolean renderResponse);

	/**
	 * Returns the object associated with the specified EL expression.
	 */
	public abstract Object resolveExpression(String elExpression);

	/**
	 * Gets the underlying/wrapped FacesContext ThreadLocal singleton instance.
	 */
	public abstract FacesContext getFacesContext();

	/**
	 * Returns the locale of the viewRoot of the current JSF FacesContext
	 */
	public abstract Locale getLocale();

	/**
	 * Returns the message associated with the specified messageId by delegating to the FacesMessageFactory, according
	 * to the current locale.
	 */
	public abstract String getMessage(String messageId);

	/**
	 * Returns the message associated with the specified messageId by delegating to the FacesMessageFactory, according
	 * to the current locale and arguments that are to be substituted.
	 */
	public abstract String getMessage(String messageId, Object... arguments);

	/**
	 * Returns the message associated with the specified messageId by delegating to the FacesMessageFactory, according
	 * to the specified locale.
	 */
	public abstract String getMessage(Locale locale, String messageId);

	/**
	 * Returns the message associated with the specified messageId by delegating to the FacesMessageFactory, according
	 * to the specified locale and arguments that are to be substituted.
	 */
	public abstract String getMessage(Locale locale, String messageId, Object... arguments);

	/**
	 * Delegates to the underlying {@link ExternalContext#encodeNamespace(String)} method in order to get the
	 * application namespace.
	 */
	public abstract String getNamespace();

	/**
	 * Return the parent form of the given component.
	 *
	 * @param   uiComponent
	 *
	 * @return  the parent form or <code>null</code> if no parent form is found.
	 */
	public abstract UIForm getParentForm(final UIComponent uiComponent);

	/**
	 * Returns the value of the request attribute associated with the specified name.
	 */
	public abstract Object getRequestAttribute(String name);

	/**
	 * Sets the value of the a request attribute using the specified name and value.
	 */
	public abstract void setRequestAttribute(String name, Object value);

	/**
	 * @return  the request context path. {@link FacesContext#getExternalContext()} {@link
	 *          ExternalContext#getRequestContextPath()}
	 */
	public abstract String getRequestContextPath();

	/**
	 * Retrieves the specified parameter passed as part of the request
	 */
	public abstract String getRequestParameter(String name);

	/**
	 * Retrieves the specified parameter passed as part of the request as a boolean. The values "yes", "true", "y", and
	 * "1" are accetable values for "TRUE".
	 */
	public abstract boolean getRequestParameterAsBool(String name, boolean defaultValue);

	/**
	 * Retrieves the specified parameter passed as part of the request as an integer.
	 */
	public abstract int getRequestParameterAsInt(String name, int defaultValue);

	/**
	 * Retrieves the specified parameter passed as part of the request as an integer.
	 */
	public abstract long getRequestParameterAsLong(String name, long defaultValue);

	/**
	 * Retrieves the specified parameter from the ExternalContext's request parameter map.
	 */
	public abstract String getRequestParameterFromMap(String name);

	/**
	 * Returns the map of request parameters from the ExternalContext.
	 *
	 * @see  ExternalContext#getRequestParameterMap()
	 */
	public abstract Map<String, String> getRequestParameterMap();

	/**
	 * Retrieves the value of the original "javax.servlet.forward.query_string" request attribute.
	 */
	public abstract String getRequestQueryString();

	/**
	 * Retrieves the value of the specified parameter name from the original "javax.servlet.forward.query_string"
	 * request attribute.
	 */
	public abstract String getRequestQueryStringParameter(String name);

	/**
	 * Returns the session object associated with the current FacesContext.
	 *
	 * @param  create  Flag indicating whether or not a session should be created if it doesn't yet exist.
	 */
	public abstract Object getSession(boolean create);

	/**
	 * Returns the value of the session attribute associated with the specified name.
	 */
	public abstract Object getSessionAttribute(String name);

	/**
	 * Sets the value of the a session attribute using the specified name and value.
	 */
	public abstract void setSessionAttribute(String name, Object value);
}
