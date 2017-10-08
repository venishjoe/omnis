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

package net.venishjoe.omnis.controllers;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.venishjoe.omnis.commons.OmnisCoreUtils;
import net.venishjoe.omnis.data.model.OmnisCategoryTreeDataModel;
import net.venishjoe.omnis.data.model.OmnisDirScannerPathsDataModel;
import net.venishjoe.omnis.data.model.OmnisEditorScanTypeDataModel;
import net.venishjoe.omnis.data.model.OmnisEditorTableColumnsDataModel;
import net.venishjoe.omnis.data.process.OmnisEditorDataProcessor;
import net.venishjoe.omnis.gui.OmnisGUIHelper;
import net.venishjoe.omnis.staticdata.OmnisGlobalConstants;
import net.venishjoe.omnis.staticdata.OmnisGlobalMessages;
import net.venishjoe.omnis.staticdata.OmnisGlobalVariables;
import net.venishjoe.omnis.thirdparty.dialog.Dialog;

import org.apache.log4j.Logger;

public class OmnisGUIEditorController implements Initializable, EventHandler<ActionEvent>, ChangeListener<TreeItem<OmnisCategoryTreeDataModel>> {
	private static Logger logger = Logger.getLogger(OmnisGUIEditorController.class);
	
	@FXML private TreeView<OmnisCategoryTreeDataModel> omnisEditorTree;
	@FXML private TextField omnisEditorMainCatTxt;
	@FXML private TextField omnisEditorSubCatTxt;
	@FXML private TextField omnisEditorIconTxt;
	@FXML private ImageView omnisEditorCatImgView;
	@FXML private TableView<OmnisDirScannerPathsDataModel> omnisEditorScanTable;
	@FXML private Button omnisEditorDeleteCatButton;
	@FXML private ComboBox<OmnisEditorScanTypeDataModel> omnisEditorNewDirCB;
	@FXML private TextField omnisEditorNewDirTxt;
	@FXML private Button omnisEditorNewDirAddButton;
	@FXML private Button omnisEditorNewDirDeleteButton;
	@FXML private Button omnisEditorAddCatButton;
	@FXML private HBox omnisEditorStatusBarHB;
	@FXML private TextField omnisEditorNewDirExcTxt;
	@FXML private Label omnisEditorNewDirExcLbl;	
	@FXML private ImageView omnisEditorFileChooserIV;
	@FXML private ImageView omisEditorAddCatIconIV;
	
	@FXML private TextField _H_omnisEditorHiddenSubCatId;
	@FXML private TextField _H_omnisEditorHiddenMainCat;
	@FXML private TextField _H_omnisEditorHiddenIsSubCat;
	
