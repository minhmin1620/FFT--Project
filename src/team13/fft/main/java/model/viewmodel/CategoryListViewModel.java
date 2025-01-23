package model.viewmodel;

import java.util.List;

import dataaccess.BuyerAccess;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//Contributing Authors: Mackenzie Carter
public class CategoryListViewModel {
	private ObservableList<String> categories;
    private final StringProperty categoryProperty = new SimpleStringProperty("");

    public CategoryListViewModel() {
    	List<String> loadedCategories = BuyerAccess.loadCategories();
        this.categories = FXCollections.observableArrayList(loadedCategories);
    }

    public StringProperty categoryProperty() {
        return categoryProperty;
    }

    public void addCategory() {
        String categoryName = categoryProperty.get();
        if (categoryName == null || categoryName.trim().isEmpty()) {
            return; 
        }
        if (categories.contains(categoryName)) {
            return; 
        }
        categories.add(categoryName);
        BuyerAccess.addCategory(categoryName);
    }


    public void clearCategories() {
        categories.clear(); 
        BuyerAccess.clearCategories(); 
    }
    
    public ObservableList<String> getCategories() {
        return categories;
    }
}