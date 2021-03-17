/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validators;

import entities.User;
import repositories.IUserRepository;
import utils.Result;
import utils.Validator;

/**
 *
 * @author Paulo
 */
public class UserValidator implements IValidator<User> {

    private IUserRepository _userRepository;
    
    public UserValidator(IUserRepository userRepository) {
        _userRepository = userRepository;
    }
    
    @Override
    public Result validate(User user) {
        if (user.getName() == null || user.getName().equals(""))
            return Result.Error("O nome deve ser informado.");
        if (user.getLogin() == null || user.getLogin().equals(""))
            return Result.Error("O login deve ser informado.");
        if (user.getPassword() == null || user.getPassword().equals(""))
            return Result.Error("A senha deve ser informada.");
        if (user.getEmail() == null || user.getEmail().equals(""))
            return Result.Error("O email deve ser informado.");
        if (!Validator.validateEmailAddress(user.getEmail()))
            return Result.Error("O email deve ser válido.");
        if (user.getRole() == null)
            return Result.Error("O papel deve ser informado.");
        
        boolean existLogin = _userRepository.existByLogin(user.getLogin(), user.getId());
        if (existLogin)
            return Result.Error("Já existe um usuário com este login cadastrado.");
        
        boolean existEmail = _userRepository.existByEmail(user.getEmail(), user.getId());
        if (existEmail)
            return Result.Error("Já existe um usuário com este email cadastrado.");
        
        return Result.Ok();
    }
    
}
