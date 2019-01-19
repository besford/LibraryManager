package server.logic.tables;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import server.logic.model.Fee;
import server.logic.model.Loan;
import utilities.Config;
import utilities.Trace;

public class FeeTable {
	
	private Logger logger = Trace.instanceOf().getLogger("opreation_file");
	List<Fee> feeList=new ArrayList<Fee>();
	
    private static class FeeListHolder {
        private static final FeeTable _INSTANCE = new FeeTable();
    }
    
    private FeeTable() {
    	//set up the default list with some instances
    	initialization();
    }
    
    public static final FeeTable instanceOf() {
        return FeeListHolder._INSTANCE;
    }
    
	public boolean lookUp(int userId) {
		boolean result = true;
		int amt = 0;
		
		boolean userHasFee = checkUser(userId);
		if (userHasFee) {
			for (Fee fee : feeList) 
				if(userId == fee.getUserId()) 
					amt = amt + fee.getFee();
		}
		else {
			amt = 0;
		}
		
		if (amt != 0) result = false;
		
		return result;
	}
	
	private boolean checkUser(int userId) {
		boolean result = true;
		int flag = 0;
		for (Fee fee : feeList) {
			if(fee.getUserId() == userId)
				flag = flag + 1;
			else
				flag = flag + 0;
		}	
		if (flag == 0) result = false;
		
		return result;
	}
	
	public int lookupFee(int userId) {
		int amt = 0;
		
		boolean userHasLoan = checkUser(userId);
		if (userHasLoan){
			for (Fee fee : feeList) 
				if(userId == fee.getUserId()) 
					amt = amt + fee.getFee();
		}
		else{
			amt = 0;
		}
		
		return amt;
	}
	
	public void applyFee(int userId, long time) {
		int flag = 0, index = 0, count = 0;
		for (Fee fee : feeList) {
			if (userId == fee.getUserId()) {
				flag = flag + 1;
				index = count;
			}
			
			count += 1;
		}
		
		int a = (int) ((time/(Config.STIMULATED_DAY))-Config.OVERDUE);
		if(flag != 0) {
			if(a >= 0) {
				feeList.get(index).setFee(a+feeList.get(index).getFee());
				feeList.get(index).setUserId(userId);
				logger.info(String.format("Operation:Apply OutStanding Fee;Fee Info:[%d,%d];State:Success", userId,a+feeList.get(index).getFee()));
			} 
			else {
				feeList.get(index).setFee(feeList.get(index).getFee());
				feeList.get(index).setUserId(userId);
				logger.info(String.format("Operation:Apply OutStanding Fee;Fee Info:[%d,%d];State:Success", userId,a+feeList.get(index).getFee()));
			}
		} 
		else {
			if (a >= 0) {
				Fee newFee = new Fee(userId, a);
				feeList.add(newFee);
				logger.info(String.format("Operation:Apply OutStanding Fee;Fee Info:[%d,%d];State:Success", userId, a));
			}
			else {
				Fee fee = new Fee(userId, 0);
				feeList.add(fee);
				logger.info(String.format("Operation:Apply OutStanding Fee;Fee Info:[%d,%d];State:Success", userId,0));
			}
		}
	}
	
	
	
	public void initialization(){
		feeList.clear();
    	Fee fee=new Fee(0,5);
    	feeList.add(fee);
		List<Loan> loanList = LoanTable.instanceOf().getLoanTable();
    	for (Loan loan : loanList)
    		applyFee(loan.getUserId(), new Date().getTime() - loan.getDate().getTime());
    	
    	logger.info(String.format("Operation:Initialize FeeTable;FeeTable: %s", feeList));
	}
	
	public String payFine(int userId) {
		String result = "";
		int amt = 0, index = 0, count = 0;
		
		boolean userHasFee = FeeTable.instanceOf().checkUser(userId);
		if (userHasFee) {
			for (Fee fee : feeList) {
				if (userId == fee.getUserId()) {
					amt = fee.getFee();
					index = count;
				}
				else {
					amt=0;
				}
				
				count += 1;
			}
		} 
		else {
			amt=0;
		}
		
		boolean oloan = LoanTable.instanceOf().lookLimit(userId);
		if (!oloan) {
			result = "Borrowing Items Exist";
			logger.info(String.format("Operation:Pay Fine;Fee Info:[%d,%d];State:Fail;Reason:Borrowing Items Exist.", userId, amt));
		}
		else{
			feeList.get(index).setUserId(userId);
			feeList.get(index).setFee(0);
			result = "success";
			logger.info(String.format("Operation:Pay Fine;Fee Info:[%d,%d];State:Success", userId, amt));
		}
		
		return result;
	}
	
	public List<Fee> getFeeTable() {
		return feeList;
	}
}
