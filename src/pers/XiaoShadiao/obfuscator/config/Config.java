package pers.XiaoShadiao.obfuscator.config;

import org.objectweb.asm.Opcodes;
import pers.XiaoShadiao.NMSLException;

import java.io.File;
import java.lang.instrument.IllegalClassFormatException;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

public class Config {

    public static String[] remapStrings = null;
    public static int ASM_API = Opcodes.ASM5;
    public static File inputFile = null;
    public static File outputFile = null;

    public static final  Class<?>[] exceptions = new Class[] {
            NullPointerException.class,
            OutOfMemoryError.class,
            StackOverflowError.class,
            RuntimeException.class,
            NMSLException.class,
            VerifyError.class,
            IllegalAccessError.class,
            IndexOutOfBoundsException.class,
            ClassFormatError.class,
            ConcurrentModificationException.class,
            IllegalClassFormatException.class,
            NoSuchElementException.class,
            ArithmeticException.class,
            UnsupportedOperationException.class
    };

    public static boolean dontverify;
    public static boolean verifyWith508ASM;

    public static boolean visitorFollowCmdLineOrder;

    public static boolean useAntiDebugger;
    public static boolean useSecurityManagerAntiDebugger;
    public static boolean classToFolder;
    public static boolean useLCMPNumber;
}
