import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.tree.analysis.*;
import java.util.*;

public class SwitchCaseObfVisitor3 {

    public static byte[] transform(byte[] classData) throws AnalyzerException {
        ClassNode classNode = new ClassNode();
        ClassReader cr = new ClassReader(classData);
        cr.accept(classNode, 0);

        for (MethodNode method : classNode.methods) {
            if (!"<init>".equals(method.name) && !"<clinit>".equals(method.name)) {
                transformMethod(method);
            }
        }

        ClassWriter cw = new ClassWriter(0);
        for (MethodNode method : classNode.methods) {
            System.out.println(method.name + method.desc);
            for (AbstractInsnNode instruction : method.instructions) {
                if(instruction instanceof LineNumberNode) System.out.println(((LineNumberNode) instruction).line + " " + ((LineNumberNode) instruction).start + " " + ((LineNumberNode) instruction).hashCode());
                if(instruction instanceof JumpInsnNode) System.out.println(((JumpInsnNode) instruction).label);
                if(instruction instanceof TableSwitchInsnNode) {
                    System.out.println("======================TABLE======================");
                    for (LabelNode label : ((TableSwitchInsnNode) instruction).labels) {
                        System.out.println("label = " + label);
                    }
                    System.out.println("=======================END=======================");
                };
                if(instruction instanceof LabelNode) System.out.println("visitlabel -> " + instruction);
            }
        }
        classNode.accept(cw);
        return cw.toByteArray();
    }

    private static void transformMethod(MethodNode method) throws AnalyzerException {
        ControlFlowGraph cfg = analyzeControlFlow(method);
        int stateVarIndex = insertStateVariable(method);
        generateStateMachine(method, cfg, stateVarIndex);
    }

    // ----------------------- 控制流分析 -----------------------
    private static ControlFlowGraph analyzeControlFlow(MethodNode method) throws AnalyzerException {
        ControlFlowGraph cfg = new ControlFlowGraph();
        Analyzer<BasicValue> analyzer = new Analyzer<>(new BasicInterpreter());
        analyzer.analyze(method.name, method);

        // 1. 收集所有标签（包括分支目标）
        Set<LabelNode> labels = new HashSet<>();
        for (AbstractInsnNode insn : method.instructions) {
            if (insn instanceof LabelNode) {
                labels.add((LabelNode) insn);
            } else if (insn instanceof JumpInsnNode) {
                labels.add(((JumpInsnNode) insn).label);
            } else if (insn instanceof TableSwitchInsnNode) {
                TableSwitchInsnNode tswitch = (TableSwitchInsnNode) insn;
                labels.add(tswitch.dflt);
                labels.addAll(tswitch.labels);
            } else if (insn instanceof LookupSwitchInsnNode) {
                LookupSwitchInsnNode lswitch = (LookupSwitchInsnNode) insn;
                labels.add(lswitch.dflt);
                labels.addAll(lswitch.labels);
            }
        }

        // 2. 分割基本块并填充 blockMap
        int currentBlock = 0;
        boolean isNewBlock = true;

        for (AbstractInsnNode insn : method.instructions) {
            if (labels.contains(insn)) {
                currentBlock++;
                isNewBlock = true;
            }

            // 标记指令属于当前基本块
            if (isNewBlock) {
                cfg.blockMap.put(insn, currentBlock);
                isNewBlock = false;
            } else {
                cfg.blockMap.put(insn, currentBlock);
            }

            // 遇到分支指令结束当前基本块
            if (insn instanceof JumpInsnNode ||
                    insn instanceof TableSwitchInsnNode ||
                    insn instanceof LookupSwitchInsnNode) {
                currentBlock++;
                isNewBlock = true;
            }
        }

        // 3. 记录原始标签（用于后续标签映射）
        for (AbstractInsnNode insn : method.instructions) {
            if (insn instanceof LabelNode) {
                cfg.originalLabels.add((LabelNode) insn);
            }
        }

        return cfg;
    }

