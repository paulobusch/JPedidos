/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repositories;

import entities.Order;
import java.util.ArrayList;

/**
 *
 * @author Paulo
 */
public interface IOrderRepository extends IRepository<Order> {
    double getTotalPrice(int id);
}
