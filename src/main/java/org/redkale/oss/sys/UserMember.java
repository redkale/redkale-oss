/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.redkale.oss.sys;

import java.security.*;
import org.redkale.convert.*;
import org.redkale.oss.base.*;
import static org.redkale.oss.base.BaseEntity.STATUS_NORMAL;
import org.redkale.persistence.*;
import org.redkale.util.*;

/**
 *
 * @author zhangjx
 */
@Entity(cacheable = true)
@Table(name = "sys_usermember", comment = "员工信息表", uniqueConstraints = {
    @UniqueConstraint(name = "unique", columnNames = {"account"})})
public class UserMember extends BaseEntity {

    private static final MessageDigest md5;

    static {
        MessageDigest d = null;
        try {
            d = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        md5 = d;
    }

    @Id
    @Column(comment = "[用户ID] 值从1000001开始")
    private int memberid;

    @Column(length = 64, comment = "[用户账号]")
    private String account = "";

    @Column(length = 255, comment = "[用户昵称]，通常为员工姓名", nullable = false)
    private String memberName = "";

    @Column(length = 64, comment = "密码", nullable = false)
    @ConvertColumn(ignore = true, type = ConvertType.JSON)
    private String password = "";

    @Column(comment = "[类型]；8192为管理员；1为普通员工；其他类型值需要按位移值来定义:2/4/8/16/32")
    private short type;

    @Column(comment = "[状态]: 10:正常;20:待审批;40:冻结;50:隐藏;60:关闭;70:过期;80:删除;")
    private short status = STATUS_NORMAL;

    @Column(length = 32, comment = "[手机号码]", nullable = false)
    private String mobile = "";

    @Column(length = 128, comment = "[邮箱地址]", nullable = false)
    private String email = "";

    @Column(length = 128, comment = "[微信账号]", nullable = false)
    private String weixin = "";

    @Column(length = 255, comment = "[备注]", nullable = false)
    private String remark = "";

    @Column(updatable = false, comment = "[创建时间]")
    private long createTime;

    @Column(comment = "[更新时间]")
    private long updateTime;

    @Transient
    private int[] roleids;

    public MemberInfo createMemberInfo() {
        MemberInfo info = new MemberInfo();
        info.setAccount(this.account);
        info.setMemberName(this.memberName);
        info.setPassword(this.password);
        info.setStatus(this.status);
        info.setType(this.type);
        info.setMemberid(this.memberid);
        return info;
    }

    public String passwordForMD5() {
        return md5IfNeed(this.password);
    }

    public static String md5IfNeed(String password) {
        if (password == null || password.trim().isEmpty()) {
            return "";
        }
        if (password.length() == 32) {
            return password; //已经MD5了
        }
        byte[] bytes = password.trim().getBytes();
        synchronized (md5) {
            bytes = md5.digest(bytes);
        }
        return new String(Utility.binToHex(bytes));
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

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int[] getRoleids() {
        return roleids;
    }

    public void setRoleids(int[] roleids) {
        this.roleids = roleids;
    }

}
