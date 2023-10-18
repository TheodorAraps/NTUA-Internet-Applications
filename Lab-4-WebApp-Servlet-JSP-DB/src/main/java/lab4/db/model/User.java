package lab4.db.model;

import java.util.Date;

public class User {

	private final Integer id;
	private final String username;
	private final String passwordHash;
	private final Date dateCreated;
	private final Integer userType;
	
	public User(String username, String passwordHash) {
		this.id = null;
		this.username = username;
		this.passwordHash = passwordHash;
		this.dateCreated = null;
		this.userType = null;
	}

	public User(Integer id, String username, String passwordHash, Date dateCreated, Integer userType) {
		this.id = id;
		this.username = username;
		this.passwordHash = passwordHash;
		this.dateCreated = dateCreated;
		this.userType = userType;
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

	public Integer getUserType() {
		return userType;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", passwordHash=" + passwordHash + ", dateCreated="
				+ dateCreated + ", userType=" + userType + "]";
	}

}
