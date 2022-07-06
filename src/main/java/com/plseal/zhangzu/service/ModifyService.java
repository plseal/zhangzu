package com.plseal.zhangzu.service;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.RowMapper;
import com.plseal.zhangzu.entity.Zhangzu;
import java.util.Calendar;
import java.text.SimpleDateFormat;

@Service  // サービスクラスに付与。
public class ModifyService {
    private static final Logger logger = LoggerFactory.getLogger(SongAnalysisService.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    /*
     * index画面表示用データを取得
     */
	public List<Zhangzu> query_db_index(String table_id) throws Exception {
        logger.info("query_db_index [start]");
		//现在系统时间的年
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String calendar_Y =sdf.format(calendar.getTime());
		String sql = "SELECT id,z_date,z_name,z_amount,z_type,z_io_div,z_remark,IFNULL(z_m_amount,0) as z_m_amount FROM " + table_id +" WHERE z_date like '" + calendar_Y + "%' order by z_date desc ";
        logger.info("query_db_index [sql]"+sql);
		RowMapper<Zhangzu> rowMapper = new BeanPropertyRowMapper<Zhangzu>(Zhangzu.class);
        List<Zhangzu> list_zhangzu = jdbcTemplate.query(sql, rowMapper);
        logger.info("query_db_index [end]");
		return list_zhangzu;
	}
}
