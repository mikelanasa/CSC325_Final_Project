/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelview;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.FieldPath;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import com.mycompany.mvvmexample.App;
import java.io.FileNotFoundException;
import java.io.IOException;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.CustomerOrder;
import models.MenuItem;
import viewmodel.MenuItemView;
import viewmodel.OrderItemView;

/**
 *
 * @author mikel
 */
public class EmployeeViewOrdersController {
    
    @FXML
    private Button generateOrders;
    
    @FXML
    private ListView viewOrderListView;
    
    @FXML
    private Button logOutButton;

    @FXML
    private Button shutDownButton;
    
    List<MenuItem> menuItems = new ArrayList<MenuItem>();
    
    List<CustomerOrder> allOrders = new ArrayList<CustomerOrder>();
    
    List<String> orders = new ArrayList<String>();
    
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
    
    /*
    @FXML
    void initialize() throws FileNotFoundException {
        List<MenuItem> menuItems = new ArrayList<MenuItem>();

        for (int i = 0; i < 9; i++) {

            menuItems.add(getMenuItems().get(i));
            //Image image = new Image(getClass().getResourceAsStream("/com/mycompany/mvvmexample/bakerylogo.png"));
            //MenuItem NewItem = new MenuItem(name, price, description, image);
            //menuItems.add(NewItem);  
        }
        MenuDisplay(menuItems);

    }
    */
    
    @FXML
    private void showOrders() { 
        
        List<String> showEmails = getOrders();
        for(int i = 0; i < showEmails.size(); i++) {
            viewOrderListView.getItems().add(showEmails.get(i));
        }     
        
    }
    
    private List<String> getOrders() {
        CountDownLatch latch = new CountDownLatch(1);

        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection("Orders").get();

        future.addListener(() -> {
            try {
                List<QueryDocumentSnapshot> documents = future.get().getDocuments();
                for (QueryDocumentSnapshot document : documents) {
                    String order =  "email: " + document.get("email").toString() + ", item: " + document.get("item0").toString();
                    
                    
                    orders.add(order);
                }
            } catch (InterruptedException | ExecutionException ex) {
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

        return orders;
    }
}
