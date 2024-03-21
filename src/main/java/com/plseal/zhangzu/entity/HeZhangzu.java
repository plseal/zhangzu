package com.plseal.zhangzu.entity;

import lombok.Data;

/**
 * hezhangzu entity
 */
//@Data (combines @ToString, @Getter, @Setter, @EqualsAndHashCode, and @RequiredArgsConstructor):
@Data
public class HeZhangzu {
	private int id;
	private String z_date;
	private String z_name;
	private long z_amount;
	private String z_type;
	private String z_io_div;
	private String z_photo_name;
	private String z_remark;
	private long z_m_amount;
	
}