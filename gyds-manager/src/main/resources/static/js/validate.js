/**
 * 输入限制，只可以输入小数点和数字且小数点后只可以输入两个数字
 * 一般用于onkeyup 和 onpaste方法
 * 例：onkeyup="clearNoNum(this)"
 * @param obj
 */
function clearNoNum(obj){
    if(obj.value.substr(0,1)=='.'){
        obj.value = '';
    }
    obj.value = obj.value.replace(/[^\d.]/g,"");
    obj.value = obj.value.replace(/\.{2,}/g,".");
    obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
    obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');
    if(obj.value.indexOf(".")< 0 && obj.value !=""){
        obj.value= parseFloat(obj.value);
    }
}

/**
 * 输入限制，只可以输入数字
 * 一般用于onkeyup 和 onpaste方法
 * 例：onkeyup="clearNoNum(this)"
 * @param obj
 */
function clearNoNum1(obj){
    if(obj.value.substr(0,1)=='.'){
        obj.value = '';
    }
    obj.value = obj.value.replace(/[^\d.]/g,"");
    obj.value = obj.value.replace(/\./g,"");
}

/**
 * 输入限制，只可以输入小数点和数字且小数点后只可以输入一个数字
 * 一般用于onkeyup 和 onpaste方法
 * 例：onkeyup="clearNoNum(this)"
 * @param obj
 */
function clearNoNum2(obj){
    if(obj.value.substr(0,1)=='.'){
        obj.value = '';
    }
    obj.value = obj.value.replace(/[^\d.]/g,"");
    obj.value = obj.value.replace(/\.{2,}/g,".");
    obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
    obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d).*$/,'$1$2.$3');
    if(obj.value.indexOf(".")< 0 && obj.value !=""){
        obj.value= parseFloat(obj.value);
    }
}