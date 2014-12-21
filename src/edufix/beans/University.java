package edufix.beans;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

/**
 * Contains all the Properties of an University
 * @author balaji i
 *
 */

@Table("university_details")
public class University {

	@Column("university_name")
	private String universityName;
	
	@PrimaryKey("university_id")
	private Long universityRegistrationNumber;
	
	@Column("alumni_count")
	private Long alumniCount;
	
	@Column("awards")
	private Map<String,Date> awards;

	@Column("location")
	private String location;
	
	@Column("courses")
	private List<String> courses;
	
	
	public List<String> getCourses() {
		return courses;
	}

	public void setCourses(List<String> courses) {
		this.courses = courses;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getUniversityName() {
		return universityName;
	}

	public void setUniversityName(String universityName) {
		this.universityName = universityName;
	}

	public Long getUniversityRegistrationNumber() {
		return universityRegistrationNumber;
	}

	public void setUniversityRegistrationNumber(Long universityRegistrationNumber) {
		this.universityRegistrationNumber = universityRegistrationNumber;
	}

	public Long getAlumniCount() {
		return alumniCount;
	}

	public void setAlumniCount(Long alumniCount) {
		this.alumniCount = alumniCount;
	}

	public Map<String, Date> getAwards() {
		return awards;
	}

	public void setAwards(Map<String, Date> awards) {
		this.awards = awards;
	}
	
}
