package com.lyc.ai.util;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

public class CodeGenerator {
    private static final String CHARACTERS = "ABCDEFGHJKMNPRSTUVWXYZ23456789";
    private static final int CODE_LENGTH = 8;
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * 生成指定数量的唯一兑换码
     * @param count 兑换码数量
     * @return 唯一兑换码集合
     */
    public static Set<String> generateUniqueCodes(int count) {
        Set<String> codes = new HashSet<>();
        while (codes.size() < count) {
            codes.add(generateCode());
        }
        return codes;
    }

    /**
     * 生成单个兑换码
     * @return 兑换码
     */
    private static String generateCode() {
        StringBuilder sb = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }
}