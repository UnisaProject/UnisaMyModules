/****************************************************************************************/
/*                                                                                      */
/* Author : Chris Cornelissen                                                           */
/* Date : April 2003                                                                    */
/* Description : Encrypt and decrypt sol passwords                                      */
/* Modifications : None                                                                 */
/*                                                                                      */
/****************************************************************************************/

package za.ac.unisa.lms.tools.cronjobs.util;

import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public abstract class Encryption {
    
    private static Cipher ecipher;
    private static Cipher dcipher;
    // 8-byte Salt
    private static byte[] salt = {
        (byte)0xA9, (byte)0x9B, (byte)0xC8, (byte)0x32,
        (byte)0x56, (byte)0x35, (byte)0xE3, (byte)0x03
    };
    // Iteration count
    private static int iterationCount = 19;
    
    
    /************* Constructor ************/
    public Encryption() throws Exception {
    }
    
    /*************** Initial setup ******************/
    public static void setup(String codeKey) throws Exception {
        // Create the key
        KeySpec keySpec = new PBEKeySpec(codeKey.toCharArray(), salt, iterationCount);
        SecretKey key = SecretKeyFactory.getInstance(
            "PBEWithMD5AndDES").generateSecret(keySpec);
        ecipher = Cipher.getInstance(key.getAlgorithm());
        dcipher = Cipher.getInstance(key.getAlgorithm());
        // Prepare the parameter to the ciphers
        AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);
        ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
        dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);        
    }

    /************** Encrypt string ****************/
    public static String encrypt(String str, String cde) throws Exception {        
        setup(cde);
        // Encode the string into bytes using utf-8
        byte[] utf8 = str.getBytes("UTF8");

        // Encrypt
        byte[] enc = ecipher.doFinal(utf8);

        // Encode bytes to base64 to get a string
        return new sun.misc.BASE64Encoder().encode(enc);
    }

    /*************** Decrypt string ******************/
    public static String decrypt(String str, String cde) throws Exception {
        setup(cde);
        // Decode base64 to get bytes
        byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);

        // Decrypt
        byte[] utf8 = dcipher.doFinal(dec);

        // Decode using utf-8
        return new String(utf8, "UTF8");
    } 
}                               //end class