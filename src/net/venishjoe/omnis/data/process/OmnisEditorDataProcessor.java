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

package net.venishjoe.omnis.data.process;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import net.venishjoe.omnis.commons.OmnisCoreUtils;
import net.venishjoe.omnis.data.database.OmnisQueryBuilder;
import net.venishjoe.omnis.data.model.OmnisCategoryTreeDataModel;
import net.venishjoe.omnis.data.model.OmnisDirScannerPathsDataModel;
import net.venishjoe.omnis.data.model.OmnisEditorScanTypeDataModel;
import net.venishjoe.omnis.data.model.OmnisEditorTableColumnsDataModel;
import net.venishjoe.omnis.data.model.OmnisMainCategoryDataModel;
import net.venishjoe.omnis.data.model.OmnisSubCategoryDataModel;
import net.venishjoe.omnis.staticdata.OmnisGlobalConstants;
import net.venishjoe.omnis.staticdata.OmnisGlobalMessages;
import net.venishjoe.omnis.staticdata.OmnisGlobalVariables;
import net.venishjoe.omnis.thirdparty.dialog.Dialog;

import org.apache.log4j.Logger;

public class OmnisEditorDataProcessor {
	private static Logger logger = Logger.getLogger(OmnisEditorDataProcessor.class);
	private OmnisQueryBuilder omnisQueryBuilder;
	
