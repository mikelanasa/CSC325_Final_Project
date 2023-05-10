/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package modelview;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.mycompany.mvvmexample.App;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author erickcruz
 */
public class EmployeeUpdateOrderStatusController {

    @FXML
    private TextField orderIdTextField;
    @FXML
    private TextField statusUpdateTextField;

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
    private void updateOrderStatus() throws ExecutionException {
        String orderId = orderIdTextField.getText();
        String updatedStatus = statusUpdateTextField.getText(); // Assuming you have a TextField named "statusTextField"

        if (orderId.isBlank() || orderId.isEmpty()) {
            showAlert("Enter a valid order id");
        } else if (updatedStatus.isBlank() || updatedStatus.isEmpty()) {
            showAlert("Enter a valid status message");
        } else {
            // Retrieve the document from Firestore
            DocumentReference docRef = FirestoreClient.getFirestore().collection("Orders").document(orderId);
            ApiFuture<DocumentSnapshot> future = docRef.get();

            try {
                // Get the document snapshot
                DocumentSnapshot document = future.get();

                if (document.exists()) {
                    // Document with the provided ID exists

                    // Update the order status field
                    Map<String, Object> updates = new HashMap<>();
                    updates.put("status", updatedStatus);

                    ApiFuture<WriteResult> updateResult = docRef.update(updates);
                    updateResult.get();
                    // Perform further actions or display a confirmation message
                    showAlert("Order with ID " + orderId + " exists. Order status updated...");

                    // Additional actions...
                } else {
                    // Document with the provided ID does not exist
                    showAlert("Order with ID " + orderId + " does not exist.");
                }
            } catch (InterruptedException | ExecutionException e) {
                // Error occurred while retrieving the document
                showAlert("Error occurred while updating the order status. Please try again.");
            }

        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.setTitle("Order ID");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

}
