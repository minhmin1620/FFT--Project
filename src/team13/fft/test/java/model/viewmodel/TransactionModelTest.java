////Contributing Authors: Minh Nguyen, Yousef Khai
package model.viewmodel;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import dataaccess.TransactionRepo;
import model.pojos.BankStatement;
import model.pojos.BankTransaction;
import dataaccess.DummyRepo;
import java.util.List;

class TransactionModelTest {

	@Test
	public void testFilterByUser() {
	    TransactionRepo<BankStatement> repo = new DummyRepo(); 
	    TransactionModel model = new TransactionModel(repo);
	    model.loadBankStatement();

	    model.filterByUser("MN"); 
	    List<BankTransaction> filtered = model.getFilteredTransactions();

	    assertTrue(filtered.stream().allMatch(txn -> txn.getBuyer().equalsIgnoreCase("MN")));
	}
	
	@Test
	public void testFilterByMonth() {
	    TransactionRepo<BankStatement> repo = new DummyRepo();
	    TransactionModel model = new TransactionModel(repo);
	    model.loadBankStatement();

	    model.filterByMonth("JANUARY"); 
	    List<BankTransaction> filtered = model.getFilteredTransactions();

	    assertTrue(filtered.stream().allMatch(txn -> txn.getDate().getMonth().toString().equalsIgnoreCase("JANUARY")));
	}
	
	 @Test
	    public void testGetCategory() {	       
	        TransactionRepo<BankStatement> repo = new DummyRepo();
	        TransactionModel model = new TransactionModel(repo);
	        model.loadBankStatement(); 	      
	        BankTransaction transaction = model.getTransactions().get(0); 
	        assertEquals("Food & Beverage", transaction.getCategory()); 
	    }

	    @Test
	    public void testSetCategory() {	     
	        TransactionRepo<BankStatement> repo = new DummyRepo();
	        TransactionModel model = new TransactionModel(repo);
	        
	        model.loadBankStatement(); 	       
	        BankTransaction transaction = model.getTransactions().get(0); 	
	        
	        transaction.setCategory("Entertainment");
	    
	        assertEquals("Entertainment", transaction.getCategory());
	    }


}


