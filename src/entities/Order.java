/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import static enums.CrudFunctionality.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Paulo
 */
public class Order extends EntityBase {
    private int customerId;
    private int userId;
    private Date date;
    
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    public List<OrderProduct> getOrderProducts() {
        return Collections.unmodifiableList(orderProducts);
    }

    public void addOrderProduct(OrderProduct orderProduct) {
        orderProducts.add(orderProduct);
    }

    public void removeProduct(Product product) {
        int index = indexOfOrderProductById(product.getId());
        orderProducts.remove(index);
    }

    public void updateProduct(OrderProduct orderProduct) {
        int index = indexOfOrderProductById(orderProduct.getId());
        OrderProduct oldOrderProduct = orderProducts.get(index);
        oldOrderProduct.setProductId(orderProduct.getProductId());
    }
    
    private int indexOfOrderProductById(int id) {
        for (OrderProduct orderProduct : orderProducts) {
            if (orderProduct.getId() == id) 
                return orderProducts.indexOf(orderProduct);
        }
        
        return -1;
    }
}
