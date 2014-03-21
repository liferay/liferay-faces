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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.liferay.faces.demos.dto.CodeExample;
import com.liferay.faces.demos.dto.ShowcaseComponent;
import com.liferay.faces.demos.dto.ShowcaseComponentComparator;
import com.liferay.faces.demos.dto.UseCase;
import com.liferay.faces.demos.util.CodeExampleUtil;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
@ManagedBean(eager = true)
@ApplicationScoped
public class ListModelBean {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ListModelBean.class);

	// Private Data Members
	private List<ShowcaseComponent> showcaseComponents;
	private Map<String, ShowcaseComponent> showcaseComponentMap;

	public ListModelBean() {
		this.showcaseComponents = new ArrayList<ShowcaseComponent>();
		this.showcaseComponentMap = new HashMap<String, ShowcaseComponent>();

		ClassLoader classLoader = getClass().getClassLoader();
		String[] namespaces = new String[] { "aui", "liferay-ui" };

		for (String namespace : namespaces) {
			Properties properties = new Properties();
			String filename = namespace + ".properties";
			URL resource = classLoader.getResource(filename);

			if (resource != null) {

				try {
					FacesContext startupFacesContext = FacesContext.getCurrentInstance();
					ExternalContext startupExternalContext = startupFacesContext.getExternalContext();

					InputStream inputStream = resource.openStream();
					properties.load(inputStream);
					inputStream.close();

					Set<Entry<Object, Object>> entrySet = properties.entrySet();

					for (Map.Entry<Object, Object> mapEntry : entrySet) {
						String key = (String) mapEntry.getKey();

						String[] keyParts = key.split(StringPool.UNDERLINE);
						String prefix = keyParts[0];
						String camelCaseName = keyParts[1];
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

								if (sourceFileName.endsWith(".xhtml")) {

									String sourcePath = File.separator + "component" + File.separator + prefix +
										File.separator + lowerCaseName + File.separator + useCaseName + File.separator +
										sourceFileName;
									
									URL sourceFileURL = startupExternalContext.getResource(sourcePath);
									
									CodeExample codeExample = CodeExampleUtil.load(sourceFileURL, sourceFileName);
									codeExamples.add(codeExample);

									logger.debug("Loaded source file=[{0}]", sourcePath);
								}
							}

							UseCase useCase = new UseCase(useCaseName, codeExamples);
							useCases.add(useCase);
						}

						ShowcaseComponent showcaseComponent = new ShowcaseComponent(prefix, camelCaseName,
								lowerCaseName, useCases);
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
		}
	}

	public ShowcaseComponent findShowcaseComponent(String prefix, String name) {
		String key = prefix + StringPool.UNDERLINE + name;

		return showcaseComponentMap.get(key);
	}

	public List<ShowcaseComponent> getShowcaseComponents() {
		return showcaseComponents;
	}
}
