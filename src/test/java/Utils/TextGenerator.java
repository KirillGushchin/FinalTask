package Utils;

import org.apache.commons.lang3.RandomStringUtils;

public class TextGenerator {
    public static String generateText(int length){
        return RandomStringUtils.randomAlphabetic(length);
    }
}
