package server.logic.tables;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import server.logic.model.Title;
import utilities.Trace;

public class TitleTable {
	
	private Logger logger = Trace.instanceOf().getLogger("opreation_file");
	List<Title> titleList = new ArrayList<Title>();
    
	private static class TitleListHolder {
        private static final TitleTable _INSTANCE = new TitleTable();
    }
    
    private TitleTable() {
    	//set up the default list with some instances
    	initialization();
    }
    
    public static final TitleTable instanceOf() {
        return TitleListHolder._INSTANCE;
    }
    
	public boolean createtitle(String isbn, String bookTitle) {		
		boolean result = true;
		int flag = 0;
		
		for (Title title : titleList) {
			String titleIsbn = title.getIsbn();
			if (titleIsbn.equals(isbn))
				flag = flag + 1;
			else
				flag = flag + 0;
		}
		
		if (flag == 0) {
			Title newtitle=new Title(isbn,bookTitle);
			result = titleList.add(newtitle);
			logger.info(String.format("Operation:Create New Title;Title Info:[%s,%s];State:Success", isbn,bookTitle));
		}
		else{
			result = false;
			logger.info(String.format("Operation:Create New Title;Title Info:[%s,%s];State:Fail;Reason:The ISBN already existed.", isbn,bookTitle));
		}
		
		return result;	
	}
	
	public boolean lookup(String isbn) {
		boolean result = true;
		int flag = 0;
		
		for (Title title : titleList) {
			String titleIsbn = title.getIsbn();
			if (titleIsbn.equals(isbn))
				flag = flag + 1;
			else
				flag = flag + 0;
		}
		
		if(flag == 0) result = false;
		
		return result;
	}
	
	public String deleteTitle(String isbn) {
		String result = "";
		int index = 0, flag = 0, count = 0;
		
		for (Title title : titleList) {
			if (title.getIsbn().equals(isbn)) {
				flag = flag + 1;
				index = count;
			}
			else {
				flag = flag + 0;
			}
			
			count += 1;
		}
		
		if (flag != 0) {
			boolean loan = LoanTable.instanceOf().checkLoan(isbn);
			
			if (loan) {
				String bookTitle = titleList.get(index).getBookTitle();
				ItemTable.instanceOf().deleteAll(isbn);
				titleList.remove(index);
				result = "success";
				logger.info(String.format("Operation:Delete Title;Title Info:[%s,%s];State:Success", isbn, bookTitle));
			}
			else{
				result = "Active Loan Exists";
				logger.info(String.format("Operation:Delete Title;ISBN Info:[%s];State:Fail;Reason:Active Loan Exists.", isbn));
			}
		}
		else {
			result = "The Title Does Not Exist";
			logger.info(String.format("Operation:Delete Title;ISBN Info:[%s];State:Fail;Reason:The Title Does Not Exist.", isbn));
		}
		
		return result;
	}
	
	public void initialization() {
		titleList.clear();
		String[] isbnList = new String[]{"9781442668584", "9781442616899", "9781442667181", "9781611687910", "9781317594277"};
    	String[] titlenameList = new String[]{"By the grace of God", "Dante's lyric poetry ", "Courtesy lost", "Writing for justice", "The act in context"};
    	
    	for (int i = 0; i < isbnList.length; i++) {
    		Title detitle = new Title(isbnList[i], titlenameList[i]);
    		titleList.add(detitle);
		}
    	
    	logger.info(String.format("Operation:Initialize TitleTable;TitleTable: %s", titleList));
	}
	
	public List<Title> getTitleTable() {
		return titleList;
	}
}
