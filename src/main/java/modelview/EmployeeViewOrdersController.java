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
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
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
    
    private void MenuDisplay(List<MenuItem> menuItems) {

        for (int i = 0; i < menuItems.size(); i++) {
            MenuItem menuItem = menuItems.get(i);
            OrderItemView orderItemView = new OrderItemView(menuItem);
            viewOrderListView.getItems().add(orderItemView);
            //System.out.println(1%3);
            //System.out.println(1/3);
            //AddtoOrder = new Button("Add to Order");

        }
    }
    
    /*
    private List<MenuItem> getMenuItems() {
        // Create an Executor to run the code on a separate thread
        //Executor executor = Executors.newSingleThreadExecutor();

        // Submit a task to the Executor to run getMenuItems on a separate thread
        //executor.execute(() -> {
        try {
            Firestore db = FirestoreClient.getFirestore();
            // Create a reference to the cities collection
            //CollectionReference menuItem = db.collection("MenuItem");

            // asynchronously retrieve all documents
            ApiFuture<QuerySnapshot> future = db.collection("Orders").get();
            // future.get() blocks on response
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                
                String name = document.get(FieldPath.of("OrderItems[0]")).toString();
                double price = parseDouble(document.get(FieldPath.of("OrderItems[2]")).toString());
                int quantity = parseInt(document.get(FieldPath.of("OrderItems[1]")).toString());

                MenuItem m = new MenuItem(name, price, quantity);
                menuItems.add(m);
            }
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        // });

        return menuItems;
    } // ends getMenuItems
    */
}
