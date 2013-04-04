/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.bridge.context.url;

/**
 * This interface represents a bridge "redirect" URL, meaning a URL according to the deviation requirements of {@link
 * javax.faces.context.ExternalContext#redirect(String)} listed in Section 6.1.3.1 of the Bridge Spec. Since this
 * interface provides no additional method signatures, it serves as a marker interface of sorts that provides easier
 * code readability.
 *
 * @author  Neil Griffin
 */
public interface BridgeRedirectURL extends BridgeResponseURL {

}
