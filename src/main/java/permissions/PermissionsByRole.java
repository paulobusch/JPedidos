/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package permissions;

import static java.util.Arrays.asList;  
import enums.Controller;
import enums.CrudFunctionality;
import enums.Role;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Paulo
 */
public class PermissionsByRole {
    public static Map<Role, List<ControllerFunctionalities>> permissions() {
        HashMap<Role, List<ControllerFunctionalities>> roleFuncionalities = new HashMap<Role, List<ControllerFunctionalities>>();
        
        roleFuncionalities.put(Role.Admin, asList(
            new ControllerFunctionalities(Controller.Users, asList(
                CrudFunctionality.Read,
                CrudFunctionality.List,
                CrudFunctionality.Create,
                CrudFunctionality.Update,
                CrudFunctionality.Delete
            ))
        ));
        
        roleFuncionalities.put(Role.Gerente, asList(
            new ControllerFunctionalities(Controller.Products, asList(
                CrudFunctionality.Read,
                CrudFunctionality.List,
                CrudFunctionality.Create,
                CrudFunctionality.Update,
                CrudFunctionality.Delete
            )),
            new ControllerFunctionalities(Controller.Orders, asList(
                CrudFunctionality.Read,
                CrudFunctionality.List
            ))
        ));
        
        roleFuncionalities.put(Role.Funcionario, asList(
            new ControllerFunctionalities(Controller.Orders, asList(
                CrudFunctionality.Read,
                CrudFunctionality.List,
                CrudFunctionality.Create,
                CrudFunctionality.Update
            ))
        ));
        
        return roleFuncionalities;
    };
}
