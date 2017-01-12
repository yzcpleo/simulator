package com.shhxzq.fin.simulator.common.util;

import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kangyonggan
 * @since 16/7/20
 */
public class Shell {

    /**
     * 运行shell脚本
     *
     * @param cmd 需要运行的shell脚本
     */
    public static void exec(String cmd) {
        try {
            Runtime rt = Runtime.getRuntime();
            rt.exec(cmd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 运行shell
     *
     * @param cmd 需要执行的shell命令
     * @return
     * @throws Exception
     */
    public static List run(String cmd) throws Exception {
        List<String> strList = new ArrayList();

        Process process;
        process = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", cmd}, null, null);
        InputStreamReader ir = new InputStreamReader(process
                .getInputStream());
        LineNumberReader input = new LineNumberReader(ir);
        String line;
        process.waitFor();
        while ((line = input.readLine()) != null) {
            strList.add(line);
        }

        return strList;
    }

}