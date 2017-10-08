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

package net.venishjoe.omnis.staticdata;

import net.venishjoe.omnis.data.model.OmnisEditorIconChooserDataModel;

public class OmnisGlobalConstants {
	
	//Generic
	public static final int SUCCESS = 0;
	public static final int FAIL = 1;
	
	//FXMLs
	public static final String OMINIS_MAIN_GUI_FXML = "/net/venishjoe/omnis/fxmls/omnis_main.fxml";
	public static final String OMINIS_EDITOR_GUI_FXML = "/net/venishjoe/omnis/fxmls/omnis_editor.fxml";
	public static final String OMINIS_REPORTS_GUI_FXML = "/net/venishjoe/omnis/fxmls/omnis_reports.fxml";
	public static final String OMINIS_ICON_CHOOSER_FXML = "/net/venishjoe/omnis/fxmls/omnis_icon_chooser.fxml";
	public static final String OMINIS_ABOUT_GUI_FXML = "/net/venishjoe/omnis/fxmls/omnis_about.fxml"; 
	
	//Static Strings
	public static final String OMINIS_MAIN_GUI_TITLEBAR_TEXT = "Omnis";
	public static final String OMINIS_EDITOR_GUI_TITLEBAR_TEXT = "Omnis Editor";
	public static final String OMINIS_REPORTS_GUI_TITLEBAR_TEXT = "Omnis Reports";
	public static final String OMINIS_ICON_CHOOSER_GUI_TITLEBAR_TEXT = "Omnis Icon Chooser";
	public static final String OMINIS_DIALOG_GUI_TITLEBAR_TEXT = "Omnis";
	public static final String OMINIS_EDITOR_GUI_FILE_CHOOSER_TITLEBAR_TEXT = "Omnis File Chooser";
	
	//Images
	public static final String OMNIS_TITLE_BAR_IMAGE = "/net/venishjoe/omnis/resources/images/core/omnis_24x24.png";
	public static final String OMNIS_EDITOR_FILE_CHOOSER_IMAGE = "/net/venishjoe/omnis/resources/images/assets/omnis_documents_32x32.png";
	
	//GUI Constants
	public static final int OMNIS_MAIN_WINDOW_RES_WIDTH = 1164;
	public static final int OMNIS_MAIN_WINDOW_RES_HEIGHT = 785;
	public static final int OMNIS_MAIN_ACCORDION_IMAGE_SIZE = 35;
	public static final int OMNIS_EDITOR_WINDOW_RES_WIDTH = 701;
	public static final int OMNIS_EDITOR_WINDOW_RES_HEIGHT = 594;
	public static final int OMNIS_EDITOR_TREE_IMAGE_SIZE = 20;
	public static final int OMNIS_EDITOR_IMAGE_SIZE = 35;
	public static final int OMNIS_ABOUT_WINDOW_RES_WIDTH = 530;
	public static final int OMNIS_ABOUT_WINDOW_RES_HEIGHT = 296;
	public static final int OMNIS_REPORTS_WINDOW_RES_WIDTH = 787;
	public static final int OMNIS_REPORTS_WINDOW_RES_HEIGHT = 574;
	public static final int OMNIS_ICON_CHOOSER_WINDOW_RES_WIDTH = 391;
	public static final int OMNIS_ICON_CHOOSER_WINDOW_RES_HEIGHT = 368;
	public static final int OMNIS_EDITOR_FILE_CHOOSER_IMAGE_SIZE = 20;
	public static final double OMNIS_IMAGE_OPACITY_DISABLED = 0.25;
	public static final double OMNIS_IMAGE_OPACITY_ENABLED = 1;
	
	//Database Constants
	public static final String OMNIS_DB_DRIVER = "org.h2.Driver";
	public static final String OMNIS_DB_CONNECTION_STRING = "jdbc:h2:data/omnis;IFEXISTS=TRUE";
	public static final int OMNIS_DATABASE_SQL_TIMEOUT = 30;
	
	public static final String OMNIS_DATABASE_USER_ID = "sysadmin";
	public static final String OMNIS_DATABASE_PASSWORD = "init";
	
