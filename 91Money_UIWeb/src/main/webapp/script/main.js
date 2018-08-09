/// <reference path="jquery.js" />
//弹出层
(function ($) {
    var popupDialog = function (html, title, width, btns, closeBtn) {
        btns = btns || [];
        var el = $('<div style="padding-bottom:' + (btns && btns.length ? '20' : '0') + 'px;"><div class="dialog-btns"></div></div>');
        el.prepend(html)

        var btnsDom = el.find('.dialog-btns');
        $.each(btns, function (i, n) {
            var btn = $('<input type="button" class="btn-nobord" value="' + n.text + '" />');
            if (n.cls) btn.addClass(n.cls);
            btn.click(function () {
                n.click(el);
            });
            btnsDom.append(btn);
        });

        $(document.body).dialog({
            title: title,
            modal: true,
            width: width || 380,
            height: 'auto',
            el: el,
            zIndex: 9999,
            closeShow: closeBtn
        });
    };

    var popupTip = function (message, title, width, icon, btns,closeBths) {
        popupDialog('<table style="margin:20px;"><tr><th valign="middle" style="padding-right:10px;"><span class="dialog-icon ' + icon + '"></span></th><td valign="middle">' + message + '</td></tr></table>', title, width, btns, closeBths);
    };

    //弹出Html对话框
    $.jPopupHtml = function (html, title, width, closeBtn) {
        popupDialog(html, title, width, [], closeBtn);
    }

    //弹出Iframe对话框
    $.jPopupUrl = function (title, url, width, height) {
        $(document.body).dialog({
            url: url,
            title: title,
            modal: true,
            width: width,
            height: height || 'auto',
            zIndex: 9999            
        });
    }

    //确认框
    $.jConfirm = function (message, okFn, cancelFn, title, width) {

        popupTip(message, title || '确认提示', width, 'info',
            [{
                text: '&#xe62a; 确定',
                click: function () {
                    if (okFn) okFn();
                    $(document.body).dialog('destroy');
                },
                cls: 'c-important iconfont'
            }, {
                text: '&#xe60f; 取消',
                click: function () {
                    if (cancelFn) cancelFn();
                    $(document.body).dialog('destroy');
                },
                cls: 'c-normal iconfont'
            }]
        );
    };

    //通知框
    $.jNotice = function (message, title, width) {

        popupTip(message, title || '通知', width, 'info',
            [{
                text: '&#xe62a; 确定',
                click: function () {
                    $(document.body).dialog('destroy');
                },
                cls: 'c-important  iconfont'
            }]
        );
    };

    //错误框
    $.jError = function (message, title, width, fn) {
        popupTip(message, title || '错误', width, 'error',
            [{
                text: '&#xe62a; 确定',
                click: function () {
                    if (fn) fn();
                    $(document.body).dialog('destroy');
                },
                cls: 'c-important iconfont'
            }], fn ? false : true
        );
    };

    //成功框
    $.jSuccess = function (message, fn, title, width) {
        popupTip(message, title || '成功', width, 'success',
            [{
                text: '&#xe62a; 确定',
                click: function () {
                    if (fn) fn();
                    $(document.body).dialog('destroy');
                },
                cls: 'c-important iconfont'
            }]
        );
    };

})(jQuery);

