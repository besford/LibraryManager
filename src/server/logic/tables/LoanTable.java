package server.logic.tables;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import server.logic.model.Loan;
import utilities.Config;
import utilities.Trace;

public class LoanTable {
	
	private Logger logger = Trace.instanceOf().getLogger("opreation_file");
	
	List<Loan> loanList=new ArrayList<Loan>();
	
    private static class LoanListHolder {
        private static final LoanTable _INSTANCE = new LoanTable();
    }
    
    private LoanTable() {
    	//set up the default list with some instances
    	initialization();
    }
    
    public static final LoanTable instanceOf() {
        return LoanListHolder._INSTANCE;
    }
    
	public String createloan(int userId, String isbn, String copyNumber, Date date) {
		String result = "";
		boolean foundUser = UserTable.instanceOf().lookUp(userId);
		boolean foundIsbn = TitleTable.instanceOf().lookup(isbn);
		boolean foundCopyNumber = ItemTable.instanceOf().lookup(isbn,copyNumber);
		boolean oloan = LoanTable.instanceOf().lookup(userId,isbn,copyNumber);
		boolean limit = LoanTable.instanceOf().checkLimit(userId);
		boolean fee = FeeTable.instanceOf().lookUp(userId);
		
		if (!foundUser) {
			result="User Invalid";
			logger.info(String.format("Operation:Borrow Item;Loan Inf"
					+ "o:[%d,%s,%s,%s];State:Fail;Reason:Invalid User.", userId,isbn,copyNumber,dateFormat(date)));
		}
		else if (!foundIsbn) {
			result="ISBN Invalid";
			logger.info(String.format("Operation:Borrow Item;Loan Info:[%d,%s,%s,%s];State:Fail;Reason:Invalid ISBN.", userId,isbn,copyNumber,dateFormat(date)));
		}
		else if (!foundCopyNumber) {
			result="Copynumber Invalid";
			logger.info(String.format("Operation:Borrow Item;Loan Info:[%d,%s,%s,%s];State:Fail;Reason:Invalid Copynumber.", userId,isbn,copyNumber,dateFormat(date)));
		}
		else {
			if (oloan) {
				if (limit && fee) {
					Loan loan=new Loan(userId, isbn, copyNumber, date, "0");
					loanList.add(loan);
					result = "success";
					logger.info(String.format("Operation:Borrow Item;Loan Info:[%d,%s,%s,%s];State:Success", userId,isbn,copyNumber,dateFormat(date)));
				}
				else if (!limit) {
					result="The Maximun Number of Items is Reached";
					logger.info(String.format("Operation:Borrow Item;Loan Info:[%d,%s,%s,%s];State:Fail;Reason:The Maximun Number of Items is Reached.", userId,isbn,copyNumber,dateFormat(date)));
				}
				else if (!fee) {
					result="Outstanding Fee Exists";
					logger.info(String.format("Operation:Borrow Item;Loan Info:[%d,%s,%s,%s];State:Fail;Reason:Outstanding Fee Exists.", userId,isbn,copyNumber,dateFormat(date)));
				}
			}
			else {
				result="The Item is Not Available";
				logger.info(String.format("Operation:Borrow Item;Loan Info:[%d,%s,%s,%s];State:Fail;Reason:The Item is Not Available.", userId,isbn,copyNumber,dateFormat(date)));
			}
		}
		
    	return result;
	}
	
	
	public boolean lookup(int userId, String isbn, String copyNumber) {
		boolean result = true;
		int flag = 0;
		
		for (Loan loan : loanList) {
			String loanIsbn = loan.getIsbn();
			String loanCopyNum = loan.getCopyNumber();
			
			if (loanIsbn.equalsIgnoreCase(isbn) && loanCopyNum.equalsIgnoreCase(copyNumber))
				flag = flag + 1;
			else
				flag = flag + 0;
		}
		
		if (flag != 0) result = false;
		
		return result;
	}
    
	public boolean checkLimit(int userId) {
		boolean result = true;
		int flag = 0;
		
		for (Loan loan : loanList) {
			int loanUserId = loan.getUserId();
			
			if (loanUserId == userId)
				flag = flag + 1;
			else
				flag = flag + 0;
		}
		
		if (flag >= Config.MAX_BORROWED_ITEMS) result = false;
		
		return result;
	}

