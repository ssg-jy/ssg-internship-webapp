package ssg.prototype.ssginternshipwebapp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Status {
	String text;
	boolean now;
	
	public Status(String text, boolean now) {
		this.text = text;
		this.now = now;
	}
	
	public boolean getNow() {
		return this.now;
	}
}
