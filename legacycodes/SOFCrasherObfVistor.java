import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.analysis.AnalyzerException;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SOFCrasherObfVistor {

    public static final Map<String, Integer> hashmap = new HashMap<>();

    public static final String _ = "Codes by CliffV2";

    public static byte[] transform(byte[] classData) throws AnalyzerException {
        ClassNode classNode = new ClassNode();
        ClassReader cr = new ClassReader(classData);
        cr.accept(classNode, 0);

        ObfVisitor tester = new ObfVisitor(cr) {};
        if(!tester.shouldObf() || (classNode.access & (Opcodes.ACC_ENUM | Opcodes.ACC_INTERFACE)) != 0) return classData;

        AnnotationNode bert = new AnnotationNode(Type.getDescriptor(DiaoXiaosha.class));

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
            bert2.visit("C", bert2 = new AnnotationNode(Type.getDescriptor(DiaoXiaosha.class)));
        }

        if (classNode.visibleAnnotations == null) {
            classNode.visibleAnnotations = new ArrayList<>();
        }
        classNode.visibleAnnotations.add(bert);

        ClassWriter writer = new ClassWriter1(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        classNode.accept(writer);
        // System.out.println("Hash result: " + result);
        hashmap.put(classNode.name, result);

        return writer.toByteArray();
    }

}
