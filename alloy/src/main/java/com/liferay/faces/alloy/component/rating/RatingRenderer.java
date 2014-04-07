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
	
	
	protected static final String RADIO_RENDERER_TYPE = "javax.faces.Radio";
	protected static final String FACES_RUNTIME_ONCLICK = "facesRuntimeOnClick";
	protected static final String SELECTED_INDEX = "selectedIndex";
	protected static final String DEFAULT_SELECTED_VALUE = "defaultSelectedValue";
	
	@Override
	protected void encodeJavaScriptMain(FacesContext facesContext,
			UIComponent uiComponent) throws IOException {
		// TODO Auto-generated method stub
		super.encodeJavaScriptMain(facesContext, uiComponent);
	}
	
	// This will need to be overridden for all of our input alloy components
	@Override
	public Object getConvertedValue(FacesContext facesContext,
			UIComponent uiComponent, Object submittedValue)
			throws ConverterException {
		
		RenderKit renderKit = facesContext.getRenderKit();
		Renderer radioRenderer = renderKit.getRenderer(HtmlSelectOneRadio.COMPONENT_FAMILY, RADIO_RENDERER_TYPE);
		return radioRenderer.getConvertedValue(facesContext, uiComponent, submittedValue);
		
//		return super.getConvertedValue(context, component, submittedValue);
	}

	@Override
    protected void encodeHTMLBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		
		Rating rating = (Rating) uiComponent;
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		
		// start the opening tag of the component
		responseWriter.startElement("span", uiComponent);
		responseWriter.writeAttribute(StringPool.ID, uiComponent.getClientId(facesContext), StringPool.ID);
		
		RenderKit renderKit = facesContext.getRenderKit();
		Renderer radioRenderer = renderKit.getRenderer(HtmlSelectOneRadio.COMPONENT_FAMILY, RADIO_RENDERER_TYPE);
		
		RatingResponseWriter ratingResponseWriter = new RatingResponseWriter(responseWriter, rating.getDefaultSelected());
		
		// Use our own ResponseWriter to filter out stuff that the jsf-api wants to write to the DOM, and also
		// use our ResponseWriter to save interesting things along the way and later ask our ResponseWriter for them,
		// such as an onclick event that our jsf-impl will need
		facesContext.setResponseWriter(ratingResponseWriter);
		
		radioRenderer.encodeBegin(facesContext, uiComponent);
		radioRenderer.encodeChildren(facesContext, uiComponent);
		radioRenderer.encodeEnd(facesContext, uiComponent);
		
		// save the onclick for later use in the JavaScript
		String onClick = ratingResponseWriter.getOnClick();
		facesContext.getAttributes().put(FACES_RUNTIME_ONCLICK, onClick);
		
		// save the selectedIndex for later use in the JavaScript
		Integer selectedIndex = ratingResponseWriter.getSelectedIndex();
		facesContext.getAttributes().put(SELECTED_INDEX, selectedIndex);

		// save the selectedIndex for later use in the JavaScript
		Object defaultSelectedValue = ratingResponseWriter.getDefaultSelectedValue();
		facesContext.getAttributes().put(DEFAULT_SELECTED_VALUE, defaultSelectedValue);
		
		// Stop using our own ResponseWwriter
		facesContext.setResponseWriter(responseWriter);
		
	}

	@Override
    protected void encodeHTMLEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		// close the opening tag of the component
		responseWriter.endElement("span");
	}
	
	@Override
	protected void encodeJavaScriptCustom(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		Rating rating = (Rating) uiComponent;
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		
		String clientId = uiComponent.getClientId(facesContext);
		
		String defaultSelectedValue = (String) facesContext.getAttributes().remove(DEFAULT_SELECTED_VALUE);
		Integer selectedIndex = (Integer) facesContext.getAttributes().remove(SELECTED_INDEX);
		String onClick = (String) facesContext.getAttributes().remove(FACES_RUNTIME_ONCLICK);
		Object value = rating.getValue();
		
		// We need to add an onclick event to the JavaScript component using its "on" method, instead of adding an onclick to some DOM element.
		String ratingObject = ComponentUtil.resolveWidgetVar(facesContext, (Widget) uiComponent);
		encodeLiferayComponent(responseWriter, ratingObject);
		// The above should render something like this:
		// var _1_WAR_showcaseportlet__j_idt6_j_idt19_j_idt22_j_idt23 = Liferay.component('_1_WAR_showcaseportlet__j_idt6_j_idt19_j_idt22_j_idt23');
		
		responseWriter.write("var defaultSelectedIndex = " + ratingObject + ".get('selectedIndex');" + StringPool.NEW_LINE);
		
		// What should I consider when I initialize the value?
		// Contributor            What value do they want?             What will we do with this value?
		// ---------------------- -----------------------------------  ---------------------------------------------
		// 1. AlloyUI             -1                                   ignore.
		// 2. JSF                 ''                                   use as default.
		// 3. Developer attribute ratingObject.get('defaultSelected')  if defined, use this, but remember it is the number of stars, not the value.
		// 4. Developer EL        rating.getValue()                    if defined, use this.
		// 5. User event                                               no interaction from the user yet, so use the value chosen above.
		
		// 2. JSF
		String hiddenInputValue = StringPool.BLANK;
		
		// 3. Developer attribute
		if ( ! facesContext.isPostback()) {
			hiddenInputValue = (defaultSelectedValue == null) ? hiddenInputValue : defaultSelectedValue;
		}
		
		// 4. 5. Developer EL or User input
		// make sure that the hiddenValue is set to this rating's value, if any
		hiddenInputValue = (value == null) ? hiddenInputValue : value.toString();
		
		// initialize the hidden input generated by AlloyUi to the hiddenInputValue, instead of its default of -1
		responseWriter.write("document.getElementsByName('" + clientId + "')[0].value = '" + hiddenInputValue + "';" + StringPool.NEW_LINE);
		
		// Make sure that the rendered rating is correct (i.e. how many stars are filled).
		// The only way that the JavaScript ratingObject would have a selectedIndex at this point is if it has a defaultSelected attribute set.
		// Carefully decide if we need to clear the selected rating or if we need to select the user's selected rating. 
		if (selectedIndex.intValue() == -1) {
			if (facesContext.isPostback()) {
				// this is not the initial render of the component, and the user's selection was to clear it (hence -1), so 
				// we will clear it now since there may be a defaultSelected rating showing, and that's not what the user selected
				responseWriter.write(ratingObject + ".clearSelection();" + StringPool.NEW_LINE);
			}
		} else {
			// the user has selected there rating, selectedIndex.intValue(), but the rating they selected may have already been rendered as the defaultSelected rating, so
			// if the user's choice has already been rendered, we should not select it again, because that would clear the rendered rating that the user wants.
			responseWriter.write("if (" + selectedIndex.intValue() + " != defaultSelectedIndex) { " + ratingObject + ".select(" + selectedIndex.intValue() + "); }" + StringPool.NEW_LINE);
		}
		
		// What should I consider when a user clicks on the component?
		// Contributor            What value do they want?             What will we do with this value?
		// ---------------------- -----------------------------------  ---------------------------------------------
		// 1. AlloyUI             event.target.get('value')            Use this as the value unless it is -1 which is the cancel value.
		// 2. JSF                 ''                                   use this value instead of -1 or the cancel value.
		// 3. Developer attribute ratingObject.get('defaultSelected')  ignore, because user input is overriding this.
		// 4. Developer EL        rating.getValue()                    ignore, because user input is overriding this.
		// 5. User event          event.target.get('value')            unless it is the cancel or reset value, use this.
		
		// add an onclick event that 
		// 1. sets the value of the hidden input based on users interaction
		// 2. adjusts and calls the jsf-impl's onclick, if any
		responseWriter.write(
			ratingObject + ".on(" + StringPool.NEW_LINE +
			"'click', " + StringPool.NEW_LINE +
			"function(event){" + StringPool.NEW_LINE +
				"var newValue = (event.target.get('value') == '-1') ? '' : event.target.get('value');" + StringPool.NEW_LINE +
				"document.getElementsByName('" + clientId + "')[0].value = newValue;" + StringPool.NEW_LINE + 
				((onClick == null) ? "" : onClick.replaceFirst("this", "document.getElementsByName('" + clientId + "')[0]" )) + StringPool.NEW_LINE + 
			"});" + StringPool.NEW_LINE
		);
		
	}

	@Override
	public void decode(FacesContext facesContext, UIComponent uiComponent) {
		RenderKit renderKit = facesContext.getRenderKit();
		Renderer radioRenderer = renderKit.getRenderer(HtmlSelectOneRadio.COMPONENT_FAMILY, RADIO_RENDERER_TYPE);
		radioRenderer.decode(facesContext, uiComponent);
	}
}
