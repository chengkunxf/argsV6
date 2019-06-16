package com.coding.args;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ArgsTest {

    /**
     * 1. 框定需求范围,写一个测试用例,测试关键节点和边界,完成后,可以 Ignore 掉
     * 2. 解析 schema ,传入 schemaText->l:boolean p:integer d:string 解析完成后可以获取 size,获取 defaultValue,获取 type
     * 3. 当传入的参数和类型不匹配时,抛出异常和合适的明确的异常信息
     * 4. 解析 args 命令,可以解析单个的 -l true l:boolean,解析完成后,可以用 getValue("l")获取,需要定义个 map 存放解析的值
     * 5. 解析 args 命令,可以解析默认的 args -l l:boolean,解析完成后,可以用 getValue("l")获取
     * 6. 解析多个 args 命令,传入 -l -p 8080 -d /usr/log l:boolean p:integer d:string
     * 7. 扩展需求,当指令集传入 多个参数 -g this,is,all 可以解析,getValue("l") 获取后, new String[]{"this","is","all"}
     */

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
//    @Ignore
    public void should_test_demo() {
        String argsText = "-l -p 8080 -d /usr/log";

        SchemaDesc schemaDesc = new SchemaDesc("l:boolean p:integer d:string");

        Args args = new Args(argsText, schemaDesc);
        assertThat(args.getValue("l"), is(false));
        assertThat(args.getValue("p"), is(8080));
        assertThat(args.getValue("d"), is("/usr/log"));
    }

    @Test
    public void should_test_args_array_demo() {
        String argsText = "-l -p 8080 -d /usr/log -g this,is,all -f 80,90,100";

        SchemaDesc schemaDesc = new SchemaDesc("l:boolean p:integer d:string g:string[] f:integer[]");

        Args args = new Args(argsText, schemaDesc);
        assertThat(args.getValue("l"), is(false));
        assertThat(args.getValue("p"), is(8080));
        assertThat(args.getValue("d"), is("/usr/log"));
        assertThat(args.getValue("g"), is(new String[]{"this", "is", "all"}));
        assertThat(args.getValue("f"), is(new Integer[]{80, 90, 100}));
    }

    @Test
    public void should_parse_schema() {

        SchemaDesc schemaDesc = new SchemaDesc("l:boolean p:integer d:string");

        assertThat(schemaDesc.getSize(), is(3));
        assertThat(schemaDesc.getDefaultValue("l"), is(false));
        assertThat(schemaDesc.getType("l"), is("boolean"));

        assertThat(schemaDesc.getDefaultValue("p"), is(0));
        assertThat(schemaDesc.getType("p"), is("integer"));

        assertThat(schemaDesc.getDefaultValue("d"), is(""));
        assertThat(schemaDesc.getType("d"), is("string"));

    }

    @Test
    public void should_throw_illegalArgumentException() {
        SchemaDesc schemaDesc = new SchemaDesc("l:boolean p:integer d:string b:byte");

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("This byte type is not support.");
        assertThat(schemaDesc.getDefaultValue("b"), is("byte"));
    }

    @Test
    public void should_parse_one_args_and_one_schema() {
        SchemaDesc schemaDesc = new SchemaDesc("l:boolean");
        Args args = new Args("-l true", schemaDesc);
        assertThat(args.getValue("l"), is(true));

        schemaDesc = new SchemaDesc("p:integer");
        args = new Args("-p 8080", schemaDesc);
        assertThat(args.getValue("p"), is(8080));

        schemaDesc = new SchemaDesc("d:string");
        args = new Args("-d /usr/log", schemaDesc);
        assertThat(args.getValue("d"), is("/usr/log"));

    }

    @Test
    public void should_parse_one_default_args_and_one_schema() {
        SchemaDesc schemaDesc = new SchemaDesc("l:boolean");
        Args args = new Args("-l", schemaDesc);
        assertThat(args.getValue("l"), is(false));

        schemaDesc = new SchemaDesc("p:integer");
        args = new Args("-p", schemaDesc);
        assertThat(args.getValue("p"), is(0));

        schemaDesc = new SchemaDesc("d:string");
        args = new Args("-d", schemaDesc);
        assertThat(args.getValue("d"), is(""));

    }
}