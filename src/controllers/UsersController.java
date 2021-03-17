/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import context.IAuthContext;
import entities.User;
import enums.Controller;
import models.LoginModel;
import repositories.IRepository;
import repositories.IUserRepository;
import utils.Result;
import utils.Validator;
import validators.IValidator;
import validators.UserValidator;

/**
 *
 * @author Paulo
 */
public class UsersController extends ControllerBase<User> {
    private IAuthContext _authContext;
    private IUserRepository _userRepository;
    private UserValidator _userValidator;
    
    public UsersController(
        IAuthContext authContext,
        IUserRepository userRepository,
        UserValidator userValidator
    ) {
        super(Controller.Users, authContext, userRepository, userValidator);
        
        _userRepository = userRepository;
        _userValidator = userValidator;
        _authContext = authContext;
    }
    
    public Result login(LoginModel model) {
        Result validation = model.validate();
        if (validation.HasError()) return validation;

        Result defaultError = Result.Error("Usuário ou senha inválidos");
        boolean existLogin = _userRepository.existByLogin(model.login);
        if (!existLogin) return defaultError;
        
        User user = _userRepository.getByLogin(model.login);
        boolean validPassword = user.validatePassword(model.password);
        if (!validPassword) return defaultError;
       
        _authContext.setCurrentUser(user);
        
        return Result.Ok();
    }
}
