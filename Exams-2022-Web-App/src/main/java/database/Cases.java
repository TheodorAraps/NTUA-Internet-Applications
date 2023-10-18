package database;

import java.util.Date;

public class Cases {

	final Integer id;
	final Integer userId;
	final Date diagnosisDate;
	final Integer diagnosisMethodId;
	final String diagnosisLocation;
	final String diagnosisReportUid;
	final String name;
	
	public Cases(Integer id, Integer userId, Date diagnosisDate, Integer diagnosisMethodId, String diagnosisLocation, String diagnosisReportUid) {
		super();
		this.id = id;
		this.userId = userId;
		this.diagnosisDate = diagnosisDate;
		this.diagnosisMethodId = diagnosisMethodId;
		this.diagnosisLocation = diagnosisLocation;
		this.diagnosisReportUid = diagnosisReportUid;
		this.name = null;
	}
	
	public Cases(Integer id, Integer userId, Date diagnosisDate, Integer diagnosisMethodId, String diagnosisLocation, String diagnosisReportUid, String name) {
		super();
		this.id = id;
		this.userId = userId;
		this.diagnosisDate = diagnosisDate;
		this.diagnosisMethodId = diagnosisMethodId;
		this.diagnosisLocation = diagnosisLocation;
		this.diagnosisReportUid = diagnosisReportUid;
		this.name = name;
	}

	public Integer getUserId() {
		return userId;
	}

	public Date getDiagnosisDate() {
		return diagnosisDate;
	}

	public Integer getDiagnosisMethodId() {
		return diagnosisMethodId;
	}

	public String getDiagnosisLocation() {
		return diagnosisLocation;
	}

	public String getDiagnosisReportUid() {
		return diagnosisReportUid;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Reservation [userId=" + userId + ", productId=" + diagnosisDate + ", startDate=" + diagnosisMethodId + ", endDate="
				+ diagnosisLocation + ", comments=" + diagnosisReportUid + ", name=" + name + "]";
	}
	

}