	private OmnisEditorDataProcessor omnisEditorDataProcessor;
	private OmnisGUIHelper omnisGUIHelper;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		logger.debug("Entering OmnisGUIEditorController.initialize()");
		try {
			omnisEditorDataProcessor = new OmnisEditorDataProcessor();
			omnisGUIHelper = new OmnisGUIHelper();
			
			//Populating the tree
			omnisEditorTree.setRoot(omnisEditorDataProcessor.createTreeView());
			omnisEditorTree.setShowRoot(false);
			omnisEditorTree.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
			omnisEditorTree.getSelectionModel().selectedItemProperty().addListener(this);  
			
			//Load Editor Table Columns
			OmnisEditorTableColumnsDataModel omnisEditorTableColumnsDataModel;
			ArrayList<OmnisEditorTableColumnsDataModel> omnisEditorTableColumnsDataModelAL = 
					(ArrayList<OmnisEditorTableColumnsDataModel>) omnisEditorDataProcessor.getOmnisEditorTableColums();
			
			for(int indexi=0;indexi<omnisEditorTableColumnsDataModelAL.size();indexi++){
				omnisEditorTableColumnsDataModel = (OmnisEditorTableColumnsDataModel) omnisEditorTableColumnsDataModelAL.get(indexi);
				omnisEditorScanTable.getColumns().add(omnisGUIHelper.createOmnisEditorTableColumn(
						omnisEditorTableColumnsDataModel.getOmnisEditorTableColumnName(), 
						omnisEditorTableColumnsDataModel.getOmnisEditorTableColumnProperty(), 
						omnisEditorTableColumnsDataModel.getOmnisEditorTableColumnMinWidth()));
			}
			
			//Populate Add New Directory ComboBox
			omnisEditorNewDirCB.setItems(omnisEditorDataProcessor.getOmnisEditorNewDirectoryScanTypeData());
			omnisEditorNewDirCB.getSelectionModel().selectFirst();
			
			//Disabling single row selection.
			//omnisEditorScanTable.getSelectionModel().setCellSelectionEnabled(true);

			//Listener for Table
			omnisEditorScanTable.getSelectionModel().getSelectedCells().addListener(new ListChangeListener<TablePosition>() { 				 
	            @Override 
	            public void onChanged(Change<? extends TablePosition> tablePosition) { 
	            	//Enable Delete Directory Button
	            	omnisEditorNewDirDeleteButton.setDisable(false);
	            }});
			
			/*
			 * Disabling Listener for Text Field. Replaced with Image View.
				//Listener for Directory Chooser
				omnisEditorNewDirTxt.focusedProperty().addListener(new ChangeListener<Boolean>() {
		            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		                if(newValue.booleanValue()) {
		                    //Someone clicked the text field. Present Directory Chooser		                	
		                }	               
		            }
		        } );
			*/
			
			
			//Enable & Disable fields according to selection		
			omnisEditorDeleteCatButton.setDisable(true);
			omnisEditorNewDirCB.setDisable(true);
			omnisEditorNewDirTxt.setDisable(true);
			omnisEditorFileChooserIV.setDisable(true);
			omnisEditorFileChooserIV.setOpacity(OmnisGlobalConstants.OMNIS_IMAGE_OPACITY_DISABLED);
			omnisEditorNewDirAddButton.setDisable(true);
			omnisEditorNewDirDeleteButton.setDisable(true);
			omnisEditorAddCatButton.setDisable(false);
			omnisEditorMainCatTxt.setDisable(false);
			omnisEditorSubCatTxt.setDisable(true);
			omnisEditorIconTxt.setDisable(false);
			omisEditorAddCatIconIV.setDisable(false);
			omnisEditorFileChooserIV.setOpacity(OmnisGlobalConstants.OMNIS_IMAGE_OPACITY_ENABLED);
			omnisEditorNewDirExcTxt.setDisable(true);
			omnisEditorNewDirExcLbl.setDisable(true);
		} catch (Exception genericException) {
			logger.error(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + genericException.getStackTrace()[0].getMethodName(), genericException);
			Dialog.showThrowable(OmnisGlobalConstants.OMNIS_DIALOG_TITLE, OmnisGlobalMessages.OMNISGUICONTROLLEREDITOR_INIT_ERROR, genericException);
		} 
	}
	
	@FXML protected void omnisEditorCloseButtonClicked(ActionEvent closeButtonClicked) {
		logger.debug("Entering OmnisGUIEditorController.omnisEditorCloseButtonClicked()");
	    	try {
	        	  OmnisGlobalVariables.OMNIS_EDITOR_GUI_STAGE.close();
	    	} catch (Exception genericException) {
				logger.error(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + genericException.getStackTrace()[0].getMethodName(), genericException);
				Dialog.showThrowable(OmnisGlobalConstants.OMNIS_DIALOG_TITLE, OmnisGlobalMessages.OMNISGUIMAINCONTROLLER_SEARCH_ERROR, genericException);
			}    			 
	   }
	
	@SuppressWarnings("unchecked")
	@Override
	@FXML public void changed(ObservableValue<? extends TreeItem<OmnisCategoryTreeDataModel>> omnisCategoryTreeDataModelOV,
			TreeItem<OmnisCategoryTreeDataModel> omnisCategoryTreeDataModelOSV, 
			TreeItem<OmnisCategoryTreeDataModel> omnisCategoryTreeDataModelNSV) {
		logger.debug("Entering OmnisGUIEditorController.changed()");
		
		HashMap<String, Object> omnisEditorDetailsHM;
		omnisEditorDataProcessor = new OmnisEditorDataProcessor();
		
		try {						
			if (omnisCategoryTreeDataModelNSV != null) {
				
				OmnisCategoryTreeDataModel omnisCategoryTreeDataModelSV = omnisCategoryTreeDataModelNSV.getValue();
				
				//Enable/Disable fields according to selection
				if (omnisCategoryTreeDataModelNSV.getChildren().size() == 0){
					omnisEditorDeleteCatButton.setDisable(false);
					omnisEditorNewDirCB.setDisable(false);
					omnisEditorNewDirTxt.setDisable(false);
					omnisEditorFileChooserIV.setDisable(false);
					omnisEditorFileChooserIV.setOpacity(OmnisGlobalConstants.OMNIS_IMAGE_OPACITY_ENABLED);
					omnisEditorNewDirAddButton.setDisable(false);
					omnisEditorAddCatButton.setDisable(true);
					omnisEditorMainCatTxt.setDisable(true);
					omnisEditorSubCatTxt.setDisable(true);
					omnisEditorIconTxt.setDisable(true);
					omisEditorAddCatIconIV.setDisable(true);
					omnisEditorFileChooserIV.setOpacity(OmnisGlobalConstants.OMNIS_IMAGE_OPACITY_DISABLED);
					omnisEditorNewDirDeleteButton.setDisable(true);
					omnisEditorNewDirExcTxt.setDisable(false);
					omnisEditorNewDirExcLbl.setDisable(false);
				} else {  
					omnisEditorDeleteCatButton.setDisable(true);
					omnisEditorNewDirCB.setDisable(true);
					omnisEditorNewDirTxt.setDisable(true);
					omnisEditorFileChooserIV.setDisable(true);
					omnisEditorFileChooserIV.setOpacity(OmnisGlobalConstants.OMNIS_IMAGE_OPACITY_DISABLED);
					omnisEditorNewDirAddButton.setDisable(true);
					omnisEditorAddCatButton.setDisable(false);
					omnisEditorMainCatTxt.setDisable(true);
					omnisEditorSubCatTxt.setDisable(false);
					omnisEditorIconTxt.setDisable(true);
					omisEditorAddCatIconIV.setDisable(true);
					omnisEditorFileChooserIV.setOpacity(OmnisGlobalConstants.OMNIS_IMAGE_OPACITY_DISABLED);
					omnisEditorNewDirDeleteButton.setDisable(true);
					omnisEditorNewDirExcTxt.setDisable(true);
					omnisEditorNewDirExcLbl.setDisable(true);
				}
				
				omnisEditorDetailsHM = omnisEditorDataProcessor.getCategoryEditorDetails(omnisCategoryTreeDataModelNSV.isLeaf(),
						omnisCategoryTreeDataModelSV.getOmnisMainCategoryId(), 
						omnisCategoryTreeDataModelSV.getOmnisSubCategoryId());
				 
				omnisEditorMainCatTxt.setText(omnisEditorDetailsHM.get(OmnisGlobalConstants.OMNIS_EDITOR_MAIN_CATEGORY_NAME_KEY).toString());
				omnisEditorSubCatTxt.setText(omnisEditorDetailsHM.get(OmnisGlobalConstants.OMNIS_EDITOR_SUB_CATEGORY_NAME_KEY).toString());
				_H_omnisEditorHiddenSubCatId.setText(omnisEditorDetailsHM.get(OmnisGlobalConstants.OMNIS_EDITOR_SUB_CATEGORY_ID_KEY).toString());
				_H_omnisEditorHiddenMainCat.setText(omnisEditorDetailsHM.get(OmnisGlobalConstants.OMNIS_EDITOR_MAIN_CATEGORY_ID_KEY).toString());
				_H_omnisEditorHiddenIsSubCat.setText(Boolean.toString(omnisCategoryTreeDataModelNSV.isLeaf()));
				
				//Replaced full resource path with only file name
				//omnisEditorIconTxt.setText(omnisEditorDetailsHM.get(OmnisGlobalConstants.OMNIS_EDITOR_MAIN_CATEGORY_ICON_PATH_KEY).toString());
				omnisEditorIconTxt.setText(OmnisCoreUtils.tokenizeStringLastValue(
						omnisEditorDetailsHM.get(OmnisGlobalConstants.OMNIS_EDITOR_MAIN_CATEGORY_ICON_PATH_KEY).toString(),
						OmnisGlobalConstants.OMNIS_EDITOR_ICON_PATH_URL_TOKENIZER_TOKEN));				
				
				omnisEditorCatImgView.setImage(new Image(
						getClass().getResource(omnisEditorDetailsHM.get(
								OmnisGlobalConstants.OMNIS_EDITOR_MAIN_CATEGORY_ICON_PATH_KEY).toString()).toString()));								 
				
				omnisEditorScanTable.setItems((ObservableList<OmnisDirScannerPathsDataModel>) 
						omnisEditorDetailsHM.get(OmnisGlobalConstants.OMNIS_EDITOR_DIR_SCANNER_PATHS_KEY));						
			}
		} catch (Exception genericException) {
			logger.error(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + genericException.getStackTrace()[0].getMethodName(), genericException);
			Dialog.showThrowable(OmnisGlobalConstants.OMNIS_DIALOG_TITLE, OmnisGlobalMessages.OMNISGUICONTROLLEREDITOR_INIT_ERROR, genericException);
		} 
		
	}

	@Override
	public void handle(ActionEvent arg0) {
		logger.debug("Entering OmnisGUIEditorController.handle()");
	}
	
	@FXML protected void omnisEditorTableRecordClick(MouseEvent mouseEvent) {
		logger.debug("Entering OmnisGUIEditorController.omnisEditorTableRecordClick()");
		try{
			if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
	        }
		} catch (Exception genericException) {
			logger.error(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + genericException.getStackTrace()[0].getMethodName(), genericException);
			Dialog.showThrowable(OmnisGlobalConstants.OMNIS_DIALOG_TITLE, OmnisGlobalMessages.OMNISGUIMAINCONTROLLER_TABLE_RCD_DBLCLICK_ERROR, genericException);
		}
	}
		
	
	@FXML protected void omnisEditorAddDirButtonClicked(ActionEvent addDirButtonClicked) {
			logger.debug("Entering OmnisGUIEditorController.omnisEditorAddDirButtonClicked()");
			int numberOfRowsInserted = 0;
			
	    	try {	    		
	    		//Validate if we have all data to insert
	    		OmnisEditorScanTypeDataModel omnisEditorScanTypeSelectedObject = (OmnisEditorScanTypeDataModel) omnisEditorNewDirCB.getValue();
	    		if (omnisEditorNewDirTxt.getText().trim() != null && !omnisEditorNewDirTxt.getText().trim().equalsIgnoreCase("") && 
	    				_H_omnisEditorHiddenSubCatId.getText() != null) {
    				//Validation Passed. Proceed with Insert
	    			omnisEditorDataProcessor = new OmnisEditorDataProcessor();
	    				    		
	    			numberOfRowsInserted = omnisEditorDataProcessor.insertNewDirectoryScannerPath(Integer.parseInt(_H_omnisEditorHiddenSubCatId.getText()), 
	    					omnisEditorScanTypeSelectedObject.getOmnisEditorScanTypeId(),
	    					omnisEditorNewDirTxt.getText().trim(), omnisEditorNewDirExcTxt.getText().trim());
	    			
	    			Dialog.showInfo(OmnisGlobalConstants.OMINIS_DIALOG_GUI_TITLEBAR_TEXT, 
	    					numberOfRowsInserted + OmnisGlobalMessages.OMNISGUICONTROLLEREDIOR_DIR_PATH_INSERT_INFO);
	    			
	    			//Auto Refresh Table
	    			omnisEditorScanTable.setItems((ObservableList<OmnisDirScannerPathsDataModel>) 
	    			omnisEditorDataProcessor.getOmnisEditorDirScanTableData(Boolean.parseBoolean(_H_omnisEditorHiddenIsSubCat.getText()), 
	    					Integer.parseInt(_H_omnisEditorHiddenSubCatId.getText()), 
	    					Integer.parseInt(_H_omnisEditorHiddenMainCat.getText())));
    			} else {
    				Dialog.showError(OmnisGlobalConstants.OMINIS_DIALOG_GUI_TITLEBAR_TEXT, 
	    					OmnisGlobalMessages.OMNISGUICONTROLLEREDIOR_DIR_PATH_INSERT_VALIDATION_ERROR);
    			}
	    	} catch (Exception genericException) {
				logger.error(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + genericException.getStackTrace()[0].getMethodName(), genericException);
				Dialog.showThrowable(OmnisGlobalConstants.OMNIS_DIALOG_TITLE, OmnisGlobalMessages.OMNISGUIMAINCONTROLLER_SEARCH_ERROR, genericException);
			}    			 
	   }
	
	@FXML protected void omnisEditorDeleteDirButtonClicked(ActionEvent deleteDirButtonClicked) {
		logger.debug("Entering OmnisGUIEditorController.omnisEditorDeleteDirButtonClicked()");
	    	try {
	    		omnisEditorDataProcessor = new OmnisEditorDataProcessor();
	    		int numberOfDeletedRows = 0;
	    		OmnisDirScannerPathsDataModel omnisDirScannerPathsDataModel = omnisEditorScanTable.getSelectionModel().getSelectedItem();
	    		if (omnisDirScannerPathsDataModel != null) {
	    			numberOfDeletedRows = omnisEditorDataProcessor.deleteOmnisEditorDirectoryScannerPath(
	    					Integer.parseInt(omnisDirScannerPathsDataModel.getOmnisDirectoryScanId()));
	    		}
	    		
	    		Dialog.showInfo(OmnisGlobalConstants.OMINIS_DIALOG_GUI_TITLEBAR_TEXT, 
	    				numberOfDeletedRows + OmnisGlobalMessages.OMNISGUICONTROLLEREDIOR_DIR_PATH_DELETION_INFO);
	    		
	    		//Auto Refresh Table
	    		omnisEditorScanTable.setItems((ObservableList<OmnisDirScannerPathsDataModel>) 
		    			omnisEditorDataProcessor.getOmnisEditorDirScanTableData(Boolean.parseBoolean(_H_omnisEditorHiddenIsSubCat.getText()), 
		    					Integer.parseInt(_H_omnisEditorHiddenSubCatId.getText()), 
		    					Integer.parseInt(_H_omnisEditorHiddenMainCat.getText())));
	    		
	    	} catch (Exception genericException) {
				logger.error(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + genericException.getStackTrace()[0].getMethodName(), genericException);
				Dialog.showThrowable(OmnisGlobalConstants.OMNIS_DIALOG_TITLE, OmnisGlobalMessages.OMNISGUIMAINCONTROLLER_SEARCH_ERROR, genericException);
			}    			 
	   }
	
    @FXML protected void omnisEditorScanTypeCatCBChanged(ActionEvent omnisEditorScanTypeCatCBChangedEvent) {
    	logger.debug("Entering OmnisGUIEditorController.omnisEditorScanTypeCatCBChanged()");
    	try {
    		omnisEditorNewDirExcTxt.setDisable(false);
    		omnisEditorNewDirExcLbl.setDisable(false);
    		OmnisEditorScanTypeDataModel omnisEditorScanTypeSelectedObject = (OmnisEditorScanTypeDataModel) omnisEditorNewDirCB.getValue();
    		if (omnisEditorScanTypeSelectedObject != null) {
    			if (omnisEditorScanTypeSelectedObject.getOmnisEditorScanTypeId() == 
    					OmnisGlobalConstants.OMNIS_EDITOR_NEW_DIR_SCAN_TYPE_DIR_ONLY_ID) {
    				omnisEditorNewDirExcTxt.setDisable(true);
    				omnisEditorNewDirExcLbl.setDisable(true);
    			}
    		}
    	} catch (Exception genericException) {
			logger.error(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + genericException.getStackTrace()[0].getMethodName(), genericException);
			Dialog.showThrowable(OmnisGlobalConstants.OMNIS_DIALOG_TITLE, OmnisGlobalMessages.OMNISGUIMAINCONTROLLER_CAT_CHANGE_ERROR, genericException);
		}     	
    }
    
    @FXML protected void omnisEditorDirChooserOnClick(MouseEvent omnisEditorDirChooserOnClickEvent) {
    	logger.debug("Entering OmnisGUIEditorController.omnisEditorDirChooserOnClick()");
    	try {
    		//Present File Chooser and set text field with selected data
    		DirectoryChooser omnisEditorDirectoryChooser = new DirectoryChooser();
    		omnisEditorDirectoryChooser.setTitle(OmnisGlobalConstants.OMINIS_EDITOR_GUI_FILE_CHOOSER_TITLEBAR_TEXT);
    		omnisEditorDirectoryChooser.setInitialDirectory(new File (OmnisGlobalConstants.OMNIS_EDITOR_FILE_CHOOSER_DEFAULT_DIRECTORY));
        	File omnisEditorSelectedDirectory = omnisEditorDirectoryChooser.showDialog(OmnisGlobalVariables.OMNIS_EDITOR_GUI_STAGE);
        	omnisEditorNewDirTxt.setText(omnisEditorSelectedDirectory.getAbsolutePath());
    	} catch (Exception genericException) {
			logger.error(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + genericException.getStackTrace()[0].getMethodName(), genericException);
			Dialog.showThrowable(OmnisGlobalConstants.OMNIS_DIALOG_TITLE, OmnisGlobalMessages.OMNISGUIMAINCONTROLLER_CAT_CHANGE_ERROR, genericException);
		}     	
    }
    
    @FXML protected void omnisEditorIconChooserOnClick(MouseEvent omnisEditorIconChooserOnClickEvent) {
    	logger.debug("Entering OmnisGUIEditorController.omnisEditorIconChooserOnClick()");
    	try {
    		Parent omnisIconChooserPageParent = FXMLLoader.load(getClass().getResource(OmnisGlobalConstants.OMINIS_ICON_CHOOSER_FXML));
        	Stage omnisIconChooserPageStage = new Stage();
        	
        	omnisIconChooserPageStage.initModality(Modality.APPLICATION_MODAL);
        	omnisIconChooserPageStage.initStyle(StageStyle.UTILITY);    	    	
        	omnisIconChooserPageStage.getIcons().add(new Image(getClass().getResource(OmnisGlobalConstants.OMNIS_TITLE_BAR_IMAGE).toString())); 
        	omnisIconChooserPageStage.setTitle(OmnisGlobalConstants.OMINIS_ICON_CHOOSER_GUI_TITLEBAR_TEXT);
        	omnisIconChooserPageStage.setScene(new Scene(omnisIconChooserPageParent, OmnisGlobalConstants.OMNIS_ICON_CHOOSER_WINDOW_RES_WIDTH, 
        			OmnisGlobalConstants.OMNIS_ICON_CHOOSER_WINDOW_RES_HEIGHT));
        	omnisIconChooserPageStage.setResizable(false);
        	omnisIconChooserPageStage.showAndWait();
    	} catch (Exception genericException) {
			logger.error(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + genericException.getStackTrace()[0].getMethodName(), genericException);
			Dialog.showThrowable(OmnisGlobalConstants.OMNIS_DIALOG_TITLE, OmnisGlobalMessages.OMNISGUIMAINCONTROLLER_CAT_CHANGE_ERROR, genericException);
		}     	
    }
}
