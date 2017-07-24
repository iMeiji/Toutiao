package com.meiji.toutiao.util;

import com.meiji.toutiao.Constant;
import com.meiji.toutiao.ErrorAction;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Meiji on 2017/7/24.
 */

public class ToutiaoUtil {

    /**
     * 生成 as 和 cp , 用作 API 的请求参数
     * <p>
     * function () {
     * var t = Math.floor((new Date).getTime() / 1e3), i = t.toString(16).toUpperCase(), e = md5(t).toString().toUpperCase();
     * if (8 != i.length)return {as: "479BB4B7254C150", cp: "7E0AC8874BB0985"};
     * for (var s = e.slice(0, 5), o = e.slice(-5), n = "", a = 0; 5 > a; a++)n += s[a] + i[a];
     * for (var l = "", r = 0; 5 > r; r++)l += i[r + 3] + o[r];
     * return {as: "A1" + n + i.slice(-3), cp: i.slice(0, 3) + l + "E1"}
     * }
     * </p>
     */
    public static Map<String, String> getAsCp() {
        int t = (int) (System.currentTimeMillis() / 1000);
        String i = Integer.toHexString(t).toUpperCase();
        String e = getMD5(t + "").toUpperCase();

        String s = e.substring(0, 5);
        String o = e.substring(e.length() - 5, e.length());
        String n = "";
        for (int j = 0; 5 > j; j++) {
            n += s.substring(j, j + 1) + i.substring(j, j + 1);
        }

        String l = "";
        for (int r = 0; 5 > r; r++) {
            l += i.substring(r + 3, r + 3 + 1) + o.substring(r, r + 1);
        }

        String as = "A1" + n + i.substring(i.length() - 3, i.length());
        String cp = i.substring(0, 3) + l + "E1";

        Map<String, String> map = new HashMap<>();
        map.put(Constant.AS, as);
        map.put(Constant.CP, cp);
        return map;
    }

    /**
     * 对字符串 MD5 加密
     */
    public static String getMD5(String str) {
        try {
            // 生成一个 MD5 加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算 MD5 函数
            md.update(str.getBytes());
            // digest() 最后确定返回 MD5 hash 值, 返回值为8为字符串
            // 因为 MD5 hash 值是16位的hex值, 实际上就是8位的字符
            // BigInteger 函数则将8位的字符串转换成 16 位 hex 值, 用字符串来表示得到字符串形式的 hash 值
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            ErrorAction.print(e);
        }
        return "";
    }
}
