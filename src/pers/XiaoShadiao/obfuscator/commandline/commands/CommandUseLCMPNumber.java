package pers.XiaoShadiao.obfuscator.commandline.commands;

import pers.XiaoShadiao.obfuscator.config.Config;
import pers.XiaoShadiao.obfuscator.interfaces.ICommandExecutor;

public class CommandUseLCMPNumber implements ICommandExecutor {
    @Override
    public String getCmdName() {
        return "useLCMPNumber";
    }

    @Override
    public String getCmdArgs() {
        return "";
    }

    @Override
    public String getCmdDesc() {
        return "部分数字使用LCMP字节码混淆";
    }

    @Override
    public boolean execute(String[] args) throws Exception {
        Config.useLCMPNumber = true;
        return true;
    }
}
