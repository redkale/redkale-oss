/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.redkale.oss.sys;

import org.redkale.convert.json.*;
import org.redkale.oss.base.BaseBean;
import org.redkale.source.FilterBean;

/**
 *
 * @author zhangjx
 */
public class LogRecordBean extends BaseBean implements FilterBean {

    @Override
    public String toString() {
        return JsonConvert.root().convertTo(this);
    }
}
