package pers.XiaoShadiao.obfuscator.visitor;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import pers.XiaoShadiao.obfuscator.config.Config;
import pers.XiaoShadiao.obfuscator.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThrowableStringObfVisitor extends SimpleStringObfVisitor {

    public static final String dynamicStringKey = Utils.spawnRandomChar(50, false);
    public final String fieldName = Utils.getRandomNameFromMap();
    public final String fieldNameDesc = "[Ljava/lang/Object;";
    public Map<String, Integer> arrayIndex = new HashMap<>();

    public ThrowableStringObfVisitor(byte[] bytes, String[] args) {
        super(bytes, args);
    }

    @Override
    public byte[] transfer(byte[] bytes) {
        ClassNode cn = byteToClassNode(bytes);
        if((cn.access | Opcodes.ACC_SYNTHETIC) == cn.access || (cn.access | Opcodes.ACC_INTERFACE) == cn.access || (cn.access | Opcodes.ACC_ENUM) == cn.access) return classNodeToBytes(cn);
        for (MethodNode method : cn.methods) {
            for (AbstractInsnNode insnNode : method.instructions) {
                if (insnNode instanceof LdcInsnNode) {
                    LdcInsnNode insnNode1 = (LdcInsnNode) insnNode;
                    if (insnNode1.cst instanceof String) {
                        for (int jj = 0; jj < (Utils.r.nextInt(5) <= 1 ? Utils.r.nextInt(2) : 0); jj++) {
                            arrayIndex.put(Utils.spawnRandomChar(Utils.r.nextInt(30) + 1, true), arrayIndex.size());
                        }

                        if (!arrayIndex.containsKey(insnNode1.cst.toString()))
                            arrayIndex.put(insnNode1.cst.toString(), arrayIndex.size());
                    }
                }
            }
        }

        unorderKeyValue(arrayIndex);

        for (MethodNode method : cn.methods) {
            if(method.name.equals("原神启动") && method.desc.equals("(Ljava/lang/Object;)Ljava/lang/String;")) {
                continue;
            }
            // if(cn.name.equals("XiaoShadiao") && isClinitNode(method)) continue;
            InsnList insnList = new InsnList();
            for (AbstractInsnNode insnNode : method.instructions) {
                if(insnNode instanceof LdcInsnNode) {
                    LdcInsnNode ldcInsnNode = (LdcInsnNode) insnNode;
                    if(ldcInsnNode.cst instanceof String) {
                        int arrayII;
                        if(arrayIndex.containsKey(ldcInsnNode.cst.toString())) {
                            arrayII = arrayIndex.get(ldcInsnNode.cst.toString());
                        } else {
                            throw new Error(ldcInsnNode.cst + " String NOT FOUND, THIS SHOULD NOT HAPPEN!");
                        }
                        insnList.add(new FieldInsnNode(Opcodes.GETSTATIC, cr.getClassName(), fieldName, fieldNameDesc));
                        insnList.add(new LdcInsnNode(arrayII));
                        insnList.add(new InsnNode(Opcodes.AALOAD));
                        // insnList.add(new TypeInsnNode(Opcodes.CHECKCAST, "java/lang/String"));
                        insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "XiaoShadiao", "原神启动", "(Ljava/lang/Object;)Ljava/lang/String;", false));
                        continue;
                    }
                }
                insnList.add(insnNode);
            }
        };
        initArray(cn);

        return classNodeToBytes(cn);
    }

    private void initArray(ClassNode cn) {
        if(!arrayIndex.isEmpty()) {
            cn.fields.add(new FieldNode(Opcodes.ACC_PRIVATE | Opcodes.ACC_STATIC, fieldName, fieldNameDesc, null, null));

            MethodNode methodNode = getOrCreateClinitNode(cn);

            InsnList insnList = new InsnList();

            insnList.add(new LdcInsnNode(arrayIndex.size() + 1));
            insnList.add(new TypeInsnNode(Opcodes.ANEWARRAY, "java/lang/Object"));
            for (Map.Entry<String, Integer> entry : arrayIndex.entrySet()) {
                String s = entry.getKey();

                String className;

                className = Utils.getRandomMember(Config.exceptions).getName();

                char[] chars = s.toCharArray();
                char[] chars2 = className.toCharArray();

                int key = Utils.r.nextInt(126) + 1;
                int key3 = 0;

                int key2 = Utils.spawnRandomChar().charAt(0);
                char key2_ = (char) key2;

                char[] newchars = new char[chars.length];

                for (int i = 0; i < chars.length; i++) {
                    key2 <<= key2 + (key2 % 4) + dynamicStringKey.hashCode();// 123456789;
                    key3 = (key2 / 9) >> 2;
                    newchars[i] = (char) (chars[i] ^ ((((key * (i + 1)) ^ (key2 + key3)) ^ chars2[i % chars2.length]) % 0x10000));
                }

                String encodedString = key2_ + new String(newchars);

                LabelNode l = new LabelNode();

                insnList.add(l);
                insnList.add(new LineNumberNode(key, l));
                insnList.add(new InsnNode(Opcodes.DUP));
                insnList.add(new LdcInsnNode(entry.getValue()));

                insnList.add(new TypeInsnNode(Opcodes.NEW, className.replace(".", "/")));
                insnList.add(new InsnNode(Opcodes.DUP));
                insnList.add(new LdcInsnNode(encodedString));
                insnList.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, className.replace(".", "/"), "<init>", "(Ljava/lang/String;)V", false));

                // insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "XiaoShadiao", "原神启动", "(Ljava/lang/Object;)Ljava/lang/String;", false));

                insnList.add(new InsnNode(Opcodes.AASTORE));
            }
            insnList.add(new FieldInsnNode(Opcodes.PUTSTATIC, cr.getClassName(), fieldName, fieldNameDesc));
            insnList.add(methodNode.instructions);

            methodNode.instructions = insnList;
        }
    }

}
