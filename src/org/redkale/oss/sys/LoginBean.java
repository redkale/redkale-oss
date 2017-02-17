/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.redkale.oss.sys;

import javax.persistence.*;
import org.redkale.oss.base.BaseBean;
import org.redkale.source.FilterBean;
import org.redkale.util.Comment;

/**
 *
 * @author zhangjx
 */
@Comment("员工登录参数类")
public final class LoginBean extends BaseBean implements FilterBean {

    @Column(length = 64, comment = "用户账号")
    private String account;

    @Column(length = 64, comment = "密码")
    private String password;

    @Transient
    @Comment("是否微信登录")
    private boolean wxlogin = false;

    @Transient
    @Comment("SESSION会话ID")
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
