import java.io.*;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.mayikt.yushengjun.YuShengJun;
import org.objectweb.asm.*;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
import pers.XiaoShadiao.NMSLError;
import pers.XiaoShadiao.NMSLException;

class XSDURLClassLoader extends URLClassLoader {

    public XSDURLClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
        // TODO 自动生成的构造函数存根
    }

    @Override
    public void addURL(URL url) {
        // TODO 自动生成的方法存根
        super.addURL(url);
    }

    public Class<?> defineClass(byte[] b) {
        return super.defineClass(b, 0, b.length);
    }

}

class test114514 {
    public static void main(String[] args) {
        String ss = "";
        for (int i = 0; i < Math.pow(2,9); i++) {
            String s = Integer.toBinaryString(i);
            while(s.length() < 9) s = "0" + s;
            ss += s + "\r\n";
        }

        System.out.println(ss.replace("0", "瑶").replace("1", "摇"));
    }
}

public class Main {

    public static Map<String, XiaoShadiaoSerializableObfVisitor.FieldType> xiaoshadiaoFieldMap = new HashMap<>();
    public static String[] xiaoshadiaoFields;
    public static Map<String, Integer> xiaoshadiaoFieldMapHashCode = new HashMap<>();
    public static byte[] xsdSerializedClassBytes;
    public static int totalSerializedHash;
    public static String totalHashedSerializedFieldName;
    public static final RuntimeException notInclude = new NotInclude(), exclude = new Exclude();
    public static List<String> errors = new ArrayList<>();

    public static final String version = "1.4";

    public static final String[] xiaoshadiaoStrs;
    public static XSDURLClassLoader urlcl;

    public final static XSDURLClassLoader ASM5_0_3Verifyer;
    public static int switchCaseObfCount = 2;
    public static File dirctionary;
    public static File mapFolder;

