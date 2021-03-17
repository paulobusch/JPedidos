/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpedidos;

import database.DatabaseAdapter;
import javax.swing.JOptionPane;
import utils.JPedidosException;
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
        //Settings.Load("configuration.properties");
        //DatabaseAdapter adapter = new DatabaseAdapter();
        try {
            LoginForm view = new LoginForm();
            view.setVisible(true);
        } catch (JPedidosException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            System.out.println(e.toString());
        }
    }
    
}
