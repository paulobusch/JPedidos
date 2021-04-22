/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import enums.Role;
import jpedidos.Settings;
import utils.AESHash;

/**
 *
 * @author Paulo
 */
public class User extends EntityBase {
    private String name;
    private String login;
    private String password;
    public boolean passwordTemporary;
    private String email;
    private Role role;

    public boolean validatePassword(String password) {
        if (password == null || password.equals(""))
            return this.password == null;
        
        String hash = AESHash.encrypt(password, Settings.SecretKey);
        return hash.equals(this.password);
    }

    public void setRawPassword(String password) {
        if (password == null || password.equals("")) {
            this.password = null;
            return;
        }
        
        this.password = AESHash.encrypt(password, Settings.SecretKey);
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }
    
    public String getRoleString() {
        if (this.role == Role.Admin) return "Admin"; 
        if (this.role == Role.Gerente) return "Gerente"; 
        if (this.role == Role.Funcionario) return "Funcionário"; 
        
        return null;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setRole(String role) {
        if (role == null) {
            this.role = null;
            return;
        }
        
        switch (role) {
            case "Admin": 
                this.role = Role.Admin;
                return;
            case "Gerente": 
                this.role = Role.Gerente;
                return;
            case "Funcionário": 
                this.role = Role.Funcionario;
                return;
        }
        
        this.role = null;
    }

    public boolean isPasswordTemporary() {
        return passwordTemporary;
    }

    public void setPasswordTemporary(boolean passwordTemporary) {
        this.passwordTemporary = passwordTemporary;
    }
    
    
}
