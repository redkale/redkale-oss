/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.redkale.oss.base;

import java.util.Arrays;
import javax.persistence.*;
import org.redkale.convert.ConvertColumn;

/**
 * 内存中常驻的用户基本信息
 *
 * @author zhangjx
 */
public class MemberInfo extends BaseEntity {

    //管理员类型
    public static final short TYPE_ADMIN = 8192;

    //普通用户
    public static final short TYPE_COMMON = 1;

    protected int memberid;

    protected String account;

    protected String membername;

    protected String password;

    protected short type;

    protected short status;

    @Transient
    protected int[] options;

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" + "memberid=" + memberid + ", account=" + account
            + ", membername=" + membername + ", password=" + password + ", options=" + (options == null ? "[]" : Arrays.toString(options)) + ", type=" + type + ", status=" + status + '}';
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
    public boolean isStatusFreeze() {
        return this.status == STATUS_FREEZE;
    }

    @ConvertColumn(ignore = true)
    public boolean canAdmin() {
        return (this.type & TYPE_ADMIN) > 0;
    }

    public int getMemberid() {
        return memberid;
    }

    public void setMemberid(int memberid) {
        this.memberid = memberid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getMembername() {
        return membername;
    }

    public void setMembername(String membername) {
        this.membername = membername;
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
