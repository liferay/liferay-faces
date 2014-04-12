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
package com.liferay.faces.alloy.component.rating;

import java.io.IOException;

import javax.faces.application.ResourceDependency;
import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.component.html.HtmlSelectOneRadio;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.ConverterException;
import javax.faces.render.FacesRenderer;
import javax.faces.render.RenderKit;
import javax.faces.render.Renderer;

import com.liferay.faces.util.component.ComponentUtil;
import com.liferay.faces.util.component.LiferayComponent;
import com.liferay.faces.util.helper.StringHelper;
import com.liferay.faces.util.lang.StringPool;


/**
 * @author  Vernon Singleton
 */
@FacesRenderer(componentFamily = Rating.COMPONENT_FAMILY, rendererType = Rating.RENDERER_TYPE)
@ResourceDependency(library = "liferay-faces-alloy", name = "liferay.js")
public class RatingRenderer extends RatingRendererBase {

	// Private Constants
	private static final String RADIO_RENDERER_TYPE = "javax.faces.Radio";
	private static final String FACES_RUNTIME_ONCLICK = "facesRuntimeOnClick";
	private static final String SELECTED_INDEX = "selectedIndex";
	private static final String DEFAULT_SELECTED_VALUE = "defaultSelectedValue";

	@Override
	public void decode(FacesContext facesContext, UIComponent uiComponent) {
		RenderKit renderKit = facesContext.getRenderKit();
		Renderer radioRenderer = renderKit.getRenderer(HtmlSelectOneRadio.COMPONENT_FAMILY, RADIO_RENDERER_TYPE);
		radioRenderer.decode(facesContext, uiComponent);
	}

	@Override
	protected void encodeHTMLBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();

		// Start the opening tag of the component.
		responseWriter.startElement(StringPool.SPAN, uiComponent);
		responseWriter.writeAttribute(StringPool.ID, uiComponent.getClientId(facesContext), StringPool.ID);

		// Use our own ResponseWriter to filter out stuff that the jsf-api wants to write to the DOM, and also use our
		// ResponseWriter to save interesting things along the way and later ask our ResponseWriter for them, such as an
		// onclick event that our jsf-impl will need.
		RatingComponent ratingComponent = (RatingComponent) uiComponent;
		String defaultSelected = StringHelper.toString(ratingComponent.getDefaultSelected(), null);
		RatingResponseWriter ratingResponseWriter = new RatingResponseWriter(responseWriter, defaultSelected);
		facesContext.setResponseWriter(ratingResponseWriter);

		// Delegate rendering of the component to the JSF runtime.
		RenderKit renderKit = facesContext.getRenderKit();
		Renderer radioRenderer = renderKit.getRenderer(HtmlSelectOneRadio.COMPONENT_FAMILY, RADIO_RENDERER_TYPE);
		radioRenderer.encodeBegin(facesContext, uiComponent);
		radioRenderer.encodeChildren(facesContext, uiComponent);
		radioRenderer.encodeEnd(facesContext, uiComponent);

		// Save the onclick for later use in the JavaScript.
		String onClick = ratingResponseWriter.getOnClick();
		facesContext.getAttributes().put(FACES_RUNTIME_ONCLICK, onClick);

		// Save the selectedIndex for later use in the JavaScript.
		Long selectedIndex = ratingResponseWriter.getSelectedIndex();
		facesContext.getAttributes().put(SELECTED_INDEX, selectedIndex);

		// Save the defaultSelectedValue for later use in the JavaScript.
		Object defaultSelectedValue = ratingResponseWriter.getDefaultSelectedValue();
		facesContext.getAttributes().put(DEFAULT_SELECTED_VALUE, defaultSelectedValue);

