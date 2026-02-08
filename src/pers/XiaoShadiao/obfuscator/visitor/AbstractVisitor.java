package pers.XiaoShadiao.obfuscator.visitor;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodNode;
import pers.XiaoShadiao.obfuscator.config.Config;
import pers.XiaoShadiao.obfuscator.interfaces.IVisitor;
import pers.XiaoShadiao.obfuscator.interfaces.IVisitorFactory;
import pers.XiaoShadiao.obfuscator.utils.ClassWriter1;
import pers.XiaoShadiao.obfuscator.utils.Utils;
import pers.XiaoShadiao.obfuscator.utils.customclassloader.ClassVerifyer;

import java.util.*;

public abstract class AbstractVisitor implements IVisitor {

    protected static List<String> includeClasses = Collections.emptyList();
    protected static List<String> excludeClasses = Collections.emptyList();

    public static void loadIncludeClasses(List<String> includeClasses) {
        AbstractVisitor.includeClasses = includeClasses;
    }

    public static void loadExcludeClasses(List<String> excludeClasses) {
        AbstractVisitor.excludeClasses = excludeClasses;
    }

    public static List<String> getIncludeClasses() {
        return includeClasses;
    }

    public static List<String> getExcludeClasses() {
        return excludeClasses;
    }

    protected final ClassReader cr;
    protected final String[] args;
    protected final byte[] originalBytes;

    public AbstractVisitor(ClassReader cr, String[] args) {
        this.cr = cr;
        this.args = args;
        this.originalBytes = Arrays.copyOf(cr.b, cr.b.length);
    }

    public AbstractVisitor(byte[] bytes, String[] args) {
        this(new ClassReader(bytes), args);
    }

    public byte[] getOriginalBytes() {
        return originalBytes;
    }

    public ClassReader getClassReader() {
        return cr;
    }

    public boolean isIn() {
        for(String inn : includeClasses) {
            if(cr.getClassName().matches(inn)) {
                return true;
            }
        }
        return false;
    }

