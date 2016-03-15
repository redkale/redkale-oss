/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.redkale.oss.base;


import java.io.*;
import org.redkale.convert.json.JsonFactory;

/**
 *
 * @author zhangjx
 */
public abstract class BaseEntity implements Serializable {

    @Override
    public String toString() {
        return JsonFactory.root().getConvert().convertTo(this);
    }

}
