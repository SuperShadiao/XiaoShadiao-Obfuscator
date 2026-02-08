package pers.XiaoShadiao.obfuscator.visitor;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.tree.analysis.AnalyzerException;
import pers.XiaoShadiao.obfuscator.Main;
import pers.XiaoShadiao.obfuscator.config.Config;
import pers.XiaoShadiao.obfuscator.utils.ClassWriter1;
import pers.XiaoShadiao.obfuscator.utils.Utils;
import pers.XiaoShadiao.obfuscator.utils.customclassloader.XSDURLClassLoader;
import pers.XiaoShadiao.obfuscator.visitor.visitorfactory.VisitorFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;
import java.util.zip.ZipEntry;

public class XiaoShadiaoSerializableObfVisitor extends AbstractVisitor {

    public volatile static XiaoShadiaoSerializableObfVisitor INSTANCE;

    public String totalHashedSerializedFieldName;
    public final Map<String, FieldType> xiaoshadiaoFieldMap = new HashMap<>();
    public final Map<String, Integer> xiaoshadiaoFieldMapHashCode = new HashMap<>();
    public int totalSerializedHash;
    public byte[] xsdSerializedClassBytes;
    public String[] xiaoshadiaoFields;

    public XiaoShadiaoSerializableObfVisitor(byte[] bytes, String[] args) {
        super(bytes, args);
    }

    @Override
    public byte[] transfer(byte[] classData) {

        ClassNode classNode = byteToClassNode(classData);

        System.out.println("XiaoShadiaoSerializableObfVisitor -> " + classNode.name);
        if(!classNode.name.contains("XiaoShadiao")) return classData;

        INSTANCE = this;
        System.out.println("XiaoShadiaoSerializableObfVisitor instance loaded");

        if(!Config.useAntiDebugger && !VisitorManager.isVisitorEnabled(VisitorFactory.AntiDebugger)) {
            System.out.println("AntiDebugger is disabled");
            classNode.methods.removeIf(method -> method.name.equals("init") || method.name.equals("init2") || method.name.contains("lambda$init")); // lambda$init$0
            return classNodeToBytes(classNode);
        }

        if(!Config.useSecurityManagerAntiDebugger) classNode.methods.removeIf(method -> method.name.equals("init2"));

        for (int i = 0; i < 100; i++) {
            FieldType type = FieldType.values()[Utils.r.nextInt(99) % FieldType.values().length];

            String fieldName;
            switch (type) {
                case STRING:
                    classNode.fields.add(new FieldNode(Opcodes.ACC_PUBLIC, fieldName = Utils.spawnRandomChar(10, false) + "XiaoShadiao666", "Ljava/lang/String;", null, null));
                    break;
                case INT:
                    classNode.fields.add(new FieldNode(Opcodes.ACC_PUBLIC, fieldName = Utils.spawnRandomChar(10, false) + "XiaoShadiao666", "I", null, null));
                    break;
                default:
                    throw new RuntimeException("Unknown type: " + type);
            }

            xiaoshadiaoFieldMap.put(fieldName, type);
        }

        totalHashedSerializedFieldName = Utils.spawnRandomChar(10, false) + "XiaoShadiao666";

        classNode.fields.add(new FieldNode(Opcodes.ACC_PUBLIC | Opcodes.ACC_TRANSIENT, totalHashedSerializedFieldName, "I", null, null));

        a:for (MethodNode method : classNode.methods) {
            if("init".equals(method.name)) {
                for (AbstractInsnNode insn : method.instructions) {
                    if(insn instanceof LdcInsnNode) {
                        LdcInsnNode ldc = (LdcInsnNode) insn;
                        if("unusedObject".equals(ldc.cst)) {
                            ldc.cst = totalHashedSerializedFieldName;
                            break a;
                        }
                    }
                }
            }
        }

        byte[] bytes = classNodeToBytes(classNode);

        try(XSDURLClassLoader loader = new XSDURLClassLoader(new URL[0], XiaoShadiaoSerializableObfVisitor.class.getClassLoader())) {
            Class<?> xsdClass = loader.defineClass(bytes);
            Object xsd = xsdClass.newInstance();

            Field[] fields = xsd.getClass().getDeclaredFields();

            for (Field field : fields) {
                if(!field.getName().endsWith("XiaoShadiao666")) continue;
                if(field.getType() == int.class) field.set(xsd, Utils.r.nextInt());
                if(field.getType() == String.class) field.set(xsd, Utils.spawnRandomChar(10, false));
            }

            int hash = 0;

            int i = 0;
            for (Field field : fields) {
                if(!totalHashedSerializedFieldName.equals(field.getName()) && field.getName().endsWith("XiaoShadiao666")) {
                    int hash1 = field.get(xsd).hashCode();
                    hash ^= hash1;
                    i++;
                    xiaoshadiaoFieldMapHashCode.put(field.getName(), hash1);
                }
            }

            totalSerializedHash = hash;

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            new ObjectOutputStream(baos).writeObject(xsd);
            byte[] bytes1 = baos.toByteArray();
            for (int j = 0; j < bytes1.length; j++) {
                bytes1[j] ^= (byte)(new Random(j).nextInt(256));
            }
            xsdSerializedClassBytes = bytes1;
            Main.writeToOutput(new ZipEntry("XiaoShadiaoStuff"), bytes1);

        } catch (IOException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        xiaoshadiaoFieldMap.put(totalHashedSerializedFieldName, FieldType.INT);
        xiaoshadiaoFieldMapHashCode.put(totalHashedSerializedFieldName, totalSerializedHash);

        xiaoshadiaoFields = xiaoshadiaoFieldMapHashCode.keySet().toArray(new String[0]);

        return bytes;
    }

    @Override
    public List<String> getVisitorTags() {
        return Collections.emptyList();
    }

    public enum FieldType {
        STRING("Ljava/lang/String;"),
        INT("I");

        public final String desc;

        FieldType(String desc) {
            this.desc = desc;
        }
    }

}
