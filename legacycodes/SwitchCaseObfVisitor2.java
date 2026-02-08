import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.tree.analysis.*;
import java.util.*;

public class SwitchCaseObfVisitor2 {

    public static byte[] transform(byte[] classData) throws AnalyzerException {
        ClassNode classNode = new ClassNode();
        ClassReader cr = new ClassReader(classData);
        cr.accept(classNode, 0);

        for (MethodNode method : classNode.methods) {
            System.out.println(method.name + method.desc);
            for (AbstractInsnNode instruction : method.instructions) {
                if(instruction instanceof LineNumberNode) System.out.println(((LineNumberNode) instruction).line + " " + ((LineNumberNode) instruction).start + " " + ((LineNumberNode) instruction).hashCode());
            }
        }

        for (MethodNode method : classNode.methods) {
            if (!"<init>".equals(method.name) && !"<clinit>".equals(method.name)) {
                transformMethod(method);
            }
        }

        for (MethodNode method : classNode.methods) {
            System.out.println(method.name + method.desc);
            for (AbstractInsnNode instruction : method.instructions) {
                if(instruction instanceof LineNumberNode) System.out.println(((LineNumberNode) instruction).line + " " + ((LineNumberNode) instruction).start + " " + ((LineNumberNode) instruction).hashCode());
            }
        }
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
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

        InsnList instructions = method.instructions;
        Map<AbstractInsnNode, Integer> blockMap = new HashMap<>();
        int blockId = 0;

        for (AbstractInsnNode insn : instructions) {
            if (insn instanceof LabelNode) {
                blockId++;
            }
            blockMap.put(insn, blockId);
        }

        cfg.blockMap = blockMap;
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

        // 1. 插入循环标签和状态机框架
        LabelNode loopLabel = new LabelNode();
        newInstructions.add(loopLabel);
        newInstructions.add(new FrameNode(Opcodes.F_APPEND, 1, new Object[]{Opcodes.INTEGER}, 0, null));

        // 2. 加载状态变量并生成 switch
        newInstructions.add(new VarInsnNode(Opcodes.ILOAD, stateVarIndex));
        LabelNode defaultLabel = new LabelNode();
        LabelNode[] caseLabels = generateCaseLabels(cfg, labelMap);
        newInstructions.add(new TableSwitchInsnNode(1, caseLabels.length, defaultLabel, caseLabels));

        // 3. 为每个基本块生成 case 分支
        Map<Integer, List<AbstractInsnNode>> blockToInsns = new HashMap<>();
        for (AbstractInsnNode insn : method.instructions) {
            Integer blockId = cfg.blockMap.get(insn);
            if (blockId == null) {
                blockId = 0; // 分配默认块（需根据实际情况处理）
            }
            blockToInsns.computeIfAbsent(blockId, k -> new ArrayList<>()).add(insn);
        }

        for (int blockId = 1; blockId <= caseLabels.length; blockId++) {
            LabelNode caseLabel = caseLabels[blockId - 1];
            newInstructions.add(caseLabel);
            newInstructions.add(new FrameNode(Opcodes.F_SAME, 0, null, 0, null));

            // 复制基本块内的所有指令
            List<AbstractInsnNode> blockInsns = blockToInsns.get(blockId);
            if (blockInsns == null) continue;

            for (AbstractInsnNode insn : blockInsns) {
                if (insn instanceof LabelNode) continue; // 跳过原始标签

                // 处理跳转指令
                if (insn instanceof JumpInsnNode) {
                    JumpInsnNode jump = (JumpInsnNode) insn;
                    int targetBlock = cfg.blockMap.get(jump.label);
                    newInstructions.add(new IntInsnNode(Opcodes.BIPUSH, targetBlock));
                    newInstructions.add(new VarInsnNode(Opcodes.ISTORE, stateVarIndex));
                    newInstructions.add(new JumpInsnNode(Opcodes.GOTO, loopLabel));
                    break; // 跳转后结束当前基本块
                }
                AbstractInsnNode cloned = insn.clone(labelMap);
                if (insn instanceof LineNumberNode) {
                    cloned = insn.clone(labelMap);
                }
                // 克隆其他指令并修复标签
                newInstructions.add(cloned);
            }
        }

        // 4. 插入 default 分支
        newInstructions.add(defaultLabel);
        newInstructions.add(new InsnNode(Opcodes.RETURN));

        method.instructions = newInstructions;
    }

    private static LabelNode[] generateCaseLabels(ControlFlowGraph cfg, Map<LabelNode, LabelNode> labelMap) {
        int maxBlockId = Collections.max(cfg.blockMap.values());
        LabelNode[] labels = new LabelNode[maxBlockId];
        for (int i = 0; i < maxBlockId; i++) {
            labels[i] = new LabelNode();
            labelMap.put(new LabelNode(), labels[i]); // 映射原始标签到新标签
        }
        return labels;
    }

    private static class ControlFlowGraph {
        Map<AbstractInsnNode, Integer> blockMap;
    }
}