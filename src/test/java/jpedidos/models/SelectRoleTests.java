/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpedidos.models;

import enums.Role;
import models.SelectRole;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import utils.Result;

/**
 *
 * @author Paulo
 */
public class SelectRoleTests {
    @Test
    void constructor() {
        Role role = Role.Funcionario;
        String label = "Funcionário";
        
        SelectRole model = new SelectRole(role, label);
        
        assertEquals(label, model.text);
        assertEquals(role, model.value);
    }
    
    @Test
    void validModel() {
        SelectRole model = new SelectRole();
        model.text = "Admin";
        model.value = Role.Admin;
        
        Result result = model.validate();
        
        assertFalse(result.hasError());
        assertNull(result.getErrorMessage());
    }
    
    @Test
    void getString() {
        SelectRole model = new SelectRole();
        model.text = "Admin";
        model.value = Role.Admin;
        
        String result = model.toString();
        
        assertEquals(model.text, result);
    }
}
