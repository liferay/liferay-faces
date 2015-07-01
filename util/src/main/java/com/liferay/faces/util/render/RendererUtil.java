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
package com.liferay.faces.util.render;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.util.component.ComponentUtil;
import com.liferay.faces.util.component.Styleable;


/**
 * @author  Neil Griffin
 */
public class RendererUtil {

	public static void decodeClientBehaviors(FacesContext facesContext, UIComponent uiComponent) {

		if (uiComponent instanceof ClientBehaviorHolder) {

			ClientBehaviorHolder clientBehaviorHolder = (ClientBehaviorHolder) uiComponent;
			Map<String, List<ClientBehavior>> clientBehaviorMap = clientBehaviorHolder.getClientBehaviors();

			Map<String, String> requestParameterMap = facesContext.getExternalContext().getRequestParameterMap();
			String behaviorEvent = requestParameterMap.get("javax.faces.behavior.event");

			if (behaviorEvent != null) {

				List<ClientBehavior> clientBehaviors = clientBehaviorMap.get(behaviorEvent);

				if (clientBehaviors != null) {
					String source = requestParameterMap.get("javax.faces.source");

					if (source != null) {
						String clientId = uiComponent.getClientId(facesContext);

						if (clientId.startsWith(source)) {

							for (ClientBehavior behavior : clientBehaviors) {
								behavior.decode(facesContext, uiComponent);
							}
						}
					}
				}
			}
		}
	}

	public static void encodeStyleable(ResponseWriter responseWriter, Styleable styleable, String... classNames)
		throws IOException {

		StringBuilder allCssClasses = new StringBuilder();
		String cssClasses = ComponentUtil.concatCssClasses(classNames);

		if (cssClasses != null) {
			allCssClasses.append(cssClasses);
			allCssClasses.append(" ");
		}

		String styleClass = styleable.getStyleClass();

		if (styleClass != null) {
			allCssClasses.append(styleClass);
		}

		if (allCssClasses.length() > 0) {
			responseWriter.writeAttribute("class", allCssClasses.toString(), Styleable.STYLE_CLASS);
		}

		String style = styleable.getStyle();

		if (style != null) {
			responseWriter.writeAttribute(Styleable.STYLE, style, Styleable.STYLE);
		}
	}
}
