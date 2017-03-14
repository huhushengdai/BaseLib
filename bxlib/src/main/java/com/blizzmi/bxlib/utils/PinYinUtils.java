package com.blizzmi.bxlib.utils;

import android.text.TextUtils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * Date： 2016/9/26
 * Description:
 * 拼音转换工具
 *
 * @author WangLizhi
 * @version 1.0
 */
public class PinYinUtils {
    /**
     * 获取首字母
     *
     * @param text 文字
     * @return 如果不属于汉字英文等，返回#
     */
    public static String getFirstLetter(String text) {
        if (TextUtils.isEmpty(text)){
            return "";
        }
        char letter = text.charAt(0);
        if (Character.isLetter(letter)){
            return String.valueOf(Character.toUpperCase(letter));
        }else {
            return "#";
        }
    }

    /**
     * 将汉字转换为全拼
     */
    public static String getPingYin(String src) {

        char[] t1 = null;
        t1 = src.toCharArray();
        String[] t2 = new String[t1.length];
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();

        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);
        String t4 = "";
        int t0 = t1.length;
        try {
            for (int i = 0; i < t0; i++) {
                // 判断是否为汉字字符
                if (java.lang.Character.toString(t1[i]).matches(
                        "[\\u4E00-\\u9FA5]+")) {
                    t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
                    t4 += t2[0];
                } else
                    t4 += java.lang.Character.toString(t1[i]);
            }
            // System.out.println(t4);
            return t4;
        } catch (BadHanyuPinyinOutputFormatCombination e1) {
            e1.printStackTrace();
        }
        return t4;
    }
}
