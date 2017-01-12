package com.shhxzq.fin.simulator.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author kangyonggan
 * @since 2016/05/26
 */
public class ZipUtil {

    public static void zip(String fileName, String zipFileName) {
        File file = new File(fileName);
        File zipFile = new File(zipFileName);
        InputStream in = null;
        ZipOutputStream out = null;
        try {
            in = new FileInputStream(file);
            out = new ZipOutputStream(new FileOutputStream(zipFile));
            out.putNextEntry(new ZipEntry(file.getName()));
            out.setComment("银联对账文件");
            int temp;
            while ((temp = in.read()) != -1) {
                out.write(temp);
            }
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

