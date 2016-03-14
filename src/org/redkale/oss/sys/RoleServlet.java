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
import org.redkale.oss.base.BasedServlet;
import static org.redkale.oss.base.Services.*;

/**
 *
 * @author zhangjx
 */
@WebServlet({"/role/*"})
public final class RoleServlet extends BasedServlet {

    @Resource
    private RoleService service;

    @WebAction(actionid = ACTION_UPDATE, url = "/role/upduserole")
    public void upduserole(HttpRequest req, HttpResponse resp) throws IOException {
        resp.finishJson(service.updateUserToRole(currentUser(req), req.getJsonParameter(int[].class, "delseqids"), req.getJsonParameter(UserToRole[].class, "data")));
    } 

    @WebAction(actionid = ACTION_QUERY, url = "/role/qryuserole")
    public void qryuserole(HttpRequest req, HttpResponse resp) throws IOException {
        resp.finishJson(service.queryUserToRole(findFlipper(req), req.getJsonParameter(UserFilterBean.class, "bean")));
    }

    @WebAction(actionid = ACTION_UPDATE, url = "/role/updoption")
    public void updoption(HttpRequest req, HttpResponse resp) throws IOException {
        resp.finishJson(service.updateRoleToOption(currentUser(req), req.getJsonParameter(int[].class, "delseqids"), req.getJsonParameter(RoleToOption[].class, "data")));
    }

    @WebAction(actionid = ACTION_QUERY, url = "/role/qryoption")
    public void qryoption(HttpRequest req, HttpResponse resp) throws IOException {
        resp.finishJson(service.queryRoleToOption(req.getJsonParameter(RoleToOption.class, "bean")));
    } 

    @WebAction(actionid = ACTION_QUERY, url = "/role/query")
    public void qryrole(HttpRequest req, HttpResponse resp) throws IOException {
        resp.finishJson(service.queryRoleInfo(findFlipper(req), null));
    }

    @WebAction(actionid = ACTION_CREATE, url = "/role/create")
    public void crtrole(HttpRequest req, HttpResponse resp) throws IOException {
        resp.finishJson(service.createRoleInfo(currentUser(req), req.getJsonParameter(RoleInfo.class, "data")));
    }

    @WebAction(actionid = ACTION_UPDATE, url = "/role/update")
    public void updrole(HttpRequest req, HttpResponse resp) throws IOException {
        resp.finishJson(service.updateRoleInfo(req.getJsonParameter(RoleInfo.class, "data")));
    }
}