    static {

        List<String> sl = new ArrayList<>();
        String[] sl2 = new String[] {"\uEF94", "\uF229", "\uF770", "ᨬ", "\uFD4B"};
        for (int i = 0; i < 3; i++) {
            StringBuilder ss = new StringBuilder("XiaoShadiao");
            for(int j = 0; j < 150; j++) {
                ss.append(Utils.getRandomMember(sl2));
            }
            sl.add(ss.toString());
        }
        xiaoshadiaoStrs = sl.toArray(new String[0]);

        try {
            // System.out.println(URLDecoder.decode(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8"));
            File parentFile = new File(URLDecoder.decode(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8")).getParentFile();
            dirctionary = parentFile;
            mapFolder = new File(parentFile, "maps");
        } catch (UnsupportedEncodingException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
        // new File(new File(System.getProperty("user.dir")), "maps");
        if(!mapFolder.exists()) mapFolder.mkdirs();
        // System.out.println(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath());

        File tempfile = new File(dirctionary, "oldasm503verifyer.jar");

        try {
            if(!tempfile.exists()) {
                FileOutputStream fos = new FileOutputStream(tempfile);
                InputStream is = Main.class.getResourceAsStream("oldasm503verifyer.jar");
                if(is == null) throw new NMSLException("Can't find oldasm503verifyer.jar in the jar, this is impossble");
                int i;
                while ((i = is.read()) != -1) {
                    fos.write(i);
                }
                fos.close();
                is.close();
            }
            ASM5_0_3Verifyer = new XSDURLClassLoader(new URL[] {tempfile.toURI().toURL()}, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static Random r = new Random();
    protected static List<String> excludeClass = new ArrayList<>();
    protected static List<String> includeClass = new ArrayList<>();

    public static boolean dontEncode = false;
    public static boolean useJunkCode = false;
    public static boolean useMoreJunkCode = false;
    public static boolean useMoreJunkCode2 = false;
    public static boolean useSuperJunkCode = false;
    public static boolean useInvokeDynamicObf = false;
    public static boolean useInvokeDynamicObfT = false;
    public static boolean useStringObf = false;
    public static boolean useStringObfT = false;
    public static boolean useNumberObf = false;
    public static boolean useNumberLCMPObf = false;
    public static boolean obfLocalVar = false;
    public static boolean delLocalVar = false;
    public static boolean fixVersion = false;
    public static boolean addSyntheticFlag = false;
    public static boolean dontverify = false;
    public static boolean YuShengJunObf = false;
    public static boolean classToFolder = false;
    public static boolean bigBrainNumberObf = false;
    public static boolean useJunkTryCatchBlock = false;
    public static boolean useSwitchCaseObf = false;
    public static boolean verifyWith508ASM = false;
    public static boolean useLabelResorter = false;
    public static boolean useAntiDebugger = false;
    public static boolean useSecurityManagerAntiDebugger = false;
    public static boolean resortFieldMethod = false;
    public static boolean useSOFCrasher = false;
    public static boolean exceptionSignRemover = false;

    public static int 原批诶批爱 = Opcodes.ASM5;

    public static File DIYRemapStringsFile;

    public static String[] remapStrings;


    public static Class<?>[] exceptions = new Class[] {
            NullPointerException.class,
            OutOfMemoryError.class,
            StackOverflowError.class,
            RuntimeException.class,
            NMSLException.class,
            VerifyError.class,
            IllegalAccessError.class,
            IndexOutOfBoundsException.class,
            ClassFormatError.class,
            ConcurrentModificationException.class,
            IllegalClassFormatException.class,
            NoSuchElementException.class,
            ArithmeticException.class,
            UnsupportedOperationException.class
    };
    public static void printUsage() {

        System.out.println();
        System.out.println();
        System.out.println("========= XiaoShadiao Obfuscator v" + version + " =========");
        System.out.println();
        System.out.println("用法:java -jar xsdobfor.jar <输入Jar> <输出Jar> [其他参数...]");
        System.out.println("其中<输入Jar> <输出Jar>必须填!");
        System.out.println("其他参数: ");
        System.out.println();
        System.out.println();
        System.out.println("      -?                    |  显示本帮助");
        System.out.println();
        System.out.println("      -inClass <classes> ...|  输入要混淆的类, -inClass后面所有参数都为要混淆的类, 直到-结束该参数, 使用正则表达式匹配, 例如: -inClass com.example.packageA(.*) com.example.packageB(.*)");
        System.out.println("      -exClass <classes> ...|  输入要在inClass中排除的类, 直到-结束该参数, 使用正则表达式匹配, 例如: -inClass com.example.packageA.dontobf(.*) com.example.packageB.dontobf(.*)");
        System.out.println();
        System.out.println("      -useInvokeDynamicObf  |* 使用InvokeDynamic字节码混淆");
        System.out.println("      -useInvokeDynamicObfT |* 在InvokeDynamic的基础上再混淆一次");
        System.out.println("      -useStringObf         |* 混淆所有字符串");
        System.out.println("      -useStringObfT        |* 在混淆字符串的基础上再混淆一次");
        System.out.println("      -useNumberObf         |* 混淆所有整数");
        System.out.println("      -useNumberLCMPObf     |* ");
        System.out.println("      -bigBrainNumberObf    |  艺术式混淆数字 (大型项目没必要用, 会很卡)");
        System.out.println("      -applymap <File>      |  请将map文件置入本混淆器所处根目录的map文件夹, 此时<File>为xxx.txtr");
        System.out.println("      -obfLocalVar          |  使用-applymap设置的map内容将方法中的临时变量混淆");
        System.out.println("      -delLocalVar          |* 将方法中的临时变量混淆");
        System.out.println("      -dontEncode           |  不要加密invokeDynamic的字符, 可缩小jar包体积 (都明文写出来了, 建议用-useInvokeDynamicObfT)");
        System.out.println("      -useJunkCode          |* 向代码中插入垃圾代码");
        System.out.println("      -useMoreJunkCode      |* 向代码中插入更多垃圾代码");
        System.out.println("      -useSuperJunkCode     |* 向代码中插入更多焯寄垃圾代码 (注意, 使用后只可在Java 1.8运行, 若在Java 1.9及以后的版本运行出现的bug自负)");
        System.out.println("      -addSyntheticFlag     |* 通过使字段和方法赋有\"合成\"标签来使反编译器不能直接显示源码");
        System.out.println("      -YuShengJunObf        |  使用余胜军混淆 (只是整活, 效果可能不咋地)");
        System.out.println("      -full                 |  将本说明中带*的参数全部启用");
        System.out.println("      -asmVer (4-10)        |  使用指定ASM版本混淆 (范围为4-10)");
        System.out.println("      -classToFolder        |  将class文件隐藏成文件夹使其无法被直接识别");
        System.out.println("      -useJunkTryCatchBlock |* 在字节码中插入冗余try catch块代码");
        System.out.println("      -useSwitchCaseObf     |* 在字节码中插入冗余switch case块代码");
        System.out.println("      -setSwitchCaseObfTransformCount | 设置使用switch case transformer的次数 (默认为2)");
        System.out.println("      -verifywith508asm     |  使用ASM 5.0.8版本验证混淆后的jar文件 (用于通过Minecraft Forge 1.8.9的旧版本ASM Transformer)");
        System.out.println("      -useLabelResorter     |  (不稳定) 将字节码的label顺序打乱 (将默认启用-delLocalVar)");
        System.out.println("      -useAntiDebugger      |  加入一点Java反调试代码");
        System.out.println("      -useSecurityManagerAntiDebugger | 使用SecurityManager反调试 (仅支持Java 17以下 (不支持17) 的版本)");
        System.out.println("      -resortFieldMethod    |  将所有字段和方法的定义顺序全部打乱");
        System.out.println("      -useSOFCrasher        |  加入一个尝试让反编译器发生SOF的代码 (使用此项请携带JVM参数 -Xss200M)");
        System.out.println("      -useExceptionSignRemover |* 移除方法中携带的Exception的签名");
        System.out.println();
        System.out.println();
        System.out.println("一颗栗子: ");
        System.out.println("java -jar xxx.jar \"D:\\a.jar\" \"D:\\b.jar\" -inClass (.*) -exClass org(.*) -useJunkCode");
        System.out.println();
        System.out.println("若你要混淆Minecraft Forge Mod, 小沙雕推荐下面的命令行:");
        System.out.println("java -jar xxx.jar \"D:\\a.jar\" \"D:\\b.jar\" -inClass (.*) -exClass org(.*) -delLocalVar -full -useNumberLCMPObf -useInvokeDynamicObfT -useStringObf -useStringObfT -useNumberObf -delLocalVar -useJunkCode -? -addSyntheticFlag -YuShengJunObf -useSuperJunkCode -useJunkTryCatchBlock -useSwitchCaseObf -setSwitchCaseObfTransformCount 1 -verifywith508asm");
        System.out.println();
        System.out.println("关于是否混淆成功:");
        System.out.println("如果你的jar混淆成功了, 那么混淆器运行到最后会输出\"诶嘿\"字样");
        System.out.println("并按照<输出Jar>参数输出jar文件. 否则为混淆失败");
        System.out.println("如果混淆失败了, 请尝试取消一些参数再试");
        System.out.println("当然, 如果还有疑问, 你可以加群来找小沙雕帮你混淆");
        System.out.println("https://xiaoshadiao.yqloss.net/qqg");
        System.out.println();
    }

    // -useStringObf -useStringObfT -useNumberObf -useNumberLCMPObf -delLocalVar -useJunkCode -useMoreJunkCode -useSuperJunkCode -addSyntheticFlag -useJunkTryCatchBlock -useSwitchCaseObf
    public static void main(String[] args) throws Exception {

        FutureTask<String> updateCheck = new FutureTask<>(() -> {
            URLConnection uc = new URL("https://xiaoshadiao.club/obfversion").openConnection();

            uc.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            String inputLine;
            StringBuilder sb = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                sb.append(inputLine);
            }
            in.close();
            return sb.toString();
        });
        new Thread(updateCheck).start();

//		ClassLoader classLoader = Main.class.getClassLoader();
//		if(!(classLoader instanceof XSDURLClassLoader2)) {
//			throw new RuntimeException(String.valueOf(classLoader));
//		}
        String lastArg = "";
        boolean exF = false,inF = false;
        if(args.length < 2) {
            printUsage();
            System.exit(-1);
        }

        int i = 0;
        f:for(String arg : args) {
            i++;
            //System.out.println(arg);
            label:{
                if(arg.isEmpty() || i <= 2 || arg.startsWith("#")) continue f;
                if(arg.equals("-exClass")) {
                    exF = true;
                    inF = false;
                    break label;
                }
                if(arg.equals("-inClass")) {
                    exF = false;
                    inF = true;
                    break label;
                }

                if(arg.startsWith("-")) exF = inF = false;

                if(arg.equals("-full")) {
                    useInvokeDynamicObf =
                            useInvokeDynamicObfT =
                                    useStringObf =
                                            useStringObfT =
                                                    useNumberObf =
                                                            useNumberLCMPObf =
                                                                    delLocalVar =
                                                                            useJunkCode =
                                                                                    useMoreJunkCode =
                                                                                            useSuperJunkCode =
                                                                                                    useSwitchCaseObf =
                                                                                                            addSyntheticFlag =
                                                                                                                    useJunkTryCatchBlock =
                                                                                                                            exceptionSignRemover =
                                                                                                                                    true;

                    break label;
                }
                if(arg.equals("-?")) {
                    printUsage();
                    break label;
                }
                if(arg.equals("-dontEncode")) {
                    dontEncode = true;
                    break label;
                }
                if(arg.equals("-dontVerify")) {
                    dontverify = true;
                    break label;
                }
                if(arg.equals("-useSwitchCaseObf")) {
                    useSwitchCaseObf = true;
                    break label;
                }
                if(arg.equals("-useJunkCode")) {
                    useJunkCode = true;
                    break label;
                }
                if(arg.equals("-useSuperJunkCode")) {
                    useSuperJunkCode = true;
                    break label;
                }
                if(arg.equals("-useMoreJunkCode")) {
                    useMoreJunkCode = true;
                    break label;
                }
                if(arg.equals("-useMoreJunkCode2")) {
                    useMoreJunkCode2 = true;
                    break label;
                }
                if(arg.equals("-useInvokeDynamicObf")) {
                    useInvokeDynamicObf = true;
                    break label;
                }
                if(arg.equals("-useInvokeDynamicObfT")) {
                    useInvokeDynamicObfT= true;
                    break label;
                }
                if(arg.equals("-useStringObf")) {
                    useStringObf = true;
                    break label;
                }
                if(arg.equals("-useStringObfT")) {
                    useStringObfT = true;
                    break label;
                }
                if(arg.equals("-useNumberObf")) {
                    useNumberObf = true;
                    break label;
                }
                if(arg.equals("-useNumberLCMPObf")) {
                    useNumberLCMPObf = true;
                    break label;
                }
                if(arg.equals("-obfLocalVar")) {
                    obfLocalVar = true;
                    break label;
                }
                if(arg.equals("-delLocalVar")) {
                    delLocalVar = true;
                    break label;
                }
                if(arg.equals("-fixVersion")) {
                    fixVersion = true;
                    break label;
                }
                if(arg.equals("-addSyntheticFlag")) {
                    addSyntheticFlag = true;
                    break label;
                }
                if(arg.equals("-classToFolder")) {
                    classToFolder = true;
                    break label;
                }
                if(arg.equals("-YuShengJunObf")) {
                    YuShengJunObf = true;
                    break label;
                }
                if(arg.equals("-bigBrainNumberObf")) {
                    bigBrainNumberObf = true;
                    break label;
                }
                if(arg.equals("-useJunkTryCatchBlock")) {
                    useJunkTryCatchBlock = true;
                    break label;
                }
                if(arg.equals("-verifywith508asm")) {
                    verifyWith508ASM = true;
                    break label;
                }
                if(arg.equals("-applymap")) {
                    break label;
                }
                if(arg.equals("-useLabelResorter")) {
                    useLabelResorter = true;
                    break label;
                }
                if(lastArg.equals("-applymap")) {
                    DIYRemapStringsFile = new File(mapFolder, arg);
                    if(!DIYRemapStringsFile.exists()) {
                        File f = new File(mapFolder, "测试");
                        f.createNewFile();
                        throw new NMSLException("找不到文件" + arg + ": " + mapFolder + ": " + DIYRemapStringsFile);
                    }
                    else {
                        remapStrings = MapReader.read(new String(Utils.取is字节集列表(new FileInputStream(DIYRemapStringsFile)), "UTF-8"));
                    }
                    break label;
                }
                if(arg.equals("-asmVer")) {
                    break label;
                }
                if(lastArg.equals("-asmVer")) {

                    switch(Integer.parseInt(arg)) {
                        case 4:
                            原批诶批爱 = Opcodes.ASM4;
                            break;
                        case 5:
                            原批诶批爱 = Opcodes.ASM5;
                            break;
                        case 6:
                            原批诶批爱 = Opcodes.ASM6;
                            break;
                        case 7:
                            原批诶批爱 = Opcodes.ASM7;
                            break;
                        case 8:
                            原批诶批爱 = Opcodes.ASM8;
                            break;
                        case 9:
                            原批诶批爱 = Opcodes.ASM9;
                            break;
                        case 10:
                            原批诶批爱 = Opcodes.ASM10_EXPERIMENTAL;
                        default:
                            throw new NMSLException("-asmVer后面的参数应该为x∈{x|4≤x≤10, x∈N} (x应为4-10的整数), 你输入了: " + arg);
                    }
                    break label;
                }
                if(arg.equals("-setSwitchCaseObfTransformCount")) {
                    break label;
                }
                if(arg.equals("-useAntiDebugger")) {
                    useAntiDebugger = true;
                    break label;
                }
                if(arg.equals("-useSecurityManagerAntiDebugger")) {
                    useSecurityManagerAntiDebugger = true;
                    break label;
                }
                if(arg.equals("-resortFieldMethod")) {
                    resortFieldMethod = true;
                    break label;
                }
                if(lastArg.equals("-setSwitchCaseObfTransformCount")) {
                    switchCaseObfCount = Integer.parseInt(arg);
                    break label;
                }
                if(arg.equals("-useSOFCrasher")) {
                    useSOFCrasher = true;
                    break label;
                }
                if(arg.equals("-useExceptionSignRemover")) {
                    exceptionSignRemover = true;
                    break label;
                }
                if(exF) {
                    excludeClass.add(arg);
                    //System.out.println("ex: " + arg);
                    break label;
                }
                if(inF) {
                    includeClass.add(arg);
                    //	System.out.println("in: " + arg);
                    break label;
                }
                throw new NMSLException("小沙雕不认识 " + arg);
            }
            lastArg = arg;
        }

        System.out.println("Include Class: " + includeClass);
        System.out.println("Exclude Class: " + excludeClass);

        includeClass.add("XiaoShadiao");
        if(DIYRemapStringsFile == null && obfLocalVar) throw new NMSLException("-obfLocalVar找不到你设置的Mapping, 使用参数-applymap");

        read(args[0]);

        try {
            startObf(args[0], args[1]);

            System.out.println();
            System.out.println("诶嘿");
            System.out.println();
        } catch (StackOverflowError e) {
            if(Main.useSOFCrasher) throw new NMSLException("请携带JVM参数 -Xss200M 再使用SOFCrasher!");
            throw e;
        } finally {
            try {
                String s = updateCheck.get();
                if(!version.equals(s)) {
                    System.out.println("发现新版本: " + s);
                    System.out.println("下载: https://xiaoshadiao.club/obfdownload");
                }
            } catch (ExecutionException e) {
                System.out.println("检查更新失败: " + e.getCause());
            }
        }

        System.exit(0);
    }

    public static void print(int a) {
        System.out.println(a);
    }

    @SuppressWarnings("deprecation")
    private static void read(String string) {
        // TODO 自动生成的方法存根
        File file = new File(string);
        if(!file.exists()) throw new RuntimeException(new FileNotFoundException(file.getAbsolutePath()));
        try {
            //System.out.println(file.toURL());
            urlcl = new XSDURLClassLoader(new URL[] {file.toURL()}, Main.class.getClassLoader());
            //			zipfile = new ZipInputStream(new FileInputStream(file));
            //
            //			ZipEntry a;
            //
            //			while((a = zipfile.getNextEntry()) != null) {
            //				if(a.getName().endsWith(".class")) {
            //
            //					ByteArrayOutputStream os = new ByteArrayOutputStream();
            //					int b;
            //					while((b = zipfile.read()) != -1) {
            //						os.write(b);
            //					}
            //					if(!a.getName().contains("org/spongepowered")) urlcl.loadClass(a.getName().substring(0,a.getName().length() - 6).replace("/", "."));
            //				}
            //
            //			}

        } catch(Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private static void startObf(String in, String out) throws Exception {
        ZipInputStream zipfile = new ZipInputStream(new FileInputStream(in));

        FileOutputStream fileout = new FileOutputStream(new File(out));
        ZipOutputStream zzzz = new ZipOutputStream(fileout);

        List<ClassWriter1> writerList = new ArrayList<>();
        List<ClassReader> readerList = new ArrayList<>();

        String current = null;

        ZipEntry a;

        ClassReader cr;
        ClassWriter1 cw;

        boolean temp1 = Main.useStringObf,temp2 = Main.useStringObfT,temp3 = Main.useNumberObf,temp4 = Main.useJunkCode,temp5 = Main.addSyntheticFlag,temp6 = Main.delLocalVar, temp8 = Main.useMoreJunkCode;

        Main.useStringObf = Main.useStringObfT = Main.useNumberObf = Main.useJunkCode = Main.addSyntheticFlag = Main.delLocalVar = Main.useMoreJunkCode = true;


//		List<InputStream> isl = new ArrayList<>();
//		isl.add(XiaoShadiao.class.getResourceAsStream("XiaoShadiao.class"));
//		if (Main.YuShengJunObf) {
//			isl.add(com.mayikt.yushengjun.Object.class.getResourceAsStream("Object.class"));
//			isl.add(YuShengJun.class.getResourceAsStream("YuShengJun.class"));
//		}

        List<Entry<Class<?>, String>> stringl = new ArrayList<>();
        stringl.add(new AbstractMap.SimpleEntry<>(XiaoShadiao.class, "XiaoShadiao.class"));
        /*if(Main.useSecurityManagerAntiDebugger) */stringl.add(new AbstractMap.SimpleEntry<>(XiaoShadiao.class, "ShaXiaodiao.class"));
        if(Main.YuShengJunObf) {
            stringl.add(new AbstractMap.SimpleEntry<>(com.mayikt.yushengjun.Object.class, "Object.class"));
            stringl.add(new AbstractMap.SimpleEntry<>(YuShengJun.class, "YuShengJun.class"));
        }
        stringl.add(new AbstractMap.SimpleEntry<>(DiaoXiaosha.class, "DiaoXiaosha.class"));

        int flag = 2;//Main.useSecurityManagerAntiDebugger ? 2 : 1;
        for(Entry<Class<?>, String> string : stringl) {
            if(flag > 0) {
                flag--;
                cr = new ClassReader(Objects.requireNonNull(string.getKey().getResourceAsStream(string.getValue())));
                System.out.println(cr.getClassName());
                cw = new ClassWriter1(cr, ClassWriter1.COMPUTE_MAXS | ClassWriter1.COMPUTE_FRAMES);
                cr.accept(new LocalVarObfVisitor(cr,cw,原批诶批爱), 0);
                byte[] switchcase = cw.toByteArray();
                switchcase = XiaoShadiaoSerializableObfVisitor.transform(switchcase);
                for (int i = 0; i < switchCaseObfCount; i++) {
                    switchcase = SwitchCaseObfVisitor8.transform(switchcase, i);
                }
                switchcase = FieldMethodResorterVisitorObf.transform(switchcase);
                if(Main.useSOFCrasher) switchcase = SOFCrasherObfVistor.transform(switchcase);
                cr = new ClassReader(switchcase);
                cw = new ClassWriter1(cr, ClassWriter1.COMPUTE_MAXS | ClassWriter1.COMPUTE_FRAMES);
                cr.accept(new StringObfVisitorT(cr,cw,原批诶批爱), 0);
                cr = new ClassReader(cw.toByteArray());
                cw = new ClassWriter1(cr, ClassWriter1.COMPUTE_MAXS | ClassWriter1.COMPUTE_FRAMES);
//				cr.accept(new SuperJunkCodeObfVisitor(cr,cw,原批诶批爱), 0);
//				cr = new ClassReader(cw.toByteArray());
//				cw = new ClassWriter1(cr, ClassWriter1.COMPUTE_MAXS | ClassWriter1.COMPUTE_FRAMES);
                cr.accept(new StringNumberObfVisitor(cr,cw,原批诶批爱, true), 0);
//				cr = new ClassReader(cw.toByteArray());
//				cw = new ClassWriter1(cr, ClassWriter1.COMPUTE_MAXS | ClassWriter1.COMPUTE_FRAMES);
//				cr.accept(new JunkTryCatchBlockObfVisitor(cr,cw,原批诶批爱), 0);
                cr = new ClassReader(JunkTryCatchBlock2ObfVisitor.transfer(cw.toByteArray()));
                cw = new ClassWriter1(cr, ClassWriter1.COMPUTE_MAXS | ClassWriter1.COMPUTE_FRAMES);
                cr.accept(new SyntheticBridgeApplyerObfVisitor(cr,cw,原批诶批爱), 0);
                cr = new ClassReader(cw.toByteArray());
                cw = new ClassWriter1(cr, ClassWriter1.COMPUTE_MAXS | ClassWriter1.COMPUTE_FRAMES);
                cr.accept(new XiaoShadiaoStrReplacerObfVisitor(cr,cw,原批诶批爱), 0);
            } else if(string.getKey() == DiaoXiaosha.class) {
                cr = new ClassReader(Objects.requireNonNull(string.getKey().getResourceAsStream(string.getValue())));
                // System.out.println(cr.getClassName());
                ClassNode cn = new ClassNode(原批诶批爱);
                cr.accept(cn, 0);
                MethodNode mn = new MethodNode();
                mn.name = "C";
                mn.desc = "()LDiaoXiaosha;";
                mn.access = Opcodes.ACC_PUBLIC | Opcodes.ACC_ABSTRACT;
                mn.annotationDefault = null;
                cn.methods.add(mn);

                cw = new ClassWriter1(cr, ClassWriter1.COMPUTE_MAXS | ClassWriter1.COMPUTE_FRAMES);
                cn.accept(cw);
            } else {
                cr = new ClassReader(Objects.requireNonNull(string.getKey().getResourceAsStream(string.getValue())));
                System.out.println(cr.getClassName());
                cw = new ClassWriter1(cr, ClassWriter1.COMPUTE_MAXS | ClassWriter1.COMPUTE_FRAMES);
                cr.accept(cw, 0);
            }

            writerList.add(cw);
            readerList.add(cr);
        }
        Main.useStringObf = temp1;
        Main.useStringObfT = temp2;
        Main.useNumberObf = temp3;
        Main.useJunkCode = temp4;
        Main.addSyntheticFlag = temp5;
        Main.delLocalVar = temp6;
        Main.useMoreJunkCode = temp8;

        cr = new ClassReader(NMSLException.class.getResourceAsStream("NMSLException.class"));
        cw = new ClassWriter1(cr, ClassWriter1.COMPUTE_MAXS | ClassWriter1.COMPUTE_FRAMES);
        cr.accept(cw, 0);
        writerList.add(cw);
        readerList.add(cr);

        while((a = zipfile.getNextEntry()) != null) {
            try {

                ByteArrayOutputStream os = new ByteArrayOutputStream();
                int b;
                while((b = zipfile.read()) != -1) {
                    os.write(b);
                }

                ClassReader oldcr = null, startcr = null;
                ClassWriter1 oldcw = null, startcw = null;
                if(a.getName().endsWith(".class")) {
                    boolean isIn = false, isEx = false;
                    for(String inn : Main.includeClass) {
                        if(cr.getClassName().matches(inn) || cr.getClassName().startsWith("XiaoShadiao")) {
                            isIn = true;
                            // System.out.println("Include: " + cr.getClassName());
                            break;
                        }
                    }
                    for(String exx : Main.excludeClass) {
                        if(cr.getClassName().matches(exx)) {
                            isEx = true;
                            // System.out.println("Ignored: " + cr.getClassName());
                            break;
                        }
                    }

                    System.out.println(cr.getClassName());

                    try {
                        startcr = cr = new ClassReader(os.toByteArray());
                        startcw = cw = new ClassWriter1(cr, ClassWriter1.COMPUTE_MAXS | ClassWriter1.COMPUTE_FRAMES);
                        oldcr = cr;
                        oldcw = cw;
                        cr.accept(new LocalVarObfVisitor(cr,cw,原批诶批爱), 0);
                        verify(new MyCL(), cw.toByteArray());
                        // if(isEx) throw exclude;
                        // if(!isIn) throw notInclude;
                    } catch(NotInclude | Exclude e) {
                        cr = oldcr;
                        cw = oldcw;
                        System.out.println(" - Skip: " + e.getClass().getSimpleName());
                    } catch(Exception e) {
                        e.printStackTrace();
                        errors.add("[!] 进行LocalVarObfVisitor时发生错误: " + cr.getClassName() + " : " + e);
                        if (e instanceof ClassTooLargeException || e instanceof MethodTooLargeException) {
                            cr = startcr;
                            cw = startcw;
                        } else {
                            cr = oldcr;
                            cw = oldcw;
                        }
                    }

                    if (Main.useLabelResorter) {
                        try {
                            oldcr = cr;
                            oldcw = cw;
                            byte[] switchcase = cw.toByteArray();
                            switchcase = LabelResorterObfVisitor.transform(switchcase);
                            cr = new ClassReader(switchcase);
                            cw = new ClassWriter1(cr, ClassWriter1.COMPUTE_MAXS | ClassWriter1.COMPUTE_FRAMES);
                            cr.accept(new ObfVisitor(cr, cw, 原批诶批爱) {}, 0);
                            verify(new MyCL(), cw.toByteArray());
                        } catch(NotInclude | Exclude e) {
                            cr = oldcr;
                            cw = oldcw;
                            System.out.println(" - Skip: " + e.getClass().getSimpleName());
                        } catch(Exception e) {
                            e.printStackTrace();
                            errors.add("[!] 进行LabelResorterObfVisitor时发生错误: " + cr.getClassName() + " : " + e);
                            if (e instanceof ClassTooLargeException || e instanceof MethodTooLargeException) {
                                cr = startcr;
                                cw = startcw;
                            } else {
                                cr = oldcr;
                                cw = oldcw;
                            }
                        }
                    }

                    if (Main.exceptionSignRemover) {
                        try {
                            oldcr = cr;
                            oldcw = cw;
                            byte[] switchcase = cw.toByteArray();
                            switchcase = MethodThrowableSignRemoverObfVisitor.transform(switchcase);
                            cr = new ClassReader(switchcase);
                            cw = new ClassWriter1(cr, ClassWriter1.COMPUTE_MAXS | ClassWriter1.COMPUTE_FRAMES);
                            cr.accept(new ObfVisitor(cr, cw, 原批诶批爱) {}, 0);
                            verify(new MyCL(), cw.toByteArray());
                        } catch(NotInclude | Exclude e) {
                            cr = oldcr;
                            cw = oldcw;
                            System.out.println(" - Skip: " + e.getClass().getSimpleName());
                        } catch(Exception e) {
                            e.printStackTrace();
                            errors.add("[!] 进行MethodThrowableSignRemoverObfVisitor时发生错误: " + cr.getClassName() + " : " + e);
                            if (e instanceof ClassTooLargeException || e instanceof MethodTooLargeException) {
                                cr = startcr;
                                cw = startcw;
                            } else {
                                cr = oldcr;
                                cw = oldcw;
                            }
                        }
                    }

                    if (Main.resortFieldMethod) {
                        try {
                            oldcr = cr;
                            oldcw = cw;
                            byte[] switchcase = cw.toByteArray();
                            switchcase = FieldMethodResorterVisitorObf.transform(switchcase);
                            cr = new ClassReader(switchcase);
                            cw = new ClassWriter1(cr, ClassWriter1.COMPUTE_MAXS | ClassWriter1.COMPUTE_FRAMES);
                            cr.accept(new ObfVisitor(cr, cw, 原批诶批爱) {}, 0);
                            verify(new MyCL(), cw.toByteArray());
                        } catch(NotInclude | Exclude e) {
                            cr = oldcr;
                            cw = oldcw;
                            System.out.println(" - Skip: " + e.getClass().getSimpleName());
                        } catch(Exception e) {
                            e.printStackTrace();
                            errors.add("[!] 进行LabelResorterObfVisitor时发生错误: " + cr.getClassName() + " : " + e);
                            if (e instanceof ClassTooLargeException || e instanceof MethodTooLargeException) {
                                cr = startcr;
                                cw = startcw;
                            } else {
                                cr = oldcr;
                                cw = oldcw;
                            }
                        }
                    }

                    if (Main.useSOFCrasher) {
                        try {
                            oldcr = cr;
                            oldcw = cw;
                            byte[] switchcase = cw.toByteArray();
                            switchcase = SOFCrasherObfVistor.transform(switchcase);
                            cr = new ClassReader(switchcase);
                            cw = new ClassWriter1(cr, ClassWriter1.COMPUTE_MAXS | ClassWriter1.COMPUTE_FRAMES);
                            cr.accept(new ObfVisitor(cr, cw, 原批诶批爱) {}, 0);
                            verify(new MyCL(), cw.toByteArray());
                        } catch(NotInclude | Exclude e) {
                            cr = oldcr;
                            cw = oldcw;
                            System.out.println(" - Skip: " + e.getClass().getSimpleName());
                        } catch(Exception e) {
                            e.printStackTrace();
                            errors.add("[!] 进行LabelResorterObfVisitor时发生错误: " + cr.getClassName() + " : " + e);
                            if (e instanceof ClassTooLargeException || e instanceof MethodTooLargeException) {
                                cr = startcr;
                                cw = startcw;
                            } else {
                                cr = oldcr;
                                cw = oldcw;
                            }
                        }
                    }

                    if(Main.useSwitchCaseObf) {
                        int acc = cr.getAccess();
                        if((acc&(Opcodes.ACC_INTERFACE|Opcodes.ACC_ENUM))==0) {
                            try {
                                oldcr = cr;
                                oldcw = cw;
                                byte[] switchcase = cw.toByteArray();
                                for (int i = 0; i < switchCaseObfCount; i++) {
                                    switchcase = SwitchCaseObfVisitor8.transform(switchcase, i);
                                }
                                // switchcase = SwitchCaseObfVisitor5.transform(switchcase);
                                cr = new ClassReader(switchcase);
                                cw = new ClassWriter1(cr, ClassWriter1.COMPUTE_MAXS | ClassWriter1.COMPUTE_FRAMES);
                                cr.accept(new ObfVisitor(cr, cw, 原批诶批爱) {}, 0);
                                verify(new MyCL(), cw.toByteArray());
                                // if(isEx) throw exclude;
                                // if(!isIn) throw notInclude;
                            } catch(NotInclude | Exclude e) {
                                cr = oldcr;
                                cw = oldcw;
                                System.out.println(" - Skip: " + e.getClass().getSimpleName());
                            } catch(Throwable e) {
                                e.printStackTrace();
                                errors.add("[!] 进行SwitchCaseObfVisitor时发生错误: " + cr.getClassName() + " : " + e);
                                if (e instanceof ClassTooLargeException || e instanceof MethodTooLargeException) {
                                    cr = startcr;
                                    cw = startcw;
                                } else {
                                    cr = oldcr;
                                    cw = oldcw;
                                }
                            }
                        } else {

                        }
                    }
                    if(Main.useJunkTryCatchBlock) {
                        int acc = cr.getAccess();
                        if((acc&(Opcodes.ACC_INTERFACE|Opcodes.ACC_ENUM))==0) {
                            try {
                                oldcr = cr;
                                oldcw = cw;
//								cr = new ClassReader(cw.toByteArray());
//								cw = new ClassWriter1(cr, ClassWriter1.COMPUTE_MAXS | ClassWriter1.COMPUTE_FRAMES);
//								cr.accept(new JunkTryCatchBlockObfVisitor(cr,cw,原批诶批爱), 0);
                                cr = new ClassReader(JunkTryCatchBlock2ObfVisitor.transfer(cw.toByteArray()));
                                cw = new ClassWriter1(cr, ClassWriter1.COMPUTE_MAXS | ClassWriter1.COMPUTE_FRAMES);
                                cr.accept(cw, 0);
                                verify(new MyCL(), cw.toByteArray());
                                // if(isEx) throw exclude;
                                // if(!isIn) throw notInclude;
                            } catch(NotInclude | Exclude e) {
                                cr = oldcr;
                                cw = oldcw;
                                System.out.println(" - Skip: " + e.getClass().getSimpleName());
                            } catch(Exception | Error e) {
                                e.printStackTrace();
                                errors.add("[!] 进行JunkTryCatchBlockObfVisitor时发生错误: " + cr.getClassName() + " : " + e);
                                if (e instanceof ClassTooLargeException || e instanceof MethodTooLargeException) {
                                    cr = startcr;
                                    cw = startcw;
                                } else {
                                    cr = oldcr;
                                    cw = oldcw;
                                }
                            }
                        }
                    }
                    try {
                        oldcr = cr;
                        oldcw = cw;
                        cr = new ClassReader(cw.toByteArray());
                        cw = new ClassWriter1(cr, ClassWriter1.COMPUTE_MAXS | ClassWriter1.COMPUTE_FRAMES);
                        cr.accept(new SyntheticBridgeApplyerObfVisitor(cr,cw,原批诶批爱), 0);
                        verify(new MyCL(), cw.toByteArray());
                        // if(isEx) throw exclude;
                        // if(!isIn) throw notInclude;
                    } catch(NotInclude | Exclude e) {
                        cr = oldcr;
                        cw = oldcw;
                        System.out.println(" - Skip: " + e.getClass().getSimpleName());
                    } catch(Throwable e) {
                        e.printStackTrace();
                        errors.add("[!] 进行SyntheticBridgeApplyerObfVisitor时发生错误: " + cr.getClassName() + " : " + e);
                        if (e instanceof ClassTooLargeException || e instanceof MethodTooLargeException) {
                            cr = startcr;
                            cw = startcw;
                        } else {
                            cr = oldcr;
                            cw = oldcw;
                        }
                    }
                    if(Main.useMoreJunkCode2) {
                        try {
                            oldcr = cr;
                            oldcw = cw;
                            cr = new ClassReader(cw.toByteArray());
                            cw = new ClassWriter1(cr, ClassWriter1.COMPUTE_MAXS | ClassWriter1.COMPUTE_FRAMES);
                            cr.accept(new More2JunkCodeObfVisitor(cr,cw,原批诶批爱), 0);
                            verify(new MyCL(), cw.toByteArray());
                            // if(isEx) throw exclude;
                            // if(!isIn) throw notInclude;
                        } catch(NotInclude | Exclude e) {
                            cr = oldcr;
                            cw = oldcw;
                            System.out.println(" - Skip: " + e.getClass().getSimpleName());
                        } catch(Throwable e) {
                            e.printStackTrace();
                            errors.add("[!] 进行More2JunkCodeObfVisitor时发生错误: " + cr.getClassName() + " : " + e);
                            if (e instanceof ClassTooLargeException || e instanceof MethodTooLargeException) {
                                cr = startcr;
                                cw = startcw;
                            } else {
                                cr = oldcr;
                                cw = oldcw;
                            }
                        }
                    }
                    if (Main.useStringObfT && Main.useStringObf) {
                        try {
                            oldcr = cr;
                            oldcw = cw;
                            cr = new ClassReader(cw.toByteArray());
                            cw = new ClassWriter1(cr, ClassWriter1.COMPUTE_MAXS | ClassWriter1.COMPUTE_FRAMES);
                            cr.accept(new StringObfVisitorT(cr,cw,原批诶批爱), 0);
                            verify(new MyCL(), cw.toByteArray());
                            // if(isEx) throw exclude;
                            // if(!isIn) throw notInclude;
                        } catch(NotInclude | Exclude e) {
                            cr = oldcr;
                            cw = oldcw;
                            System.out.println(" - Skip: " + e.getClass().getSimpleName());
                        } catch(Throwable e) {
                            e.printStackTrace();
                            errors.add("[!] 进行StringObfVisitorT时发生错误: " + cr.getClassName() + " : " + e);
                            if (e instanceof ClassTooLargeException || e instanceof MethodTooLargeException) {
                                cr = startcr;
                                cw = startcw;
                            } else {
                                cr = oldcr;
                                cw = oldcw;
                            }
                        }
                    }
                    if (Main.useSuperJunkCode) {
                        try {
                            oldcr = cr;
                            oldcw = cw;
                            cr = new ClassReader(cw.toByteArray());
                            cw = new ClassWriter1(cr, ClassWriter1.COMPUTE_MAXS | ClassWriter1.COMPUTE_FRAMES);
                            cr.accept(new SuperJunkCodeObfVisitor(cr,cw,原批诶批爱), 0);
                            verify(new MyCL(), cw.toByteArray());
                            // if(isEx) throw exclude;
                            // if(!isIn) throw notInclude;
                        } catch(NotInclude | Exclude e) {
                            cr = oldcr;
                            cw = oldcw;
                            System.out.println(" - Skip: " + e.getClass().getSimpleName());
                        } catch(Throwable e) {
                            e.printStackTrace();
                            errors.add("[!] 进行SuperJunkCodeObfVisitor时发生错误: " + cr.getClassName() + " : " + e);
                            if (e instanceof ClassTooLargeException || e instanceof MethodTooLargeException) {
                                cr = startcr;
                                cw = startcw;
                            } else {
                                cr = oldcr;
                                cw = oldcw;
                            }
                        }
                    }
                    if(Main.useStringObf || Main.useNumberObf || Main.bigBrainNumberObf) try {
                        oldcr = cr;
                        oldcw = cw;
                        cr = new ClassReader(cw.toByteArray());
                        cw = new ClassWriter1(cr, ClassWriter1.COMPUTE_MAXS | ClassWriter1.COMPUTE_FRAMES);
                        cr.accept(new StringNumberObfVisitor(cr,cw,原批诶批爱, true), 0);
                        verify(new MyCL(), cw.toByteArray());
                        // if(isEx) throw exclude;
                        // if(!isIn) throw notInclude;
                    } catch(NotInclude | Exclude e) {
                        cr = oldcr;
                        cw = oldcw;
                        System.out.println(" - Skip: " + e.getClass().getSimpleName());
                    } catch(Throwable e1) {
                        if (e1 instanceof ClassTooLargeException || e1 instanceof MethodTooLargeException) {
                            cr = startcr;
                            cw = startcw;
                        } else {
                            cr = oldcr;
                            cw = oldcw;
                        }
                        try {
                            oldcr = cr;
                            oldcw = cw;
                            cr = new ClassReader(cw.toByteArray());
                            cw = new ClassWriter1(cr, ClassWriter1.COMPUTE_MAXS | ClassWriter1.COMPUTE_FRAMES);
                            cr.accept(new StringNumberObfVisitor(cr,cw,原批诶批爱, false), 0);
                            verify(new MyCL(), cw.toByteArray());
                            // if(isEx) throw exclude;
                            // if(!isIn) throw notInclude;
                        } catch(NotInclude | Exclude e) {
                            cr = oldcr;
                            cw = oldcw;
                            System.out.println(" - Skip: " + e.getClass().getSimpleName());
                        } catch(Throwable e) {
                            e.printStackTrace();
                            errors.add("[!] 进行StringNumberObfVisitor时发生错误: " + cr.getClassName() + " : " + e);
                            if (e instanceof ClassTooLargeException || e instanceof MethodTooLargeException) {
                                cr = startcr;
                                cw = startcw;
                            } else {
                                cr = oldcr;
                                cw = oldcw;
                            }
                        }
                    }
                    if(Main.useInvokeDynamicObf) {
                        int acc = cr.getAccess();
                        if((acc|Opcodes.ACC_INTERFACE)!=acc && Main.useInvokeDynamicObfT) {
                            try {
                                oldcr = cr;
                                oldcw = cw;
                                cr = new ClassReader(cw.toByteArray());
                                cw = new ClassWriter1(cr, ClassWriter1.COMPUTE_MAXS | ClassWriter1.COMPUTE_FRAMES);
                                cr.accept(new InvokeDynamicObfVisitorT(cr,cw,原批诶批爱), 0);
                                verify(new MyCL(), cw.toByteArray());
                                // if(isEx) throw exclude;
                                // if(!isIn) throw notInclude;
                            } catch(NotInclude | Exclude e) {
                                cr = oldcr;
                                cw = oldcw;
                                System.out.println(" - Skip: " + e.getClass().getSimpleName());
                            } catch(Throwable e) {
                                e.printStackTrace();
                                errors.add("[!] 进行InvokeDynamicObfVisitorT时发生错误: " + cr.getClassName() + " : " + e);
                                if (e instanceof ClassTooLargeException || e instanceof MethodTooLargeException) {
                                    cr = startcr;
                                    cw = startcw;
                                } else {
                                    cr = oldcr;
                                    cw = oldcw;
                                }
                            }
                        } else {
                            try {
                                oldcr = cr;
                                oldcw = cw;
                                cr = new ClassReader(cw.toByteArray());
                                cw = new ClassWriter1(cr, ClassWriter1.COMPUTE_MAXS | ClassWriter1.COMPUTE_FRAMES);
                                cr.accept(new InvokeDynamicObfVisitor(cr,cw,原批诶批爱), 0);
                                verify(new MyCL(), cw.toByteArray());
                                // if(isEx) throw exclude;
                                // if(!isIn) throw notInclude;
                            } catch(NotInclude | Exclude e) {
                                cr = oldcr;
                                cw = oldcw;
                                System.out.println(" - Skip: " + e.getClass().getSimpleName());
                            } catch(Throwable e) {
                                e.printStackTrace();
                                errors.add("[!] 进行InvokeDynamicObfVisitor时发生错误: " + cr.getClassName() + " : " + e);
                                if (e instanceof ClassTooLargeException || e instanceof MethodTooLargeException) {
                                    cr = startcr;
                                    cw = startcw;
                                } else {
                                    cr = oldcr;
                                    cw = oldcw;
                                }
                            }
                        }
                    }
                    if(Main.YuShengJunObf) {
                        int acc = cr.getAccess();
                        if((acc&(Opcodes.ACC_INTERFACE|Opcodes.ACC_ENUM))==0) {
                            try {
                                oldcr = cr;
                                oldcw = cw;
                                cr = new ClassReader(cw.toByteArray());
                                cw = new ClassWriter1(cr, ClassWriter1.COMPUTE_MAXS | ClassWriter1.COMPUTE_FRAMES);
                                cr.accept(new YuShengJunObfVisitor(cr,cw,原批诶批爱), 0);
                                verify(new MyCL(), cw.toByteArray());
                                // if(isEx) throw exclude;
                                // if(!isIn) throw notInclude;
                            } catch(NotInclude | Exclude e) {
                                cr = oldcr;
                                cw = oldcw;
                                System.out.println(" - Skip: " + e.getClass().getSimpleName());
                            } catch(Throwable e) {
                                e.printStackTrace();
                                errors.add("[!] 进行YuShengJunObfVisitor时发生错误: " + cr.getClassName() + " : " + e);
                                if (e instanceof ClassTooLargeException || e instanceof MethodTooLargeException) {
                                    cr = startcr;
                                    cw = startcw;
                                } else {
                                    cr = oldcr;
                                    cw = oldcw;
                                }
                            }
                        } else {

                        }
                    }

                    // System.out.println(cr.getClassName());
                    try {
                        verify(new MyCL(), cw.toByteArray(), true);
                    } catch(Throwable e) {
                        e.printStackTrace();
                        errors.add("[!] 无法混淆" + cr.getClassName() + " : " + e);
                        cr = startcr;
                        cw = startcw;
                    }


                    // System.out.println(cr.getClassName());

                    writerList.add(cw);
                    readerList.add(cr);

                } else {
                    try {
                        zzzz.putNextEntry(new ZipEntry(a.getName()));
                        zzzz.write(os.toByteArray());
                    } catch(ZipException e) {
                        e.printStackTrace();
                    }

                }

            } catch(Exception e) {
                try {
                    zipfile.close();
                    zzzz.close();
                    fileout.close();
                } catch (IOException e1) {
                    // TODO 自动生成的 catch 块
                    e1.printStackTrace();
                }
                throw new RuntimeException(current, e);
            }
        }
        // System.out.println(writerList.size());

        try {

            if(useAntiDebugger) {
                zzzz.putNextEntry(new ZipEntry("XiaoShadiaoStuff"));
                zzzz.write(xsdSerializedClassBytes);
            }

            for (ClassWriter1 classWriter1 : writerList) {

                byte[] bytes = classWriter1.toByteArray();
                cr = new ClassReader(bytes);

                System.out.println("写出: " + cr.getClassName());
                verify(new MyCL(), bytes, true);

                try {
                    zzzz.putNextEntry(new ZipEntry(cr.getClassName() + ".class" + (Main.classToFolder ? "/" : "")));
                    zzzz.write(bytes);
                } catch (ZipException e1) {
                    // TODO 自动生成的 catch 块
                    e1.printStackTrace();
                }

            }
        } finally {
            try {
                zipfile.close();
                zzzz.close();
                fileout.close();
            } catch (IOException e1) {
                // TODO 自动生成的 catch 块
                e1.printStackTrace();
            }
        }

        for(String errorss : errors) {
            System.out.println(errorss);
        }

        System.out.println("操作类数: " + writerList.size() + ", 错误数: " + errors.size());

    }

    public static void addTempLib() {

    }

    private static Method verifyerMethod;

    public static void verify(MyCL mycl, byte[] bytes) {
        verify(mycl, bytes, false);
    }
    public static void verify(MyCL mycl, byte[] bytes, boolean use503ASMVerify) {

        if(verifyerMethod == null) {
            try {
                verifyerMethod = Class.forName("Verifyer", false, ASM5_0_3Verifyer).getMethod("verify", byte[].class);
            } catch (ClassNotFoundException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        try {
            if(use503ASMVerify && verifyWith508ASM) verifyerMethod.invoke(null, (Object) bytes);
        } catch (IllegalAccessException | InvocationTargetException e) {
            a:{
                Throwable t = e;
                while ((t = t.getCause()) != null) {
                    if(t.toString().contains("java.lang.ClassNotFoundException")) break a;
                }

                throw new RuntimeException("This class is not passed the ASM 5.0.3 test", e);
            }
        }
        try {
            Class<?> cla$$ = mycl.define(bytes);
            Method[] ms = cla$$.getDeclaredMethods();
            Object obj = null;
            try {
                obj = cla$$.newInstance();
            } catch(Throwable e) {
                if(e instanceof ClassFormatError || e instanceof VerifyError) {
                    if(e.getLocalizedMessage().startsWith("Class file version does not support constant tag")) throw new NMSLError("尝试加-fixVersion参数看看能否修复此问题", e);
                    throw (Error)e;
                }
                if(e instanceof NMSLException || e instanceof IndexOutOfBoundsException) {
                    throw new NMSLException("混淆出的代码可能有些问题", e);
                }
            }
            for(Method m : ms) {
                m.setAccessible(true);
//				try {
//					m.invoke(obj, new Object[m.getParameterCount()]);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
            }
        } catch(Throwable e) {
            if(dontverify) {
                if(!(e instanceof NoClassDefFoundError)) e.printStackTrace();
                return;
            }
            if((e instanceof BootstrapMethodError)) e.printStackTrace();
            if(e instanceof ClassFormatError || e instanceof VerifyError) {
                if(e.getLocalizedMessage().startsWith("Class file version does not support constant tag")) throw new NMSLError("尝试加-fixVersion参数看看能否修复此问题", e);
                throw (Error)e;
            }
            if(e instanceof NMSLException || e instanceof IndexOutOfBoundsException) {
                throw new NMSLException("混淆出的代码可能有些问题", e);
            }
        }
    }
}

class ClassWriter1 extends ClassWriter {

    public ClassWriter1(int flags) {
        super(flags);
    }

    public ClassWriter1(ClassReader var1, int var2) {
        super(var1, var2);
        // TODO 自动生成的构造函数存根
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected String getCommonSuperClass(String var1, String var2) {
        // ClassLoader var3 = ClassWriter1.class.getClassLoader();

        Class var4;
        Class var5;
        try {
            // var4 = Class.forName(var1.replace('/', '.'), false, var3);
            var4 = Main.urlcl.loadClass(var1.replace('/', '.'));
        } catch (Throwable var7) {
            // System.err.println(var1);
            // var7.printStackTrace();
            //System.out.println(var1 + " - " + var2);
            if(var7 instanceof ClassNotFoundException) {
                Main.urlcl.defineClass(getClass(var7.getLocalizedMessage().replace(".", "/")));
                return getCommonSuperClass(var1, var2);
            }
            return "java/lang/Object";
        }
        try {
            // var5 = Class.forName(var2.replace('/', '.'), false, var3);
            var5 = Main.urlcl.loadClass(var2.replace('/', '.'));
        } catch (Throwable var7) {
            // System.err.println(var2);
            //System.out.println(var1 + " - " + var2);
            // var7.printStackTrace();
            if(var7 instanceof ClassNotFoundException) {
                Main.urlcl.defineClass(getClass(var7.getLocalizedMessage().replace(".", "/")));
                return getCommonSuperClass(var1, var2);
            }
            return "java/lang/Object";
        }


        if (var4.isAssignableFrom(var5)) {
            return var1;
        } else if (var5.isAssignableFrom(var4)) {
            return var2;
        } else if (!var4.isInterface() && !var5.isInterface()) {
            do {
                var4 = var4.getSuperclass();
            } while(!var4.isAssignableFrom(var5));

            return var4.getName().replace('.', '/');
        } else {
            return "java/lang/Object";
        }
    }

    private byte[] getClass(String name) {

        ClassWriter cw = new ClassWriter(0);
        cw.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC | Opcodes.ACC_SUPER, name, null, "java/lang/Object", null);

        return cw.toByteArray();

    }

}

class InvokeDynamicObfVisitor extends ObfVisitor {

    public InvokeDynamicObfVisitor(ClassReader cr, ClassWriter1 cw,int api) {
        super(cr,cw,api);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        // TODO 自动生成的方法存根
        if((access | Opcodes.ACC_SYNTHETIC) == access || (access | Opcodes.ACC_ENUM) == access) isEx = true;
        super.visit(version, access, name, signature, superName, interfaces);
    }
    //
    //	@Override
    //	public void visitSource(String var1, String var2) {
    //		// TODO 自动生成的方法存根
    //
    //		super.visitSource("TMD可莉炸死你们这群王八蛋.java", var2);
    //	}
    //
    @Override
    public void visitAttribute(Attribute var1) {
        // TODO 自动生成的方法存根
        // System.out.println(var1.type);
        super.visitAttribute(var1);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature,
                                     String[] exceptions) {
        // TODO 自动生成的方法存根
        if(isEx) return super.visitMethod(access, name, descriptor, signature, exceptions);
        if(isIn) return new MethodVisitor(api, super.visitMethod(access, name, descriptor, signature, exceptions)) {

            @Override
            public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                int key2;
                // TODO 自动生成的方法存根
                int key = key2 = r.nextInt();

                char[] charKey,charOwner,charName,charDescriptor;
                // System.out.println(name + descriptor);
                // if(owner.startsWith("org")) {
                // super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                charKey = new char[key % 10 + 10];
                for(int i = 0;i < charKey.length;i++) {
                    key += (key * (key >> 5) + (key << 5)) + 114514;
                    charKey[i] = (char) (key % 0x10000);
                }
                charOwner = (owner.replace("/", ".")).toCharArray();
                charName = name.toCharArray();
                charDescriptor = descriptor.toCharArray();
                // System.out.println(charOwner.length);
                // System.out.println(charKey.length);
                for(int i = 0;i < charOwner.length;i++) {
                    charOwner[i] = (char) (charOwner[i] ^ charKey[i % charKey.length]);
                }
                for(int i = 0;i < charName.length;i++) {
                    charName[i] = (char) (charName[i] ^ charKey[i % charKey.length]);
                }
                for(int i = 0;i < charDescriptor.length;i++) {
                    charDescriptor[i] = (char) (charDescriptor[i] ^ charKey[i % charKey.length]);
                }

                switch(opcode) {
                    case Opcodes.INVOKESTATIC:

                        if(!Main.dontEncode) super.visitInvokeDynamicInsn("XiaoShadiao", descriptor, new Handle(Opcodes.H_INVOKESTATIC, "XiaoShadiao", "最喜欢玩原神了", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"), new Object[] {"玩原神玩的", key2, new String(charOwner), new String(charName), new String(charDescriptor)});
                        else super.visitInvokeDynamicInsn("XiaoShadiao", descriptor, new Handle(Opcodes.H_INVOKESTATIC, "XiaoShadiao", "最喜欢玩原神了", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"), new Object[] {"玩原神玩的", owner, name, descriptor});
                        break;
                    case Opcodes.INVOKEINTERFACE:
                    case Opcodes.INVOKEVIRTUAL:


                        if(!descriptor.contains("[L")) {
                            if(!Main.dontEncode) super.visitInvokeDynamicInsn("XiaoShadiao", descriptor.replace("(", "(Ljava/lang/Object;"), new Handle(Opcodes.H_INVOKESTATIC, "XiaoShadiao", "最喜欢玩原神了", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"), new Object[] {"婉约婶婉徳", key2, new String(charOwner), new String(charName), new String(charDescriptor)});
                            else super.visitInvokeDynamicInsn("XiaoShadiao", descriptor.replace("(", "(Ljava/lang/Object;"), new Handle(Opcodes.H_INVOKESTATIC, "XiaoShadiao", "最喜欢玩原神了", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"), new Object[] {"婉约婶婉徳", owner, name, descriptor});
                        } else super.visitMethodInsn(opcode, owner.replace(".", "/"), name, descriptor, isInterface);
                        break;
                    default:
                        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                        break;
                }
                // super.visitInsn(Opcodes.NOP);
                // super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
            }


        };
        return super.visitMethod(access, name, descriptor, signature, exceptions);
    }

}

class InvokeDynamicObfVisitorT extends ObfVisitor {

    class MethodInfo {

        boolean isStatic;
        String owner, name, des;

        public String toString() {
            return (isStatic ? "static:" : "VIRTUAL:".toLowerCase()) + owner + ":" + name + des;
        }

        public boolean equals(Object o) {
            return o!=null && this.toString().equals(o.toString());
        }

        public int hashCode() {
            return toString().hashCode();
        }

    }

    public final String fieldName1;
    public final String fieldName1Desc = "[LXiaoShadiao;";

    public Map<MethodInfo, Integer> methods = new HashMap<>();
    private MethodVisitor clinitmv;
    private boolean startedFlag;
    private boolean hasClinitMethod;

    public InvokeDynamicObfVisitorT(ClassReader cr, ClassWriter1 cw,int api) {
        super(cr,cw,api);
        fieldName1 = Main.DIYRemapStringsFile == null ? Utils.spawnRandomChar(20, false) : Utils.getRandomMember(Main.remapStrings);

        // System.out.println(cr.getClassName() + isEx + isIn);
    }

    @Override
    public void visitEnd() {
        // TODO 自动生成的方法存根

        if(!hasClinitMethod && clinitmv != null) {
            clinitmv.visitInsn(Opcodes.RETURN);
            clinitmv.visitMaxs(1, 1);
        }

        super.visitEnd();

    }
    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        // TODO 自动生成的方法存根
        if((access | Opcodes.ACC_SYNTHETIC) == access || (access | Opcodes.ACC_ENUM) == access) isEx = true;
        if((version <= 51 && version != -65536 && Main.fixVersion) && !isEx && isIn) version = 52;
        super.visit(version, access, name, signature, superName, interfaces);
        if(Main.useInvokeDynamicObfT && isIn && !isEx) {

            ClassReader reader = new ClassReader(cr.b);
            reader.accept(new ClassVisitor(Main.原批诶批爱) {
                @Override
                public MethodVisitor visitMethod(int access, String name, String descriptor, String signature,
                                                 String[] exceptions) {
                    // TODO 自动生成的方法存根
                    return new MethodVisitor(Main.原批诶批爱, super.visitMethod(access, name, descriptor, signature, exceptions)) {

                        @Override
                        public void visitMethodInsn(int opcode, String owner, String name, String descriptor) {
                            // TODO 自动生成的方法存根
                            this.visitMethodInsn(opcode, owner, name, descriptor, false);
                        }

                        @Override
                        public void visitMethodInsn(int opcode, String owner, String name, String descriptor,
                                                    boolean isInterface) {
                            // TODO 自动生成的方法存根
                            super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);

                            if(opcode  != Opcodes.INVOKESPECIAL) {
//
//								if(r.nextBoolean()) {
//									MethodInfo mi = new MethodInfo();
//									mi.isStatic = r.nextBoolean();
//									mi.owner = Utils.spawnRandomChar();
//									mi.name = Utils.spawnRandomChar();
//									mi.des = Utils.spawnRandomChar();
//									methods.put(mi, methods.size());
//								}
//
                                MethodInfo mi = new MethodInfo();mi.isStatic = Opcodes.INVOKESTATIC == opcode;
                                mi.owner = owner;
                                mi.name = name;
                                mi.des = descriptor;
                                if(!methods.containsKey(mi)) methods.put(mi, methods.size());
//
//								if(r.nextBoolean()) {
//									mi = new MethodInfo();
//									mi.isStatic = r.nextBoolean();
//									mi.owner = Utils.spawnRandomChar();
//									mi.name = Utils.spawnRandomChar();
//									mi.des = Utils.spawnRandomChar();
//									methods.put(mi, methods.size());
//								}

                            }
                        }

                    };
                }
            }, 0);

            // visitField(Opcodes.ACC_STATIC | Opcodes.ACC_PRIVATE | (Main.addSyntheticFlag ? Opcodes.ACC_SYNTHETIC : 0), fieldName1, fieldName1Desc, null, null);
            // arrayIniter = StringNumberObfVisitor.this.visitMethod(Opcodes.ACC_PRIVATE | Opcodes.ACC_STATIC | (Main.addSyntheticFlag ? (Opcodes.ACC_SYNTHETIC | Opcodes.ACC_BRIDGE) : 0), methodName1, methodName1Desc, null, null);
            clinitmv = this.visitMethod(Opcodes.ACC_STATIC | (Main.addSyntheticFlag ? Opcodes.ACC_SYNTHETIC : 0), "<clinit>", "()V", null, null);
            initArray();
            // clinitmv.visitMethodInsn(Opcodes.INVOKESTATIC, StringNumberObfVisitor.this.cr.getClassName(), methodName1, methodName1Desc, false);
        }
        startedFlag = true;
    }

    private void initArray() {
        // TODO 自动生成的方法存根
        if(!methods.isEmpty()) {
            visitField(Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC | (Main.addSyntheticFlag ? Opcodes.ACC_SYNTHETIC : 0), fieldName1, fieldName1Desc, null, null);
            clinitmv.visitLdcInsn(methods.size() + 1);
            clinitmv.visitTypeInsn(Opcodes.ANEWARRAY, "XiaoShadiao");

            ArrayList<MethodInfo> al = new ArrayList<>(methods.keySet());
            for(int i = 0;i < al.size();i++) {
                MethodInfo str1 = al.get(i), str2 = al.get(r.nextInt(al.size()));
                if(Objects.equals(str1, str2)) continue;
                int iii1 = methods.get(str1), iii2 = methods.get(str2);
                methods.put(str1, iii2); methods.put(str2, iii1);
            }

            for(Entry<MethodInfo, Integer> entry : methods.entrySet()) {
                // System.out.println(arrayIndex);
                MethodInfo s = entry.getKey();

                clinitmv.visitInsn(Opcodes.DUP);
                clinitmv.visitLdcInsn(entry.getValue());// mv2.visitLdcInsn(entry.getValue());

                clinitmv.visitTypeInsn(Opcodes.NEW, "XiaoShadiao");
                clinitmv.visitInsn(Opcodes.DUP);
                clinitmv.visitLdcInsn(entry.getKey().isStatic ? "玩原神玩的" : "婉约婶婉徳");
                for(String str : new String[] {entry.getKey().owner.replace("/", "."), entry.getKey().name, entry.getKey().des}) {
                    String className = Utils.getRandomMember(Main.exceptions).getName();

                    Label l = new Label();
                    int key = r.nextInt(126) + 1;
                    clinitmv.visitLabel(l);
                    clinitmv.visitLineNumber(key, l);
                    clinitmv.visitTypeInsn(Opcodes.NEW, className.replace(".", "/"));
                    clinitmv.visitInsn(Opcodes.DUP);

                    {
                        char cc = 0, cc1;
                        char[] chars = str.toCharArray();
                        for(int i = 0;i < chars.length;i++) {
                            cc1 = chars[i];
                            chars[i] = (char) (chars[i] ^ ((cc ^ (key * (chars.length - i)) ^ className.charAt(i % className.length())) % 0x10000));
                            cc = cc1;
                        }
                        clinitmv.visitLdcInsn(new String(chars));
                    }

                    clinitmv.visitMethodInsn(Opcodes.INVOKESPECIAL, className.replace(".", "/"), "<init>", "(Ljava/lang/String;)V", false);
                }

                clinitmv.visitMethodInsn(Opcodes.INVOKESPECIAL, "XiaoShadiao", "<init>", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V", false);
                // super.visitInsn(Opcodes.ATHROW);
                clinitmv.visitInsn(Opcodes.AASTORE);
            }
            clinitmv.visitFieldInsn(Opcodes.PUTSTATIC, cr.getClassName(), fieldName1, fieldName1Desc);
        }

    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature,
                                     String[] exceptions) {
        if(name.equals("<clinit>") && clinitmv != null) {
            hasClinitMethod = true;
            return clinitmv;
        }
        MethodVisitor temp = visitMethod2(access, name, descriptor, signature, exceptions);

        if(name.equals("<clinit>") && clinitmv == null) {
            clinitmv = temp;
            // initArray();
        }

        return temp;
    }

    private String encodeStringKey = Utils.spawnRandomChar(1, false);
    private MethodVisitor visitMethod2(int access, String name, String descriptor, String signature,
                                       String[] exceptions) {
        // TODO 自动生成的方法存根
        if(isEx) return super.visitMethod(access, name, descriptor, signature, exceptions);
        if(isIn) return new MethodVisitor(api, super.visitMethod(access, name, descriptor, signature, exceptions)) {

            private String encodeString(String value) {

                String key = encodeStringKey;

                char keychar = key.charAt(0);
                char[] chars = value.toCharArray();
                for (int i = 0; i < chars.length; i++) {
                    chars[i] = (char) (chars[i] ^ (keychar * (i + 1)));
                }

                return key + new String(chars);
            }

            @Override
            public void visitMaxs(int maxStack, int maxLocals) {
                // TODO 自动生成的方法存根
                // System.out.println(cr.getClassName() + "." + name + descriptor);
                super.visitMaxs(maxStack, maxLocals);
            }

            @Override
            public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                Integer index;
                // System.out.println(owner + "========" + name + descriptor);
                switch(opcode) {
                    case Opcodes.INVOKESTATIC:
                        MethodInfo mi = new MethodInfo();
                        mi.des = descriptor;
                        mi.name = name;
                        mi.owner = owner;
                        mi.isStatic = true;
                        if((index = methods.get(mi)) == null) throw new NMSLException("找不到方法: " + mi.toString());
//					visitFieldInsn(Opcodes.GETSTATIC, cr.getClassName(), fieldName1, fieldName1Desc);
//					visitLdcInsn(index);
//					visitInsn(Opcodes.AALOAD);
                        int indexId = index;
                        int hash1 = cr.getClassName().replace("/", ".").hashCode();
                        int hash2 = fieldName1.hashCode();
                        int hash3 = Utils.r.nextInt();
                        int hash4 = indexId ^ hash2 ^ hash1 ^ hash3;
                        // if(Main.useSOFCrasher) hash4 ^= SOFCrasherObfVistor.hashmap.get(cr.getClassName());
                        super.visitInvokeDynamicInsn("XiaoShadiao", descriptor.replace("(", "("), new Handle(Opcodes.H_INVOKESTATIC, "XiaoShadiao", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"), new Object[] {
                                encodeString(cr.getClassName().replace("/", ".")),
                                encodeString(fieldName1),
                                hash3,
                                hash4
                        });
                        break;
                    case Opcodes.INVOKEINTERFACE:
                    case Opcodes.INVOKEVIRTUAL:
                        if(!descriptor.contains("[L")) {
                            mi = new MethodInfo();
                            mi.des = descriptor;
                            mi.name = name;
                            mi.owner = owner;
                            mi.isStatic = false;
                            if((index = methods.get(mi)) == null) throw new NMSLException("找不到方法: " + mi.toString());

                            indexId = index;
                            hash1 = cr.getClassName().replace("/", ".").hashCode();
                            hash2 = fieldName1.hashCode();

                            hash3 = Utils.r.nextInt();
                            hash4 = indexId ^ hash2 ^ hash1 ^ hash3;
                            // if(Main.useSOFCrasher) hash4 ^= SOFCrasherObfVistor.hashmap.get(cr.getClassName());

                            super.visitInvokeDynamicInsn("XiaoShadiao", descriptor.replace("(", "(Ljava/lang/Object;"), new Handle(Opcodes.H_INVOKESTATIC, "XiaoShadiao", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"), new Object[] {
                                    encodeString(cr.getClassName().replace("/", ".")),
                                    encodeString(fieldName1),
                                    hash3,
                                    hash4
                            });
                        } else super.visitMethodInsn(opcode, owner.replace(".", "/"), name, descriptor, isInterface);
                        break;
                    default:
                        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                        break;
                }
                // super.visitInsn(Opcodes.NOP);
                // super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
            }


        };
        return super.visitMethod(access, name, descriptor, signature, exceptions);
    }
}

class StringObfVisitorT extends ObfVisitor {

    public final String fieldName1;
    public final String fieldName1Desc = "[Ljava/lang/Object;";

    public Map<String, Integer> arrayIndex = new HashMap<String, Integer>();
    private MethodVisitor clinitmv;
    private boolean startedFlag;
    private boolean hasClinitMethod;

    public StringObfVisitorT(ClassReader cr, ClassWriter1 cw, int api) {
        super(cr,cw,api);
        fieldName1 = Main.DIYRemapStringsFile == null ? Utils.spawnRandomChar(20, false) : Utils.getRandomMember(Main.remapStrings);

        // System.out.println(cr.getClassName() + isEx + isIn);
    }

    @Override
    public void visitEnd() {
        // TODO 自动生成的方法存根

        if(!hasClinitMethod && clinitmv != null) {
            clinitmv.visitInsn(Opcodes.RETURN);
            clinitmv.visitMaxs(1, 1);
        }

        super.visitEnd();

    }
    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        // TODO 自动生成的方法存根
        if((access | Opcodes.ACC_SYNTHETIC) == access || (access | Opcodes.ACC_ENUM) == access || (access | Opcodes.ACC_INTERFACE) == access) isEx = true;
        if((version <= 51 && version != -65536 && Main.fixVersion) && !isEx && isIn) version = 52;
        super.visit(version, access, name, signature, superName, interfaces);
        if(Main.useStringObfT && isIn && !isEx) {

            ClassReader reader = new ClassReader(cr.b);
            reader.accept(new ClassVisitor(Main.原批诶批爱) {
                @Override
                public MethodVisitor visitMethod(int access, String name, String descriptor, String signature,
                                                 String[] exceptions) {
                    // TODO 自动生成的方法存根
                    return new MethodVisitor(Main.原批诶批爱, super.visitMethod(access, name, descriptor, signature, exceptions)) {

                        @Override
                        public void visitLdcInsn(Object value) {
                            if(value instanceof String) {
                                for (int jj = 0; jj < (r.nextBoolean() && r.nextBoolean() ? r.nextInt(2) : 0); jj++) {
                                    arrayIndex.put(Utils.spawnRandomChar(r.nextInt(30) + 1, true), arrayIndex.size());
                                }

                                if(!arrayIndex.containsKey(value)) arrayIndex.put(value.toString(), arrayIndex.size());
                            }
                            super.visitLdcInsn(value);
                        }
                    };
                }
            }, 0);

            // visitField(Opcodes.ACC_STATIC | Opcodes.ACC_PRIVATE | (Main.addSyntheticFlag ? Opcodes.ACC_SYNTHETIC : 0), fieldName1, fieldName1Desc, null, null);
            // arrayIniter = StringNumberObfVisitor.this.visitMethod(Opcodes.ACC_PRIVATE | Opcodes.ACC_STATIC | (Main.addSyntheticFlag ? (Opcodes.ACC_SYNTHETIC | Opcodes.ACC_BRIDGE) : 0), methodName1, methodName1Desc, null, null);
            clinitmv = this.visitMethod(Opcodes.ACC_STATIC | (Main.addSyntheticFlag ? Opcodes.ACC_SYNTHETIC : 0), "<clinit>", "()V", null, null);
            initArray();
            // clinitmv.visitMethodInsn(Opcodes.INVOKESTATIC, StringNumberObfVisitor.this.cr.getClassName(), methodName1, methodName1Desc, false);
        }
        startedFlag = true;
    }

    void initArray() {

        // TODO 自动生成的方法存根

        // MethodVisitor arrayIniter = null;

        if(!Main.useStringObfT) return;

        // if(clinitmv != null && arrayIniter == null) arrayIniter = StringNumberObfVisitor.this.visitMethod(Opcodes.ACC_PRIVATE | Opcodes.ACC_STATIC | (Main.addSyntheticFlag ? (Opcodes.ACC_SYNTHETIC | Opcodes.ACC_BRIDGE) : 0), methodName1, methodName1Desc, null, null);
        if(!arrayIndex.isEmpty()) {
            visitField(Opcodes.ACC_PRIVATE | Opcodes.ACC_STATIC | (Main.addSyntheticFlag ? Opcodes.ACC_SYNTHETIC : 0), fieldName1, fieldName1Desc, null, null);

//			ArrayList<String> al = new ArrayList<>(arrayIndex.keySet());
//			for(int i = 0;i < al.size();i++) {
//				String str1 = al.get(i), str2 = al.get(r.nextInt(al.size()));
//				if(Objects.equals(str1, str2)) continue;
//				int iii1 = arrayIndex.get(str1), iii2 = arrayIndex.get(str2);
//				arrayIndex.put(str1, iii2); arrayIndex.put(str2, iii1);
//			}
            List<String> keys = new ArrayList<>(arrayIndex.keySet());
            for (int i = keys.size() - 1; i > 0; i--) {
                int j = r.nextInt(i + 1); // 0 ≤ j ≤ i

                // 避免不必要的交换检查
                String str1 = keys.get(i);
                String str2 = keys.get(j);

                int val1 = arrayIndex.get(str1);
                int val2 = arrayIndex.get(str2);

                arrayIndex.put(str1, val2);
                arrayIndex.put(str2, val1);
            }
            // if(arrayIniter == null) arrayIniter = StringNumberObfVisitor.this.visitMethod(Opcodes.ACC_PRIVATE | Opcodes.ACC_STATIC | (Main.addSyntheticFlag ? (Opcodes.ACC_SYNTHETIC | Opcodes.ACC_BRIDGE) : 0), methodName1, methodName1Desc, null, null);

            clinitmv.visitLdcInsn(arrayIndex.size() + 1);
            clinitmv.visitTypeInsn(Opcodes.ANEWARRAY, "java/lang/Object");
            // mv2.visitInsn(Opcodes.DUP);
            // mv2.visitIntInsn(Opcodes.NEWARRAY, operand);
            // System.out.println(arrayIndex);
            for(Entry<String, Integer> entry : arrayIndex.entrySet()) {
                String s = entry.getKey();

                String className;

                className = Utils.getRandomMember(Main.exceptions).getName();

                char[] chars = s.toCharArray();
                char[] chars2 = className.toCharArray();

                int key = r.nextInt(126) + 1;
                int key3 = 0;

                int key2 = Utils.spawnRandomChar().charAt(0);
                char key2_ = (char) key2;

                char[] newchars = new char[chars.length];

                for(int i = 0;i < chars.length;i++) {
                    // if(i == 0) key2 = chars[i];
                    // else {
                    key2 <<= key2 + (key2 % 4) + 123456789;
                    key3 = (key2 / 9) >> 2;
                    newchars[i] = (char) (chars[i] ^ ((((key * (i+1)) ^ (key2 + key3)) ^ chars2[i % chars2.length]) % 0x10000));
                    // }
                }

                String encodedString = key2_ + new String(newchars);

                Label l = new Label();

                clinitmv.visitLabel(l);
                clinitmv.visitLineNumber(key, l);
                // mv2.visitFieldInsn(Opcodes.GETSTATIC, StringNumberObfVisitor.this.cr.getClassName(), fieldName1, fieldName1Desc);
                clinitmv.visitInsn(Opcodes.DUP);
                clinitmv.visitLdcInsn(entry.getValue());// mv2.visitLdcInsn(entry.getValue());

                clinitmv.visitTypeInsn(Opcodes.NEW, className.replace(".", "/"));
                clinitmv.visitInsn(Opcodes.DUP);
                clinitmv.visitLdcInsn(encodedString);
                clinitmv.visitMethodInsn(Opcodes.INVOKESPECIAL, className.replace(".", "/"), "<init>", "(Ljava/lang/String;)V", false);
                // super.visitInsn(Opcodes.ATHROW);

                clinitmv.visitInsn(Opcodes.AASTORE);

                // System.out.println(arrayIndex);
            }
            // mv2.visitInsn(Opcodes.DUP);
            clinitmv.visitFieldInsn(Opcodes.PUTSTATIC, cr.getClassName(), fieldName1, fieldName1Desc);
            // mv2.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Throwable", "<init>", "([BLjava/lang/Throwable;)V", false);
            // arrayIniter.visitInsn(Opcodes.RETURN);
            // arrayIniter.visitMaxs(0, 0);
        } else {
            // arrayIniter.visitInsn(Opcodes.RETURN);
            // arrayIniter.visitMaxs(0, 0);
        }
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature,
                                     String[] exceptions) {
        if(name.equals("<clinit>") && clinitmv != null) {
            hasClinitMethod = true;
            return clinitmv;
        }
        MethodVisitor temp = visitMethod2(access, name, descriptor, signature, exceptions);

        if(name.equals("<clinit>") && clinitmv == null) {
            clinitmv = temp;
            // initArray();
        }

        return temp;
    }

    private MethodVisitor visitMethod2(int access, String name, String descriptor, String signature,
                                       String[] exceptions) {
        // TODO 自动生成的方法存根
        if(isEx) return super.visitMethod(access, name, descriptor, signature, exceptions);
        if(isIn) return new MethodVisitor(api, super.visitMethod(access, name, descriptor, signature, exceptions)) {

            @Override
            public void visitLdcInsn(Object value) {
                if((name.equals("<clinit>") && !isXiaoShadiao)) {
                    super.visitLdcInsn(value);
                    return;
                }
                if (value instanceof String) {
                    if(arrayIndex.containsKey(value)) {

                        int arrayII;

                        if(arrayIndex.containsKey(value)) {
                            arrayII = arrayIndex.get(value);
                            // System.out.println(value + " -> " + arrayII);
                        } else {
                            throw new NMSLException(value + " String NOT FOUND, THIS SHOULD NOT HAPPEN!");
                            // arrayIndex.put(value, arrayII = arrayIndex.size());
                        }

                        super.visitFieldInsn(Opcodes.GETSTATIC, cr.getClassName(), fieldName1, fieldName1Desc);
                        this.visitLdcInsn(arrayII);
                        super.visitInsn(Opcodes.AALOAD);
                        super.visitMethodInsn(Opcodes.INVOKESTATIC, "XiaoShadiao", "原神启动", "(Ljava/lang/Object;)Ljava/lang/String;", false);
                        //
                        //					for(int jj = 0;jj < (r.nextBoolean() ? r.nextInt(2) : 0);jj++) {
                        //						arrayIndex.put(Utils.spawnRandomChar(r.nextInt(30) + 1, true), arrayIndex.size());
                        //					}

                    } else {
                        super.visitLdcInsn(value);
                    }
                } else {
                    super.visitLdcInsn(value);
                }
            }
        };
        return super.visitMethod(access, name, descriptor, signature, exceptions);
    }
}
class StringNumberObfVisitor extends ObfVisitor {

    private final boolean visitLongLCMP;
    public int randomFlag = Utils.r.nextInt();

    public boolean isClassNameContains$;

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        isClassNameContains$ = name.contains("$");
    }

    public StringNumberObfVisitor(ClassReader cr, ClassWriter1 cw, int api, boolean visitLongLCMP) {
        super(cr, cw, api);
        this.visitLongLCMP = visitLongLCMP && Main.useNumberLCMPObf;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        if(isEx || name.equals("原神启动")) return super.visitMethod(access, name, descriptor, signature, exceptions);
        if(isIn) return new MethodVisitor(api, super.visitMethod(access, name, descriptor, signature, exceptions)) {

            private int negNumberVisited;
            int junkCount = 0;
            boolean isClinit;
            {
                isClinit = name.contains("init>");
            }
            @Override
            public void visitIincInsn(int varIndex, int increment) {
                addJunkLabelThrow();
                super.visitIincInsn(varIndex, increment);
            }
            @Override
            public void visitMaxs(int maxStack, int maxLocals) {
                // TODO 自动生成的方法存根

                Iterator<Label> it = invaildLabel.iterator();

                while (it.hasNext()) {
                    Label label = it.next();
                    it.remove();
                    mv.visitLabel(label);
                    super.visitTypeInsn(Opcodes.NEW, "pers/XiaoShadiao/NMSLException");
                    super.visitInsn(Opcodes.DUP);
                    super.visitLdcInsn("\u73a9\u539f\u795e\u73a9\u7684");
                    super.visitMethodInsn(Opcodes.INVOKESPECIAL, "pers/XiaoShadiao/NMSLException", "<init>", "(Ljava/lang/String;)V", false);
                    super.visitInsn(Opcodes.ATHROW);

                }
                invaildLabel.clear();

				/*super.visitTypeInsn(Opcodes.NEW, "pers/XiaoShadiao/NMSLException");
				super.visitInsn(Opcodes.DUP);
				super.visitLdcInsn("\u73a9\u539f\u795e\u73a9\u7684");
				super.visitMethodInsn(Opcodes.INVOKESPECIAL, "pers/XiaoShadiao/NMSLException", "<init>", "(Ljava/lang/String;)V", false);
				super.visitInsn(Opcodes.ATHROW);*/

                super.visitMaxs(maxStack, maxLocals);
            }

            private void visitNumberLong(long l) {
                long T1_1 = r.nextLong();
                long T1_2 = l ^ T1_1;

                long T2_1 = r.nextLong();
                long T2_2 = T1_1 ^ T2_1;

                super.visitLdcInsn(T2_1);
                for (int j = 0; j < r.nextInt(5) + (isClinit || !Main.bigBrainNumberObf ? -1000 : 1); j++) {
                    super.visitInsn(Opcodes.LNEG); // super.visitInsn(Opcodes.DUP); super.visitInsn(Opcodes.POP);
                    super.visitInsn(Opcodes.LNEG); // super.visitInsn(Opcodes.DUP); super.visitInsn(Opcodes.POP);
                }

                super.visitLdcInsn(T2_2);
                for (int j = 0; j < r.nextInt(5) + (isClinit || !Main.bigBrainNumberObf ? -1000 : 1); j++) {
                    super.visitInsn(Opcodes.LNEG); // super.visitInsn(Opcodes.DUP); super.visitInsn(Opcodes.POP);
                    super.visitInsn(Opcodes.LNEG); // super.visitInsn(Opcodes.DUP); super.visitInsn(Opcodes.POP);
                }

                super.visitLdcInsn(~T1_2);
                for (int j = 0; j < r.nextInt(5) + (isClinit || !Main.bigBrainNumberObf ? -1000 : 1); j++) {
                    super.visitInsn(Opcodes.LNEG); // super.visitInsn(Opcodes.DUP); super.visitInsn(Opcodes.POP);
                    super.visitInsn(Opcodes.LNEG); // super.visitInsn(Opcodes.DUP); super.visitInsn(Opcodes.POP);
                }

                // if(randomFlag++ % 8 == 0) addJunkLabelThrow();
                super.visitMethodInsn(Opcodes.INVOKESTATIC, "XiaoShadiao", "原神启动", "(JJJ)J", false);
                for (int j = 0; j < (isClinit || !Main.bigBrainNumberObf ? -1000 : 1); j++) {
                    super.visitInsn(Opcodes.LNEG); // super.visitInsn(Opcodes.DUP); super.visitInsn(Opcodes.POP);
                    super.visitInsn(Opcodes.LNEG); // super.visitInsn(Opcodes.DUP); super.visitInsn(Opcodes.POP);
                }

            }

            private int intEncodeCount;
            private void visitNumberInt(int i) {

                if(i != 0 || !visitLongLCMP || r.nextBoolean()) {

                    int T2_1, T2_2, T1_2;

                    String fieldName = "";

                    intEncodeCount++;
                    boolean useAntiDebuggerFlag = Main.useAntiDebugger && !isXiaoShadiao && intEncodeCount > (Main.useSwitchCaseObf ? 10 : 2);
                    if(useAntiDebuggerFlag) {
                        int T1_1 = r.nextInt();
                        T1_2 = i ^ T1_1;

                        // T2_1 = r.nextInt();
                        fieldName = r.nextBoolean() ? Utils.getRandomMember(Main.xiaoshadiaoFields) : Main.totalHashedSerializedFieldName;
                        T2_1 = Main.xiaoshadiaoFieldMapHashCode.get(fieldName);
                        T2_2 = T1_1 ^ T2_1;
                    } else {
                        int T1_1 = r.nextInt();
                        T1_2 = i ^ T1_1;

                        T2_1 = r.nextInt();
                        T2_2 = T1_1 ^ T2_1;
                    }

                    if(useAntiDebuggerFlag) {
                        super.visitFieldInsn(Opcodes.GETSTATIC, "XiaoShadiao", "i", "LXiaoShadiao;");
                        XiaoShadiaoSerializableObfVisitor.FieldType fieldType = Main.xiaoshadiaoFieldMap.get(fieldName);
                        super.visitFieldInsn(Opcodes.GETFIELD, "XiaoShadiao", fieldName, fieldType.desc);

                        switch (fieldType) {
                            case STRING:
                                super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "hashCode", "()I", false);
                                break;
                            case INT:
                            default:
                                break;
                        }

                    } else {
                        super.visitLdcInsn(T2_1);
                    }
//				for (int j = 0; j < r.nextInt(2) + (isClinit || !Main.bigBrainNumberObf ? -1000 : 1); j++) {
//					Label l = new Label();
//					super.visitInsn(Opcodes.INEG); super.visitJumpInsn(Opcodes.GOTO, l); super.visitLabel(l);
//					l = new Label();
//					super.visitInsn(Opcodes.INEG); super.visitJumpInsn(Opcodes.GOTO, l); super.visitLabel(l);
//				}
                    visitNegNumber(r.nextInt(5) + (isClinit || !Main.bigBrainNumberObf ? -1000 : 1));
                    super.visitLdcInsn(T2_2);
//				for (int j = 0; j < r.nextInt(2) + (isClinit || !Main.bigBrainNumberObf ? -1000 : 1); j++) {
//					Label l = new Label();
//					super.visitInsn(Opcodes.INEG); super.visitJumpInsn(Opcodes.GOTO, l); super.visitLabel(l);
//					l = new Label();
//					super.visitInsn(Opcodes.INEG); super.visitJumpInsn(Opcodes.GOTO, l); super.visitLabel(l);
//				}
                    visitNegNumber(r.nextInt(5) + (isClinit || !Main.bigBrainNumberObf ? -1000 : 1));
                    super.visitLdcInsn(~T1_2);
//				for (int j = 0; j < r.nextInt(3) + (isClinit || !Main.bigBrainNumberObf ? -1000 : 1); j++) {
//					Label l = new Label();
//					super.visitInsn(Opcodes.INEG); super.visitJumpInsn(Opcodes.GOTO, l); super.visitLabel(l);
//					l = new Label();
//					super.visitInsn(Opcodes.INEG); super.visitJumpInsn(Opcodes.GOTO, l); super.visitLabel(l);
//				}
                    visitNegNumber(r.nextInt(5) + (isClinit || !Main.bigBrainNumberObf ? -1000 : 1));
                    // if(randomFlag++ % 12 == 0) addJunkLabelThrow();
                    super.visitMethodInsn(Opcodes.INVOKESTATIC, "XiaoShadiao", "原神启动", "(III)I", false);
//				for (int j = 0; j < r.nextInt(42) + (isClinit ? -1000 : 8); j++) {
//					super.visitInsn(Opcodes.INEG); super.visitInsn(Opcodes.DUP); super.visitInsn(Opcodes.POP);
//					super.visitInsn(Opcodes.INEG); super.visitInsn(Opcodes.DUP); super.visitInsn(Opcodes.POP);
//				}
                } else {
                    long l1 = r.nextLong();
                    this.visitNumberLong(l1);
                    long l2 = r.nextLong();
                    this.visitNumberLong(l2);
                    super.visitInsn(Opcodes.LCMP);
                    if(l1 > l2) visitNumberInt(-1);
                    else if(l1 < l2) visitNumberInt(1);

                    if(l1 != l2) super.visitInsn(Opcodes.IADD);

                    if(false) {


                        super.visitInsn(Opcodes.ICONST_0);


                        long l = r.nextLong();
                        super.visitLdcInsn(l);
                        super.visitLdcInsn(l);
                        super.visitInsn(Opcodes.LCMP);

                    }

                }
            }

            private void visitString(String value, boolean flag) {

                if (!Main.useStringObf) {
                    super.visitLdcInsn(value);
                    return;
                }

                String value1 = value.toString();
                String key = Utils.spawnRandomChar(1, false);

                char keychar = key.charAt(0);
                char[] chars = value1.toCharArray();
                for (int i = 0; i < chars.length; i++) {
                    chars[i] = (char) (chars[i] ^ (keychar * (i + 1)));
                }

                super.visitLdcInsn(key + new String(chars));
                super.visitMethodInsn(Opcodes.INVOKESTATIC, "XiaoShadiao", "原神启动", "(Ljava/lang/Object;)Ljava/lang/String;", false);

                if (flag) addJunkLabelThrow();
            }
            private void addJunkLabelThrow() {

                if(isClinit && r.nextBoolean()) return;
                if (!Main.useJunkCode) return;

                Label labelIf = new Label();
                int token;
                if (true) {
                    token = r.nextInt();
                    this.visitLdcInsn(token);
                } else {
                    String s = Utils.spawnRandomChar(10, false) + 2;
                    visitString(s, false);
                    super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "hashCode", "()I", false);
                    token = s.hashCode();
                }

                if (token > 0) {
                    // super.visitFrame(Opcodes.F_APPEND, 2, new Object[] {Opcodes.INTEGER, Opcodes.INTEGER}, 0, null);
                    // super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "hashCode", "()I", false);
                    // this.visitLdcInsn(token);
                    if(!(r.nextBoolean()) || !Main.useMoreJunkCode/* || !Main.bigBrainNumberObf || true*/) {

                        this.visitInsn(Opcodes.ICONST_0);

                        super.visitJumpInsn(Opcodes.IF_ICMPGE, labelIf);

                        super.visitTypeInsn(Opcodes.NEW, "pers/XiaoShadiao/NMSLException");
                        super.visitInsn(Opcodes.DUP);
                        super.visitLdcInsn("\u73a9\u539f\u795e\u73a9\u7684");
                        super.visitMethodInsn(Opcodes.INVOKESPECIAL, "pers/XiaoShadiao/NMSLException", "<init>", "(Ljava/lang/String;)V", false);

                        // super.visitInsn(Opcodes.ACONST_NULL);
                        super.visitInsn(Opcodes.ATHROW);
                        mv.visitLabel(labelIf);
                    } else {
//						Label templ = new Label(), l = new Label(), l2 = new Label(), l3 = new Label(), l4 = new Label();
//
//						super.visitJumpInsn(Opcodes.GOTO, templ);
//						super.visitLabel(l);
//
//						token = r.nextInt();
//						this.visitLdcInsn(token);
//						this.visitInsn(Opcodes.ICONST_0);
//						super.visitJumpInsn(token > 0 ? Opcodes.IF_ICMPLT : Opcodes.IF_ICMPGE, l);
//
//						super.visitTypeInsn(Opcodes.NEW, "pers/XiaoShadiao/NMSLException");
//						super.visitInsn(Opcodes.DUP);
//						this.visitLdcInsn(r.nextInt());
////						for (int j = 0; j < r.nextInt(2) + (isClinit || !Main.bigBrainNumberObf ? -1000 : 1); j++) {
////							Label l_ = new Label();
////							super.visitInsn(Opcodes.INEG); super.visitJumpInsn(Opcodes.GOTO, l_); super.visitLabel(l_);
////							l_ = new Label();
////							super.visitInsn(Opcodes.INEG); super.visitJumpInsn(Opcodes.GOTO, l_); super.visitLabel(l_);
////						}
//						visitNegNumber(r.nextInt(5) + (isClinit || !Main.bigBrainNumberObf ? -1000 : 5));
//						this.visitInsn(Opcodes.POP);
//						super.visitLdcInsn("玩原神玩的");
//						super.visitMethodInsn(Opcodes.INVOKESPECIAL, "pers/XiaoShadiao/NMSLException", "<init>", "(Ljava/lang/String;)V", false);
//						super.visitInsn(Opcodes.ATHROW);
//						// super.visitJumpInsn(Opcodes.GOTO, l4);
//
//						this.visitLdcInsn(r.nextInt());
//						this.visitInsn(Opcodes.POP);
//						super.visitLabel(templ);
//
//						this.visitInsn(Opcodes.ICONST_0);
//
//						super.visitJumpInsn(Opcodes.IF_ICMPLT, l);
//
//						token = r.nextInt();
//						this.visitLdcInsn(token);
//						this.visitLdcInsn(r.nextInt());
//						this.visitInsn(Opcodes.POP);
//						this.visitInsn(Opcodes.ICONST_0);
//						super.visitJumpInsn(token > 0 ? Opcodes.IF_ICMPLT : Opcodes.IF_ICMPGE, l);

                        Label templ = new Label(), l = new Label(), l2 = new Label(), l3 = new Label(), l4 = new Label();

                        super.visitJumpInsn(Opcodes.GOTO, templ);
                        super.visitLabel(l);

                        token = r.nextInt();
                        this.visitLdcInsn(token);
                        this.visitInsn(Opcodes.ICONST_0);
                        super.visitJumpInsn(token > 0 ? Opcodes.IF_ICMPLT : Opcodes.IF_ICMPGE, l);

                        super.visitTypeInsn(Opcodes.NEW, "pers/XiaoShadiao/NMSLException");

                        this.visitLdcInsn(token);
                        this.visitInsn(Opcodes.ICONST_0);
                        super.visitJumpInsn(token > 0 ? Opcodes.IF_ICMPLT : Opcodes.IF_ICMPGE, l2);

                        super.visitInsn(Opcodes.DUP);
                        super.visitLdcInsn("玩原神玩的");
                        super.visitMethodInsn(Opcodes.INVOKESPECIAL, "pers/XiaoShadiao/NMSLException", "<init>", "(Ljava/lang/String;)V", false);
                        super.visitInsn(Opcodes.ATHROW);

                        super.visitLabel(l2);
                        this.visitInsn(Opcodes.POP);
                        super.visitJumpInsn(Opcodes.GOTO, l3);

                        super.visitLabel(templ);

                        this.visitInsn(Opcodes.ICONST_0);

                        super.visitJumpInsn(Opcodes.IF_ICMPLT, l);

                        super.visitLabel(l3);

                        token = r.nextInt();
                        this.visitLdcInsn(token);
                        this.visitLdcInsn(r.nextInt());
                        this.visitInsn(Opcodes.POP);
                        this.visitInsn(Opcodes.ICONST_0);

                        super.visitJumpInsn(token > 0 ? Opcodes.IF_ICMPLT : Opcodes.IF_ICMPGE, l);
                    }



                } else if (token < 0) {

                    this.visitInsn(Opcodes.ICONST_0);

                    super.visitJumpInsn(Opcodes.IF_ICMPGE, labelIf);
                    invaildLabel.add(0, labelIf);


                } else {
                    this.visitInsn(Opcodes.ICONST_0);

                    super.visitJumpInsn(Opcodes.IF_ICMPEQ, labelIf);
                    // super.visitInsn(Opcodes.ACONST_NULL);
                    super.visitTypeInsn(Opcodes.NEW, "pers/XiaoShadiao/NMSLException");
                    super.visitInsn(Opcodes.DUP);
                    super.visitLdcInsn("\u73a9\u539f\u795e\u73a9\u7684");
                    super.visitMethodInsn(Opcodes.INVOKESPECIAL, "pers/XiaoShadiao/NMSLException", "<init>", "(Ljava/lang/String;)V", false);

                    super.visitInsn(Opcodes.ATHROW);
                    mv.visitLabel(labelIf);

                }
            }
            @Override
            public void visitLdcInsn(Object value) {

                if(value instanceof String && Main.useStringObf) {
                    try {

                        visitString(value.toString(), true);

                    } catch(Exception e) {
                        throw e;
                    }
                } else if(value instanceof Integer && Main.useNumberObf) {
                    visitNumberInt((Integer) value);
                } else if(value instanceof Long && Main.useNumberObf) {
                    visitNumberLong((Long) value);
                } else super.visitLdcInsn(value);
            }

            @Override
            public void visitInsn(int opcode) {
                Integer in = null;
                Long l = null;
//
//				if(Arrays.asList(Opcodes.IADD, Opcodes.ISUB, Opcodes.IMUL, Opcodes.IDIV, Opcodes.IREM).contains(opcode)) {
//					if(randomFlag++ % 8 == 0) addJunkLabelThrow();
//				}

                if(opcode >= 2 && opcode <= 8 && Main.useNumberObf) {
                    in = opcode - 3;
                } else if(opcode >= 9 && opcode <= 10 && Main.useNumberObf) {
                    l = Long.valueOf(opcode - 9);
                }
                if(in != null) {
                    visitNumberInt(in);
                } else if(l != null) {
                    visitNumberLong(l);
                } else super.visitInsn(opcode);

            }

            @Override
            public void visitIntInsn(int opcode, int operand) {

                if((opcode == Opcodes.BIPUSH || opcode == Opcodes.SIPUSH) && Main.useNumberObf) this.visitLdcInsn(operand); else super.visitIntInsn(opcode, operand);

            }

            @Override
            public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                // TODO 自动生成的方法存根
                super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
            }

            @Override
            public void visitTypeInsn(int opcode, String type) {
                // TODO 自动生成的方法存根
                // super.visitTypeInsn(Opcodes.NEW, "pers/XiaoShadiao/NMSLException");
                super.visitTypeInsn(opcode, type);
                if(opcode == Opcodes.NEW) addJunkLabelThrow();

            }

            public void visitNegNumber(int count) {
                if(negNumberVisited++ > 800) return;
                for (int j = 0; j < count; j++) {
                    Label l = new Label();
                    super.visitInsn(Opcodes.INEG); super.visitInsn(Opcodes.DUP); super.visitInsn(Opcodes.POP); // super.visitJumpInsn(Opcodes.GOTO, l); super.visitLabel(l);
                    l = new Label();
                    super.visitInsn(Opcodes.INEG); super.visitInsn(Opcodes.DUP); super.visitInsn(Opcodes.POP); // super.visitJumpInsn(Opcodes.GOTO, l); super.visitLabel(l);
                }
            }
        };

        return super.visitMethod(access, name, descriptor, signature, exceptions);
    }
}
class StringNumberObfVisitorFFFFF extends ObfVisitor {

    public final Label tempLabel = new Label();
    public Map<String, Integer> arrayIndex = new HashMap<String, Integer>();
    // public final Label whileEndLabel = new Label();
    public final String fieldName1;
    public final String fieldName1Desc = "[Ljava/lang/Object;";

    public final String methodName1 = Main.DIYRemapStringsFile == null ? Utils.spawnRandomChar(20, false) : Utils.getRandomMember(Main.remapStrings);
    public final String methodName1Desc = "()V";

    MethodVisitor arrayIniter = null;

    private boolean startedFlag = false;

    public MethodVisitor clinitmv;
    private boolean hasClinitMethod;

    // private boolean isEnd;

    public List<JunkLabelType> invaildLabel = new ArrayList<>();

    public boolean shouldObfStrT() {
        int acc = cr.getAccess();
        return (acc|Opcodes.ACC_INTERFACE)!=acc && Main.useStringObfT && shouldObf();

    }

    public StringNumberObfVisitorFFFFF(ClassReader cr, ClassWriter1 cw, int api) {
        super(cr,cw,api);
        fieldName1 = Main.DIYRemapStringsFile == null ? Utils.spawnRandomChar(20, false) : Utils.getRandomMember(Main.remapStrings);
    }

    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        // TODO 自动生成的方法存根
        return super.visitField(access, name, descriptor, signature, value);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        // TODO 自动生成的方法存根
        if((access | Opcodes.ACC_SYNTHETIC) == access || (access | Opcodes.ACC_ENUM) == access) isEx = true;
        if((version <= 51 && version != -65536 && Main.fixVersion) && !isEx && isIn) version = 52;
        super.visit(version, access, name, signature, superName, interfaces);
        if(shouldObfStrT()) {

            ClassReader reader = new ClassReader(cr.b);
            reader.accept(new ClassVisitor(Main.原批诶批爱) {
                @Override
                public MethodVisitor visitMethod(int access, String name, String descriptor, String signature,
                                                 String[] exceptions) {
                    // TODO 自动生成的方法存根
                    return new MethodVisitor(Main.原批诶批爱, super.visitMethod(access, name, descriptor, signature, exceptions)) {
                        @Override
                        public void visitLdcInsn(Object value) {
                            // TODO 自动生成的方法存根
                            if(value instanceof String) {

                                for(int jj = 0;jj < (r.nextBoolean() ? r.nextInt(2) : 0);jj++) {
                                    arrayIndex.put(Utils.spawnRandomChar(r.nextInt(30) + 1, true), arrayIndex.size());
                                }

                                if(!arrayIndex.containsKey(value)) arrayIndex.put(value.toString(), arrayIndex.size());
//
//								for(int jj = 0;jj < (r.nextBoolean() ? r.nextInt(2) : 0);jj++) {
//									arrayIndex.put(Utils.spawnRandomChar(r.nextInt(30) + 1, true), arrayIndex.size());
//								}

                            }
                        }
                    };
                }
            }, 0);

            // visitField(Opcodes.ACC_STATIC | Opcodes.ACC_PRIVATE | (Main.addSyntheticFlag ? Opcodes.ACC_SYNTHETIC : 0), fieldName1, fieldName1Desc, null, null);
            // arrayIniter = StringNumberObfVisitor.this.visitMethod(Opcodes.ACC_PRIVATE | Opcodes.ACC_STATIC | (Main.addSyntheticFlag ? (Opcodes.ACC_SYNTHETIC | Opcodes.ACC_BRIDGE) : 0), methodName1, methodName1Desc, null, null);
            clinitmv = StringNumberObfVisitorFFFFF.this.visitMethod(Opcodes.ACC_STATIC | (Main.addSyntheticFlag ? Opcodes.ACC_SYNTHETIC : 0), "<clinit>", "()V", null, null);
            initArray();
            // clinitmv.visitMethodInsn(Opcodes.INVOKESTATIC, StringNumberObfVisitor.this.cr.getClassName(), methodName1, methodName1Desc, false);
        }
        startedFlag = true;
    }
    //
    //	@Override
    //	public void visitSource(String var1, String var2) {
    //		// TODO 自动生成的方法存根
    //
    //		super.visitSource("TMD可莉炸死你们这群王八蛋.java", var2);
    //	}
    //
    @Override
    public void visitAttribute(Attribute var1) {
        // TODO 自动生成的方法存根
        // System.out.println(var1.type);
        super.visitAttribute(var1);
    }

    void initArray() {

        // TODO 自动生成的方法存根

        // MethodVisitor arrayIniter = null;

        if(!shouldObfStrT()) return;

        // if(clinitmv != null && arrayIniter == null) arrayIniter = StringNumberObfVisitor.this.visitMethod(Opcodes.ACC_PRIVATE | Opcodes.ACC_STATIC | (Main.addSyntheticFlag ? (Opcodes.ACC_SYNTHETIC | Opcodes.ACC_BRIDGE) : 0), methodName1, methodName1Desc, null, null);
        arrayIniter = clinitmv;
        if(arrayIndex.size() != 0) {
            visitField(Opcodes.ACC_PRIVATE | Opcodes.ACC_STATIC | (Main.addSyntheticFlag ? Opcodes.ACC_SYNTHETIC : 0), fieldName1, fieldName1Desc, null, null);

            ArrayList<String> al = new ArrayList<>(arrayIndex.keySet());
            for(int i = 0;i < al.size();i++) {
                String str1 = al.get(i), str2 = al.get(r.nextInt(al.size()));
                if(Objects.equals(str1, str2)) continue;
                int iii1 = arrayIndex.get(str1), iii2 = arrayIndex.get(str2);
                arrayIndex.put(str1, iii2); arrayIndex.put(str2, iii1);
            }
            // if(arrayIniter == null) arrayIniter = StringNumberObfVisitor.this.visitMethod(Opcodes.ACC_PRIVATE | Opcodes.ACC_STATIC | (Main.addSyntheticFlag ? (Opcodes.ACC_SYNTHETIC | Opcodes.ACC_BRIDGE) : 0), methodName1, methodName1Desc, null, null);

            arrayIniter.visitLdcInsn(arrayIndex.size() + 1);
            arrayIniter.visitTypeInsn(Opcodes.ANEWARRAY, "java/lang/Object");
            // mv2.visitInsn(Opcodes.DUP);
            // mv2.visitIntInsn(Opcodes.NEWARRAY, operand);
            for(Entry<String, Integer> entry : arrayIndex.entrySet()) {
                // System.out.println(arrayIndex);
                String s = entry.getKey();

                String className;

                className = Utils.getRandomMember(Main.exceptions).getName();

                char[] chars = s.toCharArray();
                char[] chars2 = className.toCharArray();

                int key = r.nextInt(126) + 1;
                int key3 = 0;

                int key2 = Utils.spawnRandomChar().charAt(0);
                char key2_ = (char) key2;

                char[] newchars = new char[chars.length];

                for(int i = 0;i < chars.length;i++) {
                    // if(i == 0) key2 = chars[i];
                    // else {
                    key2 <<= key2 + (key2 % 4) + 123456789;
                    key3 = (key2 / 9) >> 2;
                    newchars[i] = (char) (chars[i] ^ ((((key * (i+1)) ^ (key2 + key3)) ^ chars2[i % chars2.length]) % 0x10000));
                    // }
                }

                String encodedString = key2_ + new String(newchars);

                Label l = new Label();

                arrayIniter.visitLabel(l);
                arrayIniter.visitLineNumber(key, l);
                // mv2.visitFieldInsn(Opcodes.GETSTATIC, StringNumberObfVisitor.this.cr.getClassName(), fieldName1, fieldName1Desc);
                arrayIniter.visitInsn(Opcodes.DUP);
                arrayIniter.visitLdcInsn(entry.getValue());// mv2.visitLdcInsn(entry.getValue());

                arrayIniter.visitTypeInsn(Opcodes.NEW, className.replace(".", "/"));
                arrayIniter.visitInsn(Opcodes.DUP);
                arrayIniter.visitLdcInsn(encodedString);
                arrayIniter.visitMethodInsn(Opcodes.INVOKESPECIAL, className.replace(".", "/"), "<init>", "(Ljava/lang/String;)V", false);
                // super.visitInsn(Opcodes.ATHROW);

                arrayIniter.visitInsn(Opcodes.AASTORE);

                // System.out.println(arrayIndex);
            }
            // mv2.visitInsn(Opcodes.DUP);
            arrayIniter.visitFieldInsn(Opcodes.PUTSTATIC, StringNumberObfVisitorFFFFF.this.cr.getClassName(), StringNumberObfVisitorFFFFF.this.fieldName1, StringNumberObfVisitorFFFFF.this.fieldName1Desc);
            // mv2.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Throwable", "<init>", "([BLjava/lang/Throwable;)V", false);
            // arrayIniter.visitInsn(Opcodes.RETURN);
            // arrayIniter.visitMaxs(0, 0);
        } else {
            // arrayIniter.visitInsn(Opcodes.RETURN);
            // arrayIniter.visitMaxs(0, 0);
        }
    }

    @Override
    public void visitEnd() {
        // TODO 自动生成的方法存根

        if(!hasClinitMethod && clinitmv != null) {
            clinitmv.visitInsn(Opcodes.RETURN);
            clinitmv.visitMaxs(1, 1);
        }

        super.visitEnd();
    }

    private void visitNumberInt(MethodVisitor mv, int i) {

        if(!Main.useNumberObf) {
            mv.visitLdcInsn(i);
            return;
        }

        int T1_1 = r.nextInt();
        int T1_2 = i ^ T1_1;

        int T2_1 = r.nextInt();
        int T2_2 = T1_1 ^ T2_1;

        mv.visitLdcInsn(T2_1);
        mv.visitLdcInsn(T2_2);
        mv.visitLdcInsn(~T1_2);

        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "XiaoShadiao", "原神启动", "(III)I", false);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature,
                                     String[] exceptions) {
        if(shouldObfStrT() && name.equals("<clinit>") && clinitmv != null) {
            hasClinitMethod = true;
            return clinitmv;
        }
        MethodVisitor temp = visitMethod2(access, name, descriptor, signature, exceptions);

        if(name.equals("<clinit>") && clinitmv == null) {
            clinitmv = temp;
            // initArray();
        }

        return temp;
    }

    private MethodVisitor visitMethod2(int access, String name, String descriptor, String signature,
                                       String[] exceptions) {
        // TODO 自动生成的方法存根
        if(isEx || name.equals("原神启动")/* || name.equals("invoke")*/) return super.visitMethod(access, name, descriptor, signature, exceptions);
        if(isIn) return new MethodVisitor(api, super.visitMethod(access, name, descriptor, signature, exceptions)) {

            {
                if(Main.useStringObf && shouldObfStrT() && name.equals("<clinit>")) {
                    // this.visitMethodInsn(Opcodes.INVOKESTATIC, StringNumberObfVisitor.this.cr.getClassName(), methodName1, methodName1Desc, false);
                    // this.visitInsn(Opcodes.POP);
                }
            }

            @Override
            public void visitLdcInsn(Object value) {

                if(value instanceof String && Main.useStringObf) {
                    try {

                        visitString(value.toString(), true);

                    } catch(Exception e) {
                        throw e;
                    }
                } else if(value instanceof Integer && Main.useNumberObf) {
                    visitNumberInt((Integer) value);
                } else if(value instanceof Long && Main.useNumberObf) {
                    visitNumberLong((Long) value);
                } else super.visitLdcInsn(value);
            }
            //
            //			@Override
            //			public void visitMaxs(int maxStack, int maxLocals) {
            //				// TODO 自动生成的方法存根
            //				super.visitMaxs(maxStack, maxLocals);
            //			}
            @Override
            public void visitInsn(int opcode) {
                Integer in = null;
                Long l = null;

                if(opcode >= 2 && opcode <= 8 && Main.useNumberObf) {
                    in = opcode - 3;
                } else if(opcode >= 9 && opcode <= 10 && Main.useNumberObf) {
                    l = Long.valueOf(opcode - 9);
                }
                if(in != null) {
                    visitNumberInt(in);
                } else if(l != null) {
                    visitNumberLong(l);
                } else super.visitInsn(opcode);
            }

            @Override
            public void visitIntInsn(int opcode, int operand) {

                if((opcode == Opcodes.BIPUSH || opcode == Opcodes.SIPUSH) && Main.useNumberObf) this.visitLdcInsn(operand); else super.visitIntInsn(opcode, operand);

            }

            @Override
            public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                // TODO 自动生成的方法存根
                super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
            }
            //
            //			@Override
            //			public void visitEnd() {
            //				// TODO 自动生成的方法存根
            //				for(Label label : invaildLabel) {
            //					mv.visitLabel(label);
            //					super.visitInsn(Opcodes.ACONST_NULL);
            //					super.visitInsn(Opcodes.ATHROW);
            //				}
            //				super.visitEnd();
            //			}

            @Override
            public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
                // TODO 自动生成的方法存根
                if((opcode == Opcodes.GETFIELD || opcode == Opcodes.GETSTATIC) && Main.useJunkCode) addJunkLabelWhile();
                super.visitFieldInsn(opcode, owner, name, descriptor);
            }

            private Label getRandomWhileLabel() {
                if(invaildLabel.isEmpty()) return null;
                JunkLabelType temp;
                int i = 0;
                while((temp = invaildLabel.get(r.nextInt(invaildLabel.size()))).type != JunkLabelType.Type.WHILE) {
                    i++;
                    if(i > 50) return null;
                }

                return temp.label2;
            }

            @Override
            public void visitTypeInsn(int opcode, String type) {
                // TODO 自动生成的方法存根
                // super.visitTypeInsn(Opcodes.NEW, "pers/XiaoShadiao/NMSLException");
                super.visitTypeInsn(opcode, type);
                if(opcode == Opcodes.NEW) addJunkLabelThrow();

            }

            private void visitNumberLong(long l) {
                long T1_1 = r.nextLong();
                long T1_2 = l ^ T1_1;

                long T2_1 = r.nextLong();
                long T2_2 = T1_1 ^ T2_1;

                super.visitLdcInsn(T2_1);
                super.visitLdcInsn(T2_2);
                super.visitLdcInsn(~T1_2);

                super.visitMethodInsn(Opcodes.INVOKESTATIC, "XiaoShadiao", "原神启动", "(JJJ)J", false);

            }

            private void visitNumberInt(int i) {

                int T1_1 = r.nextInt();
                int T1_2 = i ^ T1_1;

                int T2_1 = r.nextInt();
                int T2_2 = T1_1 ^ T2_1;

                super.visitLdcInsn(T2_1);
                super.visitLdcInsn(T2_2);
                super.visitLdcInsn(~T1_2);

                super.visitMethodInsn(Opcodes.INVOKESTATIC, "XiaoShadiao", "原神启动", "(III)I", false);
            }

            private void visitString(String value, boolean flag) {

                if(!Main.useStringObf) {
                    super.visitLdcInsn(value);
                    return;
                }

                if(shouldObfStrT() && arrayIndex.containsKey(value)) {

                    int arrayII;
//
//					for(int jj = 0;jj < (r.nextBoolean() ? r.nextInt(2) : 0);jj++) {
//						arrayIndex.put(Utils.spawnRandomChar(r.nextInt(30) + 1, true), arrayIndex.size());
//					}
//
                    if(arrayIndex.containsKey(value)) {
                        arrayII = arrayIndex.get(value);
                        // System.out.println(value + " -> " + arrayII);
                    } else {
                        throw new NMSLException(value + " String NOT FOUND, THIS SHOULD NOT HAPPEN!");
                        // arrayIndex.put(value, arrayII = arrayIndex.size());
                    }

                    super.visitFieldInsn(Opcodes.GETSTATIC, StringNumberObfVisitorFFFFF.this.cr.getClassName(), fieldName1, fieldName1Desc);
                    this.visitLdcInsn(arrayII);
                    super.visitInsn(Opcodes.AALOAD);
                    super.visitMethodInsn(Opcodes.INVOKESTATIC, "XiaoShadiao", "原神启动", "(Ljava/lang/Object;)Ljava/lang/String;", false);
//
//					for(int jj = 0;jj < (r.nextBoolean() ? r.nextInt(2) : 0);jj++) {
//						arrayIndex.put(Utils.spawnRandomChar(r.nextInt(30) + 1, true), arrayIndex.size());
//					}

                } else {

                    String value1 = value.toString();
                    String key = Utils.spawnRandomChar(1, false);

                    char keychar = key.charAt(0);
                    char[] chars = value1.toCharArray();
                    for(int i = 0;i < chars.length;i++) {
                        chars[i] = (char) (chars[i] ^ (keychar * (i + 1)));
                    }

                    super.visitLdcInsn(key + new String(chars));
                    super.visitMethodInsn(Opcodes.INVOKESTATIC, "XiaoShadiao", "原神启动", "(Ljava/lang/Object;)Ljava/lang/String;", false);

                }

                if(flag) addJunkLabelThrow();
            }

            @Override
            public void visitMaxs(int maxStack, int maxLocals) {
                // TODO 自动生成的方法存根

                Iterator<JunkLabelType> it = invaildLabel.iterator();

                while(it.hasNext()) {
                    JunkLabelType label = it.next();
                    it.remove();
                    switch(label.type) {
                        case THROW:
                            mv.visitLabel(label.label);


                            super.visitTypeInsn(Opcodes.NEW, "pers/XiaoShadiao/NMSLException");
                            super.visitInsn(Opcodes.DUP);
                            super.visitLdcInsn("\u73a9\u539f\u795e\u73a9\u7684");
                            super.visitMethodInsn(Opcodes.INVOKESPECIAL, "pers/XiaoShadiao/NMSLException", "<init>", "(Ljava/lang/String;)V", false);
                            super.visitInsn(Opcodes.ATHROW);
                            break;

                        // super.visitInsn(Opcodes.ACONST_NULL);


                        case WHILE:
                            mv.visitJumpInsn(Opcodes.GOTO, label.label);
                            mv.visitLabel(label.label2);
                            //System.out.println("Visit While Label " + label.label2.hashCode());
                            Label l = getRandomWhileLabel();
                            if(invaildLabel.size() != 0 && l != null) {
                                mv.visitInsn(Opcodes.ICONST_0);
                                mv.visitJumpInsn(Opcodes.IFNE, l);
                            } else {
                                super.visitTypeInsn(Opcodes.NEW, "pers/XiaoShadiao/NMSLException");
                                super.visitInsn(Opcodes.DUP);
                                super.visitLdcInsn("\u73a9\u539f\u795e\u73a9\u7684");
                                super.visitMethodInsn(Opcodes.INVOKESPECIAL, "pers/XiaoShadiao/NMSLException", "<init>", "(Ljava/lang/String;)V", false);
                                super.visitInsn(Opcodes.ATHROW);
                            }
                            break;
                        default:
                            throw new NMSLException("JunkLabelType -> " + label.type);
                    }

                }
                invaildLabel.clear();

                super.visitTypeInsn(Opcodes.NEW, "pers/XiaoShadiao/NMSLException");
                super.visitInsn(Opcodes.DUP);
                super.visitLdcInsn("\u73a9\u539f\u795e\u73a9\u7684");
                super.visitMethodInsn(Opcodes.INVOKESPECIAL, "pers/XiaoShadiao/NMSLException", "<init>", "(Ljava/lang/String;)V", false);
                super.visitInsn(Opcodes.ATHROW);

                super.visitMaxs(maxStack, maxLocals);
            }

            @SuppressWarnings("unused")
            private void addJunkLabelWhile() {

                if(true) return;

                if(!Main.useJunkCode) return;

                Label labelWhile = new Label();

                mv.visitLabel(labelWhile);
                invaildLabel.add(0, new JunkLabelType(labelWhile, JunkLabelType.Type.WHILE));
            }

            private void addJunkLabelThrow() {

                if(!Main.useJunkCode || !startedFlag) return;

                Label labelIf = new Label();
                int token;
                if(true) {
                    token = r.nextInt();
                    this.visitLdcInsn(token);
                } else {
                    String s = Utils.spawnRandomChar(10, false) + 2;
                    visitString(s, false);
                    super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "hashCode", "()I", false);
                    token = s.hashCode();
                }

                if(token > 0) {
                    // super.visitFrame(Opcodes.F_APPEND, 2, new Object[] {Opcodes.INTEGER, Opcodes.INTEGER}, 0, null);
                    // super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "hashCode", "()I", false);
                    // this.visitLdcInsn(token);
                    this.visitInsn(Opcodes.ICONST_0);
                    Label l = getRandomWhileLabel();

                    if(l != null && r.nextBoolean()) {
                        //System.out.println("While Label " + l.hashCode());
                        super.visitJumpInsn(Opcodes.IF_ICMPGE, l);
                    } else {
                        super.visitJumpInsn(Opcodes.IF_ICMPGE, labelIf);

                        super.visitTypeInsn(Opcodes.NEW, "pers/XiaoShadiao/NMSLException");
                        super.visitInsn(Opcodes.DUP);
                        super.visitLdcInsn("\u73a9\u539f\u795e\u73a9\u7684");
                        super.visitMethodInsn(Opcodes.INVOKESPECIAL, "pers/XiaoShadiao/NMSLException", "<init>", "(Ljava/lang/String;)V", false);

                        // super.visitInsn(Opcodes.ACONST_NULL);
                        super.visitInsn(Opcodes.ATHROW);
                        mv.visitLabel(labelIf);
                    }
                } else if(token < 0) {
                    // super.visitFrame(Opcodes.F_APPEND, 2, new Object[] {Opcodes.INTEGER, Opcodes.INTEGER}, 0, null);
                    // super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "hashCode", "()I", false);
                    // this.visitLdcInsn(token);
                    this.visitInsn(Opcodes.ICONST_0);
                    Label l = getRandomWhileLabel();

                    if(l != null && r.nextBoolean()) {
                        //System.out.println("While Label " + l.hashCode());
                        super.visitJumpInsn(Opcodes.IF_ICMPGE, l);
                    } else {
                        super.visitJumpInsn(Opcodes.IF_ICMPGE, labelIf);
                        invaildLabel.add(0, new JunkLabelType(labelIf, JunkLabelType.Type.THROW));
                    }
                    //					super.visitInsn(Opcodes.ACONST_NULL);
                    //					super.visitInsn(Opcodes.ATHROW);
                } else if(token == 0) {
                    // super.visitFrame(Opcodes.F_APPEND, 2, new Object[] {Opcodes.INTEGER, Opcodes.INTEGER}, 0, null);
                    // super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "hashCode", "()I", false);
                    // this.visitLdcInsn(token);
                    this.visitInsn(Opcodes.ICONST_0);
                    Label l = getRandomWhileLabel();

                    if(l != null && r.nextBoolean()) {
                        //System.out.println("While Label " + l.hashCode());
                        super.visitJumpInsn(Opcodes.IF_ICMPGE, l);
                    } else {
                        super.visitJumpInsn(Opcodes.IF_ICMPEQ, labelIf);
                        // super.visitInsn(Opcodes.ACONST_NULL);
                        super.visitTypeInsn(Opcodes.NEW, "pers/XiaoShadiao/NMSLException");
                        super.visitInsn(Opcodes.DUP);
                        super.visitLdcInsn("\u73a9\u539f\u795e\u73a9\u7684");
                        super.visitMethodInsn(Opcodes.INVOKESPECIAL, "pers/XiaoShadiao/NMSLException", "<init>", "(Ljava/lang/String;)V", false);

                        super.visitInsn(Opcodes.ATHROW);
                        mv.visitLabel(labelIf);
                    }
                }

            }

        };
        return super.visitMethod(access, name, descriptor, signature, exceptions);

    }

}

class LocalVarObfVisitor extends ObfVisitor {

    public LocalVarObfVisitor(ClassReader cr, ClassWriter1 cw, int api) {
        super(cr, cw, api);
        // TODO 自动生成的构造函数存根
    }

    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature,
                                     String[] exceptions) {
        // TODO 自动生成的方法存根
        if(isEx) return super.visitMethod(access, name, descriptor, signature, exceptions);
        if(isIn) return new MethodVisitor(api, super.visitMethod(access, name, descriptor, signature, exceptions)) {

            private HashSet<String> usedNames = new HashSet<String>();

            @Override
            public void visitLocalVariable(String name, String descriptor, String signature, Label start, Label end,
                                           int index) {
                // TODO 自动生成的方法存根
                if(Main.delLocalVar && !Main.obfLocalVar) return;
                if(Main.obfLocalVar) {
                    int tries = 0;
                    do {
                        name = Utils.getRandomMember(Main.remapStrings);
                        for(int i = 0;i < tries / 50;i++) {
                            name += ("_" + Utils.getRandomMember(Main.remapStrings));
                        }
                        tries++;
                    } while(usedNames.contains(name));
                    usedNames.add(name);
                }

                super.visitLocalVariable(name, descriptor, signature, start, end, index);
            }

        };

        return super.visitMethod(access, name, descriptor, signature, exceptions);
    }
}

class SyntheticBridgeApplyerObfVisitor extends ObfVisitor {

    public static final String[][] joking = new String[][] {
            new String[] {"啊哦", "这儿好像啥也没有呢", "去别的地方看看吧"},
            new String[] {"哎呀", "小沙雕刚把代码吃完就被你发现了"},
            new String[] {"你要找的东西被小沙雕吃掉了", "对了你玩原神吗", "小沙雕的UID是263087074"},
            // new String[] {"这里被小小可莉炸成了一片废墟"},
            new String[] {"小沙雕到此一游", "小沙雕的闲聊群1103539591"}
    };

    public SyntheticBridgeApplyerObfVisitor(ClassReader cr, ClassWriter1 cw, int api) {
        super(cr, cw, api);
        // TODO 自动生成的构造函数存根
    }

    private int classAccess;

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        // TODO 自动生成的方法存根
        if(shouldObf()) {
            String[] temp = joking[r.nextInt(joking.length)];
            for(String temp2 : temp) {
                // super.visitField(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL | Opcodes.ACC_STATIC, "啊哦", "Lpers/XiaoShadiao/NMSLException;", null, null);
                // super.visitField(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL | Opcodes.ACC_STATIC, "这儿好像啥也没有呢", "Lpers/XiaoShadiao/NMSLException;", null, null);
                super.visitField(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL | Opcodes.ACC_STATIC, temp2, "Lpers/XiaoShadiao/NMSLException;", null, null);

            }
        }
        super.visit(version, classAccess = access, name, signature, superName, interfaces);
    }

    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        // TODO 自动生成的方法存根
        return super.visitField(access | (!shouldObf() ? 0 : Opcodes.ACC_SYNTHETIC), name, descriptor, signature, value);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature,
                                     String[] exceptions) {
        // TODO 自动生成的方法存根
        return super.visitMethod(access | (!shouldObf() || ((Opcodes.ACC_ANNOTATION & classAccess) != 0) ? 0 : (Opcodes.ACC_SYNTHETIC | (name.contains("init>") ? 0 : Opcodes.ACC_BRIDGE))), name, descriptor, signature, exceptions);
    }

    @Override
    public boolean shouldObf() {
        // TODO 自动生成的方法存根
        return super.shouldObf() && Main.addSyntheticFlag;
    }

}

class YuShengJunObfVisitor extends ObfVisitor {

