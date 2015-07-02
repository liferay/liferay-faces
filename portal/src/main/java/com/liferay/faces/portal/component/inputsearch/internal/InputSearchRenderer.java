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
package com.liferay.faces.portal.component.inputsearch.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.el.MethodExpression;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.behavior.AjaxBehavior;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.MethodExpressionActionListener;
import javax.faces.event.PreRenderComponentEvent;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.portal.component.inputsearch.InputSearch;
import com.liferay.faces.portal.render.internal.DelayedPortalTagRenderer;
import com.liferay.faces.util.event.PreRenderComponentEventListener;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.render.RendererUtil;

import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import com.liferay.taglib.ui.InputSearchTag;


/**
 * This class is a renderer for the {@link com.liferay.faces.portal.component.inputsearch.InputSearch} component. The
 * component has unique requirements in the sense that the corresponding JSP-based {@link
 * com.liferay.taglib.ui.InputSearchTag} renders an <input type="text">...</input> and also a <button></button> for
 * submission. From a JSF perspective, this creates a multiple-inheritance dilemma. For example, should the component
 * component extend {@link javax.faces.component.UIInput} or {@link javax.faces.component.UICommand}? The solution is to
 * have the component extend {@link javax.faces.component.UIInput}, but to dynamically create an {@link
 * javax.faces.component.html.HtmlCommandButton} child that can participate in the processing of JSF events. This design
 * is essentially a 100% Java equivalent of a JSF composite component.
 *
 * @author  Juan Gonzalez
 */
