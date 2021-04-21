/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpedidos.models;

import models.LoginModel;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import utils.Result;


/**
 *
 * @author Paulo
 */
public class LoginModelTests {
    @ParameterizedTest
    @CsvSource({
        "     , Pass123$, O usuário deve ser informado.",
        "   '', Pass123$, O usuário deve ser informado.",
        "Admin,         , A senha deve ser informada.",
        "Admin,       '', A senha deve ser informada."
    })
    void invalidLogin(String login, String password, String expectedError) {
        LoginModel model = new LoginModel();
        model.login = login;
        model.password = password;
        
        Result result = model.validate();
        
        assertTrue(result.hasError());
        assertEquals(expectedError, result.getErrorMessage());
    }
    
    @Test
    void validLogin() {
        LoginModel model = new LoginModel();
        model.login = "Admin";
        model.password = "Pass123$";
        
        Result result = model.validate();
        
        assertFalse(result.hasError());
        assertNull(result.getErrorMessage());
    }
}
