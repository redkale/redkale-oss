/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.redkale.oss.sys;

import javax.annotation.Resource;
import org.redkale.source.DataSource;
import org.redkale.util.AutoLoad;

/**
 *
 * @author zhangjx
 */
@AutoLoad(false)
public abstract class BaseService extends org.redkale.oss.base.BasedService {

    @Resource(name = "redoss")
    protected DataSource source;
}
