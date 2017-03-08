/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.redkale.oss.sys;

import java.io.IOException;
import javax.annotation.Resource;
import org.redkale.net.http.HttpRequest;
import org.redkale.net.http.HttpResponse;
import org.redkale.net.http.WebServlet;
import org.redkale.oss.base.*;
import static org.redkale.oss.base.Services.*;
import org.redkale.source.Flipper;

/**
 *
 * @author zhangjx
 */
@WebServlet(value = {"/user/*"}, moduleid = MODULE_USER, comment = "【OSS系统】员工管理模块")
public class UserMemberServlet extends BaseServlet {

    @Resource
    private UserMemberService service;

    @WebMapping(url = "/user/logout", comment = "用户退出登录")
    public void logout(HttpRequest req, HttpResponse resp) throws IOException {
        service.logout(req.getSessionid(false));
        resp.setHeader("Location", "/");
        resp.finish(302, null);
    }

    @AuthIgnore
    @WebMapping(url = "/user/myinfo", comment = "获取当前用户信息", result = "MemberInfo", results = {MemberInfo.class})
    public void myinfo(HttpRequest req, HttpResponse resp) throws IOException {
        resp.finishJson(currentUser(req));
    }

    @AuthIgnore
    @WebMapping(url = "/user/js/myinfo", comment = "获取当前用户信息(js格式)", result = "MemberInfo", results = {MemberInfo.class})
    public void myjsinfo(HttpRequest req, HttpResponse resp) throws IOException {
        resp.setContentType("application/javascript; charset=utf-8");
        resp.finish("var userself = " + convert.convertTo(currentUser(req)) + ";");
    }

    @AuthIgnore
    @WebMapping(url = "/user/login", comment = "账号方式登录", result = "RetResult")
    @WebParam(name = "bean", type = LoginBean.class, comment = "登录对象")
    public void login(HttpRequest req, HttpResponse resp) throws IOException {
        LoginBean bean = req.getJsonParameter(LoginBean.class, "bean");
        bean.setSessionid(req.getSessionid(true));
        bean.setWxlogin(false);
        LoginResult result = bean.emptySessionid() ? null : service.login(bean);
        String message;
        if (result == null) {
            message = "{\"success\":false, \"retcode\":1, \"message\":\"Login Error\"}";
        } else if (result.success()) {
            message = "{\"success\":true, \"retcode\":0, \"message\":\"Login Success\"}";
        } else {
            message = "{\"success\":false, \"retcode\":" + result.getRetcode() + ", \"message\":\"Login Error\"}";
        }
        resp.finish(message);
    }

    @WebMapping(url = "/user/changepwd", comment = "修改密码", result = "RetResult")
    @WebParam(name = "oldpwd", type = String.class, comment = "旧密码md5")
    @WebParam(name = "newpwd", type = String.class, comment = "新密码md5")
    public void changePwd(HttpRequest req, HttpResponse resp) throws IOException {
        String oldpwd = req.getParameter("oldpwd");
        String newpwd = req.getParameter("newpwd");
        int retcode = service.updatePwd(req.getSessionid(false), newpwd, oldpwd);
        resp.finish("{\"retcode\":" + retcode + ", \"success\": " + (retcode == 0) + "}");
    }

    @WebMapping(url = "/user/query", actionid = ACTION_QUERY, comment = "查询员工列表", result = "Sheet<UserMember>", results = {UserMember.class})
    @WebParam(name = "bean", type = UserMemberBean.class, comment = "过滤条件")
    @WebParam(name = "flipper", type = Flipper.class, comment = "翻页信息")
    public void query(HttpRequest req, HttpResponse resp) throws IOException {
        Flipper flipper = req.getFlipper();
        UserMemberBean bean = req.getJsonParameter(UserMemberBean.class, "bean");
        resp.finishJson(service.queryMember(flipper, bean));
    }

    @WebMapping(url = "/user/create", actionid = ACTION_CREATE, comment = "新增员工", result = "RetResult")
    @WebParam(name = "bean", type = UserMember.class, comment = "员工对象")
    public void create(HttpRequest req, HttpResponse resp) throws IOException {
        UserMember user = req.getJsonParameter(UserMember.class, "bean");
        service.createMember(user);
        resp.finish("{\"retcode\":0,\"success\":true}");
    }

    @WebMapping(url = "/user/update", actionid = ACTION_UPDATE, comment = "修改员工", result = "RetResult")
    @WebParam(name = "bean", type = UserMember.class, comment = "员工对象")
    public void update(HttpRequest req, HttpResponse resp) throws IOException {
        UserMember user = req.getJsonParameter(UserMember.class, "bean");
        service.updateMember(user);
        resp.finish("{\"retcode\":0,\"success\":true}");
    }

}
