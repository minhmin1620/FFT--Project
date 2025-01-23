package view.controllers;

import javafx.geometry.Insets;
import dataaccess.*;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import model.viewmodel.TransactionModel;
import view.builders.Builder;
import view.builders.MainMenuViewBuilder;

//Contributing authors: Minh Nguyen, Yousef Khai
public class MainMenuController {
	
    private Builder<Region> builder;
    private Stage primaryStage;
    private TransactionModel model;
    
    public MainMenuController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.builder = new MainMenuViewBuilder(handleFileSubmit(), handleViewLedger(), handleClose());
        this.model = new TransactionModel(new XSLXTransactionRepo());
    }

    public Region getView() {
        return builder.build();
    }

    private Runnable handleFileSubmit() {
        return () -> {
            GridPane results = new GridPane();
            results.setPadding(new Insets(10, 10, 10, 10));
            results.setHgap(20);
            results.setVgap(20);
            results.add(new BankStatementController(model, primaryStage).getView(), 0, 0);

            Scene bankScene = new Scene(results, 1200, 850);
            primaryStage.setScene(bankScene);
        };
    }
    
	private Runnable handleViewLedger() {
		return() ->{
	        
	        GridPane results = new GridPane();
	        results.setPadding(new Insets(10, 10, 10, 10));
	        results.setHgap(20);
	        results.setVgap(20);
	        results.add(new LedgerController(primaryStage, model).getView(), 0, 0);
        
	        Scene ledgerScene = new Scene(results, 680, 400);
	        primaryStage.setScene(ledgerScene);
		};
	}
	
	private Runnable handleClose() {
		return() ->{
	        primaryStage.close();
		};
	}
}