/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.regex.Pattern;
import utils.Result;

/**
 *
 * @author Paulo
 */
public class ChangePasswordModel implements IModel {
    public String newPassword;
    public String confirmPassword;
    
    @Override
    public Result validate() {
        if (newPassword == null || newPassword.equals(""))
            return Result.error("A nova senha deve ser informada.");
        if (!Pattern.compile(".*[a-z].*").matcher(newPassword).matches())
            return Result.error("A senha deve ter caracteres em minúsculo.");
        if (!Pattern.compile(".*[A-Z].*").matcher(newPassword).matches())
            return Result.error("A senha deve ter caracteres em maiúsculo.");
        if (!Pattern.compile(".*[0-9].*").matcher(newPassword).matches())
            return Result.error("A senha deve ter caracteres numéricos.");
        if (!Pattern.compile(".*[!@#\\$%\\^&].*").matcher(newPassword).matches())
            return Result.error("A senha deve ter caracteres especiais.");
        if (newPassword.length() < 8)
            return Result.error("A senha deve ter pelo menos 8 caracteres.");
        if (!newPassword.equals(confirmPassword))
            return Result.error("As senhas deve coincindir.");
        return Result.ok();
    }
    
}
