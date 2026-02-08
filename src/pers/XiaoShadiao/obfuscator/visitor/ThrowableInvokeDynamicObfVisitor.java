package pers.XiaoShadiao.obfuscator.visitor;

import org.objectweb.asm.Handle;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import pers.XiaoShadiao.obfuscator.config.Config;
import pers.XiaoShadiao.obfuscator.utils.Utils;

import java.util.*;

public class ThrowableInvokeDynamicObfVisitor extends InvokeDynamicObfVisitor {

    public ThrowableInvokeDynamicObfVisitor(byte[] bytes, String[] args) {
        super(bytes, args);
    }

    public final String fieldName = Utils.getRandomNameFromMap();;
    public final String fieldNameDesc = "[LXiaoShadiao;";

    public final String encodedClassName = getEncodedString(cr.getClassName().replace("/", "."));
    public final String encodedFieldName = getEncodedString(fieldName);

    public Map<MethodInfo, Integer> methods = new HashMap<>();

    @Override
    public byte[] transfer(byte[] bytes) {
        ClassNode cn = byteToClassNode(bytes);

        if((cn.access | Opcodes.ACC_SYNTHETIC) == cn.access || (cn.access | Opcodes.ACC_INTERFACE) == cn.access || (cn.access | Opcodes.ACC_ENUM) == cn.access) return classNodeToBytes(cn);
        for (MethodNode method : cn.methods) {
            for (AbstractInsnNode insnNode : method.instructions) {
                if(insnNode instanceof MethodInsnNode) {
                    MethodInsnNode insnNode1 = (MethodInsnNode) insnNode;
                    if(insnNode1.getOpcode() != Opcodes.INVOKESPECIAL) {
                        MethodInfo mi = new MethodInfo();
                        mi.isStatic = Opcodes.INVOKESTATIC == insnNode1.getOpcode();
                        mi.owner = insnNode1.owner;
                        mi.name = insnNode1.name;
                        mi.des = insnNode1.desc;
                        if (!methods.containsKey(mi)) methods.put(mi, methods.size());
                    }
                }
            }
        }

        unorderKeyValue(methods);

        for (MethodNode method : cn.methods) {
            InsnList insnList = new InsnList();
            for (AbstractInsnNode insnNode : method.instructions) {
                if (insnNode instanceof MethodInsnNode) {
                    MethodInsnNode insnNode1 = (MethodInsnNode) insnNode;

                    Integer index;
                    // System.out.println(owner + "========" + name + descriptor);
                    switch(insnNode1.getOpcode()) {
                        case Opcodes.INVOKESTATIC:
                            MethodInfo mi = new MethodInfo();
                            mi.des = insnNode1.desc;
                            mi.name = insnNode1.name;
                            mi.owner = insnNode1.owner;
                            mi.isStatic = true;
                            if((index = methods.get(mi)) == null) throw new Error("找不到方法: " + mi.toString());
                            int indexId = index;
                            int hash1 = cr.getClassName().replace("/", ".").hashCode();
                            int hash2 = fieldName.hashCode();
                            int hash3 = Utils.r.nextInt();
                            int hash4 = indexId ^ hash2 ^ hash1 ^ hash3;

                            insnList.add(new InvokeDynamicInsnNode("XiaoShadiao", insnNode1.desc, new Handle(Opcodes.H_INVOKESTATIC, "XiaoShadiao", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"), new Object[] {
                                    encodedClassName,
                                    encodedFieldName,
                                    hash3,
                                    hash4
                            }));
                            break;
                        case Opcodes.INVOKEINTERFACE:
                        case Opcodes.INVOKEVIRTUAL:
                            if(!insnNode1.desc.contains("[L")) {
                                mi = new MethodInfo();
                                mi.des = insnNode1.desc;
                                mi.name = insnNode1.name;
                                mi.owner = insnNode1.owner;
                                mi.isStatic = false;
                                if((index = methods.get(mi)) == null) throw new Error("找不到方法: " + mi.toString());

                                indexId = index;
                                hash1 = cr.getClassName().replace("/", ".").hashCode();
                                hash2 = fieldName.hashCode();

                                hash3 = Utils.r.nextInt();
                                hash4 = indexId ^ hash2 ^ hash1 ^ hash3;
                                // if(Main.useSOFCrasher) hash4 ^= SOFCrasherObfVistor.hashmap.get(cr.getClassName());

                                insnList.add(new InvokeDynamicInsnNode("XiaoShadiao", insnNode1.desc.replace("(", "(Ljava/lang/Object;"), new Handle(Opcodes.H_INVOKESTATIC, "XiaoShadiao", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"), new Object[] {
                                        encodedClassName,
                                        encodedFieldName,
                                        hash3,
                                        hash4
                                }));
                            } else insnList.add(insnNode1);
                            break;
                        default:
                            insnList.add(insnNode1);
                            break;
                    }
                    continue;
                }
                insnList.add(insnNode);
            }
        }
        initArray(cn);

        return classNodeToBytes(cn);
    }

