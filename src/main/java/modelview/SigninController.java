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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;

/**
 *
 * @author mikel
 */
public class SigninController {
    
    @FXML
    private Button loginButton, button_signUp;

    @FXML
    private TextField emailTextField;
    
    @FXML
    private PasswordField passwordTextField;
    
    static UserRecord currentUser;
    
    private String role;
    
    private boolean isEmployee = false;

    @FXML
    void handleButton_signIn(ActionEvent event) throws IOException {
        try {
            // get username input
            String email = emailTextField.getText();
            // get password input
            String pass = passwordTextField.getText();
            // compare input to firebase authentication
            currentUser = FirebaseAuth.getInstance().getUserByEmail(email);
            if(checkRole(currentUser))
                App.setRoot("EmployeeMenu.fxml");
            else
                App.setRoot("CustomerMenu.fxml");
            
        } catch (FirebaseAuthException | IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(null, "Error signing in.\nCheck email and password and try again");
        }
    } // ends sign in button handler

    @FXML
    void handleButton_signUp(ActionEvent event) throws IOException {
        App.setRoot("signUpPage.fxml");
    }
    
    public boolean checkRole(UserRecord u) {
             
        String userEmail = u.getEmail();
        Firestore db = FirestoreClient.getFirestore();
        Query query = db.collection("User")
                .whereEqualTo("email", userEmail).limit(1);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        try {
            for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
                role = document.get("role").toString();
                if(role.equals("employee"))
                    isEmployee = true;
            }
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(SigninController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isEmployee;
    }
    
}