	@SuppressWarnings("unchecked")
	public TreeItem<OmnisCategoryTreeDataModel> createTreeView()  {
		logger.debug("Entering OmnisEditorDataProcessor.createTreeView()");
        
		TreeItem<OmnisCategoryTreeDataModel> omnisEditorRootNode = null;
		TreeItem<OmnisCategoryTreeDataModel> [] omnisEditorTreeItems = null;		
		TreeItem<OmnisCategoryTreeDataModel>[] omnisEditorChildTreeItems = null;
		ImageView[] omnisEditorTreeImageView = null;			
				
		try {
			//Initialize Classes
			omnisQueryBuilder = new OmnisQueryBuilder();
			OmnisMainCategoryDataModel omnisMainCategoryDataModel;
			
			/*
			 * No longer using root node in GUI. Commented.
			 
			//Populate the root Node
			ImageView omnisRootNodeIV = new ImageView();
			omnisRootNodeIV.setImage(new Image(getClass().getResource(OmnisGlobalConstants.OMNIS_TITLE_BAR_IMAGE).toString()));
			omnisRootNodeIV.setFitWidth(OmnisGlobalConstants.OMNIS_EDITOR_TREE_IMAGE_SIZE);
			omnisRootNodeIV.setPreserveRatio(true);
			omnisRootNodeIV.setSmooth(true);
			omnisRootNodeIV.setCache(true);
			
			omnisEditorRootNode = new TreeItem<String>(OmnisGlobalConstants.OMINIS_MAIN_GUI_TITLEBAR_TEXT, omnisRootNodeIV);
			omnisEditorRootNode.setExpanded(true);
			
			*/
			
			//Creating an empty root node (due to above comment)
			omnisEditorRootNode = new TreeItem<OmnisCategoryTreeDataModel>();
			
			//Get Data for main category nodes
			ArrayList<OmnisMainCategoryDataModel> omnisMainCategoryDataAL = new ArrayList<OmnisMainCategoryDataModel>();
			ArrayList<OmnisSubCategoryDataModel> omnisSubCategoryDataAL = new ArrayList<OmnisSubCategoryDataModel>();
			
			Connection omnisDBConnection = (Connection) OmnisGlobalVariables.OMNIS_DB_CONNECTION_POOL.getConnection();
			
			Statement getOmnisMainCategoryDataStatement = omnisDBConnection.createStatement();
			getOmnisMainCategoryDataStatement.setQueryTimeout(OmnisGlobalConstants.OMNIS_DATABASE_SQL_TIMEOUT);
			ResultSet getOmnisMainCategoryDataResultSet = 
					getOmnisMainCategoryDataStatement.executeQuery(omnisQueryBuilder.getOmnisMainCategoryDetailsSQL());
			
			while (getOmnisMainCategoryDataResultSet.next()) {
				omnisMainCategoryDataAL.add(new OmnisMainCategoryDataModel(
						getOmnisMainCategoryDataResultSet.getString(OmnisGlobalConstants.OMNIS_MAIN_CATEGORY__OMNIS_MAIN_CAT_NAME),
						getOmnisMainCategoryDataResultSet.getInt(OmnisGlobalConstants.OMNIS_MAIN_CATEGORY__OMNIS_MAIN_CAT_ID),
						getOmnisMainCategoryDataResultSet.getString(OmnisGlobalConstants.OMNIS_MAIN_CATEGORY__OMNIS_MAIN_ICON_PATH)));
			}
			
			getOmnisMainCategoryDataResultSet.close();
			getOmnisMainCategoryDataStatement.close();
											
			omnisEditorTreeItems = new TreeItem[omnisMainCategoryDataAL.size()];
			
			//Get data for sub category nodes
			omnisEditorTreeImageView = new ImageView[omnisMainCategoryDataAL.size()];			
			omnisEditorChildTreeItems = new TreeItem[omnisMainCategoryDataAL.size()];
						
			for (int _indexi = 0; _indexi < omnisMainCategoryDataAL.size(); _indexi++) {
				omnisMainCategoryDataModel = (OmnisMainCategoryDataModel) omnisMainCategoryDataAL.get(_indexi);
	            
				omnisEditorTreeImageView[_indexi] = new ImageView();
				omnisEditorTreeImageView[_indexi].setImage(new Image(getClass().getResource(
						omnisMainCategoryDataModel.getOmnisMainCategoryImagePath()).toString()));
				omnisEditorTreeImageView[_indexi].setFitWidth(OmnisGlobalConstants.OMNIS_EDITOR_TREE_IMAGE_SIZE);
				omnisEditorTreeImageView[_indexi].setPreserveRatio(true);
				omnisEditorTreeImageView[_indexi].setSmooth(true);
				omnisEditorTreeImageView[_indexi].setCache(true);
	            
				omnisEditorTreeItems[_indexi] = new TreeItem<OmnisCategoryTreeDataModel>(
						new OmnisCategoryTreeDataModel(omnisMainCategoryDataModel.getOmnisMainCategoryName(),
								omnisMainCategoryDataModel.getOmnisMainCategoryId(),0),
						omnisEditorTreeImageView[_indexi]);	            				          
	            
	            PreparedStatement getOmnisSubCategoryDataPStmt = 
	            		omnisDBConnection.prepareStatement(omnisQueryBuilder.getOmnisSubCategoryDetailsSQL());
	            getOmnisSubCategoryDataPStmt.setInt(1, omnisMainCategoryDataModel.getOmnisMainCategoryId());
	            getOmnisSubCategoryDataPStmt.setQueryTimeout(OmnisGlobalConstants.OMNIS_DATABASE_SQL_TIMEOUT);
	            ResultSet getOmnisSubCategoryDataResultSet = getOmnisSubCategoryDataPStmt.executeQuery();
	            
	            while (getOmnisSubCategoryDataResultSet.next()) {
	            	omnisSubCategoryDataAL.add(new OmnisSubCategoryDataModel(
	            			getOmnisSubCategoryDataResultSet.getString(OmnisGlobalConstants.OMNIS_SUB_CATEGORY__OMNIS_SUB_CAT_NAME),
	            			getOmnisSubCategoryDataResultSet.getInt(OmnisGlobalConstants.OMNIS_SUB_CATEGORY__OMNIS_SUB_CAT_ID),
	            			getOmnisSubCategoryDataResultSet.getInt(OmnisGlobalConstants.OMNIS_SUB_CATEGORY__OMNIS_MAIN_CAT_ID)));
				}	            
	            
	            getOmnisSubCategoryDataResultSet.close();
	            getOmnisSubCategoryDataPStmt.close();
	            
	    	    for (int _indexj=0; _indexj<omnisSubCategoryDataAL.size(); _indexj++) {
	    	    	final OmnisSubCategoryDataModel omnisSubCategoryDataModel = (OmnisSubCategoryDataModel) omnisSubCategoryDataAL.get(_indexj);
	    	    	
	    	    	omnisEditorChildTreeItems[_indexi] = new TreeItem<OmnisCategoryTreeDataModel>(
	    	    			new OmnisCategoryTreeDataModel(
	    	    					omnisSubCategoryDataModel.getOmnisSubCategoryName(),
	    	    					omnisSubCategoryDataModel.getOmnisMainCategoryId(),
	    	    					omnisSubCategoryDataModel.getOmnisSubCategoryId()));
	    	    	
	    	    	omnisEditorTreeItems[_indexi].getChildren().add(omnisEditorChildTreeItems[_indexi]);
	    	        	    	        
	    	    }	    	    	    	    	    	    
	    	    omnisSubCategoryDataAL.clear();
	    	    
	    	    omnisEditorRootNode.getChildren().add(omnisEditorTreeItems[_indexi]);
	        }
			omnisEditorRootNode.setExpanded(true);
			omnisDBConnection.close();
		} catch (Exception genericException) {
			logger.error(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + genericException.getStackTrace()[0].getMethodName(), genericException);
			Dialog.showThrowable(OmnisGlobalConstants.OMNIS_DIALOG_TITLE, OmnisGlobalMessages.OMNISGUICONTROLLEREDITOR_LOAD_ERROR, genericException);
		} 
		
		return omnisEditorRootNode;
	}
	
