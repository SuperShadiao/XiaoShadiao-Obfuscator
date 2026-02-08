package pers.XiaoShadiao.obfuscator.visitor;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.tree.analysis.*;
import pers.XiaoShadiao.obfuscator.Main;
import pers.XiaoShadiao.obfuscator.utils.ClassWriter1;
import pers.XiaoShadiao.obfuscator.utils.Utils;
import pers.XiaoShadiao.obfuscator.utils.customclassloader.ClassVerifyer;
import pers.XiaoShadiao.obfuscator.utils.customclassloader.VerifyerClassLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JunkTryCatchBlockObfVisitor extends CtrlFlowObfVisitor {
    public JunkTryCatchBlockObfVisitor(byte[] bytes, String[] args) {
        super(bytes, args);
    }

    @Override
    public byte[] transfer(byte[] bytes) {
        return transfer(bytes, true);
    }

    private byte[] transfer(byte[] bytes, boolean flag) {
        ClassNode cn = byteToClassNode(bytes);

        if ((cn.access & (Opcodes.ACC_INTERFACE | Opcodes.ACC_ENUM)) != 0) return classNodeToBytes(cn);

        MethodNode clinit = getOrCreateClinitNode(cn);
        String fieldName = Utils.getRandomNameFromMap();
        String randKey = Utils.spawnRandomChar(Utils.r.nextInt(20) + 30, false);

        for (MethodNode methodNode : cn.methods) {
            if ((methodNode.access & Opcodes.ACC_ABSTRACT) != 0 || (methodNode.access & Opcodes.ACC_NATIVE) != 0 || methodNode.name.equals("<init>") || (methodNode.name.equals("原神启动") && methodNode.desc.equals("(III)I")))
                continue;

            LabelNode labelThrow = new LabelNode();
            LabelNode labelThrow2 = new LabelNode();
            LabelNode labelThrowHandler2 = new LabelNode();
            LabelNode labelThrowHandler = new LabelNode();

            LabelNode label = new LabelNode();

            InsnList insnList = new InsnList();
            insnList.add(new FieldInsnNode(Opcodes.GETSTATIC, cn.name, fieldName, "Lpers/XiaoShadiao/NMSLException;"));
            insnList.add(new JumpInsnNode(Opcodes.GOTO, labelThrow));
            insnList.add(labelThrowHandler2);
            insnList.add(labelThrow);
            insnList.add(new InsnNode(Opcodes.DUP));
            insnList.add(new InsnNode(Opcodes.ATHROW));
            insnList.add(labelThrowHandler);

            LabelNode GOTO = new LabelNode();
            insnList.add(new InsnNode(Opcodes.DUP));
            // insnList.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/Throwable", "getMessage", "()Ljava/lang/String;", false));
            insnList.add(new FieldInsnNode(Opcodes.GETSTATIC, cn.name, fieldName, "Lpers/XiaoShadiao/NMSLException;"));
            // insnList.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/Throwable", "getMessage", "()Ljava/lang/String;", false));
            // insnList.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/String", "equals", "(Ljava/lang/Object;)Z", false));
            insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "java/util/Objects", "equals", "(Ljava/lang/Object;Ljava/lang/Object;)Z", false));
            insnList.add(new JumpInsnNode(Opcodes.IFNE, GOTO));
            insnList.add(new InsnNode(Opcodes.ATHROW));

            insnList.add(labelThrow2);
            methodNode.tryCatchBlocks.add(0, new TryCatchBlockNode(labelThrow, labelThrowHandler, labelThrowHandler, "java/lang/Throwable"));
            methodNode.tryCatchBlocks.add(0, new TryCatchBlockNode(labelThrowHandler, labelThrow2, labelThrowHandler2, "java/lang/Throwable"));

            insnList.add(GOTO);
            insnList.add(new InsnNode(Opcodes.POP));
            insnList.add(label);

            List<LabelNode> throwPoints = new ArrayList<>();

            Analyzer<BasicValue> analyzer = new Analyzer<>(new BasicInterpreter());
            try {
                analyzer.analyze(methodNode.name, methodNode);
            } catch (AnalyzerException e) {
                throw new RuntimeException("Exception in " + cn.name + " " + methodNode.name + " " + methodNode.desc, e);
            }

            Frame<BasicValue>[] frames = analyzer.getFrames();

            // int counter = 0;
            for (AbstractInsnNode insnNode : methodNode.instructions) {

                if (Optional.ofNullable(frames[methodNode.instructions.indexOf(insnNode)]).map(Frame::getStackSize).orElse(-1) != 0) {
                    insnList.add(insnNode);
                    continue;
                }

                if (insnNode instanceof JumpInsnNode && insnNode.getOpcode() == Opcodes.GOTO/* && counter-- <= 0*/) {
                    // counter = 5;
                    LabelNode labelNode = ((JumpInsnNode) insnNode).label;

                    LabelNode jump = new LabelNode();
                    LabelNode thr = new LabelNode();
                    LabelNode GOTO2 = new LabelNode();
                    LabelNode throwPoint = new LabelNode();

                    insnList.add(thr);
                    insnList.add(new FieldInsnNode(Opcodes.GETSTATIC, cn.name, fieldName, "Lpers/XiaoShadiao/NMSLException;"));
                    insnList.add(throwPoint);
                    throwPoints.add(throwPoint);
                    insnList.add(new InsnNode(Opcodes.ATHROW));
                    insnList.add(jump);

                    methodNode.tryCatchBlocks.add(0, new TryCatchBlockNode(thr, jump, jump, "java/lang/Throwable"));

                    insnList.add(new InsnNode(Opcodes.DUP));
                    // insnList.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/Throwable", "getMessage", "()Ljava/lang/String;", false));
                    insnList.add(new FieldInsnNode(Opcodes.GETSTATIC, cn.name, fieldName, "Lpers/XiaoShadiao/NMSLException;"));
                    // insnList.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/Throwable", "getMessage", "()Ljava/lang/String;", false));
                    // insnList.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/String", "equals", "(Ljava/lang/Object;)Z", false));
                    insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "java/util/Objects", "equals", "(Ljava/lang/Object;Ljava/lang/Object;)Z", false));
                    insnList.add(new JumpInsnNode(Opcodes.IFNE, GOTO2));

                    if (flag) {
                        int random1 = -Math.abs(Utils.r.nextInt(Integer.MAX_VALUE));
                        int random2 = Math.abs(Utils.r.nextInt(Integer.MAX_VALUE));
                        LabelNode GOTO3 = new LabelNode();
                        insnList.add(new LdcInsnNode(random1));
                        insnList.add(new InsnNode(Opcodes.I2L));
                        insnList.add(new InsnNode(Opcodes.L2I));
                        insnList.add(new LdcInsnNode(random2));
                        insnList.add(new InsnNode(Opcodes.I2L));
                        insnList.add(new InsnNode(Opcodes.L2I));
                        insnList.add(new JumpInsnNode(Opcodes.IF_ICMPLE, GOTO3));

                        if (throwPoints.size() <= 2 || Utils.r.nextBoolean()) {
                            insnList.add(new JumpInsnNode(Opcodes.GOTO, labelThrowHandler2));
                        } else {
                            insnList.add(new JumpInsnNode(Opcodes.GOTO, throwPoints.get(Utils.r.nextInt(throwPoints.size() - 1))));
                        }
                        insnList.add(GOTO3);
                    }
                    insnList.add(new InsnNode(Opcodes.ATHROW));

                    insnList.add(GOTO2);
                    insnList.add(new InsnNode(Opcodes.POP));
                    insnList.add(new JumpInsnNode(Opcodes.GOTO, labelNode));
                } else {
                    insnList.add(insnNode);
                }
            }

            methodNode.instructions = insnList;
        }

        cn.fields.add(new FieldNode(Opcodes.ACC_PRIVATE | Opcodes.ACC_STATIC | Opcodes.ACC_FINAL, fieldName, "Lpers/XiaoShadiao/NMSLException;", null, null));

        {
            InsnList insnList = new InsnList();
            insnList.add(new TypeInsnNode(Opcodes.NEW, "pers/XiaoShadiao/NMSLException"));
            insnList.add(new InsnNode(Opcodes.DUP));
            insnList.add(new LdcInsnNode(randKey));
            insnList.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, "pers/XiaoShadiao/NMSLException", "<init>", "(Ljava/lang/String;)V", false));

            insnList.add(new FieldInsnNode(Opcodes.PUTSTATIC, cr.getClassName(), fieldName, "Lpers/XiaoShadiao/NMSLException;"));

            insnList.add(clinit.instructions);

            clinit.instructions = insnList;
        }

        byte[] bytes2 = null;
        try {
            bytes2 = classNodeToBytes(cn);
            ClassVerifyer.verify(bytes2);
        } catch (NoClassDefFoundError e) {

        } catch (Throwable e) {
            if (flag) {
                // System.out.println("Errored in try catch block obf, use other method");
                return transfer(bytes, false);
            } else throw e;
        }

        return bytes2;
    }
}
