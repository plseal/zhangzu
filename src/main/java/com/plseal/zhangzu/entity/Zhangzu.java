package com.plseal.zhangzu.entity;

/**
 * zhangzu entity
 */
public class Zhangzu {
	private int id;
	private String z_date;
	private String z_name;
	private long z_amount;
	private String z_type;
	private String z_io_div;
	private String z_remark;
	private long z_m_amount;
	public void setId(int id){
		this.id=id;
	}
	public int getId(){
		return id;
	}
	public void setZ_date(String z_date){
		this.z_date=z_date;
	}
	public String getZ_date(){
		return z_date;
	}
	public void setZ_name(String z_name){
		this.z_name=z_name;
	}
	public String getZ_name(){
		return z_name;
	}
	public void setZ_amount(long z_amount){
		this.z_amount=z_amount;
	}
	public long getZ_amount(){
		return z_amount;
	}
	public void setZ_type(String z_type){
		this.z_type=z_type;
	}
	public String getZ_type(){
		return z_type;
	}
	public void setZ_io_div(String z_io_div){
		this.z_io_div=z_io_div;
	}
	public String getZ_io_div(){
		return z_io_div;
	}
	public void setZ_remark(String z_remark){
		this.z_remark=z_remark;
	}
	public String getZ_remark(){
		return z_remark;
	}
	public void setZ_m_amount(long z_m_amount){
		this.z_m_amount=z_m_amount;
	}
	public long getZ_m_amount(){
		return z_m_amount;
	}
}