    public boolean isEx() {
        for(String ex : excludeClasses) {
            if(cr.getClassName().matches(ex)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public byte[] transfer() {
        return transfer(originalBytes);
    }

    @Override
    public String getClassName() {
        return cr.getClassName();
    }

    @Override
    public String getTransformerName() {
        return this.getClass().getSimpleName();
    }

    public boolean getBooleanArgOrDefault(int argIndex, boolean defaultValue) {
        if(args.length > argIndex) {
            return Boolean.parseBoolean(args[argIndex]);
        }
        return defaultValue;
    }

    public String getStringArgOrDefault(int argIndex, String defaultValue) {
        if(args.length > argIndex) {
            return args[argIndex];
        }
        return defaultValue;
    }

    public int getIntArgOrDefault(int argIndex, int defaultValue) {
        if(args.length > argIndex) {
            return Integer.parseInt(args[argIndex]);
        }
        return defaultValue;
    }

    public double getDoubleArgOrDefault(int argIndex, double defaultValue) {
        if(args.length > argIndex) {
            return Double.parseDouble(args[argIndex]);
        }
        return defaultValue;
    }

    public static List<IVisitorFactory> visitorFactories = new ArrayList<>();

    public static void registerVisitorFactory(IVisitorFactory factory) {
        visitorFactories.add(factory);
    }

    public static boolean isVisitorFactoryRegistered(IVisitorFactory factory) {
        return visitorFactories.contains(factory);
    }

    private static boolean isInited;

    public static void initOrder() {
        if(!Config.visitorFollowCmdLineOrder) visitorFactories.sort(Comparator.comparingInt(IVisitorFactory::getOrder));
        isInited = true;
    }

    public static TransferResult transferClass(byte[] bytes) {
        assert isInited;
        byte[] originalBytes = bytes;
        List<Map.Entry<String, Throwable>> exceptions = new ArrayList<>();
        IVisitor visitor = null;
        for(IVisitorFactory factory : visitorFactories) {
            visitor = factory.getVisitor(bytes);
            try {
                if(visitor.isIn() && !visitor.isEx()) {
                    byte[] transfer = visitor.transfer();
                    ClassVerifyer.verify(transfer);
                    bytes = transfer;
                }
            } catch (StackOverflowError e) {
                exceptions.add(new AbstractMap.SimpleEntry<>(visitor.getTransformerName(), new RuntimeException("请携带JVM参数 -Xss200M 再使用SOFCrasher!")));
            } catch (Throwable e) {
                exceptions.add(new AbstractMap.SimpleEntry<>(visitor.getTransformerName(), e));
            }
        }
        try {
            if(visitor != null && visitor.isIn() && !visitor.isEx()) ClassVerifyer.verify(bytes, true);
        } catch (Throwable e) {
            exceptions.add(new AbstractMap.SimpleEntry<>("OldASMVerifying", e));
            bytes = originalBytes;
        }
        return new TransferResult(visitor == null ? "nothing" : visitor.getClassName(), bytes, exceptions);
    }

    public static TransferResult transferClassWithFactories(byte[] bytes, IVisitorFactory... factories) {
        List<Map.Entry<String, Throwable>> exceptions = new ArrayList<>();
        IVisitor visitor = null;
        for(IVisitorFactory factory : factories) {
            visitor = factory.getVisitor(bytes);
            // System.out.println(visitor + " " + visitor.getClassName());
            try {
                byte[] transfer = visitor.transfer();
                ClassVerifyer.verify(transfer);
                bytes = transfer;
            } catch (Throwable e) {
                exceptions.add(new AbstractMap.SimpleEntry<>(visitor.getTransformerName(), e));
            }
        }
        return new TransferResult(visitor == null ? "nothing" : visitor.getClassName(), bytes, exceptions);
    }

    public static ClassNode byteToClassNode(byte[] bytes) {
        return readerToClassNode(new ClassReader(bytes));
    }

    public static ClassNode readerToClassNode(ClassReader cr) {
        ClassNode cn = new ClassNode();
        cr.accept(cn, 0);
        return cn;
    }

    public static byte[] classNodeToBytes(ClassNode cn) {
        return classNodeToBytes(cn, ClassWriter1.COMPUTE_FRAMES | ClassWriter1.COMPUTE_MAXS);
    }

    public static byte[] classNodeToBytes(ClassNode cn, int flags) {
        ClassWriter1 cw = new ClassWriter1(flags);
        cn.accept(cw);
        return cw.toByteArray();
    }

    public static class TransferResult {
        public final String className;
        public final byte[] bytes;
        public final List<Map.Entry<String, Throwable>> exceptions;

        public TransferResult(String className, byte[] bytes, List<Map.Entry<String, Throwable>> exceptions) {
            this.className = className;
            this.bytes = bytes;
            this.exceptions = exceptions;
        }
    }

    public static boolean isClinitNode(MethodNode mn) {
        return mn.name.equals("<clinit>");
    }

    public static boolean isInitNode(MethodNode mn) {
        return mn.name.equals("<init>");
    }

    public static MethodNode getOrCreateClinitNode(ClassNode cn) {
        for(MethodNode mn : cn.methods) {
            if(isClinitNode(mn)) {
                return mn;
            }
        }
        MethodNode initNode = new MethodNode(
                Opcodes.ACC_STATIC,
                "<clinit>",
                "()V",
                null,
                null
        );
        cn.methods.add(initNode);
        initNode.instructions.add(new InsnNode(Opcodes.RETURN));
        return initNode;
    }

    public static String getEncodedString(String value) {

        String key = Utils.spawnRandomChar(1, false);

        char keychar = key.charAt(0);
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) (chars[i] ^ (keychar * (i + 1)));
        }

        return key + new String(chars);
    }

    public static <T> void unorderKeyValue(Map<T, Integer> map) {
        List<T> keys = new ArrayList<>(map.keySet());
        for (int i = keys.size() - 1; i > 0; i--) {
            int j = Utils.r.nextInt(i + 1); // 0 ≤ j ≤ i
            T str1 = keys.get(i);
            T str2 = keys.get(j);
            Integer val1 = map.get(str1);
            Integer val2 = map.get(str2);
            map.put(str1, val2);
            map.put(str2, val1);
        }
    }

}
