import os
from io import BytesIO
from google.cloud import storage
import pandas as pd

# ------------------------------
# change tsv file to csv file
# ------------------------------
df_tsv_sep = pd.read_csv('C:\\db_backup\\t_zhangzu.tsv', index_col=0, sep='\t')
print(df_tsv_sep.head())

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