		// Restore the original ResponseWwriter.
		facesContext.setResponseWriter(responseWriter);
	}

	@Override
	protected void encodeHTMLEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		ResponseWriter responseWriter = facesContext.getResponseWriter();

		// Close the component tag.
		responseWriter.endElement(StringPool.SPAN);
	}

	@Override
	protected void encodeJavaScriptCustom(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();

		String varName = ComponentUtil.getVarName(facesContext, (LiferayComponent) uiComponent);
		encodeLiferayComponent(responseWriter, varName);
		// The above should render something like this: var _1_WAR_showcaseportlet__j_idt6_j_idt19_j_idt22_j_idt23 =
		// Liferay.component('_1_WAR_showcaseportlet__j_idt6_j_idt19_j_idt22_j_idt23');

		//J-
		// What should we consider when we initialize the value of this component?
		//
		// Contributor             What value do they want?             What will we do with this value?
		// ----------------------  -----------------------------------  ---------------------------------------------
		// 1. AlloyUI              -1                                   Ignore.
		//
		// 2. JSF                  ""                                   Use as default.
		//
		// 3. Developer Attribute  Liferay.component(liferayKey).       If defined, use this, but remember it is the
		//                          .get('defaultSelected')             number of stars, not the value.
		//
		// 4. Developer EL         rating.getValue()                    If defined, use this.
		//
		// 5. User Event                                                Since no interaction from the user yet, use
		//                                                              the value chosen above.
		//J+

		// 2. JSF
		String hiddenInputValue = StringPool.BLANK;

		// 3. Developer attribute
		String defaultSelectedValue = (String) facesContext.getAttributes().remove(DEFAULT_SELECTED_VALUE);

		// If this is the initial render of the page, then the value of the hidden input should be set to the
		// defaultSelectedValue
		if (!facesContext.isPostback()) {

			if (defaultSelectedValue != null) {
				hiddenInputValue = defaultSelectedValue;
			}
		}

		// 4. and 5. If the developer EL or user input is specified, then the value of the hidden input field should be
		// set to this value of the rating component
		ValueHolder valueHolder = (ValueHolder) uiComponent;
		Object value = valueHolder.getValue();

		if (value != null) {
			hiddenInputValue = value.toString();
		}

		// Initialize the hidden input generated by AlloyUI to the hiddenInputValue, instead of its default of -1.
		String clientId = uiComponent.getClientId(facesContext);

		responseWriter.write("document.getElementsByName('");
		responseWriter.write(clientId);
		responseWriter.write("')[0].value='");
		responseWriter.write(hiddenInputValue);
		responseWriter.write(StringPool.APOSTROPHE);
		responseWriter.write(StringPool.SEMICOLON);

		// Make sure that the rendered rating is correct (i.e. how many stars are filled). The only way that the
		// client-side LiferayComponent would have a selectedIndex at this point is if the defaultSelected attribute
		// is set. Carefully decide if we need to clear the selected rating or if we need to select the user's selected
		// rating.
		Long selectedIndex = (Long) facesContext.getAttributes().remove(SELECTED_INDEX);

		// If the selectedIndex is -1, then that means one of two things ... 1. the user selected no value (or cleared
		// the stars), which must have been in a postback. 2. no selected index was found because this is the initial
		// render of the page, and no valid default value was set.
		if (selectedIndex.intValue() == RatingResponseWriter.NO_SELECTION_INDEX) {

			// This is case 1.  the user selected to clear the value.
			if (facesContext.isPostback()) {
				responseWriter.write(varName);
				responseWriter.write(".clearSelection();");
			}

			// Otherwise, this is case 2. Since there has been no user interaction yet, then we do not need to clear
			// anything, or select anything, since the Alloy component will simply be able to render itself correctly.
			else {
				// no operation needed.
			}
		}

		// Otherwise, JSF says that this component has a value, because JSF rendered one of the input type="radio" as
		// "checked" and its selectedIndex is not -1.  There are two possible reasons for this:
		// 1. The model has a default value coming through on initial render
		// 2. The user clicked on a star, this is a postback, and we need the component to have that many stars filled.
		//
		// This is a special case where a defaultSelectedIndex has been established, and Alloy renders stars already
		// filled to the defaultSelected input, but the user has selected a different number of stars than the
		// defaultSelected, so we need to "select" the correct number of stars to be filled. If the defaultSelected is
		// the same as the chosen selectedIndex, we do not want to select it again, since alloy has already filled the
		// correct number of stars.  If we did select it again, it would clear the stars ... not showing what the user
		// selected.
		else {

			String selectedIndexAsString = selectedIndex.toString();

			responseWriter.write("var defaultSelectedIndex=");
			responseWriter.write(varName);
			responseWriter.write(".get('selectedIndex');");

			responseWriter.write("if(");
			responseWriter.write(selectedIndexAsString);
			responseWriter.write("!=defaultSelectedIndex){");
			responseWriter.write(varName);
			responseWriter.write(".select(");
			responseWriter.write(selectedIndexAsString);
			responseWriter.write(StringPool.CLOSE_PARENTHESIS);
			responseWriter.write(StringPool.SEMICOLON);
			responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
		}

		//J-
		// What should we consider when a user clicks on the component?
		//
		// Contributor             What value do they want?             What will we do with this value?
		// ----------------------  -----------------------------------  ---------------------------------------------
		// 1. AlloyUI              event.target.get('value')            Use this as the value unless it is -1 which is
		//                                                              the cancel value.
		//
		// 2. JSF                  ''                                   Use this value instead of -1 or the cancel
		//                                                              value.
		//
		// 3. Developer Attribute  Liferay.component('liferayKey')      Ignore because user input is overriding this.
		//                           .get('defaultSelected')
		//
		// 4. Developer EL         rating.getValue()                    Ignore because user input is overriding this.
		//
		// 5. User Event           event.target.get('value')            Use this unless it is the cancel or reset
		//                                                              value.
		//J+

		// Start encoding the onclick event.
		responseWriter.write(varName);
		responseWriter.write(".on('click',function(event){");

		// Within the onclick event, establish the newValue of the hiddenInput.
		responseWriter.write("var newValue=(event.target.get('value')=='-1')?'':event.target.get('value');");

		// Within the onclick event, set the newValue of the hidden input.
		responseWriter.write("document.getElementsByName('");
		responseWriter.write(clientId);
		responseWriter.write("')[0].value=newValue;");

		// Within the onclick event, write out the jsf onclick event to call, that was captured by the ResponseWriter,
		// if any.
		String onClick = (String) facesContext.getAttributes().remove(FACES_RUNTIME_ONCLICK);
		String hiddenInputNodeJs = "document.getElementsByName('" + clientId + "')[0]";
		if (onClick != null) {
			responseWriter.write(onClick.replaceFirst("this", hiddenInputNodeJs));
		}
		
		// Finish encoding the onclick event.
		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
		responseWriter.write(StringPool.CLOSE_PARENTHESIS);
		responseWriter.write(StringPool.SEMICOLON);

	}

	// This will need to be overridden for all of our input components.
	@Override
	public Object getConvertedValue(FacesContext facesContext, UIComponent uiComponent, Object submittedValue)
		throws ConverterException {
		RenderKit renderKit = facesContext.getRenderKit();
		Renderer radioRenderer = renderKit.getRenderer(HtmlSelectOneRadio.COMPONENT_FAMILY, RADIO_RENDERER_TYPE);

		return radioRenderer.getConvertedValue(facesContext, uiComponent, submittedValue);
	}
}
