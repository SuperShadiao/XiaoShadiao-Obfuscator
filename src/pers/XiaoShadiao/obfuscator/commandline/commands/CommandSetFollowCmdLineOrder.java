package pers.XiaoShadiao.obfuscator.commandline.commands;

import pers.XiaoShadiao.obfuscator.config.Config;
import pers.XiaoShadiao.obfuscator.interfaces.ICommandExecutor;

public class CommandSetFollowCmdLineOrder implements ICommandExecutor {
    @Override
    public String getCmdName() {
        return "visitorFollowCmdLineOrder";
    }

    @Override
    public String getCmdArgs() {
        return "";
    }

    @Override
    public String getCmdDesc() {
        return "各个Transfomer按照你的命令行顺序进行混淆, 若不加此参数则按照小沙雕规定的顺序混淆 (注: 启用后可能会发生VerifyError)";
    }

    @Override
    public boolean execute(String[] args) throws Exception {
        Config.visitorFollowCmdLineOrder = true;
        return true;
    }
}
