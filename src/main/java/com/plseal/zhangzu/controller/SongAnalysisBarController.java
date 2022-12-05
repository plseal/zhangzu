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
	public String song_analysis_bar(String zhangzu_ac, String io_div, HttpServletRequest request) throws Exception {
		logger.info("["+this.getClass()+"][song_analysis_bar][start]");
		

		logger.info("["+this.getClass()+"][song_analysis_bar][end] to song_analysis_bar.html");

		return "song_analysis_bar";
	}


	
}