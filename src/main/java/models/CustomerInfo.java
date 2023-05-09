/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author mikel
 */
public class CustomerInfo {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    public CustomerInfo(String f, String l, String e, String p) {
        this.firstName = f;
        this.lastName = l;
        this.email = e;
        this.phone = p;
    }
    
    public CustomerInfo() {
        this.firstName = null;
        this.lastName = null;
        this.email = null;
        this.phone = null;
    }

    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    public String getName() {
        return firstName + " " + lastName;
    }

    public void setFirstName(String name) {
        this.firstName = name;
    }
    
    public void setLastName(String name) {
        this.lastName = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String e) {
        this.email = e;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String p) {
        this.phone = p;
    }
    
    
    
}
