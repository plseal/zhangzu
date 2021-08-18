DROP TABLE IF EXISTS DEPT;

CREATE TABLE DEPT (
    ID IDENTITY NOT NULL PRIMARY KEY,
    NAME VARCHAR(255) NOT NULL
);

DROP TABLE IF EXISTS t_zhangzu;

CREATE TABLE t_zhangzu (
    id IDENTITY NOT NULL PRIMARY KEY,
    z_date VARCHAR(255),
    z_name VARCHAR(255),
    z_amount DOUBLE,
    z_type VARCHAR(255),
    z_io_div VARCHAR(255),
    z_remark VARCHAR(255),
    z_m_amount DOUBLE
);