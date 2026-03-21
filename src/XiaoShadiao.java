import java.io.*;
import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles.Lookup;

import pers.XiaoShadiao.NMSLException;
import pers.XiaoShadiao.obfuscator.Main;
import sun.misc.Unsafe;

import java.lang.invoke.MethodType;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Field;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class XiaoShadiao implements Serializable {

	private static final int consts = 5000;
	private static final long serialVersionUID = 1145141919810L;
	public static final boolean _________;
	public static final String xsd_1,xsd_2,xsd_3;

	static {

        byte[] a = new byte[11];
		boolean b = false;
		try(InputStream is = XiaoShadiao.class.getResourceAsStream("XiaoShadiao.txt")) {
			is.read(a);
			b = new String(a).equals("XiaoShadiao");
		} catch (Exception e) { }

		String xsd1 = "代替换文本1";
		String xsd2 = "代替换文本2";
		String xsd3 = "代替换文本3";

		try {
			InputStream stream = XiaoShadiao.class.getResourceAsStream("XiaoShadiaoStuff");
			if (stream != null) {

				byte[] bytes = new byte[stream.available()];
				stream.read(bytes);
				stream.close();
				for (int i = 0; i < bytes.length; i++) {
					bytes[i] ^= (byte)(new Random(i).nextInt(256));
				}

				// try { Thread.sleep(30000); } catch (InterruptedException ignored) {}

				ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
				XiaoShadiao ii = i = (XiaoShadiao) ois.readObject();
				ois.close();
				ii.init();
			}
		} catch (Throwable e) {
		}

		boolean isErrored = false;
        try {

			Field f = xsd1.getClass().getDeclaredField("value");
			f.setAccessible(true);
			Object obj = f.get(xsd1);
			if (obj instanceof byte[]) {
				Field f2 = xsd1.getClass().getDeclaredField("coder");
				f2.setAccessible(true);

				f2.set(xsd1, f2.get(xsd2));
				f.set(xsd1, f.get(xsd2));
			} else if (obj instanceof char[]) {
				f.set(xsd1, xsd2.toCharArray());
			} else throw new RuntimeException("idk");

		} catch (Throwable e) {
            // throw new RuntimeException("当前Jar包已经受XiaoShadiao Obfuscator混淆, 你当前java版本可能不支持运行该jar包.", e);
			isErrored = true;
        }

		xsd_1 = xsd1;
		xsd_2 = isErrored ? xsd1 : xsd2;
		xsd_3 = xsd3;

		_________ = b;

        boolean flag = false;
        try {
            flag = Optional.ofNullable(Main.class.getProtectionDomain()).map(ProtectionDomain::getCodeSource).map(CodeSource::getLocation).map(URL::getPath).isPresent();
        } catch(Throwable ignored) {

        }
        if (!flag) {
            System.out.println("======================================================");
            System.out.println("                                                      ");
            System.out.println("  X   X  III   AAA   OOOOO                            ");
            System.out.println("   X X    I   A   A  O   O                            ");
            System.out.println("    X     I   AAAAA  O   O                            ");
            System.out.println("   X X    I   A   A  O   O                            ");
            System.out.println("  X   X  III  A   A  OOOOO                            ");
            System.out.println("                                                      ");
            System.out.println("          SSSS  H   H  AAA     DDDD  III  AAA  OOOOO  ");
            System.out.println("         S      H   H A   A    D   D  I  A   A O   O  ");
            System.out.println("          SSSS  HHHHH AAAAA    D   D  I  AAAAA O   O  ");
            System.out.println("              S H   H A   A    D   D  I  A   A O   O  ");
            System.out.println("          SSSS  H   H A   A    DDDD  III A   A OOOOO  ");
            System.out.println("                                                      ");
            System.out.println("             XiaoShadiao     Obfuscator               ");
            System.out.println("                                                      ");
            System.out.println("                                   ---by XiaoShadiao  ");
            System.out.println("                                                      ");
            System.out.println("  QQ Group:  https://xiaoshadiao.club/qqg             ");
            System.out.println("  Download:  https://xiaoshadiao.club/obfdownload     ");
            System.out.println("  Bilibili:  https://xiaoshadiao.club/xsdb            ");
            System.out.println("                                                      ");
            System.out.println("======================================================");
        }
	}
	
	public static Object 最喜欢玩原神了(Object lookup, Object idk, Object mt, Object type, Object key, Object clazz, Object method, Object des) {

		try {

			// System.out.println("decode start");
			// if(!_________) new Throwable().printStackTrace();
			
			int key1 = (Integer) key;
			char[] charKey = new char[key1 % 10 + 10];
			for(int i = 0;i < charKey.length;i++) {
				key1 += (key1 * (key1 >> 5) + (key1 << 5)) + 114514;
				charKey[i] = (char) (key1 % 0x10000);
			}
			
			char[] charOwner = clazz.toString().toCharArray();
			char[] charName = method.toString().toCharArray();
			char[] charDescriptor = des.toString().toCharArray();
			for(int i = 0;i < charOwner.length;i++) {
				charOwner[i] = (char) (charOwner[i] ^ charKey[i % charKey.length]);
			}
			for(int i = 0;i < charName.length;i++) {
				charName[i] = (char) (charName[i] ^ charKey[i % charKey.length]);
			}
			for(int i = 0;i < charDescriptor.length;i++) {
				charDescriptor[i] = (char) (charDescriptor[i] ^ charKey[i % charKey.length]);
			}
			
			MethodHandle mh = null;
			// System.out.println(new String(charOwner) + " " + new String(charName) + " " + new String(charDescriptor) + " start");
			switch(type.hashCode()) {
				case -2051695357:
					mh = ((Lookup) lookup).findStatic(
							Class.forName(new String(charOwner)), 
							new String(charName), 
							MethodType.fromMethodDescriptorString(
									new String(charDescriptor), 
									XiaoShadiao.class.getClassLoader()
							)
					);
					break;
				case 859384035:
					mh = ((Lookup) lookup).findVirtual(
							Class.forName(new String(charOwner)), 
							new String(charName), 
							MethodType.fromMethodDescriptorString(
									new String(charDescriptor), 
									XiaoShadiao.class.getClassLoader()
							)
					);
					break;	
				default:
					throw new NMSLException("玩原神玩的");
			}
			// System.out.println(new String(charOwner) + " " + new String(charName) + " " + new String(charDescriptor) + " type");
			mh = mh.asType((MethodType) mt);

			// System.out.println(new String(charOwner) + " " + new String(charName) + " " + new String(charDescriptor) + " new");

            // System.out.println(new String(charOwner) + " " + new String(charName) + " " + new String(charDescriptor) + " end");
			return new ConstantCallSite(mh);
			
		} catch(Throwable e) {
			throw new NMSLException("发生异常了, 一定是你玩原神了, 请你马上卸载原神", e);
		}
		
	}

	public static Object 最喜欢玩原神了(Object lookup, Object idk, Object mt, Object type, Object clazz, Object method, Object des) {

		try {

			MethodHandle mh = null;
			// System.out.println(clazz + " " + method + " " + des);
			switch(type.hashCode()) {
				case -2051695357:
					mh = ((Lookup) lookup).findStatic(
							Class.forName(clazz.toString()), 
							new String(method.toString()), 
							MethodType.fromMethodDescriptorString(
									new String(des.toString()), 
									XiaoShadiao.class.getClassLoader()
							)
					);
					break;
				case 859384035:
					mh = ((Lookup) lookup).findVirtual(
							Class.forName(clazz.toString()), 
							new String(method.toString()), 
							MethodType.fromMethodDescriptorString(
									new String(des.toString()), 
									XiaoShadiao.class.getClassLoader()
							)
					);
					break;
				default:
					throw new NMSLException("玩原神玩的");
			}

			// System.out.println("test2");

			// System.out.println(clazz + " " + method + " " + des);
			mh = mh.asType((MethodType) mt);

			// System.out.println(clazz + " " + method + " " + des);
			return new ConstantCallSite(mh);
		} catch(Throwable e) {
			throw new NMSLException("发生异常了, 一定是你玩原神了, 请你马上卸载原神", e);
		}
		
	}

	public static HashMap<Object, Object> hashMap;

	public static String 原神启动(Object s) {
		if(hashMap == null) hashMap = new HashMap<>();
		if(hashMap.containsKey(s)) {
            return hashMap.get(s).toString();
        }
		char[] newchars;
		if(s instanceof Throwable) {
			int key = ((Throwable) s).getStackTrace()[0].getLineNumber();
			int key2 = 0;
			int key3 = 0;
			char[] chars = ((Throwable) s).getLocalizedMessage().toCharArray();
			char[] chars2 = s.getClass().getName().toCharArray();
			
			newchars = new char[chars.length - 1];
			
			for(int i = 0;i < chars.length;i++) {
				if(i == 0) key2 = chars[i];
				else {
					key2 <<= key2 + (key2 % 4) + "dynamicStringKey".hashCode();// 123456789;
					key3 = (key2 / 9) >> 2;
					newchars[i - 1] = (char) (chars[i] ^ ((((key * (i)) ^ (key2 + key3)) ^ chars2[(i- 1) % chars2.length]) % 0x10000));
				}
			}
		} else {
			char key = 0;
			char[] chars = s.toString().toCharArray();
			
			newchars = new char[chars.length - 1];
			
			for(int i = 0;i < chars.length;i++) {
				if(i == 0) key = chars[i];
				else {
					newchars[i - 1] = (char) (chars[i] ^ (key * i));
				}
			}
		}

		synchronized (hashMap) {
			return hashMap.computeIfAbsent(s, k -> new String(newchars)).toString();
		}
	}

	public static long 原神启动(long l1, long l2, long l3) {
		if(_________) return l1 ^ l2 ^ l3 ^ -1;
		return (l1 ^ l2) ^ (~l3);
	}
	
	public static int 原神启动(int i1, int i2, int i3) {
		if(_________) return i1 ^ i2 ^ i3 ^ -1;
		return (i1 ^ i2) ^ (~i3);
	}
	
	public static void log(Object obj) {
		if(!_________) System.out.println(obj);
	}
	
	public static String test(String s) {
		System.out.println(s);
		return s;
	}

//	public static String getXSDString1() {
//		return xsd_1;
//	}
//
//	public static String getXSDString2() {
//		return xsd_2;
//	}
//
//	public static String getXSDString3() {
//		return xsd_3;
//	}




















	// ============================ INSTANCE AERA =================================
	
	private transient final Object type, clazz, method, des;
	private transient Object cache;
	
	public XiaoShadiao(Object type, Object clazz, Object method, Object des) {
		this.type = type;
		this.clazz = clazz;
		this.method = method;
		this.des = des;
	}

	public XiaoShadiao() {
		this(null, null, null, null);
	}

	public static final ConcurrentHashMap<String, Map.Entry<XiaoShadiao[], Integer>> map = new ConcurrentHashMap<>();
	public static Set<Map.Entry<String, Map.Entry<XiaoShadiao[], Integer>>> entries = map.entrySet();
	// public static String[] s = new String[1024];
	// public static XiaoShadiao[][] a = new XiaoShadiao[1024][];
	
	public static Object invoke(Object lookup, Object idk, Object mt, Object var1, Object var2, Object var3, Object var4) throws Throwable {

		String var11 = 原神启动(var1);
		String var22 = 原神启动(var2);
		// XiaoShadiao[] x = map.get(var11);
		Map.Entry<XiaoShadiao[], Integer> x = map.computeIfAbsent(var11, k -> {
			try {
				Class<?> clazz = Class.forName(var11);
				MethodHandle mh = ((Lookup) lookup).findStaticGetter(
						clazz,
                        var22,
						XiaoShadiao[].class
				);
				XiaoShadiao[] value = (XiaoShadiao[]) mh.invokeExact();
				int m = 0;
//				if(clazz.getAnnotation(DiaoXiaosha.class) != null) {
//					DiaoXiaosha ann = clazz.getAnnotation(DiaoXiaosha.class);
//					int j = ann.𝑪𝒊𝒂𝒍𝒍𝒐();
//					m ^= j;
//					Random r = new Random(j);
//					do {
//
//						ann = (DiaoXiaosha) ann.getClass().getMethod("C").invoke(ann);
//						if(ann == null) break;
//
//						int temp = ann.𝑪𝒊𝒂𝒍𝒍𝒐();
//						int temp2 = r.nextInt();
//						// System.out.println(temp + " = " + temp2);
//
//						if(temp == 0) break;
//						if(temp != temp2) throw new NMSLException();
//						m ^= temp;
//
//					} while(true);
//
//					// System.out.println("Hash result: " + m);
//				}
				return new AbstractMap.SimpleEntry<>(value, m);
			} catch (Throwable e) {
				// e.printStackTrace();
				throw new NMSLException();
			}
		});

        return (x.getKey())[var11.hashCode() ^ var22.hashCode() ^ var3.hashCode() ^ var4.hashCode() ^ x.getValue()].invoke0(lookup, mt);
	}
	
	
	
	public Object invoke0(Object lookup, Object mt) {
		// System.out.println("11");
		if(cache == null) {

			try {

				Throwable t = (Throwable)  clazz;
				// System.out.println("decode start");
				// if(!_________) new Throwable().printStackTrace();
				char cc = 0;
				char[] chars = t.getLocalizedMessage().toCharArray();
				int key = t.getStackTrace()[0].getLineNumber();
				for(int i = 0;i < chars.length;i++) {
					chars[i] = (char) (chars[i] ^ ((cc ^ (key * (chars.length - i)) ^ t.getClass().getName().charAt(i % t.getClass().getName().length())) % 0x10000));
					cc = chars[i];
				}
				String charOwner = new String(chars);
				
				t = (Throwable) method;
				cc = 0;
				chars = t.getLocalizedMessage().toCharArray();
				key = t.getStackTrace()[0].getLineNumber();
				for(int i = 0;i < chars.length;i++) {
					chars[i] = (char) (chars[i] ^ ((cc ^ (key * (chars.length - i)) ^ t.getClass().getName().charAt(i % t.getClass().getName().length())) % 0x10000));
					cc = chars[i];
				}
				String charName = new String(chars);
				
				t = (Throwable) des;
				cc = 0;
				chars = t.getLocalizedMessage().toCharArray();
				key = t.getStackTrace()[0].getLineNumber();
				for(int i = 0;i < chars.length;i++) {
					chars[i] = (char) (chars[i] ^ ((cc ^ (key * (chars.length - i)) ^ t.getClass().getName().charAt(i % t.getClass().getName().length())) % 0x10000));
					cc = chars[i];
				}
				String charDescriptor = new String(chars);
				
				
				MethodHandle mh = null;
				// System.out.println(new String(charOwner) + " " + new String(charName) + " " + new String(charDescriptor) + " 12");
				switch(type.hashCode()) {
					case -2051695357:
						mh = ((Lookup) lookup).findStatic(
								Class.forName(new String(charOwner)), 
								new String(charName), 
								MethodType.fromMethodDescriptorString(
										new String(charDescriptor), 
										XiaoShadiao.class.getClassLoader()
								)
						);
						break;
					case 859384035:
						mh = ((Lookup) lookup).findVirtual(
								Class.forName(new String(charOwner)), 
								new String(charName), 
								MethodType.fromMethodDescriptorString(
										new String(charDescriptor), 
										XiaoShadiao.class.getClassLoader()
								)
						);
						break;	
					default:
						throw new NMSLException("玩原神玩的");
				}
				// System.out.println(new String(charOwner) + " " + new String(charName) + " " + new String(charDescriptor) + " type");
				try {
					mh = mh.asType((MethodType) mt);
				} catch(Exception e) {
					e.printStackTrace();
				}

				// System.out.println(new String(charOwner) + " " + new String(charName) + " " + new String(charDescriptor) + " new");

                // System.out.println(new String(charOwner) + " " + new String(charName) + " " + new String(charDescriptor) + " end");
				cache = new ConstantCallSite(mh);
				
			} catch(Throwable e) {
				throw new NMSLException("发生异常了, 一定是你玩原神了, 请你马上卸载原神", e);
			}
			
		}
		return cache;
	}






	public static final boolean unusedObject = false;

	public static XiaoShadiao i;

	public void init() throws Exception {

		Field[] f = getClass().getFields();
		int hash = 0;
		boolean a = false;
		String fieldName = "unusedObject";
		// int i = 0;
		for(Field field : f) {
			if(field.getName().endsWith("XiaoShadiao666") && !fieldName.equals(field.getName())) {

				int hash1 = field.get(this).hashCode();
//				long t = System.currentTimeMillis();
//				long startTime = ManagementFactory.getRuntimeMXBean().getStartTime();
//
//				long delta = t - startTime;
//				if(delta > 1000 || delta < 0) {
//					hash1 ^= (int) startTime;
//					hash += hash1;
//					hash1 *= 2;
//					a = true;
//				}

				List<String> arguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
				if(arguments != ManagementFactory.getRuntimeMXBean().getInputArguments()) {
					// System.out.println("XiaoShadiao is breaking hash!");
					hash1 ^= arguments.hashCode();
					hash += hash1;
					hash1 *= 2;
					a = true;
				}
				try {
					arguments.add("");
					// System.out.println("XiaoShadiao is breaking hash!");
					hash1 ^= arguments.hashCode();
					hash += hash1;
					hash1 *= 2;
					a = true;
				} catch (Exception ignored) {

				}

				for (String string : arguments) {
					if (string.contains("-javaagent:")) {
						// System.out.println("XiaoShadiao is breaking hash!");
						hash1 ^= arguments.hashCode();
						hash += hash1;
						hash1 *= 2;
						a = true;
					}
					if (string.contains("-Xrunjdwp:")) {
						// System.out.println("XiaoShadiao is breaking hash!");
						hash1 ^= arguments.hashCode();
						hash += hash1;
						hash1 *= 2;
						a = true;
					}
					if (string.contains("-agentlib:jdwp")) {
						// System.out.println("XiaoShadiao is breaking hash!");
						hash1 ^= arguments.hashCode();
						hash += hash1;
						hash1 *= 2;
						a = true;
					}
					if (string.contains("-Xdebug")) {
						// System.out.println("XiaoShadiao is breaking hash!");
						hash1 ^= arguments.hashCode();
						hash += hash1;
						hash1 *= 2;
						a = true;
					}
				}
                if(a) field.set(this, new Random(fieldName.hashCode()).nextInt() ^ hash1 ^ ((Boolean) a).hashCode() ^ 1231);

				hash ^= hash1;
			}
		}

		getClass().getField(fieldName).set(this, hash);

		try {
			init2();
		} catch(Throwable e) {
			if(e instanceof UnsupportedOperationException) throw new NMSLException("Please use < java 17 to run");
		}

		if(a) {
			Thread thread = new Thread(() -> {
				try {
					Thread.sleep(consts);
					Field field;
					field = Unsafe.class.getDeclaredField("theUnsafe");
					field.setAccessible(true);
					((Unsafe) field.get(null)).putAddress(0, 0);
				} catch (Throwable ignored) {
				}
			});
			thread.setDaemon(true);
			thread.start();
		}
		// System.out.println("Final: " + fieldName + ": " + hash);

	}

	public void init2() {

		System.setSecurityManager(new ShaXiaodiao());
		Thread thread1 = new Thread(() -> {
			try {
				while (true) {
					Thread.sleep(1000);
					if(System.getSecurityManager() instanceof ShaXiaodiao) {
						System.setSecurityManager(new ShaXiaodiao());
					}
				}
			} catch (Exception ignored) {

			}
			i = null;
		});
		thread1.setDaemon(true);
		thread1.start();

	}

//	@Override
//	public void visit(Map<String, ClassNode> classes) {
//		AnnotationNode bert = new AnnotationNode(Type.getDescriptor(Deprecated.class));
//
//		bert.values = new ArrayList<>();
//
//
//		AnnotationNode bert2 = bert;
//
//		for (int i = 0; i < 3000; i++) {
//			bert2.visit("", bert2 = new AnnotationNode("V()"));
//		}
//		classes.values().forEach(classNode -> {
//
//			if (classNode.visibleAnnotations == null) {
//				classNode.visibleAnnotations = new ArrayList<>();
//			}
//			classNode.visibleAnnotations.add(bert);
//		});
//	}
}
