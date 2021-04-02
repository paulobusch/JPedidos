/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repositories;

import entities.Customer;
import java.util.ArrayList;
import models.SelectOption;

/**
 *
 * @author Paulo
 */
public interface ICustomerRepository extends IRepository<Customer> {
    ArrayList<SelectOption> getAllFlat();
}
