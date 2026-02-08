package pers.XiaoShadiao.obfuscator.visitor;

import org.objectweb.asm.tree.ClassNode;

import java.util.Collections;
import java.util.List;

public class FieldMethodResorterObfVisitor extends AbstractVisitor {

    public FieldMethodResorterObfVisitor(byte[] bytes, String[] args) {
        super(bytes, args);
    }

    @Override
    public byte[] transfer(byte[] bytes) {
        ClassNode node = byteToClassNode(bytes);

        Collections.shuffle(node.fields);
        Collections.shuffle(node.methods);

        return classNodeToBytes(node);
    }

    @Override
    public List<String> getVisitorTags() {
        return Collections.emptyList();
    }
}
