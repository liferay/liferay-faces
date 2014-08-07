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
package com.liferay.faces.jrebel;

import org.zeroturnaround.javarebel.ClassResourceSource;
import org.zeroturnaround.javarebel.Integration;
import org.zeroturnaround.javarebel.IntegrationFactory;
import org.zeroturnaround.javarebel.Plugin;
import org.zeroturnaround.javarebel.Resource;


/**
 * This class is the Liferay Faces Plugin for JRebel.
 */
public class LiferayFacesPlugin implements Plugin {

	/**
	 * Returns a boolean indicating whether or not this plugin has the dependencies necessary to perform.
	 */
	public boolean checkDependencies(ClassLoader classLoader, ClassResourceSource classResourceSource) {

		Resource facesClassResource = classResourceSource.getClassResource("javax.faces.context.FacesContext");
		Resource templateClassResource = classResourceSource.getClassResource(
				"com.liferay.faces.util.template.Template");

		return ((facesClassResource != null) && (templateClassResource != null));
	}

	/**
	 * .
	 */
	public void preinit() {

		// Register bytecode processors that are associated with this plugin.
		Integration integration = IntegrationFactory.getInstance();
		integration.addIntegrationProcessor(new RendererBytecodeProcessor(), true);

		// Register class reload listeners.
		// ReloaderFactory.getInstance().addClassReloadListener(new LiferayFacesClassEventListener());
	}

	public String getAuthor() {
		return "Liferay, Inc.";
	}

	public String getDescription() {
		return "Liferay Faces class reload processing.";
	}

	public String getId() {
		return "liferay-faces-jrebel-plugin";
	}

	public String getName() {
		return "Liferay Faces JRebel Plugin";
	}

	public String getSupportedVersions() {
		return null;
	}

	public String getTestedVersions() {
		return null;
	}

	public String getWebsite() {
		return "http://www.liferay.com";
	}
}
