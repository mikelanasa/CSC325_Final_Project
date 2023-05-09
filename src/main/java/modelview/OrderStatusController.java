/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelview;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.shape.Circle;
import javafx.scene.image.Image;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mycompany.mvvmexample.App;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import static modelview.SigninController.currentUser;

/**
 *
 * @author ahmad
 */
public class OrderStatusController {

    @FXML
    private ProgressBar step1ProgressBar;
    @FXML
    private ProgressBar step2ProgressBar;
    @FXML
    private Circle OrderPlacedPic;
    @FXML
    private Circle OrderBakedPic;
    @FXML
    private Circle OrderReadyPic;
    
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

    public OrderStatusController() {
    }

    public void initialize() {
        displayPicOnTimeline();
        startOrderProcessing();
        
        currentUser = SigninController.currentUser;
        //RetrieveStoredTime(currentUser);
    }

    public void displayPicOnTimeline() {
        Image image1 = new Image("/com/mycompany/mvvmexample/image1.png", false);
        // Set the image to the corresponding Circle using ImageView
        OrderPlacedPic.setFill(new javafx.scene.paint.ImagePattern(image1));
        image1 = new Image("/com/mycompany/mvvmexample/image2.png", false);
        OrderBakedPic.setFill(new javafx.scene.paint.ImagePattern(image1));
        image1 = new Image("/com/mycompany/mvvmexample/image3.png", false);
        OrderReadyPic.setFill(new javafx.scene.paint.ImagePattern(image1));
        
    }

    private void startOrderProcessing() {
        int orderProcessingTime1 = 50000;
        int orderProcessingTime2 = 30000;

        // Create a task for the first order
        Task<Void> order1Task = createOrderTask(orderProcessingTime1);
        step1ProgressBar.progressProperty().bind(order1Task.progressProperty());

        // Start the first task and create a handler to start the second task when the first task is done
        order1Task.setOnSucceeded(event -> {
            Task<Void> order2Task = createOrderTask(orderProcessingTime2);
            step2ProgressBar.progressProperty().bind(order2Task.progressProperty());
            new Thread(order2Task).start();
        });

        // Start the first task
        new Thread(order1Task).start();
    }

    private Task<Void> createOrderTask(int processingTime) {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                for (int i = 0; i < processingTime / 100; i++) {
                    Thread.sleep(100);
                    updateProgress(i + 1, processingTime / 100);
                }
                return null;
            }
        };
    }
    
    
   
    /*
    public long  RetrieveStoredTime(UserRecord u){
        
       
    }
    */
    public void StoreTime(UserRecord u){
        
    }

}
