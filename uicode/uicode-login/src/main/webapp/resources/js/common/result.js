var Result = {};
Result.parseResult = function(data) {
    if(data.code == '00') {
        return data.result;
    }else if(data.code == '01') {
        confirm('error! message: ' + data.message);
    }else {
        confirm('error! unknown code! Please communicate with server');
    }
}