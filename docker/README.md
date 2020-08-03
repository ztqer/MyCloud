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
volumes统一挂载在 /mydata/project-name/some-path 下
### nacos
该实例为一个3节点的nacos-server集群，通过mysql外挂数据源，并使用nginx做负载均衡
#### 使用方法
将项目中docker/nacos文件夹内容复制到机器/mydata/nacos中，并部署：
```
	cd /mydata/nacos
	docker-compose -f cluster-hostname.yml up
```  
可通过  宿主机ip:8080/nacos-cluster/nacos/  访问控制台
