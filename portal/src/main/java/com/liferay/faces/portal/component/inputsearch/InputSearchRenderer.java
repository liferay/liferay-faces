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
package com.liferay.faces.portal.component.inputsearch;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.portal.render.internal.DelayedPortalTagRenderer;

import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import com.liferay.taglib.ui.InputSearchTag;


/**
 * @author  Juan Gonzalez
 */
//J-
@FacesRenderer(componentFamily = InputSearch.COMPONENT_FAMILY, rendererType = InputSearch.RENDERER_TYPE)
//J+
public class InputSearchRenderer extends DelayedPortalTagRenderer<InputSearch, InputSearchTag> {

	public static final String ACTION = "action";
	public static final String ACTION_LISTENER = "actionListener";

	@Override
	public void decode(FacesContext facesContext, UIComponent uiComponent) {
		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, String> requestParameterMap = externalContext.getRequestParameterMap();
		String clientId = uiComponent.getClientId();

		String submittedValue = requestParameterMap.get(clientId);

		InputSearch inputSearch = cast(uiComponent);
		inputSearch.setSubmittedValue(submittedValue);
	}

	@Override
	public InputSearchTag newTag() {
		return new InputSearchTag();
	}

	@Override
	protected InputSearch cast(UIComponent uiComponent) {
		return (InputSearch) uiComponent;
	}

	@Override
	protected void copyFrameworkAttributes(FacesContext facesContext, InputSearch inputSearch,
		InputSearchTag inputSearchTag) {

		inputSearchTag.setCssClass(inputSearch.getStyleClass());
		inputSearchTag.setId(inputSearch.getClientId());
		inputSearchTag.setName(inputSearch.getClientId());
		inputSearchTag.setAutoFocus(inputSearch.isAutoFocus());

		if (inputSearch.getButtonLabel() != null) {
			inputSearchTag.setButtonLabel(inputSearch.getButtonLabel());
		}

		if (inputSearch.getPlaceholder() != null) {
			inputSearchTag.setPlaceholder(inputSearch.getPlaceholder());
		}

		inputSearchTag.setShowButton(inputSearch.isShowButton());

		if (inputSearch.getTitle() != null) {
			inputSearchTag.setTitle(inputSearch.getTitle());
		}
	}

	@Override
	protected void copyNonFrameworkAttributes(FacesContext facesContext, InputSearch u, InputSearchTag t) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getChildInsertionMarker() {
		return "</div>";
	}

	@Override
	protected StringBuilder getMarkup(UIComponent uiComponent, StringBuilder markup) throws Exception {

		// Here we process the portal generated markup and add the JSF needed markup accordingly
		// so we can have best of both worlds: portal and JSF

		InputSearch inputSearch = cast(uiComponent);
		StringBuilder createdString = markup;

		Document document = SAXReaderUtil.read(createdString.toString());
		Element rootElement = document.getRootElement();

		String xpathInput = "//input[contains(@id, '" + uiComponent.getClientId() + "')]";
		Element inputElement = (Element) rootElement.selectSingleNode(xpathInput);

		if (inputElement != null) {

			// Move some attributes from HtmlInputText to the original one
			inputElement.attribute("value").setValue((String) inputSearch.getValue());

			String xpathInputs = "//input[@type='text']";
			List<Node> inputElements = rootElement.selectNodes(xpathInputs);

			if ((inputElements != null) && (inputElements.size() == 2)) {
				Element secondInputText = (Element) inputElements.get(1);
				Iterator<Attribute> attributeIterator = secondInputText.attributeIterator();

				while (attributeIterator.hasNext()) {
					Attribute attribute = attributeIterator.next();
					String attributeName = attribute.getName();

					if (attributeName.startsWith("on")) {
						inputElement.addAttribute(attributeName, attribute.getValue());
					}
				}

				secondInputText.getParent().remove(secondInputText);
			}
		}

		HtmlCommandButton commandButton = (HtmlCommandButton) uiComponent.getChildren().get(1);
		String buttonClientId = commandButton.getClientId();

		String xpathInputButton = "//input[contains(@name,'" + buttonClientId + "') and @type='submit']";

		Element inputButton = (Element) rootElement.selectSingleNode(xpathInputButton);

		// Depending if there is any AjaxBehaviour the button would have an id or name
		if (inputButton == null) {
			xpathInputButton = "//input[contains(@id,'" + buttonClientId + "') and @type='submit']";
			inputButton = (Element) rootElement.selectSingleNode(xpathInputButton);
		}

		String xpathButton = "//button[@type='submit']";
		Element button = (Element) rootElement.selectSingleNode(xpathButton);

		if ((inputButton != null) && (button != null)) {
			// We populate some button attributes based on JSF generated HtmlCommandButton

			Attribute onClickAttr = inputButton.attribute("onclick");

			if (onClickAttr != null) {
				button.addAttribute("onclick", onClickAttr.getValue());
			}

			Attribute nameAttr = inputButton.attribute("name");

			if (nameAttr != null) {
				button.addAttribute("name", nameAttr.getValue());
			}

			Attribute idAttr = inputButton.attribute("id");

			if (idAttr != null) {
				button.addAttribute("id", idAttr.getValue());
			}

			inputButton.getParent().remove(inputButton);
		}

		createdString = new StringBuilder(1);

		createdString.append(rootElement.asXML());

		uiComponent.getAttributes().put(InputSearch.CHILD_COMPONENTS_ADDED, Boolean.TRUE);

		return createdString;
	}
}
