# zookeeper基础
## zookeeper概念
### Zookeeper数据模型

`zookeeper` 维护了一个类似文件系统的数据结构，每个子目录（/微信、/微信/公众号）都被称作为 `znode` 即节点。和文件系统一样，我们可以很轻松的对 `znode` 节点进行增加、删除等操作，而且还可以在一个`znode`下增加、删除`子znode`。每个`znode`可存储的最大数据量为1Mb。

### znode节点属性

![znode节点属性](https://mmbiz.qpic.cn/mmbiz_png/0OzaL5uW2aP4Ow8yhvlcRzp6Scq09sGrB1PP8mYwySoYgRkrkhrGsyLpMzI49H8gWyEvqYYrxutHSic5MUqsRXQ/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

### znode节点类型

* 持久节点
* 持久顺序节点
* 临时节点
* 临时顺序节点

## Zookeeper的灵魂Watcher机制

### 1、什么是watcher？

`watcher` 是`zooKeeper`中一个非常核心功能 ，客户端`watcher` 可以监控节点的数据变化以及它子节点的变化，一旦这些状态发生变化，zooKeeper服务端就会通知所有在这个节点上设置过`watcher`的客户端 ，从而每个客户端都很快感知，它所监听的节点状态发生变化，而做出对应的逻辑处理。

### 2、watcher类型？

znode节点可以设置两类watch，一种是对znode数据的**DataWatch**，一种是对znode孩子节点的**ChildWatch**。删除znode时会同时触发**DataWatch**和**ChildWatch**。如果被删除的节点还有父节点，父节点还会触发**ChildWatch**。

### 3、watcher特性

watch对节点的监听事件是一次性的。使用curator客户端时，可以自动重新设置监听。

## Zookeeper集群

Zookeeper 是一个由多个 server 组成的集群,一个 leader，多个 follower。（这个不同于我们常见的Master/Slave模式）

leader 为客户端服务器提供**读写服务**，除了leader外其他的机器只能提供**读服务**。

每个 server 保存一份数据副本全数据一致，分布式读 follower，**写由 leader 实施更新请求转发，由 leader 实施更新请求顺序进行，来自同一个 client 的更新请求按其发送顺序依次执行数据更新**。

原子性，一次数据更新要么成功，要么失败。

全局唯一数据视图，client 无论连接到哪个 server，数据视图都是一致的实时性，在一定事件范围内，client 能读到最新数据。

### 集群角色

* Leader

* Follower
    * 参与选举和提议

* Observer
    * 观察者可以提高读取的吞吐量；
    * 观察者不参与选举，也不响应提议，也不参与写操作的“过半写”策略
    * 观察者不需要持久化事务到磁盘

### 集群心跳机制

Leader和Follower之间通过**PING**来感知对方的存活，一旦与leader的心跳中断，就需要重新进行leader选举。

## Zookeeper的特性

* 顺序一致性，从同一个客户端发起的事务请求，最终将会严格地按照其发起顺序被应用到Zookeeper中去。

* 原子性，所有事务请求的处理结果在整个集群中所有机器上的应用情况是一致的，即整个集群要么都成功应用了某个事务，要么都没有应用。

* 单一视图，无论客户端连接的是哪个 Zookeeper 服务器，其看到的服务端数据模型都是一致的。

* 可靠性，一旦服务端成功地应用了一个事务，并完成对客户端的响应，那么该事务所引起的服务端状态变更将会一直被保留，除非有另一个事务对其进行了变更。

* 实时性，Zookeeper 保证在一定的时间段内，客户端最终一定能够从服务端上读取到最新的数据状态。

## Zookeeper的使用场景

* 服务注册与发现。
* 分布式锁。利用watcher机制和znode的临时有序节点。
* 分布式队列。利用有序节点。
* 配置管理。利用watcher机制，如Kafka使用Zookeeper维护broker的信息，dubbo管理服务的配置信息。一旦修改如/config节点的配置，就能通知各个客户端重新拉取配置信息。

## Zookeeper客户端curator

### 客户端调度策略

* 随机策略
* round robin策略（按顺序轮询）

### curator源码解析

### Watch监听机制(NodeCache,PathChildrenCache,TreeCache)

https://blog.csdn.net/hosaos/article/details/88658676

------

## 参考

* https://juejin.im/post/5baf7db75188255c3d11622e
* [你的简历写了 “熟悉” zookeeper ？那这些你会吗？](https://mp.weixin.qq.com/s/FdG5jd3zEh6ug3vbE-zhLg)