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
package com.liferay.faces.demos.tree;

import java.io.Serializable;



import java.io.IOException;
import java.io.Serializable;

import org.apache.log4j.Logger;
import org.primefaces.model.StreamedContent;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;

/**
 *
 * @author marcel.spescha
 */

public class PrimefacesDocument implements Serializable{
    
    private long id;
	private String documentName;
    private String type;
    private String description;
    private String text = "No_text";
	private String documentIcon="tree_folder_close.gif";
    private DLFileEntry fileEntry;
    private Boolean permittedToDelete;
    private int n;
    
	// serialVersionUID
	private static final long serialVersionUID = 7459628254337818773L;
                
  
    public PrimefacesDocument(long id, String documentName, String type, String description, DLFileEntry fileEntry, Boolean permittedToDelete ) {
        this.id = id;
        this.documentName = documentName;
        this.type = type;
        this.description = description;
        this.text = text;
        this.documentIcon = documentIcon;
        this.fileEntry = fileEntry;
        this.permittedToDelete = permittedToDelete;
    }

    
    public DLFileEntry getFileEntry() {
    	
        return fileEntry;
    }

    public void setFileEntry(DLFileEntry fileEntry) {
        this.fileEntry = fileEntry;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDocumentIcon() {
        return documentIcon;
    }

    public void setDocumentIcon(String documentName) {
        this.documentIcon = documentIcon;
    }

	public Boolean getPermittedToDelete() {
        return permittedToDelete;
    }

    public void setPermittedToDelete(Boolean permittedToDelete) {
        this.permittedToDelete = permittedToDelete;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PrimefacesDocument other = (PrimefacesDocument) obj;
        return true;
    }


    @Override
    public String toString() {
        return super.toString();
    }
    
        
}
