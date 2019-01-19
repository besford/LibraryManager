package server.logic.tables;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import server.logic.model.Item;
import utilities.Trace;

public class ItemTable {
	
	private Logger logger = Trace.instanceOf().getLogger("opreation_file");
	List<Item> itemList = new ArrayList<Item>();
    
	private static class ItemListHolder {
        private static final ItemTable _INSTANCE = new ItemTable();
    }
    
	private ItemTable() {
    	initialization();
    }
	
    public static final ItemTable instanceOf() {
        return ItemListHolder._INSTANCE;
    }
	
    public boolean createItem(String isbn) {
		boolean result = true;
		result = TitleTable.instanceOf().lookup(isbn);
		
		if (result) {
			int flag = 0;
			
			for (Item item : itemList) {
				if (item.getIsbn().equalsIgnoreCase(isbn))
					flag = flag + 1;
				else
					flag = flag + 0;
			}
			
			Item newitem = new Item(itemList.size(), isbn, String.valueOf(flag + 1));
			itemList.add(newitem);
			logger.info(String.format("Operation:Create New Item;Item Info:[%s,%s];State:Success", isbn, String.valueOf(flag + 1)));
		}
		else{
			result = false;
			logger.info(String.format("Operation:Create New Item;Item Info:[%s,%s];State:Fail;Reason:No such ISBN existed.", isbn,"N/A"));
		}
		
		return result;
	}
    
    public boolean lookup(String isbn, String copyNumber) {
		boolean result = true;
		int flag = 0;
		
		for (Item item : itemList) {
			String foundIsbn = item.getIsbn();
			String foundCopyNumber = item.getCopyNumber();
			
			if (foundIsbn.equalsIgnoreCase(isbn) && foundCopyNumber.equalsIgnoreCase(copyNumber))
				flag = flag + 1;
			else
				flag = flag + 0;	
		}
		
		if (flag == 0) result = false;
		
		return result;
	}
	
	public Object delete(String isbn, String copyNumber) {
		//Since the itemid and copynumber in is automatically assigned to the item,upon its creation.
		//Each item has a unique itemid and copynumber.Even it is deleted,they can not be assigned to other item.
		//To maintain the correctness of the data,here instead delete index from the List.
		//I choose to remove the item's information instead the whole index.
		String result="";
		int flag = 0, index = 0, count = 0;
		for(Item item : itemList) {
			String itemIsbn = item.getIsbn();
			String itemCopyNum = item.getCopyNumber();
			
			if (itemIsbn.equalsIgnoreCase(isbn) && itemCopyNum.equalsIgnoreCase(copyNumber)) {
				index = count;
				flag= flag + 1;
			}
			else{
				flag=flag+0;
			}
			
			count += 1;
		}
		
		if (flag != 0) {
			boolean loan = LoanTable.instanceOf().checkLoan(isbn,copyNumber);
			if (!loan) {
				itemList.get(index).setCopyNumber("N/A");
				result="success";
				logger.info(String.format("Operation:Delete Item;Item Info:[%s,%s];State:Success", isbn,"N/A"));
			}
			else {
				result = "Active Loan Exists";
				logger.info(String.format("Operation:Delete Item;Item Info:[%s,%s];State:Fail;Reason:The item is currently on loan.", isbn,copyNumber));
			}
		}
		else {
			result = "The Item Does Not Exist";
			logger.info(String.format("Operation:Delete Item;Item Info:[%s,%s];State:Fail;Reason:The Item Does Not Exist.", isbn,copyNumber));
		}
		
		return result;
	}
	
	public void deleteAll(String isbn) {
		for (int i = 0; i < itemList.size(); i++) {
			if (isbn.equalsIgnoreCase(itemList.get(i).getIsbn())) {
				itemList.get(i).setIsbn("N/A");
				itemList.get(i).setCopyNumber("N/A");
				logger.info(String.format("Operation:Delete Item Due to Title Deletion;ISBN Info:[%s];State:Success", isbn));
			}
		}
	}
	
	public void initialization() {
		itemList.clear();
		String[] isbnList = new String[]{"9781442668584", "9781442616899", "9781442667181", "9781611687910"};
    	String[] cnList = new String[]{"1", "1", "1", "1"};
    	
    	for (int i = 0; i < isbnList.length; i++) {
			Item item = new Item(i, isbnList[i], cnList[i]);
			itemList.add(item);
		}
    	
    	logger.info(String.format("Operation:Initialize ItemTable;ItemTable: %s", itemList));
	}
	
	public List<Item> getItemTable() {
		return itemList;
	}
}