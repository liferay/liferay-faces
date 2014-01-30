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
package com.liferay.faces.util.portal;

import java.lang.reflect.Method;

import javax.servlet.jsp.PageContext;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.product.Product;
import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;


/**
 * This class serves as a facade for instances of the Liferay Portal {@link com.liferay.taglib.aui.ScriptTag} class.
 * Some of the method signatures of the class have undergone changes since Liferay Portal 6.0.11 which requires the use
 * of version detection and reflection in this class.
 *
 * @author  Neil Griffin
 */
public class ScriptTagUtil {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ScriptTagUtil.class);

	// Private Constants
	private static final String FLUSH_SCRIPT_DATA = "flushScriptData";
	private static final Method FLUSH_SCRIPT_DATA_METHOD;
	private static final String SCRIPT_TAG_FQCN = "com.liferay.taglib.aui.ScriptTag";

	static {

		Product liferayPortal = ProductMap.getInstance().get(ProductConstants.LIFERAY_PORTAL);

		if (liferayPortal.isDetected()) {

			int buildNumber = liferayPortal.getBuildId();

			if (buildNumber >= 6011) {

				Method flushScriptDataMethod = null;

				try {
					Class<?> scriptTagClass = Class.forName(SCRIPT_TAG_FQCN);
					flushScriptDataMethod = scriptTagClass.getMethod(FLUSH_SCRIPT_DATA,
							new Class[] { PageContext.class });
				}
				catch (Exception e) {
					logger.error(e);
				}

				FLUSH_SCRIPT_DATA_METHOD = flushScriptDataMethod;
			}
			else {
				FLUSH_SCRIPT_DATA_METHOD = null;
			}
		}
		else {
			FLUSH_SCRIPT_DATA_METHOD = null;
		}
	}

	public static void flushScriptData(PageContext pageContext) {

		try {

			if (FLUSH_SCRIPT_DATA_METHOD != null) {
				FLUSH_SCRIPT_DATA_METHOD.invoke(null, new Object[] { pageContext });
			}
		}
		catch (Exception e) {
			logger.error(e);
		}
	}
}
