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

import net.venishjoe.omnis.data.database.OmnisDBConnectionManager;
import net.venishjoe.omnis.staticdata.OmnisGlobalConstants;
import net.venishjoe.omnis.staticdata.OmnisGlobalVariables;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.h2.jdbcx.JdbcConnectionPool;

public class OmnisGlobalInit {
	private static Logger logger = Logger.getLogger(OmnisGlobalInit.class);		
	
	//Called before invoking the GUI
	public static void omnisGlobalInitHook() {
		logger.debug("Entering OmnisGlobalInit.omnisGlobalInitHook()");
		try {
			//Initialize Log4J
			DOMConfigurator.configure(OmnisGlobalInit.class.getResource(
					OmnisGlobalConstants.OMNIS_LOG4J_PROPERTIES_RELATIVE_PATH 
					+ OmnisGlobalConstants.OMNIS_LOG4J_PROPERTIES));

			//Set Default Logger Level
			new OmnisLogger().setLoggerToDebug();
			logger.info("---* Entering MAIN Method for the application *---");
			
			//Creating database connection pool
			OmnisDBConnectionManager.getOmnisDBConnectionPool();
			
			//Print System & Database Details			
			logger.info(new OmnisSystemUtility().printSystemData());
			logger.info(new OmnisSystemUtility().printDatabaseDetails());		
			
		} catch (Exception genericException) {
			logger.fatal(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + genericException.getStackTrace()[0].getMethodName(), genericException);			
		}
	}
	
	//Called before shutting down the app
	public static void omnisGlobalShutdownHook() {
		logger.debug("Entering OmnisGlobalInit.omnisGlobalShutdownHook()");
		try {
			//Shutting down connection pool
			JdbcConnectionPool omnisDBConnectionPool = OmnisGlobalVariables.OMNIS_DB_CONNECTION_POOL;
			omnisDBConnectionPool.dispose();
		} catch (Exception genericException) {
			logger.fatal(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + genericException.getStackTrace()[0].getMethodName(), genericException);			
		}
	}
}
