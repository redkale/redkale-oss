/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.redkale.oss.sys;

import java.lang.reflect.*;
import java.util.*;
import org.redkale.net.http.*;
import org.redkale.oss.base.Services;
import org.redkale.oss.base.Services.ActionName;
import org.redkale.oss.base.Services.ModuleName;
import org.redkale.oss.base.MemberInfo;
import static org.redkale.oss.base.Services.*;
import org.redkale.service.RetResult;
import org.redkale.source.*;
import org.redkale.util.*;

/**
 *
 * @author zhangjx
 */
@RestService(name = "role", moduleid = MODULE_ROLE, comment = "【OSS系统】角色管理模块")
public class RoleService extends BaseService {

    @Override
    public void init(AnyValue conf) {
        moduleinfos.addAll(source.queryList(ModuleInfo.class, (FilterNode) null));
        actioninfos.addAll(source.queryList(ActionInfo.class, (FilterNode) null));
    }

    @Comment("获取单个角色对象")
    @RestMapping(name = "find", auth = true, actionid = ACTION_QUERY, comment = "获取单个角色对象")
    public RoleInfo findRoleInfo(@RestParam(name = "#", comment = "角色ID") int roleid) {
        return source.find(RoleInfo.class, roleid);
    }

    @RestMapping(name = "create", auth = true, actionid = ACTION_CREATE, comment = "新增单个角色对象")
    public int createRoleInfo(@Comment("当前用户") MemberInfo admin, @Comment("角色对象") RoleInfo bean) {
        if (admin != null) {
            bean.setCreatetime(System.currentTimeMillis());
            bean.setCreator(admin.getMembername());
        }
        int maxid = source.getNumberResult(RoleInfo.class, FilterFunc.MAX, 2000, "roleid", (FilterNode) null).intValue();
        bean.setRoleid(maxid + 1);
        source.insert(bean);
        return 1;
    }

    @RestMapping(name = "update", auth = true, actionid = ACTION_UPDATE, comment = "修改单个角色对象")
    public RetResult updateRoleInfo(@Comment("角色对象") RoleInfo bean) {
        source.update(bean);
        return RetResult.success();
    }

    @RestMapping(name = "query", auth = true, actionid = ACTION_QUERY, comment = "查询角色列表")
    public Sheet<RoleInfo> queryRoleInfo(@Comment("翻页信息") Flipper flipper) {
        return source.querySheet(RoleInfo.class, flipper, (FilterBean) null);
    }

    @RestMapping(ignore = true, comment = "获取所有模块列表")
    public List<ModuleInfo> queryModuleInfo() {
        return moduleinfos;
    }

    @RestMapping(ignore = true, comment = "获取所有操作列表")
    public List<ActionInfo> queryActionInfo() {
        return actioninfos;
    }

    @RestMapping(ignore = true, comment = "创建员工角色关联")
    public int createUserToRole(@Comment("当前用户") MemberInfo admin, @Comment("员工角色关联对象") UserToRole info) {
        if (admin != null) {
            info.setCreatetime(System.currentTimeMillis());
            info.setCreator(admin.getMembername());
        }
        info.setSeqid((int) (System.currentTimeMillis() / 1000));
        source.insert(info);
        return 0;
    }

    @RestMapping(ignore = true, comment = "删除员工角色关联")
    public void deleteUserToRole(@Comment("员工角色关联对象ID") int seqid) {
        UserToRole utr = source.find(UserToRole.class, seqid);
        if (utr == null) return;
        source.delete(utr);
    }

    @RestMapping(name = "qryuserole", auth = true, actionid = ACTION_QUERY, comment = "查询角色员工关系列表")
    public Sheet<UserToRole> queryUserToRole(@Comment("过滤条件") UserMemberBean bean, @Comment("翻页信息") Flipper flipper) {
        return source.querySheet(UserToRole.class, flipper, bean);
    }

