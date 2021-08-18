cd C:\GitHub\zhangzu\src\main\resources\
del application.properties
copy application.properties_prod application.properties
cd C:\GitHub\zhangzu
mvn spring-boot:run