/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.util.List;

/**
 *
 * @author mikel
 */
public class CustomerOrder {
    
    private String email;
    public List<MenuItem> order;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<MenuItem> getOrder() {
        return order;
    }

    public void setOrder(List<MenuItem> order) {
        this.order = order;
    }
    
}
