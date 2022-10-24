import os
from io import BytesIO
from google.cloud import storage

# ↓my_gcp_credentials.jsonはサービスアカウントから発行している
storage_client  = storage.Client.from_service_account_json('c:\\tools\\gcpsecret.json')

print("storage_client init OK!")