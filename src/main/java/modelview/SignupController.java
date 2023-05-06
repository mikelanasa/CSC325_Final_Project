/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelview;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.WriteResult;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.mycompany.mvvmexample.App;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
    private TextField firstNameTextField, lastNameTextField, emailTextField, phoneNumberTextField, verificationTextField;

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
        if (firstNameTextField.getText().isBlank()
                || lastNameTextField.getText().isBlank()
                || emailTextField.getText().isBlank()
                || phoneNumberTextField.getText().isBlank()
                || passwordTextField1.getText().isBlank()
                || passwordTextField2.getText().isBlank()) {
            showAlert("Error creating account.\nAll fields must be completed.");
        } else if (!passwordTextField1.getText().equals(passwordTextField2.getText())) {
            showAlert("Passwords do not match");
        } else if (passwordTextField1.getText().length() < 6) {
            showAlert("Password must be larger than 6 characters");
        } else {
            Thread signUpThread = new Thread(() -> {
                UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                        .setDisplayName(firstNameTextField.getText())
                        .setEmail(emailTextField.getText())
                        .setPassword(passwordTextField1.getText());
                try {
                    userRecord = App.fauth.createUser(request);
                    Platform.runLater(() -> {

                        addData();

                        // handleButton_signIn(event);
                    });
                } catch (FirebaseAuthException ex) {
                    Platform.runLater(() -> showAlert("Error creating account.\nInfo already taken or information incomplete"));
                }
            });

            signUpThread.start();
        }

    } // ends handleButton_signUp

    public void addData() {
        Platform.runLater(() -> {
            DocumentReference docRef = App.fstore.collection("User").document(UUID.randomUUID().toString());
            // Add document data using a hashmap
            Map<String, Object> data = new HashMap<>();
            data.put("firstName", firstNameTextField.getText());
            data.put("lastName", lastNameTextField.getText());
            data.put("email", emailTextField.getText());
            data.put("password", passwordTextField1.getText());
            data.put("phoneNumber", phoneNumberTextField.getText());
            if (verificationTextField.getText() != null && verificationTextField.getText().equals(employeeCode)) {
                data.put("role", "employee");
                showAlert("Successfully created new employee account");
            } else {
                data.put("role", "customer");
                showAlert("Successfully created new customer account");
            }
            // Asynchronously write data
            ApiFuture<WriteResult> result = docRef.set(data);
            result.addListener(() -> {
                // Clear all the text fields
                Platform.runLater(() -> {
                    clearTextFields();
                });
            }, MoreExecutors.directExecutor());
        });
    } // ends addData

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Account Creation");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void clearTextFields() {
        firstNameTextField.clear();
        lastNameTextField.clear();
        emailTextField.clear();
        phoneNumberTextField.clear();
        passwordTextField1.clear();
        passwordTextField2.clear();
        verificationTextField.clear();
    }

}
