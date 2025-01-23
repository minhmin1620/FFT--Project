package model.viewmodel;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;

import model.pojos.Buyer;
import dataaccess.BuyerAccess;
import java.util.List;

//Contributing Authors: Mackenzie Carter
public class BuyerListViewModel {
    private ObservableList<Buyer> buyers;
    private final StringProperty initialProperty = new SimpleStringProperty("");
    private final IntegerProperty idProperty = new SimpleIntegerProperty(0);

    public BuyerListViewModel() {
    	List<Buyer> loadedBuyers = BuyerAccess.loadBuyers();
        this.buyers = FXCollections.observableArrayList(loadedBuyers);
    }

    public StringProperty initialProperty() {
        return initialProperty;
    }

    public IntegerProperty idProperty() {
        return idProperty;
    }

    public void addBuyer() {
        String initials = initialProperty.get();
        if (initials == null || initials.isBlank()) {return;}
        int id = buyers.size() + 1;
        Buyer newBuyer = new Buyer(id, initials);
        buyers.add(newBuyer);
        idProperty.set(newBuyer.getId());
        BuyerAccess.addBuyer(newBuyer);
        
    }

    public void clearBuyers() {
        buyers.clear(); 
        BuyerAccess.clearBuyers(); 
    }
    
    public ObservableList<Buyer> getBuyers() {
        return buyers;
    }
}
