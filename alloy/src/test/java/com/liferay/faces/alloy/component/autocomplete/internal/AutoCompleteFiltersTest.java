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
package com.liferay.faces.alloy.component.autocomplete.internal;

import java.util.Arrays;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;


/**
 * @author  Kyle Stiemann
 */
public class AutoCompleteFiltersTest {

	private AutoCompleteFilterFactory autoCompleteFilterFactory;

	public AutoCompleteFiltersTest() {
		autoCompleteFilterFactory = new AutoCompleteFilterFactoryImpl();
	}

	@Test
	public void charMatchFilterTest() {

		AutoCompleteFilter autoCompleteFilter = autoCompleteFilterFactory.getAutoCompleteFilter("charMatch");
		Assert.assertTrue(autoCompleteFilter.doFilter("abcd", Arrays.asList("red", "black", "blue"), false,
				Locale.ENGLISH).isEmpty());
		Assert.assertTrue(Arrays.asList("black", "black and white").equals(
				autoCompleteFilter.doFilter("abc", Arrays.asList("red", "black", "blue", "black and white"), false,
					Locale.ENGLISH)));
		Assert.assertTrue(Arrays.asList("Red", "red").equals(
				autoCompleteFilter.doFilter("r", Arrays.asList("Red", "red"), false, Locale.ENGLISH)));
		Assert.assertTrue(Arrays.asList("red").equals(
				autoCompleteFilter.doFilter("r", Arrays.asList("Red", "red"), true, Locale.ENGLISH)));
	}

	@Test
	public void phraseMatchFilterTest() {

		AutoCompleteFilter autoCompleteFilter = autoCompleteFilterFactory.getAutoCompleteFilter("phraseMatch");
		Assert.assertTrue(autoCompleteFilter.doFilter("red blue", Arrays.asList("red", "black", "red black"), false,
				Locale.ENGLISH).isEmpty());
		Assert.assertTrue(Arrays.asList("red black").equals(
				autoCompleteFilter.doFilter("red black", Arrays.asList("red", "black", "red black"), false,
					Locale.ENGLISH)));
		Assert.assertTrue(Arrays.asList("xxred blackxx").equals(
				autoCompleteFilter.doFilter("red black", Arrays.asList("red", "black", "xxred blackxx"), false,
					Locale.ENGLISH)));
		Assert.assertTrue(Arrays.asList("red blackxx").equals(
				autoCompleteFilter.doFilter("red black", Arrays.asList("red", "black", "red blackxx"), false,
					Locale.ENGLISH)));
		Assert.assertTrue(Arrays.asList("xxred black").equals(
				autoCompleteFilter.doFilter("red black", Arrays.asList("red", "black", "xxred black"), false,
					Locale.ENGLISH)));
		Assert.assertTrue(Arrays.asList("Red", "red").equals(
				autoCompleteFilter.doFilter("red", Arrays.asList("Red", "red"), false, Locale.ENGLISH)));
		Assert.assertTrue(Arrays.asList("red").equals(
				autoCompleteFilter.doFilter("red", Arrays.asList("Red", "red"), true, Locale.ENGLISH)));
	}

	@Test
	public void startsWithFilterTest() {

		AutoCompleteFilter autoCompleteFilter = autoCompleteFilterFactory.getAutoCompleteFilter("startsWith");
		Assert.assertTrue(autoCompleteFilter.doFilter("red", Arrays.asList("xx red", "black", "xx red black"), false,
				Locale.ENGLISH).isEmpty());
		Assert.assertTrue(Arrays.asList("red", "red black").equals(
				autoCompleteFilter.doFilter("red", Arrays.asList("red", "black", "red black"), false, Locale.ENGLISH)));
		Assert.assertTrue(Arrays.asList("Red", "red").equals(
				autoCompleteFilter.doFilter("red", Arrays.asList("Red", "red"), false, Locale.ENGLISH)));
		Assert.assertTrue(Arrays.asList("red").equals(
				autoCompleteFilter.doFilter("red", Arrays.asList("Red", "red"), true, Locale.ENGLISH)));
	}

	@Test
	public void subWordMatchTest() {

		AutoCompleteFilter autoCompleteFilter = autoCompleteFilterFactory.getAutoCompleteFilter("subWordMatch");
		Assert.assertTrue(autoCompleteFilter.doFilter("red black blue", Arrays.asList("red", "black", "blue"), false,
				Locale.ENGLISH).isEmpty());
		Assert.assertTrue(Arrays.asList("red black blue", "redblack blue").equals(
				autoCompleteFilter.doFilter("blue red black",
					Arrays.asList("red", "black", "red black blue", "redblack blue"), false, Locale.ENGLISH)));
		Assert.assertTrue(Arrays.asList("John Sim").equals(
				autoCompleteFilter.doFilter("John", Arrays.asList("John Sim", "Joe Sim", "Robert Kim"), false,
					Locale.ENGLISH)));
		Assert.assertTrue(Arrays.asList("John Sim", "Joe Sim").equals(
				autoCompleteFilter.doFilter("J. Sim", Arrays.asList("John Sim", "Joe Sim", "Robert Kim"), false,
					Locale.ENGLISH)));
		Assert.assertTrue(Arrays.asList("John Sim", "Joe Sim").equals(
				autoCompleteFilter.doFilter("S., Jo.", Arrays.asList("John Sim", "Joe Sim", "Robert Kim"), false,
					Locale.ENGLISH)));
		Assert.assertTrue(Arrays.asList("John Sim", "Joe Sim", "Robert Kim [12345]").equals(
				autoCompleteFilter.doFilter("im", Arrays.asList("John Sim", "Joe Sim", "Robert Kim [12345]", "O.E."),
					false, Locale.ENGLISH)));
		Assert.assertTrue(Arrays.asList("Jane-Jones").equals(
				autoCompleteFilter.doFilter("(Jones-Jan.)", Arrays.asList("Jane-Jones", "Jan-Smith"), false,
					Locale.ENGLISH)));
		Assert.assertTrue(Arrays.asList("Red", "red").equals(
				autoCompleteFilter.doFilter("red", Arrays.asList("Red", "red"), false, Locale.ENGLISH)));
		Assert.assertTrue(Arrays.asList("red").equals(
				autoCompleteFilter.doFilter("red", Arrays.asList("Red", "red"), true, Locale.ENGLISH)));
	}

	@Test
	public void wordMatchTest() {

		AutoCompleteFilter autoCompleteFilter = autoCompleteFilterFactory.getAutoCompleteFilter("wordMatch");
		Assert.assertTrue(autoCompleteFilter.doFilter("red black blue", Arrays.asList("red", "black", "blue"), false,
				Locale.ENGLISH).isEmpty());
		Assert.assertTrue(Arrays.asList("red black blue").equals(
				autoCompleteFilter.doFilter("blue red black",
					Arrays.asList("red", "black", "red black blue", "redblack blue"), false, Locale.ENGLISH)));
		Assert.assertTrue(Arrays.asList("red", "red black blue").equals(
				autoCompleteFilter.doFilter("red", Arrays.asList("red", "black", "red black blue", "redblack blue"),
					false, Locale.ENGLISH)));
		Assert.assertTrue(Arrays.asList("Red", "red").equals(
				autoCompleteFilter.doFilter("red", Arrays.asList("Red", "red"), false, Locale.ENGLISH)));
		Assert.assertTrue(Arrays.asList("red").equals(
				autoCompleteFilter.doFilter("red", Arrays.asList("Red", "red"), true, Locale.ENGLISH)));
	}
}
