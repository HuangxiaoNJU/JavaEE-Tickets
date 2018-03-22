package edu.nju.tickets.util;

import java.util.Random;

public class RandomUtil {

    // 小写字母集合
    public static final String ALPHABET_LOWER = "qwertyuiopasdfghjklzxcvbnm";
    // 大写字母集合
    public static final String ALPHABET_UPPER = "QWERTYUIOPASDFGHJKLZXCVBNM";
    // 数字集合
    public static final String NUMBERS = "1234567890";

    /**
     * 随机生成字符串
     *
     * @param range     字符可选范围
     * @param length    生成随机字符串长度
     * @return          随机字符串
     */
    public static String getRandomString(String range, int length) {
        Random random = new java.util.Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(range.length());
            sb.append(range.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 随机生成字符串
     * 字符范围为大小写字母和数字
     *
     * @param length    生成随机字符串长度
     * @return          随机字符串
     */
    public static String getRandomString(int length) {
        String range = ALPHABET_LOWER + ALPHABET_UPPER + NUMBERS;
        return getRandomString(range, length);
    }

}
