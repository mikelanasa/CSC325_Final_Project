/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelview;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.cloud.FirestoreClient;
import com.mycompany.mvvmexample.App;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import models.CustomerInfo;

/**
 *
 * @author mikel
 */
public class EmployeeContactCustomerController {
    
    @FXML
    private Button getCustomerInfo;
    
    @FXML
    private Button logOutButton;

    @FXML
    private Button shutDownButton;
    
    @FXML
    private Text customerName;
    
    @FXML
    private Text customerPhone;
    
    @FXML
    private TextField emailTextField;
    
    private String name;
    private String phone;
    static UserRecord currentUser;
    private CustomerInfo currentCustomer;
    
    
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
    private void handleButton_ShowInfo() {
        try {
            String email = emailTextField.getText();
            currentUser = FirebaseAuth.getInstance().getUserByEmail(email);
            currentCustomer = getContactInfo(currentUser);
            customerName.setText(currentCustomer.getName());
            customerPhone.setText(currentCustomer.getPhone());
            
        } catch (FirebaseAuthException ex) {
            Logger.getLogger(EmployeeContactCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private CustomerInfo getContactInfo(UserRecord u) {
        ApiFuture<QuerySnapshot> querySnapshot = getUserInfoQuery(u);
        CustomerInfo customer = new CustomerInfo();
        try {
            for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
                customer.setFirstName(document.get("firstName").toString());
                customer.setLastName(document.get("lastName").toString());
                customer.setEmail(document.get("email").toString());
                customer.setPhone(document.get("phoneNumber").toString());
            }
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(SigninController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return customer;
    }
    
    private ApiFuture<QuerySnapshot> getUserInfoQuery(UserRecord u) {
        String userEmail = u.getEmail();
        Firestore db = FirestoreClient.getFirestore();
        Query query = db.collection("User")
                .whereEqualTo("email", userEmail).limit(1);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        return querySnapshot;
    }
    
}
