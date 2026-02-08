package pers.XiaoShadiao.obfuscator.commandline.commands;

import pers.XiaoShadiao.obfuscator.config.Config;
import pers.XiaoShadiao.obfuscator.interfaces.ICommandExecutor;

public class CommandNoVerify implements ICommandExecutor {
    @Override
    public String getCmdName() {
        return "noVerify";
    }

    @Override
    public String getCmdArgs() {
        return "";
    }

    @Override
    public String getCmdDesc() {
        return "混淆后不对字节码进行验证 (若混淆后的文件放在运行环境若jvm不带-noverify参数, 有问题的字节码仍然会抛出VerifyError)";
    }

    @Override
    public boolean execute(String[] args) throws Exception {
        Config.dontverify = true;
        return true;
    }
}
