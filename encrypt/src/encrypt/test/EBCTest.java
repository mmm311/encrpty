package encrypt.test;


import org.bouncycastle.util.encoders.Hex;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;


public class EBCTest {
    public static SecretKey generateKey() throws GeneralSecurityException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES", "BCFIPS");
        keyGenerator.init(256);
        return keyGenerator.generateKey();
    }

    public static SecretKey defineKey(byte[] keyBytes)
    {
        if (keyBytes.length != 16 && keyBytes.length != 24 && keyBytes.length != 32)
        {
            throw new IllegalArgumentException("keyBytes wrong length for AES key");
        }
        return new SecretKeySpec(keyBytes, "AES");
    }

    public static byte[] ecbEncrypt(SecretKey key, byte[] data) throws GeneralSecurityException{
        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    public static byte[] ecbDecrypt(SecretKey key, byte[] cipherText) throws GeneralSecurityException{
        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(cipherText);
    }


    public static void main(String[] args) throws GeneralSecurityException{


        String keyText = "hell0 hello hell";
        SecretKey key = defineKey(keyText.getBytes());
        String text = "Hello worl dhe llowowrd Helloworld hellowow rdHellow orld hellow";
        byte[] textBytes = text.getBytes();
        System.out.println(textBytes.length);
        byte[] encrytText = ecbEncrypt(key, textBytes);
        System.out.println(encrytText.length);
        for (int i = 0; i < encrytText.length; i++){
            System.out.print(encrytText[i]);
        }
        System.out.println();

        byte[] testText = new byte[16];
        for (int i = 0 ; i < 16; i++){
            testText[i ] = encrytText[i];
        }
        byte[] decrypText = ecbDecrypt(key, testText);
        System.out.println(new String(decrypText));
    }


}