    @RestMapping(name = "upduserole", auth = true, actionid = ACTION_UPDATE, comment = "更新角色员工用户")
    public int[] updateUserToRole(MemberInfo admin,
        @RestParam(name = "delmemberid", comment = "待删除的员工ID") int delmemberid,
        @RestParam(name = "delroleids", comment = "待删除员工的角色ID") int[] delroleids,
        @RestParam(name = "bean", comment = "待新增的角色与员工关系对象") UserToRole... beans) {
        final boolean deled = delmemberid > 0 && delroleids != null && delroleids.length > 0;
        if (deled) source.delete(UserToRole.class, FilterNode.create("roleid", FilterExpress.IN, delroleids).and("memberid", delmemberid));
        if (beans.length == 0) return new int[0];
        if (admin != null) {
            long now = System.currentTimeMillis();
            int seqid = (int) (now / 1000);
            for (UserToRole info : beans) {
                if (info.getMemberid() < 1 || info.getRoleid() < 1) throw new RuntimeException(UserToRole.class.getSimpleName() + "(" + info + ") is illegal");
                info.setCreatetime(now);
                info.setSeqid(seqid++);
                info.setCreator(admin.getMembername());
            }
        }
        int[] rs = new int[beans.length];
        source.insert(beans);
        for (int i = 0; i < rs.length; i++) {
            rs[i] = beans[i].getSeqid();
        }
        return rs;
    }

    @RestMapping(name = "updoption", auth = true, actionid = ACTION_UPDATE, comment = "更新角色的操作权限")
    public int[] updateRoleToOption(MemberInfo admin,
        @RestParam(name = "delseqids", comment = "待删除的角色操作ID") int[] delseqids,
        @RestParam(name = "bean", comment = "待新增的角色与操作关系对象") RoleToOption... infos) {
        final boolean deled = delseqids != null && delseqids.length > 0;
        if (deled) source.delete(RoleToOption.class, FilterNode.create("seqid", FilterExpress.IN, delseqids));
        if (infos.length == 0) return new int[0];
        if (admin != null) {
            long now = System.currentTimeMillis();
            int seqid = (int) (now / 1000);
            for (RoleToOption info : infos) {
                if (info.getRoleid() < 1 || info.getOptionid() < 1) throw new RuntimeException(RoleToOption.class.getSimpleName() + "(" + info + ") is illegal");
                info.setCreatetime(now);
                info.setSeqid(seqid++);
                info.setCreator(admin.getMembername());
            }
        }
        int[] rs = new int[infos.length];
        source.insert(infos);
        for (int i = 0; i < rs.length; i++) {
            rs[i] = infos[i].getSeqid();
        }
        return rs;
    }

    public void deleteRoleToOption(int seqid) {
        RoleToOption rto = source.find(RoleToOption.class, seqid);
        if (rto == null) return;
        source.delete(rto);
        List<UserToRole> utrs = source.queryList(UserToRole.class, FilterNode.create("roleid", rto.getRoleid()));
        if (utrs.isEmpty()) return;
        int[] memberids = new int[utrs.size()];
        int index = -1;
        for (int i = 0; i < memberids.length; i++) {
            memberids[++index] = utrs.get(index).getMemberid();
        }
    }

    @RestMapping(name = "qryoption", auth = true, actionid = ACTION_QUERY, comment = "查询角色操作关系列表")
    public List<RoleToOption> queryRoleToOption(RoleToOption bean) {
        return source.queryList(RoleToOption.class, bean);
    }

    public int[] queryOptionidsByMemberid(int memberid) {
        Set<Integer> list = source.queryColumnSet("optionid", RoleToOption.class, "roleid", queryRoleidByMemberid(memberid));
        return format(list);
    }

    public int[] queryRoleidByMemberid(int memberid) {
        List<Integer> list = source.queryColumnList("roleid", UserToRole.class, "memberid", memberid);
        return format(list);
    }

    private static int[] format(Collection<Integer> list) {
        if (list == null || list.isEmpty()) return new int[0];
        int[] rs = new int[list.size()];
        int i = -1;
        for (int v : list) {
            rs[++i] = v;
        }
        return rs;
    }

    private static final List<ModuleInfo> moduleinfos = new ArrayList<>();

    private static final List<ActionInfo> actioninfos = new ArrayList<>();

    static {
        for (Field field : Services.class.getFields()) {
            if (!Modifier.isStatic(field.getModifiers())) continue;
            int value;
            try {
                value = field.getInt(null);
            } catch (Exception ex) {
                ex.printStackTrace();
                continue;
            }
            ModuleName m = field.getAnnotation(ModuleName.class);
            if (m != null) {
                moduleinfos.add(new ModuleInfo(value, m.value()));
                continue;
            }
            ActionName a = field.getAnnotation(ActionName.class);
            if (a != null) {
                actioninfos.add(new ActionInfo(value, a.value()));
            }
        }

    }
}
