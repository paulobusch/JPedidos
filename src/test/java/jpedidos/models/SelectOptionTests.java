/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpedidos.models;

import enums.Role;
import models.SelectOption;
import models.SelectRole;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import utils.Result;

/**
 *
 * @author Paulo
 */
public class SelectOptionTests {
    @Test
    void validModel() {
        SelectOption model = new SelectOption();
        model.text = "Item 1";
        model.value = 8;
        
        Result result = model.validate();
        
        assertFalse(result.hasError());
        assertNull(result.getErrorMessage());
    }
    
    @Test
    void getString() {
        SelectOption model = new SelectOption();
        model.text = "Item 2";
        model.value = 9;
        
        String result = model.toString();
        
        assertEquals(model.text, result);
    }
}
