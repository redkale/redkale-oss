/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.redkale.oss.sys;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import org.redkale.oss.base.BaseEntity;
import org.redkale.source.FilterBean;

/**
 * CREATE TABLE `sys_roletooption` (
 * `seqid` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长序号',
 * `roleid` int(11) NOT NULL DEFAULT '0' COMMENT '角色ID',
 * `optionid` int(11) NOT NULL DEFAULT '0' COMMENT 'optionid = moduleid * 10000 + actionid',
 * `createtime` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
 * `creator` varchar(255) NOT NULL DEFAULT '' COMMENT '创建人',
 * PRIMARY KEY (`seqid`),
 * UNIQUE KEY `unique` (`roleid`,`optionid`)
 * ) ENGINE=InnoDB AUTO_INCREMENT=1000001 DEFAULT CHARSET=utf8;
 *
 * @author zhangjx
 */
@Entity
@Cacheable
@Table(name = "sys_roletooption")
public class RoleToOption extends BaseEntity implements FilterBean, Serializable {

    @Id
    @GeneratedValue
    private int seqid;

    @Column(updatable = false)
    private int roleid;

    //optionid = moduleid * 10000 + actionid
    @Column(updatable = false)
    private int optionid;

    @Column(updatable = false)
    private long createtime;

    @Column(updatable = false)
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
