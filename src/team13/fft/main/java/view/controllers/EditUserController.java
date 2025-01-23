//Author: Mackenzie Carter
package view.controllers;

import view.builders.EditUserViewBuilder;
import view.builders.Builder;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import model.viewmodel.BuyerListViewModel;

//Contributing Authors: Mackenzie Carter
public class EditUserController {
	private Stage primaryStage;
	private Builder<Region> builder;
	private final BuyerListViewModel bModel;
	
	public EditUserController(Stage primaryStage, BuyerListViewModel bModel) {
		this.primaryStage = primaryStage;
		this.bModel = bModel;
		this.builder = new EditUserViewBuilder(bModel.initialProperty(), bModel.idProperty(), bModel.getBuyers(), this::handleAddUsers, handleBack(), this::handleClearBuyers);
	}
	
	public Region getView() {
		return builder.build();
	}
	
	private void handleAddUsers() {
		bModel.addBuyer();
		bModel.initialProperty().set(null);
	}
	
	private void handleClearBuyers() {
		bModel.clearBuyers();
	}
	private Runnable handleBack() {
	    return () -> {
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
