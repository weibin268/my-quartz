package com.zhuang.quartz.util;

public class PathUtils {

    public static String getCurrentDirectory() {
        //return System.getProperty("user.dir");
        return PathUtils.class.getResource("/").getPath();
    }

}
