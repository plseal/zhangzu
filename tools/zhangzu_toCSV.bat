SET dbhost=localhost
SET dbuser=root
SET dbpasswd=123456
SET dbName=lingzhu
SET sqlpath=C:\GitHub\zhangzu\
set sqlfile=t_zhangzu_toCSV.sql



rem *******************************Get CSV from Target Table*****************************
C:\mysql\bin\mysql -h%dbhost% -u%dbuser% -p%dbpasswd% %dbName% < %sqlpath%%sqlfile% --default-character-set=utf8 > C:\db_backup\t_zhangzu.csv