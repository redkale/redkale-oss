/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.redkale.oss.sys;

import javax.persistence.*;
import org.redkale.oss.base.BaseEntity;

/**
 * @author zhangjx
 */
@Entity
@Cacheable
@Table(name = "sys_moduleinfo", comment = "模块信息表")
public class ModuleInfo extends BaseEntity {

    @Id
    @Column(comment = "[模块ID] 值范围必须是2001-9999，1xx预留给框架")
    private int moduleid;

    @Column(length = 64, comment = "[模块名称]")
    private String modulename;

    public ModuleInfo() {
    }

    public ModuleInfo(int moduleid, String name) {
        this.moduleid = moduleid;
        this.modulename = name;
    }

    @Override
    public int hashCode() {
        return this.moduleid;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final ModuleInfo other = (ModuleInfo) obj;
        return (this.moduleid == other.moduleid);
    }

    public int getModuleid() {
        return moduleid;
    }

    public void setModuleid(int moduleid) {
        this.moduleid = moduleid;
    }

    public String getModulename() {
        return modulename;
    }

    public void setModulename(String modulename) {
        this.modulename = modulename;
    }

}
