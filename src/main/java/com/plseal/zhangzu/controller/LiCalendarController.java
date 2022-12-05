package com.plseal.zhangzu.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 
 * 李帳本预约用
 *
 */
@Controller
public class LiCalendarController {
	private static final Logger logger = LoggerFactory.getLogger(LiCalendarController.class);
    
	@Autowired
    JdbcTemplate jdbcTemplate;

	@RequestMapping("/li/calendar")
	public String li_calendar(String zhangzu_ac, HttpServletRequest request) throws Exception {
		logger.info("["+this.getClass()+"][li_calendar][start]");

		
		logger.info("["+this.getClass()+"][li_calendar][end] to li_calendar.html");

		return "li_calendar";
	}
	@RequestMapping("/month.html")
	public String li_month(String zhangzu_ac, HttpServletRequest request) throws Exception {
		logger.info("["+this.getClass()+"][li_month][start]");

		
		logger.info("["+this.getClass()+"][li_month][end] to month.html");

		return "tmpls/month";
	}
	@RequestMapping("/month-day.html")
	public String li_month_day(String zhangzu_ac, HttpServletRequest request) throws Exception {
		logger.info("["+this.getClass()+"][li_month_day][start]");
		
		logger.info("["+this.getClass()+"][li_month_day][end] to month-day.html");

		return "tmpls/month-day";
	}

	@RequestMapping("/events-list.html")
	public String li_events_list(String zhangzu_ac, HttpServletRequest request) throws Exception {
		logger.info("["+this.getClass()+"][li_events_list][start]");
		
		logger.info("["+this.getClass()+"][li_events_list][end] to events-list.html");

		return "tmpls/events-list";
	}
	
}
