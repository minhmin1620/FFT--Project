import dataaccess.TransactionAccess;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import view.controllers.MainMenuController;

//Contributing authors: Minh Nguyen,Yousef Khai
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
    	TransactionAccess.loadTransactions();
 
        GridPane results = new GridPane();
        results.setPadding(new Insets(10, 10, 10, 10));
        results.setHgap(10); 
        results.setVgap(10);	

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.ALWAYS); 
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS);

        results.getColumnConstraints().addAll(col1, col2);

        results.add(new MainMenuController(primaryStage).getView(), 0, 0, 2, 1);
        //Add CSS
        Scene scene = new Scene(results, 800, 600); 

        primaryStage.setTitle("Family Finance Tracker");
        primaryStage.setScene(scene);

        primaryStage.show();
    }
    public void stop() {
        TransactionAccess.writeTransactions();
    }
}