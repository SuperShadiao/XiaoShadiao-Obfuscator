package pers.XiaoShadiao.obfuscator.visitor;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.Collections;
import java.util.List;

public class SimpleStringObfVisitor extends AbstractVisitor {
    public SimpleStringObfVisitor(byte[] bytes, String[] args) {
        super(bytes, args);
    }

    @Override
    public byte[] transfer(byte[] bytes) {
        ClassNode cn = byteToClassNode(bytes);
        for (MethodNode method : cn.methods) {
            InsnList insnList = new InsnList();
            for (AbstractInsnNode insnNode : method.instructions) {
                if(insnNode instanceof LdcInsnNode) {
                    LdcInsnNode ldcInsnNode = (LdcInsnNode) insnNode;
                    if(ldcInsnNode.cst instanceof String) {
                        insnList.add(new LdcInsnNode(getEncodedString(ldcInsnNode.cst.toString())));
                        insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "XiaoShadiao", "原神启动", "(Ljava/lang/Object;)Ljava/lang/String;", false));
                        continue;
                    }
                }
                insnList.add(insnNode);
            }
            method.instructions = insnList;
        }
        return classNodeToBytes(cn);
    }

    @Override
    public List<String> getVisitorTags() {
        return Collections.emptyList();
    }
}
