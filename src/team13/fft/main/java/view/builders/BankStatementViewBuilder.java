package view.builders;
import dataaccess.BuyerAccess;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import model.pojos.BankTransaction;
import model.pojos.Buyer;
import model.viewmodel.TransactionModel;
import javafx.scene.control.ListCell;

//Contributing Authors: Minh nguyen, Yousef Khai, Mackenzie Carter
public class BankStatementViewBuilder implements Builder<Region> {
    private Runnable browseHandler;
    private Runnable backToMenu;
    private Runnable editUsers;
    private Runnable editCategories;
    private Runnable assignBuyerHandler;
    private Runnable assignCategoryHandler;
    private Runnable submitFileHandler;
    private Runnable unassignBuyerHandler;
	private Runnable unassignCategoryHandler;
    private final StringProperty bankStatementProperty;
    private final TransactionModel model;

    private ObservableList<Buyer> buyersList;
    private ObservableList<String> categoriesList;
    private ComboBox<String> categoryComboBox;
    private TableView<BankTransaction> transactionList;

    public BankStatementViewBuilder(StringProperty bankStatementProperty, TransactionModel model,
                                    Runnable browseHandler, Runnable backToMenu, 
                                    Runnable editUsers, Runnable assignBuyerHandler, 
                                    Runnable assignCategoryHandler, Runnable submitFileHandler, 
                                    Runnable editCategories,Runnable unassignCategoryHandler, Runnable unassignBuyerHandler) {
        this.bankStatementProperty = bankStatementProperty;
        this.model = model;
        this.editCategories = editCategories;
        this.browseHandler = browseHandler;
        this.backToMenu = backToMenu;
        this.editUsers = editUsers;
        this.assignBuyerHandler = assignBuyerHandler;
        this.assignCategoryHandler = assignCategoryHandler;
        this.submitFileHandler = submitFileHandler;
        this.unassignBuyerHandler = unassignBuyerHandler;
        this.unassignCategoryHandler = unassignCategoryHandler;
        this.buyersList = FXCollections.observableArrayList(BuyerAccess.loadBuyers());
        this.categoriesList = FXCollections.observableArrayList(BuyerAccess.loadCategories());
    }
    @Override
    public Region build() {
        BorderPane result = new BorderPane();
        result.setId("main-pane");

        result.setRight(transactionsPane());
        result.setLeft(uploadBankStatementPane());

        return result;
    }


    private Node monthSelectBox() {
        ComboBox<String> monthSelectBox = new ComboBox<>();
        monthSelectBox.setPromptText("View Previous Monthly Transactions");
        monthSelectBox.getItems().addAll(model.getMonths());
        monthSelectBox.setOnAction(evt -> {
            model.filterByMonth(monthSelectBox.getValue());
            model.loadStoredTransactions();
            transactionList.setItems(model.getFilteredTransactions());
        });
        return monthSelectBox;
    }


    private Node buyerSelectionDropdown() {
        ComboBox<Buyer> buyerComboBox = new ComboBox<>();
        buyerComboBox.setPromptText("Select Buyer");     
        buyerComboBox.getItems().addAll(buyersList);

        // Handle selection changes
        buyerComboBox.setOnAction(evt -> {
            Buyer selectedBuyer = buyerComboBox.getValue();
            model.selectedBuyerProperty().set(selectedBuyer);
        });
        return buyerComboBox;
    }

