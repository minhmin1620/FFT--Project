package view.builders;

import model.viewmodel.TransactionModel;
import model.viewmodel.TransactionModel.MonthlyUserSummary;

import java.util.List;
import java.util.stream.Collectors;

import dataaccess.BuyerAccess;
import javafx.collections.FXCollections;
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
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;

//Contributing authors: Minh Nguyen, Yousef Khai,Kyle DeMerchant
public class LedgerViewBuilder implements Builder<Region> {
	
	 private TableView<MonthlyUserSummary> tableView;
	 private Runnable handleExport;
	 private Runnable handleBackToMenu;
	 private Runnable handleMonthlyTrans;
	 private TransactionModel model;


	 public LedgerViewBuilder(Runnable handleBackToMenu, Runnable handleExport, Runnable handleMonthlyTrans, TransactionModel model) {
		 this.handleBackToMenu = handleBackToMenu;
		 this.handleExport = handleExport;
		 this.handleMonthlyTrans = handleMonthlyTrans;
     this.model = model;
	 }
	 
	 @Override
	 public Region build() {
	            
	     BorderPane results = new BorderPane();
	     results.setLeft(leftPane());   
	     results.setCenter(rightPane()); 

	     return results;
	 }
	 private Node leftPane() {
		 VBox results = new VBox(10, new Label("User:"), userSelectBox());
	     results.setPadding(new javafx.geometry.Insets(10));
	     results.setStyle("-fx-border-color: lightgray; -fx-border-width: 1; -fx-padding: 10;");
	     results.setPrefWidth(250);
	     
	     Node buttonsContainer = createButtonsContainer();
	     results.getChildren().add(buttonsContainer);
	     VBox.setVgrow(buttonsContainer, Priority.ALWAYS);
	     results.getStylesheets().add(this.getClass().getResource("/style.css").toExternalForm());

	     return results;
	 }
	 
	 private Node rightPane() {
		 tableView = new TableView<>();
	     createTable();
	     List<MonthlyUserSummary> summaries = model.getMonthlySummariesByUser(null);
	        tableView.setItems(FXCollections.observableArrayList(summaries));
		 VBox results = new VBox(tableView);
	     results.setSpacing(10);
	     results.setPrefWidth(500);
	     return results;
	 }
	 
	 private void createTable() {
		 TableColumn<MonthlyUserSummary, String> userColumn = new TableColumn<>("User");
	     userColumn.setCellValueFactory(new PropertyValueFactory<>("user"));

	     TableColumn<MonthlyUserSummary, String> monthColumn = new TableColumn<>("Month");
	     monthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));

	     TableColumn<MonthlyUserSummary, Double> debitColumn = new TableColumn<>("Total Debit");
	     debitColumn.setCellValueFactory(new PropertyValueFactory<>("totalDebit"));

	     TableColumn<MonthlyUserSummary, Double> creditColumn = new TableColumn<>("Total Credit");
	     creditColumn.setCellValueFactory(new PropertyValueFactory<>("totalCredit"));

	     TableColumn<MonthlyUserSummary, Double> balanceColumn = new TableColumn<>("Balance");
	     balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));

	     tableView.getColumns().addAll(userColumn, monthColumn, debitColumn, creditColumn, balanceColumn); 
	   
	 }
	 
	 private Node userSelectBox() {
			ComboBox<String> userSelectBox = new ComboBox<>();
			userSelectBox.setPromptText("Select User");
			
			 List<String> users = BuyerAccess.loadBuyersInitial();
			        users.add(0, "All Users"); 
			        userSelectBox.setItems(FXCollections.observableArrayList(users));
			        userSelectBox.setPromptText("Select User");

			      
			        userSelectBox.setOnAction(e -> {
			            String selectedUser = userSelectBox.getValue();
			            if ("All Users".equals(selectedUser)) {
			                updateTable(model.getMonthlySummariesByUser(null)); 
			            } else {
			                updateTable(model.getMonthlySummariesByUser(selectedUser));
			            }
			        });

				
			return userSelectBox;
		}
		
		private void updateTable(List<MonthlyUserSummary> filteredData) {
	        tableView.setItems(FXCollections.observableArrayList(filteredData));
	    }
	
	private Node createButtonsContainer() {
	    Button exportButton = new Button("Export ledger");
	    exportButton.setOnAction(e -> handleExport.run());
	    
	    Button monthlyTransactionsButton = new Button("Monthly trans");
	    monthlyTransactionsButton.setOnAction(e -> handleMonthlyTrans.run());
	    
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

