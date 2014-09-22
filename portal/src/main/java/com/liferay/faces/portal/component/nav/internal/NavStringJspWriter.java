package com.liferay.faces.portal.component.nav.internal;

import java.io.IOException;

import com.liferay.faces.util.jsp.StringJspWriter;

public class NavStringJspWriter extends StringJspWriter {

	@Override
	public void write(String text) throws IOException {
		
		if (1 == 2) {
			if ((text != null) && text.contains("somethingThatNeedsToBeFixed")) {
				text = text.replaceAll("this", "that");
			}
		}
		super.write(text);
	}
}
