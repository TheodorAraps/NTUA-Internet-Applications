package database;

public class User {
	private final String username;
	private final String password;
	private final Integer roleID;
	private final Integer userID;
	
	public User(String username, String password, Integer roleID, Integer userID) {

		this.username = username;
		this.password = password;
		this.roleID = roleID;
		this.userID = userID;
	}
	
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	
	public Integer getRoleID() {
		return roleID;
	}
	public Integer getUserID() {
		return userID;
	}
}
