var numberOfSprit = getNumberOfSprit();
var stringQianzhui = getQianzhui();
var pathName = "uicode-login";
var devPath = "resources/js/common/dev.js";
loadCssAndJS();

//加载通用CSS和JS
function loadCssAndJS() {
    if(numberOfSprit > 0) {
        importJS(devPath);
        importJS(rsaPath);
        importJS(jqueryPath);
    }
}

function importJS(JSfilePath) {
    var stringJSPath = "<script language='javascript' src='" + stringQianzhui + JSfilePath + "'></script>";
    document.write(stringJSPath);
}

function importCCS(CSSfilePath) {
    var stringCSSPath = "<link rel='stylesheet' href='" + stringQianzhui + CSSfilePath + "'>";
    document.write(stringCSSPath);
}

//返回项目前缀
function getQianzhui() {
    var qianzhui = "";
    for(i = 0; i < numberOfSprit - 1; i++) {
        qianzhui = qianzhui.concat("../");
    }
    return qianzhui;
}

//返回字符串中斜杠个数
function getNumberOfSprit() {
    var curPath = window.document.location.href;

    var pos = curPath.indexOf(pathName);
    //	console.log("pos:" + pos);
    if(pos < 0) {
        pos = curPath.indexOf("B2B");
        if(pos < 0) {
            pos = curPath.indexOf("HBuilder");
        }
    }
    var path2 = curPath.substr(pos);
    console.log("path：" + path2);
    var reg = /\//ig;
    var numberOfSprit = path2.match(reg).length;
    return numberOfSprit;
};

//--------------------------------------

function isPhoneNumber(stringPhone) {
    console.log("Phone:" + stringPhone);
    if(!(/^1[3456789]\d{9}$/.test(stringPhone))) {
        console.log("手机号码有误，请重填");
        return false;
    }
    return true;
}

function formatPhoneNumber(stringPhone) {
    var value = stringPhone;
    value = value.replace(/\s*/g, "");
    var stringFormat = "";
    for(var i = 0; i < value.length; i++) {
        if(i == 3 || i == 7) {
            stringFormat = stringFormat.concat("-" + value.charAt(i));
        } else {
            stringFormat = stringFormat.concat(value.charAt(i));
        }
    }
    return stringFormat;
}

//每隔2位加冒号
function formatMACAddress(stringValue) {
    stringValue = stringValue.replace(/\s/g, ':').replace(/(\d{2})(?=\d)/g, "$1 ");
    return stringValue;
}

//每隔4位加空格
function formatValue(stringValue) {
    stringValue = stringValue.replace(/\s/g, '').replace(/(\d{4})(?=\d)/g, "$1 ");
    return stringValue;
}
