# 微服务

## 服务注册与发现基础

![img](https://img-blog.csdn.net/20160229174312700)

### 服务注册流程

* 服务提供者B启动时注册自身ip、端口和元数据等到zookeeper
* 服务消费者A启动时获取最新的服务提供者B列表，并保存在缓存中；
* A调用B时根据负载均衡策略选择一个B进行请求。 

### 服务健康



### 服务下线

* 当某一个服务B由于异常或人为关闭后，zookeeper集群某一个节点会探测到B节点的变化，更新本节点B的服务列表；
* zookeeper集群多个节点进行数据同步；
* 客户端监听到B服务列表的变化，更新本地缓存。

### 自动重发机制

* B挂掉到A本地缓存列表更新，大概需要3-5s时间（根据网络环境不同）
* 此时当A访问B报错时，需要根据具体报错信息，处理特定异常进行自动重发。

### zookeeper负载均衡的实现思路

zookeeper没有内置负载均衡的实现，需要自己实现。但是可以利用zookeeper的树形数据结构、watcher机制等特性，把zookeeper作为服务和变更通知中心。

？nginx负载均衡的实现思路？

### zookeeper服务注册与发现的缺点

* 服务发现速度慢，从服务挂掉到消费者感知需要一定时间；
* zookeeper作为服务发现还存在可用性差的问题。因为zookeeper的设计只能保证CAP中的CP。
  * 任何时刻对zookeeper的访问请求能得到一致的数据结果

## 服务注册与发现进阶

### 如何确定注册的IP和端口？

**确定IP**

* 手动配置IP。适用于物理机房部署，IP比较稳定。
* 遍历网卡，找到第一个不为本地环回地址的IP地址。（dubbo就是采用这种方式）
* 手动指定网卡名，来指定使用哪一块网卡所对应的IP地址进行注册（需要对机房进行统一的网络规划）
* 以上三种如果行不通，则可以直接与服务注册中心建立socket连接，通过`socket.getLocalAddress()`获取本机IP。

**确定端口**

使用应用配置项中的端口

### 为了更好的实现服务治理，注册的信息需要包含哪些？

除了最基本的IP和Port信息外，还会有以下需求：

* 想知道某个HTTP服务是否开启了TLS。
* 想对相同服务下的不同节点设置不同的权重，进行流量调度。
* 将服务分为预发环境和生产环境，方便进行AB Test功能。
* 不同机房的服务注册时加上机房的标签，以实现同机房有限的路由规则。
* 业务应用支持多个框架协议时，想知道使用某种协议的应用有哪些？
* 想知道某个应用依赖哪些服务？

### 如何优雅的实现服务注册和服务下线？

### 如何进行注册服务的健康检查？

**客户端心跳**

* 客户端定时向服务端主动发送“心跳”，表明自己服务状态正常。心跳可以是TCP或HTTP；
* 客户端也可以通过维持与服务端的socket连接实现客户端心跳；

ps：zookeeper服务注册与发现是通过利用临时节点的特性来实现对服务的健康检查。一旦应用出现异常后断开，临时节点就会被删除，进而通知监听该节点的客户端。

缺点：客户端心跳只能保证链路正常，不能保证服务真的正常（如无法探测搭配DB连接异常）

**服务端主动探测**

* 服务端调用服务发布者某个HTTP接口来完成健康检查；
* 有些RPC引用不提供HTTP服务，也可以调用服务发布者的接口来完成健康检查；

缺点：主动调用接口无法做到通用

### 如何方便的查看某个应用发布和订阅了哪些服务，以及所订阅的服务有哪些节点？



## 服务注册与发现的技术选型

现状是采用zookeeper进行服务注册与发现。

服务注册：

本地配置ip和端口注册到zookeeper

服务发现：

监听某个服务的zookeeper节点，并将其放入本地缓存（key,ServiceCache）

通过ServiceCache.getInstances方法获取某个服务的所有实例；

随机挑选一个进行调用，并进行健康检查；

不可用，则在剩余其他服务中随机挑选一个健康的；

若都不健康，则随机挑选一个。

## 参考

* https://crossoverjie.top/2018/08/27/distributed/distributed-discovery-zk/