    // ----------------------- 插入状态变量 -----------------------
    private static int insertStateVariable(MethodNode method) {
        int stateVarIndex = new LocalVariablesSorter(method.access, method.desc, method)
                .newLocal(Type.INT_TYPE);
        InsnList initCode = new InsnList();
        initCode.add(new InsnNode(Opcodes.ICONST_1));
        initCode.add(new VarInsnNode(Opcodes.ISTORE, stateVarIndex));
        method.instructions.insert(initCode);
        return stateVarIndex;
    }

    // ----------------------- 生成状态机 -----------------------
    private static void generateStateMachine(MethodNode method, ControlFlowGraph cfg, int stateVarIndex) {
        InsnList newInstructions = new InsnList();
        Map<LabelNode, LabelNode> labelMap = new HashMap<>();

        // 1. 为所有原始标签生成映射
        for (LabelNode originalLabel : cfg.originalLabels) {
            labelMap.put(originalLabel, new LabelNode());
        }

        // 2. 插入循环标签和状态机框架
        LabelNode loopLabel = new LabelNode();
        newInstructions.add(loopLabel);
        newInstructions.add(new FrameNode(Opcodes.F_APPEND, 1, new Object[]{Opcodes.INTEGER}, 0, null));

        // 3. 加载状态变量并生成 switch
        newInstructions.add(new VarInsnNode(Opcodes.ILOAD, stateVarIndex));
        LabelNode defaultLabel = new LabelNode();
        LabelNode[] caseLabels = generateCaseLabels(cfg, labelMap);
        newInstructions.add(new TableSwitchInsnNode(1, caseLabels.length, defaultLabel, caseLabels));

        // 4. 为每个基本块生成 case 分支
        Map<Integer, List<AbstractInsnNode>> blockToInsns = new HashMap<>();
        for (AbstractInsnNode insn : method.instructions) {
            Integer blockId = cfg.blockMap.get(insn);
            if(blockId == null) blockId = 0;
            blockToInsns.computeIfAbsent(blockId, k -> new ArrayList<>()).add(insn);
        }

        for (int blockId = 1; blockId <= caseLabels.length; blockId++) {
            LabelNode caseLabel = caseLabels[blockId - 1];
            newInstructions.add(caseLabel);
            newInstructions.add(new FrameNode(Opcodes.F_SAME, 0, null, 0, null));

            // 复制基本块内的所有指令并修复标签
            List<AbstractInsnNode> blockInsns = blockToInsns.get(blockId);
            if (blockInsns == null) continue;

            for (AbstractInsnNode insn : blockInsns) {
                if (insn instanceof LabelNode) {
                    // 替换为映射后的新标签
                    LabelNode newLabel = labelMap.get(insn);
                    newInstructions.add(newLabel);
                } else if (insn instanceof LineNumberNode) {
                    LineNumberNode lineNode = (LineNumberNode) insn;
                    // 修复 start 标签
                    LabelNode newStart = labelMap.get(lineNode.start);
                    newInstructions.add(new LineNumberNode(lineNode.line, newStart));
                } else if (insn instanceof JumpInsnNode) {
                    JumpInsnNode jump = (JumpInsnNode) insn;
                    LabelNode newTarget = labelMap.get(jump.label);
                    newInstructions.add(new JumpInsnNode(jump.getOpcode(), newTarget));
                } else {
                    newInstructions.add(insn.clone(labelMap));
                }
            }

            // 更新状态变量
            newInstructions.add(new IntInsnNode(Opcodes.BIPUSH, blockId + 1));
            newInstructions.add(new VarInsnNode(Opcodes.ISTORE, stateVarIndex));
            newInstructions.add(new JumpInsnNode(Opcodes.GOTO, loopLabel));
        }

        // 5. 插入 default 分支
        newInstructions.add(defaultLabel);
        newInstructions.add(new InsnNode(Opcodes.RETURN));

        method.instructions = newInstructions;
    }

    private static LabelNode[] generateCaseLabels(ControlFlowGraph cfg, Map<LabelNode, LabelNode> labelMap) {
        // 每个基本块的起始标签已预先映射
        return labelMap.values().toArray(new LabelNode[0]);
    }

    private static class ControlFlowGraph {
        public Map<AbstractInsnNode, Integer> blockMap = new HashMap<>(); // 确保实例化
        public Set<LabelNode> originalLabels = new HashSet<>();
    }
}