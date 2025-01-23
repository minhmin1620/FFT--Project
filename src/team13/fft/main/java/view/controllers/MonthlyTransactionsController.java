package view.controllers;

import dataaccess.DummyRepo;
import dataaccess.LedgerExport;
import dataaccess.TransactionAccess;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import model.viewmodel.TransactionModel;
import view.builders.MonthlyTransactionViewBuilder;
//Contributing Authors: Minh nguyen, Yousef Khai
public class MonthlyTransactionsController {
    private final MonthlyTransactionViewBuilder builder;
    private Stage primaryStage;

  //Contributing Authors: Minh nguyen
    public MonthlyTransactionsController(TransactionModel model, Stage primaryStage) {
        this.builder = new MonthlyTransactionViewBuilder(handleBackToMenu(), model, handleBackLedger(), this::handleExport);
        this.primaryStage = primaryStage;
    }
  //Contributing Authors: Minh nguyen
    public Region getView() {
        return builder.build();
    }
  //Contributing Authors: Minh nguyen
    private Runnable handleBackLedger() {
		return() ->{
	        
	        GridPane results = new GridPane();
	        results.setPadding(new Insets(10, 10, 10, 10));
	        results.setHgap(20);
	        results.setVgap(20);
	        
	        TransactionModel model = new TransactionModel(new DummyRepo());
	        model.loadBankStatement();
	        results.add(new LedgerController(primaryStage,model).getView(), 0, 0);
	        
	        Scene ledgerScene = new Scene(results, 680, 400);
	        primaryStage.setScene(ledgerScene);
		};
	}
    
  //Contributing Authors: Yousef Khai
    private Runnable handleBackToMenu() {
		return() ->{
	        
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
	        
	        Scene menuScene = new Scene(results, 900, 600); 

	        primaryStage.setTitle("Family Finance Tracker");
	        primaryStage.setScene(menuScene);

	        primaryStage.setMinWidth(400);
	        primaryStage.setMinHeight(300);
		};
	}
    
  //Contributing Authors: Minh nguyen
    private void handleExport() {
		LedgerExport ledgerExport = new LedgerExport();
		ledgerExport.exportToExcel(TransactionAccess.getStoredTransactions());
		
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
	    alert.setTitle("Export Successful");
	    alert.setHeaderText(null);
	    alert.setContentText("The export was successful!");
	    alert.showAndWait();
			
	}
}