//公用操作
var common = {
    //发送型验证码
    sendValidCode: {
        //发送(按妞，发送目标，发送要输入的文本框)。
        send: function (btn, target, input) {
            btn = $(btn), btnText = btn.attr('value');            

            $.ajax({
                url: '/User/ValidCode' + gconfig.pageExt + '?action=getSendKey',
                cache: false,
                dataType: 'html',
                beforeSend: function () {
                    btn.attr('disabled', 'disabled').addClass('c-disable');
                },
                success: function (k) {
                    $.ajax({
                        url: '/User/ValidCode' + gconfig.pageExt + '?action=sendValidCode',
                        cache: false,
                        dataType: 'json',
                        data: {
                            data: target,
                            key: k
                        },
                        success: function (res) {
                            if (res.Success) {
                                input.removeAttr('disabled');
                                btn.attr('value', '发送成功');
                                var time = 180;
                                var timer = setInterval(function () {
                                    time--;
                                    if (time > 0) {
                                        btn.attr('value', '(' + time + ')秒后可重新发送');
                                    } else {
                                        btn.attr('value', btnText).removeAttr('disabled').removeClass('c-disable');
                                        clearInterval(timer);
                                    }
                                }, 1000);
                            } else {
                                btn.removeAttr('disabled').removeClass('c-disable');
                            }

                            btn.closest('div').find('em').html(res.Message);
                        }
                    });
                }
            });
        },
        //验证
        valid: function (target, code) {
            var res = true;
            $.ajax({
                type: 'get',
                url: '/User/ValidCode' + gconfig.pageExt + '?action=validSendCode',
                async: false,
                cache: false,
                data: { data: target, code: code },
                success: function (result) {
                    res = result == 'ok';
                }
            });
            return res;
        },
        //初始化表单
        initInput: function (el) {
            el = el || document.body;
            $('.js-sendvcode', el).each(function (i, n) {
                var n = $(n), target = $(n.attr('data-vcode-target'));
                n.find('.js-sendvcode-btn').click(function () {
                    var fm = $(this).closest('form'), area = $(this).closest('div.js-step,form');
                    var tipFn = function (msg) {
                        if (fm.hasClass('js-vinline')) {
                            common.formValids.inline.errorFn(msg);
                        } else if (fm.hasClass('js-vtip')) {
                        } else if (fm.hasClass('js-vsum')) {
                            area.find('em.js-vsum').html(msg);
                        }
                    };
                    if (window.form.valid(target.closest(n.attr('data-vcode-targetForm') || '.js-tip'), tipFn, true)) {
                        common.sendValidCode.send(this, target.val(), n.find('.js-sendvcode-code'));
                    }
                });
            });
        }
    },
    imageValidCode: {
        valid: function (code) {
            var res = true;
            $.ajax({
                type: 'get',
                url: '/User/ValidCode' + gconfig.pageExt + '?action=validImageCode',
                async: false,
                cache: false,
                data: { data: code },
                success: function (result) {
                    res = result == 'ok';
                }
            });
            return res;
        }
    },
    //表单验证处理函数
    formValids: {
        inline: {
            focusFn: function () { },
            errorFn: function (msg) {
                var ctr = $(this).removeClass('success').addClass('error').closest('.js-tip'),
                    em = ctr.find('em.m-tip');

                if (em.length)
                    em.html(msg);
                else {
                    var dom = '<em class="m-tip">' + msg + '</em>';
                    ctr.find('.js-prompt').length > 0 ? ctr.find('.js-prompt').before(dom) : ctr.append(dom)
                }
                    
            },
            okFn: function () {
                $(this).removeClass('error').addClass('success').closest('.js-tip')
                    .find('em.m-tip').remove();
            }
        }
    },
    //页面提示
    pageTip: function (type, msg, toUrl, toText) {
        toUrl = toUrl || '';
        toText = toText || '';
        location.href = '/Tips' + gconfig.pageExt + '?type=' + type + '&msg=' + encodeURIComponent(msg) + '&toUrl=' + encodeURIComponent(toUrl) + '&toText=' + encodeURIComponent(toText);
    },
    //处理表单提交
    formSubmit: function () {

        var me = $(this), valid = false,
            step = me.hasClass('js-step'), steps = me.children('.js-step');

        //向导式表单时需验证的区域
        var validArea = step ? steps.filter(':visible') : me;

        //验证
        if (me.is('.js-vinline')) {
            valid = window.form.valid(validArea, function (msg) {
                common.formValids.inline.errorFn.call(this, msg);
            }, true);
        } else if (me.is('.js-vtip')) {

            valid = window.form.valid(validArea, function (msg) {                
                var input = $(this), offset = input.is(':checkbox') ? input.parent().offset() : input.offset();
                var ctr = $(document.body);
                input.change(function () {
                    ctr.find('em.js-vtip').remove();
                });

                var em = ctr.find('em.js-vtip');
                if (em.length == 0)
                    em = $('<em class="m-toptip js-vtip"><i class="toptip-bgtop">' + msg + '</i></em>');
                else
                    em.find('i').html(msg);
                
                ctr.append(em.css({
                    'left': offset.left,
                    'position': 'absolute',
                    'top': offset.top + (input.is(':checkbox') ? input.parent()[0].scrollHeight : input[0].scrollHeight) + 6
                }));
            }, true, true);

        } else if (me.is('.js-vsum')) {
            var msgAll = new Array();
            valid = window.form.valid(validArea, function (msg) {
                msgAll.push(msg);
            }, false);
            if (!valid) {
                validArea.find('em.js-vsum').html(msgAll[0]);
            }
        };
        if (valid) {
            validArea.find('em.js-vsum').html(null);
            //向导式表单验证最后一步时可提交     
            var canSubmit = step && steps.index(validArea) != steps.length - 1 ? false : true;

            if (canSubmit) {
                var beforeSubmit = me.attr('data-beforeSubmit');
                if (beforeSubmit)
                    eval(beforeSubmit);

                window.form.ajax(this, 'json', function (r) {
                    if (r.Success) {
                        var submit = validArea.find(':submit');
                        var btnText = submit.attr('data-success-text'), oldText = submit.attr('value');
                        submit.attr('disabled', 'disabled').addClass('c-important');
                        if (btnText) submit.val(btnText);

                        setTimeout(function () {
                            submit.removeAttr('disabled').removeClass('c-important').val(oldText);
                        }, 1500);

                        var onsuccess = me.attr('data-onsuccess');
                        if (onsuccess) {
                            window['ajaxResponseData'] = r;

                            if (btnText)
                                setTimeout(function () { eval(onsuccess); }, 1000);
                            else
                                eval(onsuccess);
                        }
                    }
                    else {
                        validArea.find('em.js-vsum').html(r.Message);
                        var onerror = me.attr('data-onerror');
                        if (onerror) eval(onerror);
                    }
                });
            } else if (step) {
                commonUi.stepSwitch(steps.index(validArea.hide()) + 1);
            }
        }

        return false;
    }
};

