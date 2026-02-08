import javafx.util.Pair;
import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.tree.analysis.*;

import java.util.*;
import java.util.concurrent.Callable;

public class SwitchCaseObfVisitor4 {

    public static byte[] transform(byte[] classData) throws AnalyzerException {
        ClassNode classNode = new ClassNode();
        ClassReader cr = new ClassReader(classData);
        cr.accept(classNode, 0);

        if(!new ObfVisitor(cr) {}.shouldObf() || (classNode.access & (Opcodes.ACC_ENUM | Opcodes.ACC_INTERFACE)) != 0) return classData;

        for(MethodNode methodNode : classNode.methods) {

            // test the method access that is not abstract

            if((methodNode.access & (Opcodes.ACC_ABSTRACT | Opcodes.ACC_NATIVE)) == 0 && !methodNode.name.contains("init>")) transformMethod(classNode, methodNode);
        }

        ClassWriter writer = new ClassWriter1(ClassWriter.COMPUTE_FRAMES);
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

        int variableIndex = 0;
        List<Map.Entry<Character, Integer>> variableIndexes = new ArrayList<>();
        for(AbstractInsnNode insnNode : methodNode.instructions) {
            if(insnNode instanceof VarInsnNode) {
                variableIndex = Math.max(((VarInsnNode) insnNode).var, variableIndex);

                char ch;
                switch (insnNode.getOpcode()) {
                    case Opcodes.ISTORE:
                    case Opcodes.ILOAD:
                        // variableIndexes.add(new AbstractMap.SimpleEntry<>('I', ((VarInsnNode) insnNode).var));
                        ch = 'I';
                        break;
                    case Opcodes.LSTORE:
                    case Opcodes.LLOAD:
                        // variableIndexes.add(new AbstractMap.SimpleEntry<>('J', ((VarInsnNode) insnNode).var));
                        ch = 'J';
                        break;
                    case Opcodes.FSTORE:
                    case Opcodes.FLOAD:
                        // variableIndexes.add(new AbstractMap.SimpleEntry<>('F', ((VarInsnNode) insnNode).var));
                        ch = 'F';
                        break;
                    case Opcodes.DSTORE:
                    case Opcodes.DLOAD:
                        // variableIndexes.add(new AbstractMap.SimpleEntry<>('D', ((VarInsnNode) insnNode).var));
                        ch = 'D';
                        break;
                    case Opcodes.ASTORE:
                    case Opcodes.ALOAD:
                        // variableIndexes.add(new AbstractMap.SimpleEntry<>('A', ((VarInsnNode) insnNode).var));
                        ch = 'A';
                        break;
                    default:
                        throw new IllegalStateException("Unknown opcode: " + insnNode.getOpcode());
                }

                AbstractMap.SimpleEntry<Character, Integer> entry = new AbstractMap.SimpleEntry<>(ch, ((VarInsnNode) insnNode).var);
                if(!variableIndexes.contains(entry)) variableIndexes.add(entry);

            }
        }
        variableIndex += 2;
        int variableExceptionIndexStart;
        int variableExceptionIndex = variableExceptionIndexStart = variableIndex + 1;

        List<LabelNode> list = new ArrayList<>();

        List<LabelNode> gotolist = new ArrayList<>();
        Map<LabelNode, Integer> gotolistMap = new HashMap<>();

        for(AbstractInsnNode insnNode : methodNode.instructions) {
            if(insnNode instanceof LabelNode) list.add((LabelNode) insnNode);
            if(insnNode instanceof JumpInsnNode) {
                if(true||insnNode.getOpcode() == Opcodes.GOTO) {
                    Frame<BasicValue> frame = frames[methodNode.instructions.indexOf(insnNode)];
                    if(frame != null && frame.getStackSize() == 0) gotolist.add(((JumpInsnNode) insnNode).label);
//                    else {
//                        System.out.println("Try to add block " + ((JumpInsnNode) insnNode).label + " to gotolist, but stack size is " + frame.getStackSize());
//                        for (int i = 0; i < frame.getStackSize(); i++) {
//                            BasicValue value = frame.getStack(i);
//                            System.out.printf("栈位置 %d: %s\n", i, value);
//                        }
//                    }
                }
            }
        }
        for(LabelNode labelNode : gotolist) {
            gotolistMap.put(labelNode, Main.r.nextInt());
        }

        LabelNode defaultBlock = new LabelNode();
        LabelNode startBlock = new LabelNode();
        int startInt = Main.r.nextInt();
        gotolistMap.put(startBlock, startInt);
        LabelNode loopBlock = new LabelNode();

        int[] keys = new int[gotolistMap.size()];
        LabelNode[] labels = new LabelNode[gotolistMap.size()];
        final int[] i = {0};
        gotolistMap.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEach(entry -> {
            keys[i[0]] = entry.getValue();
            labels[i[0]] = entry.getKey();
            i[0]++;
        });

        InsnList insnList = new InsnList();
        int initVariableCount = Type.getMethodType(methodNode.desc).getArgumentTypes().length;
        if((methodNode.access & Opcodes.ACC_STATIC) == 0) initVariableCount += 1;

        for (Map.Entry<Character, Integer> index : variableIndexes) {
            if(index.getValue() < initVariableCount) continue;
            switch (index.getKey()) {
                case 'I':
                case 'Z':
                case 'B':
                case 'C':
                case 'S':
                    insnList.add(new InsnNode(Opcodes.ICONST_0));
                    insnList.add(new VarInsnNode(Opcodes.ISTORE, index.getValue()));
                    break;
                case 'J':
                    insnList.add(new InsnNode(Opcodes.LCONST_0));
                    insnList.add(new VarInsnNode(Opcodes.LSTORE, index.getValue()));
                    break;
                case 'F':
                    insnList.add(new InsnNode(Opcodes.FCONST_0));
                    insnList.add(new VarInsnNode(Opcodes.FSTORE, index.getValue()));
                    break;
                case 'D':
                    insnList.add(new InsnNode(Opcodes.DCONST_0));
                    insnList.add(new VarInsnNode(Opcodes.DSTORE, index.getValue()));
                    break;
                case 'A':
                    insnList.add(new InsnNode(Opcodes.ACONST_NULL));
                    insnList.add(new VarInsnNode(Opcodes.ASTORE, index.getValue()));
                    break;
                default:
                    throw new IllegalStateException("Unknown type: " + index.getKey());
            }
        }

//        insnList.add(new InsnNode(Opcodes.ACONST_NULL));
//        insnList.add(new VarInsnNode(Opcodes.ASTORE, variableExceptionIndex));
        insnList.add(new LdcInsnNode(startInt));
        insnList.add(new VarInsnNode(Opcodes.ISTORE, variableIndex));

        insnList.add(loopBlock);
        insnList.add(new VarInsnNode(Opcodes.ILOAD, variableIndex));
        insnList.add(new LookupSwitchInsnNode(defaultBlock, keys, labels));

        insnList.add(startBlock);
//        for (AbstractInsnNode insnNode : methodNode.instructions) {
//            if(insnNode instanceof LabelNode) {
//                if(list.contains(insnNode)) insnList.add(insnNode); else throw new IllegalStateException("NO LABEL");
//            } else if(insnNode instanceof JumpInsnNode && insnNode.getOpcode() == Opcodes.GOTO && gotolist.contains(((JumpInsnNode) insnNode).label)) {
//
//                insnList.add(new LdcInsnNode(gotolistMap.get(((JumpInsnNode) insnNode).label)));
//                insnList.add(new VarInsnNode(Opcodes.ISTORE, variableIndex));
//                insnList.add(new JumpInsnNode(Opcodes.GOTO, loopBlock));
//
//            } else insnList.add(insnNode);
//        }

//        LabelNode tryCatchHandler = null;
//        int exceptionVar = -1;
        LabelNode lastGotoLabel = null;

        Map<LabelNode, Integer> tryCatchHandler_And_ExceptionVar = new HashMap<>();
        Map<LabelNode, Integer> tryCatchHandler_variableExceptionIndex = new HashMap<>();
        List<LabelNode> currentTryCatchHandler = new ArrayList<>();
        Map<LabelNode, LabelNode> tryCatchBlockEnd = new HashMap<>();
        boolean isInNewTCB = false;

        List<LabelNode> tryCatchBlockHandlers = new ArrayList<>();
        for (TryCatchBlockNode tryCatchBlock : methodNode.tryCatchBlocks) {
            tryCatchBlockHandlers.add(tryCatchBlock.handler);
        }

        for (AbstractInsnNode insnNode : methodNode.instructions) {
            if(insnNode instanceof LabelNode) {
                if(list.contains(insnNode)) insnList.add(insnNode); else throw new IllegalStateException("NO LABEL");

                if(tryCatchBlockHandlers.contains(insnNode)) {
                    currentTryCatchHandler.add((LabelNode) insnNode);
                    tryCatchHandler_And_ExceptionVar.put((LabelNode) insnNode, -1);
                    tryCatchBlockEnd.put(lastGotoLabel, (LabelNode) insnNode);
                    isInNewTCB = true;
                }
                if(tryCatchBlockEnd.containsKey(insnNode)) {
                    LabelNode label = tryCatchBlockEnd.get(insnNode);
                    tryCatchHandler_And_ExceptionVar.remove(label);
                    currentTryCatchHandler.remove(label);
                }
            } else if(insnNode instanceof JumpInsnNode && /*insnNode.getOpcode() == Opcodes.GOTO && */gotolist.contains(((JumpInsnNode) insnNode).label)) {

                if(insnNode.getOpcode() == Opcodes.GOTO) lastGotoLabel = ((JumpInsnNode) insnNode).label;
                insnList.add(new LdcInsnNode(gotolistMap.get(((JumpInsnNode) insnNode).label)));
                insnList.add(new VarInsnNode(Opcodes.ISTORE, variableIndex));
                insnList.add(new JumpInsnNode(insnNode.getOpcode(), loopBlock));
                if(tryCatchHandler_And_ExceptionVar.size() > 0) {
                    Iterator<Map.Entry<LabelNode, Integer>> it = tryCatchHandler_And_ExceptionVar.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry<LabelNode, Integer> entry = it.next();
                        if(methodNode.instructions.indexOf(((JumpInsnNode) insnNode).label) < methodNode.instructions.indexOf(entry.getKey())) {
                            it.remove();
                            currentTryCatchHandler.remove(entry.getKey());
                        }
                    }
                }

            } else if(tryCatchHandler_And_ExceptionVar.size() > 0 && insnNode instanceof VarInsnNode) {
                Integer exceptionVar = tryCatchHandler_And_ExceptionVar.get(currentTryCatchHandler.get(currentTryCatchHandler.size() - 1));
                if(exceptionVar == -1) {
                    if(insnNode.getOpcode() == Opcodes.ASTORE) {
                        exceptionVar = ((VarInsnNode) insnNode).var;
                        isInNewTCB = false;
                        tryCatchHandler_And_ExceptionVar.put(currentTryCatchHandler.get(currentTryCatchHandler.size() - 1), exceptionVar);
                        tryCatchHandler_variableExceptionIndex.put(currentTryCatchHandler.get(currentTryCatchHandler.size() - 1), variableExceptionIndex);
                        insnList.add(new VarInsnNode(insnNode.getOpcode(), variableExceptionIndex));
                        variableExceptionIndex++;
                    } else {
                        insnList.add(insnNode);
                    }
                } else {
                    if(((VarInsnNode) insnNode).var == exceptionVar) {
                        insnList.add(new VarInsnNode(insnNode.getOpcode(), tryCatchHandler_variableExceptionIndex.get(currentTryCatchHandler.get(currentTryCatchHandler.size() - 1))));
                    } else {
                        insnList.add(insnNode);
                    }
                }
            } else insnList.add(insnNode);

            if(tryCatchHandler_And_ExceptionVar.size() > 0 && insnNode.getOpcode() == Opcodes.POP && isInNewTCB) {
                tryCatchBlockHandlers.remove(currentTryCatchHandler.get(currentTryCatchHandler.size() - 1));
                currentTryCatchHandler.remove(currentTryCatchHandler.size() - 1);
                isInNewTCB = false;
            }
        }

