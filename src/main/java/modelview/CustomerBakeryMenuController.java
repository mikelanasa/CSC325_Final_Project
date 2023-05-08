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
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import models.MenuItem;
import viewmodel.MenuItemView;

/**
 * FXML Controller class
 *
 * @author erickcruz
 */
public class CustomerBakeryMenuController {

    private double totalPrice;

    @FXML
    private TabPane tabPane;

    @FXML
    private Button logOutButton;

    @FXML
    private GridPane MenuGrid;

    @FXML
    private Button shutDownButton;

    @FXML
    private Button addtoOrder;

    @FXML
    private Button clearCart;

    @FXML
    private GridPane CartGrid;

    List<MenuItem> menuItems = new ArrayList<MenuItem>();
    List<MenuItem> cartItems = new ArrayList<MenuItem>();

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("logInPage.fxml");
    }

    @FXML
    private void switchToHome() throws IOException {
        App.setRoot("CustomerMenu.fxml");
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
        CartGrid.setGridLinesVisible(true);
    }

    /*
    @FXML
    void initialize() throws FileNotFoundException {
        //List<MenuItem> menuItems = new ArrayList<MenuItem>();

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
            MenuItemView menuItemView = new MenuItemView(menuItem);

            // Add event handler to the image
            Image image = menuItem.getImage();
            ImageView imageView = menuItemView.getImageView();
            imageView.setOnMouseClicked(event -> addToCartGrid(menuItem));

            MenuGrid.add(menuItemView, i % 3, i / 3);
            //System.out.println(1%3);
            //System.out.println(1/3);
            //AddtoOrder = new Button("Add to Order");

        }
        CartGrid.setGridLinesVisible(true);
    }

    private List<MenuItem> getMenuItems() {
        List<MenuItem> menuItems = new ArrayList<>();
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
    }

    /*
    private List<MenuItem> getMenuItems() {
        // Create an Executor to run the code on a separate thread
        Executor executor = Executors.newSingleThreadExecutor();

        // Submit a task to the Executor to run getMenuItems on a separate thread
        executor.execute(() -> {
        try {
            Firestore db = FirestoreClient.getFirestore();
            // Create a reference to the cities collection
            //CollectionReference menuItem = db.collection("MenuItem");

            // asynchronously retrieve all documents
            ApiFuture<QuerySnapshot> future = db.collection("MenuItem").get();
             future.get(); //blocks on response
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
         });

        return menuItems;
    } // ends getMenuItems
     */
    private int cartColumn = 0; // Keeps track of the current column index in the CartGrid
    private int cartRow = 0; // Keeps track of the current row index in the CartGrid

    private void addToCartGrid(MenuItem menuItem) {
        // Create a new CartItemView with the selected item
        MenuItemView cartItemView = new MenuItemView(menuItem);

        // Add the CartItemView to the CartGrid
        CartGrid.add(cartItemView, cartColumn, cartRow);
        cartItems.add(menuItem);
        totalPrice = totalPrice + menuItem.getPrice();
        System.out.println(totalPrice);

        cartItemView.getImageView().setOnMouseClicked(event -> {
            CartGrid.getChildren().remove(cartItemView); // Remove the MenuItemView from the CartGrid
            cartItems.remove(cartItemView); // Remove the MenuItemView from the MenuItemViews list
        });

        // Increment the column index for the next item
        cartColumn++;

        // If we reach the third column, move to the next row and reset the column index
        if (cartColumn == 3) {
            cartColumn = 0;
            cartRow++;
        }

        // Add a quantity input field to the CartItemView
        //TextField quantityField = cartItemView
        //quantityField.setText("1"); // Set default quantity to 1
        // Add an event listener to the quantity input field
        // quantityField.textProperty().addListener((observable, oldValue, newValue) -> {
        //int quantity = Integer.parseInt(newValue);
        // updateCartItemQuantity(cartItemView, quantity);
        //});
        System.out.println(cartItems.toArray());
    }

    @FXML
    private void clearTheCart() {
        cartItems.clear();
        System.out.println("cleared");
        //CartGrid.getChildren().retainAll(lineslayer);
        CartGrid.getChildren().clear();
        cartColumn = 0;
        cartRow = 0;
        CartGrid.setGridLinesVisible(true);
        totalPrice = 0;
    }

   

}
