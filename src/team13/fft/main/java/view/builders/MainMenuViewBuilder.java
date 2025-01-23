package view.builders;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.controllers.LedgerController;
import view.controllers.BankStatementController;

//Contributing authors: Yousef Khai
public class MainMenuViewBuilder implements Builder<Region> {

    private Runnable handleFileSubmit;
    private Runnable handleViewLedger;
    private Runnable handleClose;

    public MainMenuViewBuilder(Runnable handleFileSubmit, Runnable handleViewLedger, Runnable handleClose) {
        this.handleFileSubmit = handleFileSubmit;
        this.handleViewLedger = handleViewLedger;
        this.handleClose = handleClose;
    }
    

    @Override
    public Region build() {   
        VBox results = new VBox(20,title(), submitFileButton(), ledgerButton(), exitButton());
        results.setStyle("-fx-alignment: center;"); 
        results.getStylesheets().add(this.getClass().getResource("/style.css").toExternalForm());

        return results;
    }

    private Node submitFileButton() {
    	Button results = new Button("Submit File");
    	results.setOnAction(e -> handleFileSubmit.run());
    	return results; 		
    }
    private Node exitButton() {
    	Button results = new Button("Exit App");
    	results.setOnAction(e -> handleClose.run());
    	return results;
    }
    private Node ledgerButton() {
    	Button results = new Button("View Ledger");
    	results.setOnAction(e -> handleViewLedger.run());
    	return results;
    }
    private Node title(){
    	Label results  = new Label("Family Finance Tracker");
        results.setId("Title");
        return results;	
    }

   
}