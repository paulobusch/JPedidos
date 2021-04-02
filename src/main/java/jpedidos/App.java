/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpedidos;

import context.AuthContext;
import context.IAuthContext;
import controllers.OrdersController;
import controllers.ProductsController;
import controllers.UsersController;
import database.DatabaseAdapter;
import entities.User;
import javax.swing.JOptionPane;
import repositories.CustomerRepository;
import repositories.ICustomerRepository;
import repositories.IOrderRepository;
import repositories.IProductRepository;
import repositories.IUserRepository;
import repositories.OrderRepository;
import repositories.ProductRepository;
import repositories.UserRepository;
import utils.JPedidosException;
import validators.CustomerValidator;
import validators.OrderProductValidator;
import validators.OrderValidator;
import validators.ProductValidator;
import validators.UserValidator;
import views.LoginForm;
import views.OrderForm;

/**
 *
 * @author Paulo
 */
public class App {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Settings.Load("configuration.properties");
        DatabaseAdapter adapter = new DatabaseAdapter();
        
        IAuthContext context = new AuthContext();
        
        IUserRepository userRepository = new UserRepository(adapter);
        IOrderRepository orderRepository = new OrderRepository(adapter);
        IProductRepository productRepository = new ProductRepository(adapter);
        ICustomerRepository customerRepository = new CustomerRepository(adapter);
        
        UserValidator userValidator = new UserValidator(userRepository);
        CustomerValidator customerValidator = new CustomerValidator();
        ProductValidator productValidator = new ProductValidator();
        OrderProductValidator orderProductValidator = new OrderProductValidator(); 
        OrderValidator orderValidator = new OrderValidator(orderProductValidator);
        
        UsersController usersController = new UsersController(
            context, 
            userRepository, 
            userValidator
        );
        OrdersController ordersController = new OrdersController(
            context,
            orderRepository,
            customerRepository,
            productRepository,
            orderValidator
        );
        ProductsController productsController = new ProductsController(
            context,
            productRepository,
            productValidator
        );
        
        
        try {
            new LoginForm(
                context,
                usersController, 
                ordersController, 
                productsController
            ).setVisible(true);
            
            // by pass
            /*
            User user = userRepository.getByLogin("admin");
            context.setCurrentUser(user);
            */
            
            new OrderForm(ordersController).setVisible(true);
        } catch (JPedidosException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            System.out.println(e.toString());
        }
    }
    
}
