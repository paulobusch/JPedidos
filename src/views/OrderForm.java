/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import controllers.OrdersController;
import entities.Order;
import entities.OrderProduct;
import entities.User;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import models.SelectOption;
import utils.Result;
import utils.ResultData;
import validators.OrderProductValidator;

/**
 *
 * @author Paulo
 */
public class OrderForm extends javax.swing.JFrame {

    /**
     * Creates new form OrderForm
     */
    private OrdersController _ordersController;
    private OrderProductValidator _orderProductValidator;
    
    private Order _orderCurrent = new Order();
    private OrderProduct _orderProductCurrent;
    
    private List<SelectOption> _customers;
    private List<SelectOption> _products;
    
    private DefaultTableModel _orderTableModel;
    private DefaultTableModel _orderProductTableModel;
    
    public OrderForm(
        OrdersController ordersController
    ) {
        initComponents();
        
        _ordersController = ordersController;
        _orderProductValidator = ordersController.getValidator().getOrderProductValidator();
        
        loadSelectFields();
        updateTitle();
        
    }
    
    private void updateTitle() {
        User currentUser = _ordersController.getCurrentUser();
        setTitle("JPedidos - Pedidos (" + currentUser.getRoleString() + ")");
    }
    
    private Order getOrder() {
        Order order = _orderCurrent;
        
        SelectOption customerSelected = cb_customer.getSelectedItem() != null
            ? (SelectOption)cb_customer.getSelectedItem()
            : new SelectOption();
        order.setCustomerId(customerSelected.value);
        order.setDate(new Date());
        
        return order;
    }
    
    private void setOrder(Order order) {
        _orderCurrent = order;
        cb_customer.getModel().setSelectedItem(getCustomerSelected(order.getCustomerId()));
        
        clearOrderProductTable();
        for (OrderProduct op : order.getOrderProducts())
            addRowOrderProductTable(op);
    }
    
    private SelectOption getCustomerSelected(int id) {
        for (SelectOption so : _customers) {
            if (so.value == id) return so;
        }
        
        return new SelectOption();
    }
    
    private SelectOption getProductSelected(int id) {
        for (SelectOption so : _products) {
            if (so.value == id) return so;
        }
        
        return new SelectOption();
    }
    
    private OrderProduct getOrderProduct() {
        OrderProduct orderProduct = _orderProductCurrent == null
            ? new OrderProduct()
            : _orderProductCurrent; 
        
        orderProduct.setOrderId(_orderCurrent.getId());
        SelectOption productSelected = cb_product.getSelectedItem() != null
            ? (SelectOption)cb_product.getSelectedItem()
            : new SelectOption();
        orderProduct.setProductId(productSelected.value);
        orderProduct.setAmount(txt_product_amount.getText());
        
        return orderProduct;
    }
    
    private void setOrderProduct(OrderProduct orderProduct) {
        _orderProductCurrent = orderProduct;
        
        cb_product.getModel().setSelectedItem(getProductSelected(orderProduct.getProductId()));
        txt_product_amount.setText(String.valueOf(orderProduct.getAmount()));
    }
    
    private void loadSelectFields() {        
        cb_customer.removeAllItems();
        cb_product.removeAllItems();
        
        _customers = _ordersController.getCustomersFlat();
        _products = _ordersController.getProductsFlat();
        
        for (SelectOption customer : _customers)
            cb_customer.addItem(customer);        
        for (SelectOption product : _products)
            cb_product.addItem(product);
    }
    
