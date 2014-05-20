package com.liferay.faces.demos.bean;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 * @author  Vernon Singleton
 */
@ManagedBean
@RequestScoped
public class OutputTextModelBean {

	private String property = "Hello model bean property.";
	
	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	private Date today = GregorianCalendar.getInstance().getTime();

	public Date getToday() {
		return today;
	}

	public void setToday(Date today) {
		this.today = today;
	}

}
