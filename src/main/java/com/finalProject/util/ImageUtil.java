package com.finalProject.util;

import java.util.Base64;

public class ImageUtil {
    public static String convertToBase64(byte[] image) {
        return Base64.getEncoder().encodeToString(image);
    }
}

