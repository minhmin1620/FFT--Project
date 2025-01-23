
//@author Mackenzie Carter 3756279

package model.pojos;
import java.time.LocalDate;
public class BankTransaction extends Transaction {

   private double credit;
   private double debit; 
   private double balance; 
   private String buyer;
   private String category;
   
   public BankTransaction(String dateIn, String descIn, double cIn, double dIn, double balIn,String buyerIn, String categoryIn) {
	   super(dateIn, descIn);
	   credit = cIn;
	   debit = dIn;
	   balance = balIn;
	   buyer = buyerIn;
	   category = categoryIn;
   }
   
   public LocalDate getDate() {
	   return super.getDate();
   }
   
   public String getDescription() {
	   return super.getDescription();
   }
   
   public double getCredit() {
	   return credit;
   }
   
   public double getDebit() {
	   return debit;
   }
   
   public double getBalance() {
	   return balance;
   }
   
   public String getBuyer() {
	   return buyer;
   }

   public String getCategory() {
	return category;
   }
   public void setCategory(String categoryIn) {
	   category = categoryIn;
   }
   public void setBuyer(String buyerIn) {
	   buyer = buyerIn;
   }
   

}
