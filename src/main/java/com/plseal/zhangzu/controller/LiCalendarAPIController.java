package com.plseal.zhangzu.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.plseal.zhangzu.common.DateUtils;
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
	public String li_api_calendar(String div) {
		
		List<Map<String, Object>> list1_zz_analysis;
		String strSQL1 =
		"SELECT C_DATE,C_TIME_FROM,C_TIME_TO,C_NAME,C_DIv,C_RESERVE FROM LI_CALENDAR " +
		"order by C_DATE";
		logger.info("["+this.getClass()+"][li_api_calendar][SQL1]"+strSQL1);
        list1_zz_analysis = jdbcTemplate.queryForList(strSQL1);
        // Map<String, String> resultJson = Collections.singletonMap("result", "OK");
        logger.info("list.size():"+list1_zz_analysis.size());
        logger.info("list.get(0):"+list1_zz_analysis.get(0));
		
		CalendarModel cmodel = new CalendarModel();
		cmodel.success = "0";
		cmodel.result = new ArrayList<CalendarResult>();
		logger.debug(String.valueOf(list1_zz_analysis.size()));
		if (list1_zz_analysis.size() > 0) {
			cmodel.success = "1";
			for (int i = 0; i < list1_zz_analysis.size(); i++) {
				CalendarResult result1 = new CalendarResult();
				result1.id = String.valueOf(i);
				String strTitleSub2 = "test";
				String strTitle = "";
				String eventDate = (String)list1_zz_analysis.get(i).get("C_DATE");

				result1.url = "./calendar_detail?EMPLOYEE_ID="+"&DEPARTMENT_ID="+"&EVENT_DATE=" + eventDate;
				strTitle = strTitleSub2;

				result1.title = strTitle;
				
				// if (Double.valueOf(eventMaxFever) >= ConstantsFeverWeb.FEVER_THRESHOLD) {
				// 	result1.class_ = "event-important";
				// } else {
				result1.class_ = "event-success";
				// }
				
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
