/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelview;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import com.mycompany.mvvmexample.App;
import models.MenuItem;
import viewmodel.MenuItemView;

import java.io.FileNotFoundException;
import java.io.IOException;
import static java.lang.Double.parseDouble;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    List<MenuItem> menuItems = new ArrayList<MenuItem>();

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
                
                menuItems.add(getMenuItems().get(i));
                //Image image = new Image(getClass().getResourceAsStream("/com/mycompany/mvvmexample/bakerylogo.png"));
                //MenuItem NewItem = new MenuItem(name, price, description, image);
                //menuItems.add(NewItem);  
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
    
    private List<MenuItem> getMenuItems() {
        
        //List<MenuItem> menuItems = new ArrayList<MenuItem>();
        
        try {
            Firestore db = FirestoreClient.getFirestore();
            // Create a reference to the cities collection
            //CollectionReference menuItem = db.collection("MenuItem");
            
            // asynchronously retrieve all documents
            ApiFuture<QuerySnapshot> future = db.collection("MenuItem").get();
            // future.get() blocks on response
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                Image image = new Image(document.get("image").toString());
                String name = document.get("name").toString();
                double price = parseDouble(document.get("price").toString());
                String description = document.get("description").toString();
                
                MenuItem m = new MenuItem(name, price, description, image);
                menuItems.add(m);
            }           
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return menuItems;
    }

}
