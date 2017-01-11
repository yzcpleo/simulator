package com.shhxzq.fin.simulator.common.util;

import org.pegdown.PegDownProcessor;

/**
 * @author kangyonggan
 * @since 2016/10/18
 */
public class MarkdownUtil {

    private static PegDownProcessor processor = new PegDownProcessor(Integer.MAX_VALUE);

    /**
     * markdown语法转html语法
     *
     * @param markdown
     * @return
     */
    public static String markdownToHtml(String markdown) {
        return processor.markdownToHtml(markdown);
    }

}
