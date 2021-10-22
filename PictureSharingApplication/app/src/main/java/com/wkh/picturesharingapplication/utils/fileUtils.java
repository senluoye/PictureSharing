package com.wkh.picturesharingapplication.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class fileUtils {
    /**
     * 将Byte数组转换成文件
     * @param bytes byte数组
     * @param filePath 文件路径  如 D://test/ 最后“/”结尾
     * @param fileName  文件名
     */
    public static void fileToBytes(byte[] bytes, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {

            file = new File(filePath + fileName);
            if (!file.getParentFile().exists()){
                //文件夹不存在 生成
                file.getParentFile().mkdirs();
            }
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
