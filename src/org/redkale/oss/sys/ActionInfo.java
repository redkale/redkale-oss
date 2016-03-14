/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.redkale.oss.sys;

import java.io.Serializable;
import javax.persistence.*;
import org.redkale.util.AutoLoad;

/**
 *
 * @author zhangjx
 */
@Entity
@AutoLoad
@Cacheable
public class ActionInfo implements Serializable {

    @Id
    //@DistributeGenerator(initialValue = 200, allocationSize = 1)
    private int actionid;

    private String actionname;

    public ActionInfo() {
    }

    public ActionInfo(int actionid, String name) {
        this.actionid = actionid;
        this.actionname = name;
    }

    @Override
    public int hashCode() {
        return this.actionid;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final ActionInfo other = (ActionInfo) obj;
        return (this.actionid == other.actionid);
    }

    public int getActionid() {
        return actionid;
    }

    public void setActionid(int actionid) {
        this.actionid = actionid;
    }

    public String getActionname() {
        return actionname;
    }

    public void setActionname(String actionname) {
        this.actionname = actionname;
    }

}
