package com.coding.args;

import java.util.HashMap;
import java.util.Map;

public class Args {
    private final String argsText;
    private final SchemaDesc schemaDesc;

    private final Map<String, Object> argsMap = new HashMap<>();

    public Args(String argsText, SchemaDesc schemaDesc) {

        this.argsText = argsText;
        this.schemaDesc = schemaDesc;

        parse();
    }

    private void parse() {
        // -l true l:boolean

        String[] argsTextArray = argsText.split("-");
        for (int i = 0; i < argsTextArray.length; i++) {
            String oneArgStr = argsTextArray[i];
            String[] oneArgArray = oneArgStr.split(" ");
            String flag = oneArgArray[0];
            String flagValue = oneArgArray.length > 1 ? oneArgArray[1] : schemaDesc.getDefaultValue(flag).toString();
            String type = schemaDesc.getType(flag);

            Object realValue = ArgsValueServiceFactory.getArgsValueServiceStrategy(type).getArgsValue(flagValue);
            argsMap.put(flag,realValue);
        }
    }

    public Object getValue(String flag) {
        return argsMap.get(flag);
    }


}
