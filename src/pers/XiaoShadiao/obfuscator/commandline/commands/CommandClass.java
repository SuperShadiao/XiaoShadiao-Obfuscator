package pers.XiaoShadiao.obfuscator.commandline.commands;

import pers.XiaoShadiao.obfuscator.interfaces.ICommandExecutor;

public abstract class CommandClass implements ICommandExecutor {
    @Override
    public String getCmdArgs() {
        return "<class1> [class2] [class3] ...";
    }

}
