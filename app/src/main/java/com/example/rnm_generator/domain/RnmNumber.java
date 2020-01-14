package com.example.rnm_generator.domain;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class RnmNumber {
    private final static String NINE_ZEROS = "000000000";
    private final static String COUNT_KKM = "1";

    @NotNull
    public static String calculate(@NotNull String inn,
                                   @NotNull String factoryNumber) {
        if (inn.length() < 12) {
            throw new IllegalArgumentException("ИНН должен быть из 12 цифр. Например: 009715225506");
        }
        if (factoryNumber.length() < 14) {
            throw new IllegalArgumentException("Заводской номер кассы должен быть из 14 цифр. Например: 00308300087104");
        }

        StringBuilder resultBuilder = new StringBuilder(512);

        String innerBuilder =
                NINE_ZEROS +   // 9 zeros
                COUNT_KKM +    // порядковый номер кассы
                inn +          // inn 12 number's
                "000000" +     // 6
                factoryNumber; // factory number 14

        int sum = calculateCRC16CCITT(innerBuilder);

        String resultRnm = NINE_ZEROS + COUNT_KKM + "0" + String.format(Locale.getDefault(),
                "%05d", sum);

        return resultBuilder.append(formatRnm(resultRnm)).toString();
    }

    @NotNull
    private static String formatRnm(@NotNull String incomingRnm) {
        return String.format("%s %s %s %s", incomingRnm.substring(0, 4), incomingRnm.substring(4, 8),
                incomingRnm.substring(8, 12), incomingRnm.substring(12, 16));
    }

    @Contract(pure = true)
    private static int calculateCRC16CCITT(@NotNull String inputValue) {
        int crc = 0xFFFF;          // initial value
        int polynomial = 0x1021;   // 0001 0000 0010 0001  (0, 5, 12)

        byte[] bytes = inputValue.getBytes();

        for (byte singleByte : bytes) {
            for (int i = 0; i < 8; i++) {
                boolean bit = ((singleByte >> (7 - i) & 1) == 1);
                boolean c15 = ((crc >> 15 & 1) == 1);
                crc <<= 1;
                if (c15 ^ bit) crc ^= polynomial;
            }
        }
        crc &= 0xffff;
        return crc;
    }
}
