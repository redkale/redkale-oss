/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.redkale.oss.wx;

import java.io.*;
import java.util.*;
import javax.annotation.*;
import org.redkale.net.http.HttpRequest;
import org.redkale.net.http.HttpResponse;
import org.redkale.net.http.WebServlet;
import org.redkale.oss.base.BasedServlet;
import org.redkale.oss.sys.LoginBean;
import org.redkale.oss.sys.UserMemberService;
import org.redkale.plugins.weixin.WeiXinQYService;

/**
 *
 * @author zhangjx
 */
@WebServlet({"/wx/*"})
public class WeiXinQYServlet extends BasedServlet {

    @Resource
    private UserMemberService uemberService;

    @Resource
    private WeiXinQYService service;

    @AuthIgnore
    @WebAction(url = "/wx/login")
    public void login(HttpRequest req, HttpResponse resp) throws IOException {
        if (finest) logger.finest(req.toString());
        if (currentUser(req) == null) {
            Map<String, String> map = service.getQYUserCode(req.getParameter("code"), req.getParameter("agentid"));
            logger.finest("wx.login : " + map);
            LoginBean bean = new LoginBean();
            bean.setAccount(map.get("UserId"));
            bean.setSessionid(req.getSessionid(true));
            bean.setWxlogin(true);
            uemberService.login(bean);
        }
        resp.setHeader("Location", req.getParameter("url", "/"));
        resp.finish(302, null);
    }

    @AuthIgnore
    @WebAction(url = "/wx/verifyqy")
    public void verifyqy(HttpRequest req, HttpResponse resp) throws IOException {
        if (finest) logger.finest(req.toString());
        resp.finish(service.verifyQYURL(req.getParameter("msg_signature"), req.getParameter("timestamp"), req.getParameter("nonce"), req.getParameter("echostr")));
    }

}
