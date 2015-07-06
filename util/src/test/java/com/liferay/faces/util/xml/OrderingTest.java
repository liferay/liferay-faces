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
package com.liferay.faces.util.xml;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.junit.Assert;
import org.junit.Test;

import com.liferay.faces.util.config.internal.FacesConfigDescriptor;
import com.liferay.faces.util.config.internal.FacesConfigDescriptorParser;
import com.liferay.faces.util.config.internal.FacesConfigDescriptorParserImpl;
import com.liferay.faces.util.config.internal.Ordering;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Vernon Singleton
 */
public class OrderingTest {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(OrderingTest.class);

	private static final String META_INF_FACES_CONFIG_XML = "META-INF/faces-config.xml";
	private static final String WEB_INF_FACES_CONFIG_XML = "WEB-INF/faces-config.xml";

	public static void parseConfigurationResources(String testCase, List<FacesConfigDescriptor> facesConfigDescriptors,
		String configPath) {

		try {
			SAXParserFactory saxParserFactory = ConcurrentSAXParserFactory.newInstance();
			SAXParser saxParser = saxParserFactory.newSAXParser();
			FacesConfigDescriptorParser facesConfigDescriptorParser = new FacesConfigDescriptorParserImpl(saxParser,
					false);
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			Enumeration<URL> orderingUrls = classLoader.getResources(testCase);

			if (orderingUrls.hasMoreElements()) {
				URL orderingUrl = orderingUrls.nextElement();
				File orderingDirectory = new File(orderingUrl.toURI());
				File[] configurationDirectories = orderingDirectory.listFiles();

				for (File directory : configurationDirectories) {

					// config path should be either META_INF_FACES_CONFIG_XML or WEB_INF_FACES_CONFIG_XML
					String resourcePath = testCase + "/" + directory.getName() + "/" + configPath;
					Enumeration<URL> facesConfigUrls = classLoader.getResources(resourcePath);

					while (facesConfigUrls.hasMoreElements()) {
						URL facesConfigURL = facesConfigUrls.nextElement();

						// logger.info("parseConfigurationResources: " + facesConfigURL.toString());
						InputStream inputStream = facesConfigURL.openStream();
						FacesConfigDescriptor facesConfigDescriptor = facesConfigDescriptorParser.parse(inputStream,
								facesConfigURL);

						// logger.info("parseConfigurationResources: >" + facesConfigDescriptor.getName() + "<");
						facesConfigDescriptors.add(facesConfigDescriptor);
						inputStream.close();
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	// This fails intermittently without the preSort
	@Test
	public void test00_0() throws Exception {

//      logger.info("test00_0: beginning ...");

		List<FacesConfigDescriptor> facesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/00", facesConfigDescriptors, META_INF_FACES_CONFIG_XML);
		Collections.shuffle(facesConfigDescriptors);

		// Parse the WEB-INF/faces-config.xml to get any absolute-ordering, if any.
		List<FacesConfigDescriptor> webInfFacesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/00", webInfFacesConfigDescriptors, WEB_INF_FACES_CONFIG_XML);

		FacesConfigDescriptor facesConfig = webInfFacesConfigDescriptors.get(0);
		List<String> absoluteOrdering = facesConfig.getAbsoluteOrdering();

		String[] originalOrder = Ordering.extractNames(facesConfigDescriptors);

		List<FacesConfigDescriptor> order = new ArrayList<FacesConfigDescriptor>();

		if (absoluteOrdering == null) {
			order = Ordering.getOrder(facesConfigDescriptors);
		}
		else {
			Assert.fail("test00_0: absoluteOrdering != null. It should be null.");
			order = Ordering.getOrder(facesConfigDescriptors, absoluteOrdering);
		}

		// Append facesConfig from the applicationFacesConfig
		order.add(facesConfig);

		String[] orderedNames = Ordering.extractNames(order);

		List<String> original = Arrays.asList(originalOrder);
		List<String> actually = Arrays.asList(orderedNames);
		List<String> expected = Arrays.asList("LiferayFacesUtil", "LiferayFacesBridge", "prettyfaces",
				"LiferayFacesAlloy", "");

		// solutions:
		// LiferayFacesUtil, LiferayFacesBridge, prettyfaces, LiferayFacesAlloy,
		String message = "\n original: " + original + "\n expected: " + expected + "\n actually: " + actually + "\n";
		Assert.assertTrue(message, expected.equals(actually));
		logger.info("test00_0: Passed" + message);

	}

	// This fails intermittently without the preSort
	@Test
	public void test00_1() throws Exception {

//      logger.info("test00_1: beginning ...");

		List<FacesConfigDescriptor> facesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/04", facesConfigDescriptors, META_INF_FACES_CONFIG_XML);
		Collections.shuffle(facesConfigDescriptors);

		// extractedNames = [ICEfacesAce, LiferayFacesBridge, LiferayFacesAlloy, LiferayFacesUtil, ICEfacesCore]
// HashMap<String, FacesConfigDescriptor> configHashMap = Ordering.getConfigHashMap(facesConfigDescriptors);
// List<FacesConfigDescriptor> temp = new ArrayList<FacesConfigDescriptor>();
// temp.add(configHashMap.get("ICEfacesAce"));
// temp.add(configHashMap.get("LiferayFacesBridge"));
// temp.add(configHashMap.get("LiferayFacesAlloy"));
// temp.add(configHashMap.get("LiferayFacesUtil"));
// temp.add(configHashMap.get("ICEfacesCore"));
// facesConfigDescriptors = temp;

		// Parse the WEB-INF/faces-config.xml to get any absolute-ordering, if any.
		List<FacesConfigDescriptor> webInfFacesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/04", webInfFacesConfigDescriptors, WEB_INF_FACES_CONFIG_XML);

		FacesConfigDescriptor facesConfig = webInfFacesConfigDescriptors.get(0);
		List<String> absoluteOrdering = facesConfig.getAbsoluteOrdering();

		String[] originalOrder = Ordering.extractNames(facesConfigDescriptors);

		List<FacesConfigDescriptor> order = new ArrayList<FacesConfigDescriptor>();

		if (absoluteOrdering == null) {
			order = Ordering.getOrder(facesConfigDescriptors);
		}
		else {
			Assert.fail("test00_1: absoluteOrdering != null. It should be null.");
			order = Ordering.getOrder(facesConfigDescriptors, absoluteOrdering);
		}

		// Append facesConfig from the applicationFacesConfig
		order.add(facesConfig);

		String[] orderedNames = Ordering.extractNames(order);

		List<String> original = Arrays.asList(originalOrder);
		List<String> actually = Arrays.asList(orderedNames);
		List<String> possibility1 = Arrays.asList("ICEfacesCore", "LiferayFacesUtil", "LiferayFacesBridge",
				"LiferayFacesAlloy", "ICEfacesAce", "");
		List<String> possibility2 = Arrays.asList("ICEfacesCore", "ICEfacesAce", "LiferayFacesUtil",
				"LiferayFacesBridge", "LiferayFacesAlloy", "");

		// some solutions:
		// [ICEfacesCore, LiferayFacesUtil, LiferayFacesBridge, LiferayFacesAlloy, ICEfacesAce, ]
		// [ICEfacesCore, ICEfacesAce, LiferayFacesUtil, LiferayFacesBridge, LiferayFacesAlloy, ]
		boolean assertion = (actually.equals(possibility1) || actually.equals(possibility2));
		String message = "\n original: " + original + "\n expected: " + possibility1 + "\n       or: " + possibility2 +
			"\n actually: " + actually + "\n";
		Assert.assertTrue(message, assertion);
		logger.info("test00_1: Passed" + message);

	}

	// This fails intermittently without the preSort
	@Test
	public void test00_2() throws Exception {

//      logger.info("test00_2: beginning ...");

		List<FacesConfigDescriptor> facesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/05", facesConfigDescriptors, META_INF_FACES_CONFIG_XML);
		Collections.shuffle(facesConfigDescriptors);

		// extractedNames = [ICEfacesAce, LiferayFacesBridge, LiferayFacesAlloy, LiferayFacesUtil, ICEfacesCore]
// HashMap<String, FacesConfigDescriptor> configHashMap = Ordering.getConfigHashMap(facesConfigDescriptors);
// List<FacesConfigDescriptor> temp = new ArrayList<FacesConfigDescriptor>();
// temp.add(configHashMap.get("ICEfacesAce"));
// temp.add(configHashMap.get("LiferayFacesBridge"));
// temp.add(configHashMap.get("LiferayFacesAlloy"));
// temp.add(configHashMap.get("LiferayFacesUtil"));
// temp.add(configHashMap.get("ICEfacesCore"));
// facesConfigDescriptors = temp;

		// Parse the WEB-INF/faces-config.xml to get any absolute-ordering, if any.
		List<FacesConfigDescriptor> webInfFacesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/05", webInfFacesConfigDescriptors, WEB_INF_FACES_CONFIG_XML);

		FacesConfigDescriptor facesConfig = webInfFacesConfigDescriptors.get(0);
		List<String> absoluteOrdering = facesConfig.getAbsoluteOrdering();

		String[] originalOrder = Ordering.extractNames(facesConfigDescriptors);

		List<FacesConfigDescriptor> order = new ArrayList<FacesConfigDescriptor>();

		if (absoluteOrdering == null) {
			order = Ordering.getOrder(facesConfigDescriptors);
		}
		else {
			Assert.fail("test00_2: absoluteOrdering != null. It should be null.");
			order = Ordering.getOrder(facesConfigDescriptors, absoluteOrdering);
		}

		// Append facesConfig from the applicationFacesConfig
		order.add(facesConfig);

		String[] orderedNames = Ordering.extractNames(order);

		List<String> original = Arrays.asList(originalOrder);
		List<String> actually = Arrays.asList(orderedNames);
		List<String> possibility1 = Arrays.asList("ICEfacesCore", "LiferayFacesUtil", "LiferayFacesBridge",
				"LiferayFacesAlloy", "richfaces_core", "ICEfacesAce", "");
		List<String> possibility2 = Arrays.asList("ICEfacesCore", "LiferayFacesUtil", "LiferayFacesBridge",
				"LiferayFacesAlloy", "ICEfacesAce", "richfaces_core", "");
		List<String> possibility3 = Arrays.asList("ICEfacesCore", "LiferayFacesUtil", "LiferayFacesBridge",
				"richfaces_core", "LiferayFacesAlloy", "ICEfacesAce", "");

		// some solutions:
		// [ICEfacesCore, LiferayFacesUtil, LiferayFacesBridge, LiferayFacesAlloy, richfaces_core, ICEfacesAce, ]
		// [ICEfacesCore, LiferayFacesUtil, LiferayFacesBridge, LiferayFacesAlloy, ICEfacesAce, richfaces_core, ]
		// [ICEfacesCore, LiferayFacesUtil, LiferayFacesBridge, richfaces_core, LiferayFacesAlloy, ICEfacesAce, ]
		boolean assertion = (actually.equals(possibility1) || actually.equals(possibility2) ||
				actually.equals(possibility3));
		String message = "\n original: " + original + "\n expected: " + possibility1 + "\n       or: " + possibility2 +
			"\n       or: " + possibility3 + "\n actually: " + actually + "\n";
		Assert.assertTrue(message, assertion);
		logger.info("test00_2: Passed" + message);

	}

	@Test
	public void test00_3() throws Exception {

//      logger.info("test00_1: beginning ...");

		List<FacesConfigDescriptor> facesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/06", facesConfigDescriptors, META_INF_FACES_CONFIG_XML);
		Collections.shuffle(facesConfigDescriptors);

		// extractedNames = [ICEfacesAce, LiferayFacesBridge, LiferayFacesAlloy, LiferayFacesUtil, ICEfacesCore]
// HashMap<String, FacesConfigDescriptor> configHashMap = Ordering.getConfigHashMap(facesConfigDescriptors);
// List<FacesConfigDescriptor> temp = new ArrayList<FacesConfigDescriptor>();
// temp.add(configHashMap.get("ICEfacesAce"));
// temp.add(configHashMap.get("LiferayFacesBridge"));
// temp.add(configHashMap.get("LiferayFacesAlloy"));
// temp.add(configHashMap.get("LiferayFacesUtil"));
// temp.add(configHashMap.get("ICEfacesCore"));
// facesConfigDescriptors = temp;

		// Parse the WEB-INF/faces-config.xml to get any absolute-ordering, if any.
		List<FacesConfigDescriptor> webInfFacesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/04", webInfFacesConfigDescriptors, WEB_INF_FACES_CONFIG_XML);

		FacesConfigDescriptor facesConfig = webInfFacesConfigDescriptors.get(0);
		List<String> absoluteOrdering = facesConfig.getAbsoluteOrdering();

		String[] originalOrder = Ordering.extractNames(facesConfigDescriptors);

		List<FacesConfigDescriptor> order = new ArrayList<FacesConfigDescriptor>();

		if (absoluteOrdering == null) {
			order = Ordering.getOrder(facesConfigDescriptors);
		}
		else {
			Assert.fail("test00_1: absoluteOrdering != null. It should be null.");
			order = Ordering.getOrder(facesConfigDescriptors, absoluteOrdering);
		}

		// Append facesConfig from the applicationFacesConfig
		order.add(facesConfig);

		String[] orderedNames = Ordering.extractNames(order);

		List<String> original = Arrays.asList(originalOrder);
		List<String> actually = Arrays.asList(orderedNames);
		List<String> possibility1 = Arrays.asList("ICEfacesCore", "LiferayFacesUtil", "LiferayFacesBridge",
				"LiferayFacesAlloy", "ICEfacesAce", "");
		List<String> possibility2 = Arrays.asList("ICEfacesCore", "ICEfacesAce", "LiferayFacesUtil",
				"LiferayFacesBridge", "LiferayFacesAlloy", "");

		// some solutions:
		// [ICEfacesCore, LiferayFacesUtil, LiferayFacesBridge, LiferayFacesAlloy, ICEfacesAce, ]
		// [ICEfacesCore, ICEfacesAce, LiferayFacesUtil, LiferayFacesBridge, LiferayFacesAlloy, ]
		boolean assertion = (actually.equals(possibility1) || actually.equals(possibility2));
		String message = "\n original: " + original + "\n expected: " + possibility1 + "\n       or: " + possibility2 +
			"\n actually: " + actually + "\n";
		Assert.assertTrue(message, assertion);
		logger.info("test00_3: Passed" + message);

	}

	@Test
	public void test01_fromSpec() throws Exception {

//      logger.info("test01_fromSpec: beginning ...");

		List<FacesConfigDescriptor> facesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/01", facesConfigDescriptors, META_INF_FACES_CONFIG_XML);
		Collections.shuffle(facesConfigDescriptors);

		// Parse the WEB-INF/faces-config.xml to get any absolute-ordering, if any.
		List<FacesConfigDescriptor> webInfFacesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/01", webInfFacesConfigDescriptors, WEB_INF_FACES_CONFIG_XML);

		FacesConfigDescriptor facesConfig = webInfFacesConfigDescriptors.get(0);
		List<String> absoluteOrdering = facesConfig.getAbsoluteOrdering();

		String[] originalOrder = Ordering.extractNames(facesConfigDescriptors);

		List<FacesConfigDescriptor> order = new ArrayList<FacesConfigDescriptor>();

		if (absoluteOrdering == null) {
			order = Ordering.getOrder(facesConfigDescriptors);
		}
		else {
			Assert.fail("test01_fromSpec: absoluteOrdering != null. It should be null.");
			order = Ordering.getOrder(facesConfigDescriptors, absoluteOrdering);
		}

		// Append facesConfig from the applicationFacesConfig
		order.add(facesConfig);

		String[] orderedNames = Ordering.extractNames(order);

		List<String> original = Arrays.asList(originalOrder);
		List<String> actually = Arrays.asList(orderedNames);
		List<String> expected = Arrays.asList("C", "B", "A", "my");

		String message = "\n original: " + original + "\n expected: " + expected + "\n actually: " + actually + "\n";
		Assert.assertTrue(message, expected.equals(actually));
		logger.info("test01_fromSpec: Passed" + message);

	}

	@Test
	public void test02_fromSpec() throws Exception {

//      logger.info("test02_fromSpec: beginning ...");

		List<FacesConfigDescriptor> facesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/02", facesConfigDescriptors, META_INF_FACES_CONFIG_XML);
		Collections.shuffle(facesConfigDescriptors);

		// Parse the WEB-INF/faces-config.xml to get any absolute-ordering, if any.
		List<FacesConfigDescriptor> webInfFacesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/02", webInfFacesConfigDescriptors, WEB_INF_FACES_CONFIG_XML);

		FacesConfigDescriptor facesConfig = webInfFacesConfigDescriptors.get(0);
		List<String> absoluteOrdering = facesConfig.getAbsoluteOrdering();

		String[] originalOrder = Ordering.extractNames(facesConfigDescriptors);

		List<FacesConfigDescriptor> order = new ArrayList<FacesConfigDescriptor>();

		if (absoluteOrdering == null) {
			Assert.fail("test02_fromSpec: absoluteOrdering == null. It should not be null.");
			order = Ordering.getOrder(facesConfigDescriptors);
		}
		else {
			order = Ordering.getOrder(facesConfigDescriptors, absoluteOrdering);
		}

		// Append facesConfig from the applicationFacesConfig
		order.add(facesConfig);

		String[] orderedNames = Ordering.extractNames(order);

		List<String> original = Arrays.asList(originalOrder);
		List<String> actually = Arrays.asList(orderedNames);
		List<String> expected = Arrays.asList("C", "A", "my");

		String message = "\n original: " + original + "\n expected: " + expected + "\n actually: " + actually + "\n";
		Assert.assertTrue(message, expected.equals(actually));
		logger.info("test02_fromSpec: Passed" + message);

	}

	@Test
	public void test03_getAbsoluteOrdering() throws Exception {

//      logger.info("test03_getAbsoluteOrdering: beginning ...");

		// Parse the WEB-INF/faces-config.xml to get any absolute-ordering, if any.
		List<FacesConfigDescriptor> webInfFacesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/GetAbsoluteOrdering_01", webInfFacesConfigDescriptors,
			WEB_INF_FACES_CONFIG_XML);

		FacesConfigDescriptor facesConfig = webInfFacesConfigDescriptors.get(0);
		List<String> absoluteOrdering = facesConfig.getAbsoluteOrdering();

//      List<String> actually = Arrays.asList(absoluteOrdering.get(0), absoluteOrdering.get(1),
//              absoluteOrdering.get(2));
		List<String> actually = absoluteOrdering;
		List<String> expected = Arrays.asList("a", "b", "c");

		String message = "\n expected: " + expected + "\n actually: " + actually + "\n";
		Assert.assertTrue(message, expected.equals(actually));
		logger.info("test03_getAbsoluteOrdering: " + message);

		webInfFacesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/GetAbsoluteOrdering_02", webInfFacesConfigDescriptors,
			WEB_INF_FACES_CONFIG_XML);

		facesConfig = webInfFacesConfigDescriptors.get(0);
		absoluteOrdering = facesConfig.getAbsoluteOrdering();

//      actually = Arrays.asList(absoluteOrdering.get(0), absoluteOrdering.get(1), absoluteOrdering.get(2),
//              absoluteOrdering.get(3));
		actually = absoluteOrdering;
		expected = Arrays.asList("a", "b", Ordering.OTHERS, "c");

		message = "\n expected: " + expected + "\n actually: " + actually + "\n";
		Assert.assertTrue(message, expected.equals(actually));
		logger.info("test03_getAbsoluteOrdering: " + message);

		webInfFacesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/GetAbsoluteOrdering_03", webInfFacesConfigDescriptors,
			WEB_INF_FACES_CONFIG_XML);

		facesConfig = webInfFacesConfigDescriptors.get(0);
		absoluteOrdering = facesConfig.getAbsoluteOrdering();

		Assert.assertTrue("absoluteOrdering != null. But it should be null.", absoluteOrdering == null);

		logger.info("test03_getAbsoluteOrdering: Passed\n");
	}

	@Test
	public void test04_absoluteOrdering() throws Exception {

//      logger.info("test04_absoluteOrdering: beginning ...");

		List<FacesConfigDescriptor> facesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/AbsoluteOrdering_01", facesConfigDescriptors, META_INF_FACES_CONFIG_XML);
		Collections.shuffle(facesConfigDescriptors);

		// Parse the WEB-INF/faces-config.xml to get any absolute-ordering, if any.
		List<FacesConfigDescriptor> webInfFacesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/AbsoluteOrdering_01", webInfFacesConfigDescriptors,
			WEB_INF_FACES_CONFIG_XML);

		FacesConfigDescriptor facesConfig = webInfFacesConfigDescriptors.get(0);
		List<String> absoluteOrdering = facesConfig.getAbsoluteOrdering();

		String[] originalOrder = Ordering.extractNames(facesConfigDescriptors);

		List<FacesConfigDescriptor> order = new ArrayList<FacesConfigDescriptor>();

		if (absoluteOrdering == null) {
			Assert.fail("test04_absoluteOrdering: absoluteOrdering == null. It should not be null.");
			order = Ordering.getOrder(facesConfigDescriptors);
		}
		else {
			order = Ordering.getOrder(facesConfigDescriptors, absoluteOrdering);
		}

		String[] orderedNames = Ordering.extractNames(order);

		List<String> original = Arrays.asList(originalOrder);
		List<String> actually = Arrays.asList(orderedNames);
		List<String> expected = Arrays.asList("a", "b", "c");

		String message = "\n original: " + original + "\n expected: " + expected + "\n actually: " + actually + "\n";
		Assert.assertTrue(message, expected.equals(actually));
		logger.info("test04_absoluteOrdering: " + message);

		webInfFacesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/AbsoluteOrdering_02", webInfFacesConfigDescriptors,
			WEB_INF_FACES_CONFIG_XML);

		facesConfig = webInfFacesConfigDescriptors.get(0);
		absoluteOrdering = facesConfig.getAbsoluteOrdering();

		originalOrder = Ordering.extractNames(facesConfigDescriptors);

		order = new ArrayList<FacesConfigDescriptor>();

		if (absoluteOrdering == null) {
			Assert.fail("test04_absoluteOrdering: absoluteOrdering == null. It should not be null.");
			order = Ordering.getOrder(facesConfigDescriptors);
		}
		else {
			logger.info("test04_absoluteOrdering: absoluteOrdering = " + absoluteOrdering);
			order = Ordering.getOrder(facesConfigDescriptors, absoluteOrdering);
		}

		orderedNames = Ordering.extractNames(order);

		// solutions:
		// [a, b, e, d, f, c]
		// [a, b, f, e, d, c]
		// [a, b, f, d, e, c]
		// [a, b, d, e, f, c]
		// [a, b, d, f, e, c]
		// [a, b, e, f, d, c]

		original = Arrays.asList(originalOrder);
		actually = Arrays.asList(orderedNames);

		List<String> possibility1 = Arrays.asList("a", "b", "e", "d", "f", "c");
		List<String> possibility2 = Arrays.asList("a", "b", "f", "e", "d", "c");
		List<String> possibility3 = Arrays.asList("a", "b", "f", "d", "e", "c");
		List<String> possibility4 = Arrays.asList("a", "b", "d", "e", "f", "c");
		List<String> possibility5 = Arrays.asList("a", "b", "d", "f", "e", "c");
		List<String> possibility6 = Arrays.asList("a", "b", "e", "f", "d", "c");

		boolean assertion = (actually.equals(possibility1) || actually.equals(possibility2) ||
				actually.equals(possibility3) || actually.equals(possibility4) || actually.equals(possibility5) ||
				actually.equals(possibility6));
		message = "\n original: " + original + "\n expected: " + possibility1 + "\n       or: " + possibility2 +
			"\n       or: " + possibility3 + "\n       or: " + possibility4 + "\n       or: " + possibility5 +
			"\n       or: " + possibility6 + "\n actually: " + actually + "\n";
		Assert.assertTrue(message, assertion);
		logger.info("test04_absoluteOrdering: Passed" + message);

	}

