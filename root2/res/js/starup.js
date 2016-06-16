$(document).ready(function () {

    var footerhtml = [];
    footerhtml.push('<span>Copyright &copy; 2016. Redkale.</span>');
    //------------------------- 用户登录框 ------------------------------------------------------
    footerhtml.push('<div id="dialog-user-login" class="modal fade" tabindex="-1" data-width="500" data-backdrop="static" data-keyboard="false" style="display: none;"> ');
    footerhtml.push('    <div class="modal-header">  ');
    footerhtml.push('        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>  ');
    footerhtml.push('        <h4 class="modal-title">用户登录</h4>  ');
    footerhtml.push('    </div>  ');
    footerhtml.push('    <div class="modal-body">  ');
    footerhtml.push('        <form id="form-user-login" class="m-t" role="form" >  ');
    footerhtml.push('            <div class="form-group"><input name="data.account" type="text" class="form-control" placeholder="用户名" required=""></div>  ');
    footerhtml.push('            <div class="form-group"><input name="data.password" type="password" class="form-control" placeholder="密 码" required=""></div><br>  ');
    footerhtml.push('            <button id="btnok-user-login" type="button" class="btn btn-theme btn-lg btn-block ">登 录</button><br>  ');
    footerhtml.push('        </form>  ');
    footerhtml.push('    </div>  ');
    footerhtml.push('    <div id="tips-user-login" class="module-alert-tips"></div><br>  ');
    footerhtml.push('</div>');
    //------------------------- 密码修改框 ------------------------------------------------------
    footerhtml.push('<div id="dialog-user-changepwd" class="modal fade" tabindex="-1" data-width="500" style="display: none;"> ');
    footerhtml.push('    <div class="modal-header"> ');
    footerhtml.push('        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button> ');
    footerhtml.push('        <h4 class="modal-title">密码修改</h4> ');
    footerhtml.push('    </div> ');
    footerhtml.push('    <div class="modal-body"> ');
    footerhtml.push('        <form id="form-user-changepwd" class="m-t" role="form"> ');
    footerhtml.push('            <div class="form-group"><input name="data.oldpwd" type="password" class="form-control" placeholder="旧密码" required=""></div> ');
    footerhtml.push('            <div class="form-group"><input name="data.newpwd" type="password" class="form-control" placeholder="新密码" required=""></div> ');
    footerhtml.push('            <div class="form-group"><input name="data.newpwd2" type="password" class="form-control" placeholder="确认密码" required=""></div><br> ');
    footerhtml.push('            <button id="btnok-user-changepwd" type="button" class="btn btn-theme btn-lg btn-block ">密码修改</button><br> ');
    footerhtml.push('        </form> ');
    footerhtml.push('    </div> ');
    footerhtml.push('    <div id="tips-user-changepwd" class="module-alert-tips"></div><br>  ');
    footerhtml.push('</div>');
    document.getElementById("footer").innerHTML = footerhtml.join('');
    //---------------------------------- 用户登录框 绑定事件 -------------------------------------
    $('#dialog-user-login').on('show.bs.modal', function () { //
        $("#form-user-login")[0].reset();
        $("#tips-user-login").html('');
    });
    //
    $('#btnok-user-login').click(function (e) {
        var form = $("#form-user-login");
        $.ajax({
            cache: false,
            dataType: "json",
            data: {
                "bean": form.serializeJsonString("data"),
            },
            url: '/pipes/user/login',
            error: function () {//请求失败处理函数
                alert('请求失败');
            },
            success: function (data) {
                if (!data.success) {
                    if (data.retcode === 1001) {
                        $("#tips-user-login").html('用户或密码错误!');
                    } else if (data.retcode === 1002) {
                        $("#tips-user-login").html('用户已被禁用!');
                    } else {
                        $("#tips-user-login").html('登陆失败!');
                    }
                    return;
                } else {
                    if (window.localStorage) {
                        window.localStorage['glogin_account'] = $('#glogin_account').val();
                    }
                }
                window.location.reload();
            }
        });
    });
    //---------------------------------- 密码修改框 绑定事件 -------------------------------------
    $('#dialog-user-changepwd').on('show.bs.modal', function () { //
        $("#form-user-changepwd")[0].reset();
        $("#tips-user-changepwd").html('');
    });
    //
    $('#btnok-user-changepwd').click(function (e) {
        var form = $("#form-user-changepwd");
        var json = form.serializeJson("data");
        if (json.newpwd !== json.newpwd2) {
            alert("两次新密码输入不一致");
            return;
        }
        $.ajax({
            async: false,
            cache: false,
            dataType: "json",
            data: {
                "oldpwd": $.md5(json.oldpwd),
                "newpwd": $.md5(json.newpwd),
            },
            url: '/pipes/user/changepwd',
            error: function () {//请求失败处理函数
                alert('请求失败');
            },
            success: function (data) {
                if (!data.success) {
                    if (data.retcode === 1010021) {
                        $("#tips-user-changepwd").html('新密码未加密!');
                    } else if (data.retcode === 1010020) {
                        $("#tips-user-changepwd").html('旧密码错误!');
                    } else {
                        $("#tips-user-changepwd").html('密码修改失败!');
                    }
                    return;
                }
                $("#dialog-user-changepwd").modal('hide');
            }
        });
    });
    //-------------------------- topbar ----------------------------------------------------------
    var topbarhtml = [];
    topbarhtml.push('<div class="topbar-left"><div class="text-center"><a href="/index.html" class="logo">工作平台</a></div></div>');
    topbarhtml.push('<div class="pull-left menu-toggle"><i class="fa fa-bars"></i></div>');
    topbarhtml.push('<ul class="nav navbar-nav  top-right-nav hidden-xs">');
    topbarhtml.push('   <li class="dropdown profile-link hidden-xs">');
    topbarhtml.push('       <div class="clearfix" id="curruserel">');
    //userinfo 
    if (system_memberinfo && system_memberinfo.userid) {
        topbarhtml.push('       <a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown"> \
                                    <img src="/res/images/user.png" alt="" class="pull-left" style="width:36px;height:36px;"> \
                                    <span>' + system_memberinfo.chname + '<br><em>' + system_memberinfo.account + '</em></span>    \
                                </a> \
                                <ul class="dropdown-menu profile-drop"> \
                                    <li><a data-toggle="modal" data-target="#dialog-user-changepwd">设 置</a></li> \
                                    <li><a id="user-logout-btn" href="/pipes/user/logout">注 销</a></li> \
                                </ul>');
    }
    topbarhtml.push('       </div>');
    topbarhtml.push('   </li>');
    topbarhtml.push('   <li class=" hidden-xs" style="width: 3rem;">&nbsp;</li>');
    topbarhtml.push('</ul>');
    $("#topbar").html(topbarhtml.join(''));
    //-------------------------- side-menu ----------------------------------------------------------
    //menu data
    var winhref = '' + window.location.href;
    var hasactived = false;
    var recursmenu = function (menuhtml, onemenu, index) {
        if (onemenu.active) hasactived = true;
        if (onemenu.children && onemenu.children.length) {
            var subhref = false;
            var subminhtml = [];
            for (var j = 0; j < onemenu.children.length; j++) {
                subhref |= recursmenu(subminhtml, onemenu.children[j], index + 1);
            }
            if (subhref) onemenu.active = true;

            menuhtml.push('<li ' + (onemenu.active ? ' class="active"' : '') + '><a href="' + (onemenu.url || 'javascript:void(0);') + '"><i class="fa ' + onemenu.iconCls + '"></i> <span>' + onemenu.text + '</span><span class="fa arrow"></span></a>');
            menuhtml.push('    <ul class="nav nav-' + (index + 1) + '-level collapse">');
            menuhtml.push(subminhtml.join(''));
            menuhtml.push('    </ul>');
            menuhtml.push('</li>');
            return subhref;
        } else {
            var rs = (onemenu.url && winhref.indexOf(onemenu.url) >= 0);
            if (rs) onemenu.active = true;
            menuhtml.push('<li ' + (onemenu.active ? ' class="active"' : '') + '><a href="' + (onemenu.url || 'javascript:void(0);') + '"><i class="fa ' + onemenu.iconCls + '"></i> <span>' + onemenu.text + '</span></a></li>');
            return rs;
        }
    };
    var menuhtml = [];
    menuhtml.push('<ul class="metismenu clearfix" id="menu">');
    menuhtml.push('    <li class="first_active"><a href="/index.html"><i class="fa fa-home"></i>  <span>工作台首页</span></a></li>');

    for (var i = 0; system_sysmenus && i < system_sysmenus.length; i++) {
        hasactived |= recursmenu(menuhtml, system_sysmenus[i], 1);
    }
    menuhtml.push('</ul>');
    menuhtml.push('<div class="nav-bottom clearfix">');
    menuhtml.push('    <a href="javascript:void(0);" style="border-right: 0px;"><i class="fa fa-lock"></i></a>');
    menuhtml.push('     <a href="javascript:void(0);" style="border-right: 0px;"><i class="fa fa-download"></i></a>');
    menuhtml.push('    <a href="javascript:void(0);" style="border-right: 0px;"><i class="fa fa-globe"></i></a>');
    menuhtml.push('    <a href="javascript:void(0);" style="border-right: 0px;"><i class="fa fa-phone"></i></a>');
    menuhtml.push('</div>');
    $("#side-menu").html(menuhtml.join('').replace('first_active', hasactived ? '' : 'active'));
    $("#sidebar-toggle").click(function () {
        $(".sidebar").toggleClass("active");
    });
    $(".menu-toggle").click(function () {
        $("body").toggleClass("widescreen");
    });
    //slim
    $('.nicescroll').slimScroll({
        height: '100%',
        railOpacity: 0.9
    });
    //metis menu
    $("#menu").metisMenu();
    $(".content-page,.side-menu").equalize({});
    //tooltips
    $(function () {
        $('[data-toggle="tooltip"]').tooltip();
        $('[data-toggle="popover"]').popover();
    });
    if (!window['system_memberinfo'] || !system_memberinfo.userid) {
        $('#dialog-user-login').modal('show');
    }
});