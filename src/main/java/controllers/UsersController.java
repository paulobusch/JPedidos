/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import context.IAuthContext;
import entities.User;
import enums.Controller;
import jpedidos.Settings;
import models.ChangePasswordModel;
import models.LoginModel;
import repositories.IRepository;
import repositories.IUserRepository;
import utils.AESHash;
import utils.RandomGenerator;
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
        if (validation.hasError()) return validation;

        Result defaultError = Result.error("Usuário ou senha inválidos");
        boolean existLogin = _userRepository.existByLogin(model.login);
        if (!existLogin) return defaultError;
        
        User user = _userRepository.getByLogin(model.login);
        boolean validPassword = user.validatePassword(model.password);
        if (!validPassword) return defaultError;
       
        _authContext.setCurrentUser(user);
        
        return Result.ok();
    }
    
    public Result logout() {
        _authContext.setCurrentUser(null);
        
        return Result.ok();
    }
    
    public String generatePassword() {
        return RandomGenerator.generatePassword(30);
    }
    
    public Result resetPassword(int userId, String password) {
        if (userId == 0) return Result.error("O usuário deve ser informado para geração da senha");
        _userRepository.changePassword(userId, encryptPassword(password), true);
        return Result.ok();
    }
    
    public Result changePassword(ChangePasswordModel model) {
        Result validation = model.validate();
        if (validation.hasError()) return validation;
        _userRepository.changePassword(getCurrentUser().getId(), encryptPassword(model.confirmPassword), false);
        return Result.ok();
    }
    
    private String encryptPassword(String password) {
        return AESHash.encrypt(password, Settings.SecretKey);
    }
}
