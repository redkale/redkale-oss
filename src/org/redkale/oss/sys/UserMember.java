/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.redkale.oss.sys;

import java.security.*;
import javax.persistence.*;
import org.redkale.convert.ConvertColumn;
import org.redkale.convert.ConvertType;
import org.redkale.oss.base.BaseEntity;
import org.redkale.oss.base.UserInfo;
import static org.redkale.oss.base.UserInfo.STATUS_NORMAL;
import org.redkale.util.AutoLoad;
import org.redkale.util.Utility;

/**

CREATE TABLE `usermember` (
  `userid` int(10) NOT NULL AUTO_INCREMENT,
  `account` varchar(64) NOT NULL DEFAULT '',
  `chname` varchar(255) NOT NULL DEFAULT '',
  `password` varchar(64) NOT NULL DEFAULT '',
  `type` smallint(5) NOT NULL DEFAULT '0',
  `status` smallint(5) NOT NULL DEFAULT '0',
  `mobile` varchar(32) NOT NULL DEFAULT '',
  `email` varchar(128) NOT NULL DEFAULT '',
  `remark` varchar(255) NOT NULL DEFAULT '',
  `createtime` bigint(20) NOT NULL DEFAULT '0',
  `updatetime` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`userid`),
  UNIQUE KEY `singel` (`account`)
) ENGINE=InnoDB AUTO_INCREMENT=1000001 DEFAULT CHARSET=utf8
 *
 * @author zhangjx
 */
@Entity
@AutoLoad
@Cacheable
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userid;

    @Column(updatable = false)
    private String account;

    private String chname;

    @Column(updatable = false)
    private String password = "";

    @Column(updatable = false)
    private short type;

    @Column(updatable = false)
    private short status = STATUS_NORMAL;

    private String mobile;

    private String email;

    @Column(updatable = false)
    private long createtime;

    private long updatetime;

    private String remark;

    @Transient
    private int[] roleids;

    @Override
    public int hashCode() {
        return this.userid;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        return (this.userid != ((UserMember) obj).userid);
    }

    public UserInfo createUserInfo() {
        UserInfo info = new UserInfo();
        info.setAccount(this.account);
        info.setChname(this.chname);
        info.setPassword(this.password);
        info.setStatus(this.status);
        info.setType(this.type);
        info.setUserid(this.userid);
        return info;
    }

    public String passwordForMD5() {
        return md5IfNeed(this.password);
    }

    public static String md5IfNeed(String password) {
        if (password == null || password.trim().isEmpty()) return "";
        if (password.length() == 32) return password; //已经MD5了
        byte[] bytes = password.trim().getBytes();
        synchronized (md5) {
            bytes = md5.digest(bytes);
        }
        return new String(Utility.binToHex(bytes));
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

    @ConvertColumn(ignore = true, type = ConvertType.JSON)
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

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public long getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(long updatetime) {
        this.updatetime = updatetime;
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
