/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpedidos.controllers;

import context.IAuthContext;
import controllers.ControllerBase;
import entities.User;
import enums.Controller;
import enums.CrudFunctionality;
import java.util.ArrayList;
import jpedidos.mocks.EntityMock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import repositories.IRepository;
import utils.Result;
import utils.ResultData;
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
    
    @Test
    void getByIdWithoutPermission() {
        when(_authContextMock.hasPermission(getController(), CrudFunctionality.Read))
            .thenReturn(false);
        ControllerBase<EntityMock> baseController = getBaseController();
        
        ResultData<EntityMock> result = baseController.getById(0);
        
        assertTrue(result.hasError());
        assertEquals("O Usuário não tem permissão para ler registro.", result.getErrorMessage());
    }
    
    @Test
    void getByIdWithSuccess() {
        EntityMock entityMock = new EntityMock();
        entityMock.setId(5);
        when(_authContextMock.hasPermission(getController(), CrudFunctionality.Read))
            .thenReturn(true);
        when(_repositoryMock.getById(entityMock.getId()))
            .thenReturn(entityMock);
        ControllerBase<EntityMock> baseController = getBaseController();
        
        ResultData<EntityMock> result = baseController.getById(entityMock.getId());
        
        assertFalse(result.hasError());
        assertNotNull(result.getData());
        assertEquals(entityMock.getId(), result.getData().getId());
        assertEquals(entityMock, result.getData());
    }
    
    @Test
    void getAllWithoutPermission() {
        when(_authContextMock.hasPermission(getController(), CrudFunctionality.List))
            .thenReturn(false);
        ControllerBase<EntityMock> baseController = getBaseController();
        
        ResultData<ArrayList<EntityMock>> result = baseController.getAll();
        
        assertTrue(result.hasError());
        assertEquals("O Usuário não tem permissão para listar os registros.", result.getErrorMessage());
    }
    
    @Test
    void getAllWithSuccess() {
        ArrayList<EntityMock> list = new ArrayList<>();
        EntityMock entityMock = new EntityMock();
        list.add(entityMock);
        when(_authContextMock.hasPermission(getController(), CrudFunctionality.List))
            .thenReturn(true);
        when(_repositoryMock.getAll())
            .thenReturn(list);
        ControllerBase<EntityMock> baseController = getBaseController();
        
        ResultData<ArrayList<EntityMock>> result = baseController.getAll();
        
        assertFalse(result.hasError());
        assertNotNull(result.getData());
        assertEquals(list.size(), result.getData().size());
        assertEquals(entityMock, result.getData().get(0));
    }
    
    @Test
    void createWithoutPermission() {
        EntityMock entityMock = new EntityMock();
        entityMock.setId(6);
        when(_authContextMock.hasPermission(getController(), CrudFunctionality.Create))
            .thenReturn(false);
        ControllerBase<EntityMock> baseController = getBaseController();
        
        Result result = baseController.create(entityMock);
        
        assertTrue(result.hasError());
        assertEquals("O Usuário não tem permissão para criar um novo registro", result.getErrorMessage());
        verify(_repositoryMock, never()).create(any());
    }
    
    @Test
    void createWithInvalidModel() {
        Result expectedResult = Result.error("Model inválido");
        EntityMock entityMock = new EntityMock();
        entityMock.setId(6);
        when(_authContextMock.hasPermission(getController(), CrudFunctionality.Create))
            .thenReturn(true);
        when(_validatorMock.validate(entityMock))
            .thenReturn(expectedResult);
        ControllerBase<EntityMock> baseController = getBaseController();
        
        Result result = baseController.create(entityMock);
        
        assertTrue(result.hasError());
        assertEquals(expectedResult.getErrorMessage(), result.getErrorMessage());
        verify(_repositoryMock, never()).create(any());
    }
    
    @Test
    void createWithSuccess() {
        EntityMock entityMock = new EntityMock();
        entityMock.setId(6);
        when(_authContextMock.hasPermission(getController(), CrudFunctionality.Create))
            .thenReturn(true);
        when(_validatorMock.validate(entityMock))
            .thenReturn(Result.ok());
        ControllerBase<EntityMock> baseController = getBaseController();
        
        Result result = baseController.create(entityMock);
        
        assertFalse(result.hasError());
        verify(_repositoryMock, atLeastOnce()).create(entityMock);
    }
    
    @Test
    void updateWithoutPermission() {
        EntityMock entityMock = new EntityMock();
        entityMock.setId(6);
        when(_authContextMock.hasPermission(getController(), CrudFunctionality.Update))
            .thenReturn(false);
        ControllerBase<EntityMock> baseController = getBaseController();
        
        Result result = baseController.update(entityMock);
        
        assertTrue(result.hasError());
        assertEquals("O Usuário não tem permissão para atualizar o registro", result.getErrorMessage());
        verify(_repositoryMock, never()).update(any());
    }
    
    @Test
    void updateWithInvalidModel() {
        Result expectedResult = Result.error("Model inválido");
        EntityMock entityMock = new EntityMock();
        entityMock.setId(6);
        when(_authContextMock.hasPermission(getController(), CrudFunctionality.Update))
            .thenReturn(true);
        when(_validatorMock.validate(entityMock))
            .thenReturn(expectedResult);
        ControllerBase<EntityMock> baseController = getBaseController();
        
        Result result = baseController.update(entityMock);
        
        assertTrue(result.hasError());
        assertEquals(expectedResult.getErrorMessage(), result.getErrorMessage());
        verify(_repositoryMock, never()).update(any());
    }
    
    @Test
    void updateWithSuccess() {
        EntityMock entityMock = new EntityMock();
        entityMock.setId(6);
        when(_authContextMock.hasPermission(getController(), CrudFunctionality.Update))
            .thenReturn(true);
        when(_validatorMock.validate(entityMock))
            .thenReturn(Result.ok());
        ControllerBase<EntityMock> baseController = getBaseController();
        
        Result result = baseController.update(entityMock);
        
        assertFalse(result.hasError());
        verify(_repositoryMock, atLeastOnce()).update(entityMock);
    }
    
    @Test
    void deleteWithoutPermission() {
        EntityMock entityMock = new EntityMock();
        entityMock.setId(6);
        when(_authContextMock.hasPermission(getController(), CrudFunctionality.Delete))
            .thenReturn(false);
        ControllerBase<EntityMock> baseController = getBaseController();
        
        Result result = baseController.delete(entityMock.getId());
        
        assertTrue(result.hasError());
        assertEquals("O Usuário não tem permissão para remover o registro", result.getErrorMessage());
        verify(_repositoryMock, never()).delete(any());
    }
    
    @Test
    void deleteWithSuccess() {
        EntityMock entityMock = new EntityMock();
        entityMock.setId(6);
        when(_authContextMock.hasPermission(getController(), CrudFunctionality.Delete))
            .thenReturn(true);
        when(_repositoryMock.getById(entityMock.getId()))
            .thenReturn(entityMock);
        ControllerBase<EntityMock> baseController = getBaseController();
        
        Result result = baseController.delete(entityMock.getId());
        
        assertFalse(result.hasError());
        verify(_repositoryMock, atLeastOnce()).delete(entityMock);
    }
    
    private ControllerBase<EntityMock> getBaseController() {
        return new ControllerBase<EntityMock>(
            getController(), 
            _authContextMock, 
            _repositoryMock, 
            _validatorMock
        );
    }
    
    private Controller getController () {
        return Controller.Customers;
    }
}
