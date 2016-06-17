/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.redkale.oss.sys;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;
import javax.annotation.Resource;
import org.redkale.oss.base.Services;
import static org.redkale.oss.base.Services.*;
import org.redkale.oss.base.MemberInfo;
import org.redkale.source.Flipper;
import org.redkale.util.AnyValue;
import org.redkale.util.Sheet;

/**
 *
 * @author zhangjx
 */
public class UserMemberService extends BaseService {

    protected static final class UserSessionEntry {

        public String sessionid;

        public long creationTime; //登陆时间

        public long lastAccessed; //最后刷新时间

        public MemberInfo info;

        public UserSessionEntry(String sessionid, MemberInfo info) {
            this.sessionid = sessionid;
            this.info = info;
            this.creationTime = System.currentTimeMillis();
            this.lastAccessed = this.creationTime;
        }

        @Override
        public String toString() {
            return "UserSessionEntry{" + "sessionid=" + sessionid + ", info=" + info + '}';
        }

    }

    private final ScheduledThreadPoolExecutor sessionExpirerExecutor = new ScheduledThreadPoolExecutor(1, (Runnable r) -> {
        final Thread t = new Thread(r, "Session-Expirer");
        t.setDaemon(true);
        return t;
    });

    protected final Map<String, UserSessionEntry> sessions = new ConcurrentHashMap<>();

    @Resource
    private RoleService roleService;

    public UserMemberService() {
        final long timeout = 60 * 60 * 1000L; //60分钟
        sessionExpirerExecutor.scheduleAtFixedRate(() -> {
            long expireTime = System.currentTimeMillis() - timeout;
            Iterator<Map.Entry<String, UserSessionEntry>> iterator = sessions.entrySet().iterator();
            while (iterator.hasNext()) {
                if (iterator.next().getValue().lastAccessed < expireTime) {
                    iterator.remove();
                }
            }
        }, 10, 10, TimeUnit.SECONDS);
    }

    @Override
    public void destroy(AnyValue conf) {
        sessionExpirerExecutor.shutdownNow();
    }

    public MemberInfo findMemberInfo(int userid) {
        UserMember detail = source.find(UserMember.class, userid);
        return detail == null ? null : detail.createMemberInfo();
    }

    public MemberInfo current(String sessionid) {
        UserSessionEntry entry = sessions.get(sessionid);
        if (entry != null) entry.lastAccessed = System.currentTimeMillis();
        return entry == null ? null : entry.info;
    }

    public LoginResult login(LoginBean bean) {
        if (bean == null || bean.emptySessionid() || bean.emptyAccount()) return null;
        final LoginResult result = new LoginResult();
        final int optionid = Services.optionid(MODULE_USER, ACTION_LOGIN);
        if (!bean.isWxlogin()) bean.setPassword(UserMember.md5IfNeed(bean.getPassword()));
        UserMember detail = source.find(UserMember.class, "account", bean.getAccount());
        if (detail == null || (!bean.isWxlogin() && !Objects.equals(detail.getPassword(), bean.getPassword()))) {
            result.setRetcode(1001);
            super.log(null, optionid, "用户账号或密码错误，登录失败.");
            return result;
        }
        result.setRetcode(0);
        result.setSessionid(bean.getSessionid());
        MemberInfo user = detail.createMemberInfo();
        if (user.isStatusFreeze()) {
            result.setRetcode(1002);
            super.log(user, optionid, "用户被禁用，登录失败.");
            return result;
        }
        if (!user.canAdmin()) user.setOptions(roleService.queryOptionidsByUserid(user.getUserid()));
        result.setUser(user);
        super.log(user, optionid, "用户登录成功.");
        this.sessions.put(result.getSessionid(), new UserSessionEntry(result.getSessionid(), result.getUser()));
        return result;
    }

    public int updatePwd(String sessionid, String newpwd, String oldpwd) {
        MemberInfo user = sessionid == null ? null : current(sessionid);
        if (user == null) return 1010021;
        if (newpwd == null || newpwd.length() < 30) return 1010021;
        if (!Objects.equals(user.getPassword(), oldpwd)) return 1010020;
        source.updateColumn(UserMember.class, user.getUserid(), "password", newpwd);
        user.setPassword(newpwd);
        return 0;
    }

    public void updateCache(int... userids) {
        Stream<UserSessionEntry> stream = this.sessions.values().stream();
        if (userids.length > 0) stream = stream.filter(x -> Arrays.binarySearch(userids, x.info.getUserid()) >= 0);
        stream.forEach(x -> x.info.setOptions(roleService.queryOptionidsByUserid(x.info.getUserid())));
    }

    public boolean logout(String sessionid) {
        sessions.remove(sessionid);
        return true;
    }

    public long fresh(String sessionid) {
        UserSessionEntry entry = sessions.get(sessionid);
        if (entry == null) return 0L;
        long time = System.currentTimeMillis();
        entry.lastAccessed = time;
        return time;
    }

    public Sheet<UserMember> queryUser(Flipper flipper, final UserFilterBean bean) {
        Sheet<UserMember> sheet = source.querySheet(UserMember.class, flipper, bean);
        if (sheet.isEmpty()) return sheet;
        int olduserid = bean == null ? 0 : bean.getUserid();
        for (UserMember detail : sheet.getRows()) {
            detail.setRoleids(roleService.queryRoleidByUserid(detail.getUserid()));
        }
        if (bean != null) bean.setUserid(olduserid);
        return sheet;
    }

    public UserMember findUser(int userid) {
        return source.find(UserMember.class, userid);
    }

    public void createUser(UserMember user) {
        if (user != null) {
            user.setCreatetime(System.currentTimeMillis());
            user.setPassword(user.passwordForMD5());
            user.setStatus(MemberInfo.STATUS_NORMAL);
        }
        source.insert(user);
    }

    public void updateUser(UserMember user) {
        user.setUpdatetime(System.currentTimeMillis());
        source.update(user);
    }

    public void deleteUser(UserMember user) {
        source.delete(user);
    }

}