        insnList.add(defaultBlock);
        insnList.add(new InsnNode(Opcodes.ACONST_NULL));
        insnList.add(new InsnNode(Opcodes.ATHROW));

        InsnList insnList2 = new InsnList();
        for(int k = variableExceptionIndexStart;k < variableExceptionIndex;k++) {
            insnList2.add(new InsnNode(Opcodes.ACONST_NULL));
            insnList2.add(new VarInsnNode(Opcodes.ASTORE, k));
        }
        insnList2.add(insnList);

        methodNode.instructions = insnList2;
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
                        if (count++ % 5 == 0 && count > 1) { // 每5个有效标签插入一次
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

    private static void insertCheckcast(MethodNode methodNode) {

        List<AbstractInsnNode> newInstructions = new ArrayList<>();
        Analyzer<BasicValue> analyzer = new Analyzer<>(new BasicInterpreter());
        try {
            analyzer.analyze(methodNode.name, methodNode);
        } catch (AnalyzerException e) {
            throw new RuntimeException(e);
        }
        Frame<BasicValue>[] frames = analyzer.getFrames();

        for (AbstractInsnNode insn : methodNode.instructions) {
            newInstructions.add(insn);

            if(insn instanceof MethodInsnNode) {
                MethodInsnNode methodInsnNode = (MethodInsnNode) insn;
                if(methodInsnNode.getOpcode() == Opcodes.INVOKEVIRTUAL) {
                    Type[] types = Type.getArgumentTypes(methodInsnNode.desc);
                    Type owner = Type.getObjectType(methodInsnNode.owner);

                    int i = types.length - 1;

                    List<VarInsnNode> checked = new ArrayList<>();

                    for (; i >= 0; i--) {
                        if(types[i].getSort() >= Type.BOOLEAN && types[i].getSort() <= Type.LONG) {
                        } else {
                            ListIterator<AbstractInsnNode> listIterator = newInstructions.listIterator(newInstructions.size());

                            while(listIterator.hasPrevious()) {
                                AbstractInsnNode node = listIterator.previous();
                                if(node instanceof VarInsnNode) {
                                    if(!checked.contains(node) && node.getOpcode() == Opcodes.ALOAD) {
                                        VarInsnNode varInsnNode = (VarInsnNode) node;
                                        int index = newInstructions.indexOf(varInsnNode) + 1;
                                        newInstructions.add(index, new TypeInsnNode(Opcodes.CHECKCAST, types[i].getInternalName()));
                                        newInstructions.add(index, new TypeInsnNode(Opcodes.CHECKCAST, "java/lang/Object"));
                                        checked.add(varInsnNode);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    ListIterator<AbstractInsnNode> listIterator = newInstructions.listIterator();

                    while(listIterator.hasPrevious()) {
                        AbstractInsnNode node = listIterator.previous();
                        if(node instanceof VarInsnNode) {
                            if(!checked.contains(node) && node.getOpcode() == Opcodes.ALOAD) {
                                VarInsnNode varInsnNode = (VarInsnNode) node;
                                int index = newInstructions.indexOf(varInsnNode) + 1;
                                newInstructions.add(index, new TypeInsnNode(Opcodes.CHECKCAST, owner.getInternalName()));
                                newInstructions.add(index, new TypeInsnNode(Opcodes.CHECKCAST, "java/lang/Object"));
                                checked.add(varInsnNode);
                                break;
                            }
                        }
                    }
                }
            } else if(insn instanceof InsnNode) {
                InsnNode insnNode = (InsnNode) insn;
                if(insnNode.getOpcode() == Opcodes.ARETURN) {
                    int index = newInstructions.indexOf(insnNode);

                    Type returnType = Type.getReturnType(methodNode.desc);
                    newInstructions.add(index, new TypeInsnNode(Opcodes.CHECKCAST, returnType.getInternalName()));
                    newInstructions.add(index, new TypeInsnNode(Opcodes.CHECKCAST, "java/lang/Object"));
                }
            }
        }

        methodNode.instructions = new InsnList();
        newInstructions.forEach(methodNode.instructions::add);

    }

}
