package pers.XiaoShadiao.obfuscator.visitor.visitorfactory;

import pers.XiaoShadiao.obfuscator.interfaces.IVisitor;
import pers.XiaoShadiao.obfuscator.interfaces.IVisitorFactory;

public abstract class OverrideVisitorFactory implements IVisitorFactory {

    private final IVisitorFactory parent;

    public OverrideVisitorFactory(IVisitorFactory parent) {
        this.parent = parent;
    }

    @Override
    public IVisitor getVisitor(byte[] bytes) {
        return parent.getVisitor(bytes);
    }

    @Override
    public byte[] transfer(byte[] bytes) {
        return parent.transfer(bytes);
    }

    @Override
    public Class<? extends IVisitor> getVisitorType() {
        return parent.getVisitorType();
    }

    @Override
    public int getOrder() {
        return parent.getOrder();
    }

}
