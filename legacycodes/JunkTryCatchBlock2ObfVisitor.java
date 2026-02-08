import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.tree.analysis.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JunkTryCatchBlock2ObfVisitor {

    private static MethodNode clinit;
    private static String fieldName,
            randKey;

    public static byte[] transfer(byte[] bs) {
        return transfer(bs, false);
    }

    private static byte[] transfer(byte[] bs, boolean flag) {

        clinit = null;
        fieldName = Main.DIYRemapStringsFile == null ? Utils.spawnRandomChar(20, false) : Utils.getRandomMember(Main.remapStrings);
        randKey = Utils.spawnRandomChar(Main.r.nextInt(20) + 30, false);

        ClassReader cr = new ClassReader(bs);
        if((cr.getAccess() & (Opcodes.ACC_INTERFACE|Opcodes.ACC_ENUM)) != 0) return bs;

        ObfVisitor test = new ObfVisitor(cr) {};

        ClassNode cn = new ClassNode();
        cr.accept(cn, 0);

        if (test.isIn && !test.isEx) {
            cn.methods.stream().filter(m -> m.name.equals("<clinit>")).findFirst().ifPresent(m -> clinit = m);

            if (clinit == null) {
                clinit = new MethodNode(Opcodes.ACC_STATIC, "<clinit>", "()V", null, null);
                clinit.instructions.add(new InsnNode(Opcodes.RETURN));
                cn.methods.add(clinit);
            }
            cn.fields.add(new FieldNode(Opcodes.ACC_PRIVATE | Opcodes.ACC_STATIC | Opcodes.ACC_FINAL, fieldName, "Lpers/XiaoShadiao/NMSLException;", null, null));

            {
//			visitField(Opcodes.ACC_PRIVATE | Opcodes.ACC_STATIC | Opcodes.ACC_FINAL, fieldName, "Lpers/XiaoShadiao/NMSLException;", null, null);
//
//			// System.out.println("initClinitMethod " + cr.getClassName() + " " + fieldName + " " + clinit);
//			clinit.visitTypeInsn(Opcodes.NEW, "pers/XiaoShadiao/NMSLException");
//			clinit.visitInsn(Opcodes.DUP);
//			clinit.visitLdcInsn(randKey);
//			clinit.visitMethodInsn(Opcodes.INVOKESPECIAL, "pers/XiaoShadiao/NMSLException", "<init>", "(Ljava/lang/String;)V", false);
//
//			clinit.visitFieldInsn(Opcodes.PUTSTATIC, cr.getClassName(), fieldName, "Lpers/XiaoShadiao/NMSLException;");
                InsnList insnList = new InsnList();
                insnList.add(new TypeInsnNode(Opcodes.NEW, "pers/XiaoShadiao/NMSLException"));
                insnList.add(new InsnNode(Opcodes.DUP));
                insnList.add(new LdcInsnNode(randKey));
                insnList.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, "pers/XiaoShadiao/NMSLException", "<init>", "(Ljava/lang/String;)V", false));

                insnList.add(new FieldInsnNode(Opcodes.PUTSTATIC, cr.getClassName(), fieldName, "Lpers/XiaoShadiao/NMSLException;"));

                insnList.add(clinit.instructions);

                clinit.instructions = insnList;

            }

            for (MethodNode methodNode : cn.methods) {
                if (/*test.isEx || */(methodNode.access & Opcodes.ACC_ABSTRACT) != 0 || clinit == methodNode || methodNode.name.contains("init>") || (methodNode.name.equals("原神启动") && methodNode.desc.equals("(III)I")))
                    continue;

                transferMethod(cn, methodNode, flag);
            }
        }

        ClassWriter writer = new ClassWriter1(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        cn.accept(writer);

        MyCL cl = new MyCL();

        try {
            Class<?> define = cl.define(writer.toByteArray());
            define.getDeclaredFields();
        } catch(java.lang.NoClassDefFoundError e) {

        } catch(Throwable e) {
            if(flag) {
                System.out.println("Errored in try catch block obf, use other method");
                return transfer(bs, false);
            } else throw e;
        }

        return writer.toByteArray();

    }

    private static void transferMethod(ClassNode cn, MethodNode methodNode, boolean flag) {

        LabelNode labelThrow = new LabelNode();
        LabelNode labelThrow2 = new LabelNode();
        LabelNode labelThrowHandler2 = new LabelNode();
        LabelNode labelThrowHandler = new LabelNode();

        LabelNode label = new LabelNode();
        LabelNode handler = new LabelNode();

//		super.visitFieldInsn(Opcodes.GETSTATIC, cr.getClassName(), fieldName, "Lpers/XiaoShadiao/NMSLException;");
//		super.visitJumpInsn(Opcodes.GOTO, labelThrow);
//		super.visitLabel(labelThrowHandler2);
////						super.visitInsn(Opcodes.POP);
////						super.visitInsn(Opcodes.ACONST_NULL);
//		super.visitLabel(labelThrow);
//		super.visitInsn(Opcodes.DUP);
//		// super.visitFieldInsn(Opcodes.GETSTATIC, cr.getClassName(), fieldName, "Lpers/XiaoShadiao/NMSLException;");
//		super.visitInsn(Opcodes.ATHROW);
//		super.visitLabel(labelThrowHandler);
//		super.visitInsn(Opcodes.POP);
//		super.visitLabel(labelThrow2);
//		super.visitTryCatchBlock(labelThrow, labelThrowHandler, labelThrowHandler, "pers/XiaoShadiao/NMSLException");
//		super.visitTryCatchBlock(labelThrowHandler, labelThrow2, labelThrowHandler2, "pers/XiaoShadiao/NMSLException");

        InsnList insnList = new InsnList();
        insnList.add(new FieldInsnNode(Opcodes.GETSTATIC, cn.name, fieldName, "Lpers/XiaoShadiao/NMSLException;"));
        insnList.add(new JumpInsnNode(Opcodes.GOTO, labelThrow));
        insnList.add(labelThrowHandler2);
        insnList.add(labelThrow);
        insnList.add(new InsnNode(Opcodes.DUP));
        insnList.add(new InsnNode(Opcodes.ATHROW));
        insnList.add(labelThrowHandler);

//		insnList.add(new FieldInsnNode(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"));
//		insnList.add(new LdcInsnNode("-THROW POINT " + Main.r.nextInt(Integer.MAX_VALUE) +"-"));
//		insnList.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false));
//		super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Throwable", "getMessage", "()Ljava/lang/String;", false);
//		super.visitFieldInsn(Opcodes.GETSTATIC, cr.getClassName(), fieldName, "Lpers/XiaoShadiao/NMSLException;");
//		super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Throwable", "getMessage", "()Ljava/lang/String;", false);
//		super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "equals", "(Ljava/lang/Object;)Z", false);
//		super.visitJumpInsn(Opcodes.IFEQ, GOTO);
        LabelNode GOTO = new LabelNode();
        insnList.add(new InsnNode(Opcodes.DUP));
        insnList.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/Throwable", "getMessage", "()Ljava/lang/String;", false));
        insnList.add(new FieldInsnNode(Opcodes.GETSTATIC, cn.name, fieldName, "Lpers/XiaoShadiao/NMSLException;"));
        insnList.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/Throwable", "getMessage", "()Ljava/lang/String;", false));
        insnList.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/String", "equals", "(Ljava/lang/Object;)Z", false));
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

        for (AbstractInsnNode insnNode : methodNode.instructions) {

            if(Optional.ofNullable(frames[methodNode.instructions.indexOf(insnNode)]).map(Frame::getStackSize).orElse(-1) != 0) {
                insnList.add(insnNode);
                continue;
            }

            if (insnNode instanceof JumpInsnNode && insnNode.getOpcode() == Opcodes.GOTO) {
                LabelNode labelNode = ((JumpInsnNode) insnNode).label;

                LabelNode jump = new LabelNode();
                LabelNode thr = new LabelNode();
                LabelNode GOTO2 = new LabelNode();
                LabelNode throwPoint = new LabelNode();

                // insnList.add(new JumpInsnNode(insnNode.getOpcode(), thr));
                // insnList.add(new JumpInsnNode(Opcodes.GOTO, jump));
                insnList.add(thr);
                insnList.add(new FieldInsnNode(Opcodes.GETSTATIC, cn.name, fieldName, "Lpers/XiaoShadiao/NMSLException;"));
                insnList.add(throwPoint);
                throwPoints.add(throwPoint);
                insnList.add(new InsnNode(Opcodes.ATHROW));
                insnList.add(jump);

//				insnList.add(new FieldInsnNode(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"));
//				insnList.add(new LdcInsnNode("-THROW POINT " + Main.r.nextInt(Integer.MAX_VALUE) +"-"));
//				insnList.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false));
                methodNode.tryCatchBlocks.add(0, new TryCatchBlockNode(thr, jump, jump, "java/lang/Throwable"));

                insnList.add(new InsnNode(Opcodes.DUP));
                insnList.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/Throwable", "getMessage", "()Ljava/lang/String;", false));
                insnList.add(new FieldInsnNode(Opcodes.GETSTATIC, cn.name, fieldName, "Lpers/XiaoShadiao/NMSLException;"));
                insnList.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/Throwable", "getMessage", "()Ljava/lang/String;", false));
                insnList.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/String", "equals", "(Ljava/lang/Object;)Z", false));
                insnList.add(new JumpInsnNode(Opcodes.IFNE, GOTO2));

                // Math.random();
//				insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "java/lang/Math", "random", "()D", false));
//				insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "java/lang/Math", "random", "()D", false));
//				insnList.add(new InsnNode(Main.r.nextBoolean() ? Opcodes.DCMPL : Opcodes.DCMPG));
//				insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "java/lang/Math", "random", "()D", false));
//				insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "java/lang/Math", "random", "()D", false));
//				insnList.add(new InsnNode(Main.r.nextBoolean() ? Opcodes.DCMPL : Opcodes.DCMPG));

                if (flag) {
                    int random1 = -Math.abs(Main.r.nextInt(Integer.MAX_VALUE));
                    int random2 = Math.abs(Main.r.nextInt(Integer.MAX_VALUE));
                    LabelNode GOTO3 = new LabelNode();
                    insnList.add(new LdcInsnNode(random1));
                    insnList.add(new InsnNode(Opcodes.I2L));
                    insnList.add(new InsnNode(Opcodes.L2I));
                    insnList.add(new LdcInsnNode(random2));
                    insnList.add(new InsnNode(Opcodes.I2L));
                    insnList.add(new InsnNode(Opcodes.L2I));
                    insnList.add(new JumpInsnNode(Opcodes.IF_ICMPLE, GOTO3));

                    if(throwPoints.size() <= 2 || Main.r.nextBoolean()) {
                        // insnList.add(new InsnNode(Opcodes.ATHROW));
                        insnList.add(new JumpInsnNode(Opcodes.GOTO, labelThrowHandler2));
                    } else {
                        insnList.add(new JumpInsnNode(Opcodes.GOTO, throwPoints.get(Main.r.nextInt(throwPoints.size() - 1))));
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
}