	//Logger
	public static final String OMNIS_LOG4J_PROPERTIES_RELATIVE_PATH = "/conf/log4j/";
	public static final String OMNIS_LOG4J_PROPERTIES = "log4j.xml";
	public static final String OMNIS_EXCEPTION_PREFIX = "Exception in method: ";
	
	//Dialog
	public static final String OMNIS_DIALOG_TITLE = "Omnis";	
	
	//Database Table/Column Definitions
	public static final String OMNIS_MAIN_CATEGORY__OMNIS_MAIN_CAT_ID = "OMNIS_MAIN_CAT_ID";	
	public static final String OMNIS_MAIN_CATEGORY__OMNIS_MAIN_CAT_NAME = "OMNIS_MAIN_CAT_NAME";
	public static final String OMNIS_MAIN_CATEGORY__OMNIS_MAIN_ICON_PATH = "OMNIS_MAIN_ICON_PATH";	
	public static final String OMNIS_SUB_CATEGORY__OMNIS_SUB_CAT_NAME = "OMNIS_SUB_CAT_NAME";	
	public static final String OMNIS_SUB_CATEGORY__OMNIS_SUB_CAT_ID = "OMNIS_SUB_CAT_ID";
	public static final String OMNIS_SUB_CATEGORY__OMNIS_MAIN_CAT_ID = "OMNIS_MAIN_CAT_ID";	
	public static final String OMNIS_DIR_SCANNER__OMNIS_DIR_SCAN_ID = "OMNIS_DIR_SCAN_ID";
	public static final String OMNIS_DIR_SCANNER__OMNIS_SUB_CAT_ID = "OMNIS_SUB_CAT_ID";
	public static final String OMNIS_DIR_SCANNER__OMNIS_SCAN_TYPE_ID = "OMNIS_SCAN_TYPE_ID";
	public static final String OMNIS_DIR_SCANNER__OMNIS_SCAN_PATH = "OMNIS_SCAN_PATH";
	
	//Data Models	
	public static final String OMNIS_FILESIZE_FILTER_DM_EQ_TXT = "Equals";
	public static final int OMNIS_FILESIZE_FILTER_DM_EQ_VAL = 0;
	public static final String OMNIS_FILESIZE_FILTER_DM_GE_TXT = "Greater Than";
	public static final int OMNIS_FILESIZE_FILTER_DM_GE_VAL = 1;
	public static final String OMNIS_FILESIZE_FILTER_DM_LE_TXT = "Less Than";
	public static final int OMNIS_FILESIZE_FILTER_DM_LE_VAL = 2;
	public static final int OMNIS_EDITOR_NEW_DIR_SCAN_TYPE_FILE_ONLY_ID = 100;
	public static final String OMNIS_EDITOR_NEW_DIR_SCAN_TYPE_FILE_ONLY_NAME = "Files";
	public static final int OMNIS_EDITOR_NEW_DIR_SCAN_TYPE_DIR_ONLY_ID = 200;
	public static final String OMNIS_EDITOR_NEW_DIR_SCAN_TYPE_DIR_ONLY_NAME = "Directories";
	public static final int OMNIS_EDITOR_NEW_DIR_SCAN_TYPE_BOTH_ID = 300;
	public static final String OMNIS_EDITOR_NEW_DIR_SCAN_TYPE_BOTH_NAME = "Both";
	
	public static final String OMNIS_SUB_CAT_FILTER_DM_ALL_TXT = "All";
	public static final int OMNIS_SUB_CAT_FILTER_DM_ALL_VAL = 1337;
	public static final String OMNIS_MAIN_CAT_FILTER_DM_ALL_TXT = "All";
	public static final int OMNIS_MAIN_CAT_FILTER_DM_ALL_VAL = 1337;
	
	//Properties
	public static final String OMNIS_VERSION_PROP_FILE_PATH = "/conf/prop/version.properties";
	
