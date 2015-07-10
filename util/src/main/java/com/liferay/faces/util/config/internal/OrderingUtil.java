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
package com.liferay.faces.util.config.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Vernon Singleton
 */
public class OrderingUtil {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(OrderingUtil.class);

	// Private Constants
	private static final int MAX_ATTEMPTS = (Integer.MAX_VALUE / (Byte.MAX_VALUE * Byte.MAX_VALUE * Byte.MAX_VALUE)); // 1048

	private static String[] appendAndSort(String[]... groups) {

		HashMap<String, Integer> map = new HashMap<String, Integer>();

		// retain OTHERS, if it is in the first group, but do not allow OTHERS to be appended
		if (groups[0] != null) {

			if (containsOthers(groups[0])) {
				map.put(Ordering.OTHERS, 1);
			}
		}

		for (String[] group : groups) {

			for (String name : group) {

				if (!name.equals(Ordering.OTHERS)) {
					map.put(name, 1);
				}
			}
		}

		Set<String> keySet = map.keySet();
		String[] orderedNames = keySet.toArray(new String[keySet.size()]);
		Arrays.sort(orderedNames);

		return orderedNames;
	}

	private static void checkForBothBeforeAndAfter(FacesConfigDescriptor config)
		throws OrderingBeforeAndAfterException {

		String configName = config.getName();
		Ordering configOrdering = config.getOrdering();
		EnumMap<Ordering.Path, String[]> orderingRoutes = configOrdering.getRoutes();

		HashMap<String, Integer> map = new HashMap<String, Integer>();

		String[] beforeRoutes = orderingRoutes.get(Ordering.Path.BEFORE);

		for (String name : beforeRoutes) {
			Integer value = map.get(name);

			if (value == null) {
				value = 1;
			} else {
				value += 1;
			}

			map.put(name, value);
		}

		String[] afterRoutes = orderingRoutes.get(Ordering.Path.AFTER);

		for (String name : afterRoutes) {
			Integer value = map.get(name);

			if (value == null) {
				value = 1;
			} else {
				value += 1;
			}

			map.put(name, value);
		}

		Set<String> keySet = map.keySet();
		String[] namesToCheck = keySet.toArray(new String[keySet.size()]);

		for (String name : namesToCheck) {

			if (map.get(name) > 1) {
				throw new OrderingBeforeAndAfterException(configName, name);
			}
		}
	}

	private static void checkForSpecExceptions(List<FacesConfigDescriptor> configs)
		throws OrderingBeforeAndAfterException, OrderingCircularDependencyException {

		for (FacesConfigDescriptor config : configs) {

			// Check for "duplicate name exception"
			checkForBothBeforeAndAfter(config);

			// Map the routes along both paths, checking for "circular references" along each path
			for (Ordering.Path path : Ordering.Path.values()) {
				mapRoutes(config, path, configs);
			}
		}
	}

	private static boolean containsOthers(String[] route) {
		return (Arrays.binarySearch(route, Ordering.OTHERS) >= 0);
	}

