package com.liferay.faces.demos.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 * @author  Vernon Singleton
 */
@ManagedBean
@RequestScoped
public class PopoverModelBean {

	private String header = "Header content";

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}
	
	private String body = "Body content";

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

}
