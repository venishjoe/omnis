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
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import net.venishjoe.omnis.staticdata.OmnisGlobalConstants;
import net.venishjoe.omnis.staticdata.OmnisGlobalMessages;
import net.venishjoe.omnis.thirdparty.dialog.Dialog;

import org.apache.log4j.Logger;

public class OmnisGUIAboutController implements Initializable {
	private static Logger logger = Logger.getLogger(OmnisGUIAboutController.class);
	
	@FXML private Label omnisAboutTitleLabel;
	@FXML private Label omnisAboutVersionLabel;
	@FXML private Label omnisAboutBuildLabel;
	@FXML private Label omnisAboutAuthorValueLabel;
	@FXML private Label omnisAboutWebValueLabel;
	@FXML private Label omnisAboutSuppValueLabel;
	@FXML private TextArea omnisAboutCopyrightTextArea;
	@FXML private Hyperlink omnisAboutCopyrightHLink;

	@Override
	public void initialize (URL location, ResourceBundle resources) {
		logger.debug("Entering OmnisAboutMainController.initialize()");
		try {			
			Properties omnisVersionProperties = new Properties();
			omnisVersionProperties.load(getClass().getResourceAsStream(OmnisGlobalConstants.OMNIS_VERSION_PROP_FILE_PATH));
			
			omnisAboutTitleLabel.setText(omnisVersionProperties.getProperty(OmnisGlobalConstants.OMNIS_VERSION_PROP_ABOUT_TITLE));			
			omnisAboutVersionLabel.setText(omnisVersionProperties.getProperty(OmnisGlobalConstants.OMNIS_VERSION_PROP_ABOUT_VERSION));
			omnisAboutBuildLabel.setText(omnisVersionProperties.getProperty(OmnisGlobalConstants.OMNIS_VERSION_PROP_ABOUT_BUILD));
			omnisAboutAuthorValueLabel.setText(omnisVersionProperties.getProperty(OmnisGlobalConstants.OMNIS_VERSION_PROP_ABOUT_AUTHOR));
			omnisAboutWebValueLabel.setText(omnisVersionProperties.getProperty(OmnisGlobalConstants.OMNIS_VERSION_PROP_ABOUT_WEBSITE));
			omnisAboutSuppValueLabel.setText(omnisVersionProperties.getProperty(OmnisGlobalConstants.OMNIS_VERSION_PROP_ABOUT_SUPPORT));			
			omnisAboutCopyrightTextArea.setText(omnisVersionProperties.getProperty(OmnisGlobalConstants.OMNIS_VERSION_PROP_ABOUT_LICTEXT));
			omnisAboutCopyrightHLink.setText(omnisVersionProperties.getProperty(OmnisGlobalConstants.OMNIS_VERSION_PROP_ABOUT_LICURL));
						
		} catch (Exception genericException) {
			logger.error(OmnisGlobalConstants.OMNIS_EXCEPTION_PREFIX + genericException.getStackTrace()[0].getMethodName(), genericException);
			Dialog.showThrowable(OmnisGlobalConstants.OMNIS_DIALOG_TITLE, OmnisGlobalMessages.OMNISGUICONTROLLERABOUT_INIT_ERROR, genericException);
		} 
	}

}
