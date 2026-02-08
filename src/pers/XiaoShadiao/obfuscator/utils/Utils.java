package pers.XiaoShadiao.obfuscator.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.stream.IntStream;

import org.objectweb.asm.ClassWriter;
import pers.XiaoShadiao.obfuscator.config.Config;

public class Utils {

	public static Random r = new Random();

	public static String spawnRandomChar(int charCount, char range1, char range2) {
		char[] chars = new char[charCount];
		for(int i = 0;i < chars.length;i++) {
			chars[i] = (char) ((((r.nextInt(Integer.MAX_VALUE) >> r.nextInt(4) * r.nextInt(Integer.MAX_VALUE) << r.nextInt(4)) ^ r.nextInt(Integer.MAX_VALUE)) % (range2 - range1) + range1));
		}
		return new String(chars);
	}
	
	public static String spawnRandomChar(int charCount, boolean skipCheck) {
		if(true) return UnicodeNameGenerator.generateRandomName(charCount);
		char[] chars = new char[charCount];
		for(int i = 0;i < chars.length;i++) {
			chars[i] = (char) ((((r.nextInt(Integer.MAX_VALUE) >> r.nextInt(4) * r.nextInt(Integer.MAX_VALUE) << r.nextInt(4)) ^ r.nextInt(Integer.MAX_VALUE)) % (0xFFFF - 0x2000) + 0x2000));
		}
		String s = new String(chars);
		if(!skipCheck)
			try {
				getBytes(chars);
			} catch(IllegalArgumentException e) {
				// e.printStackTrace();
				return spawnRandomChar(charCount, skipCheck);
			}
		return s;
	}

	public static String spawnRandomChar() {
		return spawnRandomChar(r.nextInt(3) + 10, false);
	}

	private static byte[] getBytes(char[] s) {
		try {
			ByteBuffer bb = StandardCharsets.UTF_8.newEncoder()
					.onMalformedInput(CodingErrorAction.REPORT)
					.onUnmappableCharacter(CodingErrorAction.REPORT).encode(CharBuffer.wrap(s));
			int pos = bb.position();
			int limit = bb.limit();
			if (bb.hasArray() && pos == 0 && limit == bb.capacity()) {
				return bb.array();
			}
			byte[] bytes = new byte[bb.limit() - bb.position()];
			bb.get(bytes);
			return bytes;
		} catch (CharacterCodingException x) {
			throw new IllegalArgumentException(x);
		}
	}

	public static void CWvisit(ClassWriter cw, int version, int flags, String classname, String sign, String superclass, String[] implementss) {
		cw.visit(version, flags, classname, sign, superclass, implementss);
	}

	public static int[] spiltNumber(int i) {
		int[] il = new int[2];
		if(i > 0) {
			int temp = r.nextInt(i + 1);
			int temp2 = i - temp;
			il[0] = temp;
			il[1] = temp2;
		}
		return il;
	}

	public static String 可莉awa() {
		String s = "";
		for(int i = 0;i < 15;i++) {
			s += r.nextBoolean() ? "可" : "莉";
		}
		return s;
	}

	public static String getRandomMember(String[] s) {
		if(s == null) return null;
		if(s.length == 0) return "";
		return s[r.nextInt(s.length)];
	}

	public static <T> T getRandomMember(T[] s) {
		if(s == null) return null;
		if(s.length == 0) return null;
		return s[r.nextInt(s.length)];
	}

	public static byte[] 取is字节集列表(InputStream is) throws IOException {return 取is字节集列表(is,true);}
	
	public static byte[] 取is字节集列表(InputStream is,boolean close) throws IOException {
		
		if(is == null) {return new byte[0];}

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		
		int b;
        while((b = is.read()) != -1) {
            os.write(b);
            // System.out.println(b);
        }
        byte[] bl = os.toByteArray();

        try {
            os.close();
        } catch(Exception e) {
            
        }
        try {
            if(close) is.close();
        } catch(Exception e) {
            
        }
        
        return bl;
	}

    public static String getRandomNameFromMap() {
        if(Config.remapStrings == null || Config.remapStrings.length == 0) return UnicodeNameGenerator.generateRandomName(20);
        return getRandomMember(Config.remapStrings);
    }

    public static class UnicodeNameGenerator {

		public static final String deepseek = "Made by DeepSeek";
		private static final Random random = new Random();

		// 定义各个Unicode区间
		private static final int[][] UNICODE_RANGES = {
				{0x4DC0, 0x4DFF},   // 易经八卦符号
				{0xA640, 0xA69F},   // Cyrillic扩展-B
				{0x3400, 0x4DBF},   // CJK扩展A
				{0x1D400, 0x1D7FF}, // 数学字母数字
				{0x1F600, 0x1F64F}, // 表情符号
				{0x1F000, 0x1F02F}, // 麻将牌
				{0x2600, 0x26FF},   // 占星术符号
				{0x2700, 0x27BF}    // 装饰符号
		};

		// 合法首字符(包括常规字符)
		private static final int[][] START_CHAR_RANGES = {
				{0x0041, 0x005A},  // A-Z
				{0x0061, 0x007A},   // a-z
				{0x005F, 0x005F},   // _
				{0x0024, 0x0024},   // $
				{0x4DC0, 0x4DFF},   // 八卦符号
				{0xA640, 0xA69F},   // Cyrillic扩展
				{0x3400, 0x4DBF}    // CJK扩展A
		};

		public static String generateRandomName(int length) {

			if (length < 1) {
				throw new IllegalArgumentException("Length must be at least 1");
			}

			StringBuilder sb = new StringBuilder(length);

			// 生成首字符(不能是数字)
			sb.append(getRandomCharFromRanges(UNICODE_RANGES));

			// 生成剩余字符
			IntStream.range(1, length).forEach(i -> {
				sb.append(getRandomCharFromRanges(UNICODE_RANGES));
			});

			return sb.toString();
		}

		private static char getRandomCharFromRanges(int[][] ranges) {
			// 随机选择一个区间
			int[] range = ranges[random.nextInt(ranges.length)];
			int start = range[0];
			int end = range[1];

			// 处理需要代理对的情况(大于0xFFFF的字符)
			if (start > 0xFFFF) {
				return getSurrogatePairChar(start, end);
			} else {
				return (char) (start + random.nextInt(end - start + 1));
			}
		}

		private static char getSurrogatePairChar(int start, int end) {
			// 简化处理：只返回基本平面的随机字符
			// 实际使用时可以实现完整的代理对逻辑
			return (char) (0x4DC0 + random.nextInt(0x4DFF - 0x4DC0 + 1));
		}

		public static void main(String[] args) {
			// 生成10个随机名称测试
			for (int i = 0; i < 10; i++) {
				int length = 5 + random.nextInt(6); // 5-10个字符长度
				System.out.println(generateRandomName(length));
			}
		}
	}
}