    public static final NameAndDesc[] NAME_AND_DESCS = new NameAndDesc[] {
            new NameAndDesc("hashCode", "()I"),
            new NameAndDesc("<init>", "()V"),
            new NameAndDesc("equals", "(Ljava/lang/Object;)Z"),
            new NameAndDesc("toString", "()Ljava/lang/String;")
    };

    public boolean shouldChange = false;
    public YuShengJunObfVisitor(ClassReader cr, ClassWriter1 cw, int api) {
        super(cr, cw, api);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        if(shouldObf() && superName.equals("java/lang/Object")) {
            superName = "com/mayikt/yushengjun/Object";
            shouldChange = true;
        }
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        if(!shouldChange) return super.visitMethod(access, name, descriptor, signature, exceptions);

        NameAndDesc nd = Arrays.stream(NAME_AND_DESCS).filter(nameAndDesc -> nameAndDesc.name.equals(name) && ("<init>".equals(name) || nameAndDesc.desc.equals(descriptor))).findFirst().orElse(null);
        if(nd == null) return super.visitMethod(access, name, descriptor, signature, exceptions);

        return new MethodVisitor(api, super.visitMethod(access, name, descriptor, signature, exceptions)) {
            @Override
            public void visitMethodInsn(int opcode, String owner, String name, String descriptor) {
                this.visitMethodInsn(opcode, owner, name, descriptor, false);
            }
            @Override
            public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                if ((!"<init>".equals(name) || opcode == Opcodes.INVOKESPECIAL) && (
                        name.equals(nd.name) && ("<init>".equals(name) || descriptor.equals(nd.desc))
//								|| name.equals("<init>") && descriptor.equals("()V")
//								|| name.equals("equals") && descriptor.equals("(Ljava/lang/Object;)Z")
//								|| name.equals("toString") && descriptor.equals("()Ljava/lang/String;")
                )
                        && owner.equals("java/lang/Object")) {
                    owner = "com/mayikt/yushengjun/Object";
                }

                super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
            }

        };

    }

