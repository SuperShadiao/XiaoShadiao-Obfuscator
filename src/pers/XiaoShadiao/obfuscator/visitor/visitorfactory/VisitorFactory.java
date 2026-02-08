package pers.XiaoShadiao.obfuscator.visitor.visitorfactory;

import pers.XiaoShadiao.obfuscator.config.Config;
import pers.XiaoShadiao.obfuscator.interfaces.ICommandExecutor;
import pers.XiaoShadiao.obfuscator.interfaces.IVisitor;
import pers.XiaoShadiao.obfuscator.interfaces.IVisitorFactory;
import pers.XiaoShadiao.obfuscator.visitor.*;

public enum VisitorFactory implements IVisitorFactory, ICommandExecutor {

    LocalVar("localVarObf", "(false/true)", "对局部变量进行混淆, 使用参数false来删除变量, 使用参数true来使用-applymap的设置来重命名变量, 默认false", LocalVarObfVisitor::new, LocalVarObfVisitor.class),
    YuShengJunObf("yuShengJunObf", "", "将继承java.lang.Object的类全部改为继承余胜军", YuShengJunObfVisitor::new, YuShengJunObfVisitor.class),
    LabelResorter("labelResorter", "", "将代码分块打乱顺序, 不稳定, 容易出现VerifyError", LabelResorterObfVisitor::new, LabelResorterObfVisitor.class),
    FieldMethodResorter("fieldMethodResorter", "", "将类的字段和方法的顺序打乱", FieldMethodResorterObfVisitor::new, FieldMethodResorterObfVisitor.class),
    SwitchCaseObfVisitor("junkSwitchCaseObf", "", "添加一些垃圾switch case代码", SwitchCaseObfVisitor::new, SwitchCaseObfVisitor.class),
    JunkTryCatchObf("junkTryCatchObf", "", "添加一些垃圾try catch代码", JunkTryCatchBlockObfVisitor::new, JunkTryCatchBlockObfVisitor.class),
    AntiDebugger("useAntiDebugger", "", "插入反调试代码", (bytes, args) -> { Config.useAntiDebugger = true; return new AntiDebuggerObfVisitor(bytes, args); }, AntiDebuggerObfVisitor.class),
    StringObf("stringObf", "(false/true)", "对字符串进行混淆, 使用参数false进行简单混淆, 使用参数true则在加密部分添加一些Throwable类, 默认false", (bytes, args) -> args.length > 0 && Boolean.parseBoolean(args[0]) ? new ThrowableStringObfVisitor(bytes, args) : new SimpleStringObfVisitor(bytes, args), SimpleStringObfVisitor.class),
    SimpleJunkLabelObf("junkThrowObf", "", "添加一些垃圾throw死代码", SimpleJunkLabelObfVisitor::new, SimpleJunkLabelObfVisitor.class),
    NumberObf("numberObf", "", "对数字用复杂计算的方式进行混淆", NumberObfVisitor::new, NumberObfVisitor.class),
    InvokeDynamicObf("invokeDynamicObf", "(false/true)", "对方法的调用进行InvokeDynamic混淆, 使用false进行简单混淆, 使用true则在加密部分添加一些Throwable类, 默认false", (bytes, args) -> args.length > 0 && Boolean.parseBoolean(args[0]) ? new ThrowableInvokeDynamicObfVisitor(bytes, args) : new InvokeDynamicObfVisitor(bytes, args), InvokeDynamicObfVisitor.class),
    SOFCrasherObfVistor("SOFCrasher", "", "尝试令反编译器发生SOF异常", SOFCrasherObfVistor::new, SOFCrasherObfVistor.class),
    MethodThrowableSignRemoverObfVisitor("removeMethodThrows", "", "移除方法体右边的throws语句", MethodThrowableSignRemoverObfVisitor::new, MethodThrowableSignRemoverObfVisitor.class),
    SyntheticFlag("syntheticFlag", "(false/true)", "对所有方法和字段添加Synthetic和Bridge访问标志, 以使部分反编译器不会显示这些方法和字段, 使用参数true来添加一些玩笑字段, 默认true", SyntheticBridgeApplyerObfVisitor::new, SyntheticBridgeApplyerObfVisitor.class),
    ;

    public interface Creator<T extends IVisitor> {
        public T createVisitor(byte[] bytes, String[] args);
    }

    private final Creator<? extends IVisitor> creator;
    private final String cmdName;
    private final String cmdArgs;
    private String[] cmdArgsArray = new String[0];
    private final String cmdDesc;
    private final Class<? extends IVisitor> visitorType;

    <T extends IVisitor> VisitorFactory(String cmdName, String cmdArgs, String cmdDesc, Creator<T> creator, Class<T> visitorType) {
        this.creator = creator;
        this.cmdName = cmdName;
        this.cmdArgs = cmdArgs;
        this.cmdDesc = cmdDesc;
        this.visitorType = visitorType;
    }

    @Override
    public IVisitor getVisitor(byte[] bytes) {
        return creator.createVisitor(bytes, cmdArgsArray);
    }

    @Override
    public byte[] transfer(byte[] bytes) {
        return getVisitor(bytes).transfer();
    }

    @Override
    public Class<? extends IVisitor> getVisitorType() {
        return visitorType;
    }

    @Override
    public int getOrder() {
        return ordinal();
    }

    @Override
    public String getCmdName() {
        return cmdName;
    }

    @Override
    public String getCmdArgs() {
        return cmdArgs;
    }

    @Override
    public String getCmdDesc() {
        return cmdDesc;
    }

    @Override
    public boolean execute(String[] args) {
        cmdArgsArray = args;
        VisitorManager.enableVisitor(this);
        return true;
    }

}
