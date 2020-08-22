#初始化组复制
mysql -u root -h 127.0.0.1 -P 3301 -pzang19980226 < ./master.sql
for index in 2 3
do
	mysql -u root -h 127.0.0.1 -P 330$index -pzang19980226 < ./slave.sql
done
#完成初始化，修改组复制为自动开启
for dic in master slave1 slave2
do
	#mac需要额外一个""设置备份格式(空表示不备份)
	sed -i "" "s/loose-group_replication_start_on_boot=off/loose-group_replication_start_on_boot=on/g" ./$dic/conf/conf.d/myconf.cnf
done