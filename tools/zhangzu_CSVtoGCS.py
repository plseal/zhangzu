import os
from io import BytesIO
from google.cloud import storage

# ↓my_gcp_credentials.jsonはサービスアカウントから発行している
storage_client  = storage.Client.from_service_account_json('c:\\tools\\gcpsecret.json')

print("storage_client init OK!")


bucket_name = 'uploadtogcs'
file_name = 't_zhangzu.csv' #送るものの名前

bucket = storage_client.get_bucket(bucket_name)
blob = bucket.blob(file_name)
blob.upload_from_filename(file_name)

print("uploaded to 【project:family】 【GCS:uploadtogcs】 success!")