	public HashMap<String, Object> getCategoryEditorDetails (boolean isSubCategory, int omnisMainCategoryId, int omnisSubCategoryId)  {
		logger.debug("Entering OmnisEditorDataProcessor.getCategoryEditorDetails()");
		
		HashMap<String, Object> omnisEditorDetailsHM = new HashMap<String, Object>();
		ObservableList<OmnisDirScannerPathsDataModel> omnisDirScannerPathsDataModel = null;
		
		//Initialize Classes
		omnisQueryBuilder = new OmnisQueryBuilder();
		
		try {
			Connection omnisDBConnection = (Connection) OmnisGlobalVariables.OMNIS_DB_CONNECTION_POOL.getConnection();
						
			if(!isSubCategory) {
				//If any of the main category is selected in tree
				PreparedStatement getOmnisMainCategoryDataPStmt = 
	            		omnisDBConnection.prepareStatement(omnisQueryBuilder.getOmnisMainCategoryDetailsByIdSQL());
				getOmnisMainCategoryDataPStmt.setInt(1, omnisMainCategoryId);
				getOmnisMainCategoryDataPStmt.setQueryTimeout(OmnisGlobalConstants.OMNIS_DATABASE_SQL_TIMEOUT);
	            ResultSet getOmnisMainCategoryDataResultSet = getOmnisMainCategoryDataPStmt.executeQuery();
	            
	            while (getOmnisMainCategoryDataResultSet.next()) {	            	
	            	omnisEditorDetailsHM.put(OmnisGlobalConstants.OMNIS_EDITOR_MAIN_CATEGORY_NAME_KEY, 
	            			getOmnisMainCategoryDataResultSet.getString(OmnisGlobalConstants.OMNIS_MAIN_CATEGORY__OMNIS_MAIN_CAT_NAME));
	            	omnisEditorDetailsHM.put(OmnisGlobalConstants.OMNIS_EDITOR_MAIN_CATEGORY_ID_KEY, 
	            			getOmnisMainCategoryDataResultSet.getInt(OmnisGlobalConstants.OMNIS_MAIN_CATEGORY__OMNIS_MAIN_CAT_ID));
	            	omnisEditorDetailsHM.put(OmnisGlobalConstants.OMNIS_EDITOR_MAIN_CATEGORY_ICON_PATH_KEY, 
	            			getOmnisMainCategoryDataResultSet.getString(OmnisGlobalConstants.OMNIS_MAIN_CATEGORY__OMNIS_MAIN_ICON_PATH));
	            	omnisEditorDetailsHM.put(OmnisGlobalConstants.OMNIS_EDITOR_SUB_CATEGORY_NAME_KEY, "");
	            	omnisEditorDetailsHM.put(OmnisGlobalConstants.OMNIS_EDITOR_SUB_CATEGORY_ID_KEY, 0);

				}	            
	            
	            getOmnisMainCategoryDataResultSet.close();
	            getOmnisMainCategoryDataPStmt.close();	            	           
			} else { 
				//If any of the sub category is selected in tree
				PreparedStatement getOmnisAllCategoryDataPStmt = 
	            		omnisDBConnection.prepareStatement(omnisQueryBuilder.getOmnisAllCategoryDetailsByIdSQL());
				getOmnisAllCategoryDataPStmt.setInt(1, omnisSubCategoryId);
				getOmnisAllCategoryDataPStmt.setQueryTimeout(OmnisGlobalConstants.OMNIS_DATABASE_SQL_TIMEOUT);
	            ResultSet getOmnisAllCategoryDataResultSet = getOmnisAllCategoryDataPStmt.executeQuery();
	            
	            while (getOmnisAllCategoryDataResultSet.next()) {	      
	            	omnisEditorDetailsHM.put(OmnisGlobalConstants.OMNIS_EDITOR_MAIN_CATEGORY_ID_KEY, 
	            			getOmnisAllCategoryDataResultSet.getInt(OmnisGlobalConstants.OMNIS_MAIN_CATEGORY__OMNIS_MAIN_CAT_ID));
	            	omnisEditorDetailsHM.put(OmnisGlobalConstants.OMNIS_EDITOR_MAIN_CATEGORY_NAME_KEY, 
	            			getOmnisAllCategoryDataResultSet.getString(OmnisGlobalConstants.OMNIS_MAIN_CATEGORY__OMNIS_MAIN_CAT_NAME));
	            	omnisEditorDetailsHM.put(OmnisGlobalConstants.OMNIS_EDITOR_MAIN_CATEGORY_ICON_PATH_KEY, 
	            			getOmnisAllCategoryDataResultSet.getString(OmnisGlobalConstants.OMNIS_MAIN_CATEGORY__OMNIS_MAIN_ICON_PATH));
	            	omnisEditorDetailsHM.put(OmnisGlobalConstants.OMNIS_EDITOR_SUB_CATEGORY_NAME_KEY, 
	            			getOmnisAllCategoryDataResultSet.getString(OmnisGlobalConstants.OMNIS_SUB_CATEGORY__OMNIS_SUB_CAT_NAME));
	            	omnisEditorDetailsHM.put(OmnisGlobalConstants.OMNIS_EDITOR_SUB_CATEGORY_ID_KEY, 
	            			getOmnisAllCategoryDataResultSet.getInt(OmnisGlobalConstants.OMNIS_SUB_CATEGORY__OMNIS_SUB_CAT_ID));
				}	            
	            
	            getOmnisAllCategoryDataResultSet.close();
	            getOmnisAllCategoryDataPStmt.close();		            	            
			}
			
			//Get scan directory paths
			omnisDirScannerPathsDataModel = getOmnisEditorDirScanTableData(isSubCategory, omnisSubCategoryId, omnisMainCategoryId);            
            omnisEditorDetailsHM.put(OmnisGlobalConstants.OMNIS_EDITOR_DIR_SCANNER_PATHS_KEY, omnisDirScannerPathsDataModel);
			
			omnisDBConnection.close();
		} catch (Exception genericException) {
			logger.error(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + genericException.getStackTrace()[0].getMethodName(), genericException);
			Dialog.showThrowable(OmnisGlobalConstants.OMNIS_DIALOG_TITLE, OmnisGlobalMessages.OMNISGUICONTROLLEREDITOR_LOAD_ERROR, genericException);
		}
		
		return omnisEditorDetailsHM;
	}
	
