package pers.XiaoShadiao.obfuscator.commandline.commands;

import pers.XiaoShadiao.obfuscator.config.Config;

import java.io.File;
import java.io.FileNotFoundException;

public class CommandOutJar extends CommandFile {
    @Override
    public String getCmdName() {
        return "outJar";
    }

    @Override
    public String getCmdDesc() {
        return "设置混淆后输出的jar文件";
    }

    @Override
    public boolean execute(String[] args) throws Exception {
        Config.outputFile = new File(args[0]);
        return true;
    }
}
