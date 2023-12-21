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
@Entity(cacheable = true)
@Table(name = "sys_roleinfo", comment = "角色信息表")
public class RoleInfo extends BaseEntity {

    @Id
    @Column(comment = "[角色ID] 值范围必须是2001-999，1xxx预留给框架")
    private int roleid;

    @Column(length = 64, comment = "[角色名称]", nullable = false)
    private String roleName;

    @Column(length = 255, comment = "[角色描述]", nullable = false)
    private String description = "";

    @Column(updatable = false, comment = "[创建时间]")
    private long createTime;

    @Column(length = 255, comment = "[创建人]", nullable = false)
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

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
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

}
