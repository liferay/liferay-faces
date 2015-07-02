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
package com.liferay.faces.alloy.component.inputfile.internal;

import org.junit.Test;

import com.liferay.faces.alloy.component.inputfile.InputFile;

import junit.framework.Assert;


/**
 * @author  Neil Griffin
 */
public class AjaxParametersTest {

	@Test
	public void tester() {

		// Initialize
		String formClientId = "j_1234:myForm";
		String clientId = "j_1234:myForm:myInputFile";

		// Test that an empty value for execute maps to the clientId
		AjaxParameters ajaxParameters = newAjaxParameters(clientId, formClientId, "", "");
		Assert.assertTrue(clientId.equals(ajaxParameters.getExecute()));

		// Test that an empty value for render maps to an empty value.
		Assert.assertTrue("".equals(ajaxParameters.getRender()));

		// Test that @form maps to the clientId of the form
		ajaxParameters = newAjaxParameters(clientId, formClientId, "@form", "@form");
		Assert.assertTrue(ajaxParameters.getExecute().equals(clientId + " " + formClientId));
		Assert.assertTrue(ajaxParameters.getRender().equals(formClientId));

		// Test that @all maps to @all
		ajaxParameters = newAjaxParameters(clientId, formClientId, "@all", "@all");
		Assert.assertTrue(ajaxParameters.getExecute().equals("@all"));
		Assert.assertTrue(ajaxParameters.getRender().equals("@all"));

		// Test that the presence of @all removes all others
		ajaxParameters = newAjaxParameters(clientId, formClientId, "@this @all @form", "@this @all @form");
		Assert.assertTrue(ajaxParameters.getExecute().equals("@all"));
		Assert.assertTrue(ajaxParameters.getRender().equals("@all"));

		// Test that @none maps to an empty value.
		ajaxParameters = newAjaxParameters(clientId, formClientId, "@none", "@none");
		Assert.assertTrue(ajaxParameters.getExecute().equals(""));
		Assert.assertTrue(ajaxParameters.getRender().equals(""));

		// Test that the presence of @non removes all others.
		ajaxParameters = newAjaxParameters(clientId, formClientId, "@this @none @form", "@this @none @form");
		Assert.assertTrue(ajaxParameters.getExecute().equals(""));
		Assert.assertTrue(ajaxParameters.getRender().equals(""));
	}

	protected AjaxParameters newAjaxParameters(String clientId, String formClientId, String execute, String render) {

		InputFile inputFile = new InputFileMockImpl(execute, render);

		return new AjaxParameters(inputFile, clientId, formClientId);
	}
}
