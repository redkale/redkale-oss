/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.redkale.oss.base;

import java.util.logging.Logger;
import org.redkale.service.*;
import org.redkale.util.AutoLoad;

/**
 *
 * @author zhangjx
 */
@AutoLoad(false)
public abstract class BaseService extends AbstractService {

    protected static final boolean winos = System.getProperty("os.name").contains("Window");

    protected final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    protected void log(MemberInfo user, int optionid, String message) {

    }
}
