$(document).ready(function () {

    var footerhtml = [];
    footerhtml.push('<span>Copyright &copy; 2016. Redkale.</span>');
    //------------------------- 用户登录框 ------------------------------------------------------
    footerhtml.push('<div id="module-login-dialog" class="modal fade" tabindex="-1" data-width="500" data-backdrop="static" data-keyboard="false" style="display: none;"> ');
    footerhtml.push('    <div class="modal-header">  ');
    footerhtml.push('        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>  ');
    footerhtml.push('        <h4 class="modal-title">用户登录</h4>  ');
    footerhtml.push('    </div>  ');
    footerhtml.push('    <div class="modal-body">  ');
    footerhtml.push('        <form id="module-login-form" class="m-t" role="form" >  ');
    footerhtml.push('            <div class="form-group"><input name="data.account" type="text" class="form-control" placeholder="用户名" required=""></div>  ');
    footerhtml.push('            <div class="form-group"><input name="data.password" type="password" class="form-control" placeholder="密 码" required=""></div><br>  ');
    footerhtml.push('            <button id="module-login-submit" type="button" class="btn btn-theme btn-lg btn-block ">登 录</button><br><br>  ');
    footerhtml.push('        </form>  ');
    footerhtml.push('    </div>  ');
    footerhtml.push('</div>');
    //------------------------- 密码修改框 ------------------------------------------------------
    footerhtml.push('<div id="module-changepwd-dialog" class="modal fade" tabindex="-1" data-width="500" data-backdrop="static" data-keyboard="false" style="display: none;"> ');
    footerhtml.push('    <div class="modal-header"> ');
    footerhtml.push('        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button> ');
    footerhtml.push('        <h4 class="modal-title">密码修改</h4> ');
    footerhtml.push('    </div> ');
    footerhtml.push('    <div class="modal-body"> ');
    footerhtml.push('        <form id="module-changepwd-form" class="m-t" role="form"> ');
    footerhtml.push('            <div class="form-group"><input name="data.oldpwd" type="password" class="form-control" placeholder="旧密码" required=""></div> ');
    footerhtml.push('            <div class="form-group"><input name="data.newpwd" type="password" class="form-control" placeholder="新密码" required=""></div> ');
    footerhtml.push('            <div class="form-group"><input name="data.newpwd2" type="password" class="form-control" placeholder="确认密码" required=""></div><br> ');
    footerhtml.push('            <button id="module-changepwd-submit" type="button" class="btn btn-theme btn-lg btn-block ">密码修改</button><br><br> ');
    footerhtml.push('        </form> ');
    footerhtml.push('    </div> ');
    footerhtml.push('</div>');
    document.getElementById("footer").innerHTML = footerhtml.join('');
    //---------------------------------- 用户登录框 绑定事件 -------------------------------------
    $('#module-login-dialog').on('show.bs.modal', function () { //
        $("#module-login-form")[0].reset();
    });
    //
    $('#module-login-submit').click(function (e) {
        var form = $("#module-login-form");
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
                        alert("用户或密码错误!");
                    } else if (data.retcode === 1002) {
                        alert("用户已被禁用!");
                    } else {
                        alert("登陆失败!");
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
    $('#module-changepwd-dialog').on('show.bs.modal', function () { //
        $("#module-changepwd-form")[0].reset();
    });
    //
    $('#module-changepwd-submit').click(function (e) {
        var form = $("#module-changepwd-form");
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
                        alert("新密码未加密!");
                    } else if (data.retcode === 1010020) {
                        alert("旧密码错误!");
                    } else {
                        alert("密码修改失败!");
                    }
                    return;
                }
                alert("密码已修改成功");
                $("#module-changepwd-dialog").modal('hide');
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
                                    <li><a data-toggle="modal" data-target="#module-changepwd-dialog">设 置</a></li> \
                                    <li><a href="javascript:void(0);">注 销</a></li> \
                                </ul>');
    }
    topbarhtml.push('       </div>');
    topbarhtml.push('   </li>');
    topbarhtml.push('   <li class=" hidden-xs" style="width: 3rem;">&nbsp;</li>');
    topbarhtml.push('</ul>');
    $("#topbar").html(topbarhtml.join(''));
    //-------------------------- side-menu ----------------------------------------------------------
    //menu data
    var menuhtml = [];
    menuhtml.push('<ul class="metismenu clearfix" id="menu">');
    menuhtml.push('    <li class="active"><a href="/index.html"><i class="fa fa-home"></i>  <span>工作台首页</span></a></li>');
    for (var i = 0; i < system_sysmenus.length; i++) {
        var submenu = system_sysmenus[i];
        menuhtml.push('<li><a href="' + (submenu.url || 'javascript:void(0);') + '"><i class="fa ' + submenu.iconCls + '"></i> <span>' + submenu.text + '</span><span class="fa arrow"></span></a>');
        if (submenu.children && submenu.children.length) {
            menuhtml.push('    <ul class="nav nav-second-level collapse">');
            for (var j = 0; j < submenu.children.length; j++) {
                var child = submenu.children[j];
                menuhtml.push('    <li><a href="' + child.url + '">' + (child.iconCls ? ('<i class="fa ' + child.iconCls + '"></i>  ') : '') + child.text + '</a></li>');
            }
            menuhtml.push('    </ul>');
        }
        menuhtml.push('</li>');
    }
    menuhtml.push('</ul>');
    menuhtml.push('<div class="nav-bottom clearfix">');
    menuhtml.push('    <a href="javascript:void(0);" style="border-right: 0px;"><i class="fa fa-lock"></i></a>');
    menuhtml.push('     <a href="javascript:void(0);" style="border-right: 0px;"><i class="fa fa-download"></i></a>');
    menuhtml.push('    <a href="javascript:void(0);" style="border-right: 0px;"><i class="fa fa-globe"></i></a>');
    menuhtml.push('    <a href="javascript:void(0);" style="border-right: 0px;"><i class="fa fa-phone"></i></a>');
    menuhtml.push('</div>');
    $("#side-menu").html(menuhtml.join(''));
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
        $('#module-login-dialog').modal('show');
    }
});