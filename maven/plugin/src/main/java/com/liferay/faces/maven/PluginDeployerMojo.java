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

import java.io.File;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;


/**
 * Deploys (copies) a Plugin to the $LIFERAY_HOME/deploy folder.
 *
 * @author  Mika Koivisto
 * @author  Thiago Moreira
 * @goal    deploy
 */

public class PluginDeployerMojo extends AbstractLiferayMojo {

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

	/**
	 * @parameter  expression="${autoDeployDir}"
	 * @required
	 */
	private File autoDeployDir;

	/**
	 * @parameter  default-value="${project.build.directory}/${project.build.finalName}.war" expression="${warFile}"
	 * @required
	 */
	private File warFile;

	/**
	 * @parameter  default-value="${project.build.finalName}.war" expression="${warFileName}"
	 * @required
	 */
	private String warFileName;

	public void execute() throws MojoExecutionException {

		if (!isLiferayProject()) {
			return;
		}

		if (warFile.exists()) {
			getLog().info("FAST Deploying " + warFileName + " to " + autoDeployDir.getAbsolutePath());

			CopyTask.copyFile(warFile, autoDeployDir, warFileName, null, true, true);
		}
		else {
			getLog().warn(warFileName + " does not exist");
		}
	}

	@Override
	protected boolean isLiferayProject() {
		String packaging = project.getPackaging();

		if (packaging.equals("pom")) {
			getLog().info("Skipping " + project.getArtifactId());

			return false;
		}

		return true;
	}

}
