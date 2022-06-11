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

		// 年(2022だけ)	  支出 分类
		// 2022 100  餐饮饮食
		// 2022 100  诚诚
		ZhangzuAnalysis zz_analysis;
		
		String str_ac = "";
		long ac_min ;
		String str_ac_type = "";

		String strSQL1 =
		"SELECT " +
		"left(z_date,4) ac,z_type ac_type,sum(z_amount) ac_min "+
		"FROM t_zhangzu " +
		"where "+ 
		"left(z_date,4) = '2022' " + 
		"and z_io_div = '支出'  " + 
		"GROUP BY  left(z_date,4),z_type   " + 
		"order by ac, ac_min";
		logger.info("["+this.getClass()+"][analysis_song][SQL1]"+strSQL1);
		List<ZhangzuAnalysis> list_z_type_analysis_by_year = new ArrayList<ZhangzuAnalysis>();
		List<Map<String, Object>> list1_z_type_analysis_by_year = jdbcTemplate.queryForList(strSQL1);
        logger.info("list.size():"+list1_z_type_analysis_by_year.size());
        logger.info("list.get(0):"+list1_z_type_analysis_by_year.get(0));

		for(int i = 0 ; i < list1_z_type_analysis_by_year.size() ; i++) {
			zz_analysis = new ZhangzuAnalysis();
			str_ac  = list1_z_type_analysis_by_year.get(i).get("ac").toString();
			ac_min = Integer.valueOf(list1_z_type_analysis_by_year.get(i).get("ac_min").toString());
			str_ac_type = list1_z_type_analysis_by_year.get(i).get("ac_type").toString();
			zz_analysis.setAc(str_ac);
			zz_analysis.setAc_type(str_ac_type);
            zz_analysis.setAc_min(ac_min);
			list_z_type_analysis_by_year.add(zz_analysis);
		}

		request.setAttribute("list_z_type_analysis_by_year", list_z_type_analysis_by_year);
		request.setAttribute("analysis_title_ac_year", "2022");
		

		// 年(全年度)		  支出 分类
		// 2022              100  餐饮饮食
		// 2022              100  诚诚
		// 2021              100  诚诚
 		String strSQL2 =
		"SELECT " +
		"left(z_date,4) ac,z_type ac_type,sum(z_amount)*-1 ac_min "+
		"FROM t_zhangzu " +
		"where "+ 
		"z_io_div = '支出'  " + 
		"GROUP BY  left(z_date,4),z_type   " + 
		"order by ac, ac_min";

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
		
		// 年一覧
		List<ZhangzuAnalysis> list_ac_year = new ArrayList<ZhangzuAnalysis>();
		String strSQL3 = "select distinct left(z_date,4) as ac_year from t_zhangzu";
		logger.info("["+this.getClass()+"][analysis_song][SQL3]"+strSQL3);
		List<Map<String, Object>> list1_ac_year = jdbcTemplate.queryForList(strSQL3);
        logger.info("list.size():"+list1_ac_year.size());
        logger.info("list.get(0):"+list1_ac_year.get(0));
		String str_ac_year = "";
		for(int i = 0 ; i < list1_ac_year.size() ; i++) {
			zz_analysis = new ZhangzuAnalysis();
			str_ac_year  = list1_ac_year.get(i).get("ac_year").toString();
			zz_analysis.setAc_year(str_ac_year);
			list_ac_year.add(zz_analysis);
		}
		// プルダウンの初期値
        request.setAttribute("selectedValue_ac_year", "2022");
		request.setAttribute("list_ac_year", list_ac_year);

		// z_type一覧
		List<ZhangzuAnalysis> list_ac_type = new ArrayList<ZhangzuAnalysis>();
		String strSQL4 = "select distinct z_type as ac_type from t_zhangzu";
		logger.info("["+this.getClass()+"][analysis_song][SQL4]"+strSQL4);
		List<Map<String, Object>> list1_ac_type = jdbcTemplate.queryForList(strSQL4);
        logger.info("list.size():"+list1_ac_type.size());
        logger.info("list.get(0):"+list1_ac_type.get(0));
		for(int i = 0 ; i < list1_ac_type.size() ; i++) {
			zz_analysis = new ZhangzuAnalysis();
			str_ac_type  = list1_ac_type.get(i).get("ac_type").toString();
			zz_analysis.setAc_type(str_ac_type);
			list_ac_type.add(zz_analysis);
		}
		// プルダウンの初期値
        // request.setAttribute("selectedValue_ac_type", "餐饮饮食");
		request.setAttribute("list_ac_type", list_ac_type);

		
		logger.info("["+this.getClass()+"][analysis_song][end] to analysis_song.jsp");

		String selectedValue_year_type = "2022_餐饮饮食";
		request.setAttribute("selectedValue_year_type", selectedValue_year_type);
		

		return "analysis_song";
	}

	@RequestMapping(path = "/to_detail", method = RequestMethod.POST)
	public String to_detail(Model model, @RequestParam("year_type") String year_type) throws Exception {
        logger.info("["+this.getClass()+"][to_detail][start] ");
        logger.info("["+this.getClass()+"][to_detail][year_type]" + year_type + " ");
        String[] array_year_type = year_type.split("_");

		String sql = "SELECT z_date,z_name,z_amount,z_type,z_io_div,z_remark,0 as z_m_amount FROM t_zhangzu WHERE z_date like '"+ array_year_type[0] + "%' and z_type = '" + array_year_type[1] + "' order by z_date desc ";

		RowMapper<Zhangzu> rowMapper = new BeanPropertyRowMapper<Zhangzu>(Zhangzu.class);
        List<Zhangzu> list_zhangzu = jdbcTemplate.query(sql, rowMapper);

		model.addAttribute("list_zhangzu", list_zhangzu);
		return "detail_song";
	}

	@RequestMapping(path = "/to_analysis_song", method = RequestMethod.POST)
	public String to_analysis_song(Model model, @RequestParam("selectedValue_year_type") String selectedValue_year_type, HttpServletRequest request) throws Exception {
        logger.info("["+this.getClass()+"][to_analysis_song][start] ");
        logger.info("["+this.getClass()+"][to_analysis_song][selectedValue_year_type]" + selectedValue_year_type + " ");
		String[] array_selectedValue_year_type = selectedValue_year_type.split("_");
		
		// 年	  支出 分类
		// 2022 100  餐饮饮食
		// 2022 100  诚诚
 		String strSQL2 =
		"SELECT " +
		"left(z_date,4) ac,z_type ac_type,sum(z_amount)*-1 ac_min "+
		"FROM t_zhangzu " +
		"where "+ 
		"left(z_date,4) = '" + array_selectedValue_year_type[0] + "' " + 
		"and z_type = '" + array_selectedValue_year_type[1] + "' " + 
		"GROUP BY  left(z_date,4),z_type   " + 
		"order by ac, ac_min";
		ZhangzuAnalysis zz_analysis;
		logger.info("["+this.getClass()+"][to_analysis_song][SQL2]"+strSQL2);
		List<ZhangzuAnalysis> list_zz_type_analysis = new ArrayList<ZhangzuAnalysis>();
		List<Map<String, Object>> list1_zz_type_analysis = jdbcTemplate.queryForList(strSQL2);
        logger.info("list.size():"+list1_zz_type_analysis.size());
		String str_ac_type = "";
		if (list1_zz_type_analysis.size() == 0) {
			request.setAttribute("alert_count_0", "没查到数据");
		} else {
        	logger.info("list.get(0):"+list1_zz_type_analysis.get(0));
			String str_ac = "";
			long ac_min ;
			
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
		}
		// 年一覧
		List<ZhangzuAnalysis> list_ac_year = new ArrayList<ZhangzuAnalysis>();
		String strSQL3 = "select distinct left(z_date,4) as ac_year from t_zhangzu";
		logger.info("["+this.getClass()+"][to_analysis_song][SQL3]"+strSQL3);
		List<Map<String, Object>> list1_ac_year = jdbcTemplate.queryForList(strSQL3);
        logger.info("list.size():"+list1_ac_year.size());
        logger.info("list.get(0):"+list1_ac_year.get(0));
		String str_ac_year = "";
		for(int i = 0 ; i < list1_ac_year.size() ; i++) {
			zz_analysis = new ZhangzuAnalysis();
			str_ac_year  = list1_ac_year.get(i).get("ac_year").toString();
			zz_analysis.setAc_year(str_ac_year);
			list_ac_year.add(zz_analysis);
		}
		// プルダウンの初期値
        request.setAttribute("selectedValue_ac_year", array_selectedValue_year_type[0]);
		request.setAttribute("list_ac_year", list_ac_year);

		// z_type一覧
		List<ZhangzuAnalysis> list_ac_type = new ArrayList<ZhangzuAnalysis>();
		String strSQL4 = "select distinct z_type as ac_type from t_zhangzu";
		logger.info("["+this.getClass()+"][to_analysis_song][SQL4]"+strSQL4);
		List<Map<String, Object>> list1_ac_type = jdbcTemplate.queryForList(strSQL4);
        logger.info("list.size():"+list1_ac_type.size());
		if (list1_ac_type.size() == 0) {
			request.setAttribute("alert_count_0", "没查到数据");
		} else {
			logger.info("list.get(0):"+list1_ac_type.get(0));
			for(int i = 0 ; i < list1_ac_type.size() ; i++) {
				zz_analysis = new ZhangzuAnalysis();
				str_ac_type  = list1_ac_type.get(i).get("ac_type").toString();
				zz_analysis.setAc_type(str_ac_type);
				list_ac_type.add(zz_analysis);
			}
			request.setAttribute("list_ac_type", list_ac_type);
		}

		// プルダウンの初期値
        request.setAttribute("selectedValue_ac_type", array_selectedValue_year_type[1]);
		request.setAttribute("selectedValue_year_type", selectedValue_year_type);
		
		
		logger.info("["+this.getClass()+"][to_analysis_song][end] to analysis_song.jsp");

		
		
		
		return "analysis_song";
	}
	
}