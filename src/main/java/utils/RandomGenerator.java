/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.Random;

/**
 *
 * @author Paulo
 */
public class RandomGenerator {
    private static String _chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*?";
    
    public static String generatePassword(int length) {
        Random random = new Random(); 
        String result = ""; 
        
        for (int i = 0; i < length; i++) 
            result += _chars.charAt(random.nextInt(_chars.length())); 
        
        return result;
    }
}
