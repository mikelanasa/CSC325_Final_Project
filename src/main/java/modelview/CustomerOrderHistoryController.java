/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package modelview;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.cloud.FirestoreClient;
import com.mycompany.mvvmexample.App;
import java.io.IOException;
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
import javafx.scene.control.ListView;
import static modelview.SigninController.currentUser;

/**
 * FXML Controller class
 *
 * @author erickcruz
 */
public class CustomerOrderHistoryController {

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
    private ListView viewOrderListView;
    
    List<String> emails = new ArrayList<String>();
    
    //static UserRecord currentUser;
    
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
        ApiFuture<QuerySnapshot> future = db.collection("Orders").whereEqualTo("email", currentUser.getEmail()).get();

        future.addListener(() -> {
            try {
                List<QueryDocumentSnapshot> documents = future.get().getDocuments();
                for (QueryDocumentSnapshot document : documents) {
                    String email =  "email: " + document.get("email").toString() + ", item: " + document.get("item0").toString();
                    
                    emails.add(email);
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

        return emails;
    }
    
}
