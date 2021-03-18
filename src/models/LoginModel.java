/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import utils.Result;

/**
 *
 * @author Paulo
 */
public class LoginModel implements IModel {
    public String login;
    public String password;

    @Override
    public Result validate() {
        if (login == null || login.equals(""))
            return Result.error("O usuário deve ser informado.");
        if (password == null || password.equals(""))
            return Result.error("A senha deve ser informada.");
        return Result.ok();
    }
}
