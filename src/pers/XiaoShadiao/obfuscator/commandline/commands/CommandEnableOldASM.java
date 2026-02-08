package pers.XiaoShadiao.obfuscator.commandline.commands;

import pers.XiaoShadiao.obfuscator.config.Config;
import pers.XiaoShadiao.obfuscator.interfaces.ICommandExecutor;

public class CommandEnableOldASM implements ICommandExecutor {
    @Override
    public String getCmdName() {
        return "verifyWith508ASM";
    }

    @Override
    public String getCmdArgs() {
        return "";
    }

    @Override
    public String getCmdDesc() {
        return "使用5.0.8版本的ASM对字节码进行验证, 适用于Minecraft Forge 1.8.9的Mod";
    }

    @Override
    public boolean execute(String[] args) throws Exception {
        Config.verifyWith508ASM = true;
        return true;
    }
}
