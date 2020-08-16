# Docker
docker打包，分布式快速部署
## 前期准备  
1. 创建一个docker swarm集群
```
	docker swarm init --advertise-addr leader_ip
	docker swarm join-token manager/worker 获取加入集群指令
```
2. 创建一个overlay network(修改该网络配置，需要检查各组件docker-compose.yml中属性一致)
```
	docker network create -d overlay --subnet 192.168.0.0/24 --gateway 192.168.0.1 myoverlay
```
3. 在leader节点上通过docker-compose部署  
注意：1.版本号;2.在yml文件中根据需求添加deploy和network配置。

## 重要组件
volumes统一挂载在 /my-data-path/project-name/some-path 下
### nacos
该实例为一个3节点的nacos-server集群，通过mysql外挂数据源，并使用nginx做负载均衡
#### 使用方法
将项目中docker/nacos文件夹内容复制到机器/mydata/nacos中，并部署：
```
	cd /mydata/nacos
	docker-compose -f cluster-hostname.yml up
```  
1. 通过网址  宿主机ip:8888/nacos-cluster/nacos/  访问控制台
2. 微服务配置以下属性接入nacos
``` 
	spring.cloud.nacos.config.server-addr=宿主机ip:8888/nacos-cluster/
	spring.cloud.nacos.discovery.server-addr=宿主机ip:8888/nacos-cluster/
```
### srs
srs流媒体服务器
#### 使用方法
首先从git拉取源文件
```
	cd /mydata
	git clone https://gitee.com/winlinvip/srs.oschina.git srs &&
	cd srs/trunk && git remote set-url origin https://github.com/ossrs/srs.git && git pull
```
默认为3.0版本，如rtc等功能需切换4.0
```
	git checkout 4.0release
	git branch -v
```
docker部署
```
	docker run -it --privileged --name=srs -v `pwd`:/tmp/srs/trunk -w /tmp/srs/trunk -p 1935:1935 \
	-p 1985:1985 -p 8080:8080 -p 8085:8085 -p 8000:8000/udp \
	registry.cn-hangzhou.aliyuncs.com/ossrs/srs:dev bash
```
进入容器后
```
	#第一次使用需要编译
	./configure && make
	#选择配置文件开启服务器
	./objs/srs -c conf/XXX.conf
```
控制台地址:[http://r.ossrs.net:1985/console](http://r.ossrs.net:1985/console)
### sentinel-dashboard
sentinel控制台
#### 使用方法
进入项目中docker/sentinel-dashboard文件夹
```
	docker-compose -f sentinel-dashboard.yml up
```
1. 通过网址 宿主机ip:9999 访问控制台
2. 微服务配置以下属性接入sentinel控制台
``` 
	spring.cloud.sentinel.transport.dashboard=宿主机ip:9999
```
### rocketmq
该实例包含一个nameserver,2主2从的broker集群，以及控制台
#### 使用方法
将项目中docker/rocketmq文件夹内容复制到机器/mydata/rocketmq中  
然后修改各个broker的配置，例：docker/rocketmq/broker-m1/conf/broker.conf，修改brokerIP1值 
```
	问题:
	1.docker-compose会为各容器提供网桥连接，故broker上报的ip为内网，生产者消费者从nameserver取得的路由地址不可达
	2.broker上报端口为自身端口，当docker对端口做了映射后，就算解决了问题1，nameserver或客户端会在宿主机ip的错误端口连接，仍不可达
	解决办法:
	手动为broker配置brokerIP1为宿主机ip,listenPort自定义端口并且在docker部署时与宿主机相同端口作映射
```
部署：
```
	cd /mydata/rocketmq
	docker-compose -f rocketmq-cluster.yml up
```
1. 通过网站 宿主机ip:11000/ 访问控制台
2. 微服务配置以下属性接入rocketmq
```
	spring.cloud.stream.rocketmq.binder.name-server=宿主机ip:9876
	··· 其他配置 ···
```
### seata
seata服务器，即TC(Transaction Coordinator)部分，TM(Transaction Manager)和RM(Resource Manager)集成在业务系统中
####使用方法
进入项目中docker/seata文件夹
```
	docker-compose -f seata.yml up
```
### mysql
mysql 用户名:root 密码:zang19980226
#### 使用方法
将项目中docker/mysql文件夹内容复制到机器/mydata/mysql中，并部署：
```
	cd /mydata/mysql
	docker-compose -f mysql.yml up
``` 
### redis
redis集群，采用cluster模式，3主3从
#### 使用方法
将项目中docker/redis文件夹内容复制到机器/mydata/redis中
```
	问题:
	1.docker中运行的redis各节点通信通过announce ip/port/bus-port(各容器需要可达)
	2.外界客户端访问key，如果被映射到其他节点会根据announce ip/port/bus-port转发请求(外界需要可达)
	解决办法:
	1.compose的yml文件中映射port和port+10000(未找不到配置busport的地方，故按默认port+10000)
	2.redis.conf中配置相应ip和端口为容器在宿主机上的映射
```
部署：
```
	cd /mydata/redis
	docker-compose -f redis-cluster.yml up
``` 
创建集群
```
	docker exec -it redis1 bash
	redis-cli --cluster create 宿主机ip:6301 宿主机ip:6302 宿主机ip:6303 宿主机ip:6304 宿主机ip:6305 
	宿主机ip:6306 --cluster-replicas 1
```
客户端访问
```
	redis-cli -c -h 宿主机ip -p 任意节点端口
```
