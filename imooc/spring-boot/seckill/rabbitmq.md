# 安装

## mac

```
brew install rabbitmq
会先去安装erlang，rabbitmq依赖erlang

cd /usr/local/Cellar/rabbitmq/3.7.8/sbin
// 启动
./rabbitmq-server 

// 产生日志 /usr/local/var/log/rabbitmq/rabbit@localhost.log

tail -f  /usr/local/var/log/rabbitmq/rabbit@localhost.log

// 关闭
./rabbitmqctl stop
```

### 配置环境

```
vi ~/.bash_profile
export PATH=$PATH:/usr/local/Cellar/rabbitmq/3.7.8/sbin
```

### 允许rabbitmq远程连接

```
cd /usr/local/Cellar/rabbitmq/3.7.8/etc
mkdir rabbitmq
vi rabbitmq.config

[  
{rabbit, [{tcp_listeners, [5672]}, {loopback_users, ["test_user"]}]}  
].
```

## linux

```
wget http://erlang.org/download/otp_src_21.1.tar.gz
wget http://www.rabbitmq.com/releases/rabbitmq-server/v3.6.2/rabbitmq-server-generic-unix-3.6.2.tar.xz
yum install ncurses-devel
```

### 安装erlang

```
tar xf otp_src_21.1.tar.gz
cd otp_src_21.1
// 安装到usr/local/erlang21这个目录来
./configure --prefix=/usr/local/erlang21 --without-javac
make 
make install

cd /usr/local/erlang21/bin
./erl 
进行shell说明安装成功
```

### 配置erlang环境

```
vi ~/.bash_profile
source ~/.bash_profile
PATH=$PATH:/usr/local/erlang21/bin
```

### 安装rabbitmq

```
cd /root/software
xz -d rabbitmq-server-generic-unix-3.6.2.tar.xz
// 解压出来是一个tar，再解压
tar xf rabbitmq-server-generic-unix-3.6.2.tar
// 安装几个依赖
yum install python -y
yum install xmlto -y
yum install python-simplejson -y
mv rabbitmq_server-3.6.2  /usr/local/rabbitmq
cd /usr/local/rabbitmq/sbin
// 启动
./rabbitmq-server 

// 已经有log了
cat /usr/local/rabbitmq/var/log/rabbitmq/rabbit@izm5e8tkg8pwfsm4w7wpwiz.log
```
