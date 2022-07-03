package com.plseal.zhangzu.controller;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.plseal.zhangzu.common.SongMaster;
import com.plseal.zhangzu.entity.Zhangzu;

/**
 * 
 * 宋帳本增删改查用
 *
 */
@Controller
@RequestMapping("/song")
public class SongZhangzuController {
	private static final Logger logger = LoggerFactory.getLogger(SongZhangzuController.class);
    
	@Autowired
    JdbcTemplate jdbcTemplate;

	@RequestMapping(path = "/index", method = RequestMethod.GET)
	public String index(Model model) throws Exception {

		String sql = "SELECT id,z_date,z_name,z_amount,z_type,z_io_div,z_remark,IFNULL(z_m_amount,0) as z_m_amount FROM t_zhangzu WHERE z_date like '2022%' order by z_date desc ";

		RowMapper<Zhangzu> rowMapper = new BeanPropertyRowMapper<Zhangzu>(Zhangzu.class);
        List<Zhangzu> list_zhangzu = jdbcTemplate.query(sql, rowMapper);

		model.addAttribute("list_zhangzu", list_zhangzu);
		return "song_index";
	}

	@RequestMapping(path = "/insert", method = RequestMethod.GET)
	public String insert(Model model) throws Exception {

		Zhangzu zhangzu = new Zhangzu();
		Date today = new Date();
		zhangzu.setZ_date(new SimpleDateFormat("yyyy/MM/dd").format(today));
		model.addAttribute("zhangzu", zhangzu);

		List<String> z_type_list = SongMaster.make_ztype_list();
		
		model.addAttribute("z_type_list", z_type_list);
		
		return "song_insert";
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
		
		return "song_crud_OK";
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
		return "song_crud_OK";
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
		
		List<String> z_type_list = SongMaster.make_ztype_list();
		
		model.addAttribute("z_type_list", z_type_list);

		return "song_update";
	}

}
