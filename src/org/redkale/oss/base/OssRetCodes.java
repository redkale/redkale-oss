/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.redkale.oss.base;

import org.redkale.demo.base.RetCodes;

/**
 *
 * @author zhangjx
 */
public abstract class OssRetCodes extends RetCodes {

    protected OssRetCodes() {
    }

    //2000_0001 - 2999_9999 预留给 Redkale的扩展包redkalex使用
    //3000_0001 - 7999_9999 为平台系统使用
    //8000_0001 - 9999_9999 为OSS系统使用
}
