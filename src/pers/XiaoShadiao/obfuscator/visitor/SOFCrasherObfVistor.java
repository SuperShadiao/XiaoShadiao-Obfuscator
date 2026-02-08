package pers.XiaoShadiao.obfuscator.visitor;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.analysis.AnalyzerException;

import java.lang.annotation.Annotation;
import java.util.*;

public class SOFCrasherObfVistor extends AbstractVisitor {

    public static final Map<String, Integer> hashmap = new HashMap<>();

    public static final String author = "Codes by CliffV2";

    public SOFCrasherObfVistor(byte[] bytes, String[] args) {
        super(bytes, args);
    }

    public byte[] transfer(byte[] classData) {
        ClassNode classNode = byteToClassNode(classData);

        if((classNode.access & (Opcodes.ACC_ENUM | Opcodes.ACC_INTERFACE)) != 0) return classData;

        Class<?> DiaoXiaoSha;
        try {
            DiaoXiaoSha = Class.forName("DiaoXiaosha");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        AnnotationNode bert = new AnnotationNode(Type.getDescriptor(DiaoXiaoSha));

        AnnotationNode bert2 = bert;

        bert.values = new ArrayList<>();

        Random r = new Random();
        int k = r.nextInt();
        int result = 0;
        r = new Random(k);

        for (int i = 0; i < 1700; i++) {
            if(i == 0) {
                bert2.visit("\uD835\uDC6A\uD835\uDC8A\uD835\uDC82\uD835\uDC8D\uD835\uDC8D\uD835\uDC90", k);
                result ^= k;
            } else {
                int value = r.nextInt();
                bert2.visit("\uD835\uDC6A\uD835\uDC8A\uD835\uDC82\uD835\uDC8D\uD835\uDC8D\uD835\uDC90", value);
                result ^= value;
            }
            bert2.visit("C", bert2 = new AnnotationNode(Type.getDescriptor(DiaoXiaoSha)));
        }

        if (classNode.visibleAnnotations == null) {
            classNode.visibleAnnotations = new ArrayList<>();
        }
        classNode.visibleAnnotations.add(bert);

        hashmap.put(classNode.name, result);

        return classNodeToBytes(classNode);
    }

    @Override
    public List<String> getVisitorTags() {
        return Collections.emptyList();
    }

}
