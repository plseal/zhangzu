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
		List<ZhangzuAnalysis> list_z_type_analysis_by_year = songAnalysisService.get_ac_type_ac_min_by_year("2022");
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
		List<ZhangzuAnalysis> list_ac_type_ac_min_by_10days = songAnalysisService.get_ac_type_ac_min_by_10days();
		request.setAttribute("list_ac_type_ac_min_by_10days", list_ac_type_ac_min_by_10days);

		// ****************
		// for table
		// ****************
		// 年	  支出 分类
		// 2022    100  餐饮饮食
		// 2022    100  诚诚
		// 2021    100  诚诚
		List<ZhangzuAnalysis> list_zz_type_analysis = songAnalysisService.get_ac_type_ac_min_by_year_and_type("2022", "餐饮饮食", songAnalysisPieForm);
		request.setAttribute("list_zz_type_analysis", list_zz_type_analysis);
		
		// ********************
		// for pulldown list
		// ********************
		// 年一覧
		List<ZhangzuAnalysis> list_ac_year = songAnalysisService.get_distinct_ac_year();
		request.setAttribute("list_ac_year", list_ac_year);

		// ********************
		// for pulldown list
		// ********************
		// z_type一覧
		List<ZhangzuAnalysis> list_ac_type = songAnalysisService.get_distinct_ac_type_by_year("2022");
		request.setAttribute("list_ac_type", list_ac_type);
		
		// ********************
		// for search button
		// ********************
		String selectedValue_year_type = "2022_餐饮饮食";
		request.setAttribute("selectedValue_year_type", selectedValue_year_type);



		logger.info("["+this.getClass()+"][song_analysis_pie][end] to song_analysis_pie.html");

		return "song_analysis_pie";
	}

	//[统计分析 饼图].html[查询]ボタン押下後
	@RequestMapping(path = "/song/analysis/pie_post", method = RequestMethod.POST)
	public String song_analysis_pie_post(@ModelAttribute SongAnalysisPieForm songAnalysisPieForm, 
		Model model, @RequestParam("selectedValue_year_type") String selectedValue_year_type, HttpServletRequest request) throws Exception {
        logger.info("["+this.getClass()+"][song_analysis_pie_post][start] ");
        logger.info("["+this.getClass()+"][song_analysis_pie_post][selectedValue_year_type]" + selectedValue_year_type + " ");
		String[] array_selectedValue_year_type = selectedValue_year_type.split("_");

		String ac_year = array_selectedValue_year_type[0];
		String ac_type = array_selectedValue_year_type[1];

		// ****************
		// for pie chart
		// ****************
		// 年  支出 分类
		// 2022 100  餐饮饮食
		// 2022 100  诚诚
		List<ZhangzuAnalysis> list_z_type_analysis_by_year = songAnalysisService.get_ac_type_ac_min_by_year(ac_year);
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
		List<ZhangzuAnalysis> list_zz_type_analysis = songAnalysisService.get_ac_type_ac_min_by_year_and_type(ac_year, ac_type, songAnalysisPieForm);
		if (list_zz_type_analysis.size() == 0) {
			model.addAttribute("alert_count_0", "没查到数据");
		} else {
			model.addAttribute("list_zz_type_analysis", list_zz_type_analysis);
		}
		// ********************
		// for pulldown list
		// ********************
		// 年一覧
		List<ZhangzuAnalysis> list_ac_year = songAnalysisService.get_distinct_ac_year();

		// プルダウンの初期値
        model.addAttribute("selectedValue_ac_year", ac_year);
		model.addAttribute("list_ac_year", list_ac_year);

		// ********************
		// for pulldown list
		// ********************
		// z_type一覧
		List<ZhangzuAnalysis> list_ac_type = songAnalysisService.get_distinct_ac_type_by_year(ac_year);

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
		List<ZhangzuAnalysis> list_ac_type_ac_min_by_10days = songAnalysisService.get_ac_type_ac_min_by_10days();
		request.setAttribute("list_ac_type_ac_min_by_10days", list_ac_type_ac_min_by_10days);
		
		logger.info("["+this.getClass()+"][song_analysis_pie_post][end] to song_analysis_pie.html");
		
		return "song_analysis_pie";
	}

	//[统计分析 饼图].html[详细确认]ボタン押下後
	@RequestMapping(path = "/song/analysis/to_detail", method = RequestMethod.POST)
	public String song_analysis_to_detail(Model model, @RequestParam("year_type") String year_type) throws Exception {
        logger.info("["+this.getClass()+"][song_analysis_to_detail][start] ");
        logger.info("["+this.getClass()+"][song_analysis_to_detail][year_type]" + year_type + " ");
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
		logger.info("["+this.getClass()+"][song_analysis_to_detail][end] to:song_analysis_detail.html");
		return "song_analysis_detail";
	}

	
}