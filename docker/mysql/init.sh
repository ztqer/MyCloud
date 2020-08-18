#初始化组复制
mysql -u root -h 127.0.0.1 -P 3301 -pzang19980226 < ./master.sql
for index in 2 3
do
	mysql -u root -h 127.0.0.1 -P 330$index -pzang19980226 < ./slave.sql
done