cd C:\work\GitHub\zhangzu\zhangzu\src\main\resources\
del application.properties
copy application.properties_test application.properties
cd C:\work\GitHub\zhangzu\zhangzu
echo "http://localhost:8080/h2-console"
C:\maven\bin\mvn spring-boot:run
