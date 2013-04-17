package com.liferay.faces.test;

import org.jboss.arquillian.graphene.javascript.Dependency;
import org.jboss.arquillian.graphene.javascript.JavaScript;

@JavaScript("selectText")
@Dependency(sources = "selectText.js")
public interface SelectText {
	void setSelection(String id, int start, int end);
	String getSelection(String id);
}
