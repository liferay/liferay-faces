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
package com.liferay.faces.bridge.container.liferay;

import java.util.Calendar;

import org.junit.Test;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.kernel.util.StringBundler;

import junit.framework.Assert;


/**
 * The purpose of this test is to verify that the {@link LiferaySharedPageTop} class is working properly in a
 * multi-threaded environment. It starts up 10,000 threads, each of which attempts to create a {@link
 * LiferaySharedPageTop} instance that parses an XML document and removes duplicate link and script elements. Note that
 * not all of the threads have a chance to terminate gracefully before the JVM terminates, but that's OK.
 *
 * @author  Neil Griffin
 */
public class LiferaySharedPageTopTest {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(LiferaySharedPageTopTest.class);

	// Private Constants
	private static final String RESOURCES_PORTLET1;
	private static final String RESOURCES_PORTLET2;
	private static final StringBundler RESOURCES_BOTH_PORTLETS;
	private static final int TOTAL_THREADS = 10000;

	static {
		RESOURCES_BOTH_PORTLETS = new StringBundler();

		StringBuilder buf = new StringBuilder();
		buf.append(
			"<link rel=\"stylesheet\" type=\"text/css\" href=\"http://localhost:8080/web/guest/double-pf?p_p_id=1_WAR_primefaces3portlet_INSTANCE_7sweRQbXT8GJ&amp;p_p_lifecycle=2&amp;p_p_state=normal&amp;p_p_mode=view&amp;p_p_cacheability=cacheLevelPage&amp;p_p_col_id=column-2&amp;p_p_col_count=1&amp;_1_WAR_primefaces3portlet_INSTANCE_7sweRQbXT8GJ_javax.faces.resource=theme.css&amp;_1_WAR_primefaces3portlet_INSTANCE_7sweRQbXT8GJ_ln=primefaces-aristo\"></link>");
		buf.append(
			"<link rel=\"stylesheet\" type=\"text/css\" href=\"http://localhost:8080/web/guest/double-pf?p_p_id=1_WAR_primefaces3portlet_INSTANCE_7sweRQbXT8GJ&amp;p_p_lifecycle=2&amp;p_p_state=normal&amp;p_p_mode=view&amp;p_p_cacheability=cacheLevelPage&amp;p_p_col_id=column-2&amp;p_p_col_count=1&amp;_1_WAR_primefaces3portlet_INSTANCE_7sweRQbXT8GJ_javax.faces.resource=primefaces.css&amp;_1_WAR_primefaces3portlet_INSTANCE_7sweRQbXT8GJ_ln=primefaces\"></link>");
		buf.append(
			"<link rel=\"stylesheet\" type=\"text/css\" href=\"http://localhost:8080/web/guest/double-pf?p_p_id=1_WAR_primefaces3portlet_INSTANCE_7sweRQbXT8GJ&amp;p_p_lifecycle=2&amp;p_p_state=normal&amp;p_p_mode=view&amp;p_p_cacheability=cacheLevelPage&amp;p_p_col_id=column-2&amp;p_p_col_count=1&amp;_1_WAR_primefaces3portlet_INSTANCE_7sweRQbXT8GJ_javax.faces.resource=fileupload%2Ffileupload.css&amp;_1_WAR_primefaces3portlet_INSTANCE_7sweRQbXT8GJ_ln=primefaces\"></link>");
		buf.append(
			"<link rel=\"stylesheet\" type=\"text/css\" href=\"http://localhost:8080/web/guest/double-pf?p_p_id=1_WAR_primefaces3portlet_INSTANCE_7sweRQbXT8GJ&amp;p_p_lifecycle=2&amp;p_p_state=normal&amp;p_p_mode=view&amp;p_p_cacheability=cacheLevelPage&amp;p_p_col_id=column-2&amp;p_p_col_count=1&amp;_1_WAR_primefaces3portlet_INSTANCE_7sweRQbXT8GJ_javax.faces.resource=example.css&amp;_1_WAR_primefaces3portlet_INSTANCE_7sweRQbXT8GJ_ln=example\"></link>");
		buf.append(
			"<link rel=\"stylesheet\" type=\"text/css\" href=\"http://localhost:8080/web/guest/double-pf?p_p_id=1_WAR_primefaces3portlet_INSTANCE_7sweRQbXT8GJ&amp;p_p_lifecycle=2&amp;p_p_state=normal&amp;p_p_mode=view&amp;p_p_cacheability=cacheLevelPage&amp;p_p_col_id=column-2&amp;p_p_col_count=1&amp;_1_WAR_primefaces3portlet_INSTANCE_7sweRQbXT8GJ_javax.faces.resource=portlet.css&amp;_1_WAR_primefaces3portlet_INSTANCE_7sweRQbXT8GJ_ln=example\"></link>");
		buf.append(
			"<link rel=\"stylesheet\" type=\"text/css\" href=\"http://localhost:8080/web/guest/double-pf?p_p_id=1_WAR_primefaces3portlet_INSTANCE_7sweRQbXT8GJ&amp;p_p_lifecycle=2&amp;p_p_state=normal&amp;p_p_mode=view&amp;p_p_cacheability=cacheLevelPage&amp;p_p_col_id=column-2&amp;p_p_col_count=1&amp;_1_WAR_primefaces3portlet_INSTANCE_7sweRQbXT8GJ_javax.faces.resource=liferay-theme-override.css&amp;_1_WAR_primefaces3portlet_INSTANCE_7sweRQbXT8GJ_ln=example\"></link>");
		buf.append(
			"<script src=\"http://localhost:8080/web/guest/double-pf?p_p_id=1_WAR_primefaces3portlet_INSTANCE_7sweRQbXT8GJ&amp;p_p_lifecycle=2&amp;p_p_state=normal&amp;p_p_mode=view&amp;p_p_cacheability=cacheLevelPage&amp;p_p_col_id=column-2&amp;p_p_col_count=1&amp;_1_WAR_primefaces3portlet_INSTANCE_7sweRQbXT8GJ_javax.faces.resource=jquery%2Fjquery.js&amp;_1_WAR_primefaces3portlet_INSTANCE_7sweRQbXT8GJ_ln=primefaces\" type=\"text/javascript\"></script>");
		buf.append(
			"<script src=\"http://localhost:8080/web/guest/double-pf?p_p_id=1_WAR_primefaces3portlet_INSTANCE_7sweRQbXT8GJ&amp;p_p_lifecycle=2&amp;p_p_state=normal&amp;p_p_mode=view&amp;p_p_cacheability=cacheLevelPage&amp;p_p_col_id=column-2&amp;p_p_col_count=1&amp;_1_WAR_primefaces3portlet_INSTANCE_7sweRQbXT8GJ_javax.faces.resource=primefaces.js&amp;_1_WAR_primefaces3portlet_INSTANCE_7sweRQbXT8GJ_ln=primefaces\" type=\"text/javascript\"></script>");
		buf.append(
			"<script src=\"http://localhost:8080/web/guest/double-pf?p_p_id=1_WAR_primefaces3portlet_INSTANCE_7sweRQbXT8GJ&amp;p_p_lifecycle=2&amp;p_p_state=normal&amp;p_p_mode=view&amp;p_p_cacheability=cacheLevelPage&amp;p_p_col_id=column-2&amp;p_p_col_count=1&amp;_1_WAR_primefaces3portlet_INSTANCE_7sweRQbXT8GJ_javax.faces.resource=jsf.js&amp;_1_WAR_primefaces3portlet_INSTANCE_7sweRQbXT8GJ_ln=javax.faces&amp;_1_WAR_primefaces3portlet_INSTANCE_7sweRQbXT8GJ_stage=Development\" type=\"text/javascript\"></script>");
		buf.append(
			"<script src=\"http://localhost:8080/web/guest/double-pf?p_p_id=1_WAR_primefaces3portlet_INSTANCE_7sweRQbXT8GJ&amp;p_p_lifecycle=2&amp;p_p_state=normal&amp;p_p_mode=view&amp;p_p_cacheability=cacheLevelPage&amp;p_p_col_id=column-2&amp;p_p_col_count=1&amp;_1_WAR_primefaces3portlet_INSTANCE_7sweRQbXT8GJ_javax.faces.resource=fileupload%2Ffileupload.js&amp;_1_WAR_primefaces3portlet_INSTANCE_7sweRQbXT8GJ_ln=primefaces\" type=\"text/javascript\"></script>");
		RESOURCES_PORTLET1 = buf.toString();
		RESOURCES_BOTH_PORTLETS.append(RESOURCES_PORTLET1);

		buf = new StringBuilder();
		buf.append(
			"<link rel=\"stylesheet\" type=\"text/css\" href=\"http://localhost:8080/web/guest/double-pf?p_p_id=1_WAR_primefaces3portlet_INSTANCE_lagDN0HOEH1J&amp;p_p_lifecycle=2&amp;p_p_state=normal&amp;p_p_mode=view&amp;p_p_cacheability=cacheLevelPage&amp;p_p_col_id=column-1&amp;p_p_col_count=1&amp;_1_WAR_primefaces3portlet_INSTANCE_lagDN0HOEH1J_javax.faces.resource=theme.css&amp;_1_WAR_primefaces3portlet_INSTANCE_lagDN0HOEH1J_ln=primefaces-aristo\"></link>");
		buf.append(
			"<link rel=\"stylesheet\" type=\"text/css\" href=\"http://localhost:8080/web/guest/double-pf?p_p_id=1_WAR_primefaces3portlet_INSTANCE_lagDN0HOEH1J&amp;p_p_lifecycle=2&amp;p_p_state=normal&amp;p_p_mode=view&amp;p_p_cacheability=cacheLevelPage&amp;p_p_col_id=column-1&amp;p_p_col_count=1&amp;_1_WAR_primefaces3portlet_INSTANCE_lagDN0HOEH1J_javax.faces.resource=primefaces.css&amp;_1_WAR_primefaces3portlet_INSTANCE_lagDN0HOEH1J_ln=primefaces\"></link>");
		buf.append(
			"<link rel=\"stylesheet\" type=\"text/css\" href=\"http://localhost:8080/web/guest/double-pf?p_p_id=1_WAR_primefaces3portlet_INSTANCE_lagDN0HOEH1J&amp;p_p_lifecycle=2&amp;p_p_state=normal&amp;p_p_mode=view&amp;p_p_cacheability=cacheLevelPage&amp;p_p_col_id=column-1&amp;p_p_col_count=1&amp;_1_WAR_primefaces3portlet_INSTANCE_lagDN0HOEH1J_javax.faces.resource=fileupload%2Ffileupload.css&amp;_1_WAR_primefaces3portlet_INSTANCE_lagDN0HOEH1J_ln=primefaces\"></link>");
		buf.append(
			"<link rel=\"stylesheet\" type=\"text/css\" href=\"http://localhost:8080/web/guest/double-pf?p_p_id=1_WAR_primefaces3portlet_INSTANCE_lagDN0HOEH1J&amp;p_p_lifecycle=2&amp;p_p_state=normal&amp;p_p_mode=view&amp;p_p_cacheability=cacheLevelPage&amp;p_p_col_id=column-1&amp;p_p_col_count=1&amp;_1_WAR_primefaces3portlet_INSTANCE_lagDN0HOEH1J_javax.faces.resource=example.css&amp;_1_WAR_primefaces3portlet_INSTANCE_lagDN0HOEH1J_ln=example\"></link>");
		buf.append(
			"<link rel=\"stylesheet\" type=\"text/css\" href=\"http://localhost:8080/web/guest/double-pf?p_p_id=1_WAR_primefaces3portlet_INSTANCE_lagDN0HOEH1J&amp;p_p_lifecycle=2&amp;p_p_state=normal&amp;p_p_mode=view&amp;p_p_cacheability=cacheLevelPage&amp;p_p_col_id=column-1&amp;p_p_col_count=1&amp;_1_WAR_primefaces3portlet_INSTANCE_lagDN0HOEH1J_javax.faces.resource=portlet.css&amp;_1_WAR_primefaces3portlet_INSTANCE_lagDN0HOEH1J_ln=example\"></link>");
		buf.append(
			"<link rel=\"stylesheet\" type=\"text/css\" href=\"http://localhost:8080/web/guest/double-pf?p_p_id=1_WAR_primefaces3portlet_INSTANCE_lagDN0HOEH1J&amp;p_p_lifecycle=2&amp;p_p_state=normal&amp;p_p_mode=view&amp;p_p_cacheability=cacheLevelPage&amp;p_p_col_id=column-1&amp;p_p_col_count=1&amp;_1_WAR_primefaces3portlet_INSTANCE_lagDN0HOEH1J_javax.faces.resource=liferay-theme-override.css&amp;_1_WAR_primefaces3portlet_INSTANCE_lagDN0HOEH1J_ln=example\"></link>");
		buf.append(
			"<script src=\"http://localhost:8080/web/guest/double-pf?p_p_id=1_WAR_primefaces3portlet_INSTANCE_lagDN0HOEH1J&amp;p_p_lifecycle=2&amp;p_p_state=normal&amp;p_p_mode=view&amp;p_p_cacheability=cacheLevelPage&amp;p_p_col_id=column-1&amp;p_p_col_count=1&amp;_1_WAR_primefaces3portlet_INSTANCE_lagDN0HOEH1J_javax.faces.resource=jquery%2Fjquery.js&amp;_1_WAR_primefaces3portlet_INSTANCE_lagDN0HOEH1J_ln=primefaces\" type=\"text/javascript\"></script>");
		buf.append(
			"<script src=\"http://localhost:8080/web/guest/double-pf?p_p_id=1_WAR_primefaces3portlet_INSTANCE_lagDN0HOEH1J&amp;p_p_lifecycle=2&amp;p_p_state=normal&amp;p_p_mode=view&amp;p_p_cacheability=cacheLevelPage&amp;p_p_col_id=column-1&amp;p_p_col_count=1&amp;_1_WAR_primefaces3portlet_INSTANCE_lagDN0HOEH1J_javax.faces.resource=primefaces.js&amp;_1_WAR_primefaces3portlet_INSTANCE_lagDN0HOEH1J_ln=primefaces\" type=\"text/javascript\"></script>");
		buf.append(
			"<script src=\"http://localhost:8080/web/guest/double-pf?p_p_id=1_WAR_primefaces3portlet_INSTANCE_lagDN0HOEH1J&amp;p_p_lifecycle=2&amp;p_p_state=normal&amp;p_p_mode=view&amp;p_p_cacheability=cacheLevelPage&amp;p_p_col_id=column-1&amp;p_p_col_count=1&amp;_1_WAR_primefaces3portlet_INSTANCE_lagDN0HOEH1J_javax.faces.resource=jsf.js&amp;_1_WAR_primefaces3portlet_INSTANCE_lagDN0HOEH1J_ln=javax.faces&amp;_1_WAR_primefaces3portlet_INSTANCE_lagDN0HOEH1J_stage=Development\" type=\"text/javascript\"></script>");
		buf.append(
			"<script src=\"http://localhost:8080/web/guest/double-pf?p_p_id=1_WAR_primefaces3portlet_INSTANCE_lagDN0HOEH1J&amp;p_p_lifecycle=2&amp;p_p_state=normal&amp;p_p_mode=view&amp;p_p_cacheability=cacheLevelPage&amp;p_p_col_id=column-1&amp;p_p_col_count=1&amp;_1_WAR_primefaces3portlet_INSTANCE_lagDN0HOEH1J_javax.faces.resource=fileupload%2Ffileupload.js&amp;_1_WAR_primefaces3portlet_INSTANCE_lagDN0HOEH1J_ln=primefaces\" type=\"text/javascript\"></script>");
		RESOURCES_PORTLET2 = buf.toString();
		RESOURCES_BOTH_PORTLETS.append(RESOURCES_PORTLET2);

	}

