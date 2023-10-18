package database;

import java.util.Date;

public class User {

	private final Integer id;
	private final String username;
	private final String passwordHash;
	private final Date dateCreated;
	private final Integer roleID;
	
	public User(String username, String passwordHash) {
		this.id = null;
		this.username = username;
		this.passwordHash = passwordHash;
		this.dateCreated = null;
		this.roleID = null;
	}

	public User(Integer id, String username, String passwordHash, Date dateCreated, Integer roleID) {
		this.id = id;
		this.username = username;
		this.passwordHash = passwordHash;
		this.dateCreated = dateCreated;
		this.roleID = roleID;
	}

	public Integer getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public Integer getRoleID() {
		return roleID;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", passwordHash=" + passwordHash + ", dateCreated="
				+ dateCreated + ", userType=" + roleID + "]";
	}

}
