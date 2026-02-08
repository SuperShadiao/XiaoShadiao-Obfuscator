package pers.XiaoShadiao.obfuscator.visitor;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import pers.XiaoShadiao.obfuscator.utils.Utils;

public class SimpleJunkLabelObfVisitor extends CtrlFlowObfVisitor {
    public SimpleJunkLabelObfVisitor(byte[] bytes, String[] args) {
        super(bytes, args);
    }

    @Override
    public byte[] transfer(byte[] bytes) {
        ClassNode cn = byteToClassNode(bytes);

        for (MethodNode method : cn.methods) {
            if(isClinitNode(method)) continue;
            InsnList insnList = new InsnList();
            for (AbstractInsnNode insnNode : method.instructions) {
                if(insnNode instanceof IincInsnNode || (insnNode instanceof LdcInsnNode && ((LdcInsnNode) insnNode).cst instanceof String) || (insnNode instanceof TypeInsnNode && insnNode.getOpcode() == Opcodes.NEW)) addJunkLabelThrow(insnList);
                insnList.add(insnNode);
            }
        }

        return classNodeToBytes(cn);
    }
}
