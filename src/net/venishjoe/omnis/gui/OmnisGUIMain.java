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

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import net.venishjoe.omnis.commons.OmnisGlobalInit;
import net.venishjoe.omnis.staticdata.OmnisGlobalConstants;
import net.venishjoe.omnis.staticdata.OmnisGlobalMessages;
import net.venishjoe.omnis.thirdparty.dialog.Dialog;

import org.apache.log4j.Logger;

public class OmnisGUIMain extends Application {
	private static Logger logger = Logger.getLogger(OmnisGUIMain.class);
	
	public static void main(String[] omnisGUIMainArgs) {		
		try {
			//Initialization
			OmnisGlobalInit.omnisGlobalInitHook();
		} catch (Exception genericException) {
			logger.fatal(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + genericException.getStackTrace()[0].getMethodName(), genericException);			
		}
		
		try {
			//Launch the application
			launch(OmnisGUIMain.class, omnisGUIMainArgs);
		} catch (Exception genericException) {
			logger.fatal(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + genericException.getStackTrace()[0].getMethodName(), genericException);
			Dialog.showThrowable(OmnisGlobalConstants.OMNIS_DIALOG_TITLE, OmnisGlobalMessages.OMNISGUIMAIN_APPLICATION_LOAD_ERROR, genericException);
		}
        
    }
    
    @Override
    public void start(Stage primaryStage) {
    	logger.debug("Entering OmnisGUIMain.start()");
    	
    	try {    		
    		//Show application main window
    		Parent omnisRootParent = FXMLLoader.load(getClass().getResource(OmnisGlobalConstants.OMINIS_MAIN_GUI_FXML));
        	
        	primaryStage.initStyle(StageStyle.DECORATED);
        	primaryStage.getIcons().add(new Image(getClass().getResource(OmnisGlobalConstants.OMNIS_TITLE_BAR_IMAGE).toString())); 
            primaryStage.setTitle(OmnisGlobalConstants.OMINIS_MAIN_GUI_TITLEBAR_TEXT);
            primaryStage.setScene(new Scene(omnisRootParent, OmnisGlobalConstants.OMNIS_MAIN_WINDOW_RES_WIDTH, 
            		OmnisGlobalConstants.OMNIS_MAIN_WINDOW_RES_HEIGHT));
            primaryStage.setResizable(false);
            primaryStage.show();
            
            //Application Shutdown
            primaryStage.setOnHiding(new EventHandler<WindowEvent>() {            	 
                public void handle(WindowEvent event) {
                	logger.debug("Application Shutdown detected. Cleaning up resources.");

                	//Cleanup before shutdown
                	OmnisGlobalInit.omnisGlobalShutdownHook();
                }
            });
    	} catch (Exception genericException) {
			logger.fatal(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + genericException.getStackTrace()[0].getMethodName(), genericException);
			Dialog.showThrowable(OmnisGlobalConstants.OMNIS_DIALOG_TITLE, OmnisGlobalMessages.OMNISGUIMAIN_APPLICATION_LOAD_ERROR, genericException);
		}    	                    
    }
}