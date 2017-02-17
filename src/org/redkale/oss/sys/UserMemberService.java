/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.redkale.oss.sys;

import java.util.*;
import javax.annotation.Resource;
import org.redkale.oss.base.Services;
import static org.redkale.oss.base.Services.*;
import static org.redkale.oss.base.OssRetCodes.*;
import org.redkale.oss.base.MemberInfo;
import org.redkale.source.*;
import org.redkale.util.AnyValue;
import org.redkale.util.Sheet;

/**
 *
 * @author zhangjx
 */
public class UserMemberService extends BaseService {

    private final int sessionExpireSeconds = 30 * 60;

    @Resource(name = "membersessions")
    protected CacheSource<String, Integer> sessions;

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
        Integer memberid = sessions.getAndRefresh(sessionid, sessionExpireSeconds);
        MemberInfo user = memberid == null ? null : findMemberInfo(memberid);
        if (user == null) return null;
        if (!user.canAdmin()) user.setOptions(roleService.queryOptionidsByMemberid(user.getMemberid()));
        return user;
    }

    public LoginResult login(LoginBean bean) {
        if (bean == null || bean.emptySessionid() || bean.emptyAccount()) return null;
        final LoginResult result = new LoginResult();
        final int optionid = Services.optionid(MODULE_USER, ACTION_LOGIN);
        if (!bean.isWxlogin()) bean.setPassword(UserMember.md5IfNeed(bean.getPassword()));
        UserMember detail = source.find(UserMember.class, "account", bean.getAccount());
        if (detail == null || (!bean.isWxlogin() && !Objects.equals(detail.getPassword(), bean.getPassword()))) {
            result.setRetcode(RET_USER_ACCOUNT_PWD_ILLEGAL);
            super.log(null, optionid, "用户账号或密码错误，登录失败.");
            return result;
        }
        result.setRetcode(0);
        result.setSessionid(bean.getSessionid());
        MemberInfo user = detail.createMemberInfo();
        if (user.isStatusFreeze()) {
            result.setRetcode(RET_USER_FREEZED);
            super.log(user, optionid, "用户被禁用，登录失败.");
            return result;
        }
        if (!user.canAdmin()) user.setOptions(roleService.queryOptionidsByMemberid(user.getMemberid()));
        result.setUser(user);
        super.log(user, optionid, "用户登录成功.");
        this.sessions.set(sessionExpireSeconds, bean.getSessionid(), result.getUser().getMemberid());
        return result;
    }

    public int updatePwd(String sessionid, String newpwd, String oldpwd) {
        MemberInfo user = sessionid == null ? null : current(sessionid);
        if (user == null) return RET_USER_NOTEXISTS;
        if (newpwd == null || newpwd.length() < 30) return RET_USER_PASSWORD_ILLEGAL;
        if (!Objects.equals(user.getPassword(), oldpwd)) return RET_USER_PASSWORD_ILLEGAL;
        source.updateColumn(UserMember.class, user.getMemberid(), "password", newpwd);
        user.setPassword(newpwd);
        return 0;
    }

    public boolean logout(String sessionid) {
        sessions.remove(sessionid);
        return true;
    }

    public Sheet<UserMember> queryMember(Flipper flipper, final UserMemberBean bean) {
        Sheet<UserMember> sheet = source.querySheet(UserMember.class, flipper, bean);
        if (sheet.isEmpty()) return sheet;
        int oldmemberid = bean == null ? 0 : bean.getMemberid();
        for (UserMember detail : sheet.getRows()) {
            detail.setRoleids(roleService.queryRoleidByMemberid(detail.getMemberid()));
        }
        if (bean != null) bean.setMemberid(oldmemberid);
        return sheet;
    }

    public UserMember findMember(int memberid) {
        if (memberid < 1) return null;
        return source.find(UserMember.class, memberid);
    }

    public UserMember findMemberByWeixin(String weixin) {
        if (weixin == null || weixin.isEmpty()) return null;
        return source.find(UserMember.class, FilterNode.create("weixin", weixin));
    }

    public void createMember(UserMember user) {
        if (user != null) {
            user.setCreatetime(System.currentTimeMillis());
            user.setPassword(user.passwordForMD5());
            user.setStatus(MemberInfo.STATUS_NORMAL);
        }
        int maxid = source.getNumberResult(UserMember.class, FilterFunc.MAX, 1000000, "memberid", (FilterNode) null).intValue();
        user.setMemberid(maxid + 1);
        source.insert(user);
    }

    public void updateMember(UserMember user) {
        user.setUpdatetime(System.currentTimeMillis());
        source.update(user);
    }

    public void deleteMember(UserMember user) {
        source.delete(user);
    }

}
