/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.redkale.oss.base;


import javax.persistence.*;
import org.redkale.convert.ConvertColumn;

/**
 * 内存中常驻的用户基本信息
 *
 * @author zhangjx
 */
public class UserInfo extends BasedEntity {

    //管理员类型
    public static final short TYPE_ADMIN = 8192;

    //普通用户
    public static final short TYPE_COMMON = 1;

    //正常
    public static final short STATUS_NORMAL = 10;

    //禁用
    public static final short STATUS_FORBID = 20;

    protected int userid;

    protected String account;

    protected String chname;

    protected String password;

    protected short type;

    protected short status;

    @Transient
    protected int[] options;

    @Override
    public int hashCode() {
        return this.userid;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) return false;
        return this.userid == ((UserInfo) obj).userid;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" + "userid=" + userid + ", account=" + account
                + ", chname=" + chname + ", password=" + password + ", type=" + type + ", status=" + status + '}';
    }

    public boolean checkAuth(int moduleid, int actionid) {
        if (moduleid == 0 || actionid == 0) return true;
        if (this.canAdmin()) return true;
        if (options == null || options.length == 0) return false;
        int optionid = moduleid * 10000 + actionid;
        for (int i : options) {
            if (i == optionid) return true;
        }
        return false;
    }

    @ConvertColumn(ignore = true)
    public boolean isStatusNormal() {
        return this.status == STATUS_NORMAL;
    }

    @ConvertColumn(ignore = true)
    public boolean isStatusFrobid() {
        return this.status == STATUS_FORBID;
    }

    @ConvertColumn(ignore = true)
    public boolean canAdmin() {
        return (this.type & TYPE_ADMIN) > 0;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getChname() {
        return chname;
    }

    public void setChname(String chname) {
        this.chname = chname;
    }

    @ConvertColumn(ignore = true)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public int[] getOptions() {
        return options;
    }

    public void setOptions(int[] options) {
        this.options = options;
    }

}
