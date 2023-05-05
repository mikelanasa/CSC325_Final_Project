/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelview;

import com.mycompany.mvvmexample.App;
import models.MenuItem;
import viewmodel.MenuItemView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

public class MenuController {

    @FXML
    private Button logOutButton;

    @FXML
    private GridPane MenuGrid;

    @FXML
    private Button shutDownButton;

    @FXML
    private Button AddtoOrder;

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("logInPage.fxml");
    }
    
    @FXML
    private void shutDownApp() {
        Platform.exit();
    }

    @FXML
    void initialize() throws FileNotFoundException {
        List<MenuItem> menuItems = new ArrayList<MenuItem>();

       
        for (int i = 0; i < 3; i++) {
            Image image = new Image(getClass().getResourceAsStream("/com/mycompany/mvvmexample/bakerylogo.png"));
            MenuItem NewItem = new MenuItem("Cake", 20.0, "sdsdsd", image);
            menuItems.add(NewItem);
        }
        MenuDisplay(menuItems);

    }

    private void MenuDisplay(List<MenuItem> menuItems) {

        for (int i = 0; i < menuItems.size(); i++) {
            MenuItem menuItem = menuItems.get(i);
            MenuItemView menuItemView = new MenuItemView(menuItem);
            MenuGrid.add(menuItemView, i % 3, i / 3);
            //System.out.println(1%3);
            //System.out.println(1/3);
            //AddtoOrder = new Button("Add to Order");

        }
    }

}