	public ObservableList<OmnisDirScannerPathsDataModel> getOmnisEditorDirScanTableData (boolean _isSubCategory, int _omnisSubCategoryId,
			int _omnisMainCategoryId) {
		logger.debug("Entering OmnisEditorDataProcessor.getOmnisEditorDirScanTableData()");
		
		ObservableList<OmnisDirScannerPathsDataModel> omnisDirScannerPathsDataModel = null;
		omnisQueryBuilder = new OmnisQueryBuilder();
		
		try {
			omnisDirScannerPathsDataModel = FXCollections.observableArrayList();
			Connection omnisDBConnection = (Connection) OmnisGlobalVariables.OMNIS_DB_CONNECTION_POOL.getConnection();
			
			PreparedStatement getOmnisDirScanPathsPS  = 
            		omnisDBConnection.prepareStatement(omnisQueryBuilder.getOmnisDirectoryScannerDetailsSQL(_isSubCategory));
			if(_isSubCategory)
				getOmnisDirScanPathsPS.setInt(1, _omnisSubCategoryId);
			else
				getOmnisDirScanPathsPS.setInt(1, _omnisMainCategoryId);
			
			getOmnisDirScanPathsPS.setQueryTimeout(OmnisGlobalConstants.OMNIS_DATABASE_SQL_TIMEOUT);
            ResultSet getOmnisDirScanPathsRS = getOmnisDirScanPathsPS.executeQuery();
            
            while (getOmnisDirScanPathsRS.next()) {
            	omnisDirScannerPathsDataModel.add(new OmnisDirScannerPathsDataModel(
            			OmnisCoreUtils.getScanTypeNameFromId(getOmnisDirScanPathsRS.getInt(OmnisGlobalConstants.OMNIS_DIR_SCANNER__OMNIS_SCAN_TYPE_ID)),
            			Integer.toString(getOmnisDirScanPathsRS.getInt(OmnisGlobalConstants.OMNIS_DIR_SCANNER__OMNIS_SCAN_TYPE_ID)),
            			getOmnisDirScanPathsRS.getString(OmnisGlobalConstants.OMNIS_DIR_SCANNER__OMNIS_SCAN_PATH),
            			Integer.toString(getOmnisDirScanPathsRS.getInt(OmnisGlobalConstants.OMNIS_DIR_SCANNER__OMNIS_DIR_SCAN_ID)),
            			Integer.toString(getOmnisDirScanPathsRS.getInt(OmnisGlobalConstants.OMNIS_DIR_SCANNER__OMNIS_SUB_CAT_ID))));
            }						
            
            getOmnisDirScanPathsRS.close();
            getOmnisDirScanPathsPS.close();
            omnisDBConnection.close();
		} catch (Exception genericException) {
			logger.error(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + genericException.getStackTrace()[0].getMethodName(), genericException);
			Dialog.showThrowable(OmnisGlobalConstants.OMNIS_DIALOG_TITLE, OmnisGlobalMessages.OMNISGUICONTROLLEREDITOR_LOAD_ERROR, genericException);
		} 
		
		return omnisDirScannerPathsDataModel;
	}
	
