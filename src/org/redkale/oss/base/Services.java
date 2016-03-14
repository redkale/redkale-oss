/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.redkale.oss.base;

import java.lang.annotation.*;

/**
 *
 * @author zhangjx
 */
public class Services {

    private Services() {
        throw new Error();
    }

    //----------------------------------------------操作ID-----------------------------------------------------------
    @ActionName("查询")
    public static final int ACTION_QUERY = 1001;

    @ActionName("新增")
    public static final int ACTION_CREATE = 1002;

    //修改、预览和同步文件
    @ActionName("修改")
    public static final int ACTION_UPDATE = 1003;

    @ActionName("删除")
    public static final int ACTION_DELETE = 1004;

    @ActionName("登录")
    public static final int ACTION_LOGIN = 1010;

    //----------------------------------------------模块ID  注意: @ModuleName名称尽量控制在5个汉字以内-----------------------------------------------------------
    @ModuleName("用户管理")
    public static final int MODULE_USER = 101;

    @ModuleName("权限管理")
    public static final int MODULE_AUTH = 102;

    @ModuleName("角色管理")
    public static final int MODULE_ROLE = 103;

    //-------------------------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * 将模块ID与操作ID合并成一个int值
     *
     * @param moduleid
     * @param actionid
     * @return
     */
    public static int optionid(int moduleid, int actionid) {
        return moduleid * 10000 + actionid;
    }

    @Target(value = {ElementType.FIELD})
    @Retention(value = RetentionPolicy.RUNTIME)
    public @interface ModuleName {

        String value();
    }

    @Target(value = {ElementType.FIELD})
    @Retention(value = RetentionPolicy.RUNTIME)
    public @interface ActionName {

        String value();
    }

    @Target(value = {ElementType.FIELD})
    @Retention(value = RetentionPolicy.RUNTIME)
    public @interface TypeName {

        String value();
    }
}
