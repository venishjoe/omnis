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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import net.venishjoe.omnis.commons.OmnisCoreUtils;
import net.venishjoe.omnis.data.database.OmnisQueryBuilder;
import net.venishjoe.omnis.data.model.OmnisMainTableViewDataModel;
import net.venishjoe.omnis.data.model.OmnisFileSizeOptionsDataModel;
import net.venishjoe.omnis.data.model.OmnisMainCategoryDataModel;
import net.venishjoe.omnis.data.model.OmnisMainTableColumnsDataModel;
import net.venishjoe.omnis.data.model.OmnisSubCategoryDataModel;
import net.venishjoe.omnis.staticdata.OmnisGlobalConstants;
import net.venishjoe.omnis.staticdata.OmnisGlobalMessages;
import net.venishjoe.omnis.staticdata.OmnisGlobalVariables;
import net.venishjoe.omnis.thirdparty.dialog.Dialog;

import org.apache.log4j.Logger;

public class OmnisMainDataProcessor {
	private static Logger logger = Logger.getLogger(OmnisMainDataProcessor.class);
	private OmnisQueryBuilder omnisQueryBuilder;
	
	public TitledPane [] createAccordion()  {
		logger.debug("Entering OmnisMainDataProcessor.createAccordion()");
		TitledPane [] omnisMainAccordionTP = null;
		
		try {
			omnisQueryBuilder = new OmnisQueryBuilder();
			OmnisMainCategoryDataModel omnisMainCategoryDataModel;
			
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
											
			omnisMainAccordionTP = new TitledPane[omnisMainCategoryDataAL.size()];
			ImageView[] omnisMainAccordionImageView = new ImageView[omnisMainCategoryDataAL.size()];			
			VBox[] omnisMainAccordionVBox = new VBox[omnisMainCategoryDataAL.size()];
			ScrollPane[] omnisTPItemsScrollPane = new ScrollPane[omnisMainCategoryDataAL.size()];
			
			for (int _indexi = 0; _indexi < omnisMainCategoryDataAL.size(); _indexi++) {
				omnisMainCategoryDataModel = (OmnisMainCategoryDataModel) omnisMainCategoryDataAL.get(_indexi);
	            
				omnisMainAccordionImageView[_indexi] = new ImageView();
				omnisMainAccordionImageView[_indexi].setImage(new Image(getClass().getResource(
						omnisMainCategoryDataModel.getOmnisMainCategoryImagePath()).toString()));
				omnisMainAccordionImageView[_indexi].setFitWidth(OmnisGlobalConstants.OMNIS_MAIN_ACCORDION_IMAGE_SIZE);
				omnisMainAccordionImageView[_indexi].setPreserveRatio(true);
				omnisMainAccordionImageView[_indexi].setSmooth(true);
				omnisMainAccordionImageView[_indexi].setCache(true);
	            
	            omnisMainAccordionTP[_indexi] = new TitledPane();
	            omnisMainAccordionTP[_indexi].setText(OmnisCoreUtils.padLeft(omnisMainCategoryDataModel.getOmnisMainCategoryName(),7));
	            omnisMainAccordionTP[_indexi].setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 14));
	            omnisMainAccordionTP[_indexi].setGraphic(omnisMainAccordionImageView[_indexi]);
	            omnisMainAccordionTP[_indexi].setContentDisplay(ContentDisplay.LEFT);
	            
	            omnisMainAccordionVBox[_indexi] = new VBox();
	            omnisMainAccordionVBox[_indexi].setPadding(new Insets(10));
	            omnisMainAccordionVBox[_indexi].setSpacing(8);	           
	            
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
	            
	            Hyperlink listOfOmnisSubCategoryValues;
	    	    for (int _indexj=0; _indexj<omnisSubCategoryDataAL.size(); _indexj++) {
	    	    	final OmnisSubCategoryDataModel omnisSubCategoryDataModel = (OmnisSubCategoryDataModel) omnisSubCategoryDataAL.get(_indexj);
	    	    	listOfOmnisSubCategoryValues = new Hyperlink(omnisSubCategoryDataModel.getOmnisSubCategoryName());
	    	    	listOfOmnisSubCategoryValues.setCursor(Cursor.HAND);
	    	    	listOfOmnisSubCategoryValues.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 14));
	    	        VBox.setMargin(listOfOmnisSubCategoryValues, new Insets(0, 0, 0, 8));
	    	        omnisMainAccordionVBox[_indexi].getChildren().add(listOfOmnisSubCategoryValues);
	    	        
