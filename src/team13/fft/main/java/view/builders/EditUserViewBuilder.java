package view.builders;

import javafx.beans.property.IntegerProperty;
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

import javafx.scene.text.Text;
import model.pojos.Buyer;

//Contributing Authors: Mackenzie Carter
public class EditUserViewBuilder implements Builder<Region>{
	
	
	private final ObservableList<Buyer> buyerList;
    private final StringProperty initialProperty;
    private final IntegerProperty idProperty;
    private final Runnable handleAddUsers;
    private final Runnable handleBackToMenu;
    private final Runnable handleClearBuyers;

    public EditUserViewBuilder(StringProperty initialProperty, IntegerProperty idProperty,
                                ObservableList<Buyer> buyerList,
                                Runnable handleAddUsers, Runnable handleBackToMenu, Runnable handleClearBuyers) {
        this.buyerList = buyerList;
        this.initialProperty = initialProperty;
        this.idProperty = idProperty;
        this.handleAddUsers = handleAddUsers;
        this.handleBackToMenu = handleBackToMenu;
        this.handleClearBuyers = handleClearBuyers;
    }
	
    @Override
	public Region build() {
        
	     BorderPane results = new BorderPane();
	     results.setTop(addUser());   
	     //results.setLeft(userInfo()); 
	     results.setBottom(menuButton());
	     results.setRight(displayBuyerList());
	     return results;
	 }
	
	private Node addUser() {
		Label label1 = new Label("Enter initials to create a new user:");
		TextField tf1 = new TextField();
		tf1.textProperty().bindBidirectional(initialProperty);
		tf1.setOnAction(e->handleAddUsers.run());
		VBox results = new VBox(10, label1, tf1);
	    results.setPadding(new javafx.geometry.Insets(10));
	    results.setStyle("-fx-border-color: lightgray; -fx-border-width: 1; -fx-padding: 10;");
	    results.setPrefWidth(250);
	    results.getStylesheets().add(this.getClass().getResource("/style.css").toExternalForm());
	    return results;
	}
	
	private Node userInfo() {
		Text t1 = new Text("");
		t1.textProperty().bindBidirectional(initialProperty);
		Text t2 = new Text("");
		t2.textProperty().bind(idProperty.asString("User ID: %d"));
		VBox results = new VBox(10, t1, t2);
	    results.setPadding(new javafx.geometry.Insets(10));
	    results.setStyle("-fx-border-color: lightgray; -fx-border-width: 1; -fx-padding: 10;");
	    results.setPrefWidth(250);
	    results.getStylesheets().add(this.getClass().getResource("/style.css").toExternalForm());
		return results;
	}
	
	private Node displayBuyerList() {
        ListView<Buyer> results = new ListView<>(buyerList);
        results.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Buyer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText("ID: " + item.getId() + " | Initials: " + item.getInitials());
                }
            }
        });
	    results.getStylesheets().add(this.getClass().getResource("/style.css").toExternalForm());

        return results;
    }
	
	private Node menuButton() {
		/*Button results = new Button("Menu");
		results.setOnAction(e->handleBackToMenu.run());
		results.getStylesheets().add(this.getClass().getResource("/style.css").toExternalForm());
		return results;*/
		Button menuButton = new Button("Menu");
	    menuButton.setOnAction(e -> handleBackToMenu.run());

	    Button clearButton = new Button("Clear Buyers");
	    clearButton.setOnAction(e -> handleClearBuyers.run());

	    HBox results = new HBox(10, menuButton, clearButton);
	    return results;
	}
}
