import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.tree.analysis.*;

import java.util.*;
import java.util.stream.Collectors;

public class SwitchCaseObfVisitor6 {

    public static byte[] transform(byte[] classData) throws AnalyzerException {
        ClassNode classNode = new ClassNode();
        ClassReader cr = new ClassReader(classData);
        cr.accept(classNode, 0);

        if(!new ObfVisitor(cr) {}.shouldObf() || (classNode.access & (Opcodes.ACC_ENUM | Opcodes.ACC_INTERFACE)) != 0) return classData;

        for(MethodNode methodNode : classNode.methods) {
            if((methodNode.access & (Opcodes.ACC_ABSTRACT | Opcodes.ACC_NATIVE)) == 0 && !methodNode.name.contains("init>")) transformMethod(classNode, methodNode);
        }

        ClassWriter writer = new ClassWriter1((ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS) & -01);
        classNode.accept(writer);

        return writer.toByteArray();
    }

    private static void transformMethod(ClassNode classNode, MethodNode methodNode) {

        addMoreGoto(classNode, methodNode);

        Analyzer<BasicValue> analyzer = new Analyzer<>(new BasicInterpreter());
        try {
            analyzer.analyze(methodNode.name, methodNode);
        } catch (AnalyzerException e) {
            throw new RuntimeException(e);
        }
        Frame<BasicValue>[] frames = analyzer.getFrames();

        InsnList insnList = new InsnList();

        int variableIndex = 0;
        List<Map.Entry<String, Integer>> variableIndexes = new ArrayList<>();
        for(AbstractInsnNode insnNode : methodNode.instructions) {
            if(insnNode instanceof VarInsnNode) {
                variableIndex = Math.max(((VarInsnNode) insnNode).var, variableIndex);

                int opcode = insnNode.getOpcode();
                if(
                        opcode == Opcodes.ISTORE ||
                                opcode == Opcodes.LSTORE ||
                                opcode == Opcodes.FSTORE ||
                                opcode == Opcodes.DSTORE ||
                                opcode == Opcodes.ASTORE
                ) {
                    insnList.add(new EmptyFlagInsnNode());
                }

            }
            insnList.add(insnNode);
        }
        variableIndex += 2;
        methodNode.instructions = insnList;

        try {
            analyzer.analyze(methodNode.name, methodNode);
        } catch (AnalyzerException e) {
            throw new RuntimeException(e);
        }

//        List<LabelNode> list = new ArrayList<>();
//
//        List<LabelNode> gotolist = new ArrayList<>();
//        Map<LabelNode, Integer> gotolistMap = new HashMap<>();
//
//        for(AbstractInsnNode insnNode : methodNode.instructions) {
//            if(insnNode instanceof LabelNode) list.add((LabelNode) insnNode);
//            if(insnNode instanceof JumpInsnNode) {
//                if(true||insnNode.getOpcode() == Opcodes.GOTO) {
//                    Frame<BasicValue> frame = frames[methodNode.instructions.indexOf(insnNode)];
//                    if(frame != null && frame.getStackSize() == 0) gotolist.add(((JumpInsnNode) insnNode).label);
//                    else if(frame != null) {
////                        System.out.println("Try to add block " + ((JumpInsnNode) insnNode).label + " to gotolist, but stack size is " + frame.getStackSize());
////                        System.out.println(frame);
//                    }
//                }
//            }
//        }
//        for(LabelNode labelNode : gotolist) {
//            gotolistMap.put(labelNode, Main.r.nextInt());
//        }
//
//        LabelNode defaultBlock = new LabelNode();
//        LabelNode startBlock = new LabelNode();
//        int startInt = Main.r.nextInt();
//        gotolistMap.put(startBlock, startInt);
//        LabelNode loopBlock = new LabelNode();
//
//        int[] keys = new int[gotolistMap.size()];
//        LabelNode[] labels = new LabelNode[gotolistMap.size()];
//        final int[] i = {0};
//        gotolistMap.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEach(entry -> {
//            keys[i[0]] = entry.getValue();
//            labels[i[0]] = entry.getKey();
//            i[0]++;
//        });
//
//        insnList = new InsnList();
//        int initVariableCount = Type.getMethodType(methodNode.desc).getArgumentTypes().length;
//        if((methodNode.access & Opcodes.ACC_STATIC) == 0) initVariableCount += 1;
//
//        insnList.add(new LdcInsnNode(startInt));
//        insnList.add(new VarInsnNode(Opcodes.ISTORE, variableIndex));
//
//        insnList.add(loopBlock);
//        insnList.add(new VarInsnNode(Opcodes.ILOAD, variableIndex));
//        insnList.add(new LookupSwitchInsnNode(defaultBlock, keys, labels));
//
//        insnList.add(startBlock);
//
//        for (AbstractInsnNode insnNode : methodNode.instructions) {
//            if(insnNode instanceof LabelNode) {
//                if(list.contains(insnNode)) insnList.add(insnNode); else throw new IllegalStateException("NO LABEL");
//            } else if(insnNode instanceof JumpInsnNode && /*insnNode.getOpcode() == Opcodes.GOTO && */gotolist.contains(((JumpInsnNode) insnNode).label)) {
//
//                insnList.add(new LdcInsnNode(gotolistMap.get(((JumpInsnNode) insnNode).label)));
//                insnList.add(new VarInsnNode(Opcodes.ISTORE, variableIndex));
//                insnList.add(new JumpInsnNode(insnNode.getOpcode(), loopBlock));
//
//            } else insnList.add(insnNode);
//        }
//
//        insnList.add(defaultBlock);
//        insnList.add(new InsnNode(Opcodes.ACONST_NULL));
//        insnList.add(new InsnNode(Opcodes.ATHROW));

        List<List<LabelNode>> list = new ArrayList<>();

        List<List<LabelNode>> gotolist = new ArrayList<>();
        List<Map<LabelNode, Integer>> gotolistMap = new ArrayList<>();

        List<LabelNode> tempList = new ArrayList<>();
        List<LabelNode> tempGotolist = new ArrayList<>();

        for(AbstractInsnNode insnNode : methodNode.instructions) {

            if(insnNode instanceof LabelNode) tempList.add((LabelNode) insnNode);
            if(insnNode instanceof JumpInsnNode) {
                if(true||insnNode.getOpcode() == Opcodes.GOTO) {
                    Frame<BasicValue> frame = frames[methodNode.instructions.indexOf(insnNode)];
                    if(frame != null && frame.getStackSize() == 0) tempGotolist.add(((JumpInsnNode) insnNode).label);
                    else if(frame != null) {
//                        System.out.println("Try to add block " + ((JumpInsnNode) insnNode).label + " to gotolist, but stack size is " + frame.getStackSize());
//                        System.out.println(frame);
                    }
                }
            }

            if(insnNode instanceof EmptyFlagInsnNode) {
                list.add(tempList);
                gotolist.add(tempGotolist);
                tempList = new ArrayList<>();
                tempGotolist = new ArrayList<>();
            }
        }
        list.add(tempList);
        gotolist.add(tempGotolist);

//        for(LabelNode labelNode : gotolist) {
//            gotolistMap.put(labelNode, Main.r.nextInt());
//        }
        for (List<LabelNode> labelNodes : gotolist) {
            Map<LabelNode, Integer> tempMap = new HashMap<>();
            for(LabelNode labelNode : labelNodes) {
                tempMap.put(labelNode, Main.r.nextInt());
            }
            gotolistMap.add(tempMap);
        }

        insnList = new InsnList();

        ListIterator<AbstractInsnNode> insnNodeListIterator = methodNode.instructions.iterator();

        Iterator<List<LabelNode>> listIterator = list.iterator();
        Iterator<List<LabelNode>> gotolistIterator = gotolist.iterator();
        Iterator<Map<LabelNode, Integer>> gotolistMapIterator = gotolistMap.iterator();

        List<LabelNode> currentList = null;// = listIterator.next();
        List<LabelNode> currentGotolist = null;// = gotolistIterator.next();
        Map<LabelNode, Integer> currentGotolistMap = null;// = gotolistMapIterator.next();

        LabelNode defaultBlock = null;// new LabelNode();
        LabelNode startBlock = null;// new LabelNode();
        LabelNode loopBlock = null;// new LabelNode();

        LabelNode newLabel = null;

        while (insnNodeListIterator.hasNext()) {
            AbstractInsnNode insnNode = insnNodeListIterator.next();

            if(currentList != null && insnNode instanceof EmptyFlagInsnNode) {
                insnList.add(new JumpInsnNode(Opcodes.GOTO, newLabel));
                insnList.add(defaultBlock);
                insnList.add(new InsnNode(Opcodes.ACONST_NULL));
                insnList.add(new InsnNode(Opcodes.ATHROW));
            }

            if(currentList == null || insnNode instanceof EmptyFlagInsnNode) {
                currentList = listIterator.next();
                currentGotolist = gotolistIterator.next();
                currentGotolistMap = gotolistMapIterator.next();

                defaultBlock = new LabelNode();
                startBlock = new LabelNode();
                int startInt = Main.r.nextInt();
                currentGotolistMap.put(startBlock, startInt);
                loopBlock = new LabelNode();

                int[] keys = new int[currentGotolistMap.size()];
                LabelNode[] labels = new LabelNode[currentGotolistMap.size()];
                final int[] i = {0};
                currentGotolistMap.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEach(entry -> {
                    keys[i[0]] = entry.getValue();
                    labels[i[0]] = entry.getKey();
                    i[0]++;
                });

                int initVariableCount = Type.getMethodType(methodNode.desc).getArgumentTypes().length;
                if((methodNode.access & Opcodes.ACC_STATIC) == 0) initVariableCount += 1;

                if(newLabel != null) insnList.add(newLabel);

                insnList.add(new LdcInsnNode(startInt));
                insnList.add(new VarInsnNode(Opcodes.ISTORE, variableIndex));

                insnList.add(loopBlock);
                insnList.add(new VarInsnNode(Opcodes.ILOAD, variableIndex));
                insnList.add(new LookupSwitchInsnNode(defaultBlock, keys, labels));

                insnList.add(startBlock);

                newLabel = new LabelNode();
            }

            if(insnNode instanceof LabelNode) {
                insnList.add(insnNode); // if(list.contains(insnNode)) insnList.add(insnNode); else throw new IllegalStateException("NO LABEL");
            } else if(insnNode instanceof JumpInsnNode /*&& /*insnNode.getOpcode() == Opcodes.GOTO && currentGotolist.contains(((JumpInsnNode) insnNode).label)*/) {

                Integer value = currentGotolistMap.get(((JumpInsnNode) insnNode).label);
                if(value == null) {
                    insnList.add(insnNode);
                } else {
                    insnList.add(new LdcInsnNode(value));
                    insnList.add(new VarInsnNode(Opcodes.ISTORE, variableIndex));
                    insnList.add(new JumpInsnNode(insnNode.getOpcode(), loopBlock));
                }

            } else insnList.add(insnNode);

        }

        insnList.add(defaultBlock);
        insnList.add(new InsnNode(Opcodes.ACONST_NULL));
        insnList.add(new InsnNode(Opcodes.ATHROW));
//        LabelNode defaultBlock = new LabelNode();
//        LabelNode startBlock = new LabelNode();
//        int startInt = Main.r.nextInt();
//        gotolistMap.put(startBlock, startInt);
//        LabelNode loopBlock = new LabelNode();
//
//        int[] keys = new int[gotolistMap.size()];
//        LabelNode[] labels = new LabelNode[gotolistMap.size()];
//        final int[] i = {0};
//        gotolistMap.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEach(entry -> {
//            keys[i[0]] = entry.getValue();
//            labels[i[0]] = entry.getKey();
//            i[0]++;
//        });
//
//        int initVariableCount = Type.getMethodType(methodNode.desc).getArgumentTypes().length;
//        if((methodNode.access & Opcodes.ACC_STATIC) == 0) initVariableCount += 1;
//
//        insnList.add(new LdcInsnNode(startInt));
//        insnList.add(new VarInsnNode(Opcodes.ISTORE, variableIndex));
//
//        insnList.add(loopBlock);
//        insnList.add(new VarInsnNode(Opcodes.ILOAD, variableIndex));
//        insnList.add(new LookupSwitchInsnNode(defaultBlock, keys, labels));
//
//        insnList.add(startBlock);
//
//        for (AbstractInsnNode insnNode : methodNode.instructions) {
//            if(insnNode instanceof LabelNode) {
//                if(list.contains(insnNode)) insnList.add(insnNode); else throw new IllegalStateException("NO LABEL");
//            } else if(insnNode instanceof JumpInsnNode && /*insnNode.getOpcode() == Opcodes.GOTO && */gotolist.contains(((JumpInsnNode) insnNode).label)) {
//
//                insnList.add(new LdcInsnNode(gotolistMap.get(((JumpInsnNode) insnNode).label)));
//                insnList.add(new VarInsnNode(Opcodes.ISTORE, variableIndex));
//                insnList.add(new JumpInsnNode(insnNode.getOpcode(), loopBlock));
//
//            } else insnList.add(insnNode);
//        }
//
//        insnList.add(defaultBlock);
//        insnList.add(new InsnNode(Opcodes.ACONST_NULL));
//        insnList.add(new InsnNode(Opcodes.ATHROW));

        methodNode.instructions = insnList;
    }

