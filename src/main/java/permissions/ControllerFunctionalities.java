/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package permissions;

import enums.Controller;
import enums.CrudFunctionality;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Paulo
 */
public class ControllerFunctionalities {
    private Controller _controller;
    private List<CrudFunctionality> _functionalities;
    
    ControllerFunctionalities(
        Controller controller,
        List<CrudFunctionality> functionalities
    ) {
        _controller = controller;
        _functionalities = functionalities;
    }

    public Controller getController() {
        return _controller;
    }

    public void setController(Controller _controller) {
        this._controller = _controller;
    }

    public List<CrudFunctionality> getFunctionalities() {
        return _functionalities;
    }

    public void setFunctionalities(List<CrudFunctionality> _functionalities) {
        this._functionalities = _functionalities;
    }
    
    
}
