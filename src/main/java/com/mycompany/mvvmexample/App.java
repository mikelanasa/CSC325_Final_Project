package com.mycompany.mvvmexample;



import com.google.cloud.firestore.Firestore;
import com.mycompany.mvvmexample.FirestoreContext;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {
   public static Firestore fstore;
    private final FirestoreContext contxtFirebase = new FirestoreContext();
	@Override
	public void start(Stage primaryStage) throws Exception {
                fstore = contxtFirebase.firebase();

		Parent login = FXMLLoader.load(getClass().getResource("AccessFBView.fxml"));
		Scene scene = new Scene(login);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void main(String[] args) {
            
		launch(args);
                
	}
        
        


}
