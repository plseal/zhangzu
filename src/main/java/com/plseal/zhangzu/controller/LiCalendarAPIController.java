package com.plseal.zhangzu.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.plseal.zhangzu.common.DateUtils;
import com.plseal.zhangzu.common.HttpUtils;
import com.plseal.zhangzu.entity.CalendarModel;
import com.plseal.zhangzu.entity.CalendarResult;


/**
 * 
 * 李帳本预约用API
 *
 */
@RestController
public class LiCalendarAPIController {
	private static final Logger logger = LoggerFactory.getLogger(LiCalendarAPIController.class);
    
	@Autowired
    JdbcTemplate jdbcTemplate;
	
	@RequestMapping("/li_api_calendar")
	public String li_api_calendar(String div, HttpServletRequest request) {
		
		List<Map<String, Object>> list1_li_calendar;
		String strSQL1 =
		"SELECT id,C_DATE,C_TIME_FROM,C_TIME_TO,C_NAME,C_DIv,C_RESERVE FROM LI_CALENDAR " +
		"order by C_DATE";
		logger.info("["+this.getClass()+"][li_api_calendar][SQL1]"+strSQL1);
        list1_li_calendar = jdbcTemplate.queryForList(strSQL1);
        // Map<String, String> resultJson = Collections.singletonMap("result", "OK");
        logger.info("list.size():"+list1_li_calendar.size());
        logger.info("list.get(0):"+list1_li_calendar.get(0));
		
		CalendarModel cmodel = new CalendarModel();
		cmodel.success = "0";
		cmodel.result = new ArrayList<CalendarResult>();
		logger.debug(String.valueOf(list1_li_calendar.size()));
		if (list1_li_calendar.size() > 0) {
			cmodel.success = "1";
			for (int i = 0; i < list1_li_calendar.size(); i++) {
				CalendarResult result1 = new CalendarResult();
				result1.id = String.valueOf(i);
				String c_id = (String)list1_li_calendar.get(i).get("id").toString();
				String eventDate = (String)list1_li_calendar.get(i).get("C_DATE");
				// String c_name = (String)list1_li_calendar.get(i).get("C_NAME");
				String c_reserve = (String)list1_li_calendar.get(i).get("C_RESERVE");
				
				if (HttpUtils.isAdmin(request)) {
					// adminは変更可能
					result1.url = "./calendar_update?id=" + c_id + "&c_date='9999/99/99'";
				} else {
					// 普通userは変更不可
					result1.url = "./calendar";
				}
				
				
				if (c_reserve.equals("预约可")) {
					result1.class_ = "event-success";
					result1.title = c_reserve;
				} else {
					result1.class_ = "event-important";
					result1.title = c_reserve + "已预约";
				}
				
				Long lEventDate = DateUtils.stringToLong(eventDate,"yyyy/MM/dd");
				result1.start = String.valueOf(lEventDate+34550);
				result1.end = String.valueOf(lEventDate+34550);

				cmodel.result.add(result1);

			}

		}
		String json = "{}";
		ObjectMapper mapper = new ObjectMapper();
		try {
			json = mapper.writeValueAsString(cmodel);
		} catch (JsonProcessingException e) {
			
			e.printStackTrace();
		}


		return json;
	}
	
}
