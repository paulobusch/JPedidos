/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpedidos.models;

import models.ChangePasswordModel;
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
public class ChangePasswordModelTests {
    @ParameterizedTest
    @CsvSource({
        "        , Pass123$, A nova senha deve ser informada.",
        "      '', Pass123$, A nova senha deve ser informada.",
        "PASS123$, PASS123$, A senha deve ter caracteres em minúsculo.",
        "pass123$, pass123$, A senha deve ter caracteres em maiúsculo.",
        "Passsss$, Passsss$, A senha deve ter caracteres numéricos.",
        "Pass1234, Pass1234, A senha deve ter caracteres especiais.",
        " Pass12$,  Pass12$, A senha deve ter pelo menos 8 caracteres.",
        "Pass123$,  Pass12$, As senhas deve coincindir."
    })
    void invalidModel(String password, String confirmPassword, String expectedError) {
        ChangePasswordModel model = new ChangePasswordModel();
        model.newPassword = password;
        model.confirmPassword = confirmPassword;
        
        Result result = model.validate();
        
        assertTrue(result.hasError());
        assertEquals(expectedError, result.getErrorMessage());
    }
    
    @Test
    void validModel() {
        ChangePasswordModel model = new ChangePasswordModel();
        model.newPassword = "Pass123$";
        model.confirmPassword = "Pass123$";
        
        Result result = model.validate();
        
        assertFalse(result.hasError());
        assertNull(result.getErrorMessage());
    }
}
