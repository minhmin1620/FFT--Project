////Contributing Authors: K DeMerchant, Mackenzie Carter
package model.pojos;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import model.pojos.BankStatement;
import model.pojos.BankTransaction;

import java.util.ArrayList;

class BankStatementTest {

    @Test
    void testAddTransaction() {
        ArrayList<BankTransaction> transactions = new ArrayList<>();
        BankStatement bankStatement = new BankStatement(transactions);

        BankTransaction transaction = new BankTransaction("2023-11-10", "Grocery", 100.0, 50.0, 500.0, "MN", "Test Transaction");
        bankStatement.addTransaction(transaction);

        assertTrue(bankStatement.getList().contains(transaction)); 
    }

    @Test
    void testGetList() {
        ArrayList<BankTransaction> transactions = new ArrayList<>();
        BankTransaction transaction = new BankTransaction("2023-11-21", "Shopping", 100.0, 50.0, 500.0, "YK", "Test Transaction");
        transactions.add(transaction);

        BankStatement bankStatement = new BankStatement(transactions);

        assertEquals(1, bankStatement.getList().size());
        assertEquals(transaction, bankStatement.getList().get(0));
    }
  
    @Test
    void testTransactionExistsInBankStatement() {
        ArrayList<BankTransaction> transactions = new ArrayList<>();
        BankStatement bankStatement = new BankStatement(transactions);

        BankTransaction transaction1 = new BankTransaction("2023-11-06", "Grocery", 100.0, 50.0, 500.0, "MN", "Test Transaction");
        BankTransaction transaction2 = new BankTransaction("2023-11-14", "Memberships", 100.0, 50.0, 500.0, "MC", "Test Transaction");
        bankStatement.addTransaction(transaction1);
        bankStatement.addTransaction(transaction2);

        
        BankTransaction expectedTransaction = transaction2;
        assertTrue(bankStatement.getList().contains(expectedTransaction)); 
    }
}


