package com.plseal.zhangzu.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import org.springframework.web.multipart.MultipartFile;

import com.plseal.zhangzu.common.SongMaster;
import com.plseal.zhangzu.entity.HeZhangzu;
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
    
	private static final String UPLOAD_DIR = "C:\\Github\\zhangzu\\src\\main\\resources\\static\\uploads";

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
		
		String forward_html = "he_crud_OK";
		if("UPDATE".equals(update_delete_button)) {
			String sql_update = "update "+target_table+" set z_date = ?,z_name = ?,z_amount = ?,z_type = ?,z_io_div = ?,z_remark = ?,z_m_amount = ? where id = ?";
			jdbcTemplate.update(sql_update,z_date,z_name,z_amount,z_type,z_io_div,z_remark,z_m_amount,id);
		} else if("DELETE".equals(update_delete_button)) {
			String sql_delete = "delete from "+target_table+" where id = ?";
			jdbcTemplate.update(sql_delete,id);
		} else if("UPLOAD_FILE".equals(update_delete_button)) {
			model.addAttribute("id", id);
			forward_html = "he_upload_index";
		} else {
			logger.error("ERROR!!! update_delete_button:" + update_delete_button);
		}
		return forward_html;
	}
    // @GetMapping("/he_upload_index")
    // public String uploadIndex(Model model) {
    //     logger.info("uploadIndex start");
        
    //     return "upload_index";
    // }

    @PostMapping("/upload_file_post")
    public String uploadFilePost(Model model,@RequestParam("file") MultipartFile file,@RequestParam("id") String id) {
        logger.info("uploadFilePost [start]");
		logger.info("uploadFilePost id:"+id);
        try {
            // 获取文件名
            String fileName = file.getOriginalFilename();
            logger.info("uploadFilePost fileName:" + fileName);
			String extension = extractExtension(fileName);
			// 获取当前日期时间
			LocalDateTime now = LocalDateTime.now();

			// 定义格式化模式
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

			// 格式化日期时间
			String formattedDateTime = now.format(formatter);
			String NewFileName = formattedDateTime + "." + extension;
			logger.info("uploadFilePost NewfileName:" + NewFileName);

            // 构建文件存储路径
            String filePath = UPLOAD_DIR + File.separator + NewFileName;
            // 将文件保存到指定路径
            file.transferTo(new File(filePath));
			logger.info("uploadFilePost [ファイルをdiskに保存成功]");
			// 文件名をDBに保存
			String sql_update = "update "+target_table+" set z_photo_name = ? where id = ?";
			jdbcTemplate.update(sql_update,NewFileName,id);
			logger.info("uploadFilePost [ファイル名をDBに保存成功]");
			model.addAttribute("id", id);
			model.addAttribute("newfilename", NewFileName);
			logger.info("uploadFilePost [end]");
			logger.info("upload_result.htmlに遷移");
            return "he_upload_result";
        } catch (IOException e) {
            e.printStackTrace();
            return "文件上传失败: " + e.getMessage();
        }
    }



	@PostMapping(path = "/update")
	public String update(Model model, @RequestParam("id")Integer id) throws Exception {

		List<HeZhangzu> list_zhangzu = modifyService.query_db_for_he_update_html(target_table,id);
        logger.info("list.size():"+list_zhangzu.size());
        logger.info("list.get(0):"+list_zhangzu.get(0));
		model.addAttribute("zhangzu", list_zhangzu.get(0));
		
		List<String> z_type_list = SongMaster.make_ztype_list();
		List<String> z_io_div_list = SongMaster.make_z_io_div_list();

		model.addAttribute("z_type_list", z_type_list);
		model.addAttribute("z_io_div_list", z_io_div_list);
		model.addAttribute("z_photo_name", list_zhangzu.get(0).getZ_photo_name());

		return "he_update";
	}

	public static String extractExtension(String fileName) {
		int dotIndex = fileName.lastIndexOf(".");
		if (dotIndex == -1) {
			// 如果文件名中没有扩展名，返回空字符串
			return "";
		} else {
			// 截取扩展名部分
			return fileName.substring(dotIndex + 1);
		}
	}
}
