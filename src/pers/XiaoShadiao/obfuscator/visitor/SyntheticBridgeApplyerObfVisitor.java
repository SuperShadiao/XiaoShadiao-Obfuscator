package pers.XiaoShadiao.obfuscator.visitor;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;
import pers.XiaoShadiao.obfuscator.utils.Utils;

import java.util.Collections;
import java.util.List;

public class SyntheticBridgeApplyerObfVisitor extends AbstractVisitor {

    public static final String[][] joking = new String[][] {
            new String[] {"啊哦", "这儿好像啥也没有呢", "去别的地方看看吧"},
            new String[] {"哎呀", "小沙雕刚把代码吃完就被你发现了"},
            new String[] {"你要找的东西被小沙雕吃掉了", "对了你玩原神吗", "小沙雕的UID是263087074"},
            new String[] {"小沙雕到此一游", "小沙雕的闲聊群xiaoshadiao_dot_club丨qqg"}
    };

    public SyntheticBridgeApplyerObfVisitor(byte[] bytes, String[] args) {
        super(bytes, args);
    }

    @Override
    public byte[] transfer(byte[] bytes) {
        ClassNode cn = byteToClassNode(bytes);

        for (FieldNode field : cn.fields) {
            field.access |= Opcodes.ACC_SYNTHETIC;
        }
        for (MethodNode method : cn.methods) {
            method.access |= (Opcodes.ACC_ANNOTATION & cn.access) != 0 ? 0 : (Opcodes.ACC_SYNTHETIC | (method.name.contains("init>") ? 0 : Opcodes.ACC_BRIDGE));
        }

        if (getBooleanArgOrDefault(0, true)) {
            String[] temp = joking[Utils.r.nextInt(joking.length)];
            for(String temp2 : temp) {
                cn.fields.add(new FieldNode(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL | Opcodes.ACC_STATIC, temp2, "Lpers/XiaoShadiao/NMSLException;", null, null));
            }
        }

        return classNodeToBytes(cn);
    }

    @Override
    public List<String> getVisitorTags() {
        return Collections.emptyList();
    }
}
