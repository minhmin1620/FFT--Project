//Contributing Authors: K DeMerchant, Mackenzie Carter
package model.pojos;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import model.pojos.BankStatement;
import model.pojos.BankTransaction;
import model.pojos.TransactionList;

import java.util.ArrayList;
import java.time.Month;

class TransactionListTest {

    @Test
    void testAddTransaction() {
        TransactionList transactionList = new TransactionList(Month.NOVEMBER);
        BankTransaction transaction = new BankTransaction("2023-11-01", "Grocery", 100.0, 50.0, 500.0, "MN", "Test Transaction");

        transactionList.addTransaction(transaction);

        assertEquals(11, transactionList.getMonth().getValue());
    }

    @Test
    void testAddStatement() {
        TransactionList transactionList = new TransactionList(Month.NOVEMBER);

        BankTransaction transaction1 = new BankTransaction("2023-11-01", "Grocery", 100.0, 50.0, 500.0, "MN", "Test Transaction");
        BankTransaction transaction2 = new BankTransaction("2023-11-01", "Grocery", 100.0, 50.0, 500.0, "MN", "Test Transaction");
        ArrayList<BankTransaction> transactions = new ArrayList<>();
        transactions.add(transaction1);
        transactions.add(transaction2);

        BankStatement bankStatement = new BankStatement(transactions);

        transactionList.addStatement(bankStatement);

        assertEquals(2, transactions.size()); 
    }
    
}