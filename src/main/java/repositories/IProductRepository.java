/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repositories;

import entities.Order;
import entities.Product;
import java.util.ArrayList;
import models.SelectOption;

/**
 *
 * @author Paulo
 */
public interface IProductRepository extends IRepository<Product> {
    ArrayList<SelectOption> getAllFlat();
    boolean hasOrders(int id);
    void changeActive(int id, boolean active);
}