	    	        listOfOmnisSubCategoryValues.setOnAction(new EventHandler<ActionEvent>() {
	    	            @Override
	    	            public void handle(ActionEvent e) {
	    	                //Link Clicked
	    	            }
	    	        });
	    	    }
	    	    omnisSubCategoryDataAL.clear();
	    	    
	    	    omnisTPItemsScrollPane[_indexi] = new ScrollPane();
	    	    omnisTPItemsScrollPane[_indexi].setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
	    	    omnisTPItemsScrollPane[_indexi].setVbarPolicy(ScrollBarPolicy.AS_NEEDED);

	    	    omnisTPItemsScrollPane[_indexi].setContent(omnisMainAccordionVBox[_indexi]);
	    	    omnisMainAccordionTP[_indexi].setContent(omnisTPItemsScrollPane[_indexi]);   	    	   
	        }
			omnisDBConnection.close();
		} catch (Exception genericException) {
			logger.error(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + genericException.getStackTrace()[0].getMethodName(), genericException);
			Dialog.showThrowable(OmnisGlobalConstants.OMNIS_DIALOG_TITLE, OmnisGlobalMessages.OMNISMAINDATAPROCESSOR_CREATEACCD_ERROR, genericException);
		} 
		
		return omnisMainAccordionTP;
	}
	
	
	public ArrayList<OmnisMainTableColumnsDataModel> getOmnisMainTableColums() {
		logger.debug("Entering OmnisMainDataProcessor.getOmnisMainTableColums()");
		
		ArrayList<OmnisMainTableColumnsDataModel> tc = new ArrayList<OmnisMainTableColumnsDataModel>();
		
		try {
			tc.add(new OmnisMainTableColumnsDataModel("Main Category", "omnisMainCategory", 100));
			tc.add(new OmnisMainTableColumnsDataModel("Sub Category", "omnisSubCategory", 100));
			tc.add(new OmnisMainTableColumnsDataModel("File Name", "omnisFileName", 350));
			tc.add(new OmnisMainTableColumnsDataModel("File Type", "omnisFileType", 75));
			tc.add(new OmnisMainTableColumnsDataModel("File Size", "omnisFileSize", 75));
			tc.add(new OmnisMainTableColumnsDataModel("Created Date", "omnisCreatedDate", 120));
			tc.add(new OmnisMainTableColumnsDataModel("Last Accessed Date", "omnisLastAccessedDate", 120));
		}  catch (Exception genericException) {
			logger.error(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + genericException.getStackTrace()[0].getMethodName(), genericException);
			Dialog.showThrowable(OmnisGlobalConstants.OMNIS_DIALOG_TITLE, OmnisGlobalMessages.OMNISMAINDATAPROCESSOR_TBLCOLS_ERROR, genericException);
		} 

		return tc;
	}
	
	public ObservableList<OmnisMainTableViewDataModel> getMainTableData() {
		logger.debug("Entering OmnisMainDataProcessor.getMainTableData()");
		
		ObservableList<OmnisMainTableViewDataModel> data = null;
		
		try {
			 data =
			        FXCollections.observableArrayList(
			            new OmnisMainTableViewDataModel("Music", "English", "Paris Hilton - Some Music.mp3", "MP3", "30482", "2012-01-01", "2012-04-29"),
			            new OmnisMainTableViewDataModel("Music", "English", "Paris Hilton - Some Music.mp3", "MP3", "30482", "2012-01-01", "2012-04-29"),
			            new OmnisMainTableViewDataModel("Music", "English", "Paris Hilton - Some Music.mp3", "MP3", "30482", "2012-01-01", "2012-04-29"),
			            new OmnisMainTableViewDataModel("Music", "English", "Paris Hilton - Some Music.mp3", "MP3", "30482", "2012-01-01", "2012-04-29"),
			            new OmnisMainTableViewDataModel("Music", "English", "Paris Hilton - Some Music.mp3", "MP3", "30482", "2012-01-01", "2012-04-29"),
			            new OmnisMainTableViewDataModel("Music", "English", "Paris Hilton - Some Music.mp3", "MP3", "30482", "2012-01-01", "2012-04-29"),
			            new OmnisMainTableViewDataModel("Music", "English", "Paris Hilton - Some Music.mp3", "MP3", "30482", "2012-01-01", "2012-04-29"),
			            new OmnisMainTableViewDataModel("Music", "English", "Paris Hilton - Some Music.mp3", "MP3", "30482", "2012-01-01", "2012-04-29"),
			            new OmnisMainTableViewDataModel("Music", "English", "Paris Hilton - Some Music.mp3", "MP3", "30482", "2012-01-01", "2012-04-29"),
			            new OmnisMainTableViewDataModel("Music", "English", "Paris Hilton - Some Music.mp3", "MP3", "30482", "2012-01-01", "2012-04-29"),
			            new OmnisMainTableViewDataModel("Music", "English", "Paris Hilton - Some Music.mp3", "MP3", "30482", "2012-01-01", "2012-04-29"),
			            new OmnisMainTableViewDataModel("Music", "English", "Paris Hilton - Some Music.mp3", "MP3", "30482", "2012-01-01", "2012-04-29"),
			            new OmnisMainTableViewDataModel("Music", "English", "Paris Hilton - Some Music.mp3", "MP3", "30482", "2012-01-01", "2012-04-29"),
			            new OmnisMainTableViewDataModel("Music", "English", "Paris Hilton - Some Music.mp3", "MP3", "30482", "2012-01-01", "2012-04-29"),
			            new OmnisMainTableViewDataModel("Music", "English", "Paris Hilton - Some Music.mp3", "MP3", "30482", "2012-01-01", "2012-04-29"),
			            new OmnisMainTableViewDataModel("Music", "English", "Paris Hilton - Some Music.mp3", "MP3", "30482", "2012-01-01", "2012-04-29"),
			            new OmnisMainTableViewDataModel("Music", "English", "Paris Hilton - Some Music.mp3", "MP3", "30482", "2012-01-01", "2012-04-29"),
			            new OmnisMainTableViewDataModel("Music", "English", "Paris Hilton - Some Music.mp3", "MP3", "30482", "2012-01-01", "2012-04-29"),
			            new OmnisMainTableViewDataModel("Music", "English", "Paris Hilton - Some Music.mp3", "MP3", "30482", "2012-01-01", "2012-04-29"),
			            new OmnisMainTableViewDataModel("Music", "English", "Paris Hilton - Some Music.mp3", "MP3", "30482", "2012-01-01", "2012-04-29"),
			            new OmnisMainTableViewDataModel("Music", "English", "Paris Hilton - Some Music.mp3", "MP3", "30482", "2012-01-01", "2012-04-29"),
			            new OmnisMainTableViewDataModel("Music", "English", "Paris Hilton - Some Music.mp3", "MP3", "30482", "2012-01-01", "2012-04-29"),	
			            new OmnisMainTableViewDataModel("Music", "English", "Paris Hilton - Some Music.mp3", "MP3", "30482", "2012-01-01", "2012-04-29"),
			            new OmnisMainTableViewDataModel("Music", "English", "Paris Hilton - Some Music.mp3", "MP3", "30482", "2012-01-01", "2012-04-29"),
			            new OmnisMainTableViewDataModel("Music", "English", "Paris Hilton - Some Music.mp3", "MP3", "30482", "2012-01-01", "2012-04-29")
			        );
		}  catch (Exception genericException) {
			logger.error(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + genericException.getStackTrace()[0].getMethodName(), genericException);
			Dialog.showThrowable(OmnisGlobalConstants.OMNIS_DIALOG_TITLE, OmnisGlobalMessages.OMNISMAINDATAPROCESSOR_TBLDATA_ERROR, genericException);
		} 

		return data;
	}
	
	public ObservableList<OmnisMainCategoryDataModel> getMainCategoryCBData() {
		logger.debug("Entering OmnisMainDataProcessor.getMainCategoryCBData()");
		
		ObservableList<OmnisMainCategoryDataModel> omnisMainCategoryCBData = null;	
		ArrayList<OmnisMainCategoryDataModel> omnisMainCategoryCBDataAL = new ArrayList<OmnisMainCategoryDataModel>();
	
		try {
			omnisMainCategoryCBDataAL.add(new OmnisMainCategoryDataModel(
					OmnisGlobalConstants.OMNIS_MAIN_CAT_FILTER_DM_ALL_TXT,
		            OmnisGlobalConstants.OMNIS_MAIN_CAT_FILTER_DM_ALL_VAL, 
		            null));
						
			Connection omnisDBConnection = (Connection) OmnisGlobalVariables.OMNIS_DB_CONNECTION_POOL.getConnection();
			
			Statement getOmnisMainCategoryDataStatement = omnisDBConnection.createStatement();
			getOmnisMainCategoryDataStatement.setQueryTimeout(OmnisGlobalConstants.OMNIS_DATABASE_SQL_TIMEOUT);
			ResultSet getOmnisMainCategoryDataResultSet = 
					getOmnisMainCategoryDataStatement.executeQuery(omnisQueryBuilder.getOmnisMainCategoryDetailsSQL());
			
			while (getOmnisMainCategoryDataResultSet.next()) {				
				omnisMainCategoryCBDataAL.add(new OmnisMainCategoryDataModel(
						getOmnisMainCategoryDataResultSet.getString(OmnisGlobalConstants.OMNIS_MAIN_CATEGORY__OMNIS_MAIN_CAT_NAME),
			            getOmnisMainCategoryDataResultSet.getInt(OmnisGlobalConstants.OMNIS_MAIN_CATEGORY__OMNIS_MAIN_CAT_ID),
						getOmnisMainCategoryDataResultSet.getString(OmnisGlobalConstants.OMNIS_MAIN_CATEGORY__OMNIS_MAIN_ICON_PATH)));
			}
			
			getOmnisMainCategoryDataResultSet.close();
			getOmnisMainCategoryDataStatement.close();
			omnisDBConnection.close();
			
			omnisMainCategoryCBData = FXCollections.observableArrayList(omnisMainCategoryCBDataAL);
			
		}  catch (Exception genericException) {
			logger.error(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + genericException.getStackTrace()[0].getMethodName(), genericException);
			Dialog.showThrowable(OmnisGlobalConstants.OMNIS_DIALOG_TITLE, OmnisGlobalMessages.OMNISMAINDATAPROCESSOR_SEARCH_FIELD_ERROR, genericException);
		} 
		
		return omnisMainCategoryCBData;
	}
	
	public ObservableList<OmnisSubCategoryDataModel> getSubCategoryCBData() {
		logger.debug("Entering OmnisMainDataProcessor.getSubCategoryCBData()");
		
		ObservableList<OmnisSubCategoryDataModel> omnisSubCategoryCBData = null;				
		try {
			omnisSubCategoryCBData =
	 	            FXCollections.observableArrayList(
	 	            new OmnisSubCategoryDataModel(OmnisGlobalConstants.OMNIS_SUB_CAT_FILTER_DM_ALL_TXT,
		            		OmnisGlobalConstants.OMNIS_SUB_CAT_FILTER_DM_ALL_VAL, 
		            		OmnisGlobalConstants.OMNIS_MAIN_CAT_FILTER_DM_ALL_VAL));
		}  catch (Exception genericException) {
			logger.error(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + genericException.getStackTrace()[0].getMethodName(), genericException);
			Dialog.showThrowable(OmnisGlobalConstants.OMNIS_DIALOG_TITLE, OmnisGlobalMessages.OMNISMAINDATAPROCESSOR_SEARCH_FIELD_ERROR, genericException);
		}	
		
		return omnisSubCategoryCBData;
	}
	
	
	public ObservableList<OmnisSubCategoryDataModel> getSubCategoryCBData(int _omnisMainCategoryId) {
		logger.debug("Entering OmnisMainDataProcessor.getSubCategoryCBData()");
		
		ObservableList<OmnisSubCategoryDataModel> omnisSubCategoryCBData = null;	
		ArrayList<OmnisSubCategoryDataModel> omnisSubCategoryCBDataAL = new ArrayList<OmnisSubCategoryDataModel>();
		omnisQueryBuilder = new OmnisQueryBuilder();
		
		try {
			omnisSubCategoryCBDataAL.add(new OmnisSubCategoryDataModel(
					OmnisGlobalConstants.OMNIS_SUB_CAT_FILTER_DM_ALL_TXT,
            		OmnisGlobalConstants.OMNIS_SUB_CAT_FILTER_DM_ALL_VAL, 
            		OmnisGlobalConstants.OMNIS_MAIN_CAT_FILTER_DM_ALL_VAL));
			
			if(_omnisMainCategoryId != OmnisGlobalConstants.OMNIS_MAIN_CAT_FILTER_DM_ALL_VAL) {
				Connection omnisDBConnection = (Connection) OmnisGlobalVariables.OMNIS_DB_CONNECTION_POOL.getConnection();
				
				PreparedStatement getOmnisSubCategoryDataPStmt = 
	            		omnisDBConnection.prepareStatement(omnisQueryBuilder.getOmnisSubCategoryDetailsSQL());
	            getOmnisSubCategoryDataPStmt.setInt(1, _omnisMainCategoryId);
	            getOmnisSubCategoryDataPStmt.setQueryTimeout(OmnisGlobalConstants.OMNIS_DATABASE_SQL_TIMEOUT);
	            ResultSet getOmnisSubCategoryDataResultSet = getOmnisSubCategoryDataPStmt.executeQuery();
	            
	            while (getOmnisSubCategoryDataResultSet.next()) {
	            	omnisSubCategoryCBDataAL.add(new OmnisSubCategoryDataModel(
	            			getOmnisSubCategoryDataResultSet.getString(OmnisGlobalConstants.OMNIS_SUB_CATEGORY__OMNIS_SUB_CAT_NAME),
	            			getOmnisSubCategoryDataResultSet.getInt(OmnisGlobalConstants.OMNIS_SUB_CATEGORY__OMNIS_SUB_CAT_ID),
	            			getOmnisSubCategoryDataResultSet.getInt(OmnisGlobalConstants.OMNIS_SUB_CATEGORY__OMNIS_MAIN_CAT_ID)));
				}
				
	            getOmnisSubCategoryDataResultSet.close();
	            getOmnisSubCategoryDataPStmt.close();
				omnisDBConnection.close();
			}
									
			omnisSubCategoryCBData = FXCollections.observableArrayList(omnisSubCategoryCBDataAL);
		}  catch (Exception genericException) {
			logger.error(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + genericException.getStackTrace()[0].getMethodName(), genericException);
			Dialog.showThrowable(OmnisGlobalConstants.OMNIS_DIALOG_TITLE, OmnisGlobalMessages.OMNISMAINDATAPROCESSOR_SEARCH_FIELD_ERROR, genericException);
		}	
		
		return omnisSubCategoryCBData;
	}
	
	public ObservableList<OmnisFileSizeOptionsDataModel> getFileSizeOptionsCBData() {
		logger.debug("Entering OmnisMainDataProcessor.getFileSizeOptionsCBData()");
		
		ObservableList<OmnisFileSizeOptionsDataModel> omnisFileSizeOptionsCBData = null;
		try {
			omnisFileSizeOptionsCBData =
	  	            FXCollections.observableArrayList(
	  	            new OmnisFileSizeOptionsDataModel(OmnisGlobalConstants.OMNIS_FILESIZE_FILTER_DM_EQ_TXT,
	  	            		OmnisGlobalConstants.OMNIS_FILESIZE_FILTER_DM_EQ_VAL),
	  	            new OmnisFileSizeOptionsDataModel(OmnisGlobalConstants.OMNIS_FILESIZE_FILTER_DM_GE_TXT,
	  	            		OmnisGlobalConstants.OMNIS_FILESIZE_FILTER_DM_GE_VAL),
	  	            new OmnisFileSizeOptionsDataModel(OmnisGlobalConstants.OMNIS_FILESIZE_FILTER_DM_LE_TXT,
	  	            		OmnisGlobalConstants.OMNIS_FILESIZE_FILTER_DM_LE_VAL));
		}  catch (Exception genericException) {
			logger.error(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + genericException.getStackTrace()[0].getMethodName(), genericException);
			Dialog.showThrowable(OmnisGlobalConstants.OMNIS_DIALOG_TITLE, OmnisGlobalMessages.OMNISMAINDATAPROCESSOR_SEARCH_FIELD_ERROR, genericException);
		}
		
		return omnisFileSizeOptionsCBData;
	}
	
	public ObservableList<OmnisMainTableViewDataModel> searchOmnisData() {
		logger.debug("Entering OmnisMainDataProcessor.searchOmnisData()");
		
		ObservableList<OmnisMainTableViewDataModel> omnisDataSearchResults = null;
		try {
			omnisDataSearchResults =
			        FXCollections.observableArrayList(
			        		new OmnisMainTableViewDataModel("Software", "Windows", "Windows 8 Professional Edition", "DIR", "3423423423", "2012-10-10", "2012-12-11")
			        );
		}  catch (Exception genericException) {
			logger.error(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + genericException.getStackTrace()[0].getMethodName(), genericException);
			Dialog.showThrowable(OmnisGlobalConstants.OMNIS_DIALOG_TITLE, OmnisGlobalMessages.OMNISGUIMAINCONTROLLER_SEARCH_ERROR, genericException);
		} 
						
		return omnisDataSearchResults;
	}
}
