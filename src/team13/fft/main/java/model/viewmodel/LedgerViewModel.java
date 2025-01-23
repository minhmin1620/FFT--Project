package model.viewmodel;

import javafx.beans.property.SimpleStringProperty;

import javafx.beans.property.StringProperty;

//Contributing authors: Minh Nguyen
public class LedgerViewModel {
	private final StringProperty USER;
    private final StringProperty MONTH;
    private final StringProperty DEBIT;
    private final StringProperty CREDIT;
    private final StringProperty BALANCE;
    
    public LedgerViewModel(String user, String month, String debit, String credit, String balance) {
    	USER = new SimpleStringProperty(user);
    	MONTH = new SimpleStringProperty(month);
        DEBIT = new SimpleStringProperty(debit);
        CREDIT = new SimpleStringProperty(credit);
        BALANCE = new SimpleStringProperty(balance);
    }
    
    public StringProperty userProperty() {
    	return USER;
    }
    
    public StringProperty monthProperty() {
    	return MONTH;
    }
    
    public StringProperty debitProperty() {
    	return DEBIT;
    }
    
    public StringProperty creditProperty() {
    	return CREDIT;
    }
    
    public StringProperty balProperty() {
    	return BALANCE;
    }
    
    public String getUser() { 
    	return USER.get(); 
    }
    
    public String getMonth() {
    	return MONTH.get(); 
    }
    public String getDebit() {
    	return DEBIT.get();
    }
    public String getCredit() {
    	return CREDIT.get(); 
    }
    public String getBal() { 
    	return BALANCE.get();
    }
}