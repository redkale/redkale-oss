/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.redkale.oss.sys;

import javax.persistence.*;
import org.redkale.oss.base.BasedEntity;
import org.redkale.source.DistributeGenerator;
import org.redkale.util.AutoLoad;

/**
 *
 * @author zhangjx
 */
@Entity
@AutoLoad
@Cacheable
public class ModuleInfo extends BasedEntity {

    @Id
    @GeneratedValue
    @DistributeGenerator(initialValue = 201, allocationSize = 1)
    private int moduleid;

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
