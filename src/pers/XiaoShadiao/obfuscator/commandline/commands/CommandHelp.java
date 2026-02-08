package pers.XiaoShadiao.obfuscator.commandline.commands;

import pers.XiaoShadiao.obfuscator.Main;
import pers.XiaoShadiao.obfuscator.interfaces.ICommandExecutor;

public class CommandHelp implements ICommandExecutor {
    @Override
    public String getCmdName() {
        return "?";
    }

    @Override
    public String getCmdArgs() {
        return "";
    }

    @Override
    public String getCmdDesc() {
        return "显示本帮助";
    }

    @Override
    public boolean execute(String[] args) {
        Main.printHelp();
        return true;
    }
}
