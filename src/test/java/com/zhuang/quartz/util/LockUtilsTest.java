package com.zhuang.quartz.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class LockUtilsTest {

    @Test
    public void lockFile() throws InterruptedException {

        System.out.println(LockUtils.lockFile());

        Thread.sleep(60*1000);

    }
}