
package ipanelonline.mobile.survey.utils;


import java.util.LinkedHashMap;


/**
 * 作者：Rance on 2016/11/29 10:47
 * 邮箱：rance935@163.com
 * 表情加载类,可自己添加多种表情，分别建立不同的map存放和不同的标志符即可
 */
public class EmotionUtils {

    /**
     * key-表情文字;
     * value-表情图片资源
     */
    public static LinkedHashMap<String, Integer> EMPTY_GIF_MAP;
    public static LinkedHashMap<String, Integer> EMOTION_STATIC_MAP;


    static {
        EMPTY_GIF_MAP = new LinkedHashMap<>();
        EMOTION_STATIC_MAP = new LinkedHashMap<>();
    }
}