    private Node categorySelectionDropdown() {
        categoryComboBox = new ComboBox<>(categoriesList);
        categoryComboBox.setPromptText("Select Category");
        categoryComboBox.setCellFactory(param -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : "Category: " + item);
            }
        });
        model.selectedCategoryProperty().bind(categoryComboBox.valueProperty());

        return categoryComboBox;
        
        
    }

    private Node assignBuyerButton() {
        Button results = new Button("Assign Buyer");
        results.setOnAction(evt -> {
        	
        assignBuyerHandler.run();
        transactionList.refresh();
        });
        
        return results;
        
    }
    private Node unassignBuyerButton() {
    	Button results = new Button("Unassign Buyer");
        results.setOnAction(evt -> {
            	
        unassignBuyerHandler.run();
        transactionList.refresh();
          });           
        return results;             	
    }

    private Node assignCategoryButton() {
        Button results = new Button("Assign Category");
        results.setOnAction(evt -> {
        	
        assignCategoryHandler.run();
        transactionList.refresh();
        });
        return results;
    }
    private Node unassignCategoryButton() {
        Button results = new Button("Unassign Category");
        results.setOnAction(evt -> {
        	
        unassignCategoryHandler.run();
        transactionList.refresh();
        });
        return results;
    }

    private Node submitFileButton() {
        Button submitButton = new Button("Submit File");
        submitButton.setOnAction(evt -> submitFileHandler.run());
        return submitButton;
    }

    private Node transactionsPane() {
        GridPane results = new GridPane();
        results.setId("list-pane");

        transactionList = new TableView<>();
        transactionList.setItems(model.getTransactions()); 
        
        transactionList.setMinWidth(500);
        transactionList.setMinHeight(600);
        transactionList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    
        Bindings.bindContent(model.getSelectedTransactions(), transactionList.getSelectionModel().getSelectedItems());

        TableColumn<BankTransaction, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        descriptionColumn.setMinWidth(200);

        TableColumn<BankTransaction, String> creditColumn = new TableColumn<>("Credit");
        creditColumn.setCellValueFactory(new PropertyValueFactory<>("credit"));

        TableColumn<BankTransaction, String> debitColumn = new TableColumn<>("Debit");
        debitColumn.setCellValueFactory(new PropertyValueFactory<>("debit"));

        TableColumn<BankTransaction, String> balanceColumn = new TableColumn<>("Balance");
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));

        TableColumn<BankTransaction, String> buyerColumn = new TableColumn<>("Buyer");
        buyerColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBuyer()));

        TableColumn<BankTransaction, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory()));

        transactionList.getColumns().addAll(descriptionColumn, creditColumn, debitColumn, balanceColumn, buyerColumn, categoryColumn);
        results.add(monthSelectBox(), 0, 0);
        results.add(transactionList, 0, 1);
        return results;
    }
    
	//Contributing Authors: Mackenzie Carter
	private Node buttonsContainer() {
		Button editUsersB = new Button("Edit Users");
		editUsersB.setOnAction(evt -> editUsers.run());
		Button editCategoriesB = new Button("Edit Categories");
		editCategoriesB.setOnAction(evt -> editCategories.run());
		
		VBox results = new VBox(10, editUsersB, editCategoriesB);
		results.setPadding(new javafx.geometry.Insets(10));
	    results.setPrefWidth(250);
	    results.getStylesheets().add(this.getClass().getResource("/style.css").toExternalForm());
		
		return results;
	}

	//Contributing authors: Minh Nguyen, Yousef Khai
	private Node uploadBankStatementPane() {
	    GridPane results = new GridPane();
	    results.setId("load-pane");
	    results.setVgap(11);
	    results.add(title(), 1, 1);
	    results.add(statementField(), 1, 2);
	    results.add(browseButton(), 2, 2);
	    results.add(buyerSelectionDropdown(), 1, 4);

	   
	    HBox buyerButtonsContainer = new HBox(10); 
	    buyerButtonsContainer.getChildren().addAll(assignBuyerButton(), unassignBuyerButton());
	    results.add(buyerButtonsContainer, 2, 4); 
	    results.add(categorySelectionDropdown(), 1, 6);

	    HBox categoryButtonsContainer = new HBox(10); 
	    categoryButtonsContainer.getChildren().addAll(assignCategoryButton(), unassignCategoryButton());
	    results.add(categoryButtonsContainer, 2, 6); 

	    results.add(submitFileButton(), 1, 9);
	    results.add(backButton(), 1, 31);
	    results.add(buttonsContainer(), 2, 31);

	    results.getStylesheets().add(this.getClass().getResource("/style.css").toExternalForm());

	    return results;
	}


    private Node title() {
        return new Label("Loading Bank Files");
    }

    private Node statementField() {
        TextField results = new TextField();
        results.setEditable(false);
        results.setPrefWidth(200);
        results.textProperty().bind(bankStatementProperty);
        return results;
    }

    private Node browseButton() {
        Button results = new Button("Browse...");
        results.setOnAction(evt -> browseHandler.run());
        return results;
    }

    private Node backButton() {
        Button results = new Button("Menu");
        results.setOnAction(evt -> backToMenu.run());
        return results;
    }

    
}

