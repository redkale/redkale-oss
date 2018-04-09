/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.redkale.oss.base;

import java.text.MessageFormat;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import org.redkale.service.*;

/**
 *
 * @author zhangjx
 */
public abstract class OssRetCodes {

    //2000_0001 - 2999_9999 预留给 Redkale的扩展包redkalex使用
    //3000_0001 - 7999_9999 为平台系统使用
    //8000_0001 - 9999_9999 为OSS系统使用
    //------------------------------------- 通用模块 -----------------------------------------
    @RetLabel("参数无效")
    public static final int RET_PARAMS_ILLEGAL = 30010001;

    @RetLabel("无上传文件")
    public static final int RET_UPLOAD_NOFILE = 30010002;

    @RetLabel("上传文件过大")
    public static final int RET_UPLOAD_FILETOOBIG = 30010003;

    @RetLabel("上传文件不是图片")
    public static final int RET_UPLOAD_NOTIMAGE = 30010004;

    //------------------------------------- 用户模块 -----------------------------------------
    @RetLabel("未登陆")
    public static final int RET_USER_UNLOGIN = 30020001;

    @RetLabel("用户登录失败")
    public static final int RET_USER_LOGIN_FAIL = 30020002;

    @RetLabel("用户或密码错误")
    public static final int RET_USER_ACCOUNT_PWD_ILLEGAL = 30020003;

    @RetLabel("密码设置无效")
    public static final int RET_USER_PASSWORD_ILLEGAL = 30020004;

    @RetLabel("用户被禁用")
    public static final int RET_USER_FREEZED = 30020005;

    @RetLabel("用户权限不够")
    public static final int RET_USER_AUTH_ILLEGAL = 30020006;

    @RetLabel("用户不存在")
    public static final int RET_USER_NOTEXISTS = 30020007;

    @RetLabel("用户状态异常")
    public static final int RET_USER_STATUS_ILLEGAL = 30020008;

    @RetLabel("用户注册参数无效")
    public static final int RET_USER_SIGNUP_ILLEGAL = 30020009;

    @RetLabel("用户性别参数无效")
    public static final int RET_USER_GENDER_ILLEGAL = 30020010;

    @RetLabel("用户名无效")
    public static final int RET_USER_USERNAME_ILLEGAL = 30020011;

    @RetLabel("用户账号无效")
    public static final int RET_USER_ACCOUNT_ILLEGAL = 30020012;

    @RetLabel("用户账号已存在")
    public static final int RET_USER_ACCOUNT_EXISTS = 30020013;

    @RetLabel("手机号码无效")
    public static final int RET_USER_MOBILE_ILLEGAL = 30020014;

    @RetLabel("手机号码已存在")
    public static final int RET_USER_MOBILE_EXISTS = 30020015;

    @RetLabel("手机验证码发送过于频繁")
    public static final int RET_USER_MOBILE_SMSFREQUENT = 30020016;

    @RetLabel("邮箱地址无效")
    public static final int RET_USER_EMAIL_ILLEGAL = 30020017;

    @RetLabel("邮箱地址已存在")
    public static final int RET_USER_EMAIL_EXISTS = 30020018;

    @RetLabel("微信绑定号无效")
    public static final int RET_USER_WXID_ILLEGAL = 30020019;

    @RetLabel("微信绑定号已存在")
    public static final int RET_USER_WXID_EXISTS = 30020020;

    @RetLabel("绑定微信号失败")
    public static final int RET_USER_WXID_BIND_FAIL = 30020021;

    @RetLabel("QQ绑定号无效")
    public static final int RET_USER_QQID_ILLEGAL = 30020022;

    @RetLabel("QQ绑定号已存在")
    public static final int RET_USER_QQID_EXISTS = 30020023;

    @RetLabel("绑定QQ号失败")
    public static final int RET_USER_QQID_BIND_FAIL = 30020024;

