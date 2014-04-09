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
import com.liferay.faces.util.component.Widget;
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

		Rating rating = (Rating) uiComponent;
		ResponseWriter responseWriter = facesContext.getResponseWriter();

		// Start the opening tag of the component.
		responseWriter.startElement(StringPool.SPAN, uiComponent);
		responseWriter.writeAttribute(StringPool.ID, uiComponent.getClientId(facesContext), StringPool.ID);

		RenderKit renderKit = facesContext.getRenderKit();
		Renderer radioRenderer = renderKit.getRenderer(HtmlSelectOneRadio.COMPONENT_FAMILY, RADIO_RENDERER_TYPE);

		RatingResponseWriter ratingResponseWriter = new RatingResponseWriter(responseWriter,
				rating.getDefaultSelected());

		// Use our own ResponseWriter to filter out stuff that the jsf-api wants to write to the DOM, and also
		// use our ResponseWriter to save interesting things along the way and later ask our ResponseWriter for them,
		// such as an onclick event that our jsf-impl will need.
		facesContext.setResponseWriter(ratingResponseWriter);

		// Delegate rendering of the component to the JSF runtime.
		radioRenderer.encodeBegin(facesContext, uiComponent);
		radioRenderer.encodeChildren(facesContext, uiComponent);
		radioRenderer.encodeEnd(facesContext, uiComponent);

		// Save the onclick for later use in the JavaScript.
		String onClick = ratingResponseWriter.getOnClick();
		facesContext.getAttributes().put(FACES_RUNTIME_ONCLICK, onClick);

		// Save the selectedIndex for later use in the JavaScript.
		Integer selectedIndex = ratingResponseWriter.getSelectedIndex();
		facesContext.getAttributes().put(SELECTED_INDEX, selectedIndex);

		// Save the selectedIndex for later use in the JavaScript.
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
		
		// We need to add an onclick event to the JavaScript component using its "on" method, instead of adding an
		// onclick to some DOM element.
		String ratingObject = ComponentUtil.resolveWidgetVar(facesContext, (Widget) uiComponent);
		encodeLiferayComponent(responseWriter, ratingObject);
		// The above should render something like this: var _1_WAR_showcaseportlet__j_idt6_j_idt19_j_idt22_j_idt23 =
		// Liferay.component('_1_WAR_showcaseportlet__j_idt6_j_idt19_j_idt22_j_idt23');

		responseWriter.write("var defaultSelectedIndex = ");
		responseWriter.write(ratingObject);
		responseWriter.write(".get('selectedIndex');");

		//J-
		// What should we consider when we initialize the value of this component?
		// Contributor			  What value do they want?			   What will we do with this value?
		// ---------------------- -----------------------------------  ---------------------------------------------
		// 1. AlloyUI			  -1								   ignore.
		// 2. JSF				  ''								   use as default.
		// 3. Developer attribute ratingObject.get('defaultSelected')  if defined, use this, but remember it is the number of stars, not the value.
		// 4. Developer EL		  rating.getValue()					   if defined, use this.
		// 5. User event											   no interaction from the user yet, so use the value chosen above.
		//J+

		// 2. JSF
		String hiddenInputValue = StringPool.BLANK;

		// 3. Developer attribute
		String defaultSelectedValue = (String) facesContext.getAttributes().remove(DEFAULT_SELECTED_VALUE);
		if (!facesContext.isPostback()) {
			hiddenInputValue = (defaultSelectedValue == null) ? hiddenInputValue : defaultSelectedValue;
		}

		// 4. and 5. Developer EL or User input
		// make sure that the hiddenValue is set to this rating's value, if any
		ValueHolder valueHolder = (ValueHolder) uiComponent;
		Object value = valueHolder.getValue();
		if (value != null) {
			hiddenInputValue = value.toString();
		}

		// initialize the hidden input generated by AlloyUi to the hiddenInputValue, instead of its default of -1
		String clientId = uiComponent.getClientId(facesContext);
		
		responseWriter.write("document.getElementsByName('");
		responseWriter.write(clientId);
		responseWriter.write("')[0].value = '");
		responseWriter.write(hiddenInputValue);
		responseWriter.write("'; ");

		// Make sure that the rendered rating is correct (i.e. how many stars are filled). The only way that the
		// JavaScript ratingObject would have a selectedIndex at this point is if the defaultSelected attribute is set.
		// Carefully decide if we need to clear the selected rating or if we need to select the user's selected rating.
		Integer selectedIndex = (Integer) facesContext.getAttributes().remove(SELECTED_INDEX);
		
		if (selectedIndex.intValue() == -1) {

			if (facesContext.isPostback()) {

				// this is not the initial render of the component, and the user's selection was to clear it (hence -1),
				// so we will clear it now since there may be a defaultSelected rating showing, and that's not what the
				// user selected
				responseWriter.write(ratingObject);
				responseWriter.write(".clearSelection(); ");
			}
		}
		else {

			// the user has selected their rating, selectedIndex.intValue(), but the rating they selected may have
			// already been rendered as the defaultSelected rating, so if the user's choice has already been rendered,
			// we should not select it again, because that would clear the rendered rating that the user wants.
			responseWriter.write("if(");
			String selectedIndexAsString = selectedIndex.toString();
			responseWriter.write(selectedIndexAsString);
			responseWriter.write("!=defaultSelectedIndex){");
			responseWriter.write(ratingObject);
			responseWriter.write(".select(");
			responseWriter.write(selectedIndexAsString);
			responseWriter.write(StringPool.CLOSE_PARENTHESIS);
			responseWriter.write(StringPool.SEMICOLON);
			responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
		}

		//J-
		// What should we consider when a user clicks on the component?
		// Contributor			  What value do they want?			   What will we do with this value?
		// ---------------------- -----------------------------------  ---------------------------------------------
		// 1. AlloyUI			  event.target.get('value')			   Use this as the value unless it is -1 which is the cancel value.
		// 2. JSF				  ''								   use this value instead of -1 or the cancel value.
		// 3. Developer attribute ratingObject.get('defaultSelected')  ignore, because user input is overriding this.
		// 4. Developer EL		  rating.getValue()					   ignore, because user input is overriding this.
		// 5. User event		  event.target.get('value')			   unless it is the cancel or reset value, use this.
		//J+

		// add an onclick event
		responseWriter.write(ratingObject);
		responseWriter.write(".on('click',function(event){");

		// establish the newValue of the hiddenInput
		responseWriter.write("var newValue=(event.target.get('value')=='-1')?'':event.target.get('value');");

		// set the newValue of the hidden input
		responseWriter.write("document.getElementsByName('");
		responseWriter.write(clientId);
		responseWriter.write("')[0].value = newValue;");

		// write out the jsf onclick event to call, if any
		String onClick = (String) facesContext.getAttributes().remove(FACES_RUNTIME_ONCLICK);
		String hiddenInputNodeJs = "document.getElementsByName('" + clientId + "')[0]";
		responseWriter.write(((onClick == null) ? "" : onClick.replaceFirst("this", hiddenInputNodeJs)));

		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
		responseWriter.write(StringPool.CLOSE_PARENTHESIS);
		responseWriter.write(StringPool.SEMICOLON);

	}

	// This will need to be overridden for all of our input alloy components
	@Override
	public Object getConvertedValue(FacesContext facesContext, UIComponent uiComponent, Object submittedValue)
		throws ConverterException {
		RenderKit renderKit = facesContext.getRenderKit();
		Renderer radioRenderer = renderKit.getRenderer(HtmlSelectOneRadio.COMPONENT_FAMILY, RADIO_RENDERER_TYPE);

		return radioRenderer.getConvertedValue(facesContext, uiComponent, submittedValue);
	}
}
