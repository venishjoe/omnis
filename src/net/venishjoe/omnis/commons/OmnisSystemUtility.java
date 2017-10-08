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

import java.sql.Connection;
import java.sql.DatabaseMetaData;

import net.venishjoe.omnis.staticdata.OmnisGlobalConstants;
import net.venishjoe.omnis.staticdata.OmnisGlobalVariables;

import org.apache.log4j.Logger;

public class OmnisSystemUtility {
	private static Logger logger = Logger.getLogger(OmnisSystemUtility.class);
	private static double BYTES_PER_MB = 1024.0 * 1024.0;
	
	public String printSystemData () {
		String _SYSTEMINFORMATION = "";
		try {
			int _sysProcessorCount = Runtime.getRuntime().availableProcessors();
			Runtime _sysRuntime = Runtime.getRuntime();
			long _freeMemory = _sysRuntime.freeMemory();
			long _totalMemory = _sysRuntime.totalMemory();		
			
			/* Returns the below.
			 * 1. JVM Description
			 * 2. Java Platform Details
			 * 3. OS & Processor Configuration
			 * 4. Used & Available Memory 
			 */
			
			_SYSTEMINFORMATION = "\n\nVirtual Machine Information (JVM) \n" + 
			        "JVM Name: " + System.getProperty("java.vm.name") + "\n" + 
			        "JVM installation directory: " + System.getProperty("java.home") + "\n" +
			        "JVM version: " + System.getProperty("java.vm.version") + "\n" +
			        "JVM Vendor: " + System.getProperty("java.vm.vendor") + "\n" +
			        "JVM Info: " + System.getProperty("java.vm.info") + "\n" +
					"Java Runtime  Name: " + System.getProperty("java.runtime.name") + "\n" +
			        "Java Version: " + System.getProperty("java.version") + "\n" +
			        "Java Class Version: " + System.getProperty("java.class.version") + "\n" +
			        String.format("Running on %s version %s (%s) with %d processor%s available.", System
			                .getProperty("os.name"), System.getProperty("os.version"), System.getProperty("os.arch"),
			                _sysProcessorCount, _sysProcessorCount == 1 ? "" : "s") + "\n" +
	                String.format("%.1f MB of memory free out of %.1f MB total in JVM (%.1f MB used).  Configured maximum: %.1f MB.",
	    	                _freeMemory / BYTES_PER_MB, _totalMemory / BYTES_PER_MB, (_totalMemory - _freeMemory) / BYTES_PER_MB, _sysRuntime
	    	                .maxMemory() / BYTES_PER_MB) + "\n\n";
			
		} catch (Exception exception) {
			logger.error(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + exception.getStackTrace()[0].getMethodName(), exception);
		}
		return _SYSTEMINFORMATION;
	}
	
	
	public String printDatabaseDetails () {
		String _DATABASEINFORMATION = "";
		try {
			Connection omnisDBConnection = (Connection) OmnisGlobalVariables.OMNIS_DB_CONNECTION_POOL.getConnection();
			DatabaseMetaData omnisDBMetaData = omnisDBConnection.getMetaData(); 
			
			_DATABASEINFORMATION = "\n\nDatabase Information \n" +
					"Database URL: " + omnisDBMetaData.getURL() + "\n" +
					"Database User Name: " + omnisDBMetaData.getUserName() + "\n" +
					"Database Product Name: " + omnisDBMetaData.getDatabaseProductName() + "\n" +
					"Database Product Version: " + omnisDBMetaData.getDatabaseProductVersion() + "\n" +
					"Database Major Version: " + omnisDBMetaData.getDatabaseMajorVersion() + "\n" +
					"Database Minor Version: " + omnisDBMetaData.getDatabaseMinorVersion() + "\n" +
					"Driver Name: " + omnisDBMetaData.getDriverName() + "\n" +
					"Driver Version: " + omnisDBMetaData.getDriverVersion() + "\n" +
					"Driver Major Version: " + omnisDBMetaData.getDriverMajorVersion() + "\n" +
					"Driver Minor Version: " + omnisDBMetaData.getDriverMinorVersion() + "\n" +
					"Supports getGeneratedKeys(): " + omnisDBMetaData.supportsGetGeneratedKeys() + "\n\n";
			
			omnisDBConnection.close();
		} catch (Exception exception) {
			logger.error(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + exception.getStackTrace()[0].getMethodName(), exception);
		}
		return _DATABASEINFORMATION;
	}
}
