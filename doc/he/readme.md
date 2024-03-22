```mermaid
graph LR
A[首页] --> B[新增页面]
B -->|写真添付按钮<br>/he/insert_post<br>UPLOAD_FILE| C[文件上传首页面]
C --> E[文件上传处理页面]
E-->|处理文件上传| F[文件上传处理成功页面]
F-->|显示上传成功| B
B -->|查看已有数据列表| D[列表页面]
```
