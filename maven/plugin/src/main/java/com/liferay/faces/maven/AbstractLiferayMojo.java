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
package com.liferay.faces.maven;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.project.MavenProject;


/**
 * @author  Gregory Amerson
 */
public abstract class AbstractLiferayMojo extends AbstractMojo {

	/**
	 * @parameter  expression="${liferayVersion}"
	 */
	protected String liferayVersion;

	/**
	 * @parameter  expression="${project}"
	 * @required
	 * @readonly
	 */
	protected MavenProject project;

	protected boolean isLiferayProject() {
		String packaging = project.getPackaging();

		if (packaging.equals("pom")) {
			getLog().info("Skipping " + project.getArtifactId());

			return false;
		}

		return true;
	}

}