    private void displayResult(Result result) {
        JOptionPane.showMessageDialog(this, result.getErrorMessage(), "Informação", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private OrderProduct incrementProductIfExist(int id, int productId, int amount) {
        for (OrderProduct op : _orderCurrent.getOrderProducts()) {
            if (op.getId() == id) continue;
            if (op.getProductId() == productId) {
                op.addAmount(amount);
                return op;
            }
        }
        return null;
    }
    
    private int getTableIdByIndex(DefaultTableModel model, int index) {
        Object raw = model.getValueAt(index, 0);
        return Integer.parseInt(raw.toString());
    }
    
    private void clearOrderTable() {
        _orderTableModel.setRowCount(0);
    }
    
    private void clearOrderProductTable() {
        _orderProductTableModel.setRowCount(0);
    }
    
    private void removeCellEditor(JTable table) {
        for (int c = 0; c < table.getColumnCount(); c++)
        {
            Class<?> col_class = table.getColumnClass(c);
            table.setDefaultEditor(col_class, null);
        }
    }
    
    private ArrayList<Object> getOrderColumnsData(Order order) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        ArrayList<Object> columns = new ArrayList<>();
        columns.add(order.getId());
        columns.add(getCustomerSelected(order.getCustomerId()).text);
        columns.add(order.getTotal());
        columns.add(df.format(order.getDate()));
        return columns;
    }
    
    private ArrayList<Object> getOrderProductColumnsData(OrderProduct orderProduct) {
        ArrayList<Object> columns = new ArrayList<>();
        columns.add(orderProduct.getId());
        columns.add(getProductSelected(orderProduct.getProductId()).text);
        columns.add(orderProduct.getAmount());
        return columns;
    }
    
    private void addRowOrderTable(Order order) {
        ArrayList<Object> columnsData = getOrderColumnsData(order);
        _orderTableModel.addRow(columnsData.toArray());
    }
    
    private void addRowOrderProductTable(OrderProduct orderProduct) {
        ArrayList<Object> columnsData = getOrderProductColumnsData(orderProduct);
        _orderProductTableModel.addRow(columnsData.toArray());
    }
    
    private void editRowOrderTable(Order order) {
        ArrayList<Object> columnsData = getOrderColumnsData(order);
        int row = getRowIndexWithId(_orderTableModel, order.getId());
        for (Object data : columnsData){
            int col = columnsData.indexOf(data);
            _orderTableModel.setValueAt(data, row, col);
        }
    }
    
    private void editRowOrderProductTable(OrderProduct orderProduct) {
        ArrayList<Object> columnsData = getOrderProductColumnsData(orderProduct);
        int row = getRowIndexWithId(_orderProductTableModel, orderProduct.getId());
        for (Object data : columnsData){
            int col = columnsData.indexOf(data);
            _orderProductTableModel.setValueAt(data, row, col);
        }
    }
    
    private void removeRowOrderTable(Order order) {
        int index = getRowIndexWithId(_orderTableModel, order.getId());
        _orderTableModel.removeRow(index);
    }
    
    private void removeRowOrderProductTable(OrderProduct orderProduct) {
        int index = getRowIndexWithId(_orderProductTableModel, orderProduct.getId());
        _orderProductTableModel.removeRow(index);
    }
    
    private int getRowIndexWithId(DefaultTableModel model, int id) {
        for (int row = 0; row < model.getRowCount(); row++){
            Object raw = model.getValueAt(row, 0);
            int rowId = Integer.parseInt(raw.toString());
            if (rowId == id) return row;
        }
        return -1;
    }
    
    private void orderTableEvents() {
        ListSelectionModel model = tbl_list_orders.getSelectionModel();
        model.addListSelectionListener(new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = tbl_list_orders.getSelectedRow();
                if (row == -1) {
                    setOrder(new Order());
                    return;
                }
                int id = getTableIdByIndex(_orderTableModel, row);
                ResultData<Order> result = _ordersController.getById(id);
                if (result.hasError()){
                    displayResult(Result.error(result.getErrorMessage()));
                    return;
                }
                btn_trash.setEnabled(true);
                setOrder(result.getData());
            }
        });
    }
        
    private void orderProductTableEvents() {
        ListSelectionModel model = tbl_list_order_products.getSelectionModel();
        model.addListSelectionListener(new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = tbl_list_order_products.getSelectedRow();
                if (row == -1) {
                    setOrderProduct(new OrderProduct());
                    return;
                }
                int id = getTableIdByIndex(_orderProductTableModel, row);
                for (OrderProduct orderProduct : _orderCurrent.getOrderProducts()) {
                    if (orderProduct.getId() == id) {
                        btn_trash_product.setEnabled(true);
                        setOrderProduct(orderProduct);
                        return;
                    }
                }
            }
        });
    }
    
    private void clearOrderSelection() {
        btn_trash.setEnabled(false);
        tbl_list_orders.clearSelection();
        setOrder(new Order());  
        clearOrderProductSelection();
    }
    
    private void clearOrderProductSelection() {
        btn_trash_product.setEnabled(false);
        tbl_list_order_products.clearSelection();
        setOrderProduct(new OrderProduct());  
    }
    
    private DefaultTableModel newTableModel(JTable table) {
        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };

        table.setModel(tableModel);
        
        return tableModel;
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
        tbl_list_orders = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_list_order_products = new javax.swing.JTable();
        pnl_actions1 = new javax.swing.JPanel();
        btn_save_product = new javax.swing.JButton();
        btn_trash_product = new javax.swing.JButton();
        btn_clear_product = new javax.swing.JButton();
        lbl_name2 = new javax.swing.JLabel();
        cb_product = new javax.swing.JComboBox();
        lbl_name3 = new javax.swing.JLabel();
        txt_product_amount = new javax.swing.JFormattedTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

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
                .addGap(36, 36, 36)
                .addComponent(lbl_name)
                .addGap(18, 18, 18)
                .addComponent(cb_customer, javax.swing.GroupLayout.PREFERRED_SIZE, 547, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addComponent(btn_add_customer, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );
        pnl_fieldsLayout.setVerticalGroup(
            pnl_fieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_fieldsLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(pnl_fieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_add_customer, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnl_fieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbl_name)
                        .addComponent(cb_customer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        btn_clear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/clean-48.png"))); // NOI18N
        btn_clear.setName("btn_clear"); // NOI18N
        btn_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clearActionPerformed(evt);
            }
        });

        btn_save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/save-all-48.png"))); // NOI18N
        btn_save.setName("btn_add"); // NOI18N
        btn_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveActionPerformed(evt);
            }
        });

        btn_trash.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/trash-48.png"))); // NOI18N
        btn_trash.setEnabled(false);
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
                .addGap(32, 32, 32)
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

        tbl_list_orders.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Cliente", "Preço", "Data"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tbl_list_orders.setName("tbl_list_orders"); // NOI18N
        list_scroll.setViewportView(tbl_list_orders);
        if (tbl_list_orders.getColumnModel().getColumnCount() > 0) {
            tbl_list_orders.getColumnModel().getColumn(0).setPreferredWidth(1);
        }

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Produtos"));

        tbl_list_order_products.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tbl_list_order_products);
        if (tbl_list_order_products.getColumnModel().getColumnCount() > 0) {
            tbl_list_order_products.getColumnModel().getColumn(0).setPreferredWidth(1);
        }

        btn_save_product.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/save-48.png"))); // NOI18N
        btn_save_product.setName("btn_add"); // NOI18N
        btn_save_product.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_save_productActionPerformed(evt);
            }
        });

        btn_trash_product.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/trash-48.png"))); // NOI18N
        btn_trash_product.setEnabled(false);
        btn_trash_product.setName("btn_add"); // NOI18N
        btn_trash_product.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_trash_productActionPerformed(evt);
            }
        });

        btn_clear_product.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/clean-48.png"))); // NOI18N
        btn_clear_product.setName("btn_clear"); // NOI18N
        btn_clear_product.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clear_productActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_actions1Layout = new javax.swing.GroupLayout(pnl_actions1);
        pnl_actions1.setLayout(pnl_actions1Layout);
        pnl_actions1Layout.setHorizontalGroup(
            pnl_actions1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_actions1Layout.createSequentialGroup()
                .addComponent(btn_clear_product, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 501, Short.MAX_VALUE)
                .addComponent(btn_trash_product, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_save_product, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnl_actions1Layout.setVerticalGroup(
            pnl_actions1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_actions1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_actions1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_clear_product, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_trash_product, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_save_product, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        lbl_name2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbl_name2.setText("Produto:");

        cb_product.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        lbl_name3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbl_name3.setText("Quantidade:");

        txt_product_amount.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat(""))));
        txt_product_amount.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnl_actions1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(lbl_name2)
                            .addGap(18, 18, 18)
                            .addComponent(cb_product, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_name3)
                            .addGap(16, 16, 16)
                            .addComponent(txt_product_amount, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 669, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
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
                    .addComponent(pnl_fields, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(list_scroll)
                            .addComponent(pnl_actions, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnl_fields, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnl_actions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(list_scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed
        Order order = getOrder();
        boolean byInsert = order.getId() == 0;
        Result result = byInsert
            ? _ordersController.create(order)
            : _ordersController.update(order);
        
        if (result.hasError()){
            displayResult(result);
            return;
        }
        
        if (byInsert) {
            addRowOrderTable(order);
        } else {
            editRowOrderTable(order);
        }
        clearOrderSelection();
    }//GEN-LAST:event_btn_saveActionPerformed

    private void btn_add_customerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_add_customerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_add_customerActionPerformed

    private void btn_save_productActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_save_productActionPerformed
        OrderProduct orderProduct = getOrderProduct();
        boolean byInsert = orderProduct.getId() == 0;
        Result result = _orderProductValidator.validate(orderProduct);
        if (result.hasError()){
            displayResult(result);
            return;
        }

        OrderProduct orderProductPersist = orderProduct;
        OrderProduct orderProductUpdated = incrementProductIfExist(orderProduct.getId(), orderProduct.getProductId(), orderProduct.getAmount());
        if (orderProductUpdated != null) {
            byInsert = false;
            orderProductPersist = orderProductUpdated;
        }     
        
        if (byInsert) {
            _orderCurrent.addOrderProduct(orderProductPersist);
            addRowOrderProductTable(orderProductPersist);
        } else {
            _orderCurrent.updateProduct(orderProductPersist);
            editRowOrderProductTable(orderProductPersist);
            if (orderProductUpdated != null && orderProduct.getId() != 0) {
                Order order = getOrder();
                order.removeProduct(orderProduct);
                removeRowOrderProductTable(orderProduct);
            }
        }
        clearOrderProductSelection();
    }//GEN-LAST:event_btn_save_productActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        _orderTableModel = (DefaultTableModel)tbl_list_orders.getModel();                       
        _orderProductTableModel = (DefaultTableModel)tbl_list_order_products.getModel();
        
        orderTableEvents();
        orderProductTableEvents();
        
        clearOrderTable();
        clearOrderProductTable();
        
        clearOrderSelection();
        
        ResultData<ArrayList<Order>> result = _ordersController.getAll();
        if (result.hasError()){
            displayResult(Result.error(result.getErrorMessage()));
            return;
        }
        
        for (Order order : result.getData()) {
            addRowOrderTable(order);
        }
    }//GEN-LAST:event_formWindowOpened

    private void btn_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clearActionPerformed
        clearOrderSelection();
    }//GEN-LAST:event_btn_clearActionPerformed

    private void btn_clear_productActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clear_productActionPerformed
        clearOrderProductSelection();
    }//GEN-LAST:event_btn_clear_productActionPerformed

    private void btn_trashActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_trashActionPerformed
        int option = JOptionPane.showConfirmDialog(this, "Deseja excluir o pedido?", "Excluir", JOptionPane.OK_CANCEL_OPTION);
        if(option != JOptionPane.OK_OPTION) return;
        Order order = getOrder();
        Result result = _ordersController.delete(order.getId());
        if (result.hasError()) {
            displayResult(result);
            return;        
        }
        
        removeRowOrderTable(order);
        clearOrderSelection();
    }//GEN-LAST:event_btn_trashActionPerformed

    private void btn_trash_productActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_trash_productActionPerformed
        int option = JOptionPane.showConfirmDialog(this, "Deseja excluir o produto do pedido?", "Excluir", JOptionPane.OK_CANCEL_OPTION);
        if(option != JOptionPane.OK_OPTION) return;
        Order order = getOrder();
        OrderProduct orderProduct = getOrderProduct();
        order.removeProduct(orderProduct);
        
        removeRowOrderProductTable(orderProduct);
        clearOrderProductSelection();
    }//GEN-LAST:event_btn_trash_productActionPerformed

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_add_customer;
    private javax.swing.JButton btn_clear;
    private javax.swing.JButton btn_clear_product;
    private javax.swing.JButton btn_save;
    private javax.swing.JButton btn_save_product;
    private javax.swing.JButton btn_trash;
    private javax.swing.JButton btn_trash_product;
    private javax.swing.JComboBox cb_customer;
    private javax.swing.JComboBox cb_product;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_name;
    private javax.swing.JLabel lbl_name2;
    private javax.swing.JLabel lbl_name3;
    private javax.swing.JScrollPane list_scroll;
    private javax.swing.JPanel pnl_actions;
    private javax.swing.JPanel pnl_actions1;
    private javax.swing.JPanel pnl_fields;
    private javax.swing.JTable tbl_list_order_products;
    private javax.swing.JTable tbl_list_orders;
    private javax.swing.JFormattedTextField txt_product_amount;
    // End of variables declaration//GEN-END:variables
}
