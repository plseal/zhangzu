SET dbhost=localhost
SET dbuser=root
SET dbpasswd=123456
SET dbName=lingzhu
SET sqlpath=C:\GitHub\zhangzu\tools\
set sqlfile=zhangzu_toCSV.sql

rem *******************************Get CSV from Target Table*****************************
rem C:\mysql\bin\mysql -h%dbhost% -u%dbuser% -p%dbpasswd% %dbName% < %sqlpath%%sqlfile% > C:\db_backup\t_zhangzu.tsv
rem C:\mysql\bin\mysqldump -h%dbhost% -u%dbuser% -p%dbpasswd% --tab=C:\db_backup --fields-terminated-by=, %dbName%  t_zhangzu

rem dir c:\db_backup\t_zhangzu.csv