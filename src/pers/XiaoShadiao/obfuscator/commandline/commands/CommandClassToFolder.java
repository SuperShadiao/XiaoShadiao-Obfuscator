package pers.XiaoShadiao.obfuscator.commandline.commands;

import pers.XiaoShadiao.obfuscator.config.Config;
import pers.XiaoShadiao.obfuscator.interfaces.ICommandExecutor;

public class CommandClassToFolder implements ICommandExecutor {
    @Override
    public String getCmdName() {
        return "classToFolder";
    }

    @Override
    public String getCmdArgs() {
        return "";
    }

    @Override
    public String getCmdDesc() {
        return "将.class文件全部变成文件夹的形式";
    }

    @Override
    public boolean execute(String[] args) throws Exception {
        Config.classToFolder = true;
        return false;
    }
}
