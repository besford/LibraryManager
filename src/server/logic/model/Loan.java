package server.logic.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Loan {
	
	private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private int 	userId;
	private Date 	date;
	private String 	isbn, copyNumber;
	private String 	renewState;
	
	public Loan(int userId, String isbn, String copyNumber, Date date, String renewState) {
		this.userId = userId;
		this.isbn = isbn;
		this.copyNumber = copyNumber;
		this.date = date;
		this.renewState = renewState;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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

	public String getRenewState() {
		return renewState;
	}

	public void setRenewState(String renewState) {
		this.renewState = renewState;
	}
	
	@Override
	public String toString(){
		return "["+userId+","+isbn+","+copyNumber+","+format.format(this.date)+","+renewState+"]";
	}
}
