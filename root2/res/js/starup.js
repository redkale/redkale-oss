$(document).ready(function () {

    document.getElementById("footer").innerHTML = '<span>Copyright &copy; 2016. Redkale.</span>';


    //------------------------- topbar ----------------------------------------------------------
    var topbarhtml = [];
    topbarhtml.push('<div class="topbar-left"><div class="text-center"><a href="/index.html" class="logo">工作平台</a></div></div>');
    topbarhtml.push('<div class="pull-left menu-toggle"><i class="fa fa-bars"></i></div>');
    topbarhtml.push('<ul class="nav navbar-nav  top-right-nav hidden-xs">');
    topbarhtml.push('   <li class="dropdown profile-link hidden-xs">');
    topbarhtml.push('       <div class="clearfix" id="curruserel">');
    //userinfo 
    if (system_memberinfo) {
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
    
    //------------------------- side-menu ----------------------------------------------------------
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
});