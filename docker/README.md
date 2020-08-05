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
可通过  宿主机ip:8888/nacos-cluster/nacos/  访问控制台
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
	-p 1985:1985 -p 8080:8080 -p 8085:8085 -p 8000:8000/udp --env CANDIDATE=XXX.XXX.XXX.XXX(宿主机IP) \
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
### mysql
mysql 用户名:root 密码:zang19980226
#### 使用方法
将项目中docker/mysql文件夹内容复制到机器/mydata/mysql中，并部署：
```
	cd /mydata/mysql
	docker-compose -f mysql.yml up
``` 
### redis
redis
#### 使用方法
将项目中docker/redis文件夹内容复制到机器/mydata/redis中，并部署：
```
	cd /mydata/redis
	docker-compose -f redis.yml up
``` 

