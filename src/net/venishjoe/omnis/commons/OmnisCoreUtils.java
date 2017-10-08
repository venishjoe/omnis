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

package net.venishjoe.omnis.commons;

import java.util.StringTokenizer;

import net.venishjoe.omnis.staticdata.OmnisGlobalConstants;

import org.apache.log4j.Logger;

public class OmnisCoreUtils {
	private static Logger logger = Logger.getLogger(OmnisCoreUtils.class);
	
	public static String padLeft (String _inputString, int _numberOfChars) {
		try{
			return String.format("%1$" + _numberOfChars + "s", _inputString);
		} catch (Exception genericException) {
			logger.error(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + genericException.getStackTrace()[0].getMethodName(), genericException);
			return null;
		}	    		  
	}
	
	public static String tokenizeStringLastValue(String _inputString, String _token) {
		String outputString = "";
		try{
			StringTokenizer omnisStringTokenizer = new StringTokenizer(_inputString, _token);
			while (omnisStringTokenizer.hasMoreElements()) {
				outputString = omnisStringTokenizer.nextElement().toString();
			}
		} catch (Exception genericException) {
			logger.error(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + genericException.getStackTrace()[0].getMethodName(), genericException);			
		}
		
		return outputString;
	}
	
	public static String getScanTypeNameFromId (int _omnisEditorScanTypeId) {
		String outputString = "";
		try{
			if (_omnisEditorScanTypeId == OmnisGlobalConstants.OMNIS_EDITOR_NEW_DIR_SCAN_TYPE_FILE_ONLY_ID)
				outputString = OmnisGlobalConstants.OMNIS_EDITOR_NEW_DIR_SCAN_TYPE_FILE_ONLY_NAME;
			else if (_omnisEditorScanTypeId == OmnisGlobalConstants.OMNIS_EDITOR_NEW_DIR_SCAN_TYPE_DIR_ONLY_ID)
				outputString = OmnisGlobalConstants.OMNIS_EDITOR_NEW_DIR_SCAN_TYPE_DIR_ONLY_NAME;
			else if (_omnisEditorScanTypeId == OmnisGlobalConstants.OMNIS_EDITOR_NEW_DIR_SCAN_TYPE_BOTH_ID)
				outputString = OmnisGlobalConstants.OMNIS_EDITOR_NEW_DIR_SCAN_TYPE_BOTH_NAME;
		} catch (Exception genericException) {
			logger.error(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + genericException.getStackTrace()[0].getMethodName(), genericException);			
		}
		
		return outputString;
	}
}
