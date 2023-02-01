package com.plseal.zhangzu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import com.plseal.zhangzu.entity.ZhangzuAnalysis;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.plseal.zhangzu.entity.SongAnalysisPieForm;
import com.plseal.zhangzu.entity.Zhangzu;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import com.plseal.zhangzu.service.SongAnalysisService;

/*
 * 宋帳本分析用(饼图)
 * t_zhangzu
 */
@Controller
public class SongAnalysisPieController {
    private static final Logger logger = LoggerFactory.getLogger(SongAnalysisPieController.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

	// サービスクラスがＤＩされる。
    @Autowired 
	SongAnalysisService songAnalysisService;

	//index.html[统计分析 饼图]ボタン押下後
	@RequestMapping("/song/analysis/pie")
	public String song_analysis_pie(HttpServletRequest request) throws Exception {
		logger.info("["+this.getClass()+"][song_analysis_pie][start]");
		SongAnalysisPieForm songAnalysisPieForm = new SongAnalysisPieForm();

		// ****************
		// for pie chart
		// ****************
		// 年  支出 分类
		// 2022 100  餐饮饮食
		// 2022 100  诚诚
		List<ZhangzuAnalysis> list_z_type_analysis_by_year = songAnalysisService.get_ac_type_ac_min_by_year("2023");
		request.setAttribute("list_z_type_analysis_by_year", list_z_type_analysis_by_year);
		request.setAttribute("analysis_title_ac_year", "2023");

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
		List<ZhangzuAnalysis> list_ac_type_ac_min_by_10days = songAnalysisService.get_ac_type_ac_min_by_10days(calendar);
		request.setAttribute("list_ac_type_ac_min_by_10days", list_ac_type_ac_min_by_10days);

		logger.info("["+this.getClass()+"][song_analysis_pie][end] to song_analysis_pie.html");

		return "song_analysis_pie";
	}

	//[统计分析 饼图][先月]ボタン押下後
	@RequestMapping("/song/analysis/pie_lastmonth")
	public String song_analysis_pie_lastmonth(HttpServletRequest request) throws Exception {
		logger.info("["+this.getClass()+"][song_analysis_pie_lastmonth][start]");
		SongAnalysisPieForm songAnalysisPieForm = new SongAnalysisPieForm();

		// ****************
		// for pie chart
		// ****************
		// 年  支出 分类
		// 2022 100  餐饮饮食
		// 2022 100  诚诚
		List<ZhangzuAnalysis> list_z_type_analysis_by_year = songAnalysisService.get_ac_type_ac_min_by_year("2023");
		request.setAttribute("list_z_type_analysis_by_year", list_z_type_analysis_by_year);
		request.setAttribute("analysis_title_ac_year", "2023");

		// **************************************************************************
		// yyyy/mm每十天合计
		// **************************************************************************
		//现在系统时间的 年/月
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
		String calendar_YM =sdf.format(calendar.getTime());
		request.setAttribute("calendar_YM", calendar_YM+"每十天合计");

		// **************************************************************************
		// for 期間（１日～１０日）	期間（１１日～２０日）	期間（２１日～月末）
		// **************************************************************************
		List<ZhangzuAnalysis> list_ac_type_ac_min_by_10days = songAnalysisService.get_ac_type_ac_min_by_10days(calendar);
		request.setAttribute("list_ac_type_ac_min_by_10days", list_ac_type_ac_min_by_10days);

		logger.info("["+this.getClass()+"][song_analysis_pie_lastmonth][end] to song_analysis_pie.html");

		return "song_analysis_pie";
	}


	
}