    private void initArray(ClassNode cn) {
        // TODO 自动生成的方法存根
        if(!methods.isEmpty()) {

            MethodNode clinitNode = getOrCreateClinitNode(cn);
            InsnList insnList = new InsnList();

            cn.fields.add(new FieldNode(Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC, fieldName, fieldNameDesc, null, null));

            insnList.add(new LdcInsnNode(methods.size() + 1));
            insnList.add(new TypeInsnNode(Opcodes.ANEWARRAY, "XiaoShadiao"));

            for(Map.Entry<MethodInfo, Integer> entry : methods.entrySet()) {
                // System.out.println(arrayIndex);
                MethodInfo s = entry.getKey();

                insnList.add(new InsnNode(Opcodes.DUP));
                if(Config.visitorFollowCmdLineOrder) {
                    insnList.add(new LdcInsnNode(entry.getValue()));// mv2.visitLdcInsn(entry.getValue());
                } else {
                    NumberObfVisitor.visitInteger(entry.getValue(), insnList);
                }

                insnList.add(new TypeInsnNode(Opcodes.NEW, "XiaoShadiao"));
                insnList.add(new InsnNode(Opcodes.DUP));
                insnList.add(new LdcInsnNode(entry.getKey().isStatic ? "玩原神玩的" : "婉约婶婉徳"));
                for(String str : new String[] {entry.getKey().owner.replace("/", "."), entry.getKey().name, entry.getKey().des}) {
                    String className = Utils.getRandomMember(Config.exceptions).getName();

                    LabelNode l = new LabelNode();
                    int key = Utils.r.nextInt(126) + 1;
                    insnList.add(l);
                    insnList.add(new LineNumberNode(key, l));
                    insnList.add(new TypeInsnNode(Opcodes.NEW, className.replace(".", "/")));
                    insnList.add(new InsnNode(Opcodes.DUP));

                    {
                        char cc = 0, cc1;
                        char[] chars = str.toCharArray();
                        for(int i = 0;i < chars.length;i++) {
                            cc1 = chars[i];
                            chars[i] = (char) (chars[i] ^ ((cc ^ (key * (chars.length - i)) ^ className.charAt(i % className.length())) % 0x10000));
                            cc = cc1;
                        }
                        insnList.add(new LdcInsnNode(new String(chars)));
                    }

                    insnList.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, className.replace(".", "/"), "<init>", "(Ljava/lang/String;)V", false));
                }

                insnList.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, "XiaoShadiao", "<init>", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V", false));
                insnList.add(new InsnNode(Opcodes.AASTORE));
            }
            insnList.add(new FieldInsnNode(Opcodes.PUTSTATIC, cr.getClassName(), fieldName, fieldNameDesc));

            insnList.add(clinitNode.instructions);

            clinitNode.instructions = insnList;
        }

    }

    public static class MethodInfo {

        boolean isStatic;
        String owner, name, des;

        public String toString() {
            return (isStatic ? "static:" : "VIRTUAL:".toLowerCase()) + owner + ":" + name + des;
        }

        public boolean equals(Object o) {
            return o != null && this.toString().equals(o.toString());
        }

        public int hashCode() {
            return toString().hashCode();
        }

    }

}
