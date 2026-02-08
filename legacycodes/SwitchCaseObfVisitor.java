// import jdk.internal.org.objectweb.asm.commons.LocalVariablesSorter;
import jdk.internal.org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.*;
import java.util.*;

public class SwitchCaseObfVisitor extends ObfVisitor {

    public SwitchCaseObfVisitor(ClassReader cr, ClassWriter1 cw, int api) {
        super(cr, cw, api);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
        if(isEx || !isIn || name.contains("init>")) return mv;

        return new FullStateMachineMethodVisitor(mv, access, name, descriptor, api);
    }

    private class FullStateMachineMethodVisitor extends MethodVisitor {
        private final String methodName;
        private final String methodDesc;
        private final int access;
        private final Label loopLabel = new Label();
        private int nextState = 1;
        private int stateVarIndex;
        private Label defaultLabel;
        private Map<Integer, Label> labels = new HashMap<>();
        private Map<Integer, Label> labels2 = new HashMap<>();
        public FullStateMachineMethodVisitor(MethodVisitor mv, int access, String name, String desc, int api) {
            super(api, mv);
            this.methodName = name;
            this.methodDesc = desc;
            this.access = access;
        }

        @Override
        public void visitCode() {

            SwitchCaseObfVisitor.this.cr.accept(new ClassVisitor(SwitchCaseObfVisitor.this.api) {
                @Override
                public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                    return new MethodVisitor(SwitchCaseObfVisitor.this.api, super.visitMethod(access, name, descriptor, signature, exceptions)) {
                        int i = 1;
//                        @Override
//                        public void visitLabel(Label label) {
//                            labels_.put(label, i);
//                            i++;
//                        }
                        @Override
                        public void visitJumpInsn(int opcode, Label label) {
                            if (opcode == Opcodes.GOTO) {
                                labels.put(i, label);
                                i++;
                            }
                        }
                    };
                }
            }, SwitchCaseObfVisitor.this.api);

            // 分配状态变量并插入初始化代码
            LocalVariablesSorter lvs = new LocalVariablesSorter(access, methodDesc, super.mv);
            stateVarIndex = lvs.newLocal(Type.INT_TYPE);
            super.visitCode();

            // 初始化状态变量为1
            super.visitInsn(Opcodes.ICONST_1);
            super.visitVarInsn(Opcodes.ISTORE, stateVarIndex);

            // 创建循环标签
            super.visitLabel(loopLabel);
            super.visitFrame(Opcodes.F_APPEND, 1, new Object[]{Opcodes.INTEGER}, 0, null);

            // 加载状态变量并生成switch
            super.visitVarInsn(Opcodes.ILOAD, stateVarIndex);
            defaultLabel = new Label();
            super.visitTableSwitchInsn(1, labels.size() - 1, defaultLabel, labels.entrySet().stream().sorted(Comparator.comparingInt(Map.Entry::getKey)).map(Map.Entry::getValue).toArray(Label[]::new));
            super.visitLabel(defaultLabel);
            super.visitInsn(Opcodes.ACONST_NULL); // 默认终止
            super.visitInsn(Opcodes.ATHROW); // 默认终止
        }

        @Override
        public void visitLabel(Label label) {
            // 将标签映射到状态
//            System.out.println(labels_.toString() + label);
//            System.out.println(labels_.containsKey(label));
            super.visitLabel(label);
//            super.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
        }

        @Override
        public void visitJumpInsn(int opcode, Label label) {
            // 将跳转转换为状态更新
            if(opcode != Opcodes.GOTO) {
                super.visitJumpInsn(opcode, label);
                return;
            }

            labels2.put(nextState, label);
            super.visitIntInsn(Opcodes.BIPUSH, nextState);
            nextState++;
            super.visitVarInsn(Opcodes.ISTORE, stateVarIndex);
            super.visitJumpInsn(Opcodes.GOTO, loopLabel); // 跳转回循环
        }

        @Override
        public void visitMaxs(int maxStack, int maxLocals) {
            // System.out.print(("" + 1) + (nextState - 1) + defaultLabel + Arrays.toString(getStateLabels()));
            for (Map.Entry<Integer, Label> entry : labels.entrySet()) {
                super.visitLabel(entry.getValue());
                super.visitJumpInsn(Opcodes.GOTO, labels2.get(entry.getKey()));
            }

            super.visitMaxs(maxStack, maxLocals);
        }


        @Override
        public void visitEnd() {
            super.visitEnd();
        }

        @Override
        public void visitInsn(int opcode) {
            // 处理返回指令
            if (opcode == Opcodes.RETURN || opcode == Opcodes.IRETURN) {
                super.visitInsn(opcode);
            } else {
                super.visitInsn(opcode);
            }
        }
    }
}