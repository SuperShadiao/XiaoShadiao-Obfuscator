import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.tree.analysis.AnalyzerException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Random;

public class XiaoShadiaoSerializableObfVisitor {

    public static byte[] transform(byte[] classData) throws AnalyzerException {

        ClassNode classNode = new ClassNode();
        ClassReader cr = new ClassReader(classData);

        if(cr.getClassName().contains("ShaXiaodiao")) return classData;
        cr.accept(classNode, 0);

        if(!Main.useAntiDebugger) {
            classNode.methods.removeIf(method -> method.name.equals("init") || method.name.equals("init2") || method.name.contains("lambda$init")); // lambda$init$0

            ClassWriter writer = new ClassWriter1(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
            classNode.accept(writer);
            return writer.toByteArray();
        }

        if(!Main.useSecurityManagerAntiDebugger) classNode.methods.removeIf(method -> method.name.equals("init2"));

        for (int i = 0; i < 100; i++) {
            FieldType type = FieldType.values()[Main.r.nextInt(99) % FieldType.values().length];

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

            Main.xiaoshadiaoFieldMap.put(fieldName, type);
        }

        Main.totalHashedSerializedFieldName = Utils.spawnRandomChar(10, false) + "XiaoShadiao666";

        classNode.fields.add(new FieldNode(Opcodes.ACC_PUBLIC | Opcodes.ACC_TRANSIENT, Main.totalHashedSerializedFieldName, "I", null, null));

        a:for (MethodNode method : classNode.methods) {
            if("init".equals(method.name)) {
                for (AbstractInsnNode insn : method.instructions) {
                    if(insn instanceof LdcInsnNode) {
                        LdcInsnNode ldc = (LdcInsnNode) insn;
                        if("unusedObject".equals(ldc.cst)) {
                            ldc.cst = Main.totalHashedSerializedFieldName;
                            break a;
                        }
                    }
                }
            }
        }

        ClassWriter writer = new ClassWriter1(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        classNode.accept(writer);
        byte[] bytes = writer.toByteArray();

        try(XSDURLClassLoader loader = new XSDURLClassLoader(new URL[0], XiaoShadiaoSerializableObfVisitor.class.getClassLoader())) {
            Class<?> xsdClass = loader.defineClass(bytes);
            Object xsd = xsdClass.newInstance();

            Field[] fields = xsd.getClass().getDeclaredFields();

            for (Field field : fields) {
                if(!field.getName().endsWith("XiaoShadiao666")) continue;
                if(field.getType() == int.class) field.set(xsd, Main.r.nextInt());
                if(field.getType() == String.class) field.set(xsd, Utils.spawnRandomChar(10, false));
            }

            int hash = 0;

            int i = 0;
            for (Field field : fields) {
                if(!Main.totalHashedSerializedFieldName.equals(field.getName()) && field.getName().endsWith("XiaoShadiao666")) {
                    int hash1 = field.get(xsd).hashCode();
                    hash ^= hash1;
                    i++;
                    // System.out.println(i + ": " + field.getName() + ": " + hash1);
                    Main.xiaoshadiaoFieldMapHashCode.put(field.getName(), hash1);
                }
            }
            // System.out.println("Final: " + Main.totalHashedSerializedFieldName + ": " + hash);

            Main.totalSerializedHash = hash;

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            new ObjectOutputStream(baos).writeObject(xsd);
            byte[] bytes1 = baos.toByteArray();
            for (int j = 0; j < bytes1.length; j++) {
                bytes1[j] ^= (byte)(new Random(j).nextInt(256));
            }
            Main.xsdSerializedClassBytes = bytes1;

        } catch (IOException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        Main.xiaoshadiaoFieldMap.put(Main.totalHashedSerializedFieldName, FieldType.INT);
        Main.xiaoshadiaoFieldMapHashCode.put(Main.totalHashedSerializedFieldName, Main.totalSerializedHash);

        Main.xiaoshadiaoFields = Main.xiaoshadiaoFieldMapHashCode.keySet().toArray(new String[0]);

        return bytes;
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
