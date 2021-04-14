package org.triamici;

public class MenuItem implements Comparable<MenuItem> {

	private int itemNumber;
	private String menuText;
	private short accessLevel = 0;
	
	public MenuItem() {
	}
	
	public MenuItem(int itemNumber, String menuText, short accessLevel) {
		this.itemNumber = itemNumber;
		this.menuText = menuText;
		this.accessLevel = accessLevel;
	}
	
	/**
	 * @return the itemNumber
	 */
	public int getItemNumber() {
		return itemNumber;
	}
	
	/**
	 * @param itemNumber the itemNumber to set
	 */
	public void setItemNumber(int itemNumber) {
		this.itemNumber = itemNumber;
	}
	
	/**
	 * @return the menuText
	 */
	public String getMenuText() {
		return menuText;
	}
	
	/**
	 * @param menuText the menuText to set
	 */
	public void setMenuText(String menuText) {
		this.menuText = menuText;
	}
	
	/**
	 * @return the accessLevel
	 */
	public short getAccessLevel() {
		return accessLevel;
	}
	
	/**
	 * @param accessLevel the accessLevel to set
	 */
	public void setAccessLevel(short accessLevel) {
		this.accessLevel = accessLevel;
	}
	
	@Override
	public int compareTo(MenuItem item) {
		return this.itemNumber - item.getItemNumber();
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return this.itemNumber;
	}
}
