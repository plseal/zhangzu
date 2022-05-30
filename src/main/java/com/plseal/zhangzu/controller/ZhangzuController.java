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

		String sql = "SELECT * FROM li_zhangzu WHERE z_date like '2022%' order by z_date desc ";

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
		String sql = "INSERT INTO li_zhangzu VALUES(null,?,?,?,?,?,?,?)";
		jdbcTemplate.update(sql,z_date,z_name,z_amount,z_type,z_io_div,z_remark,z_m_amount);

		String z_date_month = z_date.substring(0,7);
		logger.info("z_date_month:" + z_date_month);

		String sql_index = "SELECT * FROM li_zhangzu WHERE z_date like '" + z_date_month + "%' order by z_date desc ";
        
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
			String sql_update = "update li_zhangzu set z_date = ?,z_name = ?,z_amount = ?,z_type = ?,z_io_div = ?,z_remark = ?,z_m_amount = ? where id = ?";
			jdbcTemplate.update(sql_update,z_date,z_name,z_amount,z_type,z_io_div,z_remark,z_m_amount,id);
		} else {
			String sql_delete = "delete from li_zhangzu where id = ?";
			jdbcTemplate.update(sql_delete,id);
		}
		String z_date_month = z_date.substring(0,7);
		logger.info("z_date_month:" + z_date_month);
		String sql_index = "SELECT * FROM li_zhangzu WHERE z_date like '"+z_date_month+"%' order by z_date desc ";
        
		RowMapper<Zhangzu> rowMapper = new BeanPropertyRowMapper<Zhangzu>(Zhangzu.class);
        List<Zhangzu> list_zhangzu = jdbcTemplate.query(sql_index, rowMapper);

		model.addAttribute("list_zhangzu", list_zhangzu);

		return "index";
	}

	@PostMapping(path = "/update")
	public String update(Model model, @RequestParam("id")Integer id) throws Exception {

		List<Map<String, Object>> list;
        list = jdbcTemplate.queryForList("SELECT * FROM li_zhangzu WHERE id = ? order by z_date desc ", id);
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

	@RequestMapping("/analysis_2022")
	public String analysis_2022(HttpServletRequest request) throws Exception {
		logger.info("["+this.getClass()+"][analysis_2022][start]");
		

		// 年月	支出	收入	余额	买货
		// 2022/01	9608	0	9608	0
		ZhangzuAnalysis zz_analysis;
		
		String str_ac = "";
		long ac_min ;
		long ac_plus ;
		long ac_result;
		long ac_maihuo;
		String str_ac_type = "";
		List<ZhangzuAnalysis> list_zz_analysis = new ArrayList<ZhangzuAnalysis>();
		
		zz_analysis = new ZhangzuAnalysis();
		List<Map<String, Object>> list1_zz_analysis;
		String strSQL1 =
		"SELECT 1," +
		"ZHI.AC ac,"+
		"'XXXX' ac_type,"+
		"IFNULL(SHOU.AMOUNT,0) ac_plus,"+
		"IFNULL(ZHI.AMOUNT,0) ac_min,"+
		"IFNULL(SHOU.AMOUNT - ZHI.AMOUNT, 0) ac_result,"+
		"IFNULL(MAI.AMOUNT,0) ac_maihuo "+
		"FROM v_zhangzu_zhichu_2022 ZHI " +
		"left join v_zhangzu_shouru_2022 SHOU on  ZHI.AC = SHOU.AC " + 
		"left join v_zhangzu_maihuo_2022 MAI on  ZHI.AC = MAI.AC " + 
		"order by ac";
		logger.info("["+this.getClass()+"][analysis_2022][SQL1]"+strSQL1);
        list1_zz_analysis = jdbcTemplate.queryForList(strSQL1);
        // Map<String, String> resultJson = Collections.singletonMap("result", "OK");
        logger.info("list.size():"+list1_zz_analysis.size());
        logger.info("list.get(0):"+list1_zz_analysis.get(0));

		logger.info("["+this.getClass()+"][to_zhangzu_analysis_2022][list1_zz_analysis.size()]"+list1_zz_analysis.size());
		for(int i = 0 ; i < list1_zz_analysis.size() ; i++) {
			zz_analysis = new ZhangzuAnalysis();
			str_ac  = list1_zz_analysis.get(i).get("ac").toString();
			ac_min = Integer.valueOf(list1_zz_analysis.get(i).get("ac_min").toString());
			ac_plus = Integer.valueOf(list1_zz_analysis.get(i).get("ac_plus").toString());
			ac_result = Integer.valueOf(list1_zz_analysis.get(i).get("ac_result").toString());
			ac_maihuo = Integer.valueOf(list1_zz_analysis.get(i).get("ac_maihuo").toString());
			str_ac_type = list1_zz_analysis.get(i).get("ac_type").toString();
			zz_analysis.setAc(str_ac);
			zz_analysis.setAc_min(ac_min);
			zz_analysis.setAc_plus(ac_plus);
			zz_analysis.setAc_result(ac_result);
			zz_analysis.setAc_maihuo(ac_maihuo);
			zz_analysis.setAc_type(str_ac_type);
			list_zz_analysis.add(zz_analysis);
		}

		request.setAttribute("list_zz_analysis", list_zz_analysis);

		// 年月	  支出 分类
		// 2022/1 100  餐饮饮食
		// 2022/1 100  诚诚
		String strSQL2 =
		"SELECT " +
		"left(z_date,7) ac,z_type ac_type,sum(z_amount)*-1 ac_min "+
		"FROM li_zhangzu " +
		"where left(z_date,7) = '2022/01'" + 
		"and z_io_div = '支出'  " + 
		"GROUP BY  left(z_date,7),z_type   " + 
		"order by ac_min";

		logger.info("["+this.getClass()+"][analysis_2022][SQL2]"+strSQL2);
		List<ZhangzuAnalysis> list_zz_type_analysis = new ArrayList<ZhangzuAnalysis>();
		List<Map<String, Object>> list1_zz_type_analysis = jdbcTemplate.queryForList(strSQL2);
        logger.info("list.size():"+list1_zz_type_analysis.size());
        logger.info("list.get(0):"+list1_zz_type_analysis.get(0));
		
		for(int i = 0 ; i < list1_zz_type_analysis.size() ; i++) {
			zz_analysis = new ZhangzuAnalysis();
			str_ac  = list1_zz_type_analysis.get(i).get("ac").toString();
			ac_min = Integer.valueOf(list1_zz_type_analysis.get(i).get("ac_min").toString());
			str_ac_type = list1_zz_type_analysis.get(i).get("ac_type").toString();
			zz_analysis.setAc(str_ac);
			zz_analysis.setAc_min(ac_min);
			zz_analysis.setAc_type(str_ac_type);
			list_zz_type_analysis.add(zz_analysis);
		}

		request.setAttribute("list_zz_type_analysis", list_zz_type_analysis);
		


		
		logger.info("["+this.getClass()+"][analysis_2022][end] to analysis_2022.jsp");

		return "analysis_2022";
	}

}