	public static final String OMNIS_VERSION_PROP_ABOUT_TITLE = "omnis.gui.about.title";
	public static final String OMNIS_VERSION_PROP_ABOUT_VERSION = "omnis.gui.about.version";
	public static final String OMNIS_VERSION_PROP_ABOUT_BUILD = "omnis.gui.about.build";
	public static final String OMNIS_VERSION_PROP_ABOUT_AUTHOR = "omnis.gui.about.author";
	public static final String OMNIS_VERSION_PROP_ABOUT_WEBSITE = "omnis.gui.about.website";
	public static final String OMNIS_VERSION_PROP_ABOUT_SUPPORT = "omnis.gui.about.support";
	public static final String OMNIS_VERSION_PROP_ABOUT_LICTEXT = "omnis.gui.about.lictext";
	public static final String OMNIS_VERSION_PROP_ABOUT_LICURL = "omnis.gui.about.licurl";
	
	//Table Data
	public static final String OMNIS_EDITOR_TABLEVIEW_COL_I_NAME = "Scan Type";
	public static final String OMNIS_EDITOR_TABLEVIEW_COL_I_PROP = "omnisDirectoryScanTypeName";
	public static final int OMNIS_EDITOR_TABLEVIEW_COL_I_WIDTH = 100;
	public static final String OMNIS_EDITOR_TABLEVIEW_COL_II_NAME = "Scan Path";
	public static final String OMNIS_EDITOR_TABLEVIEW_COL_II_PROP = "omnisDirectoryScanPath";
	public static final int OMNIS_EDITOR_TABLEVIEW_COL_II_WIDTH = 335;
	
	//Hash Map Keys
	public static final String OMNIS_EDITOR_MAIN_CATEGORY_NAME_KEY = "key_OmnisMainCategoryName";
	public static final String OMNIS_EDITOR_MAIN_CATEGORY_ID_KEY = "key_OmnisMainCategoryId";
	public static final String OMNIS_EDITOR_MAIN_CATEGORY_ICON_PATH_KEY = "key_OmnisMainCategoryIconPath";
	public static final String OMNIS_EDITOR_SUB_CATEGORY_NAME_KEY = "key_OmnisSubCategoryName";
	public static final String OMNIS_EDITOR_SUB_CATEGORY_ID_KEY = "key_OmnisSubCategoryId";
	public static final String OMNIS_EDITOR_DIR_SCANNER_PATHS_KEY = "key_OmnisDirScannerPaths";
	
	//Icon Configuration	
	public static final OmnisEditorIconChooserDataModel OMNIS_EDITOR_ICON_CHOOSER_DATA[] = {
		new OmnisEditorIconChooserDataModel (1, "Books Icon", "/net/venishjoe/omnis/resources/images/category/omnis_books_128x128.png"),
		new OmnisEditorIconChooserDataModel (2, "Documents Icon", "/net/venishjoe/omnis/resources/images/category/omnis_documents_128x128.png"),
		new OmnisEditorIconChooserDataModel (3, "Games Icon", "/net/venishjoe/omnis/resources/images/category/omnis_games_128x128.png"),
		new OmnisEditorIconChooserDataModel (4, "Movies Icon", "/net/venishjoe/omnis/resources/images/category/omnis_movies_128x128.png"),
		new OmnisEditorIconChooserDataModel (5, "Music Icon", "/net/venishjoe/omnis/resources/images/category/omnis_music_96x96.png"),
		new OmnisEditorIconChooserDataModel (6, "Software Icon", "/net/venishjoe/omnis/resources/images/category/omnis_software_128x128.png"),
		new OmnisEditorIconChooserDataModel (7, "Videos Icon", "/net/venishjoe/omnis/resources/images/category/omnis_videos_128x128.png"),
		new OmnisEditorIconChooserDataModel (8, "Video Songs Icon", "/net/venishjoe/omnis/resources/images/category/omnis_videosongs_128x128.png")
	};		
	
	//Others
	public static final String OMNIS_EDITOR_ICON_PATH_URL_TOKENIZER_TOKEN = "//";
	public static final String OMNIS_EDITOR_FILE_CHOOSER_DEFAULT_DIRECTORY = "C:/";
	
}
