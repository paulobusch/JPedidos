/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpedidos;

import context.AuthContext;
import context.IAuthContext;
import controllers.UsersController;
import database.DatabaseAdapter;
import javax.swing.JOptionPane;
import repositories.IUserRepository;
import repositories.UserRepository;
import utils.JPedidosException;
import validators.UserValidator;
import views.LoginForm;

/**
 *
 * @author Paulo
 */
public class JPedidos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Settings.Load("configuration.properties");
        DatabaseAdapter adapter = new DatabaseAdapter();
        
        IAuthContext context = new AuthContext();
        
        IUserRepository userRepository = new UserRepository(adapter);
        UsersController usersController = new UsersController(
            context, 
            userRepository, 
            new UserValidator(userRepository)
        );
        
        try {
            new LoginForm(usersController).setVisible(true);
        } catch (JPedidosException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            System.out.println(e.toString());
        }
    }
    
}
