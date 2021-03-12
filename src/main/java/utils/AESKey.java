/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Paulo
 */

// ref: https://stackoverflow.com/questions/5355466/converting-secret-key-into-a-string-and-vice-versa
public class AESKey {
    public static SecretKey generateAESKey(int keysize) throws InvalidParameterException {
        try {
            if (Cipher.getMaxAllowedKeyLength("AES") < keysize) {
                throw new InvalidParameterException("Key size of " + keysize
                        + " not supported in this runtime");
            }

            final KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(keysize);
            return keyGen.generateKey();
        } catch (final NoSuchAlgorithmException e) {
            throw new IllegalStateException(
                    "AES should always be present in a Java SE runtime", e);
        }
    }

    public static SecretKey decodeBase64ToAESKey(final String encodedKey)
            throws IllegalArgumentException {
        try {
            final byte[] keyData = Base64.getDecoder().decode(encodedKey);
            final int keysize = keyData.length * Byte.SIZE;

            switch (keysize) {
            case 128:
            case 192:
            case 256:
                break;
            default:
                throw new IllegalArgumentException("Invalid key size for AES: " + keysize);
            }

            if (Cipher.getMaxAllowedKeyLength("AES") < keysize) {
                throw new IllegalArgumentException("Key size of " + keysize
                        + " not supported in this runtime");
            }

            final SecretKeySpec aesKey = new SecretKeySpec(keyData, "AES");
            return aesKey;
        } catch (final NoSuchAlgorithmException e) {
            throw new IllegalStateException(
                    "AES should always be present in a Java SE runtime", e);
        }
    }

    public static String encodeAESKeyToBase64(final SecretKey aesKey)
            throws IllegalArgumentException {
        if (!aesKey.getAlgorithm().equalsIgnoreCase("AES")) {
            throw new IllegalArgumentException("Not an AES key");
        }

        final byte[] keyData = aesKey.getEncoded();
        final String encodedKey = Base64.getEncoder().encodeToString(keyData);
        return encodedKey;
    }
}
