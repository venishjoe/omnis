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

public class OmnisMainCategoryDataModel {
	
	private String omnisMainCategoryName;
	private int omnisMainCategoryId;
	private String omnisMainCategoryImagePath;
	
	@Override
    public String toString() {
        return omnisMainCategoryName;
    }
	
	public OmnisMainCategoryDataModel(String _omnisMainCategoryName, int _omnisMainCategoryId,
			String _omnisMainCategoryImagePath) {
        this.omnisMainCategoryName = _omnisMainCategoryName;
        this.omnisMainCategoryId = _omnisMainCategoryId;
        this.omnisMainCategoryImagePath = _omnisMainCategoryImagePath;
    }
	
	public String getOmnisMainCategoryName() {
		return omnisMainCategoryName;
	}
	
	public void setOmnisMainCategoryName(String omnisMainCategoryName) {
		this.omnisMainCategoryName = omnisMainCategoryName;
	}
	
	public int getOmnisMainCategoryId() {
		return omnisMainCategoryId;
	}
	
	public void setOmnisMainCategoryId(int omnisMainCategoryId) {
		this.omnisMainCategoryId = omnisMainCategoryId;
	}
	
	public String getOmnisMainCategoryImagePath() {
		return omnisMainCategoryImagePath;
	}
	
	public void setOmnisMainCategoryImagePath(String omnisMainCategoryImagePath) {
		this.omnisMainCategoryImagePath = omnisMainCategoryImagePath;
	}
}
