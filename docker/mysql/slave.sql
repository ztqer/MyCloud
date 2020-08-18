#设置复制用户
SET SQL_LOG_BIN=0;
CREATE USER replication@'%' IDENTIFIED BY 'replication';
GRANT REPLICATION SLAVE ON *.* TO replication@'%';
GRANT BACKUP_ADMIN ON *.* TO replication@'%';
FLUSH PRIVILEGES;
SET SQL_LOG_BIN=1;
#启动复制
CHANGE MASTER TO MASTER_USER='replication', MASTER_PASSWORD='replication' FOR CHANNEL 'group_replication_recovery';
RESET MASTER; #未知原因会报错数据不一致无法加入集群，故在此重置
START GROUP_REPLICATION USER='replication', PASSWORD='replication';