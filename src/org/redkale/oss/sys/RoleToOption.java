/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.redkale.oss.sys;

import java.util.List;
import javax.persistence.*;
import org.redkale.oss.base.BaseEntity;

/**
 *
 * @author zhangjx
 */
@Entity
@Cacheable
@Table(name = "sys_roletooption", comment = "角色操作关联表", uniqueConstraints = {
    @UniqueConstraint(name = "unique", columnNames = {"roleid", "optionid"})})
public class RoleToOption extends BaseEntity {

    @Id
    @Column(comment = "[记录ID] 值=当前时间秒数(一般不会并发操作)")
    private int seqid;

    @Column(comment = "[角色ID]")
    private int roleid;

    @Column(comment = "[模块操作ID] optionid = moduleid * 10000 + actionid")
    private int optionid;

    @Column(updatable = false, comment = "[创建时间]")
    private long createtime;

    @Column(length = 255, comment = "[创建人]")
    private String creator = "";

    public static int[] getOptionids(final List<RoleToOption> options) {
        if (options == null) return new int[0];
        int[] ints = new int[options.size()];
        int i = 0;
        for (RoleToOption t : options) {
            ints[i++] = t.optionid;
        }
        return ints;
    }

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

    public int getOptionid() {
        return optionid;
    }

    public void setOptionid(int optionid) {
        this.optionid = optionid;
    }
}
