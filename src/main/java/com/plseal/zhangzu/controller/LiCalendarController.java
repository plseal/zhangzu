package com.plseal.zhangzu.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.plseal.zhangzu.common.DateUtils;
import com.plseal.zhangzu.common.HttpUtils;
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
	public String li_calendar_update(Model model, @RequestParam("id")Integer id, @RequestParam("c_date")String c_date) throws Exception {
		logger.info("["+this.getClass()+"][li_calendar_update][start]");
		// 新規の場合
		if (id ==0) {
			Calendar calendar = new Calendar();
			calendar.setC_id("0");
			
			Date true_date = DateUtils.stringToDate(c_date, "yyyy-MM-dd");
			logger.info("["+this.getClass()+"][li_calendar_update][true_date]:"+true_date);
			String surasyo_date = DateUtils.dateToString(true_date, "yyyy/MM/dd");
			calendar.setC_date(surasyo_date);
			// 默认上午
			calendar.setC_reserve("上午");
			model.addAttribute("calendar", calendar);
		// 更新 or 削除の場合
		} else {
			List<Map<String, Object>> list = jdbcTemplate.queryForList("select id,C_DATE,C_TIME_FROM,C_TIME_TO,C_NAME,C_DIV,C_RESERVE FROM LI_CALENDAR WHERE id = ? ", id);
			logger.info("list.size():"+list.size());
			logger.info("list.get(0):"+list.get(0));
			Calendar calendar = new Calendar();
			calendar.setC_id(list.get(0).get("id").toString());
			calendar.setC_date(list.get(0).get("C_DATE").toString());
			calendar.setC_name(list.get(0).get("C_NAME").toString());
			calendar.setC_reserve(list.get(0).get("C_RESERVE").toString());
			model.addAttribute("calendar", calendar);
		}

		
		List<String> list_am_pm = LiMaster.make_c_calendar_am_pm();
		model.addAttribute("list_am_pm", list_am_pm);
		logger.info("["+this.getClass()+"][li_calendar_update][end] to li_calendar_update.html");
		
		return "li_calendar_update";
	}

	@PostMapping(path = "/li/calendar_update_delete_post")
	public String li_calendar_update_delete_post(Model model,
		@RequestParam("c_date") String c_date,
		@RequestParam("c_name") String c_name,
		@RequestParam("c_reserve") String c_reserve,
		@RequestParam("id") String id,
		@RequestParam("insert_update_delete_button") String insert_update_delete_button
		) throws Exception {
		logger.info("insert_update_delete_button() insert_update_delete_button:" + insert_update_delete_button);

		if("INSERT".equals(insert_update_delete_button)) {
			String sql_insert = "insert INTO LI_CALENDAR (c_date,c_time_from,c_time_to,c_name,c_div,c_reserve,c_remark) VALUES(?,?,?,?,?,?,?)";
			jdbcTemplate.update(sql_insert,c_date,"c_time_from","c_time_to",c_name,"c_div",c_reserve,"已预约");
			
		} else if("UPDATE".equals(insert_update_delete_button)) {
			String sql_update = "update LI_CALENDAR set c_date = ?,c_name = ?,c_reserve = ? where id = ?";
			jdbcTemplate.update(sql_update,c_date,c_name,c_reserve,id);
		} else {
			String sql_delete = "delete from LI_CALENDAR where id = ?";
			jdbcTemplate.update(sql_delete,id);
		}
		return "li_calendar_crud_OK";
	}

	@RequestMapping("/li/calendar")
	public String li_calendar(String zhangzu_ac, HttpServletRequest request) throws Exception {
		logger.info("["+this.getClass()+"][li_calendar][start]");

        String client_ip = HttpUtils.getRequestIP(request);
        logger.info("["+this.getClass()+"][li_calendar][client_ip]"+client_ip);
		
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
