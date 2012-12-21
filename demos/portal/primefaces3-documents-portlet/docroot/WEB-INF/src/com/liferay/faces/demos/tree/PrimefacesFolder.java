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

/**
 *
 * @author marcel.spescha
 */

public class PrimefacesFolder implements Serializable{
    
    private long id;
    private String type;
    private String description;
	private DLFolder dlFolder;
	private String folderName;
	public static final String DEFAULT_DESCRIPTION="default_description";
	// serialVersionUID
	private static final long serialVersionUID = 7459628254337818773L;
                
  
    public PrimefacesFolder(long id, String type, DLFolder dlFolder) throws IOException {
        this.id = id;
        this.type = type;
        this.description = DEFAULT_DESCRIPTION;
        this.dlFolder=dlFolder;
        folderName=(dlFolder == null ) ? "NoName" : dlFolder.getName();
    }

    public long getId() {
		return id;
	}

    public DLFolder getDlFolder() {
		return dlFolder;
	}

    public String getFolderName() {
		return folderName;
	}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type=type;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PrimefacesFolder other = (PrimefacesFolder) obj;
        return true;
    }


    @Override
    public String toString() {
        return super.toString();
    }
    
        
}
