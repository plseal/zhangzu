package com.plseal.zhangzu.common;

import java.util.List;
import java.util.ArrayList;

public class SongMaster {
    public static List<String> make_ztype_list() {
		List<String> z_type_list = new ArrayList<>();
		z_type_list.add("餐饮饮食");
		z_type_list.add("外食");
		z_type_list.add("旅游娱乐");
		z_type_list.add("日常用品");
		z_type_list.add("交通费");
		z_type_list.add("嘉怡");
		z_type_list.add("诚诚");
		z_type_list.add("人际往来");
		z_type_list.add("李服饰");
		z_type_list.add("宋服饰");
		z_type_list.add("宋父母");
		z_type_list.add("李父母");
		z_type_list.add("宽带网费");
		z_type_list.add("化妆品美容");
		z_type_list.add("医药保健");
		z_type_list.add("美甲");
		z_type_list.add("医疗保险费");
		z_type_list.add("水电煤气");
		z_type_list.add("宋工资");
		z_type_list.add("手机话费");
		z_type_list.add("网站建设");
		z_type_list.add("房产房贷");
		z_type_list.add("信用卡年费");
        return z_type_list;
    }
    public static List<String> make_z_io_div_list() {
		List<String> z_io_div_list = new ArrayList<>();
		z_io_div_list.add("支出");
		z_io_div_list.add("收入");
		z_io_div_list.add("买货");
        return z_io_div_list;
    }
}
