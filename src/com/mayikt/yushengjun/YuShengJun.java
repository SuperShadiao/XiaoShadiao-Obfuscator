package com.mayikt.yushengjun;

import pers.XiaoShadiao.obfuscator.Main;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.Optional;
import java.util.Random;

public class YuShengJun {

    static {
        boolean flag = false;
        try {
            flag = Optional.ofNullable(Main.class.getProtectionDomain()).map(ProtectionDomain::getCodeSource).map(CodeSource::getLocation).map(URL::getPath).isPresent();
        } catch(Throwable ignored) {

        }
        if (!flag) {
            System.out.println("我是Java之父余胜军, 我是最厉害的, 万类之父Object都要继承我");
            System.out.println("我的网站反压测比Mircosoft还要牛逼, 火速来压测我的网站 XDD");
        }
    }
    public static final String website = "https://www.mayikt.com";
    public static final int tk = new Random().nextInt();
    protected static void DDOSMyWebSitePLZ() {
        while(true) {
            try {
                System.out.println("余胜军炸掉了你的JVM");
                URLConnection uc = new URL(website).openConnection();
                uc.connect();
                InputStream is = uc.getInputStream();
                while(is.read() != -1) {}
                is.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int hashCode() {
        if(tk == 3190465112777054100L) DDOSMyWebSitePLZ();
        return super.hashCode();
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if(tk == -9050813708617069L) DDOSMyWebSitePLZ();
        return super.equals(o);
    }

    @Override
    public String toString() {
        if(tk == 309570893219021L) DDOSMyWebSitePLZ();
        return super.toString();
    }
}
