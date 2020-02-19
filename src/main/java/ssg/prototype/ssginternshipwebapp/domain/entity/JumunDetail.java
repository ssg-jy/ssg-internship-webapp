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
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
@Entity
@IdClass(JumunDetailId.class)
public class JumunDetail implements Serializable {

	@Id
	private int orderId;
	
	@Id
	private Long productId;
	
	@Column
	private int qty;
	
	@Column
	private int itemCode;
	
	@Builder
	public JumunDetail(int orderId, Long productId, int qty, int itemCode) {
		this.orderId = orderId;
		this.productId = productId;
		this.qty = qty;
		this.itemCode = itemCode;
	}
}
