package pers.XiaoShadiao.obfuscator.commandline.commands;

import pers.XiaoShadiao.obfuscator.config.Config;
import pers.XiaoShadiao.obfuscator.interfaces.ICommandExecutor;

public class CommandSecurityManagerAntiDebugger implements ICommandExecutor {
    @Override
    public String getCmdName() {
        return "useSecurityManagerAntiDebugger";
    }

    @Override
    public String getCmdArgs() {
        return "";
    }

    @Override
    public String getCmdDesc() {
        return "在代码中插入SecurityManager的反调试代码";
    }

    @Override
    public boolean execute(String[] args) throws Exception {
        Config.useSecurityManagerAntiDebugger = true;
        return true;
    }
}
