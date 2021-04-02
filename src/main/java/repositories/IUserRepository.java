/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repositories;

import entities.User;

/**
 *
 * @author Paulo
 */
public interface IUserRepository extends IRepository<User> {
    boolean existByLogin(String login);
    boolean existByLogin(String login, int id);
    boolean existByEmail(String email, int id);
    void changePassword(int id, String password, boolean isTemporary);
    User getByLogin(String login);
}
