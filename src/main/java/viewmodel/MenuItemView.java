/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewmodel;

import models.MenuItem;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Custom Container for displaying the Menu 
 * @author ahmad
 */
public class MenuItemView extends VBox {
    @FXML
    private ImageView imageView;
    @FXML
    private Label nameLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label ingredientsLabel;
    

    public MenuItemView(MenuItem menuItem) {
        System.out.println("Image set");
        imageView = new ImageView(menuItem.getImage());
        
        imageView.setFitWidth(150);
        imageView.setFitHeight(100);

        nameLabel = new Label(menuItem.getName());
        nameLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 16));

        priceLabel = new Label(String.format("$%.2f", menuItem.getPrice()));
        priceLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, 14));

        ingredientsLabel = new Label(String.join(", ", menuItem.getIngredients()));
        ingredientsLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
        ingredientsLabel.setMaxWidth(200);
        ingredientsLabel.setWrapText(true);
        setAlignment(Pos.CENTER);
        setFillWidth(true);
        //setSpacing(5);
        getChildren().addAll(imageView, nameLabel, priceLabel, ingredientsLabel);
    }
    
    public ImageView getImageView() {
        return imageView;
    }
    
    

}
