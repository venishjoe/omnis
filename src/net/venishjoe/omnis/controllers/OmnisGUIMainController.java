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

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.venishjoe.omnis.data.model.OmnisMainTableViewDataModel;
import net.venishjoe.omnis.data.model.OmnisFileSizeOptionsDataModel;
import net.venishjoe.omnis.data.model.OmnisMainCategoryDataModel;
import net.venishjoe.omnis.data.model.OmnisMainTableColumnsDataModel;
import net.venishjoe.omnis.data.model.OmnisSubCategoryDataModel;
import net.venishjoe.omnis.data.process.OmnisMainDataProcessor;
import net.venishjoe.omnis.gui.OmnisGUIHelper;
import net.venishjoe.omnis.staticdata.OmnisGlobalConstants;
import net.venishjoe.omnis.staticdata.OmnisGlobalMessages;
import net.venishjoe.omnis.staticdata.OmnisGlobalVariables;
import net.venishjoe.omnis.thirdparty.dialog.Dialog;

import org.apache.log4j.Logger;

public class OmnisGUIMainController implements Initializable {
	private static Logger logger = Logger.getLogger(OmnisGUIMainController.class);
	
	@FXML private Accordion omnisDataAccordion;
	@FXML private TableView<OmnisMainTableViewDataModel> omnisDataTableView;
	@FXML private TextField omnisSearchFreeFormTextField;
	@FXML private ComboBox<OmnisMainCategoryDataModel> omnisSearchMainCatCB;
	@FXML private ComboBox<OmnisSubCategoryDataModel> omnisSearchSubCatCB;
	@FXML private ComboBox<OmnisFileSizeOptionsDataModel> omnisSearchFileSizeCB;	
	 
	@FXML private Button startSearchButton;
	@FXML private HBox omnisStatusBarHBox;
	 
