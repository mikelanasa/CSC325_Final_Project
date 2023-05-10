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
public class CustomerCancelOrderController {

    @FXML
    private TextField orderIdTextField;

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
    private void cancelTheOrder() throws ExecutionException {
        String orderId = orderIdTextField.getText();

        if (orderId.isBlank() || orderId.isEmpty()) {
            showAlert("Enter a valid order id");
        } else {
            // Retrieve the document from Firestore
            DocumentReference docRef = FirestoreClient.getFirestore().collection("Orders").document(orderId);
            ApiFuture<DocumentSnapshot> future = docRef.get();

            try {
                // Get the document snapshot
                DocumentSnapshot document = future.get();

                if (document.exists()) {
                    // Document with the provided ID exists

                    ApiFuture<WriteResult> deleteResult = docRef.delete();
                    deleteResult.get();
                    // Perform further actions or display a confirmation message
                    showAlert("Order with ID " + orderId + " exists. Cancellation in progress...");

                    // Additional actions...
                } else {
                    // Document with the provided ID does not exist
                    showAlert("Order with ID " + orderId + " does not exist.");
                }
            } catch (InterruptedException | ExecutionException e) {
                // Error occurred while retrieving the document
                showAlert("Error occurred while checking the order. Please try again.");
            }
        }

    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setTitle("Order ID");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

}
