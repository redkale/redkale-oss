/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.redkale.oss.base;

import java.io.*;
import org.redkale.convert.json.*;
import org.redkale.persistence.Entity;

/**
 *
 * @author zhangjx
 */
@Entity
public abstract class BaseEntity implements Serializable {

    //状态间隔10，便于以后扩展意义接近的状态值比较靠近
    //正常
    public static final short STATUS_NORMAL = 10;

    //待审批
    public static final short STATUS_PENDING = 20;

    //冻结
    public static final short STATUS_FREEZE = 40;

    //隐藏
    public static final short STATUS_HIDDEN = 50;

    //关闭
    public static final short STATUS_CLOSED = 60;

    //过期
    public static final short STATUS_EXPIRE = 70;

    //删除
    public static final short STATUS_DELTED = 80;

    @Override
    public String toString() {
        return JsonConvert.root().convertTo(this);
    }

}
