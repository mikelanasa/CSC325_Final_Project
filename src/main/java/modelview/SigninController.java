/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelview;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.mycompany.mvvmexample.App;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 *
 * @author mikel
 */
public class SigninController {
    
    @FXML
    private Button button_signIn, button_signUp;

    @FXML
    private TextField textField_user;
    
    @FXML
    private PasswordField textField_pass;
    
    static UserRecord currentUser;

    @FXML
    void handleButton_signIn(ActionEvent event) throws IOException {
        try {
            // get username input
            String user = textField_user.getText();
            // get password input
            String pass = textField_pass.getText();
            // compare input to firebase authentication
            currentUser = FirebaseAuth.getInstance().getUser(user);
            App.setRoot("AccessFBView.fxml");
            
        } catch (FirebaseAuthException | IllegalArgumentException ex) {
            System.err.println("Error signing in. Check UserID and password and try again");
        }
    } // ends sign in button handler

    @FXML
    void handleButton_signUp(ActionEvent event) throws IOException {
        App.setRoot("signup.fxml");
    }
    
}
