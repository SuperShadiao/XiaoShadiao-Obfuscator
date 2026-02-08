package pers.XiaoShadiao.obfuscator.commandline.commands;

import pers.XiaoShadiao.NMSLException;
import pers.XiaoShadiao.obfuscator.Main;
import pers.XiaoShadiao.obfuscator.config.Config;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;

public class CommandLoadRemapString extends CommandFile {
    @Override
    public String getCmdName() {
        return "applymap";
    }

    @Override
    public String getCmdDesc() {
        return "加载自定义字段或方法混淆名文件";
    }

    @Override
    public boolean execute(String[] args) throws Exception {

        try {

            File mapFolder = new File(Main.directionary, "maps");
            if(!mapFolder.exists()) mapFolder.mkdirs();

            File target = new File(args[0]);
            if(!target.exists()) target = new File(mapFolder, args[0]);
            if(!target.exists()) {
                File f = new File(mapFolder, "测试");
                f.createNewFile();
                throw new RuntimeException("找不到文件" + args[0] + ": " + mapFolder + ": " + target);
            }
            Config.remapStrings = readAndParseToMap(String.join("\n", Files.readAllLines(target.toPath()).toArray(new String[0])));
        } catch (UnsupportedEncodingException e) {
            // TODO 自动生成的 catch 块
            throw new AssertionError(e);
        }

        return true;
    }

    public static String[] readAndParseToMap(String s) {
        char[] chars = s.toCharArray();

        Set<String> names = new HashSet<>();
        StringBuilder buffer = new StringBuilder();

        for(char c : chars) {
            boolean flag = buffer.length() == 0 ? Character.isJavaIdentifierStart(c) : Character.isJavaIdentifierPart(c);
            if(flag && c != '\n' && c != '\r') {
                buffer.append(c);
            } else if(buffer.length() > 0) {
                names.add(buffer.toString());
                buffer.setLength(0);
            }
        }
        return names.toArray(new String[0]);
    }
}
