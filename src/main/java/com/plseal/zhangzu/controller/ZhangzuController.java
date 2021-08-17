package com.plseal.zhangzu.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ui.Model;

import com.plseal.zhangzu.entity.Zhangzu;
import java.util.Arrays;



/**
 * @Description 
 * @author songml
 *
 */
@Controller
@RequestMapping("/zhangzu")
public class ZhangzuController {
	private static final Logger logger = LoggerFactory.getLogger(ZhangzuController.class);

	@RequestMapping(path = "/index", method = RequestMethod.GET)
	public String index(Model model) throws Exception {
		List<String> list = Arrays.asList("aa", "bb", "cc");
		model.addAttribute("list", list);
		model.addAttribute("msg", "Hello World zhangzu!!!");
		/** 
		logger.info("["+this.getClass()+"][to_index_zhangzu][start]");
		logger.info("["+this.getClass()+"][to_index_zhangzu][FLG]"+FLG);
		logger.info("["+this.getClass()+"][to_index_zhangzu][AC]"+AC);
		logger.info("["+this.getClass()+"][to_index_zhangzu][IO]"+IO);
		logger.info("["+this.getClass()+"][to_index_zhangzu][AC_TYPE]"+AC_TYPE);
		List<Zhangzu> zhangzus=zhangzuService.get_all();
		if ("UPDATE".equals(FLG)) {
			zhangzuService.update(zhangzu);
		} else if ("INSERT".equals(FLG)){
			zhangzuService.insert(zhangzu);
		} else if ("DELETE".equals(FLG)){
			zhangzuService.delete(zhangzu);
		} else if ("2018".equals(FLG)){
			zhangzus = zhangzuService.get_2018();
		} else if ("2019".equals(FLG)){
			zhangzus = zhangzuService.get_2019();
		} else if ("2020".equals(FLG)){
			zhangzus = zhangzuService.get_2020();
		}else {
			if ("PLUS".equals(IO)) {
			     zhangzus = zhangzuService.get_one_month_plus(AC);
			}else if ("MIN".equals(IO)) {
				 
				 if(AC_TYPE != null){
					 zhangzus = zhangzuService.get_one_month_min_type(AC,AC_TYPE);
				 }else{
					 zhangzus = zhangzuService.get_one_month_min(AC);
				 }
			}else if ("MAIHUO".equals(IO)) {
				 zhangzus = zhangzuService.get_one_month_maihuo(AC);

			
			}				
		}

		
		
		request.setAttribute("zhangzus", zhangzus);
		if ("".equals(AC)) {
			AC = "2018/02";
		}else{
			// do nothing
		}	
		request.setAttribute("INDEX_AC", AC);
		//ResponseUtil.write(response, result);
		logger.info("["+this.getClass()+"][to_index_zhangzu][end] to index_zhangzu.jsp");
		**/
		//return "../../zhangzu/index_zhangzu";
		return "index";
	}

}
