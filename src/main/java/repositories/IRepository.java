/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repositories;

import entities.EntityBase;
import java.util.ArrayList;

/**
 *
 * @author Paulo
 */
public interface IRepository<TEntity extends EntityBase> {
    TEntity getById(int id);
    ArrayList<TEntity> getAll();
    void create(TEntity entity);
    void update(TEntity entity);
    void delete(TEntity entity);
}
