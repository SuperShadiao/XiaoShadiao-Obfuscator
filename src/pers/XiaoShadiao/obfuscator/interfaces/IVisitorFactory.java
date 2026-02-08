package pers.XiaoShadiao.obfuscator.interfaces;

public interface IVisitorFactory {

    public IVisitor getVisitor(byte[] bytes);
    public byte[] transfer(byte[] bytes);
    public Class<? extends IVisitor> getVisitorType();

    public int getOrder();

}
