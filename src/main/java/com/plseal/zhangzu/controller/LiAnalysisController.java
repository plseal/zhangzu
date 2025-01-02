package com.plseal.zhangzu.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.plseal.zhangzu.entity.ZhangzuAnalysis;

/**
 * 
 * 李帳本分析用
 *
 */
@Controller
@RequestMapping("/li/analysis")
public class LiAnalysisController {
	private static final Logger logger = LoggerFactory.getLogger(LiAnalysisController.class);
    
	@Autowired
    JdbcTemplate jdbcTemplate;

	@RequestMapping("/2022")
	public String li_analysis_2022(String zhangzu_ac, HttpServletRequest request) throws Exception {
		logger.info("["+this.getClass()+"][li_analysis_2022][start]");
		logger.info("["+this.getClass()+"][li_analysis_2022][zhangzu_ac]"+zhangzu_ac);
		
		if ("".equals(zhangzu_ac) || zhangzu_ac == null) {
			zhangzu_ac = "2022/06";
		}

		// 年月	支出	收入	余额	买货
		// 2022/01	9608	0	9608	0
		ZhangzuAnalysis zz_analysis;
		
		String str_ac = "";
		long ac_min ;
		long ac_plus ;
		long ac_result;
		long ac_maihuo;
		String str_ac_type = "";
		List<ZhangzuAnalysis> list_zz_analysis = new ArrayList<ZhangzuAnalysis>();
		
		zz_analysis = new ZhangzuAnalysis();
		List<Map<String, Object>> list1_zz_analysis;
		String strSQL1 =
		"SELECT 1," +
		"ZHI.AC ac,"+
		"'XXXX' ac_type,"+
		"IFNULL(SHOU.AMOUNT,0) ac_plus,"+
		"IFNULL(ZHI.AMOUNT,0) ac_min,"+
		"IFNULL(SHOU.AMOUNT - ZHI.AMOUNT, 0) ac_result,"+
		"IFNULL(MAI.AMOUNT,0) ac_maihuo "+
		"FROM v_li_zhangzu_zhichu_2022 ZHI " +
		"left join v_li_zhangzu_shouru_2022 SHOU on  ZHI.AC = SHOU.AC " + 
		"left join v_li_zhangzu_maihuo_2022 MAI on  ZHI.AC = MAI.AC " + 
		"order by ac";
		logger.info("["+this.getClass()+"][li_analysis_2022][SQL1]"+strSQL1);
        list1_zz_analysis = jdbcTemplate.queryForList(strSQL1);
        // Map<String, String> resultJson = Collections.singletonMap("result", "OK");
        logger.info("list.size():"+list1_zz_analysis.size());
        logger.info("list.get(0):"+list1_zz_analysis.get(0));

		logger.info("["+this.getClass()+"][li_analysis_2022][list1_zz_analysis.size()]"+list1_zz_analysis.size());
		for(int i = 0 ; i < list1_zz_analysis.size() ; i++) {
			zz_analysis = new ZhangzuAnalysis();
			str_ac  = list1_zz_analysis.get(i).get("ac").toString();
			ac_min = Integer.valueOf(list1_zz_analysis.get(i).get("ac_min").toString());
			ac_plus = Integer.valueOf(list1_zz_analysis.get(i).get("ac_plus").toString());
			ac_result = Integer.valueOf(list1_zz_analysis.get(i).get("ac_result").toString());
			ac_maihuo = Integer.valueOf(list1_zz_analysis.get(i).get("ac_maihuo").toString());
			str_ac_type = list1_zz_analysis.get(i).get("ac_type").toString();
			zz_analysis.setAc(str_ac);
			zz_analysis.setAc_min(ac_min);
			zz_analysis.setAc_plus(ac_plus);
			zz_analysis.setAc_result(ac_result);
			zz_analysis.setAc_maihuo(ac_maihuo);
			zz_analysis.setAc_type(str_ac_type);
			list_zz_analysis.add(zz_analysis);
		}

		request.setAttribute("list_zz_analysis", list_zz_analysis);

		// 年月	  支出 分类
		// 2022/1 100  餐饮饮食
		// 2022/1 100  诚诚
		String strSQL2 =
		"SELECT " +
		"left(z_date,7) ac,z_type ac_type,sum(z_amount)*-1 ac_min "+
		"FROM li_zhangzu " +
		"where left(z_date,7) = '" + zhangzu_ac + "' "+ 
		"and z_io_div = '支出'  " + 
		"GROUP BY  left(z_date,7),z_type   " + 
		"order by ac_min";

		logger.info("["+this.getClass()+"][li_analysis_2022][SQL2]"+strSQL2);
		List<ZhangzuAnalysis> list_zz_type_analysis = new ArrayList<ZhangzuAnalysis>();
		List<Map<String, Object>> list1_zz_type_analysis = jdbcTemplate.queryForList(strSQL2);
        logger.info("list.size():"+list1_zz_type_analysis.size());
        logger.info("list.get(0):"+list1_zz_type_analysis.get(0));
		
		for(int i = 0 ; i < list1_zz_type_analysis.size() ; i++) {
			zz_analysis = new ZhangzuAnalysis();
			str_ac  = list1_zz_type_analysis.get(i).get("ac").toString();
			ac_min = Integer.valueOf(list1_zz_type_analysis.get(i).get("ac_min").toString());
			str_ac_type = list1_zz_type_analysis.get(i).get("ac_type").toString();
			zz_analysis.setAc(str_ac);
			zz_analysis.setAc_min(ac_min);
			zz_analysis.setAc_type(str_ac_type);
			list_zz_type_analysis.add(zz_analysis);
		}

		request.setAttribute("list_zz_type_analysis", list_zz_type_analysis);

		
		logger.info("["+this.getClass()+"][li_analysis_2022][end] to li_analysis_2022.html");

		return "li_analysis_2022";
	}
	@RequestMapping("/2023")
	public String li_analysis_2023(String zhangzu_ac, HttpServletRequest request) throws Exception {
		logger.info("["+this.getClass()+"][li_analysis_2023][start]");
		logger.info("["+this.getClass()+"][li_analysis_2023][zhangzu_ac]"+zhangzu_ac);
		
		if ("".equals(zhangzu_ac) || zhangzu_ac == null) {
			zhangzu_ac = "2023/01";
		}

		// 年月	支出	收入	余额	买货
		// 2023/01	9608	0	9608	0
		ZhangzuAnalysis zz_analysis;
		
		String str_ac = "";
		long ac_min ;
		long ac_plus ;
		long ac_result;
		long ac_maihuo;
		String str_ac_type = "";
		List<ZhangzuAnalysis> list_zz_analysis = new ArrayList<ZhangzuAnalysis>();
		
		zz_analysis = new ZhangzuAnalysis();
		List<Map<String, Object>> list1_zz_analysis;
		String strSQL1 =
		"SELECT 1," +
		"ZHI.AC ac,"+
		"'XXXX' ac_type,"+
		"IFNULL(SHOU.AMOUNT,0) ac_plus,"+
		"IFNULL(ZHI.AMOUNT,0) ac_min,"+
		"IFNULL(SHOU.AMOUNT - ZHI.AMOUNT, 0) ac_result,"+
		"IFNULL(MAI.AMOUNT,0) ac_maihuo "+
		"FROM v_li_zhangzu_zhichu_2023 ZHI " +
		"left join v_li_zhangzu_shouru_2023 SHOU on  ZHI.AC = SHOU.AC " + 
		"left join v_li_zhangzu_maihuo_2023 MAI on  ZHI.AC = MAI.AC " + 
		"order by ac";
		logger.info("["+this.getClass()+"][li_analysis_2023][SQL1]"+strSQL1);
        list1_zz_analysis = jdbcTemplate.queryForList(strSQL1);
        // Map<String, String> resultJson = Collections.singletonMap("result", "OK");
        logger.info("list.size():"+list1_zz_analysis.size());
        logger.info("list.get(0):"+list1_zz_analysis.get(0));

		logger.info("["+this.getClass()+"][li_analysis_2023][list1_zz_analysis.size()]"+list1_zz_analysis.size());
		for(int i = 0 ; i < list1_zz_analysis.size() ; i++) {
			zz_analysis = new ZhangzuAnalysis();
			str_ac  = list1_zz_analysis.get(i).get("ac").toString();
			ac_min = Integer.valueOf(list1_zz_analysis.get(i).get("ac_min").toString());
			ac_plus = Integer.valueOf(list1_zz_analysis.get(i).get("ac_plus").toString());
			ac_result = Integer.valueOf(list1_zz_analysis.get(i).get("ac_result").toString());
			ac_maihuo = Integer.valueOf(list1_zz_analysis.get(i).get("ac_maihuo").toString());
			str_ac_type = list1_zz_analysis.get(i).get("ac_type").toString();
			zz_analysis.setAc(str_ac);
			zz_analysis.setAc_min(ac_min);
			zz_analysis.setAc_plus(ac_plus);
			zz_analysis.setAc_result(ac_result);
			zz_analysis.setAc_maihuo(ac_maihuo);
			zz_analysis.setAc_type(str_ac_type);
			list_zz_analysis.add(zz_analysis);
		}

		request.setAttribute("list_zz_analysis", list_zz_analysis);

		// 年月	  支出 分类
		// 2023/1 100  餐饮饮食
		// 2023/1 100  诚诚
		String strSQL2 =
		"SELECT " +
		"left(z_date,7) ac,z_type ac_type,sum(z_amount)*-1 ac_min "+
		"FROM li_zhangzu " +
		"where left(z_date,7) = '" + zhangzu_ac + "' "+ 
		"and z_io_div = '支出'  " + 
		"GROUP BY  left(z_date,7),z_type   " + 
		"order by ac_min";

		logger.info("["+this.getClass()+"][li_analysis_2023][SQL2]"+strSQL2);
		List<ZhangzuAnalysis> list_zz_type_analysis = new ArrayList<ZhangzuAnalysis>();
		List<Map<String, Object>> list1_zz_type_analysis = jdbcTemplate.queryForList(strSQL2);
        logger.info("list.size():"+list1_zz_type_analysis.size());
        logger.info("list.get(0):"+list1_zz_type_analysis.get(0));
		
		for(int i = 0 ; i < list1_zz_type_analysis.size() ; i++) {
			zz_analysis = new ZhangzuAnalysis();
			str_ac  = list1_zz_type_analysis.get(i).get("ac").toString();
			ac_min = Integer.valueOf(list1_zz_type_analysis.get(i).get("ac_min").toString());
			str_ac_type = list1_zz_type_analysis.get(i).get("ac_type").toString();
			zz_analysis.setAc(str_ac);
			zz_analysis.setAc_min(ac_min);
			zz_analysis.setAc_type(str_ac_type);
			list_zz_type_analysis.add(zz_analysis);
		}

		request.setAttribute("list_zz_type_analysis", list_zz_type_analysis);

		
		logger.info("["+this.getClass()+"][li_analysis_2023][end] to li_analysis_2023.html");

		return "li_analysis_2023";
	}
	@RequestMapping("/2024")
	public String li_analysis_2024(String zhangzu_ac, HttpServletRequest request) throws Exception {
		logger.info("["+this.getClass()+"][li_analysis_2024][start]");
		logger.info("["+this.getClass()+"][li_analysis_2024][zhangzu_ac]"+zhangzu_ac);
		
		if ("".equals(zhangzu_ac) || zhangzu_ac == null) {
			zhangzu_ac = "2024/01";
		}

		// 年月	支出	收入	余额	买货
		// 2024/01	9608	0	9608	0
		ZhangzuAnalysis zz_analysis;
		
		String str_ac = "";
		long ac_min ;
		long ac_plus ;
		long ac_result;
		long ac_maihuo;
		String str_ac_type = "";
		List<ZhangzuAnalysis> list_zz_analysis = new ArrayList<ZhangzuAnalysis>();
		
		zz_analysis = new ZhangzuAnalysis();
		List<Map<String, Object>> list1_zz_analysis;
		String strSQL1 =
		"SELECT 1," +
		"ZHI.AC ac,"+
		"'XXXX' ac_type,"+
		"IFNULL(SHOU.AMOUNT,0) ac_plus,"+
		"IFNULL(ZHI.AMOUNT,0) ac_min,"+
		"IFNULL(SHOU.AMOUNT - ZHI.AMOUNT, 0) ac_result,"+
		"IFNULL(MAI.AMOUNT,0) ac_maihuo "+
		"FROM v_li_zhangzu_zhichu_2024 ZHI " +
		"left join v_li_zhangzu_shouru_2024 SHOU on  ZHI.AC = SHOU.AC " + 
		"left join v_li_zhangzu_maihuo_2024 MAI on  ZHI.AC = MAI.AC " + 
		"order by ac";
		logger.info("["+this.getClass()+"][li_analysis_2024][SQL1]"+strSQL1);
        list1_zz_analysis = jdbcTemplate.queryForList(strSQL1);
        // Map<String, String> resultJson = Collections.singletonMap("result", "OK");
        logger.info("list.size():"+list1_zz_analysis.size());
        logger.info("list.get(0):"+list1_zz_analysis.get(0));

		logger.info("["+this.getClass()+"][li_analysis_2024][list1_zz_analysis.size()]"+list1_zz_analysis.size());
		for(int i = 0 ; i < list1_zz_analysis.size() ; i++) {
			zz_analysis = new ZhangzuAnalysis();
			str_ac  = list1_zz_analysis.get(i).get("ac").toString();
			ac_min = Integer.valueOf(list1_zz_analysis.get(i).get("ac_min").toString());
			ac_plus = Integer.valueOf(list1_zz_analysis.get(i).get("ac_plus").toString());
			ac_result = Integer.valueOf(list1_zz_analysis.get(i).get("ac_result").toString());
			ac_maihuo = Integer.valueOf(list1_zz_analysis.get(i).get("ac_maihuo").toString());
			str_ac_type = list1_zz_analysis.get(i).get("ac_type").toString();
			zz_analysis.setAc(str_ac);
			zz_analysis.setAc_min(ac_min);
			zz_analysis.setAc_plus(ac_plus);
			zz_analysis.setAc_result(ac_result);
			zz_analysis.setAc_maihuo(ac_maihuo);
			zz_analysis.setAc_type(str_ac_type);
			list_zz_analysis.add(zz_analysis);
		}

		request.setAttribute("list_zz_analysis", list_zz_analysis);

		// 年月	  支出 分类
		// 2024/1 100  餐饮饮食
		// 2024/1 100  诚诚
		String strSQL2 =
		"SELECT " +
		"left(z_date,7) ac,z_type ac_type,sum(z_amount)*-1 ac_min "+
		"FROM li_zhangzu " +
		"where left(z_date,7) = '" + zhangzu_ac + "' "+ 
		"and z_io_div = '支出'  " + 
		"GROUP BY  left(z_date,7),z_type   " + 
		"order by ac_min";

		logger.info("["+this.getClass()+"][li_analysis_2024][SQL2]"+strSQL2);
		List<ZhangzuAnalysis> list_zz_type_analysis = new ArrayList<ZhangzuAnalysis>();
		List<Map<String, Object>> list1_zz_type_analysis = jdbcTemplate.queryForList(strSQL2);
        logger.info("list.size():"+list1_zz_type_analysis.size());
        logger.info("list.get(0):"+list1_zz_type_analysis.get(0));
		
		for(int i = 0 ; i < list1_zz_type_analysis.size() ; i++) {
			zz_analysis = new ZhangzuAnalysis();
			str_ac  = list1_zz_type_analysis.get(i).get("ac").toString();
			ac_min = Integer.valueOf(list1_zz_type_analysis.get(i).get("ac_min").toString());
			str_ac_type = list1_zz_type_analysis.get(i).get("ac_type").toString();
			zz_analysis.setAc(str_ac);
			zz_analysis.setAc_min(ac_min);
			zz_analysis.setAc_type(str_ac_type);
			list_zz_type_analysis.add(zz_analysis);
		}

		request.setAttribute("list_zz_type_analysis", list_zz_type_analysis);

		
		logger.info("["+this.getClass()+"][li_analysis_2024][end] to li_analysis_2024.html");

		return "li_analysis_2024";
	}

