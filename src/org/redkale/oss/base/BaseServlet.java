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

/**
 * boostrap 中dataTable需要设置才能获取flipper:
 *
 * <blockquote><pre>
 * $.extend($.fn.dataTable.defaults, {
 *
 *      preDrawCallback: function (settings) {
 *          $(settings.nTable).on('preXhr.dt', function (e, settings, data) {
 *              delete data.columns;
 *              delete data.search;
 *              if (data.length) {
 *                  var flipper = {limit: data.length, offset: data.start || 0};
 *                  if (data.sort) flipper.sort = data.sort + (data.order ? (" " + data.order) : "");
 *                  data.flipper = JSON.stringify(flipper);
 *              }
 *          });
 *          $(settings.nTable).on('xhr.dt', function (e, settings, json) {
 *              if (json) {
 *                  json.data = json.rows || [];
 *                  if (json.total &lt; 0) json.total = 0;
 *                  json.recordsTotal = json.total;
 *                  json.recordsFiltered = json.recordsTotal;
 *                  json.draw = new Date().getTime();
 *              }
 *          });
 *    }
 *
 * }
 * </pre></blockquote>
 *
 * @author zhangjx
 */
@HttpUserType(MemberInfo.class)
public class BaseServlet extends org.redkale.net.http.HttpServlet {

    protected final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    protected final boolean fine = logger.isLoggable(Level.FINE);

    protected final boolean finer = logger.isLoggable(Level.FINER);

    protected final boolean finest = logger.isLoggable(Level.FINEST);

    protected static final RetResult RET_UNLOGIN = OssRetCodes.retResult(OssRetCodes.RET_USER_UNLOGIN);

    protected static final RetResult RET_AUTHILLEGAL = OssRetCodes.retResult(OssRetCodes.RET_USER_AUTH_ILLEGAL);

    @Resource
    protected JsonConvert convert;

    @Resource
    private UserMemberService service;

    @Override
    public void preExecute(HttpRequest request, HttpResponse response) throws IOException {
        final String sessionid = request.getSessionid(false);
        if (sessionid != null) request.setCurrentUser(service.current(sessionid));
        response.nextEvent();
    }

    @Override
    public void authenticate(HttpRequest request, HttpResponse response) throws IOException {
        MemberInfo info = request.currentUser();
        if (info == null) {
            response.finishJson(RET_UNLOGIN);
            return;
        } else if (!info.checkAuth(request.getModuleid(), request.getActionid())) {
            response.finishJson(RET_AUTHILLEGAL);
            return;
        }
        response.nextEvent();
    }

}
