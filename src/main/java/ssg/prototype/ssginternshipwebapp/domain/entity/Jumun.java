package ssg.prototype.ssginternshipwebapp.domain.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
@Setter
@Entity
@IdClass(JumunId.class)
public class Jumun implements Serializable {
	@Id
	private Long customerId;

	@Id
	private int orderId;
	
	@Column
	private String orderedDate;
	
	@Column
	private int status;
	
	@Column
	private int code;
	
	@Column
	private int orderId0;
	
	@Builder
	public Jumun(Long customerId, int orderId, String orderedDate, int status, int code, int orderId0) {
		this.customerId = customerId;
		this.orderId = orderId;
		this.orderedDate = orderedDate;
		this.status = status;
		this.code = code;
		this.orderId0 = orderId0;
	}
}
