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

import org.apache.tools.ant.BuildLogger;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;


/**
 * @author  Mika Koivisto
 */
public class AntUtil {

	public static Project getProject() {
		Project project = new Project();

		BuildLogger buildLogger = new DefaultLogger();

		buildLogger.setErrorPrintStream(System.err);
		buildLogger.setMessageOutputLevel(Project.MSG_INFO);
		buildLogger.setOutputPrintStream(System.out);

		project.addBuildListener(buildLogger);

		return project;
	}

}
