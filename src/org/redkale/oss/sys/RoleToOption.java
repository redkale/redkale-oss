/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.redkale.oss.sys;

import org.redkale.oss.base.BasedEntity;
import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import org.redkale.source.FilterBean;
import org.redkale.util.AutoLoad;

/**
 * CREATE TABLE `roletooption` (
 * `seqid` int(11) NOT NULL AUTO_INCREMENT,
 * `sysid` int(11) NOT NULL,
 * `roleid` int(11) NOT NULL,
 * `optionid` int(11) NOT NULL COMMENT 'optionid = moduleid * 10000 + actionid',
 * `createtime` bigint(20) NOT NULL,
 * `creator` varchar(255) NOT NULL,
 * PRIMARY KEY (`seqid`)
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 *
 * @author zhangjx
 */
@Entity
@AutoLoad
@Cacheable
public class RoleToOption extends BasedEntity implements FilterBean, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seqid;

    @Column(updatable = false)
    private int sysid;

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

    public int getSysid() {
        return sysid;
    }

    public void setSysid(int sysid) {
        this.sysid = sysid;
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
