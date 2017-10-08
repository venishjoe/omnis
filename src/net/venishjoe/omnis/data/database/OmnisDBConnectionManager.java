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

import java.sql.Connection;
import java.sql.DriverManager;
import org.h2.jdbcx.JdbcConnectionPool;
import net.venishjoe.omnis.staticdata.OmnisGlobalConstants;
import net.venishjoe.omnis.staticdata.OmnisGlobalMessages;
import net.venishjoe.omnis.staticdata.OmnisGlobalVariables;
import net.venishjoe.omnis.thirdparty.dialog.Dialog;

import org.apache.log4j.Logger;


public class OmnisDBConnectionManager {
	private static Logger logger = Logger.getLogger(OmnisDBConnectionManager.class);
	
	@SuppressWarnings("unused")
	private static Connection getOmnisDBConnection() {
		logger.debug("Entering OmnisDBConnectionManager.getOmnisDBConnection()");
		Connection omnisDBConnection = null;
		try {
			Class.forName(OmnisGlobalConstants.OMNIS_DB_DRIVER);
			omnisDBConnection = DriverManager.getConnection(
					OmnisGlobalConstants.OMNIS_DB_CONNECTION_STRING, 
					OmnisGlobalConstants.OMNIS_DATABASE_USER_ID, 
					OmnisGlobalConstants.OMNIS_DATABASE_PASSWORD);
		} catch (Exception genericException) {
			logger.error(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + genericException.getStackTrace()[0].getMethodName(), genericException);
			Dialog.showThrowable(OmnisGlobalConstants.OMNIS_DIALOG_TITLE, OmnisGlobalMessages.OMNISDBCONNECTIONMANAGER_GETCONN_ERROR, genericException);
		}
		return omnisDBConnection;
	}
	
	public static void getOmnisDBConnectionPool() {
		logger.debug("Entering OmnisDBConnectionManager.getOmnisDBConnection()");
		JdbcConnectionPool omnisDBConnectionPool = null;
		try {			
			omnisDBConnectionPool = JdbcConnectionPool.create(OmnisGlobalConstants.OMNIS_DB_CONNECTION_STRING, 
					OmnisGlobalConstants.OMNIS_DATABASE_USER_ID, 
					OmnisGlobalConstants.OMNIS_DATABASE_PASSWORD);
		} catch (Exception genericException) {
			logger.error(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + genericException.getStackTrace()[0].getMethodName(), genericException);
			Dialog.showThrowable(OmnisGlobalConstants.OMNIS_DIALOG_TITLE, OmnisGlobalMessages.OMNISDBCONNECTIONMANAGER_GETCONN_ERROR, genericException);
		}
		
		OmnisGlobalVariables.OMNIS_DB_CONNECTION_POOL = omnisDBConnectionPool;
	}
	
	public static int closeDBConnection (Connection omnisDBConnection) {
		logger.debug("Entering OmnisDBConnectionManager.closeDBConnection()");
		try {
			omnisDBConnection.close();
		} catch (Exception genericException) {
			return OmnisGlobalConstants.FAIL;
		} finally {
			try {
				if (omnisDBConnection != null)
					omnisDBConnection.close();
			} catch (Exception genericException) {
				logger.error(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + genericException.getStackTrace()[0].getMethodName(), genericException);
				Dialog.showThrowable(OmnisGlobalConstants.OMNIS_DIALOG_TITLE, OmnisGlobalMessages.OMNISDBCONNECTIONMANAGER_CLOSECONN_ERROR, genericException);
				return OmnisGlobalConstants.FAIL;
			}
		}
		return OmnisGlobalConstants.SUCCESS;
	}
}