//commonUi
var commonUi = {
    //下拉菜单
    downMenu: function (str, meun) {
        var time;
        $(str).mouseenter(function () {
            var _this = $(this);
            time = setTimeout(function () { _this.find(meun).stop().slideDown('fast'); }, 200);
        }).mouseleave(function () {
            clearTimeout(time);
            $(this).find(meun).stop().slideUp('fast');
        });
    },
    //向导式导航切换
    stepSwitch: function (index) {
        var el = $(document.body);
        var navs = el.find('div.js-stepnav li'), steps = el.find('div.js-step');
        navs.each(function (i, n) {
            n = $(n);
            if (i > index) n.removeClass('done').removeClass('current');
            if (i == index) n.removeClass('done').addClass('current');
            if (i < index) n.removeClass('current').addClass('done');
        });
        steps.each(function (i, n) {
            n = $(n);
            if (i == index)
                n.show();
            else
                n.hide();
        });
    },
    //数据列表各行换色
    tableAlt: function (html) {
        html = $(html);
        html.not('.pager').filter(':odd').addClass('alt');
        return html;
    },
    //分页处理
    changePage: function () {
        var me = $(this);
        utils.cookie('site_pagesize', me.val(), { path: '/' });
        var pages = me.closest('.pages');
        var a = pages.next();
        pages.append(a);//将a放入pages内，以响应点击事件
        a.attr('href', utils.urlParam(a.attr('href'), 'Page', '1'));
        a.trigger('click');
    },
    //初始化日期
    initDate: function (el) {
        $('.ui-date', el || document.body).each(function (i, n) {
            n = $(n), mindate = n.attr('data-mindate'), maxdate = n.attr('data-maxdate');
            var ops = {};
            if (mindate)
                ops.minDate = mindate.charAt(0) == '#' ? mindate : new Date(mindate)
            if (maxdate)
                ops.maxDate = maxdate.charAt(0) == '#' ? maxdate : new Date(maxdate)

            if (n.is('.ui-datetime'))
                ops.format = 'yyyy-MM-dd HH:mm:ss';            

            n.calendar(ops);
        });
    },
    //初始化A标签类型的条件过滤
    initAFilter: function () {
        var dom = $('.js-filter').on('click', 'a[data-filter-to]', function () {
            var me = $(this).addClass('on'),
                tos = me.attr('data-filter-to'),
                values = me.attr('data-filter-value').split('|'),
                form = me.closest('form.search');

            var pre = '', orgval = '';
            $.each(tos.split('|'), function (i, n) {
                var dom = $(n);
                if (pre == '') orgval = dom.val();
                if (pre == n) {
                    if (values[i] != orgval) dom.val(values[i]);
                }
                else
                    dom.val(values[i]);

                pre = n;
            });

            form.find('a.on[data-filter-to="' + tos + '"]').not(me).removeClass('on');
            form.trigger('submit');
            return false;
        });

        dom.find('.ui-date').calendar('option', {
            onChange: function () {
                $(this).closest('form.search').trigger('submit');
            }
        });

        dom.find('input:hidden').each(function (i, n) {
            n = $(n);
            if (n.val()) {
                var as = n.closest('form.search').find('a[data-filter-to="#' + n.attr('id') + '"]');
                as.filter('.on').removeClass('on');
                as.filter('[data-filter-value="' + n.val() + '"]').addClass('on');
            }
        });
    },
    //倒计时
    timedown: function (dom, second, formats) {
        if (second == 0) return;

        dom = $(dom);
        formats = $.extend({
            d: '<span>@</span> 天 ',
            h: '<span>@</span> 小时 ',
            m: '<span>@</span> 分 ',
            s: '<span>@</span> 秒 '
        }, formats);

        var run = 0, orgTime = Math.abs(second), hd = null;
        var fn = function () {
            var str = '', t = orgTime - run;
            run++;

            if (t == 0) {
                dom.html('');
                clearInterval(hd)
            }
            else {
                var d = parseInt(t / 60 / 60 / 24); //天
                if (d > 0) {
                    str += formats.d.replace('@', d);
                    t -= (d * 60 * 60 * 24);
                }
                var h = parseInt(t / 60 / 60); //小时
                if (h > 0) {
                    str += formats.h.replace('@', h);
                    t -= (h * 60 * 60);
                }
                var m = parseInt(t / 60); //分
                if (m > 0) {
                    str += formats.m.replace('@', m);
                    t -= (m * 60);
                }
                var s = t;  //秒                
                str += formats.s.replace('@', s);

                str += (second > 0 ? '后结束' : '后开始');
                dom.html(str);
            }
        };

        fn();
        hd = setInterval(fn, 1000);
    }
};