	private static <K, V extends Comparable<? super V>> Map<K, V> descendingByValue(Map<K, V> map) {
		List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
				public int compare(Map.Entry<K, V> a, Map.Entry<K, V> b) {
					return (b.getValue()).compareTo(a.getValue());
				}
			});

		Map<K, V> result = new LinkedHashMap<K, V>();

		for (Map.Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}

		return result;
	}

	private static LinkedList<String> extractNamesList(FacesConfigDescriptor[] configs) {

		LinkedList<String> names = new LinkedList<String>();

		for (FacesConfigDescriptor config : configs) {
			names.add(config.getName());
		}

		return names;
	}

	private static int innerSort(FacesConfigDescriptor[] configs) throws OrderingMaxAttemptsException {

		int attempts = 0;
		boolean attempting = true;

		while (attempting) {

			if (attempts > MAX_ATTEMPTS) {
				throw new OrderingMaxAttemptsException(MAX_ATTEMPTS);
			}
			else {
				attempting = false;
			}

			int last = configs.length - 1;

			for (int i = 0; i < configs.length; i++) {
				int first = i;
				int second = first + 1;

				if (first == last) {
					second = first;
					first = 0;
				}

				if (isDisordered(configs[first], configs[second])) {
					FacesConfigDescriptor temp = configs[first];
					configs[first] = configs[second];
					configs[second] = temp;
					attempting = true;
				}
			}

			attempts++;
		}

		logger.trace("attempt#{0} of {1}", attempts, MAX_ATTEMPTS);

		return attempts;
	}

	private static void mapRoutes(FacesConfigDescriptor config, Ordering.Path path,
		List<FacesConfigDescriptor> facesConfigs) throws OrderingCircularDependencyException {

		String configName = config.getName();
		Ordering configOrdering = config.getOrdering();
		EnumMap<Ordering.Path, String[]> configOrderingRoutes = configOrdering.getRoutes();
		String[] routePathNames = configOrderingRoutes.get(path);

		for (String routePathName : routePathNames) {

			if (!routePathName.equals(Ordering.OTHERS)) {

				for (FacesConfigDescriptor otherConfig : facesConfigs) {

					String otherConfigName = otherConfig.getName();

					if (routePathName.equals(otherConfigName)) {

						Ordering otherConfigOrdering = otherConfig.getOrdering();
						EnumMap<Ordering.Path, String[]> otherConfigOrderingRoutes = otherConfigOrdering.getRoutes();
						String[] otherRoutePathNames = otherConfigOrderingRoutes.get(path);

						if (Arrays.binarySearch(otherRoutePathNames, configName) >= 0) {

							throw new OrderingCircularDependencyException(path, facesConfigs);
						}

						// If I am before them, they should be informed that they are after me. Similarly, if I am after
						// them, then they should be informed that they are before me.
						Ordering.Path oppositePath;

						if (path == Ordering.Path.BEFORE) {
							oppositePath = Ordering.Path.AFTER;
						}
						else {
							oppositePath = Ordering.Path.BEFORE;
						}

						if (Arrays.binarySearch(otherConfigOrderingRoutes.get(oppositePath), configName) < 0) {
							EnumMap<Ordering.Path, String[]> routes = new EnumMap<Ordering.Path, String[]>(
									Ordering.Path.class);
							routes.put(path, otherRoutePathNames);
							routes.put(oppositePath,
								appendAndSort(otherConfigOrderingRoutes.get(oppositePath),
									new String[] { configName }));
							otherConfigOrdering.setRoutes(routes);
						}

						// If I am before them and they are before others, then I should be informed that I am before
						// others too. Similarly, if I am after them and they are after others, then I should be
						// informed that I am after others too.
						if (otherRoutePathNames.length > 0) {
							EnumMap<Ordering.Path, String[]> routes = new EnumMap<Ordering.Path, String[]>(
									Ordering.Path.class);
							routes.put(path, appendAndSort(routePathNames, otherRoutePathNames));
							routes.put(oppositePath, configOrderingRoutes.get(oppositePath));
							configOrdering.setRoutes(routes);
						}
					}
				}
			}
		}
	}

	private static void postSort(FacesConfigDescriptor[] configs) {

		int i = 0;

		while (i < configs.length) {

			LinkedList<String> names = extractNamesList(configs);

			boolean done = true;

			for (int j = 0; j < configs.length; j++) {
				int k = 0;

				for (String configName : names) {

					if (configs[j].getName().equals(configName)) {
						break;
					}

					if (configs[j].getOrdering().isBefore(configName)) {

						// We have a document that is out of order, and his index is k, he belongs at index j, and all
						// the documents in between need to be shifted left.
						FacesConfigDescriptor temp = null;

						for (int m = 0; m < configs.length; m++) {

							// This is one that is out of order and needs to be moved.
							if (m == k) {
								temp = configs[m];
							}

							// This is one in between that needs to be shifted left.
							if ((temp != null) && (m != j)) {
								configs[m] = configs[m + 1];
							}

							// This is where the one that is out of order needs to be moved to.
							if (m == j) {
								configs[m] = temp;

								done = false;

								break;
							}
						}

						if (!done) {
							break;
						}
					}

					k = k + 1;

				}
			}

			if (done) {
				break;
			}
		}
	}

	private static List<FacesConfigDescriptor> preSort(List<FacesConfigDescriptor> configs) {

		List<FacesConfigDescriptor> newConfigList = new ArrayList<FacesConfigDescriptor>();
		List<FacesConfigDescriptor> anonAndUnordered = new LinkedList<FacesConfigDescriptor>();
		Map<String, Integer> namedMap = new LinkedHashMap<String, Integer>();

		for (FacesConfigDescriptor config : configs) {

			Ordering configOrdering = config.getOrdering();
			EnumMap<Ordering.Path, String[]> configOrderingRoutes = configOrdering.getRoutes();
			String[] beforePathNames = configOrderingRoutes.get(Ordering.Path.BEFORE);
			String[] afterPathNames = configOrderingRoutes.get(Ordering.Path.AFTER);

			String configName = config.getName();

			if (((configName == null) || (configName.length() == 0)) && !configOrdering.isOrdered()) {
				anonAndUnordered.add(config);
			}
			else {
				int totalPathNames = beforePathNames.length + afterPathNames.length;
				namedMap.put(configName, totalPathNames);
			}
		}

		namedMap = descendingByValue(namedMap);

		Map<String, FacesConfigDescriptor> configMap = getConfigMap(configs);

		// add named configs to the list in the correct preSorted order
		for (Map.Entry<String, Integer> entry : namedMap.entrySet()) {
			String key = entry.getKey();
			newConfigList.add(configMap.get(key));
		}

		// add configs that are both anonymous and unordered, to the list in their original, incoming order
		for (FacesConfigDescriptor config : anonAndUnordered) {
			newConfigList.add(config);
		}

		return newConfigList;
	}

	public static Map<String, FacesConfigDescriptor> getConfigMap(List<FacesConfigDescriptor> facesConfigDescriptors) {

		Map<String, FacesConfigDescriptor> configMap = new HashMap<String, FacesConfigDescriptor>();

		for (FacesConfigDescriptor facesConfigDescriptor : facesConfigDescriptors) {
			String name = facesConfigDescriptor.getName();
			configMap.put(name, facesConfigDescriptor);
		}

		return configMap;
	}

	private static boolean isDisordered(FacesConfigDescriptor config1, FacesConfigDescriptor config2) {

		String config1Name = config1.getName();
		String config2Name = config2.getName();

		Ordering config1Ordering = config1.getOrdering();
		Ordering config2Ordering = config2.getOrdering();

		if (config1Ordering.isOrdered() && !config2Ordering.isOrdered()) {

			if ((config1Ordering.getRoutes().get(Ordering.Path.AFTER).length != 0) &&
					!config1Ordering.isBeforeOthers()) {
				return true;
			}
		}

		// they are not in the specified order
		if (config2Ordering.isBefore(config1Name) || config1Ordering.isAfter(config2Name)) {
			return true;
		}

		// config1 should be after others, but it is not
		if (config1Ordering.isAfterOthers() && !config1Ordering.isBefore(config2Name) &&
				!(config1Ordering.isAfterOthers() && config2Ordering.isAfterOthers())) {
			return true;
		}

		// config2 should be before others, but it is not
		return config2Ordering.isBeforeOthers() && !config2Ordering.isAfter(config1Name) &&
			!(config1Ordering.isBeforeOthers() && config2Ordering.isBeforeOthers());
	}

	/**
	 * This method returns an ordered version of the specified list of faces-config.xml descriptors and assumes that
	 * there is no absolute ordering.
	 */
	public static List<FacesConfigDescriptor> getOrder(List<FacesConfigDescriptor> configList)
		throws OrderingBeforeAndAfterException, OrderingCircularDependencyException, OrderingMaxAttemptsException {

		if (logger.isTraceEnabled()) {

			for (FacesConfigDescriptor config : configList) {
				String name = config.getName();
				Ordering ordering = config.getOrdering();

				if (ordering != null) {
					EnumMap<Ordering.Path, String[]> routes = ordering.getRoutes();

					if (routes != null) {

						String[] beforeRoutes = routes.get(Ordering.Path.BEFORE);

						if (beforeRoutes.length != 0) {
							logger.trace("before name=[{0}] b routes=[{1}] beforeOthers=[{2}]", name,
								Arrays.asList(beforeRoutes).toString(), ordering.isBeforeOthers());
						}

						String[] afterRoutes = routes.get(Ordering.Path.AFTER);

						if (afterRoutes.length != 0) {
							logger.trace("before name=[{0}] a routes=[{1}] afterOthers=[{2}]", name,
								Arrays.asList(afterRoutes).toString(), ordering.isAfterOthers());
						}
					}
				}
			}
		}

		// Check for "duplicate name exception" and "circular references" as described in 11.4.8 Ordering of Artifacts
		checkForSpecExceptions(configList);

		if (logger.isTraceEnabled()) {

			for (FacesConfigDescriptor config : configList) {
				String name = config.getName();
				Ordering ordering = config.getOrdering();

				if (ordering != null) {
					EnumMap<Ordering.Path, String[]> routes = ordering.getRoutes();

					if (routes != null) {

						String[] beforeRoutes = routes.get(Ordering.Path.BEFORE);

						if (beforeRoutes.length != 0) {
							logger.trace("after name=[{0}] b routes=[{1}] beforeOthers=[{2}]", name,
								Arrays.asList(beforeRoutes).toString(), ordering.isBeforeOthers());
						}

						String[] afterRoutes = routes.get(Ordering.Path.AFTER);

						if (afterRoutes.length != 0) {
							logger.trace("after name=[{0}] b routes=[{1}] afterOthers=[{2}]", name,
								Arrays.asList(afterRoutes).toString(), ordering.isAfterOthers());
						}
					}
				}
			}
		}

		// Sort the documents such that specified ordering will be considered.
		//
		// It turns out that some of the specified ordering, if it was not discovered by the sort routine
		// until later in its processing, was not being considered correctly in the ordering algorithm.
		//
		// This preSort method puts all of the documents with specified ordering as early on in the
		// list of documents as possible for to consider it quickly, and be
		// able to use its ordering algorithm to the best of its ability to achieve the specified ordering.
		configList = preSort(configList);

		FacesConfigDescriptor[] configs = configList.toArray(new FacesConfigDescriptor[configList.size()]);

		// This is a multiple pass sorting routine which gets the documents close to the order they need to be in
		innerSort(configs);

		// This is the final sort which checks the list from left to right to see if they are in the specified order and
		// if they are not, it moves the incorrectly placed document(s) to the right into its proper place, and 
		// shifts others left as necessary.
		postSort(configs);

		return new ArrayList<FacesConfigDescriptor>(Arrays.asList(configs));
	}

	/**
	 * This method returns an ordered version of the specified list of faces-config.xml descriptors, taking the
	 * specified absolute ordering into account.
	 */
	public static List<FacesConfigDescriptor> getOrder(List<FacesConfigDescriptor> configs,
		List<String> absoluteOrder) {

		List<FacesConfigDescriptor> orderedList = new ArrayList<FacesConfigDescriptor>();

		List<FacesConfigDescriptor> configList = new CopyOnWriteArrayList<FacesConfigDescriptor>();
		configList.addAll(configs);

		for (String name : absoluteOrder) {

			if (Ordering.OTHERS.equals(name)) {
				continue;
			}

			boolean found = false;

			for (FacesConfigDescriptor config : configList) {

				if (!found && name.equals(config.getName())) {
					found = true;
					orderedList.add(config);
					configList.remove(config);
				}
				else if (found && name.equals(config.getName())) {
					logger.warn("name=[{0}] found more than once", name);

					break;
				}
			}

			if (!found) {
				logger.warn("name=[{0}] specified in absolute-ordering was not found", name);
			}
		}

		int othersIndex = absoluteOrder.indexOf(Ordering.OTHERS);

		if (othersIndex != -1) {

			for (FacesConfigDescriptor config : configList) {
				orderedList.add(othersIndex, config);
			}
		}

		return orderedList;
	}
}
