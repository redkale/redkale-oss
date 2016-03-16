/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.redkale.oss.sys;

import javax.persistence.*;
import org.redkale.oss.base.BaseEntity;
import org.redkale.util.AutoLoad;

/**
 * CREATE TABLE `sys_actioninfo` (
 * `actionid` int(11) NOT NULL AUTO_INCREMENT COMMENT '操作ID，值的范围必须是2001-9999,1xxx为框架预留',
 * `actionname` varchar(64) NOT NULL DEFAULT '' COMMENT '操作名称;系统已经存在的有查询、新增、修改、删除、登录',
 * PRIMARY KEY (`actionid`)
 * ) ENGINE=InnoDB AUTO_INCREMENT=2001 DEFAULT CHARSET=utf8;
 *
 * @author zhangjx
 */
@Entity
@AutoLoad
@Cacheable
@Table(name = "sys_actioninfo")
public class ActionInfo extends BaseEntity{

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
