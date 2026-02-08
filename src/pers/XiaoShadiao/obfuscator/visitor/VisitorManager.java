package pers.XiaoShadiao.obfuscator.visitor;

import pers.XiaoShadiao.obfuscator.visitor.visitorfactory.VisitorFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VisitorManager {

    private static final List<VisitorFactory> visitorFactories = new ArrayList<>();
    private static final List<VisitorFactory> enableFactories = new ArrayList<>();

    static {
        visitorFactories.addAll(Arrays.asList(VisitorFactory.values()));
    }

    public static void enableVisitor(VisitorFactory visitorFactory) {
        enableFactories.add(visitorFactory);
        AbstractVisitor.registerVisitorFactory(visitorFactory);
    }

    public static boolean isVisitorEnabled(VisitorFactory visitorFactory) {
        return AbstractVisitor.isVisitorFactoryRegistered(visitorFactory);
    }

}
