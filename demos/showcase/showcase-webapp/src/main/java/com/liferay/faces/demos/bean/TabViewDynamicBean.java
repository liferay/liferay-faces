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
package com.liferay.faces.demos.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


/**
 * @author  Neil Griffin
 */
@ManagedBean
@ViewScoped
public class TabViewDynamicBean implements Serializable {

	private static final long serialVersionUID = 9059601641277438559L;

	private boolean compatible = true;
	private boolean enterpriseReady = true;
	private boolean lightweight = true;
	private boolean openSource = true;
	private boolean powerfulIntegration = true;

	public void setCompatible(boolean compatible) {
		this.compatible = compatible;
	}

	public boolean isCompatible() {
		return compatible;
	}

	public boolean isOpenSource() {
		return openSource;
	}

	public void setEnterpriseReady(boolean enterpriseReady) {
		this.enterpriseReady = enterpriseReady;
	}

	public void setLightweight(boolean lightweight) {
		this.lightweight = lightweight;
	}

	public boolean isPowerfulIntegration() {
		return powerfulIntegration;
	}

	public void setOpenSource(boolean openSource) {
		this.openSource = openSource;
	}

	public void setPowerfulIntegration(boolean powerfulIntegration) {
		this.powerfulIntegration = powerfulIntegration;
	}

	public boolean isLightweight() {
		return lightweight;
	}

	public boolean isEnterpriseReady() {
		return enterpriseReady;
	}
}
