/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.redkale.oss.base;

import java.io.*;
import java.util.*;
import javax.annotation.Resource;
import org.redkale.convert.*;
import org.redkale.convert.json.*;
import org.redkale.net.http.*;
import org.redkale.oss.sys.*;
import org.redkale.util.*;

/**
 *
 * @author zhangjx
 */
@WebServlet({"/dync/*"})
public final class DyncServlet extends BaseServlet {

    public static final class TextValue {

        public int value;

        public String text;

        public TextValue() {

        }

        public TextValue(int value, String text) {
            this.value = value;
            this.text = text;
        }
    }

    public static final class Menu {

        public int moduleid;

        public String text;

        public String iconCls;

        public String state;

        public String url;

        public boolean hidden;

        public Menu[] children;

        public Menu copy() {
            Menu menu = new Menu();
            menu.moduleid = this.moduleid;
            menu.text = this.text;
            menu.iconCls = this.iconCls;
            menu.url = this.url;
            menu.hidden = this.hidden;
            if (this.children != null) {
                Menu[] nodes = new Menu[this.children.length];
                for (int i = 0; i < nodes.length; i++) {
                    Menu m = this.children[i];
                    if (m != null) nodes[i] = m.copy();
                }
                menu.children = nodes;
            }
            return menu;
        }

        @Override
        public String toString() {
            return "Menu{moduleid=" + moduleid + ", text=" + text + ", iconCls=" + iconCls + ", state=" + state
                + ", url=" + url + ", hidden=" + hidden + ", children=" + (children == null ? null : Arrays.toString(children)) + '}';
        }

        public boolean isLeaf() {
            return children == null || children.length == 0;
        }

        public boolean contains(Set<Integer> ids) {
            if (isLeaf()) return this.moduleid < 1 || ids.contains(this.moduleid);
            for (Menu menu : this.children) {
                if (menu.contains(ids)) return true;
            }
            return false;
        }

        public Menu trim(final Set<Integer> ids) {
            if (this.isLeaf()) {
                return (this.moduleid < 1 || ids.contains(this.moduleid)) ? this : null;
            } else {
                boolean childrenEmpty = true;
                if (this.children != null && this.children.length > 0) {
                    Menu[] newchilds = new Menu[this.children.length];
                    int index = 0;
                    for (int i = 0; i < this.children.length; i++) {
                        Menu m = this.children[i].trim(ids);
                        if (m != null) newchilds[index++] = m;
                    }
                    childrenEmpty = index == 0;
                    if (index > 0 && index < newchilds.length) {
                        Menu[] tmps = new Menu[index];
                        System.arraycopy(newchilds, 0, tmps, 0, index);
                        this.children = tmps;
                    }
                }
                if (!childrenEmpty) return this;
                return (this.moduleid < 1 || ids.contains(this.moduleid)) ? this : null;
            }
        }
    }

    private final List<Menu> menus = new ArrayList<>();

    @Resource
    private RoleService roleService;

    @Resource
    private UserMemberService userService;

    @Resource(name = "APP_HOME")
    private File home;

    public DyncServlet() {
        try {
            Encodeable encoder1 = JsonFactory.root().loadEncoder(MemberInfo.class);
            Encodeable encoder2 = JsonFactory.root().loadEncoder(UserMember.class);
            JsonFactory.root().registerSkipAllIgnore(true);
            //屏蔽掉 password 字段
            JsonFactory.root().register(MemberInfo.class, encoder1);
            JsonFactory.root().register(UserMember.class, encoder2);
            //-------------------------------------------------------------------------------
            String path = "/" + DyncServlet.class.getPackage().getName().replace('.', '/') + "/sysmenus.json";
            try (InputStream in = DyncServlet.class.getResourceAsStream(path)) {
                List<Menu> list = JsonConvert.root().convertFrom(new TypeToken<List<Menu>>() {
                }.getType(), Utility.read(in));
                menus.addAll(list);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void init(HttpContext context, AnyValue conf) {
        try {
            File file = new File(home, "conf/menus.json");
            //-------------------------------------------------------------------------------
            if (file.isFile() && file.canRead()) {
                try (InputStream in = new FileInputStream(file)) {
                    List<Menu> list = JsonConvert.root().convertFrom(new TypeToken<List<Menu>>() {
                    }.getType(), Utility.read(in));
                    menus.addAll(list);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @HttpMapping(url = "/dync/js/mydata", auth = false)
    public void mydata(HttpRequest req, HttpResponse resp) throws IOException {
        resp.setContentType("text/javascript; charset=utf-8");
        StringBuilder sb = new StringBuilder();
        int memberid = req.currentUserid(int.class);
        MemberInfo user = memberid < 1 ? null : userService.findMemberInfo(memberid);
        String userjson;
        String menujson;
        if (user == null) {
            userjson = "{}";
            menujson = "[]";
        } else {
            userjson = convert.convertTo(user);
            if (user.canAdmin()) {
                menujson = convert.convertTo(menus);
            } else {
                Set<Integer> modules = new HashSet<>();
                if (user.getOptions() != null) {
                    for (int m : user.getOptions()) {
                        int v = m / 10000;
                        if (v > 0) modules.add(v);
                    }
                }
                List<Menu> ms = new ArrayList<>();
                for (Menu m : menus) {
                    if (m.contains(modules)) {
                        ms.add(m.copy().trim(modules));
                    }
                }
                menujson = convert.convertTo(ms);
            }
        }
        sb.append("var system_adminpid = ").append(MemberInfo.TYPE_ADMIN).append(";\r\n");
        sb.append("var system_sysmenus = ").append(menujson).append(";\r\n");
        sb.append("var system_memberinfo = ").append(userjson).append(";\r\n");
        sb.append("var system_rmodules = ").append(convert.convertTo(roleService.queryModuleInfo())).append(";\r\n");
        sb.append("var system_actiones = ").append(convert.convertTo(roleService.queryActionInfo())).append(";\r\n");
        resp.finish(sb.toString());
    }
}
