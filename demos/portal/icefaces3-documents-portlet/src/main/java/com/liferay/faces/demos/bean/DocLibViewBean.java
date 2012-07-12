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
package com.liferay.faces.demos.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.portlet.faces.Bridge;
import javax.portlet.faces.BridgeUtil;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
@ManagedBean(name = "docLibViewBean")
@ViewScoped
public class DocLibViewBean implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 351876131652958472L;

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(DocLibViewBean.class);

	// Injections
	@ManagedProperty(name = "docLibModelBean", value = "#{docLibModelBean}")
	private DocLibModelBean docLibModelBean;

	// Private Data Members
	private String addDocumentURL;
	private boolean popupRendered = false;

	@PostConstruct
	public void postConstruct() {

		// NOTE: Since we don't have those nice ICEfaces 2.0 ACE components, we have to do file upload the Web 1.0 way
		// with the bridge:inputFile component. That forces us to keep the ModelBean in session scope, so that we can
		// retain knowledge of the selected tree folder. Having the following calls to docLibModelBean.force*() will
		// essentially cause the tree and list to be RequestScoped, even though the selected folder remains
		// SessionScoped. This prevents having two model beans, one RequestScoped, and the other SessionScoped, which
		// will enable easier refactoring of the model bean to ViewScoped when the ACE file upload component is
		// available. Note that the whole point of having the list data ViewScoped is to force the list to reload more
		// frequently, so that it contains updates by other users.
		logger.debug(" postConstruct() forcing requery of tree (and implicitly the document list)");
		docLibModelBean.forceTreeRequery();
	}

	public String getAddDocumentURL() {

		if (addDocumentURL == null) {
			Bridge.PortletPhase portletPhase = BridgeUtil.getPortletRequestPhase();

			if ((portletPhase == Bridge.PortletPhase.RENDER_PHASE) ||
					(portletPhase == Bridge.PortletPhase.RESOURCE_PHASE)) {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				String viewId = "/views/docUpload.xhtml";
				Map<String, List<String>> parameters = null;
				addDocumentURL = facesContext.getExternalContext().encodeBookmarkableURL(viewId, parameters);
			}
		}

		return addDocumentURL;
	}

	public boolean isPopupRendered() {
		return popupRendered;
	}

	public void setDocLibModelBean(DocLibModelBean docLibModelBean) {

		// Injected via ManagedProperty annotation
		this.docLibModelBean = docLibModelBean;
	}

	public void setPopupRendered(boolean popupRendered) {
		this.popupRendered = popupRendered;
	}

}
