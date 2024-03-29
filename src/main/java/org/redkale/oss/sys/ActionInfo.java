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
@Table(name = "sys_actioninfo", comment = "操作信息表")
public class ActionInfo extends BaseEntity {

    @Id
    @Column(comment = "[操作ID] 值的范围必须是2001-9999,1xxx预留给框架")
    private int actionid;

    @Column(length = 64, comment = "[操作名称] 系统已经存在的有查询、新增、修改、删除、登录")
    private String actionName;

    public ActionInfo() {
    }

    public ActionInfo(int actionid, String name) {
        this.actionid = actionid;
        this.actionName = name;
    }

    @Override
    public int hashCode() {
        return this.actionid;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ActionInfo other = (ActionInfo) obj;
        return (this.actionid == other.actionid);
    }

    public int getActionid() {
        return actionid;
    }

    public void setActionid(int actionid) {
        this.actionid = actionid;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

}
