import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.tree.analysis.*;

import java.util.*;
import java.util.stream.Collectors;

public class SwitchCaseObfVisitor5 {

    public static byte[] transform(byte[] classData) throws AnalyzerException {
        ClassNode classNode = new ClassNode();
        ClassReader cr = new ClassReader(classData);
        cr.accept(classNode, 0);

        if(!new ObfVisitor(cr) {}.shouldObf() || (classNode.access & (Opcodes.ACC_ENUM | Opcodes.ACC_INTERFACE)) != 0) return classData;

        for(MethodNode methodNode : classNode.methods) {

            // test the method access that is not abstract

            if((methodNode.access & (Opcodes.ACC_ABSTRACT | Opcodes.ACC_NATIVE)) == 0 && !methodNode.name.contains("init>")) transformMethod(classNode, methodNode);
        }

        ClassWriter writer = new ClassWriter1(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        classNode.accept(writer);

        return writer.toByteArray();
    }

    private static void transformMethod(ClassNode classNode, MethodNode methodNode) {

        addMoreGoto(classNode, methodNode);
        fixLocalVars(methodNode);

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
                if (ch == 'A') {
                    Iterator<Map.Entry<Character, Integer>> it = variableIndexes.iterator();
                    while(it.hasNext()) {
                        Map.Entry<Character, Integer> entry1 = it.next();
                        if(entry1.getValue() == entry.getValue()) {
                            it.remove();
                        }
                    }
                }
                if(!variableIndexes.contains(entry)) variableIndexes.add(entry);

            }
        }
        variableIndex += 2;
        int checkCastVariableIndex = variableIndex + 1;

        List<LabelNode> list = new ArrayList<>();

        List<LabelNode> gotolist = new ArrayList<>();
        Map<LabelNode, Integer> gotolistMap = new HashMap<>();

