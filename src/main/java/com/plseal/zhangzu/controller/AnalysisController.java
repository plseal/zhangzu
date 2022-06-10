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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import com.plseal.zhangzu.entity.Zhangzu;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/analysis")
public class AnalysisController {
    private static final Logger logger = LoggerFactory.getLogger(AnalysisController.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

	@RequestMapping("/analysis_song")
	public String analysis_song(String zhangzu_ac, HttpServletRequest request) throws Exception {
		logger.info("["+this.getClass()+"][analysis_song][start]");
		logger.info("["+this.getClass()+"][analysis_song][zhangzu_ac]"+zhangzu_ac);
		
		if ("".equals(zhangzu_ac) || zhangzu_ac == null) {
			zhangzu_ac = "2022/01";
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
		// List<ZhangzuAnalysis> list_zz_analysis = new ArrayList<ZhangzuAnalysis>();
		
		// zz_analysis = new ZhangzuAnalysis();
		// List<Map<String, Object>> list1_zz_analysis;
		// String strSQL1 =
		// "SELECT 1," +
		// "ZHI.AC ac,"+
		// "'XXXX' ac_type,"+
		// "IFNULL(SHOU.AMOUNT,0) ac_plus,"+
		// "IFNULL(ZHI.AMOUNT,0) ac_min,"+
		// "IFNULL(SHOU.AMOUNT - ZHI.AMOUNT, 0) ac_result,"+
		// "IFNULL(MAI.AMOUNT,0) ac_maihuo "+
		// "FROM v_li_zhangzu_zhichu_2022 ZHI " +
		// "left join v_li_zhangzu_shouru_2022 SHOU on  ZHI.AC = SHOU.AC " + 
		// "left join v_li_zhangzu_maihuo_2022 MAI on  ZHI.AC = MAI.AC " + 
		// "order by ac";
		// logger.info("["+this.getClass()+"][analysis_song][SQL1]"+strSQL1);
        // list1_zz_analysis = jdbcTemplate.queryForList(strSQL1);
        // // Map<String, String> resultJson = Collections.singletonMap("result", "OK");
        // logger.info("list.size():"+list1_zz_analysis.size());
        // logger.info("list.get(0):"+list1_zz_analysis.get(0));

		// logger.info("["+this.getClass()+"][to_zhangzu_analysis_song][list1_zz_analysis.size()]"+list1_zz_analysis.size());
		// for(int i = 0 ; i < list1_zz_analysis.size() ; i++) {
		// 	zz_analysis = new ZhangzuAnalysis();
		// 	str_ac  = list1_zz_analysis.get(i).get("ac").toString();
		// 	ac_min = Integer.valueOf(list1_zz_analysis.get(i).get("ac_min").toString());
		// 	ac_plus = Integer.valueOf(list1_zz_analysis.get(i).get("ac_plus").toString());
		// 	ac_result = Integer.valueOf(list1_zz_analysis.get(i).get("ac_result").toString());
		// 	ac_maihuo = Integer.valueOf(list1_zz_analysis.get(i).get("ac_maihuo").toString());
		// 	str_ac_type = list1_zz_analysis.get(i).get("ac_type").toString();
		// 	zz_analysis.setAc(str_ac);
		// 	zz_analysis.setAc_min(ac_min);
		// 	zz_analysis.setAc_plus(ac_plus);
		// 	zz_analysis.setAc_result(ac_result);
		// 	zz_analysis.setAc_maihuo(ac_maihuo);
		// 	zz_analysis.setAc_type(str_ac_type);
		// 	list_zz_analysis.add(zz_analysis);
		// }

		// request.setAttribute("list_zz_analysis", list_zz_analysis);

		// 年月	  支出 分类
		// 2022 100  餐饮饮食
		// 2022 100  诚诚
        String zhangzu_year = "2022";
		String strSQL2 =
		"SELECT " +
		"left(z_date,4) ac,z_type ac_type,sum(z_amount)*-1 ac_min "+
		"FROM t_zhangzu " +
		"where left(z_date,4) = '" + zhangzu_year + "' "+ 
		"and z_io_div = '支出'  " + 
		"GROUP BY  left(z_date,4),z_type   " + 
		"order by ac_min";

		logger.info("["+this.getClass()+"][analysis_song][SQL2]"+strSQL2);
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
            zz_analysis.setYear_type(str_ac + "_" + str_ac_type);
			list_zz_type_analysis.add(zz_analysis);
		}

		request.setAttribute("list_zz_type_analysis", list_zz_type_analysis);
		


		
		logger.info("["+this.getClass()+"][analysis_song][end] to analysis_song.jsp");

		return "analysis_song";
	}

	@RequestMapping(path = "/to_detail", method = RequestMethod.POST)
	public String to_detail(Model model, @RequestParam("year_type") String year_type) throws Exception {
        logger.info("["+this.getClass()+"][to_detail][start] ");
        logger.info("["+this.getClass()+"][to_detail][year_type]" + year_type + " ");
        String[] array_year_type = year_type.split("_");

		String sql = "SELECT * FROM t_zhangzu WHERE z_date like '"+ array_year_type[0] + "%' and z_type = '" + array_year_type[1] + "' order by z_date desc ";

		RowMapper<Zhangzu> rowMapper = new BeanPropertyRowMapper<Zhangzu>(Zhangzu.class);
        List<Zhangzu> list_zhangzu = jdbcTemplate.query(sql, rowMapper);

		model.addAttribute("list_zhangzu", list_zhangzu);
		return "detail_song";
	}
}