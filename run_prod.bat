cd C:\GitHub\zhangzu\src\main\resources\
del application.properties
copy application.properties_prod application.properties
cd C:\GitHub\zhangzu
git fetch
git pull
chcp 65001
chcp
mvn spring-boot:run