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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.behavior.ClientBehavior;

import com.liferay.faces.alloy.component.inputfile.InputFile;


/**
 * @author  Neil Griffin
 */
public class InputFileMockImpl extends InputFile {

	// Private Data Members
	private Map<String, List<ClientBehavior>> clientBehaviorMap;

	public InputFileMockImpl(String execute, String render) {
		List<ClientBehavior> clientBehaviorList = new ArrayList<ClientBehavior>();
		clientBehaviorList.add(new AjaxBehaviorMockImpl(execute, render));
		this.clientBehaviorMap = new HashMap<String, List<ClientBehavior>>();
		this.clientBehaviorMap.put("valueChange", clientBehaviorList);
	}

	@Override
	public Map<String, List<ClientBehavior>> getClientBehaviors() {
		return clientBehaviorMap;
	}
}
