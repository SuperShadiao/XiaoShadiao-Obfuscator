package pers.XiaoShadiao.obfuscator.visitor;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import pers.XiaoShadiao.obfuscator.config.Config;
import pers.XiaoShadiao.obfuscator.utils.Utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.LongStream;

public class NumberObfVisitor extends AbstractVisitor {

    public NumberObfVisitor(byte[] bytes, String[] args) {
        super(bytes, args);
    }

    @Override
    public byte[] transfer(byte[] bytes) {
        ClassNode cn = byteToClassNode(bytes);
        for (MethodNode method : cn.methods) {
            InsnList insnList = new InsnList();
            for (AbstractInsnNode insnNode : method.instructions) {
                if(insnNode instanceof LdcInsnNode) {
                    LdcInsnNode ldcInsnNode = (LdcInsnNode) insnNode;
                    if(ldcInsnNode.cst instanceof Integer) {
                        visitInteger((Integer) ldcInsnNode.cst, insnList);
                        continue;
                    } else if(ldcInsnNode.cst instanceof Long) {
                        visitLong((Long) ldcInsnNode.cst, insnList);
                        continue;
                    }
                } else if(insnNode instanceof IntInsnNode) {
                    IntInsnNode intInsnNode = (IntInsnNode) insnNode;
                    if((intInsnNode.getOpcode() == Opcodes.BIPUSH || intInsnNode.getOpcode() == Opcodes.SIPUSH)) {
                        visitInteger(intInsnNode.operand, insnList);
                        continue;
                    }
                } else if(insnNode instanceof InsnNode) {
                    Integer in = null;
                    Long l = null;

                    int opcode = insnNode.getOpcode();
                    if(opcode >= 2 && opcode <= 8) {
                        in = opcode - 3;
                    } else if(opcode >= 9 && opcode <= 10) {
                        l = (long) (opcode - 9);
                    }
                    if(in != null) {
                        visitInteger(in, insnList);
                        continue;
                    } else if(l != null) {
                        visitLong(l, insnList);
                        continue;
                    }
                }
                insnList.add(insnNode);
            }
            method.instructions = insnList;
        }
        return classNodeToBytes(cn);
    }

    public static void visitInteger(int value, InsnList insnList) {
        if(Config.useLCMPNumber && Utils.r.nextBoolean()) {
            int number1 = Utils.r.nextInt(3) - 1;
            LongStream randLongs = LongStream.generate(Utils.r::nextLong).limit(2);
            boolean flag = Utils.r.nextBoolean();
            int opcode = flag ? Opcodes.IADD : Opcodes.ISUB;
            int number2 = flag ? value - number1 : value + number1;

            insnList.add(new LdcInsnNode(number2));
            if(number1 == -1) {
                randLongs.boxed().sorted(Comparator.naturalOrder()).forEach(l -> visitLong(l, insnList));
            } else if(number1 == 1) {
                randLongs.boxed().sorted(Comparator.reverseOrder()).forEach(l -> visitLong(l, insnList));
            } else if(number1 == 0) {
                long l = randLongs.findFirst().getAsLong();
                visitLong(l, insnList);
                visitLong(l, insnList);
            } else throw new AssertionError();
            insnList.add(new InsnNode(Opcodes.LCMP));
            insnList.add(new InsnNode(opcode));
            return;
        }

        int flag1 = Utils.r.nextInt(2);
        int number1 = Utils.r.nextInt();
        int number2 = flag1 == 0 ? number1 + value : value - number1;

        int[] nums = {number2, number1};
        for (int num : nums) {
            boolean flag2 = Utils.r.nextBoolean();
            int[] pairs = flag2 ? get_And_Nums(num) : get_Or_Nums(num);
            insnList.add(new LdcInsnNode(pairs[0]));
            insnList.add(new LdcInsnNode(pairs[1]));
            insnList.add(flag2 ? new InsnNode(Opcodes.IAND) : new InsnNode(Opcodes.IOR));
        }

        if (flag1 == 0) {
            // number2 = number1 + ((Integer) ldcInsnNode.cst);
            // number2 - number1 = ((Integer) ldcInsnNode.cst);
            insnList.add(new InsnNode(Opcodes.ISUB));
        } else {
            // number2 = ((Integer) ldcInsnNode.cst) - number1;
            // number2 + number1 = ((Integer) ldcInsnNode.cst);
            insnList.add(new InsnNode(Opcodes.IADD));
        }
    }

