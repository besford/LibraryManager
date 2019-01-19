package server.logic.tables;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import server.logic.model.User;
import utilities.Trace;

public class UserTable {
	
	private Logger logger = Trace.instanceOf().getLogger("opreation_file");
	List<User> userList = new ArrayList<User>();
	
	
	private static class UserListHolder {
		private static final UserTable _INSTANCE = new UserTable();
	}
	 
	private UserTable() {
		String[] passwordList = new String[]{"Zhibo","Yu","Michelle","Kevin","Sun"};
    	String[] usernameList = new String[]{"Zhibo@carleton.ca","Yu@carleton.ca","Michelle@carleton.ca","Kevin@carleton.ca","Sun@carleton.ca"};
    	
    	for (int id = 0; id < usernameList.length; id++) {
    		User user = new User(id, usernameList[id], passwordList[id]);
    		userList.add(user);
    	}
    	
    	logger.info(String.format("Operation:Initialize UserTable;UserTable: %s", userList));
	}
	 
	public static final UserTable instanceOf() {
		return UserListHolder._INSTANCE;
	}
	 
	public boolean createUser(String username, String password) {
		boolean result = true;
		int flag = 0;
		
		for (User user : userList) {
			String email = user.getUsername();
			if (email.equalsIgnoreCase(username))
				flag = flag + 1;
			else
				flag = flag + 0;
		}
		
		if (flag == 0) {
			User newuser = new User(userList.size(), username, password);
			result = userList.add(newuser);
			logger.info(String.format("Operation:Create New User;User Info:[%s,%s];State:Success", username, password));
		}
		else {
			result = false;
			logger.info(String.format("Operation:Create New User;User Info:[%s,%s];State:Fail;Reason:The User already existed.", username, password));
		}
		
		return result;
	}
	
	public String delete(int userId) {
		//Since the userid in "User Creation" is automatically assigned to the user,upon its creation.
		//Each user has a unique userid.Even it is deleted,its userid can not be assigned to other user.
		//To maintain the correctness of the data,here instead delete index from the List.
		//I choose to remove the user's information instead the whole index.Keep its userid as reference.
		String result = "";
		int flag = 0, index = 0, count = 0;
		
		for (User user : userList) {
			if (user.getUserId() == userId) {
				index = count;
				flag = flag + 1;
			}
			else {
				flag = flag + 0;
			}
			
			count += 1;
		}
		
		if (flag == 0) {
			result="The User Does Not Exist";
			logger.info(String.format("Operation:Delete User;User Info:[%s,%s];State:Fail;Reason:The User Does Not Exist.", "N/A","N/A"));
		}
		else {
			boolean userHasLoan = LoanTable.instanceOf().checkLoan(userId);
			boolean userHasFee = FeeTable.instanceOf().lookUp(userId);
			String username = userList.get(index).getUsername();
			String password = userList.get(index).getPassword();
			
			if (userHasFee && userHasLoan) {
				userList.get(index).setUserId(userId);
				userList.get(index).setPassword("N/A");
				userList.get(index).setUsername("N/A");
				result = "success";
				logger.info(String.format("Operation:Delete User;User Info:[%s,%s];State:Success", username ,password));
			} 
			else if(!userHasFee) {
				result = "Outstanding Fee Exists";
				logger.info(String.format("Operation:Delete User;User Info:[%s,%s];State:Fail;Reason:Outstanding Fee Exists.", username, password));
			}
			else if(!userHasLoan) {
				result = "Active Loan Exists";
				logger.info(String.format("Operation:Delete User;User Info:[%s,%s];State:Fail;Reason:Active Loan Exists.", username, password));
			}
		}
    
		return result;
	}
	 
	public boolean lookUp(int targetId) {
		boolean result = true;
		int flag = 0;
		
		for(User user : userList) {
			int userId = user.getUserId();
			
			if (userId == targetId)
				flag = flag + 1;
			else
				flag = flag + 0;	
		}
		
		if (flag == 0) result = false;
		
		return result;
	}
	 
	public int lookUp(String username) {
		int userid = -1;
		
		for (int id = 0; id < userList.size(); id++) 
			if(userList.get(id).getUsername().equalsIgnoreCase(username))
				userid = id;
		
		return userid;
	}
	 
	public int checkUser(String username, String password) {
		int result = 0, flag = 0, index = 0;

		for (int id = 0; id < userList.size(); id++){
			if (userList.get(id).getUsername().equalsIgnoreCase(username)) {
				flag = flag + 1;
				index = id;
			}
			else{
				flag = flag + 0;
			}
		}
		
		boolean passCorrect = userList.get(index).getPassword().equalsIgnoreCase(password);
		if (flag != 0 && passCorrect)
			result = 0;
		else if(flag == 0)
			result = 2;
		else if(!passCorrect)
			result = 1;
		
		return result;
	}
	
	public List<User> getUserTable() {
		return userList;
	}
}
