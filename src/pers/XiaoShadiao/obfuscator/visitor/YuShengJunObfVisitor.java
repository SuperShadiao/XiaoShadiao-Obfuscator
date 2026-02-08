package pers.XiaoShadiao.obfuscator.visitor;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import pers.XiaoShadiao.obfuscator.Main;
import pers.XiaoShadiao.obfuscator.taskmanager.Task;
import pers.XiaoShadiao.obfuscator.taskmanager.TaskManager;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class YuShengJunObfVisitor extends AbstractVisitor {

    static {
        TaskManager.addTask(new Task(() -> {
            Main.writeToOutput(com.mayikt.yushengjun.Object.class);
            Main.writeToOutput(com.mayikt.yushengjun.YuShengJun.class);
        }, "YuShengJun"));
    }

    public static final NameAndDesc[] NAME_AND_DESCS = new NameAndDesc[] {
            new NameAndDesc("hashCode", "()I"),
            new NameAndDesc("<init>", "()V"),
            new NameAndDesc("equals", "(Ljava/lang/Object;)Z"),
            new NameAndDesc("toString", "()Ljava/lang/String;")
    };

    public YuShengJunObfVisitor(byte[] bytes, String[] args) {
        super(bytes, args);
    }

    @Override
    public byte[] transfer(byte[] bytes) {
        ClassNode cn = byteToClassNode(bytes);

        if((cn.access | Opcodes.ACC_ANNOTATION) == cn.access || (cn.access | Opcodes.ACC_INTERFACE) == cn.access || !cn.superName.equals("java/lang/Object")) return classNodeToBytes(cn);

        cn.superName = "com/mayikt/yushengjun/Object";

        cn.methods.stream().filter(method -> method.name.equals("<init>")).forEach(method -> {
            for (AbstractInsnNode insnNode : method.instructions) {
                if (insnNode instanceof MethodInsnNode) {
                    MethodInsnNode insnNode1 = (MethodInsnNode) insnNode;

                    if(insnNode1.getOpcode() == Opcodes.INVOKESPECIAL) {
                        for (NameAndDesc nameAndDesc : NAME_AND_DESCS) {
                            if (insnNode1.name.equals(nameAndDesc.name) && insnNode1.desc.equals(nameAndDesc.desc) && insnNode1.owner.equals("java/lang/Object")) {
                                insnNode1.owner = "com/mayikt/yushengjun/Object";
                                break;
                            }
                        }
                    }
                }
            }
        });

        return classNodeToBytes(cn);
    }

    @Override
    public List<String> getVisitorTags() {
        return Collections.emptyList();
    }

    public static class NameAndDesc {
        public String name;
        public String desc;
        public NameAndDesc(String name, String desc) {
            this.name = name;
            this.desc = desc;
        }
    }

}
