package test;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Test {

    public static void main(String[] args) throws IOException {

        String[] chars = {"火", "花"};
        int targetCount = 1000;

        int base = chars.length;

        if (base == 0) {
            System.out.println("chars 不能为空");
            return;
        }

        // 只有一种字符时，无法生成不同组合，只能重复输出同一行来满足行数要求
        if (base == 1) {
            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
                for (int i = 0; i < targetCount + 1; i++) {
                    writer.write(chars[0]);
                    writer.newLine();
                }
            }
            return;
        }

        // 计算最小长度 len，使得 base^len > targetCount
        int len = 1;
        long total = base;
        while (total <= targetCount) {
            len++;
            total *= base;
        }

        System.out.println("字符种类数 = " + base);
        System.out.println("每个字符串由 " + len + " 个字符组成");
        System.out.println("总行数 = " + total + "（> " + targetCount + "）");
        System.out.println("正在写入 output.txt ...");

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            generate(chars, len, 0, new StringBuilder(), writer);
        }

        System.out.println("生成完成");
    }

    private static void generate(
            String[] chars,
            int len,
            int depth,
            StringBuilder sb,
            BufferedWriter writer
    ) throws IOException {
        if (depth == len) {
            writer.write(sb.toString());
            writer.newLine();
            return;
        }

        for (String c : chars) {
            sb.append(c);
            generate(chars, len, depth + 1, sb, writer);
            sb.delete(sb.length() - c.length(), sb.length());
        }
    }

}
