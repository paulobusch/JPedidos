/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import controllers.ProductsController;
import entities.Product;
import entities.User;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import models.SelectRole;
import utils.Result;
import utils.ResultData;

/**
 *
 * @author Paulo
 */
public class ProductForm extends javax.swing.JFrame {

    /**
     * Creates new form ProductForm
     */
    private ProductsController _productsController;

    private Product _productCurrent;
    private DefaultTableModel _productTableModel;
    
    public ProductForm(ProductsController productsController) {
        initComponents();
        _productsController = productsController;
        
        updateTitle();  
    }
    
    private void updateTitle() {
        User currentUser = _productsController.getCurrentUser();
        setTitle("JPedidos - Gerenciar Produtos (" + currentUser.getRoleString() + ")");
    }
    
    private Product getProduct() {
        Product product = _productCurrent != null
            ? _productCurrent
            : new Product();
        
        product.setName(txt_name.getText());
        product.setDescription(txt_description.getText());
        product.setActive(chk_active.isSelected());
        product.setPrice(txt_price.getText().replace(',', '.'));
        
        return product;
    }
    
    private void setProduct(Product product) {
        _productCurrent = product;
        
        txt_name.setText(product.getName());
        txt_description.setText(product.getDescription());
        txt_price.setText(String.format("%.2f", product.getPrice()).replace('.', ','));
        chk_active.setSelected(product.isActive());
    }
    
