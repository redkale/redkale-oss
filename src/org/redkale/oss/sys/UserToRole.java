/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.redkale.oss.sys;

import org.redkale.oss.base.BaseEntity;
import javax.persistence.*;
import org.redkale.util.AutoLoad;

/**
 * CREATE TABLE `usertorole` (
 `seqid` int(11) NOT NULL AUTO_INCREMENT,
 `sysid` int(11) NOT NULL,
 `roleid` int(11) NOT NULL,
 `userid` int(11) NOT NULL,
 `createtime` bigint(20) NOT NULL,
 `creator` varchar(255) NOT NULL,
 PRIMARY KEY (`seqid`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 *
 * @author zhangjx
 */
@Entity
@AutoLoad
@Cacheable
public class UserToRole extends BaseEntity {

    @Id
    private int seqid;

    @Column(updatable = false)
    private int roleid;

    @Column(updatable = false)
    private int userid;

    @Column(updatable = false)
    private long createtime;

    @Column(updatable = false)
    private String creator = "";

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public int getSeqid() {
        return seqid;
    }

    public void setSeqid(int seqid) {
        this.seqid = seqid;
    }

    public int getRoleid() {
        return roleid;
    }

    public void setRoleid(int roleid) {
        this.roleid = roleid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
