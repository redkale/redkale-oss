/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.redkale.oss.base;

import java.io.IOException;
import java.util.logging.*;
import javax.annotation.Resource;
import org.redkale.convert.json.JsonConvert;
import org.redkale.net.http.*;
import org.redkale.oss.sys.UserMemberService;
import org.redkale.service.RetResult;
import org.redkale.source.Flipper;

/**
 *
 * @author zhangjx
 */
public class BaseServlet extends org.redkale.net.http.BasedHttpServlet {

    protected final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    protected final boolean fine = logger.isLoggable(Level.FINE);

    protected final boolean finer = logger.isLoggable(Level.FINER);

    protected final boolean finest = logger.isLoggable(Level.FINEST);

    @Resource
    protected JsonConvert convert;

    @Resource
    private UserMemberService service;

    @Override
    public boolean authenticate(int module, int actionid, HttpRequest request, HttpResponse response) throws IOException {
        MemberInfo info = currentMember(request);
        if (info == null) {
            response.addHeader("retcode", RetCodes.RET_USER_UNLOGIN);
            response.addHeader("retmessage", "Not Login");
            response.setStatus(203);
            response.finish("{'success':false, 'message':'Not Login'}");
            return false;
        } else if (!info.checkAuth(module, actionid)) {
            response.addHeader("retcode", RetCodes.RET_USER_AUTH_ILLEGAL);
            response.addHeader("retmessage", "No Authority");
            response.setStatus(203);
            response.finish("{'success':false, 'message':'No Authority'}");
            return false;
        }
        return true;
    }

    protected final MemberInfo currentMember(HttpRequest req) throws IOException {
        final String sessionid = req.getSessionid(false);
        if (sessionid == null) return null;
        MemberInfo user = (MemberInfo) req.getAttribute("$_CURRENT_MEMBER");
        if (user == null) {
            user = service.current(sessionid);
            req.setAttribute("$_CURRENT_MEMBER", user);
        }
        return user;
    }

    protected Flipper findFlipper(HttpRequest request) {  //bootstrap datatable
        int pageSize = request.getIntParameter("length", Flipper.DEFAULT_PAGESIZE);
        int pageNo = (request.getIntParameter("start", 0) / pageSize) + 1;
        String sort = request.getParameter("sort");
        String order = request.getParameter("order");
        String sortColumn = (sort == null ? "" : ((order == null ? sort : (sort + " " + order.toUpperCase()))));
        return new Flipper(pageSize, pageNo, sortColumn);
    }

    /**
     * 将对象以js方式输出
     *
     * @param resp   HTTP响应对象
     * @param var    对象名
     * @param result 对象
     */
    protected void sendJsResult(HttpResponse resp, String var, Object result) {
        resp.setContentType("application/javascript; charset=utf-8");
        resp.finish("var " + var + " = " + convert.convertTo(result) + ";");
    }

    /**
     * 将结果对象输出， 异常的结果在HTTP的header添加retcode值
     *
     * @param resp HTTP响应对象
     * @param ret  结果对象
     */
    protected void sendRetResult(HttpResponse resp, RetResult ret) {
        if (!ret.isSuccess()) resp.addHeader("retcode", ret.getRetcode());
        resp.finishJson(ret);
    }

    /**
     * 将结果对象输出， 异常的结果在HTTP的header添加retcode值
     *
     * @param resp    HTTP响应对象
     * @param retcode 结果码
     */
    protected void sendRetcode(HttpResponse resp, int retcode) {
        if (retcode != 0) resp.addHeader("retcode", retcode);
        resp.finish("{\"retcode\":" + retcode + ", \"success\": " + (retcode == 0) + "}");
    }
}
