package pers.XiaoShadiao.obfuscator.commandline;

import pers.XiaoShadiao.obfuscator.interfaces.ICommandExecutor;
import pers.XiaoShadiao.obfuscator.commandline.commands.*;
import pers.XiaoShadiao.obfuscator.visitor.visitorfactory.VisitorFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CommandHandler {

    public static final List<ICommandExecutor> commands;
    static {
        commands = new ArrayList<>();
        commands.add(new CommandHelp());
        commands.add(new CommandInJar());
        commands.add(new CommandOutJar());
        commands.add(new CommandLoadRemapString());
        commands.add(new CommandNoVerify());
        commands.add(new CommandEnableOldASM());
        commands.add(new CommandExcludeClass());
        commands.add(new CommandIncludeClass());
        commands.add(new CommandUseASM());
        commands.add(new CommandSetFollowCmdLineOrder());
        commands.add(new CommandSecurityManagerAntiDebugger());
        commands.add(new CommandClassToFolder());
        commands.add(new CommandUseLCMPNumber());
        commands.addAll(Arrays.asList(VisitorFactory.values()));
    }
    public static void executeCommand(String cmd) {
        String[] cmdArgs = cmd.split(" ");
        executeCommand(cmdArgs);
    }

    public static void executeCommand(String[] cmd) {
        try {
            List<String> args = new ArrayList<>();
            boolean isFirst = true;
            ICommandExecutor current = null;
            for (String arg : cmd) {
                if (arg.startsWith("#")) continue;
                if (isFirst && !arg.startsWith("-")) {
                    throw new IllegalArgumentException("第一个参数必须是以-开头的命令");
                }
                if (arg.startsWith("-")) {
                    String cmdName = arg.substring(1);
                    Optional<ICommandExecutor> executor = commands.stream().filter(command -> command.getCmdName().equals(cmdName)).findFirst();
                    if(!executor.isPresent()) {
                        throw new IllegalArgumentException("小沙雕不认识这个命令: " + arg);
                    } else {
                        if(current != null) {
                            System.out.println("执行命令: " + current.getCmdName() + " " + args);
                            current.execute(args.toArray(new String[0]));
                        }
                        current = executor.get();
                        args.clear();
                    }
                } else {
                    args.add(arg);
                }

                if (isFirst) {
                    isFirst = false;
                }
            }
            if(current != null) {
                System.out.println("执行命令: " + current.getCmdName() + " " + args);
                current.execute(args.toArray(new String[0]));
            }
        } catch (Exception e) {
            if(e instanceof IllegalArgumentException) throw (IllegalArgumentException) e;
            throw new IllegalArgumentException("执行命令时发生错误", e);
        }
    }

    public static void main(String[] args) {
        executeCommand("-inJar test.jar -outJar test-obf.jar -inClass a b c -exClass a c e");
    }

}
