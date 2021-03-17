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
public class LoginModel implements IModel<LoginModel> {
    public String login;
    public String password;

    @Override
    public Result validate(LoginModel model) {
        if (login == null || login.equals(""))
            return Result.Error("O login deve ser informado.");
        if (password == null || password.equals(""))
            return Result.Error("A senha deve ser informada.");
        return Result.Ok();
    }
}