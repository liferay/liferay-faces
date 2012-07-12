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
package com.liferay.faces.bridge.servlet;

import java.lang.reflect.Method;
import java.util.Set;

import javax.portlet.faces.Bridge;
import javax.portlet.faces.Bridge.PortletPhase;
import javax.portlet.faces.annotation.BridgeRequestScopeAttributeAdded;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;

import com.liferay.faces.bridge.BridgeFactoryFinder;
import com.liferay.faces.bridge.config.BridgeConfig;
import com.liferay.faces.bridge.config.BridgeConfigFactory;
import com.liferay.faces.bridge.context.map.RequestAttributeMap;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * <p>This class is designed to support the {@link BridgeRequestScopeAttributeAdded} annotation. It has to be specified
 * as a listener in the WEB-INF/web.xml descriptor like this:</p>
 * <code>
 * <pre>
    &lt;listener&gt;
        &lt;listener-class&gt;com.liferay.faces.bridge.context.map.BridgeRequestAttributeListener&lt;/listener-class&gt;
    &lt;/listener&gt;
 * </pre>
 * </code>
 *
 * @see     http://issues.liferay.com/browse/FACES-146
 * @author  Neil Griffin
 */
public class BridgeRequestAttributeListener implements ServletRequestAttributeListener {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(BridgeRequestAttributeListener.class);

	/**
	 * This method is called after an attribute is added to the ServletRequest. Note that this should only get called
	 * for remote WSRP portlets. For more info, see: http://issues.liferay.com/browse/FACES-146
	 */
	public void attributeAdded(ServletRequestAttributeEvent servletRequestAttributeEvent) {

		// NOTE: We only care about phases prior to the RENDER_PHASE because we're concerned here about managed beans
		// that get added to the request scope when the BridgeRequestScope begins. We're trying to provide those managed
		// beans with an opportunity to prepare for an unexpected invocation of their methods annotated with
		// @PreDestroy.
		ServletRequest servletRequest = servletRequestAttributeEvent.getServletRequest();
		PortletPhase phase = (PortletPhase) servletRequest.getAttribute(Bridge.PORTLET_LIFECYCLE_PHASE);

		// If this is taking place within a PortletRequest handled by the bridge in any phase prior to the
		// RENDER_PHASE, then
		if ((phase != null) && (phase != PortletPhase.RENDER_PHASE)) {

			// If the attribute being added is not excluded, then invoke all methods on the attribute value (class
			// instance) that are annotated with the BridgeRequestScopeAttributeAdded annotation.
			BridgeConfigFactory bridgeConfigFactory = (BridgeConfigFactory) BridgeFactoryFinder.getFactory(
					BridgeConfigFactory.class);
			BridgeConfig bridgeConfig = bridgeConfigFactory.getBridgeConfig();

			String attributeName = servletRequestAttributeEvent.getName();
			Set<String> excludedRequestScopeAttributes = bridgeConfig.getExcludedRequestAttributes();

			if (!excludedRequestScopeAttributes.contains(attributeName)) {

				Object attributeValue = servletRequestAttributeEvent.getValue();
				logger.trace("Attribute added name=[{0}] value=[{1}]", attributeName, attributeValue);

				if (attributeValue != null) {
					Method[] methods = attributeValue.getClass().getMethods();

					if (methods != null) {

						for (Method method : methods) {

							if (method != null) {

								if (method.isAnnotationPresent(BridgeRequestScopeAttributeAdded.class)) {

									try {
										method.invoke(attributeValue, new Object[] {});
									}
									catch (Exception e) {
										logger.error(e);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * This method is called after an attribute is removed from the ServletRequest. One might expect that this is a good
	 * time to call any managed bean methods annotated with @BridgePreDestroy, but that actually takes place in the
	 * Bridge's {@link RequestAttributeMap#remove(Object)} method. Note that this should only get called for remote WSRP
	 * portlets. For more info, see: http://issues.liferay.com/browse/FACES-146
	 */
	public void attributeRemoved(ServletRequestAttributeEvent servletRequestAttributeEvent) {
		String attributeName = servletRequestAttributeEvent.getName();
		Object attributeValue = servletRequestAttributeEvent.getValue();
		logger.trace("Attribute removed name=[{0}] value=[{1}]", attributeName, attributeValue);
	}

	/**
	 * This method is called after an attribute is replaced in the ServletRequest. One might expect that this is a good
	 * time to call any managed bean methods annotated with @BridgePreDestroy, but that actually takes place in the
	 * Bridge's {@link RequestAttributeMap#remove(Object)} method. Note that this should only get called for remote WSRP
	 * portlets. For more info, see: http://issues.liferay.com/browse/FACES-146
	 */
	public void attributeReplaced(ServletRequestAttributeEvent servletRequestAttributeEvent) {
		String attributeName = servletRequestAttributeEvent.getName();
		Object attributeValue = servletRequestAttributeEvent.getValue();
		logger.trace("Attribute replaced name=[{0}] value=[{1}]", attributeName, attributeValue);
	}

}
