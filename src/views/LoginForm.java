/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import controllers.UsersController;
import javax.swing.JOptionPane;
import models.LoginModel;
import utils.Result;

/**
 *
 * @author Paulo
 */
public class LoginForm extends javax.swing.JFrame {

    /**
     * Creates new form LoginFrame
     */
    private UsersController _usersController;
    
    public LoginForm(UsersController usersController) {
        initComponents();
        _usersController = usersController;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_login = new java.awt.TextField();
        jLabel3 = new javax.swing.JLabel();
        txt_password = new javax.swing.JPasswordField();
        btn_login = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("JPedidos - Login");

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel1.setText("Login");

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setText("Usuário: ");

        txt_login.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_login.setName("txt_user"); // NOI18N

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setText("Senha: ");

        txt_password.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_password.setName("txt_pass"); // NOI18N

        btn_login.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btn_login.setText("Entrar");
        btn_login.setName("btn_login"); // NOI18N
        btn_login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_loginActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(txt_login, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(txt_password, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(46, 46, 46))
            .addGroup(layout.createSequentialGroup()
                .addGap(158, 158, 158)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_login, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(145, 145, 145))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(txt_login, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(btn_login, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        txt_login.getAccessibleContext().setAccessibleName("");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private LoginModel getLodinModel() {
        LoginModel model = new LoginModel();
        model.login = txt_login.getText();
        model.password = txt_password.getText();
        return model;
    }
    
    private void btn_loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_loginActionPerformed
        Result result = _usersController.login(this.getLodinModel());
        if (result.HasError()) {
            JOptionPane.showMessageDialog(
                this, 
                result.getErrorMessage(), 
                "Informação", 
                JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }
        
        new MainForm().setVisible(true);
        
        this.dispose();
    }//GEN-LAST:event_btn_loginActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_login;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private java.awt.TextField txt_login;
    private javax.swing.JPasswordField txt_password;
    // End of variables declaration//GEN-END:variables
}
