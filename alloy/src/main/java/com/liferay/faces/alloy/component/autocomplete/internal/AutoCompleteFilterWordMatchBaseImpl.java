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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;


/**
 * @author  Kyle Stiemann
 */
abstract class AutoCompleteFilterWordMatchBaseImpl implements AutoCompleteFilter {

	private static final Map<PATTERN_KEYS, Pattern> patterns = new EnumMap<PATTERN_KEYS, Pattern>(PATTERN_KEYS.class);
	private static final Pattern SINGLE_QUOTE_PATTERN = Pattern.compile("'");
	private static final Pattern WHITESPACE = Pattern.compile("\\s");

	static {

		String path = "META-INF/resources/liferay-faces-alloy/yui/autocomplete/text-data-wordbreak.js";
		InputStream inputStream = AutoCompleteFilterWordMatchBaseImpl.class.getClassLoader().getResourceAsStream(path);
		Scanner scanner = new Scanner(inputStream);
		Pattern beginLinePattern = Pattern.compile("^\\s*[a-z][a-z]*\\s*:\\s[\"']");
		Pattern endLinePattern = Pattern.compile("[\"'],?$");

		int i = 0;

		while (scanner.hasNextLine()) {
			String nextLine = scanner.nextLine();

			if (beginLinePattern.matcher(nextLine).find()) {

				String regex = beginLinePattern.matcher(nextLine).replaceFirst("");
				regex = endLinePattern.matcher(regex).replaceFirst("");

				Pattern pattern;

				if (PATTERN_KEYS.PUNCTUATION == PATTERN_KEYS.values()[i]) {
					pattern = Pattern.compile("^" + regex + "$");
				}
				else {
					pattern = Pattern.compile(regex);
				}

				patterns.put(PATTERN_KEYS.values()[i], pattern);
				i++;
			}
		}

		scanner.close();
	}

	private static enum PATTERN_KEYS {
		ALETTER, MIDNUMLET, MIDLETTER, MIDNUM, NUMERIC, CR, LF, NEWLINE, EXTEND, FORMAT, KATAKANA, EXTENDEDNUMLET,
		PUNCTUATION
	}

	private static boolean matches(String character, PATTERN_KEYS patternKey) {
		return patterns.get(patternKey).matcher(character).matches();
	}