	public ArrayList<OmnisEditorTableColumnsDataModel> getOmnisEditorTableColums() {
		logger.debug("Entering OmnisEditorDataProcessor.getOmnisEditorTableColums()");
		
		ArrayList<OmnisEditorTableColumnsDataModel> omnisEditorTableColumnsDataModelAL = new ArrayList<OmnisEditorTableColumnsDataModel>();
		
		try {
			omnisEditorTableColumnsDataModelAL.add(new OmnisEditorTableColumnsDataModel(
					OmnisGlobalConstants.OMNIS_EDITOR_TABLEVIEW_COL_I_NAME, 
					OmnisGlobalConstants.OMNIS_EDITOR_TABLEVIEW_COL_I_PROP, 
					OmnisGlobalConstants.OMNIS_EDITOR_TABLEVIEW_COL_I_WIDTH));
			omnisEditorTableColumnsDataModelAL.add(new OmnisEditorTableColumnsDataModel(
					OmnisGlobalConstants.OMNIS_EDITOR_TABLEVIEW_COL_II_NAME, 
					OmnisGlobalConstants.OMNIS_EDITOR_TABLEVIEW_COL_II_PROP, 
					OmnisGlobalConstants.OMNIS_EDITOR_TABLEVIEW_COL_II_WIDTH));
		}  catch (Exception genericException) {
			logger.error(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + genericException.getStackTrace()[0].getMethodName(), genericException);
			Dialog.showThrowable(OmnisGlobalConstants.OMNIS_DIALOG_TITLE, OmnisGlobalMessages.OMNISGUICONTROLLEREDITOR_LOAD_ERROR, genericException);
		} 

		return omnisEditorTableColumnsDataModelAL;
	}
	
	public int deleteOmnisEditorDirectoryScannerPath(int _omnisDirectoryScanIdPK) {
		logger.debug("Entering OmnisEditorDataProcessor.deleteOmnisEditorDirectoryScannerPath()");
		
		int deleteCount = 0;
		try {
			//Initialize Classes
			omnisQueryBuilder = new OmnisQueryBuilder();
			
			Connection omnisDBConnection = (Connection) OmnisGlobalVariables.OMNIS_DB_CONNECTION_POOL.getConnection();
			omnisDBConnection.setAutoCommit(false);
			
			PreparedStatement deleteOmnisEditorDirectoryScannerPathPS = omnisDBConnection.prepareStatement(
					omnisQueryBuilder.deleteOmnisDirectoryScannerPath());
			deleteOmnisEditorDirectoryScannerPathPS.setInt(1, _omnisDirectoryScanIdPK);
			deleteCount = deleteOmnisEditorDirectoryScannerPathPS.executeUpdate();
			omnisDBConnection.commit();
			deleteOmnisEditorDirectoryScannerPathPS.close();
			
			omnisDBConnection.close();
		}  catch (Exception genericException) {
			logger.error(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + genericException.getStackTrace()[0].getMethodName(), genericException);
			Dialog.showThrowable(OmnisGlobalConstants.OMNIS_DIALOG_TITLE, OmnisGlobalMessages.OMNISGUICONTROLLEREDITOR_LOAD_ERROR, genericException);
		}
		return deleteCount;
	}
	
