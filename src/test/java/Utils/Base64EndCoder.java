package Utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Base64EndCoder {
    public static String encodeFileToBase64Binary(File file){
        String result = null;
        try {
            byte[] endCoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
            result = new String(endCoded, StandardCharsets.US_ASCII);
        } catch (IOException e) {
            System.err.println("Can't endcode file to base64 due to following error" + e.getMessage());
        }
        return result;
    }
}
