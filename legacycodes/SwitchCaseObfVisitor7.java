import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.tree.analysis.Frame;
import org.objectweb.asm.tree.analysis.*;

import java.util.*;

public class SwitchCaseObfVisitor7 {

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
        // 1. 初始分析
        Analyzer<BasicValue> analyzer = new Analyzer<>(new BasicInterpreter());
        try {
            analyzer.analyze(classNode.name, methodNode);
        } catch (AnalyzerException e) {
            throw new RuntimeException("Initial analysis failed", e);
        }

        // 2. 确定新变量索引
        int switchVarIndex = findNextAvailableLocalIndex(methodNode);

        // 3. 构建新的指令列表
        InsnList newInstructions = new InsnList();
        Map<LabelNode, Integer> labelToKeyMap = new HashMap<>();
        List<LabelNode> allLabels = new ArrayList<>();

        // 收集所有标签
        for (AbstractInsnNode insn : methodNode.instructions) {
            if (insn instanceof LabelNode) {
                allLabels.add((LabelNode) insn);
            }
        }

        // 为每个标签分配随机key
        for (LabelNode label : allLabels) {
            labelToKeyMap.put(label, new Random().nextInt());
        }

        // 创建SwitchCase结构
        LabelNode defaultLabel = new LabelNode();
        LabelNode switchStart = new LabelNode();
        LabelNode switchEnd = new LabelNode();

        // 添加初始switch变量设置
        newInstructions.add(new LdcInsnNode(0)); // 初始值
        newInstructions.add(new VarInsnNode(Opcodes.ISTORE, switchVarIndex));
        newInstructions.add(switchStart);

        // 构建switch指令
        int[] keys = labelToKeyMap.values().stream().mapToInt(i -> i).toArray();
        LabelNode[] labels = allLabels.toArray(new LabelNode[0]);
        newInstructions.add(new VarInsnNode(Opcodes.ILOAD, switchVarIndex));
        newInstructions.add(new LookupSwitchInsnNode(defaultLabel, keys, labels));

        // 复制原指令并插入switch控制
        boolean afterStore = false;
        for (AbstractInsnNode insn : methodNode.instructions) {
            // 在STORE指令后插入switch块
            if (afterStore && isControlFlowBoundary(insn)) {
                insertSwitchBlock(newInstructions, switchVarIndex, switchStart, defaultLabel);
                afterStore = false;
            }

            // 处理跳转指令
            if (insn instanceof JumpInsnNode) {
                JumpInsnNode jump = (JumpInsnNode) insn;
                if (labelToKeyMap.containsKey(jump.label)) {
                    newInstructions.add(new LdcInsnNode(labelToKeyMap.get(jump.label)));
                    newInstructions.add(new VarInsnNode(Opcodes.ISTORE, switchVarIndex));
                    newInstructions.add(new JumpInsnNode(Opcodes.GOTO, switchStart));
                    continue;
                }
            }

            newInstructions.add(insn);

            // 标记STORE指令后的位置
            if (isStoreInstruction(insn)) {
                afterStore = true;
            }
        }

        // 添加默认块（反调试）
        newInstructions.add(defaultLabel);
        newInstructions.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
                "java/lang/Thread", "currentThread", "()Ljava/lang/Thread;"));
        newInstructions.add(new InsnNode(Opcodes.POP));
        newInstructions.add(new InsnNode(Opcodes.RETURN));

        // 更新方法指令
        methodNode.instructions = newInstructions;
        methodNode.maxLocals = Math.max(methodNode.maxLocals, switchVarIndex + 1);
    }

    // 辅助方法
    private static boolean isStoreInstruction(AbstractInsnNode insn) {
        int opcode = insn.getOpcode();
        return opcode == Opcodes.ISTORE || opcode == Opcodes.LSTORE ||
                opcode == Opcodes.FSTORE || opcode == Opcodes.DSTORE ||
                opcode == Opcodes.ASTORE;
    }

    private static boolean isControlFlowBoundary(AbstractInsnNode insn) {
        return insn instanceof LabelNode ||
                insn instanceof JumpInsnNode ||
                insn instanceof TableSwitchInsnNode ||
                insn instanceof LookupSwitchInsnNode;
    }

    private static void insertSwitchBlock(InsnList insns, int varIndex, LabelNode switchStart, LabelNode defaultLabel) {
        insns.add(new JumpInsnNode(Opcodes.GOTO, switchStart));
        insns.add(defaultLabel);
        insns.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
                "java/lang/System", "currentTimeMillis", "()J"));
        insns.add(new InsnNode(Opcodes.POP2));
        insns.add(new JumpInsnNode(Opcodes.GOTO, switchStart));
    }

    private static int findNextAvailableLocalIndex(MethodNode methodNode) {
        int max = 0;
        for (AbstractInsnNode insn : methodNode.instructions) {
            if (insn instanceof VarInsnNode) {
                max = Math.max(max, ((VarInsnNode) insn).var);
            }
        }
        return max + 1;
    }

}

