package server.logic.model;

public class Item {
	
	private int    itemId;
	private String isbn, copyNumber;
	
	public Item(int itemId, String isbn, String copyNumber) {
		this.itemId = itemId;
		this.isbn = isbn;
		this.copyNumber = copyNumber;
	}
	
	public int getItemId() {
		return itemId;
	}
	
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	
	public String getIsbn() {
		return isbn;
	}
	
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
	public String getCopyNumber() {
		return copyNumber;
	}
	
	public void setCopyNumber(String copyNumber) {
		this.copyNumber = copyNumber;
	}
	
	@Override
	public String toString() {
		return "["+itemId+","+isbn+","+copyNumber+"]";
	}
}