    public static class NameAndDesc {
        public String name;
        public String desc;
        public NameAndDesc(String name, String desc) {
            this.name = name;
            this.desc = desc;
        }
    }
}

class XiaoShadiaoStrReplacerObfVisitor extends ObfVisitor {
    public XiaoShadiaoStrReplacerObfVisitor(ClassReader cr, ClassWriter1 cw, int api) {
        super(cr, cw, api);
    }
    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        if (isIn && !isEx) return new MethodVisitor(api, super.visitMethod(access, name, descriptor, signature, exceptions)) {
            @Override
            public void visitLdcInsn(Object value) {
                if(value instanceof String) {
                    int i = -1;
                    switch(value.toString()) {
                        case "代替换文本1":
                            i = 0;
                            break;
                        case "代替换文本2":
                            i = 1;
                            break;
                        case "代替换文本3":
                            i = 2;
                            break;
                    }
                    if(i != -1) super.visitLdcInsn(Main.xiaoshadiaoStrs[i]);
                    else super.visitLdcInsn(value);
                } else super.visitLdcInsn(value);
            }
        };

        return super.visitMethod(access, name, descriptor, signature, exceptions);
    }
}
class SuperJunkCodeObfVisitor extends ObfVisitor {

    private Map<String, String> map = new HashMap<>();
    private List<String> strings = new ArrayList<>();

