DROP TABLE IF EXISTS t_zhangzu;

CREATE TABLE t_zhangzu (
    id IDENTITY NOT NULL PRIMARY KEY,
    z_date VARCHAR(255),
    z_name VARCHAR(255),
    z_amount BIGINT,
    z_type VARCHAR(255),
    z_io_div VARCHAR(255),
    z_remark VARCHAR(255),
    z_m_amount BIGINT
);

DROP TABLE IF EXISTS li_zhangzu;

CREATE TABLE li_zhangzu (
    id IDENTITY NOT NULL PRIMARY KEY,
    z_date VARCHAR(255),
    z_name VARCHAR(255),
    z_amount BIGINT,
    z_type VARCHAR(255),
    z_io_div VARCHAR(255),
    z_remark VARCHAR(255),
    z_m_amount BIGINT
);

CREATE VIEW v_li_zhangzu_zhichu_2022 AS
    SELECT 
        SUBSTR(z_date,
            1,
            7) AS `ac`,
        SUM(z_amount) AS `amount`
    FROM
        li_zhangzu
    WHERE
        ((z_date LIKE '2022%')
            AND (z_io_div = '支出'))
    GROUP BY SUBSTR(z_date, 1, 7);

CREATE VIEW v_li_zhangzu_shouru_2022 AS
    SELECT 
        SUBSTR(z_date,
            1,
            7) AS `ac`,
        SUM(z_amount) AS `amount`
    FROM
        li_zhangzu
    WHERE
        ((z_date LIKE '2022%')
            AND (z_io_div = '收入'))
    GROUP BY SUBSTR(z_date, 1, 7);

CREATE VIEW v_li_zhangzu_maihuo_2022 AS
    SELECT 
        SUBSTR(z_date,
            1,
            7) AS `ac`,
        SUM(z_amount) AS `amount`
    FROM
        li_zhangzu
    WHERE
        ((z_date LIKE '2022%')
            AND (z_io_div = '买货'))
    GROUP BY SUBSTR(z_date, 1, 7);

