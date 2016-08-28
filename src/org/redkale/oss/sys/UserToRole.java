/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.redkale.oss.sys;

import javax.persistence.*;
import org.redkale.oss.base.BaseEntity;

/**
 * CREATE TABLE `sys_usertorole` (
 `seqid` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长序号',
 `roleid` int(11) NOT NULL DEFAULT '0' COMMENT '角色ID',
 `memberid` int(11) NOT NULL DEFAULT '0' COMMENT '用户ID',
 `createtime` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
 `creator` varchar(255) NOT NULL DEFAULT '' COMMENT '创建人',
 PRIMARY KEY (`seqid`),
 UNIQUE KEY `unique` (`roleid`,`memberid`)
 ) ENGINE=InnoDB AUTO_INCREMENT=10000001 DEFAULT CHARSET=utf8;
 *
 * @author zhangjx
 */
@Entity
@Cacheable
@Table(name = "sys_usertorole")
public class UserToRole extends BaseEntity {

    @Id
    @GeneratedValue
    private int seqid;

    @Column(updatable = false)
    private int roleid;

    @Column(updatable = false)
    private int memberid;

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

    public int getMemberid() {
        return memberid;
    }

    public void setMemberid(int memberid) {
        this.memberid = memberid;
    }
}
