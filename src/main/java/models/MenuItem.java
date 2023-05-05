/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import javafx.scene.image.Image;

/**
 *
 * @author ahmad
 */
public class MenuItem {
    
    private String name;
    private double price;
    private String ingredients;
    private Image image;

    public MenuItem(String name, double price, String ingredients, Image image) {
        this.name = name;
        this.price = price;
        this.ingredients = ingredients;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getIngredients() {
        return ingredients;
    }

    public Image getImage() {
        return image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setImage(Image image) {
        this.image = image;
    }
    
}
