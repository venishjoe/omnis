/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2013 Venish Joe, http://venishjoe.net, venish@venishjoe.net
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package net.venishjoe.omnis.data.model;

import javafx.beans.property.SimpleStringProperty;

public class OmnisDirScannerPathsDataModel {
	private final SimpleStringProperty omnisDirectoryScanTypeName;
	private final SimpleStringProperty omnisDirectoryScanTypeId;
	private final SimpleStringProperty omnisDirectoryScanPath;
	private final SimpleStringProperty omnisDirectoryScanId;
	private final SimpleStringProperty omnisDirectorySubCategoryId;
	
	public OmnisDirScannerPathsDataModel(String _omnisDirectoryScanTypeName, String _omnisDirectoryScanTypeId, 
			String _omnisDirectoryScanPath, String _omnisDirectoryScanId, String _omnisDirectorySubCategoryId) {
		this.omnisDirectoryScanTypeName = new SimpleStringProperty(_omnisDirectoryScanTypeName);
        this.omnisDirectoryScanTypeId =  new SimpleStringProperty(_omnisDirectoryScanTypeId);
        this.omnisDirectoryScanPath =  new SimpleStringProperty(_omnisDirectoryScanPath);
        this.omnisDirectoryScanId =  new SimpleStringProperty(_omnisDirectoryScanId);
        this.omnisDirectorySubCategoryId =  new SimpleStringProperty(_omnisDirectorySubCategoryId);
    }
	
	public String getOmnisDirectoryScanTypeName() {
		return omnisDirectoryScanTypeName.get();
	}

	public void setOmnisDirectoryScanTypeName(String _omnisDirectoryScanTypeName) {
		omnisDirectoryScanTypeName.set(_omnisDirectoryScanTypeName);
	}

	public String getOmnisDirectoryScanTypeId() {
		return omnisDirectoryScanTypeId.get();
	}

	public void setOmnisDirectoryScanTypeId(String _omnisDirectoryScanTypeId) {
		omnisDirectoryScanTypeId.set(_omnisDirectoryScanTypeId);
	}

	public String getOmnisDirectoryScanPath() {
		return omnisDirectoryScanPath.get();
	}

	public void setOmnisDirectoryScanPath(String _omnisDirectoryScanPath) {
		omnisDirectoryScanPath.set(_omnisDirectoryScanPath);
	}
	
	public String getOmnisDirectoryScanId() {
		return omnisDirectoryScanId.get();
	}

	public void setOmnisDirectoryScanId(String _omnisDirectoryScanId) {
		omnisDirectoryScanId.set(_omnisDirectoryScanId);
	}

	public String getOmnisDirectorySubCategoryId() {
		return omnisDirectorySubCategoryId.get();
	}

	public void setOmnisDirectorySubCategoryId(String _omnisDirectorySubCategoryId) {
		omnisDirectorySubCategoryId.set(_omnisDirectorySubCategoryId);
	}
		
}
