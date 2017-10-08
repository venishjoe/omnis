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

package net.venishjoe.omnis.gui;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import net.venishjoe.omnis.data.model.OmnisDirScannerPathsDataModel;
import net.venishjoe.omnis.data.model.OmnisMainTableViewDataModel;
import net.venishjoe.omnis.staticdata.OmnisGlobalConstants;

import org.apache.log4j.Logger;

public class OmnisGUIHelper {
	private static Logger logger = Logger.getLogger(OmnisGUIHelper.class);
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public TableColumn createOmnisMainTableColumn (String _columnName, String _columnProperty, int _minWidth) {
		logger.debug("Entering OmnisGUIHelper.createOmnisMainTableColumn()");
		
		TableColumn omnisTableColunm = null;
		try {
			omnisTableColunm = new TableColumn(_columnName);
			omnisTableColunm.setMinWidth(_minWidth);
			omnisTableColunm.setCellValueFactory(new PropertyValueFactory<OmnisMainTableViewDataModel, String>(_columnProperty));
		} catch (Exception genericException) {
			logger.error(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + genericException.getStackTrace()[0].getMethodName(), genericException);
		} 
	
		return omnisTableColunm;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public TableColumn createOmnisEditorTableColumn (String _columnName, String _columnProperty, int _minWidth) {
		logger.debug("Entering OmnisGUIHelper.createOmnisEditorTableColumn()");
		
		TableColumn omnisTableColunm = null;
		try {
			omnisTableColunm = new TableColumn(_columnName);
			omnisTableColunm.setMinWidth(_minWidth);
			omnisTableColunm.setCellValueFactory(new PropertyValueFactory<OmnisDirScannerPathsDataModel, String>(_columnProperty));
		} catch (Exception genericException) {
			logger.error(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + genericException.getStackTrace()[0].getMethodName(), genericException);
		} 
	
		return omnisTableColunm;
	}
}
