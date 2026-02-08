import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.tree.analysis.Analyzer;
import org.objectweb.asm.tree.analysis.AnalyzerException;
import org.objectweb.asm.tree.analysis.BasicInterpreter;
import org.objectweb.asm.tree.analysis.BasicValue;

import java.util.ArrayList;
import java.util.List;

public class LabelResorterObfVisitor {

    public static byte[] transform(byte[] classData) throws AnalyzerException {
        ClassNode classNode = new ClassNode();
        ClassReader cr = new ClassReader(classData);
        cr.accept(classNode, 0);

        ObfVisitor tester = new ObfVisitor(cr) {};
        if(!tester.shouldObf() || (classNode.access & (Opcodes.ACC_ENUM | Opcodes.ACC_INTERFACE)) != 0) return classData;

        for(MethodNode methodNode : classNode.methods) {
            if((methodNode.access & (Opcodes.ACC_ABSTRACT | Opcodes.ACC_NATIVE)) == 0 && !methodNode.name.contains("init>")) transformMethod(classNode, methodNode);
        }

        ClassWriter writer = new ClassWriter1((ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS) & -01);
        classNode.accept(writer);

        return writer.toByteArray();
    }

    private static void transformMethod(ClassNode classNode, MethodNode methodNode) throws AnalyzerException {

        Analyzer<BasicValue> analyzer = new Analyzer<>(new BasicInterpreter());
        try {
            analyzer.analyze(methodNode.name, methodNode);
        } catch (AnalyzerException e) {
            throw new RuntimeException("Exception in " + classNode.name + " " + methodNode.name + " " + methodNode.desc, e);
        }

        InsnList insnList = new InsnList();
        LabelNode startLabel = new LabelNode();
        Package currentPackage = new Package();
        List<Package> packageList = new ArrayList<>();

        currentPackage.nodes.add(startLabel);
        int enterTryCatch = 0;

        for (AbstractInsnNode insnNode : methodNode.instructions) {
            if(enterTryCatch <= 0 && insnNode instanceof LabelNode) {
                currentPackage.nodes.add(new JumpInsnNode(Opcodes.GOTO, (LabelNode) insnNode));
                packageList.add(currentPackage);
                currentPackage = new Package();
                currentPackage.nodes.add(insnNode);
            } else {
                if(enterTryCatch > 0 && insnNode instanceof LabelNode) System.out.println("In try catch " + enterTryCatch);
                currentPackage.nodes.add(insnNode);
            }

            if(insnNode instanceof LabelNode) {
                if(methodNode.tryCatchBlocks.stream().anyMatch(tc -> tc.start == insnNode)) {
                    enterTryCatch++;
                } else if(methodNode.tryCatchBlocks.stream().anyMatch(tc -> tc.end == insnNode)) {
                    enterTryCatch--;
                }
            }
        }
        packageList.add(currentPackage);

//        packageList.stream().unordered().forEach(p -> {
//            p.nodes.forEach(insnList::add);
//        });

        insnList.add(new JumpInsnNode(Opcodes.GOTO, startLabel));

        while(!packageList.isEmpty()) {
            Package p = packageList.remove(Main.r.nextInt(packageList.size()));
            p.nodes.forEach(insnList::add);
        }
        methodNode.localVariables.clear();

        methodNode.instructions = insnList;
    }
}

class Package {
    List<AbstractInsnNode> nodes = new ArrayList<>();
}
