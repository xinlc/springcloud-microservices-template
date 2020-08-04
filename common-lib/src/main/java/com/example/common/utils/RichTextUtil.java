package com.example.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Leo
 * @date 2019.12.13
 */
public class RichTextUtil {
    private static final String REGEX_IMG = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
    private static final String REGEX_IMG_SRC = "src\\s*=\\s*\"?(.*?)(\"|>|\\s+)";
    private static final Pattern P_IMAGE = Pattern.compile(REGEX_IMG, Pattern.CASE_INSENSITIVE);
    private static final Pattern P_IMAGE_SRC = Pattern.compile(REGEX_IMG_SRC);

    /**
     * 获取文本中的img标签的src属性值
     *
     * @param htmlStr
     * @return
     */
    public static List<String> getImgSrc(String htmlStr) {
        String img = "";
        List<String> pics = new ArrayList<>();
        Matcher mImage = P_IMAGE.matcher(htmlStr);
        // 匹配 img 标签
        while (mImage.find()) {
            img = mImage.group();
            Matcher m = P_IMAGE_SRC.matcher(img);
            // 匹配 src
            while (m.find()) {
                pics.add(m.group(1));
            }
        }
        return pics;
    }

    /**
     * 处理图片，删除图片地址前缀
     * http://xxx/img/1.png -> 1.png
     * @param htmlStr
     * @return
     */
    public static String removeImagePathPrefix(String htmlStr) {
        String content = htmlStr;
        if (StringUtils.isNoneBlank(content)) {
            // 获取富文本中的图片地址
            List<String> pics = getImgSrc(content);
            for (String pic: pics) {
                String imgName = pic.substring(pic.lastIndexOf("/") + 1);
                // 删除图片地址前缀：http://xxx/img/1.png -> 1.png
                content = content.replace(pic, imgName);
            }
        }
        return content;
    }

    /**
     * 处理图片，删除图片地址前缀
     * 1.png -> http://xxx/img/1.png
     * @param htmlStr
     * @param pathPrefix
     * @return
     */
    public static String addImagePathPrefix(String htmlStr, String pathPrefix) {
        String content = htmlStr;
        if (StringUtils.isNoneBlank(content)) {
            // 获取富文本中的图片地址
            List<String> pics = getImgSrc(content);
            for (String pic: pics) {
                String path = pathPrefix + pic;
                // 添加图片地址前缀: 1.png -> http://xxx/img/1.png
                content = content.replace(pic, path);
            }
        }
        return content;
    }
}
