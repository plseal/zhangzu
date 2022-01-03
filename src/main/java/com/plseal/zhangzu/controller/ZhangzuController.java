package com.plseal.zhangzu.controller;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.plseal.zhangzu.common.master;
import com.plseal.zhangzu.entity.Zhangzu;
import com.plseal.zhangzu.entity.ZhangzuAnalysis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

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

		String sql = "SELECT * FROM t_zhangzu WHERE z_date like '2022%' order by z_date desc ";

		RowMapper<Zhangzu> rowMapper = new BeanPropertyRowMapper<Zhangzu>(Zhangzu.class);
        List<Zhangzu> list_zhangzu = jdbcTemplate.query(sql, rowMapper);

		model.addAttribute("list_zhangzu", list_zhangzu);
		return "index";
	}

	@RequestMapping(path = "/insert", method = RequestMethod.GET)
	public String insert(Model model) throws Exception {

		Zhangzu zhangzu = new Zhangzu();
		Date today = new Date();
		zhangzu.setZ_date(new SimpleDateFormat("yyyy/MM/dd").format(today));
		model.addAttribute("zhangzu", zhangzu);

		List<String> z_type_list = master.make_ztype_list();
		
		model.addAttribute("z_type_list", z_type_list);
		
		return "insert";
	}

	@PostMapping(path = "/insert_post")
	public String insert_post(Model model,
		@RequestParam("z_date") String z_date,
		@RequestParam("z_name") String z_name,
		@RequestParam("z_amount") BigInteger z_amount,
		@RequestParam("z_type") String z_type,
		@RequestParam("z_io_div") String z_io_div,
		@RequestParam("z_remark") String z_remark,
		@RequestParam("z_m_amount") BigInteger z_m_amount
		) throws Exception {
		logger.info("insert_post() z_date:" + z_date);
		String sql = "INSERT INTO t_zhangzu VALUES(null,?,?,?,?,?,?,?)";
		jdbcTemplate.update(sql,z_date,z_name,z_amount,z_type,z_io_div,z_remark,z_m_amount);

		String z_date_month = z_date.substring(0,7);
		logger.info("z_date_month:" + z_date_month);

		String sql_index = "SELECT * FROM t_zhangzu WHERE z_date like '" + z_date_month + "%' order by z_date desc ";
        
		RowMapper<Zhangzu> rowMapper = new BeanPropertyRowMapper<Zhangzu>(Zhangzu.class);
        List<Zhangzu> list_zhangzu = jdbcTemplate.query(sql_index, rowMapper);

		model.addAttribute("list_zhangzu", list_zhangzu);
		return "index";
	}

	@PostMapping(path = "/update_delete_post")
	public String update_delete_post(Model model,
		@RequestParam("z_date") String z_date,
		@RequestParam("z_name") String z_name,
		@RequestParam("z_amount") BigInteger z_amount,
		@RequestParam("z_type") String z_type,
		@RequestParam("z_io_div") String z_io_div,
		@RequestParam("z_remark") String z_remark,
		@RequestParam("z_m_amount") BigInteger z_m_amount,
		@RequestParam("id") String id,
		@RequestParam("update_delete_button") String update_delete_button
		) throws Exception {
		logger.info("update_delete_post() update_delete_button:" + update_delete_button);

		if("UPDATE".equals(update_delete_button)) {
			String sql_update = "update t_zhangzu set z_date = ?,z_name = ?,z_amount = ?,z_type = ?,z_io_div = ?,z_remark = ?,z_m_amount = ? where id = ?";
			jdbcTemplate.update(sql_update,z_date,z_name,z_amount,z_type,z_io_div,z_remark,z_m_amount,id);
		} else {
			String sql_delete = "delete from t_zhangzu where id = ?";
			jdbcTemplate.update(sql_delete,id);
		}
		String z_date_month = z_date.substring(0,7);
		logger.info("z_date_month:" + z_date_month);
		String sql_index = "SELECT * FROM t_zhangzu WHERE z_date like '"+z_date_month+"%' order by z_date desc ";
        
		RowMapper<Zhangzu> rowMapper = new BeanPropertyRowMapper<Zhangzu>(Zhangzu.class);
        List<Zhangzu> list_zhangzu = jdbcTemplate.query(sql_index, rowMapper);

		model.addAttribute("list_zhangzu", list_zhangzu);

		return "index";
	}

	@PostMapping(path = "/update")
	public String update(Model model, @RequestParam("id")Integer id) throws Exception {

		List<Map<String, Object>> list;
        list = jdbcTemplate.queryForList("SELECT * FROM t_zhangzu WHERE id = ? order by z_date desc ", id);
        logger.info("list.size():"+list.size());
        logger.info("list.get(0):"+list.get(0));
		Zhangzu zhangzu = new Zhangzu();
		zhangzu.setId(Integer.valueOf(list.get(0).get("id").toString()));
		zhangzu.setZ_date(list.get(0).get("z_date").toString());
		zhangzu.setZ_name(list.get(0).get("z_name").toString());
		zhangzu.setZ_amount(Integer.valueOf(list.get(0).get("z_amount").toString()));
		zhangzu.setZ_type(list.get(0).get("z_type").toString());
		zhangzu.setZ_io_div(list.get(0).get("z_io_div").toString());
		zhangzu.setZ_remark(list.get(0).get("z_remark").toString());
		zhangzu.setZ_m_amount(Integer.valueOf(list.get(0).get("z_m_amount").toString()));
		model.addAttribute("zhangzu", zhangzu);
		
		List<String> z_type_list = master.make_ztype_list();
		
		model.addAttribute("z_type_list", z_type_list);

		return "update";
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
