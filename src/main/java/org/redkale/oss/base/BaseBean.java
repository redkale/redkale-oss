/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.redkale.oss.base;

import org.redkale.convert.json.*;
import org.redkale.util.Bean;

/**
 *
 * @author zhangjx
 */
@Bean
public abstract class BaseBean {

    @Override
    public String toString() {
        return JsonConvert.root().convertTo(this);
    }
}