	@Test
	public void test05_AafterCOthers_BbeforeOthers_CafterOthers_FbeforeCOthers() throws Exception {

//      logger.info("test05_afterAfterOthers: beginning ...");

		List<FacesConfigDescriptor> facesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/AafterCOthers_BbeforeOthers_CafterOthers_FbeforeCOthers",
			facesConfigDescriptors, META_INF_FACES_CONFIG_XML);
		Collections.shuffle(facesConfigDescriptors);

		String[] originalOrder = Ordering.extractNames(facesConfigDescriptors);

		facesConfigDescriptors = Ordering.getOrder(facesConfigDescriptors);

		String[] orderedNames = Ordering.extractNames(facesConfigDescriptors);

		List<String> original = Arrays.asList(originalOrder);
		List<String> actually = Arrays.asList(orderedNames);

		List<String> possibility1 = Arrays.asList("f", "b", "e", "d", "c", "a");
		List<String> possibility2 = Arrays.asList("f", "b", "d", "e", "c", "a");
		List<String> possibility3 = Arrays.asList("b", "f", "d", "e", "c", "a");
		List<String> possibility4 = Arrays.asList("b", "f", "e", "d", "c", "a");

		// some solutions:
		// [f, b, e, d, c, a]
		// [f, b, d, e, c, a]
		// [b, f, d, e, c, a]
		// [b, f, e, d, c, a]
		boolean assertion = (actually.equals(possibility1) || actually.equals(possibility2) ||
				actually.equals(possibility3) || actually.equals(possibility4));
		String message = "\n original: " + original + "\n expected: " + possibility1 + "\n       or: " + possibility2 +
			"\n       or: " + possibility3 + "\n       or: " + possibility4 + "\n actually: " + actually + "\n";
		Assert.assertTrue(message, assertion);
		logger.info("test05_AafterCOthers_BbeforeOthers_CafterOthers_FbeforeCOthers: Passed" + message);

	}

	// This fails intermittently without the preSort
	@Test
	public void test06_CafterDbeforeOthers() throws Exception {

		List<FacesConfigDescriptor> facesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/CafterDbeforeOthers", facesConfigDescriptors, META_INF_FACES_CONFIG_XML);
		Collections.shuffle(facesConfigDescriptors);

		String[] originalOrder = Ordering.extractNames(facesConfigDescriptors);

		facesConfigDescriptors = Ordering.getOrder(facesConfigDescriptors);

		String[] orderedNames = Ordering.extractNames(facesConfigDescriptors);

		List<String> original = Arrays.asList(originalOrder);
		List<String> actually = Arrays.asList(orderedNames);

		List<String> possibility1 = Arrays.asList("d", "c", "a", "b");
		List<String> possibility2 = Arrays.asList("d", "c", "b", "a");

		// some solutions:
		// [d, c, a, b]
		// [d, c, b, a]
		boolean assertion = (actually.equals(possibility1) || actually.equals(possibility2));
		String message = "\n original: " + original + "\n expected: " + possibility1 + "\n       or: " + possibility2 +
			"\n actually: " + actually + "\n";
		Assert.assertTrue(message, assertion);
		logger.info("test06_CafterDbeforeOthers: Passed" + message);

	}

	// This fails intermittently without the preSort
	@Test
	public void test07_eachAfterTheNext() throws Exception {

		List<FacesConfigDescriptor> facesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/EachAfterTheNext", facesConfigDescriptors, META_INF_FACES_CONFIG_XML);
		Collections.shuffle(facesConfigDescriptors);

		String[] originalOrder = Ordering.extractNames(facesConfigDescriptors);

		facesConfigDescriptors = Ordering.getOrder(facesConfigDescriptors);

		String[] orderedNames = Ordering.extractNames(facesConfigDescriptors);

		List<String> original = Arrays.asList(originalOrder);
		List<String> actually = Arrays.asList(orderedNames);
		List<String> expected = Arrays.asList("d", "c", "b", "a");

		String message = "\n original: " + original + "\n expected: " + expected + "\n actually: " + actually + "\n";
		Assert.assertTrue(message, expected.equals(actually));
		logger.info("test07_eachAfterTheNext: Passed" + message);

	}

	// This fails intermittently without the preSort
	@Test
	public void test08_eachBeforeThePrevious() throws Exception {

		List<FacesConfigDescriptor> facesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/EachBeforeThePrevious", facesConfigDescriptors,
			META_INF_FACES_CONFIG_XML);
		Collections.shuffle(facesConfigDescriptors);

		String[] originalOrder = Ordering.extractNames(facesConfigDescriptors);

		facesConfigDescriptors = Ordering.getOrder(facesConfigDescriptors);

		String[] orderedNames = Ordering.extractNames(facesConfigDescriptors);

		List<String> original = Arrays.asList(originalOrder);
		List<String> actually = Arrays.asList(orderedNames);
		List<String> expected = Arrays.asList("d", "c", "b", "a");

		String message = "\n original: " + original + "\n expected: " + expected + "\n actually: " + actually + "\n";
		Assert.assertTrue(message, expected.equals(actually));
		logger.info("test08_eachBeforeThePrevious: Passed" + message);

	}

	@Test
	public void test10_AafterBbeforeB_CbeforeOthers() throws Exception {

		List<FacesConfigDescriptor> facesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/AafterBbeforeB_CbeforeOthers", facesConfigDescriptors,
			META_INF_FACES_CONFIG_XML);
		Collections.shuffle(facesConfigDescriptors);

		try {
			Ordering.getOrder(facesConfigDescriptors);
			Assert.fail("Before and after exception should have been thrown");
		}
		catch (Exception e) {

			if (e.getMessage().contains(Ordering.BOTH_BEFORE_AND_AFTER)) {

				// this is the expected result
				logger.info(
					"test10_AafterBbeforeB_CbeforeOthers: Passed\n Expected exception thrown: e.getMessage() = " +
					e.getMessage());
			}
			else {
				Assert.fail("An exception stating '" + Ordering.BOTH_BEFORE_AND_AFTER +
					"' should have been thrown.  Instead, got: " + e.getMessage() + "\n");
			}
		}

	}

	@Test
	public void test11_CafterOthersbeforeB() throws Exception {

		List<FacesConfigDescriptor> facesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/CafterOthersbeforeB", facesConfigDescriptors, META_INF_FACES_CONFIG_XML);
		Collections.shuffle(facesConfigDescriptors);

		String[] originalOrder = Ordering.extractNames(facesConfigDescriptors);

		facesConfigDescriptors = Ordering.getOrder(facesConfigDescriptors);

		String[] orderedNames = Ordering.extractNames(facesConfigDescriptors);

		List<String> original = Arrays.asList(originalOrder);
		List<String> actually = Arrays.asList(orderedNames);

		List<String> possibility1 = Arrays.asList("a", "d", "c", "b");
		List<String> possibility2 = Arrays.asList("d", "a", "c", "b");

		// some solutions:
		// [a, d, c, b]
		// [d, a, c, b]
		boolean assertion = (actually.equals(possibility1) || actually.equals(possibility2));
		String message = "\n original: " + original + "\n expected: " + possibility1 + "\n       or: " + possibility2 +
			"\n actually: " + actually + "\n";
		Assert.assertTrue(message, assertion);
		logger.info("test11_CafterOthersbeforeB: Passed" + message);

	}

	@Test
	public void test12_BafterC_CafterB() throws Exception {

		List<FacesConfigDescriptor> facesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/BafterC_CafterB", facesConfigDescriptors, META_INF_FACES_CONFIG_XML);
		Collections.shuffle(facesConfigDescriptors);

		try {
			Ordering.getOrder(facesConfigDescriptors);
			Assert.fail("Circular ordering exception should have been thrown");
		}
		catch (Exception e) {

			if (e.getMessage().contains(Ordering.CIRCULAR_DEPENDENCIES_DETECTED)) {

				// this is the expected result
				logger.info("test12_BafterC_CafterB: Passed\n Expected exception thrown: e.getMessage() = " +
					e.getMessage());
			}
			else {
				Assert.fail("An exception stating '" + Ordering.CIRCULAR_DEPENDENCIES_DETECTED +
					"' should have been thrown.  Instead, got: " + e.getMessage() + "\n");
			}
		}

	}

	@Test
	public void test13_circularFollowingAfterIds() throws Exception {

		List<FacesConfigDescriptor> facesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/CircularFollowingAfter", facesConfigDescriptors,
			META_INF_FACES_CONFIG_XML);
		Collections.shuffle(facesConfigDescriptors);

		// checkForSpecExceptions: names = [b, d, c, a] ... this order reduces the circle to
		// Ordering.BOTH_BEFORE_AND_AFTER HashMap<String, FacesConfigDescriptor> configHashMap =
		// Ordering.getConfigHashMap(facesConfigDescriptors); List<FacesConfigDescriptor> temp = new
		// ArrayList<FacesConfigDescriptor>(); temp.add(configHashMap.get("b")); temp.add(configHashMap.get("d"));
		// temp.add(configHashMap.get("c")); temp.add(configHashMap.get("a")); facesConfigDescriptors = temp;

		try {
			Ordering.getOrder(facesConfigDescriptors);
			Assert.fail("Circular ordering exception should have been thrown");
		}
		catch (Exception e) {

			if (e.getMessage().contains(Ordering.CIRCULAR_DEPENDENCIES_DETECTED)) {

				// this is the expected result
				logger.info("test13_circularFollowingAfterIds: Passed\n Expected exception thrown: e.getMessage() = " +
					e.getMessage());
			}
			else if (e.getMessage().contains(Ordering.BOTH_BEFORE_AND_AFTER)) {

				// this is the expected result
				logger.info("test13_circularFollowingAfterIds: Passed\n A circular order can quickly reduce to '" +
					Ordering.BOTH_BEFORE_AND_AFTER + "' depending on the inital order.");
				logger.info("test13_circularFollowingAfterIds: Passed\n Expected exception thrown: e.getMessage() = " +
					e.getMessage());
			}
			else {
				Assert.fail("An exception stating '" + Ordering.CIRCULAR_DEPENDENCIES_DETECTED + " or '" +
					Ordering.BOTH_BEFORE_AND_AFTER + "' should have been thrown.  Instead, got: " + e.getMessage() +
					"\n");
			}
		}

	}

	@Test
	public void test14_circularFollowingBeforeIds() throws Exception {

		List<FacesConfigDescriptor> facesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/CircularFollowingBefore", facesConfigDescriptors,
			META_INF_FACES_CONFIG_XML);
		Collections.shuffle(facesConfigDescriptors);

		// checkForSpecExceptions: names = [d, b, a, c] ... this order reduces the circle to
		// Ordering.BOTH_BEFORE_AND_AFTER HashMap<String, FacesConfigDescriptor> configHashMap =
		// Ordering.getConfigHashMap(facesConfigDescriptors); List<FacesConfigDescriptor> temp = new
		// ArrayList<FacesConfigDescriptor>(); temp.add(configHashMap.get("d")); temp.add(configHashMap.get("b"));
		// temp.add(configHashMap.get("a")); temp.add(configHashMap.get("c")); facesConfigDescriptors = temp;

		try {
			Ordering.getOrder(facesConfigDescriptors);
			Assert.fail("Circular ordering exception should have been thrown");
		}
		catch (Exception e) {

			if (e.getMessage().contains(Ordering.CIRCULAR_DEPENDENCIES_DETECTED)) {

				// this is the expected result
				logger.info("test14_circularFollowingBeforeIds: Passed\n Expected exception thrown: e.getMessage() = " +
					e.getMessage());
			}
			else if (e.getMessage().contains(Ordering.BOTH_BEFORE_AND_AFTER)) {

				// this is the expected result
				logger.info("test14_circularFollowingBeforeIds: Passed\n A circular order can quickly reduce to '" +
					Ordering.BOTH_BEFORE_AND_AFTER + "' depending on the inital order.");
				logger.info("test14_circularFollowingBeforeIds: Passed\n Expected exception thrown: e.getMessage() = " +
					e.getMessage());
			}
			else {
				Assert.fail("An exception stating '" + Ordering.CIRCULAR_DEPENDENCIES_DETECTED + " or '" +
					Ordering.BOTH_BEFORE_AND_AFTER + "' should have been thrown.  Instead, got: " + e.getMessage() +
					"\n");
			}
		}

	}

	@Test
	public void test15_BafterC_CbeforeB() throws Exception {

		List<FacesConfigDescriptor> facesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/BafterC_CbeforeB", facesConfigDescriptors, META_INF_FACES_CONFIG_XML);
		Collections.shuffle(facesConfigDescriptors);

		String[] originalOrder = Ordering.extractNames(facesConfigDescriptors);

		facesConfigDescriptors = Ordering.getOrder(facesConfigDescriptors);

		String[] orderedNames = Ordering.extractNames(facesConfigDescriptors);

		List<String> original = Arrays.asList(originalOrder);
		List<String> actually = Arrays.asList(orderedNames);
		List<String> possibility1 = Arrays.asList("a", "c", "d", "b");
		List<String> possibility2 = Arrays.asList("c", "d", "a", "b");
		List<String> possibility3 = Arrays.asList("c", "a", "d", "b");

		// some solutions:
		// [a, c, d, b]
		// [c, d, a, b]
		// [c, a, d, b]
		boolean assertion = (actually.equals(possibility1) || actually.equals(possibility2) ||
				actually.equals(possibility3));
		String message = "\n original: " + original + "\n expected: " + possibility1 + "\n       or: " + possibility2 +
			"\n       or: " + possibility3 + "\n actually: " + actually + "\n";
		Assert.assertTrue(message, assertion);
		logger.info("test15_BafterC_CbeforeB: Passed" + message);

	}

	@Test
	public void test16_AafterB_CbeforeOthers() throws Exception {

//      logger.info("test16_AafterB_CbeforeOthers: beginning ...");

		List<FacesConfigDescriptor> facesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/AafterB_CbeforeOthers", facesConfigDescriptors,
			META_INF_FACES_CONFIG_XML);
		Collections.shuffle(facesConfigDescriptors);

		String[] originalOrder = Ordering.extractNames(facesConfigDescriptors);

		facesConfigDescriptors = Ordering.getOrder(facesConfigDescriptors);

		String[] orderedNames = Ordering.extractNames(facesConfigDescriptors);

		List<String> original = Arrays.asList(originalOrder);
		List<String> actually = Arrays.asList(orderedNames);
		List<String> possibility1 = Arrays.asList("c", "b", "d", "a");
		List<String> possibility2 = Arrays.asList("c", "d", "b", "a");

		// solutions:
		// "c", "b", "d", "a"
		// "c", "d", "b", "a"
		boolean assertion = (actually.equals(possibility1) || actually.equals(possibility2));
		String message = "\n original: " + original + "\n expected: " + possibility1 + "\n       or: " + possibility2 +
			"\n actually: " + actually + "\n";
		Assert.assertTrue(message, assertion);
		logger.info("test16_AafterB_CbeforeOthers: Passed" + message);

	}

	// This fails without the preSort
	// not submitted as an issue in mojarra, but this is the first one we created to check mojarra against:
	// https://java.net/jira/browse/JAVASERVERFACES-3757
	@Test
	public void test18_AafterC_BafterCbeforeOthers_CafterDbeforeB_EafterD() throws Exception {

		List<FacesConfigDescriptor> facesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/AafterC_BafterCbeforeOthers_CafterDbeforeB_EafterD",
			facesConfigDescriptors, META_INF_FACES_CONFIG_XML);
		Collections.shuffle(facesConfigDescriptors);

		// extractedNames = [ICEfacesAce, LiferayFacesBridge, LiferayFacesAlloy, LiferayFacesUtil, ICEfacesCore]
		HashMap<String, FacesConfigDescriptor> configHashMap = Ordering.getConfigHashMap(facesConfigDescriptors);
		List<FacesConfigDescriptor> temp = new ArrayList<FacesConfigDescriptor>();
		temp.add(configHashMap.get("e"));
		temp.add(configHashMap.get("d"));
		temp.add(configHashMap.get("b"));
		temp.add(configHashMap.get("c"));
		temp.add(configHashMap.get("f"));
		temp.add(configHashMap.get("a"));
		facesConfigDescriptors = temp;

		String[] originalOrder = Ordering.extractNames(facesConfigDescriptors);

		facesConfigDescriptors = Ordering.getOrder(facesConfigDescriptors);

		String[] orderedNames = Ordering.extractNames(facesConfigDescriptors);

		List<String> original = Arrays.asList(originalOrder);
		List<String> actually = Arrays.asList(orderedNames);
		List<String> expected = Arrays.asList("d", "c", "b", "f", "a", "e");

		// solution:
		// d, c, b, f, a, e
		String message = "\n original: " + original + "\n expected: " + expected + "\n actually: " + actually + "\n";
		Assert.assertTrue(message, expected.equals(actually));
		logger.info("test18_AafterC_BafterCbeforeOthers_CafterDbeforeB_EafterD: Passed" + message);

	}

	// This fails without the preSort
	@Test
	public void test19_FbeforeD_EbeforeDafterOthers_DbeforeCafterE_BbeforeC() throws Exception {

		List<FacesConfigDescriptor> facesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/FbeforeD_EbeforeDafterOthers_DbeforeCafterE_BbeforeC",
			facesConfigDescriptors, META_INF_FACES_CONFIG_XML);
		Collections.shuffle(facesConfigDescriptors);

		// [e, a, f, b, d, c]
		HashMap<String, FacesConfigDescriptor> configHashMap = Ordering.getConfigHashMap(facesConfigDescriptors);
		List<FacesConfigDescriptor> temp = new ArrayList<FacesConfigDescriptor>();
		temp.add(configHashMap.get("e"));
		temp.add(configHashMap.get("a"));
		temp.add(configHashMap.get("f"));
		temp.add(configHashMap.get("b"));
		temp.add(configHashMap.get("d"));
		temp.add(configHashMap.get("c"));
		facesConfigDescriptors = temp;

		String[] originalOrder = Ordering.extractNames(facesConfigDescriptors);

		facesConfigDescriptors = Ordering.getOrder(facesConfigDescriptors);

		// some solutions:
		// [f, e, d, b, a, c]
		// [f, e, d, b, c, a]
		// [a, b, f, e, d, c]

		String[] orderedNames = Ordering.extractNames(facesConfigDescriptors);

		List<String> original = Arrays.asList(originalOrder);
		List<String> actually = Arrays.asList(orderedNames);

		List<String> possibility1 = Arrays.asList("f", "e", "d", "b", "a", "c");
		List<String> possibility2 = Arrays.asList("f", "e", "d", "b", "c", "a");
		List<String> possibility3 = Arrays.asList("a", "b", "f", "e", "d", "c");

		boolean assertion = (actually.equals(possibility1) || actually.equals(possibility2) ||
				actually.equals(possibility3));
		String message = "\n original: " + original + "\n expected: " + possibility1 + "\n       or: " + possibility2 +
			"\n       or: " + possibility3 + "\n actually: " + actually + "\n";
		Assert.assertTrue(message, assertion);
		logger.info("test19_FbeforeD_EbeforeDafterOthers_DbeforeCafterE_BbeforeC: Passed" + message);

	}

	@Test
	public void test20_beforeCafterOthers_BbeforeC_DafterOthers_startWith_BCDEF() throws Exception {

		List<FacesConfigDescriptor> facesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/_beforeCafterOthers_BbeforeC_DafterOthers_startWithABCDEF",
			facesConfigDescriptors, META_INF_FACES_CONFIG_XML);

		String[] originalOrder = Ordering.extractNames(facesConfigDescriptors);
		facesConfigDescriptors = Ordering.getOrder(facesConfigDescriptors);

		// some solutions:
		// [B, E, F, , C, D]
		// [B, E, F, D, , C]

		String[] orderedNames = Ordering.extractNames(facesConfigDescriptors);

		List<String> original = Arrays.asList(originalOrder);
		List<String> actually = Arrays.asList(orderedNames);

		List<String> possibility1 = Arrays.asList("b", "e", "f", "", "c", "d");
		List<String> possibility2 = Arrays.asList("b", "e", "f", "d", "", "c");

		boolean assertion = (actually.equals(possibility1) || actually.equals(possibility2));
		String message = "\n original: " + original + "\n expected: " + possibility1 + "\n       or: " + possibility2 +
			"\n actually: " + actually + "\n";
		Assert.assertTrue(message, assertion);
		logger.info("test20_beforeCafterOthers_BbeforeC_DafterOthers_startWith_BCDEF: Passed" + message);

	}

	@Test
	public void test21_AafterOthers_BbeforeOthers_DafterOthers_EafterCbeforeOthers_startingWithABCDEF()
		throws Exception {

		List<FacesConfigDescriptor> facesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources(
			"ordering/AafterOthers_BbeforeOthers_DafterOthers_EafterCbeforeOthers_startingWithABCDEF",
			facesConfigDescriptors, META_INF_FACES_CONFIG_XML);

		String[] originalOrder = Ordering.extractNames(facesConfigDescriptors);
		facesConfigDescriptors = Ordering.getOrder(facesConfigDescriptors);

		// some solutions:
		// [B, C, E, F, A, D]
		// [B, C, E, F, D, A]
		// [C, E, B, F, A, D]
		// [C, E, B, F, D, A]

		String[] orderedNames = Ordering.extractNames(facesConfigDescriptors);

		List<String> original = Arrays.asList(originalOrder);
		List<String> actually = Arrays.asList(orderedNames);

		List<String> possibility1 = Arrays.asList("b", "c", "e", "f", "a", "d");
		List<String> possibility2 = Arrays.asList("b", "c", "e", "f", "d", "a");
		List<String> possibility3 = Arrays.asList("c", "e", "b", "f", "a", "d");
		List<String> possibility4 = Arrays.asList("c", "e", "b", "f", "d", "a");

		boolean assertion = (
			actually.equals(possibility1) || actually.equals(possibility2) ||
			actually.equals(possibility3) || actually.equals(possibility4)
		);
		String message = "\n original: " + original +
			"\n expected: " + possibility1 +
			"\n       or: " + possibility2 +
			"\n       or: " + possibility3 +
			"\n       or: " + possibility4 +
			"\n actually: " + actually + "\n";
		Assert.assertTrue(message, assertion);
		logger.info("test21_AafterOthers_BbeforeOthers_DafterOthers_EafterCbeforeOthers_startingWithABCDEF: Passed" +
			message);
	}

	// submitted as an issue in mojarra:
	// https://java.net/jira/browse/JAVASERVERFACES-3757
	@Test
	public void test_JAVASERVERFACES_3757_AafterD_BafterCbeforeOthers_CafterDbeforeB_startWithABCD() throws Exception {
		List<FacesConfigDescriptor> facesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/AafterD_BafterCbeforeOthers_CafterDbeforeB", facesConfigDescriptors,
			META_INF_FACES_CONFIG_XML);
		Collections.shuffle(facesConfigDescriptors);

		HashMap<String, FacesConfigDescriptor> configHashMap = Ordering.getConfigHashMap(facesConfigDescriptors);
		List<FacesConfigDescriptor> temp = new ArrayList<FacesConfigDescriptor>();
		temp.add(configHashMap.get("a"));
		temp.add(configHashMap.get("b"));
		temp.add(configHashMap.get("c"));
		temp.add(configHashMap.get("d"));
		facesConfigDescriptors = temp;

		String[] originalOrder = Ordering.extractNames(facesConfigDescriptors);

		facesConfigDescriptors = Ordering.getOrder(facesConfigDescriptors);

		String[] orderedNames = Ordering.extractNames(facesConfigDescriptors);

		List<String> original = Arrays.asList(originalOrder);
		List<String> actually = Arrays.asList(orderedNames);
		List<String> expected = Arrays.asList("d", "c", "b", "a");

		// solution:
		// ['d', 'c', 'b', 'a']
		String message = "\n original: " + original + "\n expected: " + expected + "\n actually: " + actually + "\n";
		Assert.assertTrue(message, expected.equals(actually));
		logger.info("test_JAVASERVERFACES_3757_AafterD_BafterCbeforeOthers_CafterDbeforeB_startWithABCD: Passed" +
			message);
	}

	// submitted as an issue in mojarra:
	// https://java.net/jira/browse/JAVASERVERFACES-3757
	@Test
	public void test_JAVASERVERFACES_3757_AafterD_BafterCbeforeOthers_CafterDbeforeB_startWithADBC() throws Exception {
		List<FacesConfigDescriptor> facesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/AafterD_BafterCbeforeOthers_CafterDbeforeB", facesConfigDescriptors,
			META_INF_FACES_CONFIG_XML);
		Collections.shuffle(facesConfigDescriptors);

		HashMap<String, FacesConfigDescriptor> configHashMap = Ordering.getConfigHashMap(facesConfigDescriptors);
		List<FacesConfigDescriptor> temp = new ArrayList<FacesConfigDescriptor>();
		temp.add(configHashMap.get("a"));
		temp.add(configHashMap.get("d"));
		temp.add(configHashMap.get("b"));
		temp.add(configHashMap.get("c"));
		facesConfigDescriptors = temp;

		String[] originalOrder = Ordering.extractNames(facesConfigDescriptors);

		facesConfigDescriptors = Ordering.getOrder(facesConfigDescriptors);

		String[] orderedNames = Ordering.extractNames(facesConfigDescriptors);

		List<String> original = Arrays.asList(originalOrder);
		List<String> actually = Arrays.asList(orderedNames);
		List<String> expected = Arrays.asList("d", "c", "b", "a");

		// solution:
		// ['d', 'c', 'b', 'a']
		String message = "\n original: " + original + "\n expected: " + expected + "\n actually: " + actually + "\n";
		Assert.assertTrue(message, expected.equals(actually));
		logger.info("test_JAVASERVERFACES_3757_AafterD_BafterCbeforeOthers_CafterDbeforeB_startWithADBC: Passed" +
			message);
	}

	// submitted as an issue in mojarra:
	// https://java.net/jira/browse/JAVASERVERFACES-3757
	@Test
	public void test_JAVASERVERFACES_3757_Caftera_startWithCab() throws Exception {

		List<FacesConfigDescriptor> facesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/Caftera_startWithCab", facesConfigDescriptors, META_INF_FACES_CONFIG_XML);

		// Start with C, a,
		HashMap<String, FacesConfigDescriptor> configHashMap = Ordering.getConfigHashMap(facesConfigDescriptors);
		List<FacesConfigDescriptor> temp = new ArrayList<FacesConfigDescriptor>();
		temp.add(configHashMap.get("C"));
		temp.add(configHashMap.get("a"));
		temp.add(configHashMap.get(""));
		facesConfigDescriptors = temp;

		String[] originalOrder = Ordering.extractNames(facesConfigDescriptors);

		facesConfigDescriptors = Ordering.getOrder(facesConfigDescriptors);

		String[] orderedNames = Ordering.extractNames(facesConfigDescriptors);

		// a solution:
		// ['a', '', 'C']
		List<String> original = Arrays.asList(originalOrder);
		List<String> actually = Arrays.asList(orderedNames);

		List<String> possibility1 = Arrays.asList("a", "", "C");

		boolean assertion = (actually.equals(possibility1));
		String message = "\n original: " + original + "\n expected: " + possibility1 + "\n actually: " + actually +
			"\n";
		Assert.assertTrue(message, assertion);
		logger.info("test_JAVASERVERFACES_3757_Caftera_startWithCab: Passed" + message);
	}

	// submitted as an issue in mojarra:
	// https://java.net/jira/browse/JAVASERVERFACES-3757
	@Test
	public void test_JAVASERVERFACES_3757_noOrdering_startWithCab() throws Exception {

		List<FacesConfigDescriptor> facesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/noOrdering_startWithCab", facesConfigDescriptors,
			META_INF_FACES_CONFIG_XML);

		// Start with C, a,
		HashMap<String, FacesConfigDescriptor> configHashMap = Ordering.getConfigHashMap(facesConfigDescriptors);
		List<FacesConfigDescriptor> temp = new ArrayList<FacesConfigDescriptor>();
		temp.add(configHashMap.get("C"));
		temp.add(configHashMap.get("a"));
		temp.add(configHashMap.get(""));
		facesConfigDescriptors = temp;

		String[] originalOrder = Ordering.extractNames(facesConfigDescriptors);

		facesConfigDescriptors = Ordering.getOrder(facesConfigDescriptors);

		String[] orderedNames = Ordering.extractNames(facesConfigDescriptors);

		// a solution:
		// ['C', 'a', '']
		List<String> original = Arrays.asList(originalOrder);
		List<String> actually = Arrays.asList(orderedNames);

		List<String> possibility1 = Arrays.asList("C", "a", "");

		boolean assertion = (actually.equals(possibility1));
		String message = "\n original: " + original + "\n expected: " + possibility1 + "\n actually: " + actually +
			"\n";
		Assert.assertTrue(message, assertion);
		logger.info("test_JAVASERVERFACES_3757_noOrdering_startWithCab: Passed" + message);
	}

}