//图表
var charts = {
    bar: function (element, text, names, amount, color) {
        color = color || '#5ec2eb';
        var myChart = echarts.init(document.getElementById(element), macarons);
        var option = {
            title: {
                x: 'center',
                text: text
            },
            tooltip: {
                trigger: 'item',
                formatter: '{b}\n{c}元'
            },
            toolbox: {
                show: true,
                feature: {
                    saveAsImage: { show: true }
                }
            },
            calculable: true,
            grid: {
                borderWidth: 0
            },
            xAxis: [
                {
                    type: 'category',
                    show: false,
                    data: names
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    show: false
                }
            ],
            series: [
                {
                    name: '统计金额',
                    type: 'bar',
                    itemStyle: {
                        normal: {
                            barBorderRadius: [5, 5, 0, 0],
                            color: color,
                            label: {
                                show: true,
                                position: 'top',
                                formatter: '{c}元'
                            }
                        }
                    },
                    data: amount
                }
            ]
        };
        myChart.setOption(option);
    },
    line: function (element, legendData, xAxisData, series) {
        var color = color || '#5ec2eb';
        var myChart = echarts.init(document.getElementById(element), macarons);
        option = {
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data: legendData
            },
            toolbox: {
                show: true,
                feature: {
                    saveAsImage: { show: true }
                }
            },
            calculable: true,
            xAxis: [
                {
                    type: 'category',
                    boundaryGap: false,
                    data: xAxisData
                }
            ],
            yAxis: [
                {
                    type: 'value'
                }
            ],
            series: series
        };
        myChart.setOption(option);
    }
};

