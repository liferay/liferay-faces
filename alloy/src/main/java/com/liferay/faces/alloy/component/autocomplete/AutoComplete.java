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
package com.liferay.faces.alloy.component.autocomplete;

import java.lang.reflect.Array;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.component.UISelectItems;
import javax.faces.component.UISelectOne;
import javax.faces.component.UIViewRoot;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import com.liferay.faces.util.context.MessageContext;
import com.liferay.faces.util.context.MessageContextFactory;
import com.liferay.faces.util.factory.FactoryExtensionFinder;


/**
 * @author  Kyle Stiemann
 */
@FacesComponent(value = AutoComplete.COMPONENT_TYPE)
public class AutoComplete extends AutoCompleteBase implements ClientBehaviorHolder {

	@Override
	protected void validateValue(FacesContext facesContext, Object newValue) {

		super.validateValue(facesContext, newValue);

		if (isListItemRequired() && isValid()) {

			List<String> unfilteredResults = getAllItems(facesContext);
			String newValueAsString = (String) newValue;

			if (!unfilteredResults.contains(newValueAsString)) {

				setValid(false);

				String validatorMessage = getValidatorMessage();
				FacesMessage facesMessage;

				if (validatorMessage != null) {
					facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, validatorMessage, validatorMessage);
				}
				else {
					MessageContextFactory messageContextFactory = (MessageContextFactory) FactoryExtensionFinder
						.getFactory(MessageContextFactory.class);
					MessageContext messageContext = messageContextFactory.getMessageContext();
					UIViewRoot viewRoot = facesContext.getViewRoot();
					Locale locale = viewRoot.getLocale();
					String message = messageContext.getMessage(locale, UISelectOne.INVALID_MESSAGE_ID);

					if (message != null) {
						message = MessageFormat.format(message, getLabel());
					}

					facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message);
				}

				String clientId = getClientId(facesContext);
				facesContext.addMessage(clientId, facesMessage);
			}
		}
	}

	public final List<String> getAllItems(FacesContext facesContext) {

		List<String> allItems = new ArrayList<String>();
		List<UIComponent> children = getChildren();

		for (UIComponent child : children) {

			if (child instanceof UISelectItem) {

				UISelectItem uiSelectItem = (UISelectItem) child;
				Object itemValue = uiSelectItem.getItemValue();

				if (itemValue != null) {
					allItems.add(itemValue.toString());
				}
			}
			else if (child instanceof UISelectItems) {

				UISelectItems uiSelectItems = (UISelectItems) child;
				Object value = uiSelectItems.getValue();

				if (value != null) {

					if (value instanceof SelectItem) {

						SelectItem selectItem = (SelectItem) value;
						Object item = selectItem.getValue();

						if (item != null) {
							allItems.add(item.toString());
						}
					}
					else if (value.getClass().isArray()) {

						int length = Array.getLength(value);

						for (int i = 0; i < length; i++) {

							Object itemValue = Array.get(value, i);
							String item = getItemValue(facesContext, uiSelectItems, itemValue);

							if (item != null) {
								allItems.add(item);
							}
						}
					}
					else if (value instanceof Collection) {

						Collection<?> collection = (Collection<?>) value;
						Iterator<?> iterator = collection.iterator();

						while (iterator.hasNext()) {

							String item = getItemValue(facesContext, uiSelectItems, iterator.next());

							if (item != null) {
								allItems.add(item);
							}
						}
					}
					else if (value instanceof Map) {

						Map<?, ?> map = (Map<?, ?>) value;
						Iterator<?> iterator = map.keySet().iterator();

						while (iterator.hasNext()) {

							Object key = iterator.next();
							String item = getItemValue(facesContext, uiSelectItems, map.get(key));

							if (item != null) {
								allItems.add(item);
							}
						}
					}
					else {
						throw new IllegalArgumentException();
					}
				}
			}
		}

		return Collections.unmodifiableList(allItems);
	}

	@Override
	public String getAutocomplete() {
		return (String) getStateHelper().eval(PropertyKeys.autocomplete, "off");
	}

	private String getItemValue(FacesContext facesContext, UISelectItems uiSelectItems, Object item) {

		String value = null;

		if (item != null) {

			if (item instanceof SelectItem) {

				SelectItem selectItem = (SelectItem) item;
				Object selectItemValue = selectItem.getValue();

				if (selectItemValue != null) {
					value = selectItemValue.toString();
				}
			}
			else {

				Map<String, Object> attributes = uiSelectItems.getAttributes();
				String var = (String) attributes.get("var");

				if (var != null) {

					ExternalContext externalContext = facesContext.getExternalContext();
					Map<String, Object> requestMap = externalContext.getRequestMap();
					requestMap.put(var, item);
					value = (String) attributes.get("itemValue");
					requestMap.remove(var);
				}
				else {
					value = (String) attributes.get("itemValue");
				}
			}
		}

		return value;
	}

}