	public ObservableList<OmnisEditorScanTypeDataModel> getOmnisEditorNewDirectoryScanTypeData() {
		logger.debug("Entering OmnisEditorDataProcessor.getOmnisEditorNewDirectoryScanTypeData()");
		
		ObservableList<OmnisEditorScanTypeDataModel> omnisEditorNewDirectoryScanTypeData = null;				
		try {
			omnisEditorNewDirectoryScanTypeData =
	 	            FXCollections.observableArrayList(
	 	            new OmnisEditorScanTypeDataModel(OmnisGlobalConstants.OMNIS_EDITOR_NEW_DIR_SCAN_TYPE_FILE_ONLY_ID,
		            		OmnisGlobalConstants.OMNIS_EDITOR_NEW_DIR_SCAN_TYPE_FILE_ONLY_NAME),
	 	           new OmnisEditorScanTypeDataModel(OmnisGlobalConstants.OMNIS_EDITOR_NEW_DIR_SCAN_TYPE_DIR_ONLY_ID,
		            		OmnisGlobalConstants.OMNIS_EDITOR_NEW_DIR_SCAN_TYPE_DIR_ONLY_NAME),
	 	           new OmnisEditorScanTypeDataModel(OmnisGlobalConstants.OMNIS_EDITOR_NEW_DIR_SCAN_TYPE_BOTH_ID,
		            		OmnisGlobalConstants.OMNIS_EDITOR_NEW_DIR_SCAN_TYPE_BOTH_NAME));
		}  catch (Exception genericException) {
			logger.error(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + genericException.getStackTrace()[0].getMethodName(), genericException);
			Dialog.showThrowable(OmnisGlobalConstants.OMNIS_DIALOG_TITLE, OmnisGlobalMessages.OMNISMAINDATAPROCESSOR_SEARCH_FIELD_ERROR, genericException);
		}	
		
		return omnisEditorNewDirectoryScanTypeData;
	}
	
	public int insertNewDirectoryScannerPath(int _omnisSubCategoryId, int _omnisDirScannerScanTypeId, 
			String _omnisDirScannerDirPath, String _omnisDirScannerExclusions) {
		logger.debug("Entering OmnisEditorDataProcessor.insertNewDirectoryScannerPath()");
		
		int insertCount = 0;
		try {
			//Initialize Classes
			omnisQueryBuilder = new OmnisQueryBuilder();
			
			Connection omnisDBConnection = (Connection) OmnisGlobalVariables.OMNIS_DB_CONNECTION_POOL.getConnection();
			omnisDBConnection.setAutoCommit(false);
			
			PreparedStatement insertNewDirectoryScannerPathPS = omnisDBConnection.prepareStatement(
					omnisQueryBuilder.insertOmnisDirectoryScannerPath());
			insertNewDirectoryScannerPathPS.setInt(1, _omnisSubCategoryId);
			insertNewDirectoryScannerPathPS.setInt(2, _omnisDirScannerScanTypeId);
			insertNewDirectoryScannerPathPS.setString(3, _omnisDirScannerDirPath);
			insertCount = insertNewDirectoryScannerPathPS.executeUpdate();
			omnisDBConnection.commit();
			insertNewDirectoryScannerPathPS.close();
			
			omnisDBConnection.close();
		}  catch (Exception genericException) {
			logger.error(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + genericException.getStackTrace()[0].getMethodName(), genericException);
			Dialog.showThrowable(OmnisGlobalConstants.OMNIS_DIALOG_TITLE, OmnisGlobalMessages.OMNISGUICONTROLLEREDITOR_LOAD_ERROR, genericException);
		}
		return insertCount;
	}
}
