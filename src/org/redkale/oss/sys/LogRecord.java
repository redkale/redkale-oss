/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.redkale.oss.sys;

import javax.persistence.*;
import org.redkale.oss.base.BaseEntity;

/**
 *
 * @author zhangjx
 */
@Entity
@Table(name = "sys_logrecord")
public class LogRecord extends BaseEntity {

    @Id
    @GeneratedValue
    private long logid;

    private int memberid;//操作用户ID',

    private String membername;// '操作用户姓名', 

    private int moduleid;// '模块ID',

    private int actionid;// '操作ID',

    private String requri = "";//请求的URL

    private String reqparams = "";// '请求参数',

    private String respobj = "";// '输出对象信息',

    private String logmsg = "";// '操作信息描述',

    private String exceptmsg = "";// '操作异常信息描述',

    private long createtime;// '创建时间',

    public LogRecord() {
    }

    public long getLogid() {
        return logid;
    }

    public void setLogid(long logid) {
        this.logid = logid;
    }

    public int getMemberid() {
        return memberid;
    }

    public void setMemberid(int memberid) {
        this.memberid = memberid;
    }

    public String getMembername() {
        return membername;
    }

    public void setMembername(String membername) {
        this.membername = membername;
    }

    public int getModuleid() {
        return moduleid;
    }

    public void setModuleid(int moduleid) {
        this.moduleid = moduleid;
    }

    public int getActionid() {
        return actionid;
    }

    public void setActionid(int actionid) {
        this.actionid = actionid;
    }

    public String getRequri() {
        return requri;
    }

    public void setRequri(String requri) {
        this.requri = requri;
    }

    public String getReqparams() {
        return reqparams;
    }

    public void setReqparams(String reqparams) {
        this.reqparams = reqparams;
    }

    public String getRespobj() {
        return respobj;
    }

    public void setRespobj(String respobj) {
        this.respobj = respobj;
    }

    public String getLogmsg() {
        return logmsg;
    }

    public void setLogmsg(String logmsg) {
        this.logmsg = logmsg;
    }

    public String getExceptmsg() {
        return exceptmsg;
    }

    public void setExceptmsg(String exceptmsg) {
        this.exceptmsg = exceptmsg;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

}
