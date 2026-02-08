package pers.XiaoShadiao.obfuscator.utils;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import pers.XiaoShadiao.obfuscator.Main;
import pers.XiaoShadiao.obfuscator.utils.customclassloader.XSDURLClassLoader;

public class ClassWriter1 extends ClassWriter {

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
            var4 = Main.loader.loadClass(var1.replace('/', '.'));
        } catch (ClassNotFoundException var7) {
            // System.err.println(var1);
            // var7.printStackTrace();
            //System.out.println(var1 + " - " + var2);
            if(var7 instanceof ClassNotFoundException) {
//                Main.loader.defineClass(getClass(var7.getLocalizedMessage().replace(".", "/")));
//                return getCommonSuperClass(var1, var2);
                throw new RuntimeException("无法找到类 " + var1 + " , 为了保证类与类间的继承关系正确, 请将依赖jar文件补全, 放入 " + XSDURLClassLoader.libFolder.getAbsolutePath() + " 文件夹即可");
            }
            // return "java/lang/Object";
            throw null;
        }
        try {
            // var5 = Class.forName(var2.replace('/', '.'), false, var3);
            var5 = Main.loader.loadClass(var2.replace('/', '.'));
        } catch (ClassNotFoundException var7) {
            // System.err.println(var2);
            //System.out.println(var1 + " - " + var2);
            // var7.printStackTrace();
            if(var7 instanceof ClassNotFoundException) {
//                Main.loader.defineClass(getClass(var7.getLocalizedMessage().replace(".", "/")));
//                return getCommonSuperClass(var1, var2);
                throw new RuntimeException("无法找到类 " + var2 + " , 为了保证类与类间的继承关系正确, 请将依赖jar文件补全, 放入 " + XSDURLClassLoader.libFolder.getAbsolutePath() + " 文件夹即可");
            }
            // return "java/lang/Object";
            throw null;
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

    protected ClassLoader getClassLoader() {
        return Main.loader;
    }

    private byte[] getClass(String name) {

        ClassWriter cw = new ClassWriter(0);
        cw.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC | Opcodes.ACC_SUPER, name, null, "java/lang/Object", null);

        return cw.toByteArray();

    }

}