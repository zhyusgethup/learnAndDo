var rsa ={};
rsa.pub = null;
rsa.pri = null;
$(function(){
    var url = dev.formatUrl('RSA/getKeysInTwoPair');
    var dataType = 'json';
    var success = function(data) {
        var result = Result.parseResult(data);
        if(result != undefined && result != null) {

        }else {
            return;
        }
    }
});