    boolean isInterface;
    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        isInterface = (access & Opcodes.ACC_INTERFACE) != 0;
    }

    public SuperJunkCodeObfVisitor(ClassReader cr, ClassWriter1 cw, int api) {
        super(cr, cw, api);

        ClassReader reader = new ClassReader(cr.b);
        reader.accept(new ClassVisitor(Main.原批诶批爱) {

            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature,
                                             String[] exceptions) {
                // TODO 自动生成的方法存根
                return new MethodVisitor(Main.原批诶批爱, super.visitMethod(access, name, descriptor, signature, exceptions)) {

                    @Override
                    public void visitLdcInsn(Object value) {
                        if(value instanceof String) {
                            if(!strings.contains(value)) strings.add(value.toString());
                        }
                        super.visitLdcInsn(value);
                    }
                };
            }
        }, 0);
    }

    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        if((access | Opcodes.ACC_STATIC) != access) map.put(descriptor, name);
        return super.visitField(access, name, descriptor, signature, value);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        if(isIn && !isEx && isInterface) return new MethodVisitor(api, super.visitMethod(access, name, descriptor, signature, exceptions)) {

            private List<Label> junkThrowLabel = new ArrayList<>();
            // private List<Label> junkThrowLabel = new ArrayList<>();
            private final Label endL = new Label();
            private boolean isStatic, shouldObf;
            {
                if((access | Opcodes.ACC_STATIC) == access) isStatic = true;
                shouldObf = !name.contains("init>");
            }

            @Override
            public void visitCode() {
                // addJunkThrow();
                super.visitCode();
            }

            @Override
            public void visitLdcInsn(Object value) {

                if(value instanceof String) {
                    visitString(value.toString(), true);
                } else if(value instanceof Integer) {
                    visitNumberInt((Integer) value);
                } else if(value instanceof Long) {
                    visitNumberLong((Long) value);
                } else super.visitLdcInsn(value);
            }

            private void visitNumberLong(Long value) {

                if(!shouldObf) {
                    mv.visitLdcInsn(value);
                    return;
                }

                int str1 = r.nextInt(3);

                boolean flag = r.nextBoolean();

                int str2 = flag ? (str1 == 0 || str1 == 1 ? r.nextInt(2) : 2) : (str1 == 0 || str1 == 1 ? 2 : r.nextInt(2));

                super.visitFieldInsn(Opcodes.GETSTATIC, "XiaoShadiao", "xsd_" + (str1 + 1), "Ljava/lang/String;");
                super.visitFieldInsn(Opcodes.GETSTATIC, "XiaoShadiao", "xsd_" + (str2 + 1), "Ljava/lang/String;");

//				super.visitMethodInsn(Opcodes.INVOKESTATIC, "XiaoShadiao", "getXSDString" + (str1 + 1), "()Ljava/lang/String;", false);
//				super.visitMethodInsn(Opcodes.INVOKESTATIC, "XiaoShadiao", "getXSDString" + (str1 + 1), "()Ljava/lang/String;", false);

                super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "equals", "(Ljava/lang/Object;)Z", false);

                Label labelIf = new Label();
                Label l = new Label();
                Runnable ornginal = () -> mv.visitLdcInsn(value), obf = () -> {
                    if(r.nextBoolean()) {
                        mv.visitLdcInsn(r.nextLong());
                    } else {
                        Entry<String, String> entry = (Entry) Utils.getRandomMember(map.entrySet().stream().filter(a -> a.getKey().equals("J")).toArray());
                        if(entry != null && !isStatic) {
                            mv.visitVarInsn(Opcodes.ALOAD, 0);
                            mv.visitFieldInsn(Opcodes.GETFIELD, cr.getClassName().replace(".","/"), entry.getValue(), entry.getKey());
                        } else mv.visitLdcInsn(r.nextLong());

                        super.visitTypeInsn(Opcodes.NEW, "pers/XiaoShadiao/NMSLException");
                        super.visitInsn(Opcodes.DUP);
                        super.visitLdcInsn("玩原神玩的");
                        super.visitMethodInsn(Opcodes.INVOKESPECIAL, "pers/XiaoShadiao/NMSLException", "<init>", "(Ljava/lang/String;)V", false);
                        super.visitInsn(Opcodes.ATHROW);
                    }
                };
                super.visitJumpInsn(Opcodes.IFEQ, labelIf);

                if(flag) {
                    ornginal.run();
                } else {
                    obf.run();
                }

                super.visitJumpInsn(Opcodes.GOTO, l);
                mv.visitLabel(labelIf);

                if(!flag) {
                    ornginal.run();
                } else {
                    obf.run();
                }
                mv.visitLabel(l);
            }

            private void visitNumberInt(Integer value) {

                if(!shouldObf || (strings.size() > 40) && Main.bigBrainNumberObf) {
                    mv.visitLdcInsn(value);
                    return;
                }
                int str1 = r.nextInt(3);

                boolean flag = r.nextBoolean();

                int str2 = flag ? (str1 == 0 || str1 == 1 ? r.nextInt(2) : 2) : (str1 == 0 || str1 == 1 ? 2 : r.nextInt(2));

                super.visitFieldInsn(Opcodes.GETSTATIC, "XiaoShadiao", "xsd_" + (str1 + 1), "Ljava/lang/String;");
                super.visitFieldInsn(Opcodes.GETSTATIC, "XiaoShadiao", "xsd_" + (str2 + 1), "Ljava/lang/String;");

//				super.visitMethodInsn(Opcodes.INVOKESTATIC, "XiaoShadiao", "getXSDString" + (str1 + 1), "()Ljava/lang/String;", false);
//				super.visitMethodInsn(Opcodes.INVOKESTATIC, "XiaoShadiao", "getXSDString" + (str1 + 1), "()Ljava/lang/String;", false);

                super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "equals", "(Ljava/lang/Object;)Z", false);

                Label labelIf = new Label();
                Label l = new Label();
                Runnable ornginal = () -> mv.visitLdcInsn(value), obf = () -> {
                    if(r.nextBoolean()) {
                        if(r.nextBoolean() && !junkThrowLabel.isEmpty()) {
                            super.visitJumpInsn(Opcodes.GOTO, (Label) Utils.getRandomMember(junkThrowLabel.toArray()));
                        } else super.visitLdcInsn(r.nextInt());
                    } else {
                        Entry<String, String> entry = (Entry) Utils.getRandomMember(map.entrySet().stream().filter(a -> a.getKey().equals("I")).toArray());
                        if(entry != null && !isStatic) {
                            mv.visitVarInsn(Opcodes.ALOAD, 0);
                            super.visitFieldInsn(Opcodes.GETFIELD, cr.getClassName(), entry.getValue(), "I");
                        } else super.visitLdcInsn(r.nextInt());

                        super.visitTypeInsn(Opcodes.NEW, "pers/XiaoShadiao/NMSLException");
                        super.visitInsn(Opcodes.DUP);
                        super.visitLdcInsn("玩原神玩的");
                        super.visitMethodInsn(Opcodes.INVOKESPECIAL, "pers/XiaoShadiao/NMSLException", "<init>", "(Ljava/lang/String;)V", false);
                        super.visitInsn(Opcodes.ATHROW);
                    }
                };
                super.visitJumpInsn(Opcodes.IFEQ, labelIf);
                if(flag) {
                    ornginal.run();
                } else {
                    obf.run();
                }

                super.visitJumpInsn(Opcodes.GOTO, l);
                super.visitLabel(labelIf);
                if(!flag) {
                    ornginal.run();
                } else {
                    obf.run();
                }
                super.visitLabel(l);
