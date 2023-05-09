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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
    private String password;

    private boolean isEmployee = false;

    @FXML
    void handleButton_signIn(ActionEvent event) {
        Thread signInThread = new Thread(() -> {
            try {
                // get username input
                String email = emailTextField.getText();
                // get password input
                String pass = passwordTextField.getText();
                // compare input to firebase authentication
                currentUser = FirebaseAuth.getInstance().getUserByEmail(email);
                
                String userPass = getPassword(currentUser);
                System.out.println(userPass);
                if(pass.equals(userPass)) {
                    Platform.runLater(() -> {
                        if (checkRole(currentUser)) {
                            try {
                                App.setRoot("EmployeeMenu.fxml");
                            } catch (IOException ex) {
                                Logger.getLogger(SigninController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            try {
                                App.setRoot("CustomerMenu.fxml");
                            } catch (IOException ex) {
                            Logger.getLogger(SigninController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                }
                else {
                    Platform.runLater(() -> {
                    showAlert("Error signing in.\nCheck email and password and try again");
                });
                }
            } catch (FirebaseAuthException | IllegalArgumentException ex) {
                Platform.runLater(() -> {
                    
                    showAlert("Error signing in.\nCheck email and password and try again");
                });
            }
        });

        signInThread.start();
    }// ends sign in button handler

    @FXML
    void handleButton_signUp(ActionEvent event) throws IOException {
        App.setRoot("signUpPage.fxml");
    }

    public boolean checkRole(UserRecord u) {
      
        ApiFuture<QuerySnapshot> querySnapshot = getUserInfoQuery(u);
        try {
            for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
                role = document.get("role").toString();
                if (role.equals("employee")) {
                    isEmployee = true;
                }
            }
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(SigninController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isEmployee;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setTitle("Sign In Error");
        alert.setHeaderText(null);
        alert.showAndWait();
    }
    
    private String getPassword(UserRecord u) {
        ApiFuture<QuerySnapshot> querySnapshot = getUserInfoQuery(u);
        try {
            for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
                password = document.get("password").toString();
            }
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(SigninController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return password;
    }
    
    private ApiFuture<QuerySnapshot> getUserInfoQuery(UserRecord u) {
        String userEmail = u.getEmail();
        Firestore db = FirestoreClient.getFirestore();
        Query query = db.collection("User")
                .whereEqualTo("email", userEmail).limit(1);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        return querySnapshot;
    }
    
    public String getLogTime(UserRecord userRecord) {
        String logTime = null;
        ApiFuture<QuerySnapshot> querySnapshot = getUserInfoQuery(userRecord);
        try {
            for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
                logTime = document.getString("logTime");
            }
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(SigninController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return logTime;
    }

}
