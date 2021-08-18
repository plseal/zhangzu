package com.plseal.zhangzu.entity;

/**
 * ZhangzuAnalysis
 */
public class ZhangzuAnalysis {
	private int id;
	private String ac;
	private String ac_type;
	private long ac_plus;
	private long ac_min;
	private long ac_result;
	private long ac_maihuo;
	public void setId(int id){
		this.id=id;
	}
	public int getId(){
		return id;
	}
	public void setAc(String ac){
		this.ac=ac;
	}
	public String getAc(){
		return ac;
	}
	public void setAc_type(String ac_type){
		this.ac_type=ac_type;
	}
	public String getAc_type(){
		return ac_type;
	}
	public void setAc_plus(long ac_plus){
		this.ac_plus=ac_plus;
	}
	public long getAc_plus(){
		return ac_plus;
	}
	public void setAc_min(long ac_min){
		this.ac_min=ac_min;
	}
	public long getAc_min(){
		return ac_min;
	}
	public void setAc_result(long ac_result){
		this.ac_result=ac_result;
	}
	public long getAc_result(){
		return ac_result;
	}
	public void setAc_maihuo(long ac_maihuo){
		this.ac_maihuo=ac_maihuo;
	}
	public long getAc_maihuo(){
		return ac_maihuo;
	}
}