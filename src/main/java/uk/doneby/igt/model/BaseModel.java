package uk.doneby.igt.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class BaseModel {
		
	@Id
	@Column(name = "ID")
	@GeneratedValue
	private Long id;

	@Column(name = "CDATE", nullable = false)
	private Date cdate;

	@Column(name = "MDATE", nullable = false)
	private Date mdate;
	
	@Column(name = "CREATED_BY", nullable = false)
    @CreatedBy
    private User createdBy;
	
	@Column(name = "modified_by", nullable = false)
    @LastModifiedBy
    private User modifiedByUser;
	
	@PrePersist
	void onCreate() {
		Date now = new Date();
		this.setCdate(now);
		this.setMdate(now);
	}
	
	@PreUpdate
	void onPersist() {
		this.setMdate(new Date());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCdate() {
		return cdate;
	}

	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}

	public Date getMdate() {
		return mdate;
	}

	public void setMdate(Date mdate) {
		this.mdate = mdate;
	}
	


	public User  getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User  createdBy) {
		this.createdBy = createdBy;
	}

	public User  getModifiedByUser() {
		return modifiedByUser;
	}

	public void setModifiedByUser(User  modifiedByUser) {
		this.modifiedByUser = modifiedByUser;
	}
	
	public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append("id: '").append(id).append("', ")
		.append("cdate: '").append(cdate).append("', ")
		.append("mdate: '").append(mdate).append("', ")
		.append("createdBy: '").append(createdBy).append("', ")
		.append("modifiedByUser: '").append(modifiedByUser).append("', ").append(super.toString());

		return sb.toString();
	}
}
