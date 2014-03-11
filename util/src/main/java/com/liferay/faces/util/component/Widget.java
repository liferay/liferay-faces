package com.liferay.faces.util.component;

public interface Widget {

	public static final String WIDGET_VAR = "widgetVar";

	public String getWidgetVar();

	public void setWidgetVar(String widgetVar);

	public String getClientId();
}
