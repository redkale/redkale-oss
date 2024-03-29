/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.redkale.oss.sys;

import java.util.List;
import org.redkale.oss.base.BaseEntity;
import org.redkale.persistence.*;
import org.redkale.source.FilterBean;

/**
 *
 * @author zhangjx
 */
@Entity(cacheable = true)
@Table(name = "sys_roletooption", comment = "角色操作关联表")
@Index(name = "unique", columns = {"roleid", "optionid"}, unique = true)
public class RoleToOption extends BaseEntity implements FilterBean {

    @Id
    @Column(comment = "[记录ID] 值=当前时间豪秒数(一般不会并发操作)")
    private long seqid;

    @Column(comment = "[角色ID]")
    private int roleid;

    @Column(comment = "[模块操作ID] optionid = moduleid * 10000 + actionid")
    private int optionid;

    @Column(updatable = false, comment = "[创建时间]")
    private long createTime;

    @Column(length = 255, comment = "[创建人]")
    private String creator = "";

    public static int[] getOptionids(final List<RoleToOption> options) {
        if (options == null) {
            return new int[0];
        }
        int[] ints = new int[options.size()];
        int i = 0;
        for (RoleToOption t : options) {
            ints[i++] = t.optionid;
        }
        return ints;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public long getSeqid() {
        return seqid;
    }

    public void setSeqid(long seqid) {
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
