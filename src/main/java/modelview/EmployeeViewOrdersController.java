/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelview;

import com.mycompany.mvvmexample.App;
import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

/**
 *
 * @author mikel
 */
public class EmployeeViewOrdersController {
    
    @FXML
    private Button generateOrders;
    
    @FXML
    private ListView viewOrderListView;
    
    @FXML
    private Button logOutButton;

    @FXML
    private Button shutDownButton;
    
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
    
}
