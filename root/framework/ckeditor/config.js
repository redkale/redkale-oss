/**
 * @license Copyright (c) 2003-2015, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function (config) {

    config.height = 580; //高度
    config.toolbar = 'Basic';
    config.jqueryOverrideVal = true;
    config.dialog_noConfirmCancel = true;
    config.removePlugins = 'about,a11yhelp,flash,iframe,language';
    
    var pathName = window.document.location.pathname;
    //获取带"/"的项目名，如：/uimcardprj
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    //var h = 'https://' + ((("" + window.document.location).indexOf("/cms.oa.com") >= 0) ? "www.3wyc.com" : "lab.3wyc.com");
    config.filebrowserImageUploadUrl = '/pipes/upload/resx'; //固定路径
};
