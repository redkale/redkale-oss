/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.redkale.oss.sys;

import org.redkale.oss.base.MemberInfo;

/**
 *
 * @author zhangjx
 */
public class LoginResult {

    private String sessionid;

    private MemberInfo user;

    private int retcode = -1; //返回码， 0表示登陆成功

    @Override
    public String toString() {
        return "LoginResult{" + "sessionid=" + sessionid + ", user=" + user + ", retcode=" + retcode + '}';
    }

    public boolean success() {
        return retcode == 0;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public MemberInfo getUser() {
        return user;
    }

    public void setUser(MemberInfo user) {
        this.user = user;
    }

    public int getRetcode() {
        return retcode;
    }

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }
}
