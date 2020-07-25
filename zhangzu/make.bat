cd C:\GitHub\zhangzu\zhangzu
path
rem mvn package -Dskiptests
mvn package -Dmaven.test.skip=true
rem mvn install -DskipTests=true