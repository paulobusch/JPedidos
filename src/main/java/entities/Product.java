/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.regex.Pattern;

/**
 *
 * @author Paulo
 */
public class Product extends EntityBase {
    private String name;
    private String description;
    private boolean active;
    private double price;

    public Product() {
        active = true;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setPrice(String price) {
        this.price = Pattern.matches("^\\d+\\.?\\d+$", price)
            ? Double.parseDouble(price)
            : 0;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
}
