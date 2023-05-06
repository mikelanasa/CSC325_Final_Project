/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewmodel;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import models.MenuItem;

/**
 *
 * @author mikel
 */
public class OrderItemView extends VBox {
    
    @FXML
    private Label nameLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label quantityLabel;
    
     public OrderItemView(MenuItem menuItem) {
        
        nameLabel = new Label(menuItem.getName());
        nameLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 16));

        priceLabel = new Label(String.format("$%.2f", menuItem.getPrice()));
        priceLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, 14));

        quantityLabel = new Label(Integer.toString(menuItem.getQuantity()));
        quantityLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
        
        setAlignment(Pos.CENTER);
        setFillWidth(true);
        //setSpacing(5);
        getChildren().addAll(nameLabel, priceLabel, quantityLabel);
    }
    
}
