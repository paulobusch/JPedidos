/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import controllers.OrdersController;
import entities.Order;
import entities.Product;
import entities.User;
import enums.OrderStatus;
import enums.Role;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import java.text.SimpleDateFormat;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import utils.Result;
import utils.ResultData;

/**
 *
 * @author Paulo
 */
public class OrderList extends javax.swing.JFrame {

    /**
     * Creates new form OrderList
     */
    private OrdersController _ordersController;
    
    private Order _orderOpenedCurrent;
    private DefaultTableModel _ordersOpenedTableModel;
    private DefaultTableModel _ordersClosedTableModel;
    
    public OrderList(OrdersController ordersController) {
        initComponents();
        _ordersController = ordersController;

        User currentUser = _ordersController.getCurrentUser();
        updateTitle();
        toggleFieldsByRole(currentUser.getRole());
    }
    
    private void updateTitle() {
        User currentUser = _ordersController.getCurrentUser();
        setTitle("JPedidos - Gerenciar Pedidos (" + currentUser.getRoleString() + ")");
    }
    
    private void toggleFieldsByRole(Role role) {
       if (role != Role.Funcionario) {
           pnl_actions.setVisible(false);
       } 
    }
    
    private void setOrderOpened(Order order) {
        _orderOpenedCurrent = order;
        btn_close_order.setEnabled(order != null);
    }
    
    private void displayResult(Result result) {
        JOptionPane.showMessageDialog(this, result.getErrorMessage(), "Informação", JOptionPane.INFORMATION_MESSAGE);
    }
        
