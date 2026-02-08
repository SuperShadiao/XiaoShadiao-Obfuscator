package pers.XiaoShadiao.obfuscator;

import org.objectweb.asm.ClassReader;
import pers.XiaoShadiao.NMSLException;
import pers.XiaoShadiao.obfuscator.commandline.CommandHandler;
import pers.XiaoShadiao.obfuscator.config.Config;
import pers.XiaoShadiao.obfuscator.console.Printer;
import pers.XiaoShadiao.obfuscator.interfaces.ICommandExecutor;
import pers.XiaoShadiao.obfuscator.interfaces.IVisitor;
import pers.XiaoShadiao.obfuscator.interfaces.IVisitorFactory;
import pers.XiaoShadiao.obfuscator.taskmanager.Task;
import pers.XiaoShadiao.obfuscator.taskmanager.TaskManager;
import pers.XiaoShadiao.obfuscator.taskmanager.TaskThread;
import pers.XiaoShadiao.obfuscator.utils.ClassWriter1;
import pers.XiaoShadiao.obfuscator.utils.Utils;
import pers.XiaoShadiao.obfuscator.utils.customclassloader.XSDURLClassLoader;
import pers.XiaoShadiao.obfuscator.visitor.*;
import pers.XiaoShadiao.obfuscator.visitor.visitorfactory.OverrideVisitorFactory;
import pers.XiaoShadiao.obfuscator.visitor.visitorfactory.VisitorFactory;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Main {

    public static XSDURLClassLoader loader;
    private static final Queue<Map.Entry<ZipEntry, byte[]>> writeQueue = new ConcurrentLinkedQueue<>();
    private static final List<AbstractVisitor.TransferResult> transferResults = Collections.synchronizedList(new ArrayList<>());

    public static final File directionary;

    static {
        try {
            directionary = new File(URLDecoder.decode(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8")).getParentFile();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        loader = new XSDURLClassLoader(new URL[0], Main.class.getClassLoader());
    }

    public static final String version = "2.0.0";

    public static void main(String[] args) throws Throwable {

        FutureTask<String> updateCheck = new FutureTask<>(() -> {
            HttpsURLConnection uc = (HttpsURLConnection) new URL("https://xiaoshadiao.club/obfversion").openConnection();
            uc.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:101.0) Gecko/20100101 Firefox/101.0");

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

        Printer printer = new Printer();

        Thread.currentThread().setUncaughtExceptionHandler((t, e) -> {
            printer.close();
            System.err.println("混淆器执行时发生了错误 :(");
            e.printStackTrace();
            System.exit(1);
        });
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            while(true) {
                printer.close();
                try {
                    String s = updateCheck.get();
                    if (!version.equals(s)) {
                        System.out.println("发现新版本: " + s);
                        System.out.println("下载: https://xiaoshadiao.club/obfdownload");
                    }
                    return;
                } catch (ExecutionException e) {
                    System.out.println("检查更新失败: " + e.getCause());
                    return;
                } catch (InterruptedException ignored) {}
            }
        }));

//        {
//            printer.start();
//            test();
//        }
        CommandHandler.executeCommand(args);

        if(Config.inputFile == null || Config.outputFile == null) {
            System.out.println("\n你没有设置输入和输出文件!\n");
            printHelp();
            System.exit(0);
        }

        ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(Config.inputFile.toPath()));
        FileOutputStream fileOutputStream = new FileOutputStream(Config.outputFile);
        ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try { zipOutputStream.close(); } catch (IOException e) { e.printStackTrace(); }
            try { fileOutputStream.close(); } catch (IOException e) {  e.printStackTrace();  }
            try { zipInputStream.close(); } catch (IOException e) {  e.printStackTrace();  }
        }));

        printer.start();

        AbstractVisitor.initOrder();

        IVisitorFactory[] factories = {
                new IVisitorFactory() {
                    @Override
                    public IVisitor getVisitor(byte[] bytes) {
                        return new XiaoShadiaoStrReplacerObfVisitor(bytes, new String[0]);
                    }

                    @Override
                    public byte[] transfer(byte[] bytes) {
                        return getVisitor(bytes).transfer();
                    }

                    @Override
                    public Class<? extends IVisitor> getVisitorType() {
                        return XiaoShadiaoStrReplacerObfVisitor.class;
                    }

                    @Override
                    public int getOrder() {
                        return 0;
                    }
                },
                new IVisitorFactory() {
                    @Override
                    public IVisitor getVisitor(byte[] bytes) {
                        return new XiaoShadiaoSerializableObfVisitor(bytes, new String[0]);
                    }

                    @Override
                    public byte[] transfer(byte[] bytes) {
                        return getVisitor(bytes).transfer();
                    }

                    @Override
                    public Class<? extends IVisitor> getVisitorType() {
                        return XiaoShadiaoSerializableObfVisitor.class;
                    }

                    @Override
                    public int getOrder() {
                        return 0;
                    }
                },
                VisitorFactory.LocalVar,
                new OverrideVisitorFactory(VisitorFactory.StringObf) {
                    @Override
                    public IVisitor getVisitor(byte[] bytes) {
                        return new ThrowableStringObfVisitor(bytes, new String[] {"true"});
                    }
                },
                VisitorFactory.NumberObf,
                VisitorFactory.JunkTryCatchObf,
                VisitorFactory.SimpleJunkLabelObf,
                VisitorFactory.SwitchCaseObfVisitor,
                VisitorFactory.FieldMethodResorter,
                VisitorFactory.SyntheticFlag,
                // VisitorFactory.LabelResorter,
        };
        IVisitorFactory[] emptyFactory = {};

        writeToOutput(Class.forName("XiaoShadiao"), factories);
        writeToOutput(Class.forName("ShaXiaodiao"), factories);
        writeToOutput(Class.forName("DiaoXiaosha"), emptyFactory);
        writeToOutput(NMSLException.class, emptyFactory);

        ZipEntry zipEntry;
        byte[] buffer = new byte[1024];

        Config.outputFile.createNewFile();

        AtomicInteger totalClass = new AtomicInteger(0);
        AtomicInteger totalObfClass = new AtomicInteger(0);
        AtomicInteger exceptionCount = new AtomicInteger(0);

        while((zipEntry = zipInputStream.getNextEntry()) != null) {
            int len;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while((len = zipInputStream.read(buffer)) > 0) {
                baos.write(buffer, 0, len);
            }
            byte[] bytes = baos.toByteArray();

            ZipEntry finalZipEntry = zipEntry;
            TaskManager.addTask(new Task(() -> {
                if(finalZipEntry.getName().endsWith(".class")) {
                    totalClass.incrementAndGet();
                    AbstractVisitor.TransferResult transferResult = AbstractVisitor.transferClass(bytes);
                    transferResults.add(transferResult);
                    if(transferResult.bytes != bytes) {
                        totalObfClass.incrementAndGet();
                    }
                    writeQueue.add(new AbstractMap.SimpleEntry<>(finalZipEntry, transferResult.bytes));
                } else {
                    writeQueue.add(new AbstractMap.SimpleEntry<>(finalZipEntry, bytes));
                }
            }, zipEntry.getName()));

        }

        long lastTime = System.currentTimeMillis();
        while(true) {
            if(TaskManager.getTaskCount() > 0) {
                lastTime = System.currentTimeMillis();
            }
            Map.Entry<ZipEntry, byte[]> entry = writeQueue.poll();
            if(entry == null) {
                if(System.currentTimeMillis() - lastTime > 100) {
                    break;
                }
                continue;
            }
            ZipEntry entryKey = new ZipEntry(entry.getKey().getName() + (Config.classToFolder && entry.getKey().getName().endsWith(".class") ? "/" : ""));
            entryKey.setTime(Long.MAX_VALUE);
            System.out.println("写出: " + entryKey.getName());
            // zzzz.putNextEntry(new ZipEntry(cr.getClassName() + ".class" + (Main.classToFolder ? "/" : "")));
            try {
                zipOutputStream.putNextEntry(entryKey);
            } catch(java.util.zip.ZipException e) {
                if(e.getMessage().contains("duplicate entry")) continue;
                throw e;
            }
            zipOutputStream.write(entry.getValue());
            zipOutputStream.closeEntry();
        }
        for (AbstractVisitor.TransferResult transferResult : transferResults) {
            for (Map.Entry<String, Throwable> exception : Objects.requireNonNull(transferResult.exceptions)) {
                System.err.println("[!] 在对" + transferResult.className + "执行" + exception.getKey() + "时发生错误: ");
                exception.getValue().printStackTrace();
                exceptionCount.incrementAndGet();
            }
        }
        if(AbstractVisitor.getIncludeClasses().isEmpty()) System.out.println("你好像没有指定要混淆的类哦, 要不要先试试加上 -inClass (.*) 来混淆所有类呢?");
        System.out.println("读入" + totalClass.get() + "个类, 实际混淆" + totalObfClass.get() + "个类, 发生" + exceptionCount.get() + "个异常");

        System.out.println();
        System.out.println("Ciallo～(∠ · ω< )⌒ ★");
        System.out.println();

        System.exit(0);
    }

    private static ZipEntry getZipEntry(ClassReader thisReader) {
        return new ZipEntry(thisReader.getClassName() + ".class");
    }

    public static void printHelp() {
        System.out.println();
        System.out.println();
        System.out.println("========= XiaoShadiao Obfuscator v" + version + " =========");
        System.out.println();
        System.out.println("用法:java -jar XiaoShadiaoObf.jar -inJar <输入Jar> -outJar <输出Jar> [其他参数...]");
        System.out.println("其中<输入Jar> <输出Jar>必须填!");
        System.out.println("其他参数: ");
        System.out.println();
        int maxLength = CommandHandler.commands.stream().mapToInt(command -> command.getCmdName().length() + command.getCmdArgs().length() + 1).max().getAsInt();
        for (ICommandExecutor command : CommandHandler.commands) {
            System.out.print("  -");
            String cmdName = command.getCmdName();
            System.out.print(cmdName);
            System.out.print(" ");
            String cmdDesc = command.getCmdArgs();
            System.out.print(cmdDesc);
            for (int i = 0; i < maxLength - cmdName.length() - cmdDesc.length(); i++) {
                System.out.print(" ");
            }
            System.out.print("| ");
            System.out.println(command.getCmdDesc());
        }
        System.out.println();
        System.out.println("示例: java -jar XiaoShadiaoObf.jar -inJar xxx.jar -outJar xxx_out.jar -inClass com.examplepackage(.*) -exClass com.examplepackage.excludepackage(.*) -numberObf -stringObf true");
        System.out.println("如果你想混淆Minecraft Forge 1.8.9 Mod, 你可以添加参数 -verifyWith508ASM 防止出现游戏内的意外");
        System.out.println("提示: 一些参数代表了某个Transformer, 你可以添加它的重复参数以反复使用该Transformer, 例如 -numberObf -numberObf");
        System.out.println("提示: 可以使用Proguard混淆器来混淆类名, 本混淆器没有类名混淆功能");
        System.out.println();
        System.out.println("========= XiaoShadiao Obfuscator v" + version + " =========");
        System.out.println();
        System.out.println();
    }

    private static void test() {

        List<Task> tasks = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            tasks.add(TaskManager.addTask(new Task(() -> {
                try {
                    Thread.sleep(Utils.r.nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "" + i)));
        }
        for (Task task : tasks) {
            try {
                task.await();
                System.out.println("task " + task.getTaskName() + " done");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writeToOutput(Class<?> clazz) {
        writeToOutput(clazz, new IVisitorFactory[0]);
    }

    public static void writeToOutput(Class<?> clazz, IVisitorFactory... factories) {
        try {
            writeToOutput(new ClassReader(Objects.requireNonNull(clazz.getResourceAsStream(clazz.getSimpleName() + ".class"))), factories);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeToOutput(ClassReader cr) {
        writeToOutput(cr, new IVisitorFactory[0]);
    }

    public static void writeToOutput(ClassReader thisReader, IVisitorFactory... factories) {
        AbstractVisitor.TransferResult transferResult = AbstractVisitor.transferClassWithFactories(thisReader.b, factories);
        transferResults.add(transferResult);
        writeQueue.add(new AbstractMap.SimpleEntry<>(getZipEntry(thisReader), transferResult.bytes));
    }

    public static void writeToOutput(ZipEntry zipEntry, byte[] bytes) {
        writeQueue.add(new AbstractMap.SimpleEntry<>(zipEntry, bytes));
    }

}
