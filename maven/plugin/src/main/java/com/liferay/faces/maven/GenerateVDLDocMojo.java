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
package com.liferay.faces.maven;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.omnifaces.vdldoc.VdldocGenerator;


/**
 * Generates VDLDoc according to configuration parameters. For more information, see: http://code.google.com/p/vdldoc
 *
 * @goal    generate-vdldoc
 * 
 * @author  Neil Griffin
 */
public class GenerateVDLDocMojo extends AbstractMojo {

	/**
	 * @parameter
	 */
	private String docTitle;

	/**
	 * @parameter
	 */
	private boolean quiet;

	/**
	 * @parameter
	 */
	private String windowTitle;

	/**
	 * @parameter
	 */
	private String[] taglibs;

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {

		VdldocGenerator generator = new VdldocGenerator();
		generator.setWindowTitle(windowTitle);
		generator.setDocTitle(docTitle);
		generator.setOutputDirectory(new File("target/vdldoc"));
		generator.setQuiet(quiet);

		if (taglibs != null) {

			for (String taglib : taglibs) {
				generator.addTaglib(new File(taglib));
			}
		}

		generator.generate();
	}

}
