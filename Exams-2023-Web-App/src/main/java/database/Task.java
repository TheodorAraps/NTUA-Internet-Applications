package database;

import java.sql.Date;

public class Task {
	private final Integer taskID;
	private final String title;
	private final String desc;
	private final Integer statusID;
	private final Date date;
	
	public Task(Integer taskID, String title, String desc, Integer statusID, Date date) {

		this.taskID = taskID;
		this.title = title;
		this.desc = desc;
		this.statusID = statusID;
		this.date = date;
	}
	
	public Integer getTaskID() {
		return taskID;
	}

	public String getTitle() {
		return title;
	}
	
	public String getDesc() {
		if (desc.length() > 50) return desc.substring(0, 50);
		else return desc;
	}
	
	public Integer getStatusID() {
		return statusID;
	}
	
	public Date getDate() {
		return date;
	}
}
