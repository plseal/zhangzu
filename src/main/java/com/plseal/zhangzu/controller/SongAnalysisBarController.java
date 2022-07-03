package com.plseal.zhangzu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import com.plseal.zhangzu.entity.ZhangzuAnalysis;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import com.plseal.zhangzu.service.SongAnalysisService;
/*
 * 宋帳本分析用(柱状图)
 * t_zhangzu
 */
@Controller
public class SongAnalysisBarController {
    private static final Logger logger = LoggerFactory.getLogger(SongAnalysisBarController.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

	@Autowired 
	SongAnalysisService songAnalysisService;

	@RequestMapping("/song/analysis/bar")
	public String song_analysis_bar(String zhangzu_ac, HttpServletRequest request) throws Exception {
		logger.info("["+this.getClass()+"][song_analysis_bar][start]");
		logger.info("["+this.getClass()+"][song_analysis_bar][zhangzu_ac]"+zhangzu_ac);
		
		if ("".equals(zhangzu_ac) || zhangzu_ac == null) {
			zhangzu_ac = "2022/06";
		}
		// ********************
		// for pulldown list
		// ********************
		// 年一覧
		List<ZhangzuAnalysis> list_ac_year = songAnalysisService.get_distinct_ac_year();
		request.setAttribute("list_ac_year", list_ac_year);

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
		List<ZhangzuAnalysis> list_zz_analysis_one_month = new ArrayList<ZhangzuAnalysis>();
		
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
		"FROM v_song_zhangzu_zhichu_2022 ZHI " +
		"left join v_song_zhangzu_shouru_2022 SHOU on  ZHI.AC = SHOU.AC " + 
		"left join v_song_zhangzu_maihuo_2022 MAI on  ZHI.AC = MAI.AC " + 
		"order by ac";
		logger.info("["+this.getClass()+"][song_analysis_bar][SQL1]"+strSQL1);
        list1_zz_analysis = jdbcTemplate.queryForList(strSQL1);
        // Map<String, String> resultJson = Collections.singletonMap("result", "OK");
        logger.info("list.size():"+list1_zz_analysis.size());
        logger.info("list.get(0):"+list1_zz_analysis.get(0));

		logger.info("["+this.getClass()+"][song_analysis_bar][list1_zz_analysis.size()]"+list1_zz_analysis.size());
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
			if (str_ac.equals(zhangzu_ac)) {
				list_zz_analysis_one_month.add(zz_analysis);
			}
			
		}

		request.setAttribute("list_zz_analysis_for_bar_chart", list_zz_analysis);
		request.setAttribute("list_zz_analysis_one_month", list_zz_analysis_one_month);
		

		// 年月	  支出 分类
		// 2022/1 100  餐饮饮食
		// 2022/1 100  诚诚
		String strSQL2 =
		"SELECT " +
		"left(z_date,7) ac,z_type ac_type,sum(z_amount)*-1 ac_min "+
		"FROM t_zhangzu " +
		"where left(z_date,7) = '" + zhangzu_ac + "' "+ 
		"and z_io_div = '支出'  " + 
		"GROUP BY  left(z_date,7),z_type   " + 
		"order by ac_min";

		logger.info("["+this.getClass()+"][song_analysis_bar][SQL2]"+strSQL2);
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
		
		logger.info("["+this.getClass()+"][song_analysis_bar][end] to song_analysis_bar.html");

		return "song_analysis_bar";
	}


	
}