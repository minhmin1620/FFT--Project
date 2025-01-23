//Contributing Authors: K DeMerchant
package model.pojos;

import java.util.ArrayList;
//Contributing Authors: Mackenzie Carter
public class BankStatement {
	
	private ArrayList<BankTransaction> transactions;
	
	public BankStatement(ArrayList<BankTransaction> transIn) {
		transactions = transIn;
	}
	
	public ArrayList<BankTransaction> getList() {
		return transactions;
	}
	
	public void addTransaction(BankTransaction transIn) {
		transactions.add(transIn);
	}
}
