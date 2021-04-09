/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpedidos.constrollers;

import context.IAuthContext;
import controllers.ControllerBase;
import entities.User;
import enums.Controller;
import jpedidos.mocks.EntityMock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import repositories.IRepository;
import validators.IValidator;

/**
 *
 * @author Paulo
 */
public class BaseControllerTests {
   
    private IAuthContext _authContextMock;
    private IRepository<EntityMock> _repositoryMock;
    private IValidator<EntityMock> _validatorMock;
    
    public BaseControllerTests() {
        _authContextMock = mock(IAuthContext.class);
        _repositoryMock = mock(IRepository.class);
        _validatorMock = mock(IValidator.class);
    }
    
    @Test
    void getCurrentUser() {
        User userMock = mock(User.class);
        when(_authContextMock.getCurrentUser())
            .thenReturn(userMock);
        ControllerBase<EntityMock> baseController = getBaseController();
        
        User currentUser = baseController.getCurrentUser();
        
        assertNotNull(currentUser);
        assertEquals(userMock, currentUser);
    }
    
    private ControllerBase<EntityMock> getBaseController() {
        return new ControllerBase<EntityMock>(
            Controller.Customers, 
            _authContextMock, 
            _repositoryMock, 
            _validatorMock
        );
    }
}
