//Contributing Authors: K DeMerchant, Mackenzie Carter
package model.pojos;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BankTransactionTest {
	private BankTransaction transaction;
	
	@BeforeEach
	void setUp() {
		transaction = new BankTransaction("2023-11-01", "Shoppers", 0.0, 50.0, 450.0, "KD", "Restaurant");
	}
	
	@AfterEach
	void tearDown() {
		transaction = null;
	}
    @Test
    void testGetters() {
        assertEquals("Shoppers", transaction.getDescription());
        assertEquals(50.0, transaction.getDebit());
        assertEquals(0.0, transaction.getCredit());
        assertEquals(450.0, transaction.getBalance());
        assertEquals("2023-11-01", transaction.getDate().toString());
    }
    
    @Test
    void monthTest() {
    	LocalDate date = transaction.getDate();
    	String month = date.getMonth().toString();
    	assertEquals("NOVEMBER", month);
    }
    
    @Test
    void dayTest() {
    	LocalDate date = transaction.getDate();
    	int day = date.getDayOfMonth();
    	assertEquals(1,day);
    }
}
