/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import controllers.OrdersController;
import controllers.ProductsController;
import entities.Customer;
import entities.Order;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import models.SelectOption;
import utils.Result;

/**
 *
 * @author Paulo
 */
public class OrderForm extends javax.swing.JFrame {

    /**
     * Creates new form OrderForm
     */
    private OrdersController _ordersController;
    private ProductsController _productsController;
    
    private Order _orderCurrent = new Order();
    
    public OrderForm(
        OrdersController ordersController,
        ProductsController productsController
    ) {
        initComponents();
        
        _ordersController = ordersController;
        _productsController = productsController;;
        
        loadSelectFields();
    }
    
    private Order getOrder() {
        Order order = _orderCurrent;
        
        Customer customerSelected = cb_customer.getSelectedItem() != null
            ? (Customer)cb_customer.getSelectedItem()
            : null;
        order.setCustomer(customerSelected);
        
        return order;
    }
    
    private void setOrder(Order order) {
        // TODO: implement this method
    }
    
    private void loadSelectFields() {        
        cb_customer.removeAllItems();
        cb_product.removeAllItems();
        
        for (SelectOption customer : _ordersController.getCustomersFlat())
            cb_customer.addItem(customer);        
        for (SelectOption product : _ordersController.getProductsFlat())
            cb_product.addItem(product);
    }
    
    private void displayResult(Result result) {
        JOptionPane.showMessageDialog(this, result.getErrorMessage(), "Informação", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_fields = new javax.swing.JPanel();
        lbl_name = new javax.swing.JLabel();
        cb_customer = new javax.swing.JComboBox();
        btn_add_customer = new javax.swing.JButton();
        pnl_actions = new javax.swing.JPanel();
        btn_clear = new javax.swing.JButton();
        btn_save = new javax.swing.JButton();
        btn_trash = new javax.swing.JButton();
        list_scroll = new javax.swing.JScrollPane();
        tbl_list = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        pnl_actions1 = new javax.swing.JPanel();
        btn_clear1 = new javax.swing.JButton();
        btn_save1 = new javax.swing.JButton();
        btn_trash1 = new javax.swing.JButton();
        lbl_name2 = new javax.swing.JLabel();
        cb_product = new javax.swing.JComboBox();
        lbl_name3 = new javax.swing.JLabel();
        txt_product_amount = new javax.swing.JFormattedTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lbl_name.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbl_name.setText("Cliente:");

        cb_customer.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        btn_add_customer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/plus-48.png"))); // NOI18N
        btn_add_customer.setName("btn_add"); // NOI18N
        btn_add_customer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_add_customerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_fieldsLayout = new javax.swing.GroupLayout(pnl_fields);
        pnl_fields.setLayout(pnl_fieldsLayout);
        pnl_fieldsLayout.setHorizontalGroup(
            pnl_fieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_fieldsLayout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(lbl_name)
                .addGap(18, 18, 18)
                .addComponent(cb_customer, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_add_customer, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );
        pnl_fieldsLayout.setVerticalGroup(
            pnl_fieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_fieldsLayout.createSequentialGroup()
                .addGroup(pnl_fieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_fieldsLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(pnl_fieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_name)
                            .addComponent(cb_customer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnl_fieldsLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(btn_add_customer, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        btn_clear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/clean-48.png"))); // NOI18N
        btn_clear.setName("btn_clear"); // NOI18N

        btn_save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/save-48.png"))); // NOI18N
        btn_save.setName("btn_add"); // NOI18N
        btn_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveActionPerformed(evt);
            }
        });

        btn_trash.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/trash-48.png"))); // NOI18N
        btn_trash.setName("btn_add"); // NOI18N

        javax.swing.GroupLayout pnl_actionsLayout = new javax.swing.GroupLayout(pnl_actions);
        pnl_actions.setLayout(pnl_actionsLayout);
        pnl_actionsLayout.setHorizontalGroup(
            pnl_actionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_actionsLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btn_clear, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_trash, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_save, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );
        pnl_actionsLayout.setVerticalGroup(
            pnl_actionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_actionsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_actionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_trash, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_save, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_clear, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tbl_list.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Cliente", "Produtos", "Data"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tbl_list.setName("tbl_list"); // NOI18N
        list_scroll.setViewportView(tbl_list);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Produtos"));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Produto", "Quantidade"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        btn_clear1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/clean-48.png"))); // NOI18N
        btn_clear1.setName("btn_clear"); // NOI18N

        btn_save1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/save-48.png"))); // NOI18N
        btn_save1.setName("btn_add"); // NOI18N
        btn_save1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_save1ActionPerformed(evt);
            }
        });

        btn_trash1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/trash-48.png"))); // NOI18N
        btn_trash1.setName("btn_add"); // NOI18N

        javax.swing.GroupLayout pnl_actions1Layout = new javax.swing.GroupLayout(pnl_actions1);
        pnl_actions1.setLayout(pnl_actions1Layout);
        pnl_actions1Layout.setHorizontalGroup(
            pnl_actions1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_actions1Layout.createSequentialGroup()
                .addComponent(btn_clear1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 345, Short.MAX_VALUE)
                .addComponent(btn_trash1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_save1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnl_actions1Layout.setVerticalGroup(
            pnl_actions1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_actions1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_actions1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_trash1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_save1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_clear1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        lbl_name2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbl_name2.setText("Produto:");

        cb_product.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        lbl_name3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbl_name3.setText("Quantidade:");

        txt_product_amount.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(68, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lbl_name2)
                        .addGap(18, 18, 18)
                        .addComponent(cb_product, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(lbl_name3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_product_amount, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 513, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(pnl_actions1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(7, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_name2)
                    .addComponent(cb_product, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_name3)
                    .addComponent(txt_product_amount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnl_actions1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(list_scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 673, Short.MAX_VALUE)
                            .addComponent(pnl_actions, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(pnl_fields, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnl_fields, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(pnl_actions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(list_scroll, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed
        Order order = getOrder();
        boolean byInsert = _orderCurrent.getId() == 0;
        Result result = byInsert
            ? _ordersController.create(order)
            : _ordersController.update(order);
        
        if (result.hasError()){
            displayResult(result);
            return;
        }
        
        if (byInsert) {
            // TODO: insert in table
        } else {
            // TODO: update in table
        }
    }//GEN-LAST:event_btn_saveActionPerformed

    private void btn_add_customerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_add_customerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_add_customerActionPerformed

    private void btn_save1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_save1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_save1ActionPerformed

    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_add_customer;
    private javax.swing.JButton btn_clear;
    private javax.swing.JButton btn_clear1;
    private javax.swing.JButton btn_save;
    private javax.swing.JButton btn_save1;
    private javax.swing.JButton btn_trash;
    private javax.swing.JButton btn_trash1;
    private javax.swing.JComboBox cb_customer;
    private javax.swing.JComboBox cb_product;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lbl_name;
    private javax.swing.JLabel lbl_name2;
    private javax.swing.JLabel lbl_name3;
    private javax.swing.JScrollPane list_scroll;
    private javax.swing.JPanel pnl_actions;
    private javax.swing.JPanel pnl_actions1;
    private javax.swing.JPanel pnl_fields;
    private javax.swing.JTable tbl_list;
    private javax.swing.JFormattedTextField txt_product_amount;
    // End of variables declaration//GEN-END:variables
}
