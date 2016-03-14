/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.redkale.oss.base;

import java.io.*;
import java.util.*;
import javax.annotation.*;
import org.redkale.convert.json.JsonFactory;
import org.redkale.net.http.HttpContext;
import org.redkale.net.http.HttpRequest;
import org.redkale.net.http.HttpResponse;
import org.redkale.net.http.WebServlet;
import org.redkale.oss.sys.RoleService;
import org.redkale.util.AnyValue;
import org.redkale.util.TypeToken;

/**
 *
 * @author zhangjx
 */
@WebServlet({"/dync/*"})
public final class DyncServlet extends BasedServlet {

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

        public Menu[] children;

        public Menu copy() {
            Menu menu = new Menu();
            menu.moduleid = this.moduleid;
            menu.text = this.text;
            menu.iconCls = this.iconCls;
            menu.url = this.url;
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
                + ", url=" + url + ", children=" + (children == null ? null : Arrays.toString(children)) + '}';
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

    @Resource(name = "APP_HOME")
    private File home;

    public DyncServlet() {
        try {
            JsonFactory.root().registerSkipAllIgnore(true);
            //-------------------------------------------------------------------------------
            String path = "/" + DyncServlet.class.getPackage().getName().replace('.', '/') + "/sysmenus.json";
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try (InputStream in = DyncServlet.class.getResourceAsStream(path)) {
                byte[] bytes = new byte[1024];
                int pos;
                while ((pos = in.read(bytes)) > -1) {
                    out.write(bytes, 0, pos);
                }
                List<Menu> list = JsonFactory.root().getConvert().convertFrom(new TypeToken<List<Menu>>() {
                }.getType(), out.toString("utf-8"));
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
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                try (InputStream in = new FileInputStream(file)) {
                    byte[] bytes = new byte[1024];
                    int pos;
                    while ((pos = in.read(bytes)) > -1) {
                        out.write(bytes, 0, pos);
                    }
                    List<Menu> list = JsonFactory.root().getConvert().convertFrom(new TypeToken<List<Menu>>() {
                    }.getType(), out.toString("utf-8"));
                    menus.addAll(list);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @AuthIgnore
    @WebAction(url = "/dync/myjsinfo")
    public void myjsinfo(HttpRequest req, HttpResponse resp) throws IOException {
        long s = System.currentTimeMillis();
        resp.setContentType("text/javascript");
        UserInfo user = currentUser(req);
        //https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx79b7707365c6ce61&response_type=code&scope=snsapi_base&redirect_uri=http%3A%2F%2Foa.3wyc.cn%2Fpipes%2Fwx%2Flogin%3Fagentid%3D2%26url%3D%2Fview%2Fweeky.html#wechat_redirect
        if (user == null) {
            //String url = URLEncoder.encode("http://oa.3wyc.cn/pipes/wx/login?agentid=" + req.getParameter("agentid") + "&url=" + req.getHeader("Referer", "/"), "utf-8");
            //resp.finish("window.location.href = 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx79b7707365c6ce61&response_type=code&scope=snsapi_base&redirect_uri=" + url + "#wechat_redirect';");
            resp.finish("var system_userinfo = " + convert.convertTo(user) + "; var userself = system_userinfo;");
        } else {
            resp.finish("var system_userinfo = " + convert.convertTo(user) + "; var userself = system_userinfo;");
        }
        long e = System.currentTimeMillis() - s;
        //logger.finest("/dync/myjsinfo in " + e + " ms"); 
    }

    @AuthIgnore
    @WebAction(url = "/dync/mydata")
    public void mydata(HttpRequest req, HttpResponse resp) throws IOException {
        resp.setContentType("text/javascript");
        StringBuilder sb = new StringBuilder();
        UserInfo user = currentUser(req);
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
        sb.append("var system_adminpid = ").append(UserInfo.TYPE_ADMIN).append(";\r\n");
        sb.append("var system_sysmenus = ").append(menujson).append(";\r\n");
        sb.append("var system_userinfo = ").append(userjson).append(";\r\n");
        sb.append("var system_rmodules = ").append(convert.convertTo(roleService.queryModuleInfo())).append(";\r\n");
        sb.append("var system_actiones = ").append(convert.convertTo(roleService.queryActionInfo())).append(";\r\n");
        resp.finish(sb.toString());
    }
}
