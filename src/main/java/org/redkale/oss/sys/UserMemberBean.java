/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.redkale.oss.sys;

import org.redkale.annotation.Comment;
import org.redkale.oss.base.BaseBean;
import org.redkale.persistence.Column;
import org.redkale.source.*;
import static org.redkale.source.FilterExpress.*;

/**
 *
 * @author zhangjx
 */
@Comment("员工过滤条件类")
public class UserMemberBean extends BaseBean implements FilterBean {

    @Column(comment ="[用户ID] 值从1000001开始")
    private int memberid;

    @FilterColumn(express = LIKE)
    @Column(length = 64, comment = "用户账号")
    private String account;

    @FilterColumn(express = LIKE)
    @Column(length = 255, comment = "员工姓名")
    private String membername;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        if (account != null && !account.isEmpty()) this.account = account;
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
        if (membername != null && !membername.isEmpty()) this.membername = membername;
    }

}
