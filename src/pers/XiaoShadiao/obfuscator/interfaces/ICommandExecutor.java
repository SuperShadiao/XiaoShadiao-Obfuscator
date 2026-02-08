package pers.XiaoShadiao.obfuscator.interfaces;

public interface ICommandExecutor {

    public String getCmdName();
    public String getCmdArgs();
    public String getCmdDesc();
    public boolean execute(String[] args) throws Exception;

}
