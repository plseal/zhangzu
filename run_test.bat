cd C:\GitHub\zhangzu\src\main\resources\
del application.properties
copy application.properties_test application.properties
cd C:\GitHub\zhangzu
chcp 65001
chcp
@REM start chrome.exe "http://127.0.0.1:8080/h2-console"
@REM start chrome.exe "http://127.0.0.1:8080/song/index"
start chrome.exe "http://127.0.0.1:8080/li/index"
start chrome.exe "http://127.0.0.1:8080/li/calendar"
@REM start chrome.exe "http://127.0.0.1:8080/li/api_calendar"



C:\maven\bin\mvn spring-boot:run



