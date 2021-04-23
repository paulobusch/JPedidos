/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import enums.OrderStatus;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import utils.JPedidosException;

/**
 *
 * @author Paulo
 */
public class Order extends EntityBase {
    private int customerId;
    private int userId;
    private OrderStatus status;
    private Date openDate;
    private Date closeDate;
    
    private double total;
    private Customer customer;
    private ArrayList<OrderProduct> orderProducts;
    private User user;
    
    public Order() {
        orderProducts = new ArrayList<>();
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customerId = customer == null ? 0 : customer.getId();
        this.customer = customer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.userId = user == null ? 0 : user.getId();
        this.user = user;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public String getStatusString() {
        if (this.status == OrderStatus.Open) return "aberto";
        if (this.status == OrderStatus.Close) return "fechado";
        
        return null;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setStatus(String status) {
        switch(status) {
            case "aberto":
                this.status = OrderStatus.Open;
                return;
            case "fechado":
                this.status = OrderStatus.Close;
                return;
        }
        
        this.status = null;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }
    
    public List<OrderProduct> getOrderProducts() {
        return Collections.unmodifiableList(orderProducts);
    }

    public void addOrderProduct(OrderProduct orderProduct) {
        if (orderProduct.getId() == 0) {
            int maxId = orderProduct.getId();
            for (OrderProduct op : orderProducts) {
                if (op.getId() > maxId) 
                    maxId = op.getId();
            }

            orderProduct.setId(maxId + 1);
        }
        
        orderProducts.add(orderProduct);
    }

    public void removeProduct(OrderProduct orderProduct) {
        int index = indexOfOrderProductById(orderProduct.getId());
        if (index == -1) throw new JPedidosException("O produto deve existir no pedido para ser removido!");
        orderProducts.remove(index);
    }

    public void updateProduct(OrderProduct orderProduct) {
        int index = indexOfOrderProductById(orderProduct.getId());
        if (index == -1) throw new JPedidosException("O produto deve existir no pedido para ser atualizado!");
        OrderProduct oldOrderProduct = orderProducts.get(index);
        oldOrderProduct.setProductId(orderProduct.getProductId());
        oldOrderProduct.setAmount(orderProduct.getAmount());
    }
    
    private int indexOfOrderProductById(int id) {
        for (OrderProduct orderProduct : orderProducts) {
            if (orderProduct.getId() == id) 
                return orderProducts.indexOf(orderProduct);
        }
        
        return -1;
    }
    
    public double computeTotal() {
        if (orderProducts.size() == 0) return 0;
        double total = 0;
        for (OrderProduct op : orderProducts) {
            total += op.computeTotal();
        }
        
        return total;
    }
    
    public void closeOrder() {
        this.status = OrderStatus.Close;
        this.closeDate = new Date();
    }
}
