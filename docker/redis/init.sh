#根据ip.env为node1-node6的配置文件添加cluster-announce-ip
source ./env/ip.env
for index in 1 2 3 4 5 6
do
	echo -e "\n""cluster-announce-ip $REAL_IP" >> "./node$index/conf/redis.conf"
done