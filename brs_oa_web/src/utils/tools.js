/* eslint-disable semi */
/* eslint-disable space-infix-ops */
/* eslint-disable no-const-assign */
/* eslint-disable indent */

export function strToDateRange(dateStr) {
    let dateRange = [];
    // eslint-disable-next-line eqeqeq
    if (dateStr != undefined && dateStr != null) {
        dateRange = dateStr.split(',');
    }
    return dateRange;
}

export function dateRangeToStr(dateRange) {
    let dateStr= '';
    // eslint-disable-next-line eqeqeq
    if (dateRange != undefined && dateRange != null) {
        dateStr = dateRange[0]+',' + dateRange[1];
    }
    return dateStr;
}


// 时间格式化
Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1,                 //月份
        "d+": this.getDate(),                    //日
        "h+": this.getHours(),                   //小时
        "m+": this.getMinutes(),                 //分
        "s+": this.getSeconds(),                 //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds()             //毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}   

export function formatTimeToStr(times, pattern) {
    var d = new Date(times).Format("yyyy-MM-dd hh:mm:ss");
    if (pattern) {
        d = new Date(times).Format(pattern);
    }
    return d.toLocaleString();
}