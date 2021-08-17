cd C:\GitHub\zhangzu\src\main\resources\
del application.properties
copy application.properties_test application.properties
cd C:\GitHub\zhangzu
"C:\Program Files\Google\Chrome\Application\chrome.exe" "http://127.0.0.1:8080/h2-console"
C:\maven\bin\mvn spring-boot:run