//用户
var member = {
    logined: null,

    //初始化顶部登录状态
    initStatus: function () {
        $.ajax({
            type: 'get',
            url: '/User/Status' + gconfig.pageExt + '?action=loginStatus',
            cache: false,
            success: function (result) {
                $('.head-fixednav').remove();
                $('.head-nav').append(result);

                if (result.indexOf('data-logined') != -1)
                    member.logined = parseInt($('.head-nav').find('[data-logined]:first').attr('data-logined'));
                else
                    member.logined = null;

                commonUi.downMenu('.head-fixednav', '.my-account');
                $('.head-fixednav').mouseenter(function () {
                    $.ajax({
                        type: 'get',
                        url: '/User/UserData' + gconfig.pageExt + '?action=topUserData',
                        cache: false,
                        success: function (result) {
                            $('#js-myAccout').html(result);
                        }
                    });
                });
            }
        });
    },
    //mini登录
    miniLogin: function (callback) {
        window.miniLoginCallback = function () {
            $(document.body).dialog('destroy');
            member.initStatus();
            if (callback) callback()
        };
        window.miniLoginError = function () {
            var dom = $('#js-codeinput');

            $.get('/User/ValidCode' + gconfig.pageExt + '?action=isneed&v=' + (new Date()).getTime(), function (res) {
                if (res == 'yes') {
                    dom.show();
                    dom.find('a').trigger('click');
                    dom.find('input').attr('data-vtype', 'must');
                } else {
                    dom.find('input').removeAttr('data-vtype');
                }
            });
        };
        $.get('/User/MiniLogin' + gconfig.pageExt, function (res) {
            res = $(res);
            res.checkbox('.ui-vcheckbox :checkbox');
            res.find('form').submit(common.formSubmit);
            $.jPopupHtml(res, '帐户登录', 300);
        });
    },
    //js登出
    logout: function () {
        var loc = top.location;
        top.location.href = '/User/Logout' + gconfig.pageExt + '?ref=' + encodeURIComponent(loc.pathname + loc.search + loc.hash);
    },
    //支付
    pay: function (action, query, callback) {
        query = query || '';
        window.payCallback = function () {
            $(document.body).dialog('destroy');
            if (callback) callback();
        };
        $.get('/User/PayPassword' + gconfig.pageExt + '?action=' + action + '&' + query + '&_rnd=' + (new Date()).getTime(), function (res) {
            if (res) {
                res = $(res);
                res.find('form').submit(common.formSubmit);
                $.jPopupHtml(res, '请输入支付密码', 506);
            } else {
                //为空时为未登录
                member.miniLogin(function () {
                    member.pay(action, query, callback);
                });
            }
        });
    },
    //用户举报
    userReport: function (uid) {        
        $.get('/User/UserReport' + gconfig.pageExt + '?userId=' + uid, function (res) {
            if (res) {
                res = $(res);
                res.find('form').submit(common.formSubmit);
                $.jPopupHtml(res, '用户举报', 506);
            }
        });
        return false;
    },
    //意见反馈
    guestBook: function () {
        $.get('/User/GuestBook' + gconfig.pageExt, function (res) {
            if (res) {
                res = $(res);
                res.find('form').submit(common.formSubmit);
                $.jPopupHtml(res, '意见反馈', 506);
            } else {
                //为空时为未登录
                member.miniLogin(function () {
                    member.guestBook();
                });
            }
        });
        return false;
    },
    //添加好友
    addFriend: function (uid) {
        $.get('/User/SNS/FriendList' + gconfig.pageExt + '?action=addFriend&uid=' + uid + '&_rnd=' + (new Date()).getTime(), function (res) {
            if (res.Success)
                $.jSuccess('添加好友成功。');
            else
                $.jError(res.Message);
        }, 'json');
    },
    //加入黑名单
    addBlackList: function (uid) {
        $.get('/User/SNS/FriendList' + gconfig.pageExt + '?action=addBlackList&uid=' + uid + '&_rnd=' + (new Date()).getTime(), function (res) {
            if (res.Success)
                $.jSuccess('拉入黑名单成功。');
            else
                $.jError(res.Message);
        }, 'json');
    },
    initUserCenter: function () {
        //用户中心侧边导航控制
        var changTree = function (dd, bool) {
            if (bool) {
                dd.prev().find('a').removeClass('on').find('.js-arrow').html('&#xe60b;');//下拉箭头
            } else {
                dd.prev().find('a').addClass('on').find('.js-arrow').html('&#xe621;');//上拉箭头
            }
        };

        var linkStr = function (str) {
            return str.slice(str.indexOf('User/') + 5, str.lastIndexOf('/')).toLowerCase();
        };

        var local = linkStr(String(location.href)),
            treeNav = $('.ui-treenav');

        if (local) {
            treeNav.find('a').each(function () {
                var str = linkStr($(this).attr('href'));
                if (str.indexOf(local) != -1) changTree($(this).parents('dd').show());
            });
        } else {
            changTree($('.ui-treenav dd:first').show());
        };
        treeNav.find('dt').each(function () {
            var dt = $(this),
                dd = dt.next();
            if (dd.find('li').length == 0) {
                dt.find('.js-arrow').remove();
            } else {
                dt.find('.js-arrow').show();
                dt.on('click', function () {
                    if (dd.is(':hidden')) {
                        changTree(dd.stop().slideDown('fast'));
                    } else {
                        changTree(dd.stop().slideUp('fast'), true);
                    }
                });
            }
        });
    }
};

