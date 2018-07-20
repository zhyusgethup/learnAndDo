/**
 * 
 */
 var dev = {};
 dev.ip = "localhost:8001";
 dev.ctx = dev.ip + "/uicode-login/";
 dev.postfix = '.do';

var rsaPath = dev.ctx + '/resources/js/common/security/rsa.js';
var jqueryPath = "../../plugins/jquery/jquery.min.js";


dev.formatUrl = function(url) {
    return dev.ctx + url + dev.postfix;
}