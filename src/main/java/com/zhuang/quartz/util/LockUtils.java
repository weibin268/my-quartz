package com.zhuang.quartz.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.nio.file.Path;

public class LockUtils {

    public static boolean lockFile() {
        String lockFilePath = PathUtils.getCurrentDirectory() + "/lockfile";
        RandomAccessFile randomAccessFile;
        try {
            File file = new File(lockFilePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            randomAccessFile = new RandomAccessFile(file, "rw");
            FileLock fileLock = randomAccessFile.getChannel().tryLock();
            if (fileLock != null && fileLock.isValid()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException("操作锁文件出错！", e);
        } finally {
		/*	try {
				fileOutputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
        }
    }
}
