/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.regex.Pattern;
import utils.JPedidosException;

/**
 *
 * @author Paulo
 */
public class OrderProduct extends EntityBase {
    private int orderId;
    private int productId;
    private int amount;
    
    private Order order;
    private Product product;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setAmount(String amount) {
        this.amount = Pattern.matches("^\\d+$", amount)
            ? Integer.parseInt(amount)
            : 0;
    }

    public void addAmount(int amount) {
        this.amount += amount;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.orderId = order == null ? 0 : order.getId();
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.productId = product == null ? 0 : product.getId();
        this.product = product;
    }
    
    public double computeTotal() {
        if (product == null)
            throw new JPedidosException("O produto deve ser carregado para cálculo do preço!");
        
        return amount * product.getPrice();
    }
}
