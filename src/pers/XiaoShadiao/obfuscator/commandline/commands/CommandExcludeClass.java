package pers.XiaoShadiao.obfuscator.commandline.commands;

import pers.XiaoShadiao.obfuscator.visitor.AbstractVisitor;

import java.util.Arrays;

public class CommandExcludeClass extends CommandClass {
    @Override
    public String getCmdName() {
        return "exClass";
    }

    @Override
    public String getCmdDesc() {
        return "输入要在inClass中排除的类, 直到-结束该参数, 使用正则表达式匹配, 例如: -inClass com.example.packageA.dontobf(.*) com.example.packageB.dontobf(.*)";
    }

    @Override
    public boolean execute(String[] args) {
        AbstractVisitor.loadExcludeClasses(Arrays.asList(args));
        return true;
    }
}
