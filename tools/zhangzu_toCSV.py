# ------------------------------
# download data from mysql
# ------------------------------
import pandas as pd
import pymysql.cursors

# データベースに接続
connection = pymysql.connect(host='localhost',
                             user='root',
                             password='123456',
                             database='lingzhu',
                             cursorclass=pymysql.cursors.DictCursor,charset="utf8")

# データ読み込み
sql = "SELECT * FROM t_zhangzu where z_date like '2022%' or z_date like '2021%' order by z_date"

df = pd.read_sql(sql, connection)

df.to_csv(path_or_buf='C:\\db_backup\\t_zhangzu.csv',encoding='utf-8',index=False)