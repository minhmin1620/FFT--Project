//Contributing Authors: K DeMerchant, Mackenzie Carter
package model.pojos;

import java.util.ArrayList;

public class Buyer {
	
	private static int buyerId = 1;
	private int ID;
	private String initials;
	private ArrayList<String> tagList;
	
	public Buyer(String initialsIn) {
		ID = buyerId++;
		initials = initialsIn;
		tagList = new ArrayList<String>();
	}
	
	public Buyer(int idIn, String initialsIn) {
		ID = idIn;
		initials = initialsIn;
		tagList = new ArrayList<String>();
	}
	public void setTags(String tagIn) {
		tagList.add(tagIn);
	}
	
	public String getInitials() {
		return initials;
	}
	
	public String setInitials() {
		return initials;
	}
	
	public ArrayList<String> getTags() {
		return tagList;
	}
	
	public int getId() {
		return ID;
	}
	
	@Override
	public String toString() {
		return "ID: " + this.getId() + " | Initials: " + this.getInitials();
	}
}
