package server.logic.model;

public class User {
	private int    userId;
	private String username, password;
	
	public User(int userId, String username, String password) {
		this.userId = userId;
		this.password = password;
		this.username = username;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "["+userId+","+username+","+password+"]";
	}
}
