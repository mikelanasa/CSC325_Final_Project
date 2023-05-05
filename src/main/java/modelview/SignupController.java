/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelview;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.mycompany.mvvmexample.App;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
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
public class SignupController {
    
    @FXML
    private Button button_signIn, button_signUp;

    @FXML
    private TextField  firstNameTextField, lastNameTextField, emailTextField, phoneNumberTextField, verificationTextField;

    @FXML
    private PasswordField passwordTextField1, passwordTextField2;
    
    static UserRecord userRecord;

    @FXML
    void handleButton_signIn(ActionEvent event) throws IOException {
        App.setRoot("logInPage.fxml");
    }
    
    private String employeeCode = "bakery";

    @FXML
    void handleButton_signUp(ActionEvent event) throws IOException {
        
        if (firstNameTextField.getText().isBlank() ||
            lastNameTextField.getText().isBlank() ||
            emailTextField.getText().isBlank() ||
            phoneNumberTextField.getText().isBlank() ||
            passwordTextField1.getText().isBlank() ||
            passwordTextField2.getText().isBlank()) {
                JOptionPane.showMessageDialog(null, "Error creating account.\nAll fields must be completed.");
        }
        if (passwordTextField1.getText() == null ? passwordTextField2.getText() != null : 
                !passwordTextField1.getText().equals(passwordTextField2.getText())) {
            JOptionPane.showMessageDialog(null, "Passwords do not match");
        }
        else {
            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setDisplayName(firstNameTextField.getText())
                .setEmail(emailTextField.getText())
                .setPassword(passwordTextField1.getText());
            try {
                userRecord = App.fauth.createUser(request);
                //System.out.println("Successfully created new user: " + userRecord.getUid());
                JOptionPane.showMessageDialog(null, "Successfully created new account");
            } catch (FirebaseAuthException ex) {
                JOptionPane.showMessageDialog(null, "Error creating account.\nInfo already taken or information incomplete");
            }
            
            addData();

            handleButton_signIn(event);
        }   
    } // ends handleButton_signUp
    
    public void addData() {

        DocumentReference docRef = App.fstore.collection("User").document(UUID.randomUUID().toString());
        // Add document data using a hashmap
        Map<String, Object> data = new HashMap<>();
        data.put("firstName", firstNameTextField.getText());
        data.put("lastName", lastNameTextField.getText());
        data.put("email", emailTextField.getText());
        data.put("password", passwordTextField1.getText());
        data.put("phoneNumber", phoneNumberTextField.getText());
        if(verificationTextField.getText() != null && verificationTextField.getText().equals(employeeCode)) {
            data.put("role", "employee");
        }
        else {
            data.put("role", "customer");
        }
        //asynchronously write data
        ApiFuture<WriteResult> result = docRef.set(data);
    } // ends addData
    
}
