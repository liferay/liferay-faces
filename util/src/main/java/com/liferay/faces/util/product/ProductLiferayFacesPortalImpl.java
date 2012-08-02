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
package com.liferay.faces.util.product;

/**
 * @author  Neil Griffin
 */
public class ProductLiferayFacesPortalImpl extends ProductBaseImpl {

	public ProductLiferayFacesPortalImpl() {

		try {
			this.title = ProductConstants.LIFERAY_FACES_PORTAL;

			Class<?> auiPanelClass = Class.forName("com.liferay.faces.portal.context.LiferayFacesContext");
			init(auiPanelClass, ProductConstants.LIFERAY_FACES_PORTAL);
		}
		catch (Exception e) {
			// Ignore -- Liferay Faces Portal is likely not present.
		}
	}
}
