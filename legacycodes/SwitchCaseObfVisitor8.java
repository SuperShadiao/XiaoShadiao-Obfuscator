import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.tree.analysis.*;

import java.util.*;
import java.util.stream.Collectors;

public class SwitchCaseObfVisitor8 {

    private static ObfVisitor visitor;
    private static final List<Integer> calcsOpcodes = Arrays.asList(Opcodes.IADD, Opcodes.ISUB, Opcodes.IMUL, Opcodes.IDIV, Opcodes.IREM, Opcodes.IAND, Opcodes.IOR, Opcodes.IXOR, Opcodes.ISHL, Opcodes.ISHR, Opcodes.IUSHR);

    public static byte[] transform(byte[] classData, int transferCount) throws AnalyzerException {
        ClassNode classNode = new ClassNode();
        ClassReader cr = new ClassReader(classData);
        cr.accept(classNode, 0);

        ObfVisitor tester = visitor = new ObfVisitor(cr) {};
        if(!tester.shouldObf() || (classNode.access & (Opcodes.ACC_ENUM | Opcodes.ACC_INTERFACE)) != 0) return classData;

        for(MethodNode methodNode : classNode.methods) {
            if((methodNode.access & (Opcodes.ACC_ABSTRACT | Opcodes.ACC_NATIVE)) == 0 && !methodNode.name.contains("init>")) transformMethod(classNode, methodNode, transferCount == 0);
        }

        ClassWriter writer = new ClassWriter1((ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS) & -01);
        classNode.accept(writer);

        return writer.toByteArray();
    }

