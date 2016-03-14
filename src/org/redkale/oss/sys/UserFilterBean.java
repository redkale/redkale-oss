/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.redkale.oss.sys;

import org.redkale.source.FilterBean;



/**
 *
 * @author zhangjx
 */
public class UserFilterBean implements FilterBean {

    private int userid;

    private String account;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        if (account != null && !account.isEmpty()) this.account = account;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

}