//J-
@FacesRenderer(componentFamily = InputSearch.COMPONENT_FAMILY, rendererType = InputSearch.RENDERER_TYPE)
//J+
public class InputSearchRenderer extends DelayedPortalTagRenderer<InputSearch, InputSearchTag>
	implements PreRenderComponentEventListener {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(InputSearchRenderer.class);

	@Override
	public void decode(FacesContext facesContext, UIComponent uiComponent) {

		RendererUtil.decodeClientBehaviors(facesContext, uiComponent);

		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, String> requestParameterMap = externalContext.getRequestParameterMap();
		String clientId = uiComponent.getClientId();
		String submittedValue = requestParameterMap.get(clientId);
		InputSearch inputSearch = cast(uiComponent);
		inputSearch.setSubmittedValue(submittedValue);
	}

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.startElement("div", uiComponent);
		responseWriter.writeAttribute("class", "form-search", "class");

		// Delegate to PortalTagRenderer so that the JSP tag output will get encoded.
		super.encodeBegin(facesContext, uiComponent);
	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Delegate to PortalTagRenderer so that the JSP tag output will get encoded.
		super.encodeEnd(facesContext, uiComponent);

		// Encode the closing </div> element
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.endElement("div");
	}

	@Override
	public InputSearchTag newTag() {
		return new InputSearchTag();
	}

	@Override
	public void processEvent(PreRenderComponentEvent preRenderComponentEvent) throws AbortProcessingException {
		dynamicallyAddChildComponents((InputSearch) preRenderComponentEvent.getComponent());
	}

	@Override
	protected InputSearch cast(UIComponent uiComponent) {
		return (InputSearch) uiComponent;
	}

	protected void changeClientBehaviorIds(ClientBehavior clientBehavior, String id) {

		// Determine whether or not the developer added an f:ajax child tag.
		if (clientBehavior instanceof AjaxBehavior) {

			// Add the element Id to the list of components that participate in the "execute" portion
			// of the JSF partial request lifecycle.
			AjaxBehavior ajaxBehavior = (AjaxBehavior) clientBehavior;
			Collection<String> execute = new ArrayList<String>();
			execute.addAll(ajaxBehavior.getExecute());

			if (execute.contains("@this") || !execute.contains(id)) {
				execute.add(id);
				ajaxBehavior.setExecute(execute);
			}

			// Add the element Id to the list of components that participate in the "render" portion
			// of the JSF partial request lifecycle.
			Collection<String> render = new ArrayList<String>();
			render.addAll(ajaxBehavior.getRender());

			if (render.contains("@this")) {
				render.remove("@this");
				render.add(id);
				ajaxBehavior.setRender(render);
			}
		}
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
		// no-op
	}

	// The purpose of this method is to dynamically add HtmlInputText and HtmlCommandButton JSF child components. These
	// children will render child <input>..</input> elements with attributes like "id","name", "onclick", etc. that need
	// to be copied to the HTML elements that are rendered by html/taglib/ui/input_search/page.jsp
	protected void dynamicallyAddChildComponents(InputSearch inputSearch) {

		// If the HtmlInputText and HtmlCommandButton JSF child components are not already present in the component
		// tree, then
		List<UIComponent> children = inputSearch.getChildren();

		if (children.size() == 0) {

			// Dynamically create the HtmlInputText JSF child
			FacesContext facesContext = FacesContext.getCurrentInstance();
			Application application = facesContext.getApplication();

			HtmlInputText htmlInputText = (HtmlInputText) application.createComponent(HtmlInputText.COMPONENT_TYPE);
			children.add(htmlInputText);

			// Dynamically create the HtmlCommandButton JSF child and set JSF attributes accordingly.
			HtmlCommandButton htmlCommandButton = (HtmlCommandButton) application.createComponent(
					HtmlCommandButton.COMPONENT_TYPE);
			children.add(htmlCommandButton);

			MethodExpression action = inputSearch.getAction();

			if (action != null) {
				htmlCommandButton.setActionExpression(action);
			}

			MethodExpression actionListener = inputSearch.getActionListener();

			if (actionListener != null) {
				htmlCommandButton.addActionListener(new MethodExpressionActionListener(actionListener));
			}

			boolean showButton = inputSearch.isShowButton();

			if (!showButton) {
				htmlCommandButton.setRendered(false);
			}

			// For each client behavior associated with the InputSearch component:
			Map<String, List<ClientBehavior>> clientBehaviours = inputSearch.getClientBehaviors();
			Set<Map.Entry<String, List<ClientBehavior>>> entries = clientBehaviours.entrySet();
			boolean hasButtonAjaxBehavior = false;

			for (Map.Entry<String, List<ClientBehavior>> mapEntry : entries) {

				// If the developer did not specify an event, then that means the "action" (default event name for
				// HtmlCommandButton) is implied.
				List<ClientBehavior> clientBehaviors = mapEntry.getValue();
				String eventName = mapEntry.getKey();
				String defaultEventName = inputSearch.getDefaultEventName();

				// If processing the "action" event, then
				if (eventName.equals(defaultEventName)) {

					// For each client behavior associated with the "action" event:
					for (ClientBehavior clientBehavior : clientBehaviors) {

						hasButtonAjaxBehavior = true;

						changeClientBehaviorIds(clientBehavior, inputSearch.getId());

						htmlCommandButton.addClientBehavior(defaultEventName, clientBehavior);
					}
				}

				// Otherwise, add the client behavior to the HtmlInputText JSF child so that attributes will be rendered
				// by the renderer.
				else {

					for (ClientBehavior clientBehavior : clientBehaviors) {
						changeClientBehaviorIds(clientBehavior, inputSearch.getId());

						htmlInputText.addClientBehavior(eventName, clientBehavior);
					}
				}
			}

			// If the developer has specified an action/actionListener or has enabled Ajax, then log an error indicating
			// that the developer must set showButton="true".
			if (!showButton && (hasButtonAjaxBehavior || (action != null) || (actionListener != null))) {
				logger.error("Set showButton=\"true\" when using action/actionListener or f:ajax.");
			}

			inputSearch.markInitialState();
		}

	}

	@Override
	public String getChildInsertionMarker() {
		return "</div>";
	}

	@Override
	protected StringBuilder getMarkup(UIComponent uiComponent, StringBuilder markup) throws Exception {

		//J-
		// NOTE: The specified markup looks like the following (HTML comments added for clarity):
		//
		// <!-- Opening <div> rendered by html/taglib/ui/input_search/page.jsp -->
		// <div class="input-append">
		//
		//	 <!-- Input text field rendered by html/taglib/ui/input_search/page.jsp -->
		//	 <input class="search-query span9" id="...:jid42" name="..." placeholder="" type="text" value="" />
		//
		//	 <!-- Search button rendered by html/taglib/ui/input_search/page.jsp -->
		//	 <button class="btn" type="submit">Search</button>
		//
		//	 <!-- HtmlInputText (dynamically added JSF child component) -->
		//	 <input type="text" name="...:htmlInputText" />
		//
		//	 <!-- HtmlCommandButton (dynamically added JSF child component) -->
		//	 <input type="submit" name="...:htmlCommandButton" value="" />
		//
		// <!-- Closing </div> rendered by html/taglib/ui/input_search/page.jsp -->
		// </div>
		//J+

		// Parse the generated markup as an XML document.
		InputSearch inputSearch = cast(uiComponent);
		Document document = SAXReaderUtil.read(markup.toString());
		Element rootElement = document.getRootElement();

		// Locate the first/main input element in the XML document. This is the one that will contain contain the value
		// that will be submitted in the postback and received by the decode(FacesContext, UIComponent) method).
		String xpathInput = "//input[contains(@id, '" + uiComponent.getClientId() + "')]";
		Element mainInputElement = (Element) rootElement.selectSingleNode(xpathInput);

		if (mainInputElement != null) {

			// Copy the value attribute of the InputSearch component to the first input element in the XML document.
			mainInputElement.attribute("value").setValue((String) inputSearch.getValue());

			// Locate the dynamically added HtmlInputText and HtmlCommandButton child components.
			String xpathInputs = "//input[@type='text']";
			List<Node> inputElements = rootElement.selectNodes(xpathInputs);

			if ((inputElements != null) && (inputElements.size() == 2)) {

				// Copy each "on" attribute name/value pairs from the HtmlInputText to the first input element in
				// the XML document. This will enable all of the AjaxBehavior events like keyup/keydown to work.
				Element htmlInputTextElement = (Element) inputElements.get(1);
				Iterator<Attribute> attributeIterator = htmlInputTextElement.attributeIterator();

				while (attributeIterator.hasNext()) {
					Attribute attribute = attributeIterator.next();
					String attributeName = attribute.getName();

					if (attributeName.startsWith("on")) {
						mainInputElement.addAttribute(attributeName, attribute.getValue());
					}
				}

				// Remove the HtmlInputText <input> from the XML document so that only one text field is rendered.
				htmlInputTextElement.getParent().remove(htmlInputTextElement);
			}
		}

		// Locate the HtmlCommandButton in the XML document.
		List<UIComponent> children = uiComponent.getChildren();
		HtmlCommandButton htmlCommandButton = (HtmlCommandButton) children.get(1);
		String htmlCommandButtonClientId = htmlCommandButton.getClientId();

		// Note that if there is an AjaxBehavior, then the rendered HtmlCommandButton can be located in the XML document
		// via the name attribute. Otherwise it can be located in the XML document via the id attribute.
		String htmlCommandButtonXPath = "//input[contains(@name,'" + htmlCommandButtonClientId +
			"') and @type='submit']";
		Element htmlCommandButtonElement = (Element) rootElement.selectSingleNode(htmlCommandButtonXPath);

		if (htmlCommandButtonElement == null) {
			htmlCommandButtonXPath = "//input[contains(@id,'" + htmlCommandButtonClientId + "') and @type='submit']";
			htmlCommandButtonElement = (Element) rootElement.selectSingleNode(htmlCommandButtonXPath);
		}

		if (htmlCommandButtonElement != null) {

			// Locate the <button> element in the XML document that was rendered by page.jsp
			Element buttonElement = (Element) rootElement.selectSingleNode("//button[@type='submit']");

			if (buttonElement != null) {

				// Copy attributes found on the HtmlCommandButton <input> element to the <button> element that was
				// rendered by page.jsp
				Attribute onClickAttr = htmlCommandButtonElement.attribute("onclick");

				if (onClickAttr != null) {
					buttonElement.addAttribute("onclick", onClickAttr.getValue());
				}

				Attribute nameAttr = htmlCommandButtonElement.attribute("name");

				if (nameAttr != null) {
					buttonElement.addAttribute("name", nameAttr.getValue());
				}

				Attribute idAttr = htmlCommandButtonElement.attribute("id");

				if (idAttr != null) {
					buttonElement.addAttribute("id", idAttr.getValue());
				}

				// Remove the HtmlCommandButton <input> from the XML document so that only one button is rendered.
				htmlCommandButtonElement.getParent().remove(htmlCommandButtonElement);
			}
		}

		// Returned the modified verson of the specified markup.
		return new StringBuilder(rootElement.asXML());
	}
}
