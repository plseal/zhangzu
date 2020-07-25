package com.plseal.zhangzu.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DbController {
    private static final Logger logger = LoggerFactory.getLogger(DbController.class);
    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public String index() {
        List<Map<String, Object>> list;
        list = jdbcTemplate.queryForList("select * from face.employee");
        return list.toString();
    }

    @RequestMapping(path = "/users/{id}", method = RequestMethod.GET)
    public String read(@PathVariable final String id) {
        List<Map<String, Object>> list;
        list = jdbcTemplate.queryForList("select * from face.employee where employee_id = ?", id);
        return list.toString();
    }

    @RequestMapping(path = "/get_one_zhangzu", method = RequestMethod.GET)
    public Map<String, Object> get_one_zhangzu(@RequestParam("flg") final String flg, @RequestParam("z_date") final String z_date,
            @RequestParam("z_name") final String z_name) {

        List<Map<String, Object>> list;

        list = jdbcTemplate.queryForList(
                "SELECT * FROM t_zhangzu WHERE z_date like CONCAT('%',?,'%') and z_name like CONCAT('%',?,'%') order by z_date desc ",
                z_date, z_name);
        // Map<String, String> resultJson = Collections.singletonMap("result", "OK");
        logger.info("list.size():"+list.size());
        logger.info("list.get(0):"+list.get(0));
        Map<String, Object> oneMap = (Map<String, Object>)list.get(0);
        // logger.info("oneMap.get(z_date):"+oneMap.get("z_date"));
        // if(list.size() > 0) {
            
        //     resultJson.put("z_date",oneMap.get("z_date").toString());
        // } else {
        //     resultJson = Collections.singletonMap("result", "NG");
        // }
        // logger.info("resultJson:"+resultJson.toString());
        return oneMap;
        
        

        //return list.toString();

   }
}