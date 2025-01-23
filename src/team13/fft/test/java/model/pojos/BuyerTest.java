//Contributing Authors: K DeMerchant
package model.pojos;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import model.pojos.Buyer;

class BuyerTest {

    @Test
    void testInitials() {
        Buyer buyer = new Buyer("KD");

        assertEquals("KD", buyer.getInitials());
       
        
        Buyer buyer2 = new Buyer("YK");
        assertEquals(2, buyer2.getId());
    }

    @Test
    void testSetandGet() {
        Buyer buyer = new Buyer("YK");
        buyer.setTags("Gas");
        buyer.setTags("Food");

        assertTrue(buyer.getTags().contains("Food"));
        assertTrue(buyer.getTags().contains("Gas"));
        assertEquals(2, buyer.getTags().size());
    }
}