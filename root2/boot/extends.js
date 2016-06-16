
$.extend($.fn.dataTableExt, {
    errMode: "none"
});

$.extend($.fn.dataTable.defaults, {
    bSort: false,
    bFilter: false,
    processing: true,
    serverSide: true,
    bLengthChange: false,
    iDisplayLength: 20,
    aLengthMenu: [20],
    sPaginationType: "full_numbers",
    sServerMethod: 'POST',
    language: {
        sLengthMenu: "每页显示 _MENU_ 条记录",
        sZeroRecords: "没有检索到数据",
        sInfo: "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
        sInfoEmtpy: "没有数据",
        sProcessing: '正在加载数据...',
        paginate: {
            sFirst: "首页",
            sPrevious: "前一页",
            sNext: "后一页",
            sLast: "尾页"
        }
    },
    preDrawCallback: function (settings) {
        $(settings.nTable).on('preXhr.dt', function (e, settings, data) {
            delete data.columns;
            delete data.search;
        });
        $(settings.nTable).on('xhr.dt', function (e, settings, json) {
            if (json) {
                if (json.rows) json.data = json.rows;
                if (json.total) json.recordsTotal = json.total;
                json.recordsFiltered = json.recordsTotal;
                json.draw = new Date().getTime();
            }
        });
    },
    initComplete: function (settings, json) {
        if (json) {
            if (json.rows) json.data = json.rows;
            if (json.total) json.recordsTotal = json.total;
            json.recordsFiltered = json.recordsTotal;
            json.draw = new Date().getTime();
        }
    }
});

if (!window['console'])
    console = {
        log: function () {
        }
    };

if (!String.prototype.trim) {
    String.prototype.trim = function () {
        return this.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
    };
}

if (!String.prototype.bytelength) {
    String.prototype.bytelength = function () {
        var len = this.length;
        for (var i = 0; i < this.length; i++) {
            if (this.charCodeAt(i) > 127)
                len++;
        }
        return len;
    };
}
if (!String.prototype.cut) {
    String.prototype.cut = function (len) {
        return (this.length <= len) ? this : this.substring(0, len - 3) + "...";
    };
}
if (!String.prototype.fmoney) {
    String.prototype.fmoney = function (n) {
        return parseInt(this).fmoney(n);
    };
}
if (!Number.prototype.fmoney) {
    Number.prototype.fmoney = function (n) {
        var num = this;
        num = String(num.toFixed(n ? n : 0));
        var re = /(-?\d+)(\d{3})/;
        while (re.test(num))
            num = num.replace(re, "$1,$2");
        return num;
    };
}
if (!Date.prototype.format) {
    Date.prototype.format = function (fmt) { //author: meizz   
        var o = {
            "M+": this.getMonth() + 1, //月份   
            "d+": this.getDate(), //日   
            "h+": this.getHours(), //小时   
            "m+": this.getMinutes(), //分   
            "s+": this.getSeconds(), //秒   
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度   
            "S": this.getMilliseconds()             //毫秒   
        };
        if (/(y+)/.test(fmt))
            fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o) {
            if (new RegExp("(" + k + ")").test(fmt)) {
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
            }
        }
        return fmt;
    };
    Date.prototype.toString = function () {
        return this.format(window['LOCALE'] === 'en' ? "MM/dd/yyyy hh:mm:ss" : "yyyy-MM-dd hh:mm:ss");
    };
    Date.prototype.toDayString = function () {
        return this.format(window['LOCALE'] === 'en' ? "MM/dd/yyyy" : "yyyy年MM月dd日");
    };
    Date.prototype.toSimpleDayString = function () {
        return this.format(window['LOCALE'] === 'en' ? "MM/dd/yyyy" : "yyyy-MM-dd");
    };
    Date.prototype.toYMDhmString = function () {
        return this.format(window['LOCALE'] === 'en' ? "MM/dd/yyyy hh:mm" : "yyyy-MM-dd hh:mm");
    };
}

