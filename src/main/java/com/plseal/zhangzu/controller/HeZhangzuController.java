package com.plseal.zhangzu.controller;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

import com.plseal.zhangzu.common.SongMaster;
import com.plseal.zhangzu.entity.Zhangzu;
import com.plseal.zhangzu.service.ModifyService;

/**
 * 
 * 贺帳本增删改查用
 *
 */
@Controller
@RequestMapping("/he")
public class HeZhangzuController {
	private static final Logger logger = LoggerFactory.getLogger(HeZhangzuController.class);
    
	@Autowired
    JdbcTemplate jdbcTemplate;
	
	@Autowired 
	ModifyService modifyService;

	String target_table = "he_zhangzu";

	@RequestMapping(path = "/index", method = RequestMethod.GET)
	public String index(Model model) throws Exception {
		//现在系统时间的年
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String calendar_Y =sdf.format(calendar.getTime());
		List<Zhangzu> list_zhangzu = modifyService.query_db_index(target_table, calendar_Y);

		model.addAttribute("list_zhangzu", list_zhangzu);
		return "he_index";
	}

	@RequestMapping(path = "/insert", method = RequestMethod.GET)
	public String insert(Model model) throws Exception {

		Zhangzu zhangzu = new Zhangzu();
		Date today = new Date();
		zhangzu.setZ_date(new SimpleDateFormat("yyyy/MM/dd").format(today));

		List<String> z_type_list = SongMaster.make_ztype_list();
		List<String> z_io_div_list = SongMaster.make_z_io_div_list();
		
		zhangzu.setZ_type(z_type_list.get(0));
		zhangzu.setZ_io_div(z_io_div_list.get(0));
		// logger.info("zhangzu:" + zhangzu.toString());
		model.addAttribute("zhangzu", zhangzu);
		model.addAttribute("z_type_list", z_type_list);
		model.addAttribute("z_io_div_list", z_io_div_list);
		
		return "he_insert";
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
		String sql = "INSERT INTO " + target_table + " VALUES(null,?,?,?,?,?,?,?)";
		jdbcTemplate.update(sql,z_date,z_name,z_amount,z_type,z_io_div,z_remark,z_m_amount);
		
		return "he_crud_OK";
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
			String sql_update = "update "+target_table+" set z_date = ?,z_name = ?,z_amount = ?,z_type = ?,z_io_div = ?,z_remark = ?,z_m_amount = ? where id = ?";
			jdbcTemplate.update(sql_update,z_date,z_name,z_amount,z_type,z_io_div,z_remark,z_m_amount,id);
		} else {
			String sql_delete = "delete from "+target_table+" where id = ?";
			jdbcTemplate.update(sql_delete,id);
		}
		return "he_crud_OK";
	}

	@PostMapping(path = "/update")
	public String update(Model model, @RequestParam("id")Integer id) throws Exception {

		List<Zhangzu> list_zhangzu = modifyService.query_db_for_update_html(target_table,id);
        logger.info("list.size():"+list_zhangzu.size());
        logger.info("list.get(0):"+list_zhangzu.get(0));
		model.addAttribute("zhangzu", list_zhangzu.get(0));
		
		List<String> z_type_list = SongMaster.make_ztype_list();
		List<String> z_io_div_list = SongMaster.make_z_io_div_list();

		model.addAttribute("z_type_list", z_type_list);
		model.addAttribute("z_io_div_list", z_io_div_list);

		return "he_update";
	}

}