	public String renewal(int userId, String isbn, String copyNumber, Date date) {
		String result="";
		int flag = 0, index = 0, count = 0;
		
		boolean limit = LoanTable.instanceOf().checkLimit(userId);
		boolean fee = FeeTable.instanceOf().lookUp(userId);
		
		for (Loan loan : loanList) {
			String loanIsbn = loan.getIsbn();
			String loanCopyNum = loan.getCopyNumber();
			int loanUserId = loan.getUserId();
			
			if ((loanUserId == userId) && loanIsbn.equalsIgnoreCase(isbn) && loanCopyNum.equalsIgnoreCase(copyNumber)) {
				flag = flag + 1;
				index = count;
			}
			else {
				flag = flag + 0;
			}
			
			count++;
		}
		
		if (limit && fee) {
			if (flag != 0) {
				if (loanList.get(index).getRenewState().equalsIgnoreCase("0")) {
					loanList.get(index).setUserId(userId);
					loanList.get(index).setIsbn(isbn);
					loanList.get(index).setCopyNumber(copyNumber);
					loanList.get(index).setDate(new Date());
					loanList.get(index).setRenewState("1");
					result = "success";
					logger.info(String.format("Operation:Renew Item;Loan Info:[%d,%s,%s,%s];State:Success", userId,isbn,copyNumber,dateFormat(date)));
				}
				else {
					result = "Renewed Item More Than Once for the Same Loan";
					logger.info(String.format("Operation:Renew Item;Loan Info:[%d,%s,%s,%s];State:Fail;Reason:Renewed Item More Than Once for the Same Loan.", userId,isbn,copyNumber,dateFormat(date)));
				}
			}
			else {
				result = "The loan does not exist";
				logger.info(String.format("Operation:Renew Item;Loan Info:[%d,%s,%s,%s];State:Fail;Reason:The loan does not exist.", userId,isbn,copyNumber,dateFormat(date)));
			}
		} 
		else if (!limit) {
			result = "The Maximun Number of Items is Reached";
			logger.info(String.format("Operation:Renew Item;Loan Info:[%d,%s,%s,%s];State:Fail;Reason:The Maximun Number of Items is Reached.", userId,isbn,copyNumber,dateFormat(date)));
		} 
		else if (!fee) {
			result = "Outstanding Fee Exists";
			logger.info(String.format("Operation:Renew Item;Loan Info:[%d,%s,%s,%s];State:Fail;Reason:Outstanding Fee Exists.", userId,isbn,copyNumber,dateFormat(date)));
		}
		
		return result;
	}
    
	private String dateFormat(Date date) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String datestr = format.format(date);
		return datestr;
	}
	
	public String returnItem(int userId, String isbn, String copyNumber, Date date) {
		String result="";
		int flag=0, index = 0, count = 0;
		
		for (Loan loan : loanList) {
			String loanIsbn = loan.getIsbn();
			String loanCopyNum = loan.getCopyNumber();
			int loanUserId = loan.getUserId();
			
			if((loanUserId == userId) && loanIsbn.equalsIgnoreCase(isbn) && loanCopyNum.equalsIgnoreCase(copyNumber)) {
				flag = flag + 1;
				index = count;
			}
			else{
				flag=flag+0;	
			}
			
			count += 1;
		}
		if (flag != 0) {
			long time = date.getTime()-loanList.get(index).getDate().getTime();
			loanList.remove(index);
			logger.info(String.format("Operation:Return Item;Loan Info:[%d,%s,%s,%s];State:Success", userId,isbn,copyNumber,dateFormat(date)));
			if (time>Config.OVERDUE*Config.STIMULATED_DAY)
				FeeTable.instanceOf().applyFee(userId, time);
			
			result="success";
		}
		else {
			result="The Loan Does Not Exist";
			logger.info(String.format("Operation:Return Item;Loan Info:[%d,%s,%s,%s];State:Fail;Reason:The Loan Does Not Exist.", userId,isbn,copyNumber,dateFormat(date)));
		}
		
		return result;
	}
	
	public boolean lookLimit(int userId) {
		boolean result = true;
		int flag = 0;
		
		for (Loan loan : loanList) {
			int loanUserId = loan.getUserId();
			
			if (loanUserId == userId)
				flag = flag + 1;
			else 
				flag = flag + 0;
		}
		
		if (flag != 0) result = false;
		
		return result;
	}
	
	public boolean checkLoan(int userId) {
		boolean result = true;
		int flag=0;
		
		for (Loan loan : loanList) {
			if (loan.getUserId() == userId)
				flag = flag + 1;
			else 
				flag = flag + 0;
		}
		
		if (flag != 0) result = false;
		
		return result;
	}
	public boolean checkLoan(String isbn, String copyNumber) {
		boolean result = true;
		int flag = 0;
		
		for (Loan loan : loanList) {
			String loanIsbn = loan.getIsbn();
			String loanCopyNum = loan.getCopyNumber();
			
			if (loanIsbn.equalsIgnoreCase(isbn) && loanCopyNum.equalsIgnoreCase(copyNumber))
				flag = flag + 1;
			else
				flag = flag + 0;
		}
		
		if (flag != 0) result = false;
		
		return result;
	}
	
	public boolean checkLoan(String isbn) {
		boolean result = true;
		int flag = 0;
		
		for (Loan loan : loanList) {
			String loanIsbn = loan.getIsbn();
			
			if(loanIsbn.equalsIgnoreCase(isbn))
				flag=flag+1;
			else
				flag=flag+0;	
		}
		
		if (flag != 0) result = false;
		
		return result;
	}
	
	public void initialization() {
		loanList.clear();
		Loan loan = new Loan(0,"9781442668584", "1", new Date(), "0");
    	loanList.add(loan);
    	logger.info(String.format("Operation:Initialize LoanTable;LoanTable: %s", loanList));
	}
	
	public List<Loan> getLoanTable() {
		return loanList;
	}
}
