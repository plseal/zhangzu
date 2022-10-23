## t_zhangzu
```SQL
CREATE TABLE `t_zhangzu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `z_date` varchar(20) DEFAULT NULL,
  `z_name` varchar(200) DEFAULT NULL,
  `z_amount` decimal(50,0) DEFAULT NULL,
  `z_type` varchar(200) DEFAULT NULL,
  `z_io_div` varchar(50) DEFAULT NULL,
  `z_remark` varchar(100) DEFAULT NULL,
  `z_m_amount` decimal(50,0) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15414 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT
```
## li_zhangzu
```SQL
CREATE TABLE `li_zhangzu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `z_date` varchar(20) DEFAULT NULL,
  `z_name` varchar(200) DEFAULT NULL,
  `z_amount` decimal(50,0) DEFAULT NULL,
  `z_type` varchar(200) DEFAULT NULL,
  `z_io_div` varchar(50) DEFAULT NULL,
  `z_remark` varchar(100) DEFAULT NULL,
  `z_m_amount` decimal(50,0) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15414 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT
```
## v_song_zhangzu_zhichu_2022
```
CREATE 
VIEW `lingzhu`.`v_song_zhangzu_zhichu_2022` AS
    SELECT 
        SUBSTR(`lingzhu`.`t_zhangzu`.`z_date`,
            1,
            7) AS `ac`,
        SUM(`lingzhu`.`t_zhangzu`.`z_amount`) AS `amount`
    FROM
        `lingzhu`.`t_zhangzu`
    WHERE
        ((`lingzhu`.`t_zhangzu`.`z_date` LIKE '2022%')
            AND (`lingzhu`.`t_zhangzu`.`z_io_div` = '支出'))
    GROUP BY SUBSTR(lingzhu.t_zhangzu.z_date, 1, 7)
```
## v_song_zhangzu_shouru_2022
```
CREATE 
VIEW `lingzhu`.`v_song_zhangzu_shouru_2022` AS
    SELECT 
        SUBSTR(`lingzhu`.`t_zhangzu`.`z_date`,
            1,
            7) AS `ac`,
        SUM(`lingzhu`.`t_zhangzu`.`z_amount`) AS `amount`
    FROM
        `lingzhu`.`t_zhangzu`
    WHERE
        ((`lingzhu`.`t_zhangzu`.`z_date` LIKE '2022%')
            AND (`lingzhu`.`t_zhangzu`.`z_io_div` = '收入'))
    GROUP BY SUBSTR(lingzhu.t_zhangzu.z_date, 1, 7)
```

## v_song_zhangzu_maihuo_2022
```
CREATE 
VIEW `lingzhu`.`v_song_zhangzu_maihuo_2022` AS
    SELECT 
        SUBSTR(`lingzhu`.`t_zhangzu`.`z_date`,
            1,
            7) AS `ac`,
        SUM(`lingzhu`.`t_zhangzu`.`z_amount`) AS `amount`
    FROM
        `lingzhu`.`t_zhangzu`
    WHERE
        ((`lingzhu`.`t_zhangzu`.`z_date` LIKE '2022%')
            AND (`lingzhu`.`t_zhangzu`.`z_io_div` = '买货'))
    GROUP BY SUBSTR(lingzhu.t_zhangzu.z_date, 1, 7)
```