	protected List<String> getWords(String words) {

		// http://unicode.org/reports/tr29/#Word_Boundary_Rules

		List<String> wordList = new ArrayList<String>();

		if ((words != null) && (words.length() > 0)) {

			StringBuilder stringBuilder = new StringBuilder();

			for (int i = 0; i < words.length(); i++) {

				String character = Character.toString(words.charAt(i));

				if ((i + 1) == words.length()) {

					stringBuilder.append(character);
					wordList.add(stringBuilder.toString());
				}
				else {

					String nextCharacter = Character.toString(words.charAt(i + 1));
					String nextNextCharacter = null;

					if ((i + 1 + 1) < words.length()) {
						nextNextCharacter = Character.toString(words.charAt(i + 1 + 1));
					}

					String prevCharacter = null;

					if ((i - 1) >= 0) {
						prevCharacter = Character.toString(words.charAt(i - 1));
					}

					// WB3
					if (matches(character, PATTERN_KEYS.CR) && matches(nextCharacter, PATTERN_KEYS.LF)) {
						stringBuilder.append(character);
					}

					// WB3a
					else if (matches(character, PATTERN_KEYS.CR) || matches(character, PATTERN_KEYS.LF) ||
							matches(character, PATTERN_KEYS.NEWLINE)) {

						stringBuilder.append(character);
						wordList.add(stringBuilder.toString());
						stringBuilder.setLength(0);
					}

					// WB3b
					else if (matches(nextCharacter, PATTERN_KEYS.CR) || matches(nextCharacter, PATTERN_KEYS.LF) ||
							matches(nextCharacter, PATTERN_KEYS.NEWLINE)) {

						stringBuilder.append(character);
						wordList.add(stringBuilder.toString());
						stringBuilder.setLength(0);
					}

					// WB4
					else if (matches(character, PATTERN_KEYS.EXTEND) || matches(character, PATTERN_KEYS.FORMAT)) {
						// Ignore
					}

					// WB5
					else if (matches(character, PATTERN_KEYS.ALETTER) && matches(nextCharacter, PATTERN_KEYS.ALETTER)) {
						stringBuilder.append(character);
					}

					// WB6
					else if (matches(character, PATTERN_KEYS.ALETTER) &&
							(matches(nextCharacter, PATTERN_KEYS.MIDLETTER) ||
								matches(nextCharacter, PATTERN_KEYS.MIDNUMLET) ||
								SINGLE_QUOTE_PATTERN.matcher(nextCharacter).matches()) &&
							((nextNextCharacter != null) && matches(nextNextCharacter, PATTERN_KEYS.ALETTER))) {
						stringBuilder.append(character);
					}

					// WB7
					else if (((prevCharacter != null) && matches(prevCharacter, PATTERN_KEYS.ALETTER)) &&
							(matches(character, PATTERN_KEYS.MIDLETTER) || matches(character, PATTERN_KEYS.MIDNUMLET) ||
								SINGLE_QUOTE_PATTERN.matcher(character).matches()) &&
							matches(nextCharacter, PATTERN_KEYS.ALETTER)) {
						stringBuilder.append(character);
					}

					// WB8
					else if (matches(character, PATTERN_KEYS.NUMERIC) && matches(nextCharacter, PATTERN_KEYS.NUMERIC)) {
						stringBuilder.append(character);
					}

					// WB9
					else if (matches(character, PATTERN_KEYS.ALETTER) && matches(nextCharacter, PATTERN_KEYS.NUMERIC)) {
						stringBuilder.append(character);
					}

					// WB10
					else if (matches(character, PATTERN_KEYS.NUMERIC) && matches(nextCharacter, PATTERN_KEYS.ALETTER)) {
						stringBuilder.append(character);
					}

					// WB11
					else if (matches(character, PATTERN_KEYS.NUMERIC) &&
							(matches(nextCharacter, PATTERN_KEYS.MIDNUM) ||
								matches(nextCharacter, PATTERN_KEYS.MIDNUMLET) ||
								SINGLE_QUOTE_PATTERN.matcher(nextCharacter).matches()) &&
							((nextNextCharacter != null) && matches(nextNextCharacter, PATTERN_KEYS.NUMERIC))) {
						stringBuilder.append(character);
					}

					// WB12
					else if (((prevCharacter != null) && matches(prevCharacter, PATTERN_KEYS.NUMERIC)) &&
							(matches(character, PATTERN_KEYS.MIDNUM) || matches(character, PATTERN_KEYS.MIDNUMLET) ||
								SINGLE_QUOTE_PATTERN.matcher(character).matches()) &&
							matches(nextCharacter, PATTERN_KEYS.NUMERIC)) {
						stringBuilder.append(character);
					}

					// WB13
					else if (matches(character, PATTERN_KEYS.KATAKANA) &&
							matches(nextCharacter, PATTERN_KEYS.KATAKANA)) {
						stringBuilder.append(character);
					}

					// WB13a
					else if ((matches(character, PATTERN_KEYS.ALETTER) || matches(character, PATTERN_KEYS.NUMERIC) ||
								matches(character, PATTERN_KEYS.KATAKANA) ||
								matches(character, PATTERN_KEYS.EXTENDEDNUMLET)) &&
							matches(nextCharacter, PATTERN_KEYS.EXTENDEDNUMLET)) {
						stringBuilder.append(character);
					}

					// WB13a
					else if (matches(character, PATTERN_KEYS.EXTENDEDNUMLET) &&
							(matches(nextCharacter, PATTERN_KEYS.ALETTER) ||
								matches(nextCharacter, PATTERN_KEYS.NUMERIC) ||
								matches(nextCharacter, PATTERN_KEYS.KATAKANA) ||
								matches(nextCharacter, PATTERN_KEYS.EXTENDEDNUMLET))) {
						stringBuilder.append(character);
					}
					else {
						stringBuilder.append(character);
						wordList.add(stringBuilder.toString());
						stringBuilder.setLength(0);
					}
				}
			}
		}

		Iterator<String> iterator = wordList.iterator();

		while (iterator.hasNext()) {

			String word = (String) iterator.next();

			if (WHITESPACE.matcher(word).matches() || matches(word, PATTERN_KEYS.PUNCTUATION)) {

				// Ignore whitespace and punctuation
				iterator.remove();
			}
		}

		return wordList;
	}
}
