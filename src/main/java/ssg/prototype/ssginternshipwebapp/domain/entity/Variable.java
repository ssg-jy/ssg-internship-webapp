package ssg.prototype.ssginternshipwebapp.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
public class Variable {
	@Id
	@Column(length = 20, nullable = false)
	private String key;
	
	
	@Column(nullable = false)
	private int value;

	@Builder
	public Variable(String key, int value) {
		this.key = key;
		this.value = value;
	}
}
