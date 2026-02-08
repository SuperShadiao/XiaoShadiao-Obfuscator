package pers.XiaoShadiao.obfuscator.visitor;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodNode;
import pers.XiaoShadiao.obfuscator.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class XiaoShadiaoStrReplacerObfVisitor extends AbstractVisitor {

    public static final String[] xiaoshadiaoStrs;

    static {
        List<String> sl = new ArrayList<>();
        String[] sl2 = new String[] {"\uEF94", "\uF229", "\uF770", "ᨬ", "\uFD4B"};
        for (int i = 0; i < 3; i++) {
            StringBuilder ss = new StringBuilder("XiaoShadiao");
            for(int j = 0; j < 150; j++) {
                ss.append(Utils.getRandomMember(sl2));
            }
            sl.add(ss.toString());
        }
        xiaoshadiaoStrs = sl.toArray(new String[0]);
    }

    public XiaoShadiaoStrReplacerObfVisitor(byte[] bytes, String[] args) {
        super(bytes, args);
    }

    @Override
    public byte[] transfer(byte[] bytes) {
        ClassNode cn = byteToClassNode(bytes);
        if(cn.name.equals("XiaoShadiao")) {
            for (MethodNode method : cn.methods) {
                if(method.name.equals("原神启动") && method.desc.equals("(Ljava/lang/Object;)Ljava/lang/String;")) {
                    findAndReplaceString(method, new String[][] { {"dynamicStringKey", ThrowableStringObfVisitor.dynamicStringKey} });
                }
                if(isClinitNode(method)) {
                    findAndReplaceString(method, new String[][] {
                            {"代替换文本1", xiaoshadiaoStrs[0]},
                            {"代替换文本2", xiaoshadiaoStrs[1]},
                            {"代替换文本3", xiaoshadiaoStrs[2]},
                    });
                }
            }
        }
        return classNodeToBytes(cn);
    }

    public static void findAndReplaceString(MethodNode method, String[][] strs) {
        for (AbstractInsnNode insnNode : method.instructions) {
            if(insnNode instanceof LdcInsnNode) {
                LdcInsnNode ldcInsnNode = (LdcInsnNode) insnNode;
                if (ldcInsnNode.cst instanceof String) {
                    for (String[] str : strs) {
                        if(str[0].equals(ldcInsnNode.cst)) {
                            ldcInsnNode.cst = str[1];
                        }
                    }
                }
            }
        }
    }

    @Override
    public List<String> getVisitorTags() {
        return Collections.emptyList();
    }
}
