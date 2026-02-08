package pers.XiaoShadiao.obfuscator.visitor;

import org.objectweb.asm.tree.*;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import pers.XiaoShadiao.obfuscator.utils.Utils;

import java.util.Collections;
import java.util.List;

public abstract class CtrlFlowObfVisitor extends AbstractVisitor {
    public CtrlFlowObfVisitor(ClassReader cr, String[] args) {
        super(cr, args);
    }

    public CtrlFlowObfVisitor(byte[] bytes, String[] args) {
        super(bytes, args);
    }

    public static void addJunkLabelThrow(InsnList insnList) {

        LabelNode labelIf = new LabelNode();
        int token;
        if(true) {
            token = Utils.r.nextInt();
            insnList.add(new LdcInsnNode(token));
        } else {
            String s = Utils.spawnRandomChar(5, false);
            insnList.add(new LdcInsnNode(s));
            insnList.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/String", "hashCode", "()I", false));
            token = s.hashCode();
        }

        if(token > 0) {
            insnList.add(new InsnNode(Opcodes.ICONST_0));

            insnList.add(new JumpInsnNode(Opcodes.IF_ICMPGE, labelIf));

            if (Utils.r.nextBoolean()) {
                visitThrow(insnList);
            } else {
                visitThrowNull(insnList);
            }

            insnList.add(labelIf);
        } else if(token < 0) {
            insnList.add(new InsnNode(Opcodes.ICONST_0));

            insnList.add(new JumpInsnNode(Opcodes.IF_ICMPLE, labelIf));

            if (Utils.r.nextBoolean()) {
                visitThrow(insnList);
            } else {
                visitThrowNull(insnList);
            }

            insnList.add(labelIf);
        } else if(token == 0) {
            insnList.add(new InsnNode(Opcodes.ICONST_0));

            insnList.add(new JumpInsnNode(Opcodes.IF_ICMPEQ, labelIf));

            if (Utils.r.nextBoolean()) {
                visitThrow(insnList);
            } else {
                visitThrowNull(insnList);
            }

            insnList.add(labelIf);

        }

    }

    public static void visitThrow(InsnList insnList) {
        insnList.add(new TypeInsnNode(Opcodes.NEW, "pers/XiaoShadiao/NMSLException"));
        insnList.add(new InsnNode(Opcodes.DUP));
        insnList.add(new LdcInsnNode("\u73a9\u539f\u795e\u73a9\u7684"));
        insnList.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, "pers/XiaoShadiao/NMSLException", "<init>", "(Ljava/lang/String;)V", false));

        insnList.add(new InsnNode(Opcodes.ATHROW));
    }

    public static void visitThrowNull(InsnList insnList) {
        insnList.add(new InsnNode(Opcodes.ACONST_NULL));
        insnList.add(new InsnNode(Opcodes.ATHROW));
    }

    @Override
    public List<String> getVisitorTags() {
        return Collections.emptyList();
    }
}
