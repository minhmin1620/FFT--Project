package view.controllers;
import view.builders.Builder; 

import dataaccess.LedgerExport;
import view.builders.LedgerViewBuilder;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import model.viewmodel.TransactionModel;
import dataaccess.*;

//Contributing authors: Minh Nguyen, Kyle DeMerchant, Yousef Khai
public class LedgerController {
	
	private LedgerViewBuilder builder;
	private Stage primaryStage;
	private TransactionModel model;
	
	//Contributing authors: Minh Nguyen, Kyle DeMerchant, Yousef Khai
	public LedgerController(Stage primaryStage, TransactionModel model) {
		this.primaryStage = primaryStage;

		this.model = model;
		this.builder = new LedgerViewBuilder(handleBackToMenu(), this::handleExport,handleMonthlyTrans(), model);

	}
	//Contributing authors: Minh Nguyen
	public Region getView() {
		return builder.build();
	}
	
	//Contributing authors: Minh Nguyen, Yousef Khai
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
		
	//Contributing authors: Minh Nguyen, Kyle DeMerchant
		private void handleExport() {
			LedgerExport ledgerExport = new LedgerExport();
			ledgerExport.exportToExcel(TransactionAccess.getStoredTransactions());
			
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
		    alert.setTitle("Export Successful");
		    alert.setHeaderText(null);
		    alert.setContentText("The export was successful!");
		    alert.showAndWait();
				
		}
	
		//Contributing authors: Minh Nguyen
	private Runnable handleMonthlyTrans() {
	    return () -> {
	        TransactionModel model = new TransactionModel(new XSLXTransactionRepo());
	        
	        GridPane layout = new GridPane();
	        layout.setPadding(new Insets(10, 10, 10, 10));
	        layout.setHgap(10);
	        layout.setVgap(10);

	        layout.add(new MonthlyTransactionsController(model, primaryStage).getView(), 0, 0);

	        Scene monthlyTransScene = new Scene(layout, 900, 600);
	        primaryStage.setScene(monthlyTransScene);
	    };
	}
}

	
	