	private OmnisMainDataProcessor omnisMainDataProcessor;
	private OmnisGUIHelper omnisGUIHelper;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		logger.debug("Entering OmnisGUIMainController.initialize()");
		try {
			//Initializing Classes
			omnisMainDataProcessor = new OmnisMainDataProcessor();
			omnisGUIHelper = new OmnisGUIHelper();
			
			//Set Status Bar
			//TODO
			omnisStatusBarHBox.getChildren().add(new Text(""));
			omnisStatusBarHBox.setAlignment(Pos.CENTER_RIGHT);
			
			//Load Accordion
			TitledPane[] omnisMainCatTPArray = (TitledPane[]) omnisMainDataProcessor.createAccordion();						
			omnisDataAccordion.getPanes().addAll(omnisMainCatTPArray);  
			omnisDataAccordion.setExpandedPane(omnisMainCatTPArray[0]);															
			
			//Load Main Table
			OmnisMainTableColumnsDataModel omnisMainTableColumnsDataModel;
			ArrayList<OmnisMainTableColumnsDataModel> aatb = (ArrayList<OmnisMainTableColumnsDataModel>) omnisMainDataProcessor.getOmnisMainTableColums();
			for(int j=0;j<aatb.size();j++){
				omnisMainTableColumnsDataModel = (OmnisMainTableColumnsDataModel) aatb.get(j);
				omnisDataTableView.getColumns().add(omnisGUIHelper.createOmnisMainTableColumn(omnisMainTableColumnsDataModel.getOmnisMainTableColumnName(), 
						omnisMainTableColumnsDataModel.getOmnisMainTableColumnProperty(), 
						omnisMainTableColumnsDataModel.getOmnisMainTableColumnMinWidth()));
			}
			
			omnisDataTableView.setEditable(true);        
			omnisDataTableView.setItems(omnisMainDataProcessor.getMainTableData());
			
			
			//Load Search Combo Boxes
			omnisSearchMainCatCB.setItems(omnisMainDataProcessor.getMainCategoryCBData());
			omnisSearchMainCatCB.getSelectionModel().selectFirst();
			 
			omnisSearchSubCatCB.setItems(omnisMainDataProcessor.getSubCategoryCBData());
			omnisSearchSubCatCB.getSelectionModel().selectFirst();
			 		 
			omnisSearchFileSizeCB.setItems(omnisMainDataProcessor.getFileSizeOptionsCBData());
			omnisSearchFileSizeCB.getSelectionModel().selectFirst();
		} catch (Exception genericException) {
			logger.error(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + genericException.getStackTrace()[0].getMethodName(), genericException);
			Dialog.showThrowable(OmnisGlobalConstants.OMNIS_DIALOG_TITLE, OmnisGlobalMessages.OMNISGUIMAINCONTROLLER_APPLICATION_INIT_ERROR, genericException);
		}				
	}
	
	@FXML protected void omnisDataTableRecordClick(MouseEvent mouseEvent) {
		logger.debug("Entering OmnisGUIMainController.omnisDataTableRecordClick()");
		try{
			if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
	            if(mouseEvent.getClickCount() == 2){
	                logger.info("Double clicked");
	            }
	        }
		} catch (Exception genericException) {
			logger.error(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + genericException.getStackTrace()[0].getMethodName(), genericException);
			Dialog.showThrowable(OmnisGlobalConstants.OMNIS_DIALOG_TITLE, OmnisGlobalMessages.OMNISGUIMAINCONTROLLER_TABLE_RCD_DBLCLICK_ERROR, genericException);
		}
	}

    
    @FXML protected void omnisScannerButtonClicked(ActionEvent searchButtonClicked) {
    	logger.debug("Entering OmnisGUIMainController.omnisScannerButtonClicked()");
    	try {
    		//Initializing Classes
        	omnisMainDataProcessor = new OmnisMainDataProcessor(); 
        	        	
    	} catch (Exception genericException) {
			logger.error(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + genericException.getStackTrace()[0].getMethodName(), genericException);
			Dialog.showThrowable(OmnisGlobalConstants.OMNIS_DIALOG_TITLE, OmnisGlobalMessages.OMNISGUIMAINCONTROLLER_SEARCH_ERROR, genericException);
		}    			 
   }
    
    @FXML protected void omnisEditorButtonClicked(ActionEvent searchButtonClicked) {
    	logger.debug("Entering OmnisGUIMainController.omnisEditorButtonClicked()");
    	try {
    		//Initializing Classes
        	omnisMainDataProcessor = new OmnisMainDataProcessor(); 
        	
        	Parent omnisEditorPageParent = FXMLLoader.load(getClass().getResource(OmnisGlobalConstants.OMINIS_EDITOR_GUI_FXML));
        	Stage omnisEditorPageStage = new Stage();
        	
        	omnisEditorPageStage.initModality(Modality.APPLICATION_MODAL);
        	omnisEditorPageStage.initStyle(StageStyle.UTILITY);    	    	
        	omnisEditorPageStage.getIcons().add(new Image(getClass().getResource(OmnisGlobalConstants.OMNIS_TITLE_BAR_IMAGE).toString())); 
        	omnisEditorPageStage.setTitle(OmnisGlobalConstants.OMINIS_EDITOR_GUI_TITLEBAR_TEXT);
        	omnisEditorPageStage.setScene(new Scene(omnisEditorPageParent, OmnisGlobalConstants.OMNIS_EDITOR_WINDOW_RES_WIDTH, 
        			OmnisGlobalConstants.OMNIS_EDITOR_WINDOW_RES_HEIGHT));
        	omnisEditorPageStage.setResizable(false);
        	OmnisGlobalVariables.OMNIS_EDITOR_GUI_STAGE = omnisEditorPageStage;
        	omnisEditorPageStage.showAndWait();
        	
    	} catch (Exception genericException) {
			logger.error(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + genericException.getStackTrace()[0].getMethodName(), genericException);
			Dialog.showThrowable(OmnisGlobalConstants.OMNIS_DIALOG_TITLE, OmnisGlobalMessages.OMNISGUIMAINCONTROLLER_SEARCH_ERROR, genericException);
		}    			 
   }    
    
    @FXML protected void omnisSearchButtonClicked(ActionEvent searchButtonClicked) {
    	logger.debug("Entering OmnisGUIMainController.omnisSearchButtonClicked()");
    	try {
    		//Initializing Classes
        	omnisMainDataProcessor = new OmnisMainDataProcessor(); 
        	
        	omnisDataTableView.setItems(omnisMainDataProcessor.searchOmnisData());
    	} catch (Exception genericException) {
			logger.error(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + genericException.getStackTrace()[0].getMethodName(), genericException);
			Dialog.showThrowable(OmnisGlobalConstants.OMNIS_DIALOG_TITLE, OmnisGlobalMessages.OMNISGUIMAINCONTROLLER_SEARCH_ERROR, genericException);
		}    			 
   }    
    
    @FXML protected void omnisMainCategoryCBChanged(ActionEvent omnisMainCategoryChangedEvent) {
    	logger.debug("Entering OmnisGUIMainController.omnisMainCategoryCBChanged()");
    	try {
    		//Initializing Classes
        	omnisMainDataProcessor = new OmnisMainDataProcessor(); 
        	
    		OmnisMainCategoryDataModel omnisMainCategorySelectedObject = (OmnisMainCategoryDataModel) omnisSearchMainCatCB.getValue();
    		if (omnisMainCategorySelectedObject != null) {
    			omnisSearchSubCatCB.setItems(omnisMainDataProcessor.getSubCategoryCBData(
    					omnisMainCategorySelectedObject.getOmnisMainCategoryId()));
				omnisSearchSubCatCB.getSelectionModel().selectFirst();
    		}
    	} catch (Exception genericException) {
			logger.error(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + genericException.getStackTrace()[0].getMethodName(), genericException);
			Dialog.showThrowable(OmnisGlobalConstants.OMNIS_DIALOG_TITLE, OmnisGlobalMessages.OMNISGUIMAINCONTROLLER_CAT_CHANGE_ERROR, genericException);
		}     	
    }
    
    @FXML protected void omnisSubCategoryCBChanged(ActionEvent omnisSubCategoryChangedEvent) {
    	logger.debug("Entering OmnisGUIMainController.omnisSubCategoryCBChanged()");
    	try {
    		@SuppressWarnings("unused")
			OmnisSubCategoryDataModel omnisSubCategorySelectedObject = (OmnisSubCategoryDataModel) omnisSearchSubCatCB.getValue();
    	} catch (Exception genericException) {
			logger.error(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + genericException.getStackTrace()[0].getMethodName(), genericException);
			Dialog.showThrowable(OmnisGlobalConstants.OMNIS_DIALOG_TITLE, OmnisGlobalMessages.OMNISGUIMAINCONTROLLER_CAT_CHANGE_ERROR, genericException);
		} 
    }
    
    @FXML protected void omnisSearchFileSizeTypeChanged(ActionEvent omnisSearchFileSizeTypeChangedEvent) {
    	logger.debug("Entering OmnisGUIMainController.omnisSearchFileSizeTypeChanged()");
    	try {
    		@SuppressWarnings("unused")
			OmnisFileSizeOptionsDataModel omnisFileSizeOptionSelectedObject = (OmnisFileSizeOptionsDataModel) omnisSearchFileSizeCB.getValue();
    	} catch (Exception genericException) {
			logger.error(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + genericException.getStackTrace()[0].getMethodName(), genericException);
			Dialog.showThrowable(OmnisGlobalConstants.OMNIS_DIALOG_TITLE, OmnisGlobalMessages.OMNISGUIMAINCONTROLLER_CAT_CHANGE_ERROR, genericException);
		}
    }
    
    @FXML protected void omnisReportsButtonClicked(ActionEvent reportsButtonClicked) {
    	logger.debug("Entering OmnisGUIMainController.omnisReportsButtonClicked()");
    	try {
    		Parent omnisReportsPageParent = FXMLLoader.load(getClass().getResource(OmnisGlobalConstants.OMINIS_REPORTS_GUI_FXML));
        	Stage omnisReportsPageStage = new Stage();
        	
        	omnisReportsPageStage.initModality(Modality.APPLICATION_MODAL);
        	omnisReportsPageStage.initStyle(StageStyle.UTILITY);    	    	
        	omnisReportsPageStage.getIcons().add(new Image(getClass().getResource(OmnisGlobalConstants.OMNIS_TITLE_BAR_IMAGE).toString())); 
        	omnisReportsPageStage.setTitle(OmnisGlobalConstants.OMINIS_REPORTS_GUI_TITLEBAR_TEXT);
        	omnisReportsPageStage.setScene(new Scene(omnisReportsPageParent, OmnisGlobalConstants.OMNIS_REPORTS_WINDOW_RES_WIDTH, 
        			OmnisGlobalConstants.OMNIS_REPORTS_WINDOW_RES_HEIGHT));
        	omnisReportsPageStage.setResizable(false);
        	OmnisGlobalVariables.OMNIS_REPORTS_GUI_STAGE = omnisReportsPageStage;
        	omnisReportsPageStage.showAndWait();
    	} catch (Exception genericException) {
			logger.error(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + genericException.getStackTrace()[0].getMethodName(), genericException);
			Dialog.showThrowable(OmnisGlobalConstants.OMNIS_DIALOG_TITLE, OmnisGlobalMessages.OMNISGUIMAINCONTROLLER_REP_PG_LOAD_ERROR, genericException);
		}    	
    }
    
    
    @FXML protected void omnisMenuHelpAboutClicked(ActionEvent omnisMenuHelpAboutClicked)  {
    	logger.debug("Entering OmnisGUIMainController.omnisMenuHelpAboutClicked()");
    	try {
    		
    		Parent omnisAboutHelpParent = FXMLLoader.load(getClass().getResource(OmnisGlobalConstants.OMINIS_ABOUT_GUI_FXML));
        	Stage omnisAboutHelpStage = new Stage();
        	
        	omnisAboutHelpStage.initModality(Modality.APPLICATION_MODAL);
        	omnisAboutHelpStage.initStyle(StageStyle.UTILITY);    	    	
        	omnisAboutHelpStage.getIcons().add(new Image(getClass().getResource(OmnisGlobalConstants.OMNIS_TITLE_BAR_IMAGE).toString())); 
        	omnisAboutHelpStage.setTitle("Omnis About");
        	omnisAboutHelpStage.setScene(new Scene(omnisAboutHelpParent, OmnisGlobalConstants.OMNIS_ABOUT_WINDOW_RES_WIDTH, 
        			OmnisGlobalConstants.OMNIS_ABOUT_WINDOW_RES_HEIGHT));
        	omnisAboutHelpStage.setResizable(false);
        	omnisAboutHelpStage.showAndWait();
        	
    	} catch (Exception genericException) {
			logger.error(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + genericException.getStackTrace()[0].getMethodName(), genericException);
			Dialog.showThrowable(OmnisGlobalConstants.OMNIS_DIALOG_TITLE, OmnisGlobalMessages.OMNISGUIMAINCONTROLLER_ABT_PG_LOAD_ERROR, genericException);
		}     	
    }
}
