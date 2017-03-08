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
import org.redkale.service.RetResult;
import org.redkale.source.Flipper;

/**
 *
 * @author zhangjx
 */
@WebServlet(value = {"/role/*"}, moduleid = MODULE_ROLE, comment = "【OSS系统】角色管理模块")
public final class RoleServlet extends BaseServlet {

    @Resource
    private RoleService service;

    @WebMapping(actionid = ACTION_UPDATE, url = "/role/upduserole", comment = "更新角色员工用户", result = "int[]")
    @WebParam(name = "delroleids", type = int[].class, comment = "待删除员工的角色ID")
    @WebParam(name = "delmemberid", type = int[].class, comment = "待删除的员工ID")
    @WebParam(name = "bean", type = UserToRole[].class, comment = "待新增的角色与员工关系对象")
    public void upduserole(HttpRequest req, HttpResponse resp) throws IOException {
        int[] delroleids = req.getJsonParameter(int[].class, "delroleids");
        resp.finishJson(service.updateUserToRole(currentUser(req), req.getIntParameter("delmemberid", 0), delroleids, req.getJsonParameter(UserToRole[].class, "bean")));
    }

    @WebMapping(actionid = ACTION_QUERY, url = "/role/qryuserole", comment = "查询角色员工关系列表", result = "Sheet<UserToRole>")
    @WebParam(name = "bean", type = UserMemberBean.class, comment = "过滤条件")
    @WebParam(name = "flipper", type = Flipper.class, comment = "翻页信息")
    public void qryuserole(HttpRequest req, HttpResponse resp) throws IOException {
        resp.finishJson(service.queryUserToRole(req.getJsonParameter(UserMemberBean.class, "bean"), req.getFlipper()));
    }

    @WebMapping(actionid = ACTION_UPDATE, url = "/role/updoption", comment = "更新角色的操作权限", result = "int[]")
    @WebParam(name = "delseqids", type = int[].class, comment = "待删除的角色操作ID")
    @WebParam(name = "bean", type = RoleToOption[].class, comment = "待新增的角色与操作关系对象")
    public void updoption(HttpRequest req, HttpResponse resp) throws IOException {
        resp.finishJson(service.updateRoleToOption(currentUser(req), req.getJsonParameter(int[].class, "delseqids"), req.getJsonParameter(RoleToOption[].class, "bean")));
    }

    @WebMapping(actionid = ACTION_QUERY, url = "/role/qryoption", comment = "查询角色操作关系列表", result = "Sheet<RoleToOption>")
    @WebParam(name = "bean", type = RoleToOption.class, comment = "过滤条件")
    public void qryoption(HttpRequest req, HttpResponse resp) throws IOException {
        resp.finishJson(service.queryRoleToOption(req.getJsonParameter(RoleToOption.class, "bean")));
    }

    @WebMapping(actionid = ACTION_QUERY, url = "/role/query", comment = "查询角色列表", result = "Sheet<RoleInfo>")
    @WebParam(name = "flipper", type = Flipper.class, comment = "翻页信息")
    public void qryrole(HttpRequest req, HttpResponse resp) throws IOException {
        resp.finishJson(service.queryRoleInfo(null, req.getFlipper()));
    }

    @WebMapping(actionid = ACTION_CREATE, url = "/role/create", comment = "新增角色", result = "int")
    @WebParam(name = "bean", type = RoleInfo.class, comment = "角色对象")
    public void crtrole(HttpRequest req, HttpResponse resp) throws IOException {
        resp.finishJson(service.createRoleInfo(currentUser(req), req.getJsonParameter(RoleInfo.class, "bean")));
    }

    @WebMapping(actionid = ACTION_UPDATE, url = "/role/update", comment = "修改角色", result = "RetResult")
    @WebParam(name = "bean", type = RoleInfo.class, comment = "角色对象")
    public void updrole(HttpRequest req, HttpResponse resp) throws IOException {
        service.updateRoleInfo(req.getJsonParameter(RoleInfo.class, "bean"));
        resp.finishJson(RetResult.success());
    }
}
