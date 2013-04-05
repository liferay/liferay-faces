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
package com.liferay.faces.demos.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.xhtmlrenderer.pdf.ITextRenderer;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class PDFUtil {

	private static final Logger logger = LoggerFactory.getLogger(PDFUtil.class);

	public static byte[] TXT2PDF(String htmlFragment, String headMarkup, String pdfTitle, String description,
		String author) throws IOException {
		byte[] pdf = null;

		if (htmlFragment != null) {
			StringBuilder xhtml = new StringBuilder();
			xhtml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			xhtml.append(
				"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
			xhtml.append("<html>");

			if ((pdfTitle != null) || (description != null) || (author != null)) {
				xhtml.append("<head>");
			}

			if (description != null) {
				xhtml.append("<meta name=\"description\" content=\"");
				xhtml.append(description);
				xhtml.append("\" />");
			}

			if (description != null) {
				xhtml.append("<meta name=\"author\" content=\"");
				xhtml.append(author);
				xhtml.append("\" />");
			}

			if (pdfTitle != null) {
				xhtml.append("<title>");
				xhtml.append(pdfTitle);
				xhtml.append("</title>");
			}

			if (headMarkup != null) {
				xhtml.append(headMarkup);
			}

			if ((pdfTitle != null) || (description != null)) {
				xhtml.append("</head>");
			}

			xhtml.append("<body>");

			if (htmlFragment != null) {
				htmlFragment = htmlFragment.replace("\n", "<br />");
			}

			xhtml.append(htmlFragment);
			xhtml.append("</body>");
			xhtml.append("</html>");
			pdf = XHTML2PDF(xhtml.toString());
		}

		return pdf;
	}

	public static byte[] XHTML2PDF(String xhtml) throws IOException {
		byte[] pdf = null;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

		try {
			ITextRenderer itextRenderer = new ITextRenderer();
			itextRenderer.setDocumentFromString(xhtml);
			itextRenderer.layout();
			itextRenderer.createPDF(byteArrayOutputStream);
			pdf = byteArrayOutputStream.toByteArray();
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			logger.error("vvv--- Offending XHTML ---vvv");
			logger.error(xhtml);
			throw new IOException(e.getMessage());
		}

		return pdf;
	}

}
