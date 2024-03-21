cd C:\GitHub\zhangzu\src\main\resources\
del application.properties
copy application.properties_test application.properties
cd C:\GitHub\zhangzu
chcp 65001
chcp
@REM start chrome.exe "http://127.0.0.1:8080/h2-console"
@REM start chrome.exe "http://127.0.0.1:8080/song/index"
@REM start chrome.exe "http://127.0.0.1:8080/upload_index"
@REM start chrome.exe "http://127.0.0.1:8080/li/index"
@REM start chrome.exe "http://127.0.0.1:8080/li/calendar"
@REM start chrome.exe "http://127.0.0.1:8080/li/api_calendar"
start chrome.exe "http://127.0.0.1:8080/he/index"


C:\maven\bin\mvn spring-boot:run



