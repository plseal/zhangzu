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
import java.util.Map;
import com.plseal.zhangzu.entity.Zhangzu;
import com.plseal.zhangzu.entity.ZhangzuAnalysis;

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
    
	@Autowired
    JdbcTemplate jdbcTemplate;

	@RequestMapping(path = "/index", method = RequestMethod.GET)
	public String index(Model model) throws Exception {
		//List<String> list = Arrays.asList("aa", "bb", "cc");
		List<Map<String, Object>> list;
        list = jdbcTemplate.queryForList("SELECT * FROM t_zhangzu WHERE z_date like '2021%' order by z_date desc ");
        // Map<String, String> resultJson = Collections.singletonMap("result", "OK");
        logger.info("list.size():"+list.size());
        logger.info("list.get(0):"+list.get(0));

		model.addAttribute("list", list);
		model.addAttribute("msg", "Hello World zhangzu!!!");
		
		return "index";
	}
	@RequestMapping(path = "/index_for_analysis", method = RequestMethod.GET)
	public String index_for_analysis(
		@RequestParam("AC") final String AC,
        @RequestParam("AC_TYPE") final String AC_TYPE,
		Model model) throws Exception {
		//List<String> list = Arrays.asList("aa", "bb", "cc");
		List<Map<String, Object>> list;
        list = jdbcTemplate.queryForList("SELECT * FROM t_zhangzu WHERE z_date like CONCAT('%',?,'%') and z_io_div = '支出' and z_type = ? order by z_date desc ",
		AC, AC_TYPE);

        // Map<String, String> resultJson = Collections.singletonMap("result", "OK");
        logger.info("list.size():"+list.size());
        logger.info("list.get(0):"+list.get(0));

		model.addAttribute("list", list);
		model.addAttribute("msg", "Hello World zhangzu!!!");
		
		return "index";
	}
	

	@RequestMapping("/analysis_2021")
	public String analysis_2021(HttpServletRequest request) throws Exception {
		logger.info("["+this.getClass()+"][analysis_2021][start]");
				
		ZhangzuAnalysis zz_analysis;
		
		long ac_min ;
		long ac_plus ;
		long ac_result;
		long ac_maihuo;
		List<ZhangzuAnalysis> list_zz_analysis = new ArrayList<ZhangzuAnalysis>();
		
		zz_analysis = new ZhangzuAnalysis();
		List<Map<String, Object>> list1_zz_analysis;
        list1_zz_analysis = jdbcTemplate.queryForList(
		"SELECT 1," +
		"ZHI.AC ac,"+
		"'XXXX' ac_type,"+
		"SHOU.AMOUNT ac_plus,"+
		"ZHI.AMOUNT ac_min,"+
		"SHOU.AMOUNT - ZHI.AMOUNT ac_result,"+
		"MAI.AMOUNT ac_maihuo "+
		"FROM v_zhangzu_zhichu_2021 ZHI " +
		"left join v_zhangzu_shouru_2021 SHOU on  ZHI.AC = SHOU.AC " + 
		"left join v_zhangzu_maihuo_2021 MAI on  ZHI.AC = MAI.AC " + 
		"order by ac");
        // Map<String, String> resultJson = Collections.singletonMap("result", "OK");
        logger.info("list.size():"+list1_zz_analysis.size());
        logger.info("list.get(0):"+list1_zz_analysis.get(0));
		/*
		ac_min = 0;
		ac_plus = 0;
		ac_result = 0;
		ac_maihuo = 0;
		//	2021/01	XXXX	0	    -911732	0  zhichu
		//	2021/01	XXXX	374334	0	    0
		//	2021/01	XXXX	0	    -911732	0  maihuo
		logger.info("["+this.getClass()+"][to_zhangzu_analysis_2021][list1_zz_analysis.size()]"+list1_zz_analysis.size());
		for(int i = 0 ; i < list1_zz_analysis.size() ; i++) {
			if (i == 0 ) {
				ac_min = list1_zz_analysis.get(i).getAc_min();
			} else if (i == 1){
				ac_plus = list1_zz_analysis.get(i).getAc_plus();
			} else {
				ac_maihuo = list1_zz_analysis.get(i).getAc_min();
			}
		}
		ac_result = ac_plus + ac_min;
		zz_analysis.setAc(zhangzu_ac);
		zz_analysis.setAc_min(ac_min);
		zz_analysis.setAc_plus(ac_plus);
		zz_analysis.setAc_result(ac_result);
		zz_analysis.setAc_maihuo(ac_maihuo);
		logger.info("["+this.getClass()+"][to_zhangzu_analysis_2021][ac_maihuo]"+ac_maihuo);
		list_zz_analysis.add(zz_analysis);

		request.setAttribute("list_zz_analysis", list_zz_analysis);

		List<ZhangzuAnalysis> list_zz_type_analysis = new ArrayList<ZhangzuAnalysis>();
		zz_analysis = new ZhangzuAnalysis();
		list_zz_type_analysis = zhangzuService.get_analysis_by_type(zhangzu_ac);
		
		request.setAttribute("list_zz_type_analysis", list_zz_type_analysis);
		
		
		
		List<ZhangzuAnalysis> list_2021_zz_analysis = zhangzuService.get_analysis_2021();
		request.setAttribute("list_2021_zz_analysis", list_2021_zz_analysis);
		//ResponseUtil.write(response, result);
		logger.info("["+this.getClass()+"][to_zhangzu_analysis_2021][end] to zhangzu_analysis_2021.jsp");
		 */
		return "analysis_2021";
	}

}
