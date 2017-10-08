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

package net.venishjoe.omnis.data.database;

import net.venishjoe.omnis.staticdata.OmnisGlobalConstants;

import org.apache.log4j.Logger;

public class OmnisQueryBuilder {
	private static Logger logger = Logger.getLogger(OmnisQueryBuilder.class);
	private String omnisSQLQuery = null;
	
	public String getOmnisMainCategoryDetailsSQL() {
		try {
			omnisSQLQuery = "SELECT OMNIS_MAIN_CAT_ID, OMNIS_MAIN_CAT_NAME, OMNIS_MAIN_ICON_PATH " +
							"FROM OMNIS.OMNIS_MAIN_CATEGORY " +
							"ORDER BY OMNIS_MAIN_CAT_ID ASC";
		}  catch (Exception genericException) {
			logger.error(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + genericException.getStackTrace()[0].getMethodName(), genericException);
		}
		logger.info("SQL Query from OmnisQueryBuilder.getOmnisMainCategoryDetailsSQL()");
		logger.info(omnisSQLQuery);
		
		return omnisSQLQuery;
	}
	
	public String getOmnisMainCategoryDetailsByIdSQL() {
		try {
			omnisSQLQuery = "SELECT OMNIS_MAIN_CAT_ID, OMNIS_MAIN_CAT_NAME, OMNIS_MAIN_ICON_PATH " +
							"FROM OMNIS.OMNIS_MAIN_CATEGORY " +
							"WHERE OMNIS_MAIN_CAT_ID=? " +
							"ORDER BY OMNIS_MAIN_CAT_ID ASC";
		}  catch (Exception genericException) {
			logger.error(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + genericException.getStackTrace()[0].getMethodName(), genericException);
		}
		logger.info("SQL Query from OmnisQueryBuilder.getOmnisMainCategoryDetailsByIdSQL()");
		logger.info(omnisSQLQuery);
		
		return omnisSQLQuery;
	}
	
	public String getOmnisSubCategoryDetailsSQL() {
		try {
			omnisSQLQuery = "SELECT OMNIS_SUB_CAT_NAME, OMNIS_SUB_CAT_ID, OMNIS_MAIN_CAT_ID " +
							"FROM OMNIS.OMNIS_SUB_CATEGORY " +
							"WHERE OMNIS_MAIN_CAT_ID=? " +
							"ORDER BY OMNIS_SUB_CAT_ID ASC";
		}  catch (Exception genericException) {
			logger.error(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + genericException.getStackTrace()[0].getMethodName(), genericException);
		}
		logger.info("SQL Query from OmnisQueryBuilder.getOmnisSubCategoryDetailsSQL()");
		logger.info(omnisSQLQuery);
		
		return omnisSQLQuery;
	}
	
	public String getOmnisAllCategoryDetailsByIdSQL() {
		try {
			omnisSQLQuery = "SELECT OMC.OMNIS_MAIN_CAT_ID, OMC.OMNIS_MAIN_CAT_NAME,OMC.OMNIS_MAIN_ICON_PATH, OSC.OMNIS_SUB_CAT_NAME,OSC.OMNIS_SUB_CAT_ID " +
							"FROM OMNIS.OMNIS_MAIN_CATEGORY OMC, OMNIS.OMNIS_SUB_CATEGORY OSC " +
							"WHERE OMC.OMNIS_MAIN_CAT_ID=OSC.OMNIS_MAIN_CAT_ID " +
							"AND OSC.OMNIS_SUB_CAT_ID=? " +
							"ORDER BY OSC.OMNIS_SUB_CAT_ID ASC";
		}  catch (Exception genericException) {
			logger.error(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + genericException.getStackTrace()[0].getMethodName(), genericException);
		}
		logger.info("SQL Query from OmnisQueryBuilder.getOmnisAllCategoryDetailsByIdSQL()");
		logger.info(omnisSQLQuery);
		
		return omnisSQLQuery;
	}	
	
	public String getOmnisDirectoryScannerDetailsSQL(boolean isSubCategory) {
		try {
			omnisSQLQuery = "SELECT OMNIS_DIR_SCAN_ID, OMNIS_SUB_CAT_ID, OMNIS_SCAN_TYPE_ID, OMNIS_SCAN_PATH " +
							"FROM OMNIS.OMNIS_DIR_SCANNER ";
			
			if (isSubCategory)
				omnisSQLQuery += "WHERE OMNIS_SUB_CAT_ID=? ";
			else
				omnisSQLQuery += "WHERE OMNIS_SUB_CAT_ID IN (SELECT OMNIS_SUB_CAT_ID FROM OMNIS.OMNIS_SUB_CATEGORY WHERE OMNIS_MAIN_CAT_ID=?) ";
			
			omnisSQLQuery += "ORDER BY OMNIS_DIR_SCAN_ID ASC";
		}  catch (Exception genericException) {
			logger.error(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + genericException.getStackTrace()[0].getMethodName(), genericException);
		}
		logger.info("SQL Query from OmnisQueryBuilder.getOmnisDirectoryScannerDetailsSQL()");
		logger.info(omnisSQLQuery);
		
		return omnisSQLQuery;
	}
	
	public String deleteOmnisDirectoryScannerPath () {
		try {
			omnisSQLQuery = "DELETE FROM OMNIS.OMNIS_DIR_SCANNER " +
							"WHERE OMNIS_DIR_SCAN_ID=?";
		}  catch (Exception genericException) {
			logger.error(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + genericException.getStackTrace()[0].getMethodName(), genericException);
		}
		logger.info("SQL Query from OmnisQueryBuilder.deleteOmnisDirectoryScannerPath()");
		logger.info(omnisSQLQuery);
		
		return omnisSQLQuery;
	}
	
	public String insertOmnisDirectoryScannerPath () {
		try {
			omnisSQLQuery = "INSERT INTO OMNIS.OMNIS_DIR_SCANNER VALUES ( " +
							"(SELECT (MAX(OMNIS_DIR_SCAN_ID)+1) FROM OMNIS.OMNIS_DIR_SCANNER), " +
							"?, ?, ?)";
		}  catch (Exception genericException) {
			logger.error(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + genericException.getStackTrace()[0].getMethodName(), genericException);
		}
		logger.info("SQL Query from OmnisQueryBuilder.insertOmnisDirectoryScannerPath()");
		logger.info(omnisSQLQuery);
		
		return omnisSQLQuery;
	}
	
}
