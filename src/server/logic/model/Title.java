package server.logic.model;

public class Title {
	private String isbn, bookTitle;

	public Title(String isbn, String bookTitle) {
		this.isbn = isbn;
		this.bookTitle = bookTitle;
	}
	
	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	@Override
	public String toString() {
		return "["+isbn+","+bookTitle+"]";
	}
}
