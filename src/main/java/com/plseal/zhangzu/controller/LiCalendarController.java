package com.plseal.zhangzu.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.plseal.zhangzu.common.LiMaster;
import com.plseal.zhangzu.entity.Calendar;


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


	@RequestMapping("/li/calendar_update")
	public String li_calendar_update(Model model, @RequestParam("id")Integer id) throws Exception {
		logger.info("["+this.getClass()+"][li_calendar_update][start]");

        List<Map<String, Object>> list = jdbcTemplate.queryForList("select id,C_DATE,C_TIME_FROM,C_TIME_TO,C_NAME,C_DIV,C_RESERVE FROM LI_CALENDAR WHERE id = ? ", id);
        logger.info("list.size():"+list.size());
        logger.info("list.get(0):"+list.get(0));
		Calendar calendar = new Calendar();
		calendar.setC_id(list.get(0).get("id").toString());
		calendar.setC_date(list.get(0).get("C_DATE").toString());
		calendar.setC_reserve(list.get(0).get("C_RESERVE").toString());
		model.addAttribute("calendar", calendar);
		
		List<String> list_am_pm = LiMaster.make_c_calendar_am_pm();
		model.addAttribute("list_am_pm", list_am_pm);
		logger.info("["+this.getClass()+"][li_calendar_update][end] to li_calendar_update.html");
		
		return "li_calendar_update";
	}

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
