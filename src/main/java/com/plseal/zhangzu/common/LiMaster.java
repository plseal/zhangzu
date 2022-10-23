package com.plseal.zhangzu.common;

import java.util.List;
import java.util.ArrayList;

public class LiMaster {
    public static List<String> make_ztype_list() {
		List<String> z_type_list = new ArrayList<>();
		
		z_type_list.add("其他");
		z_type_list.add("美甲收入");
		z_type_list.add("甲油胶纯色（大套）");
		z_type_list.add("甲油胶纯色（小套）");
		z_type_list.add("甲油胶透色");
		z_type_list.add("甲油胶猫眼");
		z_type_list.add("甲油胶晕染");
		z_type_list.add("甲油胶玛卡龙");
		z_type_list.add("功能胶");
		z_type_list.add("甲片粘合剂");
		z_type_list.add("粘钻胶");
		z_type_list.add("建构底胶");
		z_type_list.add("美甲工具（光疗灯）");
		z_type_list.add("美甲工具（手持灯）");
		z_type_list.add("美甲工具（U型剪）");
		z_type_list.add("美甲工具（甲片剪）");
		z_type_list.add("美甲工具（卸钻钳）");
		z_type_list.add("美甲工具（双头镊子）");
		z_type_list.add("美甲工具（搓条）");
		z_type_list.add("美甲工具（粉尘刷）");
		z_type_list.add("美甲工具（印章）");
		z_type_list.add("美甲工具（打磨机）");
		z_type_list.add("美甲工具（打磨头）");
		z_type_list.add("美甲工具（清洁）");
		z_type_list.add("美甲工具（卸甲包）");
		z_type_list.add("笔刷");
		z_type_list.add("贴纸");
		z_type_list.add("甲片");
		z_type_list.add("胶带");
		z_type_list.add("展示条");
		z_type_list.add("脚甲用品");
		
		
		
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