    private void productTableEvents() {
        ListSelectionModel model = tbl_list.getSelectionModel();
        model.addListSelectionListener(new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = tbl_list.getSelectedRow();
                if (row == -1) {
                    setProduct(new Product());
                    return;
                }
                int id = getTableIdByIndex(_productTableModel, row);
                ResultData<Product> result = _productsController.getById(id);
                if (result.hasError()){
                    displayResult(Result.error(result.getErrorMessage()));
                    return;
                }
                btn_trash.setEnabled(true);
                setProduct(result.getData());
            }
        });
    }
    
    private int getTableIdByIndex(DefaultTableModel model, int index) {
        Object raw = model.getValueAt(index, 0);
        return Integer.parseInt(raw.toString());
    }
    
    private ArrayList<Object> getProductColumnsData(Product product) {
        ArrayList<Object> columns = new ArrayList<>();
        columns.add(product.getId());
        columns.add(product.getName());
        columns.add(String.format("%.2f", product.getPrice()).replace('.', ','));
        columns.add(product.isActive() ? "Ativo" : "Inativo");
        return columns;
    }
    
    private void addRowProductTable(Product product) {
        ArrayList<Object> columnsData = getProductColumnsData(product);
        _productTableModel.addRow(columnsData.toArray());
    }
    
    private void editRowProductTable(Product product) {
        ArrayList<Object> columnsData = getProductColumnsData(product);
        int row = getRowIndexWithId(_productTableModel, product.getId());
        for (Object data : columnsData){
            int col = columnsData.indexOf(data);
            _productTableModel.setValueAt(data, row, col);
        }
    }
    
    private void removeRowProductTable(Product product) {
        int index = getRowIndexWithId(_productTableModel, product.getId());
        _productTableModel.removeRow(index);
    }
    
    private void displayResult(Result result) {
        JOptionPane.showMessageDialog(this, result.getErrorMessage(), "Informação", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void clearProductTable() {
        _productTableModel.setRowCount(0);
    }
    
    private void clearProductSelection() {
        btn_trash.setEnabled(false);
        tbl_list.clearSelection();
        setProduct(new Product());  
    }
    
    private int getRowIndexWithId(DefaultTableModel model, int id) {
        for (int row = 0; row < model.getRowCount(); row++){
            Object raw = model.getValueAt(row, 0);
            int rowId = Integer.parseInt(raw.toString());
            if (rowId == id) return row;
        }
        return -1;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        pnl_fields = new javax.swing.JPanel();
        lbl_name = new javax.swing.JLabel();
        txt_name = new java.awt.TextField();
        lbl_price = new javax.swing.JLabel();
        txt_price = new javax.swing.JFormattedTextField();
        lbl_description = new javax.swing.JLabel();
        txt_scroll = new javax.swing.JScrollPane();
        txt_description = new javax.swing.JTextArea();
        chk_active = new javax.swing.JCheckBox();
        pnl_actions = new javax.swing.JPanel();
        btn_clear = new javax.swing.JButton();
        btn_save = new javax.swing.JButton();
        btn_trash = new javax.swing.JButton();
        list_scroll = new javax.swing.JScrollPane();
        tbl_list = new javax.swing.JTable();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("JPedidos - Produtos");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        lbl_name.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbl_name.setText("Nome:");

        txt_name.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_name.setName("txt_name"); // NOI18N

        lbl_price.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbl_price.setText("Preço:");

        txt_price.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_price.setName("txt_price"); // NOI18N

        lbl_description.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbl_description.setText("Descrição:");

        txt_description.setColumns(20);
        txt_description.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_description.setRows(5);
        txt_description.setName("txt_description"); // NOI18N
        txt_scroll.setViewportView(txt_description);

        chk_active.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        chk_active.setText("Ativo");

        javax.swing.GroupLayout pnl_fieldsLayout = new javax.swing.GroupLayout(pnl_fields);
        pnl_fields.setLayout(pnl_fieldsLayout);
        pnl_fieldsLayout.setHorizontalGroup(
            pnl_fieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_fieldsLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(pnl_fieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_description)
                    .addComponent(lbl_name))
                .addGap(18, 18, 18)
                .addGroup(pnl_fieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(pnl_fieldsLayout.createSequentialGroup()
                        .addComponent(txt_name, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbl_price)
                        .addGap(18, 18, 18)
                        .addComponent(txt_price, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(chk_active))
                    .addComponent(txt_scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 517, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnl_fieldsLayout.setVerticalGroup(
            pnl_fieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_fieldsLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(pnl_fieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_name, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_name)
                    .addGroup(pnl_fieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_price, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lbl_price)
                        .addComponent(chk_active)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(pnl_fieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_description)
                    .addComponent(txt_scroll, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        btn_clear.setIcon(new javax.swing.ImageIcon("C:\\Users\\Paulo\\Desktop\\Cursos\\UTFPR\\7º Semestre\\Oficina de Integração 2\\JPedidos\\src\\main\\java\\assets\\clean-48.png")); // NOI18N
        btn_clear.setName("btn_clear"); // NOI18N
        btn_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clearActionPerformed(evt);
            }
        });

        btn_save.setIcon(new javax.swing.ImageIcon("C:\\Users\\Paulo\\Desktop\\Cursos\\UTFPR\\7º Semestre\\Oficina de Integração 2\\JPedidos\\src\\main\\java\\assets\\save-all-48.png")); // NOI18N
        btn_save.setName("btn_add"); // NOI18N
        btn_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveActionPerformed(evt);
            }
        });

        btn_trash.setIcon(new javax.swing.ImageIcon("C:\\Users\\Paulo\\Desktop\\Cursos\\UTFPR\\7º Semestre\\Oficina de Integração 2\\JPedidos\\src\\main\\java\\assets\\trash-48.png")); // NOI18N
        btn_trash.setName("btn_add"); // NOI18N
        btn_trash.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_trashActionPerformed(evt);
            }
        });

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

            },
            new String [] {
                "ID", "Nome", "Preço", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tbl_list.setName("tbl_list"); // NOI18N
        list_scroll.setViewportView(tbl_list);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(list_scroll, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 661, Short.MAX_VALUE)
            .addComponent(pnl_actions, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnl_fields, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnl_fields, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnl_actions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(list_scroll, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed
        Product product = getProduct();
        boolean byInsert = product.getId() == 0;
        Result result = byInsert
            ? _productsController.create(product)
            : _productsController.update(product);
                
        if (result.hasError()){
            displayResult(result);
            return;
        }
        
        if (byInsert) {
            addRowProductTable(product);
        } else {
            editRowProductTable(product);
        }
        
        clearProductSelection();
    }//GEN-LAST:event_btn_saveActionPerformed

    private void btn_trashActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_trashActionPerformed
        int option = JOptionPane.showConfirmDialog(this, "Deseja excluir o produto?", "Excluir", JOptionPane.YES_NO_OPTION);
        if(option != JOptionPane.OK_OPTION) return;
        Product product = getProduct();
        if (_productsController.hasOrders(product.getId())) {
            String message = "O produto possui pedidos vinculados, "
                + "deseja desativar o produto em vez de remover?";
            option = JOptionPane.showConfirmDialog(this, message, "Excluir", JOptionPane.YES_NO_OPTION);
            if(option != JOptionPane.OK_OPTION) return;
            product.setActive(false);
            _productsController.changeActive(product.getId(), false);
            editRowProductTable(product);
            clearProductSelection();
            return;
        }
        
        Result result = _productsController.delete(product.getId());
        if (result.hasError()) {
            displayResult(result);
            return;        
        }
        
        removeRowProductTable(product);
        clearProductSelection();
    }//GEN-LAST:event_btn_trashActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        _productTableModel = (DefaultTableModel)tbl_list.getModel();                       
        
        productTableEvents();
        clearProductTable();
        clearProductSelection();
        
        ResultData<ArrayList<Product>> result = _productsController.getAll();
        if (result.hasError()){
            displayResult(Result.error(result.getErrorMessage()));
            return;
        }
        
        for (Product product : result.getData()) {
            addRowProductTable(product);
        }
    }//GEN-LAST:event_formWindowOpened

    private void btn_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clearActionPerformed
        clearProductSelection();
    }//GEN-LAST:event_btn_clearActionPerformed
     
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_clear;
    private javax.swing.JButton btn_save;
    private javax.swing.JButton btn_trash;
    private javax.swing.JCheckBox chk_active;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lbl_description;
    private javax.swing.JLabel lbl_name;
    private javax.swing.JLabel lbl_price;
    private javax.swing.JScrollPane list_scroll;
    private javax.swing.JPanel pnl_actions;
    private javax.swing.JPanel pnl_fields;
    private javax.swing.JTable tbl_list;
    private javax.swing.JTextArea txt_description;
    private java.awt.TextField txt_name;
    private javax.swing.JFormattedTextField txt_price;
    private javax.swing.JScrollPane txt_scroll;
    // End of variables declaration//GEN-END:variables
}
