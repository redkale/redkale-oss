/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.redkale.oss.sys;

import javax.persistence.*;
import org.redkale.oss.base.BaseEntity;
import org.redkale.source.DistributeGenerator;

/**
 * CREATE TABLE `sys_roleinfo` (
 * `roleid` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
 * `rolename` varchar(64) NOT NULL DEFAULT '' COMMENT '角色名称',
 * `description` varchar(255) NOT NULL DEFAULT '' COMMENT '角色描述',
 * `createtime` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
 * `creator` varchar(255) NOT NULL DEFAULT '' COMMENT '创建人',
 * PRIMARY KEY (`roleid`)
 * ) ENGINE=InnoDB AUTO_INCREMENT=2001 DEFAULT CHARSET=utf8;
 *
 * @author zhangjx
 */
@Entity
@Cacheable
@Table(name = "sys_roleinfo")
public class RoleInfo extends BaseEntity {

    @Id
    @GeneratedValue
    @DistributeGenerator(initialValue = 2001, allocationSize = 1)
    private int roleid;

    private String rolename;

    private String description = "";

    @Column(updatable = false)
    private long createtime;

    @Column(updatable = false)
    private String creator = "";

    public int getRoleid() {
        return roleid;
    }

    public void setRoleid(int roleid) {
        this.roleid = roleid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
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

}
