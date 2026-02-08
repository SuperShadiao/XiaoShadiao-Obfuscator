package pers.XiaoShadiao.obfuscator.commandline.commands;

import org.objectweb.asm.Opcodes;
import pers.XiaoShadiao.obfuscator.config.Config;
import pers.XiaoShadiao.obfuscator.interfaces.ICommandExecutor;

public class CommandUseASM implements ICommandExecutor {
    @Override
    public String getCmdName() {
        return "asmVer";
    }

    @Override
    public String getCmdArgs() {
        return "<version>";
    }

    @Override
    public String getCmdDesc() {
        return "欲使用的ASM API版本, 取值范围为4~10, 默认值为5";
    }

    @Override
    public boolean execute(String[] args) throws Exception {
        int temp = Integer.parseInt(args[0]);
        int targetASM;
        switch(temp) {
            case 4:
                targetASM = Opcodes.ASM4;
                break;
            case 5:
                targetASM = Opcodes.ASM5;
                break;
            case 6:
                targetASM = Opcodes.ASM6;
                break;
            case 7:
                targetASM = Opcodes.ASM7;
                break;
            case 8:
                targetASM = Opcodes.ASM8;
                break;
            case 9:
                targetASM = Opcodes.ASM9;
                break;
            case 10:
                targetASM = Opcodes.ASM10_EXPERIMENTAL;
                break;
            default:
                throw new IllegalArgumentException("-asmVer后面的参数应该为x∈{x|4≤x≤10, x∈N} (x应为4-10的整数), 你输入了: " + args[0]);
        }
        Config.ASM_API = targetASM;
        return true;
    }
}