    public static void visitLong(long value, InsnList insnList) {
        int flag1 = Utils.r.nextInt(2);
        long number1 = Utils.r.nextLong();
        long number2 = flag1 == 0 ? number1 + value : value - number1;

        long[] nums = {number2, number1};
        for (long num : nums) {
            boolean flag2 = Utils.r.nextBoolean();
            long[] pairs = flag2 ? get_And_Nums_L(num) : get_Or_Nums_L(num);
            insnList.add(new LdcInsnNode(pairs[0]));
            insnList.add(new LdcInsnNode(pairs[1]));
            insnList.add(flag2 ? new InsnNode(Opcodes.LAND) : new InsnNode(Opcodes.LOR));
        }

        if (flag1 == 0) {
            // number2 = number1 + ((Integer) ldcInsnNode.cst);
            // number2 - number1 = ((Integer) ldcInsnNode.cst);
            insnList.add(new InsnNode(Opcodes.LSUB));
        } else {
            // number2 = ((Integer) ldcInsnNode.cst) - number1;
            // number2 + number1 = ((Integer) ldcInsnNode.cst);
            insnList.add(new InsnNode(Opcodes.LADD));
        }
    }

    @Override
    public List<String> getVisitorTags() {
        return Collections.emptyList();
    }

    // 元宝AI发力了。

    public static int[] get_And_Nums(int x) {
        Random random = Utils.r;

        // 基本思路：a 必须包含 x 的所有位
        // 可以在 x 的基础上添加任意其他位

        int a = x;
        int b = x;

        // 为 a 和 b 添加一些随机位
        // 注意：添加的位不能破坏 a & b = x
        // 即：如果我们在 a 的某位设为 1，那么 b 的对应位必须为 0
        // 如果我们在 b 的某位设为 1，那么 a 的对应位必须为 0

        // 生成一个随机掩码，用于添加额外位
        int extraMask = random.nextInt() & ~x;  // 确保不包含 x 的位

        // 将 extraMask 随机分配给 a 和 b
        int aExtra = extraMask & random.nextInt();
        int bExtra = extraMask & ~aExtra;  // 确保 aExtra 和 bExtra 不重叠

        a |= aExtra;
        b |= bExtra;

        if((a & b) != x) return get_And_Nums(x);
        return new int[] {a, b};
    }

    public static int[] get_Or_Nums(int x) {
        if (x == 0) {
            return new int[]{0, 0};
        }

        int a = 0;
        int b = 0;

        // 遍历每一位
        for (int i = 0; i < 32; i++) {
            int mask = 1 << i;

            if ((x & mask) != 0) {
                // x 的这一位是 1
                // 随机分配给 a 或 b 或两者
                int choice = Utils.r.nextInt(3);
                switch (choice) {
                    case 0: a |= mask; break;  // 只给 a
                    case 1: b |= mask; break;  // 只给 b
                    case 2:                     // 给两者
                        a |= mask;
                        b |= mask;
                        break;
                }
            }
            // 如果 x 的这一位是 0，a 和 b 都不设置该位
        }

        if((a | b) != x) return get_Or_Nums(x);
        return new int[] {a, b};
    }

    public static long[] get_And_Nums_L(long x) {
        Random random = Utils.r;

        // 基本思路：a 必须包含 x 的所有位
        // 可以在 x 的基础上添加任意其他位

        long a = x;
        long b = x;

        // 为 a 和 b 添加一些随机位
        // 注意：添加的位不能破坏 a & b = x
        // 即：如果我们在 a 的某位设为 1，那么 b 的对应位必须为 0
        // 如果我们在 b 的某位设为 1，那么 a 的对应位必须为 0

        // 生成一个随机掩码，用于添加额外位
        long extraMask = random.nextLong() & ~x;  // 确保不包含 x 的位

        // 将 extraMask 随机分配给 a 和 b
        long aExtra = extraMask & random.nextLong();
        long bExtra = extraMask & ~aExtra;  // 确保 aExtra 和 bExtra 不重叠

        a |= aExtra;
        b |= bExtra;

        if((a & b) != x) return get_And_Nums_L(x);
        return new long[] {a, b};
    }

    public static long[] get_Or_Nums_L(long x) {
        if (x == 0) {
            return new long[]{0, 0};
        }

        long a = 0;
        long b = 0;

        // 遍历每一位
        for (int i = 0; i < 64; i++) {
            long mask = 1L << i;

            if ((x & mask) != 0) {
                // x 的这一位是 1
                // 随机分配给 a 或 b 或两者
                int choice = Utils.r.nextInt(3);
                switch (choice) {
                    case 0: a |= mask; break;  // 只给 a
                    case 1: b |= mask; break;  // 只给 b
                    case 2:                     // 给两者
                        a |= mask;
                        b |= mask;
                        break;
                }
            }
            // 如果 x 的这一位是 0，a 和 b 都不设置该位
        }

        if((a | b) != x) return get_Or_Nums_L(x);
        return new long[] {a, b};
    }
}
