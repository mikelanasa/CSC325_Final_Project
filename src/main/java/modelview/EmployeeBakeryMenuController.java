/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package modelview;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import com.mycompany.mvvmexample.App;
import java.io.FileNotFoundException;
import java.io.IOException;
import static java.lang.Double.parseDouble;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import models.MenuItem;
import viewmodel.MenuItemView;

/**
 * FXML Controller class
 *
 * @author erickcruz
 */
public class EmployeeBakeryMenuController {

    
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
    private void switchToHome() throws IOException {
        App.setRoot("EmployeeMenu.fxml");
    }
    
    
    @FXML
    private void shutDownApp() {
        Platform.exit();
    }

   @FXML
    void initialize() throws FileNotFoundException {

        //List<MenuItem> menuItems = new ArrayList<MenuItem>();
        menuItems = getMenuItems();
        MenuDisplay(menuItems.subList(0, Math.min(9, menuItems.size())));
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
        CountDownLatch latch = new CountDownLatch(1);

        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection("MenuItem").get();

        future.addListener(() -> {
            try {
                List<QueryDocumentSnapshot> documents = future.get().getDocuments();
                for (QueryDocumentSnapshot document : documents) {
                    Image image = new Image(document.get("image").toString());
                    String name = document.get("name").toString();
                    double price = Double.parseDouble(document.get("price").toString());
                    String description = document.get("description").toString();

                    MenuItem m = new MenuItem(name, price, description, image);
                    menuItems.add(m);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(CustomerBakeryMenuController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                Logger.getLogger(CustomerBakeryMenuController.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                latch.countDown(); // Signal that the task is complete
            }
        }, ForkJoinPool.commonPool()); // Use the common fork-join pool as the executor

        try {
            latch.await(); // Wait for the task to complete
        } catch (InterruptedException e) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, e);
            Thread.currentThread().interrupt();
        }

        return menuItems;
    } // ends getMenuItems

}
