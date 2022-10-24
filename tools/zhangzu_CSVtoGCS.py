import os
from io import BytesIO
from google.cloud import storage

# ↓my_gcp_credentials.jsonはサービスアカウントから発行している
storage_client  = storage.Client.from_service_account_json('c:\\tools\\gcpsecret2.json')

print("storage_client init OK!")


bucket_name = 'zhangzu2022'
from_file_with_path = 'C:\\db_backup\\t_zhangzu.csv'
to_file_name = 't_zhangzu.csv'

bucket = storage_client.get_bucket(bucket_name)
blob = bucket.blob(from_file_with_path)
blob.upload_from_filename(to_file_name)

print("uploaded to 【project:family】 【GCS:uploadtogcs】 success!")