	// Private Data Members
	private Integer totalPassed = 0;
	private Integer totalFailed = 0;

	public synchronized void incrementTotalFailed() {
		totalPassed++;
	}

	public synchronized void incrementTotalPassed() {
		totalFailed++;
	}

	@Test
	public void testMultiThreaded() {

		long start = Calendar.getInstance().getTimeInMillis();

		for (int i = 0; i < 10000; i++) {

			Thread testThread = new TestThread(this);
			testThread.start();

		}

		boolean done = false;

		while (!done) {

			if ((totalFailed > 0) || (totalPassed == TOTAL_THREADS)) {
				done = true;
			}
			else {

				try {
					Thread.sleep(5);
				}
				catch (InterruptedException e) {
					done = true;
				}
			}
		}

		Assert.assertEquals(0, totalFailed.intValue());
		Assert.assertEquals(TOTAL_THREADS, totalPassed.intValue());

		long finish = Calendar.getInstance().getTimeInMillis();
		long duration = finish - start;

		logger.info("Started {0} asynchronous threads, duration=[{1}ms] passed=[{2}] failed=[{3}]", TOTAL_THREADS,
			duration, totalPassed, totalFailed);
	}

	protected class TestThread extends Thread {

		// Private Data Members
		LiferaySharedPageTopTest liferaySharedPageTopTest;

		public TestThread(LiferaySharedPageTopTest liferaySharedPageTopTest) {
			this.liferaySharedPageTopTest = liferaySharedPageTopTest;
		}

		@Override
		public void run() {

			try {
				LiferaySharedPageTop liferaySharedPageTop = new LiferaySharedPageTop(RESOURCES_BOTH_PORTLETS);
				liferaySharedPageTop.removeDuplicates();

				String resourcesResult = liferaySharedPageTop.toStringBundler().toString();

				if (RESOURCES_PORTLET1.equals(resourcesResult)) {
					liferaySharedPageTopTest.incrementTotalFailed();
				}
				else {
					liferaySharedPageTopTest.incrementTotalPassed();
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
