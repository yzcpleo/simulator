package com.shhxzq.fin.simulator.web.util;

import com.shhxzq.fin.simulator.biz.util.PropertiesUtil;
import com.shhxzq.fin.simulator.model.constants.AppConstants;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.fileupload.FileUploadException;

/**
 * @author kangyonggan
 * @since 2016/12/6
 */
public class Images {

    //大Logo
    public static String large(String source) throws FileUploadException {
        return thumbnails(source, "l", 200, 200);
    }

    //中Logo
    public static String middle(String source) throws FileUploadException {
        return thumbnails(source, "m", 128, 128);
    }

    //小Logo
    public static String small(String source) throws FileUploadException {
        return thumbnails(source, "s", 64, 64);
    }

    private static String thumbnails(String source, String suffix, int width, int height) throws FileUploadException {
        String desc = FileUpload.extractFilePath(source, suffix);

        try {
            Thumbnails.of(PropertiesUtil.getProperties(AppConstants.FILE_PATH_ROOT) + source)
                    .size(width, height)
                    .keepAspectRatio(false)
                    .toFile(PropertiesUtil.getProperties(AppConstants.FILE_PATH_ROOT) + desc);
        } catch (Exception e) {
            throw new FileUploadException("文件转换异常", e);
        }

        return desc;
    }
}
