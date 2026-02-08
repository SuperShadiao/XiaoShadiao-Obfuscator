import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.analysis.AnalyzerException;

import java.util.Collections;

public class FieldMethodResorterVisitorObf {

    public static byte[] transform(byte[] classData) throws AnalyzerException {

        ClassNode node = new ClassNode();
        ClassReader cr = new ClassReader(classData);

        cr.accept(node, 0);

        Collections.shuffle(node.fields);
        Collections.shuffle(node.methods);

        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        node.accept(cw);
        return cw.toByteArray();

    }

}
