/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import context.IAuthContext;
import entities.EntityBase;
import entities.User;
import enums.Controller;
import enums.CrudFunctionality;
import java.util.ArrayList;
import repositories.IRepository;
import utils.Result;
import utils.ResultData;
import validators.IValidator;

/**
 *
 * @author Paulo
 */
public class ControllerBase<TEntity extends EntityBase> {
    private Controller _controller;
    private IAuthContext _authContext;
    private IRepository<TEntity> _repository;
    private IValidator<TEntity> _validator;
    
    public ControllerBase(
        Controller controller,
        IAuthContext authContext,
        IRepository<TEntity> repository,
        IValidator<TEntity> validator
    ) {
        _repository = repository;
        _validator = validator;
        _controller = controller;
        _authContext = authContext;
    }
    
    public User getCurrentUser() {
        return _authContext.getCurrentUser();
    }
    
    public ResultData<TEntity> getById(int id) {
        if (!_authContext.hasPermission(_controller, CrudFunctionality.Read))
            return ResultData.error("O Usuário não tem permissão para ler registro.");
        
        TEntity entity = _repository.getById(id);
        return ResultData.ok(entity);
    }
    
    public ResultData<ArrayList<TEntity>> getAll() {
        if (!_authContext.hasPermission(_controller, CrudFunctionality.List))
            return ResultData.error("O Usuário não tem permissão para listar os registros.");
        
        ArrayList<TEntity> list = _repository.getAll();
        return ResultData.ok(list);
    }
    
    public Result create(TEntity entity) {
        if (!_authContext.hasPermission(_controller, CrudFunctionality.Create))
            return Result.error("O Usuário não tem permissão para criar um novo registro");
        
        Result validationResult = _validator.validate(entity);
        if (validationResult.hasError()) return validationResult;
        
        _repository.create(entity);
        
        return Result.ok();
    }
    
    public Result update(TEntity entity) {
        if (!_authContext.hasPermission(_controller, CrudFunctionality.Update))
            return Result.error("O Usuário não tem permissão para atualizar o registro");
        
        Result validationResult = _validator.validate(entity);
        if (validationResult.hasError()) return validationResult;
        
        _repository.update(entity);
        
        return Result.ok();
    }
    
    public Result delete(int id) {
        if (!_authContext.hasPermission(_controller, CrudFunctionality.Delete))
            return Result.error("O Usuário não tem permissão para remover o registro");
        
        TEntity entity = _repository.getById(id);
        
        _repository.delete(entity);
        
        return Result.ok();
    }
}
