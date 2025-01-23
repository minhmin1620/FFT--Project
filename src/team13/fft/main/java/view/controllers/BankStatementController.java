package view.controllers;

import view.builders.Builder; 
import view.builders.BankStatementViewBuilder;

import java.io.File;

import dataaccess.TransactionAccess;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.viewmodel.TransactionModel;
import model.viewmodel.CategoryListViewModel;
import model.pojos.BankTransaction;
import model.pojos.Buyer;
import model.viewmodel.BuyerListViewModel;


//Contributing authors: Minh Nguyen, Yousef Khai, Mackenzie Carter

public class BankStatementController {
 
    private final TransactionModel model;
    private final BankStatementViewBuilder view;
    private Stage primaryStage;
    
    public BankStatementController(TransactionModel model, Stage primaryStage) {
        this.model = model;
        this.primaryStage = primaryStage;
      
        this.view = new BankStatementViewBuilder(
                model.bankStatementProperty(),
                model,
                browseBankStatement(),
                handleBackToMenu(),
                editUsers(),
                assignBuyer(),
                assignCategory(),
                submitFile(),
                editCategories(),unassignCategoryHandler(),unassignBuyerHandler()
        );
    }
    private Runnable unassignBuyerHandler() {
    	return() -> {
	    	ObservableList<BankTransaction> selectedTransactions = model.getSelectedTransactions();
	
	        if (selectedTransactions != null) {
	            for (BankTransaction i : selectedTransactions) {
	                i.setBuyer("");
	            }
	        }
	        TransactionAccess.writeTransactions();
		};
    }
	private Runnable unassignCategoryHandler() {
		return() -> {
	          ObservableList<BankTransaction> selectedTransactions = model.getSelectedTransactions();
	        
	        if (selectedTransactions != null) {
	            for (BankTransaction i : selectedTransactions) {
	                i.setCategory("");
	            }
	            TransactionAccess.writeTransactions();
	
	        }
    	};
	}

    private Runnable assignBuyer() {
    	return() -> {
	        Buyer selectedBuyer = model.selectedBuyerProperty().get();
	        ObservableList<BankTransaction> selectedTransactions = model.getSelectedTransactions();
	        
	        if (selectedBuyer != null && selectedTransactions != null) {
	            for (BankTransaction i : selectedTransactions) {
	                i.setBuyer(selectedBuyer.getInitials());	    
	            }
	            TransactionAccess.writeTransactions();
	
	        }
    	};
        
    }

    private Runnable assignCategory() {
    	return () -> {
	        String selectedCategory = model.selectedCategoryProperty().get();
	        ObservableList<BankTransaction> selectedTransactions = model.getSelectedTransactions();
	
	        if (selectedCategory != null && selectedTransactions != null) {
	            for (BankTransaction i : selectedTransactions) {
	                i.setCategory(selectedCategory);
	            }
	            TransactionAccess.writeTransactions();
	        }
    	};
    }


    private Runnable submitFile() {
        return () -> {
        	TransactionAccess.addTransactions(model.getTransactions());
            TransactionAccess.writeTransactions();

        	
        	model.getTransactions().setAll();
            System.out.println("Submit file handler executed.");
            
        };
    }

    public Region getView() {
        return view.build();
    }
    
    private Runnable browseBankStatement() {
        return () -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose Bank Statement");
           
            File initialDirectory = new File("./src/main/data");
            if (initialDirectory.exists() && initialDirectory.isDirectory()) {
                fileChooser.setInitialDirectory(initialDirectory);
            }
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                model.setBankStatement(selectedFile.getAbsolutePath());
                model.loadBankStatement(); 
            } else {
                System.out.println("No file selected.");
            }
        };
    }
    
    private Runnable editUsers() {
    	return () -> {
    		GridPane results = new GridPane();
	        results.setPadding(new Insets(10, 10, 10, 10));
	        results.setHgap(20);
	        results.setVgap(20);
	        
	        BuyerListViewModel model = new BuyerListViewModel();
	        results.add(new EditUserController(primaryStage, model).getView(), 0, 0);
	        
	        Scene editUsersScene = new Scene(results, 250, 400);
	        primaryStage.setScene(editUsersScene);
    	};
    }    
    private Runnable editCategories() {
        return () -> {
            try {
                GridPane results = new GridPane();
                results.setPadding(new Insets(10, 10, 10, 10));
                results.setHgap(20);
                results.setVgap(20);

                CategoryListViewModel cModel = new CategoryListViewModel();
                EditCategoryController categoryController = new EditCategoryController(primaryStage, cModel);
                
                Region view = categoryController.getView();
                if (view == null) {
                    throw new IllegalStateException("EditCategoryController returned a null view.");
                }
                results.add(view, 0, 0);

                Scene editCategoriesScene = new Scene(results, 250, 400);
                primaryStage.setScene(editCategoriesScene);
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Failed to open edit categories screen: " + e.getMessage());
            }
        };
    }
    
    
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
}
