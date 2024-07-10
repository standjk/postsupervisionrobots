package com.gzb.postsupervisionrobots.utils;

import java.util.HashMap;
import java.util.Map;

public class CurrencyCodes {
    private static final Map<String, String> CURRENCY_MAP = new HashMap<>();

    static {
        CURRENCY_MAP.put("人民币", "156");
        CURRENCY_MAP.put("美元", "840");
        CURRENCY_MAP.put("欧元", "978");
        CURRENCY_MAP.put("港币", "344");
        // 初始化其他映射...
    }

    public static String getCodeByCurrencyName(String currencyName) {
        return CURRENCY_MAP.getOrDefault(currencyName, null);
    }
}
