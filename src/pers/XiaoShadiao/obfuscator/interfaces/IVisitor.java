package pers.XiaoShadiao.obfuscator.interfaces;

import java.util.List;

public interface IVisitor {

    public boolean isIn();
    public boolean isEx();

    public byte[] transfer();
    public byte[] transfer(byte[] bytes);

    public List<String> getVisitorTags();

    public String getClassName();
    public String getTransformerName();

}
