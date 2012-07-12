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
package com.liferay.faces.bridge.util;

import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Method;
import java.net.URLEncoder;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * This class provides reflective wrapper access to methods in Mojarra and MyFaces HTML/URL encoding utility methods.
 *
 * @author  Neil Griffin
 */
public class FacesURLEncoder {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(FacesURLEncoder.class);

	// Private Constants
	private static final String MOJARRA_ENCODER_FQCN = "com.sun.faces.util.HtmlUtils";
	private static final String MOJARRA_METHOD_WRITE_URL = "writeURL";
	private static final String MYFACES_ENCODER_FQCN = "org.apache.myfaces.shared.renderkit.html.util.HTMLEncoder";
	private static final String MYFACES_METHOD_ENCODE_URI_ATTRIBUTE = "encodeURIAtributte";

	// Private Data Members
	private static Class<?> mojarraHtmlUtilsClass;
	private static Method mojarraMethodWriteURL;
	private static Class<?> myFacesHTMLEncoderClass;
	private static Method myFacesMethodEncodeURIAtributte;

	static {

		try {
			mojarraHtmlUtilsClass = Class.forName(MOJARRA_ENCODER_FQCN);
			mojarraMethodWriteURL = mojarraHtmlUtilsClass.getMethod(MOJARRA_METHOD_WRITE_URL,
					new Class[] { Writer.class, String.class, char[].class, String.class });
		}
		catch (Exception e1) {

			try {
				myFacesHTMLEncoderClass = Class.forName(MYFACES_ENCODER_FQCN);
				myFacesMethodEncodeURIAtributte = myFacesHTMLEncoderClass.getMethod(MYFACES_METHOD_ENCODE_URI_ATTRIBUTE,
						new Class[] { String.class, String.class });
			}
			catch (Exception e2) {
				// Ignore
			}
		}
	}

	public static String encode(String url, String encoding) {
		String encodedURL = url;

		if (url != null) {

			try {

				if (mojarraMethodWriteURL != null) {
					StringWriter stringWriter = new StringWriter();
					char[] urlBuf = new char[url.length() * 2];
					mojarraMethodWriteURL.invoke(null, stringWriter, url, urlBuf, encoding);
					stringWriter.flush();
					encodedURL = stringWriter.toString();
				}
				else if (myFacesMethodEncodeURIAtributte != null) {
					encodedURL = (String) myFacesMethodEncodeURIAtributte.invoke(null, url, encoding);
				}
				else {
					encodedURL = URLEncoder.encode(url, encoding);
				}
			}
			catch (Exception e) {
				logger.error(e);
			}
		}

		return encodedURL;
	}
}
