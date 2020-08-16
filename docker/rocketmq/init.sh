#根据ip.env为node1-node6的配置文件添加cluster-announce-ip
source ./env/ip.env
for name in m1 m2 s1 s2
do
	echo -e "\n""brokerIP1=$REAL_IP" >> "./broker-$name/conf/broker.conf"
done