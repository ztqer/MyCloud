#设置复制用户
SET SQL_LOG_BIN=0;
CREATE USER replication@'%' IDENTIFIED BY 'replication';
GRANT REPLICATION SLAVE ON *.* TO replication@'%';
GRANT BACKUP_ADMIN ON *.* TO replication@'%';
FLUSH PRIVILEGES;
SET SQL_LOG_BIN=1;
#启动组
SET GLOBAL group_replication_bootstrap_group=ON;
START GROUP_REPLICATION USER='replication', PASSWORD='replication';
SET GLOBAL group_replication_bootstrap_group=OFF;