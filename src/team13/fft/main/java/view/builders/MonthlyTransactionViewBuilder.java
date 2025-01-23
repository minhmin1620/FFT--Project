package view.builders;

import dataaccess.BuyerAccess;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import model.pojos.BankTransaction;
import model.viewmodel.LedgerViewModel;
import model.viewmodel.TransactionModel;
//Contributing Authors: Minh nguyen, Yousef Khai

public class MonthlyTransactionViewBuilder implements Builder<Region> {
    private final TransactionModel model;
    private TableView<BankTransaction> tableView;
    private Runnable handleBackLedger;
    private Runnable handleBackToMenu;
    private Runnable handleExport;

    public MonthlyTransactionViewBuilder(Runnable handleBackToMenu,TransactionModel model, Runnable handleBackLedger, Runnable handleExport) {
        this.model = model;
        this.tableView = new TableView<>();
        this.handleBackLedger = handleBackLedger;
        this.handleBackToMenu = handleBackToMenu;
        this.handleExport = handleExport;
        
    }

  //Contributing Authors: Minh nguyen
    @Override
    public Region build() {
        
	     BorderPane results = new BorderPane();
	     results.setLeft(leftPane());   
	     results.setCenter(rightPane()); 

	     return results;
	 }
  //Contributing Authors: Minh nguyen
	 private Node leftPane() {
		 VBox results = new VBox(10, new Label("User:"), userSelectBox(), new Label("Month"), monthSelectBox());
	     results.setPadding(new javafx.geometry.Insets(10));
	     results.setStyle("-fx-border-color: lightgray; -fx-border-width: 1; -fx-padding: 10;");
	     results.setPrefWidth(250);
	     
	     Node buttonsContainer = createButtonsContainer();
	     results.getChildren().add(buttonsContainer);
	     VBox.setVgrow(buttonsContainer, Priority.ALWAYS);
	     results.getStylesheets().add(this.getClass().getResource("/style.css").toExternalForm());

	     return results;
	 }
	//Contributing Authors: Minh nguyen
	 private Node rightPane() {
		 tableView = new TableView<>();
	     createTable();
		 VBox results = new VBox(tableView);
	     results.setSpacing(10);
	     results.setPrefWidth(500);
	     return results;
	 }
	 
	//Contributing Authors: Minh nguyen
	 private Node createTable() {
		 	model.loadStoredTransactions();
	        tableView.setItems(model.getFilteredTransactions());
		 	TableColumn<BankTransaction, String> dateColumn = new TableColumn<>("Date");
	        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
	        
	        TableColumn<BankTransaction, String> buyerColumn = new TableColumn<>("Buyer");
	        buyerColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBuyer()));
	        
	        TableColumn<BankTransaction, String> descColumn = new TableColumn<>("Description");
	        descColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

	        TableColumn<BankTransaction, Double> debitColumn = new TableColumn<>("Debit");
	        debitColumn.setCellValueFactory(new PropertyValueFactory<>("debit"));

	        TableColumn<BankTransaction, Double> creditColumn = new TableColumn<>("Credit");
	        creditColumn.setCellValueFactory(new PropertyValueFactory<>("credit"));

	        tableView.getColumns().addAll(dateColumn,buyerColumn, descColumn, debitColumn, creditColumn);
	        

	        return tableView;
	 }
	 
	//Contributing Authors: Minh nguyen, Yousef Khai
	 private Node userSelectBox() {
		    ComboBox<String> userSelectBox = new ComboBox<>();
		    userSelectBox.setPromptText("Select User");
		    userSelectBox.getItems().addAll(BuyerAccess.loadBuyersInitial());
		    userSelectBox.setOnAction(evt -> model.filterByUser(userSelectBox.getValue()));
		    return userSelectBox;
		}

	//Contributing Authors: Minh nguyen, Yousef Khai
		private Node monthSelectBox() {
		    ComboBox<String> monthSelectBox = new ComboBox<>();
		    monthSelectBox.setPromptText("Select Month");
		    monthSelectBox.getItems().addAll(model.getMonths());
		    monthSelectBox.setOnAction(evt -> model.filterByMonth(monthSelectBox.getValue())); 
		    return monthSelectBox;
		}
	
	//Contributing Authors: Minh nguyen
	private Node createButtonsContainer() {
	    Button exportButton = new Button("Export ledger");
	    exportButton.setOnAction(e -> handleExport.run());
	    
	    Button monthlyTransactionsButton = new Button("Ledger");
	    monthlyTransactionsButton.setOnAction(e -> handleBackLedger.run());
	    
	    Button menuButton = new Button("Menu");
	    menuButton.setOnAction(e -> handleBackToMenu.run());

	    HBox topRowButtons = new HBox(20, exportButton, monthlyTransactionsButton); 
	    topRowButtons.setStyle("-fx-alignment: center;"); 
	    VBox buttonsContainer = new VBox(10, topRowButtons, menuButton);
	    buttonsContainer.setStyle("-fx-alignment: center;"); 

	    VBox result = new VBox();
	    result.getChildren().addAll(new Region(), buttonsContainer); 
	    result.setStyle("-fx-alignment: bottom-center;"); 
	    

	    return result;
	}

}
