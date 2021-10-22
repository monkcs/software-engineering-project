package com.example.covid_tracker;

import java.nio.charset.StandardCharsets;

public class Encryption {
    public static String encryptData(final String s){

        //check for åäö
        String mod = s.replaceAll("å", "%");
        mod = mod.replaceAll("ä", "&");
        mod = mod.replaceAll("ö", "#");
        mod = mod.replaceAll("Å", "!");
        mod = mod.replaceAll("Ä", "£");
        mod = mod.replaceAll("Ö", "¤");
        mod = mod.replaceAll("9", "NINE");

        System.out.println("modded string: " + mod);

        String reversed = reverseString(mod);

        byte[] encrypted = reversed.getBytes(StandardCharsets.UTF_8);

        for(int i = 0; i < reversed.length(); i++){
            encrypted[i] = (byte) (encrypted[i] + 1);
        }

        return new String(encrypted);
    }

    public static String decryptData(final String s){

        byte[] decryptedChars = s.getBytes(StandardCharsets.UTF_8);

        for(int i = 0; i < s.length(); i++){
            decryptedChars[i] = (byte) (decryptedChars[i] - 1);
        }

        String decryptedWithSpec = reverseString(new String(decryptedChars));

        //check for åäö
        String decrypted = decryptedWithSpec.replaceAll("%", "å");
        decrypted = decrypted.replaceAll("&", "å");
        decrypted = decrypted.replaceAll("#", "ö");
        decrypted = decrypted.replaceAll("!", "Å");
        decrypted = decrypted.replaceAll("£", "Ä");
        decrypted = decrypted.replaceAll("¤", "Ö");
        decrypted = decrypted.replaceAll("NINE", "9");

        return decrypted;
    }

    private static String reverseString(final String s){
        // getBytes() method to convert string
        // into bytes[].
        byte[] strAsByteArray = s.getBytes();

        byte[] result = new byte[strAsByteArray.length];

        // Store result in reverse order into the
        // result byte[]
        for (int i = 0; i < strAsByteArray.length; i++)
            result[i] = strAsByteArray[strAsByteArray.length - i - 1];

        //System.out.println(new String(result));

        return new String(result);
    }

}
