/// <reference path="jquery.js" />
/**
* 提供常用工具。
*/

var utils = {
    //返回一个函数，用于jQuery fn 扩展。注意init为构造函数。
    fn: function (prototype) {
        return function (method) {
            if (prototype[method]) {
                return prototype[method].apply(this, Array.prototype.slice.call(arguments, 1));
            } else if ((typeof method === 'object' || !method) && prototype.init) {
                return prototype.init.apply(this, arguments);
            }
        };
    },
    //加入收藏
    addFav: function (url, title) {
        try {
            external.addFavorite(url, title);
        } catch (e) {
            try {
                sidebar.addPanel(title, url, '');
            } catch (e) {
                alert('您使用的是非IE浏览器，请使用Ctrl+D进行收藏。');
            }
        }
    },
    //设为首页
    setHome: function(o, url) {
        try {
            o.style.behavior = 'url(#default#homepage)';
            o.setHomePage(url);
        }
        catch (e) {
            alert('您使用的是非IE浏览器，请手动设置。');
        }
    },
    //cookie操作
    cookie: function (name, value, options) {
        if (typeof value != 'undefined') {
            options = options || {};
            if (value == null) {
                value = '';
                options.expires = -1;
            }
            var expires = '';
            if (options.expires && (typeof options.expires == 'number' || options.expires.toUTCString)) {
                var date;
                if (typeof options.expires == 'number') {
                    date = new Date();
                    date.setTime(date.getTime() + (options.expires * 24 * 60 * 60 * 1000));
                } else {
                    date = options.expires;
                }
                expires = '; expires=' + date.toUTCString();
            }
            var path = options.path ? '; path=' + (options.path) : '';
            var domain = options.domain ? '; domain=' + (options.domain) : '';
            document.cookie = [name, '=', encodeURIComponent(value), expires, path, domain].join('');
        } else {
            var cookieValue = null;
            if (document.cookie && document.cookie != '') {
                var cookies = document.cookie.split(';');
                for (var i = 0; i < cookies.length; i++) {
                    var ck = $.trim(cookies[i]);
                    if (ck.substring(0, name.length + 1) == (name + '=')) {
                        cookieValue = decodeURIComponent(ck.substring(name.length + 1));
                        break;
                    }
                };
            }
            return cookieValue;
        }
    },

    //获取或设置 url 字串中查询参数的值。
    urlParam: function (url, name, value) {
        if (typeof value == 'undefined') {
            var reg = new RegExp('(?:\\\?|&)' + name + '=(?:[^&]*)', 'ig');
            var val = url.match(reg);
            if (val == null) return '';
            var rep = new RegExp('(?:\\\?|&)' + name + '=', 'i');
            for (var i = 0; i < val.length; i++)
                val[i] = val[i].replace(rep, '');
            return unescape(val.join());
        } else {
            var reg = new RegExp(name + '=(?:[^&]*)&*', 'ig');
            url = url.replace(reg, '');
            if (url.indexOf('?') == -1)
                url += '?';

            var idx = url.indexOf('?') + 1, param = name + '=' + encodeURIComponent(value) + (idx == url.length ? '' : '&');
            return url.substr(0, idx) + param + url.substr(idx);
        }
    },
    //转换一个 Json 对象为字符串。
    toJsonString: function (obj) {
        if (obj === null)
            return '';
        var arr = [];
        var getJson = function (obj) {
            if (obj == null) {
                return 'null';
            }
            switch (obj.constructor) {
                case Array:
                case Object:
                    return utils.toJsonString(obj);
                case Number:
                    return obj;
                case String:
                    return '\"' + obj.replace(/\\/g, '\\\\').replace(/\"/g, '\\"').replace(/\//g, '\\\/') + '\"';
                case Boolean:
                    return obj ? "true" : "false";
                case Date:
                    return '"\\\/Date(' + (obj - new Date(1970, 0, 1) - 28800000) + ')\\\/"';
            };
        };

        for (var item in obj) {
            var vl = obj[item];
            if (/^\d+$/.test(item)) {
                if (vl.constructor && vl.constructor == Array) {
                    arr.push(utils.toJsonString(vl));
                } else {
                    arr.push(getJson(vl));
                }
            }
            else arr.push('\"' + item + '\":' + getJson(vl));
        };
        if (obj.constructor == Array)
            return '[' + arr.join() + ']';
        if (obj.constructor == Object)
            return '{' + arr.join() + '}';

        return arr.join();
    },

    //转换当前字串为 Date 型，支持.net中序列化出来的/Date(\d+)/格式。
    toDate: function (str, format) {
        if (str == null || str == '')
            return null;

        if (str.constructor == Date)
            return str;

        var jsonReg = /\/Date\((\d+)\)\//;
        if (jsonReg.test(str)) {
            return new Date(parseInt(str.replace(jsonReg, '$1'), 10));
        };
        format = format || 'yyyy-MM-dd';
        var dt = new Date(1970, 0, 1);
        var reps = {
            'y{4}': function (val) { dt.setFullYear(parseInt(val, 10)); },
            'M{1,2}': function (val) { dt.setMonth(parseInt(val, 10) - 1); },
            'd{1,2}': function (val) { dt.setDate(parseInt(val, 10)); },
            'H{1,2}': function (val) { dt.setHours(parseInt(val, 10)); },
            'm{1,2}': function (val) { dt.setMinutes(parseInt(val, 10)); },
            's{1,2}': function (val) { dt.setSeconds(parseInt(val, 10)); }
        };

        var count = 0;
        for (var k in reps) {
            var find = (new RegExp(k)).exec(format);
            if (find != null) {                
                var s = jQuery.trim(str.substring(find.index, find.index + find.toString().length));
                if (s && !isNaN(s)) {
                    reps[k](s);
                    count++;
                }
            }
        };
        return count == 0 ? null : dt;
    },

    //转换当前日期为指定 format 的字串。
    dateToString: function (date, format) {
        format = format || 'yyyy-MM-dd';
        var dt = date;
        var result = format;
        var replaceValue = function (start, end, val) {
            var len = end - start;
            val = val.toString();
            while (val.length < len)
                val = '0' + val;
            result = result.substring(0, start) + val + result.substring(end);
        };
        var reps = {
            'y{4}': function (start, end) { replaceValue(start, end, dt.getFullYear()); },
            'M{1,2}': function (start, end) { replaceValue(start, end, dt.getMonth() + 1); },
            'd{1,2}': function (start, end) { replaceValue(start, end, dt.getDate()); },
            'H{1,2}': function (start, end) { replaceValue(start, end, dt.getHours()); },
            'm{1,2}': function (start, end) { replaceValue(start, end, dt.getMinutes()); },
            's{1,2}': function (start, end) { replaceValue(start, end, dt.getSeconds()); }
        };

        for (var k in reps) {
            var find = (new RegExp(k)).exec(format);
            if (find != null)
                reps[k](find.index, find.index + find.toString().length);
        };

        return result;
    }
};

//金融相关。
var finance = {
    //转换金额字符显示为纯数字。(去,号)
    toMoneyNumber: function (val) {
        if (typeof (val) == 'number')
            return val;

        var ret = 0;
        if (val) ret = parseFloat(val.toString().replace(/,/g, ''));
        if (isNaN(ret)) ret = 0;
        return ret;
    },
    //转换金额显示字串。
    toMoneyString: function (val) {
        if (isNaN(val)) return '0.00';
        val = val.toString().replace(/^0+(?=[0-9])/g, '');
        var dindex = val.indexOf('.');
        var dleft = (dindex == -1 ? val : val.substring(0, dindex)).replace(/(\d{1,3})(?=(?:\d{3})+(?!\d))/g, '$1,');
        val = dleft + '.' + (dindex == -1 ? '00' : val.substr(dindex + 1, 2));
        return val;
    },
    //四舍五入，原生js只支持整数的四舍五入，不能保留小数点。num为需保留的小数位
    roundx: function (x, num) {
        return Math.round(x * Math.pow(10, num)) / Math.pow(10, num);
    },
    //将年化利率转换为字符串, type:d日化, m月化。
    yearRateTo: function (val, type) {
        var types = {
            'm': function (v) { return (isNaN(val) ? 0 : finance.roundx(v / 12 * 10, 3)) + '‰' },
            'd': function (v) { return (isNaN(val) ? 0 : finance.roundx(v / 365 * 100, 3)) + '‱' }
        };
        if (types[type])
            return types[type](val);

        return val.toString();
    },
    //将数字转换成中文缩写。如50000.01 TO 5万 
    toChineseUnit: function (val) {

        if (/^(\d+)(\.\d+)?$/.test(val)) {
            var dindex = val.indexOf('.'),
                arrNum = [12, 8, 4],
                arrUnit = [" 万亿", " 亿", " 万"],
                leng, decimal;
                val = dindex == -1 ? val : val.substring(0, dindex);
                for (var i = 0; i < arrNum.length; i++) {
                    if (val.length > arrNum[i]) {
                        leng = val.length - arrNum[i];
                        decimal = val.substring(leng, leng + 2);
                        val = decimal=='00'? val.substring(0, leng) + arrUnit[i]: val.substring(0, leng) + '.' + decimal + arrUnit[i];
                        break;
                    } else {
                        val = val;
                    }
                }
        }
        return val;
    }
};

//ui base
(function ($) {
    window.ui = {
        browser: {
            ie: !!navigator.userAgent.match(/MSIE ([0-9]{1,}[\.0-9]{0,})/),
            ie6: 'undefined' == typeof (document.body.style.maxHeight),
            mobile: /AppleWebKit.*Mobile/i.test(navigator.userAgent) || /MIDP|SymbianOS|NOKIA|SAMSUNG|LG|NEC|TCL|Alcatel|BIRD|DBTEL|Dopod|PHILIPS|HAIER|LENOVO|MOT-|Nokia|SonyEricsson|SIE-|Amoi|ZTE/.test(navigator.userAgent)
        },

        keyCode: { ESC: 27, LEFT: 37, RIGHT: 39, ENTER: 13 },

        //滚动，支持指字指动到的数字及元互
        scroll: function (v, callback) {
            if (/^-?\d+(px)?$/.test(v))
                v = Math.max(parseInt(v), 0);
            else
                v = $(v).offset().top;
            $('html,body').animate({ scrollTop: v }, 200, 'swing', callback);
        }
    };

    //获取元素可视大小。
    $.fn.clientSize = function () {
        var el = this[0];
        var isDoc = el === window || el === document || el === document.body;
        return {
            width: isDoc ? document.documentElement.clientWidth : el.clientWidth,
            height: isDoc ? document.documentElement.clientHeight : el.clientHeight
        };
    };

    //获取元素滚动条 left,top 信息
    $.fn.scrollInfo = function () {
        var dom = this[0];
        var isbody = (dom === document || dom === document.body);
        return {
            left: isbody ? (document.documentElement.scrollLeft || document.body.scrollLeft) : dom.scrollLeft,
            top: isbody ? (document.documentElement.scrollTop || document.body.scrollTop) : dom.scrollTop
        };
    };

    //获取元素最大尺寸
    $.fn.fullSize = function () {
        var dom = this[0], w, h;

        if (dom === document || dom === document.body || dom == document.documentElement) {
            w = Math.max(document.documentElement.scrollWidth, document.body.scrollWidth, document.documentElement.clientWidth);
            h = Math.max(document.documentElement.scrollHeight, document.body.scrollHeight, document.documentElement.clientHeight);
        } else {
            w = dom.offsetWidth;
            h = dom.offsetHeight;
        }

        return { width: w, height: h };
    };

    //元素居中。
    $.fn.center = function (container) {
        if (this.length == 0) return this;

        var ct = $(container || document.body);
        var isbody = ct[0] === document || ct[0] === document.body;
        var ctSroll = ct.scrollInfo();
        var ctSize = isbody ? ct.clientSize() : ct.fullSize();

        var that = this;
        return this.each(function (i, n) {
            var obj = $(n);
            var size = obj.fullSize();
            var scroll = (obj.css('position') == 'fixed') ? { left: 0, top: 0 } : ctSroll;
            obj.css({
                left: (ctSize.width - size.width) / 2 + scroll.left,
                top: (ctSize.height - size.height) / 2 + scroll.top
            });
        });
    };

    //产生阴影效果。
    $.fn.shadow = function () {
        return this.each(function (i, n) {
            var obj = $(n);
            $(obj).css({
                '-webkit-box-shadow': '1px 2px 5px #666',
                '-moz-box-shadow': '1px 2px 5px #666',
                'filter': obj.css('filter') + ' ' + 'progid:DXImageTransform.Microsoft.Shadow(color=#aaaaaa,direction=145,strength=3)',
                'zoom': '1'
            });
        });
    };

    //透明处理。
    $.fn.opacity = function (value) {
        return this.each(function (i, n) {
            var obj = $(n);
            $(obj).css({
                'opacity': value,
                '-moz-opacity': value,
                'filter': obj.css('filter') + ' ' + 'alpha(opacity=' + (value * 100) + ')'
            });
        });
    };

    //对当前对象用iframe做背景，用于解决ie6下div遮不住select的问题。
    $.fn.bgiframe = (ui.browser.ie6) ? function () {
        var html = '<iframe class="bgiframe" style="display:block;position:absolute;z-index:-1;_filter:alpha(opacity=\'0\');_top:expression(((parseInt(this.parentNode.currentStyle.borderTopWidth)||0)*-1)+\'px\');_left:expression(((parseInt(this.parentNode.currentStyle.borderLeftWidth)||0)*-1)+\'px\');_width:expression(this.parentNode.offsetWidth+\'px\');_height:expression(this.parentNode.offsetHeight+\'px\');" frameborder="0" tabindex="-1" src=""></iframe>';
        return this.each(function () {
            if ($(this).children('iframe.bgiframe').length == 0) {
                this.insertBefore(document.createElement(html), this.firstChild);
            }
        });
    } : function () {
        return this;
    };
})(jQuery);

//form
(function ($) {
    //表单操作
    form = {
        //禁用表单
        disable: function (fm) {
            $(fm).find(":input").each(function (i, n) {
                var tmp = $(n);
                tmp.attr('data-oldDisabled', tmp.attr('disabled') ? 'true' : 'false');
                tmp.attr('disabled', 'disabled');
            });
        },
        //启用表单
        enable: function (fm) {
            $(fm).find(":input").each(function (i, n) {
                var tmp = $(n);
                if (tmp.attr('data-oldDisabled') == 'false') tmp.removeAttr('disabled');
            });
        },
        //ajax提交
        ajax: function (fm, dataType, callback) {
            if (typeof dataType == 'function')
                callback = dataType;
            var f = $(fm);

            $.ajax({
                url: f.attr('action') || location.href,
                type: f.attr('method') || 'get',
                data: f.serialize(),
                cache: false,
                async: false,
                dataType: dataType,
                beforeSend: function () {
                    form.disable(fm);
                },
                success: function (res) {
                    form.enable(fm);
                    if (callback) callback(res);
                },
                error: function (res) {
                    form.enable(fm);
                    alert('远程服务器错误，请稍候再试！');
                }
            });
        },

        //验证绑定，并不执行提交验证。
        validBind: function (fm, fns, focus) {
            if (fns.constructor == Function)
                fns = { errorFn: fns };

            var that = this;
            $(fm).find(':input').filter('[data-vtype]').each(function (i, n) {
                $(n).focus(function () {
                    if (fns.focusFn) fns.focusFn.call(n);
                }).blur(function () {                    
                    if (that._checkInput(n, function (msg) {
                        if (fns.errorFn) fns.errorFn.call(n, msg);
                    }) && fns.okFn) fns.okFn.call(n);
                });
            });

        },

        //表单验证
        valid: function (fm, fn, focus, one) {
            var that = this;
            fm = $(fm);
            fn = fn || alert;
            var pass = true, first = null;            

            fm.find(':input').filter('[data-vtype]').each(function (i, n) {
                var res = that._checkInput(n, function (msg) {
                    fn.call(n, msg);
                    if (focus && first == null) {
                        n.focus();
                        first = n;
                    }
                });
                if (!res) {
                    pass = false;
                    if (one) return pass;
                }
                //return res; 批量验证
            });
            return pass;
        }
        ,
        _checkInput: function (n, fn) {
            with (n) {
                if (getAttribute('data-vmust') == 'false' && value == '') return true;
                var tp = getAttribute('data-vtype');
                if (tp) {
                    if (tp == 'group') {    //验证组时，需把验证项放在最后一个input
                        var groups = document.getElementsByName(getAttribute("name"));
                        if (n != groups[groups.length - 1])
                            return true;
                    }

                    var s = tp.split('|');
                    for (var n = 0; n < s.length; n++) {
                        if (!(eval(this.validType[s[n]]))) {
                            var m = (getAttribute('data-vmsg') || '请输入正确数据').split('|');
                            fn(m[n] || m[0]);
                            return false;
                        }
                    };
                }
                return true;
            };
        },
        validType: {
            must: 'jQuery.trim(value) != "" && jQuery.trim(value) != (getAttribute("data-vempty") || "")',
            number: '/^-?\\d+$/.test(value)',
            money: '/^-?[0-9]{1,}(\\,[0-9]{3})*(\\.\\d+)?$/.test(value)',
            email: '/^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$/.test(value)',
            url: '/^http:\\/\\/[A-Za-z0-9]+\\.[A-Za-z0-9]+[\\/=\\?%\\-&_~`@[\\]\\\':+!]*([^<>\\"\\"])*$/.test(value)',
            zip: '/^[1-9]\\d{5}$/.test(value)',
            phone: '/^[1]\\d{10}$/.test(value)',
            qq: '/^\\d{5,15}$/.test(value)',
            chinese: '/^[\\u0391-\\uFFE5]$/.test(value)',
            length: 'value.length >= (parseInt(getAttribute("data-vmin"),10)||0) && value.length <= (parseInt(getAttribute("data-vmax"),10)||9999999)',
            range: '(value||"0").replace(/,/g,"") >= (parseFloat(getAttribute("data-vmin"),10)||0) && (value||"0").replace(/,/g,"") <= (parseFloat(getAttribute("data-vmax"),10)||0)',
            custom: '(new RegExp(getAttribute("data-vreg"), "i")).test(value)',
            date: '(new RegExp(getAttribute("data-vfmt") || "^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)(?:\\\\s[012]\\\\d:\\\\d{1,2}(?::\\\\d{1,2})?)?$")).test(value)',
            ajax: 'this.validType._ajaxValid(getAttribute("data-vurl") || location.href, value)',
            same: 'value == document.getElementById(getAttribute("data-vref")).value',
            group: 'this.validType._checked(getAttribute("name"), getAttribute("data-vmin"), getAttribute("data-vmax"))',
            fun: 'window[getAttribute("data-vfun")](value)',
            _ajaxValid: function (url, val) {
                var res = true;
                $.ajax({
                    type: 'get',
                    url: url,
                    async: false,
                    cache: false,
                    data: { data: val },
                    error: function () {
                        alert('远程请求发生错误');
                        res = false;
                    },
                    success: function (result) {
                        res = result == 'ok';
                    }
                });
                return res;
            },
            _checked: function (name, min, max) {
                var groups = document.getElementsByName(name);
                var hasChecked = 0;
                min = min || 1;
                max = max || groups.length;
                for (var i = groups.length - 1; i >= 0; i--)
                    if (groups[i].checked) hasChecked++;
                return min <= hasChecked && hasChecked <= max;
            }
        },
        //对Placeholder不支持浏览器的效果
        fixPlaceholder: function () {
            var blurFn = function () {
                var me = $(this), val = me.val(), place = me.attr('placeholder');
                if (val == '' || val == place) {
                    me.css('color', '#a9a9a9');
                    me.val(place);
                }
            };

            if (true != ('placeholder' in document.createElement('input'))) {
                $('input[placeholder]').focus(function () {
                    var me = $(this);
                    if (me.val() == me.attr('placeholder')) {
                        me.val('');
                        me.css('color', '');
                    }
                }).blur(blurFn).each(function (i, n) {
                    blurFn.call(n);
                });

                $('form').submit(function () {
                    $('input[placeholder]', this).each(function (i, n) {
                        n = $(n);
                        if (n.val() == n.attr('placeholder')) n.val('');
                    });
                });
            };
        }
    };
})(jQuery);

//tabs
(function ($) {
    var renderAjaxTab = function (selector) {        
        if (selector && selector.charAt(0) == '#') {
            var dom = $(selector), ajax = dom.attr('data-content-ajax');            
            if (ajax && ajax != '' && $.trim(dom.html()) == '') {
                $.ajax({
                    type: 'get',
                    url: ajax,
                    cache: false,
                    success: function (res) {
                        dom.html(res);
                    }
                });
            }
        }
    };

    $.fn.tabs = function (ops) {
        ops = $.extend({
            tabSelector: '>dt a',
            selectedClass: 'on',
            event: 'click',
            swich: null
        }, ops);

        return this.each(function (i, n) {
            n = $(n);
            var tabs = n.find(ops.tabSelector).bind(ops.event, function () {
                var active = $(this).addClass(ops.selectedClass).attr('href');
                if (active && (active.charAt(0) == '#' || active.charAt(0) == '.')) {
                    $(active).show();
                    renderAjaxTab(active);

                    tabs.not(this).each(function (j, k) {                        
                        $($(k).removeClass(ops.selectedClass).attr('href')).hide();
                    });

                    if (ops.swich) ops.swich(active);
                    return false;
                }
            });

            if (location.hash) {
                tabs.filter('[href="' + location.hash + '"]').trigger(ops.event);
            };

            renderAjaxTab(tabs.filter('.' + ops.selectedClass).attr('href'));
        });
    };
})(jQuery);

//遮罩
(function ($) {
    $.fn.mask = utils.fn({
        init: function (ops) {
            ops = $.extend({
                bgColor: '#000',
                opacity: 0.2,
                zIndex: 1,
                anima: true
            }, ops);

            return this.each(function (i, n) {
                var isbody = n === document || n === document.body;
                n = $(n);
                var div = $('<div class="ui-mask" style="position:absolute;z-index:' + ops.zIndex + ';background-color:' + ops.bgColor + ';display:none;"></div>').opacity(ops.opacity).bgiframe();

                var initSize = function () {
                    var clientSize = n.clientSize(), fullSize = n.fullSize();
                    var size = {
                        width: Math.max(clientSize.width, fullSize.width),
                        height: Math.max(clientSize.height, fullSize.height)
                    };
                    var pos = (n.css('position') == 'relative') ? { left: 0, top: 0 } : n.position();

                    //要减去边框宽度
                    var leftBorder = parseInt(n.css('border-left-width')) || 0;
                    var rightBorder = parseInt(n.css('border-right-width')) || 0;
                    var topBorder = parseInt(n.css('border-top-width')) || 0;
                    var bottomBorder = parseInt(n.css('border-bottom-width')) || 0;

                    //定位
                    div.css({
                        left: pos.left + leftBorder,
                        top: pos.top + topBorder,
                        right: 0,
                        width: isbody ? '100%' : size.width - (leftBorder + rightBorder),
                        height: size.height - (topBorder + bottomBorder)
                    });
                };

                initSize();
                n.append(div);
                if (ops.anima) div.fadeIn('fast'); else div.show();

                //当遮罩为整个页面时，改变窗口大小需改变遮罩大小
                if (isbody) {
                    n[0].mask_resize = initSize;
                    $(window).resize(n[0].mask_resize);
                }
            });
        },

        destroy: function () {
            return this.each(function (i, n) {
                if (n.mask_resize) $(window).unbind('resize', n.mask_resize);
                $(n).find('.ui-mask').remove();
            });
        }
    });
})(jQuery);

//无限级select
(function ($) {
    $.fn.cascade = utils.fn({
        init: function (ops) {
            ops = $.extend({
                data: null,
                emptyTip: '---',
                names: [],
                values: [],
                ismust: false
            }, ops);

            //初始化下一个select
            var initNext = function (dom) {
                var option = $(':selected', dom);
                var nextData = option.data('data-cascade-data');
                if (nextData) {
                    var d = initSelect(dom, nextData);
                    if (ops.ismust) {
                        $(dom).removeAttr('data-vtype').removeAttr('data-vmsg');
                        d.attr('data-vtype', 'must').attr('data-vmsg', '请选择');
                    }
                    return d;
                }
                else {
                    $(dom).nextAll('select').remove();
                    if (ops.ismust) $(dom).attr('data-vtype', 'must').attr('data-vmsg', '请选择');
                    return null;
                }
            };

            //初始化select
            var initSelect = function (dom, data) {
                if (data && data.length) {
                    var select = $('<select></select>');
                    //深度
                    var level = $(dom).data('data-cascade-level');
                    level = typeof level != 'undefined' ? level + 1 : 0;
                    select.data('data-cascade-level', level);

                    //给控件初始化name, id
                    if (ops.names.length > level) {
                        select.attr('id', ops.names[level]);
                        select.attr('name', ops.names[level]);
                    } else if (ops.names.length) {
                        select.attr('id', ops.names[0] + level);
                        select.attr('name', ops.names[0] + level);
                    }

                    //空白提示
                    if (ops.emptyTip) select.append('<option value="">' + ops.emptyTip + '</option>');

                    //option数据
                    for (var i = 0; i < data.length; i++) {
                        var option = $('<option value="' + data[i][0] + '">' + data[i][1] + '</option>');
                        if (data[i].length > 2) option.data('data-cascade-data', data[i][2]);
                        select.append(option);
                    }

                    //更新时初始化下一个select
                    select.change(function () {
                        initNext(this);
                    });

                    if (dom.tagName == 'SELECT') {
                        dom = $(dom);
                        dom.nextAll('select').remove();
                        dom.after(select);
                    }
                    else
                        $(dom).empty().append(select);

                    return select;
                }
            };

            return this.each(function (i, n) {
                var select = initSelect(n, ops.data);
                if (ops.ismust) select.attr('data-vtype', 'must').attr('data-vmsg', '请选择');

                //初始化值
                for (var i = 0; i < ops.values.length; i++) {
                    if (select) {
                        select.val(ops.values[i]);
                        select = initNext(select[0]);
                    }
                }
            });
        }
    });
})(jQuery);

//dialog
(function ($) {
    $.fn.dialog = utils.fn({
        init: function (ops) {
            ops = $.extend({
                width: 500,
                height: 300,
                title: '',
                modal: true,
                zIndex: 9999,
                url: '',
                html: '',
                el: null,
                closeShow:true
            }, ops);

            return this.each(function (i, n) {
                var w = ops.width, h = ops.height;
                if (ops.width < 1 && ops.width > 0)
                    w = $(n).clientSize().width * ops.width;
                if (ops.height != 'auto' && ops.height < 1 && ops.height > 0)
                    h = $(n).clientSize().height * ops.height;
                var closeBth = ops.closeShow ? '<a class="ui-dialogClose iconfont" title="关闭" href="javascript:">&#xe605;</a>' : '';
                var dom = $('<div class="ui-dialog">' +
                            (ops.title ?  '<div class="ui-dialogTitle" unselectable="on" onselectstart="return false;">' + closeBth + '<h1>' + ops.title + '</h1>' + '</div>': closeBth) +
                        '<div class="ui-dialogBody"></div>' +
                     '</div>').css({
                    'width': w + 'px',
                    'z-index': ops.zIndex
                }).appendTo(n);
                n.dialogOps = ops;

                //设置内容高度
                var body = dom.find('.ui-dialogBody');
                var header = dom.find('.ui-dialogTitle').height();

                //内容
                if (ops.url) {
                    var frame = $('<iframe frameborder="0" width="100%" scrolling="auto" marginwidth="0" marginheight="0" height="' + (h - header) + '" src=""></iframe>');
                    body.append(frame);
                    frame.attr('src', ops.url);
                }
                else if (ops.html)
                    body.html(ops.html);
                else if (ops.el)
                    body.append(ops.el);

                //遮罩
                if (ops.modal) $(n).mask({ zIndex: ops.zIndex - 1 });

                //高度
                //dom.height(ops.height == 'auto' ? body[0].scrollHeight + header : h).center();
                if (ops.height != 'auto') dom.height(h)
                dom.center();

                //事件
                dom.find('.ui-dialogClose').click(function () {
                    $(n).dialog('destroy');
                });
                n.dialog_esc = function (event) {
                    if (event.keyCode == ui.keyCode.ESC) {
                        $(n).dialog('destroy');
                    }
                };
                n.dialog_resize = function () {
                    if (ops.width < 1 && ops.width > 0) {
                        var rw = $(n).clientSize().width * ops.width;
                        dom.width(rw);
                    }
                    if (ops.height != 'auto' && ops.height < 1 && ops.height > 0) {
                        var rh = $(n).clientSize().height * ops.height;
                        dom.height(rh);
                        if (ops.url) dom.find('iframe').height(rh - header);
                    }

                    dom.center();
                };

                $(document).bind('keyup', n.dialog_esc);
                $(window).bind('resize', n.dialog_resize);

                var btn = body.find('input:first');
                if (btn.length)
                    btn.focus();
                else
                    body.find('iframe').focus();

                n.dialog_dom = dom;
            });
        },
        destroy: function () {
            return this.each(function (i, n) {
                $(document).unbind('keyup', n.dialog_esc);
                $(window).unbind('resize', n.dialog_resize);
                $(n).find('.ui-dialog').remove();
                if (n.dialogOps.modal)
                    $(n).mask('destroy');
            });
        }
    });
})(jQuery);

//slider
(function ($) {
    var dispalyNum = function (val, flag) {
        var returns = val;
        if (flag != "1") {
            returns = (val / flag).toFixed(String(flag).length - 1);
        }
        return returns;
    };

    var handNull = function (obj) {
        var num = parseFloat(obj.val());
        if (isNaN(num)) {
            num = 0;
        };
        return num;
    };

    $.fn.slider = utils.fn({
        init: function (opt) {
            this.slider('formatNum', opt);
            return this.each(function (i, n) {
                dom = '<div class="ui-slider" unselectable="on" onselectstart="return false;">' +
                    '<a href="javascript:void(0);" unselectable="on" onselectstart="return false;" class="slider-cutback iconfont fs20">&#xe606;</a>' +  //JS-iconfont:空心左箭头
                    '<div class="slider-background" unselectable="on" onselectstart="return false;">' +
                '<div class="slider-fillingbar" unselectable="on" onselectstart="return false;"></div>' +
                '<div class="slider-changebtn iconfont" unselectable="on" onselectstart="return false;"><i></i></div>' +
                '</div>' +
                '<a href="javascript:void(0);" unselectable="on" onselectstart="return false;" class="slider-increase iconfont fs20">&#xe607;</a>' + //JS-iconfont:空心左箭头
                '</div>';
                $(n).after(dom).slider('play');

                function changNum(val, step) {
                    var _temp = parseInt(val);
                    if (_temp % step != 0) {
                        if (_temp < n.slider_data.max && _temp > n.slider_data.min) {
                            while (_temp % step != 0) {
                                _temp += 1;
                            }
                        }
                    }
                    if (_temp > n.slider_data.max) {
                        _temp = n.slider_data.max;
                    }
                    if (_temp < n.slider_data.min) {
                        _temp = n.slider_data.min;
                    }

                    return _temp;
                };

                function changeWidht(num, win) {
                    _num = (n.slider_data.max - n.slider_data.min) <= 0 || (1 - ((n.slider_data.max - num) / (n.slider_data.max - n.slider_data.min))) * 100 > 100 ?
                        100 : (1 - ((n.slider_data.max - num) / (n.slider_data.max - n.slider_data.min))) * 100;
                    
                    if (win) {
                        _num = num;
                    }
                    if (_num > 100) {
                        _num = 100;
                    } else if (_num < 0) {
                        _num = 0;
                    }
                    $(n).next().find('.slider-changebtn').css('left', _num + "%");
                    $(n).next().find('.slider-fillingbar').css('width', _num + "%");
                };

                function setpSize(num, alg) {
                    if (num != null) {
                        if (alg == "+") num += n.slider_data.step;
                        if (alg == "-") num -= n.slider_data.step;
                    }
                    return changNum(num, n.slider_data.step);
                }

                $(n).on({
                    keyup: function () {
                        var _num = Math.round(handNull($(n)) * n.slider_data.flagNum);
                        changeWidht(_num);
                    },
                    keydown: function (e) {
                        var _num = Math.round(handNull($(n)) * n.slider_data.flagNum);
                        if ((e.keyCode == 38 || e.keyCode == 39)) {
                            _num = setpSize(_num, "+");
                            $(this).val(dispalyNum(_num, n.slider_data.flagNum));
                        }
                        if ((e.keyCode == 40 || e.keyCode == 37)) {
                            _num = setpSize(_num, "-");
                            $(this).val(dispalyNum(_num, n.slider_data.flagNum));
                        }
                        changeWidht(_num);
                    },
                    blur: function () {
                        var _num = changNum(handNull($(n)) * n.slider_data.flagNum, n.slider_data.step);
                        $(this).val(dispalyNum(_num, n.slider_data.flagNum));
                        changeWidht(_num);
                    }
                });
                //绑定增加按钮事件
                $(n).next().find('.slider-increase').on('click', function () {
                    var _num = Math.round(handNull($(n)) * n.slider_data.flagNum);
                    _num = setpSize(_num, "+");
                    $(n).val(dispalyNum(_num, n.slider_data.flagNum)).trigger('blur');
                    changeWidht(_num);
                });
                $(n).next().find('.slider-cutback').on('click', function () {
                    var _num = handNull($(n)) * n.slider_data.flagNum;
                    _num = setpSize(_num, "-");
                    $(n).val(dispalyNum(_num, n.slider_data.flagNum)).trigger('blur');
                    changeWidht(_num);
                });
                $(n).next().find('.slider-background').on('mousedown', function () {
                    var thisbg = $(this),
                        thisWidht = thisbg.width();
                    var moveSlider = function (e) {
                        var eleft = thisbg.offset().left;
                        $("body").addClass("slider-noneselect");
                        $(n).focus();
                        var moveNum = parseFloat(((e.pageX - eleft) / thisWidht) * 100);
                        changeWidht(moveNum, true);
                        if (moveNum >= 100) {
                            moveNum = n.slider_data.max;
                        } else if (moveNum <= 0) {
                            moveNum = n.slider_data.min;
                        } else {
                            moveNum = ((n.slider_data.max - n.slider_data.min) * (moveNum / 100)) + n.slider_data.min;
                        }
                        $(n).val(dispalyNum(changNum(moveNum, n.slider_data.step), n.slider_data.flagNum));
                    }
                    $(document).bind("mousemove", moveSlider).one('mouseup', function () {
                        $("body").removeClass("slider-noneselect");
                        $(n).blur();
                        $(document).unbind('mousemove', moveSlider);
                    });
                });

            })
        },

        play: function () {
            return this.each(function (i, n) {
                var _width = n.slider_data.width,
                    _starterWidth = (n.slider_data.max - n.slider_data.min) <= 0 || (1 - ((n.slider_data.max - n.slider_data.starter) / (n.slider_data.max - n.slider_data.min))) * 100 > 100 ? 100 : (1 - ((n.slider_data.max - n.slider_data.starter) / (n.slider_data.max - n.slider_data.min))) * 100,
                    $this = $(n);
                $this.next().css('width', _width + 'px');
                $this.next().find('.slider-background').css('width', (_width - 100) + 'px');
                $this.next().find('.slider-fillingbar').css('width', _starterWidth + '%');
                $this.next().find('.slider-changebtn').css('left', _starterWidth + '%');
                $this.val(dispalyNum(n.slider_data.starter, n.slider_data.flagNum)).attr({
                    "maxlength": n.slider_data.maxLength,
                    "size": n.slider_data.maxLength
                });
            })
        },

        formatNum: function (json) {
            var options = {
                min: 0,
                max: 100,
                step: 1,
                starter: 0,
                width: 350,
                flagNum: 1
            }
            if (json) {
                var strLength = 0,
                    count = 0;
                $.each(json, function (i, num) {
                    if (i != "width") {
                        var munLength = String(num).indexOf(".") != -1 ? String(num).slice(String(num).indexOf(".")).length : 1;
                        var countLength = String(num).length;
                        strLength = munLength > strLength ? munLength : strLength;
                        count = countLength > count ? countLength : count;
                    }
                });
                var tempArr = new Array(strLength);
                options.flagNum = parseInt("1" + tempArr.join("0"));
                options.maxLength = count + 1;
            }
            return this.each(function (i, n) {
                if (!json) return n.slider_data = options;
                var sliderData = n.slider_data ? n.slider_data : options;
                sliderData.flagNum = options.flagNum;
                sliderData.maxLength = options.maxLength;
                $.each(json, function (i, num) {
                    sliderData[i] = num * sliderData.flagNum;
                    if (i == "width") {
                        sliderData[i] = num;
                    }
                });
                if (sliderData.min > sliderData.max) {
                    sliderData.min = sliderData.max;
                }
                if (sliderData.step < 0) {
                    sliderData.step = Math.abs(sliderData.step);
                }

                if (sliderData.step > Math.abs(sliderData.max)) {
                    sliderData.step = Math.abs(sliderData.max);
                }

                if (sliderData.starter > sliderData.max) {
                    sliderData.starter = sliderData.max;
                }

                if (sliderData.starter < sliderData.min) {
                    sliderData.starter = sliderData.min;
                }
                if (sliderData.starter % sliderData.step != 0) {
                    if (sliderData.starter != sliderData.max && sliderData.starter != sliderData.max) {
                        while (sliderData.starter % sliderData.step != 0) {
                            sliderData.starter += 1;
                        }
                    }
                }

                n.slider_data = sliderData;
            })
        },

        revise: function (opt) {
            this.slider('formatNum', opt);
            return this.each(function (i, n) {
                $(n).slider('play');
            })
        }
    });
})(jQuery);
//date
(function ($) {
    $.fn.calendar = utils.fn({
        init: function (ops) {
            ops = $.extend({
                format: 'yyyy-MM-dd',
                minDate: null,
                maxDate: null,
                onChange: null
            }, ops);

            return this.each(function (i, n) {
                var dom = n.calendar_dom = $('<div class="ui-calendar">' +
                                '<div class="calendar-title">' +
                                    '<a href="javascript:" class="calendar-prevmonth"><i class="iconfont fs20">&#xe60a;</i></a>' +  //JS-iconfont:实心左箭头
                                        '<a href="javascript:" class="ui-calendarYear"></a>' +
                                    '<a href="javascript:" class="ui-calendarMonth"></a>' +
                                    '<a href="javascript:" class="calendar-nextmonth"><i class="iconfont fs20">&#xe608;</i></a>' +  //JS-iconfont:实心右箭头
                                
                                '</div>' +
                                '<div class="calendar-body">' +
                                    '<div class="calendar-week">' +
                                        '<span>一</span><span>二</span><span>三</span><span>四</span><span>五</span><span>六</span><span>日</span>' +
                                    '</div>' +
                                    '<div class="calendar-day"></div>' +
                                    '<div class="calendar-time">时间 <input type="text" maxlength="2" class="calendar-time-h"/>:<input type="text" maxlength="2" class="calendar-time-m"/>:<input type="text" maxlength="2" class="calendar-time-s"/></div>' +
                                '</div>' +
                                '<div class="calendar-foot">' +
                                    '<input type="button" class="ui-calendarClear c-important c-important btn-bordsmall mr10" value="清空" /> ' +
                                    '<input type="button" class="ui-calendarToday c-obvious c-priority btn-bordsmall" value="今天" /> ' +
                                    '<input type="button" class="ui-calendarConfirm c-priority c-normal btn-bordsmall ml40" value="确定" />' +
                                '</div>' +
                            '</div>').shadow().click(function (event) {
                                return false;
                            }).appendTo(document.body);

                n.calendar_ops = ops;

                var hideDom = function () {
                    $(document).trigger('click');
                };

                var setTime = function (date) {
                    var h = parseInt(dom.find('.calendar-time-h').val());
                    if (h && h > 0 && h < 24)
                        date.setHours(h);

                    var m = parseInt(dom.find('.calendar-time-m').val());
                    if (m && m > 0 && m < 60)
                        date.setMinutes(m);

                    var s = parseInt(dom.find('.calendar-time-s').val());
                    if (s && s > 0 && s < 60)
                        date.setSeconds(s);

                    return date;
                }

                var setVal = function (v) {
                    var changed = $(n).val() != v;
                    if (changed) {
                        $(n).val(v);
                        if (ops.onChange) ops.onChange.call(n, v);
                    }
                    hideDom();
                };

                //清空
                dom.find('.ui-calendarClear').click(function () {
                    setVal('');
                    hideDom();
                });

                //今天
                dom.find('.ui-calendarToday').click(function () {
                    setVal(utils.dateToString(new Date(), ops.format));                    
                    hideDom();
                });

                //确定
                dom.find('.ui-calendarConfirm').click(function () {
                    setVal(utils.dateToString(setTime(n.calendar_date), ops.format));                    
                    hideDom();
                });

                //每天点击
                dom.find('.calendar-day').click(function (event) {
                    if (event.target.tagName == 'A') {
                        var date = n.calendar_date;
                        date.setDate(parseInt(event.target.innerHTML, 10));

                        setVal(utils.dateToString(setTime(date), ops.format));
                        hideDom();
                    }
                });

                //上一月,下一月
                dom.find('.calendar-prevmonth').click(function () {
                    var date = n.calendar_date;
                    var y = date.getFullYear(), m = date.getMonth(), d = date.getDate();
                    //上月最后一天
                    var lastDay = (new Date(y, m, 0)).getDate();
                    d = Math.min(d, lastDay);

                    $(n).calendar('renderDate', new Date(y, m - 1, d));
                });
                dom.find('.calendar-nextmonth').click(function () {
                    var date = n.calendar_date;
                    var y = date.getFullYear(), m = date.getMonth(), d = date.getDate();
                    //下月最后一天
                    var lastDay = (new Date(y, m + 2, 0)).getDate();
                    d = Math.min(d, lastDay);

                    $(n).calendar('renderDate', new Date(y, m + 1, d));
                });
                //年月选择
                dom.find('.ui-calendarYear').click(function () {
                    var me = $(this);
                    var input = $('<input type="text" value="' + parseInt(me.text(), 10) + '">');
                    input.width(me[0].offsetWidth);
                    var handler = function () {
                        if (!/\d{4}/g.test(input.val())) {
                            input.prev().show();
                            input.remove();
                            return true;
                        } else {
                            input.prev().show();
                            input.remove();
                            var date = n.calendar_date;
                            date.setFullYear(parseInt(input.val(), 10));
                            $(n).calendar('renderDate', date);
                            return true;
                        }
                    };
                    input.blur(handler);
                    input.bind('keyup', function (event) {
                        var keyCode = event.keyCode || event.which;
                        //按键事件
                        if (keyCode) {
                            switch (keyCode) {
                                case ui.keyCode.ESC:
                                    input.val(n.calendar_date.getFullYear());
                                    return handler();
                                case ui.keyCode.ENTER:
                                    return handler();
                            }
                            return true;
                        }
                    });
                    me.after(input).hide();
                    input[0].select();
                });
                dom.find('.ui-calendarMonth').click(function () {
                    var me = $(this);
                    var input = $('<input type="text" value="' + parseInt(me.text(), 10) + '">');
                    input.width(me[0].offsetWidth);
                    var handler = function () {
                        var val = parseInt(input.val(), 10);
                        if (isNaN(val) || val > 12 || val < 1) {
                            input.prev().show();
                            input.remove();
                            return true;
                        } else {
                            input.prev().show();
                            input.remove();
                            var date = n.calendar_date;
                            date.setMonth(parseInt(input.val() - 1, 10));
                            $(n).calendar('renderDate', date);
                            return true;
                        }
                    };
                    input.blur(handler);
                    input.bind('keyup', function (event) {
                        var keyCode = event.keyCode || event.which;
                        //按键事件
                        if (keyCode) {
                            switch (keyCode) {
                                case ui.keyCode.ESC:
                                    input.val(n.calendar_date.getMonth() + 1);
                                    return handler();
                                case ui.keyCode.ENTER:
                                    return handler();
                            }
                            return true;
                        }
                    });
                    me.after(input).hide();
                    input[0].select();
                });

                //元素获取焦点时弹出日历控件
                $(n).click(function (e) {
                    $(n).calendar('show');
                }).keyup(function (e) {
                    var key = window.event ? e.keyCode : e.which;
                    if (key == 9) { //tab跳出
                        hideDom();
                    }
                });
            });
        },
        renderDate: function (date) {
            date = date || new Date();
            var year = date.getFullYear(), month = date.getMonth(), day = date.getDate();
            var lastDay = (new Date(year, month + 1, 0)).getDate();
            var space, startWeek = (new Date(year, month, 1)).getDay();
            space = startWeek == 0 ? 6 : startWeek - 1;
            var padLeft = function (n) {
                n = n.toString();
                if (n.length < 2)
                    n = '0' + n;
                return n;
            };

            return this.each(function (i, n) {
                n.calendar_date = date;
                n.calendar_dom.find('.ui-calendarYear').html(year + '年');
                n.calendar_dom.find('.ui-calendarMonth').html(month + 1 + '月');

                var html = '';
                var minDate = n.calendar_ops.minDate ? (utils.toDate(n.calendar_ops.minDate) ? utils.toDate(n.calendar_ops.minDate) : utils.toDate($(n.calendar_ops.minDate).val())) : null;
                var maxDate = n.calendar_ops.maxDate ? (utils.toDate(n.calendar_ops.maxDate) ? utils.toDate(n.calendar_ops.maxDate) : utils.toDate($(n.calendar_ops.maxDate).val())) : null;
                for (var i = 0; i < space; i++)
                    html += '<span></span>';
                for (var i = 1; i <= lastDay; i++) {
                    if (minDate && new Date(year, month, i) < minDate)
                        html += '<span>' + i + '</span>';
                    else if (maxDate && new Date(year, month, i) > maxDate)
                        html += '<span>' + i + '</span>';
                    else
                        html += '<a href="javascript:"' + (i == day ? ' class="current"' : '') + '>' + i + '</a>';
                }

                n.calendar_dom.find('.calendar-day').html(html);
                n.calendar_dom.find('.calendar-time-h').val(padLeft(date.getHours()));
                n.calendar_dom.find('.calendar-time-m').val(padLeft(date.getMinutes()));
                n.calendar_dom.find('.calendar-time-s').val(padLeft(date.getSeconds()));
            });
        },
        show: function () {
            var bodyHeight = $(document.body).fullSize().height;

            return this.each(function (i, n) {
                var me = $(n);
                me.calendar('renderDate', utils.toDate(me.val() || new Date(), n.calendar_ops.format));

                if (n.calendar_dom.css('display') == 'none') {
                    var pos = me.offset();
                    var top = pos.top + n.offsetHeight + 1;
                    if (bodyHeight - n.calendar_dom.height() < pos.top)
                        top = pos.top - n.calendar_dom.height() - 4;    //底部3px阴影

                    n.calendar_dom.css({ left: pos.left, top: top }).show();
                    setTimeout(function () {
                        $(document).one('click', function () {
                            $(n.calendar_dom).hide();
                        });
                    }, 1);
                }
            });
        },
        minDate: function (date) {
            return this.each(function (i, n) {
                n.calendar_ops.minDate = date;
                $(n).calendar('renderDate', n.calendar_date);
            });
        },
        maxDate: function (date) {
            return this.each(function (i, n) {
                n.calendar_ops.maxDate = date;
                $(n).calendar('renderDate', n.calendar_date);
            });
        },
        option: function (ops) {
            return this.each(function (i, n) {
                n.calendar_ops = $.extend(n.calendar_ops, ops);
            });
        }
    });
})(jQuery);

//input limit
(function ($) {
    $.inputlimit = function (selector, reg) {
        var regex = new RegExp(reg);

        var valid = function (c) {
            return regex.test(c);
        };

        var clear = function () {
            var me = $(this);
            setTimeout(function () {

                var val = me.val(), newval = '';

                for (var i = 0; i < val.length; i++) {
                    if (valid(val.charAt(i)))
                        newval += val.charAt(i);
                }

                me.val(newval);
            }, 10);
        };

        $(document).on('keypress', selector, function (e) {
            var key = window.event ? e.keyCode : e.which;
            if (key != 13) return valid(String.fromCharCode(key));
        }).on('paste', selector, clear);
    };
})(jQuery);

//单选、多选重写
(function ($) {
    var changeSelected = function (selector, area) {
        $(selector, area).each(function (i, n) {
            var me = $(n);            
            var parent = me.parent().mouseleave(function () {
                //保证表单验证效果
                $(document.getElementsByName(me.attr('name'))).filter(':last').trigger('blur');
            });
            if (!n.disabled) {
                if (n.checked) {
                    parent.addClass('on');
                };
            } else {
                n.checked ? parent.addClass('disabled') : parent.addClass('ondisabled');
            }
            if (!me.attr('id')) {
                var id = $(this).attr('type') + (new Date()).valueOf() + i;
                me.attr('id', id).parent().attr('for', id);
            }
            me.on('click', function () {
                if (me.attr('type') == 'radio') {
                    parent.siblings().removeClass('on').find(':radio').each(function (j, m) {
                        $(m).removeAttr('checked');
                    });
                    me.attr('checked', 'checked').parent().addClass('on');
                } else {
                    if (parent.hasClass('on')) {
                        me.removeAttr('checked').parent().removeClass('on');
                    } else {
                        me.attr('checked', 'checked').parent().addClass('on');
                    }
                }
            })
        });
    };

    $.fn.checkbox = $.fn.radio = function (selector) {
        return this.each(function (i, n) {
            changeSelected(selector, n);
        });
    };
})(jQuery);

//滑块
(function ($) {
    var changeSelected = function (selector) {
        if (selector.parent().hasClass('on')) {
            selector.removeAttr('checked').parent().removeClass('on').find('.toggle-text').html('关闭').stop().animate({ left: '30px' }, "slow").next().stop().animate({ left: '2px' }, "fast");
        } else {
            selector.attr('checked', 'checked').parent().addClass('on').find('.toggle-text').html('开启').stop().animate({ left: '2px' }, "slow").next().stop().animate({ left: '+30px' }, "fast");
        };
    }
    var initSelected = function (i, n) {
        var me = $(n);
        if (n.checked) {
            me.parent().addClass('on').find('.toggle-text').html('开启').css('left', '2px').next().css('left', '30px');
        } else {
            me.parent().find('.toggle-text').html('关闭').css('left', '30px').next().css('left', '2px');;
        };
        if (!me.attr('id')) {
            var id = me.attr('type') + (new Date()).valueOf() + i;
            me.attr('id', id).parent().attr('for', id);
        };
        me.on('click', function () {
            changeSelected(me);
        });
    }
    $.fn.toggle = utils.fn({
        init: function (opt) {
            return this.each(function (i, n) {
                var dom = '<span class="toggle-text"></span><span class="toggle-btn"></span>';
                $(n).after(dom);
                initSelected(i, n);
            });
        },
        changeClick: function (bol) {
            return this.each(function (i, n) {
                var me = $(n);
                bol ? me.parent().removeClass('on') : me.parent().addClass('on');
                changeSelected(me);
            });
        }
    });
})(jQuery);

//图片重置
(function ($) {
    var imgReady = function (url, callback, errorfn) {
        var img = new Image();
        img.src = url;

        if (img.complete) {
            return callback(img.width);
        };

        img.onload = function () {
            callback(img.width);
            img.onload = img.onerror = null;
        };

        img.onerror = function () {
            errorfn(true);
        };
    };
    $.fn.imageWidthReset = function (opt) {
        ops = $.extend({
            pWidth:$(this).width()
        }, opt);
        return this.each(function (i, n) {
            $(n).find('img').each(function () {
                var me = $(this),
                    url = me.attr('src');
                imgReady(url,
                    function (w) {
                        if (w >= ops.pWidth) me.width(ops.pWidth);
                    },
                    function (b) {
                        if (b) me.remove();
                    });
            });
        })
    }
})(jQuery);

//滚动
(function ($) {
    $.fn.scrollview = function (ops) {
        if (typeof ops == 'string') ops = { selector: ops };

        ops = $.extend({
            selector: 'div',
            time: 3000,
            direction: 'top'	//方向，有top,left
        }, ops);
        
        return this.each(function (i, n) {
            var hander = null, idx = 0;
            var fn = function (init) {
                var elems = $(ops.selector, n);                
                if (elems.length > 1) {
                    var step = 0, json = {};
                    switch (ops.direction) {
                        case 'left':
                            step = $(n).width();
                            break;
                        case 'top':
                        default:
                            step = $(n).height();
                            break;
                    }

                    if (true !== init) {
                        //prev
                        json[ops.direction] = -step;
                        $(elems.eq(idx)).animate(json, function () {
                            //next
                            json[ops.direction] = step;
                            $(elems.eq((idx + 1) % elems.length)).css(json);
                            $(this).hide();
                        });

                        //current
                        idx = ++idx % elems.length;
                        json[ops.direction] = 0;
                        $(elems.eq(idx)).show().animate(json);
                    } else {
                        //next
                        json[ops.direction] = step;
                        $(elems.eq((idx + 1) % elems.length)).css(json);
                    }
                }
            };

            $(n).css({
                position: 'relative',
                overflow: 'hidden'
            }).mouseenter(function () {
                clearInterval(hander);
                hander = null;
            }).mouseleave(function () {
                if (hander == null) hander = setInterval(fn, ops.time);
            }).find(ops.selector).css('position', 'absolute')
				.not(':first').hide();

            fn(true);
            hander = setInterval(fn, ops.time);
        });
    };
})(jQuery);


//延迟加载。
(function ($) {
    $.fn.lazy = function (ops) {
        if (this.length == 0) return this;
        ops = $.extend({
            render: function () {
                var n = $(this);
                n.attr('src', n.attr('data-src')).opacity(0).fadeTo('fast', 1);
            }
        }, ops);

        var that = this;
        var fn = function () {
            var pageHeight = $(document).clientSize().height;
            var pageTop = pageHeight + Math.max(document.documentElement.scrollTop, document.body.scrollTop);
            that.each(function (i, n) {
                if (!n.lazy_rendered) {
                    var obj = $(n);
                    if (obj.offset().top <= pageTop) {
                        n.lazy_rendered = true;
                        ops.render.apply(n);
                    }
                }
            });
        };
        $(window).bind('load scroll resize', fn);

        return this;
    };
})(jQuery);



//数据滚动
(function ($) {
    $.fn.scrollno = function (timer, callbak) {
        if (typeof (timer) == 'function') {
            callbak = timer;
            timer = null;
        }
        timer = timer || 2000;
        var stepTime = 100; //100毫秒一次

        return this.each(function (i, n) {
            n = $(n);
            var html = n.html(), src = n.text().match(/[\d,\.]+/);
            if (src.length) {
                var end = parseFloat(src[0].replace(/,/g, ''));

                if (!isNaN(end)) {
                    var step = end / (timer / stepTime), used = 0;

                    var hd = setInterval(function () {
                        used++;
                        var val = Math.ceil(used * step);

                        if (val >= end) {
                            n.html(html);
                            clearInterval(hd);
                            if (callbak) callbak(n[0]);
                        } else {
                            n.html(html.replace(src, val.toString().replace(/(\d{1,3})(?=(?:\d{3})+(?!\d))/g, '$1,')));
                        }
                    }, stepTime);
                }
            }
        });
    };
})(jQuery);

//浮动。
(function ($) {
    $.fn.float = function (ops) {
        ops = $.extend({
            top: null,
            right: '5px',            
            scrollCallbak: null
        }, ops);
        var ie6 = ui.browser.ie6;

        return this.each(function (i, n) {
            n = $(n);
            var top = 0;

            //event
            $(n).find('a[href^=#]').each(function (i, n) {
                $(n).closest('div').mouseenter(function () {
                    $($(n).attr('href')).stop().fadeIn('fast');
                }).mouseleave(function () {
                    $($(n).attr('href')).stop().fadeOut('fast');
                });
            }).click(function () { return false; });

            //初始化位置
            var initDom = function () {
                top = ops.top || ($(document).clientSize().height - n.height()) / 2;

                if (ie6) {
                    n.css({
                        top: top,
                        left: $(document).clientSize().width - n.width() - parseInt(ops.right),
                        position: 'absolute'
                    }).show();
                } else {
                    n.css({
                        top: top,
                        right: ops.right,
                        position: 'fixed'
                    }).show();
                }                
            };
            initDom();

            //变更时
            var timer = null;
            var change = function () {
                clearTimeout(timer);

                timer = setTimeout(function () {
                    if (ie6) n.stop().animate({ top: $(document).scrollInfo().top + top });

                    if (ops.scrollCallbak) {
                        ops.scrollCallbak.call(n[0], $(document).scrollInfo().top);
                    }
                }, 30);
            };

            $(window).scroll(change).resize(function () {
                initDom();
                change();
            });
        });
    };
})(jQuery);