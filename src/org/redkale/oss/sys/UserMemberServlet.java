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
import org.redkale.oss.base.BaseServlet;
import static org.redkale.oss.base.Services.*;
import org.redkale.source.Flipper;

/**
 *
 * @author zhangjx
 */
@WebServlet(value = {"/user/*"}, moduleid = MODULE_USER)
public class UserMemberServlet extends BaseServlet {

    @Resource
    private UserMemberService service;

    @WebAction(url = "/user/logout")
    public void logout(HttpRequest req, HttpResponse resp) throws IOException {
        service.logout(req.getSessionid(false));
        resp.setHeader("Location", "/");
        resp.finish(302, null);
    }

    @AuthIgnore
    @WebAction(url = "/user/myinfo")
    public void myinfo(HttpRequest req, HttpResponse resp) throws IOException {
        resp.finishJson(currentUser(req));
    }

    @AuthIgnore
    @WebAction(url = "/user/js/myinfo")
    public void myjsinfo(HttpRequest req, HttpResponse resp) throws IOException {
        resp.setContentType("application/javascript; charset=utf-8");
        resp.finish("var userself = " + convert.convertTo(currentUser(req)) + ";");
    }

    @AuthIgnore
    @WebAction(url = "/user/login")
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

    @WebAction(url = "/user/changepwd")
    public void changePwd(HttpRequest req, HttpResponse resp) throws IOException {
        String oldpwd = req.getParameter("oldpwd");
        String newpwd = req.getParameter("newpwd");
        int retcode = service.updatePwd(req.getSessionid(false), newpwd, oldpwd);
        resp.finish("{\"retcode\":" + retcode + ", \"success\": " + (retcode == 0) + "}");
    }

    @WebAction(url = "/user/query", actionid = ACTION_QUERY)
    public void query(HttpRequest req, HttpResponse resp) throws IOException {
        Flipper flipper = req.getFlipper();
        UserMemberBean bean = req.getJsonParameter(UserMemberBean.class, "bean");
        resp.finishJson(service.queryMember(flipper, bean));
    }

    @WebAction(url = "/user/create", actionid = ACTION_CREATE)
    public void create(HttpRequest req, HttpResponse resp) throws IOException {
        UserMember user = req.getJsonParameter(UserMember.class, "bean");
        service.createMember(user);
        resp.finish("{\"retcode\":0,\"success\":true}");
    }

    @WebAction(url = "/user/update", actionid = ACTION_UPDATE)
    public void update(HttpRequest req, HttpResponse resp) throws IOException {
        UserMember user = req.getJsonParameter(UserMember.class, "bean");
        service.updateMember(user);
        resp.finish("{\"retcode\":0,\"success\":true}");
    }

}