//分页处理
$('.js-ajaxlist').on('click', '.pages a', function () {
    var tb = $(this).closest('.js-ajaxlist'), firsttr = tb.find('tr:first');
    var f = $('form.search'), fillto = f.attr('data-ajax-fill'), scroll = f.attr('data-ajax-scroll');

    $.ajax({
        url: $(this).attr('href'),
        cache: false,
        success: function (res) {
            if (fillto) {
                $(fillto).html(res);
                if (scroll) ui.scroll(scroll);
            } else {
                var nextall = firsttr.nextAll();
                firsttr.after(commonUi.tableAlt(res));
                nextall.remove();
                ui.scroll(firsttr);
            }

            tb.find('.pages select').change(commonUi.changePage);
            tb.find('script.js-run').each(function (i, n) {
                eval(n.innerHTML);
            });
        },
        error: function (res) {
            $.jError('远程服务器错误，请稍候再试！');
        }
    });
    return false;
});

//ajax 加载数据列表
$('form.search').submit(function () {
    var f = $(this), content = $('.js-ajaxlist'), firsttr = content.find('tr:first'), tdcount = firsttr.find('th').length;
    var fillto = f.attr('data-ajax-fill'), loading = f.attr('data-ajax-loading'), error = f.attr('data-ajax-error'), scroll = f.attr('data-ajax-scroll');

    $.ajax({
        url: f.attr('action') || location.href,
        type: f.attr('method') || 'get',
        data: f.serialize(),
        cache: false,
        beforeSend: function () {
            window.form.disable(f);
            if (fillto) {
                $(fillto).html(loading);
            } else {
                firsttr.nextAll().remove();
                firsttr.after('<tr><td colspan="' + tdcount + '" class="g-fleft">数据加载中...</td></tr>');
            }
        },
        success: function (res) {
            if (fillto) {
                $(fillto)[0].innerHTML = res;

            } else {
                firsttr.nextAll().remove();
                firsttr.after(commonUi.tableAlt(res));
            }

            if (scroll) ui.scroll(scroll);

            content.find('.pages select').change(commonUi.changePage);
            content.find('script.js-run').each(function (i, n) {
                eval(n.innerHTML);
            });
        },
        complete: function () {
            window.form.enable(f);
        },
        error: function (res) {
            if (fillto) {
                firsttr.nextAll().remove();
                firsttr.after('<tr><td colspan="' + tdcount + '" class="g-fleft">远程服务器错误，请稍候再试！</td></tr>');
            } else {
                $(fillto).html(error);
            }
        }
    });

    return false;
}).on('reset', function () {
    //重置时重新加载数据
    var me = $(this);
    setTimeout(function () {
        me.trigger('submit');
    }, 10);
}).each(function (i, n) {
    n = $(n);
    if (n.attr('data-ajax-auto') != 'false')
        n.trigger('submit');    //默认第一次进来加载
});

