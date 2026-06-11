
package app.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;



public class Util{

    /** Generate a random and unique token.*/
    public static String generateToken(){
        UUID uuid = UUID.randomUUID();
        String result = uuid.toString().replaceAll("-","");
        result = result.substring(0,16);
        return result;
    }

    /* Returns true if s contains at least one digit.*/
    public static boolean includeDigit(String s){

        for(int i = 0; i < s.length(); i++){
            char c = s.charAt(i);

            if(c >= '0' && c <= '9')
                return true;
        }

        return false;
    }

    /** Returns a cryptographic hash, of the string given. The algorithm used is SHA-256. */
    public static String hash(String s) throws NoSuchAlgorithmException{
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(s.getBytes(StandardCharsets.UTF_8));
        String result = Util.bytesToHex(hash);
        return result;
    }


    
    /** Convert an array of bytes, to a hexadecimal string */
    private static String bytesToHex(byte[] hash) {

        StringBuilder hexString = new StringBuilder(2 * hash.length);

        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);

            if(hex.length() == 1) 
                hexString.append('0');
        
            hexString.append(hex);
        }

        return hexString.toString();
    }


    /** Return the integer given in the form of a string, with the size asked. A certain number of "0" will be added if necessary. */
    private static String formatNumber(int number, int sizeExpected){
        String result = "" + number;

        while(result.length() < sizeExpected)
            result = "0" + result;

        return result;
    }
    
}