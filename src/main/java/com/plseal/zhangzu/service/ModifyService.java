package com.plseal.zhangzu.service;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.RowMapper;

import com.plseal.zhangzu.entity.HeZhangzu;
import com.plseal.zhangzu.entity.Zhangzu;

@Service  // サービスクラスに付与。
public class ModifyService {
    private static final Logger logger = LoggerFactory.getLogger(SongAnalysisService.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    /*
     * index画面表示用データを取得
     */
	public List<Zhangzu> query_db_index(String table_id, String year) throws Exception {
        logger.info("query_db_index [start]");

		String sql = "SELECT id,z_date,z_name,IFNULL(z_amount,0) as z_amount,z_type,z_io_div,z_remark,IFNULL(z_m_amount,0) as z_m_amount FROM " + table_id +" WHERE z_date like '" + year + "%' order by z_date desc ";
        logger.info("query_db_index [sql]"+sql);
		RowMapper<Zhangzu> rowMapper = new BeanPropertyRowMapper<Zhangzu>(Zhangzu.class);
        List<Zhangzu> list_zhangzu = jdbcTemplate.query(sql, rowMapper);
        logger.info("query_db_index [end]");
		return list_zhangzu;
	}

    /*
     * update画面表示用データを取得
     */
	public List<Zhangzu>  query_db_for_update_html(String table_id, Integer id) throws Exception {
        logger.info("query_db_for_update_html [start]");
		String sql = "SELECT id,z_date,z_name,IFNULL(z_amount,0) as z_amount ,z_type,z_io_div,z_remark,IFNULL(z_m_amount,0) as z_m_amount FROM " + table_id +" WHERE id = " + id + " order by z_date desc ";
        logger.info("query_db_for_update_html [sql]"+sql);
        RowMapper<Zhangzu> rowMapper = new BeanPropertyRowMapper<Zhangzu>(Zhangzu.class);
        List<Zhangzu> list_zhangzu  = jdbcTemplate.query(sql, rowMapper);
        logger.info("query_db_for_update_html [end]");
		return list_zhangzu;
	}
    /*
     * 賀用index画面表示用データを取得
     */
	public List<HeZhangzu> query_db_he_index(String table_id, String year) throws Exception {
        logger.info("query_db_he_index [start]");

		String sql = "SELECT id,z_date,z_name,IFNULL(z_amount,0) as z_amount,z_type,z_io_div,z_remark,IFNULL(z_m_amount,0) as z_m_amount,z_photo_name FROM " + table_id +" WHERE z_date like '" + year + "%' order by z_date desc ";
        logger.info("query_db_he_index [sql]"+sql);
		RowMapper<HeZhangzu> rowMapper = new BeanPropertyRowMapper<HeZhangzu>(HeZhangzu.class);
        List<HeZhangzu> list_zhangzu = jdbcTemplate.query(sql, rowMapper);
        logger.info("query_db_he_index [end]");
		return list_zhangzu;
	}
    /*
     * 賀用update画面表示用データを取得
     */
	public List<HeZhangzu>  query_db_for_he_update_html(String table_id, Integer id) throws Exception {
        logger.info("query_db_for_he_update_html [start]");
		String sql = "SELECT id,z_date,z_name,IFNULL(z_amount,0) as z_amount ,z_type,z_io_div,z_remark,IFNULL(z_m_amount,0) as z_m_amount,z_photo_name FROM " + table_id +" WHERE id = " + id + " order by z_date desc ";
        logger.info("query_db_for_he_update_html [sql]"+sql);
        RowMapper<HeZhangzu> rowMapper = new BeanPropertyRowMapper<HeZhangzu>(HeZhangzu.class);
        List<HeZhangzu> list_zhangzu  = jdbcTemplate.query(sql, rowMapper);
        logger.info("query_db_for_he_update_html [end]");
		return list_zhangzu;
	}
}
