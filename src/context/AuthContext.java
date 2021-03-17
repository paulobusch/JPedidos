/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package context;

import entities.User;
import enums.Controller;
import enums.CrudFunctionality;

/**
 *
 * @author Paulo
 */
public class AuthContext implements IAuthContext {

    private User _currentUser;
    
    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public boolean hasPermission(Controller controller, CrudFunctionality functionality) {
        return true;
    }

    @Override
    public User getCurrentUser() {
        return _currentUser;
    }
    
    @Override
    public void setCurrentUser(User user) {
        _currentUser = user;
    }
}
