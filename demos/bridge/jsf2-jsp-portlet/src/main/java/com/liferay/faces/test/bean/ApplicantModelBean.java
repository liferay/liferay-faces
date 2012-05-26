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
package com.liferay.faces.test.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.liferay.faces.bridge.model.UploadedFile;


/**
 * This is a model managed bean that represents an applicant that is applying for a job.
 *
 * @author  "Neil Griffin"
 */
@ManagedBean(name = "applicantModelBean")
@ViewScoped
public class ApplicantModelBean implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 7459628254337818761L;

	// Private Data Members
	private List<UploadedFile> uploadedFiles;
	private String city;
	private String comments;
	private Date dateOfBirth;
	private String emailAddress;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String postalCode;
	private Long provinceId;

	// Private Data Members (auto-fill)
	private String autoFillCity;
	private Long autoFillProvinceId;

	public ApplicantModelBean() {
		clearProperties();
	}

	public void clearProperties() {
		uploadedFiles = new ArrayList<UploadedFile>();
		city = null;
		comments = null;
		dateOfBirth = null;
		emailAddress = null;
		firstName = null;
		lastName = null;
		phoneNumber = null;
		postalCode = null;
		provinceId = null;
	}

	public void setAutoFillCity(String autoFillCity) {
		this.autoFillCity = autoFillCity;
	}

	public void setAutoFillProvinceId(Long autoFillProvinceId) {
		this.autoFillProvinceId = autoFillProvinceId;
	}

	public String getCity() {

		if (autoFillCity == null) {
			return city;
		}
		else {
			return autoFillCity;
		}
	}

	public void setCity(String city) {

		if (autoFillCity == null) {
			this.city = city;
		}
		else {
			this.city = autoFillCity;
			autoFillCity = null;
		}
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public Long getProvinceId() {

		if (autoFillProvinceId == null) {
			return provinceId;
		}
		else {
			return autoFillProvinceId;
		}
	}

	public void setProvinceId(Long provinceId) {

		if (autoFillProvinceId == null) {
			this.provinceId = provinceId;
		}
		else {
			this.provinceId = autoFillProvinceId;
			autoFillProvinceId = null;
		}
	}

	public List<UploadedFile> getUploadedFiles() {
		return uploadedFiles;
	}

}
