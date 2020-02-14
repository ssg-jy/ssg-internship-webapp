package ssg.prototype.ssginternshipwebapp.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
@Entity
public class JumunStatus {
	@Id
	private int orderId;
	
	@Column
	private int status;
	
	@Builder
	public JumunStatus(int orderId, int status) {
		this.orderId = orderId;
		this.status = status;
	}
}
