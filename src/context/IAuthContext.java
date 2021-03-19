/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package context;

import entities.User;
import enums.Controller;
import enums.CrudFunctionality;
import enums.Role;
import java.util.ArrayList;
import java.util.List;
import permissions.ControllerFunctionalities;

/**
 *
 * @author Paulo
 */
public interface IAuthContext {
    boolean isAuthenticated();
    boolean hasPermission(Controller controller, CrudFunctionality functionality);
    boolean anyPermission(Controller controller, List<CrudFunctionality> functionalities);
    boolean anyPermission(List<ControllerFunctionalities> controllerFunctionalities);    
    User getCurrentUser();    
    void setCurrentUser(User user);
}
