/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.regex.Pattern;

/**
 *
 * @author Paulo
 */
public class Validator {
    public static boolean validateEmailAddress(String email) {
        Pattern regexPattern = Pattern.compile("^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$");
        return regexPattern.matcher(email).matches();
    }
    
    public static boolean validatePhone(String phone) {
        Pattern regexPattern = Pattern.compile("^\\([0-9]{2}\\) 9[0-9]{4}-[0-9]{4}$");
        return regexPattern.matcher(phone).matches();
    }
}
