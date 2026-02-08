package pers.XiaoShadiao.obfuscator.visitor;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import pers.XiaoShadiao.obfuscator.utils.Utils;

import java.util.Collections;
import java.util.List;

public class LocalVarObfVisitor extends AbstractVisitor {
    public LocalVarObfVisitor(byte[] bytes, String[] args) {
        super(bytes, args);
    }

    @Override
    public byte[] transfer(byte[] bytes) {
        ClassNode cn = readerToClassNode(new ClassReader(bytes));
        boolean useMap = getBooleanArgOrDefault(0, false);
        cn.methods.forEach(m -> {
            if(m.localVariables != null) if(useMap) {
                m.localVariables.forEach(v -> {
                    v.name = Utils.getRandomNameFromMap();
                });
            } else m.localVariables.clear();
        });
        return classNodeToBytes(cn);
    }

    @Override
    public List<String> getVisitorTags() {
        return Collections.emptyList();
    }
}
