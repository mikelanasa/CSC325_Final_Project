/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelview;
/**
 *
 * @author cyril
 */
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import com.mycompany.mvvmexample.App;

public class EmployeeMenuController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button bakeryBttn;

    @FXML
    private Button contactBttn;

    @FXML
    private Button logOutBttn;

    @FXML
    private Button orderStatusBttn;

    @FXML
    private Button ordersBttn;
    
    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("logInPage.fxml");
    }
    
    @FXML
    private void switchToMenu() throws IOException {
        App.setRoot("EmployeesBakeryMenu.fxml");
        //App.setRoot("menuPage.fxml");
    }

    @FXML
    void contactCustomer(MouseEvent event) {

    }

    @FXML
    void logOut(MouseEvent event) {

    }

    @FXML
    void orderStatus(MouseEvent event) {

    }

    @FXML
    void viewBakery(MouseEvent event) {

    }

    @FXML
    void viewOrders(MouseEvent event) {

    }

    @FXML
    void initialize() {
        assert bakeryBttn != null : "fx:id=\"bakeryBttn\" was not injected: check your FXML file 'Untitled'.";
        assert contactBttn != null : "fx:id=\"contactBttn\" was not injected: check your FXML file 'Untitled'.";
        assert logOutBttn != null : "fx:id=\"logOutBttn\" was not injected: check your FXML file 'Untitled'.";
        assert orderStatusBttn != null : "fx:id=\"orderStatusBttn\" was not injected: check your FXML file 'Untitled'.";
        assert ordersBttn != null : "fx:id=\"ordersBttn\" was not injected: check your FXML file 'Untitled'.";

    }

}