Date.DateFormatter = function (value, type, full) {
    return value ? new Date(value) : "";
};
Date.DayFormatter = function (value, type, full) {
    return value ? new Date(value).toDayString() : "";
};

if (!Date.prototype.zone) {
    Date.prototype.zone = function (zoneOffset) {
        var localOffset = this.getTimezoneOffset() * 60000; //获得当地时间偏移的毫秒数 
        var utc = this.getTime() + localOffset; //utc即GMT时间 
        var result = utc + (3600000 * zoneOffset);
        return result;
    };
}
if (!String.prototype.template) {
    String.prototype.template = function (data) {
        return this.replace(/\$\{([\w\.]*)\}/g, function (str, key) {
            var keys = key.split(".");
            var v = data[keys.shift()];
            if (data._show0 && v === 0)
                return "0";
            if (!v)
                return "";
            if (v instanceof Array && v.length > 0)
                v = v[0];
            for (var i = 0, l = keys.length; v && i < l; i++)
                v = v[keys[i]];
            if (data._show0 && v === 0)
                return "0";
            return (typeof v !== "undefined" && v) ? v : "";
        });
    };
}

(function ($) {
    $.fn.htmlTemplate = function (data) {
        $(this).html($(this).html().template(data));
    };
})(jQuery);

(function ($) {
    $.fn.serializeJson = function (prefix) {
        var result = {};
        var n = prefix ? (prefix + '.') : "";
        var arrayTypes = this["arrayTypes"];
        var array = this.serializeArray();
        var datetimeTypes = this["datetimeTypes"] || [];
        for (var i = 0; i < array.length; i++) {
            if ($.inArray(array[i].name, datetimeTypes) >= 0) {
                if (array[i].value) {
                    array[i].value = "" + new Date(array[i].value.replace(/-/g, "/")).getTime();
                } else {
                    delete array[i].name;
                }
            }
        }
        $(array).each(function () {
            if (!this.name || this.name.indexOf(n) !== 0)
                return;
            var name = this.name.substring(n.length);
            var idot = name.indexOf('.');
            if (idot > 0) {
                var n1 = name.substring(0, idot);
                var n2 = name.substring(idot + 1);
                if (!result[n1])
                    result[n1] = {};
                result[n1][n2] = this.value.trim();
            } else if (result[name]) {
                if ($.isArray(result[name])) {
                    result[name].push(this.value.trim());
                } else {
                    result[name] = [result[name], this.value.trim()];
                }
            } else {
                result[name] = this.value.trim();
            }
        });
        if (!arrayTypes)
            return result;
        for (var i = 0; i < arrayTypes.length; i++) {
            var n = arrayTypes[i];
            if (!result[n]) {
                delete result[n];
            } else if (!$.isArray(result[n])) {
                result[n] = [result[n]];
            }
        }
        return result;
    };

    var privateJsonString = function (object) {
        var type = typeof object;
        try {
            if ('object' === type && Array === object.constructor) {
                type = 'array';
            }
        } catch (e) {
        }
        switch (type) {
            case 'number':
                return object ? ("" + object) : "0";
            case 'string':
                return '"' + object.replace(/(\\|\")/g, "\\$1") + '"';
            case 'object':
                var results = [];
                for (var property in object) {
                    results.push('"' + property + '":' + privateJsonString(object[property]));
                }
                return '{' + results.join(',') + '}';
            case 'array':
                var results = [];
                for (var i = 0; i < object.length; i++) {
                    results.push(object[i] ? privateJsonString(object[i]) : "null");
                }
                return '[' + results.join(',') + ']';
            default :
                return (object === undefined) ? ("" + object) : "null";
        }
    };
    $.objectToString = privateJsonString;
    $.fn.serializeJsonString = function (prefix) {
        return privateJsonString(this.serializeJson(prefix));
    };

})(jQuery);

