package view.builders;

import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import javafx.scene.Node;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;


//Contributing Authors: Mackenzie Carters
public class EditCategoryViewBuilder implements Builder<Region> {
	private final ObservableList<String> categoryList;
    private final StringProperty categoryProperty;
    private final Runnable handleAddCategories;
    private final Runnable handleBackToMenu;
    private final Runnable handleClearCategories;

    public EditCategoryViewBuilder(StringProperty categoryProperty,
                                ObservableList<String> categoryList,
                                Runnable handleAddCategories, Runnable handleBackToMenu, Runnable handleClearCategories) {
        this.categoryList = categoryList;
        this.categoryProperty = categoryProperty;
        this.handleAddCategories = handleAddCategories;
        this.handleBackToMenu = handleBackToMenu;
        this.handleClearCategories = handleClearCategories;
    }
	
    @Override
	public Region build() {
        
	     BorderPane results = new BorderPane();
	     results.setTop(addCategory());   
	     results.setBottom(menuButton());
	     results.setRight(displayCategoryList());
	     return results;
	 }
	
	private Node addCategory() {
		Label label1 = new Label("Enter name of new category:");
		TextField tf1 = new TextField();
		tf1.textProperty().bindBidirectional(categoryProperty);
		tf1.setOnAction(e->handleAddCategories.run());
		VBox results = new VBox(10, label1, tf1);
	    results.setPadding(new javafx.geometry.Insets(10));
	    results.setStyle("-fx-border-color: lightgray; -fx-border-width: 1; -fx-padding: 10;");
	    results.setPrefWidth(250);
	    results.getStylesheets().add(this.getClass().getResource("/style.css").toExternalForm());
	    return results;
	}
	
	private Node displayCategoryList() {
        ListView<String> results = new ListView<>(categoryList);
        results.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText("Category: " + item);
                }
            }
        });
	    results.getStylesheets().add(this.getClass().getResource("/style.css").toExternalForm());

        return results;
    }
	
	private Node menuButton() {
		Button menuButton = new Button("Menu");
	    menuButton.setOnAction(e -> handleBackToMenu.run());

	    Button clearButton = new Button("Clear Categories");
	    clearButton.setOnAction(e -> handleClearCategories.run());

	    HBox results = new HBox(10, menuButton, clearButton);
	    return results;
	}
}