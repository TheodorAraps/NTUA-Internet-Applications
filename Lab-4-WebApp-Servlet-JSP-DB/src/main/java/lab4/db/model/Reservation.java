package lab4.db.model;

import java.util.Date;

public class Reservation {

	final Integer userId;
	final Integer productId;
	final Date startDate;
	final Date endDate;
	final String comments;
	
	public Reservation(Integer userId, Integer productId, Date startDate, Date endDate, String comments) {
		super();
		this.userId = userId;
		this.productId = productId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.comments = comments;
	}

	public Integer getUserId() {
		return userId;
	}

	public Integer getProductId() {
		return productId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public String getComments() {
		return comments;
	}

	@Override
	public String toString() {
		return "Reservation [userId=" + userId + ", productId=" + productId + ", startDate=" + startDate + ", endDate="
				+ endDate + ", comments=" + comments + "]";
	}
	

}
