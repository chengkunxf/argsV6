package com.coding.args;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SchemaDesc {
    private String schemaText;

    public Map<String, Object> defaultValueMap = new HashMap() {
        {
            put("boolean", false);
            put("integer", 0);
            put("string", "");
        }
    };


    public SchemaDesc(String schemaText) {
        this.schemaText = schemaText;
    }

    public int getSize() {
        return schemaText.split(" ").length;
    }

    public Object getDefaultValue(String flag) {
        String type = getType(flag);
        if(!defaultValueMap.containsKey(type)){
            throw new IllegalArgumentException(String.format("This %s type is not support.", type));
        }
        return defaultValueMap.get(type);
    }

    public String getType(String flag) {
        // l:boolean p:integer d:string
        String[] schemaTextArray = schemaText.split(" ");
        String oneSchema = Arrays.stream(schemaTextArray).filter(e -> e.startsWith(flag)).findAny().get();
        return oneSchema.split(":")[1];
    }
}
