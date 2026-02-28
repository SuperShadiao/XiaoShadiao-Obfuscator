package pers.XiaoShadiao.obfuscator.utils.customclassloader;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.analysis.*;
import pers.XiaoShadiao.NMSLException;
import pers.XiaoShadiao.obfuscator.config.Config;
import pers.XiaoShadiao.obfuscator.visitor.AbstractVisitor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;

public class ClassVerifyer {

    public final static XSDURLClassLoader ASM5_0_3Verifyer;
    private static Method verifyerMethod;

    static {
        try {
            File tempfile = File.createTempFile("oldasm503verifyer", ".jar");
            tempfile.deleteOnExit();

            // 总是重新写入，覆盖现有内容
            try (InputStream is = Class.forName("XiaoShadiao").getResourceAsStream("oldasm503verifyer.jar");
                 FileOutputStream fos = new FileOutputStream(tempfile, false)) {  // false = 覆盖

                if (is == null) {
                    throw new RuntimeException("Can't find oldasm503verifyer.jar in the jar, this is impossible");
                }

                // 使用缓冲区提高效率
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }

                fos.flush();
            }

            // 验证文件大小
            if (tempfile.length() == 0) {
                throw new RuntimeException("Extracted jar file is empty");
            }

            ASM5_0_3Verifyer = new XSDURLClassLoader(new URL[] {tempfile.toURI().toURL()}, null);

        } catch (Throwable e) {
            if (e instanceof RuntimeException) throw (RuntimeException) e;
            throw new RuntimeException(e);
        }
    }

    public static void verify(byte[] bytes) {
        verify(bytes, false);
    }

    public static void verify(byte[] bytes, boolean useOldASM) {

        if(verifyerMethod == null) {
            try {
                verifyerMethod = Class.forName("Verifyer", false, ASM5_0_3Verifyer).getMethod("verify", byte[].class);
            } catch (ClassNotFoundException | NoSuchMethodException e) {
                // e.printStackTrace();
            }
        }
        if(Config.dontverify) {
            return;
        }

        try {
            if(useOldASM && Config.verifyWith508ASM) verifyerMethod.invoke(null, (Object) bytes);
        } catch (IllegalAccessException | InvocationTargetException e) {
            a:{
                Throwable t = e;
                while ((t = t.getCause()) != null) {
                    if(t.toString().contains("java.lang.ClassNotFoundException")) break a;
                }

                throw new RuntimeException("This class is not passed the ASM 5.0.3 test", e);
            }
        }
        try(VerifyerClassLoader classLoader = new VerifyerClassLoader()) {
            ClassNode classNode = AbstractVisitor.byteToClassNode(bytes);
//            SimpleVerifier simpleVerifier = new SimpleVerifier();
//            simpleVerifier.setClassLoader(classLoader);
//            Analyzer<BasicValue> analyzer = new Analyzer<>(simpleVerifier);
            Analyzer<BasicValue> analyzer = new Analyzer<>(new BasicVerifier());
            for (MethodNode method : classNode.methods) {
                analyzer.analyze(classNode.name, method);
            }

            Class<?> cla$$ = classLoader.define(bytes);
            cla$$.getDeclaredMethods();
            cla$$.newInstance();
        } catch(AnalyzerException e) {
            throw new RuntimeException(e);
        } catch(Throwable e) {
            if(Config.dontverify) {
                return;
            }
            if(e instanceof ClassFormatError || e instanceof VerifyError) {
                if(e.getLocalizedMessage().startsWith("Class file version does not support constant tag")) throw new Error("尝试加-fixVersion参数看看能否修复此问题", e);
                throw (Error)e;
            }
            if(e instanceof NMSLException || e instanceof IndexOutOfBoundsException) {
                throw new RuntimeException("混淆出的代码可能有些问题", e);
            }
        }
    }

}

