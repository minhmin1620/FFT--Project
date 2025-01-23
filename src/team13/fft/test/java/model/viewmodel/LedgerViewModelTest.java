////Contributing Authors: Minh Nguyen
package model.viewmodel;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class LedgerViewModelTest {

	@Test
	public void testLedgerViewModelConstructor() {
	    LedgerViewModel ledger = new LedgerViewModel("MN", "JANUARY", "50.0", "10.0", "40.0");

	    assertEquals("MN", ledger.getUser());
	    assertEquals("JANUARY", ledger.getMonth());
	    assertEquals("50.0", ledger.getDebit());
	    assertEquals("10.0", ledger.getCredit());
	    assertEquals("40.0", ledger.getBal());
	}



}


