/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.Base64;    
import javax.crypto.Cipher;   
import javax.crypto.SecretKey;

/**
 *
 * @author Paulo
 */

// ref: https://stackoverflow.com/questions/10303767/encrypt-and-decrypt-in-java
public class AESHash {
    public static String encrypt(String plainText, SecretKey secretKey) {
        try {
            byte[] plainTextByte = plainText.getBytes();
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedByte = cipher.doFinal(plainTextByte);
            Base64.Encoder encoder = Base64.getEncoder();
            String encryptedText = encoder.encodeToString(encryptedByte);
            return encryptedText;
        } catch (Exception e) {
            throw new JPedidosException("Erro ao criprografar mensagem", e);
        }
    }

    public static String decrypt(String encryptedText, SecretKey secretKey)  {
        try {        
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] encryptedTextByte = decoder.decode(encryptedText);
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedByte = cipher.doFinal(encryptedTextByte);
            String decryptedText = new String(decryptedByte);
            return decryptedText;
        } catch (Exception e) {
            throw new JPedidosException("Erro ao descriptografar mensagem", e);
        }
    }
}