        for(AbstractInsnNode insnNode : methodNode.instructions) {
            if(insnNode instanceof LabelNode) list.add((LabelNode) insnNode);
            if(insnNode instanceof JumpInsnNode) {
                if(true||insnNode.getOpcode() == Opcodes.GOTO) {
                    Frame<BasicValue> frame = frames[methodNode.instructions.indexOf(insnNode)];
                    if(frame != null && frame.getStackSize() == 0) gotolist.add(((JumpInsnNode) insnNode).label);
                    else if(frame != null) {
//                        System.out.println("Try to add block " + ((JumpInsnNode) insnNode).label + " to gotolist, but stack size is " + frame.getStackSize());
//                        System.out.println(frame);
                    }
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

        for (AbstractInsnNode insnNode : methodNode.instructions) {
            if(insnNode instanceof LabelNode) {
                if(list.contains(insnNode)) insnList.add(insnNode); else throw new IllegalStateException("NO LABEL");
            } else if(insnNode instanceof JumpInsnNode && /*insnNode.getOpcode() == Opcodes.GOTO && */gotolist.contains(((JumpInsnNode) insnNode).label)) {

                insnList.add(new LdcInsnNode(gotolistMap.get(((JumpInsnNode) insnNode).label)));
                insnList.add(new VarInsnNode(Opcodes.ISTORE, variableIndex));
                insnList.add(new JumpInsnNode(insnNode.getOpcode(), loopBlock));

            } else if(false && insnNode instanceof MethodInsnNode) {
                if(insnNode.getOpcode() == Opcodes.INVOKEVIRTUAL || insnNode.getOpcode() == Opcodes.INVOKEINTERFACE) {
                    Type[] types = Type.getArgumentTypes(((MethodInsnNode) insnNode).desc);
                    int stackSize = types.length + 1;

                    Frame<BasicValue> frame = frames[methodNode.instructions.indexOf(insnNode)];

//                    System.out.println(methodNode.name + " " + methodNode.desc);
//                    System.out.println("frame: " + frame.toString());
//                    System.out.println(((MethodInsnNode) insnNode).name + " " + ((MethodInsnNode) insnNode).desc);
                    List<Map.Entry<Character, Integer>> checkCastIndex = new ArrayList<>();
                    int checkCastVariableIndex2 = checkCastVariableIndex;
                    for(int j = 0; j < stackSize; j++) {
                        char type = frame.getStack(frame.getStackSize() - stackSize + j).toString().charAt(0);
                        switch (type) {
                            case 'I':
                                checkCastIndex.add(new AbstractMap.SimpleEntry<>('I', checkCastVariableIndex2));
                                break;
                            case 'J':
                                checkCastIndex.add(new AbstractMap.SimpleEntry<>('J', checkCastVariableIndex2));
                                break;
                            case 'F':
                                checkCastIndex.add(new AbstractMap.SimpleEntry<>('F', checkCastVariableIndex2));
                                break;
                            case 'D':
                                checkCastIndex.add(new AbstractMap.SimpleEntry<>('D', checkCastVariableIndex2));
                                break;
                            case 'A':
                            case 'R':
                                checkCastIndex.add(new AbstractMap.SimpleEntry<>('A', checkCastVariableIndex2));
                                break;
                            default:
                                throw new IllegalStateException("Unknown type: " + type);
                        }
                        /*if(type == 'J' || type == 'D') checkCastVariableIndex2 += 2; else */checkCastVariableIndex2 += 2;
                    }

                    for (int i1 = checkCastIndex.size() - 1; i1 >= 0; i1--) {
                        Map.Entry<Character, Integer> entry = checkCastIndex.get(i1);
                        switch (entry.getKey()) {
                            case 'I':
                                insnList.add(new VarInsnNode(Opcodes.ISTORE, entry.getValue()));
                                break;
                            case 'J':
                                insnList.add(new VarInsnNode(Opcodes.LSTORE, entry.getValue()));
                                break;
                            case 'F':
                                insnList.add(new VarInsnNode(Opcodes.FSTORE, entry.getValue()));
                                break;
                            case 'D':
                                insnList.add(new VarInsnNode(Opcodes.DSTORE, entry.getValue()));
                                break;
                            case 'A':
                            case 'R':
                                insnList.add(new VarInsnNode(Opcodes.ASTORE, entry.getValue()));
                                break;
                            default:
                                throw new IllegalStateException("Unknown type: " + entry.getKey());
                        }
                    }
                    for (int i1 = 0; i1 < checkCastIndex.size(); i1++) {
                        Map.Entry<Character, Integer> entry = checkCastIndex.get(i1);
                        switch (entry.getKey()) {
                            case 'I':
                                insnList.add(new VarInsnNode(Opcodes.ILOAD, entry.getValue()));
                                break;
                            case 'J':
                                insnList.add(new VarInsnNode(Opcodes.LLOAD, entry.getValue()));
                                break;
                            case 'F':
                                insnList.add(new VarInsnNode(Opcodes.FLOAD, entry.getValue()));
                                break;
                            case 'D':
                                insnList.add(new VarInsnNode(Opcodes.DLOAD, entry.getValue()));
                                break;
                            case 'A':
                            case 'R':
                                insnList.add(new VarInsnNode(Opcodes.ALOAD, entry.getValue()));
                                insnList.add(new TypeInsnNode(Opcodes.CHECKCAST, i1 == 0 ? ((MethodInsnNode) insnNode).owner : types[i1 - 1].getInternalName()));
                                break;
                            default:
                                throw new IllegalStateException("Unknown type: " + entry.getKey());
                        }
                    }
                }

                insnList.add(insnNode);
            } else insnList.add(insnNode);
        }

        insnList.add(defaultBlock);
        insnList.add(new InsnNode(Opcodes.ACONST_NULL));
        insnList.add(new InsnNode(Opcodes.ATHROW));

        methodNode.instructions = insnList;

        methodNode.localVariables = null;
        methodNode.parameters = null;
        methodNode.signature = null;
        // methodNode.maxStack = methodNode.maxStack + 5;

//        for (AbstractInsnNode instruction : method.instructions) {
//            if (instruction instanceof LineNumberNode) {
//                method.instructions.remove(instruction);
//            }
//        }

        for (FieldNode field : classNode.fields)
            field.signature = null;
        classNode.signature = null;
        classNode.innerClasses.clear();
        classNode.sourceFile = null;
        classNode.sourceDebug = null;

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

    interface Adder {
        void add(char ch, int index);
    }

//    private static void fixLocalVars(MethodNode methodNode) {
//
//        List<AbstractInsnNode> newInstructions = new ArrayList<>();
//        Analyzer<BasicValue> analyzer = new Analyzer<>(new BasicInterpreter());
//        try {
//            analyzer.analyze(methodNode.name, methodNode);
//        } catch (AnalyzerException e) {
//            throw new RuntimeException(e);
//        }
//        Frame<BasicValue>[] frames = analyzer.getFrames();
//
//        Map<Map.Entry<Character, Integer>, Map.Entry<Character, Integer>> vars = new HashMap<>();
//
//        final int[] varIndex = {0};
//
//        Adder adder = (ch, index) -> {
//            AbstractMap.SimpleEntry<Character, Integer> entry = new AbstractMap.SimpleEntry<>(ch, index);
//            if (!vars.containsKey(entry)) {
//                vars.put(entry, new AbstractMap.SimpleEntry<>(ch, varIndex[0]));
//                varIndex[0] = varIndex[0] + (ch == 'L' || ch == 'D' ? 2 : 1);
//            }
//        };
//
//        int tempIndex = 0;
//        if((methodNode.access & Opcodes.ACC_STATIC) == 0) {
//            adder.add('A', tempIndex);
//            tempIndex++;
//        }
//
//        Type[] argTypes = Type.getArgumentTypes(methodNode.desc);
//        for (Type argType : argTypes) {
//            switch (argType.getInternalName().charAt(0)) {
//                case 'I':
//                case 'Z':
//                case 'C':
//                case 'B':
//                case 'S':
//                    adder.add('I', tempIndex);
//                    tempIndex++;
//                    break;
//                case 'J':
//                    adder.add('J', tempIndex);
//                    tempIndex += 2;
//                    break;
//                case 'F':
//                    adder.add('F', tempIndex);
//                    tempIndex++;
//                    break;
//                case 'D':
//                    adder.add('D', tempIndex);
//                    tempIndex += 2;
//                    break;
//                case 'L':
//                case '[':
//                    adder.add('A', tempIndex);
//                    tempIndex++;
//                    break;
//            }
//        }
//
//        for (AbstractInsnNode insnNode : methodNode.instructions) {
//            if (insnNode instanceof VarInsnNode) {
//                VarInsnNode insn = (VarInsnNode) insnNode;
//                switch (insn.getOpcode()) {
//                    case Opcodes.ILOAD:
//                    case Opcodes.ISTORE:
//                        adder.add('I', insn.var);
//                        break;
//                    case Opcodes.LLOAD:
//                    case Opcodes.LSTORE:
//                        adder.add('L', insn.var);
//                        break;
//                    case Opcodes.FLOAD:
//                    case Opcodes.FSTORE:
//                        adder.add('F', insn.var);
//                        break;
//                    case Opcodes.DLOAD:
//                    case Opcodes.DSTORE:
//                        adder.add('D', insn.var);
//                        break;
//                    case Opcodes.ALOAD:
//                    case Opcodes.ASTORE:
//                        adder.add('A', insn.var);
//                        break;
//                }
//            }
//        }
//
//        InsnList insnList = new InsnList();
//        for (AbstractInsnNode insnNode : methodNode.instructions) {
//            if (insnNode instanceof VarInsnNode) {
//                VarInsnNode insn = (VarInsnNode) insnNode;
//
//                insnList.add(new VarInsnNode(insn.getOpcode(), vars.get(new AbstractMap.SimpleEntry<>(insn.getOpcode() == Opcodes.ALOAD || insn.getOpcode() == Opcodes.ASTORE ? 'A' : insn.getOpcode() == Opcodes.LLOAD || insn.getOpcode() == Opcodes.LSTORE ? 'L' : insn.getOpcode() == Opcodes.DLOAD || insn.getOpcode() == Opcodes.DSTORE ? 'D' : 'I', insn.var)).getValue()));
//            } else insnList.add(insnNode);
//        }
//
//        methodNode.instructions = insnList;
//    }

    public static void fixLocalVars(MethodNode methodNode) {
        // 1. 确定方法参数占用的索引范围
        boolean isStatic = (methodNode.access & Opcodes.ACC_STATIC) != 0;
        Type[] argTypes = Type.getArgumentTypes(methodNode.desc);
        int parametersSize = argTypes.length + (isStatic ? 0 : 1); // 非静态方法索引0是this

        // 2. 收集所有非参数局部变量的类型使用情况
        Map<Integer, Set<Type>> varTypeMap = new HashMap<>();
        for (AbstractInsnNode insn : methodNode.instructions) {
            if (insn instanceof VarInsnNode) {
                processVarInsn((VarInsnNode) insn, parametersSize, varTypeMap);
            } else if (insn instanceof IincInsnNode) {
                processIincInsn((IincInsnNode) insn, parametersSize, varTypeMap);
            }
        }

        // 3. 检测需要重新分配的冲突索引
        List<Integer> conflictVars = varTypeMap.entrySet().stream()
                .filter(e -> e.getValue().size() > 1)
                .map(Map.Entry::getKey)
                .sorted()
                .collect(Collectors.toList());

        if (conflictVars.isEmpty()) return;

        // 4. 重新分配索引
        int currentMaxIndex = parametersSize;
        Map<Integer, Integer> indexRemap = new HashMap<>();

        for (int oldIndex : conflictVars) {
            int slotSize = getMaxSlotSize(varTypeMap.get(oldIndex));
            indexRemap.put(oldIndex, currentMaxIndex);
            currentMaxIndex += slotSize;
        }

        // 5. 更新指令中的索引
        updateInstructions(methodNode.instructions, indexRemap);
    }

    private static void processVarInsn(VarInsnNode insn, int paramsSize, Map<Integer, Set<Type>> varTypeMap) {
        int varIndex = insn.var;
        if (varIndex < paramsSize) return;

        Type type = getVarInsnType(insn.getOpcode());
        varTypeMap.computeIfAbsent(varIndex, k -> new HashSet<>()).add(type);
    }

    private static void processIincInsn(IincInsnNode insn, int paramsSize, Map<Integer, Set<Type>> varTypeMap) {
        int varIndex = insn.var;
        if (varIndex < paramsSize) return;

        varTypeMap.computeIfAbsent(varIndex, k -> new HashSet<>()).add(Type.INT_TYPE);
    }

    private static Type getVarInsnType(int opcode) {
        switch (opcode) {
            case Opcodes.ILOAD: case Opcodes.ISTORE: return Type.INT_TYPE;
            case Opcodes.LLOAD: case Opcodes.LSTORE: return Type.LONG_TYPE;
            case Opcodes.FLOAD: case Opcodes.FSTORE: return Type.FLOAT_TYPE;
            case Opcodes.DLOAD: case Opcodes.DSTORE: return Type.DOUBLE_TYPE;
            case Opcodes.ALOAD: case Opcodes.ASTORE: return Type.getType("Ljava/lang/Object;");
            default: throw new IllegalArgumentException("Unexpected opcode: " + opcode);
        }
    }

    private static int getMaxSlotSize(Set<Type> types) {
        return types.stream()
                .mapToInt(Type::getSize)
                .max()
                .orElse(1);
    }

    private static void updateInstructions(InsnList instructions, Map<Integer, Integer> indexRemap) {
        for (AbstractInsnNode insn : instructions) {
            if (insn instanceof VarInsnNode) {
                VarInsnNode varInsn = (VarInsnNode) insn;
                varInsn.var = indexRemap.getOrDefault(varInsn.var, varInsn.var);
            } else if (insn instanceof IincInsnNode) {
                IincInsnNode iinc = (IincInsnNode) insn;
                iinc.var = indexRemap.getOrDefault(iinc.var, iinc.var);
            }
        }
    }

}
