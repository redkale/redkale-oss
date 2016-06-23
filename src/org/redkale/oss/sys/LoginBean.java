/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.redkale.oss.sys;

import javax.persistence.Transient;
import org.redkale.oss.base.BaseBean;
import org.redkale.source.FilterBean;

/**
 *
 * @author zhangjx
 */
public final class LoginBean extends BaseBean implements FilterBean {

    private String account;

    private String password;

    @Transient
    private boolean wxlogin = false;

    @Transient
    private String sessionid = "";

    @Override
    public String toString() {
        return "LoginBean{account=" + account + ", password=" + password + ", sessionid=" + sessionid + ", wxlogin=" + wxlogin + '}';
    }

    public boolean emptyAccount() {
        return this.account == null || this.account.isEmpty();
    }

    public boolean emptyPassword() {
        return this.password == null || this.password.isEmpty();
    }

    public boolean emptySessionid() {
        return this.sessionid == null || this.sessionid.isEmpty();
    }

    public boolean isWxlogin() {
        return wxlogin;
    }

    public void setWxlogin(boolean wxlogin) {
        this.wxlogin = wxlogin;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
