package com.mmall.permission.utils;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;

public class LevelUtil {
    private final static String SEPARATOR = ".";

    public final static String ROOT = "0";

    // 0
    // 0.1
    // 0.1.2
    // 0.1.3
    // 0.4
    public static String calculateLevel(String parentLevel, int parentId) {
        if (Strings.isNullOrEmpty(parentLevel)) {
            return ROOT;
        } else {
            return Joiner.on(SEPARATOR).join(parentLevel, parentId);
        }
    }
}
