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
import org.redkale.source.Flipper;

/**
 *
 * @author zhangjx
 */
public class BaseServlet extends org.redkale.net.http.BasedHttpServlet {

    protected final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    protected final boolean finest = logger.isLoggable(Level.FINEST);

    protected final boolean fine = logger.isLoggable(Level.FINE);

    @Resource
    protected JsonConvert convert;

    @Resource
    private UserMemberService service;

    @Override
    public boolean authenticate(int moduleid, int actionid, HttpRequest request, HttpResponse response) throws IOException {
        MemberInfo info = currentUser(request);
        if (info == null) {
            response.addHeader("RetCode", "1001");
            response.addHeader("RetMessage", "Not Login");
            response.setStatus(203);
            response.finish("{'success':false, 'message':'Not Login'}");
            return false;
        } else if (!info.checkAuth(moduleid, actionid)) {
            response.addHeader("RetCode", "2001");
            response.addHeader("RetMessage", "No Authority");
            response.setStatus(203);
            response.finish("{'success':false, 'message':'No Authority'}");
        }
        return true;
    }

    protected final MemberInfo currentUser(HttpRequest req) throws IOException {
        final String sessionid = req.getSessionid(false);
        if (sessionid == null) return null;
        MemberInfo user = (MemberInfo) req.getAttribute("CurrentMemberInfo");
        if (user == null) {
            user = service.current(sessionid);
            req.setAttribute("CurrentMemberInfo", user);
        }
        return user;
    }

    protected Flipper findFlipper(HttpRequest request) {  //easyUI
        int pageSize = request.getIntParameter("rows", request.getIntParameter("length", Flipper.DEFAULT_PAGESIZE));
        int pageNo = request.getIntParameter("page", request.getIntParameter("start", 0) / pageSize + 1);
        String sort = request.getParameter("sort");
        String order = request.getParameter("order");
        String sortColumn = (sort == null ? "" : ((order == null ? sort : (sort + " " + order.toUpperCase()))));
        return new Flipper(pageSize, pageNo, sortColumn);
    }

    protected void sendJsResult(HttpResponse resp, String var, Object result) {
        resp.setContentType("application/javascript; charset=utf-8");
        resp.finish("var " + var + " = " + convert.convertTo(result) + ";");
    }

    protected void sendJsResult(HttpResponse resp, JsonConvert jsonConvert, String var, Object result) {
        resp.setContentType("application/javascript; charset=utf-8");
        resp.finish("var " + var + " = " + jsonConvert.convertTo(result) + ";");
    }
}
