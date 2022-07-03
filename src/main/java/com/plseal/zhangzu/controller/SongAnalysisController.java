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
import java.util.Calendar;
import java.text.SimpleDateFormat;
/*
 * 
 * 宋帳本分析用
 * t_zhangzu
 */
@Controller
@RequestMapping("/song/analysis")
public class SongAnalysisController {
    private static final Logger logger = LoggerFactory.getLogger(SongAnalysisController.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

	@RequestMapping("/2022")
	public String song_analysis_2022(String zhangzu_ac, HttpServletRequest request) throws Exception {
		logger.info("["+this.getClass()+"][song_analysis_2022][start]");
		logger.info("["+this.getClass()+"][song_analysis_2022][zhangzu_ac]"+zhangzu_ac);
		
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
		logger.info("["+this.getClass()+"][song_analysis_2022][SQL1]"+strSQL1);
        list1_zz_analysis = jdbcTemplate.queryForList(strSQL1);
        // Map<String, String> resultJson = Collections.singletonMap("result", "OK");
        logger.info("list.size():"+list1_zz_analysis.size());
        logger.info("list.get(0):"+list1_zz_analysis.get(0));

		logger.info("["+this.getClass()+"][song_analysis_2022][list1_zz_analysis.size()]"+list1_zz_analysis.size());
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

		logger.info("["+this.getClass()+"][song_analysis_2022][SQL2]"+strSQL2);
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
		
		logger.info("["+this.getClass()+"][song_analysis_2022][end] to song_analysis_2022.html");

		return "song_analysis_2022";
	}

	@RequestMapping("/pie")
	public String analysis_song(HttpServletRequest request) throws Exception {
		logger.info("["+this.getClass()+"][analysis_song][start]");

		// ****************
		// for pie chart
		// ****************
		// 年  支出 分类
		// 2022 100  餐饮饮食
		// 2022 100  诚诚
		List<ZhangzuAnalysis> list_z_type_analysis_by_year = get_ac_type_ac_min_by_year("2022");
		request.setAttribute("list_z_type_analysis_by_year", list_z_type_analysis_by_year);
		request.setAttribute("analysis_title_ac_year", "2022");

		// **************************************************************************
		// yyyy/mm每十天合计
		// **************************************************************************
		//现在系统时间的 年/月
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
		String calendar_YM =sdf.format(calendar.getTime());
		request.setAttribute("calendar_YM", calendar_YM+"每十天合计");

		// **************************************************************************
		// for 期間（１日～１０日）	期間（１１日～２０日）	期間（２１日～月末）
		// **************************************************************************
		List<ZhangzuAnalysis> list_ac_type_ac_min_by_10days = get_ac_type_ac_min_by_10days();
		request.setAttribute("list_ac_type_ac_min_by_10days", list_ac_type_ac_min_by_10days);

		// ****************
		// for table
		// ****************
		// 年	  支出 分类
		// 2022    100  餐饮饮食
		// 2022    100  诚诚
		// 2021    100  诚诚
		List<ZhangzuAnalysis> list_zz_type_analysis = get_ac_type_ac_min_by_year_and_type("2022", "餐饮饮食");
		request.setAttribute("list_zz_type_analysis", list_zz_type_analysis);
		
		// ********************
		// for pulldown list
		// ********************
		// 年一覧
		List<ZhangzuAnalysis> list_ac_year = get_distinct_ac_year();
		request.setAttribute("list_ac_year", list_ac_year);

		// ********************
		// for pulldown list
		// ********************
		// z_type一覧
		List<ZhangzuAnalysis> list_ac_type = get_distinct_ac_type_by_year("2022");
		request.setAttribute("list_ac_type", list_ac_type);
		
		// ********************
		// for search button
		// ********************
		String selectedValue_year_type = "2022_餐饮饮食";
		request.setAttribute("selectedValue_year_type", selectedValue_year_type);



		logger.info("["+this.getClass()+"][analysis_song][end] to analysis_song.jsp");

		return "song_analysis_pie";
	}

	@RequestMapping(path = "/to_detail", method = RequestMethod.POST)
	public String to_detail(Model model, @RequestParam("year_type") String year_type) throws Exception {
        logger.info("["+this.getClass()+"][to_detail][start] ");
        logger.info("["+this.getClass()+"][to_detail][year_type]" + year_type + " ");
        String[] array_year_type = year_type.split("_");

		String sql = "SELECT z_date,z_name,z_amount,z_type,z_io_div,z_remark,0 as z_m_amount " +
		"FROM t_zhangzu " + 
		"WHERE z_date like '"+ array_year_type[0] + "%' " + 
		"and z_io_div = '支出' " + 
		"and z_type = '" + array_year_type[1] + "' " + 
		"order by z_date desc ";

		RowMapper<Zhangzu> rowMapper = new BeanPropertyRowMapper<Zhangzu>(Zhangzu.class);
        List<Zhangzu> list_zhangzu = jdbcTemplate.query(sql, rowMapper);

		model.addAttribute("list_zhangzu", list_zhangzu);
		return "song_analysis_detail";
	}

	@RequestMapping(path = "/pie_post", method = RequestMethod.POST)
	public String to_analysis_song(Model model, @RequestParam("selectedValue_year_type") String selectedValue_year_type, HttpServletRequest request) throws Exception {
        logger.info("["+this.getClass()+"][pie_post][start] ");
        logger.info("["+this.getClass()+"][pie_post][selectedValue_year_type]" + selectedValue_year_type + " ");
		String[] array_selectedValue_year_type = selectedValue_year_type.split("_");

		String ac_year = array_selectedValue_year_type[0];
		String ac_type = array_selectedValue_year_type[1];

		// ****************
		// for pie chart
		// ****************
		// 年  支出 分类
		// 2022 100  餐饮饮食
		// 2022 100  诚诚
		List<ZhangzuAnalysis> list_z_type_analysis_by_year = get_ac_type_ac_min_by_year(ac_year);
		model.addAttribute("list_z_type_analysis_by_year", list_z_type_analysis_by_year);
		model.addAttribute("analysis_title_ac_year", ac_year);

		// **************************************************************************
		// yyyy/mm每十天合计
		// **************************************************************************
		//现在系统时间的 年/月
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
		String calendar_YM =sdf.format(calendar.getTime());
		request.setAttribute("calendar_YM", calendar_YM+"每十天合计");
		
		// ****************
		// for table
		// ****************
		// 年	  支出 分类
		// 2022    100  餐饮饮食
		// 2022    100  诚诚
		// 2021    100  诚诚
		List<ZhangzuAnalysis> list_zz_type_analysis = get_ac_type_ac_min_by_year_and_type(ac_year, ac_type);
		if (list_zz_type_analysis.size() == 0) {
			model.addAttribute("alert_count_0", "没查到数据");
		} else {
			model.addAttribute("list_zz_type_analysis", list_zz_type_analysis);
		}
		// ********************
		// for pulldown list
		// ********************
		// 年一覧
		List<ZhangzuAnalysis> list_ac_year = get_distinct_ac_year();

		// プルダウンの初期値
        model.addAttribute("selectedValue_ac_year", ac_year);
		model.addAttribute("list_ac_year", list_ac_year);

		// ********************
		// for pulldown list
		// ********************
		// z_type一覧
		List<ZhangzuAnalysis> list_ac_type = get_distinct_ac_type_by_year(ac_year);

		// プルダウンの初期値
        model.addAttribute("selectedValue_ac_type", ac_type);
		model.addAttribute("list_ac_type", list_ac_type);

		// ********************
		// for search button
		// ********************
		model.addAttribute("selectedValue_year_type", selectedValue_year_type);

		// **************************************************************************
		// for 期間（１日～１０日）	期間（１１日～２０日）	期間（２１日～月末）
		// **************************************************************************
		List<ZhangzuAnalysis> list_ac_type_ac_min_by_10days = get_ac_type_ac_min_by_10days();
		request.setAttribute("list_ac_type_ac_min_by_10days", list_ac_type_ac_min_by_10days);
		
		logger.info("["+this.getClass()+"][pie_post][end] to song_analysis_pie.html");
		
		return "song_analysis_pie";
	}

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
	public List<ZhangzuAnalysis> get_ac_type_ac_min_by_year_and_type(String ac_year, String ac_type) throws Exception {
		ZhangzuAnalysis zz_analysis;
		String str_ac;
		long ac_min;
		String str_ac_type;
		String strSQL2 =
		"SELECT " +
		"left(z_date,4) ac,z_type ac_type,sum(z_amount)*-1 ac_min "+
		"FROM t_zhangzu " +
		"where "+ 
		"left(z_date,4) = '" + ac_year + "' " +
		"and z_type = '" + ac_type + "' " + 
		"and z_io_div = '支出' " + 
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

	// 年	
	// 2022 
	// 2021 
	// 2020 
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