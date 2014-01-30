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
package com.liferay.faces.demos.resource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.context.FacesContext;

import com.liferay.faces.demos.dto.Customer;
import com.liferay.faces.demos.service.CustomerService;
import com.liferay.faces.demos.util.PDFUtil;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class CustomerExportResource extends Resource {

	// Public Constants
	public static final String CONTENT_TYPE = "application/pdf";
	public static final String RESOURCE_NAME = "export";

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(CustomerExportResource.class);

	// Private Constants
	private static final String PARAM_NAME_CUSTOMER_ID = "customerId";

	// Private Data Members
	private Customer customer;
	private String requestPath;

	public CustomerExportResource() {
		setLibraryName(CustomerResourceHandler.LIBRARY_NAME);
		setResourceName(RESOURCE_NAME);
		setContentType(CONTENT_TYPE);
	}

	public CustomerExportResource(Customer customer) {
		this();
		this.customer = customer;
	}

	@Override
	public boolean userAgentNeedsUpdate(FacesContext context) {

		// Since this is a list that can potentially change dynamically, always return true.
		return true;
	}

	protected Customer getCustomer() {

		if (customer == null) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			String customerId = facesContext.getExternalContext().getRequestParameterMap().get(PARAM_NAME_CUSTOMER_ID);
			String elExpression = "customerService";
			CustomerService customerService = (CustomerService) facesContext.getApplication().getELResolver().getValue(
					facesContext.getELContext(), null, elExpression);

			try {
				customer = customerService.getCustomer(Long.parseLong(customerId));
			}
			catch (NumberFormatException e) {
				logger.error(e);
			}
		}

		return customer;
	}

	@Override
	public InputStream getInputStream() throws IOException {

		byte[] byteArray = new byte[] {};

		try {

			// Convert the fragment into a full HTML document, and then to a PDF.
			StringBuilder buf = new StringBuilder();
			buf.append("<table>");
			buf.append("<thead>");
			buf.append("<tr>");
			buf.append("<th>First Name</th>");
			buf.append("<th>Last Name</th>");
			buf.append("</tr>");
			buf.append("</thead>");
			buf.append("<tbody>");
			buf.append("<tr>");
			buf.append("<td>");
			buf.append(getCustomer().getFirstName());
			buf.append("</td>");
			buf.append("<td>");
			buf.append(getCustomer().getLastName());
			buf.append("</td>");
			buf.append("</tr>");
			buf.append("</tbody>");
			buf.append("</table>");

			String htmlFragment = buf.toString();

			// Encode any ampersand characters to ensure that the PDF conversion will work.
			htmlFragment = htmlFragment.replaceAll("[&]", "&amp;");

			String headMarkup = buf.toString();
			String pdfTile = "Customers";
			String description = pdfTile;
			String author = "Author Name";
			htmlFragment = htmlFragment.replaceAll("[\\n]", " ");
			htmlFragment = htmlFragment.replaceAll("[\\t]", " ");
			byteArray = PDFUtil.TXT2PDF(headMarkup, headMarkup, pdfTile, description, author);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return new ByteArrayInputStream(byteArray);
	}

	@Override
	public String getRequestPath() {

		if (requestPath == null) {
			StringBuilder buf = new StringBuilder();
			buf.append(ResourceHandler.RESOURCE_IDENTIFIER);
			buf.append("/");
			buf.append(getResourceName());
			buf.append("?ln=");
			buf.append(getLibraryName());
			buf.append("&");
			buf.append(PARAM_NAME_CUSTOMER_ID);
			buf.append("=");
			buf.append(getCustomer().getCustomerId());
			requestPath = buf.toString();
		}

		return requestPath;
	}

	@Override
	public Map<String, String> getResponseHeaders() {
		return null;
	}

	@Override
	public URL getURL() {

		// TODO Auto-generated method stub
		return null;
	}

}