	@RequestMapping("/2025")
	public String li_analysis_2025(String zhangzu_ac, HttpServletRequest request) throws Exception {
		logger.info("["+this.getClass()+"][li_analysis_2025][start]");
		logger.info("["+this.getClass()+"][li_analysis_2025][zhangzu_ac]"+zhangzu_ac);
		
		if ("".equals(zhangzu_ac) || zhangzu_ac == null) {
			zhangzu_ac = "2025/01";
		}

		// 年月	支出	收入	余额	买货
		// 2025/01	9608	0	9608	0
		ZhangzuAnalysis zz_analysis;
		
		String str_ac = "";
		long ac_min ;
		long ac_plus ;
		long ac_result;
		long ac_maihuo;
		String str_ac_type = "";
		List<ZhangzuAnalysis> list_zz_analysis = new ArrayList<ZhangzuAnalysis>();
		
		zz_analysis = new ZhangzuAnalysis();
		List<Map<String, Object>> list1_zz_analysis;
		String strSQL1 =
		"SELECT 1," +
		"ZHI.AC ac,"+
		"'XXXX' ac_type,"+
		"IFNULL(SHOU.AMOUNT,0) ac_plus,"+
		"IFNULL(ZHI.AMOUNT,0) ac_min,"+
		"IFNULL(SHOU.AMOUNT - ZHI.AMOUNT, 0) ac_result,"+
		"IFNULL(MAI.AMOUNT,0) ac_maihuo "+
		"FROM v_li_zhangzu_zhichu_2025 ZHI " +
		"left join v_li_zhangzu_shouru_2025 SHOU on  ZHI.AC = SHOU.AC " + 
		"left join v_li_zhangzu_maihuo_2025 MAI on  ZHI.AC = MAI.AC " + 
		"order by ac";
		logger.info("["+this.getClass()+"][li_analysis_2025][SQL1]"+strSQL1);
        list1_zz_analysis = jdbcTemplate.queryForList(strSQL1);
        // Map<String, String> resultJson = Collections.singletonMap("result", "OK");
        logger.info("list.size():"+list1_zz_analysis.size());
        logger.info("list.get(0):"+list1_zz_analysis.get(0));

		logger.info("["+this.getClass()+"][li_analysis_2025][list1_zz_analysis.size()]"+list1_zz_analysis.size());
		for(int i = 0 ; i < list1_zz_analysis.size() ; i++) {
			zz_analysis = new ZhangzuAnalysis();
			str_ac  = list1_zz_analysis.get(i).get("ac").toString();
			ac_min = Integer.valueOf(list1_zz_analysis.get(i).get("ac_min").toString());
			ac_plus = Integer.valueOf(list1_zz_analysis.get(i).get("ac_plus").toString());
			ac_result = Integer.valueOf(list1_zz_analysis.get(i).get("ac_result").toString());
			ac_maihuo = Integer.valueOf(list1_zz_analysis.get(i).get("ac_maihuo").toString());
			str_ac_type = list1_zz_analysis.get(i).get("ac_type").toString();
			zz_analysis.setAc(str_ac);
			zz_analysis.setAc_min(ac_min);
			zz_analysis.setAc_plus(ac_plus);
			zz_analysis.setAc_result(ac_result);
			zz_analysis.setAc_maihuo(ac_maihuo);
			zz_analysis.setAc_type(str_ac_type);
			list_zz_analysis.add(zz_analysis);
		}

		request.setAttribute("list_zz_analysis", list_zz_analysis);

		// 年月	  支出 分类
		// 2025/1 100  餐饮饮食
		// 2025/1 100  诚诚
		String strSQL2 =
		"SELECT " +
		"left(z_date,7) ac,z_type ac_type,sum(z_amount)*-1 ac_min "+
		"FROM li_zhangzu " +
		"where left(z_date,7) = '" + zhangzu_ac + "' "+ 
		"and z_io_div = '支出'  " + 
		"GROUP BY  left(z_date,7),z_type   " + 
		"order by ac_min";

		logger.info("["+this.getClass()+"][li_analysis_2025][SQL2]"+strSQL2);
		List<ZhangzuAnalysis> list_zz_type_analysis = new ArrayList<ZhangzuAnalysis>();
		List<Map<String, Object>> list1_zz_type_analysis = jdbcTemplate.queryForList(strSQL2);
        logger.info("list.size():"+list1_zz_type_analysis.size());
        logger.info("list.get(0):"+list1_zz_type_analysis.get(0));
		
		for(int i = 0 ; i < list1_zz_type_analysis.size() ; i++) {
			zz_analysis = new ZhangzuAnalysis();
			str_ac  = list1_zz_type_analysis.get(i).get("ac").toString();
			ac_min = Integer.valueOf(list1_zz_type_analysis.get(i).get("ac_min").toString());
			str_ac_type = list1_zz_type_analysis.get(i).get("ac_type").toString();
			zz_analysis.setAc(str_ac);
			zz_analysis.setAc_min(ac_min);
			zz_analysis.setAc_type(str_ac_type);
			list_zz_type_analysis.add(zz_analysis);
		}

		request.setAttribute("list_zz_type_analysis", list_zz_type_analysis);

		
		logger.info("["+this.getClass()+"][li_analysis_2025][end] to li_analysis_2024.html");

		return "li_analysis_2025";
	}
}
