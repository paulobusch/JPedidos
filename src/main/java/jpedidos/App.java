package jpedidos;

import database.DatabaseAdapter;
import javax.swing.JOptionPane;
import utils.JPedidosException;
import views.LoginForm;

public class App {
    public static void main(String[] args) {
        Settings.Load("configuration.properties");
        DatabaseAdapter adapter = new DatabaseAdapter();
        try {
            LoginForm view = new LoginForm();
            view.setVisible(true);
        } catch (JPedidosException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            System.out.println(e.toString());
        }
    }
}
