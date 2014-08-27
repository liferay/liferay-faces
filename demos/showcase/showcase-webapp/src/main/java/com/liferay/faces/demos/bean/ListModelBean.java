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
package com.liferay.faces.demos.bean;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.faces.application.ProjectStage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.liferay.faces.demos.dto.CodeExample;
import com.liferay.faces.demos.dto.ShowcaseComponent;
import com.liferay.faces.demos.dto.ShowcaseComponentComparator;
import com.liferay.faces.demos.dto.ShowcaseComponentImpl;
import com.liferay.faces.demos.dto.UseCase;
import com.liferay.faces.demos.util.CodeExampleUtil;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.product.Product;
import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;


/**
 * @author  Neil Griffin
 */
@ManagedBean(eager = true)
@ApplicationScoped
public class ListModelBean {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ListModelBean.class);

	// Private Constants
	private static final boolean LIFERAY_FACES_BRIDGE_DETECTED = ProductMap.getInstance().get(
			ProductConstants.LIFERAY_FACES_BRIDGE).isDetected();
	private static final boolean LIFERAY_PORTAL_DETECTED = ProductMap.getInstance().get(ProductConstants.LIFERAY_PORTAL)
		.isDetected();
	private static final String[] PACKAGE_NAMES = new String[] {
			"com.liferay.faces.demos.bean", "com.liferay.faces.demos.dto", "com.liferay.faces.demos.converter",
			"com.liferay.faces.demos.portlet"
		};

	// Private Data Members
	private List<String> showcaseCategoryList;
	private List<ShowcaseComponent> showcaseComponents;
	private Map<String, List<ShowcaseComponent>> showcaseCategoryMap;
	private Map<String, ShowcaseComponent> showcaseComponentMap;
	private String copyrightInfo;
	private String dependencyInfo;

	public ListModelBean() {

		FacesContext startupFacesContext = FacesContext.getCurrentInstance();
		boolean developmentMode = startupFacesContext.isProjectStage(ProjectStage.Development);
		boolean systemTestMode = startupFacesContext.isProjectStage(ProjectStage.SystemTest);
		showcaseCategoryList = new ArrayList<String>();
		showcaseCategoryList.add("buttonlink");
		showcaseCategoryList.add("data");
		showcaseCategoryList.add("input");
		showcaseCategoryList.add("misc");
		showcaseCategoryList.add("multimedia");
		showcaseCategoryList.add("output");
		showcaseCategoryList.add("panel");

		if ((developmentMode) || (systemTestMode)) {
			showcaseCategoryList.add("pick");
		}

		if (LIFERAY_FACES_BRIDGE_DETECTED) {
			showcaseCategoryList.add("portlet");
		}

		showcaseCategoryList.add("select");

		this.showcaseComponents = new ArrayList<ShowcaseComponent>();
		this.showcaseCategoryMap = new HashMap<String, List<ShowcaseComponent>>();
		this.showcaseComponentMap = new HashMap<String, ShowcaseComponent>();

		ClassLoader classLoader = getClass().getClassLoader();

		List<String> namespaces = new ArrayList<String>();
		namespaces.add("alloy");

		if (LIFERAY_FACES_BRIDGE_DETECTED) {
			namespaces.add("bridge");
		}

		if ((developmentMode) || (systemTestMode)) {
			namespaces.add("h");
		}

		if (LIFERAY_PORTAL_DETECTED) {
			namespaces.add("liferay-ui");
		}

		if (LIFERAY_FACES_BRIDGE_DETECTED) {
			namespaces.add("portlet");
		}

		for (String namespace : namespaces) {

			Properties properties = new Properties();
			String filename = namespace + ".properties";
			URL resource = classLoader.getResource(filename);

			if (resource != null) {

				try {
					ExternalContext startupExternalContext = startupFacesContext.getExternalContext();

					InputStream inputStream = resource.openStream();
					properties.load(inputStream);
					inputStream.close();

					Set<Entry<Object, Object>> entrySet = properties.entrySet();

					for (Map.Entry<Object, Object> mapEntry : entrySet) {
						String key = (String) mapEntry.getKey();

						String[] keyParts = key.split(StringPool.UNDERLINE);
						String category = keyParts[0];

						String prefix = keyParts[1];
						String camelCaseName = keyParts[2];
						String lowerCaseName = camelCaseName.toLowerCase();

						String value = (String) mapEntry.getValue();
						String[] useCaseArray = value.split(StringPool.OPEN_BRACKET + StringPool.PIPE +
								StringPool.CLOSE_BRACKET);
						List<UseCase> useCases = new ArrayList<UseCase>(useCaseArray.length);

						for (String useCaseInfo : useCaseArray) {
							String[] useCaseParts = useCaseInfo.split(StringPool.COLON);
							String useCaseName = useCaseParts[0];
							String[] sourceFileNames = useCaseParts[1].split(StringPool.COMMA);
							List<CodeExample> codeExamples = new ArrayList<CodeExample>();

							for (String sourceFileName : sourceFileNames) {

								URL sourceFileURL = null;

								if (sourceFileName.endsWith(".css")) {

									String sourcePath = File.separator + "resources" + File.separator + "css" +
										File.separator + sourceFileName;

									sourceFileURL = startupExternalContext.getResource(sourcePath);
								}
								else if (sourceFileName.endsWith(".js")) {

									String sourcePath = File.separator + "resources" + File.separator + "js" +
										File.separator + sourceFileName;

									sourceFileURL = startupExternalContext.getResource(sourcePath);
								}
								else if (sourceFileName.endsWith(".xhtml")) {

									String sourcePath = File.separator + "component" + File.separator + prefix +
										File.separator + lowerCaseName + File.separator;

									if (!sourceFileName.toLowerCase().contains("common")) {
										sourcePath = sourcePath + useCaseName + File.separator;
									}

									sourcePath = sourcePath + sourceFileName;

									sourceFileURL = startupExternalContext.getResource(sourcePath);
								}
								else if (sourceFileName.endsWith(".xml")) {
									String sourcePath = File.separator + "WEB-INF" + File.separator + sourceFileName;
									sourceFileURL = startupExternalContext.getResource(sourcePath);
								}
								else {

									for (int i = 0; ((i < PACKAGE_NAMES.length) && (sourceFileURL == null)); i++) {

										int pos = sourceFileName.lastIndexOf(".java");
										String fqcn = PACKAGE_NAMES[i] + StringPool.PERIOD +
											sourceFileName.substring(0, pos);

										try {
											Class<?> clazz = Class.forName(fqcn);
											sourceFileURL = clazz.getResource(sourceFileName);
										}
										catch (ClassNotFoundException e) {
											// ignore
										}
									}
								}

								if (sourceFileURL != null) {

									CodeExample codeExample = CodeExampleUtil.read(sourceFileURL, sourceFileName);
									codeExamples.add(codeExample);

									logger.debug("Loaded source file=[{0}]", sourceFileName);
								}
								else {
									logger.error("Unable to find source for sourceFileName=[{0}]", sourceFileName);
								}
							}

							UseCase useCase = new UseCase(useCaseName, codeExamples);
							useCases.add(useCase);
						}

						int categoryIndex = showcaseCategoryList.indexOf(category);
						ShowcaseComponent showcaseComponent = new ShowcaseComponentImpl(categoryIndex, prefix,
								camelCaseName, lowerCaseName, useCases);
						String lookupKey = prefix + StringPool.UNDERLINE + lowerCaseName;
						this.showcaseComponentMap.put(lookupKey, showcaseComponent);
						this.showcaseComponents.add(showcaseComponent);
					}

					inputStream.close();
				}
				catch (IOException e) {
					logger.error("Unable to load file: " + filename);
				}
			}
			else {
				logger.info("Missing file: " + filename);
			}

			Collections.sort(this.showcaseComponents, new ShowcaseComponentComparator());

			for (int i = 0; i < showcaseCategoryList.size(); i++) {

				List<ShowcaseComponent> categoryShowcaseComponents = new ArrayList<ShowcaseComponent>();

				for (ShowcaseComponent showcaseComponent : this.showcaseComponents) {

					if (i == showcaseComponent.getCategoryIndex()) {
						categoryShowcaseComponents.add(showcaseComponent);
					}
				}

				this.showcaseCategoryMap.put(showcaseCategoryList.get(i), categoryShowcaseComponents);
			}
		}
	}

	public ShowcaseComponent findShowcaseComponent(String prefix, String name) {
		String key = prefix + StringPool.UNDERLINE + name;

		return showcaseComponentMap.get(key);
	}

	public String getCopyrightInfo() {

		if (copyrightInfo == null) {
			copyrightInfo = "Copyright (c) 2000-" + Calendar.getInstance().get(Calendar.YEAR) +
				" Liferay, Inc. All rights reserved.";
		}

		return copyrightInfo;
	}

	public String getDependencyInfo() {

		if (dependencyInfo == null) {
			StringBuilder buf = new StringBuilder();
			ProductMap productMap = ProductMap.getInstance();
			buf.append("Liferay Faces ");

			Product liferayFacesAlloy = productMap.get(ProductConstants.LIFERAY_FACES_ALLOY);
			String version = liferayFacesAlloy.getVersion();
			int pos = version.indexOf(StringPool.SPACE);

			if (pos > 0) {
				version = version.substring(0, pos);
			}

			buf.append(version);
			buf.append(" + ");
			buf.append(productMap.get(ProductConstants.JSF));
			dependencyInfo = buf.toString();
		}

		return dependencyInfo;
	}

	public List<String> getShowcaseCategories() {
		return showcaseCategoryList;
	}

	public Map<String, List<ShowcaseComponent>> getShowcaseCategoryMap() {
		return showcaseCategoryMap;
	}
}
