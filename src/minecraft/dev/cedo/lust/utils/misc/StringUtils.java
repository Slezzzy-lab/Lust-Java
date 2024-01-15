package dev.cedo.lust.utils.misc;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class StringUtils {

    public static String random(int size) {
        byte[] array = new byte[size];
        new Random().nextBytes(array);

        return new String(array, StandardCharsets.UTF_8);
    }
}
