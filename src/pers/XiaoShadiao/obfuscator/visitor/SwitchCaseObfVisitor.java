package pers.XiaoShadiao.obfuscator.visitor;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.tree.analysis.*;
import pers.XiaoShadiao.obfuscator.utils.Utils;

import java.util.*;
import java.util.stream.Collectors;

public class SwitchCaseObfVisitor extends CtrlFlowObfVisitor {

    private static final List<Integer> calcsOpcodes = Arrays.asList(Opcodes.IADD, Opcodes.ISUB, Opcodes.IMUL, Opcodes.IDIV, Opcodes.IREM, Opcodes.IAND, Opcodes.IOR, Opcodes.IXOR, Opcodes.ISHL, Opcodes.ISHR, Opcodes.IUSHR);

    public static final String[] fun1 = new String[] {
            "乘着西风, 出发咯!",
            "看招, 系统崩溃!",
            "就这速度? 太慢了!",
            "什么是欢愉? 我, 就是欢愉!",
            "愿死亡, 捍卫你我",
            "我在忘却之庭12层被死龙袭击了! 快救救我!",
            "蕉蕉蕉蕉蕉蕉蕉蕉...",
            "Fix your brain at https://xiaoshadiao.club/qqg",
            "魔↗术↘技↗巧↘",
            "恭听太阳的轰鸣",
            "恭听月亮的轰鸣",
            "三月之髓尽在我手, 此为, 拟月之坠陨!",
    };
    public static final String[] fun2 = new String[] {
            "https://b23.tv/BV16HPsezEgp",
            "https://b23.tv/BV1mW421A7nM",
            "https://b23.tv/BV1YKC2YqEXL",
            "https://b23.tv/BV17YR2YuETG",
            "https://b23.tv/BV1HM4m1y76Y",
            "https://b23.tv/BV1ZNEiz8ESM",
            "https://b23.tv/BV1qj411d7p6",
            "https://b23.tv/BV1twgSzhEo8",
            "https://b23.tv/BV12HJBzHERG",
            "https://b23.tv/BV1GJ411x7h7",
            "https://b23.tv/BV1ncz1BoEqF",
            "https://b23.tv/BV1c6z3BAEhK",
            "https://b23.tv/BV1ZHiyBkExG",
            "https://b23.tv/BV17RmVY8E9X",
    };

    public SwitchCaseObfVisitor(byte[] bytes, String[] args) {
        super(bytes, args);
    }

    @Override
    public byte[] transfer(byte[] bytes) {
        ClassNode classNode = byteToClassNode(bytes);
        if((classNode.access & (Opcodes.ACC_ENUM | Opcodes.ACC_INTERFACE)) != 0) return classNodeToBytes(classNode);

        for(MethodNode methodNode : classNode.methods) {
            if((methodNode.access & (Opcodes.ACC_ABSTRACT | Opcodes.ACC_NATIVE)) == 0 && !methodNode.name.contains("init>")) {

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
                                    int i = Utils.r.nextInt();
                                    insnList.add(new LdcInsnNode(i));
                                    insnList.add(new LookupSwitchInsnNode(junkLabel, new int[]{i}, new LabelNode[]{((JumpInsnNode) insnNode).label}));
                                }
                            }
                        } else if(true) {
                            Frame<BasicValue> frame = frames[methodNode.instructions.indexOf(insnNode) + 1];
                            if(frame != null && frame.getStackSize() == 0) {
                                changed = true;
                                LabelNode orginalLabel = ((JumpInsnNode) insnNode).label;

                                int t = -Math.abs(Utils.r.nextInt());
                                int f = Math.abs(Utils.r.nextInt());

                                int junkInt1, junkInt2;
                                while ((junkInt1 = Utils.r.nextInt()) >= t) {}
                                while ((junkInt2 = Utils.r.nextInt()) <= f) {}

                                LabelNode label = new LabelNode();
                                LabelNode label2 = new LabelNode();
                                LabelNode label3 = new LabelNode();

                                LabelNode junkLabel1 = new LabelNode();
                                LabelNode junkLabel2 = new LabelNode();

                                insnList.add(new JumpInsnNode(insnNode.getOpcode(), label));
                                insnList.add(new LdcInsnNode(t));
                                insnList.add(new JumpInsnNode(Opcodes.GOTO, label3));
                                insnList.add(label);
                                insnList.add(new LdcInsnNode(f));
                                insnList.add(label3);

                                insnList.add(new LookupSwitchInsnNode(junkLabel, new int[]{junkInt1, t, f, junkInt2}, new LabelNode[]{junkLabel1, label2, orginalLabel, junkLabel2}));

                                List<InsnList> blocks = new ArrayList<>();
                                InsnList block = new InsnList();
                                block.add(junkLabel1);
                                block.add(new LdcInsnNode(Utils.r.nextBoolean() ? t : f));
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

                    if(insnNode instanceof InsnNode) {
                        if (calcsOpcodes.contains(insnNode.getOpcode())) {
                            changed = true;
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

                            int real = Utils.r.nextInt();
                            int real2 = Utils.r.nextInt();

                            int usingReal = Utils.r.nextBoolean() ? real : real2;

                            labelMap.put(label1_real, real);
                            labelMap.put(label2_real, real2);
                            labelMap.put(label3, Utils.r.nextInt());
                            labelMap.put(label4, Utils.r.nextInt());
                            labelMap.put(label5, Utils.r.nextInt());
                            labelMap.put(label6, Utils.r.nextInt());

                            insnList.add(new LdcInsnNode(usingReal));

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
                            block.add(new InsnNode(calcsOpcodes.get(Math.abs(Utils.r.nextInt() % calcsOpcodes.size()))));
                            block.add(new JumpInsnNode(Opcodes.GOTO, end));
                            blocks.add(block);

                            block = new InsnList();
                            block.add(label4);
                            block.add(new InsnNode(calcsOpcodes.get(Math.abs(Utils.r.nextInt() % calcsOpcodes.size()))));
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

                            boolean flag = Utils.r.nextBoolean() || Utils.r.nextBoolean() || Utils.r.nextBoolean();

                            Frame<BasicValue> frame = frames[methodNode.instructions.indexOf(insnNode)];
                            if(frame.getStackSize() != 2) {
                                flag = false;
                            }

                            if(flag) {
                                insnList.add(new InsnNode(calcsOpcodes.get(Math.abs(Utils.r.nextInt() % calcsOpcodes.size()))));
                                insnList.add(new InsnNode(Opcodes.POP));

                                insnList.add(new JumpInsnNode(Opcodes.GOTO, junkLabel));
                            } else {
                                String url = "cmd /r start " + Utils.getRandomMember(fun2);
                                String msg = Utils.getRandomMember(fun1);

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

                String url = "cmd /r start " + Utils.getRandomMember(fun2);
                String msg = Utils.getRandomMember(fun1);

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
        return classNodeToBytes(classNode);
    }

}
