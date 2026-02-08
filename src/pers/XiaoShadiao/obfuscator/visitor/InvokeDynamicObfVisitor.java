package pers.XiaoShadiao.obfuscator.visitor;

import org.objectweb.asm.Handle;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import pers.XiaoShadiao.obfuscator.utils.Utils;

import java.util.Collections;
import java.util.List;

public class InvokeDynamicObfVisitor extends AbstractVisitor {
    public InvokeDynamicObfVisitor(byte[] bytes, String[] args) {
        super(bytes, args);
    }

    @Override
    public byte[] transfer(byte[] bytes) {
        ClassNode cn = byteToClassNode(bytes);

        if((cn.access | Opcodes.ACC_SYNTHETIC) == cn.access || (cn.access | Opcodes.ACC_ENUM) == cn.access) return classNodeToBytes(cn);
        for (MethodNode method : cn.methods) {
            InsnList insnList = new InsnList();
            for (AbstractInsnNode insnNode : method.instructions) {
                if(insnNode instanceof MethodInsnNode) {
                    MethodInsnNode insnNode1 = (MethodInsnNode) insnNode;

                    int key2;
                    int key = key2 = Utils.r.nextInt();

                    char[] charKey,charOwner,charName,charDescriptor;

                    charKey = new char[key % 10 + 10];
                    for(int i = 0;i < charKey.length;i++) {
                        key += (key * (key >> 5) + (key << 5)) + 114514;
                        charKey[i] = (char) (key % 0x10000);
                    }
                    charOwner = (insnNode1.owner.replace("/", ".")).toCharArray();
                    charName = insnNode1.name.toCharArray();
                    charDescriptor = insnNode1.desc.toCharArray();

                    for(int i = 0;i < charOwner.length;i++) {
                        charOwner[i] = (char) (charOwner[i] ^ charKey[i % charKey.length]);
                    }
                    for(int i = 0;i < charName.length;i++) {
                        charName[i] = (char) (charName[i] ^ charKey[i % charKey.length]);
                    }
                    for(int i = 0;i < charDescriptor.length;i++) {
                        charDescriptor[i] = (char) (charDescriptor[i] ^ charKey[i % charKey.length]);
                    }

                    switch(insnNode1.getOpcode()) {
                        case Opcodes.INVOKESTATIC:
                            insnList.add(new InvokeDynamicInsnNode("XiaoShadiao", insnNode1.desc, new Handle(Opcodes.H_INVOKESTATIC, "XiaoShadiao", "最喜欢玩原神了", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"), new Object[] {"玩原神玩的", key2, new String(charOwner), new String(charName), new String(charDescriptor)}));
                            break;
                        case Opcodes.INVOKEINTERFACE:
                        case Opcodes.INVOKEVIRTUAL:
                            if(!insnNode1.desc.contains("[L")) {
                                insnList.add(new InvokeDynamicInsnNode("XiaoShadiao", insnNode1.desc.replace("(", "(Ljava/lang/Object;"), new Handle(Opcodes.H_INVOKESTATIC, "XiaoShadiao", "最喜欢玩原神了", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"), new Object[] {"婉约婶婉徳", key2, new String(charOwner), new String(charName), new String(charDescriptor)}));
                            } else insnList.add(insnNode);
                            break;
                        default:
                            insnList.add(insnNode);
                            break;
                    }
                    continue;
                }
                insnList.add(insnNode);
            }

        }

        return classNodeToBytes(cn);
    }

    @Override
    public List<String> getVisitorTags() {
        return Collections.emptyList();
    }
}
