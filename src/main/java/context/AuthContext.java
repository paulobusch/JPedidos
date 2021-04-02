/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package context;

import entities.User;
import enums.Controller;
import enums.CrudFunctionality;
import java.util.List;
import jpedidos.Settings;
import permissions.ControllerFunctionalities;

/**
 *
 * @author Paulo
 */
public class AuthContext implements IAuthContext {

    private User _currentUser;
    
    @Override
    public boolean isAuthenticated() {
        return _currentUser != null;
    }

    @Override
    public boolean anyPermission(List<ControllerFunctionalities> controllerFunctionalities) {
        if (!isAuthenticated()) return false;
        for (ControllerFunctionalities cf : controllerFunctionalities) {
            if (!anyPermission(cf.getController(), cf.getFunctionalities())) continue;
            return true;
        }
        
        return false;
    }

    @Override
    public boolean anyPermission(Controller controller, List<CrudFunctionality> functionalities) {
        if (!isAuthenticated()) return false;
        for (CrudFunctionality functionality : functionalities) {
            if (!hasPermission(controller, functionality)) continue;
            return true;
        }
        
        return false;
    }

    @Override
    public boolean hasPermission(Controller controller, CrudFunctionality functionality) {
        if (!isAuthenticated()) return false;
        List<ControllerFunctionalities> controllerFunctionalities = Settings.Permissions.get(_currentUser.getRole());
        for (ControllerFunctionalities cf : controllerFunctionalities) {
            if (cf.getController() != controller) continue;
            for (CrudFunctionality f : cf.getFunctionalities()) {
                if (f != functionality) continue;
                return true;
            }
        }
        
        return false;
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
