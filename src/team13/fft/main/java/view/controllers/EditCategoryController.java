package view.controllers;

import view.builders.EditCategoryViewBuilder;
import view.builders.Builder;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import model.viewmodel.CategoryListViewModel;

//Contributing Authors: Mackenzie Carter
public class EditCategoryController {

	private Stage primaryStage;
	private Builder<Region> builder;
	private CategoryListViewModel cModel;
	
	public EditCategoryController(Stage primaryStage, CategoryListViewModel model) {
	    this.primaryStage = primaryStage;
	    this.cModel = model;
	    this.builder = new EditCategoryViewBuilder(cModel.categoryProperty(), cModel.getCategories(), this::handleAddCategories, handleBack(), this::handleClearCategories);
	}
	
	public Region getView() {
		return builder.build();
	}
	
	private void handleAddCategories() {
		cModel.addCategory();
		cModel.categoryProperty().set(null);
	}
	
	private void handleClearCategories() {
		cModel.clearCategories();
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