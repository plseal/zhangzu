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

DROP TABLE IF EXISTS he_zhangzu;

CREATE TABLE he_zhangzu (
    id IDENTITY NOT NULL PRIMARY KEY,
    z_date VARCHAR(255),
    z_name VARCHAR(255),
    z_amount BIGINT,
    z_type VARCHAR(255),
    z_io_div VARCHAR(255),
    z_photo_name VARCHAR(255),
    z_remark VARCHAR(255),
    z_m_amount BIGINT
);

DROP TABLE IF EXISTS li_calendar;

CREATE TABLE li_calendar (
    id IDENTITY NOT NULL PRIMARY KEY,
    c_date VARCHAR(255),
    c_time_from VARCHAR(255),
    c_time_to VARCHAR(255),
    c_name VARCHAR(255),
    c_div VARCHAR(255),
    c_reserve VARCHAR(255),
    c_remark VARCHAR(255)
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


CREATE VIEW v_li_zhangzu_zhichu_2023 AS
    SELECT 
        SUBSTR(z_date,
            1,
            7) AS `ac`,
        SUM(z_amount) AS `amount`
    FROM
        li_zhangzu
    WHERE
        ((z_date LIKE '2023%')
            AND (z_io_div = '支出'))
    GROUP BY SUBSTR(z_date, 1, 7);

CREATE VIEW v_li_zhangzu_shouru_2023 AS
    SELECT 
        SUBSTR(z_date,
            1,
            7) AS `ac`,
        SUM(z_amount) AS `amount`
    FROM
        li_zhangzu
    WHERE
        ((z_date LIKE '2023%')
            AND (z_io_div = '收入'))
    GROUP BY SUBSTR(z_date, 1, 7);

CREATE VIEW v_li_zhangzu_maihuo_2023 AS
    SELECT 
        SUBSTR(z_date,
            1,
            7) AS `ac`,
        SUM(z_amount) AS `amount`
    FROM
        li_zhangzu
    WHERE
        ((z_date LIKE '2023%')
            AND (z_io_div = '买货'))
    GROUP BY SUBSTR(z_date, 1, 7);
    
CREATE VIEW v_li_zhangzu_zhichu_2024 AS
    SELECT 
        SUBSTR(z_date,
            1,
            7) AS `ac`,
        SUM(z_amount) AS `amount`
    FROM
        li_zhangzu
    WHERE
        ((z_date LIKE '2024%')
            AND (z_io_div = '支出'))
    GROUP BY SUBSTR(z_date, 1, 7);

CREATE VIEW v_li_zhangzu_shouru_2024 AS
    SELECT 
        SUBSTR(z_date,
            1,
            7) AS `ac`,
        SUM(z_amount) AS `amount`
    FROM
        li_zhangzu
    WHERE
        ((z_date LIKE '2024%')
            AND (z_io_div = '收入'))
    GROUP BY SUBSTR(z_date, 1, 7);

CREATE VIEW v_li_zhangzu_maihuo_2024 AS
    SELECT 
        SUBSTR(z_date,
            1,
            7) AS `ac`,
        SUM(z_amount) AS `amount`
    FROM
        li_zhangzu
    WHERE
        ((z_date LIKE '2024%')
            AND (z_io_div = '买货'))
    GROUP BY SUBSTR(z_date, 1, 7);

CREATE VIEW v_song_zhangzu_zhichu_2022 AS
    SELECT 
        SUBSTR(z_date,
            1,
            7) AS `ac`,
        SUM(z_amount) AS `amount`
    FROM
        t_zhangzu
    WHERE
        ((z_date LIKE '2022%')
            AND (z_io_div = '支出'))
    GROUP BY SUBSTR(z_date, 1, 7);

CREATE VIEW v_song_zhangzu_shouru_2022 AS
    SELECT 
        SUBSTR(z_date,
            1,
            7) AS `ac`,
        SUM(z_amount) AS `amount`
    FROM
        t_zhangzu
    WHERE
        ((z_date LIKE '2022%')
            AND (z_io_div = '收入'))
    GROUP BY SUBSTR(z_date, 1, 7);

CREATE VIEW v_song_zhangzu_maihuo_2022 AS
    SELECT 
        SUBSTR(z_date,
            1,
            7) AS `ac`,
        SUM(z_amount) AS `amount`
    FROM
        t_zhangzu
    WHERE
        ((z_date LIKE '2022%')
            AND (z_io_div = '买货'))
    GROUP BY SUBSTR(z_date, 1, 7);