$(function () {
    //用户状态
    member.initStatus();

    //初始化常用控件
    
    $('.ui-tab').tabs();
    $('.ui-editor').css('width', '98%').each(function (i, n) { UE.getEditor(n); });
    $.inputlimit('.ui-int', '[0-9]');
    $.inputlimit('.ui-float', '[0-9\.]');
    $.inputlimit('.ui-interest', '[0-9\.]');
    $.inputlimit('.ui-date', '[0-9-:\\s]');
    $('.js-convertnum').each(function (i, n) {
        var me = $(this),
            val = finance.toChineseUnit(me.html());
        me.html(val);
    })
    
    $(document.body).on('focus', '.ui-float', function () {
        var me = $(this);
        setTimeout(function () {
            var val = me.val();
            if (val) {
                me.val(val.indexOf(',') == -1 ? val : val.replace(/,/ig, ''));
            }
        }, 1);
    });

    $(document.body).on('blur', '.ui-float', function () {
        var me = $(this);
        setTimeout(function () {
            var val = me.val();
            if (val) {
                me.val(finance.toMoneyString(val));
            }
        }, 1);
    });

    //菜单
    commonUi.downMenu('.js-topnav li', 'dl');
    commonUi.downMenu('.head-nav li', 'dl');

    //单选、多选重写
    $(document.body).checkbox('.ui-checkbox :checkbox, .ui-vcheckbox :checkbox');
    $(document.body).radio('.ui-radio :radio');

    //选项卡
    $('.js-tabs').each(function (i, n) {
        n = $(n);
        n.tabs({ tabSelector: n.attr('data-tabs-selector') });
    });

    $('.js-slider').each(function (i, n) {
        n = $(n), isFloat = n.attr('data-slider-step').indexOf('.') != -1;
        var parseFn = function (str) {
            str = str.replace(/,/g, '');
            return isFloat ? parseFloat(str) : parseInt(str);
        };
        n.slider({
            min: parseFn(n.attr('data-slider-min')),
            max: parseFn(n.attr('data-slider-max')),
            step: parseFn(n.attr('data-slider-step')),
            starter: parseFn(n.val())
        });
    });

    window.form.fixPlaceholder();

    //表单处理    
    $('form.js-auto').each(function (i, n) {
        n = $(n);
        if (n.is('.js-vinline')) {
            window.form.validBind(n, common.formValids.inline, true);
        }
    }).submit(common.formSubmit);

    //操作
    $(document.body).on('click', 'input.js-action', function () {
        var me = $(this),
            tip = me.attr('data-action-confirm'),
            action = me.attr('data-action'),
            success = me.attr('data-action-success');

        var process = function () {
            if (window[action] && window[action]() && success) {
                eval(success);
            } else {
                $.ajax({
                    type: 'post',
                    url: action,
                    cache: false,
                    dataType: 'json',
                    beforeSend: function () {
                        me.attr('disabled', 'disabled');
                    },
                    success: function (res) {
                        if (res.Success && success) {
                            eval(success);
                        } else {
                            $.jError(res.Message);
                        }
                    },
                    complete: function () {
                        setTimeout(function () {
                            me.removeAttr('disabled');
                        }, 10);
                    },
                    error: function () {
                        $.jError('远程服务器错误，请稍候再试！');
                    }
                });
            }
        };

        if (tip) {
            $.jConfirm(tip, function () {
                process();
            });
        } else process();

        return false;
    });

    //初始化时间控件
    commonUi.initDate();

    //初始化条件筛选
    commonUi.initAFilter();

    //用户中心
    member.initUserCenter();

    //绑定滑动组件
    $('.js-toggle :checkbox').toggle();

    //绑定用户信息菜单
    $(document).on('mouseenter', '.js-umenu', function () {
        var uid = $(this).attr('data-uid'), myid = member.logined;
        if (myid && uid && myid != uid) {
            var me = $(this);
            var dom = '<div class="ui-smsmenu">' +
                '<span class="smsmenu-arrow"></span>' +
                '<a href="/User/SNS/FriendList' + gconfig.pageExt + '?uid=' + uid + '"><i class="iconfont">&#xe637;</i> 发送私信</a>' +
                '<a href="javascript:void(0);" onclick="member.addFriend(\'' + uid + '\');"><i class="iconfont">&#xe635;</i> 加为我的好友</a>' +
                '<a href="javascript:void(0);" onclick="member.addBlackList(\'' + uid + '\');"><i class="iconfont">&#xe636;</i> 设为黑名单</a>' +
                '<a href="javascript:void(0);" onclick="member.userReport(\'' + uid + '\');" class="smsmenu-noneborder"><i class="iconfont">&#xe638;</i> 违规举报</a>' +
                '</div>';

            me.append(dom).css({ 'position': 'relative', 'cursor': 'pointer' }).hover(function () {
                me.find('.ui-smsmenu').stop().slideDown('fast');
            }, function () {
                me.find('.ui-smsmenu').remove();
            })
        }
    });

    //生成浮动
    $('#js-floatMenu').float({
        scrollCallbak: function (pageScrolltop) {
            if (pageScrolltop >= $(document).clientSize().height / 3) {
                $(this).find('.floatmenu-top').stop().fadeIn();
            } else {
                $(this).find('.floatmenu-top').stop().fadeOut();
            }
        }
    });
});

/**
 * 提交表单数据
 * @returns
 */
function sub(obj){
	var $form =$("#formid");
	$.ajax({
		url:$form.attr("action"),
		data:$form.serialize(),
		success:obj
		
	})
	
	
	
}


function gatUserdata() {
	//首先从cookie中获取数据
	var token =$.cookie("duandian");//从本地cookie获取用户信息的key
	if(!token){//如果没有数据,应该显示登录
		$("#loginregist").css("display","block");
		return;
	}
	$.ajax({
		url:"http://localhost:9001/user/getUserData.action",
		data:"token="+token,
		dataType:"jsonp",
		success:function(data){
			var json=JSON.parse(data);
			//console.info(data);
			if(json.code==1){//只需要判断登录成功,因为首页不需要必须登录
			//	alert('登录成功');
			//将登录注册按钮隐藏,显示我的账户按钮
			
			$("#myaccount").css("display","block");
			}else{
			$("#loginregist").css("display","block");
			}
	
		}
	
	})
}
