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
package com.liferay.faces.alloy.component.inputfile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.liferay.faces.util.context.MessageContext;
import com.liferay.faces.util.context.MessageContextFactory;
import com.liferay.faces.util.factory.FactoryExtensionFinder;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.model.UploadedFile;


/**
 * @author  Neil Griffin
 */
@FacesValidator(value = "com.liferay.faces.alloy.component.inputfile.InputFileValidator")
public class InputFileValidator implements Validator {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(InputFileValidator.class);

	// Private Data Members
	private Long maxFileSize;
	private String contentTypes;
	private Set<String> contentTypeSet;

	@Override
	public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {

		Locale locale = facesContext.getViewRoot().getLocale();
		MessageContextFactory messageContextFactory = (MessageContextFactory) FactoryExtensionFinder.getFactory(
				MessageContextFactory.class);
		MessageContext messageContext = messageContextFactory.getMessageContext();
		List<FacesMessage> facesMessages = new ArrayList<FacesMessage>();
		@SuppressWarnings("unchecked")
		List<UploadedFile> uploadedFiles = (List<UploadedFile>) value;

		for (UploadedFile uploadedFile : uploadedFiles) {

			if ((maxFileSize != null) && (maxFileSize >= 0) && (uploadedFile.getSize() > maxFileSize)) {

				String errorMessage = messageContext.getMessage(locale, "file-x-is-y-bytes-but-may-not-exceed-z-bytes",
						uploadedFile.getName(), uploadedFile.getSize(), maxFileSize);
				facesMessages.add(new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, errorMessage));
			}

			if ((contentTypeSet != null) && !contentTypeSet.contains(uploadedFile.getContentType())) {

				String errorMessage = messageContext.getMessage(locale, "file-x-has-an-invalid-content-type-y",
						uploadedFile.getName(), uploadedFile.getContentType());
				facesMessages.add(new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, errorMessage));
			}
		}

		if (facesMessages.size() > 0) {

			Iterator<UploadedFile> itr = uploadedFiles.iterator();

			while (itr.hasNext()) {
				UploadedFile uploadedFile = itr.next();

				try {
					uploadedFile.delete();
				}
				catch (IOException e) {
					logger.error(e);
				}

				itr.remove();
			}

			throw new ValidatorException(facesMessages);
		}
	}

	public String getContentTypes() {
		return contentTypes;
	}

	public void setContentTypes(String contentTypes) {

		this.contentTypes = contentTypes;

		if (contentTypes == null) {
			this.contentTypeSet = null;
		}
		else {
			this.contentTypeSet = new HashSet<String>(Arrays.asList(contentTypes.split(StringPool.COMMA)));
		}
	}

	public Long getMaxFileSize() {
		return maxFileSize;
	}

	public void setMaxFileSize(Long maxFileSize) {
		this.maxFileSize = maxFileSize;
	}
}