    private void orderOpenedTableEvents() {
        ListSelectionModel model = tbl_list_opened.getSelectionModel();
        model.addListSelectionListener(new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = tbl_list_opened.getSelectedRow();
                if (row == -1) {
                    setOrderOpened(null);
                    return;
                }
                int id = getTableIdByIndex(_ordersOpenedTableModel, row);
                ResultData<Order> result = _ordersController.getById(id);
                if (result.hasError()){
                    displayResult(Result.error(result.getErrorMessage()));
                    return;
                }
                setOrderOpened(result.getData());
            }
        });
    }
    
    private void clearOrderOpenedTable() {
        _ordersOpenedTableModel.setRowCount(0);
    }
    
    private void clearOrderClosedTable() {
        _ordersClosedTableModel.setRowCount(0);
    }
    
    private void clearOrderOpenedSelection() {
        tbl_list_opened.clearSelection();
        setOrderOpened(null);  
    }
    
    private int getTableIdByIndex(DefaultTableModel model, int index) {
        Object raw = model.getValueAt(index, 0);
        return Integer.parseInt(raw.toString());
    }    
    
    private ArrayList<Object> getOrderOpenedColumnsData(Order order) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        ArrayList<Object> columns = new ArrayList<>();
        columns.add(order.getId());
        columns.add(order.getCustomer().getName());
        columns.add(order.getCustomer().getPhone());
        columns.add(String.format("%.2f", order.getTotal()).replace('.', ','));
        columns.add(df.format(order.getOpenDate()));
        return columns;
    }
    
    private ArrayList<Object> getOrderClosedColumnsData(Order order) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        ArrayList<Object> columns = new ArrayList<>();
        columns.add(order.getId());
        columns.add(order.getCustomer().getName());
        columns.add(order.getCustomer().getPhone());
        columns.add(String.format("%.2f", order.getTotal()).replace('.', ','));
        columns.add(df.format(order.getOpenDate()));
        columns.add(df.format(order.getCloseDate()));
        return columns;
    }
    
    private void addRowOrderOpenedTable(Order order) {
        ArrayList<Object> columnsData = getOrderOpenedColumnsData(order);
        _ordersOpenedTableModel.addRow(columnsData.toArray());
    }
    
    private void addRowOrderClosedTable(Order order) {
        ArrayList<Object> columnsData = getOrderClosedColumnsData(order);
        _ordersClosedTableModel.addRow(columnsData.toArray());
    }
    
    private void removeRowOrderOpenedTable(Order order) {
        int index = getRowIndexWithId(_ordersOpenedTableModel, order.getId());
        _ordersOpenedTableModel.removeRow(index);
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
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabs = new javax.swing.JTabbedPane();
        tab_orders_opened = new javax.swing.JPanel();
        pnl_actions = new javax.swing.JPanel();
        btn_close_order = new javax.swing.JButton();
        pnl_list = new javax.swing.JPanel();
        scroll_list_opened = new javax.swing.JScrollPane();
        tbl_list_opened = new javax.swing.JTable();
        tab_orders_closed = new javax.swing.JPanel();
        scroll_list_closed = new javax.swing.JScrollPane();
        tbl_list_closed = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        btn_close_order.setIcon(new javax.swing.ImageIcon("C:\\Users\\Paulo\\Desktop\\Cursos\\UTFPR\\7º Semestre\\Oficina de Integração 2\\JPedidos\\src\\main\\java\\assets\\close-48.png")); // NOI18N
        btn_close_order.setEnabled(false);
        btn_close_order.setName("btn_add"); // NOI18N
        btn_close_order.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_close_orderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_actionsLayout = new javax.swing.GroupLayout(pnl_actions);
        pnl_actions.setLayout(pnl_actionsLayout);
        pnl_actionsLayout.setHorizontalGroup(
            pnl_actionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_actionsLayout.createSequentialGroup()
                .addContainerGap(893, Short.MAX_VALUE)
                .addComponent(btn_close_order, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );
        pnl_actionsLayout.setVerticalGroup(
            pnl_actionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_actionsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_close_order, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tab_orders_opened.add(pnl_actions);

        tbl_list_opened.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nome do Cliente", "Telefone do Cliente", "Valor Total", "Data/Hora de Abertura"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        scroll_list_opened.setViewportView(tbl_list_opened);

        javax.swing.GroupLayout pnl_listLayout = new javax.swing.GroupLayout(pnl_list);
        pnl_list.setLayout(pnl_listLayout);
        pnl_listLayout.setHorizontalGroup(
            pnl_listLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scroll_list_opened, javax.swing.GroupLayout.DEFAULT_SIZE, 952, Short.MAX_VALUE)
        );
        pnl_listLayout.setVerticalGroup(
            pnl_listLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_listLayout.createSequentialGroup()
                .addComponent(scroll_list_opened, javax.swing.GroupLayout.PREFERRED_SIZE, 489, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        tab_orders_opened.add(pnl_list);

        tabs.addTab("Pedidos Abertos", tab_orders_opened);

        tbl_list_closed.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nome do Cliente", "Telefone do Cliente", "Valor Total", "Data/Hora de Abertura", "Data/Hora de Fechamento"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        scroll_list_closed.setViewportView(tbl_list_closed);

        javax.swing.GroupLayout tab_orders_closedLayout = new javax.swing.GroupLayout(tab_orders_closed);
        tab_orders_closed.setLayout(tab_orders_closedLayout);
        tab_orders_closedLayout.setHorizontalGroup(
            tab_orders_closedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scroll_list_closed, javax.swing.GroupLayout.PREFERRED_SIZE, 952, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        tab_orders_closedLayout.setVerticalGroup(
            tab_orders_closedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scroll_list_closed, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE)
        );

        tabs.addTab("Pedidos Fechados", tab_orders_closed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabs, javax.swing.GroupLayout.DEFAULT_SIZE, 954, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 463, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        _ordersOpenedTableModel = (DefaultTableModel)tbl_list_opened.getModel();                     
        _ordersClosedTableModel = (DefaultTableModel)tbl_list_closed.getModel();                       
        
        orderOpenedTableEvents();
        clearOrderOpenedTable();
        clearOrderClosedTable();
        clearOrderOpenedSelection();
        
        ResultData<ArrayList<Order>> result = _ordersController.getAll();
        if (result.hasError()){
            displayResult(Result.error(result.getErrorMessage()));
            return;
        }
        
        for (Order order : result.getData()) {
            if (order.getStatus() == OrderStatus.Open)
                addRowOrderOpenedTable(order);
            if (order.getStatus() == OrderStatus.Close)
                addRowOrderClosedTable(order);
        }
    }//GEN-LAST:event_formWindowOpened

    private void btn_close_orderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_close_orderActionPerformed
        int option = JOptionPane.showConfirmDialog(this, "Deseja fechar o pedido?", "Fechar Pedido", JOptionPane.YES_NO_OPTION);
        if(option != JOptionPane.OK_OPTION) return;
        _orderOpenedCurrent.closeOrder();
        Result result = _ordersController.update(_orderOpenedCurrent);
        if (result.hasError()) {
            displayResult(result);
            return;
        }

        addRowOrderClosedTable(_orderOpenedCurrent);
        removeRowOrderOpenedTable(_orderOpenedCurrent);
        clearOrderOpenedSelection();
    }//GEN-LAST:event_btn_close_orderActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_close_order;
    private javax.swing.JPanel pnl_actions;
    private javax.swing.JPanel pnl_list;
    private javax.swing.JScrollPane scroll_list_closed;
    private javax.swing.JScrollPane scroll_list_opened;
    private javax.swing.JPanel tab_orders_closed;
    private javax.swing.JPanel tab_orders_opened;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tbl_list_closed;
    private javax.swing.JTable tbl_list_opened;
    // End of variables declaration//GEN-END:variables
}
