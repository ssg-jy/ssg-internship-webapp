package ssg.prototype.ssginternshipwebapp.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

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
