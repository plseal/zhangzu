@echo off
chcp 65001
rem *******************************Code Start*****************************

set TableID=t_zhangzu

title Echo date if format 'yyyy-MM-dd HH:mm:ss'
for /f "tokens=1 delims=/ " %%j in ("%date%") do set d1=%%j
for /f "tokens=2 delims=/ " %%j in ("%date%") do set d2=%%j
for /f "tokens=3 delims=/ " %%j in ("%date%") do set d3=%%j
for /f "tokens=4 delims=/ " %%j in ("%date%") do set d4=%%j
for /f "tokens=1 delims=: " %%j in ("%time%") do set t1=%%j
for /f "tokens=2 delims=: " %%j in ("%time%") do set t2=%%j
for /f "tokens=3 delims=:. " %%j in ("%time%") do set t3=%%j
echo Date Time2: %d1%-%d2%-%d3% %t1%:%t2%:%t3%

set Ymd=%d1%%d2%%d3%
set TableDataYYYYMMDD=%TableID%_%Ymd%.sql
set TableCountYYYYMMDD=%TableID%_cnt_%Ymd%.txt
set TableCountBASE=%TableID%_cnt_BASE.txt

echo TableDataYYYYMMDD :%TableDataYYYYMMDD%
echo TableCountYYYYMMDD :%TableCountYYYYMMDD%
echo TableCountBASE :%TableCountBASE%

SET dbhost=localhost
SET dbuser=root
SET dbpasswd=123456
SET dbName=lingzhu
SET sqlpath=C:\GitHub\zhangzu\tools\
set sqlfile=%TableID%_count.sql



rem *******************************Get Count of Target Table*****************************
C:\mysql\bin\mysql -h%dbhost% -u%dbuser% -p%dbpasswd% %dbName% < %sqlpath%%sqlfile% --default-character-set=utf8 > C:\db_backup\%TableCountYYYYMMDD%

rem *******************************Compare Count*****************************
rem compare C:\DB_BACKUP\t_family_cnt_20191221.txt and C:\DB_BACKUP\T_FAMILY_CNT_BASE.TXT
fc /b C:\db_backup\%TableCountYYYYMMDD% C:\db_backup\%TableCountBASE%

echo %errorlevel%

rem *******************************Compare result no diff do nothing*****************************
if %errorlevel%==0 ( 
	rem do nothing
	echo Do Nothing
rem *******************************Compare result have diff do backup  and update BASE File*****************************
) else (
	C:\mysql\bin\mysqldump --opt -u %dbuser% --password=%dbpasswd% %dbName% %TableID% > C:\db_backup\%TableDataYYYYMMDD%
	rem /Y同名のファイルが存在する場合、上書きの確認を行わない
	copy /Y C:\db_backup\%TableCountYYYYMMDD% C:\db_backup\%TableCountBASE%
	rem start backup to qiniuyun
	set workpath=C:\tools\qiniu\qshell
	C:\tools\qiniu\qshell fput mybackup %TableDataYYYYMMDD% C:\\db_backup\\%TableDataYYYYMMDD%
)

@echo on
rem *******************************Code End*****************************