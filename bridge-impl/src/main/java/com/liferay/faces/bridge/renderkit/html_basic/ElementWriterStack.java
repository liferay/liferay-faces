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
package com.liferay.faces.bridge.renderkit.html_basic;

import java.util.Stack;

import org.w3c.dom.Element;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class ElementWriterStack extends Stack<ElementWriter> {

	// serialVersionUID
	private static final long serialVersionUID = 3761771658484098988L;

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ElementWriterStack.class);

	/**
	 * The safePeek() method may have pushed some blank elements, so this method takes that into account.
	 */
	@Override
	public synchronized ElementWriter pop() {

		// Pop the top element off the stack.
		ElementWriter topElementWriter = super.pop();
		Element topElement = topElementWriter.getElement();

		// For each element that remains on the top of the stack:
		boolean done = isEmpty();

		while (!done) {

			// If the top stack element is a blank element, then PREPEND its textContent to that of the topElement.
			Element peekedElement = peek().getElement();

			if (peekedElement instanceof ElementBlankImpl) {
				StringBuilder prependedTextContent = new StringBuilder();
				prependedTextContent.append(peekedElement.getTextContent());
				prependedTextContent.append(topElement.getTextContent());
				topElement.setTextContent(prependedTextContent.toString());
				super.pop();
				done = isEmpty();
			}
			else {
				done = true;
			}
		}

		return topElementWriter;
	}

	/**
	 * The purpose of safePeek() is to prevent an EmptyStackException from being thrown due to a JSF component renderer
	 * not playing by the rules, whereby startElement() should be called first before write().
	 */
	public synchronized ElementWriter safePeek() {

		if (isEmpty()) {
			logger.debug("Stack was empty so created blank element", (Object[]) null);
			push(new ElementWriter(new ElementBlankImpl()));
		}

		return peek();
	}

}
