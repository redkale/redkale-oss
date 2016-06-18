/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.redkale.oss.sys;

import javax.annotation.Resource;
import org.redkale.net.http.WebServlet;
import org.redkale.oss.base.BaseServlet;
import org.redkale.util.AutoLoad;

/**
 *
 * @author zhangjx
 */
@AutoLoad(false)
@WebServlet({"/log/*"})
public class LogRecordServlet extends BaseServlet {

    @Resource
    private LogRecordService service;
}
