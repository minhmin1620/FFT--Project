//Contributing authors: Minh Nguyen
package model.pojos;



import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SelectClasses;

@Suite
@SelectClasses({BankStatementTest.class, BankTransactionTest.class,BuyerTest.class,TransactionListTest.class,})
public class PojosAllTest {
	
}
