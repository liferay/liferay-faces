package com.liferay.faces.util.component;

/**
 * @author  Neil Griffin
 */
public interface Styleable {

	public static final String CSS_CLASS = "cssClass";
	public static final String STYLE_CLASS = "styleClass";

	public String getCssClass();

	public void setCssClass(String cssClass);

	public String getStyleClass();

	public void setStyleClass(String styleClass);
}