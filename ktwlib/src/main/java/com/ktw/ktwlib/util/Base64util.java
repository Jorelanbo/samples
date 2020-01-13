package com.ktw.ktwlib.util;

// Created by JS on 2019/11/26.

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class Base64util {

    /**
     * 字符Base64加密
     * @param str
     * @return
     */
    public static String encodeToString(String str){
        return Base64.encodeToString(str.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT);
    }
    /**
     * 字符Base64解密
     * @param str
     * @return
     */
    public static String decodeToString(String str){
        return new String(Base64.decode(str.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT));
    }
}
