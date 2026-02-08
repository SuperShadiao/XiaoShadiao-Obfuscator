package pers.XiaoShadiao.obfuscator.utils.customclassloader;

import pers.XiaoShadiao.obfuscator.Main;

import java.net.URLClassLoader;

public class VerifyerClassLoader extends URLClassLoader {
    public VerifyerClassLoader() {
        super(Main.loader.getURLs(), null);
    }

    public Class<?> define(byte[] b) {
        return super.defineClass(b, 0, b.length);
    }
}
