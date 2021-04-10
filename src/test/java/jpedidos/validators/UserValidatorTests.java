/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpedidos.validators;

import entities.User;
import enums.Role;
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
public class UserValidatorTests {
    
    private IUserRepository _userRepositoryMock;
    
    public UserValidatorTests() {
        _userRepositoryMock = mock(IUserRepository.class);
    }
    
    @ParameterizedTest
    @CsvSource({
        "     , lucas, 321, lucas@email.com, Admin, O nome deve ser informado.",
        "''   , lucas, 321, lucas@email.com, Admin, O nome deve ser informado.",
        "Lucas,      , 321, lucas@email.com, Admin, O login deve ser informado.",
        "Lucas, ''   , 321, lucas@email.com, Admin, O login deve ser informado.",
        "Lucas, lucas,    , lucas@email.com, Admin, A senha deve ser informada.",
        "Lucas, lucas, '' , lucas@email.com, Admin, A senha deve ser informada.",
        "Lucas, lucas, 321,                , Admin, O email deve ser informado.",
        "Lucas, lucas, 321, ''             , Admin, O email deve ser informado.",
        "Lucas, lucas, 321, lucas.com      , Admin, O email deve ser válido.",
        "Lucas, lucas, 321, lucas@email.com,      , O papel deve ser informado.",
        "Lucas, lucas, 321, lucas@email.com, ''   , O papel deve ser informado."
    }) 
    void invalidUser(String name, String login, String password, String email, String role, String expectedError) {
        User user = new User();
        user.setName(name);
        user.setLogin(login);
        user.setPassword(password);
        user.setEmail(email);
        user.setRole(role);
        UserValidator validator = new UserValidator(_userRepositoryMock);
        
        Result result = validator.validate(user);
        
        assertTrue(result.hasError());
        assertEquals(expectedError, result.getErrorMessage());
    }
    
    @ParameterizedTest
    @CsvSource({
        "true, false, Já existe um usuário com este login cadastrado.",
        "false, true, Já existe um usuário com este email cadastrado."
    }) 
    void duplicateUser(boolean existLogin, boolean existEmail, String expectedError) {
        User user = new User();
        user.setName("Lucas");
        user.setLogin("lucas");
        user.setPassword("321");
        user.setEmail("lucas@email.com");
        user.setRole(Role.Admin);
        UserValidator validator = new UserValidator(_userRepositoryMock);
        when(_userRepositoryMock.existByLogin(user.getLogin(), user.getId()))
            .thenReturn(existLogin);
        when(_userRepositoryMock.existByEmail(user.getEmail(), user.getId()))
            .thenReturn(existEmail);
        
        Result result = validator.validate(user);
        
        assertTrue(result.hasError());
        assertEquals(expectedError, result.getErrorMessage());
    }
    
    @Test
    void validUser() {
        User user = new User();
        user.setName("Lucas");
        user.setLogin("lucas");
        user.setPassword("321");
        user.setEmail("lucas@email.com.br");
        user.setRole(Role.Admin);
        UserValidator validator = new UserValidator(_userRepositoryMock);
        when(_userRepositoryMock.existByLogin(user.getLogin(), user.getId()))
            .thenReturn(false);
        when(_userRepositoryMock.existByEmail(user.getEmail(), user.getId()))
            .thenReturn(false);
        
        Result result = validator.validate(user);
        
        assertFalse(result.hasError());
        assertNull(result.getErrorMessage());
    }
}
