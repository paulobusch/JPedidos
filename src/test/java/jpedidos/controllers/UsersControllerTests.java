/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpedidos.controllers;

import context.IAuthContext;
import controllers.UsersController;
import entities.User;
import models.ChangePasswordModel;
import models.LoginModel;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import repositories.IUserRepository;
import utils.Result;
import validators.UserValidator;

/**
 *
 * @author Paulo
 */
public class UsersControllerTests {
    private IAuthContext _authContextMock;
    private IUserRepository _userRepositoryMock;
    private UserValidator _userValidatorMock;
    
    public UsersControllerTests() {
        _authContextMock = mock(IAuthContext.class);
        _userRepositoryMock = mock(IUserRepository.class);
        _userValidatorMock = mock(UserValidator.class);
    }
    
    @ParameterizedTest
    @CsvSource({
        "false, true,  true,  Model inválido",
        "true,  false, true,  Usuário ou senha inválidos",
        "true,  true,  false, Usuário ou senha inválidos"
    }) 
    void loginFail(boolean validModel, boolean validPassword, boolean existLogin, String expectedError) {
        LoginModel modelMock = mock(LoginModel.class);
        when(modelMock.validate())
            .thenReturn(validModel ? Result.ok() : Result.error(expectedError));
        modelMock.login = "login";
        modelMock.password = "123";
        User userMock = mock(User.class);
        when(userMock.validatePassword(modelMock.password))
            .thenReturn(validPassword);
        when(_userRepositoryMock.existByLogin(modelMock.login))
            .thenReturn(existLogin);
        when(_userRepositoryMock.getByLogin(modelMock.login))
            .thenReturn(userMock);
        UsersController usersController = getUsersController();
        
        Result result = usersController.login(modelMock);
        
        assertTrue(result.hasError());
        assertEquals(expectedError, result.getErrorMessage());
    }
    
    @Test
    void loginSuccess() {
        LoginModel modelMock = mock(LoginModel.class);
        when(modelMock.validate())
            .thenReturn(Result.ok());
        modelMock.login = "login";
        modelMock.password = "123";
        User userMock = mock(User.class);
        when(userMock.validatePassword(modelMock.password))
            .thenReturn(true);
        when(_userRepositoryMock.existByLogin(modelMock.login))
            .thenReturn(true);
        when(_userRepositoryMock.getByLogin(modelMock.login))
            .thenReturn(userMock);
        UsersController usersController = getUsersController();
        
        Result result = usersController.login(modelMock);
        
        assertFalse(result.hasError());
        verify(_authContextMock, atLeastOnce()).setCurrentUser(userMock);
    }
    
    @Test
    void logout() {
        UsersController usersController = getUsersController();
        
        Result result = usersController.logout();
        
        assertFalse(result.hasError());
        verify(_authContextMock, atLeastOnce()).setCurrentUser(null);
    }
        
    @Test
    void generatePassword() {
        UsersController usersController = getUsersController();
        
        String password = usersController.generatePassword();
        
        assertNotNull(password);
        assertEquals(30, password.length());
    }
    
    @Test
    void resetPasswordFail() {
        UsersController usersController = getUsersController();
        
        Result result = usersController.resetPassword(0, "321");
        
        assertTrue(result.hasError());
        assertEquals("O usuário deve ser informado para geração da senha", result.getErrorMessage());
    }
    
    @Test
    void resetPasswordSuccess() {
        int userId = 5;
        UsersController usersController = getUsersController();
        
        Result result = usersController.resetPassword(userId, "321");
        
        assertFalse(result.hasError());
    }
    
    @Test
    void changePasswordFail() {
        String expectedError = "Model inválido";
        ChangePasswordModel modelMock = mock(ChangePasswordModel.class); 
        modelMock.confirmPassword = "321";
        when(modelMock.validate())
            .thenReturn(Result.error(expectedError));
        UsersController usersController = getUsersController();
      
        Result result = usersController.changePassword(modelMock);
        
        assertTrue(result.hasError());
        assertEquals(expectedError, result.getErrorMessage());
    }
    
    @Test
    void changePasswordSuccess() {
        User userMock = mock(User.class);
        when(userMock.getId())
            .thenReturn(5);
        ChangePasswordModel modelMock = mock(ChangePasswordModel.class); 
        modelMock.confirmPassword = "321";
        when(modelMock.validate())
            .thenReturn(Result.ok());
        when(_authContextMock.getCurrentUser())
            .thenReturn(userMock);
        UsersController usersController = getUsersController();
      
        Result result = usersController.changePassword(modelMock);
        
        assertFalse(result.hasError());
    }
    
    private UsersController getUsersController(){
        return new UsersController(_authContextMock, _userRepositoryMock, _userValidatorMock);
    }
}
