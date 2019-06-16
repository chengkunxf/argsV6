package com.coding.args;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ArgsValueServiceFactory {

    public static final Map<String, ArgsValueServiceIF> argsValueServiceMap = new HashMap<>();

    static {
        argsValueServiceMap.put("boolean", (flagValue -> Boolean.valueOf(flagValue)));
        argsValueServiceMap.put("integer", (flagValue -> Integer.valueOf(flagValue)));
        argsValueServiceMap.put("string", (flagValue -> String.valueOf(flagValue)));
        argsValueServiceMap.put("string[]", (flagValue -> flagValue.split(",")));
        argsValueServiceMap.put("integer[]", (flagValue -> {
            String[] flagValueArray = flagValue.split(",");
            int[] ints = Arrays.stream(flagValueArray).mapToInt(Integer::parseInt).toArray();
            return ArrayUtils.toObject(ints);
        }));
    }

    public static ArgsValueServiceIF getArgsValueServiceStrategy(String type) {
        return argsValueServiceMap.get(type);
    }
}