//
//				super.visitJumpInsn(Opcodes.IFNE, labelIf);
//				mv.visitLdcInsn(value);
//				super.visitJumpInsn(Opcodes.GOTO, l);
//				mv.visitLabel(labelIf);
//				if(r.nextBoolean()) {
//					mv.visitLdcInsn(r.nextInt());
//				} else {
//					Entry<String, String> entry = (Entry) Utils.getRandomMember(map.entrySet().stream().filter(a -> a.getKey().equals("I")).toArray());
//					if(entry != null && !isStatic) {
//						mv.visitFieldInsn(Opcodes.GETFIELD, cr.getClassName(), entry.getValue(), entry.getKey());
//					} else mv.visitLdcInsn(r.nextInt());
//				}
//				mv.visitLabel(l);
            }

            private void visitString(String string, boolean b) {

                if(!shouldObf || ((strings.size() > 40) && Main.bigBrainNumberObf)) {
                    mv.visitLdcInsn(string);
                    return;
                }
                int str1 = r.nextInt(3);

                boolean flag = r.nextBoolean();

                int str2 = flag ? (str1 == 0 || str1 == 1 ? r.nextInt(2) : 2) : (str1 == 0 || str1 == 1 ? 2 : r.nextInt(2));

//				super.visitLdcInsn(Main.xiaoshadiaoStrs[str1]);
//				super.visitMethodInsn(Opcodes.INVOKESTATIC, "XiaoShadiao", "test", "(Ljava/lang/String;)Ljava/lang/String;", false);
//				super.visitLdcInsn(Main.xiaoshadiaoStrs[str2]);
//				super.visitMethodInsn(Opcodes.INVOKESTATIC, "XiaoShadiao", "test", "(Ljava/lang/String;)Ljava/lang/String;", false);
                super.visitFieldInsn(Opcodes.GETSTATIC, "XiaoShadiao", "xsd_" + (str1 + 1), "Ljava/lang/String;");
                super.visitFieldInsn(Opcodes.GETSTATIC, "XiaoShadiao", "xsd_" + (str2 + 1), "Ljava/lang/String;");

//				super.visitMethodInsn(Opcodes.INVOKESTATIC, "XiaoShadiao", "getXSDString" + (str1 + 1), "()Ljava/lang/String;", false);
//				super.visitMethodInsn(Opcodes.INVOKESTATIC, "XiaoShadiao", "getXSDString" + (str1 + 1), "()Ljava/lang/String;", false);

                super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "equals", "(Ljava/lang/Object;)Z", false);

                String[] strs = strings.toArray(new String[0]);
                Label labelIf = new Label();
                Label l = new Label();
                Runnable ornginal = () -> mv.visitLdcInsn(string), obf = () -> {
                    if(r.nextBoolean()) {
						/*if(r.nextBoolean() && !junkThrowLabel.isEmpty()) {
							super.visitJumpInsn(Opcodes.GOTO, (Label) Utils.getRandomMember(junkThrowLabel.toArray()));
						} else */super.visitLdcInsn(Utils.getRandomMember(strs));
                    } else {
                        Entry<String, String> entry = (Entry) Utils.getRandomMember(map.entrySet().stream().filter(a -> a.getKey().equals("Ljava/lang/String;")).toArray());
                        if(entry != null && !isStatic) {
                            mv.visitVarInsn(Opcodes.ALOAD, 0);
                            super.visitFieldInsn(Opcodes.GETFIELD, cr.getClassName(), entry.getValue(), "Ljava/lang/String;");
                        } else super.visitLdcInsn(Utils.getRandomMember(strs));

                        super.visitTypeInsn(Opcodes.NEW, "pers/XiaoShadiao/NMSLException");
                        super.visitInsn(Opcodes.DUP);
                        super.visitLdcInsn("玩原神玩的");
                        super.visitMethodInsn(Opcodes.INVOKESPECIAL, "pers/XiaoShadiao/NMSLException", "<init>", "(Ljava/lang/String;)V", false);
                        super.visitInsn(Opcodes.ATHROW);
                    }
                    // super.visitLdcInsn(Utils.getRandomMember(strs));
                };
                super.visitJumpInsn(Opcodes.IFEQ, labelIf);
                if(flag) {
                    ornginal.run();
                } else {
                    obf.run();
                }

                super.visitJumpInsn(Opcodes.GOTO, l);
                super.visitLabel(labelIf);
                if(!flag) {
                    ornginal.run();
                } else {
                    obf.run();
                }

                super.visitLabel(l);
            }

            //
            //			@Override
            //			public void visitMaxs(int maxStack, int maxLocals) {
            //				// TODO 自动生成的方法存根
            //				super.visitMaxs(maxStack, maxLocals);
            //			}
            @Override
            public void visitInsn(int opcode) {
                Integer in = null;
                Long l = null;

                // if(r.nextInt(7) == 5) addJunkThrow();

                if(opcode >= 2 && opcode <= 8) {
                    in = opcode - 3;
                } else if(opcode >= 9 && opcode <= 10) {
                    l = Long.valueOf(opcode - 9);
                }
                List<Integer> intOpcodes;
                if(in != null) {
                    visitNumberInt(in);
                } else if(l != null) {
                    visitNumberLong(l);
                } else if((intOpcodes = Arrays.asList(Opcodes.IADD, Opcodes.ISUB, Opcodes.IMUL, Opcodes.IDIV, Opcodes.IREM)).contains(opcode) && ((r.nextInt(10) == 5) || !Main.bigBrainNumberObf)) {
                    int str1 = r.nextInt(3);

                    boolean flag = r.nextBoolean();

                    int str2 = flag ? (str1 == 0 || str1 == 1 ? r.nextInt(2) : 2) : (str1 == 0 || str1 == 1 ? 2 : r.nextInt(2));

                    super.visitFieldInsn(Opcodes.GETSTATIC, "XiaoShadiao", "xsd_" + (str1 + 1), "Ljava/lang/String;");
                    super.visitFieldInsn(Opcodes.GETSTATIC, "XiaoShadiao", "xsd_" + (str2 + 1), "Ljava/lang/String;");

                    super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "equals", "(Ljava/lang/Object;)Z", false);

                    Label labelIf = new Label();
                    Label l2 = new Label();
                    Runnable ornginal = () -> super.visitInsn(opcode), obf = () -> super.visitInsn((Integer) Utils.getRandomMember(intOpcodes.toArray()));
                    super.visitJumpInsn(Opcodes.IFEQ, labelIf);
                    if(flag) {
                        ornginal.run();
                    } else {
                        obf.run();
                    }

                    super.visitJumpInsn(Opcodes.GOTO, l2);
                    super.visitLabel(labelIf);
                    if(!flag) {
                        ornginal.run();
                    } else {
                        obf.run();
                    }
                    super.visitLabel(l2);
                } else super.visitInsn(opcode);

            }

            @Override
            public void visitMaxs(int maxStack, int maxLocals) {
                super.visitLabel(endL);
                super.visitMaxs(maxStack, maxLocals);
            }

            @Override
            public void visitIincInsn(int varIndex, int increment) {

                int str1 = r.nextInt(3);

                boolean flag = r.nextBoolean();

                int str2 = flag ? (str1 == 0 || str1 == 1 ? r.nextInt(2) : 2) : (str1 == 0 || str1 == 1 ? 2 : r.nextInt(2));

                super.visitFieldInsn(Opcodes.GETSTATIC, "XiaoShadiao", "xsd_" + (str1 + 1), "Ljava/lang/String;");
                super.visitFieldInsn(Opcodes.GETSTATIC, "XiaoShadiao", "xsd_" + (str2 + 1), "Ljava/lang/String;");

//				super.visitMethodInsn(Opcodes.INVOKESTATIC, "XiaoShadiao", "getXSDString" + (str1 + 1), "()Ljava/lang/String;", false);
//				super.visitMethodInsn(Opcodes.INVOKESTATIC, "XiaoShadiao", "getXSDString" + (str1 + 1), "()Ljava/lang/String;", false);

                super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "equals", "(Ljava/lang/Object;)Z", false);

                Label labelIf = new Label();
                Label l = new Label();
                Runnable ornginal = () -> super.visitIincInsn(varIndex, increment), obf = () -> super.visitIincInsn(varIndex, -increment);
                super.visitJumpInsn(Opcodes.IFEQ, labelIf);
                if(flag) {
                    ornginal.run();
                } else {
                    obf.run();
                }

                super.visitJumpInsn(Opcodes.GOTO, l);
                super.visitLabel(labelIf);
                if(!flag) {
                    ornginal.run();
                } else {
                    obf.run();
                }
                super.visitLabel(l);
            }

            @Override
            public void visitIntInsn(int opcode, int operand) {

                if((opcode == Opcodes.BIPUSH || opcode == Opcodes.SIPUSH)) this.visitLdcInsn(operand); else super.visitIntInsn(opcode, operand);

            }

            private void addJunkThrow() {

                Label templ = new Label(), l = new Label();

                junkThrowLabel.add(l);
                super.visitJumpInsn(Opcodes.GOTO, templ);
                super.visitLabel(l);

                super.visitFrame(Opcodes.F_NEW, 0, new Object[] {}, 1, new Object[] {"java/lang/Throwable"});
                super.visitTypeInsn(Opcodes.NEW, "pers/XiaoShadiao/NMSLException");
                super.visitInsn(Opcodes.DUP);
                super.visitLdcInsn("玩崩铁玩的");
                super.visitMethodInsn(Opcodes.INVOKESPECIAL, "pers/XiaoShadiao/NMSLException", "<init>", "(Ljava/lang/String;)V", false);
                super.visitInsn(Opcodes.ATHROW);

                super.visitJumpInsn(Opcodes.GOTO, endL);

                super.visitLabel(templ);

            }
        };
        return super.visitMethod(access, name, descriptor, signature, exceptions);
    }


}
class JunkLabelType {

