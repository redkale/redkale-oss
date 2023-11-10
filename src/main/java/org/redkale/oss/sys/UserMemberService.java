/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.redkale.oss.sys;

import java.util.Objects;
import org.redkale.annotation.Resource;
import org.redkale.convert.json.JsonConvert;
import org.redkale.net.http.*;
import org.redkale.oss.base.*;
import static org.redkale.oss.base.OssRetCodes.*;
import static org.redkale.oss.base.Services.*;
import org.redkale.oss.sys.BaseService;
import org.redkale.service.RetResult;
import org.redkale.source.*;
import org.redkale.util.*;

/**
 *
 * @author zhangjx
 */
@RestService(name = "user", moduleid = MODULE_USER, comment = "【OSS系统】员工管理模块")
public class UserMemberService extends BaseService {

    private final int sessionExpireSeconds = 30 * 60;

    @Resource(name = "membersessions")
    protected CacheSource sessions;

    @Resource
    protected JsonConvert convert;

    @Resource
    private RoleService roleService;

    @Override
    public void destroy(AnyValue conf) {
    }

    public MemberInfo findMemberInfo(int memberid) {
        UserMember detail = source.find(UserMember.class, memberid);
        return detail == null ? null : detail.createMemberInfo();
    }

    public MemberInfo current(String sessionid) {
        long memberid = sessions.getexLong(sessionid, sessionExpireSeconds, 0L);
        MemberInfo user = memberid < 1 ? null : findMemberInfo((int) memberid);
        if (user == null) {
            return null;
        }
        if (!user.canAdmin()) {
            user.setOptions(roleService.queryOptionidsByMemberid(user.getMemberid()));
        }
        return user;
    }

    @RestMapping(name = "login", auth = false, comment = "账号方式登录")
    public RetResult login(LoginBean bean) {
        if (bean == null || bean.emptySessionid() || bean.emptyAccount()) {
            return OssRetCodes.retResult(OssRetCodes.RET_PARAMS_ILLEGAL);
        }
        final LoginResult result = new LoginResult();
        final int optionid = Services.optionid(MODULE_USER, ACTION_LOGIN);
        if (!bean.isWxlogin()) {
            bean.setPassword(UserMember.md5IfNeed(bean.getPassword()));
        }
        UserMember detail = source.find(UserMember.class, "account", bean.getAccount());
        if (detail == null || (!bean.isWxlogin() && !Objects.equals(detail.getPassword(), bean.getPassword()))) {
            result.setRetcode(RET_USER_ACCOUNT_PWD_ILLEGAL);
            super.log(null, optionid, "用户账号或密码错误，登录失败.");
            return OssRetCodes.retResult(OssRetCodes.RET_USER_ACCOUNT_PWD_ILLEGAL);
        }
        result.setRetcode(0);
        result.setSessionid(bean.getSessionid());
        MemberInfo user = detail.createMemberInfo();
        if (user.isStatusFreeze()) {
            result.setRetcode(RET_USER_FREEZED);
            super.log(user, optionid, "用户被禁用，登录失败.");
            return OssRetCodes.retResult(OssRetCodes.RET_USER_FREEZED);
        }
        if (!user.canAdmin()) {
            user.setOptions(roleService.queryOptionidsByMemberid(user.getMemberid()));
        }
        result.setUser(user);
        super.log(user, optionid, "用户登录成功.");
        this.sessions.setexLong(bean.getSessionid(), sessionExpireSeconds, result.getUser().getMemberid());
        return RetResult.success();
    }

    @RestMapping(name = "changepwd", auth = true, actionid = ACTION_UPDATE, comment = "修改密码")
    public RetResult updatePwd(@RestSessionid String sessionid,
        @RestParam(name = "newpwd", comment = "新密码md5") String newpwd,
        @RestParam(name = "oldpwd", comment = "旧密码md5") String oldpwd) {
        MemberInfo user = sessionid == null ? null : current(sessionid);
        if (user == null) {
            return OssRetCodes.retResult(RET_USER_NOTEXISTS);
        }
        if (newpwd == null || newpwd.length() < 30) {
            return OssRetCodes.retResult(RET_USER_PASSWORD_ILLEGAL);
        }
        if (!Objects.equals(user.getPassword(), oldpwd)) {
            return OssRetCodes.retResult(RET_USER_PASSWORD_ILLEGAL);
        }
        source.updateColumn(UserMember.class, user.getMemberid(), "password", newpwd);
        user.setPassword(newpwd);
        return RetResult.success();
    }

    @RestMapping(name = "logout", auth = true, comment = "用户退出登录")
    public HttpResult logout(@RestSessionid String sessionid) {
        sessions.del(sessionid);
        return new HttpResult().header("Location", "/").status(302);
    }

    @RestMapping(name = "myinfo", auth = false, comment = "获取当前用户信息")
    public MemberInfo myinfo(@RestSessionid String sessionid) {
        return current(sessionid);
    }

    @RestMapping(name = "jsmyinfo", auth = false, comment = "获取当前用户信息(js格式)")
    public HttpResult jsmyinfo(@RestSessionid String sessionid) {
        sessions.del(sessionid);
        return new HttpResult("var userself = " + convert.convertTo(current(sessionid)) + ";").contentType("application/javascript; charset=utf-8");
    }

    @RestMapping(name = "query", auth = true, actionid = ACTION_QUERY, comment = "查询员工列表")
    public Sheet<UserMember> queryMember(Flipper flipper,
        @RestParam(name = "bean", comment = "过滤条件") final UserMemberBean bean) {
        Sheet<UserMember> sheet = source.querySheet(UserMember.class, flipper, bean);
        if (sheet.isEmpty()) {
            return sheet;
        }
        int oldmemberid = bean == null ? 0 : bean.getMemberid();
        for (UserMember detail : sheet.getRows()) {
            detail.setRoleids(roleService.queryRoleidByMemberid(detail.getMemberid()));
        }
        if (bean != null) {
            bean.setMemberid(oldmemberid);
        }
        return sheet;
    }

    public UserMember findMember(int memberid) {
        if (memberid < 1) {
            return null;
        }
        return source.find(UserMember.class, memberid);
    }

    public UserMember findMemberByWeixin(String weixin) {
        if (weixin == null || weixin.isEmpty()) {
            return null;
        }
        return source.find(UserMember.class, FilterNodes.eq("weixin", weixin));
    }

    @RestMapping(name = "create", auth = true, actionid = ACTION_CREATE, comment = "新增员工")
    public void createMember(@RestParam(name = "bean") UserMember user) {
        user.setCreatetime(System.currentTimeMillis());
        user.setPassword(user.passwordForMD5());
        user.setStatus(MemberInfo.STATUS_NORMAL);
        int maxid = source.getNumberResult(UserMember.class, FilterFunc.MAX, 1000000, "memberid", (FilterNode) null).intValue();
        user.setMemberid(maxid + 1);
        source.insert(user);
    }

    @RestMapping(name = "update", auth = true, actionid = ACTION_UPDATE, comment = "修改员工")
    public void updateMember(UserMember bean) {
        bean.setUpdatetime(System.currentTimeMillis());
        source.update(bean);
    }

    public void deleteMember(UserMember user) {
        source.delete(user);
    }

}
