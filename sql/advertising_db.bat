@echo off
chcp 65001
mysql --host=localhost --user=ads_admin --password=admin < advertising.sql
pause