$(document).ready(function () {
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

    //userinfo 
    if (system_memberinfo) {
        $("#curruserel").html('<a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown"> \
                                    <img src="/res/images/user.png" alt="" class="pull-left" style="width:36px;height:36px;"> \
                                    <span>' + system_memberinfo.chname + '<br><em>' + system_memberinfo.account + '</em></span>    \
                                </a> \
                                <ul class="dropdown-menu profile-drop"> \
                                    <li><a href="javascript:void(0);">设 置</a></li> \
                                    <li><a href="javascript:void(0);">注 销</a></li> \
                                </ul>');
    }

    //menu data
    var menuhtml = [];
    menuhtml.push('<li class="active"><a href="/index.html"><i class="fa fa-home"></i>  <span>工作台首页</span></a></li>');
    for (var i = 0; i < system_sysmenus.length; i++) {
        var submenu = system_sysmenus[i];
        menuhtml.push('<li><a href="' + (submenu.url || 'javascript:void(0);') + '"><i class="fa ' + submenu.iconCls + '"></i> <span>' + submenu.text + '</span><span class="fa arrow"></span></a>');
        if (submenu.children && submenu.children.length) {
            menuhtml.push('<ul class="nav nav-second-level ' + (submenu.state == 'open' ? '' : 'collapse') + '">');
            for (var j = 0; j < submenu.children.length; j++) {
                var child = submenu.children[j];
                menuhtml.push('<li><a href="' + child.url + '">' + (child.iconCls ? ('<i class="fa ' + child.iconCls + '"></i>  ') : '') + child.text + '</a></li>');
            }
            menuhtml.push('</ul>');
        }
        menuhtml.push('</li>');
    }
    $("#menu").html(menuhtml.join(''));
    //metis menu
    $("#menu").metisMenu();

    $(".content-page,.side-menu").equalize({
    });

    //tooltips
    $(function () {
        $('[data-toggle="tooltip"]').tooltip();
        $('[data-toggle="popover"]').popover();
    });
});