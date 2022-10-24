SHOW VARIABLES LIKE "secure_file_priv";

SELECT *
FROM t_zhangzu 
WHERE z_date LIKE '2022%'
ORDER BY z_date
INTO OUTFILE 'c:\\db_backup\\t_zhangzu.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n';
