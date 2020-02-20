package ssg.prototype.ssginternshipwebapp;

public class OrdCode {
	public static final int ORDER = 0;
	public static final int ADD_ORD = 1;
	public static final int PART_CANCEL = 2;
	public static final int ALL_CANCEL = 3;
	public static final int RETURN = 4;
	
	public static final String[] code_string = {"주문", "주문더하기", "부분취소", "전체취소", "반환"};
	public static final int[] code_int = {ORDER, ADD_ORD, PART_CANCEL, ALL_CANCEL, RETURN};
}
