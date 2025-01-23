
//@author Mackenzie Carter 3756279
package model.pojos;
import java.util.ArrayList;
import java.time.Month;
public class TransactionList {
	private Month month; 
	private ArrayList<BankTransaction> list;
	public TransactionList(Month monthIn) {
		month = monthIn;
		list = new ArrayList<BankTransaction>();
	}
	
	public Month getMonth() {
		return month;
	}
	
	public void addTransaction(BankTransaction tIn) {
		list.add(tIn);
	}
	
	public void addStatement(BankStatement BSIn) {
		ArrayList<BankTransaction> list = BSIn.getList();
		for (BankTransaction t: list) {
			this.list.add(t);
		}
	}
}
