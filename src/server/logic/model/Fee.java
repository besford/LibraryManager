package server.logic.model;

public class Fee {
	
	private int userId, fee;
	
	public Fee(int userId,int fee){
		this.userId=userId;
		this.fee=fee;
	}
	
	public String toString(){
		return "["+userId+","+fee+"]";
	}
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public int getFee() {
		return fee;
	}
	
	public void setFee(int fee) {
		this.fee = fee;
	}
}
