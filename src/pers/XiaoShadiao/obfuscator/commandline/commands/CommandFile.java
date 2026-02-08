package pers.XiaoShadiao.obfuscator.commandline.commands;

import pers.XiaoShadiao.obfuscator.interfaces.ICommandExecutor;

public abstract class CommandFile implements ICommandExecutor {
    @Override
    public String getCmdArgs() {
        return "<file>";
    }
}