    @RetLabel("获取绑定QQ信息失败")
    public static final int RET_USER_QQID_INFO_FAIL = 30020025;

    @RetLabel("验证码无效")
    public static final int RET_USER_RANDCODE_ILLEGAL = 30020026; //邮件或者短信验证码

    @RetLabel("验证码已过期")
    public static final int RET_USER_RANDCODE_EXPIRED = 30020027; //邮件或者短信验证码

    @RetLabel("验证码错误或失效")
    public static final int RET_USER_CAPTCHA_ILLEGAL = 30020028; //图片验证码

    @RetLabel("用户类型无效")
    public static final int RET_USER_TYPE_ILLEGAL = 30020029;

    @RetLabel("用户设备ID无效")
    public static final int RET_USER_APPTOKEN_ILLEGAL = 30020030;

    //-----------------------------------------------------------------------------------------------------------
    protected static final Map<String, Map<Integer, String>> rets = RetLabel.RetLoader.loadMap(OssRetCodes.class);

    protected static final Map<Integer, String> defret = rets.get("");

    public static RetResult retResult(int retcode) {
        if (retcode == 0) return RetResult.success();
        return new RetResult(retcode, retInfo(retcode));
    }

    public static RetResult retResult(String locale, int retcode) {
        if (retcode == 0) return RetResult.success();
        return new RetResult(retcode, retInfo(locale, retcode));
    }

    public static RetResult retResult(int retcode, Object... args) {
        if (retcode == 0) return RetResult.success();
        if (args == null || args.length < 1) return new RetResult(retcode, retInfo(retcode));
        String info = MessageFormat.format(retInfo(retcode), args);
        return new RetResult(retcode, info);
    }

    public static RetResult retResult(String locale, int retcode, Object... args) {
        if (retcode == 0) return RetResult.success();
        if (args == null || args.length < 1) return new RetResult(retcode, retInfo(locale, retcode));
        String info = MessageFormat.format(retInfo(locale, retcode), args);
        return new RetResult(retcode, info);
    }

    public static <T> CompletableFuture<RetResult<T>> retResultFuture(int retcode) {
        return CompletableFuture.completedFuture(retResult(retcode));
    }

    public static <T> CompletableFuture<RetResult<T>> retResultFuture(String locale, int retcode) {
        return CompletableFuture.completedFuture(retResult(locale, retcode));
    }

    public static <T> CompletableFuture<RetResult<T>> retResultFuture(int retcode, Object... args) {
        return CompletableFuture.completedFuture(retResult(retcode, args));
    }

    public static <T> CompletableFuture<RetResult<T>> retResultFuture(String locale, int retcode, Object... args) {
        return CompletableFuture.completedFuture(retResult(locale, retcode, args));
    }

    public static RetResult set(RetResult result, int retcode, Object... args) {
        if (retcode == 0) return result.retcode(0).retinfo("");
        if (args == null || args.length < 1) return result.retcode(retcode).retinfo(retInfo(retcode));
        String info = MessageFormat.format(retInfo(retcode), args);
        return result.retcode(retcode).retinfo(info);
    }

    public static RetResult set(RetResult result, String locale, int retcode, Object... args) {
        if (retcode == 0) return result.retcode(0).retinfo("");
        if (args == null || args.length < 1) return result.retcode(retcode).retinfo(retInfo(locale, retcode));
        String info = MessageFormat.format(retInfo(locale, retcode), args);
        return result.retcode(retcode).retinfo(info);
    }

    public static String retInfo(int retcode) {
        if (retcode == 0) return "Success";
        return defret.getOrDefault(retcode, "Error");
    }

    public static String retInfo(String locale, int retcode) {
        if (locale == null || locale.isEmpty()) return retInfo(retcode);
        if (retcode == 0) return "Success";
        String key = locale == null ? "" : locale;
        Map<Integer, String> map = rets.get(key);
        if (map == null) return "Error";
        return map.getOrDefault(retcode, "Error");
    }
}