    //    private static void addMoreGoto(ClassNode classNode, MethodNode methodNode) {
//        Analyzer<BasicValue> analyzer = new Analyzer<>(new BasicInterpreter());
//        try {
//            analyzer.analyze(methodNode.name, methodNode);
//        } catch (AnalyzerException e) {
//            throw new RuntimeException(e);
//        }
//        Frame<BasicValue>[] frames = analyzer.getFrames();
//
//        int count = 0;
//
//        InsnList list = new InsnList();
//        for (AbstractInsnNode instruction : methodNode.instructions) {
//            if(instruction instanceof LabelNode) {
//                Frame<BasicValue> frame = frames[methodNode.instructions.indexOf(instruction)];
//
//                if(frame != null && frame.getStackSize() == 0 && count++ > 4) {
//                    list.add(new JumpInsnNode(Opcodes.GOTO, (LabelNode) instruction));
//                } else if(frame == null) System.out.println("null?");
//                list.add(instruction);
//            } else {
//                list.add(instruction);
//            }
//        }
//
//        methodNode.instructions = list;
//    }
    private static void addMoreGoto(ClassNode classNode, MethodNode methodNode) {
        try {
            Analyzer<BasicValue> analyzer = new Analyzer<>(new BasicInterpreter());
            analyzer.analyze(methodNode.name, methodNode);
            Frame<BasicValue>[] frames = analyzer.getFrames();

            InsnList newInstructions = new InsnList();
            int index = 0;
            int count = 0;

            for (AbstractInsnNode insn : methodNode.instructions) {
                Frame<BasicValue> frame = frames[index];
                if (insn instanceof LabelNode) {
                    if (frame != null && frame.getStackSize() == 0) {
                        if ((count++ % 5 == 0 || (Main.r.nextBoolean() && Main.r.nextBoolean())) && count > 15) {
                            LabelNode target = (LabelNode) insn;
                            newInstructions.add(new JumpInsnNode(Opcodes.GOTO, target));
                            newInstructions.add(target);
                        }
                    } else if (frame == null) {
                        // 跳过不可达标签
                        newInstructions.add(insn);
                        index++;
                        continue;
                    }
                }
                newInstructions.add(insn);
                index++;
            }

            // 更新指令列表并重新计算 max_stack/max_locals
            methodNode.instructions = newInstructions;
            // insertCheckcast(methodNode);
//        methodNode.maxStack = 0; // 触发自动计算
//        methodNode.maxLocals = 0;
//        MethodVisitor mv = new MethodWriter(/* ... */);
//        methodNode.accept(mv);
        } catch (AnalyzerException e) {
            throw new RuntimeException(e);
        }

    }

}

class EmptyFlagInsnNode extends AbstractInsnNode {

    protected EmptyFlagInsnNode() {
        super(Opcodes.NOP);
    }

    @Override
    public int getType() {
        return 0;
    }

    @Override
    public void accept(MethodVisitor methodVisitor) {

    }

    @Override
    public AbstractInsnNode clone(Map<LabelNode, LabelNode> clonedLabels) {
        return new EmptyFlagInsnNode();
    }
}
