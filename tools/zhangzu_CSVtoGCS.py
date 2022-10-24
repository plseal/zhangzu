import os
from io import BytesIO
from google.cloud import storage
import pandas as pd
import json

# ------------------------------
# download data from mysql
# ------------------------------
import pymysql.cursors

# データベースに接続
connection = pymysql.connect(host='localhost',
                             user='root',
                             password='123456',
                             database='lingzhu',
                             cursorclass=pymysql.cursors.DictCursor,charset="utf8")

# データ読み込み
sql = "SELECT * FROM t_zhangzu where z_date like '2022%' order by z_date"

df = pd.read_sql(sql, connection)

df.to_csv(path_or_buf='C:\\db_backup\\t_zhangzu.csv',encoding='utf-8',index=False)
   
   
   

# ------------------------------
# change tsv file to csv file
# ------------------------------
# df_tsv_sep = pd.read_csv('C:\\db_backup\\t_zhangzu.tsv', sep='\t')
# print(df_tsv_sep.head())
# df_tsv_sep.to_csv('C:\\db_backup\\t_zhangzu.csv', index=False)

# ------------------------------
# upload to GCP
# ------------------------------
# ↓my_gcp_credentials.jsonはサービスアカウントから発行している
storage_client  = storage.Client.from_service_account_json('c:\\tools\\gcpsecret2.json')

print("storage_client init OK!")


bucket_name = 'zhangzu2022'
from_file_with_path = 'C:\\db_backup\\t_zhangzu.csv'
to_file_name = 't_zhangzu.csv'

bucket = storage_client.get_bucket(bucket_name)
blob = bucket.blob(to_file_name)
blob.upload_from_filename(from_file_with_path)

print("uploaded to 【project:family】 【GCS:uploadtogcs】 success!")