    public final Label label;
    public final Type type;
    public final Label label2 = new Label();

    public JunkLabelType(Label label, Type type) {
        this.label = label;
        this.type = type;
    }

    public enum Type {
        WHILE,
        THROW
    }

}

abstract class ObfVisitor extends ClassVisitor {

    public Random r = new Random();
    public ClassReader cr;

    // public final Label whileEndLabel = new Label();

    public boolean flag = true;

    public boolean
            isEx,
            isIn,
            isXiaoShadiao;

    public List<Label> invaildLabel = new ArrayList<Label>();

    public ObfVisitor(ClassReader cr, ClassWriter1 cw,int api) {
        super(api,cw);
        this.cr = cr;

        for(String in : Main.includeClass) {
            boolean xsd = cr.getClassName().startsWith("XiaoShadiao") || cr.getClassName().startsWith("ShaXiaodiao");
            if(xsd) isXiaoShadiao = true;
            if(cr.getClassName().matches(in) || xsd) {
                isIn = true;
                break;
            }
        }
        for(String ex : Main.excludeClass) {
            if(cr.getClassName().matches(ex)) {
                isEx = true;
                break;
            }
        }
    }
    public ObfVisitor(ClassReader cr) {
        super(Opcodes.ASM5, new ClassWriter(0));
        this.cr = cr;

        for(String in : Main.includeClass) {
            boolean xsd = cr.getClassName().startsWith("XiaoShadiao");
            if(xsd) isXiaoShadiao = true;
            if(cr.getClassName().matches(in) || xsd) {
                isIn = true;
                break;
            }
        }
        for(String ex : Main.excludeClass) {
            if(cr.getClassName().matches(ex)) {
                isEx = true;
                return;
            }
        }
    }

    public boolean shouldObf() {
        return (isIn && !isEx) || isXiaoShadiao;
    }

}

class ObfClassNode extends ClassNode {

    public Random r = new Random();
    public ClassReader cr;

    // public final Label whileEndLabel = new Label();

    public boolean flag = true;

    public boolean isEx, isIn;

    public List<Label> invaildLabel = new ArrayList<Label>();

    public ObfClassNode(ClassReader cr) {
        super();
        this.cr = cr;

        for(String in : Main.includeClass) {
            if(cr.getClassName().matches(in) || cr.getClassName().startsWith("XiaoShadiao")) {
                isIn = true;
                break;
            }
        }
        for(String ex : Main.excludeClass) {
            if(cr.getClassName().matches(ex)) {
                isEx = true;
                return;
            }
        }
    }

    public boolean shouldObf() {
        return (isIn && !isEx) || cr.getClassName().startsWith("XiaoShadiao");
    }

}

class MyCL extends ClassLoader {
    public Class<?> define(byte[] b) {
        return super.defineClass(b, 0, b.length);
    }
}

class More2JunkCodeObfVisitor extends ObfVisitor {

    public More2JunkCodeObfVisitor(ClassReader cr, ClassWriter1 cw, int api) {
        super(cr, cw, api);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        if(isEx || name.contains("init>")) return super.visitMethod(access, name, descriptor, signature, exceptions);
        if(isIn) return new MethodVisitor(api, super.visitMethod(access, name, descriptor, signature, exceptions)) {

            private final Label constant = new Label();
            private final Map<Label, Long> accessSwitchToken = new HashMap<>();
            private final Map<Label, Integer> key = new HashMap<>();
            private final Map<Label, Label> gotoLabel = new HashMap<>();
            private final Map<Label, Label> gotoLabelThrow = new HashMap<>();

            private int maxVarIndex = 0;

            @Override
            public void visitCode() {
                ClassReader reader = new ClassReader(cr.b);
                reader.accept(new ClassVisitor(Main.原批诶批爱) {
                    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                        return new MethodVisitor(Main.原批诶批爱, super.visitMethod(access, name, descriptor, signature, exceptions)) {
                            @Override
                            public void visitVarInsn(int opcode, int varIndex) {
                                // super.visitVarInsn(opcode, varIndex);
                                maxVarIndex = Math.max(maxVarIndex, varIndex);
                            }
                        };
                    }
                }, 0);

                super.visitCode();
            }

            @Override
            public void visitInsn(int opcode) {
                Integer in = null;
//
//				if(Arrays.asList(Opcodes.IADD, Opcodes.ISUB, Opcodes.IMUL, Opcodes.IDIV, Opcodes.IREM).contains(opcode)) {
//					if(randomFlag++ % 8 == 0) addJunkLabelThrow();
//				}

                if(opcode >= 2 && opcode <= 8) {
                    in = opcode - 3;
                }
                if(in != null) {
                    visitNumberInt(in);
                } else super.visitInsn(opcode);

            }

            private void visitNumberInt(Integer in) {

                int key = r.nextInt();
                long accessSwitchToken = r.nextLong();
                Label jumpBack = new Label();
                this.key.put(jumpBack, key);
                this.accessSwitchToken.put(jumpBack, accessSwitchToken);

                super.visitLdcInsn(in ^ key);

                Label gotoL = new Label();
                this.gotoLabel.put(jumpBack, gotoL);
                super.visitJumpInsn(Opcodes.GOTO, gotoL);
                super.visitLabel(jumpBack);

                this.gotoLabelThrow.put(jumpBack, new Label());
            }

            @Override
            public void visitLdcInsn(Object value) {
                if(value instanceof Integer) {
                    visitNumberInt((Integer) value);
                } else super.visitLdcInsn(value);
            }

            @Override
            public void visitMaxs(int maxStack, int maxLocals) {

                for(Label jumpLabel : this.accessSwitchToken.keySet().toArray(new Label[0])) {

                    super.visitLabel(this.gotoLabel.get(jumpLabel));

                    super.visitLdcInsn(Type.getType("LXiaoShadiao;"));
                    // super.visitInsn(Opcodes.POP);
                    super.visitJumpInsn(Opcodes.IFNULL, this.gotoLabelThrow.get(jumpLabel));

                    super.visitLdcInsn(this.key.get(jumpLabel));

                    super.visitInsn(Opcodes.IXOR);
                    super.visitJumpInsn(Opcodes.GOTO, jumpLabel);

                    super.visitLabel(this.gotoLabelThrow.get(jumpLabel));
                    super.visitTypeInsn(Opcodes.NEW, "pers/XiaoShadiao/NMSLException");
                    super.visitInsn(Opcodes.DUP);
                    super.visitLdcInsn("玩原神玩的");
                    super.visitMethodInsn(Opcodes.INVOKESPECIAL, "pers/XiaoShadiao/NMSLException", "<init>", "(Ljava/lang/String;)V", false);

                    super.visitInsn(Opcodes.ATHROW);

                    super.visitJumpInsn(Opcodes.GOTO, this.gotoLabelThrow.get(jumpLabel));
                }


                super.visitMaxs(maxStack, maxLocals);
            }

        };
        return super.visitMethod(access, name, descriptor, signature, exceptions);
    }
}

class JunkTryCatchBlockObfVisitor extends ObfVisitor {

    private boolean inited;
    private final String fieldName = Main.DIYRemapStringsFile == null ? Utils.spawnRandomChar(20, false) : Utils.getRandomMember(Main.remapStrings);
    private final String randKey = Utils.spawnRandomChar(r.nextInt(20) + 30, false);
    private MethodVisitor clinit;

    private List<Label> head = new ArrayList<>();
    private List<Label> tail = new ArrayList<>();

    public JunkTryCatchBlockObfVisitor(ClassReader cr, ClassWriter1 cw, int api) {
        super(cr, cw, api);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = visitMethod2(access, name, descriptor, signature, exceptions);
//		if(name.equals("<clinit>")) {
//			this.clinit = methodVisitor;
//
//			initClinitMethod(name);
//		}

        return methodVisitor;
    }

