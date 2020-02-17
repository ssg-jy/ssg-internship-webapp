package ssg.prototype.ssginternshipwebapp;

public class OrdStat {
	public static final int ORD_COMPLETE = 0;
	public static final int DLV_ING = 1;
	public static final int DLV_COMPLETE = 2;
	
	public static final String[] stat_string = {"주문완료", "배송중", "배송완료"};
	public static final int[] stat_int = {ORD_COMPLETE, DLV_ING, DLV_COMPLETE};
}
