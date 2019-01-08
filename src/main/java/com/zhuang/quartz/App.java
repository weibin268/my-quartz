package com.zhuang.quartz;

import com.zhuang.quartz.util.LockUtils;

public class App {

    public static void main(String[] args) {

        if (!LockUtils.lockFile()) {
            System.out.println("has another process running!");
            System.exit(1);
        }

        QuartzStarter quartzStarter;
        quartzStarter = new QuartzStarter();
        quartzStarter.start();

    }
}