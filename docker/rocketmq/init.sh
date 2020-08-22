#根据ip.env为各broker的配置文件添加brokerIP1
source ./env/ip.env
for name in m1 m2 s1 s2
do
	echo -e "\n""brokerIP1=$REAL_IP" >> "./broker-$name/conf/broker.conf"
done