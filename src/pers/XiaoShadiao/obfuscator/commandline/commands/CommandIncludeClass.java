package pers.XiaoShadiao.obfuscator.commandline.commands;

import pers.XiaoShadiao.obfuscator.visitor.AbstractVisitor;

import java.util.Arrays;

public class CommandIncludeClass extends CommandClass {
    @Override
    public String getCmdName() {
        return "inClass";
    }

    @Override
    public String getCmdDesc() {
        return "输入要混淆的类, -inClass后面所有参数都为要混淆的类, 直到-结束该参数, 使用正则表达式匹配, 例如: -inClass com.example.packageA(.*) com.example.packageB(.*)";
    }

    @Override
    public boolean execute(String[] args) {
        AbstractVisitor.loadIncludeClasses(Arrays.asList(args));
        return true;
    }
}
