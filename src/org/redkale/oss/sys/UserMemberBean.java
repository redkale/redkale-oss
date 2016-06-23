/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.redkale.oss.sys;

import org.redkale.oss.base.BaseBean;
import org.redkale.source.*;
import static org.redkale.source.FilterExpress.*;

/**
 *
 * @author zhangjx
 */
public class UserMemberBean extends BaseBean implements FilterBean {

    private int userid;

    @FilterColumn(express = LIKE)
    private String account;

    @FilterColumn(express = LIKE)
    private String chname;

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

    public String getChname() {
        return chname;
    }

    public void setChname(String chname) {
        if (chname != null && !chname.isEmpty()) this.chname = chname;
    }

}
