cd C:\GitHub\zhangzu\zhangzu\src\main\resources\
del application.properties
copy application.properties_prod application.properties
cd C:\GitHub\zhangzu\zhangzu
C:\maven\bin\mvn spring-boot:run