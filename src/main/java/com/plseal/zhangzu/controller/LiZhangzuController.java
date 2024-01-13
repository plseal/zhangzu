package com.plseal.zhangzu.controller;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

import com.plseal.zhangzu.common.LiMaster;
import com.plseal.zhangzu.entity.Zhangzu;
import com.plseal.zhangzu.service.ModifyService;

/**
 * 
 * 李帳本增删改查用
 *
 */
@Controller
@RequestMapping("/li")
public class LiZhangzuController {
	private static final Logger logger = LoggerFactory.getLogger(LiZhangzuController.class);
    
	@Autowired
    JdbcTemplate jdbcTemplate;

	@Autowired 
	ModifyService modifyService;

	String target_table = "li_zhangzu";

	@RequestMapping(path = "/index", method = RequestMethod.GET)
	public String index(Model model) throws Exception {
		//现在系统时间的年
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String calendar_Y =sdf.format(calendar.getTime());
        List<Zhangzu> list_zhangzu = modifyService.query_db_index(target_table, calendar_Y);
		model.addAttribute("list_zhangzu", list_zhangzu);
		return "li_index";
	}
	@RequestMapping(path = "/index_2022", method = RequestMethod.GET)
	public String index_2022(Model model) throws Exception {

		List<Zhangzu> list_zhangzu = modifyService.query_db_index(target_table, "2022");

		model.addAttribute("list_zhangzu", list_zhangzu);
		return "li_index";
	}

	@RequestMapping(path = "/index_2023", method = RequestMethod.GET)
	public String index_2023(Model model) throws Exception {

		List<Zhangzu> list_zhangzu = modifyService.query_db_index(target_table, "2023");

		model.addAttribute("list_zhangzu", list_zhangzu);
		return "li_index";
	}
	@RequestMapping(path = "/insert", method = RequestMethod.GET)
	public String insert(Model model) throws Exception {

		Zhangzu zhangzu = new Zhangzu();
		Date today = new Date();
		zhangzu.setZ_date(new SimpleDateFormat("yyyy/MM/dd").format(today));

		List<String> z_type_list = LiMaster.make_ztype_list();
		List<String> z_io_div_list = LiMaster.make_z_io_div_list();
		
		zhangzu.setZ_type(z_type_list.get(0));
		zhangzu.setZ_io_div(z_io_div_list.get(0));
		// logger.info("zhangzu:" + zhangzu.toString());
		model.addAttribute("zhangzu", zhangzu);
		model.addAttribute("z_type_list", z_type_list);
		model.addAttribute("z_io_div_list", z_io_div_list);
		
		return "li_insert";
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
		String sql = "INSERT INTO "+ target_table +" VALUES(null,?,?,?,?,?,?,?)";
		jdbcTemplate.update(sql,z_date,z_name,z_amount,z_type,z_io_div,z_remark,z_m_amount);
		
		return "li_crud_OK";
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
			String sql_update = "update " + target_table +" set z_date = ?,z_name = ?,z_amount = ?,z_type = ?,z_io_div = ?,z_remark = ?,z_m_amount = ? where id = ?";
			jdbcTemplate.update(sql_update,z_date,z_name,z_amount,z_type,z_io_div,z_remark,z_m_amount,id);
		} else {
			String sql_delete = "delete from "+ target_table +" where id = ?";
			jdbcTemplate.update(sql_delete,id);
		}
		return "li_crud_OK";
	}

	@PostMapping(path = "/update")
	public String update(Model model, @RequestParam("id")Integer id) throws Exception {

		List<Map<String, Object>> list;
        list = jdbcTemplate.queryForList("SELECT * FROM " + target_table +" WHERE id = ? order by z_date desc ", id);
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
		
		List<String> z_type_list = LiMaster.make_ztype_list();
		List<String> z_io_div_list = LiMaster.make_z_io_div_list();

		model.addAttribute("z_type_list", z_type_list);
		model.addAttribute("z_io_div_list", z_io_div_list);

		return "li_update";
	}

}
