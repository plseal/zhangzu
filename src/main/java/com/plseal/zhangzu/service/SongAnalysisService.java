package com.plseal.zhangzu.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.plseal.zhangzu.entity.SongAnalysisPieForm;
import com.plseal.zhangzu.entity.ZhangzuAnalysis;

@Service  // サービスクラスに付与。
public class SongAnalysisService {
    private static final Logger logger = LoggerFactory.getLogger(SongAnalysisService.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

	// 年	支出 分类
	// 2022 100  餐饮饮食
	// 2022 100  诚诚
	// 2022 100  诚诚
	public List<ZhangzuAnalysis> get_ac_type_ac_min_by_year(String ac_year) throws Exception {
		ZhangzuAnalysis zz_analysis;
		String str_ac;
		long ac_min;
		String str_ac_type;
		String strSQL1 =
		"SELECT " +
		"left(z_date,4) ac,z_type ac_type,sum(z_amount) ac_min "+
		"FROM t_zhangzu " +
		"where "+ 
		"left(z_date,4) = '" + ac_year + "' " + 
		"and z_io_div = '支出' " + 
		"GROUP BY left(z_date,4), z_type " + 
		"order by ac, ac_min";
		logger.info("["+this.getClass()+"][get_ac_type_ac_min_by_year][SQL1]"+strSQL1);
		List<ZhangzuAnalysis> list_result = new ArrayList<ZhangzuAnalysis>();
		List<Map<String, Object>> list_tmp = jdbcTemplate.queryForList(strSQL1);
        logger.info("list.size():"+list_tmp.size());
        // logger.info("list.get(0):"+list_tmp.get(0));

		for(int i = 0 ; i < list_tmp.size() ; i++) {
			zz_analysis = new ZhangzuAnalysis();
			str_ac  = list_tmp.get(i).get("ac").toString();
			ac_min = Integer.valueOf(list_tmp.get(i).get("ac_min").toString());
			str_ac_type = list_tmp.get(i).get("ac_type").toString();
			zz_analysis.setAc(str_ac);
			zz_analysis.setAc_type(str_ac_type);
            zz_analysis.setAc_min(ac_min);
			list_result.add(zz_analysis);
		}
		return list_result;
	}

	// 年	支出 分类
	// 2022 100  餐饮饮食
	// 2022 100  诚诚
	// 2022 100  诚诚
	public List<ZhangzuAnalysis> get_ac_type_ac_min_by_year_and_type(String ac_year, String ac_type, SongAnalysisPieForm songAnalysisPieForm) throws Exception {
		ZhangzuAnalysis zz_analysis;
		String str_ac;
		long ac_min;
		String str_ac_type;
		String radio_type = songAnalysisPieForm.getRadio_type();
		Integer intflg;
		if("收入".equals(radio_type)) {
			intflg = 1;
		} else {
			intflg = -1;
		}
		String strSQL2 =
		"SELECT " +
		"left(z_date,4) ac,z_type ac_type,sum(z_amount)*" + intflg + " ac_min "+
		"FROM t_zhangzu " +
		"where "+ 
		"left(z_date,4) = '" + ac_year + "' " +
		"and z_type = '" + ac_type + "' " + 
		"and z_io_div = '"+ radio_type +"' " + 
		"GROUP BY left(z_date,4), z_type " + 
		"order by ac, ac_min";
		logger.info("["+this.getClass()+"][get_ac_type_ac_min_by_year_and_type][SQL2]"+strSQL2);
		List<ZhangzuAnalysis> list_result = new ArrayList<ZhangzuAnalysis>();
		List<Map<String, Object>> list_tmp = jdbcTemplate.queryForList(strSQL2);
        logger.info("list.size():"+list_tmp.size());
		if (list_tmp.size() > 0) {
        	// logger.info("list.get(0):"+list_tmp.get(0));

			for(int i = 0 ; i < list_tmp.size() ; i++) {
				zz_analysis = new ZhangzuAnalysis();
				str_ac  = list_tmp.get(i).get("ac").toString();
				// logger.info("str_ac:"+str_ac);
				ac_min = Integer.valueOf(list_tmp.get(i).get("ac_min").toString());
				// logger.info("ac_min:"+ac_min);
				str_ac_type = list_tmp.get(i).get("ac_type").toString();
				// logger.info("str_ac_type:"+str_ac_type);
				zz_analysis.setAc(str_ac);
				zz_analysis.setAc_type(str_ac_type);
				zz_analysis.setAc_min(ac_min);
				zz_analysis.setYear_type(str_ac + "_" + str_ac_type);
				list_result.add(zz_analysis);
			}
		} 
		return list_result;
	}
	/*
	 * 年 list 取得
	 * 2022 
	 * 2021 
	 * 2020 
	 */
	public List<ZhangzuAnalysis> get_distinct_ac_year() throws Exception {
		ZhangzuAnalysis zz_analysis;
		List<ZhangzuAnalysis> list_result = new ArrayList<ZhangzuAnalysis>();
		String strSQL3 = "select distinct left(z_date,4) as ac_year from t_zhangzu order by ac_year desc";
		logger.info("["+this.getClass()+"][get_distinct_ac_year][SQL3]"+strSQL3);
		List<Map<String, Object>> list_tmp = jdbcTemplate.queryForList(strSQL3);
        logger.info("list.size():"+list_tmp.size());
        // logger.info("list.get(0):"+list_tmp.get(0));
		String str_ac_year = "";
		for(int i = 0 ; i < list_tmp.size() ; i++) {
			zz_analysis = new ZhangzuAnalysis();
			str_ac_year  = list_tmp.get(i).get("ac_year").toString();
			zz_analysis.setAc_year(str_ac_year);
			list_result.add(zz_analysis);
		}
		return list_result;
	}

	// 年	支出 分类
	// 2022 100  餐饮饮食
	// 2022 100  诚诚
	// 2022 100  诚诚
	public List<ZhangzuAnalysis> get_ac_type_ac_min_by_10days() throws Exception {
		Calendar calendar = Calendar.getInstance();
		int lastDayOfThisMonth = calendar.getActualMaximum(Calendar.DATE);
		int firstDayOfThisMonth = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		
		//每月1号开始
		calendar.set(Calendar.DAY_OF_MONTH, firstDayOfThisMonth);

		StringBuffer days1to10  = new StringBuffer();
		StringBuffer days11to20  = new StringBuffer();
		StringBuffer days21to30  = new StringBuffer();
		for (int i = 1; i <= 10; i++){
			if (i == 10){
				days1to10.append("'" + sdf.format(calendar.getTime()) + "'");
			} else {
				days1to10.append("'" + sdf.format(calendar.getTime()) + "',");
			}
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		for (int i = 1; i <= 10; i++){
			if (i == 10){
				days11to20.append("'" + sdf.format(calendar.getTime()) + "'");
			} else {
				days11to20.append("'" + sdf.format(calendar.getTime()) + "',");
			}
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		for (int i = 1; i <= 11; i++){

			// 月末ならstop
			if (calendar.getTime().getDate() == lastDayOfThisMonth){
				days21to30.append("'" + sdf.format(calendar.getTime()) + "'");
				break;
			} else {
				days21to30.append("'" + sdf.format(calendar.getTime()) + "',");
			}
			
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		// logger.info("["+this.getClass()+"][get_ac_type_ac_min_by_10days][days1to10]"+days1to10.toString());
		// logger.info("["+this.getClass()+"][get_ac_type_ac_min_by_10days][days11to20]"+days11to20.toString());
		// logger.info("["+this.getClass()+"][get_ac_type_ac_min_by_10days][days21to30]"+days21to30.toString());
		
		ZhangzuAnalysis zz_analysis = new ZhangzuAnalysis();
		List<ZhangzuAnalysis> list_result = new ArrayList<ZhangzuAnalysis>();
		zz_analysis.setAc(String.valueOf(get_z_amount_total_min_by_10days(days1to10.toString())));
		zz_analysis.setAc_min(get_z_amount_total_min_by_10days(days11to20.toString()));
		zz_analysis.setAc_type(String.valueOf(get_z_amount_total_min_by_10days(days21to30.toString())));
		list_result.add(zz_analysis);
		return list_result;
	}

	// type	
	// 餐饮饮食 
	// 诚诚
	public List<ZhangzuAnalysis> get_distinct_ac_type_by_year(String ac_year) throws Exception {
		ZhangzuAnalysis zz_analysis;
		String str_ac_type;
		List<ZhangzuAnalysis> list_result = new ArrayList<ZhangzuAnalysis>();
		String strSQL4 = "select distinct z_type as ac_type from t_zhangzu where left(z_date,4) = '" + ac_year + "'";
		logger.info("["+this.getClass()+"][get_distinct_ac_type_by_year][SQL4]"+strSQL4);
		List<Map<String, Object>> list_tmp = jdbcTemplate.queryForList(strSQL4);
        logger.info("list.size():"+list_tmp.size());
        // logger.info("list.get(0):"+list_tmp.get(0));
		for(int i = 0 ; i < list_tmp.size() ; i++) {
			zz_analysis = new ZhangzuAnalysis();
			str_ac_type  = list_tmp.get(i).get("ac_type").toString();
			zz_analysis.setAc_type(str_ac_type);
			list_result.add(zz_analysis);
		}
		return list_result;
	}

	//把10天间的花费算出来
	//备考栏 是数字的话，加进去
	public long get_z_amount_total_min_by_10days(String str_query) throws Exception {
		String z_amount, z_remark;
		long z_amount_total;
		String strSQL5 =
		"SELECT " +
		"z_date,z_name,z_amount,z_remark "+
		"FROM t_zhangzu " +
		"where "+ 
		"z_date in (" + str_query + ") " + 
		"and z_io_div = '支出' " + 
		"order by z_date";
		logger.info("["+this.getClass()+"][get_z_amount_total_min_by_10days][SQL5]"+strSQL5);
		List<Map<String, Object>> list_tmp = jdbcTemplate.queryForList(strSQL5);
        logger.info("list.size():"+list_tmp.size());
		boolean isNumeric = false;
		z_amount_total = 0;
		for(int i = 0 ; i < list_tmp.size() ; i++) {
			// logger.info("list.get(i):"+list_tmp.get(i));
			z_amount  = list_tmp.get(i).get("z_amount").toString();
			z_remark  = list_tmp.get(i).get("z_remark").toString();
			z_amount_total = z_amount_total + Integer.valueOf(z_amount);
			if (z_remark.length() > 0){
				isNumeric = z_remark.chars().allMatch( Character::isDigit );
				//备考栏 是数字的话，加进去
				if (isNumeric) {
					z_amount_total = z_amount_total + Integer.valueOf(z_remark);
				}
			}

		}
		return z_amount_total;
	}


}
