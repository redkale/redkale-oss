/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.redkale.oss.sys;

import org.redkale.oss.base.BaseEntity;
import org.redkale.persistence.*;

/**
 *
 * @author zhangjx
 */
@Entity
@Cacheable
@Table(name = "sys_usertorole", comment = "员工角色关联表", uniqueConstraints = {
    @UniqueConstraint(name = "unique", columnNames = {"roleid", "memberid"})})
public class UserToRole extends BaseEntity {

    @Id
    @Column(comment = "[记录ID] 值=当前时间秒数(一般不会并发操作)")
    private int seqid;

    @Column(comment = "[角色ID]")
    private int roleid;

    @Column(comment = "[用户ID]")
    private int memberid;

    @Column(updatable = false, comment = "[创建时间]")
    private long createtime;

    @Column(length = 255, comment = "[创建人]")
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
