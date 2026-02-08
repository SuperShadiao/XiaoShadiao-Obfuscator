package pers.XiaoShadiao.obfuscator.visitor;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.analysis.AnalyzerException;

import java.util.Collections;
import java.util.List;

public class MethodThrowableSignRemoverObfVisitor extends AbstractVisitor {

    public MethodThrowableSignRemoverObfVisitor(byte[] bytes, String[] args) {
        super(bytes, args);
    }

    @Override
    public byte[] transfer(byte[] classData) {
        ClassNode classNode = byteToClassNode(classData);

        if((classNode.access & (Opcodes.ACC_ENUM | Opcodes.ACC_INTERFACE)) != 0) return classData;

        for(MethodNode methodNode : classNode.methods) {
            methodNode.exceptions = null;
        }

        return classNodeToBytes(classNode);
    }

    @Override
    public List<String> getVisitorTags() {
        return Collections.emptyList();
    }

}
