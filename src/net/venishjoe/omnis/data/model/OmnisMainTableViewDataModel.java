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

public class OmnisMainTableViewDataModel {
	private final SimpleStringProperty omnisMainCategory;
	private final SimpleStringProperty omnisSubCategory;
	private final SimpleStringProperty omnisFileName;
	private final SimpleStringProperty omnisFileType;
	private final SimpleStringProperty omnisFileSize;
	private final SimpleStringProperty omnisCreatedDate;
	private final SimpleStringProperty omnisLastAccessedDate;
	
	public OmnisMainTableViewDataModel (String _omnisMainCategory, String _omnisSubCategory, 
			String _omnisFileName, String _omnisFileType, String _omnisFileSize, 
			String _omnisCreatedDate, String _omnisLastAccessedDate) {
		this.omnisMainCategory = new SimpleStringProperty(_omnisMainCategory);
		this.omnisSubCategory = new SimpleStringProperty(_omnisSubCategory);
		this.omnisFileName = new SimpleStringProperty(_omnisFileName);
		this.omnisFileType = new SimpleStringProperty(_omnisFileType);
		this.omnisFileSize = new SimpleStringProperty(_omnisFileSize);
		this.omnisCreatedDate = new SimpleStringProperty(_omnisCreatedDate);
		this.omnisLastAccessedDate = new SimpleStringProperty(_omnisLastAccessedDate);
	}

	public String getOmnisMainCategory() {
		return omnisMainCategory.get();
	}
	
	public void setOmnisMainCategory(String _omnisMainCategory) {
		omnisMainCategory.set(_omnisMainCategory);
	}
	
	public String getOmnisSubCategory() {
		return omnisSubCategory.get();
	}
	
	public void setOmnisSubCategory(String _omnisSubCategory) {
		omnisSubCategory.set(_omnisSubCategory);
	}
	
	public String getOmnisFileName() {
		return omnisFileName.get();
	}
	
	public void setOmnisFileName(String _omnisFileName) {
		omnisFileName.set(_omnisFileName);
	}
	
	public String getOmnisFileType() {
		return omnisFileType.get();
	}
	
	public void setOmnisFileType(String _omnisFileType) {
		omnisFileType.set(_omnisFileType);
	}
	
	public String getOmnisFileSize() {
		return omnisFileSize.get();
	}
	
	public void setOmnisFileSize(String _omnisFileSize) {
		omnisFileSize.set(_omnisFileSize);
	}
	
	public String getOmnisCreatedDate() {
		return omnisCreatedDate.get();
	}
	
	public void setOmnisCreatedDate(String _omnisCreatedDate) {
		omnisCreatedDate.set(_omnisCreatedDate);
	}
	
	public String getOmnisLastAccessedDate() {
		return omnisLastAccessedDate.get();
	}
	
	public void setOmnisLastAccessedDate(String _omnisLastAccessedDate) {
		omnisLastAccessedDate.set(_omnisLastAccessedDate);
	}
	
}
