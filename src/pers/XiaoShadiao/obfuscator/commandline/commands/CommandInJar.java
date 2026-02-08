package pers.XiaoShadiao.obfuscator.commandline.commands;

import pers.XiaoShadiao.obfuscator.Main;
import pers.XiaoShadiao.obfuscator.config.Config;

import java.io.File;
import java.io.FileNotFoundException;

public class CommandInJar extends CommandFile {
    @Override
    public String getCmdName() {
        return "inJar";
    }

    @Override
    public String getCmdDesc() {
        return "设置要混淆的jar文件";
    }

    @Override
    public boolean execute(String[] args) throws Exception {
        File temp = Config.inputFile = new File(args[0]);
        if (!temp.exists()) {
            throw new FileNotFoundException("文件不存在");
        }
        Main.loader.addURL(temp.toURI().toURL());
        return true;
    }
}