    public MethodVisitor visitMethod2(int access, String name, String descriptor, String signature, String[] exceptions) {
        // if((access | Opcodes.ACC_SYNTHETIC) == access || (access | Opcodes.ACC_) == access) isEx = true;
        if(isEx || (name.equals("原神启动") && descriptor.equals("(III)I"))) return super.visitMethod(access, name, descriptor, signature, exceptions);
        if(name.contains("init>")) return new MethodVisitor(api, super.visitMethod(access, name, descriptor, signature, exceptions)) {
            @Override
            public void visitCode() {
                if (name.equals("<clinit>")) {
                    clinit = this;
                    initClinitMethod(name);
                }
            }
        };

        if(isIn) return new MethodVisitor(api, super.visitMethod(access, name, descriptor, signature, exceptions)) {

            private Label labelThrow = new Label();
            private Label labelThrow2 = new Label();
            private Label labelThrowHandler2 = new Label();
            private Label labelThrowHandler = new Label();

            private Label label = new Label();
            private Label handler = new Label();

            @Override
            public void visitCode() {
                if(name.equals("<clinit>")) {
                    clinit = this;
                    initClinitMethod(name);
                }

                if(!name.equals("<clinit>") && !name.equals("原神启动")) {
                    if(true) if (false)  {
                        // super.visitFieldInsn(Opcodes.GETSTATIC, cr.getClassName(), fieldName, "Lpers/XiaoShadiao/NMSLException;");
                        super.visitJumpInsn(Opcodes.GOTO, labelThrow);
                        super.visitLabel(labelThrowHandler2);
                        super.visitInsn(Opcodes.POP);
                        super.visitLabel(labelThrow);
                        super.visitFieldInsn(Opcodes.GETSTATIC, cr.getClassName(), fieldName, "Lpers/XiaoShadiao/NMSLException;");
                        super.visitInsn(Opcodes.ATHROW);
                        super.visitLabel(labelThrowHandler);
                        super.visitInsn(Opcodes.POP);
                        super.visitLabel(labelThrow2);
                        super.visitTryCatchBlock(labelThrow, labelThrowHandler, labelThrowHandler, "pers/XiaoShadiao/NMSLException");
                        super.visitTryCatchBlock(labelThrowHandler, labelThrow2, labelThrowHandler2, "pers/XiaoShadiao/NMSLException");
                    } else {
                        super.visitFieldInsn(Opcodes.GETSTATIC, cr.getClassName(), fieldName, "Lpers/XiaoShadiao/NMSLException;");
                        super.visitJumpInsn(Opcodes.GOTO, labelThrow);
                        super.visitLabel(labelThrowHandler2);
//						super.visitInsn(Opcodes.POP);
//						super.visitInsn(Opcodes.ACONST_NULL);
                        super.visitLabel(labelThrow);
                        super.visitInsn(Opcodes.DUP);
                        // super.visitFieldInsn(Opcodes.GETSTATIC, cr.getClassName(), fieldName, "Lpers/XiaoShadiao/NMSLException;");
                        super.visitInsn(Opcodes.ATHROW);
                        super.visitLabel(labelThrowHandler);
                        super.visitInsn(Opcodes.POP);
                        super.visitLabel(labelThrow2);
                        super.visitTryCatchBlock(labelThrow, labelThrowHandler, labelThrowHandler, "pers/XiaoShadiao/NMSLException");
                        super.visitTryCatchBlock(labelThrowHandler, labelThrow2, labelThrowHandler2, "pers/XiaoShadiao/NMSLException");
                    }
                }


                super.visitLabel(label);
            }

            @Override
            public void visitMethodInsn(int opcode, String owner, String name, String descriptor) {
                this.visitMethodInsn(opcode, owner, name, descriptor, opcode == Opcodes.INVOKEINTERFACE);
            }

            @Override
            public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                if (r.nextInt(10) == 5) {
                    Label templ1 = new Label();
                    Label templ2 = new Label();
                    Label GOTO = new Label();
                    Label handler1 = new Label();

                    if (true)  {
                        super.visitLabel(templ1);
                        head.add(templ1);
                        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                        super.visitLabel(templ2);
                        super.visitJumpInsn(Opcodes.GOTO, GOTO);

                        super.visitLabel(handler1);
                        super.visitMethodInsn(Opcodes.INVOKESTATIC, "java/util/Objects", "requireNonNull", "(Ljava/lang/Object;)Ljava/lang/Object;", false);
                        super.visitTypeInsn(Opcodes.CHECKCAST, "pers/XiaoShadiao/NMSLException");
                        super.visitInsn(Opcodes.ATHROW);

                        super.visitTryCatchBlock(templ1, templ2, handler1, "pers/XiaoShadiao/NMSLException");
                        // super.visitTryCatchBlock(templ2, handler1, handler1, "pers/XiaoShadiao/NMSLException");

                        super.visitLabel(GOTO);
                    } else {

                        super.visitLabel(templ1);
                        super.visitFieldInsn(Opcodes.GETSTATIC, cr.getClassName(), fieldName, "Lpers/XiaoShadiao/NMSLException;");
                        super.visitInsn(Opcodes.ATHROW);
                        super.visitLabel(templ2);
                        super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Throwable", "getMessage", "()Ljava/lang/String;", false);
                        super.visitFieldInsn(Opcodes.GETSTATIC, cr.getClassName(), fieldName, "Lpers/XiaoShadiao/NMSLException;");
                        super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Throwable", "getMessage", "()Ljava/lang/String;", false);
                        super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "equals", "(Ljava/lang/Object;)Z", false);
                        super.visitJumpInsn(Opcodes.IFEQ, GOTO);
                        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                        Label GOTO2 = new Label();
                        super.visitJumpInsn(Opcodes.GOTO, GOTO2);
                        super.visitLabel(GOTO);
                        super.visitFieldInsn(Opcodes.GETSTATIC, cr.getClassName(), fieldName, "Lpers/XiaoShadiao/NMSLException;");
                        super.visitInsn(Opcodes.ATHROW);
                        super.visitLabel(GOTO2);

                        super.visitTryCatchBlock(templ1, templ2, templ2, "pers/XiaoShadiao/NMSLException");

                    }
                } else super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
            }


            @Override
            public void visitJumpInsn(int opcode, Label label) {
                Label label0 = new Label();
                Label label1 = new Label();
                Label handler = new Label();
                Label GOTO = new Label();

                super.visitLabel(label0);
                super.visitJumpInsn(opcode, label);
                super.visitJumpInsn(Opcodes.GOTO, GOTO);
                // super.visitLabel(label);
                super.visitLabel(label1);

                super.visitLabel(handler);
                super.visitMethodInsn(Opcodes.INVOKESTATIC, "java/util/Objects", "requireNonNull", "(Ljava/lang/Object;)Ljava/lang/Object;", false);
                super.visitTypeInsn(Opcodes.CHECKCAST, "pers/XiaoShadiao/NMSLException");
                super.visitInsn(Opcodes.ATHROW);

                super.visitLabel(GOTO);

                super.visitTryCatchBlock(label0, label1, handler, "pers/XiaoShadiao/NMSLException");
            }

            @Override
            public void visitInsn(int opcode) {
                if(opcode == Opcodes.IRETURN) {

                    super.visitLdcInsn(r.nextInt());
                    super.visitVarInsn(Opcodes.ISTORE, 0);

                    Label label0 = new Label();
                    Label label1 = new Label();
                    Label handler = new Label();
                    Label GOTO = new Label();

                    super.visitLabel(label0);
                    super.visitVarInsn(Opcodes.ISTORE, 0);
                    super.visitFieldInsn(Opcodes.GETSTATIC, cr.getClassName(), fieldName, "Lpers/XiaoShadiao/NMSLException;");
                    super.visitInsn(Opcodes.ATHROW);
                    super.visitLabel(label1);

                    super.visitLabel(handler);
                    super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Throwable", "getMessage", "()Ljava/lang/String;", false);
                    super.visitFieldInsn(Opcodes.GETSTATIC, cr.getClassName(), fieldName, "Lpers/XiaoShadiao/NMSLException;");
                    super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Throwable", "getMessage", "()Ljava/lang/String;", false);
                    super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "equals", "(Ljava/lang/Object;)Z", false);
                    super.visitJumpInsn(Opcodes.IFEQ, GOTO);
                    super.visitVarInsn(Opcodes.ILOAD, 0);
                    super.visitInsn(opcode);
                    super.visitLabel(GOTO);
                    super.visitFieldInsn(Opcodes.GETSTATIC, cr.getClassName(), fieldName, "Lpers/XiaoShadiao/NMSLException;");
                    super.visitInsn(Opcodes.ATHROW);

                    super.visitTryCatchBlock(label0, label1, handler, "pers/XiaoShadiao/NMSLException");

                } else if(opcode == Opcodes.LRETURN) {

                    super.visitLdcInsn(r.nextLong());
                    super.visitVarInsn(Opcodes.LSTORE, 0);

                    Label label0 = new Label();
                    Label label1 = new Label();
                    Label handler = new Label();
                    Label GOTO = new Label();

                    super.visitLabel(label0);
                    super.visitVarInsn(Opcodes.LSTORE, 0);
                    super.visitFieldInsn(Opcodes.GETSTATIC, cr.getClassName(), fieldName, "Lpers/XiaoShadiao/NMSLException;");
                    super.visitInsn(Opcodes.ATHROW);
                    super.visitLabel(label1);

                    super.visitLabel(handler);
                    super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Throwable", "getMessage", "()Ljava/lang/String;", false);
                    super.visitFieldInsn(Opcodes.GETSTATIC, cr.getClassName(), fieldName, "Lpers/XiaoShadiao/NMSLException;");
                    super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Throwable", "getMessage", "()Ljava/lang/String;", false);
                    super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "equals", "(Ljava/lang/Object;)Z", false);
                    super.visitJumpInsn(Opcodes.IFEQ, GOTO);
                    super.visitVarInsn(Opcodes.LLOAD, 0);
                    super.visitInsn(opcode);
                    super.visitLabel(GOTO);
                    super.visitFieldInsn(Opcodes.GETSTATIC, cr.getClassName(), fieldName, "Lpers/XiaoShadiao/NMSLException;");
                    super.visitInsn(Opcodes.ATHROW);

                    super.visitTryCatchBlock(label0, label1, handler, "pers/XiaoShadiao/NMSLException");

                } else if(opcode == Opcodes.DRETURN) {

                    super.visitLdcInsn(r.nextDouble());
                    super.visitVarInsn(Opcodes.DSTORE, 0);

                    Label label0 = new Label();
                    Label label1 = new Label();
                    Label handler = new Label();
                    Label GOTO = new Label();

                    super.visitLabel(label0);
                    super.visitVarInsn(Opcodes.DSTORE, 0);
                    super.visitFieldInsn(Opcodes.GETSTATIC, cr.getClassName(), fieldName, "Lpers/XiaoShadiao/NMSLException;");
                    super.visitInsn(Opcodes.ATHROW);
                    super.visitLabel(label1);

                    super.visitLabel(handler);
                    super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Throwable", "getMessage", "()Ljava/lang/String;", false);
                    super.visitFieldInsn(Opcodes.GETSTATIC, cr.getClassName(), fieldName, "Lpers/XiaoShadiao/NMSLException;");
                    super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Throwable", "getMessage", "()Ljava/lang/String;", false);
                    super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "equals", "(Ljava/lang/Object;)Z", false);
                    super.visitJumpInsn(Opcodes.IFEQ, GOTO);
                    super.visitVarInsn(Opcodes.DLOAD, 0);
                    super.visitInsn(opcode);
                    super.visitLabel(GOTO);
                    super.visitFieldInsn(Opcodes.GETSTATIC, cr.getClassName(), fieldName, "Lpers/XiaoShadiao/NMSLException;");
                    super.visitInsn(Opcodes.ATHROW);

                    super.visitTryCatchBlock(label0, label1, handler, "pers/XiaoShadiao/NMSLException");

                } else if(opcode == Opcodes.FRETURN) {

                    super.visitLdcInsn(r.nextFloat());
                    super.visitVarInsn(Opcodes.FSTORE, 0);

                    Label label0 = new Label();
                    Label label1 = new Label();
                    Label handler = new Label();
                    Label GOTO = new Label();

                    super.visitLabel(label0);
                    super.visitVarInsn(Opcodes.FSTORE, 0);
                    super.visitFieldInsn(Opcodes.GETSTATIC, cr.getClassName(), fieldName, "Lpers/XiaoShadiao/NMSLException;");
                    super.visitInsn(Opcodes.ATHROW);
                    super.visitLabel(label1);

                    super.visitLabel(handler);
                    super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Throwable", "getMessage", "()Ljava/lang/String;", false);
                    super.visitFieldInsn(Opcodes.GETSTATIC, cr.getClassName(), fieldName, "Lpers/XiaoShadiao/NMSLException;");
                    super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Throwable", "getMessage", "()Ljava/lang/String;", false);
                    super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "equals", "(Ljava/lang/Object;)Z", false);
                    super.visitJumpInsn(Opcodes.IFEQ, GOTO);
                    super.visitVarInsn(Opcodes.FLOAD, 0);
                    super.visitInsn(opcode);
                    super.visitLabel(GOTO);
                    super.visitFieldInsn(Opcodes.GETSTATIC, cr.getClassName(), fieldName, "Lpers/XiaoShadiao/NMSLException;");
                    super.visitInsn(Opcodes.ATHROW);

                    super.visitTryCatchBlock(label0, label1, handler, "pers/XiaoShadiao/NMSLException");

                } else if(opcode == Opcodes.RETURN) {

                    Label label0 = new Label();
                    Label label1 = new Label();
                    Label handler = new Label();
                    Label GOTO = new Label();

                    super.visitLabel(label0);
                    super.visitFieldInsn(Opcodes.GETSTATIC, cr.getClassName(), fieldName, "Lpers/XiaoShadiao/NMSLException;");
                    super.visitInsn(Opcodes.ATHROW);
                    super.visitLabel(label1);

                    super.visitLabel(handler);
                    super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Throwable", "getMessage", "()Ljava/lang/String;", false);
                    super.visitFieldInsn(Opcodes.GETSTATIC, cr.getClassName(), fieldName, "Lpers/XiaoShadiao/NMSLException;");
                    super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Throwable", "getMessage", "()Ljava/lang/String;", false);
                    super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "equals", "(Ljava/lang/Object;)Z", false);
                    super.visitJumpInsn(Opcodes.IFEQ, GOTO);
                    super.visitInsn(opcode);
                    super.visitLabel(GOTO);
                    super.visitFieldInsn(Opcodes.GETSTATIC, cr.getClassName(), fieldName, "Lpers/XiaoShadiao/NMSLException;");
                    super.visitInsn(Opcodes.ATHROW);

                    super.visitTryCatchBlock(label0, label1, handler, "pers/XiaoShadiao/NMSLException");

                } else super.visitInsn(opcode);
            }

            @Override
            public void visitMaxs(int maxStack, int maxLocals)  {

                super.visitLabel(handler);
                // Objects.requireNonNull();
                super.visitMethodInsn(Opcodes.INVOKESTATIC, "java/util/Objects", "requireNonNull", "(Ljava/lang/Object;)Ljava/lang/Object;", false);
                super.visitTypeInsn(Opcodes.CHECKCAST, "pers/XiaoShadiao/NMSLException");
                super.visitInsn(Opcodes.ATHROW);
                super.visitEnd();
                super.visitTryCatchBlock(label, handler, handler, "pers/XiaoShadiao/NMSLException");

//				for (int i = 0; i < 1; i++) {
//					super.visitTryCatchBlock(head.get(i), tail.get(i), handler, "pers/XiaoShadiao/NMSLException");
//				}

                super.visitMaxs(maxStack, maxLocals);
            }
        };

        return super.visitMethod(access, name, descriptor, signature, exceptions);
    }

    @Override
    public void visitEnd() {
        if (shouldObf()) {
            if(clinit == null) {
                clinit = super.visitMethod(Opcodes.ACC_STATIC, "<clinit>", "()V", null, null);

                initClinitMethod("CONSTANT");

                clinit.visitInsn(Opcodes.RETURN);
                clinit.visitMaxs(0, 0);
            }
        }
    }

    private void initClinitMethod(String name) {

        if(!inited) {
            inited = true;
        } else return;
        // System.out.println(name);

        visitField(Opcodes.ACC_PRIVATE | Opcodes.ACC_STATIC | Opcodes.ACC_FINAL, fieldName, "Lpers/XiaoShadiao/NMSLException;", null, null);

        // System.out.println("initClinitMethod " + cr.getClassName() + " " + fieldName + " " + clinit);
        clinit.visitTypeInsn(Opcodes.NEW, "pers/XiaoShadiao/NMSLException");
        clinit.visitInsn(Opcodes.DUP);
        clinit.visitLdcInsn(randKey);
        clinit.visitMethodInsn(Opcodes.INVOKESPECIAL, "pers/XiaoShadiao/NMSLException", "<init>", "(Ljava/lang/String;)V", false);

        clinit.visitFieldInsn(Opcodes.PUTSTATIC, cr.getClassName(), fieldName, "Lpers/XiaoShadiao/NMSLException;");

        // clinit.visitInsn(Opcodes.RETURN);

    }

}

interface test {
    public static void Main() {
        long l = 11111111111111111l;
        if(l > -1000000000000l) {
            System.out.println(System.class);
            if(System.class != null)System.out.println(System.class);
        }
    }
}

class testMain {
    public static void main(String[] args) throws Exception {
        MethodVisitor mv;

        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES + ClassWriter.COMPUTE_MAXS);

        cw.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC | Opcodes.ACC_SUPER, "test", null, "java/lang/Object", null);

        mv = cw.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC, "print", "(I)V", null, new String[] {"java/lang/Exception"});
        // mv.visitParameter("草泥马", 0);
        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitTypeInsn(Opcodes.NEW, "java/lang/StringBuilder");
        mv.visitInsn(Opcodes.DUP);
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
        mv.visitLdcInsn("你调用你妈呢: ");
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
        mv.visitVarInsn(Opcodes.ILOAD, 0);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(I)Ljava/lang/StringBuilder;", false);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);

        Label handler = new Label();

        Label l1 = new Label();
        Label l2 = new Label();
        Label l3 = new Label();
        Label l4 = new Label();

        Label end = new Label();

        mv.visitLabel(l1);

        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn("我阐述你的梦!");
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);

        mv.visitLabel(l2);

        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn("我阐述你的梦!!!!!");
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);

        mv.visitLabel(l3);

        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn("我阐述你的梦~~~~~~~~");
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);

        mv.visitLabel(l4);

        mv.visitJumpInsn(Opcodes.GOTO, end);
        mv.visitLabel(handler);
        mv.visitVarInsn(Opcodes.ASTORE, 1);
        mv.visitVarInsn(Opcodes.ALOAD, 1);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Throwable", "printStackTrace", "()V", false);
        mv.visitVarInsn(Opcodes.ALOAD, 1);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Throwable", "getMessage", "()Ljava/lang/String;", false);
        mv.visitLdcInsn("你妈死了");
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "equals", "(Ljava/lang/Object;)Z", false);
        mv.visitJumpInsn(Opcodes.IFEQ, end);
        mv.visitVarInsn(Opcodes.ALOAD, 1);
        mv.visitInsn(Opcodes.ATHROW);
        mv.visitLabel(end);

        mv.visitTryCatchBlock(l1, l2, handler, "java/lang/RuntimeException");
        mv.visitTryCatchBlock(l3, l4, handler, "java/lang/Exception");
        mv.visitTryCatchBlock(l1, l4, handler, "java/lang/Error");
        // mv.visitTryCatchBlock(l1, l4, handler, null);

        mv.visitInsn(Opcodes.RETURN);
        mv.visitMaxs(0, 0);
        mv.visitEnd();

        mv = cw.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC, "run", "()V", null, new String[] {"java/lang/Throwable"});

        mv.visitInsn(Opcodes.ICONST_2);
        mv.visitInvokeDynamicInsn(Utils.spawnRandomChar(), "(I)V", new Handle(Opcodes.H_INVOKESTATIC, "XiaoShadiao", "最喜欢玩原神了", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"), new Object[] {"玩原神玩的", "Main", "print", "(I)V"});
        mv.visitInsn(Opcodes.RETURN);
        mv.visitMaxs(0, 0);
        mv.visitEnd();

        new ClassLoader() {

            public Class<?> define(byte[] b) {
                return (super.defineClass(null, cw.toByteArray(), 0, cw.toByteArray().length));
            }
            //
            //			@Override
            //			public Class<?> loadClass(String name) throws ClassNotFoundException {
            //				// TODO 自动生成的方法存根
            //				// System.out.println(name);
            //				if("XiaoShadiao".equals(name)) return (super.defineClass(null, cw.toByteArray(), 0, cw.toByteArray().length));
            //				else return super.loadClass(name);
            //			}
        }.define(cw.toByteArray()).getDeclaredMethod("print", int.class)

                .invoke(null, 1);

        new FileOutputStream("C:\\Users\\Administrator\\Desktop\\test.class").write(cw.toByteArray());

        new Exception();
    }
}

class testMain2 {
    public static void main(String[] args) throws Exception {
        MethodVisitor mv;

        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES + ClassWriter.COMPUTE_MAXS);

        cw.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC | Opcodes.ACC_ABSTRACT | Opcodes.ACC_INTERFACE, "原神", null, "java/lang/Object", null);

        cw.visitField(Opcodes.ACC_PRIVATE | Opcodes.ACC_FINAL | Opcodes.ACC_STATIC, "。", "Ljava/lang/String;", null, null);

        System.out.println(new ClassLoader() {
            public Class<?> define(byte[] b) {
                return (super.defineClass(null, cw.toByteArray(), 0, cw.toByteArray().length));
            }

        }.define(cw.toByteArray()));
    }
}

class testMain3 {
    public static void main(String[] args) throws Exception {
        ClassNode classNode = new ClassNode();
        new ClassReader(new FileInputStream("C:\\Users\\Administrator\\Desktop\\recaf\\Main.class")).accept(classNode, 0);
    }
}