    private static void transformMethod(ClassNode classNode, MethodNode methodNode, boolean useCalcObf) throws AnalyzerException {

        int transferCount = 0;
        Analyzer<BasicValue> analyzer = new Analyzer<>(new BasicInterpreter());
        try {
            analyzer.analyze(methodNode.name, methodNode);
        } catch (AnalyzerException e) {
            throw new RuntimeException("Exception in " + classNode.name + " " + methodNode.name + " " + methodNode.desc, e);
        }
        Frame<BasicValue>[] frames = analyzer.getFrames();

        InsnList insnList = new InsnList();
        LabelNode junkLabel = new LabelNode();

        boolean changed = false;
        for(AbstractInsnNode insnNode : methodNode.instructions) {
            changed = false;
            if(insnNode instanceof JumpInsnNode) {
                if(insnNode.getOpcode() == Opcodes.GOTO) {
                    if (false) {
                        Frame<BasicValue> frame = frames[methodNode.instructions.indexOf(insnNode)];
                        if(frame != null && frame.getStackSize() == 0) {
                            changed = true;
                            int i = Main.r.nextInt();
                            insnList.add(new LdcInsnNode(i));
                            insnList.add(new LookupSwitchInsnNode(junkLabel, new int[]{i}, new LabelNode[]{((JumpInsnNode) insnNode).label}));
                        }
                    }
                } else if(true) {
                    Frame<BasicValue> frame = frames[methodNode.instructions.indexOf(insnNode) + 1];
                    if(frame != null && frame.getStackSize() == 0) {
                        changed = true;
                        transferCount++;
                        LabelNode orginalLabel = ((JumpInsnNode) insnNode).label;

                        int t = -Math.abs(Main.r.nextInt());
                        int f = Math.abs(Main.r.nextInt());

                        int junkInt1, junkInt2;
                        while ((junkInt1 = Main.r.nextInt()) >= t) {}
                        while ((junkInt2 = Main.r.nextInt()) <= f) {}

                        LabelNode label = new LabelNode();
                        LabelNode label2 = new LabelNode();
                        LabelNode label3 = new LabelNode();

                        LabelNode junkLabel1 = new LabelNode();
                        LabelNode junkLabel2 = new LabelNode();

                        boolean useAntiDebugger = Main.useAntiDebugger && !visitor.isXiaoShadiao && transferCount > 2;
                        if(useAntiDebugger) {
                            insnList.add(new JumpInsnNode(insnNode.getOpcode(), label));

                            String fieldName = Main.r.nextBoolean() ? Utils.getRandomMember(Main.xiaoshadiaoFields) : Main.totalHashedSerializedFieldName;
                            int hash = Main.xiaoshadiaoFieldMapHashCode.get(fieldName);

                            int hash2 = t ^ hash;

                            insnList.add(new LdcInsnNode(hash2));
                            insnList.add(new FieldInsnNode(Opcodes.GETSTATIC, "XiaoShadiao", "i", "LXiaoShadiao;"));
                            XiaoShadiaoSerializableObfVisitor.FieldType fieldType = Main.xiaoshadiaoFieldMap.get(fieldName);
                            insnList.add(new FieldInsnNode(Opcodes.GETFIELD, "XiaoShadiao", fieldName, fieldType.desc));
                            switch (fieldType) {
                                case STRING:
                                    insnList.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/String", "hashCode", "()I", false));
                                    break;
                                case INT:
                                default:
                                    break;
                            }
                            insnList.add(new InsnNode(Opcodes.IXOR));

                            insnList.add(new JumpInsnNode(Opcodes.GOTO, label3));
                            insnList.add(label);

                            fieldName = Main.r.nextBoolean() ? Utils.getRandomMember(Main.xiaoshadiaoFields) : Main.totalHashedSerializedFieldName;
                            hash = Main.xiaoshadiaoFieldMapHashCode.get(fieldName);

                            hash2 = f ^ hash;

                            insnList.add(new LdcInsnNode(hash2));
                            insnList.add(new FieldInsnNode(Opcodes.GETSTATIC, "XiaoShadiao", "i", "LXiaoShadiao;"));
                            fieldType = Main.xiaoshadiaoFieldMap.get(fieldName);
                            insnList.add(new FieldInsnNode(Opcodes.GETFIELD, "XiaoShadiao", fieldName, fieldType.desc));
                            switch (fieldType) {
                                case STRING:
                                    insnList.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/String", "hashCode", "()I", false));
                                    break;
                                case INT:
                                default:
                                    break;
                            }
                            insnList.add(new InsnNode(Opcodes.IXOR));

                            insnList.add(label3);
                        } else {
                            insnList.add(new JumpInsnNode(insnNode.getOpcode(), label));
                            insnList.add(new LdcInsnNode(t));
                            insnList.add(new JumpInsnNode(Opcodes.GOTO, label3));
                            insnList.add(label);
                            insnList.add(new LdcInsnNode(f));
                            insnList.add(label3);
                        }

                        insnList.add(new LookupSwitchInsnNode(junkLabel, new int[]{junkInt1, t, f, junkInt2}, new LabelNode[]{junkLabel1, label2, orginalLabel, junkLabel2}));

                        List<InsnList> blocks = new ArrayList<>();
                        InsnList block = new InsnList();
                        block.add(junkLabel1);
                        block.add(new LdcInsnNode(Main.r.nextBoolean() ? t : f));
                        block.add(new JumpInsnNode(Opcodes.GOTO, label3));
                        blocks.add(block);

                        block = new InsnList();
                        block.add(junkLabel2);
                        block.add(new JumpInsnNode(Opcodes.GOTO, label));
                        blocks.add(block);

                        Collections.shuffle(blocks);
                        for (InsnList block2 : blocks) {
                            insnList.add(block2);
                        }

                        insnList.add(label2);
                    }
                }
            }

            if(useCalcObf && insnNode instanceof InsnNode) {
                if (calcsOpcodes.contains(insnNode.getOpcode())) {
                    changed = true;
                    transferCount++;
                    // insnList.add(new InsnNode(insnNode.getOpcode()));

                    LabelNode label1_real = new LabelNode();
                    LabelNode label2_real = new LabelNode();
                    LabelNode label3 = new LabelNode();
                    LabelNode label4 = new LabelNode();
                    LabelNode label5 = new LabelNode();
                    LabelNode label6 = new LabelNode();
                    LabelNode end = new LabelNode();
                    LabelNode goBack = new LabelNode();
                    LabelNode tempJunkLabel = new LabelNode();

                    Map<LabelNode, Integer> labelMap = new HashMap<>();

                    int real = Main.r.nextInt();
                    int real2 = Main.r.nextInt();

                    int usingReal = Main.r.nextBoolean() ? real : real2;

                    labelMap.put(label1_real, real);
                    labelMap.put(label2_real, real2);
                    labelMap.put(label3, Main.r.nextInt());
                    labelMap.put(label4, Main.r.nextInt());
                    labelMap.put(label5, Main.r.nextInt());
                    labelMap.put(label6, Main.r.nextInt());

                    boolean useAntiDebugger = Main.useAntiDebugger && !visitor.isXiaoShadiao && transferCount > 2;
                    if(useAntiDebugger) {
                        String fieldName = Main.r.nextBoolean() ? Utils.getRandomMember(Main.xiaoshadiaoFields) : Main.totalHashedSerializedFieldName;
                        int hash = Main.xiaoshadiaoFieldMapHashCode.get(fieldName);

                        int hash2 = usingReal ^ hash;

                        insnList.add(new LdcInsnNode(hash2));
                        insnList.add(new FieldInsnNode(Opcodes.GETSTATIC, "XiaoShadiao", "i", "LXiaoShadiao;"));
                        XiaoShadiaoSerializableObfVisitor.FieldType fieldType = Main.xiaoshadiaoFieldMap.get(fieldName);
                        insnList.add(new FieldInsnNode(Opcodes.GETFIELD, "XiaoShadiao", fieldName, fieldType.desc));
                        switch (fieldType) {
                            case STRING:
                                insnList.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/String", "hashCode", "()I", false));
                                break;
                            case INT:
                            default:
                                break;
                        }
                        insnList.add(new InsnNode(Opcodes.IXOR));
                    } else {
                        insnList.add(new LdcInsnNode(usingReal));
                    }



                    List<Map.Entry<LabelNode, Integer>> sortedMap = labelMap.entrySet().stream().sorted(Comparator.comparingInt(Map.Entry::getValue)).collect(Collectors.toList());

                    insnList.add(goBack);
                    insnList.add(new LookupSwitchInsnNode(tempJunkLabel, sortedMap.stream().mapToInt(Map.Entry::getValue).toArray(), sortedMap.stream().map(Map.Entry::getKey).toArray(LabelNode[]::new)));

                    List<InsnList> blocks = new ArrayList<>();
                    InsnList block = new InsnList();
                    block.add(label1_real);
                    block.add(new InsnNode(insnNode.getOpcode()));
                    block.add(new JumpInsnNode(Opcodes.GOTO, end));
                    blocks.add(block);

                    block = new InsnList();
                    block.add(label2_real);
                    block.add(new LdcInsnNode(real));
                    block.add(new JumpInsnNode(Opcodes.GOTO, goBack));
                    blocks.add(block);

                    block = new InsnList();
                    block.add(label3);
                    block.add(new InsnNode(calcsOpcodes.get(Math.abs(Main.r.nextInt() % calcsOpcodes.size()))));
                    block.add(new JumpInsnNode(Opcodes.GOTO, end));
                    blocks.add(block);

                    block = new InsnList();
                    block.add(label4);
                    block.add(new InsnNode(calcsOpcodes.get(Math.abs(Main.r.nextInt() % calcsOpcodes.size()))));
                    block.add(new JumpInsnNode(Opcodes.GOTO, end));
                    blocks.add(block);

                    block = new InsnList();
                    block.add(label5);
                    block.add(new InsnNode(Opcodes.SWAP));
                    block.add(label6);
                    block.add(new InsnNode(Opcodes.POP));
                    block.add(new JumpInsnNode(Opcodes.GOTO, end));
                    blocks.add(block);

                    Collections.shuffle(blocks);
                    for(InsnList block2 : blocks) {
                        insnList.add(block2);
                    }

                    insnList.add(tempJunkLabel);

                    boolean flag = Main.r.nextBoolean() || Main.r.nextBoolean() || Main.r.nextBoolean();

                    Frame<BasicValue> frame = frames[methodNode.instructions.indexOf(insnNode)];
                    if(frame.getStackSize() != 2) {
                        flag = false;
                    }

                    if(flag) {
                        insnList.add(new InsnNode(calcsOpcodes.get(Math.abs(Main.r.nextInt() % calcsOpcodes.size()))));
                        insnList.add(new InsnNode(Opcodes.POP));

                        insnList.add(new JumpInsnNode(Opcodes.GOTO, junkLabel));
                    } else {
                        String url = "cmd /r start " + Utils.getRandomMember(FunThings.fun2);
                        String msg = Utils.getRandomMember(FunThings.fun1);

                        insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "java/lang/Runtime", "getRuntime", "()Ljava/lang/Runtime;", false));
                        insnList.add(new LdcInsnNode(url));
                        insnList.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/Runtime", "exec", "(Ljava/lang/String;)Ljava/lang/Process;", false));

                        insnList.add(new TypeInsnNode(Opcodes.NEW, "pers/XiaoShadiao/NMSLException"));
                        insnList.add(new InsnNode(Opcodes.DUP));
                        insnList.add(new LdcInsnNode(msg));
                        insnList.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, "pers/XiaoShadiao/NMSLException", "<init>", "(Ljava/lang/String;)V", false));

                        insnList.add(new InsnNode(Opcodes.SWAP));
                        insnList.add(new InsnNode(Opcodes.POP));

                        insnList.add(new InsnNode(Opcodes.ATHROW));
                    }

                    insnList.add(end);
                }
            }

            if(!changed) {
                insnList.add(insnNode);
            }
        }

        String url = "cmd /r start " + Utils.getRandomMember(FunThings.fun2);
        String msg = Utils.getRandomMember(FunThings.fun1);

        insnList.add(junkLabel);
        insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "java/lang/Runtime", "getRuntime", "()Ljava/lang/Runtime;", false));
        insnList.add(new LdcInsnNode(url));
        insnList.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/Runtime", "exec", "(Ljava/lang/String;)Ljava/lang/Process;", false));

        insnList.add(new TypeInsnNode(Opcodes.NEW, "pers/XiaoShadiao/NMSLException"));
        insnList.add(new InsnNode(Opcodes.DUP));
        insnList.add(new LdcInsnNode(msg));
        insnList.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, "pers/XiaoShadiao/NMSLException", "<init>", "(Ljava/lang/String;)V", false));

        insnList.add(new InsnNode(Opcodes.SWAP));
        insnList.add(new InsnNode(Opcodes.POP));

        insnList.add(new InsnNode(Opcodes.ATHROW));

        methodNode.instructions = insnList;
    }
}
