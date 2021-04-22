/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpedidos.entities;

import entities.User;
import enums.Role;
import jpedidos.Settings;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import utils.AESHash;

/**
 *
 * @author Paulo
 */
public class UserTests {
    @ParameterizedTest
    @CsvSource({
        "        , false",
        "      '', false",
        "321     , true",
        "Pass123$, true"
    })
    void encriptPassword(String rawPassword, boolean expectedGenerateHash) {
        User user = new User();
        
        user.setRawPassword(rawPassword);
        
        if (expectedGenerateHash){
            String expectedHash = AESHash.encrypt(rawPassword, Settings.SecretKey);
            assertEquals(expectedHash, user.getPassword());
        } else {
            assertNull(user.getPassword());
        }
    }

    @ParameterizedTest
    @CsvSource({
        "        ,         , true",
        "        ,       '', true",
        "      '',         , true",
        "      '',       '', true",
        "Pass123$, Pass123$, true",
        "Pass123$,         , false",
        "Pass123$,       '', false",
        "        , Pass123$, false",
        "      '', Pass123$, false",
    })    
    void validatePassword(String password, String checkPassword, boolean validPassword) {
        User user = new User();
        user.setRawPassword(password);
        
        boolean result = user.validatePassword(checkPassword);
        
        assertEquals(validPassword, result);
    }
    
    @Test
    void gettersAndSetters() {
        User user = new User();
        user.setPasswordTemporary(true);
        user.setRole(Role.Funcionario);
        
        assertTrue(user.isPasswordTemporary());
        assertEquals(Role.Funcionario, user.getRole());
    }
    
    @ParameterizedTest
    @CsvSource({
        "       , ",
        "Admin  , Admin",
        "Gerente, Gerente",
        "Funcionário, Funcionario",
    })
    void setRawRole(String rawRole, Role expectedRole) {
        User user = new User();
        
        user.setRole(rawRole);
        
        assertEquals(expectedRole, user.getRole());
    }
    
    @ParameterizedTest
    @CsvSource({
        "       , ",
        "Admin  , Admin",
        "Gerente, Gerente",
        "Funcionario, Funcionário"
    })
    void getRawRole(Role role, String expectedRole) {
        User user = new User();
        user.setRole(role);
        
        String roleString = user.getRoleString();
        
        assertEquals(expectedRole, roleString);
    }
}
