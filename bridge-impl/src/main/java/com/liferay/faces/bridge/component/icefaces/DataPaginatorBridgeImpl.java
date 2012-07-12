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
package com.liferay.faces.bridge.component.icefaces;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * <p>This class is part of a workaround for <a href="http://jira.icesoft.org/browse/ICE-6398">ICE-6398</a>.</p>
 *
 * <p>The basic approach is to make sure that the DataPaginator.setData(UIData) method is called ahead of time, so that
 * ICEfaces will not bother to call it's internal CoreComponentUtils.findComponent(String, UIComponent) method.</p>
 *
 * @author  Neil Griffin
 */
public class DataPaginatorBridgeImpl extends DataPaginatorWrapper {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(DataPaginatorBridgeImpl.class);

	// Private Constants
	private static final String ATTR_NAME_FOR = "for";
	private static final String DATA_PAGINATOR_GROUP_FQCN =
		"com.icesoft.faces.component.datapaginator.DataPaginatorGroup";
	private static final String METHOD_NAME_GET_UIDATA = "getUIData";
	private static final String METHOD_NAME_SET_UIDATA = "setUIData";

	// Private Data Members
	private Object wrappedDataPaginator;

	public DataPaginatorBridgeImpl(Object dataPaginator) {
		this.wrappedDataPaginator = dataPaginator;
	}

	@Override
	public void decode(FacesContext facesContext) {

		UIData uiData = null;

		try {
			uiData = getUIData();
		}
		catch (Exception e) {
			logger.error(e);
		}

		if (uiData != null) {

			if (!uiData.getAttributes().containsKey(DATA_PAGINATOR_GROUP_FQCN)) {
				uiData.getAttributes().put(DATA_PAGINATOR_GROUP_FQCN, new ArrayList<String>());
			}

			@SuppressWarnings("unchecked")
			List<String> paginatorList = (List<String>) uiData.getAttributes().get(DATA_PAGINATOR_GROUP_FQCN);
			String clientId = getClientId(facesContext);

			if (!paginatorList.contains(clientId)) {
				paginatorList.add(clientId);
			}
		}

		super.decode(facesContext);
	}

	@Override
	public UIData findUIData(FacesContext facesContext) {

		UIData uiData = null;

		// Get the value of the component's "for" attribute.
		ValueExpression valueExpression = getValueExpression(ATTR_NAME_FOR);

		if (valueExpression != null) {
			String forComponentId = (String) valueExpression.getValue(facesContext.getELContext());

			// If the component is "for" a component of type UIData, then return the component that it is for. Otherwise
			// return null.
			if (forComponentId != null) {
				Object forComponent = findComponent(forComponentId);

				if (forComponent == null) {
					forComponent = matchComponentInHierarchy(facesContext, facesContext.getViewRoot(), forComponentId);
				}

				if (forComponent != null) {

					if (forComponent instanceof UIData) {
						uiData = (UIData) forComponent;
					}
					else {
						throw new IllegalArgumentException(
							"for attribute must be an instance of javax.faces.component.UIData");
					}
				}
			}
		}

		return uiData;
	}

	@Override
	public void processDecodes(FacesContext facesContext) {

		try {
			setUIData(findUIData(facesContext));
		}
		catch (Exception e) {
			logger.error(e);
		}

		super.processDecodes(facesContext);
	}

	protected UIComponent matchComponentInHierarchy(FacesContext facesContext, UIComponent parent,
		String partialClientId) {
		UIComponent uiComponent = null;

		if (parent != null) {

			String parentClientId = parent.getClientId(facesContext);

			if ((parentClientId != null) && (parentClientId.indexOf(partialClientId) >= 0)) {
				uiComponent = parent;
			}
			else {
				Iterator<UIComponent> itr = parent.getFacetsAndChildren();

				if (itr != null) {

					while (itr.hasNext()) {
						UIComponent child = itr.next();
						uiComponent = matchComponentInHierarchy(facesContext, child, partialClientId);

						if (uiComponent != null) {
							break;
						}
					}
				}
			}
		}

		return uiComponent;
	}

	@Override
	public UIData getUIData() throws Exception {

		UIData uiData = null;

		Method method = getWrapped().getClass().getMethod(METHOD_NAME_GET_UIDATA, new Class[] {});
		uiData = (UIData) method.invoke(getWrapped(), (Object[]) null);

		return uiData;
	}

	@Override
	public void setUIData(UIData uiData) throws Exception {
		Method method = getWrapped().getClass().getMethod(METHOD_NAME_SET_UIDATA, new Class[] { UIData.class });
		method.invoke(getWrapped(), uiData);
	}

	@Override
	public Object getWrapped() {
		return wrappedDataPaginator;
	}

}
