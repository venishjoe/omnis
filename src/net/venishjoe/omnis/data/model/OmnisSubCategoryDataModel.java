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

public class OmnisSubCategoryDataModel {
	private String omnisSubCategoryName;
	private int omnisSubCategoryId;
	private int omnisMainCategoryId;
	
	@Override
    public String toString() {
        return omnisSubCategoryName;
    }
	
	public OmnisSubCategoryDataModel(String _omnisSubCategoryName, int _omnisSubCategoryId, 
			int _omnisMainCategoryId) {
        this.omnisSubCategoryName = _omnisSubCategoryName;
        this.omnisSubCategoryId = _omnisSubCategoryId;
        this.omnisMainCategoryId = _omnisMainCategoryId;
    }
	
	public String getOmnisSubCategoryName() {
		return omnisSubCategoryName;
	}
	
	public void setOmnisSubCategoryName(String omnisSubCategoryName) {
		this.omnisSubCategoryName = omnisSubCategoryName;
	}
	
	public int getOmnisSubCategoryId() {
		return omnisSubCategoryId;
	}
	
	public void setOmnisSubCategoryId(int omnisSubCategoryId) {
		this.omnisSubCategoryId = omnisSubCategoryId;
	}
	
	public int getOmnisMainCategoryId() {
		return omnisMainCategoryId;
	}
	
	public void setOmnisMainCategoryId(int omnisMainCategoryId) {
		this.omnisMainCategoryId = omnisMainCategoryId;
	}
}
